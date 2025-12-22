package view.dialogs;

import com.toedter.calendar.JDateChooser;
//import controller.FormThongTinThuePhongController;
//import entity.KhachHang;
//import entity.Phong;
//import entity.TaiKhoan;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PhieuThuePhongDialogOThueDatPhong extends JDialog {

        private static final long serialVersionUID = 1L;
        public JTextField txt_SDT;
        public JTextField txt_HoTen;
        public JTextField txt_CCCD;
        public JTextField txt_TienKhachDua;
        public JDateChooser ngayBatDau;
        public JDateChooser ngayKetThuc;
        public JDateChooser ngaySinhNguoiO;
        public JLabel lbl_PhuongThucThanhToanTrongPnlTongTien;
        public JLabel lbl_TongTienTrongPnlTongTien;
        public JLabel lbl_TienCocTrongPnlTongTien;
        public JLabel lbl_TienTraLaiKhachTrongPnlTongTien;
        public JLabel lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien;
        public JLabel lbl_TienCuaTongTienTrongPnlTongTien;
        public JLabel lbl_TienCuaTienNhanTuKhachTrongPnlTongTien;
        public JLabel lbl_TienCuaTienTraLaiKhachTrongPnlTongTien;
        public JLabel lbl_TienNhanTuKhachTrongPnlTongTien;
        public JLabel lbl_TienCuaTienCocTrongPnlTongTien;
        public JButton btn_XacNhan;
        public JButton btn_Huy;
        public JTable table_DanhSachPhong;
        public DefaultTableModel model_DanhSachPhong;
        public JTable table_DanhSachNguoiO;
        public DefaultTableModel model_DanhSachNguoiO;
        public JTextField txt_HoTenNguoiO;
        public JTextField txt_SDTNguoiO;
        public JTextField txt_CCCDNguoiO;
        public JRadioButton rdbtn_TienMat;
        public JRadioButton rdbtn_ChuyenKhoan;
        public JButton btn_ThemNguoiOVaoDanhSach;
        public JRadioButton rdbtn_NamNguoiO;
        public JRadioButton rdbtn_NuNguoiO;
