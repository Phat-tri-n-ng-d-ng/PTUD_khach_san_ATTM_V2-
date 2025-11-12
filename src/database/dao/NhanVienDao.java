package database.dao;

import database.connectDB.ConnectDB;
import entitys.NhanVien;
import entitys.TaiKhoan;
import enums.ChucVuNhanVien;
import enums.TrangThaiTaiKhoan;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class NhanVienDao {
    public ArrayList<NhanVien> getTatCaNhanVien() {
        ArrayList<NhanVien> dsNhanVien = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "SELECT n.maNV, n.tenNV, n.ngaySinh, n.gioiTinh, n.sdt, n.email,n.ChucVu, tk.tenDangNhap, tk.trangThaiTK\n" +
                    "    FROM NhanVien n\n" +
                    "    LEFT JOIN TaiKhoan tk ON tk.maNV = n.maNV";

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                String maNV = rs.getString("maNV");
                String tenNV = rs.getString("tenNV");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                Boolean gioiTinh = rs.getBoolean("gioiTinh");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");
                String string_chucVu = rs.getString("chucVu");
                ChucVuNhanVien chucVu = ChucVuNhanVien.valueOf(string_chucVu);
                String tenDangNhap = rs.getString("tenDangNhap");
                String trangThaiTaiKhoan = rs.getString("trangThaiTK");
                TaiKhoan tk = null;
                if (tenDangNhap != null && trangThaiTaiKhoan != null) {
                    TrangThaiTaiKhoan trangThai = TrangThaiTaiKhoan.valueOf(trangThaiTaiKhoan);
                    tk = new TaiKhoan(tenDangNhap, trangThai);
                }
                NhanVien nv = new NhanVien(maNV, tenNV, ngaySinh, sdt, gioiTinh, email, chucVu,tk);
                dsNhanVien.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(connection);
        }
        return dsNhanVien;
    }

    public boolean themNhanVien(NhanVien nhanVien){
        Connection connection = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "insert into NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, chucVu)\n" +
                    "                    values (?,?,?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,nhanVien.getMaNV());
            stmt.setString(2,nhanVien.getTenNV());
            stmt.setDate(3, Date.valueOf(nhanVien.getNgaySinh()));
            stmt.setBoolean(4,nhanVien.isGioiTinh());
            stmt.setString(5,nhanVien.getSdt());
            stmt.setString(6,nhanVien.getEmail());
            stmt.setString(7,nhanVien.getChucVu().toString());
            int n = stmt.executeUpdate();
            return n>0;
        }catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("Lỗi khác: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        finally {
            ConnectDB.closeConnection(connection);
        }
    }

    public int getSoLuongNhanVien() {
        Connection connection = null;
        int soLuong = 0;
        try {
            connection = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) FROM NhanVien";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                soLuong = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(connection);
        }
        return soLuong;
    }

    public NhanVien TimNhanVien(String keyword, String type) {
        NhanVien nv = null;
        Connection connection = null;
        try {
            connection = ConnectDB.getConnection();
            String sql;
            if (type.equalsIgnoreCase("MA")) {
                sql = "select n.maNV, n.tenNV, n.ngaySinh, n.gioiTinh, n.sdt, n.email, n.chucVu, tk.tenDangNhap, tk.trangThaiTK \n" +
                        "    from NhanVien n\n" +
                        "    left join TaiKhoan tk on tk.maNV = n.maNV\n" +
                        "    where n.maNV = ?";
            } else if (type.equalsIgnoreCase("SDT")) {
                sql = "select n.maNV, n.tenNV, n.ngaySinh, n.gioiTinh, n.sdt, n.email, n.chucVu, tk.tenDangNhap, tk.trangThaiTK \n" +
                        "    from NhanVien n\n" +
                        "    left join TaiKhoan tk on tk.maNV = n.maNV\n" +
                        "where n.SDT = ?";
            } else {
                throw new IllegalArgumentException("Loại tìm kiếm không hợp lệ: " + type);
            }

            PreparedStatement  stmt = connection.prepareStatement(sql);
            stmt.setString(1, keyword);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String maNV = rs.getString("maNV");
                String tenNV = rs.getString("tenNV");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                Boolean gioiTinh = rs.getBoolean("gioiTinh");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");
                String string_chucVu = rs.getString("chucVu");
                ChucVuNhanVien chucVu = ChucVuNhanVien.valueOf(string_chucVu);
                String tenDangNhap = rs.getString("tenDangNhap");
                String trangThaiTaiKhoan = rs.getString("trangThaiTK");
                TaiKhoan tk = null;
                if (tenDangNhap != null && trangThaiTaiKhoan != null) {
                    TrangThaiTaiKhoan trangThai = TrangThaiTaiKhoan.valueOf(trangThaiTaiKhoan);
                    tk = new TaiKhoan(tenDangNhap, trangThai);
                }
                nv = new NhanVien(maNV, tenNV, ngaySinh, sdt, gioiTinh, email, chucVu,tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(connection);
        }
        return nv;
    }

    public ArrayList<NhanVien> getNhanVienTheoChucVu(String chucVuChon) {
        ArrayList<NhanVien> dsNhanVien = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "select n.maNV, n.tenNV, n.ngaySinh, n.gioiTinh, n.sdt, n.email, n.chucVu, tk.tenDangNhap, tk.trangThaiTK\n" +
                    "    from NhanVien n\n" +
                    "    left join TaiKhoan tk on tk.maNV = n.maNV\n" +
                    "    where chucVu = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, chucVuChon);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maNV = rs.getString("maNV");
                String tenNV = rs.getString("tenNV");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                Boolean gioiTinh = rs.getBoolean("gioiTinh");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");
                String string_chucVu = rs.getString("chucVu");
                ChucVuNhanVien chucVu = ChucVuNhanVien.valueOf(string_chucVu);
                String tenDangNhap = rs.getString("tenDangNhap");
                String trangThaiTaiKhoan = rs.getString("trangThaiTK");
                TaiKhoan tk = null;
                if (tenDangNhap != null && trangThaiTaiKhoan != null) {
                    TrangThaiTaiKhoan trangThai = TrangThaiTaiKhoan.valueOf(trangThaiTaiKhoan);
                    tk = new TaiKhoan(tenDangNhap, trangThai);
                }
                NhanVien nv = new NhanVien(maNV, tenNV, ngaySinh, sdt, gioiTinh, email, chucVu,tk);
                dsNhanVien.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsNhanVien;
    }

    public boolean CapNhatNhanVien(NhanVien nhanVien) {
        Connection connection = null;
        PreparedStatement stmtNV = null;
        PreparedStatement stmtTK = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "UPDATE NhanVien \n" +
                    "    SET tenNV = ?, ngaySinh = ?, gioiTinh = ?, sdt = ?, email = ?, chucVu = ? \n" +
                    "    WHERE maNV = ?";
            stmtNV = connection.prepareStatement(sql);
            stmtNV.setString(1,nhanVien.getTenNV());
            stmtNV.setDate(2, Date.valueOf(nhanVien.getNgaySinh()));
            stmtNV.setBoolean(3,nhanVien.isGioiTinh());
            stmtNV.setString(4,nhanVien.getSdt());
            stmtNV.setString(5,nhanVien.getEmail());
            stmtNV.setString(6,nhanVien.getChucVu().toString());
            stmtNV.setString(7,nhanVien.getMaNV());
            if (nhanVien.getTaiKhoan() != null) {
                String sqlTK = "UPDATE TaiKhoan SET trangThaiTK = ? WHERE maNV = ?";
                stmtTK = connection.prepareStatement(sqlTK);
                stmtTK.setString(1, nhanVien.getTaiKhoan().getTrangThai().toString());
                stmtTK.setString(2, nhanVien.getMaNV());
                stmtTK.executeUpdate();
            }
            int n = stmtNV.executeUpdate();
            return n>0;
        }catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("Lỗi khác: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        finally {
            ConnectDB.closeConnection(connection);
        }
    }

    public boolean TaoTaiKhoanNhanVien(NhanVien nhanVien) {
        Connection connection = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "INSERT into TaiKhoan (tenDangNhap, matKhau, vaiTro, trangThaiTK, maNV)\n" +
                    "    values(?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,nhanVien.getTaiKhoan().getTenDangNhap());
            stmt.setString(2,nhanVien.getTaiKhoan().getMatKhau());
            stmt.setString(3,nhanVien.getTaiKhoan().getVaiTro().toString());
            stmt.setString(4,nhanVien.getTaiKhoan().getTrangThai().toString());
            stmt.setString(5,nhanVien.getMaNV());
            int n = stmt.executeUpdate();
            return n>0;
        }catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("Lỗi khác: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        finally {
            ConnectDB.closeConnection(connection);
        }
    }
}
