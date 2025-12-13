
package controllers;

import com.toedter.calendar.JDateChooser;
import database.dao.KhuyenMaiDao;
import entitys.KhuyenMai;
import enums.TrangThaiKhuyenMai;
import view.dialogs.KhuyenMaiDialog;
import view.panels.KhuyenMaiPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class KhuyenMaiController implements MouseListener, ActionListener {
    private KhuyenMaiPanel khuyenMaiPanel;
    private ArrayList<KhuyenMai> danhSachKhuyenMai;
    public KhuyenMaiDao khuyenMaiDao;

    public KhuyenMaiController(KhuyenMaiPanel khuyenMaiPanel) {
        this.khuyenMaiPanel = khuyenMaiPanel;
        this.danhSachKhuyenMai = new ArrayList<>();
        khuyenMaiDao = new KhuyenMaiDao();

        // Đăng ký sự kiện
        khuyenMaiPanel.btn_Them.addActionListener(this);
        khuyenMaiPanel.btn_LamMoi.addActionListener(this);
        khuyenMaiPanel.btn_TimMa.addActionListener(this);
        khuyenMaiPanel.table.addMouseListener(this);

        suKienTuDongTimKiem();
//        taiTatCaKhuyenMai();
//        tuDongCapNhatTrangThai();
        suKienTextField();

        // Khởi tạo giao diện
        lamMoi();
    }

    private void tuDongCapNhatTrangThai() {
        try {
            khuyenMaiDao.tuDongCapNhatTrangThaiKhuyenMai();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(khuyenMaiPanel, "Lỗi khi cập nhật trạng thái khuyến mãi");
        }
    }

    private void suKienTuDongTimKiem() {
        // Sự kiện cho DateChooser trong bộ lọc
        khuyenMaiPanel.ngayBD_1.addPropertyChangeListener(e -> {
            if ("date".equals(e.getPropertyName())) {
                thucHienTimKiemTuDong();
            }
        });

        khuyenMaiPanel.ngayKT_1.addPropertyChangeListener(e -> {
            if ("date".equals(e.getPropertyName())) {
                thucHienTimKiemTuDong();
            }
        });

        // Sự kiện cho checkbox "Tất cả" trong bộ lọc
        khuyenMaiPanel.chckbx_TatCa_1.addActionListener(e -> {
            if(khuyenMaiPanel.chckbx_TatCa_1.isSelected()){
                khuyenMaiPanel.chckbx_Standard_1.setSelected(false);
                khuyenMaiPanel.chckbx_Superior_1.setSelected(false);
                khuyenMaiPanel.chckbx_Family_1.setSelected(false);
                khuyenMaiPanel.chckbx_Deluxe_1.setSelected(false);
                khuyenMaiPanel.chckbx_Suite_1.setSelected(false);
            }
            thucHienTimKiemTuDong();
        });

        // Sự kiện cho checkbox trong bộ lọc
        ActionListener suKienCheckbox = e -> {
            // Khi chọn các checkbox khác, bỏ chọn "Tất cả"
            if(khuyenMaiPanel.chckbx_TatCa_1.isSelected()){
                khuyenMaiPanel.chckbx_TatCa_1.setSelected(false);
            }
            thucHienTimKiemTuDong();
        };

        khuyenMaiPanel.chckbx_Standard_1.addActionListener(suKienCheckbox);
        khuyenMaiPanel.chckbx_Superior_1.addActionListener(suKienCheckbox);
        khuyenMaiPanel.chckbx_Family_1.addActionListener(suKienCheckbox);
        khuyenMaiPanel.chckbx_Deluxe_1.addActionListener(suKienCheckbox);
        khuyenMaiPanel.chckbx_Suite_1.addActionListener(suKienCheckbox);
//        khuyenMaiPanel.chckbx_TatCa_1.addActionListener(suKienCheckbox);

        // Sự kiện cho ô tìm mã
        khuyenMaiPanel.txt_TimMaKhuyenMai.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (khuyenMaiPanel.txt_TimMaKhuyenMai.getText().trim().isEmpty()) {
                    thucHienTimKiemTuDong();
                }
            }
        });
    }

    private void thucHienTimKiemTuDong() {
        if (khuyenMaiPanel.txt_TimMaKhuyenMai.getText().trim().isEmpty()) {
            timKiemKhuyenMaiTuDong();
        }
    }

    private void timKiemKhuyenMaiTuDong() {
        try {
            String dieuKien = layDieuKienApDungTuCheckboxLoc();
            LocalDateTime tuNgay = layNgayTuDateChooser(khuyenMaiPanel.ngayBD_1);
            LocalDateTime denNgay = layNgayTuDateChooser(khuyenMaiPanel.ngayKT_1);

            ArrayList<KhuyenMai> ketQua;

            if (!dieuKien.isEmpty() || (tuNgay != null && denNgay != null)) {
                ketQua = khuyenMaiDao.getTatCaKhuyenMai();

                if (!dieuKien.isEmpty()) {
                    ArrayList<KhuyenMai> ketQuaDieuKien = khuyenMaiDao.locKhuyenMaiTheoDieuKien(dieuKien);
                    ketQua.retainAll(ketQuaDieuKien);
                }

                if (tuNgay != null && denNgay != null) {
                    ArrayList<KhuyenMai> ketQuaThoiGian = khuyenMaiDao.locKhuyenMaiTheoKhoangThoiGian(tuNgay, denNgay);
                    ketQua.retainAll(ketQuaThoiGian);
                }
            } else {
                ketQua = khuyenMaiDao.getTatCaKhuyenMai();
            }

            hienThiDuLieuLenBang(ketQua);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String layDieuKienApDungTuCheckboxLoc() {
        StringBuilder dieuKien = new StringBuilder();

        if (khuyenMaiPanel.chckbx_Standard_1.isSelected()) {
            dieuKien.append("Standard, ");
        }
        if (khuyenMaiPanel.chckbx_Superior_1.isSelected()) {
            dieuKien.append("Superior, ");
        }
        if (khuyenMaiPanel.chckbx_Family_1.isSelected()) {
            dieuKien.append("Family Room, ");
        }
        if (khuyenMaiPanel.chckbx_Deluxe_1.isSelected()) {
            dieuKien.append("Deluxe, ");
        }
        if (khuyenMaiPanel.chckbx_Suite_1.isSelected()) {
            dieuKien.append("Suite, ");
        }
        if (khuyenMaiPanel.chckbx_TatCa_1.isSelected()) {
            return "Tất cả";
        }

        if (dieuKien.length() > 0) {
            dieuKien.setLength(dieuKien.length() - 2);
        }

        return dieuKien.toString();
    }

    private LocalDateTime layNgayTuDateChooser(JDateChooser dateChooser) {
        if (dateChooser != null && dateChooser.getDate() != null) {
            Date ngay = dateChooser.getDate();
            return ngay.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        return null;
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
            // Loại bỏ dấu phẩy và khoảng trắng cuối cùng
            dieuKien.setLength(dieuKien.length() - 2);
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
            // 1. Tự động cập nhật trạng thái khuyến mãi (trừ những cái đang "Tạm ngừng")
            tuDongCapNhatTrangThai();

            // 2. Tải lại toàn bộ dữ liệu từ database
            taiTatCaKhuyenMai();

            // 3. Reset các bộ lọc
            resetBoLoc();

            // 4. Reset form nhập liệu
            lamMoiForm();
    }

    private void resetBoLoc() {
        // Reset các trường tìm kiếm và bộ lọc
        khuyenMaiPanel.txt_TimMaKhuyenMai.setText("");
        khuyenMaiPanel.ngayBD_1.setDate(null);
        khuyenMaiPanel.ngayKT_1.setDate(null);

        // Reset các checkbox bộ lọc
        khuyenMaiPanel.chckbx_Standard_1.setSelected(false);
        khuyenMaiPanel.chckbx_Superior_1.setSelected(false);
        khuyenMaiPanel.chckbx_Family_1.setSelected(false);
        khuyenMaiPanel.chckbx_Deluxe_1.setSelected(false);
        khuyenMaiPanel.chckbx_Suite_1.setSelected(false);
        khuyenMaiPanel.chckbx_TatCa_1.setSelected(false);
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
                        (int) (km.getTyLeGiam() * 100) + "%",
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

    private void taiTatCaKhuyenMai() {
        try {
            danhSachKhuyenMai = khuyenMaiDao.getTatCaKhuyenMai();
            hienThiDuLieuLenBang(danhSachKhuyenMai);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(khuyenMaiPanel, "Lỗi khi tải danh sách khuyến mãi");
        }
    }

    private void hienThiDuLieuLenBang(ArrayList<KhuyenMai> danhSach) {
        DefaultTableModel model = khuyenMaiPanel.model;
        model.setRowCount(0);

        for (KhuyenMai km : danhSach) {
            model.addRow(new Object[]{
                    km.getMaKM(),
                    km.getTenKM(),
                    (int) (km.getTyLeGiam() * 100) + "%",
                    km.getDieuKienApDung(),
                    km.getNgayBatDau().toLocalDate().toString(),
                    km.getNgayketThuc().toLocalDate().toString(),
                    getTrangThaiHienThi(km.getTrangThai())
            });
        }
    }

    private void hienThiDuLieuLenBang(KhuyenMai khuyenMai) {
        DefaultTableModel model = khuyenMaiPanel.model;
        model.setRowCount(0);

        if (khuyenMai != null) {
            model.addRow(new Object[]{
                    khuyenMai.getMaKM(),
                    khuyenMai.getTenKM(),
                    (int) (khuyenMai.getTyLeGiam() * 100) + "%",
                    khuyenMai.getDieuKienApDung(),
                    khuyenMai.getNgayBatDau().toLocalDate().toString(),
                    khuyenMai.getNgayketThuc().toLocalDate().toString(),
                    getTrangThaiHienThi(khuyenMai.getTrangThai())
            });
        }
    }

    private void themKhuyenMai() {
        if (kiemTraDuLieu()) {
            try {
                String tenKM = khuyenMaiPanel.txt_TenKhachHang.getText().strip();
                double tyLeGiam = Double.parseDouble(khuyenMaiPanel.txt_TyLeGiam.getText().strip()) / 100.0;

                Date dateBD = khuyenMaiPanel.ngayBD.getDate();
                Date dateKT = khuyenMaiPanel.ngayKT.getDate();
                LocalDateTime ngayBatDau = dateBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime ngayKetThuc = dateKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                String dieuKien = layDieuKienApDung();
                TrangThaiKhuyenMai trangThai = xacDinhTrangThaiTuDong(ngayBatDau, ngayKetThuc);

                // Tạo mã khuyến mãi tự động
                String maKM = taoMaKhuyenMaiTuDong(ngayBatDau);

                KhuyenMai khuyenMai = new KhuyenMai(maKM, tenKM, dieuKien, tyLeGiam, ngayBatDau, ngayKetThuc, trangThai);

                if (khuyenMaiDao.themKhuyenMai(khuyenMai)) {
                    JOptionPane.showMessageDialog(khuyenMaiPanel, "Thêm khuyến mãi thành công!");
                    lamMoiForm();
                    taiTatCaKhuyenMai();
                } else {
                    JOptionPane.showMessageDialog(khuyenMaiPanel, "Thêm khuyến mãi thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(khuyenMaiPanel, "Lỗi khi thêm khuyến mãi");
            }
        }
    }

    private TrangThaiKhuyenMai xacDinhTrangThaiTuDong(LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc) {
        LocalDateTime ngayHienTai = LocalDateTime.now();

        if (ngayHienTai.isBefore(ngayBatDau)) {
            return TrangThaiKhuyenMai.SapDienRa;
        } else if (ngayHienTai.isBefore(ngayKetThuc) || ngayHienTai.isEqual(ngayKetThuc)) {
            return TrangThaiKhuyenMai.DangHoatDong;
        } else {
            return TrangThaiKhuyenMai.HetHan;
        }
    }

    private String taoMaKhuyenMaiTuDong(LocalDateTime ngayBatDau) {
        try {
            int namApDung = ngayBatDau.getYear();
            String namStr = String.valueOf(namApDung).substring(2);

            int soThuTuLonNhat = khuyenMaiDao.getSoLuongKhuyenMaiTheoNam(namApDung);
            int soThuTuMoi = soThuTuLonNhat + 1;

            if (soThuTuMoi > 999) {
                throw new RuntimeException("Đã vượt quá số lượng khuyến mãi cho phép trong năm " + namApDung);
            }

            String maKMMoi = "KM" + namStr + String.format("%03d", soThuTuMoi);

            int dem = 0;
            while (khuyenMaiDao.kiemTraMaKMTonTai(maKMMoi) && dem < 100) {
                soThuTuMoi++;
                maKMMoi = "KM" + namStr + String.format("%03d", soThuTuMoi);
                dem++;
            }

            if (dem >= 100) {
                throw new RuntimeException("Không thể tạo mã khuyến mãi mới cho năm " + namApDung);
            }

            return maKMMoi;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tạo mã khuyến mãi tự động: " + e.getMessage());
        }
    }

    private void lamMoiForm() {
        khuyenMaiPanel.txt_TenKhachHang.setText("");
        khuyenMaiPanel.txt_TyLeGiam.setText("");
        khuyenMaiPanel.ngayBD.setDate(null);
        khuyenMaiPanel.ngayKT.setDate(null);
        khuyenMaiPanel.chckbx_Standard.setSelected(false);
        khuyenMaiPanel.chckbx_Superior.setSelected(false);
        khuyenMaiPanel.chckbx_Family.setSelected(false);
        khuyenMaiPanel.chckbx_Deluxe.setSelected(false);
        khuyenMaiPanel.chckbx_Suite.setSelected(false);
        khuyenMaiPanel.chckbx_TatCa.setSelected(false);
        khuyenMaiPanel.comboBox_TrangThai.setSelectedIndex(0);
        khuyenMaiPanel.txt_TenKhachHang.requestFocus();
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

    private void timKhuyenMaiTheoMa() {
        String maTim = khuyenMaiPanel.txt_TimMaKhuyenMai.getText().strip();
        if (maTim.isEmpty()) {
            taiTatCaKhuyenMai();
            return;
        }

        try {
            KhuyenMai ketQua = khuyenMaiDao.getKhuyenMaiTheoMa(maTim);
            hienThiDuLieuLenBang(ketQua);

            if (ketQua.getMaKM() == null) {
                JOptionPane.showMessageDialog(khuyenMaiPanel, "Không tìm thấy khuyến mãi với mã: " + maTim);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(khuyenMaiPanel, "Lỗi khi tìm kiếm khuyến mãi");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == khuyenMaiPanel.btn_Them) {
            themKhuyenMai();
        } else if (source == khuyenMaiPanel.btn_LamMoi) {
            lamMoi();
        } else if (source == khuyenMaiPanel.btn_TimMa) {
            timKhuyenMaiTheoMa();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = khuyenMaiPanel.table.getSelectedRow();
        if (row != -1) {
            String maKM = khuyenMaiPanel.table.getValueAt(row, 0).toString();
            KhuyenMai khuyenMai = khuyenMaiDao.getKhuyenMaiTheoMa(maKM);

            // Mở dialog chi tiết khuyến mãi
            if (khuyenMai != null) {
                KhuyenMaiDialog dialog = new KhuyenMaiDialog(
                        (JFrame) SwingUtilities.getWindowAncestor(khuyenMaiPanel),
                        khuyenMai
                );
                dialog.setVisible(true);

                // Refresh table sau khi đóng dialog
                taiTatCaKhuyenMai();
            }
        }
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