//        public FormThongTinThuePhongController controller;
//        private TaiKhoan taiKhoanDangNhap;
        public JTextField txt_TienTraKhach;
        public JLabel lbl_KhuyenMaiDuocApDung;
        public JLabel lbl_TenKhuyenMai;
        public JLabel lbl_MaPhong;
        public JTextField txt_MaPhong;
        public JLabel lbl_TienTraKhach;
        public JLabel lbl_TienKhachDua;
        public boolean confirmed = false;

        public PhieuThuePhongDialogOThueDatPhong() {
                jbInit();
        }
        private void jbInit() {
                getContentPane().setBackground(new Color(236, 247, 255));
                setBounds(100, 100, 1106, 800);
                getContentPane().setLayout(null);
                setLocationRelativeTo(null);

                JLabel lblTenKhachSanMenu = new JLabel("ATTM");
                lblTenKhachSanMenu.setFont(new Font("Lucida Calligraphy", Font.BOLD, 24));
                lblTenKhachSanMenu.setHorizontalAlignment(SwingConstants.LEFT);
                lblTenKhachSanMenu.setForeground(new Color(10, 110, 189));
                lblTenKhachSanMenu.setBounds(10, 10, 90, 24);
                getContentPane().add(lblTenKhachSanMenu);

                JLabel lblTieuDeForm = new JLabel("Phiếu thuê phòng");
                lblTieuDeForm.setForeground(new Color(10, 110, 189));
                lblTieuDeForm.setFont(new Font("Segoe UI", Font.BOLD, 24));
                lblTieuDeForm.setBounds(430, 3, 248, 40);
                getContentPane().add(lblTieuDeForm);

                JLabel lblFromThongTinKhachHang = new JLabel("Thông tin khách hàng thuê phòng");
                lblFromThongTinKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                lblFromThongTinKhachHang.setBounds(10, 60, 302, 26);
                getContentPane().add(lblFromThongTinKhachHang);

                JPanel pnl_ThongTinKhachHang = new JPanel();
                // Tạo viền mờ màu xám nhạt với bo góc 15px
                pnl_ThongTinKhachHang.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), HIDE_ON_CLOSE)// Viền ngoài
                        , null
                ));
                pnl_ThongTinKhachHang.setBackground(new Color(255, 255, 255));
                pnl_ThongTinKhachHang.setBounds(10, 90, 525, 158);
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
                txt_SDT.setBackground(Color.WHITE);
                pnl_ThongTinKhachHang.add(txt_SDT);
                txt_SDT.setColumns(10);

                txt_HoTen = new JTextField();
                txt_HoTen.setColumns(10);
                txt_HoTen.setBounds(132, 69, 373, 25);
                txt_HoTen.setEditable(false);
                txt_HoTen.setBackground(Color.WHITE);
                txt_HoTen.setFocusable(false);
                pnl_ThongTinKhachHang.add(txt_HoTen);

                txt_CCCD = new JTextField();
                txt_CCCD.setColumns(10);
                txt_CCCD.setBounds(132, 119, 373, 25);
                txt_CCCD.setEditable(false);
                txt_CCCD.setBackground(Color.WHITE);
                txt_CCCD.setFocusable(false);
                pnl_ThongTinKhachHang.add(txt_CCCD);

                JLabel lbl_PhuongThucThangToan = new JLabel("Phương thức thanh toán: ");
                lbl_PhuongThucThangToan.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                lbl_PhuongThucThangToan.setBounds(550, 570, 155, 20);
                getContentPane().add(lbl_PhuongThucThangToan);

                rdbtn_TienMat = new JRadioButton("Tiền mặt");
                rdbtn_TienMat.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                rdbtn_TienMat.setBackground(new Color(236, 247, 255));
                rdbtn_TienMat.setBounds(750, 570, 102, 20);
                getContentPane().add(rdbtn_TienMat);

                rdbtn_ChuyenKhoan = new JRadioButton("Chuyển khoản");
                rdbtn_ChuyenKhoan.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                rdbtn_ChuyenKhoan.setBackground(new Color(236, 247, 255));
                rdbtn_ChuyenKhoan.setBounds(900, 570, 113, 20);
                getContentPane().add(rdbtn_ChuyenKhoan);

                // Tạo nhóm và thêm radio button vào nhóm
                ButtonGroup paymentGroup = new ButtonGroup();
                paymentGroup.add(rdbtn_TienMat);
                paymentGroup.add(rdbtn_ChuyenKhoan);

                // Nếu muốn một radio mặc định được chọn
                rdbtn_TienMat.setSelected(true);


                lbl_TienKhachDua = new JLabel("Tiền khách đưa:");
                lbl_TienKhachDua.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                lbl_TienKhachDua.setBounds(550, 620, 128, 24);
                getContentPane().add(lbl_TienKhachDua);

                txt_TienKhachDua = new JTextField();
                txt_TienKhachDua.setBounds(683, 620, 393, 25);
                getContentPane().add(txt_TienKhachDua);
                txt_TienKhachDua.setColumns(10);

                JLabel lbl_DanhSachPhong = new JLabel("Danh sách phòng");
                lbl_DanhSachPhong.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                lbl_DanhSachPhong.setBounds(10, 260, 147, 26);
                getContentPane().add(lbl_DanhSachPhong);

                JPanel pnl_DanhSachPhong = new JPanel();
                pnl_DanhSachPhong.setBackground(new Color(255, 255, 255));
                pnl_DanhSachPhong.setBounds(10, 290, 525, 110);
                pnl_DanhSachPhong.setLayout(null);
                getContentPane().add(pnl_DanhSachPhong);


                model_DanhSachPhong = new DefaultTableModel(new String[] {"Mã phòng","Loại phòng","SLTĐ","Giá","Tiền cọc"}, 0);
                table_DanhSachPhong = new JTable(model_DanhSachPhong);
                JScrollPane scrollPane = new JScrollPane(table_DanhSachPhong);
                scrollPane.setBackground(new Color(255, 255, 255));
                scrollPane.setBounds(10, 10, 505, 90);
                JTableHeader header = table_DanhSachPhong.getTableHeader();
                header.setFont(new Font("Times New Roman", Font.PLAIN, 12));
                header.setBackground(Color.WHITE);
                header.setForeground(Color.BLACK);
                header.setPreferredSize(new Dimension(header.getWidth(), 35));
                table_DanhSachPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                pnl_DanhSachPhong.add(scrollPane);

                JLabel lbl_ThoiGianThue = new JLabel("Thời gian thuê");
                lbl_ThoiGianThue.setBackground(new Color(255, 255, 255));
                lbl_ThoiGianThue.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                lbl_ThoiGianThue.setBounds(10, 410, 128, 26);
                getContentPane().add(lbl_ThoiGianThue);

                JPanel pnl_ThoiGianThue = new JPanel();
                pnl_ThoiGianThue.setBackground(new Color(255, 255, 255));
                pnl_ThoiGianThue.setBounds(10, 440, 525, 85);
                getContentPane().add(pnl_ThoiGianThue);
                pnl_ThoiGianThue.setLayout(null);

                JLabel lbl_NgayBatDau = new JLabel("Ngày bắt đầu: ");
                lbl_NgayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                lbl_NgayBatDau.setBounds(10, 10, 100, 20);
                pnl_ThoiGianThue.add(lbl_NgayBatDau);

                ngayBatDau = new JDateChooser();
                ngayBatDau.setDateFormatString("dd/MM/yyyy");
                ngayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                ngayBatDau.setBounds(120, 10, 395, 25);  // <-- SỬA 125 thành 10
                pnl_ThoiGianThue.add(ngayBatDau);

                JLabel lbl_NgayKetThuc = new JLabel("Ngày kết thúc: ");
                lbl_NgayKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                lbl_NgayKetThuc.setBounds(10, 50, 100, 20);
                pnl_ThoiGianThue.add(lbl_NgayKetThuc);

                ngayKetThuc = new JDateChooser();
                ngayKetThuc.setDateFormatString("dd/MM/yyyy");
                ngayKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                ngayKetThuc.setBounds(120, 50, 395, 25);
                pnl_ThoiGianThue.add(ngayKetThuc);

                JLabel lbl_TongTien = new JLabel("Tổng tiền");
                lbl_TongTien.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                lbl_TongTien.setBounds(10, 535, 102, 26);
                getContentPane().add(lbl_TongTien);

                JPanel pnl_TongTien = new JPanel();
                pnl_TongTien.setBackground(new Color(255, 255, 255));
                pnl_TongTien.setBounds(10, 568, 525, 180);
                getContentPane().add(pnl_TongTien);
                pnl_TongTien.setLayout(null);

                lbl_PhuongThucThanhToanTrongPnlTongTien = new JLabel("Phương thức thanh toán:");
                lbl_PhuongThucThanhToanTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                lbl_PhuongThucThanhToanTrongPnlTongTien.setBounds(10, 10, 163, 18);
                pnl_TongTien.add(lbl_PhuongThucThanhToanTrongPnlTongTien);

                lbl_TongTienTrongPnlTongTien = new JLabel("Tổng tiền trả:");
                lbl_TongTienTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                lbl_TongTienTrongPnlTongTien.setBounds(10, 38, 163, 18);
                pnl_TongTien.add(lbl_TongTienTrongPnlTongTien);

                lbl_TienCocTrongPnlTongTien = new JLabel("Tiền cọc:");
                lbl_TienCocTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                lbl_TienCocTrongPnlTongTien.setBounds(10, 66, 163, 18);
                pnl_TongTien.add(lbl_TienCocTrongPnlTongTien);

                lbl_TienNhanTuKhachTrongPnlTongTien = new JLabel("Tiền nhận từ khách:");
                lbl_TienNhanTuKhachTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                lbl_TienNhanTuKhachTrongPnlTongTien.setBounds(10, 122, 163, 18);
                pnl_TongTien.add(lbl_TienNhanTuKhachTrongPnlTongTien);

                lbl_TienTraLaiKhachTrongPnlTongTien = new JLabel("Tiền trả lại khách:");
                lbl_TienTraLaiKhachTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                lbl_TienTraLaiKhachTrongPnlTongTien.setBounds(10, 150, 163, 18);
                pnl_TongTien.add(lbl_TienTraLaiKhachTrongPnlTongTien);

                lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien = new JLabel("Tiền mặt");
                lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
                lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setBounds(395, 10, 120, 18);
                pnl_TongTien.add(lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien);

                lbl_TienCuaTongTienTrongPnlTongTien = new JLabel("0 VND");
                lbl_TienCuaTongTienTrongPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
                lbl_TienCuaTongTienTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                lbl_TienCuaTongTienTrongPnlTongTien.setBounds(395, 38, 120, 18);
                pnl_TongTien.add(lbl_TienCuaTongTienTrongPnlTongTien);

                lbl_TienCuaTienCocTrongPnlTongTien = new JLabel("0 VND");
                lbl_TienCuaTienCocTrongPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
                lbl_TienCuaTienCocTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                lbl_TienCuaTienCocTrongPnlTongTien.setBounds(395, 66, 120, 18);
                pnl_TongTien.add(lbl_TienCuaTienCocTrongPnlTongTien);

                lbl_TienCuaTienNhanTuKhachTrongPnlTongTien = new JLabel("0 VND");
                lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
                lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setBounds(395, 122, 120, 18);
                pnl_TongTien.add(lbl_TienCuaTienNhanTuKhachTrongPnlTongTien);

                lbl_TienCuaTienTraLaiKhachTrongPnlTongTien = new JLabel("0 VND");
                lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setHorizontalAlignment(SwingConstants.RIGHT);
                lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setBounds(395, 150, 120, 18);
                pnl_TongTien.add(lbl_TienCuaTienTraLaiKhachTrongPnlTongTien);

                lbl_KhuyenMaiDuocApDung = new JLabel("Khuyến mãi được áp dụng:");
                lbl_KhuyenMaiDuocApDung.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                lbl_KhuyenMaiDuocApDung.setBounds(10, 94, 163, 18);
                pnl_TongTien.add(lbl_KhuyenMaiDuocApDung);

                lbl_TenKhuyenMai = new JLabel("KM");
                lbl_TenKhuyenMai.setHorizontalAlignment(SwingConstants.RIGHT);
                lbl_TenKhuyenMai.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                lbl_TenKhuyenMai.setBounds(395, 94, 120, 18);
                pnl_TongTien.add(lbl_TenKhuyenMai);

                class RoundedButton extends JButton {
                        private int radius;
                        private Color originalBg;
                        private boolean isHovered = false;
                        private boolean isPressed = false;

                        public RoundedButton(String label, Color bg, Color fg, int radius) {
                                super(label);
                                this.radius = radius;
                                this.originalBg = bg;
                                setBackground(bg);
                                setForeground(fg);
                                setContentAreaFilled(false);
                                setFocusPainted(false);
                                setBorder(null);

                                // Add mouse listener for hover and press effects
                                addMouseListener(new java.awt.event.MouseAdapter() {
                                        @Override
                                        public void mouseEntered(java.awt.event.MouseEvent e) {
                                                isHovered = true;
                                                repaint();
                                        }

                                        @Override
                                        public void mouseExited(java.awt.event.MouseEvent e) {
                                                isHovered = false;
                                                repaint();
                                        }

                                        @Override
                                        public void mousePressed(java.awt.event.MouseEvent e) {
                                                isPressed = true;
                                                repaint();
                                        }

                                        @Override
                                        public void mouseReleased(java.awt.event.MouseEvent e) {
                                                isPressed = false;
                                                repaint();
                                        }
                                });
                        }

                        @Override
                        protected void paintComponent(Graphics g) {
                                Graphics2D g2 = (Graphics2D) g.create();
                                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                                // Determine color based on state
                                Color bgColor = originalBg;
                                if (isPressed) {
                                        bgColor = originalBg.darker();  // Darker on press
                                } else if (isHovered) {
                                        bgColor = originalBg.brighter();  // Brighter on hover
                                }

                                g2.setColor(bgColor);
                                g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
                                super.paintComponent(g2);
                                g2.dispose();
                        }
                }

                //Khởi tạo các nút với tên biến chính xác
                btn_XacNhan = new JButton("Xác nhận");
                btn_XacNhan.setFont(new Font("Times New Roman", Font.BOLD, 14));
                btn_XacNhan.setBounds(683, 718, 140, 30);
                getContentPane().add(btn_XacNhan);

                btn_Huy = new JButton("Hủy");
                btn_Huy.setFont(new Font("Times New Roman", Font.BOLD, 14));
                btn_Huy.setBounds(857, 718, 140, 30);
                getContentPane().add(btn_Huy);

