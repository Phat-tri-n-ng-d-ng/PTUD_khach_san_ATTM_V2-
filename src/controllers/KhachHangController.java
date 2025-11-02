package controllers;

import entitys.KhachHang;
import entitys.NhanVien;
import enums.ChucVuNhanVien;
import enums.HangKhachHang;
import view.dialogs.KhachHangDialog;
import view.dialogs.NhanVienDialog;
import view.panels.KhachHangPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class KhachHangController implements MouseListener {
    private KhachHangPanel khachHangPanel;

    public KhachHangController(KhachHangPanel khachHangPanel){
        this.khachHangPanel = khachHangPanel;

        khachHangPanel.btn_ThemKhachHang.addActionListener(e -> ThemKhachHang());
        khachHangPanel.btn_LamMoi.addActionListener(e -> LamMoi());

        khachHangPanel.table.addMouseListener(this);

        suKienTextField();
    }

    public void getTatCaKhachHang(){
        ArrayList<KhachHang> dsKhachHang = new ArrayList<>();
        KhachHang kh1 = new KhachHang("KH001","KH1",false,LocalDate.parse("2005-01-01"),"123@gmail.com","0123456789", "123456789012",0 );
        KhachHang kh2 = new KhachHang("KH002","KH2",false,LocalDate.parse("2005-01-01"),"123@gmail.com","0123456789", "123456789012",0 );
        KhachHang kh3 = new KhachHang("KH003","KH3",false,LocalDate.parse("2005-01-01"),"123@gmail.com","0123456789", "123456789012",0 );
        dsKhachHang.add(kh1);
        dsKhachHang.add(kh2);
        dsKhachHang.add(kh3);
        DefaultTableModel model = khachHangPanel.model;
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng trước khi load mới
        for (KhachHang kh : dsKhachHang) {
            String gioiTinh = kh.isGioiTinh() ? "Nam" : "Nữ"; // Nếu có kiểu boolean
            model.addRow(new Object[]{
                    kh.getMaKH(),
                    kh.getTenKH(),
                    gioiTinh,
                    kh.getNgaySinh(),
                    kh.getSdt(),
                    kh.getEmail(),
                    kh.getSoCCCD(),
                    getHangKhachHienThi(kh.getHangKH()),
                    kh.getDiemTichLuy()
            });
        }
    }

    private void ThemKhachHang() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = khachHangPanel.table.getSelectedRow();
        if(row != -1){
            String maKH = khachHangPanel.table.getValueAt(row, 0).toString();
            String tenKH = khachHangPanel.table.getValueAt(row, 1).toString();
            boolean gioiTinh = khachHangPanel.table.getValueAt(row, 2).equals("Nam") ? true : false;
            String ngaySinhStr = khachHangPanel.table.getValueAt(row, 3).toString();
            LocalDate ngaySinh = LocalDate.parse(ngaySinhStr);
            String sdt = khachHangPanel.table.getValueAt(row, 4).toString();
            String email = khachHangPanel.table.getValueAt(row, 5).toString();
            String soCCCD = khachHangPanel.table.getValueAt(row, 6).toString();
            HangKhachHang hangKhachHang = getHangKhachHang(khachHangPanel.table.getValueAt(row, 5).toString());
            int diemTichLuy = Integer.parseInt(khachHangPanel.table.getValueAt(row, 8).toString());

            KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinh, ngaySinh, email, sdt,
                    soCCCD,0);
            KhachHangDialog dialog = new KhachHangDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(khachHangPanel),
                    khachHang
            );
            dialog.setVisible(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void LamMoi() {
        khachHangPanel.txt_TenKhachHang.setText("");
        khachHangPanel.rdbtn_Nam.setSelected(true);
        khachHangPanel.rdbtn_Nu.setSelected(false);
        khachHangPanel.txt_SoDienThoai.setText("");
        khachHangPanel.txt_Email.setText("");
        khachHangPanel.txt_soCCCD.setText("");
        khachHangPanel.txt_TenKhachHang.setText("");
        khachHangPanel.txt_TimSoDienThoai.setText("");
        khachHangPanel.txt_TimSoCanCuocCongDan.setText("");
        khachHangPanel.cbb_LocHangKhachHang.setSelectedIndex(0);
        khachHangPanel.ngaySinh.setDate(null);
    }

    private void suKienTextField() {
    }

    private HangKhachHang getHangKhachHang(String tenHangKhachHang) {
        switch (tenHangKhachHang) {
            case "Đồng" -> { return HangKhachHang.Dong ; }
            case "Bạc" -> { return HangKhachHang.Bac; }
            case "Vàng" -> { return HangKhachHang.Vang; }
            case "Kim cương" -> { return HangKhachHang.KimCuong; }
            default -> { return HangKhachHang.Dong; }
        }
    }

    private String getHangKhachHienThi(HangKhachHang hangKhachHang) {
        switch (hangKhachHang) {
            case Dong -> { return "Đồng"; }
            case Bac -> { return "Bạc"; }
            case Vang -> { return "Vàng"; }
            case KimCuong -> { return "Kim cương"; }
            default -> { return ""; }
        }
    }
}
