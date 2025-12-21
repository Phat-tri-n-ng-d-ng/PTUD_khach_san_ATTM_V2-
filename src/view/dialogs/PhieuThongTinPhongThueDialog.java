package view.dialogs;

import com.toedter.calendar.JDateChooser;
import entitys.Phong;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.Dimension;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;

public class PhieuThongTinPhongThueDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    public JTextField txt_SDT;
    public JTextField txt_HoTen;
    public JTextField txt_CCCD;
    public JDateChooser ngayBatDau;
    public JDateChooser ngayKetThuc;
    public DefaultTableModel model;
    public JButton btnThoat;
    public JButton btnDoiPhong;
    Phong phong;
    public JTextField txt_SDTNguoiO;
    public JTextField txt_HoTenNguoiO;
    public JTextField txt_CCCDNguoiO;
    public JTable table_NguoiO;
	public DefaultTableModel model_NguoiO;
	public JDateChooser ngaySinh;
	public JRadioButton rdbtn_Nam;
	public JRadioButton rdbtn_Nu;
	public JButton btnTraPhong;
	public DefaultTableModel modelPhongHienTai;
	public JTable tablePhongHienTai;
	public JButton btn_Them;

    public PhieuThongTinPhongThueDialog() {
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

        JLabel lblTieuDeForm = new JLabel("Thông tin phòng ");
        lblTieuDeForm.setForeground(new Color(10, 110, 189));
        lblTieuDeForm.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTieuDeForm.setBounds(430, 3, 302, 40);
        getContentPane().add(lblTieuDeForm);

        JLabel lblFromThongTinKhachDat = new JLabel("Thông tin khách hàng đặt");
        lblFromThongTinKhachDat.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblFromThongTinKhachDat.setBounds(13, 60, 302, 26);
        getContentPane().add(lblFromThongTinKhachDat);

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

        // Tạo nhóm và thêm radio button vào nhóm
        ButtonGroup paymentGroup = new ButtonGroup();

        JLabel lbl_DanhSachNguoiO = new JLabel("Danh sách người ở");
        lbl_DanhSachNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_DanhSachNguoiO.setBounds(552, 323, 505, 26);
        getContentPane().add(lbl_DanhSachNguoiO);


        JLabel lbl_ThoiGianThue = new JLabel("Thời gian thuê");
        lbl_ThoiGianThue.setBackground(new Color(255, 255, 255));
        lbl_ThoiGianThue.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_ThoiGianThue.setBounds(552, 197, 505, 26);
        getContentPane().add(lbl_ThoiGianThue);

        JPanel pnl_ThoiGianThue = new JPanel();
        pnl_ThoiGianThue.setBackground(new Color(255, 255, 255));
        pnl_ThoiGianThue.setBounds(552, 228, 524, 80);
        getContentPane().add(pnl_ThoiGianThue);
        pnl_ThoiGianThue.setLayout(null);

        JLabel lbl_NgayBatDau = new JLabel("Ngày bắt đầu: ");
        lbl_NgayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_NgayBatDau.setBounds(10, 10, 100, 20);
        pnl_ThoiGianThue.add(lbl_NgayBatDau);

        ngayBatDau = new JDateChooser();
        ngayBatDau.setDateFormatString("dd/MM/yyyy");
        ngayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayBatDau.setBounds(140, 9, 374, 25);
        ngayBatDau.setEnabled(false);
        pnl_ThoiGianThue.add(ngayBatDau);

        JLabel lbl_TienKhachDuaTrongPnlTongTien = new JLabel("Ngày kết thúc: ");
        lbl_TienKhachDuaTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_TienKhachDuaTrongPnlTongTien.setBounds(10, 50, 100, 20);
        pnl_ThoiGianThue.add(lbl_TienKhachDuaTrongPnlTongTien);

        ngayKetThuc = new JDateChooser();
        ngayKetThuc.setDateFormatString("dd/MM/yyyy");
        ngayKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayKetThuc.setBounds(140, 49, 374, 25);
        ngayKetThuc.setEnabled(false);
        pnl_ThoiGianThue.add(ngayKetThuc);

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

        btnDoiPhong = new JButton("Đổi phòng");
        btnDoiPhong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnDoiPhong.setBounds(552, 540, 140, 26);
        btnDoiPhong.setBackground(Color.white);
        getContentPane().add(btnDoiPhong);

        btnThoat = new JButton("Thoát");
        btnThoat.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnThoat.setBounds(936, 540, 140, 26);
        btnThoat.setBackground(new Color(255, 182, 193));
        getContentPane().add(btnThoat);

        JLabel lblFromThongTinKhachO = new JLabel("Thông tin khách hàng ở");
        lblFromThongTinKhachO.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblFromThongTinKhachO.setBounds(10, 273, 232, 26);
        getContentPane().add(lblFromThongTinKhachO);

        JPanel pnl_ThongTinKhachHang_1 = new JPanel();
        pnl_ThongTinKhachHang_1.setLayout(null);
        pnl_ThongTinKhachHang_1.setBorder(BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(new Color(200, 200, 200), HIDE_ON_CLOSE)// Viền ngoài

                        , null

                ));
        pnl_ThongTinKhachHang_1.setBackground(Color.WHITE);
        pnl_ThongTinKhachHang_1.setBounds(10, 305, 525, 261);
        getContentPane().add(pnl_ThongTinKhachHang_1);

        JLabel lbl_SDT_NguoiO = new JLabel("Số điện thoại: ");
        lbl_SDT_NguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_SDT_NguoiO.setBounds(10, 20, 102, 20);
        pnl_ThongTinKhachHang_1.add(lbl_SDT_NguoiO);

        JLabel lbl_HoTen_NguoiO = new JLabel("Họ tên:");
        lbl_HoTen_NguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_HoTen_NguoiO.setBounds(10, 70, 102, 20);
        pnl_ThongTinKhachHang_1.add(lbl_HoTen_NguoiO);

        JLabel lbl_CCCD_NguoiO = new JLabel("CCCD:");
        lbl_CCCD_NguoiO.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_CCCD_NguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_CCCD_NguoiO.setBounds(10, 120, 102, 20);
        pnl_ThongTinKhachHang_1.add(lbl_CCCD_NguoiO);

        txt_SDTNguoiO = new JTextField();
        txt_SDTNguoiO.setColumns(10);
        txt_SDTNguoiO.setBounds(132, 19, 363, 25);
        pnl_ThongTinKhachHang_1.add(txt_SDTNguoiO);

        txt_HoTenNguoiO = new JTextField();
        txt_HoTenNguoiO.setColumns(10);
        txt_HoTenNguoiO.setBounds(132, 69, 363, 25);
        pnl_ThongTinKhachHang_1.add(txt_HoTenNguoiO);

        txt_CCCDNguoiO = new JTextField();
        txt_CCCDNguoiO.setColumns(10);
        txt_CCCDNguoiO.setBounds(132, 119, 363, 25);
        pnl_ThongTinKhachHang_1.add(txt_CCCDNguoiO);

        JLabel lbl_NgaySinh = new JLabel("Ngày sinh:");
        lbl_NgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_NgaySinh.setBounds(10, 170, 112, 20);
        pnl_ThongTinKhachHang_1.add(lbl_NgaySinh);

        JLabel lbl_GioiTinh = new JLabel("Giới tính:");
        lbl_GioiTinh.setBackground(new Color(255, 255, 255));
        lbl_GioiTinh.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_GioiTinh.setBounds(302, 170, 67, 20);
        pnl_ThongTinKhachHang_1.add(lbl_GioiTinh);

         btn_Them = new JButton("Thêm ");
        btn_Them.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btn_Them.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btn_Them.setBounds(396, 218, 99, 33);
        pnl_ThongTinKhachHang_1.add(btn_Them);

         rdbtn_Nam = new JRadioButton("Nam");
        rdbtn_Nam.setSelected(true);
        rdbtn_Nam.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_Nam.setBackground(new Color(255, 255, 255));
        rdbtn_Nam.setBounds(368, 170, 67, 20);
        pnl_ThongTinKhachHang_1.add(rdbtn_Nam);

         rdbtn_Nu = new JRadioButton("Nữ");
        rdbtn_Nu.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_Nu.setBackground(new Color(255, 255, 255));
        rdbtn_Nu.setBounds(437, 170, 58, 20);
        pnl_ThongTinKhachHang_1.add(rdbtn_Nu);
        
        ButtonGroup gr= new ButtonGroup();
        gr.add(rdbtn_Nam);gr.add(rdbtn_Nu);
        
         ngaySinh = new JDateChooser();
        ngaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngaySinh.setDateFormatString("dd/MM/yyyy");
        ngaySinh.setBounds(132, 171, 152, 25);
        pnl_ThongTinKhachHang_1.add(ngaySinh);

        JPanel pnl_DanhSachNguoiO = new JPanel();
        pnl_DanhSachNguoiO.setLayout(null);
        pnl_DanhSachNguoiO.setBackground(Color.WHITE);
        pnl_DanhSachNguoiO.setBounds(552, 355, 525, 178);
        getContentPane().add(pnl_DanhSachNguoiO);

        model_NguoiO = new DefaultTableModel(
                new String[] {"Tên người ở", "Ngày sinh", "Giới tính", "SĐT", "CCCD"}, 0
        );

        table_NguoiO = new JTable(model_NguoiO);
        table_NguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        table_NguoiO.setBackground(Color.WHITE);
        table_NguoiO.getTableHeader().setBackground(Color.WHITE);

        JTableHeader headerNguoiO = table_NguoiO.getTableHeader();
        headerNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        headerNguoiO.setPreferredSize(new Dimension(headerNguoiO.getWidth(), 35));

        JScrollPane scrollPane_1 = new JScrollPane(table_NguoiO);
        scrollPane_1.setBounds(10, 10, 505, 158);
        scrollPane_1.setBackground(Color.WHITE);
        scrollPane_1.getViewport().setBackground(Color.WHITE);

        pnl_DanhSachNguoiO.add(scrollPane_1);

         btnTraPhong = new JButton("Trả phòng");
        btnTraPhong.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnTraPhong.setBackground(Color.white);
        btnTraPhong.setBounds(744, 540, 140, 26);
        getContentPane().add(btnTraPhong);
        
        JPanel pnl_DanhSachPhong = new JPanel();
        pnl_DanhSachPhong.setBackground(Color.WHITE);
        pnl_DanhSachPhong.setBounds(552, 85, 525, 88);
        pnl_DanhSachPhong.setLayout(null);
        getContentPane().add(pnl_DanhSachPhong);

         modelPhongHienTai = new DefaultTableModel(
                new String[] { "Mã phòng", "Loại phòng", "SLTĐ", "Giá", "Tiền cọc" }, 0
        );

         tablePhongHienTai = new JTable(modelPhongHienTai);
        tablePhongHienTai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        tablePhongHienTai.setBackground(Color.WHITE);
        tablePhongHienTai.getTableHeader().setBackground(Color.WHITE);

        JTableHeader header = tablePhongHienTai.getTableHeader();
        header.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        JScrollPane PhongHienTai = new JScrollPane(tablePhongHienTai);
        PhongHienTai.setBounds(10, 10, 505, 66);
        PhongHienTai.getViewport().setBackground(Color.WHITE);
        pnl_DanhSachPhong.add(PhongHienTai);

        JLabel lbl_PhongHienTai = new JLabel("Phòng hiện tại");
        lbl_PhongHienTai.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_PhongHienTai.setBounds(552, 55, 147, 26);
        getContentPane().add(lbl_PhongHienTai);

        
        


    }
}