package controllers.dialogs;

import controllers.PhongController;
import database.dao.LoaiPhongDao;
import database.dao.PhongDao;
import entitys.LoaiPhong;
import entitys.Phong;
import enums.TrangThaiPhong;
import view.dialogs.PhongDialog;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

public class PhongDialogController {
	private PhongDialog phongDialog;
	private Phong phong;
    private PhongDao phongDao;
    private LoaiPhongDao loaiPhongDao;
	public PhongDialogController(PhongDialog phongDialog, Phong phong) {
        phongDao= new PhongDao();
        loaiPhongDao= new LoaiPhongDao();
		this.phongDialog = phongDialog;
		this.phong = phong;
		phongDialog.btn_CapNhat.addActionListener(e->{
			capNhatPhong();
		});
        phongDialog.btn_Dong.addActionListener(e->{
            phongDialog.dispose();
        });
        phongDialog.cbb_LoaiPhong.addActionListener(e->{
            chinhSuaThongTinTheoLoaiPhongVaSucChua();
        });
        phongDialog.txt_SucChuaToiDa.addActionListener(e->{
            chinhSuaThongTinTheoLoaiPhongVaSucChua();
        });
        phongDialog.txt_SucChuaToiDa.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e){
                chinhSuaThongTinTheoLoaiPhongVaSucChua();
            }
        });
        phongDialog.txt_GiaPhong.addActionListener(e->{
            chinhSuaTienCocTheoGiaPhong();
        });
        phongDialog.txt_GiaPhong.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e){
                chinhSuaTienCocTheoGiaPhong();
            }
        });
        phongDialog.btn_NgungHoatDong.addActionListener(e->{
            capNhatThongTinVaTrangThaiPhong();
        });
	}

    public void chinhSuaTienCocTheoGiaPhong(){
        String tenLoaiPhong=phongDialog.cbb_LoaiPhong.getSelectedItem()+"";
        LoaiPhong loaiPhong=loaiPhongDao.getThongTinLoaiPhong(tenLoaiPhong);
        try {
            double giaPhong = Double.parseDouble(phongDialog.txt_GiaPhong.getText());
            double tienCoc = giaPhong * loaiPhong.getTyLeCoc();
            phongDialog.txt_TienCoc.setText(tienCoc+"");
        } catch (NumberFormatException e) {
//            baoLoi("Giá phòng phải là số hợp lệ và lớn hơn hoặc bằng 100.000.");
        }
    }
    public void capNhatThongTinVaTrangThaiPhong(){
        // Cập nhật thông tin phòng
        if(kiemTraDuLieu()==false) return;
        String ma=phongDialog.txt_MaPhong.getText();
        String tenLoaiPhong=phongDialog.cbb_LoaiPhong.getSelectedItem()+"";
        LoaiPhong loaiPhong=loaiPhongDao.getThongTinLoaiPhong(tenLoaiPhong);
        int tang=Integer.parseInt(phongDialog.txt_Tang.getText());
        int soPhong=Integer.parseInt(phongDialog.txt_SoPhong.getText());
        int sucChua=Integer.parseInt(phongDialog.txt_SucChuaToiDa.getText());
        double giaPhong=Double.parseDouble(phongDialog.txt_GiaPhong.getText());
        double tienCoc=Double.parseDouble(phongDialog.txt_TienCoc.getText());
        TrangThaiPhong trangThaiPhong=doiTuMoTaSangEnum(phongDialog.txt_TrangThai.getText());
        Phong p=new Phong(ma,loaiPhong,tang,soPhong,sucChua,giaPhong,tienCoc,trangThaiPhong);
        if(!phongDao.capNhatPhong(p)){
            baoLoi("Lỗi khi cập nhật thông tin phòng!"); return;
        }
        // cập nhật trạng thái phòng
        TrangThaiPhong trangThai;
        if (phongDialog.btn_NgungHoatDong.getText().equalsIgnoreCase("Hoạt động")){
            trangThai= doiTuMoTaSangEnum("Trống");
        }else {
            trangThai= doiTuMoTaSangEnum(phongDialog.btn_NgungHoatDong.getText());
        }
        if(phongDao.capNhatTrangThaiPhong(ma,trangThai)){
            baoLoi("Cập nhật thành công!");
            phongDialog.dispose();
            // LOAD LẠI BẢNG
        }else{
            baoLoi("Lỗi cập nhật trạng thái phòng!");
        }

    }

    //dùng taiLen để ngăn chạy sự kiện thay đổi cbb_LoaiPhong
    boolean taiLen=false;
    public void hienThiThongTinPhong() {
        taiLen=true;
        phongDialog.txt_MaPhong.setText(phong.getMaPhong().trim());
        ArrayList<LoaiPhong> dslp=loaiPhongDao.getDanhSachLoaiPhong();
        phongDialog.cbb_LoaiPhong.removeAllItems();
        for(LoaiPhong lp : dslp){
            phongDialog.cbb_LoaiPhong.addItem(lp.getTenLoaiPhong());
        }
        phongDialog.cbb_LoaiPhong.setSelectedItem(phong.getLoaiPhong().getTenLoaiPhong());
        phongDialog.txt_Tang.setText(phong.getTang()+"");
        phongDialog.txt_SoPhong.setText(phong.getSoPhong()+"");
        phongDialog.txt_SucChuaToiDa.setText(phong.getSucChuaToiDa()+"");
        phongDialog.txt_GiaPhong.setText(phong.getGiaPhong()+"");
        phongDialog.txt_TienCoc.setText(phong.getTienCoc()+"");
        phongDialog.txt_TrangThai.setText(phong.getTrangThai().getMoTa());
//        phongDialog.cbb_TrangThai.setSelectedItem(phong.getTrangThai().getMoTa());
        if(phong.getTrangThai().name().equals("Trong")){
            phongDialog.btn_NgungHoatDong.setEnabled(true);
        }else{
            if(phong.getTrangThai().name().equals("NgungHoatDong")){
                phongDialog.btn_NgungHoatDong.setText("Hoạt động");
                phongDialog.btn_NgungHoatDong.setEnabled(true);
                taiLen=false; return;
            }
            phongDialog.btn_NgungHoatDong.setEnabled(false);
        }

        taiLen=false;
    }
    public void chinhSuaThongTinTheoLoaiPhongVaSucChua(){
        if(taiLen) return;
        String tenLoaiPhong=phongDialog.cbb_LoaiPhong.getSelectedItem()+"";
        LoaiPhong loaiPhong=loaiPhongDao.getThongTinLoaiPhong(tenLoaiPhong);
        int sucChuaToiThieu= loaiPhong.getSucChuaToiThieu();
        int sucChuaToiDa=0;
        try {
            sucChuaToiDa = Integer.parseInt(phongDialog.txt_SucChuaToiDa.getText());
        } catch (NumberFormatException e) {
            baoLoi("Sức chứa tối đa phải là số nguyên và lớn hơn hoặc bằng " + sucChuaToiThieu + " (loại phòng: "
                    + loaiPhong.getTenLoaiPhong() + ")");
            phongDialog.txt_SucChuaToiDa.requestFocus();
            return;
        }
        double giaPhong=0;
        double giaNiemYet =loaiPhong.getGiaNiemYet();
        if(sucChuaToiDa<sucChuaToiThieu){
            giaPhong=loaiPhong.getGiaNiemYet();
        }
        else if (sucChuaToiDa == sucChuaToiThieu) {
            giaPhong = giaNiemYet;
        } else {
            int soNguoiChenhlech = sucChuaToiDa - sucChuaToiThieu;
            giaPhong = giaNiemYet + (soNguoiChenhlech / 2) * 500000;
        }
        double tienCoc=loaiPhong.getTyLeCoc()*giaPhong;
        phongDialog.txt_GiaPhong.setText(giaPhong+"");
        phongDialog.txt_TienCoc.setText(tienCoc+"");
    }

	public void capNhatPhong() {
		if(kiemTraDuLieu()==false) return;
        String ma=phongDialog.txt_MaPhong.getText();
        String tenLoaiPhong=phongDialog.cbb_LoaiPhong.getSelectedItem()+"";
        LoaiPhong loaiPhong=loaiPhongDao.getThongTinLoaiPhong(tenLoaiPhong);
        int tang=Integer.parseInt(phongDialog.txt_Tang.getText());
        int soPhong=Integer.parseInt(phongDialog.txt_SoPhong.getText());
        int sucChua=Integer.parseInt(phongDialog.txt_SucChuaToiDa.getText());
        double giaPhong=Double.parseDouble(phongDialog.txt_GiaPhong.getText());
        double tienCoc=Double.parseDouble(phongDialog.txt_TienCoc.getText());
        TrangThaiPhong trangThaiPhong=doiTuMoTaSangEnum(phongDialog.txt_TrangThai.getText());
        Phong p=new Phong(ma,loaiPhong,tang,soPhong,sucChua,giaPhong,tienCoc,trangThaiPhong);
        if(phongDao.capNhatPhong(p)){
            baoLoi("Cập nhật thành công!");
            phongDialog.dispose();

            // LÀM MỚI
        }
        else{
            baoLoi("Lỗi cập nhật!");
        }
	}

    public TrangThaiPhong doiTuMoTaSangEnum(String moTa){
        for(TrangThaiPhong ttp:TrangThaiPhong.values()){
            if(ttp.getMoTa().equalsIgnoreCase(moTa)){
                return  ttp;
            }
        }return null;
    }
    public boolean kiemTraDuLieu(){
        String soPhong= phongDialog.txt_SoPhong.getText().trim();
        String sLTD= phongDialog.txt_SucChuaToiDa.getText().trim();
        String tang= phongDialog.txt_Tang.getText().trim();
        String giaPhong= phongDialog.txt_GiaPhong.getText().trim();
        String tienCoc= phongDialog.txt_TienCoc.getText().trim();
        if(!soPhong.matches("\\d{1,3}") || Integer.parseInt(soPhong) <= 0){
            baoLoi("Số phòng phải là số nguyên > 0 và tối đa 3 chữ số.");
            phongDialog.txt_SoPhong.requestFocus();
            return false;
        }
        String tenLoaiPhong=phongDialog.cbb_LoaiPhong.getSelectedItem()+"";
        LoaiPhong loaiPhong=loaiPhongDao.getThongTinLoaiPhong(tenLoaiPhong);
        int sucChuaToiThieu= loaiPhong.getSucChuaToiThieu();
        int sucChuaToiDa=0;
        try {
            sucChuaToiDa = Integer.parseInt(phongDialog.txt_SucChuaToiDa.getText());
            if(sucChuaToiDa<sucChuaToiThieu){
                baoLoi("Sức chứa tối đa phải là số nguyên và lớn hơn hoặc bằng " + sucChuaToiThieu + " (loại phòng: "
                        + loaiPhong.getTenLoaiPhong() + ")");
                phongDialog.txt_SucChuaToiDa.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            baoLoi("Sức chứa tối đa phải là số nguyên và lớn hơn hoặc bằng " + sucChuaToiThieu + " (loại phòng: "
                    + loaiPhong.getTenLoaiPhong() + ")");
            phongDialog.txt_SucChuaToiDa.requestFocus();
            return false;
        }

        if(!tang.matches("\\d{1,2}")||Integer.parseInt(tang)<=0){
            baoLoi("Tầng phải là số nguyên > 0 và tối đa 2 chữ số.");
            phongDialog.txt_Tang.requestFocus();
            return false;
        }
        double gia;
        try {
            gia= Double.parseDouble(giaPhong);
            if (gia<100000) {
                baoLoi("Giá phòng phải là số và lớn hơn hoặc bằng 100.000.");
                phongDialog.txt_GiaPhong.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            baoLoi("Giá phòng phải là số và lớn hơn hoặc bằng 100.000.");
            phongDialog.txt_GiaPhong.requestFocus();
            return false;
        }
        double tienC;
        try {
            tienC = Double.parseDouble(tienCoc);
            double minCoc= gia*0.1;
            double maxCoc = gia*0.5;
            if (tienC < minCoc||tienC> maxCoc) {
                baoLoi("Tiền cọc phải là số và nằm trong khoảng từ 10% đến 50% giá phòng ("+minCoc+"-"+maxCoc+").");
                phongDialog.txt_TienCoc.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            baoLoi("Tiền cọc phải là số hợp lệ.");
            phongDialog.txt_TienCoc.requestFocus();
            return false;
        }

        return true;
    }

    public void baoLoi(String l) {
        JOptionPane.showMessageDialog(null, l);
    }
	
}
