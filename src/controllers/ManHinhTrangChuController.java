package controllers;

import database.dao.ManHinhTrangChuDao;
import view.panels.ManHinhTrangChuPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ManHinhTrangChuController {
    private ManHinhTrangChuPanel view;
    private ManHinhTrangChuDao dao;
    private DecimalFormat currencyFormat;
    private DecimalFormat percentFormat;

    public ManHinhTrangChuController(ManHinhTrangChuPanel view) {
        this.view = view;
        this.dao = new ManHinhTrangChuDao();
        this.currencyFormat = new DecimalFormat("#,###");
        this.percentFormat = new DecimalFormat("#.##");
        initController();
        loadData();
    }

    private void initController() {
        // Xử lý sự kiện tìm kiếm khi nhấn Enter
        view.getTxtTimKiem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = view.getTxtTimKiem().getText().trim();
                if (!keyword.isEmpty() && !keyword.equals("Tìm kiếm phòng, khách hàng...")) {
                    timKiemPhong(keyword);
                } else {
                    loadData(); // Load lại toàn bộ nếu không có keyword
                }
            }
        });
    }

    public void loadData() {
        loadTableData();
        loadCardData();
    }

    private void loadTableData() {
        // Xóa dữ liệu cũ
        view.getTableModel().setRowCount(0);

        try {
            // Lấy phòng đang thuê hôm nay
            ArrayList<Object[]> phongDangThue = dao.getPhongDangThueHomNay();
            for (Object[] row : phongDangThue) {
                view.getTableModel().addRow(row);
            }

            // Lấy phòng đã đặt hôm nay
            ArrayList<Object[]> phongDaDat = dao.getPhongDaDatHomNay();
            for (Object[] row : phongDaDat) {
                view.getTableModel().addRow(row);
            }

            // Hiển thị thông báo nếu không có dữ liệu
            if (view.getTableModel().getRowCount() == 0) {
                Object[] row = {"", "Không có phòng nào hôm nay", "", "", "", "", ""};
                view.getTableModel().addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi khi tải dữ liệu: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCardData() {
        try {
            // Card 1: Tổng doanh thu tháng
            double doanhThuThang = dao.getTongDoanhThuThangHienTai();
            String doanhThuFormatted = currencyFormat.format(doanhThuThang) + " VND";
            List<Double> doanhThu5Thang = dao.getDoanhThu5ThangGanNhat();

            // Tính phần trăm tăng/giảm doanh thu
            double doanhThuTangTruong = dao.getPhanTramTangTruongDoanhThu();
            String doanhThuPercentText;

            if (doanhThuTangTruong >= 0) {
                // Tăng
                doanhThuPercentText = "Tăng " + percentFormat.format(doanhThuTangTruong) + "%";
            } else {
                // Giảm (giá trị âm)
                doanhThuPercentText = "Giảm " + percentFormat.format(Math.abs(doanhThuTangTruong)) + "%";
            }

            // Card 2: Phòng đang thuê
            int soPhongDangThue = dao.getSoLuongPhongDangThue();
            int tongSoPhong = dao.getTongSoPhong();
            int phongThuePercent = (tongSoPhong == 0) ? 0 : (soPhongDangThue * 100) / tongSoPhong;
            String phongThueText = soPhongDangThue + "/" + tongSoPhong;
            String phongThuePercentText = "Chiếm " + phongThuePercent + "%";

            // Card 3: Phòng đã đặt
            int soPhongDaDat = dao.getSoLuongPhongDaDat();
            int phongDatPercent = (tongSoPhong == 0) ? 0 : (soPhongDaDat * 100) / tongSoPhong;
            String phongDatText = soPhongDaDat + "/" + tongSoPhong;
            String phongDatPercentText = "Chiếm " + phongDatPercent + "%";

            // Cập nhật view - sử dụng phần trăm tăng/giảm thực tế
            view.updateCardData(doanhThuFormatted, doanhThuPercentText,
                    phongThueText, phongThuePercentText,
                    phongDatText, phongDatPercentText,
                    doanhThu5Thang);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi khi tải thống kê: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void timKiemPhong(String keyword) {
        try {
            ArrayList<Object[]> ketQua = dao.timKiemPhongTheoMaHoacSDT(keyword);
            view.getTableModel().setRowCount(0);

            if (ketQua.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Không tìm thấy kết quả nào cho: " + keyword,
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadTableData(); // Load lại dữ liệu gốc
            } else {
                for (Object[] row : ketQua) {
                    view.getTableModel().addRow(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi khi tìm kiếm: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Phương thức để xử lý thao tác nhận/trả phòng từ bảng
    public void xuLyThaoTacPhong(int row, String thaoTac, String maPhong) {
        try {
            switch (thaoTac) {
                case "Nhận phòng":
                    xuLyNhanPhong(maPhong, row);
                    break;
                case "Trả phòng":
                    xuLyTraPhong(maPhong, row);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi khi xử lý: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyNhanPhong(String maPhong, int row) {
        int confirm = JOptionPane.showConfirmDialog(view,
                "Xác nhận nhận phòng " + maPhong + "?\nKhách sẽ được check-in ngay lập tức.",
                "Nhận phòng", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            // TODO: Thêm logic cập nhật trạng thái hóa đơn từ "PhieuDatPhong" sang "PhieuThuePhong"
            // TODO: Cập nhật trạng thái phòng

            // Cập nhật giao diện tạm thời
            view.getTableModel().setValueAt("Đang thuê", row, 5);
            view.getTableModel().setValueAt("Trả phòng", row, 6);

            JOptionPane.showMessageDialog(view, "Đã nhận phòng " + maPhong,
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);

            // Reload card data
            loadCardData();
        }
    }

    private void xuLyTraPhong(String maPhong, int row) {
        int confirm = JOptionPane.showConfirmDialog(view,
                "Xác nhận trả phòng " + maPhong + "?\nHệ thống sẽ tạo hóa đơn thanh toán.",
                "Trả phòng", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            // TODO: Thêm logic tạo hóa đơn thanh toán
            // TODO: Cập nhật trạng thái phòng thành "Trống"
            // TODO: Cập nhật trạng thái hóa đôn

            // Xóa dòng khỏi bảng
            view.getTableModel().removeRow(row);

            JOptionPane.showMessageDialog(view, "Đã trả phòng " + maPhong,
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);

            // Reload card data
            loadCardData();
        }
    }
}