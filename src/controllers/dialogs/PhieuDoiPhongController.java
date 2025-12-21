/*
 * @ (#) PhieuDoiPhongController.java     1.0     12/17/2025
 *
 *Copyright (c) 2025 IUH. All rights reserved.
 */
package controllers.dialogs;


import database.dao.HoaDonDao;
import entitys.ChiTietHoaDon;
import entitys.HoaDon;
import entitys.KhachHang;
import entitys.Phong;
import enums.TrangThaiPhong;
import services.KhachHangService;
import services.PhongService;
import view.dialogs.PhieuDatPhongDialog;
import view.dialogs.PhieuDoiPhongDialog;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 * @description: This class represents a bank with many bank accounts
 * @author: Anh, Le The Anh
 * @date: 12/17/2025
 * @version: 1.0
 */
public class PhieuDoiPhongController implements MouseListener {
    PhieuDoiPhongDialog phieuDoiPhongDialog;
    Phong phong;
    HoaDonDao hoaDonDao;
    KhachHangService khachHangService;
    PhongService phongService;
    DecimalFormat df=new DecimalFormat("0");

    public PhieuDoiPhongController(PhieuDoiPhongDialog phieu, Phong phong) {
        this.phieuDoiPhongDialog = phieu;
        this.phong = phong;
        hoaDonDao = new HoaDonDao();
        khachHangService = new KhachHangService();
        phongService = new PhongService();

        HoaDon hoaDon = hoaDonDao.timHoaDonTheoPhongDangThue(phong.getMaPhong());
        if (hoaDon == null) {
            baoLoi("Không tìm thấy hóa đơn");
            return;
        }
        KhachHang kh = khachHangService.TimKhachHang(hoaDon.getKhachHang().getSdt(), "SDT");
        if (kh == null) {
            baoLoi("Không tìm thấy khách hàng");
            return;
        }
        // Hiển thị KH
        phieuDoiPhongDialog.txt_HoTen.setText(kh.getTenKH());
        phieuDoiPhongDialog.txt_SDT.setText(kh.getSdt());
        phieuDoiPhongDialog.txt_CCCD.setText(kh.getSoCCCD());

        ArrayList<ChiTietHoaDon> dsCTHD=hoaDon.getcTHD();
        if (dsCTHD==null||dsCTHD.isEmpty()){
            baoLoi("Lỗi lấy chi tiết hóa đơn");return;
        }
        // Hiển thị phòng hiện taij
        phieuDoiPhongDialog.modelPhongHienTai.setRowCount(0);
        ChiTietHoaDon cthdPhongCu = null;
        for (ChiTietHoaDon ct : dsCTHD) {
            if(ct.getPhong().getMaPhong().equalsIgnoreCase(phong.getMaPhong())){
                cthdPhongCu = ct;
                Phong p = phongService.timPhongBangMa(ct.getPhong().getMaPhong());
                if (p == null) continue;
                phieuDoiPhongDialog.modelPhongHienTai .addRow(new Object[]{
                        p.getMaPhong(),p.getLoaiPhong().getTenLoaiPhong(),p.getSucChuaToiDa(),df.format(p.getGiaPhong()),df.format(p.getTienCoc())
                }); break;
            }
        }
        if (cthdPhongCu == null) {
            baoLoi("Không tìm thấy chi tiết hóa đơn của phòng");
            return;
        }

       // Hiển thị danh sách phòng trống
        ArrayList<Phong> dsPhong= phongService.getDanhSachPhong();
        for (Phong p : dsPhong) {
//            int ngay=phieuDoiPhongDialog.ngayKetThuc-phieuDoiPhongDialog.ngayBatDau;
            if(p.getTrangThai().equals(TrangThaiPhong.Trong))
            phieuDoiPhongDialog.modelPhongDoi.addRow(new Object[]{p.getMaPhong(),p.getLoaiPhong().getTenLoaiPhong(),p.getSucChuaToiDa(),df.format(p.getGiaPhong()),df.format(p.getTienCoc())});
        }
        // locj danh sách phòng theo loại
        phieuDoiPhongDialog.chckbx_Superior.addActionListener(e->{hienThiDanhSachPhongTheoLoaiPhong();});
        phieuDoiPhongDialog.chckbx_Suite.addActionListener(e->{hienThiDanhSachPhongTheoLoaiPhong();});
        phieuDoiPhongDialog.chckbx_Standard.addActionListener(e->{hienThiDanhSachPhongTheoLoaiPhong();});
        phieuDoiPhongDialog.chckbx_Deluxe.addActionListener(e->{hienThiDanhSachPhongTheoLoaiPhong();});
        phieuDoiPhongDialog.chckbx_FamilyRoom.addActionListener(e->{hienThiDanhSachPhongTheoLoaiPhong();});

        phieuDoiPhongDialog.tablePhongDoi.addMouseListener(this);

        //
        phieuDoiPhongDialog.rdbtn_KhachHangYeuCau.addActionListener(e->{
            capNhatTienKhiChonLyDoDoi();
        });
        phieuDoiPhongDialog.rdbtn_KhachSanSapXep.addActionListener(e->{
            capNhatTienKhiChonLyDoDoi();
        });


        //  Nút hủy
        phieuDoiPhongDialog.btnHuy.addActionListener(e->{
            if(JOptionPane.showConfirmDialog(
                    phieuDoiPhongDialog,"Bạn có chắc chắn hủy đổi phòng?","Nhắc nhở",
                    JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                phieuDoiPhongDialog.dispose();
            }
        });

        // nút xác nhận
        phieuDoiPhongDialog.btnXacNhan.addActionListener(e->{
            if(phieuDoiPhongDialog.tablePhongDoi.getSelectedRow()<0){
                baoLoi("Hãy chọn phòng đổi đến!");return;
            }
            if(!phieuDoiPhongDialog.rdbtn_KhachSanSapXep.isSelected()&&!phieuDoiPhongDialog.rdbtn_KhachHangYeuCau.isSelected()){
                baoLoi("Hãy chọn lý do đổi phòng");return;
            }

        });

    }

    public void capNhatTienKhiChonLyDoDoi(){
        String giaChenhLechCoDuoiVND = phieuDoiPhongDialog.lbl_TienCuaGiaChenhLech.getText();
        double giaChenhLech = Double.parseDouble(giaChenhLechCoDuoiVND.replace("VND","").trim());
        double phiDoiPhong;
        phiDoiPhong =phieuDoiPhongDialog.rdbtn_KhachHangYeuCau.isSelected()?50000:0;
        phieuDoiPhongDialog.lbl_TienCuaPhiDoiPhongTrongPnlTongTien.setText(df.format(phiDoiPhong)+" VND");
        double thanhTien=giaChenhLech+phiDoiPhong;
        phieuDoiPhongDialog.lbl_TienCuaThanhTien.setText(df.format(thanhTien)+" VND");
    }
    public void hienThiDanhSachPhongTheoLoaiPhong(){
        String chuoiLoaiPhongDaChon="";
        if(phieuDoiPhongDialog.chckbx_Superior.isSelected()) chuoiLoaiPhongDaChon+="'"+phieuDoiPhongDialog.chckbx_Superior.getText()+"',";
        if(phieuDoiPhongDialog.chckbx_Suite.isSelected()) chuoiLoaiPhongDaChon+="'"+phieuDoiPhongDialog.chckbx_Suite.getText()+"',";
        if(phieuDoiPhongDialog.chckbx_Standard.isSelected()) chuoiLoaiPhongDaChon+="'"+phieuDoiPhongDialog.chckbx_Standard.getText()+"',";
        if(phieuDoiPhongDialog.chckbx_Deluxe.isSelected()) chuoiLoaiPhongDaChon+="'"+phieuDoiPhongDialog.chckbx_Deluxe.getText()+"',";
        if(phieuDoiPhongDialog.chckbx_FamilyRoom.isSelected()) chuoiLoaiPhongDaChon+="'"+phieuDoiPhongDialog.chckbx_FamilyRoom.getText()+"',";

        // nếu chuỗi ko rỗng thì nó sẽ có dấu "," ở cuối nên bỏ đi
        ArrayList<Phong> danhSachPhong= new ArrayList<>();
        if(!chuoiLoaiPhongDaChon.isEmpty()){
            chuoiLoaiPhongDaChon=chuoiLoaiPhongDaChon.substring(0,chuoiLoaiPhongDaChon.length()-1);
            danhSachPhong=phongService.locPhongTheoLoai(chuoiLoaiPhongDaChon);
        }
        if(chuoiLoaiPhongDaChon.isEmpty()){
            danhSachPhong=phongService.getDanhSachPhong();
        }
        phieuDoiPhongDialog.modelPhongDoi.setRowCount(0);
        for (Phong p : danhSachPhong) {
            String tang=String.format("%02d",Integer.parseInt(p.getTang()+""));
            String soPhong= String.format("%03d",Integer.parseInt(p.getSoPhong()+""));
            if(p.getTrangThai().equals(TrangThaiPhong.Trong))
            phieuDoiPhongDialog.modelPhongDoi.addRow(new Object[]{p.getMaPhong(),p.getLoaiPhong().getTenLoaiPhong(),p.getSucChuaToiDa(),p.getGiaPhong(),p.getTienCoc()});
        }

    }
    public void baoLoi(String s) {
        JOptionPane.showMessageDialog(null, s);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double giaPhongCu= Double.parseDouble(phieuDoiPhongDialog.tablePhongHienTai.getValueAt(0,3)+"");
        int r=phieuDoiPhongDialog.tablePhongDoi.getSelectedRow();
        double giaPhongMoi= Double.parseDouble(phieuDoiPhongDialog.tablePhongDoi.getValueAt(r,3)+"");
        double giaChenhLech= giaPhongMoi-giaPhongCu;

        phieuDoiPhongDialog.lbl_TienCuaGiaPhongCu.setText(df.format(giaPhongCu)+ " VND");
        phieuDoiPhongDialog.lbl_TienCuaGiaPhongMoi.setText(df.format(giaPhongMoi)+ " VND");
        phieuDoiPhongDialog.lbl_TienCuaGiaChenhLech.setText(df.format(giaChenhLech)+ " VND");
        double phiDoiPhong;
        phiDoiPhong =phieuDoiPhongDialog.rdbtn_KhachHangYeuCau.isSelected()?50000:0;
        phieuDoiPhongDialog.lbl_TienCuaPhiDoiPhongTrongPnlTongTien.setText(df.format(phiDoiPhong)+" VND");
        double thanhTien=giaChenhLech+phiDoiPhong;
        phieuDoiPhongDialog.lbl_TienCuaThanhTien.setText(df.format(thanhTien)+" VND");
    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
