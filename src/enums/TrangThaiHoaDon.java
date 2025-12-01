package enums;

public enum TrangThaiHoaDon {
    PhieuDatPhong ("Phiếu đặt phòng")  ,
    PhieuThuePhong ("Phiếu thuê phòng") ,
    HoaDonHoanThanh ("Hóa đơn hoàn thành"),
    TatCa ("Tất cả");
    String s;

    private TrangThaiHoaDon(String s) {
        this.s = s;
    }

    public String getTenTrangThai() {
        return s;
    }
}
