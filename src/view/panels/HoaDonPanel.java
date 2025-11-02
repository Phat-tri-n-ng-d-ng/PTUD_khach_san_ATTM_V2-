package view.panels;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import java.awt.Component;
import java.awt.Container;

import javax.swing.table.TableModel;

import com.toedter.calendar.JDateChooser;

//import controller.HoaDonController;
//import controller.NhanVienController;

import javax.swing.JTextArea;
import java.awt.SystemColor;

public class HoaDonPanel extends JPanel {
    public static final long serialVersionUID = 1L;
    public JTextField txt_MaHoaDon;
    public JTextField txt_SoDienThoaiKhachHang;
    public JTable table_DanhSachHoaDon;
    public JComboBox cbb_TrangThaiHoaDon;
    public DefaultTableModel model_DSHD;
    public JTextField txtChonNgay;
    public JTextField txt_NgayBD;
    public JTextField txt_NgayKT;
    public JTable table_1;
    public DefaultTableModel model_DSKH;
    public DefaultTableModel model2;
//    public HoaDonController hoaDonController;
    public JButton btn_TimHoaDon_1;
    public JDateChooser ChonNgay;
    public JRadioButton rdbtn_ChonNgay;
    public JRadioButton rdbtn_ChonKhoangTG;
    public JDateChooser NgayBD;
    public JDateChooser ngayKT;
    public JRadioButton rdbtn_TimMaHoaDon;
    public JRadioButton rdbtn_SoDTKH;
    public JPanel pnlBoLocHoaDon;
    public JRadioButton rdbtn_TrangThai;
    public JButton btn_LamMoi;
    private JPanel panel_1;


