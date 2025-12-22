package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import database.connectDB.ConnectDB;
import enums.TrangThaiPhong;

public class ThongKeTyLePhongDao {

    /**
     * Lấy thống kê phòng theo trạng thái (3 loại: Đang thuê, Trống, Đã đặt)
     */
    public Map<String, Integer> getThongKePhongTheoTrangThai() {
        Map<String, Integer> thongKe = new LinkedHashMap<>();
        Connection con = ConnectDB.getConnection();

        String sql = "SELECT " +
                "SUM(CASE WHEN trangThaiPhong IN ('DangSuDung') THEN 1 ELSE 0 END) as dangThue, " +
                "SUM(CASE WHEN trangThaiPhong = 'Trong' THEN 1 ELSE 0 END) as trong, " +
                "SUM(CASE WHEN trangThaiPhong IN ('DaDat') THEN 1 ELSE 0 END) as daDat " +
                "FROM Phong " +
                "WHERE trangThaiPhong != 'NgungHoatDong'";

        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                thongKe.put("Đang thuê", rs.getInt("dangThue"));
                thongKe.put("Trống", rs.getInt("trong"));
                thongKe.put("Đã đặt", rs.getInt("daDat"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(con);
        }

        // Đảm bảo luôn có đủ 3 loại
        if (!thongKe.containsKey("Đang thuê")) thongKe.put("Đang thuê", 0);
        if (!thongKe.containsKey("Trống")) thongKe.put("Trống", 0);
        if (!thongKe.containsKey("Đã đặt")) thongKe.put("Đã đặt", 0);

        return thongKe;
    }

    /**
     * Lấy tổng số phòng
     */
    public int getTongSoPhong() {
        int tong = 0;
        Connection con = ConnectDB.getConnection();

        String sql = "SELECT COUNT(*) as tong FROM Phong WHERE trangThaiPhong != 'NgungHoatDong'";

        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                tong = rs.getInt("tong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(con);
        }
        return tong;
    }

    /**
     * Lấy danh sách phòng trống
     */
    public String getDanhSachPhongTrong() {
        StringBuilder dsPhong = new StringBuilder();
        Connection con = ConnectDB.getConnection();

        String sql = "SELECT maPhong FROM Phong WHERE trangThaiPhong = 'Trong' ORDER BY maPhong";

        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            int count = 0;
            while (rs.next()) {
                if (count > 0) dsPhong.append(", ");
                dsPhong.append(rs.getString("maPhong"));
                count++;
                if (count >= 10) break; // Giới hạn hiển thị
            }

            if (count > 10) {
                dsPhong.append(", ...");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(con);
        }
        return dsPhong.toString();
    }

    /**
     * Tính tỷ lệ lấp đầy (Occupancy Rate)
     */
    public double getTyLeLapDay() {
        Map<String, Integer> thongKe = getThongKePhongTheoTrangThai();
        int tongPhong = getTongSoPhong();

        if (tongPhong == 0) return 0;

        int phongDangSuDung = thongKe.getOrDefault("Đang thuê", 0);
        return (double) phongDangSuDung / tongPhong * 100;
    }

    /**
     * Lấy tỷ lệ lấp đầy trung bình 3 tháng gần nhất
     * (Tính dựa trên hóa đơn trong 3 tháng gần nhất)
     */
    public double getTyLeLapDayTrungBinh3Thang() {
        Connection con = ConnectDB.getConnection();
        double tyLeTrungBinh = 0;
        int soThangCoDuLieu = 0;

        LocalDate now = LocalDate.now();

        // Tính 3 tháng gần nhất (không bao gồm tháng hiện tại)
        for (int i = 1; i <= 3; i++) {
            LocalDate thangTruoc = now.minusMonths(i);
            int thang = thangTruoc.getMonthValue();
            int nam = thangTruoc.getYear();

            // Tính số phòng được thuê trong tháng đó
            int soPhongDuocThue = getSoPhongDuocThueTrongThang(thang, nam);
            int tongPhong = getTongSoPhong();

            if (tongPhong > 0) {
                double tyLeThang = (double) soPhongDuocThue / tongPhong * 100;
                tyLeTrungBinh += tyLeThang;
                soThangCoDuLieu++;
            }
        }

        ConnectDB.closeConnection(con);

        return soThangCoDuLieu > 0 ? tyLeTrungBinh / soThangCoDuLieu : 0;
    }

    private int getSoPhongDuocThueTrongThang(int thang, int nam) {
        Connection con = ConnectDB.getConnection();
        int soPhong = 0;

        try {
            // Lấy số phòng được thuê trong tháng (từ hóa đơn)
            String sql = "SELECT COUNT(DISTINCT cthd.maPhong) as soPhong " +
                    "FROM ChiTietHoaDon cthd " +
                    "JOIN HoaDon hd ON cthd.maHD = hd.maHD " +
                    "WHERE MONTH(hd.ngayLap) = ? AND YEAR(hd.ngayLap) = ?";

            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, thang);
            st.setInt(2, nam);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                soPhong = rs.getInt("soPhong");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(con);
        }

        return soPhong;
    }

    /**
     * Tính tỷ lệ lấp đầy theo tháng (dựa trên hóa đơn)
     */
    private double getTyLeLapDayTheoThang(int thang, int nam) {
        Connection con = ConnectDB.getConnection();
        double tyLe = 0;

        try {
            // Lấy tổng số ngày phòng được thuê trong tháng
            String sql = "SELECT SUM(soNgayO) as tongNgayThue " +
                    "FROM ChiTietHoaDon cthd " +
                    "JOIN HoaDon hd ON cthd.maHD = hd.maHD " +
                    "WHERE MONTH(hd.ngayLap) = ? AND YEAR(hd.ngayLap) = ? " +
                    "AND hd.trangThaiHD = 'HoaDonHoanThanh'";

            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, thang);
            st.setInt(2, nam);
            ResultSet rs = st.executeQuery();

            int tongNgayThue = 0;
            if (rs.next()) {
                tongNgayThue = rs.getInt("tongNgayThue");
            }

            // Lấy tổng số phòng
            int tongPhong = getTongSoPhong();
            int soNgayTrongThang = getSoNgayTrongThang(thang, nam);

            // Tính tỷ lệ lấp đầy: (tổng ngày thuê) / (tổng phòng * số ngày)
            if (tongPhong > 0 && soNgayTrongThang > 0) {
                tyLe = (double) tongNgayThue / (tongPhong * soNgayTrongThang) * 100;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tyLe;
    }

    /**
     * Lấy số ngày trong tháng
     */
    private int getSoNgayTrongThang(int thang, int nam) {
        switch (thang) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                return (nam % 4 == 0 && (nam % 100 != 0 || nam % 400 == 0)) ? 29 : 28;
            default:
                return 30;
        }
    }

    /**
     * Kiểm tra cảnh báo tỷ lệ lấp đầy thấp
     */
    public boolean kiemTraCanhBaoTyLeLapDay() {
        double tyLeHienTai = getTyLeLapDay();
        double tyLeTB3Thang = getTyLeLapDayTrungBinh3Thang();

        // Cảnh báo nếu tỷ lệ hiện tại thấp hơn 60% và thấp hơn 80% so với trung bình
        if (tyLeHienTai < 60 && tyLeTB3Thang > 0 && tyLeHienTai < (tyLeTB3Thang * 0.8)) {
            return true;
        }
        return false;
    }
}