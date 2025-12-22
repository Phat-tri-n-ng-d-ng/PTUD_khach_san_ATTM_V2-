package controllers.dialogs;

import database.dao.KhachHangDao;
import entitys.HangKhachHang;
import entitys.KhachHang;
import view.dialogs.HangKhachHangDialog;

import javax.swing.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class HangKhachHangDialogController {
    public HangKhachHangDialog hangKhachHangDialog;
    public ArrayList<HangKhachHang> dsHang;
    private KhachHangDao khachHangDao;

    public HangKhachHangDialogController(HangKhachHangDialog hangKhachHangDialog , ArrayList<HangKhachHang> dsHang){
        this.hangKhachHangDialog = hangKhachHangDialog;
        this.dsHang = dsHang;
        khachHangDao = new KhachHangDao();
        hangKhachHangDialog.btn_Dong.addActionListener(e -> Dong());
        hangKhachHangDialog.btn_CapNhat.addActionListener(e -> CapNhatHangKhachHang());
    }

    private void CapNhatHangKhachHang() {
        double dong = Double.parseDouble(hangKhachHangDialog.txt_DiemMoi_Dong.getText().strip());
        double bac = Double.parseDouble(hangKhachHangDialog.txt_DiemMoi_Bac.getText().strip());
        double vang = Double.parseDouble(hangKhachHangDialog.txt_DiemMoi_Vang.getText().strip());
        double kc = Double.parseDouble(hangKhachHangDialog.txt_DiemMoi_KC.getText().strip());
        for (HangKhachHang h : dsHang) {
            String tenHang = h.getTenHang().trim().toLowerCase();

            switch (tenHang) {
                case "đồng":
                    h.setDiemToiThieu(dong);
                    break;
                case "bạc":
                    h.setDiemToiThieu(bac);
                    break;
                case "vàng":
                    h.setDiemToiThieu(vang);
                    break;
                case "kim cương":
                    h.setDiemToiThieu(kc);
                    break;
            }
        }
        if(khachHangDao.CapNhatHangKhachHang(dsHang)){
            JOptionPane.showMessageDialog(hangKhachHangDialog, "Cập nhật thành công");
            Dong();
        }else{
            JOptionPane.showMessageDialog(hangKhachHangDialog, "Cập nhật thất bại");
        }
    }

    private void Dong() {
        hangKhachHangDialog.dispose();
    }
}



