package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import database.dao.LoaiPhongDao;
import database.dao.PhongDao;
import entitys.LoaiPhong;
import entitys.Phong;
import enums.TrangThaiPhong;
import view.dialogs.PhongDialog;
import view.panels.PhongPanel;

public class PhongController implements MouseListener{
	PhongPanel phongPanel;
    PhongDao phongDao;
    LoaiPhongDao loaiPhongDao;

	public PhongController(PhongPanel phongPanel) {
		this.phongPanel = phongPanel;
		phongDao= new PhongDao();
        loaiPhongDao= new LoaiPhongDao();
		phongPanel.btn_LamMoi.addActionListener(e->{
			lamMoi();
		});
		phongPanel.btn_ThemPhong.addActionListener(e->{
			themPhong();
		});
		phongPanel.btn_Tim.addActionListener(e->{
            hienThiPhongTim();
        });
        phongPanel.txt_TimMaPhong.addActionListener(e->{
            hienThiPhongTim();
        });
		phongPanel.table.addMouseListener(this);
        phongPanel.cbb_LoaiPhong.addActionListener(e->{
            hienThiDanhSachPhongTheoLoaiPhong();
        });
        phongPanel.chckbx_Deluxe.addActionListener(e->{
            hienThiDanhSachPhongTheoLoaiPhong();
        });
        phongPanel.chckbx_FamilyRoom.addActionListener(e->{
            hienThiDanhSachPhongTheoLoaiPhong();
        });
        phongPanel.chckbx_Standard.addActionListener(e->{
            hienThiDanhSachPhongTheoLoaiPhong();
        });
        phongPanel.chckbx_Suite.addActionListener(e->{
            hienThiDanhSachPhongTheoLoaiPhong();
        });
        phongPanel.chckbx_Superior.addActionListener(e->{
            hienThiDanhSachPhongTheoLoaiPhong();
        });

		
	}

    public void hienThiDanhSachPhongTheoLoaiPhong(){
        String chuoiLoaiPhongDaChon="";
        if(phongPanel.chckbx_Superior.isSelected()) chuoiLoaiPhongDaChon+="'"+phongPanel.chckbx_Superior.getText()+"',";
        if(phongPanel.chckbx_Suite.isSelected()) chuoiLoaiPhongDaChon+="'"+phongPanel.chckbx_Suite.getText()+"',";
        if(phongPanel.chckbx_Standard.isSelected()) chuoiLoaiPhongDaChon+="'"+phongPanel.chckbx_Standard.getText()+"',";
        if(phongPanel.chckbx_Deluxe.isSelected()) chuoiLoaiPhongDaChon+="'"+phongPanel.chckbx_Deluxe.getText()+"',";
        if(phongPanel.chckbx_FamilyRoom.isSelected()) chuoiLoaiPhongDaChon+="'"+phongPanel.chckbx_FamilyRoom.getText()+"',";

        // nếu chuỗi ko rỗng thì nó sẽ có dấu "," ở cuối nên bỏ đi
        ArrayList<Phong> danhSachPhong= new ArrayList<>();
        if(!chuoiLoaiPhongDaChon.isEmpty()){
            chuoiLoaiPhongDaChon=chuoiLoaiPhongDaChon.substring(0,chuoiLoaiPhongDaChon.length()-1);
            danhSachPhong=phongDao.locPhongTheoLoai(chuoiLoaiPhongDaChon);
        }
        if(chuoiLoaiPhongDaChon.isEmpty()){
            danhSachPhong=phongDao.getDanhSachPhong();
        }
        phongPanel.model.setRowCount(0);
        for (Phong p : danhSachPhong) {
            String tang=String.format("%02d",Integer.parseInt(p.getTang()+""));
            String soPhong= String.format("%03d",Integer.parseInt(p.getSoPhong()+""));
            phongPanel.model.addRow(new Object[]{p.getMaPhong(),p.getLoaiPhong().getTenLoaiPhong(),
                    tang,soPhong,p.getSucChuaToiDa(),p.getGiaPhong(),p.getTienCoc(),
                    p.getTrangThai().getMoTa()});
        }
    }
    public void hienThiPhongTim(){

        String ma =phongPanel.txt_TimMaPhong.getText();
        if(ma.isEmpty()){
            baoLoi("Hãy nhập mã để tìm"); return;
        }
        phongPanel.model.setRowCount(0);
        Phong p = phongDao.timPhongBangMa(ma);
        if(p!=null){
            String tang=String.format("%02d",Integer.parseInt(p.getTang()+""));
            String soPhong= String.format("%03d",Integer.parseInt(p.getSoPhong()+""));
            phongPanel.model.addRow(new Object[]{p.getMaPhong(),p.getLoaiPhong().getTenLoaiPhong(),
                    tang,soPhong,p.getSucChuaToiDa(),p.getGiaPhong(),p.getTienCoc(),
                    p.getTrangThai().getMoTa()});
        }else{
            baoLoi("Không tìm thấy phòng có mã: "+ma);
        }

    }
	public void hienThiDanhSachPhong(){
        ArrayList<Phong> dsp= phongDao.getDanhSachPhong();
        phongPanel.model.setRowCount(0);
        for (Phong p : dsp) {
            String tang=String.format("%02d",Integer.parseInt(p.getTang()+""));
            String soPhong= String.format("%03d",Integer.parseInt(p.getSoPhong()+""));
            phongPanel.model.addRow(new Object[]{p.getMaPhong(),p.getLoaiPhong().getTenLoaiPhong(),
                    tang,soPhong,p.getSucChuaToiDa(),p.getGiaPhong(),p.getTienCoc(),
                    p.getTrangThai().getMoTa()});
        }
    }
	
