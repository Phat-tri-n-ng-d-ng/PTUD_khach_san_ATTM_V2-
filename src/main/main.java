package main;

import view.mainframe.MainFrame;
import view.panels.LoaiPhongPanel;
import view.panels.NhanVienPanel;
import view.panels.PhongPanel;


public class main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
//        NhanVienPanel nhanVienPanel = new NhanVienPanel();
//        mainFrame.addPanel(nhanVienPanel,"nhanVien");
//        mainFrame.showPanel("nhanVien");
//        PhongPanel phongPanel= new PhongPanel();
//        mainFrame.addPanel(phongPanel, "phong");
//        mainFrame.showPanel("phong");
        LoaiPhongPanel loaiPhong = new LoaiPhongPanel();
        mainFrame.addPanel(loaiPhong, "loaiPhong");
        mainFrame.showPanel("loaiPhong");
    }
}
