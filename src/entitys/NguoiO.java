package entitys;

import java.time.LocalDate;
import java.util.Objects;

public class NguoiO {
    public NguoiO(HoaDon hoaDon, Phong phong) {
		super();
		this.hoaDon = hoaDon;
		this.phong = phong;
	}

	private String hoTen;
    private LocalDate ngaySinh;
    private String SDT;
    private String CCCD;
    private boolean gioiTinh;
    private HoaDon hoaDon;
    private Phong phong;

    public NguoiO(String hoTen, LocalDate ngaySinh, String sdt, String cccd, boolean gioiTinh) {
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.SDT = sdt;
        this.CCCD = cccd;
        this.gioiTinh = gioiTinh;
    }

    public NguoiO(String hoTen, LocalDate ngaySinh, String sDT, String cCCD, boolean gioiTinh, HoaDon hoaDon,
			Phong phong) {
		
		this.hoTen = hoTen;
		this.ngaySinh = ngaySinh;
		this.SDT = sDT;
		this.CCCD = cCCD;
		this.gioiTinh = gioiTinh;
		this.hoaDon = hoaDon;
		this.phong = phong;
	}

	public NguoiO() {
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	public Phong getPhong() {
		return phong;
	}

	public void setPhong(Phong phong) {
		this.phong = phong;
	}

	public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NguoiO nguoiO = (NguoiO) o;
        return Objects.equals(getCCCD(), nguoiO.getCCCD());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCCCD());
    }
}