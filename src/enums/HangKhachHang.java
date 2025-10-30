package enums;

public enum HangKhachHang {
	Dong ("Đồng"),
    Bac ("Bạc"),
    Vang ("Vàng"),
    KimCuong ("Kim cương");
    String s;

    HangKhachHang(String s) {
        this.s = s;
    }

    public String getTenHang() {
        return s;
    }
}
