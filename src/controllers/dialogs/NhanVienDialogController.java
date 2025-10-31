package controllers.dialogs;

import entitys.NhanVien;
import enums.ChucVuNhanVien;
import view.dialogs.NhanVienDialog;
import view.panels.NhanVienPanel;

import java.util.Date;

public class NhanVienDialogController {
    private NhanVien nhanVien;
    private NhanVienDialog nhanVienDialog;

    public NhanVienDialogController(NhanVienDialog nhanVienDialog , NhanVien nhanVien){
        this.nhanVienDialog = nhanVienDialog;
        this.nhanVien = nhanVien;
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
}
