package view.dialogs;

import com.toedter.calendar.JDateChooser;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.Dimension;

import java.awt.Font;

public class PhieuDatPhongDialogOThueDatPhong extends JDialog {

    private static final long serialVersionUID = 1L;
    public JTextField txt_SDT;
    public JTextField txt_HoTen;
    public JTextField txt_CCCD;
    public JTextField txt_TienKhachDua;
    public JDateChooser ngayBatDau;
    public JDateChooser ngayKetThuc;
    public JButton btn_XacNhan;
    public JButton btn_Huy;
    public JRadioButton rdbtn_TienMat;
    public JRadioButton rdbtn_ChuyenKhoan;
//    public FormThongTinDatPhongController controller;
    public JTable table;
    public DefaultTableModel model;
    public JPanel pnl_TongTien;
    public JLabel lbl_PhuongThucThanhToanTrongPnlTongTien;
    public JLabel lbl_TongTienTrongPnlTongTien_1;
    public JLabel lbl_TienCocTrongPnlTongTien_1;
    public JLabel lbl_TienNhanTuKhachTrongPnlTongTien_1;
    public JLabel lbl_TienTraLaiKhachTrongPnlTongTien_1;
    public JLabel lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien_1;
    public JLabel lbl_TienCuaTongTienTrongPnlTongTien_1;
    public JLabel lbl_TienCuaTienCocTrongPnlTongTien_1;
    public JLabel lbl_TienCuaTienNhanTuKhachTrongPnlTongTien_1;
    public JLabel lbl_TienCuaTienTraLaiKhachTrongPnlTongTien_1;
    public JLabel lbl_KhuyenMaiDuocApDung;
    public JLabel lbl_TenKhuyenMai;
    public JLabel lbl_TienTraKhach;
    public JTextField textField;
    public JLabel lbl_TienKhachDua;
    public boolean confirmed = false;

    public PhieuDatPhongDialogOThueDatPhong() {
        jbInit();
    }
    private void jbInit() {
        getContentPane().setBackground(new Color(236, 247, 255));
        setBounds(100, 100, 1100, 640);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblTenKhachSanMenu = new JLabel("ATTM");
        lblTenKhachSanMenu.setFont(new Font("Lucida Calligraphy", Font.BOLD, 24));
        lblTenKhachSanMenu.setHorizontalAlignment(SwingConstants.LEFT);
        lblTenKhachSanMenu.setForeground(new Color(10, 110, 189));
        lblTenKhachSanMenu.setBounds(10, 10, 90, 24);
        getContentPane().add(lblTenKhachSanMenu);

        JLabel lblTieuDeForm = new JLabel("Phiếu đặt phòng");
        lblTieuDeForm.setHorizontalAlignment(SwingConstants.CENTER);
        lblTieuDeForm.setForeground(new Color(10, 110, 189));
        lblTieuDeForm.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTieuDeForm.setBounds(430, 3, 248, 40);
        getContentPane().add(lblTieuDeForm);

        JLabel lblFromThongTinKhachHang = new JLabel("Thông tin khách hàng đặt phòng");
        lblFromThongTinKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblFromThongTinKhachHang.setBounds(10, 60, 302, 26);
        getContentPane().add(lblFromThongTinKhachHang);

        JPanel pnl_ThongTinKhachHang = new JPanel();
        // Tạo viền mờ màu xám nhạt với bo góc 15px
        pnl_ThongTinKhachHang.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), HIDE_ON_CLOSE)// Viền ngoài
                , null
        ));
        pnl_ThongTinKhachHang.setBackground(new Color(255, 255, 255));
        pnl_ThongTinKhachHang.setBounds(10, 96, 525, 165);
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
        txt_SDT.setBackground(Color.WHITE);
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

        JLabel lbl_PhuongThucThangToan = new JLabel("Phương thức thanh toán: ");
        lbl_PhuongThucThangToan.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_PhuongThucThangToan.setBounds(550, 295, 155, 20);
        getContentPane().add(lbl_PhuongThucThangToan);

        rdbtn_TienMat = new JRadioButton("Tiền mặt");
        rdbtn_TienMat.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_TienMat.setBackground(new Color(236, 247, 255));
        rdbtn_TienMat.setBounds(760, 295, 102, 20);
        getContentPane().add(rdbtn_TienMat);

        rdbtn_ChuyenKhoan = new JRadioButton("Chuyển khoản");
        rdbtn_ChuyenKhoan.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_ChuyenKhoan.setBackground(new Color(236, 247, 255));
        rdbtn_ChuyenKhoan.setBounds(899, 295, 113, 20);
        getContentPane().add(rdbtn_ChuyenKhoan);

        // Tạo nhóm và thêm radio button vào nhóm
        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(rdbtn_TienMat);
        paymentGroup.add(rdbtn_ChuyenKhoan);

        // Nếu muốn một radio mặc định được chọn
        rdbtn_TienMat.setSelected(true);


        lbl_TienKhachDua = new JLabel("Tiền khách đưa:");
        lbl_TienKhachDua.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_TienKhachDua.setBounds(550, 340, 128, 24);
        getContentPane().add(lbl_TienKhachDua);

        txt_TienKhachDua = new JTextField();
        txt_TienKhachDua.setBounds(698, 339, 377, 25);
        getContentPane().add(txt_TienKhachDua);
        txt_TienKhachDua.setColumns(10);

        JLabel lbl_DanhSachPhong = new JLabel("Danh sách phòng");
        lbl_DanhSachPhong.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_DanhSachPhong.setBounds(10, 291, 147, 26);
        getContentPane().add(lbl_DanhSachPhong);

        JPanel pnl_DanhSachPhong = new JPanel();
        pnl_DanhSachPhong.setBackground(new Color(255, 255, 255));
        pnl_DanhSachPhong.setBounds(10, 327, 525, 107);
        pnl_DanhSachPhong.setLayout(null);
        getContentPane().add(pnl_DanhSachPhong);

        model = new DefaultTableModel(new String[] {"Mã phòng","Loại phòng","SLTĐ","Giá","Tiền cọc"}, 0);
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
        lbl_ThoiGianThue.setBackground(new Color(255, 255, 255));
        lbl_ThoiGianThue.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_ThoiGianThue.setBounds(10, 464, 128, 26);
        getContentPane().add(lbl_ThoiGianThue);

        JPanel pnl_ThoiGianThue = new JPanel();
        pnl_ThoiGianThue.setBackground(new Color(255, 255, 255));
        pnl_ThoiGianThue.setBounds(10, 500, 525, 80);
        getContentPane().add(pnl_ThoiGianThue);
        pnl_ThoiGianThue.setLayout(null);

        JLabel lbl_NgayBatDau = new JLabel("Ngày bắt đầu: ");
        lbl_NgayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_NgayBatDau.setBounds(10, 10, 100, 20);
        pnl_ThoiGianThue.add(lbl_NgayBatDau);

        ngayBatDau = new JDateChooser();
        ngayBatDau.setDateFormatString("dd/MM/yyyy");
        ngayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayBatDau.setBounds(140, 9, 250, 25);  // <-- SỬA 125 thành 10
        pnl_ThoiGianThue.add(ngayBatDau);

        JLabel lbl_TienKhachDuaTrongPnlTongTien = new JLabel("Ngày kết thúc: ");
        lbl_TienKhachDuaTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_TienKhachDuaTrongPnlTongTien.setBounds(10, 50, 100, 20);
        pnl_ThoiGianThue.add(lbl_TienKhachDuaTrongPnlTongTien);

        ngayKetThuc = new JDateChooser();
        ngayKetThuc.setDateFormatString("dd/MM/yyyy");
        ngayKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayKetThuc.setBounds(140, 49, 250, 25);
        pnl_ThoiGianThue.add(ngayKetThuc);

        JLabel lbl_TongTien = new JLabel("Tổng tiền");
        lbl_TongTien.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_TongTien.setBounds(550, 60, 102, 26);
        getContentPane().add(lbl_TongTien);

