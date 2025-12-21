package view.dialogs;

import com.toedter.calendar.JDateChooser;
import entitys.Phong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.Dimension;

import java.awt.Font;

public class PhieuTraPhongDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    public JLabel lbl_TienThueTrongPnlTongTien_1_1;
    public  JLabel lbl_TienCuaTongTienThanhToanTrongPnlTongTienThanhToan;
    public  JLabel lbl_TienCuaThue;
    public  JLabel lbl_TienCuaKhuyenMaiPnlTongTien;
    public JRadioButton rdbtn_ChuyenKhoan;
    public JRadioButton rdbtn_TienMat;
    public JTextField txt_SDT;
    public JTextField txt_HoTen;
    public JTextField txt_CCCD;
    public JTextField txt_TienKhachDua;
    public JDateChooser ngayBatDau;
    public JDateChooser ngayKetThuc;
    public JLabel lbl_PhuongThucThanhToanTrongPnlTongTien;
    public JLabel lbl_TongTienPhongTrongPnlTongTien;
    public JLabel lbl_PhiDoiPhongTrongPnlTongTien;
    public JLabel lbl_TienNhanTuKhachTrongPnlTongTien;
    public JLabel lbl_TienTraLaiKhachTrongPnlTongTien;
    public JLabel lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien;
    public JLabel lbl_TienCuaTongTienTrongPnlTongTien;
    public JLabel lbl_TienCuaPhiDoiPhongTrongPnlTongTien;
    public JLabel lbl_TienCuaTienNhanTuKhachTrongPnlTongTien;
    public JLabel lbl_TienCuaTienTraLaiKhachTrongPnlTongTien;
    public JLabel lbl_TienCuaKhuyenMaiTheoHangKH;
    public JLabel lbl_KhuyenMaiTheoHangKhachHang;
    public JTable table;
    public DefaultTableModel model;
    public JTextField txt_TienTraLai;
    public JButton btnXacNhan;
    public JButton btnHuy;
    Phong phong;
	private JLabel lbl_QR;

    public PhieuTraPhongDialog() {
        getContentPane().setBackground(new Color(236, 247, 255));
        setBounds(100, 100, 1100, 650);
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
        lblFromThongTinKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblFromThongTinKhachHang.setBounds(13, 60, 302, 26);
        getContentPane().add(lblFromThongTinKhachHang);

        JPanel pnl_ThongTinKhachHang = new JPanel();
        // Tạo viền mờ màu xám nhạt với bo góc 15px
        pnl_ThongTinKhachHang.setBorder(null);
        pnl_ThongTinKhachHang.setBackground(new Color(255, 255, 255));
        pnl_ThongTinKhachHang.setBounds(10, 90, 525, 165);
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
        pnl_ThongTinKhachHang.add(txt_SDT);
        txt_SDT.setColumns(10);

        txt_HoTen = new JTextField();
        txt_HoTen.setColumns(10);
        txt_HoTen.setBounds(132, 69, 373, 25);
        pnl_ThongTinKhachHang.add(txt_HoTen);

        txt_CCCD = new JTextField();
        txt_CCCD.setColumns(10);
        txt_CCCD.setBounds(132, 119, 373, 25);
        pnl_ThongTinKhachHang.add(txt_CCCD);

        JLabel lbl_PhuongThucThangToan = new JLabel("Phương thức thanh toán: ");
        lbl_PhuongThucThangToan.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_PhuongThucThangToan.setBounds(561, 321, 155, 20);
        getContentPane().add(lbl_PhuongThucThangToan);

        rdbtn_TienMat = new JRadioButton("Tiền mặt");
        rdbtn_TienMat.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_TienMat.setBackground(new Color(236, 247, 255));
        rdbtn_TienMat.setBounds(751, 321, 102, 20);
        getContentPane().add(rdbtn_TienMat);

        rdbtn_ChuyenKhoan = new JRadioButton("Chuyển khoản");
        rdbtn_ChuyenKhoan.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_ChuyenKhoan.setBackground(new Color(236, 247, 255));
        rdbtn_ChuyenKhoan.setBounds(891, 321, 113, 20);
        getContentPane().add(rdbtn_ChuyenKhoan);

        // Tạo nhóm và thêm radio button vào nhóm
        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(rdbtn_TienMat);
        paymentGroup.add(rdbtn_ChuyenKhoan);

        // Nếu muốn một radio mặc định được chọn
        rdbtn_TienMat.setSelected(true);


        JLabel lbl_TienKhachDua = new JLabel("Tiền khách đưa:");
        lbl_TienKhachDua.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_TienKhachDua.setBounds(561, 364, 128, 24);
        getContentPane().add(lbl_TienKhachDua);

        txt_TienKhachDua = new JTextField();
        txt_TienKhachDua.setBounds(699, 363, 377, 25);
        getContentPane().add(txt_TienKhachDua);
        txt_TienKhachDua.setColumns(10);

        JLabel lbl_DanhSachPhong = new JLabel("Danh sách phòng");
        lbl_DanhSachPhong.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_DanhSachPhong.setBounds(10, 276, 147, 26);
        getContentPane().add(lbl_DanhSachPhong);

        JPanel pnl_DanhSachPhong = new JPanel();
        pnl_DanhSachPhong.setBackground(new Color(255, 255, 255));
        pnl_DanhSachPhong.setBounds(10, 306, 525, 117);
        pnl_DanhSachPhong.setLayout(null);
        getContentPane().add(pnl_DanhSachPhong);


        model = new DefaultTableModel(new String[] {"Mã phòng","Loại phòng","SLTĐ","Số ngày ở","Giá","Tiền cọc","Thành tiền"}, 0);
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(new Color(255, 255, 255));
        scrollPane.setBounds(10, 10, 505, 98);
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
        lbl_ThoiGianThue.setBounds(10, 444, 128, 26);
        getContentPane().add(lbl_ThoiGianThue);

        JPanel pnl_ThoiGianThue = new JPanel();
        pnl_ThoiGianThue.setBackground(new Color(255, 255, 255));
        pnl_ThoiGianThue.setBounds(10, 474, 525, 80);
        getContentPane().add(pnl_ThoiGianThue);
        pnl_ThoiGianThue.setLayout(null);

        JLabel lbl_NgayBatDau = new JLabel("Ngày bắt đầu: ");
        lbl_NgayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_NgayBatDau.setBounds(10, 10, 100, 20);
        pnl_ThoiGianThue.add(lbl_NgayBatDau);

        ngayBatDau = new JDateChooser();
        ngayBatDau.setDateFormatString("dd/MM/yyyy");
        ngayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayBatDau.setBounds(140, 9, 375, 25);
        ngayBatDau.setEnabled(false);
        pnl_ThoiGianThue.add(ngayBatDau);

        JLabel lbl_TienKhachDuaTrongPnlTongTien = new JLabel("Ngày kết thúc: ");
        lbl_TienKhachDuaTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_TienKhachDuaTrongPnlTongTien.setBounds(10, 50, 100, 20);
        pnl_ThoiGianThue.add(lbl_TienKhachDuaTrongPnlTongTien);

        ngayKetThuc = new JDateChooser();
        ngayKetThuc.setDateFormatString("dd/MM/yyyy");
        ngayKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayKetThuc.setBounds(140, 49, 375, 25);
        pnl_ThoiGianThue.add(ngayKetThuc);

        JLabel lbl_TongTien = new JLabel("Tổng tiền");
        lbl_TongTien.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_TongTien.setBounds(546, 60, 102, 26);
        getContentPane().add(lbl_TongTien);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(545, 90, 531, 199);
        getContentPane().add(panel);
        panel.setLayout(null);

        lbl_PhuongThucThanhToanTrongPnlTongTien = new JLabel("Phương thức thanh toán:");
        lbl_PhuongThucThanhToanTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_PhuongThucThanhToanTrongPnlTongTien.setBounds(10, 10, 163, 18);
        panel.add(lbl_PhuongThucThanhToanTrongPnlTongTien);

        lbl_TongTienPhongTrongPnlTongTien = new JLabel("Tổng tiền phòng:");
        lbl_TongTienPhongTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TongTienPhongTrongPnlTongTien.setBounds(10, 30, 163, 18);
        panel.add(lbl_TongTienPhongTrongPnlTongTien);

        lbl_PhiDoiPhongTrongPnlTongTien = new JLabel("Phí đổi phòng:");
        lbl_PhiDoiPhongTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_PhiDoiPhongTrongPnlTongTien.setBounds(10, 90, 163, 18);
        panel.add(lbl_PhiDoiPhongTrongPnlTongTien);

        lbl_TienNhanTuKhachTrongPnlTongTien = new JLabel("Tiền nhận từ khách:");
        lbl_TienNhanTuKhachTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienNhanTuKhachTrongPnlTongTien.setBounds(10, 150, 163, 18);
        panel.add(lbl_TienNhanTuKhachTrongPnlTongTien);

        lbl_TienTraLaiKhachTrongPnlTongTien = new JLabel("Tiền trả lại khách:");
        lbl_TienTraLaiKhachTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienTraLaiKhachTrongPnlTongTien.setBounds(10, 170, 163, 18);
        panel.add(lbl_TienTraLaiKhachTrongPnlTongTien);

        lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien = new JLabel("Tiền mặt");
        lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setBounds(431, 10, 84, 18);
        panel.add(lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien);


        lbl_TienCuaTongTienTrongPnlTongTien = new JLabel("0 VND");
        lbl_TienCuaTongTienTrongPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaTongTienTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaTongTienTrongPnlTongTien.setBounds(364, 30, 151, 18);
        panel.add(lbl_TienCuaTongTienTrongPnlTongTien);

        lbl_TienCuaPhiDoiPhongTrongPnlTongTien = new JLabel("0 VND");
        lbl_TienCuaPhiDoiPhongTrongPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaPhiDoiPhongTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaPhiDoiPhongTrongPnlTongTien.setBounds(364, 90, 151, 18);
        panel.add(lbl_TienCuaPhiDoiPhongTrongPnlTongTien);

        lbl_TienCuaTienNhanTuKhachTrongPnlTongTien = new JLabel("0 VND");
        lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setBounds(352, 150, 163, 18);
        panel.add(lbl_TienCuaTienNhanTuKhachTrongPnlTongTien);

        lbl_TienCuaTienTraLaiKhachTrongPnlTongTien = new JLabel("0 VND");
        lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setBounds(374, 170, 141, 18);
        panel.add(lbl_TienCuaTienTraLaiKhachTrongPnlTongTien);

        JLabel lbl_KhuyenMaiTrongPnlTongTien = new JLabel("Khuyến mãi:");
        lbl_KhuyenMaiTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_KhuyenMaiTrongPnlTongTien.setBounds(10, 50, 163, 18);
        panel.add(lbl_KhuyenMaiTrongPnlTongTien);

        lbl_TienCuaKhuyenMaiPnlTongTien = new JLabel("0 VND");
        lbl_TienCuaKhuyenMaiPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaKhuyenMaiPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaKhuyenMaiPnlTongTien.setBounds(374, 50, 141, 18);
        panel.add(lbl_TienCuaKhuyenMaiPnlTongTien);

        JLabel lbl_TongTienThanhToanTrongPnlTongTien_1 = new JLabel("Tổng tiền thanh toán:");
        lbl_TongTienThanhToanTrongPnlTongTien_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TongTienThanhToanTrongPnlTongTien_1.setBounds(10, 130, 163, 18);
        panel.add(lbl_TongTienThanhToanTrongPnlTongTien_1);

        lbl_TienCuaTongTienThanhToanTrongPnlTongTienThanhToan = new JLabel("0 VND");
        lbl_TienCuaTongTienThanhToanTrongPnlTongTienThanhToan.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaTongTienThanhToanTrongPnlTongTienThanhToan.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaTongTienThanhToanTrongPnlTongTienThanhToan.setBounds(374, 130, 141, 18);
        panel.add(lbl_TienCuaTongTienThanhToanTrongPnlTongTienThanhToan);

        lbl_TienCuaThue = new JLabel("0 VND");
        lbl_TienCuaThue.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaThue.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaThue.setBounds(364, 110, 151, 18);
        panel.add(lbl_TienCuaThue);

        lbl_TienThueTrongPnlTongTien_1_1 = new JLabel("Thuế (10% tổng tiền):");
        lbl_TienThueTrongPnlTongTien_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienThueTrongPnlTongTien_1_1.setBounds(10, 110, 163, 18);
        panel.add(lbl_TienThueTrongPnlTongTien_1_1);

        lbl_TienCuaKhuyenMaiTheoHangKH = new JLabel("0 VND");
        lbl_TienCuaKhuyenMaiTheoHangKH.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaKhuyenMaiTheoHangKH.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaKhuyenMaiTheoHangKH.setBounds(374, 70, 141, 18);
        panel.add(lbl_TienCuaKhuyenMaiTheoHangKH);

        lbl_KhuyenMaiTheoHangKhachHang = new JLabel("Khuyến mãi theo hạng khách hàng:");
        lbl_KhuyenMaiTheoHangKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_KhuyenMaiTheoHangKhachHang.setBounds(10, 70, 222, 18);
        panel.add(lbl_KhuyenMaiTheoHangKhachHang);

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

//         btn_XacNhan = new RoundedButton("Xác nhận", new Color(76, 175, 80), Color.WHITE, 30);
//        btn_XacNhan.setFont(new Font("Times New Roman", Font.BOLD, 14));
//        btn_XacNhan.setBounds(430, 570, 120, 30);
//        getContentPane().add(btn_XacNhan);
//
//         btn_Huy = new RoundedButton("Hủy", new Color(244, 67, 54), Color.WHITE, 30);
//        btn_Huy.setFont(new Font("Times New Roman", Font.BOLD, 14));
//        btn_Huy.setBounds(612, 570, 120, 30);
//        getContentPane().add(btn_Huy);

        txt_TienTraLai = new JTextField();
        txt_TienTraLai.setEditable(false);
        txt_TienTraLai.setColumns(10);
        txt_TienTraLai.setBounds(699, 398, 377, 25);
        getContentPane().add(txt_TienTraLai);
        

        JLabel lbl_TienTraLaiKhach = new JLabel("Tiền trả lại:");
        lbl_TienTraLaiKhach.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_TienTraLaiKhach.setBounds(561, 399, 128, 24);
        getContentPane().add(lbl_TienTraLaiKhach);

        btnXacNhan = new JButton("Xác nhận");
        btnXacNhan.setBounds(653, 528, 140, 26);
        btnXacNhan.setBackground(new Color(144, 238, 144));
        getContentPane().add(btnXacNhan);

        btnHuy = new JButton("Hủy");
        btnHuy.setBounds(872, 528, 140, 26);
        btnHuy.setBackground(new Color(255, 182, 193));
        getContentPane().add(btnHuy);


    }
}