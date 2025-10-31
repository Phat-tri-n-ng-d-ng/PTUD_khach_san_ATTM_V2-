package entitys;

import enums.TrangThaiKhuyenMai;
import java.time.LocalDateTime;
import java.util.Objects;

public class KhuyenMai {
	private String maKM;
	private String tenKM;
	private String dieuKienApDung;
	private double tyLeGiam;
	private LocalDateTime ngayBatDau;
	private LocalDateTime ngayketThuc;
	private TrangThaiKhuyenMai trangThai;

    public KhuyenMai(String maKM) {
        this.maKM=maKM;
    }

    public String getMaKM() {
		return maKM;
	}
	public String getTenKM() {
		return tenKM;
	}
	public void setTenKM(String tenKM) {
		this.tenKM = tenKM;
	}
	public String getDieuKienApDung() {
		return dieuKienApDung;
	}
	public void setDieuKienApDung(String dieuKienApDung) {
		this.dieuKienApDung = dieuKienApDung;
	}
	public double getTyLeGiam() {
		return tyLeGiam;
	}
	public void setTyLeGiam(double tyLeGiam) {
		this.tyLeGiam = tyLeGiam;
	}
	public LocalDateTime getNgayBatDau() {
		return ngayBatDau;
	}
	public void setNgayBatDau(LocalDateTime ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}
	public LocalDateTime getNgayketThuc() {
		return ngayketThuc;
	}
	public void setNgayketThuc(LocalDateTime ngayketThuc) {
		this.ngayketThuc = ngayketThuc;
	}
	public TrangThaiKhuyenMai getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(TrangThaiKhuyenMai trangThai) {
		this.trangThai = trangThai;
	}
	public KhuyenMai(String maKM, String tenKM, String dieuKienApDung, double tyLeGiam,
					 LocalDateTime ngayBatDau, LocalDateTime ngayketThuc , TrangThaiKhuyenMai trangThai) {
		this.maKM = maKM;
		this.tenKM = tenKM;
		this.dieuKienApDung = dieuKienApDung;
		this.tyLeGiam = tyLeGiam;
		this.ngayBatDau = ngayBatDau;
		this.ngayketThuc = ngayketThuc;
		this.trangThai = trangThai;
	}
	
	public KhuyenMai(double tyLeGiam) {
		super();
		this.tyLeGiam = tyLeGiam;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maKM);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhuyenMai other = (KhuyenMai) obj;
		return Objects.equals(maKM, other.maKM);
	}
}