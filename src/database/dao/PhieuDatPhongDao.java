package database.dao;

import database.connectDB.ConnectDB;
import entitys.*;
import enums.TrangThaiPhong;
import enums.TrangThaiHoaDon;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PhieuDatPhongDao {

    // Tạo mã hóa đơn mới (giống PhieuThuePhongDao)
    public String taoMaHDMoi() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();

            // Lấy ngày hiện tại dạng ddMMyy
            LocalDateTime now = LocalDateTime.now();
            String ngayStr = String.format("%02d%02d%02d",
                    now.getDayOfMonth(),
                    now.getMonthValue(),
                    now.getYear() % 100);

            // Tìm số thứ tự lớn nhất trong ngày
            String sql = "SELECT MAX(maHD) FROM HoaDon WHERE maHD LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "HD" + ngayStr + "%");
            rs = stmt.executeQuery();

            int soThuTu = 0;
            if (rs.next()) {
                String maHDMax = rs.getString(1);
                if (maHDMax != null && maHDMax.startsWith("HD" + ngayStr)) {
                    String soStr = maHDMax.substring(8);
                    try {
                        soThuTu = Integer.parseInt(soStr.replaceFirst("^0+", ""));
                    } catch (NumberFormatException e) {
                        soThuTu = 0;
                    }
                }
            }

            // Tăng số thứ tự lên 1
            soThuTu++;

            // Tạo mã mới và kiểm tra xem đã tồn tại chưa
            String maHDMoi;
            int maxRetry = 100;
            int retryCount = 0;

            do {
                maHDMoi = "HD" + ngayStr + String.format("%03d", soThuTu);

                // Kiểm tra xem mã này đã tồn tại chưa
                String checkSql = "SELECT COUNT(*) FROM HoaDon WHERE maHD = ?";
                try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
                    checkStmt.setString(1, maHDMoi);
                    try (ResultSet checkRs = checkStmt.executeQuery()) {
                        if (checkRs.next()) {
                            int count = checkRs.getInt(1);
                            if (count == 0) {
                                System.out.println("Đã tạo mã HD mới (chưa tồn tại): " + maHDMoi);
                                break;
                            } else {
                                System.err.println("Mã HD đã tồn tại: " + maHDMoi + ", tăng số thứ tự...");
                                soThuTu++;
                                retryCount++;
                            }
                        }
                    }
                }
            } while (retryCount < maxRetry);

            if (retryCount >= maxRetry) {
                long timestamp = System.currentTimeMillis() % 100000;
                maHDMoi = "HD" + ngayStr + String.format("%05d", timestamp);
                System.err.println("Dùng timestamp để tạo mã HD: " + maHDMoi);
            }

            return maHDMoi;

        } catch (Exception e) {
            System.err.println("Lỗi tạo mã hóa đơn: " + e.getMessage());
            e.printStackTrace();

            // Fallback: tạo mã bằng timestamp
            try {
                LocalDateTime now = LocalDateTime.now();
                String ngayStr = String.format("%02d%02d%02d",
                        now.getDayOfMonth(),
                        now.getMonthValue(),
                        now.getYear() % 100);
                long timestamp = System.currentTimeMillis() % 1000000;
                String fallbackMaHD = "HD" + ngayStr + String.format("%06d", timestamp);
                System.err.println("Fallback: tạo mã HD: " + fallbackMaHD);
                return fallbackMaHD;
            } catch (Exception ex) {
                return null;
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                ConnectDB.closeConnection(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Lưu hóa đơn đặt phòng (không có người ở)
    public boolean luuHoaDonDatPhong(HoaDon hoaDon, ArrayList<ChiTietHoaDon> dsChiTiet) {
        Connection con = null;
        PreparedStatement psHoaDon = null;
        PreparedStatement psChiTiet = null;
        PreparedStatement psUpdatePhong = null;

        try {
            con = ConnectDB.getConnection();
            con.setAutoCommit(false);

            System.out.println("=== BẮT ĐẦU LƯU HÓA ĐƠN ĐẶT PHÒNG ===");
            System.out.println("Mã HD: " + hoaDon.getMaHD());
            System.out.println("Số chi tiết: " + (dsChiTiet != null ? dsChiTiet.size() : 0));

            // 0. Kiểm tra xem mã hóa đơn đã tồn tại chưa
            String checkSql = "SELECT COUNT(*) FROM HoaDon WHERE maHD = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
                checkStmt.setString(1, hoaDon.getMaHD());
                try (ResultSet checkRs = checkStmt.executeQuery()) {
                    if (checkRs.next() && checkRs.getInt(1) > 0) {
                        System.err.println("Mã hóa đơn đã tồn tại trong DB: " + hoaDon.getMaHD());
                        String newMaHD = taoMaHDMoi();
                        if (newMaHD == null) {
                            throw new SQLException("Không thể tạo mã hóa đơn mới");
                        }
                        System.out.println("Đã tạo mã mới thay thế: " + newMaHD);
                        hoaDon.setMaHD(newMaHD);
                    }
                }
            }

            // 1. Thêm hóa đơn
            System.out.println("1. Thêm hóa đơn đặt phòng...");
            String sqlHoaDon = "INSERT INTO HoaDon (maHD, ngayLap, phuongThucTT, trangThaiHD, tongTien, " +
                    "tienThue, phiDoiPhong, tongTienThanhToan, tienGiamHangKH, tongTienSauGiam, " +
                    "tienNhan, maKH, maNV) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            psHoaDon = con.prepareStatement(sqlHoaDon);
            psHoaDon.setString(1, hoaDon.getMaHD());
            psHoaDon.setTimestamp(2, Timestamp.valueOf(hoaDon.getNgayLap()));
            psHoaDon.setString(3, hoaDon.getpTTT().toString());
            psHoaDon.setString(4, hoaDon.getTrangThai().toString());
            psHoaDon.setDouble(5, hoaDon.getTongTien());
            psHoaDon.setDouble(6, hoaDon.getTienThue());
            psHoaDon.setDouble(7, hoaDon.getPhiDoiPhong());
            psHoaDon.setDouble(8, hoaDon.getTongTienThanhToan());
            psHoaDon.setDouble(9, hoaDon.getTienGiamTheoHangKH());
            psHoaDon.setDouble(10, hoaDon.getTongTienSauGiam());
            psHoaDon.setDouble(11, hoaDon.getTienNhan());
            psHoaDon.setString(12, hoaDon.getKhachHang().getMaKH());
            psHoaDon.setString(13, hoaDon.getNhanVien().getMaNV());
            psHoaDon.executeUpdate();
            System.out.println("✓ Đã thêm hóa đơn đặt phòng: " + hoaDon.getMaHD());

            // 2. Thêm chi tiết hóa đơn cho từng phòng
            System.out.println("2. Thêm chi tiết hóa đơn đặt phòng...");
            String sqlChiTiet = "INSERT INTO ChiTietHoaDon (maHD, maPhong, maKM, ngayNhanPhong, " +
                    "ngayTraPhong, soNgayO, tienGiam, thanhTien) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            psChiTiet = con.prepareStatement(sqlChiTiet);
            for (ChiTietHoaDon cthd : dsChiTiet) {
                if (cthd.getPhong() == null) {
                    System.err.println("LỖI: Chi tiết hóa đơn có phòng null!");
                    continue;
                }

                System.out.println("  - Thêm CT cho phòng: " + cthd.getPhong().getMaPhong());

                psChiTiet.setString(1, hoaDon.getMaHD());
                psChiTiet.setString(2, cthd.getPhong().getMaPhong());

                if (cthd.getKhuyenMai() != null && cthd.getKhuyenMai().getMaKM() != null) {
                    psChiTiet.setString(3, cthd.getKhuyenMai().getMaKM());
                } else {
                    psChiTiet.setNull(3, Types.VARCHAR);
                }

                psChiTiet.setTimestamp(4, Timestamp.valueOf(cthd.getNgayNhanPhong()));
                psChiTiet.setTimestamp(5, Timestamp.valueOf(cthd.getNgayTraPhong()));
                psChiTiet.setInt(6, cthd.getSoNgayO());
                psChiTiet.setDouble(7, cthd.getTienGiam());
                psChiTiet.setDouble(8, cthd.getThanhTien());

                psChiTiet.executeUpdate();
            }
            System.out.println("✓ Đã thêm " + dsChiTiet.size() + " chi tiết hóa đơn đặt phòng");

            // 3. Cập nhật trạng thái phòng thành "Đã đặt"
            System.out.println("3. Cập nhật trạng thái phòng thành Đã đặt...");
            String sqlUpdatePhong = "UPDATE Phong SET trangThaiPhong = ? WHERE maPhong = ?";
            psUpdatePhong = con.prepareStatement(sqlUpdatePhong);

            for (ChiTietHoaDon cthd : dsChiTiet) {
                if (cthd.getPhong() == null) continue;

                System.out.println("  - Cập nhật phòng: " + cthd.getPhong().getMaPhong() + " -> Đã đặt");

                // Đặt phòng thì trạng thái là "Đã đặt" thay vì "Đang sử dụng"
                psUpdatePhong.setString(1, TrangThaiPhong.DaDat.toString());
                psUpdatePhong.setString(2, cthd.getPhong().getMaPhong());
                psUpdatePhong.executeUpdate();
            }
            System.out.println("✓ Đã cập nhật trạng thái " + dsChiTiet.size() + " phòng");

            con.commit();
            System.out.println("=== HOÀN TẤT LƯU HÓA ĐƠN ĐẶT PHÒNG ===");
            return true;

        } catch (SQLException e) {
            System.err.println("❌ LỖI SQL KHI LƯU HÓA ĐƠN ĐẶT PHÒNG:");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();

            try {
                if (con != null) {
                    con.rollback();
                    System.err.println("Đã rollback transaction");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } catch (Exception e) {
            System.err.println("❌ LỖI KHÁC KHI LƯU HÓA ĐƠN ĐẶT PHÒNG:");
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback();
                    System.err.println("Đã rollback transaction");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (psUpdatePhong != null) psUpdatePhong.close();
                if (psChiTiet != null) psChiTiet.close();
                if (psHoaDon != null) psHoaDon.close();
                if (con != null) {
                    con.setAutoCommit(true);
                    ConnectDB.closeConnection(con);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Tính số ngày ở
    public int tinhSoNgayO(java.util.Date ngayBatDau, java.util.Date ngayKetThuc) {
        if (ngayBatDau == null || ngayKetThuc == null) {
            return 0;
        }
        long diff = ngayKetThuc.getTime() - ngayBatDau.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }
}