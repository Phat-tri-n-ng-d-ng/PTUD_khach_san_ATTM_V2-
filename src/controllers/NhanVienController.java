package controllers;

import database.dao.NhanVienDao;
import entitys.NhanVien;
import entitys.TaiKhoan;
import enums.ChucVuNhanVien;
import enums.TrangThaiTaiKhoan;
import enums.VaiTro;
import view.dialogs.NhanVienDialog;
import view.panels.NhanVienPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class NhanVienController implements MouseListener {
    private NhanVienDao nhanVienDao;
    private NhanVienPanel nhanVienPanel;

    public NhanVienController(NhanVienPanel nhanVienPanel){
        nhanVienDao = new NhanVienDao();
        this.nhanVienPanel = nhanVienPanel;

        nhanVienPanel.btn_ThemNhanVien.addActionListener(e -> ThemNhanVien());
        nhanVienPanel.btn_LamMoi.addActionListener(e -> LamMoi());
        nhanVienPanel.btn_Tim.addActionListener(e -> TimNhanVien());
        nhanVienPanel.cbb_LocChucVu.addActionListener(e -> LocNhanVienTheoChucVu());

        nhanVienPanel.table.addMouseListener(this);

        suKienTextField();
    }

    public void getTatCaNhanVien(){
        try {
            ArrayList<NhanVien> dsNhanVien = nhanVienDao.getTatCaNhanVien();
            DefaultTableModel model = nhanVienPanel.model;
            model.setRowCount(0); // Xóa dữ liệu cũ trong bảng trước khi load mới
            for (NhanVien nv : dsNhanVien) {
                String gioiTinh = nv.isGioiTinh() ? "Nam" : "Nữ"; // Nếu có kiểu boolean
                model.addRow(new Object[]{
                        nv.getMaNV(),
                        nv.getTenNV(),
                        gioiTinh,
                        nv.getNgaySinh(),
                        nv.getSdt(),
                        nv.getEmail(),
                        getChucVuHienThi(nv.getChucVu()),
                        (nv.getTaiKhoan() != null)
                                ? getTrangThaiTaiKhoanHienThi(nv.getTaiKhoan().getTrangThai())
                                : ""
                });
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ThemNhanVien(){
        if(kiemTraDuLieu()){
            int namHienTai = LocalDate.now().getYear();
            String maNV = "NV" + (namHienTai % 100) +  String.format("%03d", nhanVienDao.getSoLuongNhanVien() + 1);
            String tenNV = nhanVienPanel.txt_TenNhanVien.getText().strip();
            String sdt = nhanVienPanel.txt_SoDienThoai.getText().strip();
            boolean gioiTinh = nhanVienPanel.rdbtn_Nam.isSelected() ? true : false;
            Date date = nhanVienPanel.ngaySinh.getDate();
            LocalDate ngaySinh = null;
            if (date != null) {
                ngaySinh = date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            }
            String chucVuDaChon = nhanVienPanel.cbb_ChucVu.getSelectedItem().toString();
            ChucVuNhanVien chucVu = getChucVu(chucVuDaChon);
            String email = nhanVienPanel.txt_Email.getText().strip();

            NhanVien nhanVien = new NhanVien(maNV,tenNV,ngaySinh,sdt,gioiTinh,email,chucVu);
            if(nhanVienDao.themNhanVien(nhanVien)){
                JOptionPane.showMessageDialog(nhanVienPanel, "Thêm thành công");
                LamMoi();
                getTatCaNhanVien();
            }else{
                JOptionPane.showMessageDialog(nhanVienPanel, "Nhân viên đã tồn tại");
            }
        }
    }

    private void TimNhanVien() {
        try {
            NhanVien nv;
            if (nhanVienPanel.rdbtn_TimMaNhanVien.isSelected()) {
                String maNV = nhanVienPanel.txt_TimMaNhanVien.getText().strip();
                nv = nhanVienDao.TimNhanVien(maNV, "MA");
            } else {
                String soDT = nhanVienPanel.txt_TimSoDienThoai.getText().strip();
                nv = nhanVienDao.TimNhanVien(soDT, "SDT");
            }

            if (nv != null) {
                DefaultTableModel model = nhanVienPanel.model;
                model.setRowCount(0);
                String gioiTinh = nv.isGioiTinh() ? "Nam" : "Nữ";
                model.addRow(new Object[]{
                        nv.getMaNV(),
                        nv.getTenNV(),
                        gioiTinh,
                        nv.getNgaySinh(),
                        nv.getSdt(),
                        nv.getEmail(),
                        getChucVuHienThi(nv.getChucVu()),
                        (nv.getTaiKhoan() != null)
                                ? getTrangThaiTaiKhoanHienThi(nv.getTaiKhoan().getTrangThai())
                                : ""
                });
            } else {
                JOptionPane.showMessageDialog(nhanVienPanel, "Không tìm thấy nhân viên");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void LocNhanVienTheoChucVu() {
        String chucVuChon = nhanVienPanel.cbb_LocChucVu.getSelectedItem().toString();
        ArrayList<NhanVien> dsNhanVien;
        if (chucVuChon.equals("Tất cả")) {
            dsNhanVien = nhanVienDao.getTatCaNhanVien(); // gọi lấy toàn bộ
        } else {
            ChucVuNhanVien chucVu = getChucVu(chucVuChon);
            dsNhanVien = nhanVienDao.getNhanVienTheoChucVu(chucVu.toString());
        }
        DefaultTableModel model = nhanVienPanel.model;
        model.setRowCount(0);
        for (NhanVien nv : dsNhanVien) {
            String gioiTinh = nv.isGioiTinh() ? "Nam" : "Nữ";
            model.addRow(new Object[]{
                    nv.getMaNV(),
                    nv.getTenNV(),
                    gioiTinh,
                    nv.getNgaySinh(),
                    nv.getSdt(),
                    nv.getEmail(),
                    getChucVuHienThi(nv.getChucVu()),
                    (nv.getTaiKhoan() != null)
                            ? getTrangThaiTaiKhoanHienThi(nv.getTaiKhoan().getTrangThai())
                            : ""
            });
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = nhanVienPanel.table.getSelectedRow();
        if (row != -1) {
            String maNV = nhanVienPanel.table.getValueAt(row, 0).toString();
            String tenNV = nhanVienPanel.table.getValueAt(row, 1).toString();
            boolean gioiTinh = nhanVienPanel.table.getValueAt(row, 2).equals("Nam") ? true : false;
            String ngaySinhStr = nhanVienPanel.table.getValueAt(row, 3).toString();
            LocalDate ngaySinh = LocalDate.parse(ngaySinhStr);
            String sdt = nhanVienPanel.table.getValueAt(row,4).toString();
            String email = nhanVienPanel.table.getValueAt(row,5).toString();
            ChucVuNhanVien chucVuNhanVien = getChucVu(nhanVienPanel.table.getValueAt(row,6).toString());
            TrangThaiTaiKhoan trangThaiTaiKhoan = getTrangThaiTaiKhoan(nhanVienPanel.table.getValueAt(row,7).toString());
            TaiKhoan tk = null;
            if (trangThaiTaiKhoan != null && !trangThaiTaiKhoan.toString().trim().isEmpty()) {
                tk = new TaiKhoan(sdt,"12345678", VaiTro.LeTan);
            }
            NhanVien nhanVien = new NhanVien(maNV,tenNV,ngaySinh,sdt,gioiTinh,email,chucVuNhanVien,tk);
            NhanVienDialog dialog = new NhanVienDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(nhanVienPanel),
                    nhanVien
            );
            dialog.setVisible(true);
            LamMoi();
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

    private boolean kiemTraDuLieu() {
        String tenNV = nhanVienPanel.txt_TenNhanVien.getText().strip();
        String sdt = nhanVienPanel.txt_SoDienThoai.getText().strip();
        String email = nhanVienPanel.txt_Email.getText().strip();
        Date date = nhanVienPanel.ngaySinh.getDate();
        LocalDate ngaySinh = null;
        if (date != null) {
            ngaySinh = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
        if(tenNV.equals("") || tenNV == null){
            JOptionPane.showMessageDialog(nhanVienPanel, "Vui lòng nhập họ và tên của nhân viên");
            nhanVienPanel.txt_TenNhanVien.requestFocus();
            nhanVienPanel.txt_TenNhanVien.selectAll();
            return false;
        }else{
            try {
                String regexTen = "([\\p{Lu}][\\p{L}]+\\s){1,}[\\p{Lu}][\\p{L}]+";
                if(!Pattern.matches(regexTen,tenNV)){
                    JOptionPane.showMessageDialog(nhanVienPanel, "Tên nhân viên phải bắt đầu là chữ in hoa theo sau là chữ in thường và có ít nhất 2 từ \n ví dụ \"Nguyễn Văn An\"");
                    nhanVienPanel.txt_TenNhanVien.requestFocus();
                    nhanVienPanel.txt_TenNhanVien.selectAll();
                    return false;
                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(nhanVienPanel, "Tên nhân viên phải bắt đầu là chữ in hoa theo sau là chữ in thường và có ít nhất 2 từ \n ví dụ \"Nguyễn Văn An\"");
                nhanVienPanel.txt_TenNhanVien.requestFocus();
                nhanVienPanel.txt_TenNhanVien.selectAll();
            }
        }

        if(sdt.equals("") || sdt == null){
            JOptionPane.showMessageDialog(nhanVienPanel, "Vui lòng nhập số điện thoai của nhân viên");
            nhanVienPanel.txt_SoDienThoai.requestFocus();
            nhanVienPanel.txt_SoDienThoai.selectAll();
            return false;
        }else{
            try {
                String regexSDT = "[0-9]{10}";
                if(!Pattern.matches(regexSDT,sdt)){
                    JOptionPane.showMessageDialog(nhanVienPanel, "Số điện thoại phải là số và đủ 10 chữ số");
                    nhanVienPanel.txt_SoDienThoai.requestFocus();
                    nhanVienPanel.txt_SoDienThoai.selectAll();
                    return false;
                }
            }catch (Exception e){
                nhanVienPanel.txt_SoDienThoai.requestFocus();
                nhanVienPanel.txt_SoDienThoai.selectAll();
            }
        }

        if(email.equals("") || email == null){
            JOptionPane.showMessageDialog(nhanVienPanel, "Vui lòng nhập email của nhân viên");
            nhanVienPanel.txt_Email.requestFocus();
            nhanVienPanel.txt_Email.selectAll();
            return false;
        }else{
//            try {
//                String regexEmail = "[0-9]{10}";
//                if(!Pattern.matches(regexEmail,email)){
//                    JOptionPane.showMessageDialog(nhanVienPanel, "Số điện thoại phải là số và đủ 10 chữ số");
//                    nhanVienPanel.txt_Email.requestFocus();
//                    nhanVienPanel.txt_Email.selectAll();
//                    return false;
//                }
//            }catch (Exception e){
//                nhanVienPanel.txt_Email.requestFocus();
//                nhanVienPanel.txt_Email.selectAll();
//            }
        }

        return true;
    }

    public void suKienTextField(){
        nhanVienPanel.txt_TenNhanVien.addActionListener(e->{
            nhanVienPanel.txt_SoDienThoai.requestFocus();
        });
        nhanVienPanel.txt_SoDienThoai.addActionListener(e->{
            nhanVienPanel.ngaySinh.getCalendarButton().doClick();
        });
        nhanVienPanel.rdbtn_Nam.addActionListener(e->{
            nhanVienPanel.txt_Email.requestFocus();
        });
        nhanVienPanel.rdbtn_Nu.addActionListener(e->{
            nhanVienPanel.txt_Email.requestFocus();
        });
        nhanVienPanel.cbb_ChucVu.addActionListener(e->{
            nhanVienPanel.txt_Email.requestFocus();
        });
        nhanVienPanel.rdbtn_TimMaNhanVien.addActionListener(e ->{
            nhanVienPanel.txt_TimMaNhanVien.setEditable(true);
            nhanVienPanel.txt_TimMaNhanVien.requestFocus();
            nhanVienPanel.txt_TimSoDienThoai.setEditable(false);
        });
        nhanVienPanel.rdbtn_TimSoDienThoai.addActionListener(e -> {
            nhanVienPanel.txt_TimMaNhanVien.setEditable(false);
            nhanVienPanel.txt_TimSoDienThoai.requestFocus();
            nhanVienPanel.txt_TimSoDienThoai.setEditable(true);
        });
    }

    private  void LamMoi(){
        nhanVienPanel.txt_TenNhanVien.setText("");
        nhanVienPanel.rdbtn_Nam.setSelected(true);
        nhanVienPanel.rdbtn_Nu.setSelected(false);
        nhanVienPanel.txt_SoDienThoai.setText("");
        nhanVienPanel.txt_Email.setText("");
        nhanVienPanel.txt_TimMaNhanVien.setText("");
        nhanVienPanel.txt_TimSoDienThoai.setText("");
        nhanVienPanel.ngaySinh.setDate(java.sql.Date.valueOf(nhanVienPanel.maxDate));
        nhanVienPanel.cbb_ChucVu.setSelectedIndex(0);
        nhanVienPanel.cbb_LocChucVu.setSelectedIndex(0);
        nhanVienPanel.txt_TenNhanVien.requestFocus();
        getTatCaNhanVien();
    }

    private ChucVuNhanVien getChucVu(String tenChucVu) {
        switch (tenChucVu) {
            case "Kế toán" -> { return ChucVuNhanVien.KeToan ; }
            case "Kỹ thuật" -> { return ChucVuNhanVien.KyThuat; }
            case "Lễ tân" -> { return ChucVuNhanVien.LeTan; }
            case "Buồng phòng" -> { return ChucVuNhanVien.BuongPhong; }
            case "Bếp" -> { return ChucVuNhanVien.Bep; }
            case "Bảo vệ" -> { return ChucVuNhanVien.BaoVe; }
            case "Quản lý" -> { return ChucVuNhanVien.QuanLy; }
            default -> { return ChucVuNhanVien.LeTan; }
        }
    }

    public static String getChucVuHienThi(ChucVuNhanVien chucVu) {
        if (chucVu == null) return "";
        switch (chucVu) {
            case QuanLy -> { return "Quản lý"; }
            case LeTan -> { return "Lễ tân"; }
            case KeToan -> { return "Kế toán"; }
            case KyThuat -> { return "Kỹ thuật"; }
            case BuongPhong -> { return "Buồng phòng"; }
            case Bep -> { return "Bếp"; }
            case BaoVe -> { return "Bảo vệ"; }
            default -> { return ""; }
        }
    }
    private String getTrangThaiTaiKhoanHienThi(TrangThaiTaiKhoan trangThaiTaiKhoan) {
        switch (trangThaiTaiKhoan) {
            case DangHoatDong -> { return "Đang hoạt động"; }
            case VoHieuHoa -> { return "Vô hiệu hóa"; }
            default -> { return ""; }
        }
    }

    private TrangThaiTaiKhoan getTrangThaiTaiKhoan(String trangThai) {
        switch (trangThai) {
            case "Đang hoạt động" -> { return TrangThaiTaiKhoan.DangHoatDong; }
            case "Vô hiệu hóa" -> { return TrangThaiTaiKhoan.VoHieuHoa; }
            default -> { return null; }
        }
    }
}
