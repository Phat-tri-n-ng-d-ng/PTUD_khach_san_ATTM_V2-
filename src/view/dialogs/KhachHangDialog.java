package view.dialogs;

import com.toedter.calendar.JDateChooser;
import controllers.dialogs.KhachHangDialogController;
import entitys.KhachHang;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;


public class KhachHangDialog extends JDialog{
    private KhachHang khachHang;
    public JTextField txt_MaKH;
    public JTextField txt_TenKhachHang;
    public JTextField txt_SoDienThoai;
    public JTextField txt_Email;
    public JTextField txt_SoCCCD;
    public JTextField txt_DiemTichLuy;
    public JTextField txt_HangKhachHang;
    public JDateChooser ngaySinh;
    public JRadioButton rdbtn_Nam;
    public JRadioButton rdbtn_Nu;
    private KhachHangDialogController khachHangDialogController;

    public KhachHangDialog(JFrame mainframe, KhachHang khachHang) {
        super(mainframe, "Thông tin nhân viên", true);
        this.khachHang = khachHang;
        giaoDienDialog();
        setLocationRelativeTo(mainframe);
    }
    private void giaoDienDialog() {
        setBounds(100, 100, 500, 540);
        setBackground(new Color(255, 255, 255));
        getContentPane().setLayout(null);
        getContentPane().setLayout(null);

        JPanel pnl_ThongTinNhanVien = new JPanel();
        pnl_ThongTinNhanVien.setLayout(null);
        pnl_ThongTinNhanVien.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnl_ThongTinNhanVien.setBackground(Color.WHITE);
        pnl_ThongTinNhanVien.setBounds(10, 53, 466, 400);
        getContentPane().add(pnl_ThongTinNhanVien);

        JLabel lpl_MaKhachHang = new JLabel("Mã khách hàng:");
        lpl_MaKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lpl_MaKhachHang.setBounds(10, 10, 116, 16);
        pnl_ThongTinNhanVien.add(lpl_MaKhachHang);

        txt_MaKH = new JTextField();
        txt_MaKH.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_MaKH.setBounds(10, 33, 446, 30);
        pnl_ThongTinNhanVien.add(txt_MaKH);

        JLabel lpl_TenKhachHang = new JLabel("Tên khách hàng:");
        lpl_TenKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lpl_TenKhachHang.setBounds(10, 73, 143, 16);
        pnl_ThongTinNhanVien.add(lpl_TenKhachHang);

        txt_TenKhachHang = new JTextField();
        txt_TenKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_TenKhachHang.setBounds(10, 96, 446, 30);
        pnl_ThongTinNhanVien.add(txt_TenKhachHang);

        JLabel lpl_SoDienThoai = new JLabel("Số điện thoại:");
        lpl_SoDienThoai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lpl_SoDienThoai.setBounds(10, 261, 92, 16);
        pnl_ThongTinNhanVien.add(lpl_SoDienThoai);

        txt_SoDienThoai = new JTextField();
        txt_SoDienThoai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_SoDienThoai.setBounds(10, 284, 218, 30);
        pnl_ThongTinNhanVien.add(txt_SoDienThoai);

        JLabel lpl_GioiTinh = new JLabel("Giới tính:");
        lpl_GioiTinh.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lpl_GioiTinh.setBounds(12, 204, 104, 16);
        pnl_ThongTinNhanVien.add(lpl_GioiTinh);

        rdbtn_Nam = new JRadioButton("Nam");
        rdbtn_Nam.setSelected(true);
        rdbtn_Nam.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_Nam.setBackground(Color.WHITE);
        rdbtn_Nam.setBounds(10, 234, 65, 21);
        pnl_ThongTinNhanVien.add(rdbtn_Nam);

        rdbtn_Nu = new JRadioButton("Nữ");
        rdbtn_Nu.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_Nu.setBackground(Color.WHITE);
        rdbtn_Nu.setBounds(95, 234, 47, 21);
        pnl_ThongTinNhanVien.add(rdbtn_Nu);

        ButtonGroup groupGioiTinh = new ButtonGroup();
        groupGioiTinh.add(rdbtn_Nam);
        groupGioiTinh.add(rdbtn_Nu);

        JLabel lbl_NgaySinh = new JLabel("Ngày sinh:");
        lbl_NgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_NgaySinh.setBounds(238, 204, 84, 16);
        pnl_ThongTinNhanVien.add(lbl_NgaySinh);

        ngaySinh = new JDateChooser();
        ngaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngaySinh.setDateFormatString("dd/MM/yyyy");
        ngaySinh.setBounds(238, 225, 218, 30);
        pnl_ThongTinNhanVien.add(ngaySinh);

        // Tính ngày hợp lệ (ít nhất 18 tuổi)
        LocalDate today = LocalDate.now();
        LocalDate maxDate = today.minusYears(18);
        LocalDate minDate = today.minusYears(100); // Giới hạn tối đa 100 tuổi nếu cần

        // Thiết lập khoảng hợp lệ
        ngaySinh.setMaxSelectableDate(java.sql.Date.valueOf(maxDate));
        ngaySinh.setMinSelectableDate(java.sql.Date.valueOf(minDate));

        // Set ngày mặc định (ví dụ: đủ 18 tuổi tính đến hôm nay)
        ngaySinh.setDate(java.sql.Date.valueOf(maxDate));

        // Khi click hoặc focus thì tự động mở popup
        Component editor = ngaySinh.getDateEditor().getUiComponent();
        editor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ngaySinh.getCalendarButton().doClick();
            }
        });

        JLabel lbl_HangKhachHang = new JLabel("Hạng khách hàng:");
        lbl_HangKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_HangKhachHang.setBounds(10, 324, 191, 16);
        pnl_ThongTinNhanVien.add(lbl_HangKhachHang);

        txt_Email = new JTextField();
        txt_Email.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_Email.setBounds(238, 284, 218, 30);
        pnl_ThongTinNhanVien.add(txt_Email);

        JLabel lpl_Email = new JLabel("Email:");
        lpl_Email.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lpl_Email.setBounds(238, 261, 92, 16);
        pnl_ThongTinNhanVien.add(lpl_Email);

        JLabel lpl_DiemTichLuy = new JLabel("Điểm tích lũy:");
        lpl_DiemTichLuy.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lpl_DiemTichLuy.setBounds(239, 324, 104, 16);
        pnl_ThongTinNhanVien.add(lpl_DiemTichLuy);

        txt_SoCCCD = new JTextField();
        txt_SoCCCD.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_SoCCCD.setBounds(10, 164, 446, 30);
        pnl_ThongTinNhanVien.add(txt_SoCCCD);

        JLabel lpl_SoCCCD = new JLabel("Số căn cước công dân:");
        lpl_SoCCCD.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lpl_SoCCCD.setBounds(10, 141, 143, 16);
        pnl_ThongTinNhanVien.add(lpl_SoCCCD);

        txt_HangKhachHang = new JTextField();
        txt_HangKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_HangKhachHang.setBounds(238, 350, 218, 30);
        pnl_ThongTinNhanVien.add(txt_HangKhachHang);

        txt_DiemTichLuy = new JTextField();
        txt_DiemTichLuy.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_DiemTichLuy.setBounds(10, 350, 218, 30);
        pnl_ThongTinNhanVien.add(txt_DiemTichLuy);

        JButton btn_Dong = new JButton("Đóng");
        btn_Dong.setActionCommand("Cancel");
        btn_Dong.setBounds(356, 463, 120, 30);
        getContentPane().add(btn_Dong);

        JButton btn_CapNhat = new JButton("Cập nhật");
        btn_CapNhat.setActionCommand("OK");
        btn_CapNhat.setBounds(226, 463, 120, 30);
        getContentPane().add(btn_CapNhat);

        JLabel lpl_ThongTinKhachHang = new JLabel("Thông Tin Khách Hàng");
        lpl_ThongTinKhachHang.setForeground(new Color(10, 100, 189));
        lpl_ThongTinKhachHang.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lpl_ThongTinKhachHang.setBounds(124, 10, 249, 29);
        getContentPane().add(lpl_ThongTinKhachHang);

        khachHangDialogController = new KhachHangDialogController(this , khachHang);
        khachHangDialogController.HienThiThongTinNhanVien();
    }
}