	public void lamMoi() {
		phongPanel.txt_SucChuaToiDa.setText("");
		phongPanel.txt_SoPhong.setText("");
		phongPanel.txt_Tang.setText("");
		phongPanel.txt_TimMaPhong.setText("");
		phongPanel.cbb_LoaiPhong.setSelectedIndex(0);
		phongPanel.chckbx_Deluxe.setSelected(false);
		phongPanel.chckbx_FamilyRoom.setSelected(false);
		phongPanel.chckbx_Standard.setSelected(false);
		phongPanel.chckbx_Suite.setSelected(false);
		phongPanel.chckbx_Superior.setSelected(false);

		// load lại bảng
        phongPanel.model.setRowCount(0);
        hienThiDanhSachPhong();
        phongPanel.table.clearSelection();
	}
	
	public void themPhong() {
		if(kiemTraDuLieu()==false)return;
		String tang=String.format("%02d",Integer.parseInt(phongPanel.txt_Tang.getText()));
        String soPhong= String.format("%03d",Integer.parseInt(phongPanel.txt_SoPhong.getText()));
        String ma= "P"+tang+soPhong;
        String tenlp= phongPanel.cbb_LoaiPhong.getSelectedItem()+"";
        int sltd= Integer.parseInt(phongPanel.txt_SucChuaToiDa.getText());
        LoaiPhong loaiPhong = loaiPhongDao.getThongTinLoaiPhong(tenlp);
        // giá , tiền cọc get và tính theo loại phòng
        Phong p = new Phong(ma,loaiPhong,Integer.parseInt(phongPanel.txt_Tang.getText()),Integer.parseInt(phongPanel.txt_SoPhong.getText()),sltd,TrangThaiPhong.Trong);
                if(phongDao.themPhong(p)){
            phongPanel.model.addRow(new Object[] {ma,tenlp,tang,soPhong,sltd,p.getGiaPhong(),p.getTienCoc(),TrangThaiPhong.Trong.getMoTa()});
            baoLoi("Thêm phòng thành công!");
            lamMoi();
        }

        
	}

