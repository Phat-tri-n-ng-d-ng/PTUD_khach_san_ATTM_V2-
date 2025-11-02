package controllers;
import view.mainframe.*;
import view.panels.KhuyenMaiPanel;
import view.panels.NhanVienPanel;

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
    private JPanel mangHinhChinhPanel;

    public MainController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        khoi_Tao_Cac_Panel();
        //showMangHinhChinh();
    }

    private void khoi_Tao_Cac_Panel() {
        // Khởi tạo các JPanel khác của app
        nhanVienPanel = new NhanVienPanel();
//        khachHangPanel = new KhachHangPanel();
//        hoaDonPanel = new HoaDonPanel();
//        loaiPhongPanel = new LoaiPhongPanel();
//        phongPanel = new PhongPanel();
        khuyenMaiPanel = new KhuyenMaiPanel();
//        thueDatPhongPanel = new ThueDatPhongPanel();
//        mangHinhChinhPanel = new MangHinhChinhPanel();
    }

//    public void showMangHinhChinh() {
//        mangHinhChinhPanel = new MangHinhChinhPanel();
//        doi_Panel(mangHinhChinhPanel);
//    }

//    public void showKhach_Hang_Panel() {
//        khachHangPanel = new KhachHangPanel();
//        doi_Panel(khachHangPanel);
//    }
    public void showNhan_Vien_Panel() {
        nhanVienPanel = new NhanVienPanel();
        doi_Panel(nhanVienPanel);
    }
//    public void showHoa_Don_Panel() {
//        hoaDonPanel = new HoaDonPanel();
//        doi_Panel(hoaDonPanel);
//    }
//    public void showLoai_Phong_Panel() {
//        loaiPhongPanel = new LoaiPhongPanel();
//        doi_Panel(loaiPhongPanel);
//    }
//    public void showPhong_Panel() {
//        phongPanel = new PhongPanel();
//        doi_Panel(phongPanel);
//    }
    public void showKhuyen_Mai_Panel() {
        khuyenMaiPanel = new KhuyenMaiPanel();
        doi_Panel(khuyenMaiPanel);
    }
//    public void showThue_Dat_Phong_Panel() {
//        thueDatPhongPanel = new ThueDatPhongPanel();
//        doi_Panel(thueDatPhongPanel);
//    }

    private void doi_Panel(JPanel panel_Moi) {
        if (pnl_Theo_Doi_Panel_Dang_Hien_Thi != null) {
            mainFrame.getPnlNoiDung().remove(pnl_Theo_Doi_Panel_Dang_Hien_Thi);
        }
        pnl_Theo_Doi_Panel_Dang_Hien_Thi = panel_Moi;

        if(pnl_Theo_Doi_Panel_Dang_Hien_Thi.getLayout() == null){
            pnl_Theo_Doi_Panel_Dang_Hien_Thi.setBounds(0,0,
                    mainFrame.getPnlNoiDung().getWidth(),
                    mainFrame.getPnlNoiDung().getHeight());
            mainFrame.getPnlNoiDung().add(pnl_Theo_Doi_Panel_Dang_Hien_Thi);
        }else{
            mainFrame.getPnlNoiDung().add(pnl_Theo_Doi_Panel_Dang_Hien_Thi, BorderLayout.CENTER);
        }
        mainFrame.getPnlNoiDung().revalidate();
        mainFrame.getPnlNoiDung().repaint();
    }

    /*
        "Khi người dùng kéo to/nhỏ cửa sổ chương trình, phương thức này sẽ tự
        động điều chỉnh kích thước của panel đang hiển thị để LUÔN VỪA KHÍT với cửa sổ."
    */
    public void onWindowResized() {
        if (pnl_Theo_Doi_Panel_Dang_Hien_Thi != null &&
                pnl_Theo_Doi_Panel_Dang_Hien_Thi.getLayout() == null) {
            pnl_Theo_Doi_Panel_Dang_Hien_Thi.setBounds(0, 0,
                    mainFrame.getPnlNoiDung().getWidth(),
                    mainFrame.getPnlNoiDung().getHeight());
        }
    }
}