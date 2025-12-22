/*
 * @ (#) PhieuThongTinPhongController.java     1.0     12/19/2025
 *
 *Copyright (c) 2025 IUH. All rights reserved.
 */
package controllers.dialogs;


import database.dao.HoaDonDao;
import entitys.*;
import services.KhachHangService;
import services.PhongService;
import view.dialogs.PhieuDoiPhongDialog;
import view.dialogs.PhieuThongTinPhongThueDialog;
import view.dialogs.PhieuTraPhongDialog;

import javax.swing.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/*
 * @description: This class represents a bank with many bank accounts
 * @author: Anh, Le The Anh
 * @date: 12/19/2025
 * @version: 1.0
 */
public class PhieuThongTinPhongThueController {
    PhieuThongTinPhongThueDialog phieuThongTinPhongThueDialog;
    Phong phong;
    HoaDonDao hoaDonDao;
    KhachHangService khachHangService;
    PhongService phongService;
    DecimalFormat df=new DecimalFormat("0");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDate ngayKetThucGoc;
    ThueDatPhongController thueDatPhongController;

    public PhieuThongTinPhongThueController(PhieuThongTinPhongThueDialog phieuThongTinPhongThueDialog, Phong phong, ThueDatPhongController thueDatPhongController){
        this.thueDatPhongController=thueDatPhongController;
        this.phieuThongTinPhongThueDialog = phieuThongTinPhongThueDialog;
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
        phieuThongTinPhongThueDialog.txt_HoTen.setText(kh.getTenKH());
        phieuThongTinPhongThueDialog.txt_SDT.setText(kh.getSdt());
        phieuThongTinPhongThueDialog.txt_CCCD.setText(kh.getSoCCCD());

        ArrayList<ChiTietHoaDon> dsCTHD=hoaDon.getcTHD();
        if (dsCTHD==null||dsCTHD.isEmpty()){
            baoLoi("Lỗi lấy chi tiết hóa đơn");return;
        }

        ChiTietHoaDon cthdPhongDangXem = null;
        phieuThongTinPhongThueDialog.modelPhongHienTai.setRowCount(0);
        for (ChiTietHoaDon ct : dsCTHD) {
            if(ct.getPhong().getMaPhong().equalsIgnoreCase(phong.getMaPhong())){
                cthdPhongDangXem = ct;
                Phong p = phongService.timPhongBangMa(ct.getPhong().getMaPhong());
                if (p == null) continue;
                phieuThongTinPhongThueDialog.modelPhongHienTai .addRow(new Object[]{
                        p.getMaPhong(),p.getLoaiPhong().getTenLoaiPhong(),p.getSucChuaToiDa(),df.format(p.getGiaPhong()),df.format(p.getTienCoc())
                });
                break;
            }
        }


        ArrayList<NguoiO> dsNguoiO= hoaDonDao.getNguoiOTheoPhongVaHoaDon(phong.getMaPhong(),hoaDon.getMaHD());
        for (NguoiO nguoiO : dsNguoiO) {
            phieuThongTinPhongThueDialog.model_NguoiO.addRow(new Object[]{nguoiO.getHoTen(),dtf.format(nguoiO.getNgaySinh()),
                    nguoiO.isGioiTinh()?"Nam":"Nữ",nguoiO.getSDT(),nguoiO.getCCCD()});
        }

        // Hiênr thị ngày
        if (cthdPhongDangXem == null) {
            baoLoi("Không tìm thấy chi tiết hóa đơn của phòng");
            return;
        }
        phieuThongTinPhongThueDialog.ngayBatDau.setDate(Date.from(cthdPhongDangXem.getNgayNhanPhong().atZone(java.time.ZoneId.systemDefault()).toInstant()));
        phieuThongTinPhongThueDialog.ngayKetThuc.setDate(Date.from(cthdPhongDangXem.getNgayTraPhong().atZone(java.time.ZoneId.systemDefault()).toInstant()));
        // Sự kiện thêm người ở
        phieuThongTinPhongThueDialog.btn_Them.addActionListener(e->{
            System.out.println("thêm");;themNguoiO(hoaDon,phong);});

        // Kiểm tra ngày thay đổi
        ngayKetThucGoc=cthdPhongDangXem.getNgayTraPhong().toLocalDate();
        phieuThongTinPhongThueDialog.ngayKetThuc.addPropertyChangeListener("date", evt -> {
            Date dNgayBatDau=phieuThongTinPhongThueDialog.ngayBatDau.getDate();
            Date dNgayKetThuc=phieuThongTinPhongThueDialog.ngayKetThuc.getDate();

            if (dNgayBatDau== null||dNgayKetThuc== null) return;

            LocalDate ngayBatDau = dNgayBatDau.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate ngayKetThucMoi = dNgayKetThuc.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (!ngayKetThucMoi.isAfter(ngayBatDau)) {
                baoLoi("Ngày kết thúc phải sau ngày bắt đầu");
                phieuThongTinPhongThueDialog.ngayKetThuc.setDate(
                        Date.from(ngayKetThucGoc.atStartOfDay(ZoneId.systemDefault()).toInstant())
                );
            }
        });


        // sự kiện btn đổi phòng
        phieuThongTinPhongThueDialog.btnDoiPhong.addActionListener(ev->{
            PhieuDoiPhongDialog phieuDoiPhongDialog= new PhieuDoiPhongDialog();
            PhieuDoiPhongThueController phieuDoiPhongThueController = new PhieuDoiPhongThueController(phieuDoiPhongDialog,phong);
            phieuDoiPhongDialog.setLocationRelativeTo(null);
            phieuDoiPhongDialog.setVisible(true);
            phieuThongTinPhongThueDialog.dispose();
        });
        // sự kiện btn trả phòng
        phieuThongTinPhongThueDialog.btnTraPhong.addActionListener(ev->{
            ArrayList<String> dsMaPhong = new ArrayList<>();
            for (ChiTietHoaDon cthd : dsCTHD) {
                dsMaPhong.add(cthd.getPhong().getMaPhong());
            }

            if (dsMaPhong.size() == 1) {
                String maPhongCanTra = dsMaPhong.get(0);
                PhieuTraPhongDialog phieuTraPhongDialog= new PhieuTraPhongDialog();
                PhieuTraPhongController phieuTraPhongController= new PhieuTraPhongController(phieuTraPhongDialog,phong,thueDatPhongController);
                phieuTraPhongController.traToanBoCacPhong();
                phieuTraPhongDialog.setLocationRelativeTo(null);
                phieuTraPhongDialog.setVisible(true);
                phieuThongTinPhongThueDialog.dispose();
            }
            else{
                String maPhong="";
                for (String mp:dsMaPhong) {
                    maPhong+=mp+", ";
                }
                if (!maPhong.isEmpty()) {
                    maPhong = maPhong.substring(0, maPhong.length() - 2);
                }
                String[] options = {
                        "Chỉ trả phòng này",
                        "Trả tất cả phòng trong hóa đơn ("+maPhong+")",
                        "Hủy"
                };
                int choice = JOptionPane.showOptionDialog(
                        phieuThongTinPhongThueDialog,
                        "Bạn muốn trả phòng hiện tại hay trả tất cả các phòng trong hóa đơn?",
                        "Xác nhận trả phòng",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );
                if (choice == 0) {
                    //  TRẢ 1 PHÒNG
                    PhieuTraPhongDialog phieuTraPhongDialog= new PhieuTraPhongDialog();
                    PhieuTraPhongController phieuTraPhongController= new PhieuTraPhongController(phieuTraPhongDialog,phong,thueDatPhongController);
                    phieuTraPhongController.traMotPhong();
                    phieuTraPhongDialog.setLocationRelativeTo(null);
                    phieuTraPhongDialog.setVisible(true);
                    phieuThongTinPhongThueDialog.dispose();

                } else if (choice == 1) {
                    PhieuTraPhongDialog phieuTraPhongDialog= new PhieuTraPhongDialog();
                    PhieuTraPhongController phieuTraPhongController= new PhieuTraPhongController(phieuTraPhongDialog,phong,thueDatPhongController);
                    phieuTraPhongController.traToanBoCacPhong();
                    phieuTraPhongDialog.setLocationRelativeTo(null);
                    phieuTraPhongDialog.setVisible(true);
                    phieuThongTinPhongThueDialog.dispose();

                } else {
                    return;
                }
            }

        });
    }
    public void themNguoiO(HoaDon hd, Phong p){
        // Ràng buộc
        // Kiểm tra số lượng người ở hiện tại KHÔNG được vượt quá sức chứa tối đa của phòng
        int soNguoiHienTai = phieuThongTinPhongThueDialog.model_NguoiO.getRowCount();
        int sucChuaToiDa = p.getSucChuaToiDa();

        if (soNguoiHienTai >= sucChuaToiDa) {
            JOptionPane.showMessageDialog(phieuThongTinPhongThueDialog,
                    "Số lượng người ở đã đạt tối đa cho phòng " + p.getMaPhong() +
                            "\nSức chứa tối đa: " + sucChuaToiDa + " người",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ten = phieuThongTinPhongThueDialog.txt_HoTenNguoiO.getText().trim();
        String sdt = phieuThongTinPhongThueDialog.txt_SDTNguoiO.getText().trim();
        String cccd = phieuThongTinPhongThueDialog.txt_CCCDNguoiO.getText().trim();
        java.util.Date ngaySinhDate = phieuThongTinPhongThueDialog.ngaySinh.getDate();
        LocalDate ngaySinh = null;
        if (ngaySinhDate != null) {
            ngaySinh = ngaySinhDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        String gioiTinh = phieuThongTinPhongThueDialog.rdbtn_Nam.isSelected() ? "Nam" : "Nữ";

        if (ten.isEmpty() || sdt.isEmpty() || cccd.isEmpty() || ngaySinh == null) {
            JOptionPane.showMessageDialog(phieuThongTinPhongThueDialog, "Vui lòng điền đầy đủ thông tin người ở!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Thêm vào dbs và bảng
        NguoiO nguoiOMoi=new NguoiO(ten,ngaySinh,sdt,cccd,phieuThongTinPhongThueDialog.rdbtn_Nam.isSelected());
        hoaDonDao.themNguoiO(nguoiOMoi,hd.getMaHD(),p.getMaPhong());
        phieuThongTinPhongThueDialog.model_NguoiO.addRow(new Object[]{ten,dtf.format(ngaySinh),gioiTinh,sdt,cccd});

        // làm mới
        phieuThongTinPhongThueDialog.txt_HoTenNguoiO.setText("");
        phieuThongTinPhongThueDialog.txt_SDTNguoiO.setText("");
        phieuThongTinPhongThueDialog.txt_CCCDNguoiO.setText("");
        phieuThongTinPhongThueDialog.ngaySinh.setDate(null);
        phieuThongTinPhongThueDialog.rdbtn_Nam.setSelected(true);
    }
    public void baoLoi(String s) {
        JOptionPane.showMessageDialog(null, s);
    }
}
