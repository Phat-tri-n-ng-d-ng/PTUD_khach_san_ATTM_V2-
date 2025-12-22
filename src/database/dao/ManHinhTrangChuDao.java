package database.dao;

import database.connectDB.ConnectDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManHinhTrangChuDao {

    // Lấy danh sách phòng đang thuê (ngày trả = hôm nay)
    public ArrayList<Object[]> getPhongDangThueHomNay() {
        ArrayList<Object[]> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT DISTINCT p.maPhong, kh.tenKH, kh.sdt, cthd.ngayNhanPhong, cthd.ngayTraPhong " +
                    "FROM ChiTietHoaDon cthd " +
                    "JOIN HoaDon hd ON cthd.maHD = hd.maHD " +
                    "JOIN KhachHang kh ON hd.maKH = kh.maKH " +
                    "JOIN Phong p ON cthd.maPhong = p.maPhong " +
                    "WHERE hd.trangThaiHD = 'PhieuThuePhong' " +
                    "AND CAST(cthd.ngayTraPhong AS DATE) = CAST(GETDATE() AS DATE) " +
                    "ORDER BY p.maPhong";

            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getString("maPhong");
                row[1] = rs.getString("tenKH");
                row[2] = rs.getString("sdt");
                row[3] = formatDate(rs.getTimestamp("ngayNhanPhong"));
                row[4] = formatDate(rs.getTimestamp("ngayTraPhong"));
                row[5] = "Đang thuê";
                row[6] = "Trả phòng";
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(con, stmt, rs);
        }
        return result;
    }

    // Lấy danh sách phòng đã đặt (ngày nhận = hôm nay)
    public ArrayList<Object[]> getPhongDaDatHomNay() {
        ArrayList<Object[]> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT DISTINCT p.maPhong, kh.tenKH, kh.sdt, cthd.ngayNhanPhong, cthd.ngayTraPhong " +
                    "FROM ChiTietHoaDon cthd " +
                    "JOIN HoaDon hd ON cthd.maHD = hd.maHD " +
                    "JOIN KhachHang kh ON hd.maKH = kh.maKH " +
                    "JOIN Phong p ON cthd.maPhong = p.maPhong " +
                    "WHERE hd.trangThaiHD = 'PhieuDatPhong' " +
                    "AND CAST(cthd.ngayNhanPhong AS DATE) = CAST(GETDATE() AS DATE) " +
                    "ORDER BY p.maPhong";

            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getString("maPhong");
                row[1] = rs.getString("tenKH");
                row[2] = rs.getString("sdt");
                row[3] = formatDate(rs.getTimestamp("ngayNhanPhong"));
                row[4] = formatDate(rs.getTimestamp("ngayTraPhong"));
                row[5] = "Đã đặt";
                row[6] = "Nhận phòng";
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(con, stmt, rs);
        }
        return result;
    }

    // Tính tổng doanh thu tháng hiện tại
    public double getTongDoanhThuThangHienTai() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        double tongDoanhThu = 0;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT ISNULL(SUM(tongTienSauGiam), 0) as tongDoanhThu " +
                    "FROM HoaDon " +
                    "WHERE MONTH(ngayLap) = MONTH(GETDATE()) " +
                    "AND YEAR(ngayLap) = YEAR(GETDATE())";

            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                tongDoanhThu = rs.getDouble("tongDoanhThu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(con, stmt, rs);
        }
        return tongDoanhThu;
    }

    // Lấy doanh thu 5 tháng gần nhất
    public List<Double> getDoanhThu5ThangGanNhat() {
        List<Double> doanhThuList = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT TOP 5 MONTH(ngayLap) as thang, YEAR(ngayLap) as nam, " +
                    "ISNULL(SUM(tongTienSauGiam), 0) as doanhThu " +
                    "FROM HoaDon " +
                    "WHERE YEAR(ngayLap) = YEAR(GETDATE()) " +
                    "GROUP BY MONTH(ngayLap), YEAR(ngayLap) " +
                    "ORDER BY nam DESC, thang DESC";

            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                doanhThuList.add(rs.getDouble("doanhThu"));
            }

            // Đảo ngược để có thứ tự từ cũ đến mới
            List<Double> reversed = new ArrayList<>();
            for (int i = doanhThuList.size() - 1; i >= 0; i--) {
                reversed.add(doanhThuList.get(i));
            }
            return reversed;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(con, stmt, rs);
        }
        return doanhThuList;
    }

    // Lấy số lượng phòng đang thuê
    public int getSoLuongPhongDangThue() {
        return getSoLuongPhongTheoTrangThai("PhieuThuePhong");
    }

    // Lấy số lượng phòng đã đặt
    public int getSoLuongPhongDaDat() {
        return getSoLuongPhongTheoTrangThai("PhieuDatPhong");
    }

    private int getSoLuongPhongTheoTrangThai(String trangThai) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int soLuong = 0;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT COUNT(DISTINCT cthd.maPhong) as soLuong " +
                    "FROM ChiTietHoaDon cthd " +
                    "JOIN HoaDon hd ON cthd.maHD = hd.maHD " +
                    "WHERE hd.trangThaiHD = ?";

            stmt = con.prepareStatement(sql);
            stmt.setString(1, trangThai);
            rs = stmt.executeQuery();

            if (rs.next()) {
                soLuong = rs.getInt("soLuong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(con, stmt, rs);
        }
        return soLuong;
    }

    // Lấy tổng số phòng
    public int getTongSoPhong() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int tongSoPhong = 0;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) as tongSoPhong FROM Phong";

            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                tongSoPhong = rs.getInt("tongSoPhong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(con, stmt, rs);
        }
        return tongSoPhong;
    }

    // Tìm kiếm phòng theo mã phòng hoặc số điện thoại
    public ArrayList<Object[]> timKiemPhongTheoMaHoacSDT(String keyword) {
        ArrayList<Object[]> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT DISTINCT p.maPhong, kh.tenKH, kh.sdt, cthd.ngayNhanPhong, cthd.ngayTraPhong, " +
                    "CASE WHEN hd.trangThaiHD = 'PhieuThuePhong' THEN 'Đang thuê' ELSE 'Đã đặt' END as trangThai " +
                    "FROM ChiTietHoaDon cthd " +
                    "JOIN HoaDon hd ON cthd.maHD = hd.maHD " +
                    "JOIN KhachHang kh ON hd.maKH = kh.maKH " +
                    "JOIN Phong p ON cthd.maPhong = p.maPhong " +
                    "WHERE (p.maPhong LIKE ? OR kh.sdt LIKE ?) " +
                    "AND hd.trangThaiHD IN ('PhieuThuePhong', 'PhieuDatPhong') " +
                    // Thêm điều kiện: CHỈ lấy phòng có ngày trả phòng = hôm nay (nếu là PhieuThuePhong)
                    // hoặc ngày nhận phòng = hôm nay (nếu là PhieuDatPhong)
                    "AND (" +
                    "   (hd.trangThaiHD = 'PhieuThuePhong' AND CAST(cthd.ngayTraPhong AS DATE) = CAST(GETDATE() AS DATE)) " +
                    "   OR " +
                    "   (hd.trangThaiHD = 'PhieuDatPhong' AND CAST(cthd.ngayNhanPhong AS DATE) = CAST(GETDATE() AS DATE))" +
                    ") " +
                    "ORDER BY p.maPhong";

            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getString("maPhong");
                row[1] = rs.getString("tenKH");
                row[2] = rs.getString("sdt");
                row[3] = formatDate(rs.getTimestamp("ngayNhanPhong"));
                row[4] = formatDate(rs.getTimestamp("ngayTraPhong"));
                row[5] = rs.getString("trangThai");
                row[6] = row[5].equals("Đang thuê") ? "Trả phòng" : "Nhận phòng";
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(con, stmt, rs);
        }
        return result;
    }

    // Tính phần trăm tăng trưởng của phòng đang thuê so với tháng trước
    public double getPhanTramTangTruongPhongDangThue() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        double phanTram = 15.0; // Mặc định 15%

        try {
            con = ConnectDB.getConnection();
            String sql = "WITH ThangNay AS ( " +
                    "    SELECT COUNT(DISTINCT cthd.maPhong) as soLuong " +
                    "    FROM ChiTietHoaDon cthd " +
                    "    JOIN HoaDon hd ON cthd.maHD = hd.maHD " +
                    "    WHERE hd.trangThaiHD = 'PhieuThuePhong' " +
                    "    AND MONTH(cthd.ngayNhanPhong) = MONTH(GETDATE()) " +
                    "), ThangTruoc AS ( " +
                    "    SELECT COUNT(DISTINCT cthd.maPhong) as soLuong " +
                    "    FROM ChiTietHoaDon cthd " +
                    "    JOIN HoaDon hd ON cthd.maHD = hd.maHD " +
                    "    WHERE hd.trangThaiHD = 'PhieuThuePhong' " +
                    "    AND MONTH(cthd.ngayNhanPhong) = MONTH(DATEADD(MONTH, -1, GETDATE())) " +
                    ") " +
                    "SELECT CASE WHEN ThangTruoc.soLuong = 0 THEN 100 " +
                    "       ELSE ((ThangNay.soLuong - ThangTruoc.soLuong) * 100.0 / ThangTruoc.soLuong) " +
                    "       END as phanTram " +
                    "FROM ThangNay, ThangTruoc";

            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                phanTram = rs.getDouble("phanTram");
                if (phanTram < 0) phanTram = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(con, stmt, rs);
        }
        return phanTram;
    }

    // Tính phần trăm tăng trưởng của phòng đã đặt so với tháng trước
    public double getPhanTramTangTruongPhongDaDat() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        double phanTram = 32.0; // Mặc định 32%

        try {
            con = ConnectDB.getConnection();
            String sql = "WITH ThangNay AS ( " +
                    "    SELECT COUNT(DISTINCT cthd.maPhong) as soLuong " +
                    "    FROM ChiTietHoaDon cthd " +
                    "    JOIN HoaDon hd ON cthd.maHD = hd.maHD " +
                    "    WHERE hd.trangThaiHD = 'PhieuDatPhong' " +
                    "    AND MONTH(cthd.ngayNhanPhong) = MONTH(GETDATE()) " +
                    "), ThangTruoc AS ( " +
                    "    SELECT COUNT(DISTINCT cthd.maPhong) as soLuong " +
                    "    FROM ChiTietHoaDon cthd " +
                    "    JOIN HoaDon hd ON cthd.maHD = hd.maHD " +
                    "    WHERE hd.trangThaiHD = 'PhieuDatPhong' " +
                    "    AND MONTH(cthd.ngayNhanPhong) = MONTH(DATEADD(MONTH, -1, GETDATE())) " +
                    ") " +
                    "SELECT CASE WHEN ThangTruoc.soLuong = 0 THEN 100 " +
                    "       ELSE ((ThangNay.soLuong - ThangTruoc.soLuong) * 100.0 / ThangTruoc.soLuong) " +
                    "       END as phanTram " +
                    "FROM ThangNay, ThangTruoc";

            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                phanTram = rs.getDouble("phanTram");
                if (phanTram < 0) phanTram = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(con, stmt, rs);
        }
        return phanTram;
    }

    // Helper method để format date
    private String formatDate(Timestamp timestamp) {
        if (timestamp == null) return "";
        return new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(timestamp.getTime()));
    }

    // Helper method để đóng resources
    private void closeResources(Connection con, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tính phần trăm tăng trưởng của doanh thu so với tháng trước (có thể âm nếu giảm)
    public double getPhanTramTangTruongDoanhThu() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        double phanTram = 0.0; // Mặc định 0%

        try {
            con = ConnectDB.getConnection();
            String sql = "WITH ThangNay AS ( " +
                    "    SELECT ISNULL(SUM(tongTienSauGiam), 0) as doanhThu " +
                    "    FROM HoaDon " +
                    "    WHERE MONTH(ngayLap) = MONTH(GETDATE()) " +
                    "    AND YEAR(ngayLap) = YEAR(GETDATE()) " +
                    "), ThangTruoc AS ( " +
                    "    SELECT ISNULL(SUM(tongTienSauGiam), 0) as doanhThu " +
                    "    FROM HoaDon " +
                    "    WHERE MONTH(ngayLap) = MONTH(DATEADD(MONTH, -1, GETDATE())) " +
                    "    AND YEAR(ngayLap) = YEAR(DATEADD(MONTH, -1, GETDATE())) " +
                    ") " +
                    "SELECT CASE WHEN ThangTruoc.doanhThu = 0 THEN 100 " +
                    "       ELSE ((ThangNay.doanhThu - ThangTruoc.doanhThu) * 100.0 / ThangTruoc.doanhThu) " +
                    "       END as phanTram " +
                    "FROM ThangNay, ThangTruoc";

            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                phanTram = rs.getDouble("phanTram");
                // Không set phanTram < 0 = 0 nữa, để giữ giá trị âm nếu giảm
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(con, stmt, rs);
        }
        return phanTram;
    }
}