
package controllers.dialogs;

import database.dao.KhuyenMaiDao;
import entitys.KhuyenMai;
import enums.TrangThaiKhuyenMai;
import view.dialogs.KhuyenMaiDialog;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


public class KhuyenMaiDialogController {
    private KhuyenMaiDialog khuyenMaiDialog;
    private KhuyenMai khuyenMai;
    private KhuyenMaiDao khuyenMaiDao;

    public KhuyenMaiDialogController(KhuyenMaiDialog dialog, KhuyenMai khuyenMai) {
        this.khuyenMaiDialog = dialog;
        this.khuyenMai = khuyenMai;
        this.khuyenMaiDao = new KhuyenMaiDao();

        hienThiThongTin();
        dangKySuKien();
        capNhatComboBoxTrangThaiTheoNgay();
    }

    private void capNhatComboBoxTrangThaiTheoNgay() {
        Date ngayBD = khuyenMaiDialog.ngayBatDau.getDate();
        Date ngayKT = khuyenMaiDialog.ngayKetThuc.getDate();

        if(ngayBD == null || ngayKT == null){
            return; // Ngày chưa được chọn
        }

        // CHUYỂN SANG LocalDate để chỉ so sánh ngày, không so sánh giờ
        LocalDate hienTai = LocalDate.now();
        LocalDate ngayBatDau = ngayBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ngayKetThuc = ngayKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Xóa tất cả các mục hiện có
        khuyenMaiDialog.cbb_TrangThaiHoaDon.removeAllItems();

        // Thêm các trạng thái phù hợp
        // Kiểm tra và thêm item phù hợp theo ngày
        if (ngayBatDau.isAfter(hienTai)) {
            // Chưa đến ngày bắt đầu -> chỉ hiển thị "Sắp diễn ra"
            String trangThaiHienThi = getTrangThaiHienThi(TrangThaiKhuyenMai.SapDienRa);
            khuyenMaiDialog.cbb_TrangThaiHoaDon.addItem(trangThaiHienThi);
            khuyenMaiDialog.cbb_TrangThaiHoaDon.setSelectedItem(trangThaiHienThi);
        } else if (!ngayBatDau.isAfter(hienTai) && !ngayKetThuc.isBefore(hienTai)) {
            // Đang trong thời gian hoạt động (ngày bắt đầu <= hôm nay và ngày kết thúc >= hôm nay)
            // -> hiển thị "Đang hoạt động" và "Tạm ngừng"
            String trangThaiHoatDong = getTrangThaiHienThi(TrangThaiKhuyenMai.DangHoatDong);
            String trangThaiTamNgung = getTrangThaiHienThi(TrangThaiKhuyenMai.TamNgung);

            khuyenMaiDialog.cbb_TrangThaiHoaDon.addItem(trangThaiHoatDong);
            khuyenMaiDialog.cbb_TrangThaiHoaDon.addItem(trangThaiTamNgung);

            // Nếu đang chỉnh sửa, giữ trạng thái cũ nếu nó là một trong 2 trạng thái này
            if (khuyenMai != null) {
                String trangThaiCu = getTrangThaiHienThi(khuyenMai.getTrangThai());
                if (trangThaiCu.equals(trangThaiHoatDong) || trangThaiCu.equals(trangThaiTamNgung)) {
                    khuyenMaiDialog.cbb_TrangThaiHoaDon.setSelectedItem(trangThaiCu);
                } else {
                    khuyenMaiDialog.cbb_TrangThaiHoaDon.setSelectedItem(trangThaiHoatDong);
                }
            } else {
                khuyenMaiDialog.cbb_TrangThaiHoaDon.setSelectedItem(trangThaiHoatDong);
            }
        } else if (ngayKetThuc.isBefore(hienTai)) {
            // Đã hết hạn -> chỉ hiển thị "Hết hạn"
            String trangThaiHienThi = getTrangThaiHienThi(TrangThaiKhuyenMai.HetHan);
            khuyenMaiDialog.cbb_TrangThaiHoaDon.addItem(trangThaiHienThi);
            khuyenMaiDialog.cbb_TrangThaiHoaDon.setSelectedItem(trangThaiHienThi);
        }
    }

