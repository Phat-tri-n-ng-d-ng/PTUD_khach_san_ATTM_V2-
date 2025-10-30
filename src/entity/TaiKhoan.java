package entity;

import enums.TrangThaiTaiKhoan;
import enums.VaiTro;

import java.util.Objects;

public class TaiKhoan {
	private String tenDangNhap;
	private String matKhau;
	private VaiTro vaiTro;
	private TrangThaiTaiKhoan trangThai;
	private String maNV;     // Mã nhân viên
	private String tenNV;    // Tên nhân viên (từ join)
	private String chucVu;   // Chức vụ (từ join)

	public String getTenDangNhap() {
		return tenDangNhap;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public VaiTro getVaiTro() {
		return vaiTro;
	}

	public void setVaiTro(VaiTro vaiTro) {
		this.vaiTro = vaiTro;
	}

	public TrangThaiTaiKhoan getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(TrangThaiTaiKhoan trangThai) {
		this.trangThai = trangThai;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getTenNV() {
		return tenNV;
	}

	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	public TaiKhoan(String tenDangNhap, String matKhau, VaiTro vaiTro) {
		this.tenDangNhap = tenDangNhap;
		this.matKhau = matKhau;
		this.vaiTro = vaiTro;
		this.trangThai = TrangThaiTaiKhoan.DangHoatDong;
	}
    public TaiKhoan(String tenDangNhap, TrangThaiTaiKhoan trangThai) {
        this.tenDangNhap = tenDangNhap;
        this.trangThai = trangThai;
    }

	@Override
	public int hashCode() {
		return Objects.hash(tenDangNhap);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaiKhoan other = (TaiKhoan) obj;
		return Objects.equals(tenDangNhap, other.tenDangNhap);
	}
}