package view.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HoaDonDialog extends JDialog {
    public JLabel lbl_TienCuaTongTienThanhToanTrongPnlTongTien_1;
    public JRadioButton rdbtn_ChuyenKhoan;
    public JRadioButton rdbtn_TienMat;
    public JRadioButton rdbtn_TachHoaDon;
    public JRadioButton rdbtn_GopHoaDon;
    public JTextField txt_SDT;
    public JTextField txt_HoTen;
    public JTextField txt_CCCD;
    public JTextField txt_NgaySinh;
    public JTextField txt_Email;
    public JTextField txt_HangKhachHang;
    public JTextField txt_DiemTichLuy;
    public JTextField txt_TienKhachDua;
    public JDateChooser ngayBatDau;
    public JDateChooser ngayKetThuc;
    public JLabel lbl_PhuongThucThanhToanTrongPnlTongTien;
    public JLabel lbl_TienNhanTuKhachTrongPnlTongTien;
    public JLabel lbl_TienTraLaiKhachTrongPnlTongTien;
    public JLabel lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien;
    public JLabel lbl_TienCuaTienNhanTuKhachTrongPnlTongTien;
    public JLabel lbl_TienCuaTienTraLaiKhachTrongPnlTongTien;
    public JButton btn_XacNhan;
    public JButton btn_Huy;
    public JTable table;
    public DefaultTableModel model;
    public JButton btn_inHoaDon;
    public JButton btn_huyHoaDon;
    public JRadioButton rdbtn_NamNguoiO;
    public JRadioButton rdbtn_NuNguoiO;
    public ButtonGroup gioiTinhGroup;
    
    // Inner class RoundedButton
    class RoundedButton extends JButton {
        private int radius;

        public RoundedButton(String label, Color bg, Color fg, int radius) {
            super(label);
            this.radius = radius;
            setBackground(bg);
            setForeground(fg);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorder(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }
    }

    public HoaDonDialog() {

        getContentPane().setBackground(new Color(236, 247, 255));
        setBounds(100, 100, 1100, 565);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblTenKhachSanMenu = new JLabel("ATTM");
        lblTenKhachSanMenu.setFont(new Font("Lucida Calligraphy", Font.BOLD, 24));
        lblTenKhachSanMenu.setHorizontalAlignment(SwingConstants.LEFT);
        lblTenKhachSanMenu.setForeground(new Color(10, 110, 189));
        lblTenKhachSanMenu.setBounds(10, 10, 90, 24);
        getContentPane().add(lblTenKhachSanMenu);

        JLabel lblTieuDeForm = new JLabel("Thông tin trả phòng");
        lblTieuDeForm.setForeground(new Color(10, 110, 189));
        lblTieuDeForm.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTieuDeForm.setBounds(430, 3, 302, 40);
        getContentPane().add(lblTieuDeForm);

        JLabel lblFromThongTinKhachHang = new JLabel("Thông tin khách hàng ");
        lblFromThongTinKhachHang.setHorizontalAlignment(SwingConstants.CENTER);
        lblFromThongTinKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblFromThongTinKhachHang.setBounds(118, 38, 302, 26);
        getContentPane().add(lblFromThongTinKhachHang);

        JPanel pnl_ThongTinKhachHang = new JPanel();
        pnl_ThongTinKhachHang.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), HIDE_ON_CLOSE), null
        ));
        pnl_ThongTinKhachHang.setBackground(new Color(255, 255, 255));
        pnl_ThongTinKhachHang.setBounds(10, 65, 525, 310);
        getContentPane().add(pnl_ThongTinKhachHang);
        pnl_ThongTinKhachHang.setLayout(null);

        JLabel lbl_SDT = new JLabel("Số điện thoại: ");
        lbl_SDT.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_SDT.setBounds(10, 20, 102, 20);
        pnl_ThongTinKhachHang.add(lbl_SDT);

        JLabel lbl_HoTen = new JLabel("Họ tên:");
        lbl_HoTen.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_HoTen.setBounds(10, 70, 102, 20);
        pnl_ThongTinKhachHang.add(lbl_HoTen);

        JLabel lbl_CCCD = new JLabel("CCCD:");
        lbl_CCCD.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_CCCD.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_CCCD.setBounds(10, 120, 102, 20);
        pnl_ThongTinKhachHang.add(lbl_CCCD);

        txt_SDT = new JTextField();
        txt_SDT.setBounds(132, 19, 373, 25);
        txt_SDT.setEditable(false);
        txt_SDT.setBackground(Color.WHITE);
        txt_SDT.setFocusable(false);
        pnl_ThongTinKhachHang.add(txt_SDT);
        txt_SDT.setColumns(10);

        txt_HoTen = new JTextField();
        txt_HoTen.setColumns(10);
        txt_HoTen.setBounds(132, 69, 373, 25);
        txt_HoTen.setEditable(false);
        txt_HoTen.setBackground(Color.WHITE);
        txt_HoTen.setFocusable(false);
        pnl_ThongTinKhachHang.add(txt_HoTen);

        txt_CCCD = new JTextField();
        txt_CCCD.setColumns(10);
        txt_CCCD.setBounds(132, 119, 373, 25);
        txt_CCCD.setEditable(false);
        txt_CCCD.setBackground(Color.WHITE);
        txt_CCCD.setFocusable(false);
        pnl_ThongTinKhachHang.add(txt_CCCD);

        JLabel lbl_NgaySinh = new JLabel("Ngày sinh:");
        lbl_NgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_NgaySinh.setBounds(10, 170, 112, 20);
        pnl_ThongTinKhachHang.add(lbl_NgaySinh);

        txt_NgaySinh = new JTextField();
        txt_NgaySinh.setColumns(10);
        txt_NgaySinh.setBounds(132, 169, 110, 25);
        txt_NgaySinh.setEditable(false);
        txt_NgaySinh.setBackground(Color.WHITE);
        txt_NgaySinh.setFocusable(false);
        pnl_ThongTinKhachHang.add(txt_NgaySinh);

        JLabel lbl_GioiTinh = new JLabel("Giới tính:");
        lbl_GioiTinh.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_GioiTinh.setBounds(291, 170, 92, 20);
        pnl_ThongTinKhachHang.add(lbl_GioiTinh);

        JLabel lbl_Email = new JLabel("Email:");
        lbl_Email.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_Email.setBounds(10, 220, 112, 20);
        pnl_ThongTinKhachHang.add(lbl_Email);

        txt_Email = new JTextField();
        txt_Email.setBounds(132, 219, 373, 25);
        txt_Email.setEditable(false);
        txt_Email.setBackground(Color.WHITE);
        txt_Email.setFocusable(false);
        pnl_ThongTinKhachHang.add(txt_Email);
        txt_Email.setColumns(10);

        JLabel lbl_HangKhachHang = new JLabel("Hạng khách hàng:");
        lbl_HangKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_HangKhachHang.setBounds(10, 270, 112, 20);
        pnl_ThongTinKhachHang.add(lbl_HangKhachHang);

        txt_HangKhachHang = new JTextField();
        txt_HangKhachHang.setColumns(10);
        txt_HangKhachHang.setEditable(false);
        txt_HangKhachHang.setBackground(Color.WHITE);
        txt_HangKhachHang.setFocusable(false);
        txt_HangKhachHang.setBounds(132, 269, 110, 25);
        pnl_ThongTinKhachHang.add(txt_HangKhachHang);

        JLabel lbl_DiemTichLuy = new JLabel("Điểm tích lũy:");
        lbl_DiemTichLuy.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_DiemTichLuy.setBounds(298, 270, 92, 20);
        pnl_ThongTinKhachHang.add(lbl_DiemTichLuy);

        txt_DiemTichLuy = new JTextField();
        txt_DiemTichLuy.setColumns(10);
        txt_DiemTichLuy.setEditable(false);
        txt_DiemTichLuy.setBackground(Color.WHITE);
        txt_DiemTichLuy.setFocusable(false);
        txt_DiemTichLuy.setBounds(393, 269, 112, 25);
        pnl_ThongTinKhachHang.add(txt_DiemTichLuy);

        gioiTinhGroup = new ButtonGroup();
        rdbtn_NamNguoiO = new JRadioButton("Nam");
        rdbtn_NamNguoiO.setSelected(true);
        rdbtn_NamNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_NamNguoiO.setBackground(Color.WHITE);
        rdbtn_NamNguoiO.setBounds(365, 170, 55, 20);
        pnl_ThongTinKhachHang.add(rdbtn_NamNguoiO);
        gioiTinhGroup.add(rdbtn_NamNguoiO);
        rdbtn_NuNguoiO = new JRadioButton("Nữ");
        rdbtn_NuNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_NuNguoiO.setBackground(Color.WHITE);
        rdbtn_NuNguoiO.setBounds(450, 170, 55, 20);
        pnl_ThongTinKhachHang.add(rdbtn_NuNguoiO);
        gioiTinhGroup.add(rdbtn_NuNguoiO);

        JLabel lbl_PhuongThucThangToan = new JLabel("Phương thức thanh toán: ");
        lbl_PhuongThucThangToan.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_PhuongThucThangToan.setBounds(10, 381, 204, 25);
        getContentPane().add(lbl_PhuongThucThangToan);

        rdbtn_TienMat = new JRadioButton("Tiền mặt");
        rdbtn_TienMat.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_TienMat.setBackground(new Color(236, 247, 255));
        rdbtn_TienMat.setBounds(267, 381, 102, 20);
        getContentPane().add(rdbtn_TienMat);

        rdbtn_ChuyenKhoan = new JRadioButton("Chuyển khoản");
        rdbtn_ChuyenKhoan.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_ChuyenKhoan.setBackground(new Color(236, 247, 255));
        rdbtn_ChuyenKhoan.setBounds(407, 381, 113, 20);
        getContentPane().add(rdbtn_ChuyenKhoan);

        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(rdbtn_TienMat);
        paymentGroup.add(rdbtn_ChuyenKhoan);
        rdbtn_TienMat.setSelected(true);

        JLabel lbl_TienKhachDua = new JLabel("Tiền khách đưa:");
        lbl_TienKhachDua.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_TienKhachDua.setBounds(10, 454, 158, 24);
        getContentPane().add(lbl_TienKhachDua);

        txt_TienKhachDua = new JTextField();
        txt_TienKhachDua.setBounds(233, 456, 302, 25);
        getContentPane().add(txt_TienKhachDua);
        txt_TienKhachDua.setColumns(10);

        JLabel lbl_DanhSachPhong = new JLabel("Danh sách phòng");
        lbl_DanhSachPhong.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_DanhSachPhong.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_DanhSachPhong.setBounds(755, 38, 147, 26);
        getContentPane().add(lbl_DanhSachPhong);

        JPanel pnl_DanhSachPhong = new JPanel();
        pnl_DanhSachPhong.setBackground(new Color(255, 255, 255));
        pnl_DanhSachPhong.setBounds(550, 65, 525, 107);
        pnl_DanhSachPhong.setLayout(null);
        getContentPane().add(pnl_DanhSachPhong);

        model = new DefaultTableModel(new String[] {"Mã phòng","Loại phòng","SLTĐ","Số ngày ở","Giá","Tiền cọc","Thành tiền"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(new Color(255, 255, 255));
        scrollPane.setBounds(10, 10, 505, 87);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnl_DanhSachPhong.add(scrollPane);

        JLabel lbl_ThoiGianThue = new JLabel("Thời gian thuê");
        lbl_ThoiGianThue.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_ThoiGianThue.setBackground(new Color(255, 255, 255));
        lbl_ThoiGianThue.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_ThoiGianThue.setBounds(766, 182, 128, 26);
        getContentPane().add(lbl_ThoiGianThue);

        JPanel pnl_ThoiGianThue = new JPanel();
        pnl_ThoiGianThue.setBackground(new Color(255, 255, 255));
        pnl_ThoiGianThue.setBounds(550, 210, 525, 80);
        getContentPane().add(pnl_ThoiGianThue);
        pnl_ThoiGianThue.setLayout(null);

        JLabel lbl_NgayBatDau = new JLabel("Ngày bắt đầu: ");
        lbl_NgayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_NgayBatDau.setBounds(68, 12, 100, 20);
        pnl_ThoiGianThue.add(lbl_NgayBatDau);

        ngayBatDau = new JDateChooser();
        ngayBatDau.setDateFormatString("dd/MM/yyyy");
        ngayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayBatDau.setBounds(198, 11, 250, 25);
        pnl_ThoiGianThue.add(ngayBatDau);

        JLabel lbl_TienKhachDuaTrongPnlTongTien = new JLabel("Ngày kết thúc: ");
        lbl_TienKhachDuaTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_TienKhachDuaTrongPnlTongTien.setBounds(68, 46, 100, 20);
        pnl_ThoiGianThue.add(lbl_TienKhachDuaTrongPnlTongTien);

        ngayKetThuc = new JDateChooser();
        ngayKetThuc.setDateFormatString("dd/MM/yyyy");
        ngayKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayKetThuc.setBounds(198, 45, 250, 25);
        pnl_ThoiGianThue.add(ngayKetThuc);

        JLabel lbl_TongTien = new JLabel("Tổng tiền");
        lbl_TongTien.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_TongTien.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_TongTien.setBounds(778, 300, 102, 26);
        getContentPane().add(lbl_TongTien);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(550, 330, 525, 148);
        getContentPane().add(panel);
        panel.setLayout(null);

        lbl_PhuongThucThanhToanTrongPnlTongTien = new JLabel("Phương thức thanh toán:");
        lbl_PhuongThucThanhToanTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_PhuongThucThanhToanTrongPnlTongTien.setBounds(10, 21, 163, 18);
        panel.add(lbl_PhuongThucThanhToanTrongPnlTongTien);

        lbl_TienNhanTuKhachTrongPnlTongTien = new JLabel("Tiền nhận từ khách:");
        lbl_TienNhanTuKhachTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienNhanTuKhachTrongPnlTongTien.setBounds(10, 77, 163, 18);
        panel.add(lbl_TienNhanTuKhachTrongPnlTongTien);

        lbl_TienTraLaiKhachTrongPnlTongTien = new JLabel("Tiền trả lại khách:");
        lbl_TienTraLaiKhachTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienTraLaiKhachTrongPnlTongTien.setBounds(10, 105, 163, 18);
        panel.add(lbl_TienTraLaiKhachTrongPnlTongTien);

        lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien = new JLabel("Tiền mặt");
        lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setBounds(431, 21, 84, 18);
        panel.add(lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien);

        lbl_TienCuaTienNhanTuKhachTrongPnlTongTien = new JLabel("0 VND");
        lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setBounds(352, 77, 163, 18);
        panel.add(lbl_TienCuaTienNhanTuKhachTrongPnlTongTien);

        lbl_TienCuaTienTraLaiKhachTrongPnlTongTien = new JLabel("0 VND");
        lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setBounds(374, 105, 141, 18);
        panel.add(lbl_TienCuaTienTraLaiKhachTrongPnlTongTien);

        JLabel lbl_TongTienThanhToanTrongPnlTongTien_1 = new JLabel("Tổng tiền thanh toán:");
        lbl_TongTienThanhToanTrongPnlTongTien_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TongTienThanhToanTrongPnlTongTien_1.setBounds(10, 49, 163, 18);
        panel.add(lbl_TongTienThanhToanTrongPnlTongTien_1);

        lbl_TienCuaTongTienThanhToanTrongPnlTongTien_1 = new JLabel("0 VND");
        lbl_TienCuaTongTienThanhToanTrongPnlTongTien_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaTongTienThanhToanTrongPnlTongTien_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaTongTienThanhToanTrongPnlTongTien_1.setBounds(374, 49, 141, 18);
        panel.add(lbl_TienCuaTongTienThanhToanTrongPnlTongTien_1);

        class RoundedButton extends JButton {
            private int radius;

            public RoundedButton(String label, Color bg, Color fg, int radius) {
                super(label);
                this.radius = radius;
                setBackground(bg);
                setForeground(fg);
                setContentAreaFilled(false);
                setFocusPainted(false);
                setBorder(null);
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
                super.paintComponent(g2);
                g2.dispose();
            }
        }

        btn_XacNhan = new RoundedButton("Xác nhận", new Color(76, 175, 80), Color.WHITE, 30);
        btn_XacNhan.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_XacNhan.setBounds(430, 570, 120, 30);
        getContentPane().add(btn_XacNhan);

        btn_Huy = new RoundedButton("Hủy", new Color(244, 67, 54), Color.WHITE, 30);
        btn_Huy.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_Huy.setBounds(612, 570, 120, 30);
        getContentPane().add(btn_Huy);

        JLabel lbl_PhuongThucXuatHoaDon = new JLabel("Phương thức xuất hóa đơn: ");
        lbl_PhuongThucXuatHoaDon.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_PhuongThucXuatHoaDon.setBounds(10, 417, 224, 25);
        getContentPane().add(lbl_PhuongThucXuatHoaDon);

        rdbtn_GopHoaDon = new JRadioButton("Gộp hóa đơn");
        rdbtn_GopHoaDon.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_GopHoaDon.setBackground(new Color(236, 247, 255));
        rdbtn_GopHoaDon.setBounds(407, 417, 128, 20);
        getContentPane().add(rdbtn_GopHoaDon);

        rdbtn_TachHoaDon = new JRadioButton("Tách hóa đơn");
        rdbtn_TachHoaDon.setSelected(true);
        rdbtn_TachHoaDon.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_TachHoaDon.setBackground(new Color(236, 247, 255));
        rdbtn_TachHoaDon.setBounds(267, 417, 113, 20);
        getContentPane().add(rdbtn_TachHoaDon);

        ButtonGroup phuongThucXuatHoaDon = new ButtonGroup();
        phuongThucXuatHoaDon.add(rdbtn_TachHoaDon);
        phuongThucXuatHoaDon.add(rdbtn_GopHoaDon);
        
        btn_inHoaDon = new JButton("In hóa đơn");
        btn_inHoaDon.setBounds(956, 488, 120, 30);
        getContentPane().add(btn_inHoaDon);

        btn_huyHoaDon = new JButton("Hủy hóa đơn");
        btn_huyHoaDon.setBounds(826, 488, 120, 30);
        getContentPane().add(btn_huyHoaDon);
    }
    
    // THÊM PHƯƠNG THỨC MỚI: Thiết lập DocumentListener
    public void setupAutoUpdateTienNhan() {
        txt_TienKhachDua.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                capNhatTienNhanTuKhach();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                capNhatTienNhanTuKhach();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                capNhatTienNhanTuKhach();
            }
        });
    }
    
    // THÊM PHƯƠNG THỨC MỚI: Cập nhật tiền nhận từ khách
    private void capNhatTienNhanTuKhach() {
        String text = txt_TienKhachDua.getText();
        
        try {
            if (!text.isEmpty()) {
                // Loại bỏ các ký tự không phải số
                String cleanedText = text.replaceAll("[^\\d]", "");
                if (!cleanedText.isEmpty()) {
                    double tien = Double.parseDouble(cleanedText);
                    String formatted = String.format("%,.0f VND", tien);
                    lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setText(formatted);
                    
                    // Tự động tính tiền thừa (tiền khách đưa - tổng tiền thanh toán)
                    tinhTienThuaTuDong();
                } else {
                    lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setText("0 VND");
                    lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setText("0 VND");
                }
            } else {
                lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setText("0 VND");
                lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setText("0 VND");
            }
        } catch (NumberFormatException e) {
            lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setText(text);
        }
    }
    
    // THÊM PHƯƠNG THỨC MỚI: Tính tiền thừa tự động
    private void tinhTienThuaTuDong() {
        try {
            // Lấy tổng tiền thanh toán từ label
            String tongTienThanhToanText = lbl_TienCuaTongTienThanhToanTrongPnlTongTien_1.getText();
            if (tongTienThanhToanText != null && !tongTienThanhToanText.isEmpty()) {
                // Loại bỏ "VND" và dấu phẩy
                String cleanedTongTien = tongTienThanhToanText.replace("VND", "").replace(",", "").trim();
                if (!cleanedTongTien.isEmpty()) {
                    double tongTienThanhToan = Double.parseDouble(cleanedTongTien);
                    
                    // Lấy tiền khách đưa
                    String tienKhachDuaText = txt_TienKhachDua.getText();
                    if (!tienKhachDuaText.isEmpty()) {
                        String cleanedTienKhachDua = tienKhachDuaText.replaceAll("[^\\d]", "");
                        if (!cleanedTienKhachDua.isEmpty()) {
                            double tienKhachDua = Double.parseDouble(cleanedTienKhachDua);
                            
                            // Tính tiền thừa
                            double tienThua = tienKhachDua - tongTienThanhToan;
                            if (tienThua >= 0) {
                                lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setText(String.format("%,.0f VND", tienThua));
                            } else {
                                lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setText("0 VND");
                            }
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            // Không làm gì nếu có lỗi parse
        }
    }
    
    // THÊM PHƯƠNG THỨC MỚI: Set tổng tiền thanh toán
    public void setTongTienThanhToan(double tongTienThanhToan) {
        lbl_TienCuaTongTienThanhToanTrongPnlTongTien_1.setText(String.format("%,.0f VND", tongTienThanhToan));
        // Tự động tính lại tiền thừa nếu cần
        tinhTienThuaTuDong();
    }
    
    // THÊM PHƯƠNG THỨC MỚI: Set phương thức thanh toán
    public void setPhuongThucThanhToan(String phuongThuc) {
        lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setText(phuongThuc);
        if ("Tiền mặt".equals(phuongThuc)) {
            rdbtn_TienMat.setSelected(true);
        } else if ("Chuyển khoản".equals(phuongThuc)) {
            rdbtn_ChuyenKhoan.setSelected(true);
        }
    }
    
    // THÊM PHƯƠNG THỨC MỚI: Set tiền khách đưa ban đầu
    public void setTienKhachDua(double tienKhachDua) {
        txt_TienKhachDua.setText(String.valueOf((int)tienKhachDua));
    }
    
    // THÊM PHƯƠNG THỨC MỚI: Tính tổng tiền thanh toán từ danh sách phòng
    public void tinhTongTienThanhToanTuDanhSachPhong() {
        double tongThanhTien = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            try {
                Object thanhTienObj = model.getValueAt(i, 6); // Cột thành tiền là cột 6
                if (thanhTienObj != null) {
                    // Chuyển đổi từ string đã format về số
                    String thanhTienStr = thanhTienObj.toString().replace(",", "").replace(" VND", "").trim();
                    if (!thanhTienStr.isEmpty()) {
                        double thanhTien = Double.parseDouble(thanhTienStr);
                        tongThanhTien += thanhTien;
                    }
                }
            } catch (Exception e) {
                // Bỏ qua nếu có lỗi
            }
        }
        setTongTienThanhToan(tongThanhTien);
    }
    
    // THÊM PHƯƠNG THỨC MỚI: Format tiền trong bảng
    public void formatTienTrongBang() {
        for (int i = 0; i < model.getRowCount(); i++) {
            // Format cột Giá (cột 4)
            Object giaObj = model.getValueAt(i, 4);
            if (giaObj != null && !giaObj.toString().isEmpty()) {
                try {
                    double gia = Double.parseDouble(giaObj.toString());
                    model.setValueAt(String.format("%,.0f VND", gia), i, 4);
                } catch (NumberFormatException e) {
                    // Giữ nguyên nếu không phải số
                }
            }
            
            // Format cột Tiền cọc (cột 5)
            Object tienCocObj = model.getValueAt(i, 5);
            if (tienCocObj != null && !tienCocObj.toString().isEmpty()) {
                try {
                    double tienCoc = Double.parseDouble(tienCocObj.toString());
                    model.setValueAt(String.format("%,.0f VND", tienCoc), i, 5);
                } catch (NumberFormatException e) {
                    // Giữ nguyên nếu không phải số
                }
            }
            
            // Format cột Thành tiền (cột 6)
            Object thanhTienObj = model.getValueAt(i, 6);
            if (thanhTienObj != null && !thanhTienObj.toString().isEmpty()) {
                try {
                    double thanhTien = Double.parseDouble(thanhTienObj.toString());
                    model.setValueAt(String.format("%,.0f VND", thanhTien), i, 6);
                } catch (NumberFormatException e) {
                    // Giữ nguyên nếu không phải số
                }
            }
        }
    }
}