    public HoaDonPanel() {
        setBounds(100, 100, 1336, 768);
        setBackground(new Color(236, 247, 255));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        JLabel lbl_TieuDe = new JLabel("Hóa Đơn");
        lbl_TieuDe.setForeground(new Color(10, 100, 189));
        lbl_TieuDe.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lbl_TieuDe.setBounds(725, 10, 114, 24);
        add(lbl_TieuDe);

        pnlBoLocHoaDon = new JPanel();
        pnlBoLocHoaDon.setBackground(new Color(255, 255, 255));
        pnlBoLocHoaDon.setBorder(new LineBorder(new Color(0, 0, 0)));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        int doDaiThongTinNhanVien = screenSize.width - 40;
        pnlBoLocHoaDon.setBounds(714, 379,576 , 402);
        add(pnlBoLocHoaDon);
        pnlBoLocHoaDon.setLayout(null);

        txt_MaHoaDon = new JTextField();
        txt_MaHoaDon.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_MaHoaDon.setBounds(61, 128, 390, 30);
        pnlBoLocHoaDon.add(txt_MaHoaDon);
        txt_MaHoaDon.setColumns(10);

        txt_SoDienThoaiKhachHang = new JTextField();
        txt_SoDienThoaiKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_SoDienThoaiKhachHang.setColumns(10);
        txt_SoDienThoaiKhachHang.setBounds(59, 53, 392, 30);
        pnlBoLocHoaDon.add(txt_SoDienThoaiKhachHang);

        cbb_TrangThaiHoaDon = new JComboBox();
        cbb_TrangThaiHoaDon.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        cbb_TrangThaiHoaDon.setModel(new DefaultComboBoxModel(new String[] {"Hóa đơn đặt phòng", "Hóa đơn thuê phòng", "Hóa đơn hoàn thành", "Hóa đơn tạm", "Tất cả"}));
        cbb_TrangThaiHoaDon.setBounds(151, 178, 300, 30);
        pnlBoLocHoaDon.add(cbb_TrangThaiHoaDon);

        rdbtn_TimMaHoaDon = new JRadioButton("  Tìm mã hóa đơn:");
        rdbtn_TimMaHoaDon.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_TimMaHoaDon.setBackground(Color.WHITE);
        rdbtn_TimMaHoaDon.setBounds(34, 102, 150, 21);
        pnlBoLocHoaDon.add(rdbtn_TimMaHoaDon);

        rdbtn_SoDTKH = new JRadioButton("  Tìm số điện thoại khách hàng:");
        rdbtn_SoDTKH.setHorizontalAlignment(SwingConstants.CENTER);
        rdbtn_SoDTKH.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_SoDTKH.setBackground(Color.WHITE);
        rdbtn_SoDTKH.setBounds(33, 27, 221, 21);
        pnlBoLocHoaDon.add(rdbtn_SoDTKH);

        btn_TimHoaDon_1 = new JButton("Tìm");
        btn_TimHoaDon_1.setBounds(1370, 26, 120, 30);
        pnlBoLocHoaDon.add(btn_TimHoaDon_1);

        ChonNgay = new JDateChooser();
        ChonNgay.setDateFormatString("dd/MM/yyyy");
        ChonNgay.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ChonNgay.setBounds(152, 233, 299, 30);
        pnlBoLocHoaDon.add(ChonNgay);

        rdbtn_ChonNgay = new JRadioButton("  Chọn ngày: ");
        rdbtn_ChonNgay.setBounds(34, 237, 112, 21);
        pnlBoLocHoaDon.add(rdbtn_ChonNgay);
        rdbtn_ChonNgay.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_ChonNgay.setBackground(Color.WHITE);

        rdbtn_ChonKhoangTG = new JRadioButton("  Hóa đơn trong khoảng: ");
        rdbtn_ChonKhoangTG.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_ChonKhoangTG.setBackground(Color.WHITE);
        rdbtn_ChonKhoangTG.setBounds(34, 293, 181, 21);
        pnlBoLocHoaDon.add(rdbtn_ChonKhoangTG);

        NgayBD = new JDateChooser();
        NgayBD.setDateFormatString("dd/MM/yyyy");
        NgayBD.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        NgayBD.setBounds(221, 289, 102, 30);
        pnlBoLocHoaDon.add(NgayBD);

        ngayKT = new JDateChooser();
        ngayKT.setDateFormatString("dd/MM/yyyy");
        ngayKT.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayKT.setBounds(349, 289, 102, 30);
        
        pnlBoLocHoaDon.add(ngayKT);

        rdbtn_TrangThai = new JRadioButton(" Trạng thái:");
        rdbtn_TrangThai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_TrangThai.setBackground(Color.WHITE);
        rdbtn_TrangThai.setBounds(33, 183, 99, 21);
      
        pnlBoLocHoaDon.add(rdbtn_TrangThai);
        ButtonGroup group = new ButtonGroup();
        group.add(rdbtn_TimMaHoaDon);
        group.add(rdbtn_ChonNgay);
        group.add(rdbtn_SoDTKH);
        group.add(rdbtn_ChonKhoangTG);
        group.add(rdbtn_TrangThai);
        
        JButton btn_TimHoaDon = new JButton("Tìm");
        btn_TimHoaDon.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btn_TimHoaDon.setBounds(434, 340, 131, 39);
        pnlBoLocHoaDon.add(btn_TimHoaDon);
      



        JLabel lbl_Boloc = new JLabel("Bộ lọc");
        lbl_Boloc.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_Boloc.setBounds(714, 349, 62, 20);
        add(lbl_Boloc);

        JLabel lbl_DanhSachHoaDon = new JLabel("Danh Sách Hóa Đơn");
        lbl_DanhSachHoaDon.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_DanhSachHoaDon.setBounds(20, 199, 180, 20);
        add(lbl_DanhSachHoaDon);

        JPanel pnlDanhSachHoaDon = new JPanel();
        pnlDanhSachHoaDon.setBackground(new Color(255, 255, 255));
        pnlDanhSachHoaDon.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnlDanhSachHoaDon.setBounds(20, 229, 668, 552);
        add(pnlDanhSachHoaDon);

        model_DSHD = new DefaultTableModel(new String[] {"Mã hóa đơn","Ngày lập","Tên khách hàng","Số điện thoại KH", "Tổng tiền","Trạng thái hóa đơn"}, 0);
        pnlDanhSachHoaDon.setLayout(null);
        table_DanhSachHoaDon = new JTable(model_DSHD);
        JScrollPane scrollPane = new JScrollPane(table_DanhSachHoaDon);
        scrollPane.setBounds(10, 10, 648, 526);
        JTableHeader header = table_DanhSachHoaDon.getTableHeader();
        header.setFont(new Font("Times New Roman", Font.BOLD, 18));
        header.setBackground(new Color(10, 100, 189));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        table_DanhSachHoaDon.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnlDanhSachHoaDon.add(scrollPane);

        model_DSKH= new DefaultTableModel(new String[] {"Tên KH","Giới tính","Ngày sinh","Số điện thoại", "Số CCCD"}, 0);
        pnlDanhSachHoaDon.setLayout(null);
        header.setFont(new Font("Times New Roman", Font.BOLD, 18));
        header.setBackground(new Color(10, 100, 189));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        model2= new DefaultTableModel(new String[] {"Mã phòng","Số ngày ở","Đơn giá","Thành Tiền"}, 0);
        header.setFont(new Font("Times New Roman", Font.BOLD, 18));
        header.setBackground(new Color(10, 100, 189));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
//        hoaDonController = new HoaDonController(this);
        
        panel_1 = new JPanel();
        panel_1.setBounds(1326, 10, 210, 844);
        add(panel_1);
//        hoaDonController.getTatCaHoaDon();
//
    }
}
