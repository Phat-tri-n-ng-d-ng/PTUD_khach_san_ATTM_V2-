package view.panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controllers.PhongController;
import enums.TrangThaiPhong;

import java.awt.*;

public class PhongPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    public JTextField txt_Tang;
    public JTextField txt_SoLuongToiDa;
    public JTextField txt_SoPhong;
    public JTextField txt_TimMaPhong;
    public JTable table;
    public JComboBox cbb_LoaiPhong;
    public DefaultTableModel model;
    public JButton btn_ThemPhong, btn_Tim, btn_LamMoi;
    public JCheckBox chckbx_Standard;
    public JCheckBox chckbx_Superior;
    public JCheckBox chckbx_Deluxe;
	public JCheckBox chckbx_Suite;
	public JCheckBox chckbx_FamilyRoom;

    public PhongPanel() {
        setBounds(100, 100, 1336, 768);
        setBackground(new Color(236, 247, 255));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        JLabel lbl_TieuDe = new JLabel("Phòng");
        lbl_TieuDe.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_TieuDe.setForeground(new Color(10, 100, 189));
        lbl_TieuDe.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lbl_TieuDe.setBounds(725, 10, 114, 24);
        add(lbl_TieuDe);

        // ===== Thông tin phòng =====
        JLabel lbl_ThongTinPhong = new JLabel("Thông tin phòng");
        lbl_ThongTinPhong.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_ThongTinPhong.setBounds(20, 44, 180, 20);
        add(lbl_ThongTinPhong);

        JPanel pnlThongTinPhong = new JPanel();
        pnlThongTinPhong.setBackground(Color.WHITE);
        pnlThongTinPhong.setBorder(new LineBorder(Color.BLACK));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        pnlThongTinPhong.setBounds(20, 74, 1292, 175);
        add(pnlThongTinPhong);
        pnlThongTinPhong.setLayout(null);

        JLabel lbl_SoPhong = new JLabel("Số phòng:");
        lbl_SoPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_SoPhong.setBounds(10, 10, 104, 16);
        pnlThongTinPhong.add(lbl_SoPhong);

        txt_SoPhong = new JTextField();
        txt_SoPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_SoPhong.setBounds(10, 33, 626, 30);
        pnlThongTinPhong.add(txt_SoPhong);

        JLabel lbl_SoLuongToiDa = new JLabel("Số lượng tối đa:");
        lbl_SoLuongToiDa.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_SoLuongToiDa.setBounds(652, 10, 130, 16);
        pnlThongTinPhong.add(lbl_SoLuongToiDa);

        txt_SoLuongToiDa = new JTextField();
        txt_SoLuongToiDa.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_SoLuongToiDa.setBounds(652, 33, 626, 30);
        pnlThongTinPhong.add(txt_SoLuongToiDa);

        JLabel lbl_Tang = new JLabel("Tầng:");
        lbl_Tang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_Tang.setBounds(10, 73, 104, 16);
        pnlThongTinPhong.add(lbl_Tang);

        txt_Tang = new JTextField();
        txt_Tang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_Tang.setBounds(10, 94, 626, 30);
        pnlThongTinPhong.add(txt_Tang);

        JLabel lbl_LoaiPhong = new JLabel("Loại phòng:");
        lbl_LoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_LoaiPhong.setBounds(652, 73, 104, 16);
        pnlThongTinPhong.add(lbl_LoaiPhong);

        cbb_LoaiPhong = new JComboBox();
        cbb_LoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        cbb_LoaiPhong.setModel(new DefaultComboBoxModel<>(new String[]{"Standard", "Superior", "Deluxe", "Suite", "Family Room"}));
        cbb_LoaiPhong.setBounds(652, 94, 626, 30);
        pnlThongTinPhong.add(cbb_LoaiPhong);

        btn_ThemPhong = new JButton("Thêm phòng");
        btn_ThemPhong.setBounds(1028, 134, 120, 30);
        pnlThongTinPhong.add(btn_ThemPhong);

        btn_LamMoi = new JButton("Làm mới");
        btn_LamMoi.setBounds(1158, 134, 120, 30);
        pnlThongTinPhong.add(btn_LamMoi);

        // ===== Bộ lọc =====
        JLabel lbl_BoLoc = new JLabel("Bộ lọc");
        lbl_BoLoc.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_BoLoc.setBounds(20, 259, 70, 20);
        add(lbl_BoLoc);

        JPanel pnlBoLoc = new JPanel();
        pnlBoLoc.setBackground(Color.WHITE);
        pnlBoLoc.setBorder(new LineBorder(Color.BLACK));
        pnlBoLoc.setBounds(20, 289, 1292, 104);
        add(pnlBoLoc);
        pnlBoLoc.setLayout(null);

        JLabel lbl_TimMaPhong = new JLabel("Tìm mã phòng:");
        lbl_TimMaPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_TimMaPhong.setBounds(652, 10, 104, 16);
        pnlBoLoc.add(lbl_TimMaPhong);

        txt_TimMaPhong = new JTextField();
        txt_TimMaPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_TimMaPhong.setBounds(652, 33, 626, 30);
        pnlBoLoc.add(txt_TimMaPhong);

        btn_Tim = new JButton("Tìm");
        btn_Tim.setBounds(1166, 71, 112, 30);
        pnlBoLoc.add(btn_Tim);
        
        chckbx_Standard = new JCheckBox("Standard");
        chckbx_Standard.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        chckbx_Standard.setBackground(Color.WHITE);
        chckbx_Standard.setBounds(10, 41, 100, 22);
        pnlBoLoc.add(chckbx_Standard);
        
        JLabel lbl_LoaiPhongTim = new JLabel("Loại phòng:");
        lbl_LoaiPhongTim.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_LoaiPhongTim.setBounds(10, 16, 104, 16);
        pnlBoLoc.add(lbl_LoaiPhongTim);
        
        chckbx_Superior = new JCheckBox("Superior");
        chckbx_Superior.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        chckbx_Superior.setBackground(Color.WHITE);
        chckbx_Superior.setBounds(130, 41, 100, 22);
        pnlBoLoc.add(chckbx_Superior);
        
        chckbx_Deluxe = new JCheckBox("Deluxe");
        chckbx_Deluxe.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        chckbx_Deluxe.setBackground(Color.WHITE);
        chckbx_Deluxe.setBounds(250, 41, 100, 22);
        pnlBoLoc.add(chckbx_Deluxe);
        
        chckbx_Suite = new JCheckBox("Suite");
        chckbx_Suite.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        chckbx_Suite.setBackground(Color.WHITE);
        chckbx_Suite.setBounds(370, 41, 70, 22);
        pnlBoLoc.add(chckbx_Suite);
        
        chckbx_FamilyRoom = new JCheckBox("Family Room");
        chckbx_FamilyRoom.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        chckbx_FamilyRoom.setBackground(Color.WHITE);
        chckbx_FamilyRoom.setBounds(490, 41, 130, 22);
        pnlBoLoc.add(chckbx_FamilyRoom);

        // ===== Danh sách phòng =====
        JLabel lbl_DanhSachPhong = new JLabel("Danh sách phòng");
        lbl_DanhSachPhong.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_DanhSachPhong.setBounds(20, 403, 180, 20);
        add(lbl_DanhSachPhong);

        JPanel pnlDanhSachPhong = new JPanel();
        pnlDanhSachPhong.setBackground(Color.WHITE);
        pnlDanhSachPhong.setBorder(new LineBorder(Color.BLACK));
        pnlDanhSachPhong.setBounds(20, 433, 1292, 358);
        add(pnlDanhSachPhong);
        pnlDanhSachPhong.setLayout(null);

        model = new DefaultTableModel(
                new String[]{"Mã phòng", "Loại phòng", "Tầng", "Số phòng", "Số lượng tối đa", "Giá phòng", "Tiền cọc", "Trạng thái"}, 0);
        
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 1272, 338);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Times New Roman", Font.BOLD, 18));
        header.setBackground(new Color(10, 100, 189));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnlDanhSachPhong.add(scrollPane);
        model.addRow(new Object[]{"P01001", "Standard", 1, 1, 2, 1000000, 300000, TrangThaiPhong.Trong.getMoTa()});
        model.addRow(new Object[]{"P02005", "Deluxe", 2, 5, 3, 1500000, 450000, TrangThaiPhong.DaDat.getMoTa()});
        model.addRow(new Object[]{"P03010", "Suite", 3, 10, 4, 2000000, 600000, TrangThaiPhong.NgungHoatDong.getMoTa()});

        // Controller
         PhongController phongController = new PhongController(this);
         // hiển thị lên table
    }
}
