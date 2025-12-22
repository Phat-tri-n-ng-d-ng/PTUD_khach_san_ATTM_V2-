
package view.mainframe;

import controllers.DangNhapController;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;

public class DangNhapFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    public JPanel contentPane;
    public JTextField txt_TaiKhoan;
    public JPasswordField passwordField_MatKhau;
    public JButton btn_DangNhap;
    public JButton btn_QuenMatKhau;


    public DangNhapFrame() {
        jbInit();
    }
    private void jbInit() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 860, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel bên trái - Thông tin khách sạn
        JPanel pnl_Left = new JPanel();
        pnl_Left.setBackground(new Color(10, 110, 189));
        pnl_Left.setBounds(0, 0, 423, 563); // Tăng width để cân đối
        contentPane.add(pnl_Left);
        pnl_Left.setLayout(null);

        // Icon khách sạn lớn ở giữa
        JLabel lbl_IconKhachSan = new JLabel();
        lbl_IconKhachSan.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_IconKhachSan.setBounds(125, 120, 180, 200);
        try {
            FontIcon hotelIcon = FontIcon.of(FontAwesomeSolid.HOTEL, 120, Color.WHITE);
            lbl_IconKhachSan.setIcon(hotelIcon);
        } catch (Exception e) {
            System.out.println("Lỗi tải icon: " + e.getMessage());
        }
        pnl_Left.add(lbl_IconKhachSan);

        // Tên khách sạn
        JLabel lbl_TenKhachSan = new JLabel("KHÁCH SAN ATTM");
        lbl_TenKhachSan.setForeground(Color.WHITE);
        lbl_TenKhachSan.setFont(new Font("Arial", Font.BOLD, 32));
        lbl_TenKhachSan.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_TenKhachSan.setBounds(50, 330, 350, 40);
        pnl_Left.add(lbl_TenKhachSan);

        // Dòng mô tả
        JLabel lbl_MoTa = new JLabel("Nơi nghỉ ngơi hoàn hảo cho bạn");
        lbl_MoTa.setForeground(Color.WHITE);
        lbl_MoTa.setFont(new Font("Arial", Font.PLAIN, 18));
        lbl_MoTa.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_MoTa.setBounds(50, 380, 350, 25);
        pnl_Left.add(lbl_MoTa);

        // Thông tin liên hệ ở footer bên trái
        JLabel lbl_Contact = new JLabel("Hotline: 0966-086-570 | Email: info@attm.com");
        lbl_Contact.setForeground(new Color(200, 200, 255));
        lbl_Contact.setFont(new Font("Arial", Font.PLAIN, 12));
        lbl_Contact.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Contact.setBounds(50, 500, 350, 20);
        pnl_Left.add(lbl_Contact);

        JPanel pnl_ThongTinDangNhap = new JPanel();
        pnl_ThongTinDangNhap.setBackground(new Color(236, 247, 255));
        pnl_ThongTinDangNhap.setBounds(423, 0, 423, 563);
        contentPane.add(pnl_ThongTinDangNhap);
        pnl_ThongTinDangNhap.setLayout(null);

        JLabel lbl_TieuDeDangNhap = new JLabel("ĐĂNG NHẬP");
        lbl_TieuDeDangNhap.setForeground(new Color(10, 110, 189));
        lbl_TieuDeDangNhap.setBackground(new Color(10, 110, 189));
        lbl_TieuDeDangNhap.setFont(new Font("Tahoma", Font.BOLD, 24));
        lbl_TieuDeDangNhap.setBounds(140, 60, 148, 34);
        pnl_ThongTinDangNhap.add(lbl_TieuDeDangNhap);

        JLabel lbl_TaiKhoan = new JLabel("Tài khoản");
        lbl_TaiKhoan.setFont(new Font("Arial", Font.BOLD, 16));
        lbl_TaiKhoan.setBounds(20, 140, 90, 20);
        pnl_ThongTinDangNhap.add(lbl_TaiKhoan);

        txt_TaiKhoan = new JTextField();
        txt_TaiKhoan.setFont(new Font("Arial", Font.PLAIN, 16));
        txt_TaiKhoan.setBounds(10, 165, 403, 40);
        pnl_ThongTinDangNhap.add(txt_TaiKhoan);
        txt_TaiKhoan.setColumns(10);

        JLabel lbl_MatKhau = new JLabel("Mật khẩu");
        lbl_MatKhau.setFont(new Font("Arial", Font.BOLD, 16));
        lbl_MatKhau.setBounds(20, 260, 90, 20);
        pnl_ThongTinDangNhap.add(lbl_MatKhau);

        passwordField_MatKhau = new JPasswordField();
        passwordField_MatKhau.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField_MatKhau.setBounds(10, 285, 403, 40);
        pnl_ThongTinDangNhap.add(passwordField_MatKhau);

        btn_QuenMatKhau = new JButton("Quên mật khẩu?");
        btn_QuenMatKhau.setForeground(new Color(10, 110, 189));
        btn_QuenMatKhau.setFont(new Font("Arial", Font.PLAIN, 14));
        btn_QuenMatKhau.setFocusPainted(false);
        btn_QuenMatKhau.setContentAreaFilled(false);
        btn_QuenMatKhau.setBorderPainted(false);
        btn_QuenMatKhau.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn_QuenMatKhau.setBorder(null);
        btn_QuenMatKhau.setBackground(new Color(255, 255, 255));
        btn_QuenMatKhau.setBounds(303, 335, 110, 30);
        pnl_ThongTinDangNhap.add(btn_QuenMatKhau);

        btn_DangNhap = new JButton("Đăng nhập");
        btn_DangNhap.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn_DangNhap.setForeground(new Color(255, 255, 255));
        btn_DangNhap.setBorderPainted(false);
        btn_DangNhap.setBackground(new Color(10, 110, 189));
        btn_DangNhap.setFocusable(false);
        btn_DangNhap.setFont(new Font("Tahoma", Font.BOLD, 18));
        btn_DangNhap.setBounds(10, 420, 403, 45);
        pnl_ThongTinDangNhap.add(btn_DangNhap);

        new DangNhapController(this);
    }
}
