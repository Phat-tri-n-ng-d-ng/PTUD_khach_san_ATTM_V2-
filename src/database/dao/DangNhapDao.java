
package database.dao;

import database.connectDB.ConnectDB;
import entitys.NhanVien;
import entitys.TaiKhoan;
import enums.TrangThaiTaiKhoan;
import enums.VaiTro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DangNhapDao {
    public TaiKhoan kiemTraDangNhap(String tenDangNhap, String matKhau) {
        Connection connection = null;
        TaiKhoan taiKhoan = null;

        try {
            connection = ConnectDB.getConnection();

            // Truy vấn kiểm tra đăng nhập
            String sql = "SELECT tenDangNhap, matKhau, vaiTro, trangThaiTK, maNV " +
                    "FROM TaiKhoan " +
                    "WHERE tenDangNhap = ? AND matKhau = ? " +
                    "AND trangThaiTK = 'DangHoatDong'";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, tenDangNhap);
            pstmt.setString(2, matKhau);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String tenDN = rs.getString("tenDangNhap");
                String mk = rs.getString("matKhau");
                VaiTro vaiTro = VaiTro.valueOf(rs.getString("vaiTro"));
                TrangThaiTaiKhoan trangThai = TrangThaiTaiKhoan.valueOf(rs.getString("trangThaiTK"));
                String maNV = rs.getString("maNV");

                taiKhoan = new TaiKhoan(tenDN, mk, vaiTro, trangThai, maNV);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(connection);
        }

        return taiKhoan;
    }

    // Lấy thông tin nhân viên theo mã NV
    // Lấy thông tin nhân viên theo mã NV
    public NhanVien getNhanVienByMaNV(String maNV) {
        Connection connection = null;
        NhanVien nhanVien = null;

        try {
            connection = ConnectDB.getConnection();

            String sql = "SELECT maNV, tenNV, chucVu, sdt, email, ngaySinh, gioiTinh " +
                    "FROM NhanVien " +
                    "WHERE maNV = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, maNV);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String maNhanVien = rs.getString("maNV");
                String tenNV = rs.getString("tenNV");
                String chucVuStr = rs.getString("chucVu");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");
                java.sql.Date ngaySinhSQL = rs.getDate("ngaySinh");
                boolean gioiTinh = rs.getBoolean("gioiTinh");

                // Chuyển đổi ngày sinh
                java.time.LocalDate ngaySinh = null;
                if (ngaySinhSQL != null) {
                    ngaySinh = ngaySinhSQL.toLocalDate();
                }

                // Sử dụng constructor mới của NhanVien
                nhanVien = new NhanVien(maNhanVien, tenNV, ngaySinh, sdt, gioiTinh, email, chucVuStr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(connection);
        }

        return nhanVien;
    }
}
