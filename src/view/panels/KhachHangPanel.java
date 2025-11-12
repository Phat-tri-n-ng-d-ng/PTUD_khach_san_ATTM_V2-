package view.panels;

import com.toedter.calendar.JDateChooser;
import controllers.KhachHangController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class KhachHangPanel extends JPanel {
    public LocalDate maxDate;
    public KhachHangController khachHangController;
    public JDateChooser ngaySinh;
    public JRadioButton rdbtn_Nam;
    public JRadioButton rdbtn_Nu;
    public JButton btn_ThemKhachHang;
    public JTextField txt_TenKhachHang;
    public JTextField txt_SoDienThoai;
    public JTextField txt_Email;
    public JTextField txt_TimSoDienThoai;
    public JTable table;
    public DefaultTableModel model;
    public JButton btn_Tim;
    public JTextField txt_soCCCD;
    public JRadioButton rdbtn_TimSoCanCuocCongDan;
    public JRadioButton rdbtn_TimSoDienThoai;
    public JTextField txt_TimSoCanCuocCongDan;
    public JComboBox cbb_LocHangKhachHang;
    public JButton btn_LamMoi;

    public KhachHangPanel() {
        setBackground(new Color(236, 247, 255));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        JLabel lpl_TieuDe = new JLabel("Khách Hàng");
        lpl_TieuDe.setForeground(new Color(10, 100, 189));
        lpl_TieuDe.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lpl_TieuDe.setBounds(595, 10, 133, 25);
        add(lpl_TieuDe);

        //Thông tin Khách hàng
        JLabel lbl_ThongTinKhachHang = new JLabel("Thông Tin Khách Hàng");
        lbl_ThongTinKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_ThongTinKhachHang.setBounds(20, 49, 190, 20);
        add(lbl_ThongTinKhachHang);

        JPanel pnlThongTinKhachHang = new JPanel();
        pnlThongTinKhachHang.setBackground(new Color(255, 255, 255));
        pnlThongTinKhachHang.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnlThongTinKhachHang.setBounds(20, 79,1292 , 197);
        add(pnlThongTinKhachHang);
        pnlThongTinKhachHang.setLayout(null);

        JLabel lpl_TenNhanVien = new JLabel("Tên khách hàng:");
        lpl_TenNhanVien.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lpl_TenNhanVien.setBounds(10, 10, 104, 16);
        pnlThongTinKhachHang.add(lpl_TenNhanVien);

        txt_TenKhachHang = new JTextField();
        txt_TenKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_TenKhachHang.setBounds(10, 33, 631, 30);
        pnlThongTinKhachHang.add(txt_TenKhachHang);
        txt_TenKhachHang.setColumns(10);

        txt_SoDienThoai = new JTextField();
        txt_SoDienThoai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_SoDienThoai.setColumns(10);
        txt_SoDienThoai.setBounds(651, 33, 631, 30);
        pnlThongTinKhachHang.add(txt_SoDienThoai);

        JLabel lbl_SoDienThoai = new JLabel("Số điện thoại:");
        lbl_SoDienThoai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_SoDienThoai.setBounds(651, 10, 104, 16);
        pnlThongTinKhachHang.add(lbl_SoDienThoai);

        ButtonGroup groupGioiTinh = new ButtonGroup();

        JLabel lbl_Email = new JLabel("Email:");
        lbl_Email.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_Email.setBounds(651, 74, 47, 16);
        pnlThongTinKhachHang.add(lbl_Email);

        txt_Email = new JTextField();
        txt_Email.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_Email.setColumns(10);
        txt_Email.setBounds(651, 94, 631, 30);
        pnlThongTinKhachHang.add(txt_Email);

        btn_LamMoi = new JButton("Làm mới");
        btn_LamMoi.setBounds(1162, 160, 120, 30);
        pnlThongTinKhachHang.add(btn_LamMoi);

        JLabel lbl_soCCCD = new JLabel("Số căn cước công dân:");
        lbl_soCCCD.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_soCCCD.setBounds(10, 74, 161, 16);
        pnlThongTinKhachHang.add(lbl_soCCCD);

        txt_soCCCD = new JTextField();
        txt_soCCCD.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_soCCCD.setColumns(10);
        txt_soCCCD.setBounds(10, 94, 631, 30);
        pnlThongTinKhachHang.add(txt_soCCCD);

        ngaySinh = new JDateChooser();
        ngaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngaySinh.setDateFormatString("dd/MM/yyyy");
        ngaySinh.setBounds(169, 160, 210, 30);
        pnlThongTinKhachHang.add(ngaySinh);

        // Tính ngày hợp lệ (ít nhất 18 tuổi)
        LocalDate today = LocalDate.now();
        maxDate = today.minusYears(18);
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

        JLabel lbl_NgaySinh = new JLabel("Ngày sinh:");
        lbl_NgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_NgaySinh.setBounds(169, 134, 104, 16);
        pnlThongTinKhachHang.add(lbl_NgaySinh);

        rdbtn_Nu = new JRadioButton("Nữ");
        rdbtn_Nu.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_Nu.setBackground(Color.WHITE);
        rdbtn_Nu.setBounds(95, 164, 47, 21);
        pnlThongTinKhachHang.add(rdbtn_Nu);

        rdbtn_Nam = new JRadioButton("Nam");
        rdbtn_Nam.setSelected(true);
        rdbtn_Nam.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_Nam.setBackground(Color.WHITE);
        rdbtn_Nam.setBounds(10, 164, 65, 21);
        pnlThongTinKhachHang.add(rdbtn_Nam);

        JLabel lpl_GioiTinh = new JLabel("Giới tính:");
        lpl_GioiTinh.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lpl_GioiTinh.setBounds(10, 134, 104, 16);
        pnlThongTinKhachHang.add(lpl_GioiTinh);

        btn_ThemKhachHang = new JButton("Thêm kh");
        btn_ThemKhachHang.setBounds(1032, 160, 120, 30);
        pnlThongTinKhachHang.add(btn_ThemKhachHang);

        //Bộ lọc
        JLabel lbl_BoLoc = new JLabel("Bộ Lọc");
        lbl_BoLoc.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_BoLoc.setBounds(20, 286, 70, 20);
        add(lbl_BoLoc);

        JPanel pnlBoLoc = new JPanel();
        pnlBoLoc.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnlBoLoc.setBackground(new Color(255, 255, 255));
        pnlBoLoc.setBounds(20, 316, 1292, 73);
        add(pnlBoLoc);
        pnlBoLoc.setLayout(null);

        rdbtn_TimSoCanCuocCongDan = new JRadioButton("Tìm số căn cước công dân:");
        rdbtn_TimSoCanCuocCongDan.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_TimSoCanCuocCongDan.setBackground(Color.WHITE);
        rdbtn_TimSoCanCuocCongDan.setBounds(10, 9, 220, 16);
        rdbtn_TimSoCanCuocCongDan.setSelected(true);
        pnlBoLoc.add(rdbtn_TimSoCanCuocCongDan);

        rdbtn_TimSoDienThoai = new JRadioButton("Tìm số điện thoại:");
        rdbtn_TimSoDienThoai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_TimSoDienThoai.setBackground(Color.WHITE);
        rdbtn_TimSoDienThoai.setBounds(520, 11, 159, 16);
        pnlBoLoc.add(rdbtn_TimSoDienThoai);

        ButtonGroup groupTimKiem = new ButtonGroup();
        groupTimKiem.add(rdbtn_TimSoCanCuocCongDan);
        groupTimKiem.add(rdbtn_TimSoDienThoai);

        txt_TimSoCanCuocCongDan = new JTextField();
        txt_TimSoCanCuocCongDan.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_TimSoCanCuocCongDan.setColumns(10);
        txt_TimSoCanCuocCongDan.setBounds(10, 33, 500, 30);
        pnlBoLoc.add(txt_TimSoCanCuocCongDan);

        txt_TimSoDienThoai = new JTextField();
        txt_TimSoDienThoai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_TimSoDienThoai.setColumns(10);
        txt_TimSoDienThoai.setBounds(520, 33, 500, 30);
        txt_TimSoDienThoai.setEditable(false);
        pnlBoLoc.add(txt_TimSoDienThoai);

        btn_Tim = new JButton("Tìm");
        btn_Tim.setBounds(1030, 34, 100, 30);
        pnlBoLoc.add(btn_Tim);

        JLabel lbl_HangKhachHang = new JLabel("Hạng khách hàng:");
        lbl_HangKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_HangKhachHang.setBounds(1140, 9, 114, 20);
        pnlBoLoc.add(lbl_HangKhachHang);

        cbb_LocHangKhachHang = new JComboBox();
        cbb_LocHangKhachHang.setModel(new DefaultComboBoxModel(new String[] {"Tất cả", "Đồng", "Bạc", "Vàng", "Kim cương"}));
        cbb_LocHangKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        cbb_LocHangKhachHang.setBounds(1140, 33, 142, 30);
        pnlBoLoc.add(cbb_LocHangKhachHang);


        //Danh sách khách hàng
        JLabel lbl_DanhSachKhachHang = new JLabel("Danh Sách Khách Hàng");
        lbl_DanhSachKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_DanhSachKhachHang.setBounds(20, 399, 200, 21);
        add(lbl_DanhSachKhachHang);

        JPanel pnlDanhSachKhachHang = new JPanel();
        pnlDanhSachKhachHang.setBackground(new Color(255, 255, 255));
        pnlDanhSachKhachHang.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnlDanhSachKhachHang.setBounds(20, 430, 1292, 361);
        add(pnlDanhSachKhachHang);

        model = new DefaultTableModel(new String[] {"Mã khách hàng","Tên khách hàng","Giới tính","Ngày sinh","Số điện thoại","Email","Số CCCD","Hạng khách hàng","Điểm tích lũy"}, 0);
        pnlDanhSachKhachHang.setLayout(null);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 58, 1272, 293);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Times New Roman", Font.BOLD, 18));
        header.setBackground(new Color(10, 100, 189));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnlDanhSachKhachHang.add(scrollPane);

        JButton btn_CapNhatHangKhachHang = new JButton("Cập nhận hạng khách hàng");
        btn_CapNhatHangKhachHang.setBounds(1097, 10, 185, 30);
        pnlDanhSachKhachHang.add(btn_CapNhatHangKhachHang);

        khachHangController = new KhachHangController(this);
        khachHangController.getTatCaKhachHang();
    }
}
