package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class HoaDonHuyPhong {
	private String maPH;
    private LocalDateTime ngayHuy;
    private String lyDoHuy;
    private Double tienHoanTra;
    private KhachHang khachHang;
    private HoaDon hoaDon;
	public String getMaPH() {
		return maPH;
	}
	public void setMaPH(String maPH) {
		this.maPH = maPH;
	}
	public LocalDateTime getNgayHuy() {
		return ngayHuy;
	}
	public void setNgayHuy(LocalDateTime ngayHuy) {
		this.ngayHuy = ngayHuy;
	}
	public String getLyDoHuy() {
		return lyDoHuy;
	}
	public void setLyDoHuy(String lyDoHuy) {
		this.lyDoHuy = lyDoHuy;
	}
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	public HoaDon getHoaDon() {
		return hoaDon;
	}
	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}
    //Dẫn xuất
	public Double getTienHoanTra() {
		return tienHoanTra;
	}
	public void setTienHoanTra() {
		
	}
	public HoaDonHuyPhong(String maPH, LocalDateTime ngayHuy, String lyDoHuy, KhachHang khachHang,
			HoaDon hoaDon) {
		super();
		this.maPH = maPH;
		this.ngayHuy = ngayHuy;
		this.lyDoHuy = lyDoHuy;
		setTienHoanTra();
		this.khachHang = khachHang;
		this.hoaDon = hoaDon;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maPH);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoaDonHuyPhong other = (HoaDonHuyPhong) obj;
		return Objects.equals(maPH, other.maPH);
	}
}
