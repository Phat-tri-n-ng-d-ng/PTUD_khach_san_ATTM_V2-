package entitys;

import java.time.LocalDateTime;

public class ChiTietHoaDon {
	private double thanhTien;
    private Phong phong;
    private HoaDon HoaDon;
    private int soNgayO;
    private KhuyenMai khuyenMai;
    private double tienGiam;
    private LocalDateTime ngayNhanPhong;
    private LocalDateTime ngayTraPhong;
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
    public KhuyenMai getKhuyenMai() {
        return khuyenMai;
    }
    public void setKhuyenMai(KhuyenMai khuyenMai) {
        this.khuyenMai = khuyenMai;
    }
    

    public double getTienGiam() {
		return tienGiam;
	}

	public void setTienGiam(double tienGiam) {
		try {
            if (this.khuyenMai != null && HoaDon.getTongTien() > 0) {
                this.tienGiam = HoaDon.getTongTien() * this.khuyenMai.getTyLeGiam();
            } else {
                this.tienGiam = 0;
            }
        } catch (Exception e) {
            this.tienGiam = 0; // Nếu có lỗi, set tiền giảm = 0
        }
	}
	public LocalDateTime getNgayNhanPhong() {
        return ngayNhanPhong;
    }
    public void setNgayNhanPhong(LocalDateTime ngayNhanPhong) {
        this.ngayNhanPhong = ngayNhanPhong;
    }
    public LocalDateTime getNgayTraPhong() {
        return ngayTraPhong;
    }
    public void setNgayTraPhong(LocalDateTime ngayTraPhong) {
        this.ngayTraPhong = ngayTraPhong;
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
    public ChiTietHoaDon(Phong phong, entitys.HoaDon hoaDon) {
            super();
            this.phong = phong;
            HoaDon = hoaDon;
        }

	public ChiTietHoaDon(double thanhTien, Phong phong, entitys.HoaDon hoaDon, int soNgayO, KhuyenMai khuyenMai,
			double tienGiam, LocalDateTime ngayNhanPhong, LocalDateTime ngayTraPhong) {
		this.thanhTien = thanhTien;
		this.phong = phong;
		HoaDon = hoaDon;
		this.soNgayO = soNgayO;
		this.khuyenMai = khuyenMai;
		this.tienGiam = tienGiam;
		this.ngayNhanPhong = ngayNhanPhong;
		this.ngayTraPhong = ngayTraPhong;
	}
	public ChiTietHoaDon(Phong phong, int soNgayO, KhuyenMai khuyenMai, LocalDateTime ngayNhanPhong,
			LocalDateTime ngayTraPhong) {
		this.phong = phong;
		this.soNgayO = soNgayO;
		this.khuyenMai = khuyenMai;
		this.ngayNhanPhong = ngayNhanPhong;
		this.ngayTraPhong = ngayTraPhong;
		 if (phong != null) {
		        this.thanhTien = phong.getGiaPhong() * soNgayO;
		        System.out.println("DEBUG - Tính thanhTien: " + phong.getGiaPhong() + " * " + 
		                          soNgayO + " = " + this.thanhTien);
		    } else {
		        this.thanhTien = 0;
		        System.out.println("DEBUG - Phòng null, thanhTien = 0");
		    }
	}
	public ChiTietHoaDon(Phong phong, int soNgayO, double thanhTien, KhuyenMai khuyenMai, 
            LocalDateTime ngayNhanPhong, LocalDateTime ngayTraPhong) {
		this.phong = phong;
		this.soNgayO = soNgayO;
		this.thanhTien = thanhTien; // Lấy từ database
		this.khuyenMai = khuyenMai;
		this.ngayNhanPhong = ngayNhanPhong;
		this.ngayTraPhong = ngayTraPhong;
		
		System.out.println("DEBUG CTDH - Constructor mới - thanhTien: " + this.thanhTien);
	}



	

	
}
