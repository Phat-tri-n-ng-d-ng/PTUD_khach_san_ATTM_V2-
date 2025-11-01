package controllers.dialogs;

import controllers.PhongController;
import entitys.Phong;
import view.dialogs.PhongDialog;

public class PhongDialogController {
	private PhongDialog phongDialog;
	private Phong phong;
//	private PhongController phongController;
	public PhongDialogController(PhongDialog phongDialog, Phong phong) {

		this.phongDialog = phongDialog;
		this.phong = phong;
//		phongController= new PhongController();
//		hienThiThongTinPhong();
		phongDialog.btn_CapNhat.addActionListener(e->{
			capNhatPhong();
		});
        phongDialog.btn_Dong.addActionListener(e->{
            phongDialog.dispose();
        });
	}
	
	public void hienThiThongTinPhong() {
		phongDialog.txt_MaPhong.setText(phong.getMaPhong());
		phongDialog.cbb_LoaiPhong.setSelectedItem(phong.getLoaiPhong().getTenLoaiPhong());
		phongDialog.txt_Tang.setText(phong.getTang()+"");
		phongDialog.txt_SoPhong.setText(phong.getSoPhong()+"");
		phongDialog.txt_SoLuongToiDa.setText(phong.getSoLuongToiDa()+"");
		phongDialog.txt_GiaPhong.setText(phong.getGiaPhong()+"");
		phongDialog.txt_TienCoc.setText(phong.getTienCoc()+"");
		phongDialog.cbb_TrangThai.setSelectedItem(phong.getTrangThai().getMoTa());
	}
	public void capNhatPhong() {
		
	}
	
	
	
}
