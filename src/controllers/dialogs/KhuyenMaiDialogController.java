/**
 * @ (#) KhuyenMaiDialogController.java       1.0     01/11/2025
 * <p>
 * Copuright (c) 2025 IUH, All rights reserved
 */
package controllers.dialogs;

import entitys.KhuyenMai;
import enums.TrangThaiKhuyenMai;
import view.dialogs.KhuyenMaiDialog;

import javax.swing.*;
import java.time.ZoneId;
import java.util.Date;

/**
 * @description:
 * @auther: Pham Le Huu Thang
 * @date: 01/11/2025
 * @version: 1.0
 */
public class KhuyenMaiDialogController {
    private KhuyenMaiDialog khuyenMaiDialog;
    private KhuyenMai khuyenMai;

    public KhuyenMaiDialogController(KhuyenMaiDialog dialog, KhuyenMai khuyenMai) {
        this.khuyenMaiDialog = dialog;
        this.khuyenMai = khuyenMai;

        dangKySuKien();
    }

    public void hienThiThongTin() {
        if(khuyenMai != null){
            khuyenMaiDialog.txt_MaKM.setText(khuyenMai.getMaKM());
            khuyenMaiDialog.txt_TenKM.setText(khuyenMai.getTenKM());
            khuyenMaiDialog.txt_TiLeGiamGia.setText(String.valueOf(khuyenMai.getTyLeGiam()));

            // Chuyển đổi từ LocalDate sang Date
            Date ngayBD = Date.from(khuyenMai.getNgayBatDau().atZone(ZoneId.systemDefault()).toInstant());
            Date ngayKT = Date.from(khuyenMai.getNgayketThuc().atZone(ZoneId.systemDefault()).toInstant());
            khuyenMaiDialog.ngayBatDau.setDate(ngayBD);
            khuyenMaiDialog.ngayKetThuc.setDate(ngayKT);

            // Hiển thị trạng thái
            khuyenMaiDialog.cbb_TrangThaiHoaDon.setSelectedItem(getTrangThaiHienThi(khuyenMai.getTrangThai()));

            setDieuKienCheckbox(khuyenMai.getDieuKienApDung());
        }
    }

    private String getTrangThaiHienThi(TrangThaiKhuyenMai trangThai) {
        switch (trangThai) {
            case DangHoatDong: return "Đang hoạt động";
            case SapDienRa: return "Sắp diễn ra";
            case HetHan: return "Hết hạn";
            case TamNgung: return "Tạm ngừng";
            default: return "";
        }
    }

    private void setDieuKienCheckbox(String dieuKien) {
        if (dieuKien.contains("Tất cả")){
            khuyenMaiDialog.chckbx_TatCa.setSelected(true);
            return;
        }

        khuyenMaiDialog.chckbx_Standard.setSelected(dieuKien.contains("Standard"));
        khuyenMaiDialog.chckbx_Superior.setSelected(dieuKien.contains("Superior"));
        khuyenMaiDialog.chckbx_FamilyRoom.setSelected(dieuKien.contains("Family Room"));
        khuyenMaiDialog.chckbx_Deluxe.setSelected(dieuKien.contains("Deluxe"));
        khuyenMaiDialog.chckbx_Suite.setSelected(dieuKien.contains("Suite"));
    }

    private void dangKySuKien() {
        khuyenMaiDialog.btn_CapNhat.addActionListener(e -> capNhatKhuyenMai());
        khuyenMaiDialog.btn_Dong.addActionListener(e -> khuyenMaiDialog.dispose());
    }

    private boolean kiemTraDuLieu() {
        // Kiểm tra dữ liệu nhập vào ở đây
        return true;
    }

    private void capNhatKhuyenMai() {
        if(kiemTraDuLieu()){
            // Cập nhật thông tin khuyến mãi từ dialog vào đối tượng khuyenMai
            khuyenMai.setTenKM(khuyenMaiDialog.txt_TenKM.getText().strip());
            khuyenMai.setTyLeGiam(Double.parseDouble(khuyenMaiDialog.txt_TiLeGiamGia.getText()));

            Date ngayBD = khuyenMaiDialog.ngayBatDau.getDate();
            Date ngayKT = khuyenMaiDialog.ngayKetThuc.getDate();
            khuyenMai.setNgayBatDau(ngayBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            khuyenMai.setNgayketThuc(ngayKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

            khuyenMai.setDieuKienApDung(layDieuKienApDung());
            khuyenMai.setTrangThai(getTrangThai(khuyenMaiDialog.cbb_TrangThaiHoaDon.getSelectedItem().toString()));

            JOptionPane.showMessageDialog(khuyenMaiDialog, "Cập nhật khuyến mãi thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            khuyenMaiDialog.dispose();
        }
    }

    private TrangThaiKhuyenMai getTrangThai(String tenTrangThai) {
        switch (tenTrangThai) {
            case "Đang hoạt động": return TrangThaiKhuyenMai.DangHoatDong;
            case "Sắp diễn ra": return TrangThaiKhuyenMai.SapDienRa;
            case "Hết hạn": return TrangThaiKhuyenMai.HetHan;
            case "Tạm ngừng": return TrangThaiKhuyenMai.TamNgung;
            default: return TrangThaiKhuyenMai.DangHoatDong;
        }
    }

    private String layDieuKienApDung() {
        if (khuyenMaiDialog.chckbx_TatCa.isSelected()) {
            return "Tất cả";
        }

        StringBuilder dieuKien = new StringBuilder();
        if (khuyenMaiDialog.chckbx_Standard.isSelected()) dieuKien.append("Standard, ");
        if (khuyenMaiDialog.chckbx_Superior.isSelected()) dieuKien.append("Superior, ");
        if (khuyenMaiDialog.chckbx_FamilyRoom.isSelected()) dieuKien.append("Family Room, ");
        if (khuyenMaiDialog.chckbx_Deluxe.isSelected()) dieuKien.append("Deluxe, ");
        if (khuyenMaiDialog.chckbx_Suite.isSelected()) dieuKien.append("Suite, ");

        // Loại bỏ dấu phẩy và khoảng trắng cuối cùng
        if (dieuKien.length() > 0) {
            dieuKien.setLength(dieuKien.length() - 2);
        }

        return dieuKien.toString();
    }
}
