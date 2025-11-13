package view.panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controllers.LoaiPhongController;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoaiPhongPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    public JTextField txt_TyLeCoc;
    public JTextField txt_GiaNiemYet;
    public JTextField txt_TenLoaiPhong;
    public JTextField txt_SucChuaToiThieu;
    public JTable table;
    public DefaultTableModel model;
    public JButton btn_Them, btn_LamMoi;

    public LoaiPhongPanel() {
        setBackground(new Color(236, 247, 255));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        JLabel lbl_TieuDe = new JLabel("Loại phòng");
        lbl_TieuDe.setForeground(new Color(10, 100, 189));
        lbl_TieuDe.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lbl_TieuDe.setBounds(606, 10, 120, 24);
        add(lbl_TieuDe);

        // Thông tin loại phòng
        JLabel lbl_ThongTinLoaiPhong = new JLabel("Thông tin loại phòng");
        lbl_ThongTinLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_ThongTinLoaiPhong.setBounds(20, 49, 200, 20);
        add(lbl_ThongTinLoaiPhong);

        JPanel pnlThongTinLoaiPhong = new JPanel();
        pnlThongTinLoaiPhong.setBackground(Color.WHITE);
        pnlThongTinLoaiPhong.setBorder(new LineBorder(Color.BLACK));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        pnlThongTinLoaiPhong.setBounds(20, 79, 1292, 175);
        add(pnlThongTinLoaiPhong);
        pnlThongTinLoaiPhong.setLayout(null);

        JLabel lbl_TenLoaiPhong = new JLabel("Tên loại phòng:");
        lbl_TenLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_TenLoaiPhong.setBounds(10, 10, 150, 19);
        pnlThongTinLoaiPhong.add(lbl_TenLoaiPhong);

        txt_TenLoaiPhong = new JTextField();
        txt_TenLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_TenLoaiPhong.setBounds(10, 33, 631, 30);
        pnlThongTinLoaiPhong.add(txt_TenLoaiPhong);

        JLabel lbl_GiaNiemYet = new JLabel("Giá niêm yết:");
        lbl_GiaNiemYet.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_GiaNiemYet.setBounds(651, 10, 150, 19);
        pnlThongTinLoaiPhong.add(lbl_GiaNiemYet);

        txt_GiaNiemYet = new JTextField();
        txt_GiaNiemYet.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_GiaNiemYet.setBounds(651, 33, 631, 30);
        pnlThongTinLoaiPhong.add(txt_GiaNiemYet);

        JLabel lbl_TyLeCoc = new JLabel("Tỷ lệ cọc:");
        lbl_TyLeCoc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_TyLeCoc.setBounds(10, 73, 150, 20);
        pnlThongTinLoaiPhong.add(lbl_TyLeCoc);

        txt_TyLeCoc = new JTextField();
        txt_TyLeCoc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_TyLeCoc.setBounds(10, 94, 631, 30);
        pnlThongTinLoaiPhong.add(txt_TyLeCoc);

        JLabel lbl_SucChuaToiThieu = new JLabel("Sức chứa tối thiểu:");
        lbl_SucChuaToiThieu.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_SucChuaToiThieu.setBounds(651, 73, 150, 20);
        pnlThongTinLoaiPhong.add(lbl_SucChuaToiThieu);

        txt_SucChuaToiThieu = new JTextField();
        txt_SucChuaToiThieu.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_SucChuaToiThieu.setBounds(651, 94, 631, 30);
        pnlThongTinLoaiPhong.add(txt_SucChuaToiThieu);

        btn_Them = new JButton("Thêm");
        btn_Them.setBounds(1028, 134, 120, 30);
        pnlThongTinLoaiPhong.add(btn_Them);

        btn_LamMoi = new JButton("Làm mới");
        btn_LamMoi.setBounds(1158, 134, 120, 30);
        pnlThongTinLoaiPhong.add(btn_LamMoi);

        // Danh sách loại phòng
        JLabel lbl_DanhSachLoaiPhong = new JLabel("Danh sách loại phòng");
        lbl_DanhSachLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_DanhSachLoaiPhong.setBounds(20, 264, 200, 20);
        add(lbl_DanhSachLoaiPhong);

        JPanel pnlDanhSachLoaiPhong = new JPanel();
        pnlDanhSachLoaiPhong.setBackground(Color.WHITE);
        pnlDanhSachLoaiPhong.setBorder(new LineBorder(Color.BLACK));
        pnlDanhSachLoaiPhong.setBounds(20, 294, 1292, 498);
        add(pnlDanhSachLoaiPhong);
        pnlDanhSachLoaiPhong.setLayout(null);

        model = new DefaultTableModel(
                new String[]{"Mã loại phòng", "Tên loại phòng", "Giá niêm yết", "Tỷ lệ cọc", "Sức chứa tối thiểu"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 1272, 478);
        pnlDanhSachLoaiPhong.add(scrollPane);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Times New Roman", Font.BOLD, 18));
        header.setBackground(new Color(10, 100, 189));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        
        LoaiPhongController loaiPhongController = new LoaiPhongController(this);
        loaiPhongController.hienThiDanhSachLoaiPhong();
    }
}
