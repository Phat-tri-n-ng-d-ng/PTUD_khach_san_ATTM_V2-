/*
 * @ (#) LoaiPhongDialogController.java     1.0     11/1/2025
 *
 *Copyright (c) 2025 IUH. All rights reserved.
 */
package controllers.dialogs;


import entitys.LoaiPhong;
import view.dialogs.LoaiPhongDialog;

/*
 * @description: This class represents a bank with many bank accounts
 * @author: Anh, Le The Anh
 * @date: 11/1/2025
 * @version: 1.0
 */
public class LoaiPhongDialogController {
    LoaiPhongDialog loaiPhongDialog;
    LoaiPhong loaiPhong;

    public LoaiPhongDialogController(LoaiPhongDialog loaiPhongDialog, LoaiPhong loaiPhong) {
        this.loaiPhongDialog = loaiPhongDialog;
        this.loaiPhong = loaiPhong;

        loaiPhongDialog.btn_Dong.addActionListener(e->{
            loaiPhongDialog.dispose();
        });
    }

    public void hienThiThongTinLoaiPhong(){
        loaiPhongDialog.txt_MaLoaiPhong.setText(loaiPhong.getMaLoaiPhong());
        loaiPhongDialog.txt_GiaNiemYet.setText(loaiPhong.getGiaNiemYet()+"");
        loaiPhongDialog.txt_TyLeCoc.setText(loaiPhong.getTyLeCoc()+"");
        loaiPhongDialog.txt_TenLoaiPhong.setText(loaiPhong.getTenLoaiPhong());
        loaiPhongDialog.txt_SoNguoiMacDinh.setText(loaiPhong.getSoNguoiMacDinh()+"");
    }
}
