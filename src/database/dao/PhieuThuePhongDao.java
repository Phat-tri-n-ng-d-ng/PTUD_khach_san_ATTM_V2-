package database.dao;

import database.connectDB.ConnectDB;
import entitys.*;
import enums.TrangThaiPhong;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PhieuThuePhongDao {

    // Tạo mã hóa đơn mới (SỬA LỖI TRÙNG MÃ)
    public String taoMaHDMoi() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();

            // Lấy ngày hiện tại dạng ddMMyy (2 số ngày, 2 số tháng, 2 số năm)
            LocalDateTime now = LocalDateTime.now();
            String ngayStr = String.format("%02d%02d%02d",
                    now.getDayOfMonth(),
                    now.getMonthValue(),
                    now.getYear() % 100);

            // Tìm số thứ tự lớn nhất trong ngày (sửa lại logic để tránh trùng)
            String sql = "SELECT MAX(maHD) FROM HoaDon WHERE maHD LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "HD" + ngayStr + "%");
            rs = stmt.executeQuery();

            int soThuTu = 0;
            if (rs.next()) {
                String maHDMax = rs.getString(1);
                if (maHDMax != null && maHDMax.startsWith("HD" + ngayStr)) {
                    // Lấy phần số sau "HD" + ngayStr
                    String soStr = maHDMax.substring(8); // "HD" (2) + 6 số ngày = 8 ký tự
                    try {
                        // Xóa các số 0 ở đầu và chuyển thành số
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
            int maxRetry = 100; // Giới hạn số lần thử
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
                                // Mã chưa tồn tại
                                System.out.println("Đã tạo mã HD mới (chưa tồn tại): " + maHDMoi);
                                break;
                            } else {
                                // Mã đã tồn tại, tăng số lên
                                System.err.println("Mã HD đã tồn tại: " + maHDMoi + ", tăng số thứ tự...");
                                soThuTu++;
                                retryCount++;
                            }
                        }
                    }
                }
            } while (retryCount < maxRetry);

            if (retryCount >= maxRetry) {
                // Nếu vẫn không tạo được mã sau nhiều lần thử, dùng timestamp
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

    // Lưu hóa đơn thuê phòng (transaction) - THÊM KIỂM TRA TRÙNG MÃ TRƯỚC KHI INSERT
    public boolean luuHoaDonThuePhong(HoaDon hoaDon, ArrayList<ChiTietHoaDon> dsChiTiet, ArrayList<NguoiO> dsNguoiO) {
        Connection con = null;
        PreparedStatement psHoaDon = null;
        PreparedStatement psChiTiet = null;
        PreparedStatement psNguoiO = null;
        PreparedStatement psUpdatePhong = null;

        try {
            con = ConnectDB.getConnection();
            con.setAutoCommit(false); // Bắt đầu transaction

            System.out.println("=== BẮT ĐẦU LƯU HÓA ĐƠN ===");
            System.out.println("Mã HD: " + hoaDon.getMaHD());
            System.out.println("Số chi tiết: " + (dsChiTiet != null ? dsChiTiet.size() : 0));
            System.out.println("Số người ở: " + (dsNguoiO != null ? dsNguoiO.size() : 0));

            // 0. Kiểm tra xem mã hóa đơn đã tồn tại chưa (double-check)
            String checkSql = "SELECT COUNT(*) FROM HoaDon WHERE maHD = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
                checkStmt.setString(1, hoaDon.getMaHD());
                try (ResultSet checkRs = checkStmt.executeQuery()) {
                    if (checkRs.next() && checkRs.getInt(1) > 0) {
                        // Mã đã tồn tại, cần tạo mã mới
                        System.err.println("Mã hóa đơn đã tồn tại trong DB: " + hoaDon.getMaHD());

                        // Tạo mã mới
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
            System.out.println("1. Thêm hóa đơn...");
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
            System.out.println("✓ Đã thêm hóa đơn: " + hoaDon.getMaHD());

            // 2. Thêm chi tiết hóa đơn cho từng phòng
            System.out.println("2. Thêm chi tiết hóa đơn...");
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

                psChiTiet.executeUpdate(); // Thực thi từng dòng
            }
            System.out.println("✓ Đã thêm " + dsChiTiet.size() + " chi tiết hóa đơn");

            // 3. Thêm người ở (nếu có)
            if (dsNguoiO != null && !dsNguoiO.isEmpty()) {
                System.out.println("3. Thêm người ở...");
                String sqlNguoiO = "INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, " +
                        "soCCCD, maHD, maPhong) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

                psNguoiO = con.prepareStatement(sqlNguoiO);
                for (NguoiO nguoi : dsNguoiO) {
                    if (nguoi.getPhong() == null) {
                        System.err.println("LỖI: Người ở có phòng null!");
                        continue;
                    }

                    System.out.println("  - Thêm người: " + nguoi.getHoTen() + " - Phòng: " + nguoi.getPhong().getMaPhong());

                    psNguoiO.setString(1, nguoi.getHoTen());
                    psNguoiO.setDate(2, Date.valueOf(nguoi.getNgaySinh()));
                    psNguoiO.setBoolean(3, nguoi.isGioiTinh());
                    psNguoiO.setString(4, nguoi.getSDT());
                    psNguoiO.setString(5, nguoi.getCCCD());
                    psNguoiO.setString(6, hoaDon.getMaHD());
                    psNguoiO.setString(7, nguoi.getPhong().getMaPhong());

                    psNguoiO.executeUpdate(); // Thực thi từng dòng
                }
                System.out.println("✓ Đã thêm " + dsNguoiO.size() + " người ở");
            } else {
                System.out.println("3. Không có người ở để thêm");
            }

            // 4. Cập nhật trạng thái phòng thành "Đang sử dụng"
            System.out.println("4. Cập nhật trạng thái phòng...");
            String sqlUpdatePhong = "UPDATE Phong SET trangThaiPhong = ? WHERE maPhong = ?";
            psUpdatePhong = con.prepareStatement(sqlUpdatePhong);

            for (ChiTietHoaDon cthd : dsChiTiet) {
                if (cthd.getPhong() == null) continue;

                System.out.println("  - Cập nhật phòng: " + cthd.getPhong().getMaPhong() + " -> Đang sử dụng");

                psUpdatePhong.setString(1, TrangThaiPhong.DangSuDung.toString());
                psUpdatePhong.setString(2, cthd.getPhong().getMaPhong());
                psUpdatePhong.executeUpdate(); // Thực thi từng dòng
            }
            System.out.println("✓ Đã cập nhật trạng thái " + dsChiTiet.size() + " phòng");

            con.commit(); // Commit transaction
            System.out.println("=== HOÀN TẤT LƯU HÓA ĐƠN ===");
            return true;

        } catch (SQLException e) {
            System.err.println("❌ LỖI SQL KHI LƯU HÓA ĐƠN:");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());

            // In chi tiết lỗi
            e.printStackTrace();

            try {
                if (con != null) {
                    con.rollback(); // Rollback nếu có lỗi
                    System.err.println("Đã rollback transaction");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } catch (Exception e) {
            System.err.println("❌ LỖI KHÁC KHI LƯU HÓA ĐƠN:");
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
                // Đóng tất cả các PreparedStatement
                if (psUpdatePhong != null) psUpdatePhong.close();
                if (psNguoiO != null) psNguoiO.close();
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

    // Tính số ngày ở dựa trên ngày bắt đầu và kết thúc
    public int tinhSoNgayO(java.util.Date ngayBatDau, java.util.Date ngayKetThuc) {
        if (ngayBatDau == null || ngayKetThuc == null) {
            return 0;
        }

        long diff = ngayKetThuc.getTime() - ngayBatDau.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }
}