package controllers.dialogs;

import entitys.KhachHang;
import entitys.NhanVien;
import services.KhachHangService;
import view.dialogs.KhachHangDialog;
import view.dialogs.NhanVienDialog;

import javax.swing.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class KhachHangDialogController {
    public KhachHang khachHang;
    public KhachHangDialog khachHangDialog;
    public KhachHangService khachHangService;

    public KhachHangDialogController(KhachHangDialog khachHangDialog , KhachHang khachHang){
        khachHangService = new KhachHangService();
        this.khachHangDialog = khachHangDialog;
        this.khachHang = khachHang;

        khachHangDialog.btn_CapNhat.addActionListener(e -> CapNhatKhachHang());
        khachHangDialog.btn_Dong.addActionListener(e -> Dong());
    }

    private void CapNhatKhachHang() {
        String maKH =khachHangDialog.txt_MaKH.getText().strip();
        String tenKH = khachHangDialog.txt_TenKhachHang.getText().strip();
        String sdt = khachHangDialog.txt_SoDienThoai.getText().strip();
        boolean gioiTinh = khachHangDialog.rdbtn_Nam.isSelected() ? true : false;
        Date date = khachHangDialog.ngaySinh.getDate();
        LocalDate ngaySinh = null;
        if (date != null) {
            ngaySinh = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
        String email = khachHangDialog.txt_Email.getText().strip();
        String soCCCD = khachHangDialog.txt_SoCCCD.getText().strip();
        KhachHang khachHang = new KhachHang(maKH,tenKH,gioiTinh, ngaySinh, email, sdt,soCCCD);
        if(khachHangService.CapNhatKhachHang(khachHang)){
            JOptionPane.showMessageDialog(khachHangDialog, "Cập nhật thành công");
            Dong();
        }else{
            JOptionPane.showMessageDialog(khachHangDialog, "Cập nhật thất bại");
        }
    }

    private void Dong() {
        khachHangDialog.dispose();
    }

    public void HienThiThongTinNhanVien(){
        khachHangDialog.txt_MaKH.setText(khachHang.getMaKH());
        khachHangDialog.txt_TenKhachHang.setText(khachHang.getTenKH());
        khachHangDialog.txt_SoDienThoai.setText(khachHang.getSdt());
        khachHangDialog.txt_Email.setText(khachHang.getEmail());
        khachHangDialog.txt_SoCCCD.setText(khachHang.getSoCCCD());
        if(khachHang.isGioiTinh()) {
            khachHangDialog.rdbtn_Nam.setSelected(true);
            khachHangDialog.rdbtn_Nu.setSelected(false);
        }else {
            khachHangDialog.rdbtn_Nam.setSelected(false);
            khachHangDialog.rdbtn_Nu.setSelected(true);
        }
        Date date = java.sql.Date.valueOf(khachHang.getNgaySinh());
        khachHangDialog.ngaySinh.setDate(date);
        khachHangDialog.txt_DiemTichLuy.setText(String.valueOf(khachHang.getDiemTichLuy()));
        khachHangDialog.txt_HangKhachHang.setText(khachHang.getHangKH().getTenHang());
    }
}
