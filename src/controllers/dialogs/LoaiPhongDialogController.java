/*
 * @ (#) LoaiPhongDialogController.java     1.0     11/1/2025
 *
 *Copyright (c) 2025 IUH. All rights reserved.
 */
package controllers.dialogs;


import database.dao.LoaiPhongDao;
import entitys.LoaiPhong;
import view.dialogs.LoaiPhongDialog;

import javax.swing.*;

/*
 * @description: This class represents a bank with many bank accounts
 * @author: Anh, Le The Anh
 * @date: 11/1/2025
 * @version: 1.0
 */
public class LoaiPhongDialogController {
    LoaiPhongDialog loaiPhongDialog;
    LoaiPhong loaiPhong;
    LoaiPhongDao loaiPhongDao;

    public LoaiPhongDialogController(LoaiPhongDialog loaiPhongDialog, LoaiPhong loaiPhong) {
        this.loaiPhongDialog = loaiPhongDialog;
        this.loaiPhong = loaiPhong;
        loaiPhongDao= new LoaiPhongDao();

        loaiPhongDialog.btn_Dong.addActionListener(e -> {
            loaiPhongDialog.dispose();
        });
        loaiPhongDialog.btn_CapNhat.addActionListener(e->{
            capNhatLoaiPhong();
        });

    }

    public void hienThiThongTinLoaiPhong() {
        loaiPhongDialog.txt_MaLoaiPhong.setText(loaiPhong.getMaLoaiPhong().trim());
        loaiPhongDialog.txt_GiaNiemYet.setText(loaiPhong.getGiaNiemYet() + "");
        loaiPhongDialog.txt_TyLeCoc.setText(loaiPhong.getTyLeCoc() + "");
        loaiPhongDialog.txt_TenLoaiPhong.setText(loaiPhong.getTenLoaiPhong());
        loaiPhongDialog.txt_SucChuaToiThieu.setText(loaiPhong.getSucChuaToiThieu() + "");
    }

    public void capNhatLoaiPhong(){
        if(kiemTraDuLieu()==false) return;
            String maMoi=loaiPhongDialog.txt_MaLoaiPhong.getText();
            String tenLPMoi=loaiPhongDialog.txt_TenLoaiPhong.getText();
            double giaMoi=Double.parseDouble(loaiPhongDialog.txt_GiaNiemYet.getText());
            double tyLCMoi=Double.parseDouble(loaiPhongDialog.txt_TyLeCoc.getText());
            int sucChuaMoi=Integer.parseInt(loaiPhongDialog.txt_SucChuaToiThieu.getText());

            LoaiPhong loaiPhongMoi= new LoaiPhong(maMoi,tenLPMoi,giaMoi,tyLCMoi/100,sucChuaMoi);
            if(loaiPhongDao.capNhatLoaiPhong(loaiPhongMoi)){
//                loaiPhongPanel.table.setValueAt(loaiPhongMoi.getMaLoaiPhong(),r,0);
//                loaiPhongPanel.table.setValueAt(loaiPhongMoi.getMaLoaiPhong(),r,0);
//                loaiPhongPanel.table.setValueAt(loaiPhongMoi.getMaLoaiPhong(),r,0);
//                loaiPhongPanel.table.setValueAt(loaiPhongMoi.getMaLoaiPhong(),r,0);
//                loaiPhongPanel.table.setValueAt(loaiPhongMoi.getMaLoaiPhong(),r,0);
                baoLoi("Cập nhật thành công!");
                loaiPhongDialog.dispose();

                // LÀM MỚI
            }
            else{
                baoLoi("Lỗi cập nhật!");
            }
    }
    public boolean kiemTraDuLieu() {
        String tenLP = loaiPhongDialog.txt_TenLoaiPhong.getText();
        String giaNY = loaiPhongDialog.txt_GiaNiemYet.getText().trim();
        String tyLC = loaiPhongDialog.txt_TyLeCoc.getText().trim();
        String soNguoi = loaiPhongDialog.txt_SucChuaToiThieu.getText().trim();

        if (tenLP.isEmpty() || !tenLP.matches("^[\\p{L}0-9 ]{1,50}$")) {
            baoLoi("Tên loại phòng không được rỗng, tối đa 50 ký tự và không chứa ký tự đặc biệt.");
            loaiPhongDialog.txt_TenLoaiPhong.requestFocus();
            return false;
        }
        double gia;
        try {
            gia = Double.parseDouble(giaNY);
            if (gia < 100000) {
                baoLoi("Giá niêm yết phải >= 100.000");
                loaiPhongDialog.txt_GiaNiemYet.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            baoLoi("Giá niêm yết phải là số và >= 100.000");
            loaiPhongDialog.txt_GiaNiemYet.requestFocus();
            return false;
        }
        double tyLe;
        try {
            tyLe = Double.parseDouble(tyLC);
            if (tyLe < 10 || tyLe > 50) {
                baoLoi("Tỷ lệ cọc phải nằm trong khoảng 10% - 50%.");
                loaiPhongDialog.txt_TyLeCoc.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            baoLoi("Tỷ lệ cọc phải là số hợp lệ và nằm trong khoảng 10% - 50%.");
            loaiPhongDialog.txt_TyLeCoc.requestFocus();
            return false;
        }
        int soNguoiInt;
        try {
            soNguoiInt = Integer.parseInt(soNguoi);
            if (soNguoiInt <1) {
                baoLoi("Sức chứa tối thiểu phải là số nguyên và lớn hơn hoặc bằng 1.");
                loaiPhongDialog.txt_SucChuaToiThieu.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            baoLoi("Sức chứa tối thiểu phải là số nguyên và lớn hơn hoặc bằng 1.");
            loaiPhongDialog.txt_SucChuaToiThieu.requestFocus();
            return false;
        }

        return true;
    }
    public void baoLoi(String s){
        JOptionPane.showMessageDialog(null,s);
    }
}
