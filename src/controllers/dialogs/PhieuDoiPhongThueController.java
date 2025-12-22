/*
 * @ (#) PhieuDoiPhongThueController.java     1.0     12/17/2025
 *
 * Copyright (c) 2025 IUH. All rights reserved.
 */
package controllers.dialogs;

import database.dao.HoaDonDao;
import database.dao.PhongDao;
import entitys.ChiTietHoaDon;
import entitys.HoaDon;
import entitys.KhachHang;
import entitys.Phong;
import enums.TrangThaiPhong;
import services.KhachHangService;
import services.PhongService;
import view.dialogs.PhieuDoiPhongDialog;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class PhieuDoiPhongThueController implements MouseListener {

    PhieuDoiPhongDialog phieuDoiPhongDialog;
    Phong phong;
    HoaDon hoaDon;

    HoaDonDao hoaDonDao;
    PhongDao phongDao;
    KhachHangService khachHangService;
    PhongService phongService;

    DecimalFormat df = new DecimalFormat("0");

    public PhieuDoiPhongThueController(PhieuDoiPhongDialog phieu, Phong phong) {
        this.phieuDoiPhongDialog = phieu;
        this.phong = phong;

        hoaDonDao = new HoaDonDao();
        phongDao = new PhongDao();
        khachHangService = new KhachHangService();
        phongService = new PhongService();

        hoaDon = hoaDonDao.timHoaDonTheoPhongDangThue(phong.getMaPhong());
        if (hoaDon == null) {
            baoLoi("Không tìm thấy hóa đơn đang thuê");
            phieuDoiPhongDialog.dispose();
            return;
        }

        KhachHang kh = khachHangService.TimKhachHang(
                hoaDon.getKhachHang().getSdt(), "SDT"
        );
        if (kh == null) {
            baoLoi("Không tìm thấy khách hàng");
            phieuDoiPhongDialog.dispose();
            return;
        }
        phieuDoiPhongDialog.txt_HoTen.setText(kh.getTenKH());
        phieuDoiPhongDialog.txt_SDT.setText(kh.getSdt());
        phieuDoiPhongDialog.txt_CCCD.setText(kh.getSoCCCD());

        phieuDoiPhongDialog.modelPhongHienTai.setRowCount(0);
        ChiTietHoaDon cthdPhongCu = null;

        for (ChiTietHoaDon ct : hoaDon.getcTHD()) {
            if (ct.getPhong().getMaPhong().equalsIgnoreCase(phong.getMaPhong())) {
                cthdPhongCu = ct;
                Phong p = phongService.timPhongBangMa(ct.getPhong().getMaPhong());
                if (p != null) {
                    phieuDoiPhongDialog.modelPhongHienTai.addRow(new Object[]{
                            p.getMaPhong(),
                            p.getLoaiPhong().getTenLoaiPhong(),
                            p.getSucChuaToiDa(),
                            df.format(p.getGiaPhong()),
                            df.format(p.getTienCoc())
                    });
                }
                break;
            }
        }

        if (cthdPhongCu == null) {
            baoLoi("Không tìm thấy chi tiết phòng đang thuê");
            phieuDoiPhongDialog.dispose();
            return;
        }

        loadDanhSachPhongTrong();
        Date ngayNhan = Date.from(
                cthdPhongCu.getNgayNhanPhong()
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );

        Date ngayTra = Date.from(
                cthdPhongCu.getNgayTraPhong()
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );

        phieuDoiPhongDialog.ngayBatDau.setDate(ngayNhan);
        phieuDoiPhongDialog.ngayKetThuc.setDate(ngayTra);
        phieuDoiPhongDialog.tablePhongDoi.addMouseListener(this);

        phieuDoiPhongDialog.chckbx_Superior.addActionListener(e -> hienThiDanhSachPhongTheoLoaiPhong());
        phieuDoiPhongDialog.chckbx_Suite.addActionListener(e -> hienThiDanhSachPhongTheoLoaiPhong());
        phieuDoiPhongDialog.chckbx_Standard.addActionListener(e -> hienThiDanhSachPhongTheoLoaiPhong());
        phieuDoiPhongDialog.chckbx_Deluxe.addActionListener(e -> hienThiDanhSachPhongTheoLoaiPhong());
        phieuDoiPhongDialog.chckbx_FamilyRoom.addActionListener(e -> hienThiDanhSachPhongTheoLoaiPhong());

        phieuDoiPhongDialog.rdbtn_KhachHangYeuCau.addActionListener(e -> capNhatTienKhiChonLyDoDoi());
        phieuDoiPhongDialog.rdbtn_KhachSanSapXep.addActionListener(e -> capNhatTienKhiChonLyDoDoi());

        phieuDoiPhongDialog.btnHuy.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(
                    phieuDoiPhongDialog,
                    "Bạn có chắc chắn hủy đổi phòng?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            ) == JOptionPane.YES_OPTION) {
                phieuDoiPhongDialog.dispose();
            }
        });

        phieuDoiPhongDialog.btnXacNhan.addActionListener(e -> xuLyDoiPhong());
    }

    private void xuLyDoiPhong() {
        int row = phieuDoiPhongDialog.tablePhongDoi.getSelectedRow();
        if (row < 0) {
            baoLoi("Hãy chọn phòng đổi đến");
            return;
        }

        if (!phieuDoiPhongDialog.rdbtn_KhachHangYeuCau.isSelected()
                && !phieuDoiPhongDialog.rdbtn_KhachSanSapXep.isSelected()) {
            baoLoi("Hãy chọn lý do đổi phòng");
            return;
        }

        String maHD = hoaDon.getMaHD();
        String maPhongCu = phieuDoiPhongDialog.tablePhongHienTai.getValueAt(0, 0).toString();
        String maPhongMoi = phieuDoiPhongDialog.tablePhongDoi.getValueAt(row, 0).toString();
        double phiDoiPhong = phieuDoiPhongDialog.rdbtn_KhachHangYeuCau.isSelected() ? 50000 : 0;

        if (JOptionPane.showConfirmDialog(
                phieuDoiPhongDialog,
                "Xác nhận đổi từ phòng " + maPhongCu + " sang phòng " + maPhongMoi + "?",
                "Xác nhận đổi phòng",
                JOptionPane.YES_NO_OPTION
        ) != JOptionPane.YES_OPTION) {
            return;
        }

        boolean ketQua = phongDao.doiPhong(maHD, maPhongCu, maPhongMoi, phiDoiPhong);

        if (ketQua) {
            JOptionPane.showMessageDialog(phieuDoiPhongDialog, "Đổi phòng thành công!");
            phieuDoiPhongDialog.dispose();
        } else {
            JOptionPane.showMessageDialog(phieuDoiPhongDialog, "Đổi phòng không thành công!");
        }
    }
    public void capNhatTienKhiChonLyDoDoi() {
        String txt = phieuDoiPhongDialog.lbl_TienCuaGiaChenhLech.getText().replace("VND", "").trim();
        double giaChenhLech = Double.parseDouble(txt);
        double phi = phieuDoiPhongDialog.rdbtn_KhachHangYeuCau.isSelected() ? 50000 : 0;

        phieuDoiPhongDialog.lbl_TienCuaPhiDoiPhongTrongPnlTongTien.setText(df.format(phi) + " VND");
        phieuDoiPhongDialog.lbl_TienCuaThanhTien.setText(df.format(giaChenhLech + phi) + " VND");
    }

    private void loadDanhSachPhongTrong() {
        phieuDoiPhongDialog.modelPhongDoi.setRowCount(0);
        for (Phong p : phongService.getDanhSachPhong()) {
            if (p.getTrangThai() == TrangThaiPhong.Trong) {
                phieuDoiPhongDialog.modelPhongDoi.addRow(new Object[]{
                        p.getMaPhong(),
                        p.getLoaiPhong().getTenLoaiPhong(),
                        p.getSucChuaToiDa(),
                        df.format(p.getGiaPhong()),
                        df.format(p.getTienCoc())
                });
            }
        }
    }

    public void hienThiDanhSachPhongTheoLoaiPhong() {
        String loai = "";
        if (phieuDoiPhongDialog.chckbx_Superior.isSelected()) loai += "'Superior',";
        if (phieuDoiPhongDialog.chckbx_Suite.isSelected()) loai += "'Suite',";
        if (phieuDoiPhongDialog.chckbx_Standard.isSelected()) loai += "'Standard',";
        if (phieuDoiPhongDialog.chckbx_Deluxe.isSelected()) loai += "'Deluxe',";
        if (phieuDoiPhongDialog.chckbx_FamilyRoom.isSelected()) loai += "'FamilyRoom',";

        ArrayList<Phong> ds;
        if (!loai.isEmpty()) {
            loai = loai.substring(0, loai.length() - 1);
            ds = phongService.locPhongTheoLoai(loai);
        } else {
            ds = phongService.getDanhSachPhong();
        }

        phieuDoiPhongDialog.modelPhongDoi.setRowCount(0);
        for (Phong p : ds) {
            if (p.getTrangThai() == TrangThaiPhong.Trong) {
                phieuDoiPhongDialog.modelPhongDoi.addRow(new Object[]{
                        p.getMaPhong(),
                        p.getLoaiPhong().getTenLoaiPhong(),
                        p.getSucChuaToiDa(),
                        p.getGiaPhong(),
                        p.getTienCoc()
                });
            }
        }
    }

    public void baoLoi(String s) {
        JOptionPane.showMessageDialog(phieuDoiPhongDialog, s);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double giaCu = Double.parseDouble(phieuDoiPhongDialog.tablePhongHienTai.getValueAt(0, 3).toString());
        int r = phieuDoiPhongDialog.tablePhongDoi.getSelectedRow();
        double giaMoi = Double.parseDouble(phieuDoiPhongDialog.tablePhongDoi.getValueAt(r, 3).toString());

        double chenhLech = giaMoi - giaCu;
        phieuDoiPhongDialog.lbl_TienCuaGiaPhongCu.setText(df.format(giaCu) + " VND");
        phieuDoiPhongDialog.lbl_TienCuaGiaPhongMoi.setText(df.format(giaMoi) + " VND");
        phieuDoiPhongDialog.lbl_TienCuaGiaChenhLech.setText(df.format(chenhLech) + " VND");

        capNhatTienKhiChonLyDoDoi();
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
