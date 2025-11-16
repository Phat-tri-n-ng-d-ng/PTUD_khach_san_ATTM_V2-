package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import entitys.LoaiPhong;
import services.LoaiPhongService;
import view.dialogs.LoaiPhongDialog;
import view.panels.LoaiPhongPanel;

public class LoaiPhongController implements MouseListener{
	private LoaiPhongPanel loaiPhongPanel;
    private LoaiPhongService loaiPhongService;

	public LoaiPhongController(LoaiPhongPanel loaiPhongPanel) {
		this.loaiPhongPanel = loaiPhongPanel;
        loaiPhongService= new LoaiPhongService();
		loaiPhongPanel.btn_LamMoi.addActionListener(e->{
			lamMoi();
		});
		loaiPhongPanel.btn_Them.addActionListener(e->{
			themLoaiPhong();
		});
        loaiPhongPanel.table.addMouseListener(this);
	}

    public void hienThiDanhSachLoaiPhong(){
        ArrayList<LoaiPhong> dslp= loaiPhongService.getDanhSachLoaiPhong();
        DefaultTableModel model = loaiPhongPanel.model;
        model.setRowCount(0);
        for (LoaiPhong lp : dslp){
            model.addRow(new Object[]{lp.getMaLoaiPhong(),lp.getTenLoaiPhong(),lp.getGiaNiemYet(),lp.getTyLeCoc()*100+"%",lp.getSucChuaToiThieu()});
        }
    }

	public void lamMoi() {
		loaiPhongPanel.txt_TenLoaiPhong.setText("");
		loaiPhongPanel.txt_GiaNiemYet.setText("");
		loaiPhongPanel.txt_SucChuaToiThieu.setText("");
		loaiPhongPanel.txt_TyLeCoc.setText("");
		loaiPhongPanel.model.setRowCount(0);
        hienThiDanhSachLoaiPhong();
        loaiPhongPanel.table.clearSelection();
	}
	
	public void themLoaiPhong() {
		if(kiemTraDuLieu()==false) return;
		String ma = String.format("LP%03d",loaiPhongPanel.model.getRowCount()+1);
		String tenLP= loaiPhongPanel.txt_TenLoaiPhong.getText();
        double giaNY= Double.parseDouble(loaiPhongPanel.txt_GiaNiemYet.getText().trim());
        double tyLC= Double.parseDouble(loaiPhongPanel.txt_TyLeCoc.getText().trim());
        int soNguoi= Integer.parseInt(loaiPhongPanel.txt_SucChuaToiThieu.getText().trim());
        LoaiPhong loaiPhong = new LoaiPhong(ma,tenLP,giaNY,tyLC/100,soNguoi);
        if(loaiPhongService.themLoaiPhong(loaiPhong)){
            loaiPhongPanel.model.addRow(new Object[] {ma,tenLP,giaNY,tyLC+"%",soNguoi});
            baoLoi("Thêm loại phòng thành công!");
            lamMoi();
        }else{
            baoLoi("Lỗi thêm loại phòng!");
        }
	}
	
	
	public boolean kiemTraDuLieu() {
		String tenLP= loaiPhongPanel.txt_TenLoaiPhong.getText();
        String giaNY= loaiPhongPanel.txt_GiaNiemYet.getText().trim();
        String tyLC= loaiPhongPanel.txt_TyLeCoc.getText().trim();
        String soNguoi= loaiPhongPanel.txt_SucChuaToiThieu.getText().trim();
        
        if (tenLP.isEmpty() || !tenLP.matches("^[\\p{L}0-9 ]{1,50}$")) {
            baoLoi("Tên loại phòng không được rỗng, tối đa 50 ký tự và không chứa ký tự đặc biệt.");
            loaiPhongPanel.txt_TenLoaiPhong.requestFocus();
            return false;
        }
        double gia;
        try {
            gia = Double.parseDouble(giaNY);
            if (gia < 100000) {
                baoLoi("Giá niêm yết phải >= 100.000");
                loaiPhongPanel.txt_GiaNiemYet.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            baoLoi("Giá niêm yết phải là số và >= 100.000");
            loaiPhongPanel.txt_GiaNiemYet.requestFocus();
            return false;
        }
        double tyLe;
        try {
            tyLe = Double.parseDouble(tyLC);
            if (tyLe < 10 || tyLe > 50) {
                baoLoi("Tỷ lệ cọc phải nằm trong khoảng 10% - 50%.");
                loaiPhongPanel.txt_TyLeCoc.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            baoLoi("Tỷ lệ cọc phải là số hợp lệ và nằm trong khoảng 10% - 50%.");
            loaiPhongPanel.txt_TyLeCoc.requestFocus();
            return false;
        }
        int soNguoiInt;
        try {
            soNguoiInt = Integer.parseInt(soNguoi);
            if (soNguoiInt <1) {
                baoLoi("Sức chứa tối thiểu phải là số nguyên và lớn hơn hoặc bằng 1.");
                loaiPhongPanel.txt_SucChuaToiThieu.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            baoLoi("Sức chứa tối thiểu phải là số nguyên và lớn hơn hoặc bằng 1.");
            loaiPhongPanel.txt_SucChuaToiThieu.requestFocus();
            return false;
        }

        return true;	}
	
	public void baoLoi(String l) {
		JOptionPane.showMessageDialog(loaiPhongPanel, l);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		int r= loaiPhongPanel.table.getSelectedRow();
        if(r<0) return;
		String ma = loaiPhongPanel.table.getValueAt(r,0)+"";
		String tenLP= loaiPhongPanel.table.getValueAt(r,1)+"";
        double giaNY= Double.parseDouble(loaiPhongPanel.table.getValueAt(r,2)+"");
        String tyLCString= loaiPhongPanel.table.getValueAt(r,3)+"";
        if (tyLCString.endsWith("%")) {
        	tyLCString = tyLCString.substring(0, tyLCString.length()-1);
       }
        double tyLC= Double.parseDouble(tyLCString);
        int soNguoi= Integer.parseInt(loaiPhongPanel.table.getValueAt(r,4)+"");
        LoaiPhong loaiPhong= new LoaiPhong(ma,tenLP,giaNY,tyLC,soNguoi);
        LoaiPhongDialog loaiPhongDialog= new LoaiPhongDialog(loaiPhongPanel,loaiPhong);
        // setModal để dừng, ko chạy code phía dưới, ngăn người dùng tác động đến các màn hình khác ngoài dialog
        loaiPhongDialog.setModal(true);
        loaiPhongDialog.setVisible(true);
        // Làm moi
        hienThiDanhSachLoaiPhong();
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