//        btn_XacNhan = new RoundedButton("Xác nhận", new Color(76, 175, 80), Color.WHITE, 30);
//        btn_XacNhan.setFont(new Font("Times New Roman", Font.BOLD, 14));
//        btn_XacNhan.setBounds(430, 700, 120, 30);
//        getContentPane().add(btn_XacNhan);
//
//        btn_Huy = new RoundedButton("Hủy", new Color(244, 67, 54), Color.WHITE, 30);
//        btn_Huy.setFont(new Font("Times New Roman", Font.BOLD, 14));
//        btn_Huy.setBounds(612, 700, 120, 30);
//        getContentPane().add(btn_Huy);

                JLabel lblFromThongTinNguoiO = new JLabel("Thông tin người ở ");
                lblFromThongTinNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                lblFromThongTinNguoiO.setBounds(550, 60, 155, 26);
                getContentPane().add(lblFromThongTinNguoiO);

                JPanel pnl_ThongTinNguoiO = new JPanel();
                pnl_ThongTinNguoiO.setBackground(new Color(255, 255, 255));
                pnl_ThongTinNguoiO.setBounds(550, 90, 526, 206);
                getContentPane().add(pnl_ThongTinNguoiO);
                pnl_ThongTinNguoiO.setLayout(null);

                JLabel lbl_HoTenNguoiO = new JLabel("Họ Tên:");
                lbl_HoTenNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                lbl_HoTenNguoiO.setBackground(new Color(255, 255, 255));
                lbl_HoTenNguoiO.setBounds(10, 50, 80, 20);
                pnl_ThongTinNguoiO.add(lbl_HoTenNguoiO);

                txt_HoTenNguoiO = new JTextField();
                txt_HoTenNguoiO.setBounds(100, 49, 416, 25);
                pnl_ThongTinNguoiO.add(txt_HoTenNguoiO);
                txt_HoTenNguoiO.setColumns(10);

                JLabel lbl_SDTNguoiO = new JLabel("SDT:");
                lbl_SDTNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                lbl_SDTNguoiO.setBounds(10, 90, 80, 20);
                pnl_ThongTinNguoiO.add(lbl_SDTNguoiO);

                txt_SDTNguoiO = new JTextField();
                txt_SDTNguoiO.setColumns(10);
                txt_SDTNguoiO.setBounds(100, 89, 140, 25);
                pnl_ThongTinNguoiO.add(txt_SDTNguoiO);

                JLabel lbl_CCCDNguoiO = new JLabel("CCCD:");
                lbl_CCCDNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                lbl_CCCDNguoiO.setBounds(286, 90, 80, 20);
                pnl_ThongTinNguoiO.add(lbl_CCCDNguoiO);

                txt_CCCDNguoiO = new JTextField();
                txt_CCCDNguoiO.setColumns(10);
                txt_CCCDNguoiO.setBounds(376, 89, 140, 25);
                pnl_ThongTinNguoiO.add(txt_CCCDNguoiO);

                JLabel lbl_NgaySinhNguoiO = new JLabel("Ngày sinh:");
                lbl_NgaySinhNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                lbl_NgaySinhNguoiO.setBounds(10, 130, 80, 20);
                pnl_ThongTinNguoiO.add(lbl_NgaySinhNguoiO);

