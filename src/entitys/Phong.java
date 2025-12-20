package entitys;

import enums.TrangThaiPhong;

import java.util.Objects;


public class Phong {
	private String maPhong;
    private LoaiPhong loaiPhong;
    private int tang;
    private int soPhong;
    private int sucChuaToiDa;
	private double giaPhong;
    private double tienCoc;
    private TrangThaiPhong trangThai;

    public Phong(String maPhong, LoaiPhong loaiPhong, int tang, int soPhong, int soLuongToiDa,TrangThaiPhong trangThai) {
        this.maPhong = maPhong;
        this.trangThai = trangThai;
        this.tang = tang;
		this.soPhong = soPhong;
        this.loaiPhong = loaiPhong;
        this.sucChuaToiDa = soLuongToiDa;
        setGiaPhong(loaiPhong.getSucChuaToiThieu(),loaiPhong.getGiaNiemYet());
        setTienCoc();
    }

    

    public Phong(String maPhong) {
		super();
		this.maPhong = maPhong;
	}



	public Phong(String maPhong, LoaiPhong loaiPhong, int tang, int soPhong, int soLuongToiDa, double giaPhong,
			double tienCoc, TrangThaiPhong trangThai) {
		this.maPhong = maPhong;
		this.loaiPhong = loaiPhong;
		this.tang = tang;
		this.soPhong = soPhong;
		this.sucChuaToiDa = soLuongToiDa;
		this.giaPhong = giaPhong;
		this.tienCoc = tienCoc;
		this.trangThai = trangThai;
	}



	public Phong() {

    }

    public Phong(String maPhong, LoaiPhong loaiPhong, int soLuongToiDa, double giaPhong, double tienCoc) {
        this.maPhong = maPhong;
        this.loaiPhong = loaiPhong;
        this.sucChuaToiDa = soLuongToiDa;
        this.giaPhong = giaPhong;
        this.tienCoc = tienCoc;
    }

    public String getMaPhong() {
		return maPhong;
	}
	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}
	public TrangThaiPhong getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(TrangThaiPhong trangThai) {
		this.trangThai = trangThai;
	}
	public LoaiPhong getLoaiPhong() {
		return loaiPhong;
	}
	public void setLoaiPhong(LoaiPhong loaiPhong) {
		this.loaiPhong = loaiPhong;
	}

    public int getSucChuaToiDa() {
        return sucChuaToiDa;
    }

    public void setSucChuaToiDa(int sucChuaToiDa) {
        this.sucChuaToiDa = sucChuaToiDa;
    }

    public void setGiaPhong(double giaPhong) {
        this.giaPhong = giaPhong;
    }

    public void setTienCoc(double tienCoc) {
        this.tienCoc = tienCoc;
    }

    public int getTang() {
		return tang;
	}



	public void setTang(int tang) {
		this.tang = tang;
	}



	public int getSoPhong() {
		return soPhong;
	}



	public void setSoPhong(int soPhong) {
		this.soPhong = soPhong;
	}



	// Dẫn xuất
	public double getGiaPhong() {
		return giaPhong;
	}
	public void setGiaPhong(int sucChuaToiThieu,double giaNiemYet) {
        if (sucChuaToiDa <= sucChuaToiThieu) {
            giaPhong = giaNiemYet;
        } else {
            int soNguoiChenhlech = sucChuaToiDa - sucChuaToiThieu;
            giaPhong = giaNiemYet + (soNguoiChenhlech / 2) * 500000;
        }
	}
	public double getTienCoc() {
		return tienCoc;
	}
	public void setTienCoc() {
        tienCoc=loaiPhong.getTyLeCoc()*giaPhong;
	}
	public Phong(String maPhong, double giaPhong) {
		// TODO Auto-generated constructor stub
		this.maPhong = maPhong;
		this.giaPhong = giaPhong;


	}

	public Phong(String maPhong, LoaiPhong loaiPhong, int tang, int soPhong, double giaPhong) {
		super();
		this.maPhong = maPhong;
		this.loaiPhong = loaiPhong;
		this.tang = tang;
		this.soPhong = soPhong;
		this.giaPhong = giaPhong;
	}



	@Override
	public int hashCode() {
		return Objects.hash(maPhong);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phong other = (Phong) obj;
		return Objects.equals(maPhong, other.maPhong);
	}
}
