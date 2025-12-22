package view.dialogs;

import com.toedter.calendar.JDateChooser;
import controllers.dialogs.PhieuDoiPhongThueController;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.Dimension;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PhieuDoiPhongDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    public  JLabel lbl_TienCuaThanhTien;
    public  JLabel lbl_TienCuaGiaChenhLech;
    public JTextField txt_SDT;
    public JTextField txt_HoTen;
    public JTextField txt_CCCD;
    public JDateChooser ngayBatDau;
    public JDateChooser ngayKetThuc;
    public JLabel lbl_GiaPhongCu;
    public JLabel lbl_GiaPhongMoi;
    public JLabel lbl_PhiDoiPhongTrongPnlTongTien;
    public JLabel lbl_TienCuaGiaPhongCu;
    public JLabel lbl_TienCuaGiaPhongMoi;
    public JLabel lbl_TienCuaPhiDoiPhongTrongPnlTongTien;
    public JTable table;
    public DefaultTableModel model;
    public JButton btnXacNhan;
    public JButton btnHuy;
    public DefaultTableModel modelPhongDoi,modelPhongHienTai;
    public JCheckBox chckbx_Standard;
    public JCheckBox chckbx_Superior;
    public JCheckBox chckbx_Deluxe;
    public JCheckBox chckbx_Suite;
    public JCheckBox chckbx_FamilyRoom;
    public JTable tablePhongHienTai, tablePhongDoi;
    public JRadioButton rdbtn_KhachHangYeuCau, rdbtn_KhachSanSapXep;
    PhieuDoiPhongThueController phieuDoiPhongThueController;




    public PhieuDoiPhongDialog() {
        getContentPane().setBackground(new Color(236, 247, 255));
        setBounds(100, 100, 1100, 650);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblTenKhachSanMenu = new JLabel("ATTM");
        lblTenKhachSanMenu.setFont(new Font("Lucida Calligraphy", Font.BOLD, 24));
        lblTenKhachSanMenu.setHorizontalAlignment(SwingConstants.LEFT);
        lblTenKhachSanMenu.setForeground(new Color(10, 110, 189));
        lblTenKhachSanMenu.setBounds(10, 10, 90, 24);
        getContentPane().add(lblTenKhachSanMenu);

        JLabel lblTieuDeForm = new JLabel("Thông tin đổi phòng");
        lblTieuDeForm.setForeground(new Color(10, 110, 189));
        lblTieuDeForm.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTieuDeForm.setBounds(430, 3, 302, 40);
        getContentPane().add(lblTieuDeForm);

        JLabel lblFromThongTinKhachHang = new JLabel("Thông tin khách hàng ");
        lblFromThongTinKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblFromThongTinKhachHang.setBounds(13, 60, 302, 26);
        getContentPane().add(lblFromThongTinKhachHang);

        JPanel pnl_ThongTinKhachHang = new JPanel();
        // Tạo viền mờ màu xám nhạt với bo góc 15px
        pnl_ThongTinKhachHang.setBorder(null);
        pnl_ThongTinKhachHang.setBackground(new Color(255, 255, 255));
        pnl_ThongTinKhachHang.setBounds(10, 90, 525, 165);
        getContentPane().add(pnl_ThongTinKhachHang);
        pnl_ThongTinKhachHang.setLayout(null);

        JLabel lbl_SDT = new JLabel("Số điện thoại: ");
        lbl_SDT.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_SDT.setBounds(10, 20, 102, 20);
        pnl_ThongTinKhachHang.add(lbl_SDT);

        JLabel lbl_HoTen = new JLabel("Họ tên:");
        lbl_HoTen.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_HoTen.setBounds(10, 70, 102, 20);
        pnl_ThongTinKhachHang.add(lbl_HoTen);

        JLabel lbl_CCCD = new JLabel("CCCD:");
        lbl_CCCD.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_CCCD.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_CCCD.setBounds(10, 120, 102, 20);
        pnl_ThongTinKhachHang.add(lbl_CCCD);

        txt_SDT = new JTextField();
        txt_SDT.setBounds(132, 19, 373, 25);
        pnl_ThongTinKhachHang.add(txt_SDT);
        txt_SDT.setColumns(10);

        txt_HoTen = new JTextField();
        txt_HoTen.setColumns(10);
        txt_HoTen.setBounds(132, 69, 373, 25);
        pnl_ThongTinKhachHang.add(txt_HoTen);

        txt_CCCD = new JTextField();
        txt_CCCD.setColumns(10);
        txt_CCCD.setBounds(132, 119, 373, 25);
        pnl_ThongTinKhachHang.add(txt_CCCD);


        JLabel lbl_PhongHienTai = new JLabel("Phòng hiện tại");
        lbl_PhongHienTai.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_PhongHienTai.setBounds(10, 273, 147, 26);
        getContentPane().add(lbl_PhongHienTai);

        JPanel pnl_DanhSachPhong = new JPanel();
        pnl_DanhSachPhong.setBackground(new Color(255, 255, 255));
        pnl_DanhSachPhong.setBounds(10, 303, 525, 88);
        pnl_DanhSachPhong.setLayout(null);
        getContentPane().add(pnl_DanhSachPhong);


         modelPhongHienTai =new DefaultTableModel(new String[] {
        	        "Mã phòng","Loại phòng","SLTĐ","Giá","Tiền cọc"}, 0);
         tablePhongHienTai = new JTable(modelPhongHienTai);
        JScrollPane PhongHienTai = new JScrollPane(tablePhongHienTai);
        PhongHienTai.setBounds(10, 10, 505, 66);
        pnl_DanhSachPhong.add(PhongHienTai);
        JTableHeader header = tablePhongHienTai.getTableHeader();
        header.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        tablePhongHienTai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        tablePhongHienTai.setBackground(Color.WHITE);
        tablePhongHienTai.getTableHeader().setBackground(Color.WHITE);
        PhongHienTai.getViewport().setBackground(Color.WHITE);
        pnl_DanhSachPhong.add(PhongHienTai);

        JLabel lbl_PhiDoi = new JLabel("Tổng tiền");
        lbl_PhiDoi.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_PhiDoi.setBounds(551, 273, 102, 26);
        getContentPane().add(lbl_PhiDoi);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(551, 303, 525, 117);
        getContentPane().add(panel);
        panel.setLayout(null);

        lbl_GiaPhongCu = new JLabel("Giá phòng cũ:");
        lbl_GiaPhongCu.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_GiaPhongCu.setBounds(10, 10, 163, 18);
        panel.add(lbl_GiaPhongCu);

        lbl_GiaPhongMoi = new JLabel("Giá phòng mới:");
        lbl_GiaPhongMoi.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_GiaPhongMoi.setBounds(10, 30, 163, 18);
        panel.add(lbl_GiaPhongMoi);

        lbl_PhiDoiPhongTrongPnlTongTien = new JLabel("Phí đổi phòng:");
        lbl_PhiDoiPhongTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_PhiDoiPhongTrongPnlTongTien.setBounds(10, 70, 163, 18);
        panel.add(lbl_PhiDoiPhongTrongPnlTongTien);

        lbl_TienCuaGiaPhongCu = new JLabel("0 VND");
        lbl_TienCuaGiaPhongCu.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaGiaPhongCu.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaGiaPhongCu.setBounds(364, 10, 151, 18);
        panel.add(lbl_TienCuaGiaPhongCu);


        lbl_TienCuaGiaPhongMoi = new JLabel("0 VND");
        lbl_TienCuaGiaPhongMoi.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaGiaPhongMoi.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaGiaPhongMoi.setBounds(364, 30, 151, 18);
        panel.add(lbl_TienCuaGiaPhongMoi);

        lbl_TienCuaPhiDoiPhongTrongPnlTongTien = new JLabel("0 VND");
        lbl_TienCuaPhiDoiPhongTrongPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaPhiDoiPhongTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaPhiDoiPhongTrongPnlTongTien.setBounds(364, 70, 151, 18);
        panel.add(lbl_TienCuaPhiDoiPhongTrongPnlTongTien);

        JLabel lbl_GiaChenhLech = new JLabel("Giá chênh lệch:");
        lbl_GiaChenhLech.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_GiaChenhLech.setBounds(10, 50, 163, 18);
        panel.add(lbl_GiaChenhLech);

        lbl_TienCuaGiaChenhLech = new JLabel("0 VND");
        lbl_TienCuaGiaChenhLech.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaGiaChenhLech.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaGiaChenhLech.setBounds(374, 50, 141, 18);
        panel.add(lbl_TienCuaGiaChenhLech);

        JLabel lbl_ThanhTien = new JLabel("Thành tiền:");
        lbl_ThanhTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_ThanhTien.setBounds(10, 90, 163, 18);
        panel.add(lbl_ThanhTien);

        lbl_TienCuaThanhTien = new JLabel("0 VND");
        lbl_TienCuaThanhTien.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaThanhTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaThanhTien.setBounds(374, 90, 141, 18);
        panel.add(lbl_TienCuaThanhTien);

        class RoundedButton extends JButton {
            private int radius;

            public RoundedButton(String label, Color bg, Color fg, int radius) {
                super(label);
                this.radius = radius;
                setBackground(bg);
                setForeground(fg);
                setContentAreaFilled(false);
                setFocusPainted(false);
                setBorder(null);
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
                super.paintComponent(g2);
                g2.dispose();
            }
        }
        
        btnXacNhan = new JButton("Xác nhận");
        btnXacNhan.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnXacNhan.setBounds(653, 499, 140, 26);
        btnXacNhan.setBackground(new Color(144, 238, 144));
        getContentPane().add(btnXacNhan);
        
        btnHuy = new JButton("Hủy");
        btnHuy.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnHuy.setBounds(840, 499, 140, 26);
        btnHuy.setBackground(new Color(255, 182, 193));
        getContentPane().add(btnHuy);
        
        JLabel lbl_ChonPhongDoi = new JLabel("Chọn phòng đổi đến");
        lbl_ChonPhongDoi.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_ChonPhongDoi.setBounds(551, 60, 194, 26);
        getContentPane().add(lbl_ChonPhongDoi);
        
        JPanel pnl_DanhSachPhong_1 = new JPanel();
        pnl_DanhSachPhong_1.setLayout(null);
        pnl_DanhSachPhong_1.setBackground(Color.WHITE);
        pnl_DanhSachPhong_1.setBounds(551, 90, 525, 165);
        getContentPane().add(pnl_DanhSachPhong_1);

        
        
         modelPhongDoi =new DefaultTableModel(new String[] {"Mã phòng","Loại phòng","SLTĐ","Giá","Tiền cọc"}, 0);
         tablePhongDoi = new JTable(modelPhongDoi);
        JScrollPane scrollPanePhongDoi = new JScrollPane(tablePhongDoi);
        scrollPanePhongDoi.setBounds(10, 62, 505, 93);
        pnl_DanhSachPhong_1.add(scrollPanePhongDoi);
        JTableHeader header1 = tablePhongDoi.getTableHeader();
        header1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        header1.setPreferredSize(new Dimension(header1.getWidth(), 35));
        tablePhongDoi.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        tablePhongDoi.setBackground(Color.WHITE);
        tablePhongDoi.getTableHeader().setBackground(Color.WHITE);
        scrollPanePhongDoi.getViewport().setBackground(Color.WHITE);
        pnl_DanhSachPhong_1.add(scrollPanePhongDoi);
        
        JLabel lbl_LoaiPhong = new JLabel("Loại phòng:");
        lbl_LoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_LoaiPhong.setBounds(10, 10, 72, 18);
        pnl_DanhSachPhong_1.add(lbl_LoaiPhong);
        
         chckbx_Standard = new JCheckBox("Standard");
        chckbx_Standard.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        chckbx_Standard.setBackground(Color.WHITE);
        chckbx_Standard.setBounds(10, 34, 81, 22);
        pnl_DanhSachPhong_1.add(chckbx_Standard);
        
         chckbx_Superior = new JCheckBox("Superior");
        chckbx_Superior.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        chckbx_Superior.setBackground(Color.WHITE);
        chckbx_Superior.setBounds(122, 34, 81, 22);
        pnl_DanhSachPhong_1.add(chckbx_Superior);
        
         chckbx_Deluxe = new JCheckBox("Deluxe");
        chckbx_Deluxe.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        chckbx_Deluxe.setBackground(Color.WHITE);
        chckbx_Deluxe.setBounds(229, 34, 72, 22);
        pnl_DanhSachPhong_1.add(chckbx_Deluxe);
        
         chckbx_Suite = new JCheckBox("Suite");
        chckbx_Suite.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        chckbx_Suite.setBackground(Color.WHITE);
        chckbx_Suite.setBounds(324, 34, 57, 22);
        pnl_DanhSachPhong_1.add(chckbx_Suite);
        
         chckbx_FamilyRoom = new JCheckBox("Family Room");
        chckbx_FamilyRoom.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        chckbx_FamilyRoom.setBackground(Color.WHITE);
        chckbx_FamilyRoom.setBounds(404, 34, 111, 22);
        pnl_DanhSachPhong_1.add(chckbx_FamilyRoom);
        
        JLabel lbl_LyDoDoiPhong = new JLabel("Lý do đổi phòng:");
        lbl_LyDoDoiPhong.setBounds(551, 445, 147, 26);
        getContentPane().add(lbl_LyDoDoiPhong);
        lbl_LyDoDoiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        
        ButtonGroup buttonGroup= new ButtonGroup();
         rdbtn_KhachHangYeuCau = new JRadioButton("Khách hàng yêu cầu");
        rdbtn_KhachHangYeuCau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_KhachHangYeuCau.setBounds(686, 448, 173, 20);
        getContentPane().add(rdbtn_KhachHangYeuCau);
        
         rdbtn_KhachSanSapXep = new JRadioButton("Khách sạn sắp xếp");
        rdbtn_KhachSanSapXep.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_KhachSanSapXep.setBounds(880, 448, 146, 20);
        getContentPane().add(rdbtn_KhachSanSapXep);
        buttonGroup.add(rdbtn_KhachSanSapXep);
        buttonGroup.add(rdbtn_KhachHangYeuCau);
        rdbtn_KhachHangYeuCau.setOpaque(false);
        rdbtn_KhachSanSapXep.setOpaque(false);
        
        JPanel pnl_ThoiGianThue = new JPanel();
        pnl_ThoiGianThue.setBounds(10, 445, 525, 80);
        getContentPane().add(pnl_ThoiGianThue);
        pnl_ThoiGianThue.setBackground(new Color(255, 255, 255));
        pnl_ThoiGianThue.setLayout(null);
                
        JLabel lbl_NgayBatDau = new JLabel("Ngày bắt đầu: ");
        lbl_NgayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_NgayBatDau.setBounds(10, 10, 100, 20);
        pnl_ThoiGianThue.add(lbl_NgayBatDau);
                        
        ngayBatDau = new JDateChooser();
        ngayBatDau.setDateFormatString("dd/MM/yyyy");
        ngayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayBatDau.setBounds(140, 9, 375, 25);
        ngayBatDau.setEnabled(false);
        pnl_ThoiGianThue.add(ngayBatDau);
                                
        JLabel lbl_TienKhachDuaTrongPnlTongTien = new JLabel("Ngày kết thúc: ");
        lbl_TienKhachDuaTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_TienKhachDuaTrongPnlTongTien.setBounds(10, 50, 100, 20);
        pnl_ThoiGianThue.add(lbl_TienKhachDuaTrongPnlTongTien);

        ngayKetThuc = new JDateChooser();
        ngayKetThuc.setDateFormatString("dd/MM/yyyy");
        ngayKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayKetThuc.setBounds(140, 49, 375, 25);
        ngayKetThuc.setEnabled(false);
        pnl_ThoiGianThue.add(ngayKetThuc);

        JLabel lbl_ThoiGianThue = new JLabel("Thời gian thuê");
        lbl_ThoiGianThue.setBounds(10, 415, 128, 26);
        getContentPane().add(lbl_ThoiGianThue);
        lbl_ThoiGianThue.setBackground(new Color(255, 255, 255));
        lbl_ThoiGianThue.setFont(new Font("Times New Roman", Font.PLAIN, 20));

//        Phong p=new Phong("P01001",1000000);
//        phieuDoiPhongController= new PhieuDoiPhongController(this,p);

    }
}