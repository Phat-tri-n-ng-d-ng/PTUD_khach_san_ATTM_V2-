package controllers.dialogs;

import database.dao.NhanVienDao;
import entitys.NhanVien;
import entitys.TaiKhoan;
import enums.ChucVuNhanVien;
import enums.TrangThaiTaiKhoan;
import enums.VaiTro;
import view.dialogs.NhanVienDialog;
import view.panels.NhanVienPanel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

public class NhanVienDialogController {
    private NhanVien nhanVien;
    private NhanVienDialog nhanVienDialog;
    private NhanVienDao nhanVienDao;

    public NhanVienDialogController(NhanVienDialog nhanVienDialog , NhanVien nhanVien){
        this.nhanVienDialog = nhanVienDialog;
        this.nhanVien = nhanVien;
        nhanVienDao = new NhanVienDao();
        nhanVienDialog.btn_CapNhat.addActionListener(e -> CapNhatNhanVien());
        nhanVienDialog.btn_Dong.addActionListener(e -> Dong());
        nhanVienDialog.btn_CapTaiKhoan.addActionListener(e -> CapTaiKhoanNhanVien());
    }

    private void CapTaiKhoanNhanVien() {
        if(nhanVien.getTaiKhoan() == null){
            String maNV =nhanVienDialog.txt_MaNhanVien.getText().strip();
            String sdt = nhanVienDialog.txt_SoDienThoai.getText().strip();
            ChucVuNhanVien chucVu = nhanVien.getChucVu();
            VaiTro vaiTro;
            switch (chucVu) {
                case QuanLy:
                    vaiTro = VaiTro.QuanLy;
                default:
                    vaiTro = VaiTro.LeTan;
            }
            TaiKhoan taiKhoan = new TaiKhoan(sdt,"12345678",vaiTro);
            NhanVien nhanVien1 = new NhanVien(maNV, taiKhoan);
            if(nhanVienDao.TaoTaiKhoanNhanVien(nhanVien1)){
                JOptionPane.showMessageDialog(nhanVienDialog, "Cấp tài khoản thành công");
                Dong();
            }else{
                JOptionPane.showMessageDialog(nhanVienDialog, "Cấp tài khoản thất bại");

            }
        }else{
            JOptionPane.showMessageDialog(nhanVienDialog, "Nhân viên đã có tài khoản");
        }
    }

    private void Dong() {
        nhanVienDialog.dispose();
    }

    private void CapNhatNhanVien() {
        if(kiemTraDuLieu()){
            String maNV =nhanVienDialog.txt_MaNhanVien.getText().strip();
            String tenNV = nhanVienDialog.txt_TenNhanVien.getText().strip();
            String sdt = nhanVienDialog.txt_SoDienThoai.getText().strip();
            boolean gioiTinh = nhanVienDialog.rdbtn_Nam.isSelected() ? true : false;
            Date date = nhanVienDialog.ngaySinh.getDate();
            LocalDate ngaySinh = null;
            if (date != null) {
                ngaySinh = date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            }
            String chucVuDaChon = nhanVienDialog.cbb_ChucVu.getSelectedItem().toString();
            ChucVuNhanVien chucVu = getChucVu(chucVuDaChon);
            String email = nhanVienDialog.txt_Email.getText().strip();
            TaiKhoan tk = null;
            NhanVien nhanVienNew = null;
            if(nhanVien.getChucVu().equals(ChucVuNhanVien.QuanLy) || nhanVien.getChucVu().equals(ChucVuNhanVien.LeTan)){
                String String_TrangThaiTaiKoan = nhanVienDialog.cbb_TrangThaiTaiKhoan.getSelectedItem().toString();
                TrangThaiTaiKhoan trangThaiTaiKhoan = getTrangThaiTaiKhoan(String_TrangThaiTaiKoan);
                if (trangThaiTaiKhoan != null && !trangThaiTaiKhoan.toString().trim().isEmpty()) {
                    tk = new TaiKhoan(sdt,trangThaiTaiKhoan);
                }
                nhanVienNew = new NhanVien(maNV,tenNV,ngaySinh,sdt,gioiTinh,email,chucVu,tk);
            }else{
                nhanVienNew = new NhanVien(maNV,tenNV,ngaySinh,sdt,gioiTinh,email,chucVu,tk);
            }
            int confirm = JOptionPane.showConfirmDialog(nhanVienDialog, "Bạn có chắc chắn muốn cập nhật thông tin nhân viên" + maNV,"Chú ý",JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION){
                if(nhanVienDao.CapNhatNhanVien(nhanVienNew)){
                    JOptionPane.showMessageDialog(nhanVienDialog, "Cập nhật thành công");
                    Dong();
                }else{
                    JOptionPane.showMessageDialog(nhanVienDialog, "Cập nhật thất bại");
                }
            }
        }
    }

