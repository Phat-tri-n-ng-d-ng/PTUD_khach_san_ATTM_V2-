package controllers;

import database.dao.ThongKeDoanhThuDao;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

public class ThongKeDoanhThuController {
    private ThongKeDoanhThuDao thongKeDoanhThuDao;

    public ThongKeDoanhThuController() {
        thongKeDoanhThuDao = new ThongKeDoanhThuDao();
    }

    public Map<String, Double> layDoanhThuTheoNgay(int thang, int nam) {
        return thongKeDoanhThuDao.getDoanhThuTheoNgay(thang, nam);
    }

    public double layTongDoanhThuThang(int thang, int nam) {
        return thongKeDoanhThuDao.getTongDoanhThuThang(thang, nam);
    }

    public double layDoanhThuTrungBinh3ThangGanNhat(int thang, int nam) {
        return thongKeDoanhThuDao.getDoanhThuTrungBinh3ThangGanNhat(thang, nam);
    }

    public boolean kiemTraCanhBao(int thang, int nam) {
        return thongKeDoanhThuDao.kiemTraCanhBaoDoanhThu(thang, nam);
    }

    public Map<String, Object> layChiTietCanhBao(int thang, int nam) {
        return thongKeDoanhThuDao.getChiTietCanhBao(thang, nam);
    }

    /**
     * Format số tiền thành chuỗi có dấu phân cách
     */
    public String formatTien(double tien) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        if (tien >= 1000000000) {
            return String.format("%.2f tỷ VNĐ", tien / 1000000000);
        } else if (tien >= 1000000) {
            return formatter.format(tien / 1000000) + " triệu VNĐ";
        } else if (tien >= 1000) {
            return formatter.format(tien / 1000) + " nghìn VNĐ";
        } else {
            return formatter.format(tien) + " VNĐ";
        }
    }

    /**
     * Tính phần trăm tăng trưởng
     */
    public double tinhTangTruong(double hienTai, double truocDo) {
        if (truocDo == 0) return 0;
        return ((hienTai - truocDo) / truocDo) * 100;
    }

    /**
     * Lấy doanh thu của 3 tháng gần nhất riêng biệt
     */
    public Map<String, Double> layDoanhThu3ThangGanNhat(int thang, int nam) {
        return thongKeDoanhThuDao.getDoanhThu3ThangGanNhat(thang, nam);
    }
}