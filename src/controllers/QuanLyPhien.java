
package controllers;

import entitys.NhanVien;
import entitys.TaiKhoan;

public class QuanLyPhien {
    private static QuanLyPhien instance;
    private TaiKhoan taiKhoanDangNhap;
    private NhanVien nhanVienDangNhap;

    // Phương thức lấy thể hiện duy nhất của lớp
    public static QuanLyPhien getInstance() {
        if (instance == null) {
            instance = new QuanLyPhien();
        }
        return instance;
    }

    public TaiKhoan getTaiKhoanDangNhap() {
        return taiKhoanDangNhap;
    }

    public void setTaiKhoanDangNhap(TaiKhoan taiKhoanDangNhap) {
        this.taiKhoanDangNhap = taiKhoanDangNhap;
    }

    public NhanVien getNhanVienDangNhap() {
        return nhanVienDangNhap;
    }

    public void setNhanVienDangNhap(NhanVien nhanVienDangNhap) {
        this.nhanVienDangNhap = nhanVienDangNhap;
    }

    // Lấy mã nhân viên từ phiên (để sử dụng cho hóa đơn)
    public String getMaNV() {
        if (taiKhoanDangNhap != null) {
            return taiKhoanDangNhap.getMaNV();
        }
        return null;
    }

    // Lấy tên nhân viên từ phiên
    public String getTenNV() {
        if (nhanVienDangNhap != null) {
            return nhanVienDangNhap.getTenNV();
        }
        return null;
    }

    // Lấy chức vụ nhân viên từ phiên
    public enums.ChucVuNhanVien getChucVu() {
        if (nhanVienDangNhap != null) {
            return nhanVienDangNhap.getChucVu();
        }
        return null;
    }

    // Lấy vai trò từ tài khoản
    public enums.VaiTro getVaiTro() {
        if (taiKhoanDangNhap != null) {
            return taiKhoanDangNhap.getVaiTro();
        }
        return null;
    }

    // Kiểm tra xem đã đăng nhập chưa
    public boolean daDangNhap() {
        return taiKhoanDangNhap != null && nhanVienDangNhap != null;
    }

    // Xóa phiên (khi đăng xuất)
    public void xoaPhien() {
        taiKhoanDangNhap = null;
        nhanVienDangNhap = null;
    }
}