//        textField_1 = new JTextField();
//        textField_1.setColumns(10);
//        textField_1.setBounds(100, 89, 140, 25);
//        pnl_ThongTinNguoiO.add(textField_1);

                ngaySinhNguoiO = new JDateChooser();
                ngaySinhNguoiO.setDateFormatString("dd/MM/yyyy");
                ngaySinhNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                ngaySinhNguoiO.setBounds(100, 129, 140, 25);
                pnl_ThongTinNguoiO.add(ngaySinhNguoiO);

                JLabel lbl_GioiTinhNguoiO = new JLabel("Giới tính:");
                lbl_GioiTinhNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                lbl_GioiTinhNguoiO.setBounds(286, 130, 80, 20);
                pnl_ThongTinNguoiO.add(lbl_GioiTinhNguoiO);

                rdbtn_NamNguoiO = new JRadioButton("Nam");
                rdbtn_NamNguoiO.setBackground(new Color(255, 255, 255));
                rdbtn_NamNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                rdbtn_NamNguoiO.setBounds(376, 130, 55, 20);
                pnl_ThongTinNguoiO.add(rdbtn_NamNguoiO);

                rdbtn_NuNguoiO = new JRadioButton("Nữ");
                rdbtn_NuNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                rdbtn_NuNguoiO.setBackground(Color.WHITE);
                rdbtn_NuNguoiO.setBounds(461, 130, 55, 20);
                pnl_ThongTinNguoiO.add(rdbtn_NuNguoiO);

                // Tạo nhóm và thêm radio button vào nhóm
                ButtonGroup gioiTinhGroup = new ButtonGroup();
                gioiTinhGroup.add(rdbtn_NamNguoiO);
                gioiTinhGroup.add(rdbtn_NuNguoiO);

                // Nếu muốn một radio mặc định được chọn
                rdbtn_NamNguoiO.setSelected(true);

                btn_ThemNguoiOVaoDanhSach = new JButton("Thêm");
                btn_ThemNguoiOVaoDanhSach.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                btn_ThemNguoiOVaoDanhSach.setBounds(416, 170, 100, 26);
                pnl_ThongTinNguoiO.add(btn_ThemNguoiOVaoDanhSach);

                lbl_MaPhong = new JLabel("Mã phòng:");
                lbl_MaPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                lbl_MaPhong.setBackground(Color.WHITE);
                lbl_MaPhong.setBounds(10, 10, 80, 20);
                pnl_ThongTinNguoiO.add(lbl_MaPhong);

                txt_MaPhong = new JTextField();
                txt_MaPhong.setEnabled(false);
                txt_MaPhong.setEditable(false);
                txt_MaPhong.setColumns(10);
                txt_MaPhong.setBounds(100, 9, 416, 25);
                pnl_ThongTinNguoiO.add(txt_MaPhong);

                JLabel lbl_DSNO = new JLabel("Danh sách người ở");
                lbl_DSNO.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                lbl_DSNO.setBounds(550, 309, 155, 26);
                getContentPane().add(lbl_DSNO);

                JPanel pnl_DanhSachNguoiO = new JPanel();
                pnl_DanhSachNguoiO.setBackground(new Color(255, 255, 255));
                pnl_DanhSachNguoiO.setBounds(550, 345, 526, 180);
                getContentPane().add(pnl_DanhSachNguoiO);

                model_DanhSachNguoiO = new DefaultTableModel(new String[] {"Tên người ở","Ngày sinh","Giới tính","SDT","CCCD"}, 0);
                table_DanhSachNguoiO = new JTable(model_DanhSachNguoiO);
                JScrollPane scrollPane_DanhSachNguoiO = new JScrollPane(table_DanhSachNguoiO);
                scrollPane_DanhSachNguoiO.setBackground(new Color(255, 255, 255));
                scrollPane_DanhSachNguoiO.setBounds(10, 10, 506, 160);
                JTableHeader header_DanhSachNguoiO = table_DanhSachNguoiO.getTableHeader();
                header_DanhSachNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 12));
                header_DanhSachNguoiO.setBackground(Color.WHITE);
                header_DanhSachNguoiO.setForeground(Color.BLACK);
                header_DanhSachNguoiO.setPreferredSize(new Dimension(header.getWidth(), 35));
                pnl_DanhSachNguoiO.setLayout(null);
                table_DanhSachNguoiO.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                pnl_DanhSachNguoiO.add(scrollPane_DanhSachNguoiO);

                lbl_TienTraKhach = new JLabel("Tiền trả khách:");
                lbl_TienTraKhach.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                lbl_TienTraKhach.setBounds(550, 660, 128, 24);
                getContentPane().add(lbl_TienTraKhach);

                txt_TienTraKhach = new JTextField();
                txt_TienTraKhach.setColumns(10);
                txt_TienTraKhach.setBounds(683, 660, 393, 25);
                getContentPane().add(txt_TienTraKhach);
        }

