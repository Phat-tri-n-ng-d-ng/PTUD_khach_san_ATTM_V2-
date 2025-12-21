package view.panels;

import com.toedter.calendar.JDateChooser;
import controllers.dialogs.ThueDatPhongController;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class ThueDatPhongPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    // ===== CONTROLLER =====
    private ThueDatPhongController thueDatPhongController;

    // ===== PANEL =====
    public JPanel pnlLoc;
    public JPanel pnlDanhSachPhong;
    public JPanel danhSachPhongPanel;  // Panel chứa danh sách phòng
    public JScrollPane scrollPaneDanhSachPhong;  // ScrollPane cho danh sách phòng
    public JPanel mauPhongTrong;
    public JPanel mauPhongThue;
    public JPanel mauPhongDat;

    // ===== LABEL =====
    public JLabel lbl_TieuDe;
    public JLabel lbl_BoLoc;
    public JLabel lbl_DanhSachPhong;
    public JLabel lbl_LoaiPhong;
    public JLabel lbl_KhuyenMai;

    // ===== BUTTON =====
    public JButton btn_Tim;
    public JButton btn_ApDung;
    public JButton btn_NhanPhong;
    public JButton btn_DatPhong;

    // ===== CHECKBOX =====
    public JCheckBox chckbx_phongTrong;
    public JCheckBox chckbx_phongThue;
    public JCheckBox chckbx_phongDat;

    // ===== TEXTFIELD =====
    public JTextField txt_TimSoDienThoai;

    // ===== COMBOBOX =====
    public JComboBox<String> cbb_KhuyenMai;
    public JComboBox<String> cbb_LoaiPhong;

    // ===== DATE =====
    public JDateChooser ngayBatDau;
    public JDateChooser ngayKetThuc;

    // ===== TABLE =====
    public DefaultTableModel model;

    public ThueDatPhongPanel() {
        jbInit();
    }

    private void jbInit() {
        setBounds(100, 100, 1336, 768);
        setBackground(new Color(236, 247, 255));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        // ===== TITLE =====
        lbl_TieuDe = new JLabel("Đặt/ thuê phòng");
        lbl_TieuDe.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lbl_TieuDe.setForeground(new Color(10, 100, 189));
        lbl_TieuDe.setBounds(725, 10, 250, 30);
        add(lbl_TieuDe);

        // ===== FILTER PANEL =====
        pnlLoc = new JPanel(null);
        pnlLoc.setBackground(Color.WHITE);
        pnlLoc.setBorder(new LineBorder(Color.BLACK));
        pnlLoc.setBounds(20, 75, 1293, 73);
        add(pnlLoc);

        JLabel lblTim = new JLabel("Tìm số điện thoại khách hàng:");
        lblTim.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblTim.setBounds(10, 10, 250, 20);
        pnlLoc.add(lblTim);

        txt_TimSoDienThoai = new JTextField();
        txt_TimSoDienThoai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_TimSoDienThoai.setBounds(10, 33, 316, 30);
        pnlLoc.add(txt_TimSoDienThoai);

        btn_Tim = new JButton("Tìm");
        btn_Tim.setBounds(336, 33, 100, 30);
        btn_Tim.setBackground(new Color(52, 152, 219));
        btn_Tim.setForeground(Color.WHITE);
        btn_Tim.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_Tim.setBorder(BorderFactory.createLineBorder(new Color(41, 128, 185), 1));
        btn_Tim.setFocusPainted(false);
        pnlLoc.add(btn_Tim);

        JLabel lblNgayBD = new JLabel("Ngày bắt đầu:");
        lblNgayBD.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNgayBD.setBounds(466, 10, 120, 20);
        pnlLoc.add(lblNgayBD);

        JLabel lblNgayKT = new JLabel("Ngày kết thúc:");
        lblNgayKT.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblNgayKT.setBounds(620, 10, 120, 20);
        pnlLoc.add(lblNgayKT);

        ngayBatDau = new JDateChooser();
        ngayBatDau.setBounds(466, 33, 130, 30);
        ngayBatDau.setDateFormatString("dd/MM/yyyy");
        pnlLoc.add(ngayBatDau);

        ngayKetThuc = new JDateChooser();
        ngayKetThuc.setBounds(620, 33, 130, 30);
        ngayKetThuc.setDateFormatString("dd/MM/yyyy");
        pnlLoc.add(ngayKetThuc);

        btn_ApDung = new JButton("Áp dụng");
        btn_ApDung.setBounds(760, 33, 100, 30);
        btn_ApDung.setBackground(new Color(46, 204, 113));
        btn_ApDung.setForeground(Color.WHITE);
        btn_ApDung.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_ApDung.setBorder(BorderFactory.createLineBorder(new Color(39, 174, 96), 1));
        btn_ApDung.setFocusPainted(false);
        pnlLoc.add(btn_ApDung);

        cbb_KhuyenMai = new JComboBox<>();
        cbb_KhuyenMai.setBounds(890, 33, 180, 30);
        cbb_KhuyenMai.addItem("Chọn");
        cbb_KhuyenMai.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        pnlLoc.add(cbb_KhuyenMai);

        lbl_KhuyenMai = new JLabel("Khuyến mãi:");
        lbl_KhuyenMai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_KhuyenMai.setBounds(890, 10, 180, 20);
        pnlLoc.add(lbl_KhuyenMai);

        lbl_LoaiPhong = new JLabel("Loại phòng:");
        lbl_LoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_LoaiPhong.setBounds(1110, 10, 120, 20);
        pnlLoc.add(lbl_LoaiPhong);

        cbb_LoaiPhong = new JComboBox<>();
        cbb_LoaiPhong.setBounds(1110, 33, 170, 30);
        cbb_LoaiPhong.addItem("Tất cả");
        cbb_LoaiPhong.addItem("Standard");
        cbb_LoaiPhong.addItem("Superior");
        cbb_LoaiPhong.addItem("Deluxe");
        cbb_LoaiPhong.addItem("Suite");
        cbb_LoaiPhong.addItem("Family room");
        cbb_LoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        pnlLoc.add(cbb_LoaiPhong);

        // ===== LABEL =====
        lbl_BoLoc = new JLabel("Bộ lọc");
        lbl_BoLoc.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_BoLoc.setBounds(20, 45, 100, 20);
        add(lbl_BoLoc);

        // ===== DANH SÁCH PHÒNG PANEL =====
        pnlDanhSachPhong = new JPanel(null);
        pnlDanhSachPhong.setBounds(20, 195, 1293, 596);
        pnlDanhSachPhong.setBorder(new LineBorder(Color.BLACK));
        pnlDanhSachPhong.setBackground(Color.WHITE);
        add(pnlDanhSachPhong);

        lbl_DanhSachPhong = new JLabel("Danh sách phòng");
        lbl_DanhSachPhong.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_DanhSachPhong.setBounds(20, 160, 200, 20);
        add(lbl_DanhSachPhong);

        // ===== CHECKBOX =====
        chckbx_phongTrong = new JCheckBox("Phòng trống");
        chckbx_phongTrong.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        chckbx_phongTrong.setBackground(Color.WHITE);
        chckbx_phongTrong.setBounds(10, 10, 140, 30);
        pnlDanhSachPhong.add(chckbx_phongTrong);

        mauPhongTrong = new JPanel();
        mauPhongTrong.setBounds(155, 15, 20, 20);
        mauPhongTrong.setBorder(new LineBorder(Color.BLACK));
        mauPhongTrong.setBackground(Color.WHITE);
        pnlDanhSachPhong.add(mauPhongTrong);

        chckbx_phongThue = new JCheckBox("Phòng đang thuê");
        chckbx_phongThue.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        chckbx_phongThue.setBackground(Color.WHITE);
        chckbx_phongThue.setBounds(190, 10, 140, 30);
        pnlDanhSachPhong.add(chckbx_phongThue);

        mauPhongThue = new JPanel();
        mauPhongThue.setBounds(335, 15, 20, 20);
        mauPhongThue.setBackground(new Color(144, 238, 144));
        mauPhongThue.setBorder(new LineBorder(Color.BLACK));
        pnlDanhSachPhong.add(mauPhongThue);

        chckbx_phongDat = new JCheckBox("Phòng đã đặt");
        chckbx_phongDat.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        chckbx_phongDat.setBackground(Color.WHITE);
        chckbx_phongDat.setBounds(370, 10, 140, 30);
        pnlDanhSachPhong.add(chckbx_phongDat);

        mauPhongDat = new JPanel();
        mauPhongDat.setBounds(515, 15, 20, 20);
        mauPhongDat.setBackground(new Color(255, 182, 193));
        mauPhongDat.setBorder(new LineBorder(Color.BLACK));
        pnlDanhSachPhong.add(mauPhongDat);

        // ===== BUTTON STYLE =====
        btn_NhanPhong = new JButton("Nhận phòng");
        btn_NhanPhong.setBounds(841, 10, 222, 30);
        btn_NhanPhong.setBackground(new Color(10, 100, 189));
        btn_NhanPhong.setForeground(Color.WHITE);
        btn_NhanPhong.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_NhanPhong.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(8, 80, 160), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        btn_NhanPhong.setFocusPainted(false);
        pnlDanhSachPhong.add(btn_NhanPhong);

        btn_DatPhong = new JButton("Đặt phòng");
        btn_DatPhong.setBounds(1068, 10, 215, 30);
        btn_DatPhong.setBackground(new Color(10, 100, 189));
        btn_DatPhong.setForeground(Color.WHITE);
        btn_DatPhong.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btn_DatPhong.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(8, 80, 160), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        btn_DatPhong.setFocusPainted(false);
        pnlDanhSachPhong.add(btn_DatPhong);

        // ===== PANEL CHỨA DANH SÁCH PHÒNG (KHỞI TẠO) =====
        danhSachPhongPanel = new JPanel();
        danhSachPhongPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        danhSachPhongPanel.setBackground(new Color(236, 247, 255));

        // Tạo ScrollPane để chứa danhSachPhongPanel
        scrollPaneDanhSachPhong = new JScrollPane(danhSachPhongPanel);
        scrollPaneDanhSachPhong.setBounds(10, 50, 1273, 536);
        scrollPaneDanhSachPhong.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneDanhSachPhong.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneDanhSachPhong.getVerticalScrollBar().setUnitIncrement(16);
        scrollPaneDanhSachPhong.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneDanhSachPhong.getViewport().setBackground(new Color(236, 247, 255));

        pnlDanhSachPhong.add(scrollPaneDanhSachPhong);

        // ===== CONTROLLER =====
        thueDatPhongController = new ThueDatPhongController(this);
    }
}