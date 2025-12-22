package view.panels;


import com.toedter.calendar.JDateChooser;
import controllers.ThueDatPhongController;

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
    public JButton btn_ThuePhong;
    public JButton btn_DatPhong;

    public JButton LamMoi;

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

    // ===== HÀM TẠO NÚT BO GÓC =====
    private JButton createRoundedButton(String text, Color bgColor, Color borderColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Vẽ nền bo góc
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Vẽ viền bo góc
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

                super.paintComponent(g);
                g2.dispose();
            }
        };

        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        return button;
    }

    public ThueDatPhongPanel() {
        jbInit();
    }

    private void jbInit() {

        // Panel chính với lề trái 10px, phải 10px
        setBounds(100, 100, 1336, 768);
        setBackground(new Color(248, 250, 252));
        setBorder(new EmptyBorder(5, 10, 5, 10)); // Thêm lề phải 10px
        setLayout(null);

        // ===== TITLE - CĂN GIỮA =====
        lbl_TieuDe = new JLabel("ĐẶT/THUÊ PHÒNG");
        lbl_TieuDe.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbl_TieuDe.setForeground(new Color(67, 97, 238));
        // Tính toán vị trí để căn giữa
        int titleWidth = 250;
        int panelWidth = 1336;
        int titleX = (panelWidth - titleWidth) / 2;
        lbl_TieuDe.setBounds(titleX, 10, titleWidth, 30);
        add(lbl_TieuDe);

        // ===== LABEL BỘ LỌC - CÁCH LỀ TRÁI 10px, LỀ PHẢI 10px =====
        lbl_BoLoc = new JLabel("Bộ lọc");
        lbl_BoLoc.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl_BoLoc.setForeground(new Color(51, 65, 85));
        lbl_BoLoc.setBounds(10, 45, 100, 20); // Cách lề trái 10px
        add(lbl_BoLoc);

        // ===== FILTER PANEL - CÁCH LỀ TRÁI 10px, LỀ PHẢI 10px =====
        pnlLoc = new JPanel(null);
        pnlLoc.setBackground(Color.WHITE);
        pnlLoc.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15) // Padding bên trong
        ));
        // Panel rộng: 1336 - 10 (trái) - 10 (phải) = 1316
        pnlLoc.setBounds(10, 75, 1316, 85); // Cách lề trái 10px, phải 10px
        add(pnlLoc);

        // ===== CÁC THÀNH PHẦN TRONG PANEL LỌC - ĐIỀU CHỈNH ĐỂ KHÔNG CHẠM LỀ PHẢI =====
        // Tính toán lại để các thành phần không chạm vào lề phải panel con
        JLabel lblTim = new JLabel("Tìm số điện thoại khách hàng:");
        lblTim.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTim.setBounds(10, 10, 250, 20);
        pnlLoc.add(lblTim);

        txt_TimSoDienThoai = new JTextField();
        txt_TimSoDienThoai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt_TimSoDienThoai.setBounds(10, 35, 300, 35); // Giảm width để cách lề phải
        txt_TimSoDienThoai.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        pnlLoc.add(txt_TimSoDienThoai);

        // Nút Tìm kiếm - điều chỉnh vị trí
        btn_Tim = createRoundedButton("Tìm", new Color(67, 97, 238), new Color(56, 86, 228));
        btn_Tim.setBounds(320, 35, 100, 35); // Cách textfield 10px
        pnlLoc.add(btn_Tim);

        JLabel lblNgayBD = new JLabel("Ngày bắt đầu:");
        lblNgayBD.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNgayBD.setBounds(450, 10, 120, 20); // Dịch sang phải
        pnlLoc.add(lblNgayBD);

        JLabel lblNgayKT = new JLabel("Ngày kết thúc:");
        lblNgayKT.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNgayKT.setBounds(604, 10, 120, 20); // Dịch sang phải
        pnlLoc.add(lblNgayKT);

        ngayBatDau = new JDateChooser();
        ngayBatDau.setBounds(450, 35, 130, 35); // Dịch sang phải
        ngayBatDau.setDateFormatString("dd/MM/yyyy");
        pnlLoc.add(ngayBatDau);

        ngayKetThuc = new JDateChooser();
        ngayKetThuc.setBounds(604, 35, 130, 35); // Dịch sang phải
        ngayKetThuc.setDateFormatString("dd/MM/yyyy");
        pnlLoc.add(ngayKetThuc);

        // Nút Áp dụng - điều chỉnh vị trí
        btn_ApDung = createRoundedButton("Áp dụng", new Color(46, 221, 149), new Color(36, 211, 139));
        btn_ApDung.setBounds(754, 35, 100, 35); // Dịch sang phải
        pnlLoc.add(btn_ApDung);

        cbb_KhuyenMai = new JComboBox<>();
        cbb_KhuyenMai.setBounds(884, 35, 180, 35); // Dịch sang phải
        cbb_KhuyenMai.addItem("Chọn");
        cbb_KhuyenMai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbb_KhuyenMai.setBackground(Color.WHITE);
        cbb_KhuyenMai.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        pnlLoc.add(cbb_KhuyenMai);

        lbl_KhuyenMai = new JLabel("Khuyến mãi:");
        lbl_KhuyenMai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl_KhuyenMai.setBounds(884, 10, 180, 20); // Dịch sang phải
        pnlLoc.add(lbl_KhuyenMai);

        lbl_LoaiPhong = new JLabel("Loại phòng:");
        lbl_LoaiPhong.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        // Tính toán để không chạm lề phải: 1306 - 170 - 10 = 1126
        lbl_LoaiPhong.setBounds(1104, 10, 120, 20); // Dịch sang phải, cách lề phải panel
        pnlLoc.add(lbl_LoaiPhong);

        cbb_LoaiPhong = new JComboBox<>();
        // Tính toán để không chạm lề phải: 1306 - 170 - 10 = 1126
        cbb_LoaiPhong.setBounds(1104, 35, 170, 35); // Dịch sang phải, cách lề phải panel
        cbb_LoaiPhong.addItem("Tất cả");
        cbb_LoaiPhong.addItem("Standard");
        cbb_LoaiPhong.addItem("Superior");
        cbb_LoaiPhong.addItem("Deluxe");
        cbb_LoaiPhong.addItem("Suite");
        cbb_LoaiPhong.addItem("Family room");
        cbb_LoaiPhong.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbb_LoaiPhong.setBackground(Color.WHITE);
        cbb_LoaiPhong.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        pnlLoc.add(cbb_LoaiPhong);

        // ===== LABEL DANH SÁCH PHÒNG - CÁCH LỀ TRÁI 10px, LỀ PHẢI 10px =====
        lbl_DanhSachPhong = new JLabel("DANH SÁCH PHÒNG");
        lbl_DanhSachPhong.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl_DanhSachPhong.setForeground(new Color(51, 65, 85));
        lbl_DanhSachPhong.setBounds(10, 160, 200, 20); // Cách lề trái 10px
        add(lbl_DanhSachPhong);

        // ===== DANH SÁCH PHÒNG PANEL - CÁCH LỀ TRÁI 10px, LỀ PHẢI 10px =====
        pnlDanhSachPhong = new JPanel(null);
        // Panel rộng: 1336 - 10 (trái) - 10 (phải) = 1316
        pnlDanhSachPhong.setBounds(10, 195, 1316, 596); // Cách lề trái 10px, phải 10px
        pnlDanhSachPhong.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15) // Padding bên trong
        ));
        pnlDanhSachPhong.setBackground(Color.WHITE);
        add(pnlDanhSachPhong);

        // ===== CHECKBOX - CẬP NHẬT STYLE VÀ VỊ TRÍ =====
        chckbx_phongTrong = new JCheckBox("Phòng trống");
        chckbx_phongTrong.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        chckbx_phongTrong.setFocusPainted(false);
        chckbx_phongTrong.setBackground(Color.WHITE);
        chckbx_phongTrong.setBounds(10, 10, 95, 30);
        pnlDanhSachPhong.add(chckbx_phongTrong);

        mauPhongTrong = new JPanel();
        mauPhongTrong.setBounds(110, 15, 20, 20);
        mauPhongTrong.setBorder(new LineBorder(Color.BLACK));
        mauPhongTrong.setBackground(Color.WHITE);
        pnlDanhSachPhong.add(mauPhongTrong);

        chckbx_phongThue = new JCheckBox("Phòng đang thuê");
        chckbx_phongThue.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        chckbx_phongThue.setFocusPainted(false);
        chckbx_phongThue.setBackground(Color.WHITE);
        chckbx_phongThue.setBounds(160, 10, 123, 30);
        pnlDanhSachPhong.add(chckbx_phongThue);

        mauPhongThue = new JPanel();
        mauPhongThue.setBounds(288, 15, 20, 20);
        mauPhongThue.setBackground(new Color(144, 238, 144));
        mauPhongThue.setBorder(new LineBorder(Color.BLACK));
        pnlDanhSachPhong.add(mauPhongThue);

        chckbx_phongDat = new JCheckBox("Phòng đã đặt");
        chckbx_phongDat.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        chckbx_phongDat.setFocusPainted(false);
        chckbx_phongDat.setBackground(Color.WHITE);
        chckbx_phongDat.setBounds(338, 10, 101, 30);
        pnlDanhSachPhong.add(chckbx_phongDat);

        mauPhongDat = new JPanel();
        mauPhongDat.setBounds(444, 15, 20, 20);
        mauPhongDat.setBackground(new Color(255, 182, 193));
        mauPhongDat.setBorder(new LineBorder(Color.BLACK));
        pnlDanhSachPhong.add(mauPhongDat);

        // ===== BUTTON STYLE - Các nút bo góc với hiệu ứng bàn tay =====
        int buttonWidth = 160; // Giảm width button
        int buttonHeight = 35;
        int buttonGap = 10; // Giảm khoảng cách
        int rightMargin = 10; // Lề phải bên trong panel
        // Panel pnlDanhSachPhong có width = 1316, trừ padding 15 mỗi bên = 1286
        // Tính toán vị trí bắt đầu cho các nút
        int availableWidth = 1286; // 1316 - 15 - 15
        int totalWidth = (3 * buttonWidth) + (2 * buttonGap);
        int startX = availableWidth - totalWidth - rightMargin; // Tính từ mép trái của nội dung panel

        // Nút Đổi phòng bo góc
        LamMoi = createRoundedButton("ĐỔI PHÒNG", new Color(67, 97, 238), new Color(56, 86, 228));
        LamMoi.setBounds(startX, 10, buttonWidth, buttonHeight);
        pnlDanhSachPhong.add(LamMoi);

        // Nút Thuê phòng bo góc
        btn_ThuePhong = createRoundedButton("THUÊ PHÒNG", new Color(67, 97, 238), new Color(56, 86, 228));
        btn_ThuePhong.setBounds(startX + buttonWidth + buttonGap, 10, buttonWidth, buttonHeight);
        pnlDanhSachPhong.add(btn_ThuePhong);

        // Nút Đặt phòng bo góc
        btn_DatPhong = createRoundedButton("ĐẶT PHÒNG", new Color(67, 97, 238), new Color(56, 86, 228));
        btn_DatPhong.setBounds(startX + (2 * (buttonWidth + buttonGap)), 10, buttonWidth, buttonHeight);
        pnlDanhSachPhong.add(btn_DatPhong);

        // ===== PANEL CHỨA DANH SÁCH PHÒNG (KHỞI TẠO) =====
        danhSachPhongPanel = new JPanel();
        danhSachPhongPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        danhSachPhongPanel.setBackground(new Color(248, 250, 252));

        // Tạo ScrollPane để chứa danhSachPhongPanel
        scrollPaneDanhSachPhong = new JScrollPane(danhSachPhongPanel);
        // ScrollPane rộng: 1316 - 15 - 15 = 1286
        scrollPaneDanhSachPhong.setBounds(10, 50, 1286, 536); // Điều chỉnh theo width mới
        scrollPaneDanhSachPhong.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneDanhSachPhong.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneDanhSachPhong.getVerticalScrollBar().setUnitIncrement(16);
        scrollPaneDanhSachPhong.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneDanhSachPhong.getViewport().setBackground(new Color(248, 250, 252));
        pnlDanhSachPhong.add(scrollPaneDanhSachPhong);

        // ===== CONTROLLER =====
        thueDatPhongController = new ThueDatPhongController(this);
    }
}