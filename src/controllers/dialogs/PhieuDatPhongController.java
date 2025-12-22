package controllers.dialogs;

import database.dao.*;
import controllers.QuanLyPhien;
import entitys.*;
import enums.PhuongThucThanhToan;
import enums.TrangThaiHoaDon;
import view.dialogs.PhieuDatPhongDialogOThueDatPhong;
import view.dialogs.ThemKhachHangDialog;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;

public class PhieuDatPhongController {

    private PhieuDatPhongDialogOThueDatPhong view;
    private KhachHang khachHang;
    private ArrayList<Phong> dsPhongDaChon;
    private KhuyenMai khuyenMaiDuocChon;
    private HoaDonDao hoaDonDao;
    private KhuyenMaiDao khuyenMaiDao;
    private KhachHangDao khachHangDao;
    private PhieuDatPhongDao phieuDatPhongDao;

    public PhieuDatPhongController(PhieuDatPhongDialogOThueDatPhong view, KhachHang khachHang, ArrayList<Phong> dsPhongDaChon, KhuyenMai khuyenMaiDuocChon) {
        this.view = view;
        this.khachHang = khachHang;
        this.dsPhongDaChon = dsPhongDaChon;
        this.khuyenMaiDuocChon = khuyenMaiDuocChon;

        hoaDonDao = new HoaDonDao();
        khuyenMaiDao = new KhuyenMaiDao();
        khachHangDao = new KhachHangDao();
        phieuDatPhongDao = new PhieuDatPhongDao();

        initController();
        hienThiThongTin();
        tinhTongTien();
        capNhatUITheoPhuongThucThanhToan();
    }

