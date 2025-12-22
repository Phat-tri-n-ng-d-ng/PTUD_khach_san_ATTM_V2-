package entitys;

public class HangKhachHang {
    private String maHang;
    private String tenHang;
    private double diemToiThieu;
    private double tyLeGiam;

    public HangKhachHang(String maHang, String tenHang, double diemToiThieu, double tyLeGiam) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.diemToiThieu = diemToiThieu;
        this.tyLeGiam = tyLeGiam;
    }

    public HangKhachHang(String maHang, String tenHang) {
        this.maHang = maHang;
        this.tenHang = tenHang;
    }

    public HangKhachHang(String maHang, String tenHang, int diemToiThieu) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.diemToiThieu = diemToiThieu;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public void setDiemToiThieu(double diemToiThieu) {
        this.diemToiThieu = diemToiThieu;
    }

    public void setTyLeGiam(double tyLeGiam) {
        this.tyLeGiam = tyLeGiam;
    }

    public double getTyLeGiam() {
        return tyLeGiam;
    }

    public double getDiemToiThieu() {
        return diemToiThieu;
    }

    public String getTenHang() {
        return tenHang;
    }

    public String getMaHang() {
        return maHang;
    }
}
