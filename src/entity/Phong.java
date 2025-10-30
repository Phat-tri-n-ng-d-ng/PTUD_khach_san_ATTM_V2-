package entity;

import enums.TrangThaiPhong;

import java.util.Objects;


public class Phong {
	private String maPhong;
    private LoaiPhong loaiPhong;
    private int soLuongToiDa;
	private double giaPhong;
    private double tienCoc;
    private TrangThaiPhong trangThai;

    public Phong(String maPhong, TrangThaiPhong trangThai, LoaiPhong loaiPhong, int soLuongToiDa) {
        this.maPhong = maPhong;
        this.trangThai = trangThai;

        this.loaiPhong = loaiPhong;
        this.soLuongToiDa = soLuongToiDa;
        setGiaPhong();
        setTienCoc();
    }

    public Phong(String maPhong, TrangThaiPhong trangThai, double giaPhong, double tienCoc, LoaiPhong loaiPhong, int soLuongToiDa) {
        this.maPhong = maPhong;
        this.trangThai = trangThai;
        this.giaPhong = giaPhong;
        this.tienCoc = tienCoc;
        this.loaiPhong = loaiPhong;
        this.soLuongToiDa = soLuongToiDa;
    }

    public Phong() {

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
	public int getSoLuongToiDa() {
		return soLuongToiDa;
	}
	public void setSoLuongToiDa(int soLuongToiDa) {
		this.soLuongToiDa = soLuongToiDa;
	}
	// Dẫn xuất
	public double getGiaPhong() {
		return giaPhong;
	}
	public void setGiaPhong() {
        int soNguoiMacDinh = loaiPhong.getSoNguoiMacDinh();
        double giaNiemYet = loaiPhong.getGiaNiemYet();

        if (soLuongToiDa <= soNguoiMacDinh) {
            giaPhong = giaNiemYet;
        } else {
            int soNguoiChenhlech = soLuongToiDa - soNguoiMacDinh;
            giaPhong = giaNiemYet + (soNguoiChenhlech / 2) * 500000;
        }
	}
	public double getTienCoc() {
		return tienCoc;
	}
	public void setTienCoc() {
        tienCoc=loaiPhong.getTyLeCoc()*giaPhong/100;
	}
	public Phong(String maPhong, double giaPhong) {
		// TODO Auto-generated constructor stub
		this.maPhong = maPhong;
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
