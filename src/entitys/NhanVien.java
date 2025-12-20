package entitys;

import enums.ChucVuNhanVien;

import java.time.LocalDate;
import java.util.Objects;

public class NhanVien {
    private String maNV;
    private String tenNV;
    private LocalDate ngaySinh;
    private String sdt;
    private boolean gioiTinh;
    private String email;
    private ChucVuNhanVien chucVu;
    private TaiKhoan taiKhoan;

    public String getMaNV() {
        return maNV;
    }
    public String getTenNV() {
        return tenNV;
    }
    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }
    public LocalDate getNgaySinh() {
        return ngaySinh;
    }
    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
    public String getSdt() {
        return sdt;
    }
    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    public boolean isGioiTinh() {
        return gioiTinh;
    }
    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public ChucVuNhanVien getChucVu() {
        return chucVu;
    }
    public void setChucVu(ChucVuNhanVien chucVu) {
        this.chucVu = chucVu;
    }
    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }
    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }
    public NhanVien(String maNV, String tenNV, LocalDate ngaySinh, String sdt, boolean gioiTinh, String email,
                    ChucVuNhanVien chucVu, TaiKhoan taiKhoan) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.chucVu = chucVu;
        this.taiKhoan = taiKhoan;
    }
    public NhanVien(String maNV, String tenNV, LocalDate ngaySinh, String sdt, boolean gioiTinh, String email,
                    ChucVuNhanVien chucVu) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.chucVu = chucVu;
        this.taiKhoan = taiKhoan;
    }
    

    public NhanVien(String maNV, String tenNV) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
	}
	public NhanVien(String maNV, TaiKhoan taiKhoan) {
        this.maNV = maNV;
        this.taiKhoan = taiKhoan;
    }

    public NhanVien() {

    }
    @Override
    public int hashCode() {
        return Objects.hash(maNV);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NhanVien other = (NhanVien) obj;
        return Objects.equals(maNV, other.maNV);
    }

}