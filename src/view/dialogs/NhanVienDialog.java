package view.dialogs;

import com.toedter.calendar.JDateChooser;
import controllers.dialogs.NhanVienDialogController;
import entitys.NhanVien;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Date;

public class NhanVienDialog extends JDialog {
    private NhanVienDialogController nhanVienDialogController;
    private JTextField txtTenNV;
    private JRadioButton rdbNam, rdbNu;
    private JSpinner spinnerNgaySinh;
    private JButton btnClose;

    private NhanVien nhanVien;
    public JTextField txt_MaNhanVien;
    public JTextField txt_TenNhanVien;
    public JTextField txt_SoDienThoai;
    public JTextField txt_Email;
    public JDateChooser ngaySinh;
    public JButton btn_CapTaiKhoan;
    public JButton btn_CapNhat;
    public JButton btn_Dong;
    public JRadioButton rdbtn_Nam;
    public JRadioButton rdbtn_Nu;
    public JComboBox cbb_ChucVu;
    public JComboBox cbb_TrangThaiTaiKhoan;

    public NhanVienDialog(JFrame mainframe, NhanVien nv) {
        super(mainframe, "Thông tin nhân viên", true);
        this.nhanVien = nv;

        giaoDienDialog();
        setLocationRelativeTo(mainframe);
    }

    private void giaoDienDialog() {
        setBounds(100, 100, 500, 540);
        getContentPane().setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));
        setLayout(null);

        JLabel lpl_ThongTinNhanVien = new JLabel("Thông Tin Nhân Viên");
        lpl_ThongTinNhanVien.setForeground(new Color(10, 100, 189));
        lpl_ThongTinNhanVien.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lpl_ThongTinNhanVien.setBounds(139, 10, 222, 29);
        add(lpl_ThongTinNhanVien);

        JPanel pnl_ThongTinNhanVien = new JPanel();
        pnl_ThongTinNhanVien.setBackground(new Color(255, 255, 255));
        pnl_ThongTinNhanVien.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnl_ThongTinNhanVien.setBounds(10, 49, 466, 400);
        add(pnl_ThongTinNhanVien);
        pnl_ThongTinNhanVien.setLayout(null);

        JLabel lpl_MaNhanVien = new JLabel("Mã nhân viên:");
        lpl_MaNhanVien.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lpl_MaNhanVien.setBounds(10, 10, 116, 16);
        pnl_ThongTinNhanVien.add(lpl_MaNhanVien);

        txt_MaNhanVien = new JTextField();
        txt_MaNhanVien.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_MaNhanVien.setBounds(10, 33, 446, 30);
        pnl_ThongTinNhanVien.add(txt_MaNhanVien);

        JLabel lpl_TenNhanVien_1 = new JLabel("Tên nhân viên:");
        lpl_TenNhanVien_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lpl_TenNhanVien_1.setBounds(10, 73, 546, 16);
        pnl_ThongTinNhanVien.add(lpl_TenNhanVien_1);

        txt_TenNhanVien = new JTextField();
        txt_TenNhanVien.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_TenNhanVien.setBounds(10, 96, 446, 30);
        pnl_ThongTinNhanVien.add(txt_TenNhanVien);

        JLabel lpl_SoDienThoai = new JLabel("Số điện thoại:");
        lpl_SoDienThoai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lpl_SoDienThoai.setBounds(10, 136, 92, 16);
        pnl_ThongTinNhanVien.add(lpl_SoDienThoai);

        txt_SoDienThoai = new JTextField();
        txt_SoDienThoai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_SoDienThoai.setBounds(10, 159, 446, 30);
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

        cbb_ChucVu = new JComboBox();
        cbb_ChucVu.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        cbb_ChucVu.setModel(new DefaultComboBoxModel(new String[] {"Quản lý", "Lễ tân", "Kế toán", "Kỹ thuật", "Buồng phòng", "Bếp", "Bảo vệ"}));
        cbb_ChucVu.setBounds(10, 350, 218, 30);
        pnl_ThongTinNhanVien.add(cbb_ChucVu);

        JLabel lbl_ChucVu = new JLabel("Chức vụ:");
        lbl_ChucVu.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_ChucVu.setBounds(10, 324, 191, 16);
        pnl_ThongTinNhanVien.add(lbl_ChucVu);

        txt_Email = new JTextField();
        txt_Email.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_Email.setBounds(10, 284, 446, 30);
        pnl_ThongTinNhanVien.add(txt_Email);

        JLabel lpl_Email = new JLabel("Email:");
        lpl_Email.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lpl_Email.setBounds(10, 261, 92, 16);
        pnl_ThongTinNhanVien.add(lpl_Email);

        JLabel lpl_TrangThaiTaiKhoan = new JLabel("Chức vụ:");
        lpl_TrangThaiTaiKhoan.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lpl_TrangThaiTaiKhoan.setBounds(239, 324, 77, 16);
        pnl_ThongTinNhanVien.add(lpl_TrangThaiTaiKhoan);

        cbb_TrangThaiTaiKhoan = new JComboBox();
        cbb_TrangThaiTaiKhoan.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        cbb_TrangThaiTaiKhoan.setBounds(239, 350, 218, 30);
        pnl_ThongTinNhanVien.add(cbb_TrangThaiTaiKhoan);

        btn_CapTaiKhoan = new JButton("Cấp tài khoản");
        btn_CapTaiKhoan.setActionCommand("OK");
        btn_CapTaiKhoan.setBounds(96, 459, 120, 30);
        add(btn_CapTaiKhoan);

        btn_CapNhat = new JButton("Cập nhật");
        btn_CapNhat.setActionCommand("OK");
        btn_CapNhat.setBounds(226, 459, 120, 30);
        add(btn_CapNhat);

        btn_Dong = new JButton("Đóng");
        btn_Dong.setActionCommand("Cancel");
        btn_Dong.setBounds(356, 459, 120, 30);
        add(btn_Dong);

        nhanVienDialogController = new NhanVienDialogController(this , nhanVien);
        nhanVienDialogController.HienThiThongTinNhanVien();
    }
}