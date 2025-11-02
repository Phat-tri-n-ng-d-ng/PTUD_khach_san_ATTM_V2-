package controllers.dialogs;

import entitys.KhachHang;
import entitys.NhanVien;
import view.dialogs.KhachHangDialog;
import view.dialogs.NhanVienDialog;

import java.util.Date;

public class KhachHangDialogController {
    public KhachHang khachHang;
    public KhachHangDialog khachHangDialog;

    public KhachHangDialogController(KhachHangDialog khachHangDialog , KhachHang khachHang){
        this.khachHangDialog = khachHangDialog;
        this.khachHang = khachHang;
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
        khachHangDialog.txt_HangKhachHang.setText(khachHang.getHangKH().toString());
    }
}
