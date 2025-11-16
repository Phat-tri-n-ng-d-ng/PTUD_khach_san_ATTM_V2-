package entitys;

import java.util.Objects;

public class LoaiPhong {
    private String maLoaiPhong;
    private String tenLoaiPhong;
    private double giaNiemYet;
    private double tyLeCoc;
    private int sucChuaToiThieu;


    public String getMaLoaiPhong() {
		return maLoaiPhong;
	}
	public String getTenLoaiPhong() {
		return tenLoaiPhong;
	}
	public void setTenLoaiPhong(String tenLoaiPhong) {
		this.tenLoaiPhong = tenLoaiPhong;
	}
	public double getGiaNiemYet() {
		return giaNiemYet;
	}
	public void setGiaNiemYet(double giaNiemYet) {
		this.giaNiemYet = giaNiemYet;
	}
	public double getTyLeCoc() {
		return tyLeCoc;
	}

    public void setMaLoaiPhong(String maLoaiPhong) {
        this.maLoaiPhong = maLoaiPhong;
    }

    public int getSucChuaToiThieu() {
		return sucChuaToiThieu;
	}
	public void setSucChuaToiThieu(int sucChuaToiThieu) {
		this.sucChuaToiThieu = sucChuaToiThieu;
	}
	public void setTyLeCoc(double tyLeCoc) {
        this.tyLeCoc = tyLeCoc;
    }  
    public LoaiPhong(String maLoaiPhong, String tenLoaiPhong, double giaNiemYet, double tyLeCoc, int sucChuaToiThieu) {
		this.maLoaiPhong = maLoaiPhong;
		this.tenLoaiPhong = tenLoaiPhong;
		this.giaNiemYet = giaNiemYet;
		this.tyLeCoc = tyLeCoc;
		this.sucChuaToiThieu = sucChuaToiThieu;
	}
    public LoaiPhong(String maLoaiPhong, String tenLoaiPhong) {
        this.maLoaiPhong = maLoaiPhong;
        this.tenLoaiPhong = tenLoaiPhong;
    }


    public LoaiPhong() {
    }

    public LoaiPhong(String tenlp) {
		this.tenLoaiPhong=tenlp;
	}



	@Override
	public int hashCode() {
		return Objects.hash(maLoaiPhong);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoaiPhong other = (LoaiPhong) obj;
		return Objects.equals(maLoaiPhong, other.maLoaiPhong);
	}
}