//        // Thêm phương thức setThongTin
//        public void setThongTin(KhachHang khachHang, ArrayList<Phong> danhSachPhong, String loaiThue) {
//                controller.hienThiThongTin(khachHang, danhSachPhong);
//        }
//
//        private void hienThiThongTinKhachHang(KhachHang khachHang) {
//                txt_SDT.setText(khachHang.getSdt());
//                txt_HoTen.setText(khachHang.getTenKH());
//                txt_CCCD.setText(khachHang.getSoCCCD());
//                txt_Email.setText(khachHang.getEmail());
//
//                // Hiển thị ngày sinh
//                if (khachHang.getNgaySinh() != null) {
//                        txt_NgaySinh.setText(khachHang.getNgaySinh().toString());
//                }
//
//                // Hiển thị giới tính
//                txt_GioiTinh.setText(khachHang.isGioiTinh() ? "Nam" : "Nữ");
//
//                // Hiển thị hạng khách hàng và điểm tích lũy
//                txt_HangKhachHang.setText(khachHang.getHangKH().toString());
//                txt_DiemTichLuy.setText(String.valueOf(khachHang.getDiemTichLuy()));
//        }
//
//        public void setTaiKhoanDangNhap(TaiKhoan taiKhoan) {
//                this.taiKhoanDangNhap = taiKhoan;
//        }
//
//        public TaiKhoan getTaiKhoanDangNhap() {
//                return taiKhoanDangNhap;
//        }
// Thêm getter cho trạng thái xác nhận
        public boolean isConfirmed() {
                return confirmed;
        }
}