//        btn_XacNhan = new RoundedButton("Xác nhận", new Color(76, 175, 80), Color.WHITE, 30);
//        btn_XacNhan.setFont(new Font("Times New Roman", Font.BOLD, 14));
//        btn_XacNhan.setBounds(430, 570, 120, 30);
//        getContentPane().add(btn_XacNhan);
//
//        btn_Huy = new RoundedButton("Hủy", new Color(244, 67, 54), Color.WHITE, 30);
//        btn_Huy.setFont(new Font("Times New Roman", Font.BOLD, 14));
//        btn_Huy.setBounds(612, 570, 120, 30);
//        getContentPane().add(btn_Huy);

        btn_XacNhan = new JButton("Xác nhận");
        btn_XacNhan.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_XacNhan.setBounds(698, 550, 120, 30);
        getContentPane().add(btn_XacNhan);

        btn_Huy = new JButton("Hủy");
        btn_Huy.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_Huy.setBounds(892, 550, 120, 30);
        getContentPane().add(btn_Huy);

        pnl_TongTien = new JPanel();
        pnl_TongTien.setLayout(null);
        pnl_TongTien.setBackground(Color.WHITE);
        pnl_TongTien.setBounds(550, 96, 525, 180);
        getContentPane().add(pnl_TongTien);

        lbl_PhuongThucThanhToanTrongPnlTongTien = new JLabel("Phương thức thanh toán:");
        lbl_PhuongThucThanhToanTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_PhuongThucThanhToanTrongPnlTongTien.setBounds(10, 10, 163, 18);
        pnl_TongTien.add(lbl_PhuongThucThanhToanTrongPnlTongTien);

        lbl_TongTienTrongPnlTongTien_1 = new JLabel("Tổng tiền trả:");
        lbl_TongTienTrongPnlTongTien_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TongTienTrongPnlTongTien_1.setBounds(10, 38, 163, 18);
        pnl_TongTien.add(lbl_TongTienTrongPnlTongTien_1);

        lbl_TienCocTrongPnlTongTien_1 = new JLabel("Tiền cọc:");
        lbl_TienCocTrongPnlTongTien_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCocTrongPnlTongTien_1.setBounds(10, 66, 163, 18);
        pnl_TongTien.add(lbl_TienCocTrongPnlTongTien_1);

        lbl_TienNhanTuKhachTrongPnlTongTien_1 = new JLabel("Tiền nhận từ khách:");
        lbl_TienNhanTuKhachTrongPnlTongTien_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienNhanTuKhachTrongPnlTongTien_1.setBounds(10, 122, 163, 18);
        pnl_TongTien.add(lbl_TienNhanTuKhachTrongPnlTongTien_1);

        lbl_TienTraLaiKhachTrongPnlTongTien_1 = new JLabel("Tiền trả lại khách:");
        lbl_TienTraLaiKhachTrongPnlTongTien_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienTraLaiKhachTrongPnlTongTien_1.setBounds(10, 150, 163, 18);
        pnl_TongTien.add(lbl_TienTraLaiKhachTrongPnlTongTien_1);

        lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien_1 = new JLabel("Tiền mặt");
        lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien_1.setBounds(395, 10, 120, 18);
        pnl_TongTien.add(lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien_1);

        lbl_TienCuaTongTienTrongPnlTongTien_1 = new JLabel("0 VND");
        lbl_TienCuaTongTienTrongPnlTongTien_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaTongTienTrongPnlTongTien_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaTongTienTrongPnlTongTien_1.setBounds(395, 38, 120, 18);
        pnl_TongTien.add(lbl_TienCuaTongTienTrongPnlTongTien_1);

        lbl_TienCuaTienCocTrongPnlTongTien_1 = new JLabel("0 VND");
        lbl_TienCuaTienCocTrongPnlTongTien_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaTienCocTrongPnlTongTien_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaTienCocTrongPnlTongTien_1.setBounds(395, 66, 120, 18);
        pnl_TongTien.add(lbl_TienCuaTienCocTrongPnlTongTien_1);

        lbl_TienCuaTienNhanTuKhachTrongPnlTongTien_1 = new JLabel("0 VND");
        lbl_TienCuaTienNhanTuKhachTrongPnlTongTien_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaTienNhanTuKhachTrongPnlTongTien_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaTienNhanTuKhachTrongPnlTongTien_1.setBounds(395, 122, 120, 18);
        pnl_TongTien.add(lbl_TienCuaTienNhanTuKhachTrongPnlTongTien_1);

        lbl_TienCuaTienTraLaiKhachTrongPnlTongTien_1 = new JLabel("0 VND");
        lbl_TienCuaTienTraLaiKhachTrongPnlTongTien_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaTienTraLaiKhachTrongPnlTongTien_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaTienTraLaiKhachTrongPnlTongTien_1.setBounds(395, 150, 120, 18);
        pnl_TongTien.add(lbl_TienCuaTienTraLaiKhachTrongPnlTongTien_1);

        lbl_KhuyenMaiDuocApDung = new JLabel("Khuyến mãi được áp dụng:");
        lbl_KhuyenMaiDuocApDung.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_KhuyenMaiDuocApDung.setBounds(10, 94, 163, 18);
        pnl_TongTien.add(lbl_KhuyenMaiDuocApDung);

        lbl_TenKhuyenMai = new JLabel("KM");
        lbl_TenKhuyenMai.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TenKhuyenMai.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TenKhuyenMai.setBounds(395, 94, 120, 18);
        pnl_TongTien.add(lbl_TenKhuyenMai);

        lbl_TienTraKhach = new JLabel("Tiền trả khách:");
        lbl_TienTraKhach.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_TienTraKhach.setBounds(550, 390, 128, 24);
        getContentPane().add(lbl_TienTraKhach);

        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(698, 394, 377, 25);
        getContentPane().add(textField);
    }

    // Thêm phương thức setThongTin
//    public void setThongTin(KhachHang khachHang, ArrayList<Phong> danhSachPhong, String loaiThue) {
//        controller.hienThiThongTin(khachHang, danhSachPhong);
//    }

    class RoundedButton extends JButton {
        private int radius;
        private Color originalBg;
        private boolean isHovered = false;
        private boolean isPressed = false;

        public RoundedButton(String label, Color bg, Color fg, int radius) {
            super(label);
            this.radius = radius;
            this.originalBg = bg;
            setBackground(bg);
            setForeground(fg);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorder(null);

            // Add mouse listener for hover and press effects
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    isHovered = true;
                    repaint();
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    isHovered = false;
                    repaint();
                }

                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    isPressed = true;
                    repaint();
                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                    isPressed = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Determine color based on state
            Color bgColor = originalBg;
            if (isPressed) {
                bgColor = originalBg.darker();  // Darker on press
            } else if (isHovered) {
                bgColor = originalBg.brighter();  // Brighter on hover
            }

            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }
    }

    // Thêm getter cho trạng thái xác nhận
    public boolean isConfirmed() {
        return confirmed;
    }
}