    public void HienThiThongTinNhanVien(){
        nhanVienDialog.txt_MaNhanVien.setText(nhanVien.getMaNV());
        nhanVienDialog.txt_TenNhanVien.setText(nhanVien.getTenNV());
        nhanVienDialog.txt_SoDienThoai.setText(nhanVien.getSdt());
        nhanVienDialog.txt_Email.setText(nhanVien.getEmail());
        if(nhanVien.isGioiTinh()) {
            nhanVienDialog.rdbtn_Nam.setSelected(true);
            nhanVienDialog.rdbtn_Nu.setSelected(false);
        }else {
            nhanVienDialog.rdbtn_Nam.setSelected(false);
            nhanVienDialog.rdbtn_Nu.setSelected(true);
        }
        nhanVienDialog.cbb_ChucVu.setSelectedItem(getChucVuHienThi(nhanVien.getChucVu()));
        Date date = java.sql.Date.valueOf(nhanVien.getNgaySinh());
        nhanVienDialog.ngaySinh.setDate(date);
        if(nhanVien.getChucVu().equals(ChucVuNhanVien.QuanLy) || nhanVien.getChucVu().equals(ChucVuNhanVien.LeTan)){
            if(nhanVien.getTaiKhoan() == null){
                nhanVienDialog.cbb_TrangThaiTaiKhoan.addItem("Chưa có tài khoàn");
                nhanVienDialog.cbb_TrangThaiTaiKhoan.setEnabled(false);
            }else{
                nhanVienDialog.cbb_TrangThaiTaiKhoan.addItem("Đang hoạt động");
                nhanVienDialog.cbb_TrangThaiTaiKhoan.addItem("Vô hiệu hóa");
                if(nhanVien.getTaiKhoan().getTrangThai().equals(TrangThaiTaiKhoan.DangHoatDong)){
                    nhanVienDialog.cbb_TrangThaiTaiKhoan.setSelectedItem("Đang hoạt động");
                }else{
                    nhanVienDialog.cbb_TrangThaiTaiKhoan.setSelectedItem("Vô hiệu hóa");
                }
            }
        }else{
            nhanVienDialog.btn_CapTaiKhoan.setVisible(false);
            nhanVienDialog.cbb_TrangThaiTaiKhoan.setVisible(false);
            nhanVienDialog.lpl_TrangThaiTaiKhoan.setVisible(false);
            nhanVienDialog.cbb_ChucVu.setBounds(10, 350, 446, 30);
        }
    }

    private boolean kiemTraDuLieu() {
        String tenNV = nhanVienDialog.txt_TenNhanVien.getText().strip();
        String sdt = nhanVienDialog.txt_SoDienThoai.getText().strip();
        String email = nhanVienDialog.txt_Email.getText().strip();
        Date date = nhanVienDialog.ngaySinh.getDate();
        LocalDate ngaySinh = null;
        if (date != null) {
            ngaySinh = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
        if(tenNV.equals("") || tenNV == null){
            JOptionPane.showMessageDialog(nhanVienDialog, "Vui lòng nhập họ và tên của nhân viên");
            nhanVienDialog.txt_TenNhanVien.requestFocus();
            nhanVienDialog.txt_TenNhanVien.selectAll();
            return false;
        }else{
            try {
                String regexTen = "([\\p{Lu}][\\p{L}]+\\s){1,}[\\p{Lu}][\\p{L}]+";
                if(!Pattern.matches(regexTen,tenNV)){
                    JOptionPane.showMessageDialog(nhanVienDialog, "Tên nhân viên phải bắt đầu là chữ in hoa theo sau là chữ in thường và có ít nhất 2 từ \n ví dụ \"Nguyễn Văn An\"");
                    nhanVienDialog.txt_TenNhanVien.requestFocus();
                    nhanVienDialog.txt_TenNhanVien.selectAll();
                    return false;
                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(nhanVienDialog, "Tên nhân viên phải bắt đầu là chữ in hoa theo sau là chữ in thường và có ít nhất 2 từ \n ví dụ \"Nguyễn Văn An\"");
                nhanVienDialog.txt_TenNhanVien.requestFocus();
                nhanVienDialog.txt_TenNhanVien.selectAll();
            }
        }

        if(sdt.equals("") || sdt == null){
            JOptionPane.showMessageDialog(nhanVienDialog, "Vui lòng nhập số điện thoai của nhân viên");
            nhanVienDialog.txt_SoDienThoai.requestFocus();
            nhanVienDialog.txt_SoDienThoai.selectAll();
            return false;
        }else{
            try {
                String regexSDT = "[0-9]{10}";
                if(!Pattern.matches(regexSDT,sdt)){
                    JOptionPane.showMessageDialog(nhanVienDialog, "Số điện thoại phải là số và đủ 10 chữ số");
                    nhanVienDialog.txt_SoDienThoai.requestFocus();
                    nhanVienDialog.txt_SoDienThoai.selectAll();
                    return false;
                }
            }catch (Exception e){
                nhanVienDialog.txt_SoDienThoai.requestFocus();
                nhanVienDialog.txt_SoDienThoai.selectAll();
            }
        }

        if(email.equals("") || email == null){
            JOptionPane.showMessageDialog(nhanVienDialog, "Vui lòng nhập email của nhân viên");
            nhanVienDialog.txt_Email.requestFocus();
            nhanVienDialog.txt_Email.selectAll();
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

    private String getChucVuHienThi(ChucVuNhanVien chucVu) {
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

    private VaiTro getVaiTro(String tenVaiTro) {
        switch (tenVaiTro) {
            case "Lễ tân" -> { return VaiTro.LeTan; }
            case "Quản lý" -> { return VaiTro.QuanLy; }
            default -> { return null; }
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