    private void initController() {
        view.txt_SDT.addActionListener(e -> timKiemKhachHangBangSDT());
        // Sự kiện cho phương thức thanh toán
        view.rdbtn_TienMat.addActionListener(e -> {
            view.lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien_1.setText("Tiền mặt");
            capNhatUITheoPhuongThucThanhToan();
        });

        view.rdbtn_ChuyenKhoan.addActionListener(e -> {
            view.lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien_1.setText("Chuyển khoản");
            capNhatUITheoPhuongThucThanhToan();
        });

        // Sự kiện cho tiền khách đưa
        view.txt_TienKhachDua.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                tinhTienTraLai();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                tinhTienTraLai();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                tinhTienTraLai();
            }
        });

        // SỬA SỰ KIỆN NÚT XÁC NHẬN - THÊM KIỂM TRA DỮ LIỆU
        view.btn_XacNhan.addActionListener(e -> {
            xacNhanPhieuDat();
        });

        view.btn_Huy.addActionListener(e -> {
            view.confirmed = false;
            view.dispose();
        });
    }

    private void timKiemKhachHangBangSDT() {
        String sdt = view.txt_SDT.getText().trim();

        if (sdt.isEmpty()) {
            showCustomMessageDialog(view,
                    "Vui lòng nhập số điện thoại!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        KhachHangDao khachHangDao = new KhachHangDao();
        KhachHang kh = khachHangDao.TimKhachHang(sdt, "SDT");

        if (kh != null) {
            // Cập nhật thông tin khách hàng
            this.khachHang = kh;
            hienThiThongTin();

            showCustomMessageDialog(view,
                    "Đã tìm thấy khách hàng: " + kh.getTenKH(),
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Hiển thị dialog thêm khách hàng
            int option = showCustomConfirmDialog(view,
                    "Không tìm thấy khách hàng với số điện thoại: " + sdt +
                            "\n\nBạn có muốn tạo khách hàng mới không?",
                    "Không tìm thấy",
                    JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
                ThemKhachHangDialog dialog = new ThemKhachHangDialog(parentFrame, sdt);
                dialog.setVisible(true);

                if (dialog.isDaXacNhan()) {
                    // Sau khi thêm thành công, tìm lại khách hàng
                    KhachHang khMoi = khachHangDao.TimKhachHang(sdt, "SDT");
                    if (khMoi != null) {
                        this.khachHang = khMoi;
                        hienThiThongTin();
                    }
                }
            }
        }
    }

    // Phương thức hiển thị thông báo đẹp
    private void showCustomMessageDialog(Component parent, String message, String title, int messageType) {
        JOptionPane pane = new JOptionPane(
                "<html><div style='width:300px;padding:15px;font-family:Segoe UI;font-size:14px;'>"
                        + message + "</div></html>",
                messageType
        );
        JDialog dialog = pane.createDialog(parent, title);
        dialog.getContentPane().setBackground(new Color(236, 247, 255));

        // Customize the OK button
        for (Component comp : pane.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.setBackground(new Color(10, 110, 189));
                button.setForeground(Color.WHITE);
                button.setFont(new Font("Segoe UI", Font.BOLD, 14));
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
            }
        }

        dialog.setVisible(true);
    }

    private int showCustomConfirmDialog(Component parent, String message, String title, int messageType) {
        Object[] options = {"Có", "Không"};
        JOptionPane pane = new JOptionPane(
                "<html><div style='width:300px;padding:15px;font-family:Segoe UI;font-size:14px;'>"
                        + message + "</div></html>",
                messageType,
                JOptionPane.YES_NO_OPTION,
                null,
                options,
                options[0]
        );
        JDialog dialog = pane.createDialog(parent, title);
        dialog.getContentPane().setBackground(new Color(236, 247, 255));

        // Customize buttons
        for (Component comp : pane.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                if (button.getText().equals("Có")) {
                    button.setBackground(new Color(10, 110, 189));
                } else {
                    button.setBackground(new Color(220, 220, 220));
                    button.setForeground(Color.BLACK);
                }
                button.setForeground(Color.WHITE);
                button.setFont(new Font("Segoe UI", Font.BOLD, 14));
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
            }
        }

        dialog.setVisible(true);

        Object selectedValue = pane.getValue();
        if (selectedValue == null) {
            return JOptionPane.CLOSED_OPTION;
        }

        if (selectedValue.toString().equals("Có")) {
            return JOptionPane.YES_OPTION;
        } else {
            return JOptionPane.NO_OPTION;
        }
    }

    private void xacNhanPhieuDat() {
        try {
            // 1. Lấy thông tin từ giao diện
            Date ngayBatDau = view.ngayBatDau.getDate();
            Date ngayKetThuc = view.ngayKetThuc.getDate();

            // KIỂM TRA DỮ LIỆU - KHÔNG TẮT DIALOG NẾU LỖI
            if (!kiemTraDuLieu(ngayBatDau, ngayKetThuc)) {
                return;
            }

            // 2. Tính toán thông tin
            int soNgayO = phieuDatPhongDao.tinhSoNgayO(ngayBatDau, ngayKetThuc);
            double tongTienCoc = tinhTongTienCoc();

            // 3. Lấy thông tin nhân viên đang đăng nhập
            NhanVien nhanVienHienTai = QuanLyPhien.getInstance().getNhanVienDangNhap();
            if (nhanVienHienTai == null) {
                JOptionPane.showMessageDialog(view,
                        "Không tìm thấy thông tin nhân viên! Vui lòng đăng nhập lại.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 4. Tạo mã hóa đơn mới
            String maHD = phieuDatPhongDao.taoMaHDMoi();
            if (maHD == null) {
                JOptionPane.showMessageDialog(view,
                        "Không thể tạo mã hóa đơn!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra xem mã có trùng không
            if (hoaDonDao.kiemTraMaHDTonTai(maHD)) {
                maHD = phieuDatPhongDao.taoMaHDMoi();
            }

            // 5. Tạo đối tượng HoaDon
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMaHD(maHD);
            hoaDon.setNgayLap(LocalDateTime.now());
            hoaDon.setTrangThai(TrangThaiHoaDon.PhieuDatPhong);
            hoaDon.setKhachHang(khachHang);
            hoaDon.setNhanVien(nhanVienHienTai);

            // Xác định phương thức thanh toán
            if (view.rdbtn_TienMat.isSelected()) {
                hoaDon.setpTTT(PhuongThucThanhToan.TienMat);
            } else {
                hoaDon.setpTTT(PhuongThucThanhToan.ChuyenKhoan);
            }

            // Tính các khoản tiền
            hoaDon.setTongTien(tongTienCoc);
            hoaDon.setTienThue(0);
            hoaDon.setPhiDoiPhong(0);
            hoaDon.setTongTienThanhToan();
            hoaDon.setTienGiamTheoHangKH(0);
            hoaDon.setTongTienSauGiam(hoaDon.getTongTienThanhToan());

            // Lấy tiền nhận từ khách (nếu thanh toán tiền mặt)
            double tienNhan = 0;
            if (view.rdbtn_TienMat.isSelected()) {
                try {
                    tienNhan = Double.parseDouble(view.txt_TienKhachDua.getText().trim());
                } catch (NumberFormatException e) {
                    tienNhan = 0;
                }
            }
            hoaDon.setTienNhan(tienNhan);

            // 6. Tạo danh sách chi tiết hóa đơn
            ArrayList<ChiTietHoaDon> dsChiTiet = new ArrayList<>();
            LocalDateTime ngayNhan = ngayBatDau.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            LocalDateTime ngayTra = ngayKetThuc.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            for (Phong phong : dsPhongDaChon) {
                ChiTietHoaDon chiTiet = new ChiTietHoaDon();
                chiTiet.setPhong(phong);
                chiTiet.setSoNgayO(soNgayO);
                chiTiet.setKhuyenMai(khuyenMaiDuocChon);
                chiTiet.setNgayNhanPhong(ngayNhan);
                chiTiet.setNgayTraPhong(ngayTra);
                chiTiet.setTienGiam(0);
                chiTiet.setThanhTien(phong.getTienCoc());

                dsChiTiet.add(chiTiet);
            }

            // 7. Lưu vào database (KHÔNG có danh sách người ở)
            boolean ketQua = phieuDatPhongDao.luuHoaDonDatPhong(hoaDon, dsChiTiet);

            if (ketQua) {
                JOptionPane.showMessageDialog(view,
                        "✅ Đặt phòng thành công!\nMã hóa đơn: " + maHD,
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                view.confirmed = true;
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view,
                        "❌ Đặt phòng thất bại! Vui lòng thử lại.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                    "❌ Lỗi: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // PHƯƠNG THỨC KIỂM TRA DỮ LIỆU - KHÔNG TẮT DIALOG KHI LỖI
    private boolean kiemTraDuLieu(Date ngayBatDau, Date ngayKetThuc) {
        // Kiểm tra ngày bắt đầu
        if (ngayBatDau == null) {
            JOptionPane.showMessageDialog(view,
                    "Vui lòng chọn ngày bắt đầu!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Kiểm tra ngày kết thúc
        if (ngayKetThuc == null) {
            JOptionPane.showMessageDialog(view,
                    "Vui lòng chọn ngày kết thúc!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Kiểm tra ngày kết thúc phải sau ngày bắt đầu
        if (ngayKetThuc.before(ngayBatDau) || ngayKetThuc.equals(ngayBatDau)) {
            JOptionPane.showMessageDialog(view,
                    "Ngày kết thúc phải sau ngày bắt đầu!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Kiểm tra đã chọn phương thức thanh toán
        if (!view.rdbtn_TienMat.isSelected() && !view.rdbtn_ChuyenKhoan.isSelected()) {
            JOptionPane.showMessageDialog(view,
                    "Vui lòng chọn phương thức thanh toán!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Kiểm tra nếu thanh toán tiền mặt thì phải nhập tiền khách đưa
        if (view.rdbtn_TienMat.isSelected()) {
            String tienKhachDua = view.txt_TienKhachDua.getText().trim();
            if (tienKhachDua.isEmpty()) {
                JOptionPane.showMessageDialog(view,
                        "Vui lòng nhập số tiền khách đưa!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // Kiểm tra tiền khách đưa có đủ không
            try {
                double tienKhach = Double.parseDouble(tienKhachDua);
                double tongTienCoc = tinhTongTienCoc();
                if (tienKhach < tongTienCoc) {
                    JOptionPane.showMessageDialog(view,
                            "Số tiền khách đưa không đủ!\n" +
                                    "Tổng tiền cọc: " + String.format("%,.0f VND", tongTienCoc) + "\n" +
                                    "Tiền khách đưa: " + String.format("%,.0f VND", tienKhach),
                            "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view,
                        "Số tiền không hợp lệ!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }

        return true;
    }

    private double tinhTongTienCoc() {
        double tongTien = 0;
        for (Phong phong : dsPhongDaChon) {
            tongTien += phong.getTienCoc();
        }
        return tongTien;
    }

    private void hienThiThongTin() {
        // THÊM KIỂM TRA NULL: Nếu khách hàng không tồn tại, để trống các trường
        if (khachHang != null) {
            view.txt_SDT.setText(khachHang.getSdt());
            view.txt_HoTen.setText(khachHang.getTenKH());
            view.txt_CCCD.setText(khachHang.getSoCCCD());
        } else {
            view.txt_SDT.setText("");
            view.txt_HoTen.setText("");
            view.txt_CCCD.setText("");
        }

        // Hiển thị danh sách phòng đã chọn
        view.model.setRowCount(0);
        for (Phong p : dsPhongDaChon) {
            view.model.addRow(new Object[]{
                    p.getMaPhong(),
                    p.getLoaiPhong().getTenLoaiPhong(),
                    p.getSucChuaToiDa(),
                    p.getGiaPhong(),
                    p.getTienCoc()
            });
        }

        // Hiển thị khuyến mãi nếu có
        if (khuyenMaiDuocChon != null) {
            view.lbl_TenKhuyenMai.setText(khuyenMaiDuocChon.getTenKM());
        } else {
            view.lbl_TenKhuyenMai.setText("Không có");
        }

        // Cập nhật ngày bắt đầu mặc định là hôm nay
        view.ngayBatDau.setDate(new java.util.Date());
    }

    private void tinhTongTien() {
        double tongTienCoc = tinhTongTienCoc();

        // Cập nhật UI
        view.lbl_TienCuaTongTienTrongPnlTongTien_1.setText(String.format("%,.0f VND", tongTienCoc));
        view.lbl_TienCuaTienCocTrongPnlTongTien_1.setText(String.format("%,.0f VND", tongTienCoc));

        // Cập nhật tiền trả lại khách
        tinhTienTraLai();
    }

    private void tinhTienTraLai() {
        try {
            String tienKhachDuaText = view.txt_TienKhachDua.getText().trim();
            if (tienKhachDuaText.isEmpty()) {
                view.lbl_TienCuaTienNhanTuKhachTrongPnlTongTien_1.setText("0 VND");
                view.lbl_TienCuaTienTraLaiKhachTrongPnlTongTien_1.setText("0 VND");
                view.textField.setText("0");
                return;
            }

            double tienKhachDua = Double.parseDouble(tienKhachDuaText);
            double tongTienCoc = Double.parseDouble(view.lbl_TienCuaTongTienTrongPnlTongTien_1.getText()
                    .replace(" VND", "").replace(",", ""));

            double tienTraLai = tienKhachDua - tongTienCoc;

            if (tienTraLai < 0) {
                view.lbl_TienCuaTienTraLaiKhachTrongPnlTongTien_1.setText("Không đủ");
                view.textField.setText("0");
            } else {
                view.lbl_TienCuaTienTraLaiKhachTrongPnlTongTien_1.setText(String.format("%,.0f VND", tienTraLai));
                view.textField.setText(String.format("%,.0f", tienTraLai));
            }

            view.lbl_TienCuaTienNhanTuKhachTrongPnlTongTien_1.setText(String.format("%,.0f VND", tienKhachDua));
        } catch (NumberFormatException e) {
            view.lbl_TienCuaTienNhanTuKhachTrongPnlTongTien_1.setText("0 VND");
            view.lbl_TienCuaTienTraLaiKhachTrongPnlTongTien_1.setText("0 VND");
            view.textField.setText("0");
        }
    }

    private void capNhatUITheoPhuongThucThanhToan() {
        boolean isChuyenKhoan = view.rdbtn_ChuyenKhoan.isSelected();

        // Ẩn/hiện phần nhập tiền mặt
        view.lbl_TienKhachDua.setVisible(!isChuyenKhoan);
        view.txt_TienKhachDua.setVisible(!isChuyenKhoan);
        view.lbl_TienTraKhach.setVisible(!isChuyenKhoan);
        view.textField.setVisible(!isChuyenKhoan);

        // Reset giá trị nếu là chuyển khoản
        if (isChuyenKhoan) {
            view.txt_TienKhachDua.setText("");
            view.textField.setText("");
            view.lbl_TienCuaTienNhanTuKhachTrongPnlTongTien_1.setText("0 VND");
            view.lbl_TienCuaTienTraLaiKhachTrongPnlTongTien_1.setText("0 VND");
        }
    }
}