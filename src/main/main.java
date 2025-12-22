package main;

import view.mainframe.DangNhapFrame;
import view.mainframe.MainFrame;

public class main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
//        NhanVienPanel nhanVienPanel = new NhanVienPanel();
//        mainFrame.setVisible(true);
        DangNhapFrame dangNhapFrame = new DangNhapFrame();
        dangNhapFrame.setVisible(true);
    }
}
