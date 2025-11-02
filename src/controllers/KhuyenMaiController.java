
package controllers;

import entitys.KhuyenMai;
import enums.TrangThaiKhuyenMai;
import view.dialogs.KhuyenMaiDialog;
import view.panels.KhuyenMaiPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class KhuyenMaiController implements MouseListener, ActionListener {
    private KhuyenMaiPanel khuyenMaiPanel;
    private ArrayList<KhuyenMai> danhSachKhuyenMai;

    public KhuyenMaiController(KhuyenMaiPanel khuyenMaiPanel) {
        this.khuyenMaiPanel = khuyenMaiPanel;
        this.danhSachKhuyenMai = new ArrayList<>();

        // Đăng ký sự kiện
        khuyenMaiPanel.btn_Them.addActionListener(this);
        khuyenMaiPanel.btn_LamMoi.addActionListener(this);
        khuyenMaiPanel.btn_TimMa.addActionListener(this);
        khuyenMaiPanel.table.addMouseListener(this);

        getTatCaKhuyenMai();
        suKienTextField();
    }

    private void themKhuyenMai() {
        if (kiemTraDuLieu()) {
            int namHienTai = LocalDate.now().getYear();
            String maKM = "KM" + (namHienTai % 100) + String.format("%03d", danhSachKhuyenMai.size() + 1);
            String tenKM = khuyenMaiPanel.txt_TenKhachHang.getText().strip();
            double tyLeGiam = Double.parseDouble(khuyenMaiPanel.txt_TyLeGiam.getText().strip());

            Date dateBD = khuyenMaiPanel.ngayBD.getDate();
            Date dateKT = khuyenMaiPanel.ngayKT.getDate();
            LocalDateTime ngayBatDau = dateBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime ngayKetThuc = dateKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            String dieuKien = layDieuKienApDung();
            TrangThaiKhuyenMai trangThai = getTrangThai(khuyenMaiPanel.comboBox_TrangThai.getSelectedItem().toString());

            KhuyenMai khuyenMai = new KhuyenMai(maKM, tenKM, dieuKien, tyLeGiam, ngayBatDau, ngayKetThuc, trangThai);
            danhSachKhuyenMai.add(khuyenMai);

            JOptionPane.showMessageDialog(khuyenMaiPanel, "Thêm khuyến mãi thành công!");

            // Thêm vào table
            khuyenMaiPanel.model.addRow(new Object[]{
                    khuyenMai.getMaKM(),
                    khuyenMai.getTenKM(),
                    khuyenMai.getTyLeGiam() + "%",
                    khuyenMai.getDieuKienApDung(),
                    khuyenMai.getNgayBatDau().toLocalDate(),
                    khuyenMai.getNgayketThuc().toLocalDate(),
                    getTrangThaiHienThi(khuyenMai.getTrangThai())
            });

            lamMoi();
        }
    }

    private String layDieuKienApDung() {
        if (khuyenMaiPanel.chckbx_TatCa.isSelected()) {
            return "Tất cả";
        }

        StringBuilder dieuKien = new StringBuilder();
        if (khuyenMaiPanel.chckbx_Standard.isSelected()) dieuKien.append("Standard, ");
        if (khuyenMaiPanel.chckbx_Superior.isSelected()) dieuKien.append("Superior, ");
        if (khuyenMaiPanel.chckbx_Family.isSelected()) dieuKien.append("Family Room, ");
        if (khuyenMaiPanel.chckbx_Deluxe.isSelected()) dieuKien.append("Deluxe, ");
        if (khuyenMaiPanel.chckbx_Suite.isSelected()) dieuKien.append("Suite, ");

        if (dieuKien.length() > 0) {
            dieuKien.setLength(dieuKien.length() - 2); // Remove last comma
        }

        return dieuKien.toString();
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

    private boolean kiemTraDuLieu() {
        String tenKM = khuyenMaiPanel.txt_TenKhachHang.getText().strip();
        String tyLeGiam = khuyenMaiPanel.txt_TyLeGiam.getText().strip();
        Date ngayBD = khuyenMaiPanel.ngayBD.getDate();
        Date ngayKT = khuyenMaiPanel.ngayKT.getDate();

        if (tenKM.isEmpty()) {
            JOptionPane.showMessageDialog(khuyenMaiPanel, "Vui lòng nhập tên khuyến mãi");
            khuyenMaiPanel.txt_TenKhachHang.requestFocus();
            return false;
        }

        if (tyLeGiam.isEmpty()) {
            JOptionPane.showMessageDialog(khuyenMaiPanel, "Vui lòng nhập tỷ lệ giảm");
            khuyenMaiPanel.txt_TyLeGiam.requestFocus();
            return false;
        }

        try {
            double tyLe = Double.parseDouble(tyLeGiam);
            if (tyLe <= 0 || tyLe > 100) {
                JOptionPane.showMessageDialog(khuyenMaiPanel, "Tỷ lệ giảm phải từ 1% đến 100%");
                khuyenMaiPanel.txt_TyLeGiam.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(khuyenMaiPanel, "Tỷ lệ giảm phải là số");
            khuyenMaiPanel.txt_TyLeGiam.requestFocus();
            return false;
        }

        if (ngayBD == null) {
            JOptionPane.showMessageDialog(khuyenMaiPanel, "Vui lòng chọn ngày bắt đầu");
            return false;
        }

        if (ngayKT == null) {
            JOptionPane.showMessageDialog(khuyenMaiPanel, "Vui lòng chọn ngày kết thúc");
            return false;
        }

        if (ngayBD.after(ngayKT)) {
            JOptionPane.showMessageDialog(khuyenMaiPanel, "Ngày kết thúc phải sau ngày bắt đầu");
            return false;
        }

        if (!coDieuKienApDung()) {
            JOptionPane.showMessageDialog(khuyenMaiPanel, "Vui lòng chọn ít nhất một điều kiện áp dụng");
            return false;
        }

        return true;
    }

    private boolean coDieuKienApDung() {
        return khuyenMaiPanel.chckbx_Standard.isSelected() ||
                khuyenMaiPanel.chckbx_Superior.isSelected() ||
                khuyenMaiPanel.chckbx_Family.isSelected() ||
                khuyenMaiPanel.chckbx_Deluxe.isSelected() ||
                khuyenMaiPanel.chckbx_Suite.isSelected() ||
                khuyenMaiPanel.chckbx_TatCa.isSelected();
    }

    private void lamMoi() {
        String maTim = khuyenMaiPanel.txt_TimMaKhuyenMai.getText().strip().toLowerCase();
        DefaultTableModel model = khuyenMaiPanel.model;
        model.setRowCount(0);

        for (KhuyenMai km : danhSachKhuyenMai) {
            if (km.getMaKM().toLowerCase().contains(maTim)) {
                model.addRow(new Object[]{
                        km.getMaKM(),
                        km.getTenKM(),
                        km.getTyLeGiam() + "%",
                        km.getDieuKienApDung(),
                        km.getNgayBatDau().toLocalDate(),
                        km.getNgayketThuc().toLocalDate(),
                        getTrangThaiHienThi(km.getTrangThai())
                });
            }
        }

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(khuyenMaiPanel, "Không tìm thấy khuyến mãi với mã: " + maTim);
        }
    }

    private void timKhuyenMai() {
        String maTim = khuyenMaiPanel.txt_TimMaKhuyenMai.getText().strip().toLowerCase();
        DefaultTableModel model = khuyenMaiPanel.model;
        model.setRowCount(0);

        for (KhuyenMai km : danhSachKhuyenMai) {
            if (km.getMaKM().toLowerCase().contains(maTim)) {
                model.addRow(new Object[]{
                        km.getMaKM(),
                        km.getTenKM(),
                        km.getTyLeGiam() + "%",
                        km.getDieuKienApDung(),
                        km.getNgayBatDau().toLocalDate(),
                        km.getNgayketThuc().toLocalDate(),
                        getTrangThaiHienThi(km.getTrangThai())
                });
            }
        }

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(khuyenMaiPanel, "Không tìm thấy khuyến mãi với mã: " + maTim);
        }
    }

    private void getTatCaKhuyenMai() {
        // Dữ liệu mẫu
        danhSachKhuyenMai.clear();
        KhuyenMai km1 = new KhuyenMai("KM001", "Khuyến mãi mùa hè", "Standard, Superior", 15.0,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30), TrangThaiKhuyenMai.DangHoatDong);
        KhuyenMai km2 = new KhuyenMai("KM002", "Khuyến mãi cuối năm", "Deluxe, Suite", 20.0,
                LocalDateTime.now().minusDays(10), LocalDateTime.now().plusDays(20), TrangThaiKhuyenMai.SapDienRa);

        danhSachKhuyenMai.add(km1);
        danhSachKhuyenMai.add(km2);

        DefaultTableModel model = khuyenMaiPanel.model;
        model.setRowCount(0);

        for (KhuyenMai km : danhSachKhuyenMai) {
            model.addRow(new Object[]{
                    km.getMaKM(),
                    km.getTenKM(),
                    km.getTyLeGiam() + "%",
                    km.getDieuKienApDung(),
                    km.getNgayBatDau().toLocalDate(),
                    km.getNgayketThuc().toLocalDate(),
                    getTrangThaiHienThi(km.getTrangThai())
            });
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

    private void suKienTextField() {
        // Khi nhấn Enter ở txt_TenKhachHang, chuyển focus đến txt_TyLeGiam
        khuyenMaiPanel.txt_TenKhachHang.addActionListener(e -> {
            khuyenMaiPanel.txt_TyLeGiam.requestFocus();
        });

        // Khi nhấn Enter ở txt_TyLeGiam, mở lịch chọn ngày bắt đầu
        khuyenMaiPanel.txt_TyLeGiam.addActionListener(e -> {
            khuyenMaiPanel.ngayBD.getCalendarButton().doClick();
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == khuyenMaiPanel.btn_Them) {
            themKhuyenMai();
        } else if (source == khuyenMaiPanel.btn_LamMoi) {
            lamMoi();
        } else if (source == khuyenMaiPanel.btn_TimMa) {
            timKhuyenMai();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = khuyenMaiPanel.table.getSelectedRow();
        if (row != -1) {
            String maKM = khuyenMaiPanel.table.getValueAt(row, 0).toString();
            KhuyenMai khuyenMai = timKhuyenMaiTheoMa(maKM);

            if (khuyenMai != null) {
                KhuyenMaiDialog dialog = new KhuyenMaiDialog(
                        (JFrame) SwingUtilities.getWindowAncestor(khuyenMaiPanel),
                        khuyenMai
                );
                dialog.setVisible(true);

                // Refresh table sau khi đóng dialog
                getTatCaKhuyenMai();
            }
        }
    }

    private KhuyenMai timKhuyenMaiTheoMa(String maKM) {
        for (KhuyenMai km : danhSachKhuyenMai) {
            if (km.getMaKM().equals(maKM)) {
                return km;
            }
        }
        return null;
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