    public void hienThiLoaiPhongLenCombobox(){
        ArrayList<LoaiPhong> dsLoaiPhong=loaiPhongDao.getDanhSachLoaiPhong();
        for(LoaiPhong lp:dsLoaiPhong){
            phongPanel.cbb_LoaiPhong.addItem(lp.getTenLoaiPhong());
        }
    }
	
	public boolean kiemTraDuLieu() {
		String soPhong= phongPanel.txt_SoPhong.getText().trim();
        String sLTD= phongPanel.txt_SucChuaToiDa.getText().trim();
        String tang= phongPanel.txt_Tang.getText().trim();
        if(!soPhong.matches("\\d{1,3}") || Integer.parseInt(soPhong) <= 0){
            baoLoi("Số phòng phải là số nguyên > 0 và tối đa 3 chữ số.");
            phongPanel.txt_SoPhong.requestFocus();
            return false;
        }
        String tenLoaiPhong=phongPanel.cbb_LoaiPhong.getSelectedItem()+"";
        LoaiPhong loaiPhong=loaiPhongDao.getThongTinLoaiPhong(tenLoaiPhong);
        int sucChuaToiThieu= loaiPhong.getSucChuaToiThieu();
        int sucChuaToiDa=0;
        try {
            sucChuaToiDa = Integer.parseInt(phongPanel.txt_SucChuaToiDa.getText());
            if(sucChuaToiDa<sucChuaToiThieu){
                baoLoi("Sức chứa tối đa phải là số nguyên và lớn hơn hoặc bằng " + sucChuaToiThieu + " (loại phòng: "
                        + loaiPhong.getTenLoaiPhong() + ")");
                phongPanel.txt_SucChuaToiDa.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            baoLoi("Sức chứa tối đa phải là số nguyên và lớn hơn hoặc bằng " + sucChuaToiThieu + " (loại phòng: "
                    + loaiPhong.getTenLoaiPhong() + ")");
            phongPanel.txt_SucChuaToiDa.requestFocus();
            return false;
        }
        if(!tang.matches("\\d{1,2}")||Integer.parseInt(tang)<=0){
            baoLoi("Tầng phải là số nguyên > 0 và tối đa 2 chữ số.");
            phongPanel.txt_Tang.requestFocus();
            return false;
        }
        return true;
	}
	public void baoLoi(String l) {
		JOptionPane.showMessageDialog(phongPanel, l);
	}

	public TrangThaiPhong doiTuMoTaSangEnum(String moTa) {
		for (TrangThaiPhong ttp: TrangThaiPhong.values()) {
			if(ttp.getMoTa().equalsIgnoreCase(moTa)) {
				return ttp;
			}
		}
		return null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row= phongPanel.table.getSelectedRow();
		String ma=phongPanel.table.getValueAt(row,0)+"";
		String tenlp= phongPanel.table.getValueAt(row, 1)+"";
		int tang=Integer.parseInt(phongPanel.table.getValueAt(row, 2)+"");
        int soPhong= Integer.parseInt(phongPanel.table.getValueAt(row, 3)+"");
        int sltd= Integer.parseInt(phongPanel.table.getValueAt(row, 4)+"");
		double giaPhong = Double.parseDouble(phongPanel.table.getValueAt(row, 5)+"");
		double tienCoc= Double.parseDouble(phongPanel.table.getValueAt(row, 6)+"");
		TrangThaiPhong trangThaiPhong= doiTuMoTaSangEnum(phongPanel.table.getValueAt(row, 7)+"");
		
		Phong p= new Phong(ma,new LoaiPhong(tenlp),tang,soPhong,sltd,giaPhong,tienCoc,trangThaiPhong);
        PhongDialog dialog = new PhongDialog(phongPanel,p);
        // setModal để dừng, ko chạy code phía dưới, ngăn người dùng tác động đến các màn hình khác ngoài dialog
        dialog.setModal(true);
        dialog.setVisible(true);

        hienThiDanhSachPhong();
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
