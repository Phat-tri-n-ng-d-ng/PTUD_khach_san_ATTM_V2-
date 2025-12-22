package controllers;

import view.mainframe.*;
import view.panels.*;

import javax.swing.*;
import java.awt.*;

public class MainController {
    private MainFrame mainFrame;
    private JPanel pnl_Theo_Doi_Panel_Dang_Hien_Thi;

    // Chổ để khai báo các JPanel khác của app
    private JPanel nhanVienPanel;
    private JPanel khachHangPanel;
    private JPanel hoaDonPanel;
    private JPanel loaiPhongPanel;
    private JPanel phongPanel;
    private JPanel khuyenMaiPanel;
    private JPanel thueDatPhongPanel;
    private ManHinhTrangChuPanel manHinhTrangChuPanel;

    // THÊM: Panel cho thống kê
    private JPanel thongKeDoanhThuPanel;
    private JPanel thongKeTyLePhongPanel;

    public MainController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        khoi_Tao_Cac_Panel();
        showMangHinhChinh();
        mainFrame.chonNutMacDinh();
    }

    private void khoi_Tao_Cac_Panel() {
        // Khởi tạo các JPanel khác của app
        // nhanVienPanel = new NhanVienPanel();
        // khachHangPanel = new KhachHangPanel();
        // hoaDonPanel = new HoaDonPanel();
        // loaiPhongPanel = new LoaiPhongPanel();
        // phongPanel = new PhongPanel();
        // khuyenMaiPanel = new KhuyenMaiPanel();
        // thueDatPhongPanel = new ThueDatPhongPanel();
        // manHinhTrangChuPanel = new ManHinhTrangChuPanel();
    }

    public void showMangHinhChinh() {
        manHinhTrangChuPanel = new ManHinhTrangChuPanel();
        doi_Panel(manHinhTrangChuPanel);
    }

    public void showKhach_Hang_Panel() {
        if (khachHangPanel == null) {
            khachHangPanel = new KhachHangPanel();
        }
        doi_Panel(khachHangPanel);
    }

    public void showNhan_Vien_Panel() {
        if (nhanVienPanel == null) {
            nhanVienPanel = new NhanVienPanel();
        }
        doi_Panel(nhanVienPanel);
    }

    public void showHoa_Don_Panel() {
        if (hoaDonPanel == null) {
            hoaDonPanel = new HoaDonPanel();
        }
        doi_Panel(hoaDonPanel);
    }

    public void showLoai_Phong_Panel() {
        if (loaiPhongPanel == null) {
            loaiPhongPanel = new LoaiPhongPanel();
        }
        doi_Panel(loaiPhongPanel);
    }

    public void showPhong_Panel() {
        if (phongPanel == null) {
            phongPanel = new PhongPanel();
        }
        doi_Panel(phongPanel);
    }

    public void showKhuyen_Mai_Panel() {
        if (khuyenMaiPanel == null) {
            khuyenMaiPanel = new KhuyenMaiPanel();
        }
        doi_Panel(khuyenMaiPanel);
    }

    public void showThue_Dat_Phong_Panel() {
        if (thueDatPhongPanel == null) {
            thueDatPhongPanel = new ThueDatPhongPanel();
        }
        doi_Panel(thueDatPhongPanel);
    }


    public void showThongKeDoanhThu() {
        if (thongKeDoanhThuPanel == null) {
            thongKeDoanhThuPanel = new ThongKeDoanhThuPanel();
        }
        doi_Panel(thongKeDoanhThuPanel);
    }

    public void showThongKeTyLePhong() {
        if (thongKeTyLePhongPanel == null) {
            thongKeTyLePhongPanel = new ThongKeTyLePhongPanel();
        }
        doi_Panel(thongKeTyLePhongPanel);
    }

    // THÊM: Phương thức tạo panel thống kê chung
    private JPanel taoPanelThongKe(String title, String message) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(248, 250, 252));
        panel.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(67, 97, 238));
        headerPanel.setPreferredSize(new Dimension(0, 80));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Main content
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(248, 250, 252));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel comingSoonLabel = new JLabel(message);
        comingSoonLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        comingSoonLabel.setForeground(new Color(100, 116, 139));

        contentPanel.add(comingSoonLabel);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    // THÊM: Phương thức hiển thị trợ giúp
    public void showTroGiup() {
        JOptionPane.showMessageDialog(mainFrame,
                "Hệ thống quản lý khách sạn ATTM\n\n" +
                        "Phiên bản: 1.0\n" +
                        "Hỗ trợ kỹ thuật: 0966-086-570\n" +
                        "Email: support@attm.com\n\n" +
                        "© 2024 ATTM Hotel Management System",
                "Trợ giúp",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void doi_Panel(JPanel panel_Moi) {
        if (pnl_Theo_Doi_Panel_Dang_Hien_Thi != null) {
            mainFrame.getPnlNoiDung().remove(pnl_Theo_Doi_Panel_Dang_Hien_Thi);
        }
        pnl_Theo_Doi_Panel_Dang_Hien_Thi = panel_Moi;

        mainFrame.getPnlNoiDung().removeAll();
        mainFrame.getPnlNoiDung().setLayout(new BorderLayout());
        mainFrame.getPnlNoiDung().add(pnl_Theo_Doi_Panel_Dang_Hien_Thi, BorderLayout.CENTER);

        mainFrame.getPnlNoiDung().revalidate();
        mainFrame.getPnlNoiDung().repaint();
    }

    public void onWindowResized() {
        if (pnl_Theo_Doi_Panel_Dang_Hien_Thi != null) {
            pnl_Theo_Doi_Panel_Dang_Hien_Thi.setPreferredSize(
                    new Dimension(mainFrame.getPnlNoiDung().getWidth(),
                            mainFrame.getPnlNoiDung().getHeight())
            );
            pnl_Theo_Doi_Panel_Dang_Hien_Thi.revalidate();
        }
    }
}