package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import database.connectDB.ConnectDB;

public class ThongKeDoanhThuDao {

    /**
     * Lấy doanh thu theo ngày trong tháng/năm (tất cả hóa đơn, bao gồm cả phiếu thuê/đặt)
     */
    public Map<String, Double> getDoanhThuTheoNgay(int thang, int nam) {
        Map<String, Double> data = new LinkedHashMap<>();
        Connection con = ConnectDB.getConnection();

        String sql = "SELECT DAY(hd.ngayLap) as ngay, SUM(hd.tongTienSauGiam) as doanhThu " +
                "FROM HoaDon hd " +
                "WHERE MONTH(hd.ngayLap) = ? AND YEAR(hd.ngayLap) = ? " +
                "GROUP BY DAY(hd.ngayLap) " +
                "ORDER BY ngay";

        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, thang);
            st.setInt(2, nam);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int ngay = rs.getInt("ngay");
                double doanhThu = rs.getDouble("doanhThu");
                data.put("Ngày " + ngay, doanhThu);
            }

            // Nếu không có dữ liệu, tạo dữ liệu mẫu với các ngày trong tháng
            if (data.isEmpty()) {
                LocalDate date = LocalDate.of(nam, thang, 1);
                int daysInMonth = date.lengthOfMonth();
                for (int i = 1; i <= daysInMonth; i++) {
                    data.put("Ngày " + i, 0.0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(con);
        }
        return data;
    }

    /**
     * Lấy tổng doanh thu của tháng/năm (tất cả hóa đơn)
     */
    public double getTongDoanhThuThang(int thang, int nam) {
        double tong = 0;
        Connection con = ConnectDB.getConnection();

        String sql = "SELECT SUM(hd.tongTienSauGiam) as tong " +
                "FROM HoaDon hd " +
                "WHERE MONTH(hd.ngayLap) = ? AND YEAR(hd.ngayLap) = ?";

        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, thang);
            st.setInt(2, nam);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                tong = rs.getDouble("tong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(con);
        }
        return tong;
    }

    /**
     * Lấy doanh thu trung bình 3 tháng gần nhất (không bao gồm tháng hiện tại)
     */
    public double getDoanhThuTrungBinh3ThangGanNhat(int thang, int nam) {
        Connection con = ConnectDB.getConnection();
        double tongDoanhThu3Thang = 0;
        int soThang = 0;

        // Tính toán 3 tháng trước tháng hiện tại
        for (int i = 1; i <= 3; i++) {
            int thangTruoc = thang - i;
            int namTruoc = nam;

            // Xử lý trường hợp sang năm
            if (thangTruoc <= 0) {
                thangTruoc += 12;
                namTruoc--;
            }

            double doanhThuThang = getTongDoanhThuThang(thangTruoc, namTruoc);
            tongDoanhThu3Thang += doanhThuThang;

            // Chỉ đếm tháng có dữ liệu
            if (doanhThuThang > 0) {
                soThang++;
            }
        }

        ConnectDB.closeConnection(con);

        return soThang > 0 ? tongDoanhThu3Thang / soThang : 0;
    }

    /**
     * Lấy doanh thu của từng tháng trong 3 tháng gần nhất
     */
    public Map<String, Double> getDoanhThu3ThangGanNhat(int thang, int nam) {
        Map<String, Double> data = new HashMap<>();
        LocalDate current = LocalDate.of(nam, thang, 1);

        for (int i = 1; i <= 3; i++) {
            LocalDate month = current.minusMonths(i);
            double doanhThu = getTongDoanhThuThang(month.getMonthValue(), month.getYear());
            data.put("Tháng " + month.getMonthValue() + "/" + month.getYear(), doanhThu);
        }

        return data;
    }

    /**
     * Kiểm tra cảnh báo doanh thu tháng hiện tại thấp hơn trung bình 3 tháng
     */
    public boolean kiemTraCanhBaoDoanhThu(int thang, int nam) {
        double doanhThuThangHienTai = getTongDoanhThuThang(thang, nam);
        double doanhThuTB3Thang = getDoanhThuTrungBinh3ThangGanNhat(thang, nam);

        // Nếu doanh thu tháng hiện tại thấp hơn 70% trung bình 3 tháng thì cảnh báo
        if (doanhThuTB3Thang > 0 && doanhThuThangHienTai < (doanhThuTB3Thang * 0.7)) {
            return true;
        }
        return false;
    }

    /**
     * Lấy chi tiết cảnh báo
     */
    public Map<String, Object> getChiTietCanhBao(int thang, int nam) {
        Map<String, Object> canhBao = new HashMap<>();

        double doanhThuThangHienTai = getTongDoanhThuThang(thang, nam);
        double doanhThuTB3Thang = getDoanhThuTrungBinh3ThangGanNhat(thang, nam);

        if (doanhThuTB3Thang > 0) {
            double tyLeGiam = ((doanhThuTB3Thang - doanhThuThangHienTai) / doanhThuTB3Thang) * 100;

            canhBao.put("coCanhBao", doanhThuThangHienTai < (doanhThuTB3Thang * 0.7));
            canhBao.put("tyLeGiam", tyLeGiam);
            canhBao.put("doanhThuThangHienTai", doanhThuThangHienTai);
            canhBao.put("doanhThuTB3Thang", doanhThuTB3Thang);
        } else {
            canhBao.put("coCanhBao", false);
        }

        return canhBao;
    }
}