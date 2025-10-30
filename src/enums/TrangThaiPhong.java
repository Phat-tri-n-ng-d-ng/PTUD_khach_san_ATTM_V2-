package enums;

public enum TrangThaiPhong {
	DangSuDung ("Đang sử dụng"),
    Trong ("Trống"),
    DaDat ("Đã đặt"),
    NgungHoatDong("Ngưng hoạt động");

    private String moTa;

    private TrangThaiPhong(String moTa) {
        this.moTa = moTa;
    }

    public String getMoTa() {
        return moTa;
    }


}
