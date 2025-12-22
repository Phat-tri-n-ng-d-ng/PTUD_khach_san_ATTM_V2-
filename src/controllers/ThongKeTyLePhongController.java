
package controllers;

import database.dao.ThongKeTyLePhongDao;
import java.util.Map;

public class ThongKeTyLePhongController {
    private ThongKeTyLePhongDao thongKeTyLePhongDao;

    public ThongKeTyLePhongController() {
        thongKeTyLePhongDao = new ThongKeTyLePhongDao();
    }

    public Map<String, Integer> layThongKePhongTheoTrangThai() {
        return thongKeTyLePhongDao.getThongKePhongTheoTrangThai();
    }

    public int layTongSoPhong() {
        return thongKeTyLePhongDao.getTongSoPhong();
    }

    public double layTyLeLapDay() {
        return thongKeTyLePhongDao.getTyLeLapDay();
    }

    public double layTyLeLapDayTrungBinh3Thang() {
        return thongKeTyLePhongDao.getTyLeLapDayTrungBinh3Thang();
    }

    public String layDanhSachPhongTrong() {
        return thongKeTyLePhongDao.getDanhSachPhongTrong();
    }

    public boolean kiemTraCanhBao() {
        return thongKeTyLePhongDao.kiemTraCanhBaoTyLeLapDay();
    }

    /**
     * Lấy màu sắc tương ứng cho từng loại phòng
     */
    public java.awt.Color layMauChoPhong(String loaiPhong) {
        switch (loaiPhong) {
            case "Đang thuê":
                return new java.awt.Color(144, 238, 144); // Màu xanh lá nhạt
            case "Trống":
                return new java.awt.Color(173, 216, 230); // LightBlue - xanh da trời
            case "Đã đặt":
                return new java.awt.Color(255, 182, 193); // Màu hồng nhạt
            default:
                return java.awt.Color.GRAY;
        }
    }

    /**
     * Format tỷ lệ phần trăm
     */
    public String formatTyLe(double tyLe) {
        return String.format("%.1f%%", tyLe);
    }

    /**
     * Lấy số lượng phòng theo từng trạng thái
     */
    public int laySoPhongTheoTrangThai(String trangThai) {
        Map<String, Integer> thongKe = layThongKePhongTheoTrangThai();
        return thongKe.getOrDefault(trangThai, 0);
    }
}