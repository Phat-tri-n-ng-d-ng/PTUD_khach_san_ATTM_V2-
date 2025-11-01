package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import entitys.LoaiPhong;
import entitys.Phong;
import enums.TrangThaiPhong;
import view.dialogs.NhanVienDialog;
import view.dialogs.PhongDialog;
import view.panels.PhongPanel;

public class PhongController implements MouseListener{
	PhongPanel phongPanel;

	public PhongController(PhongPanel phongPanel) {
		this.phongPanel = phongPanel;
		
		phongPanel.btn_LamMoi.addActionListener(e->{
			lamMoi();
		});
		phongPanel.btn_ThemPhong.addActionListener(e->{
			themPhong();
		});
		
		phongPanel.table.addMouseListener(this);
		
	}
	
	
	
	public void lamMoi() {
		phongPanel.txt_SoLuongToiDa.setText("");
		phongPanel.txt_SoPhong.setText("");
		phongPanel.txt_Tang.setText("");
		phongPanel.txt_TimMaPhong.setText("");
		phongPanel.cbb_LoaiPhong.setSelectedIndex(0);
		phongPanel.chckbx_Deluxe.setSelected(false);
		phongPanel.chckbx_FamilyRoom.setSelected(false);
		phongPanel.chckbx_Standard.setSelected(false);
		phongPanel.chckbx_Suite.setSelected(false);
		phongPanel.chckbx_Superior.setSelected(false);
		phongPanel.table.clearSelection();
		// load lại bảng
	}
	
	public void themPhong() {
		if(kiemTraDuLieu()==false)return;
		
		String tang=String.format("%02d",Integer.parseInt(phongPanel.txt_Tang.getText()));
        String soPhong= String.format("%03d",Integer.parseInt(phongPanel.txt_SoPhong.getText()));
        String ma= "P"+tang+soPhong;
        String tenlp= phongPanel.cbb_LoaiPhong.getSelectedItem()+"";
        int sltd= Integer.parseInt(phongPanel.txt_SoLuongToiDa.getText());

        // giá , tiền cọc get và tính theo loại phòng
        phongPanel.model.addRow(new Object[] {ma,tenlp,tang,soPhong,sltd,1000000,300000,TrangThaiPhong.Trong.getMoTa()});
        baoLoi("Thêm phòng thành công!");
        lamMoi();
        
	}
	
	
	public boolean kiemTraDuLieu() {
		String soPhong= phongPanel.txt_SoPhong.getText().trim();
        String sLTD= phongPanel.txt_SoLuongToiDa.getText().trim();
        String tang= phongPanel.txt_Tang.getText().trim();
        if(!soPhong.matches("\\d{1,3}") || Integer.parseInt(soPhong) <= 0){
            baoLoi("Số phòng phải là số nguyên > 0 và tối đa 3 chữ số.");
            phongPanel.txt_SoPhong.requestFocus();
            return false;
        }
        if(!sLTD.matches("\\d+")||Integer.parseInt(sLTD)<1){
            baoLoi("Số lượng tối đa phải là số nguyên > 0.");
            phongPanel.txt_SoLuongToiDa.requestFocus();
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
        dialog.setVisible(true);

        // Sự kiện cho nút cập nhật
//        dialog.btn_CapNhat.addActionListener(event->{
//
//
//
//        });

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
