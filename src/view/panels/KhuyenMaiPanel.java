package view.panels;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.toedter.calendar.JDateChooser;
import controllers.KhuyenMaiController;
//import controller.KhuyenMaiController;

public class KhuyenMaiPanel extends JPanel {
    // Khai báo các component là public để controller có thể truy cập
    public JTextField txt_TenKhachHang;
    public JTextField txt_TyLeGiam;
    public JTextField txt_TimMaKhuyenMai;
    public JTable table;
    public DefaultTableModel model;
    public JButton btn_Them;
    public JButton btn_LamMoi;
    public JButton btn_TimMa;
    public JComboBox comboBox_TrangThai;
    public JDateChooser ngayBD;
    public JDateChooser ngayKT;
    public JCheckBox chckbx_Standard;
    public JCheckBox chckbx_Superior;
    public JCheckBox chckbx_Family;
    public JCheckBox chckbx_Deluxe;
    public JCheckBox chckbx_Suite;
    public JCheckBox chckbx_TatCa;

    // Các component cho bộ lọc
    public JDateChooser ngayBD_1;
    public JDateChooser ngayKT_1;
    public JCheckBox chckbx_Standard_1;
    public JCheckBox chckbx_Superior_1;
    public JCheckBox chckbx_Family_1;
    public JCheckBox chckbx_Deluxe_1;
    public JCheckBox chckbx_Suite_1;
    public JCheckBox chckbx_TatCa_1;

