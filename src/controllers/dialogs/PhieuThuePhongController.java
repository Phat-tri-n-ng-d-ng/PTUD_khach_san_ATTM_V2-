package controllers.dialogs;

import database.dao.*;
import controllers.QuanLyPhien;
import enums.PhuongThucThanhToan;
import enums.TrangThaiHoaDon;
import entitys.*;
import enums.TrangThaiKhuyenMai;
import view.dialogs.PhieuThuePhongDialogOThueDatPhong;
import view.dialogs.ThemKhachHangDialog;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class PhieuThuePhongController {

    private PhieuThuePhongDialogOThueDatPhong view;
    private KhachHang khachHang;
    private ArrayList<Phong> dsPhongDaChon;
    private KhuyenMai khuyenMaiDuocChon;
    private HoaDonDao hoaDonDao;
    private KhuyenMaiDao khuyenMaiDao;
    private KhachHangDao khachHangDao;
    private PhongDao phongDao;
    private Phong phongDangChon = null;
    private PhieuThuePhongDao phieuThuePhongDao;
    private ArrayList<NguoiO> dsNguoiO = new ArrayList<>();

    // Map để lưu danh sách người ở theo từng phòng
    private Map<String, List<Object[]>> danhSachNguoiOTheoPhong;

    public PhieuThuePhongController(PhieuThuePhongDialogOThueDatPhong view, KhachHang khachHang, ArrayList<Phong> dsPhongDaChon, KhuyenMai khuyenMaiDuocChon) {
        this.view = view;
        this.khachHang = khachHang;
        this.dsPhongDaChon = dsPhongDaChon;
        this.khuyenMaiDuocChon = khuyenMaiDuocChon;


        // Khởi tạo map để lưu danh sách người ở theo từng phòng
        danhSachNguoiOTheoPhong = new HashMap<>();

        hoaDonDao = new HoaDonDao();
        khuyenMaiDao = new KhuyenMaiDao();
        khachHangDao = new KhachHangDao();
        phongDao = new PhongDao();
        phieuThuePhongDao = new PhieuThuePhongDao();

        initController();
        hienThiThongTin();
        tinhTongTien();
        capNhatUITheoPhuongThucThanhToan();
    }

    private void initController() {
        // Sự kiện cho nút thêm người ở
        view.btn_ThemNguoiOVaoDanhSach.addActionListener(e -> themNguoiO());
        view.txt_SDT.addActionListener(e -> timKiemKhachHangBangSDT());

        view.btn_XacNhan.addActionListener(e -> {
            xacNhanPhieuThue();
        });

        // Sự kiện cho bảng danh sách phòng
        view.table_DanhSachPhong.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.table_DanhSachPhong.getSelectedRow();
                if (selectedRow != -1) {
                    String maPhong = (String) view.model_DanhSachPhong.getValueAt(selectedRow, 0);
                    view.txt_MaPhong.setText(maPhong);

                    // Lưu danh sách người ở hiện tại vào map trước khi chuyển phòng
                    if (phongDangChon != null) {
                        luuDanhSachNguoiOHienTai();
                    }

                    // Tìm phòng được chọn
                    phongDangChon = null;
                    for (Phong p : dsPhongDaChon) {
                        if (p.getMaPhong().equals(maPhong)) {
                            phongDangChon = p;
                            break;
                        }
                    }

                    // Tải danh sách người ở của phòng mới
                    taiDanhSachNguoiOTuMap();
                }
            }
        });

        // Sự kiện cho phương thức thanh toán
        view.rdbtn_TienMat.addActionListener(e -> {
            view.lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setText("Tiền mặt");
            capNhatUITheoPhuongThucThanhToan();
        });

        view.rdbtn_ChuyenKhoan.addActionListener(e -> {
            view.lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setText("Chuyển khoản");
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

        // Sự kiện cho nút xác nhận và hủy
        view.btn_XacNhan.addActionListener(e -> {
            // Kiểm tra xem có phòng nào được chọn không
            if (phongDangChon == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn một phòng để thêm người ở!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Logic lưu phiếu thuê phòng
            // TODO: Thêm logic lưu vào database ở đây

            view.confirmed = true;
            view.dispose();
        });

        view.btn_Huy.addActionListener(e -> {
            view.confirmed = false;
            view.dispose();
        });
    }

//    phương thức tìm khách hàng bằng số điện thoại
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
        view.model_DanhSachPhong.setRowCount(0);
        for (Phong p : dsPhongDaChon) {
            view.model_DanhSachPhong.addRow(new Object[]{
                    p.getMaPhong(),
                    p.getLoaiPhong().getTenLoaiPhong(),
                    p.getSucChuaToiDa(),
                    p.getGiaPhong(),
                    p.getTienCoc()
            });

            // Khởi tạo danh sách người ở trống cho mỗi phòng
            danhSachNguoiOTheoPhong.put(p.getMaPhong(), new ArrayList<>());
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
        double tongTienCoc = 0;
        double tongTienPhong = 0;

        // Tính tổng tiền cọc và tổng tiền phòng
        for (Phong p : dsPhongDaChon) {
            tongTienCoc += p.getTienCoc();
            tongTienPhong += p.getTienCoc();
        }

        // Cập nhật UI
        // Tổng tiền trả là tổng tiền cọc
        view.lbl_TienCuaTongTienTrongPnlTongTien.setText(String.format("%,.0f VND", tongTienCoc));
        // Tiền cọc là tổng tiền phòng sau khuyến mãi
        view.lbl_TienCuaTienCocTrongPnlTongTien.setText(String.format("%,.0f VND", tongTienCoc));

        // Cập nhật tiền trả lại khách
        tinhTienTraLai();
    }

    private void tinhTienTraLai() {
        try {
            String tienKhachDuaText = view.txt_TienKhachDua.getText().trim();
            if (tienKhachDuaText.isEmpty()) {
                view.lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setText("0 VND");
                view.lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setText("0 VND");
                view.txt_TienTraKhach.setText("0");
                return;
            }

            double tienKhachDua = Double.parseDouble(tienKhachDuaText);
            // Lấy tổng tiền cọc (đã được tính trong tinhTongTien)
            double tongTienCoc = Double.parseDouble(view.lbl_TienCuaTongTienTrongPnlTongTien.getText()
                    .replace(" VND", "").replace(",", ""));

            double tienTraLai = tienKhachDua - tongTienCoc;

            if (tienTraLai < 0) {
                view.lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setText("Không đủ");
                view.txt_TienTraKhach.setText("0");
            } else {
                view.lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setText(String.format("%,.0f VND", tienTraLai));
                view.txt_TienTraKhach.setText(String.format("%,.0f", tienTraLai));
            }

            view.lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setText(String.format("%,.0f VND", tienKhachDua));
        } catch (NumberFormatException e) {
            view.lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setText("0 VND");
            view.lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setText("0 VND");
            view.txt_TienTraKhach.setText("0");
        }
    }

    private void capNhatUITheoPhuongThucThanhToan() {
        boolean isChuyenKhoan = view.rdbtn_ChuyenKhoan.isSelected();

        // Ẩn/hiện phần nhập tiền mặt (chỉ ẩn phần nhập liệu, không ẩn panel tổng tiền)
        view.lbl_TienKhachDua.setVisible(!isChuyenKhoan);
        view.txt_TienKhachDua.setVisible(!isChuyenKhoan);
        view.lbl_TienTraKhach.setVisible(!isChuyenKhoan);
        view.txt_TienTraKhach.setVisible(!isChuyenKhoan);

        // KHÔNG ẩn phần tiền trong panel tổng tiền - chỉ reset giá trị nếu là chuyển khoản
        if (isChuyenKhoan) {
            view.txt_TienKhachDua.setText("");
            view.txt_TienTraKhach.setText("");
            view.lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setText("0 VND");
            view.lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setText("0 VND");
        }
    }

    // Phương thức lưu danh sách người ở hiện tại vào map
    private void luuDanhSachNguoiOHienTai() {
        if (phongDangChon == null) return;

        String maPhong = phongDangChon.getMaPhong();
        List<Object[]> danhSach = new ArrayList<>();

        // Lấy tất cả dòng từ bảng
        for (int i = 0; i < view.model_DanhSachNguoiO.getRowCount(); i++) {
            Object[] row = new Object[5];
            for (int j = 0; j < 5; j++) {
                row[j] = view.model_DanhSachNguoiO.getValueAt(i, j);
            }
            danhSach.add(row);
        }

        // Lưu vào map
        danhSachNguoiOTheoPhong.put(maPhong, danhSach);
    }

    // Phương thức tải danh sách người ở từ map
    private void taiDanhSachNguoiOTuMap() {
        if (phongDangChon == null) return;

        String maPhong = phongDangChon.getMaPhong();

        // Xóa bảng hiện tại
        view.model_DanhSachNguoiO.setRowCount(0);

        // Kiểm tra xem phòng này đã có danh sách người ở chưa
        if (danhSachNguoiOTheoPhong.containsKey(maPhong)) {
            List<Object[]> danhSach = danhSachNguoiOTheoPhong.get(maPhong);

            // Thêm từng người vào bảng
            for (Object[] nguoi : danhSach) {
                view.model_DanhSachNguoiO.addRow(nguoi);
            }
        }
    }

    private void themNguoiO() {
        // Kiểm tra xem có phòng nào được chọn không
        if (phongDangChon == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một phòng trước khi thêm người ở!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra số lượng người ở hiện tại KHÔNG được vượt quá sức chứa tối đa của phòng
        int soNguoiHienTai = view.model_DanhSachNguoiO.getRowCount();
        int sucChuaToiDa = phongDangChon.getSucChuaToiDa();

        if (soNguoiHienTai >= sucChuaToiDa) {
            JOptionPane.showMessageDialog(view,
                    "Số lượng người ở đã đạt tối đa cho phòng " + phongDangChon.getMaPhong() +
                            "\nSức chứa tối đa: " + sucChuaToiDa + " người",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ten = view.txt_HoTenNguoiO.getText().trim();
        String sdt = view.txt_SDTNguoiO.getText().trim();
        String cccd = view.txt_CCCDNguoiO.getText().trim();
        java.util.Date ngaySinhDate = view.ngaySinhNguoiO.getDate();
        LocalDate ngaySinh = null;
        if (ngaySinhDate != null) {
            ngaySinh = ngaySinhDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        String gioiTinh = view.rdbtn_NamNguoiO.isSelected() ? "Nam" : "Nữ";

        if (ten.isEmpty() || sdt.isEmpty() || cccd.isEmpty() || ngaySinh == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng điền đầy đủ thông tin người ở!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Thêm vào bảng
        Object[] nguoiO = new Object[]{ten, ngaySinh.toString(), gioiTinh, sdt, cccd};
        view.model_DanhSachNguoiO.addRow(nguoiO);

        // Cập nhật vào map
        String maPhong = phongDangChon.getMaPhong();
        if (!danhSachNguoiOTheoPhong.containsKey(maPhong)) {
            danhSachNguoiOTheoPhong.put(maPhong, new ArrayList<>());
        }
        danhSachNguoiOTheoPhong.get(maPhong).add(nguoiO);

        // Xóa các trường nhập
        view.txt_HoTenNguoiO.setText("");
        view.txt_SDTNguoiO.setText("");
        view.txt_CCCDNguoiO.setText("");
        view.ngaySinhNguoiO.setDate(null);

        // Cập nhật số lượng người còn lại có thể thêm
        int soNguoiConLai = sucChuaToiDa - (soNguoiHienTai + 1);
        JOptionPane.showMessageDialog(view,
                "Đã thêm người ở thành công!\n" +
                        "Số người còn lại có thể thêm: " + soNguoiConLai,
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    //  CÁC PHƯƠNG THỨC QUAN TRỌNG

    // Phương thức chuyển đổi từ Map sang ArrayList<NguoiO>
    private ArrayList<NguoiO> layDanhSachNguoiOTuMap() {
        ArrayList<NguoiO> danhSach = new ArrayList<>();

        if (danhSachNguoiOTheoPhong == null || danhSachNguoiOTheoPhong.isEmpty()) {
            return danhSach;
        }

        for (Map.Entry<String, List<Object[]>> entry : danhSachNguoiOTheoPhong.entrySet()) {
            String maPhong = entry.getKey();
            List<Object[]> danhSachNguoi = entry.getValue();

            if (danhSachNguoi == null || danhSachNguoi.isEmpty()) {
                continue;
            }

            // Tìm đối tượng Phong từ mã phòng
            Phong phong = null;
            for (Phong p : dsPhongDaChon) {
                if (p.getMaPhong().equals(maPhong)) {
                    phong = p;
                    break;
                }
            }

            if (phong == null) {
                continue;
            }

            // Chuyển từ Object[] sang NguoiO
            for (Object[] obj : danhSachNguoi) {
                try {
                    String ten = (String) obj[0];
                    LocalDate ngaySinh = LocalDate.parse((String) obj[1]);
                    String gioiTinhStr = (String) obj[2];
                    boolean gioiTinh = gioiTinhStr.equals("Nam");
                    String sdt = (String) obj[3];
                    String cccd = (String) obj[4];

                    NguoiO nguoiO = new NguoiO(ten, ngaySinh, sdt, cccd, gioiTinh);
                    nguoiO.setPhong(phong);

                    danhSach.add(nguoiO);
                } catch (Exception e) {
                }
            }
        }

        return danhSach;
    }

    // 2. Kiểm tra sức chứa phòng
    private boolean kiemTraSucChuaPhong() {
        for (Phong phong : dsPhongDaChon) {
            String maPhong = phong.getMaPhong();
            int soNguoiHienTai = 0;

            if (danhSachNguoiOTheoPhong.containsKey(maPhong)) {
                soNguoiHienTai = danhSachNguoiOTheoPhong.get(maPhong).size();
            }

            if (soNguoiHienTai > phong.getSucChuaToiDa()) {
                JOptionPane.showMessageDialog(view,
                        "Phòng " + maPhong + " vượt quá sức chứa!\n" +
                                "Sức chứa tối đa: " + phong.getSucChuaToiDa() + "\n" +
                                "Số người đã thêm: " + soNguoiHienTai,
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    // ==================== PHƯƠNG THỨC CHÍNH ====================
    private void xacNhanPhieuThue() {
        try {
            // KIỂM TRA: Đã có khách hàng chưa?
            if (khachHang == null) {
                JOptionPane.showMessageDialog(view,
                        "Vui lòng tìm hoặc thêm khách hàng trước khi thuê phòng!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 1. Lấy thông tin từ giao diện
            Date ngayBatDau = view.ngayBatDau.getDate();
            Date ngayKetThuc = view.ngayKetThuc.getDate();

            // Kiểm tra dữ liệu - QUAN TRỌNG: KHÔNG TẮT DIALOG NẾU LỖI
            if (!kiemTraDuLieu(ngayBatDau, ngayKetThuc)) {
                return;
            }

            // 2. Tính toán thông tin
            int soNgayO = phieuThuePhongDao.tinhSoNgayO(ngayBatDau, ngayKetThuc);
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
            String maHD = phieuThuePhongDao.taoMaHDMoi();
            if (maHD == null) {
                JOptionPane.showMessageDialog(view,
                        "Không thể tạo mã hóa đơn!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra xem mã có trùng không (thêm kiểm tra phụ)
            if (hoaDonDao.kiemTraMaHDTonTai(maHD)) {
                // Tạo lại mã
                maHD = phieuThuePhongDao.taoMaHDMoi();
            }

            // 5. Tạo đối tượng HoaDon
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMaHD(maHD);
            hoaDon.setNgayLap(LocalDateTime.now());
            hoaDon.setTrangThai(TrangThaiHoaDon.PhieuThuePhong);
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
            hoaDon.setTienThue(0); // Chưa tính thuế
            hoaDon.setPhiDoiPhong(0); // Chưa có phí đổi phòng
            hoaDon.setTongTienThanhToan(); // Tự động tính
            hoaDon.setTienGiamTheoHangKH(0); // Chưa giảm theo hạng KH
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
                chiTiet.setTienGiam(0); // Tiền giảm = 0 khi thuê
                chiTiet.setThanhTien(phong.getTienCoc()); // Thành tiền = tiền cọc

                dsChiTiet.add(chiTiet);
            }

            // 7. Lấy danh sách người ở từ map
            dsNguoiO = layDanhSachNguoiOTuMap();

            // Kiểm tra sức chứa phòng
            if (!kiemTraSucChuaPhong()) {
                return;
            }

            // 8. Lưu vào database
            boolean ketQua = phieuThuePhongDao.luuHoaDonThuePhong(hoaDon, dsChiTiet, dsNguoiO);

            if (ketQua) {
                JOptionPane.showMessageDialog(view,
                        "✅ Thuê phòng thành công!\nMã hóa đơn: " + maHD,
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                view.confirmed = true;
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view,
                        "❌ Thuê phòng thất bại! Vui lòng thử lại.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                    "❌ Lỗi: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean kiemTraDuLieu(Date ngayBatDau, Date ngayKetThuc) {
        // Kiểm tra ngày
        if (ngayBatDau == null || ngayKetThuc == null) {
            JOptionPane.showMessageDialog(view,
                    "Vui lòng chọn ngày bắt đầu và ngày kết thúc!",
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
}