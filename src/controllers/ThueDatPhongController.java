package controllers;

import database.dao.*;
import entitys.*;
import enums.TrangThaiKhuyenMai;
import enums.TrangThaiPhong;
import view.dialogs.PhieuDoiPhongDialog;
import view.dialogs.PhieuPhongDatDialog;
import view.panels.ThueDatPhongPanel;

import javax.swing.*;

import controllers.dialogs.PhieuDoiPhongController;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class ThueDatPhongController {

    private HoaDonDao hoaDonDao;
    private ThueDatPhongPanel thueDatPhongPanel;
    private KhuyenMaiDao khuyenMaiDao;
    private ArrayList<Phong> danhSachPhong;
    private ArrayList<Phong> danhSachPhongHienThi;
    private ArrayList<Phong> danhSachPhongTheoLoai;
    private ArrayList<KhuyenMai> danhSachKhuyenMai;
    private ArrayList<KhuyenMai> danhSachKhuyenMaiHienThi; // Thêm biến mới
    private PhongDao phongDao;
    private KhachHangDao khachHangDao;
    private ArrayList<Phong> dsPhongDaChon = new ArrayList<>();
    private KhachHang khachHangHienTai;
    private ArrayList<Phong> dsPhongTheoKH = new ArrayList<>(); // Thêm biến mới

    public ThueDatPhongController(ThueDatPhongPanel thueDatPhongPanel){
        this.thueDatPhongPanel = thueDatPhongPanel;
        phongDao = new PhongDao();
        khachHangDao = new KhachHangDao();
        khuyenMaiDao = new KhuyenMaiDao();
        danhSachKhuyenMai = khuyenMaiDao.getTatCaKhuyenMai();
        danhSachKhuyenMaiHienThi = new ArrayList<>(); // KHỞI TẠO TẠI ĐÂY
        hoaDonDao = new HoaDonDao();

        // Gán sự kiện
        thueDatPhongPanel.txt_TimSoDienThoai.addActionListener(e -> getKhachHang());
        thueDatPhongPanel.btn_Tim.addActionListener(e -> getKhachHang());

        thueDatPhongPanel.chckbx_phongDat.addActionListener(e-> LocPhong());
        thueDatPhongPanel.chckbx_phongThue.addActionListener(e-> LocPhong());
        thueDatPhongPanel.chckbx_phongTrong.addActionListener(e-> LocPhong());

        thueDatPhongPanel.cbb_LoaiPhong.addActionListener(e -> LocPhong());
        thueDatPhongPanel.cbb_KhuyenMai.addActionListener(e -> LocPhong());

        // Sửa sự kiện cho nút Áp dụng
        thueDatPhongPanel.btn_ApDung.addActionListener(e -> {
            TuDongCapNhatTrangThaiPhong_TheoKhoangNgay();
            capNhatKhuyenMaiTheoKhoangNgay();
        });

        // Khởi tạo dữ liệu
        getTatCaPhong();
        getKhuyenMai();
    }

    public void getTatCaPhong(){
        danhSachPhong = phongDao.getDanhSachPhong();
        // Lọc dsPhongDaChon, chỉ giữ lại những phòng còn trống
        dsPhongDaChon.removeIf(phong -> !phong.getTrangThai().equals(TrangThaiPhong.Trong));
        danhSachPhongHienThi = danhSachPhong;
        HienThiDanhSachPhong(danhSachPhongHienThi);
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
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1.0; // Chia đều chiều rộng cho 5 cột
            gbc.weighty = 0;   // Không chia chiều cao (để giữ chiều cao nút cố định)

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
        } else if(TrangThaiPhong.DaDat.equals(phong.getTrangThai()))  {
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
                    new PhieuDoiPhongController(phieuDoiPhongDialog, p);
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
            JOptionPane.showMessageDialog(null,
                    "Vui lòng nhập số điện thoại!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        KhachHang khachHang = khachHangDao.TimKhachHang(soDTKhachHang,"SDT");
        if(khachHang != null){
            khachHangHienTai = khachHang;
            JOptionPane.showMessageDialog(null,
                    "Đã tìm thấy khách hàng: " + khachHang.getTenKH(),
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Không tìm thấy khách hàng với số điện thoại này!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            khachHangHienTai = null;
        }
    }

    // Sửa phương thức LocPhong để xử lý trường hợp "Không có khuyến mãi"
    private void LocPhong() {
        ArrayList<Phong> danhSachTam;

        // Nếu có khách hàng được chọn và có danh sách phòng theo KH, thì dùng danh sách đó
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
        if (khuyenMaiChon != null && !khuyenMaiChon.equals("Chọn") && !khuyenMaiChon.equals("Không có khuyến mãi")) {
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
            String[] loaiPhongKhuyenMai = khuyenMaiChon.getDieuKienApDung().split(",");

            for (Phong phong : danhSach) {
                for (String loai : loaiPhongKhuyenMai) {
                    String tenLoai = loai.trim();
                    if (phong.getLoaiPhong().getTenLoaiPhong().equalsIgnoreCase(tenLoai)) {
                        ketQua.add(phong);
                        break;
                    }
                }
            }
        }

        return ketQua.isEmpty() ? danhSach : ketQua;
    }

    public void getKhuyenMai(){
        thueDatPhongPanel.cbb_KhuyenMai.removeAllItems();
        thueDatPhongPanel.cbb_KhuyenMai.addItem("Chọn");

        danhSachKhuyenMaiHienThi.clear();

        // Ban đầu hiển thị tất cả khuyến mãi đang hoạt động
        for(KhuyenMai khuyenMai : danhSachKhuyenMai){
            if(khuyenMai.getTrangThai().equals(TrangThaiKhuyenMai.DangHoatDong)){
                thueDatPhongPanel.cbb_KhuyenMai.addItem(khuyenMai.getTenKM());
                danhSachKhuyenMaiHienThi.add(khuyenMai);
            }
        }

        // Nếu không có khuyến mãi nào
        if (danhSachKhuyenMaiHienThi.isEmpty()) {
            thueDatPhongPanel.cbb_KhuyenMai.removeAllItems();
            thueDatPhongPanel.cbb_KhuyenMai.addItem("Không có khuyến mãi");
        }
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

        int result = JOptionPane.showConfirmDialog(null,
                "Bạn có chắc muốn cập nhật trạng thái phòng theo khoảng ngày đã chọn?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            hoaDonDao.TuDongCapNhatTrangThaiPhong_TheoKhoangNgay(ngayBatDau, ngayKetThuc);
            getTatCaPhong();

            JOptionPane.showMessageDialog(null,
                    "Đã cập nhật trạng thái phòng thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public ArrayList<Phong> getDsPhongDaChon() {
        return dsPhongDaChon;
    }

    public KhachHang getKhachHangHienTai() {
        return khachHangHienTai;
    }

    public void clearDsPhongDaChon() {
        dsPhongDaChon.clear();
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
}