    public KhuyenMaiPanel() {
        // Code khởi tạo giao diện không thay đổi...
        setBackground(new Color(236, 247, 255));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        JLabel lbl_KhuyenMai = new JLabel("Khuyến mãi");
        lbl_KhuyenMai.setForeground(new Color(10, 100, 189));
        lbl_KhuyenMai.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lbl_KhuyenMai.setBounds(602, 10, 127, 29);
        add(lbl_KhuyenMai);

        JPanel pnlThongTinKhuyenMai = new JPanel();
        pnlThongTinKhuyenMai.setBackground(new Color(255, 255, 255));
        pnlThongTinKhuyenMai.setBorder(new LineBorder(new Color(0, 0, 0)));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        int doDaiThongTinNhanVien = screenSize.width - 40;
        int viChiDauThongTinNhanVien = (screenSize.width - doDaiThongTinNhanVien) / 2;
        pnlThongTinKhuyenMai.setBounds(20, 79,1292 , 176);
        add(pnlThongTinKhuyenMai);
        pnlThongTinKhuyenMai.setLayout(null);

        JLabel lpl_TenKhuyenMai = new JLabel("Tên khuyến mãi:");
        lpl_TenKhuyenMai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lpl_TenKhuyenMai.setBounds(10, 10, 104, 20);
        pnlThongTinKhuyenMai.add(lpl_TenKhuyenMai);

        txt_TenKhachHang = new JTextField();
        txt_TenKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        int doDaiTxt = doDaiThongTinNhanVien/2 - 20;
        txt_TenKhachHang.setBounds(10, 33, 631, 30);
        pnlThongTinKhuyenMai.add(txt_TenKhachHang);
        txt_TenKhachHang.setColumns(10);

        txt_TyLeGiam = new JTextField();
        txt_TyLeGiam.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_TyLeGiam.setColumns(10);
        txt_TyLeGiam.setBounds(651, 33, 631, 30);
        pnlThongTinKhuyenMai.add(txt_TyLeGiam);

        JLabel lbl_TyLeGiam = new JLabel("Tỷ lệ giảm:");
        lbl_TyLeGiam.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_TyLeGiam.setBounds(651, 11, 104, 19);
        pnlThongTinKhuyenMai.add(lbl_TyLeGiam);

        JLabel lbl_NgayBatDau = new JLabel("Ngày bắt đầu:");
        lbl_NgayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_NgayBatDau.setBounds(10, 73, 104, 16);
        pnlThongTinKhuyenMai.add(lbl_NgayBatDau);

        JLabel lbl_TrangThai = new JLabel("Trạng Thái:");
        lbl_TrangThai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_TrangThai.setBounds(651, 74, 78, 13);
        pnlThongTinKhuyenMai.add(lbl_TrangThai);

        btn_Them = new JButton("Thêm");
        btn_Them.setBounds(1032 , 134, 120, 30);
        pnlThongTinKhuyenMai.add(btn_Them);

        btn_LamMoi = new JButton("Làm mới");
        btn_LamMoi.setBounds(1162, 134, 120, 30);
        pnlThongTinKhuyenMai.add(btn_LamMoi);

        comboBox_TrangThai = new JComboBox();
        comboBox_TrangThai.setModel(new DefaultComboBoxModel(new String[] {"Đang hoạt động", "Sắp diễn ra", "Hết hạn", "Tạm ngừng"}));
        comboBox_TrangThai.setBounds(651, 94, 631, 30);
        pnlThongTinKhuyenMai.add(comboBox_TrangThai);

        JLabel lbl_NgayKetThuc = new JLabel("Ngày kết thúc:");
        lbl_NgayKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_NgayKetThuc.setBounds(150, 74, 104, 16);
        pnlThongTinKhuyenMai.add(lbl_NgayKetThuc);

        ngayBD = new JDateChooser();
        ngayBD.setDateFormatString("dd/MM/yyyy");
        ngayBD.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayBD.setBounds(10, 99, 130, 30);
        // Thiết lập cho ngày bắt đầu (không được chọn ngày trong quá khứ)
        ngayBD.setMinSelectableDate(new java.util.Date());
        pnlThongTinKhuyenMai.add(ngayBD);

        ngayKT = new JDateChooser();
        ngayKT.setDateFormatString("dd/MM/yyyy");
        ngayKT.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayKT.setBounds(150, 99, 130, 30);
        ngayBD.addPropertyChangeListener("date", e-> {
            if(ngayBD.getDate() != null){
                // Thiết lập cho ngày kết thúc (không được chọn ngày trước ngày bắt đầu)
                ngayKT.setMinSelectableDate(ngayBD.getDate());
            }
        });
        pnlThongTinKhuyenMai.add(ngayKT);

        JLabel lbl_DieuKienApDung = new JLabel("Điều kiện áp dụng:");
        lbl_DieuKienApDung.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_DieuKienApDung.setBounds(300, 73, 132, 20);
        pnlThongTinKhuyenMai.add(lbl_DieuKienApDung);

        chckbx_Standard = new JCheckBox("Standard");
        chckbx_Standard.setBounds(300, 94, 87, 26);
        pnlThongTinKhuyenMai.add(chckbx_Standard);
        chckbx_Standard.setBackground(Color.white);

        chckbx_Superior = new JCheckBox("Superior");
        chckbx_Superior.setBounds(411, 94, 87, 26);
        pnlThongTinKhuyenMai.add(chckbx_Superior);
        chckbx_Superior.setBackground(Color.white);

        chckbx_Family = new JCheckBox("Family Room");
        chckbx_Family.setBounds(521, 94, 120, 26);
        pnlThongTinKhuyenMai.add(chckbx_Family);
        chckbx_Family.setBackground(Color.white);

        chckbx_Deluxe = new JCheckBox("Deluxe");
        chckbx_Deluxe.setBounds(300, 127, 87, 26);
        pnlThongTinKhuyenMai.add(chckbx_Deluxe);
        chckbx_Deluxe.setBackground(Color.white);

        chckbx_Suite = new JCheckBox("Suite");
        chckbx_Suite.setBounds(411, 127, 87, 26);
        pnlThongTinKhuyenMai.add(chckbx_Suite);
        chckbx_Suite.setBackground(Color.white);

        chckbx_TatCa = new JCheckBox("Tất cả");
        chckbx_TatCa.setBounds(521, 127, 87, 26);
        pnlThongTinKhuyenMai.add(chckbx_TatCa);
        chckbx_TatCa.setBackground(Color.white);

        JPanel pnlBoLoc = new JPanel();
        pnlBoLoc.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnlBoLoc.setBackground(new Color(255, 255, 255));
        pnlBoLoc.setBounds(20, 295, 1286, 112);
        add(pnlBoLoc);
        pnlBoLoc.setLayout(null);

        JLabel lbl_TimMaKhuyenMai = new JLabel("Tìm mã khuyến mãi:");
        lbl_TimMaKhuyenMai.setBounds(365, 10, 142, 20);
        lbl_TimMaKhuyenMai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnlBoLoc.add(lbl_TimMaKhuyenMai);

        txt_TimMaKhuyenMai = new JTextField();
        txt_TimMaKhuyenMai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_TimMaKhuyenMai.setColumns(10);
        txt_TimMaKhuyenMai.setBounds(365, 35, 450, 30);
        pnlBoLoc.add(txt_TimMaKhuyenMai);

        btn_TimMa = new JButton("Tìm");
        btn_TimMa.setBounds(1176, 72, 100, 30);
        pnlBoLoc.add(btn_TimMa);

        JLabel lbl_ApDUngTrongKhoang = new JLabel("Áp dụng trong khoảng:");
        lbl_ApDUngTrongKhoang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_ApDUngTrongKhoang.setBounds(880, 10, 162, 20);
        pnlBoLoc.add(lbl_ApDUngTrongKhoang);

        ngayBD_1 = new JDateChooser();
        ngayBD_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayBD_1.setDateFormatString("dd/MM/yyyy");
        ngayBD_1.setBounds(880, 35, 190, 30);
        pnlBoLoc.add(ngayBD_1);

        ngayKT_1 = new JDateChooser();
        ngayKT_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayKT_1.setDateFormatString("dd/MM/yyyy");
        ngayKT_1.setBounds(1096, 35, 180, 30);
        // Tương tự cho phần bộ lọc
        ngayBD_1.addPropertyChangeListener("date", evt -> {
            if (ngayBD_1.getDate() != null) {
                ngayKT_1.setMinSelectableDate(ngayBD_1.getDate());
            }
        });
        pnlBoLoc.add(ngayKT_1);

        JLabel lbl_DieuKienApDung_1 = new JLabel("Điều kiện áp dụng:");
        lbl_DieuKienApDung_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_DieuKienApDung_1.setBounds(14, 10, 132, 20);
        pnlBoLoc.add(lbl_DieuKienApDung_1);

        chckbx_Standard_1 = new JCheckBox("Standard");
        chckbx_Standard_1.setBounds(14, 33, 87, 26);
        pnlBoLoc.add(chckbx_Standard_1);
        chckbx_Standard_1.setBackground(Color.white);

        chckbx_Superior_1 = new JCheckBox("Superior");
        chckbx_Superior_1.setBounds(130, 33, 87, 26);
        pnlBoLoc.add(chckbx_Superior_1);
        chckbx_Superior_1.setBackground(Color.white);

        chckbx_Family_1 = new JCheckBox("Family Room");
        chckbx_Family_1.setBounds(14, 68, 120, 26);
        pnlBoLoc.add(chckbx_Family_1);
        chckbx_Family_1.setBackground(Color.white);

        chckbx_Deluxe_1 = new JCheckBox("Deluxe");
        chckbx_Deluxe_1.setBounds(240, 33, 87, 26);
        pnlBoLoc.add(chckbx_Deluxe_1);
        chckbx_Deluxe_1.setBackground(Color.white);

        chckbx_Suite_1 = new JCheckBox("Suite");
        chckbx_Suite_1.setBounds(130, 68, 87, 26);
        pnlBoLoc.add(chckbx_Suite_1);
        chckbx_Suite_1.setBackground(Color.white);

        chckbx_TatCa_1 = new JCheckBox("Tất cả");
        chckbx_TatCa_1.setBounds(240, 68, 87, 26);
        pnlBoLoc.add(chckbx_TatCa_1);
        chckbx_TatCa_1.setBackground(Color.white);

        JLabel lbl_ThongTinKhuyenMai = new JLabel("Thông tin khuyến mãi ");
        lbl_ThongTinKhuyenMai.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_ThongTinKhuyenMai.setBounds(20, 49, 197, 27);
        add(lbl_ThongTinKhuyenMai);

        JLabel lbl_BoLoc = new JLabel("Bộ lọc");
        lbl_BoLoc.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_BoLoc.setBounds(20, 265, 58, 20);
        add(lbl_BoLoc);

        JLabel lbl_DanhSachKhuyenMai = new JLabel("Danh sách khuyến mãi");
        lbl_DanhSachKhuyenMai.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_DanhSachKhuyenMai.setBounds(20, 417, 219, 29);
        add(lbl_DanhSachKhuyenMai);

        JPanel pnlDanhSachNhanVien = new JPanel();
        pnlDanhSachNhanVien.setBackground(new Color(255, 255, 255));
        pnlDanhSachNhanVien.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnlDanhSachNhanVien.setBounds(20, 456, 1286, 327);
        add(pnlDanhSachNhanVien);

        model = new DefaultTableModel(new String[] {"Mã khuyến mãi","Tên khuyến mãi","Tỷ lệ giảm","Điều kiện áp dụng","Ngày bắt đầu","Ngày kết thúc","Trạng thái"}, 0);
        pnlDanhSachNhanVien.setLayout(null);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 15, 1266, 302);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Times New Roman", Font.BOLD, 18));
        header.setBackground(new Color(10, 100, 189));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnlDanhSachNhanVien.add(scrollPane);

