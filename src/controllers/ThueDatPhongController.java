package controllers;

import controllers.dialogs.PhieuDatPhongController;
import controllers.dialogs.PhieuThuePhongController;
import database.dao.*;
import entitys.*;
import enums.TrangThaiKhuyenMai;
import enums.TrangThaiPhong;
import view.dialogs.PhieuDoiPhongDialog;
import view.dialogs.PhieuPhongDatDialog;
import view.panels.ThueDatPhongPanel;
import view.dialogs.PhieuThuePhongDialogOThueDatPhong;
import view.dialogs.PhieuDatPhongDialogOThueDatPhong;

import javax.swing.*;

import controllers.dialogs.PhieuDoiPhongDatController;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class ThueDatPhongController{

    private HoaDonDao hoaDonDao;
    private ThueDatPhongPanel thueDatPhongPanel;
    private KhuyenMaiDao khuyenMaiDao;
    private ArrayList<Phong> danhSachPhong;
    private ArrayList<Phong> danhSachPhongHienThi;
    private ArrayList<KhuyenMai> danhSachKhuyenMai;
    private ArrayList<KhuyenMai> danhSachKhuyenMaiHienThi;
    private PhongDao phongDao;
    private KhachHangDao khachHangDao;
    private ArrayList<Phong> dsPhongDaChon = new ArrayList<>();
    private KhachHang khachHangHienTai;
    private ArrayList<Phong> dsPhongTheoKH = new ArrayList<>();

    public ThueDatPhongController(ThueDatPhongPanel thueDatPhongPanel){
        this.thueDatPhongPanel = thueDatPhongPanel;
        phongDao = new PhongDao();
        khachHangDao = new KhachHangDao();
        khuyenMaiDao = new KhuyenMaiDao();
        danhSachKhuyenMai = khuyenMaiDao.getTatCaKhuyenMai();
        danhSachKhuyenMaiHienThi = new ArrayList<>();
        hoaDonDao = new HoaDonDao();

        // Gán sự kiện
        thueDatPhongPanel.txt_TimSoDienThoai.addActionListener(e -> getKhachHang());
        thueDatPhongPanel.btn_Tim.addActionListener(e -> getKhachHang());

        thueDatPhongPanel.chckbx_phongDat.addActionListener(e-> LocPhong());
        thueDatPhongPanel.chckbx_phongThue.addActionListener(e-> LocPhong());
        thueDatPhongPanel.chckbx_phongTrong.addActionListener(e-> LocPhong());

        thueDatPhongPanel.cbb_LoaiPhong.addActionListener(e -> LocPhong());
        thueDatPhongPanel.cbb_KhuyenMai.addActionListener(e -> LocPhong());
        thueDatPhongPanel.btn_ApDung.addActionListener(e -> {
            TuDongCapNhatTrangThaiPhong_TheoKhoangNgay();
        });

        thueDatPhongPanel.btn_ThuePhong.addActionListener(e -> {
            openPhieuThuePhongDialog();
        });

        thueDatPhongPanel.btn_DatPhong.addActionListener(e -> {
            openPhieuDatPhongDialog();
        });

        // DocumentListener để tự động reset khi xóa hết số điện thoại
        thueDatPhongPanel.txt_TimSoDienThoai.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Không làm gì
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Khi xóa chữ, kiểm tra nếu text rỗng thì reset
                if (thueDatPhongPanel.txt_TimSoDienThoai.getText().trim().isEmpty()) {
                    resetKhiXoaHetSoDT();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Không làm gì
            }
        });
        getTatCaPhong();
        getKhuyenMai(); // tạo lại combobox
    }

    public void getTatCaPhong(){
        danhSachPhong = phongDao.getDanhSachPhong();
        // Lọc dsPhongDaChon, giữ lại những phòng còn trống
        dsPhongDaChon.removeIf(phong -> !phong.getTrangThai().equals(TrangThaiPhong.Trong));
        danhSachPhongHienThi = danhSachPhong;
        HienThiDanhSachPhong(danhSachPhongHienThi);

        // Cập nhật khuyến mãi dựa trên phòng đã chọn
        capNhatKhuyenMaiTheoPhongDaChon();
    }

    private void HienThiDanhSachPhong(ArrayList<Phong> danhSachPhongHienThi) {
        if (thueDatPhongPanel.danhSachPhongPanel == null) {
            return;
        }

        // Xóa nội dung cũ
        thueDatPhongPanel.danhSachPhongPanel.removeAll();

        // Cài đặt Layout
        thueDatPhongPanel.danhSachPhongPanel.setLayout(new GridBagLayout());
        thueDatPhongPanel.danhSachPhongPanel.setBackground(new Color(236, 247, 255));

        // Cài đặt viền (padding) cho toàn bộ panel, chỉ cách mép 10px
        thueDatPhongPanel.danhSachPhongPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (danhSachPhongHienThi.isEmpty()) {
            JLabel lblNoRoom = new JLabel("Không có phòng nào để hiển thị", SwingConstants.CENTER);
            lblNoRoom.setFont(new Font("Times New Roman", Font.BOLD, 18));
            lblNoRoom.setForeground(Color.GRAY);
            thueDatPhongPanel.danhSachPhongPanel.add(lblNoRoom);
        } else {
            GridBagConstraints gbc = new GridBagConstraints();
            // insets: Khoảng cách giữa các nút (Top, Left, Bottom, Right) -> 10px mỗi bên
            gbc.insets = new Insets(10, 10, 10, 10);

            // QUAN TRỌNG: Để lấp đầy chiều ngang
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1.0;
            gbc.weighty = 0;

            int col = 0;
            int row = 0;

            for (Phong phong : danhSachPhongHienThi) {
                gbc.gridx = col;
                gbc.gridy = row;

                JButton phongButton = createPhongButton(phong);
                thueDatPhongPanel.danhSachPhongPanel.add(phongButton, gbc);

                col++;

                if (col >= 5) { // 5 phòng 1 hàng
                    col = 0;
                    row++;
                }
            }

            // XỬ LÝ NẾU HÀNG CUỐI KHÔNG ĐỦ 5 PHÒNG (Để các nút không bị giãn phình to ra)
            if (col > 0 && col < 5) {
                while (col < 5) {
                    gbc.gridx = col;
                    gbc.gridy = row;
                    // Thêm panel rỗng vào các ô còn thiếu để giữ kích thước cột đều nhau
                    JPanel dummy = new JPanel();
                    dummy.setOpaque(false);
                    thueDatPhongPanel.danhSachPhongPanel.add(dummy, gbc);
                    col++;
                }
                row++; // Tăng row để filler nằm dưới cùng
            }

            // QUAN TRỌNG: ĐẨY TOÀN BỘ NỘI DUNG LÊN TRÊN
            // Tạo một thành phần trống chiếm hết khoảng trống dọc còn lại ở dưới cùng
            GridBagConstraints gbcFiller = new GridBagConstraints();
            gbcFiller.gridx = 0;
            gbcFiller.gridy = row; // Hàng tiếp theo sau hàng cuối cùng
            gbcFiller.gridwidth = 5; // Chiếm hết bề ngang
            gbcFiller.weighty = 1.0; // Trọng số cao để chiếm hết khoảng trống dọc thừa
            gbcFiller.fill = GridBagConstraints.BOTH;

            JPanel fillerPanel = new JPanel();
            fillerPanel.setOpaque(false);
            thueDatPhongPanel.danhSachPhongPanel.add(fillerPanel, gbcFiller);
        }

        // Refresh UI
        thueDatPhongPanel.danhSachPhongPanel.revalidate();
        thueDatPhongPanel.danhSachPhongPanel.repaint();
    }

    private JButton createPhongButton(Phong phong) {
        JButton phongButton = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                g2.setColor(getForeground());
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 15, 15);

                super.paintComponent(g);
            }
        };

        phongButton.setLayout(new BorderLayout());
        phongButton.setPreferredSize(new Dimension(100, 150));
        phongButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        phongButton.setBorderPainted(false);
        phongButton.setFocusPainted(false);
        phongButton.setContentAreaFilled(false);

        // Xác định màu sắc
        Color bgColor = Color.GRAY;
        Color borderColor = Color.BLACK;
        Color textColor = Color.BLACK;
        String trangThaiText = "KHÔNG XÁC ĐỊNH";
        boolean daChon = dsPhongDaChon.contains(phong);

        if(phong.getTrangThai().equals(TrangThaiPhong.Trong)){
            if (daChon) {
                bgColor = new Color(200, 230, 255);
                borderColor = new Color(0, 100, 255);
                textColor = Color.BLACK;
                trangThaiText = "ĐÃ CHỌN";
            } else {
                bgColor = Color.WHITE;
                borderColor = new Color(52, 152, 219);
                textColor = Color.BLACK;
                trangThaiText = "TRỐNG";
            }
        } else if(phong.getTrangThai().equals(TrangThaiPhong.DangSuDung)){
            bgColor = new Color(144, 238, 144);
            borderColor = new Color(46, 204, 113);
            textColor = Color.BLACK;
            trangThaiText = "ĐANG THUÊ";
        } else {
            bgColor = new Color(255, 182, 193);
            borderColor = new Color(231, 76, 60);
            textColor = Color.BLACK;
            trangThaiText = "ĐÃ ĐẶT";
        }

        phongButton.setBackground(bgColor);
        phongButton.setForeground(borderColor);

        // Panel thông tin
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(5, 1, 5, 5));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblMaPhong = new JLabel("Phòng: " + phong.getMaPhong(), SwingConstants.CENTER);
        lblMaPhong.setFont(new Font("Arial", Font.BOLD, 16));
        lblMaPhong.setForeground(textColor);

        JLabel lblLoaiPhong = new JLabel(phong.getLoaiPhong().getTenLoaiPhong(), SwingConstants.CENTER);
        lblLoaiPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        lblLoaiPhong.setForeground(textColor);

        JLabel lblSucChua = new JLabel("Sức chứa: " + phong.getSucChuaToiDa(), SwingConstants.CENTER);
        lblSucChua.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSucChua.setForeground(textColor);

        double giaPhong = phong.getGiaPhong();
        JLabel lblGia = new JLabel(String.format("%,.0f VNĐ", giaPhong), SwingConstants.CENTER);
        lblGia.setFont(new Font("Arial", Font.BOLD, 12));
        lblGia.setForeground(new Color(10, 100, 189));

        JLabel lblTrangThai = new JLabel(trangThaiText, SwingConstants.CENTER);
        lblTrangThai.setFont(new Font("Arial", Font.BOLD, 12));
        lblTrangThai.setForeground(borderColor.darker());

        infoPanel.add(lblMaPhong);
        infoPanel.add(lblLoaiPhong);
        infoPanel.add(lblSucChua);
        infoPanel.add(lblGia);
        infoPanel.add(lblTrangThai);

        phongButton.add(infoPanel, BorderLayout.CENTER);

        // Thêm hiệu ứng hover
        Color finalBgColor = bgColor;
        phongButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (phong.getTrangThai().equals(TrangThaiPhong.Trong)) {
                    phongButton.setBackground(finalBgColor.brighter());
                    phongButton.repaint();
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                phongButton.setBackground(finalBgColor);
                phongButton.repaint();
            }
        });

        // Thêm ActionListener
        phongButton.addActionListener(e -> {
            if(phong.getTrangThai().equals(TrangThaiPhong.Trong)) {
                if (daChon) {
                    dsPhongDaChon.remove(phong);
                } else {
                    dsPhongDaChon.add(phong);
                }
                HienThiDanhSachPhong(danhSachPhongHienThi);
            } else if (phong.getTrangThai().equals(TrangThaiPhong.DaDat)) {
                Phong p = phongDao.timPhongBangMa(phong.getMaPhong());
                HoaDon hd = hoaDonDao.timHoaDonTheoPhongDaDat(p.getMaPhong());

                if (hd == null) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Phòng này thuộc phòng thuê!",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                PhieuPhongDatDialog phieuPhongDatdialog = new PhieuPhongDatDialog();
                new PhieuPhongDatController(phieuPhongDatdialog, p);
                phieuPhongDatdialog.btnDoiPhong.addActionListener(ev -> {
                    PhieuDoiPhongDialog phieuDoiPhongDialog = new PhieuDoiPhongDialog();
                    new PhieuDoiPhongDatController(phieuDoiPhongDialog, p);
                    phieuDoiPhongDialog.setLocationRelativeTo(null);
                    phieuDoiPhongDialog.setModal(true);
                    phieuDoiPhongDialog.setVisible(true);
                    phieuPhongDatdialog.dispose();
                });
                phieuPhongDatdialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        refreshDanhSachPhong();
                    }
                });

                phieuPhongDatdialog.setLocationRelativeTo(null);
                phieuPhongDatdialog.setModal(true);
                phieuPhongDatdialog.setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(null,
                        "Phòng " + phong.getMaPhong() + " không khả dụng!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        return phongButton;
    }

    private void getKhachHang(){
        String soDTKhachHang = thueDatPhongPanel.txt_TimSoDienThoai.getText().trim();

        if (soDTKhachHang.isEmpty()) {
            showCustomMessage("Vui lòng nhập số điện thoại!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            khachHangHienTai = null;
            dsPhongTheoKH.clear();
            getTatCaPhong();
            return;
        }

        KhachHang khachHang = khachHangDao.TimKhachHang(soDTKhachHang,"SDT");
        if(khachHang != null){
            khachHangHienTai = khachHang;
            dsPhongTheoKH = hoaDonDao.getPhongTheoSDTKhachHang(soDTKhachHang);

            // Kiểm tra xem có phòng nào không
            if (dsPhongTheoKH.isEmpty()) {
                showCustomMessage("Khách hàng " + khachHang.getTenKH() +
                                " không có phòng nào đang thuê/đặt.",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);

                // Đặt tất cả checkbox về false
                SwingUtilities.invokeLater(() -> {
                    thueDatPhongPanel.chckbx_phongTrong.setSelected(false);
                    thueDatPhongPanel.chckbx_phongThue.setSelected(false);
                    thueDatPhongPanel.chckbx_phongDat.setSelected(false);

                    // Hiển thị tất cả phòng
                    getTatCaPhong();
                });
            } else {
                // Đảm bảo cập nhật UI trên EDT
                SwingUtilities.invokeLater(() -> {
                    // Reset tất cả checkbox
                    thueDatPhongPanel.chckbx_phongTrong.setSelected(false);
                    thueDatPhongPanel.chckbx_phongThue.setSelected(false);
                    thueDatPhongPanel.chckbx_phongDat.setSelected(false);

                    // Kiểm tra trạng thái phòng của khách hàng
                    boolean coPhongThue = false;
                    boolean coPhongDat = false;

                    for (Phong p : dsPhongTheoKH) {
                        if (p.getTrangThai().equals(TrangThaiPhong.DangSuDung)) {
                            coPhongThue = true;
                        } else if (p.getTrangThai().equals(TrangThaiPhong.DaDat)) {
                            coPhongDat = true;
                        }
                    }

                    // Tích checkbox dựa trên trạng thái phòng
                    if (coPhongThue) {
                        thueDatPhongPanel.chckbx_phongThue.setSelected(true);
                    }
                    // Gọi LocPhong để hiển thị danh sách phòng theo checkbox đã tích
                    LocPhong();
                });
            }
        } else {
            // Hiển thị thông báo đơn giản khi không tìm thấy khách hàng
            showCustomMessage(
                    "Không tìm thấy khách hàng với số điện thoại: " + soDTKhachHang,
                    "Không tìm thấy",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Reset thông tin khách hàng
            khachHangHienTai = null;
            dsPhongTheoKH.clear();

            // Bỏ tích tất cả checkbox và hiển thị tất cả phòng
            SwingUtilities.invokeLater(() -> {
                thueDatPhongPanel.chckbx_phongTrong.setSelected(false);
                thueDatPhongPanel.chckbx_phongThue.setSelected(false);
                thueDatPhongPanel.chckbx_phongDat.setSelected(false);
                getTatCaPhong();
            });
        }
    }



    private KhuyenMai getKhuyenMaiDuocChon() {
        String tenKM = (String) thueDatPhongPanel.cbb_KhuyenMai.getSelectedItem();
        if (tenKM == null || tenKM.equals("Không có khuyến mãi")) {
            return null;
        }

        for (KhuyenMai km : danhSachKhuyenMaiHienThi) {
            if (km.getTenKM().equals(tenKM)) {
                return km;
            }
        }
        return null;
    }

    // tự động reset khi xóa hết số điện thoại
    private void resetKhiXoaHetSoDT() {
        // Reset thông tin khách hàng
        khachHangHienTai = null;
        dsPhongTheoKH.clear();

        // Bỏ tích tất cả checkbox (KHÔNG TÍCH BẤT KỲ CHECKBOX NÀO)
        thueDatPhongPanel.chckbx_phongTrong.setSelected(false);
        thueDatPhongPanel.chckbx_phongThue.setSelected(false);
        thueDatPhongPanel.chckbx_phongDat.setSelected(false);

        // Reset combobox loại phòng về "Tất cả"
        thueDatPhongPanel.cbb_LoaiPhong.setSelectedItem("Tất cả");

        // Reset combobox khuyến mãi
        thueDatPhongPanel.cbb_KhuyenMai.removeAllItems();
        thueDatPhongPanel.cbb_KhuyenMai.addItem("Không có khuyến mãi");

        // Xóa danh sách phòng đã chọn
        clearDsPhongDaChon();

        // Load lại toàn bộ danh sách phòng (tất cả phòng, không lọc)
        getTatCaPhong();

        // Reset ô tìm kiếm về rỗng (nếu cần)
        thueDatPhongPanel.txt_TimSoDienThoai.setText("");
    }

    // Cập nhật khuyến mãi dựa trên phòng đã chọn
    private void capNhatKhuyenMaiTheoPhongDaChon() {
        // Lấy danh sách loại phòng từ các phòng đã chọn
        ArrayList<String> loaiPhongDaChon = new ArrayList<>();
        for (Phong p : dsPhongDaChon) {
            String loai = p.getLoaiPhong().getTenLoaiPhong().trim();
            // Chuyển về dạng chuẩn (viết hoa chữ cái đầu)
            loai = chuanHoaTenLoaiPhong(loai);
            if (!loaiPhongDaChon.contains(loai)) {
                loaiPhongDaChon.add(loai);
            }
        }

        // Lấy ngày hiện tại để kiểm tra khuyến mãi
        LocalDateTime ngayHienTai = LocalDateTime.now();

        // Lọc khuyến mãi đang hoạt động và áp dụng cho các loại phòng đã chọn
        ArrayList<KhuyenMai> dsKhuyenMaiPhuHop = new ArrayList<>();

        if (!loaiPhongDaChon.isEmpty()) {
            for (KhuyenMai km : danhSachKhuyenMai) {
                // Kiểm tra khuyến mãi đang hoạt động
                if (km.getTrangThai().equals(TrangThaiKhuyenMai.DangHoatDong)) {
                    // Kiểm tra ngày hiện tại có nằm trong thời gian khuyến mãi không
                    if (km.getNgayBatDau() != null && km.getNgayketThuc() != null) {
                        if (ngayHienTai.isAfter(km.getNgayBatDau()) &&
                                ngayHienTai.isBefore(km.getNgayketThuc())) {

                            // Lấy điều kiện áp dụng và chuẩn hóa
                            String dieuKien = km.getDieuKienApDung();
                            if (dieuKien == null || dieuKien.trim().isEmpty()) {
                                continue;
                            }

                            dieuKien = dieuKien.trim();

                            // Kiểm tra nếu là "Tất cả"
                            if (dieuKien.equalsIgnoreCase("Tất cả")) {
                                dsKhuyenMaiPhuHop.add(km);
                                continue;
                            }

                            // Tách các loại phòng trong điều kiện
                            String[] loaiPhongKM = dieuKien.split(",");

                            // Kiểm tra từng loại phòng
                            for (String loaiKM : loaiPhongKM) {
                                String tenLoai = loaiKM.trim();
                                // Chuẩn hóa tên loại phòng
                                tenLoai = chuanHoaTenLoaiPhong(tenLoai);

                                // Kiểm tra xem loại phòng này có trong danh sách đã chọn không
                                if (loaiPhongDaChon.contains(tenLoai)) {
                                    dsKhuyenMaiPhuHop.add(km);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        // Cập nhật combobox khuyến mãi
        thueDatPhongPanel.cbb_KhuyenMai.removeAllItems();

        if (dsKhuyenMaiPhuHop.isEmpty()) {
            thueDatPhongPanel.cbb_KhuyenMai.addItem("Không có khuyến mãi");
            danhSachKhuyenMaiHienThi.clear();
        } else {
            danhSachKhuyenMaiHienThi.clear();
            for (KhuyenMai km : dsKhuyenMaiPhuHop) {
                thueDatPhongPanel.cbb_KhuyenMai.addItem(km.getTenKM());
                danhSachKhuyenMaiHienThi.add(km);
            }
        }
    }

    // chuẩn hóa tên loại phòng (viết hoa chữ cái đầu, giữ nguyên phần còn lại)
    private String chuanHoaTenLoaiPhong(String tenLoai) {
        if (tenLoai == null || tenLoai.trim().isEmpty()) {
            return tenLoai;
        }

        tenLoai = tenLoai.trim();

        // Danh sách các tên loại phòng chuẩn trong hệ thống
        switch (tenLoai.toLowerCase()) {
            case "family room":
                return "Family Room";
            case "suite":
                return "Suite";
            case "standard":
                return "Standard";
            case "superior":
                return "Superior";
            case "deluxe":
                return "Deluxe";
            default:
                // Viết hoa chữ cái đầu của từng từ
                String[] words = tenLoai.split("\\s+");
                StringBuilder result = new StringBuilder();
                for (String word : words) {
                    if (!word.isEmpty()) {
                        result.append(word.substring(0, 1).toUpperCase())
                                .append(word.substring(1).toLowerCase())
                                .append(" ");
                    }
                }
                return result.toString().trim();
        }
    }

    private void LocPhong() {
        ArrayList<Phong> danhSachTam;

        if (khachHangHienTai != null && !dsPhongTheoKH.isEmpty()) {
            danhSachTam = new ArrayList<>(dsPhongTheoKH);
        } else {
            if (danhSachPhong == null) {
                return;
            }
            danhSachTam = new ArrayList<>(danhSachPhong);
        }

        // Lọc theo loại phòng
        String loaiPhongChon = (String) thueDatPhongPanel.cbb_LoaiPhong.getSelectedItem();
        if (loaiPhongChon != null && !loaiPhongChon.equals("Tất cả")) {
            danhSachTam.removeIf(phong -> !phong.getLoaiPhong().getTenLoaiPhong().equalsIgnoreCase(loaiPhongChon));
        }

        // Lọc theo khuyến mãi (chỉ khi không phải "Không có khuyến mãi")
        String khuyenMaiChon = (String) thueDatPhongPanel.cbb_KhuyenMai.getSelectedItem();
        if (khuyenMaiChon != null && !khuyenMaiChon.equals("Không có khuyến mãi")) {
            danhSachTam = locPhongTheoKhuyenMai(danhSachTam, khuyenMaiChon);
        }

        // Lọc theo trạng thái
        boolean locTrangThai = false;
        ArrayList<TrangThaiPhong> trangThaiCanLoc = new ArrayList<>();
        if (thueDatPhongPanel.chckbx_phongTrong.isSelected()) {
            trangThaiCanLoc.add(TrangThaiPhong.Trong);
            locTrangThai = true;
        }
        if (thueDatPhongPanel.chckbx_phongThue.isSelected()) {
            trangThaiCanLoc.add(TrangThaiPhong.DangSuDung);
            locTrangThai = true;
        }
        if (thueDatPhongPanel.chckbx_phongDat.isSelected()) {
            trangThaiCanLoc.add(TrangThaiPhong.DaDat);
            locTrangThai = true;
        }

        if (locTrangThai) {
            danhSachTam.removeIf(phong -> !trangThaiCanLoc.contains(phong.getTrangThai()));
        }

        danhSachPhongHienThi = danhSachTam;
        HienThiDanhSachPhong(danhSachPhongHienThi);
    }

    // Sửa phương thức locPhongTheoKhuyenMai để sử dụng danhSachKhuyenMaiHienThi
    private ArrayList<Phong> locPhongTheoKhuyenMai(ArrayList<Phong> danhSach, String tenKhuyenMai) {
        ArrayList<Phong> ketQua = new ArrayList<>();
        KhuyenMai khuyenMaiChon = null;

        // Sử dụng danhSachKhuyenMaiHienThi thay vì danhSachKhuyenMai
        for (KhuyenMai km : danhSachKhuyenMaiHienThi) {
            if (km.getTenKM().equals(tenKhuyenMai)) {
                khuyenMaiChon = km;
                break;
            }
        }

        if (khuyenMaiChon != null) {
            String dieuKien = khuyenMaiChon.getDieuKienApDung();
            if (dieuKien == null) {
                return danhSach;
            }

            dieuKien = dieuKien.trim();

            // Nếu là "Tất cả" thì trả về toàn bộ danh sách
            if (dieuKien.equalsIgnoreCase("Tất cả")) {
                return danhSach;
            }

            // Tách các loại phòng và chuẩn hóa
            String[] loaiPhongKhuyenMai = dieuKien.split(",");
            ArrayList<String> loaiPhongKMList = new ArrayList<>();
            for (String loai : loaiPhongKhuyenMai) {
                loaiPhongKMList.add(chuanHoaTenLoaiPhong(loai.trim()));
            }

            for (Phong phong : danhSach) {
                String tenLoaiPhong = chuanHoaTenLoaiPhong(phong.getLoaiPhong().getTenLoaiPhong());
                if (loaiPhongKMList.contains(tenLoaiPhong)) {
                    ketQua.add(phong);
                }
            }
        }

        return ketQua.isEmpty() ? danhSach : ketQua;
    }

    // Thêm phương thức mới để cập nhật khuyến mãi theo khoảng ngày
    private void capNhatKhuyenMaiTheoKhoangNgay() {
        Date ngayBatDau = thueDatPhongPanel.ngayBatDau.getDate();
        Date ngayKetThuc = thueDatPhongPanel.ngayKetThuc.getDate();

        if (ngayBatDau == null || ngayKetThuc == null) {
            return; // Không làm gì nếu chưa chọn ngày
        }

        // Chuyển đổi Date sang LocalDateTime
        LocalDateTime tuNgay = ngayBatDau.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime denNgay = ngayKetThuc.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        // Lấy danh sách khuyến mãi trong khoảng thời gian
        ArrayList<KhuyenMai> dsKhuyenMaiTrongKhoang = khuyenMaiDao.locKhuyenMaiTheoKhoangThoiGian(tuNgay, denNgay);

        // Lọc chỉ lấy khuyến mãi đang hoạt động
        ArrayList<KhuyenMai> dsKhuyenMaiDangHoatDong = new ArrayList<>();
        for (KhuyenMai km : dsKhuyenMaiTrongKhoang) {
            if (km.getTrangThai().equals(TrangThaiKhuyenMai.DangHoatDong)) {
                dsKhuyenMaiDangHoatDong.add(km);
            }
        }

        // Cập nhật combobox
        thueDatPhongPanel.cbb_KhuyenMai.removeAllItems();

        if (dsKhuyenMaiDangHoatDong.isEmpty()) {
            thueDatPhongPanel.cbb_KhuyenMai.addItem("Không có khuyến mãi");
            danhSachKhuyenMaiHienThi.clear();
        } else {
            thueDatPhongPanel.cbb_KhuyenMai.addItem("Chọn");
            for (KhuyenMai km : dsKhuyenMaiDangHoatDong) {
                thueDatPhongPanel.cbb_KhuyenMai.addItem(km.getTenKM());
            }
            danhSachKhuyenMaiHienThi = dsKhuyenMaiDangHoatDong;
        }

        // Sau khi cập nhật khuyến mãi, cần lọc lại phòng
        LocPhong();
    }

    // từ từ cập nhật vào nagyf mai
    public void TuDongCapNhatTrangThaiPhong_TheoKhoangNgay() {
        Date ngayBatDau = thueDatPhongPanel.ngayBatDau.getDate();
        Date ngayKetThuc = thueDatPhongPanel.ngayKetThuc.getDate();

        if (ngayBatDau == null || ngayKetThuc == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày bắt đầu và ngày kết thúc!");
            return;
        }

        if (ngayBatDau.after(ngayKetThuc)) {
            JOptionPane.showMessageDialog(null, "Ngày bắt đầu phải trước ngày kết thúc!");
            return;
        }
        hoaDonDao.TuDongCapNhatTrangThaiPhong_TheoKhoangNgay(ngayBatDau, ngayKetThuc);
        getTatCaPhong();
    }

    public ArrayList<Phong> getDsPhongDaChon() {
        return dsPhongDaChon;
    }

    public KhachHang getKhachHangHienTai() {
        return khachHangHienTai;
    }

    public void clearDsPhongDaChon() {
        dsPhongDaChon.clear();
        // Khi xóa phòng đã chọn, cập nhật lại combobox khuyến mãi
        getKhuyenMai();
    }

    public void refreshDanhSachPhong() {
        getTatCaPhong();
        LocPhong();
    }

    public void setBtnNhanPhongAction(java.awt.event.ActionListener action) {
        thueDatPhongPanel.btn_ThuePhong.addActionListener(action);
    }

    public void setBtnDatPhongAction(java.awt.event.ActionListener action) {
        thueDatPhongPanel.btn_DatPhong.addActionListener(action);
    }
    private void openPhieuDatPhongDialog() {
        // Kiểm tra đã chọn phòng chưa
        if (dsPhongDaChon.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Vui lòng chọn ít nhất một phòng để đặt!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy khuyến mãi đã chọn
        KhuyenMai khuyenMaiDuocChon = getKhuyenMaiDuocChon();

        // Tạo và hiển thị dialog
        PhieuDatPhongDialogOThueDatPhong dialog = new PhieuDatPhongDialogOThueDatPhong();

        // Tạo controller cho dialog và truyền dữ liệu
        new PhieuDatPhongController(dialog, khachHangHienTai, dsPhongDaChon, khuyenMaiDuocChon);

        // Hiển thị dialog
        dialog.setModal(true);
        dialog.setVisible(true);

        // Sau khi đóng dialog, có thể cần refresh lại danh sách phòng
        if (dialog.isConfirmed()) {
            refreshDanhSachPhong();
            clearDsPhongDaChon();
        }
    }

    private void openPhieuThuePhongDialog() {
        // Kiểm tra đã chọn phòng chưa
        if (dsPhongDaChon.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Vui lòng chọn ít nhất một phòng để thuê!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy khuyến mãi đã chọn
        KhuyenMai khuyenMaiDuocChon = getKhuyenMaiDuocChon();

        // Tạo và hiển thị dialog
        PhieuThuePhongDialogOThueDatPhong dialog = new PhieuThuePhongDialogOThueDatPhong();

        // Tạo controller cho dialog và truyền dữ liệu
        new PhieuThuePhongController(dialog, khachHangHienTai, dsPhongDaChon, khuyenMaiDuocChon);

        // Hiển thị dialog
        dialog.setModal(true);
        dialog.setVisible(true);

        // Sau khi đóng dialog, có thể cần refresh lại danh sách phòng
        if (dialog.isConfirmed()) {
            refreshDanhSachPhong();
            clearDsPhongDaChon();
        }
    }

    // Thêm phương thức hiển thị thông báo đẹp
    private void showCustomMessage(String message, String title, int messageType) {
        JOptionPane pane = new JOptionPane(
                "<html><div style='width:300px;padding:10px;font-family:Segoe UI;font-size:14px;'>"
                        + message + "</div></html>",
                messageType
        );
        JDialog dialog = pane.createDialog(null, title);

        // Customize the dialog appearance
        dialog.getContentPane().setBackground(new Color(236, 247, 255));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Get the OK button and customize it
        for (Component comp : pane.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.setBackground(new Color(10, 110, 189));
                button.setForeground(Color.WHITE);
                button.setFont(new Font("Segoe UI", Font.BOLD, 14));
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            }
        }

        dialog.setVisible(true);
    }

    public void getKhuyenMai(){
        thueDatPhongPanel.cbb_KhuyenMai.removeAllItems();
        danhSachKhuyenMaiHienThi.clear();

        // Chỉ hiển thị "Không có khuyến mãi" nếu không có phòng nào được chọn
        // Khi có phòng được chọn, combobox sẽ được cập nhật bởi capNhatKhuyenMaiTheoPhongDaChon()
        if (dsPhongDaChon.isEmpty()) {
            thueDatPhongPanel.cbb_KhuyenMai.addItem("Không có khuyến mãi");
        }
    }

    private int showCustomConfirmDialog(String message, String title, int messageType) {
        Object[] options = {"Có", "Không"};
        JOptionPane pane = new JOptionPane(
                "<html><div style='width:300px;padding:10px;font-family:Segoe UI;font-size:14px;'>"
                        + message + "</div></html>",
                messageType,
                JOptionPane.YES_NO_OPTION,
                null,
                options,
                options[0]
        );
        JDialog dialog = pane.createDialog(null, title);

        // Customize the dialog appearance
        dialog.getContentPane().setBackground(new Color(236, 247, 255));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

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
                button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
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
    private void LamMoi(){
        int luaChon = JOptionPane.showConfirmDialog(thueDatPhongPanel,"Bán có muốn làm mới lại trang","chú ý",JOptionPane.YES_NO_OPTION);
        if(luaChon == JOptionPane.YES_OPTION){
            thueDatPhongPanel.txt_TimSoDienThoai.setText("");
            thueDatPhongPanel.model.setRowCount(0);
            thueDatPhongPanel.ngayBatDau.setDate(null);
            thueDatPhongPanel.ngayKetThuc.setDate(null);
            thueDatPhongPanel.chckbx_phongDat.setSelected(false);
            thueDatPhongPanel.chckbx_phongThue.setSelected(false);
            thueDatPhongPanel.chckbx_phongTrong.setSelected(false);
            thueDatPhongPanel.cbb_KhuyenMai.setSelectedIndex(0);
            LocalDate ngayHomNay = LocalDate.now();
            hoaDonDao.tuDongCapNhatTrangThaiPhong(ngayHomNay);
            getTatCaPhong();
        }
    }
}