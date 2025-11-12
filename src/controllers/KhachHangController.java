package controllers;

import entitys.HangKhachHang;
import entitys.KhachHang;
import entitys.NhanVien;
import enums.ChucVuNhanVien;
import services.KhachHangService;
import view.dialogs.KhachHangDialog;
import view.dialogs.NhanVienDialog;
import view.panels.KhachHangPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class KhachHangController implements MouseListener {
    private KhachHangService khachHangService;
    private KhachHangPanel khachHangPanel;


    public KhachHangController(KhachHangPanel khachHangPanel){
        khachHangService = new KhachHangService();
        this.khachHangPanel = khachHangPanel;

        khachHangPanel.btn_ThemKhachHang.addActionListener(e -> ThemKhachHang());
        khachHangPanel.btn_LamMoi.addActionListener(e -> LamMoi());
        khachHangPanel.cbb_LocHangKhachHang.addActionListener(e -> getKhachHangTheoHang());
        khachHangPanel.btn_Tim.addActionListener(e -> TimKhachHang());

        khachHangPanel.table.addMouseListener(this);

        suKienTextField();
    }

    public void getTatCaKhachHang(){
        try {
            ArrayList<KhachHang> dsKhachHang = khachHangService.getTatCaKhachHang();
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
                        kh.getHangKH().getTenHang(),
                        kh.getDiemTichLuy()
                });
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ThemKhachHang() {
        int row = khachHangPanel.table.getSelectedRow();
        if(row == -1){
            int namHienTai = LocalDate.now().getYear();
            String maKH = "KH" + (namHienTai % 100) + String.format("%03d", khachHangService.getSoLuongKhachHang() + 1);
            String tenKH = khachHangPanel.txt_TenKhachHang.getText().strip();
            String sdt = khachHangPanel.txt_SoDienThoai.getText().strip();
            boolean gioiTinh = khachHangPanel.rdbtn_Nam.isSelected() ? true : false;
            Date date = khachHangPanel.ngaySinh.getDate();
            LocalDate ngaySinh = null;
            if (date != null) {
                ngaySinh = date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            }
            String soCCCD = khachHangPanel.txt_soCCCD.getText().strip();
            String email = khachHangPanel.txt_Email.getText().strip();

            KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinh, ngaySinh, email, sdt,
                    soCCCD);
            if(khachHangService.themKhachHang(khachHang)){
                JOptionPane.showMessageDialog(khachHangPanel, "Thêm thành công");
                LamMoi();
                getTatCaKhachHang();
            }else{
                JOptionPane.showMessageDialog(khachHangPanel, "Thêm thất bại");
            }
        }else JOptionPane.showMessageDialog(khachHangPanel, "Nhân viên đã tồn tại");
    }

    private void getKhachHangTheoHang() {
        String hangKhachHangChon = khachHangPanel.cbb_LocHangKhachHang.getSelectedItem().toString();
        ArrayList<KhachHang> dsKhachHang;
        if (hangKhachHangChon.equals("Tất cả")) {
            dsKhachHang = khachHangService.getTatCaKhachHang(); // gọi lấy toàn bộ
        } else {
            dsKhachHang = khachHangService.getKhachHangTheoHang(hangKhachHangChon);
        }
        DefaultTableModel model = khachHangPanel.model;
        model.setRowCount(0);
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
                    kh.getHangKH().getTenHang(),
                    kh.getDiemTichLuy()
            });
        }
    }

    private void TimKhachHang() {
        try {
            KhachHang kh;
            if(khachHangPanel.rdbtn_TimSoCanCuocCongDan.isSelected()){
                String soCCCD = khachHangPanel.txt_TimSoCanCuocCongDan.getText().strip();
                kh = khachHangService.TimKhachHang(soCCCD,"CCCD");
            }else{
                String soDT = khachHangPanel.txt_TimSoDienThoai.getText().strip();
                kh = khachHangService.TimKhachHang(soDT,"SDT");
            }
            if(kh != null){
                DefaultTableModel model = khachHangPanel.model;
                model.setRowCount(0);
                String gioiTinh = kh.isGioiTinh() ? "Nam" : "Nữ"; // Nếu có kiểu boolean
                model.addRow(new Object[]{
                        kh.getMaKH(),
                        kh.getTenKH(),
                        gioiTinh,
                        kh.getNgaySinh(),
                        kh.getSdt(),
                        kh.getEmail(),
                        kh.getSoCCCD(),
                        kh.getHangKH().getTenHang(),
                        kh.getDiemTichLuy()
                });
            }else JOptionPane.showMessageDialog(khachHangPanel,"Không tìm thấy khách hàng");
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(khachHangPanel, "Lỗi khi tìm khách hàng: " + e.getMessage());
        }
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
            String tenHang = khachHangPanel.table.getValueAt(row, 7).toString();
            String maHang = khachHangService.getMaHang(tenHang);
            double diemTichLuy = Double.parseDouble(khachHangPanel.table.getValueAt(row, 8).toString());

            HangKhachHang hangKhachHang = new entitys.HangKhachHang(maHang,tenHang);
            KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinh, ngaySinh, email, sdt,
                    soCCCD,diemTichLuy, hangKhachHang);
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
        khachHangPanel.ngaySinh.setDate(java.sql.Date.valueOf(khachHangPanel.maxDate));
        getTatCaKhachHang();
    }

    private void suKienTextField() {
        khachHangPanel.rdbtn_TimSoCanCuocCongDan.addActionListener(e ->{
            khachHangPanel.txt_TimSoCanCuocCongDan.setEditable(true);
            khachHangPanel.rdbtn_TimSoCanCuocCongDan.requestFocus();
            khachHangPanel.txt_TimSoDienThoai.setEditable(false);
        });
        khachHangPanel.rdbtn_TimSoDienThoai.addActionListener(e -> {
            khachHangPanel.txt_TimSoCanCuocCongDan.setEditable(false);
            khachHangPanel.txt_TimSoDienThoai.requestFocus();
            khachHangPanel.txt_TimSoDienThoai.setEditable(true);
        });
    }
}
