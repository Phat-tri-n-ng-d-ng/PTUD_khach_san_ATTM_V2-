package entity;

public class ChiTietHoaDon {
	private double thanhTien;
    private Phong phong;
    private HoaDon HoaDon;
    private int soNgayO;

    public ChiTietHoaDon() {

    }

    public int getSoNgayO() {
        return soNgayO;
    }

    public HoaDon getHoaDon() {
		return HoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		HoaDon = hoaDon;
	}

	public void setSoNgayO(int soNgayO) {
        this.soNgayO = soNgayO;
    }

    public Phong getPhong() {
        return phong;
    }

    public void setPhong(Phong phong) {
        this.phong = phong;
    }

    //	public HoaDon getHoaDon() {
//		return HoaDon;
//	}
//	public void setHoaDon(HoaDon hoaDon) {
//		HoaDon = hoaDon;
//	}
    // dẫn xuất
    private double tinhThanhTien() {
        return soNgayO * phong.getGiaPhong();
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(Phong phong, int soNgayO) {
        this.thanhTien = phong.getGiaPhong() * soNgayO;
    }

    public ChiTietHoaDon(Phong phong, HoaDon hoaDon, int soNgayO) {
        setThanhTien(phong, soNgayO);
    }

    public ChiTietHoaDon(Phong phong,  int soNgayO) {
        this.phong = phong;
        this.soNgayO = soNgayO;
        this.thanhTien = tinhThanhTien();
    }
    public ChiTietHoaDon(Phong phong, entity.HoaDon hoaDon) {
            super();
            this.phong = phong;
            HoaDon = hoaDon;
        }

	public ChiTietHoaDon(double thanhTien, Phong phong, entity.HoaDon hoaDon, int soNgayO) {
            super();
            this.thanhTien = thanhTien;
            this.phong = phong;
            HoaDon = hoaDon;
            this.soNgayO = soNgayO;
        }
}
