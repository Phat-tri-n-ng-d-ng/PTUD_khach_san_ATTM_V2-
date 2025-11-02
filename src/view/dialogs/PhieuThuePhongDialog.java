package view.dialogs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PhieuThuePhongDialog extends JDialog {
	 public JTextField txt_SDT;
	    public JTextField txt_HoTen;
	    public JTextField txt_CCCD;
	    public JTextField txt_NgaySinh;
	    public JTextField txt_Email;
	    public JTextField txt_HangKhachHang;
	    public JTextField txt_DiemTichLuy;
	    public JTextField txt_TienKhachDua;
	    public JLabel lbl_PhuongThucThanhToanTrongPnlTongTien;
	    public JLabel lbl_TongTienTrongPnlTongTien;
	    public JLabel lbl_TienTraLaiKhachTrongPnlTongTien;
	    public JLabel lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien;
	    public JLabel lbl_TienCuaTongTienTrongPnlTongTien;
	    public JLabel lbl_TienCuaTienNhanTuKhachTrongPnlTongTien;
	    public JLabel lbl_TienCuaTienTraLaiKhachTrongPnlTongTien;
	    public JLabel lbl_TienNhanTuKhachTrongPnlTongTien;
	    public JButton btn_XacNhan;
	    public JButton btn_Huy;
	    public JTable table_DanhSachPhong;
	    public DefaultTableModel model_DanhSachPhong;
	    public JTable table_DanhSachNguoiO;
	    public DefaultTableModel model_DanhSachNguoiO;
	    public JRadioButton rdbtn_TienMat;
	    public JRadioButton rdbtn_ChuyenKhoan;
	    public JRadioButton rdbtn_NamNguoiO;
	    public JRadioButton rdbtn_NuNguoiO;
		public ButtonGroup gioiTinhGroup;
		public JButton btn_inHoaDon;
		public JButton btn_huyHoaDon;

	public PhieuThuePhongDialog() {

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

        JLabel lblTieuDeForm = new JLabel("Phiếu thuê phòng");
        lblTieuDeForm.setHorizontalAlignment(SwingConstants.CENTER);
        lblTieuDeForm.setForeground(new Color(10, 110, 189));
        lblTieuDeForm.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTieuDeForm.setBounds(430, 3, 248, 40);
        getContentPane().add(lblTieuDeForm);

        JLabel lblFromThongTinKhachHang = new JLabel("Thông tin khách hàng thuê phòng");
        lblFromThongTinKhachHang.setHorizontalAlignment(SwingConstants.CENTER);
        lblFromThongTinKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblFromThongTinKhachHang.setBounds(118, 33, 302, 26);
        getContentPane().add(lblFromThongTinKhachHang);

        JPanel pnl_ThongTinKhachHang = new JPanel();
        // Tạo viền mờ màu xám nhạt với bo góc 15px
        pnl_ThongTinKhachHang.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), HIDE_ON_CLOSE)// Viền ngoài
                , null
        ));
        pnl_ThongTinKhachHang.setBackground(new Color(255, 255, 255));
        pnl_ThongTinKhachHang.setBounds(10, 69, 525, 294);
        getContentPane().add(pnl_ThongTinKhachHang);
        pnl_ThongTinKhachHang.setLayout(null);

        JLabel lbl_SDT = new JLabel("Số điện thoại: ");
        lbl_SDT.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_SDT.setBounds(10, 21, 102, 20);
        pnl_ThongTinKhachHang.add(lbl_SDT);

        JLabel lbl_HoTen = new JLabel("Họ tên:");
        lbl_HoTen.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_HoTen.setBounds(10, 67, 102, 20);
        pnl_ThongTinKhachHang.add(lbl_HoTen);

        JLabel lbl_CCCD = new JLabel("CCCD:");
        lbl_CCCD.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_CCCD.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_CCCD.setBounds(10, 113, 102, 20);
        pnl_ThongTinKhachHang.add(lbl_CCCD);

        txt_SDT = new JTextField();
        txt_SDT.setBounds(132, 20, 373, 25);
        txt_SDT.setEditable(false);
        txt_SDT.setBackground(Color.WHITE);
        txt_SDT.setFocusable(false); // <-- THÊM DÒNG NÀY
        pnl_ThongTinKhachHang.add(txt_SDT);
        txt_SDT.setColumns(10);

        txt_HoTen = new JTextField();
        txt_HoTen.setColumns(10);
        txt_HoTen.setBounds(132, 66, 373, 25);
        txt_HoTen.setEditable(false);
        txt_HoTen.setBackground(Color.WHITE);
        txt_HoTen.setFocusable(false);
        pnl_ThongTinKhachHang.add(txt_HoTen);

        txt_CCCD = new JTextField();
        txt_CCCD.setColumns(10);
        txt_CCCD.setBounds(132, 112, 373, 25);
        txt_CCCD.setEditable(false);
        txt_CCCD.setBackground(Color.WHITE);
        txt_CCCD.setFocusable(false);
        pnl_ThongTinKhachHang.add(txt_CCCD);

        JLabel lbl_NgaySinh = new JLabel("Ngày sinh:");
        lbl_NgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_NgaySinh.setBounds(10, 159, 112, 20);
        pnl_ThongTinKhachHang.add(lbl_NgaySinh);

        txt_NgaySinh = new JTextField();
        txt_NgaySinh.setColumns(10);
        txt_NgaySinh.setBounds(132, 158, 110, 25);
        txt_NgaySinh.setEditable(false);
        txt_NgaySinh.setBackground(Color.WHITE);
        txt_NgaySinh.setFocusable(false);
        pnl_ThongTinKhachHang.add(txt_NgaySinh);

        JLabel lbl_GioiTinh = new JLabel("Giới tính:");
        lbl_GioiTinh.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_GioiTinh.setBounds(291, 159, 92, 20);
        pnl_ThongTinKhachHang.add(lbl_GioiTinh);

        JLabel lbl_Email = new JLabel("Email:");
        lbl_Email.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_Email.setBounds(10, 205, 112, 20);
        pnl_ThongTinKhachHang.add(lbl_Email);

        txt_Email = new JTextField();
        txt_Email.setBounds(132, 204, 373, 25);
        txt_Email.setEditable(false);
        txt_Email.setBackground(Color.WHITE);
        txt_Email.setFocusable(false);
        pnl_ThongTinKhachHang.add(txt_Email);
        txt_Email.setColumns(10);

        JLabel lbl_HangKhachHang = new JLabel("Hạng khách hàng:");
        lbl_HangKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_HangKhachHang.setBounds(10, 251, 112, 20);
        pnl_ThongTinKhachHang.add(lbl_HangKhachHang);

        txt_HangKhachHang = new JTextField();
        txt_HangKhachHang.setColumns(10);
        txt_HangKhachHang.setEditable(false);
        txt_HangKhachHang.setBackground(Color.WHITE);
        txt_HangKhachHang.setFocusable(false);
        txt_HangKhachHang.setBounds(132, 250, 110, 25);
        pnl_ThongTinKhachHang.add(txt_HangKhachHang);

        JLabel lbl_DiemTichLuy = new JLabel("Điểm tích lũy:");
        lbl_DiemTichLuy.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_DiemTichLuy.setBounds(291, 251, 92, 20);
        pnl_ThongTinKhachHang.add(lbl_DiemTichLuy);

        txt_DiemTichLuy = new JTextField();
        txt_DiemTichLuy.setColumns(10);
        txt_DiemTichLuy.setEditable(false);
        txt_DiemTichLuy.setBackground(Color.WHITE);
        txt_DiemTichLuy.setFocusable(false);
        txt_DiemTichLuy.setBounds(393, 250, 112, 25);
        pnl_ThongTinKhachHang.add(txt_DiemTichLuy);
        
                rdbtn_NamNguoiO = new JRadioButton("Nam");
                rdbtn_NamNguoiO.setBounds(365, 159, 55, 20);
                pnl_ThongTinKhachHang.add(rdbtn_NamNguoiO);
                rdbtn_NamNguoiO.setBackground(new Color(255, 255, 255));
                rdbtn_NamNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                
                gioiTinhGroup.add(rdbtn_NamNguoiO);
                
                        // Nếu muốn một radio mặc định được chọn
                        rdbtn_NamNguoiO.setSelected(true);
                        
                                rdbtn_NuNguoiO = new JRadioButton("Nữ");
                                rdbtn_NuNguoiO.setBounds(450, 159, 55, 20);
                                pnl_ThongTinKhachHang.add(rdbtn_NuNguoiO);
                                rdbtn_NuNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                                rdbtn_NuNguoiO.setBackground(Color.WHITE);
                                gioiTinhGroup.add(rdbtn_NuNguoiO);

        JLabel lbl_PhuongThucThangToan = new JLabel("Phương thức thanh toán: ");
        lbl_PhuongThucThangToan.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_PhuongThucThangToan.setBounds(10, 504, 204, 20);
        getContentPane().add(lbl_PhuongThucThangToan);

        rdbtn_TienMat = new JRadioButton("Tiền mặt");
        rdbtn_TienMat.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_TienMat.setBackground(new Color(236, 247, 255));
        rdbtn_TienMat.setBounds(234, 504, 102, 25);
        getContentPane().add(rdbtn_TienMat);

        rdbtn_ChuyenKhoan = new JRadioButton("Chuyển khoản");
        rdbtn_ChuyenKhoan.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdbtn_ChuyenKhoan.setBackground(new Color(236, 247, 255));
        rdbtn_ChuyenKhoan.setBounds(371, 504, 128, 25);
        getContentPane().add(rdbtn_ChuyenKhoan);

        // Tạo nhóm và thêm radio button vào nhóm
        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(rdbtn_TienMat);
        paymentGroup.add(rdbtn_ChuyenKhoan);

        // Nếu muốn một radio mặc định được chọn
        rdbtn_TienMat.setSelected(true);


        JLabel lbl_TienKhachDua = new JLabel("Tiền khách đưa:");
        lbl_TienKhachDua.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_TienKhachDua.setBounds(10, 535, 128, 24);
        getContentPane().add(lbl_TienKhachDua);

        txt_TienKhachDua = new JTextField();
        txt_TienKhachDua.setBounds(204, 537, 331, 25);
        getContentPane().add(txt_TienKhachDua);
        txt_TienKhachDua.setColumns(10);

        JLabel lbl_DanhSachPhong = new JLabel("Danh sách phòng");
        lbl_DanhSachPhong.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_DanhSachPhong.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_DanhSachPhong.setBounds(670, 254, 323, 26);
        getContentPane().add(lbl_DanhSachPhong);

        JPanel pnl_DanhSachPhong = new JPanel();
        pnl_DanhSachPhong.setBackground(new Color(255, 255, 255));
        pnl_DanhSachPhong.setBounds(560, 286, 516, 115);
        pnl_DanhSachPhong.setLayout(null);
        getContentPane().add(pnl_DanhSachPhong);


        model_DanhSachPhong = new DefaultTableModel(new String[] {"Mã phòng","Loại phòng","SLTĐ","Giá","Tiền cọc"}, 0);
        table_DanhSachPhong = new JTable(model_DanhSachPhong);
        JScrollPane scrollPane = new JScrollPane(table_DanhSachPhong);
        scrollPane.setBackground(new Color(255, 255, 255));
        scrollPane.setBounds(10, 10, 496, 95);
        JTableHeader header = table_DanhSachPhong.getTableHeader();
        header.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        table_DanhSachPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnl_DanhSachPhong.add(scrollPane);

        JLabel lbl_TongTien = new JLabel("Tổng tiền");
        lbl_TongTien.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_TongTien.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_TongTien.setBounds(773, 412, 102, 26);
        getContentPane().add(lbl_TongTien);

        JPanel pnl_TongTien = new JPanel();
        pnl_TongTien.setBackground(new Color(255, 255, 255));
        pnl_TongTien.setBounds(560, 438, 516, 124);
        getContentPane().add(pnl_TongTien);
        pnl_TongTien.setLayout(null);

        lbl_PhuongThucThanhToanTrongPnlTongTien = new JLabel("Phương thức thanh toán:");
        lbl_PhuongThucThanhToanTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_PhuongThucThanhToanTrongPnlTongTien.setBounds(10, 10, 163, 18);
        pnl_TongTien.add(lbl_PhuongThucThanhToanTrongPnlTongTien);

        lbl_TongTienTrongPnlTongTien = new JLabel("Tổng tiền:");
        lbl_TongTienTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TongTienTrongPnlTongTien.setBounds(10, 38, 163, 18);
        pnl_TongTien.add(lbl_TongTienTrongPnlTongTien);

        lbl_TienNhanTuKhachTrongPnlTongTien = new JLabel("Tiền nhận từ khách:");
        lbl_TienNhanTuKhachTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienNhanTuKhachTrongPnlTongTien.setBounds(10, 66, 163, 18);
        pnl_TongTien.add(lbl_TienNhanTuKhachTrongPnlTongTien);

        lbl_TienTraLaiKhachTrongPnlTongTien = new JLabel("Tiền trả lại khách:");
        lbl_TienTraLaiKhachTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienTraLaiKhachTrongPnlTongTien.setBounds(10, 94, 163, 18);
        pnl_TongTien.add(lbl_TienTraLaiKhachTrongPnlTongTien);

        lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien = new JLabel("Tiền mặt");
        lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setBounds(386, 10, 120, 18);
        pnl_TongTien.add(lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien);

        lbl_TienCuaTongTienTrongPnlTongTien = new JLabel("0 VND");
        lbl_TienCuaTongTienTrongPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaTongTienTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaTongTienTrongPnlTongTien.setBounds(386, 38, 120, 18);
        pnl_TongTien.add(lbl_TienCuaTongTienTrongPnlTongTien);

        lbl_TienCuaTienNhanTuKhachTrongPnlTongTien = new JLabel("0 VND");
        lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setBounds(386, 66, 120, 18);
        pnl_TongTien.add(lbl_TienCuaTienNhanTuKhachTrongPnlTongTien);

        lbl_TienCuaTienTraLaiKhachTrongPnlTongTien = new JLabel("0 VND");
        lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setBounds(386, 94, 120, 18);
        pnl_TongTien.add(lbl_TienCuaTienTraLaiKhachTrongPnlTongTien);

        // Tạo nhóm và thêm radio button vào nhóm
        ButtonGroup gioiTinhGroup = new ButtonGroup();

        JLabel lblNewLabel = new JLabel("Danh sách người ở");
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblNewLabel.setBounds(751, 33, 155, 26);
        getContentPane().add(lblNewLabel);

        JPanel pnl_DanhSachNguoiO = new JPanel();
        pnl_DanhSachNguoiO.setBackground(new Color(255, 255, 255));
        pnl_DanhSachNguoiO.setBounds(560, 64, 516, 180);
        getContentPane().add(pnl_DanhSachNguoiO);

        model_DanhSachNguoiO = new DefaultTableModel(new String[] {"Tên người ở","Ngày sinh","Giới tính","SDT","CCCD"}, 0);
        table_DanhSachNguoiO = new JTable(model_DanhSachNguoiO);
        JScrollPane scrollPane_DanhSachNguoiO = new JScrollPane(table_DanhSachNguoiO);
        scrollPane_DanhSachNguoiO.setBackground(new Color(255, 255, 255));
        scrollPane_DanhSachNguoiO.setBounds(10, 10, 496, 160);
        JTableHeader header_DanhSachNguoiO = table_DanhSachNguoiO.getTableHeader();
        header_DanhSachNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        header_DanhSachNguoiO.setBackground(Color.WHITE);
        header_DanhSachNguoiO.setForeground(Color.BLACK);
        header_DanhSachNguoiO.setPreferredSize(new Dimension(header.getWidth(), 35));
        pnl_DanhSachNguoiO.setLayout(null);
        table_DanhSachNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnl_DanhSachNguoiO.add(scrollPane_DanhSachNguoiO);
        
        btn_inHoaDon = new JButton("In hóa đơn");
        btn_inHoaDon.setBounds(956, 572, 120, 30);
        getContentPane().add(btn_inHoaDon);
        
        btn_huyHoaDon = new JButton("Hủy hóa đơn");
        btn_huyHoaDon.setBounds(826, 572, 120, 30);
        getContentPane().add(btn_huyHoaDon);
        
        JLabel lbl_ThoiGianThue = new JLabel("Thời gian thuê");
        lbl_ThoiGianThue.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_ThoiGianThue.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lbl_ThoiGianThue.setBackground(Color.WHITE);
        lbl_ThoiGianThue.setBounds(194, 373, 147, 26);
        getContentPane().add(lbl_ThoiGianThue);
        
        JPanel pnl_ThoiGianThue = new JPanel();
        pnl_ThoiGianThue.setLayout(null);
        pnl_ThoiGianThue.setBackground(Color.WHITE);
        pnl_ThoiGianThue.setBounds(10, 402, 525, 80);
        getContentPane().add(pnl_ThoiGianThue);
        
        JLabel lbl_NgayBatDau = new JLabel("Ngày bắt đầu: ");
        lbl_NgayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_NgayBatDau.setBounds(68, 11, 100, 20);
        pnl_ThoiGianThue.add(lbl_NgayBatDau);
        
        JDateChooser ngayBatDau = new JDateChooser();
        ngayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayBatDau.setDateFormatString("dd/MM/yyyy");
        ngayBatDau.setBounds(198, 10, 250, 25);
        pnl_ThoiGianThue.add(ngayBatDau);
        
        JLabel lbl_TienKhachDuaTrongPnlTongTien = new JLabel("Ngày kết thúc: ");
        lbl_TienKhachDuaTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_TienKhachDuaTrongPnlTongTien.setBounds(68, 51, 100, 20);
        pnl_ThoiGianThue.add(lbl_TienKhachDuaTrongPnlTongTien);
        
        JDateChooser ngayKetThuc = new JDateChooser();
        ngayKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayKetThuc.setDateFormatString("dd/MM/yyyy");
        ngayKetThuc.setBounds(198, 50, 250, 25);
        pnl_ThoiGianThue.add(ngayKetThuc);
    
		
	}
}
