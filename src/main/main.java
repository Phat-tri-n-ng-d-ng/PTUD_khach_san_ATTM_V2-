package main;

import view.mainframe.MainFrame;
import view.panels.NhanVienPanel;

public class main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        NhanVienPanel nhanVienPanel = new NhanVienPanel();
        mainFrame.addPanel(nhanVienPanel,"nhanVien");
        mainFrame.showPanel("nhanVien");
    }
}
