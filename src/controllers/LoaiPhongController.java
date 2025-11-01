package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import entitys.LoaiPhong;
import view.dialogs.LoaiPhongDialog;
import view.panels.LoaiPhongPanel;

public class LoaiPhongController implements MouseListener{
	private LoaiPhongPanel loaiPhongPanel;

	public LoaiPhongController(LoaiPhongPanel loaiPhongPanel) {
		
		this.loaiPhongPanel = loaiPhongPanel;
		loaiPhongPanel.btn_LamMoi.addActionListener(e->{
			lamMoi();
		});
		loaiPhongPanel.btn_Them.addActionListener(e->{
			themLoaiPhong();
		});
        loaiPhongPanel.table.addMouseListener(this);
	}

	public void lamMoi() {
		loaiPhongPanel.txt_TenLoaiPhong.setText("");
		loaiPhongPanel.txt_GiaNiemYet.setText("");
		loaiPhongPanel.txt_SoNguoiMacDinh.setText("");
		loaiPhongPanel.txt_TyLeCoc.setText("");
		
		loaiPhongPanel.table.clearSelection();
		// load lại table 
		
	}
	
	public void themLoaiPhong() {
		if(kiemTraDuLieu()==false) return;
		String ma = String.format("LP%03d",loaiPhongPanel.model.getRowCount()+1);
		String tenLP= loaiPhongPanel.txt_TenLoaiPhong.getText();
        double giaNY= Double.parseDouble(loaiPhongPanel.txt_GiaNiemYet.getText().trim());
        double tyLC= Double.parseDouble(loaiPhongPanel.txt_TyLeCoc.getText().trim());
        int soNguoi= Integer.parseInt(loaiPhongPanel.txt_SoNguoiMacDinh.getText().trim());
        loaiPhongPanel.model.addRow(new Object[] {ma,tenLP,giaNY,tyLC+"%",soNguoi});
        baoLoi("Thêm loại phòng thành công!");
        lamMoi();
        
	}
	
	
	public boolean kiemTraDuLieu() {
		String tenLP= loaiPhongPanel.txt_TenLoaiPhong.getText();
        String giaNY= loaiPhongPanel.txt_GiaNiemYet.getText().trim();
        String tyLC= loaiPhongPanel.txt_TyLeCoc.getText().trim();
        String soNguoi= loaiPhongPanel.txt_SoNguoiMacDinh.getText().trim();
        
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
            baoLoi("Tỷ lệ cọc phải là số hợp lệ.");
            loaiPhongPanel.txt_TyLeCoc.requestFocus();
            return false;
        }
        int soNguoiInt;
        try {
            soNguoiInt = Integer.parseInt(soNguoi);
            if (soNguoiInt <1) {
                baoLoi("Số người mặc định phải lớn hơn hoặc bằng 1.");
                loaiPhongPanel.txt_SoNguoiMacDinh.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            baoLoi("Số người mặc định phải là số hợp lệ.");
            loaiPhongPanel.txt_SoNguoiMacDinh.requestFocus();
            return false;
        }

        return true;	}
	
	public void baoLoi(String l) {
		JOptionPane.showMessageDialog(loaiPhongPanel, l);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int r= loaiPhongPanel.table.getSelectedRow();
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
        loaiPhongDialog.setVisible(true);
		
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