        // Thêm sự kiện cho checkbox
        themSuKienCheckBoxThongTin();
        themSuKienCheckBoxBoLoc();

        // Khởi tạo controller
        new KhuyenMaiController(this);
    }

    private void themSuKienCheckBoxThongTin() {
        // Khi chọn "Tất cả", bỏ chọn các checkbox khác
        ActionListener suKien_ChekBox_TatCa = e -> {
            if(chckbx_TatCa.isSelected()){
                chckbx_Standard.setSelected(false);
                chckbx_Superior.setSelected(false);
                chckbx_Family.setSelected(false);
                chckbx_Deluxe.setSelected(false);
                chckbx_Suite.setSelected(false);
            }
        };

        // Khi chọn các checkbox khác, bỏ chọn "Tất cả"
        ActionListener suKien_Cac_CheckBox_Khac = e -> {
            if (chckbx_Standard.isSelected() || chckbx_Superior.isSelected() ||
                    chckbx_Family.isSelected() || chckbx_Deluxe.isSelected() ||
                    chckbx_Suite.isSelected()) {
                chckbx_TatCa.setSelected(false);
            }
        };

        // Đăng ký sự kiện
        chckbx_TatCa.addActionListener(suKien_ChekBox_TatCa);
        chckbx_Standard.addActionListener(suKien_Cac_CheckBox_Khac);
        chckbx_Superior.addActionListener(suKien_Cac_CheckBox_Khac);
        chckbx_Family.addActionListener(suKien_Cac_CheckBox_Khac);
        chckbx_Deluxe.addActionListener(suKien_Cac_CheckBox_Khac);
        chckbx_Suite.addActionListener(suKien_Cac_CheckBox_Khac);
    }

    private void themSuKienCheckBoxBoLoc() {
        // Khi chọn "Tất cả", bỏ chọn các checkbox khác
        ActionListener suKien_ChekBox_TatCa = e -> {
            if(chckbx_TatCa_1.isSelected()){
                chckbx_Standard_1.setSelected(false);
                chckbx_Superior_1.setSelected(false);
                chckbx_Family_1.setSelected(false);
                chckbx_Deluxe_1.setSelected(false);
                chckbx_Suite_1.setSelected(false);
            }
        };

        // Khi chọn các checkbox khác, bỏ chọn "Tất cả"
        ActionListener suKien_Cac_CheckBox_Khac = e -> {
            if (chckbx_Standard_1.isSelected() || chckbx_Superior_1.isSelected() ||
                    chckbx_Family_1.isSelected() || chckbx_Deluxe_1.isSelected() ||
                    chckbx_Suite_1.isSelected()) {
                chckbx_TatCa_1.setSelected(false);
            }
        };

        // Đăng ký sự kiện
        chckbx_TatCa_1.addActionListener(suKien_ChekBox_TatCa);
        chckbx_Standard_1.addActionListener(suKien_Cac_CheckBox_Khac);
        chckbx_Superior_1.addActionListener(suKien_Cac_CheckBox_Khac);
        chckbx_Family_1.addActionListener(suKien_Cac_CheckBox_Khac);
        chckbx_Deluxe_1.addActionListener(suKien_Cac_CheckBox_Khac);
        chckbx_Suite_1.addActionListener(suKien_Cac_CheckBox_Khac);
    }
}