    public void hienThiThongTin() {
        if(khuyenMai != null){
            khuyenMaiDialog.txt_MaKM.setText(khuyenMai.getMaKM());
            khuyenMaiDialog.txt_TenKM.setText(khuyenMai.getTenKM());
            khuyenMaiDialog.txt_TiLeGiamGia.setText(String.valueOf((int)(khuyenMai.getTyLeGiam() * 100)));

            // Chuyển đổi từ LocalDate sang Date
            Date ngayBD = Date.from(khuyenMai.getNgayBatDau().atZone(ZoneId.systemDefault()).toInstant());
            Date ngayKT = Date.from(khuyenMai.getNgayketThuc().atZone(ZoneId.systemDefault()).toInstant());
            khuyenMaiDialog.ngayBatDau.setDate(ngayBD);
            khuyenMaiDialog.ngayKetThuc.setDate(ngayKT);

//            // Hiển thị trạng thái
//            khuyenMaiDialog.cbb_TrangThaiHoaDon.setSelectedItem(getTrangThaiHienThi(khuyenMai.getTrangThai()));

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

        // Cập nhật trạng thái khi ngày thay đổi
        khuyenMaiDialog.ngayBatDau.addPropertyChangeListener("date", e -> capNhatComboBoxTrangThaiTheoNgay());
        khuyenMaiDialog.ngayKetThuc.addPropertyChangeListener("date", e -> capNhatComboBoxTrangThaiTheoNgay());
    }

    private boolean kiemTraDuLieu() {
        String tenKM = khuyenMaiDialog.txt_TenKM.getText().strip();
        String tyLeGiamText = khuyenMaiDialog.txt_TiLeGiamGia.getText().strip();
        Date ngayBD = khuyenMaiDialog.ngayBatDau.getDate();
        Date ngayKT = khuyenMaiDialog.ngayKetThuc.getDate();

        if (tenKM.isEmpty()) {
            JOptionPane.showMessageDialog(khuyenMaiDialog, "Vui lòng nhập tên khuyến mãi");
            khuyenMaiDialog.txt_TenKM.requestFocus();
            return false;
        }

        if (tyLeGiamText.isEmpty()) {
            JOptionPane.showMessageDialog(khuyenMaiDialog, "Vui lòng nhập tỷ lệ giảm");
            khuyenMaiDialog.txt_TiLeGiamGia.requestFocus();
            return false;
        }

        try {
            double tyLe = Double.parseDouble(tyLeGiamText);
            if (tyLe <= 0 || tyLe > 100) {
                JOptionPane.showMessageDialog(khuyenMaiDialog, "Tỷ lệ giảm phải từ 1% đến 100%");
                khuyenMaiDialog.txt_TiLeGiamGia.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(khuyenMaiDialog, "Tỷ lệ giảm phải là số");
            khuyenMaiDialog.txt_TiLeGiamGia.requestFocus();
            return false;
        }

        if (ngayBD == null) {
            JOptionPane.showMessageDialog(khuyenMaiDialog, "Vui lòng chọn ngày bắt đầu");
            return false;
        }

        if (ngayKT == null) {
            JOptionPane.showMessageDialog(khuyenMaiDialog, "Vui lòng chọn ngày kết thúc");
            return false;
        }

        if (ngayBD.after(ngayKT)) {
            JOptionPane.showMessageDialog(khuyenMaiDialog, "Ngày kết thúc phải sau ngày bắt đầu");
            return false;
        }

        if (!coDieuKienApDung()) {
            JOptionPane.showMessageDialog(khuyenMaiDialog, "Vui lòng chọn ít nhất một điều kiện áp dụng");
            return false;
        }

        // kiểm tra trạng thái có phù hợp với khoảng thời gian không
        if (!kiemTraTrangThaiVaThoiGian()) {
            return false;
        }

        return true;
    }

    private boolean coDieuKienApDung() {
        return khuyenMaiDialog.chckbx_Standard.isSelected() ||
                khuyenMaiDialog.chckbx_Superior.isSelected() ||
                khuyenMaiDialog.chckbx_FamilyRoom.isSelected() ||
                khuyenMaiDialog.chckbx_Deluxe.isSelected() ||
                khuyenMaiDialog.chckbx_Suite.isSelected() ||
                khuyenMaiDialog.chckbx_TatCa.isSelected();
    }

    private boolean kiemTraCoThayDoi() {
        // Lấy thông tin từ dialog
        String tenKMMoi = khuyenMaiDialog.txt_TenKM.getText().strip();
        String tyLeGiamText = khuyenMaiDialog.txt_TiLeGiamGia.getText().strip();
        double tyLeGiamMoi = Double.parseDouble(tyLeGiamText) / 100.0;

        Date ngayBD = khuyenMaiDialog.ngayBatDau.getDate();
        Date ngayKT = khuyenMaiDialog.ngayKetThuc.getDate();
        LocalDateTime ngayBatDauMoi = ngayBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime ngayKetThucMoi = ngayKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        String dieuKienMoi = layDieuKienApDung();
        TrangThaiKhuyenMai trangThaiMoi = getTrangThai(khuyenMaiDialog.cbb_TrangThaiHoaDon.getSelectedItem().toString());

        // So sánh với thông tin cũ
        boolean tenThayDoi = !tenKMMoi.equals(khuyenMai.getTenKM());
        boolean tyLeThayDoi = Math.abs(tyLeGiamMoi - khuyenMai.getTyLeGiam()) > 0.001; // So sánh double với sai số nhỏ
        boolean ngayBDThayDoi = !ngayBatDauMoi.equals(khuyenMai.getNgayBatDau());
        boolean ngayKTThayDoi = !ngayKetThucMoi.equals(khuyenMai.getNgayketThuc());
        boolean dieuKienThayDoi = !dieuKienMoi.equals(khuyenMai.getDieuKienApDung());
        boolean trangThaiThayDoi = !trangThaiMoi.equals(khuyenMai.getTrangThai());

        // Trả về true nếu có ít nhất một thay đổi
        return tenThayDoi || tyLeThayDoi || ngayBDThayDoi || ngayKTThayDoi ||
                dieuKienThayDoi || trangThaiThayDoi;
    }

    private void capNhatKhuyenMai() {
        // Kiểm tra dữ liệu hợp lệ
        if(!kiemTraDuLieu()){
            return;
        }

        // Kiểm tra xem có thay đổi nào không
        if (!kiemTraCoThayDoi()) {
            JOptionPane.showMessageDialog(khuyenMaiDialog,
                    "Không có thay đổi nào để cập nhật!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Thực hiện cập nhật
        khuyenMai.setTenKM(khuyenMaiDialog.txt_TenKM.getText().strip());
        khuyenMai.setTyLeGiam(Double.parseDouble(khuyenMaiDialog.txt_TiLeGiamGia.getText()) / 100.0);

        Date ngayBD = khuyenMaiDialog.ngayBatDau.getDate();
        Date ngayKT = khuyenMaiDialog.ngayKetThuc.getDate();
        khuyenMai.setNgayBatDau(ngayBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        khuyenMai.setNgayketThuc(ngayKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        khuyenMai.setDieuKienApDung(layDieuKienApDung());
        khuyenMai.setTrangThai(getTrangThai(khuyenMaiDialog.cbb_TrangThaiHoaDon.getSelectedItem().toString()));

        if (khuyenMaiDao.capNhatKhuyenMai(khuyenMai)) {
            JOptionPane.showMessageDialog(khuyenMaiDialog, "Cập nhật khuyến mãi thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            khuyenMaiDialog.dispose();
        } else {
            JOptionPane.showMessageDialog(khuyenMaiDialog, "Cập nhật khuyến mãi thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        khuyenMaiDialog.dispose();
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

    private boolean kiemTraTrangThaiVaThoiGian(){
        String trangThai = khuyenMaiDialog.cbb_TrangThaiHoaDon.getSelectedItem().toString();
        Date ngayBD = khuyenMaiDialog.ngayBatDau.getDate();
        Date ngayKT = khuyenMaiDialog.ngayKetThuc.getDate();

        if(ngayBD == null || ngayKT == null){
            return true; // Đã được kiểm tra ở hàm kiemTraDuLieu()
        }

        // Sử dụng LocalDate để chỉ so sánh ngày, không so sánh giờ
        LocalDate hienTai = LocalDate.now();
        LocalDate ngayBatDau = ngayBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ngayKetThuc = ngayKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

//        // In debug để kiểm tra
//        System.out.println("Debug trạng thái kiểm tra:");
//        System.out.println("Trạng thái: " + trangThai);
//        System.out.println("Ngày bắt đầu: " + ngayBatDau);
//        System.out.println("Ngày kết thúc: " + ngayKetThuc);
//        System.out.println("Ngày hiện tại: " + hienTai);

        switch (trangThai){
            case "Sắp diễn ra":
                if(!ngayBatDau.isAfter(hienTai)){
                    JOptionPane.showMessageDialog(khuyenMaiDialog,
                            "Không thể đặt trạng thái 'Sắp diễn ra' khi ngày bắt đầu đã qua hoặc là hôm nay!",
                            "Lỗi trạng thái", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;

            case "Đang hoạt động":
                // Sửa logic: Chỉ hợp lệ khi ngày hôm nay nằm trong khoảng từ ngày bắt đầu đến ngày kết thúc (bao gồm cả hai ngày)
                // Tức là: ngày bắt đầu <= hôm nay <= ngày kết thúc
                if(ngayBatDau.isAfter(hienTai) || ngayKetThuc.isBefore(hienTai)){
                    JOptionPane.showMessageDialog(khuyenMaiDialog,
                            "Không thể đặt trạng thái 'Đang hoạt động' khi chưa đến ngày bắt đầu hoặc đã qua ngày kết thúc!",
                            "Lỗi trạng thái", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;

            case "Hết hạn":
                if(!ngayKetThuc.isBefore(hienTai)){
                    JOptionPane.showMessageDialog(khuyenMaiDialog,
                            "Không thể đặt trạng thái 'Hết hạn' khi chưa đến ngày kết thúc!",
                            "Lỗi trạng thái", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;

            case "Tạm ngừng":
                // Trạng thái "Tạm ngừng" có thể đặt bất kỳ lúc nào
                return true;
        }
        return true;
    }
}
