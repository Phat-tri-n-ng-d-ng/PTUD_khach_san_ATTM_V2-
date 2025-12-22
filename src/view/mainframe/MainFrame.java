package view.mainframe;

import controllers.MainController;
import controllers.NhanVienController;
import controllers.QuanLyPhien;
import entitys.NhanVien;
import entitys.TaiKhoan;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;

public class MainFrame extends JFrame {
    private JLayeredPane layeredPane;
    private JPanel pnlChuaNoiDung;
    private JPanel pnlMenu = new JPanel();
    private NutBoGoc nutDangChon = null;
    private JPanel pnlNoiDung;
    private MainController mainController;
    private TaiKhoan taiKhoanDangNhap;
    private JLabel lblTenNguoiDung;
    private JLabel lblChucVu;
    private JLabel lblAnhDaiDien;

    // Thêm biến cho dropdown thống kê
    private NutBoGoc btnThongKe;
    private JPanel pnlSubMenuThongKe;
    private boolean isSubMenuThongKeVisible = false;

    // Thêm các biến để lưu các component cần di chuyển
    private JPanel duongKe;
    private NutBoGoc btnTroGiup;
    private NutBoGoc btnDangXuat;

    // Thêm các biến cho nút con
    private NutBoGoc btnDoanhThu;
    private NutBoGoc btnTyLePhong;

    public MainFrame() {
        khoiTaoGiaoDien();
        mainController = new MainController(this);
        capNhatThongTinNguoiDung();
    }

    private void khoiTaoGiaoDien() {
        setTitle("Quản lý khách sạn-ATTM");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pnlChuaNoiDung = new JPanel();
        pnlChuaNoiDung.setBackground(new Color(191, 227, 255));
        setContentPane(pnlChuaNoiDung);
        pnlChuaNoiDung.setLayout(null);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        // Sidebar
        pnlMenu.setBackground(Color.WHITE);
        pnlMenu.setBounds(0, 0, 220, screenHeight);
        pnlMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(226, 232, 240)));
        pnlChuaNoiDung.add(pnlMenu);
        pnlMenu.setLayout(null);

        JLabel lblTenKhachSanMenu = new JLabel("ATTM");
        lblTenKhachSanMenu.setHorizontalAlignment(SwingConstants.CENTER);
        lblTenKhachSanMenu.setForeground(new Color(67, 97, 238));
        lblTenKhachSanMenu.setFont(new Font("Lucida Calligraphy", Font.BOLD, 28));
        lblTenKhachSanMenu.setBounds(10, 13, 200, 40);
        pnlMenu.add(lblTenKhachSanMenu);

        // Phần tài khoản
        JPanel pnlTaiKhoan = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(new Color(226, 232, 240));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
            }
        };
        pnlTaiKhoan.setBackground(Color.WHITE);
        pnlTaiKhoan.setBounds(15, 60, 190, 90);
        pnlTaiKhoan.setLayout(null);
        pnlMenu.add(pnlTaiKhoan);

        lblAnhDaiDien = new JLabel();
        lblAnhDaiDien.setBounds(15, 15, 50, 50);
        FontIcon userIcon = FontIcon.of(FontAwesomeSolid.USER_ALT, 46, new Color(67, 97, 238));
        lblAnhDaiDien.setIcon(userIcon);
        lblAnhDaiDien.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lblAnhDaiDien.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblAnhDaiDien.setIcon(FontIcon.of(FontAwesomeSolid.USER_ALT, 46, new Color(30, 70, 160)));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lblAnhDaiDien.setIcon(FontIcon.of(FontAwesomeSolid.USER_ALT, 46, new Color(67, 97, 238)));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                hienThiThongTinNguoiDung();
            }
        });
        pnlTaiKhoan.add(lblAnhDaiDien);

        lblTenNguoiDung = new JLabel("Tên người dùng");
        lblTenNguoiDung.setBounds(75, 20, 100, 20);
        lblTenNguoiDung.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pnlTaiKhoan.add(lblTenNguoiDung);

        lblChucVu = new JLabel("Chức vụ");
        lblChucVu.setBounds(75, 45, 100, 20);
        lblChucVu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblChucVu.setForeground(new Color(100, 116, 139));
        pnlTaiKhoan.add(lblChucVu);

        int startY = 170;
        int buttonHeight = 40;
        int buttonSpacing = 8;

        // Các nút menu
        NutBoGoc btnTrangChu = new NutBoGoc("Màn hình chính");
        btnTrangChu.setIkonliIcon(FontAwesomeSolid.HOME);
        btnTrangChu.setBounds(10, startY, 200, buttonHeight);
        pnlMenu.add(btnTrangChu);

        NutBoGoc btnDatThuePhong = new NutBoGoc("Đặt/Thuê phòng");
        btnDatThuePhong.setIkonliIcon(FontAwesomeSolid.CALENDAR_CHECK);
        btnDatThuePhong.setBounds(10, startY + (buttonHeight + buttonSpacing), 200, buttonHeight);
        pnlMenu.add(btnDatThuePhong);

        NutBoGoc btnPhong = new NutBoGoc("Phòng");
        btnPhong.setIkonliIcon(FontAwesomeSolid.BED);
        btnPhong.setBounds(10, startY + (buttonHeight + buttonSpacing) * 2, 180, buttonHeight);
        pnlMenu.add(btnPhong);

        NutBoGoc btnLoaiPhong = new NutBoGoc("Loại phòng");
        btnLoaiPhong.setIkonliIcon(FontAwesomeSolid.BUILDING);
        btnLoaiPhong.setBounds(10, startY + (buttonHeight + buttonSpacing) * 3, 180, buttonHeight);
        pnlMenu.add(btnLoaiPhong);

        NutBoGoc btnKhuyenMai = new NutBoGoc("Khuyến mãi");
        btnKhuyenMai.setIkonliIcon(FontAwesomeSolid.TAG);
        btnKhuyenMai.setBounds(10, startY + (buttonHeight + buttonSpacing) * 4, 180, buttonHeight);
        pnlMenu.add(btnKhuyenMai);

        NutBoGoc btnHoaDon = new NutBoGoc("Hóa đơn");
        btnHoaDon.setIkonliIcon(FontAwesomeSolid.RECEIPT);
        btnHoaDon.setBounds(10, startY + (buttonHeight + buttonSpacing) * 5, 180, buttonHeight);
        pnlMenu.add(btnHoaDon);

        NutBoGoc btnKhachHang = new NutBoGoc("Khách hàng");
        btnKhachHang.setIkonliIcon(FontAwesomeSolid.USERS);
        btnKhachHang.setBounds(10, startY + (buttonHeight + buttonSpacing) * 6, 180, buttonHeight);
        pnlMenu.add(btnKhachHang);

        NutBoGoc btnNhanVien = new NutBoGoc("Nhân viên");
        btnNhanVien.setIkonliIcon(FontAwesomeSolid.USER_TIE);
        btnNhanVien.setBounds(10, startY + (buttonHeight + buttonSpacing) * 7, 180, buttonHeight);
        pnlMenu.add(btnNhanVien);

        // THÊM: Nút thống kê với dropdown - SỬA MỚI
        btnThongKe = new NutBoGoc("Thống kê");
        btnThongKe.setIkonliIcon(FontAwesomeSolid.CHART_BAR);
        btnThongKe.setBounds(10, startY + (buttonHeight + buttonSpacing) * 8, 180, buttonHeight);

        // Thêm icon mũi tên vào nút thống kê
        FontIcon arrowIcon = FontIcon.of(FontAwesomeSolid.CHEVRON_RIGHT, 12, new Color(100, 116, 139));
        btnThongKe.setIcon(arrowIcon);
        btnThongKe.setHorizontalTextPosition(SwingConstants.LEFT);
        btnThongKe.setIconTextGap(15);

        pnlMenu.add(btnThongKe);

        // THÊM: Tạo panel cho submenu thống kê với màu nền giống panel chính
        pnlSubMenuThongKe = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(248, 250, 252)); // Màu nền giống panel chính #F8FAFC
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(new Color(226, 232, 240, 100));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
            }
        };
        pnlSubMenuThongKe.setLayout(null);
        pnlSubMenuThongKe.setBounds(15, startY + (buttonHeight + buttonSpacing) * 8 + buttonHeight, 170, 0);
        pnlSubMenuThongKe.setOpaque(false);
        pnlSubMenuThongKe.setVisible(false);
        pnlMenu.add(pnlSubMenuThongKe);

        // THÊM: Tạo các nút con của thống kê bên trong pnlSubMenuThongKe
        btnDoanhThu = new NutBoGoc("Doanh thu") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (duocChon) {
                    g2.setColor(new Color(67, 97, 238, 30));
                } else if (getModel().isPressed()) {
                    g2.setColor(new Color(219, 234, 254, 150));
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(239, 246, 255, 150));
                } else {
                    g2.setColor(new Color(248, 250, 252)); // Màu nền giống panel chính
                }

                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnDoanhThu.setIkonliIcon(FontAwesomeSolid.MONEY_BILL_WAVE);
        btnDoanhThu.setBounds(0, 5, 170, 35);
        btnDoanhThu.setForeground(new Color(51, 65, 85));
        btnDoanhThu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnDoanhThu.setHorizontalAlignment(SwingConstants.LEFT);
        btnDoanhThu.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 10));
        btnDoanhThu.setVisible(false);
        btnDoanhThu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainController.showThongKeDoanhThu();
                anSubMenuThongKe();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnDoanhThu.setForeground(new Color(67, 97, 238));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnDoanhThu.setForeground(new Color(51, 65, 85));
            }
        });
        pnlSubMenuThongKe.add(btnDoanhThu);

        btnTyLePhong = new NutBoGoc("Tỷ lệ phòng") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (duocChon) {
                    g2.setColor(new Color(67, 97, 238, 30));
                } else if (getModel().isPressed()) {
                    g2.setColor(new Color(219, 234, 254, 150));
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(239, 246, 255, 150));
                } else {
                    g2.setColor(new Color(248, 250, 252)); // Màu nền giống panel chính
                }

                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnTyLePhong.setIkonliIcon(FontAwesomeSolid.CHART_PIE);
        btnTyLePhong.setBounds(0, 45, 170, 35);
        btnTyLePhong.setForeground(new Color(51, 65, 85));
        btnTyLePhong.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnTyLePhong.setHorizontalAlignment(SwingConstants.LEFT);
        btnTyLePhong.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 10));
        btnTyLePhong.setVisible(false);
        btnTyLePhong.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainController.showThongKeTyLePhong();
                anSubMenuThongKe();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnTyLePhong.setForeground(new Color(67, 97, 238));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnTyLePhong.setForeground(new Color(51, 65, 85));
            }
        });
        pnlSubMenuThongKe.add(btnTyLePhong);

        int separatorY = startY + (buttonHeight + buttonSpacing) * 9 + 10;

        duongKe = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(0x003366),
                        width / 2f, 0, new Color(0xBFE3FF),
                        true
                );
                g2d.setPaint(gp);

                g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.drawLine(10, height / 2, width - 10, height / 2);

                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.drawLine(10, height / 2 + 1, width - 10, height / 2 + 1);
            }
        };
        duongKe.setOpaque(false);
        duongKe.setBounds(0, separatorY, 210, 4);
        pnlMenu.add(duongKe);

        btnTroGiup = new NutBoGoc("Trợ giúp");
        btnTroGiup.setIkonliIcon(FontAwesomeSolid.QUESTION_CIRCLE);
        btnTroGiup.setBounds(10, separatorY + 10, 180, buttonHeight);
        pnlMenu.add(btnTroGiup);

        // THÊM: Nút đăng xuất
        btnDangXuat = new NutBoGoc("Đăng xuất");
        btnDangXuat.setIkonliIcon(FontAwesomeSolid.SIGN_OUT_ALT);
        btnDangXuat.setBounds(10, separatorY + 10 + buttonHeight + buttonSpacing, 180, buttonHeight);
        pnlMenu.add(btnDangXuat);

        // Content panel
        pnlNoiDung = new JPanel();
        pnlNoiDung.setBackground(new Color(248, 250, 252));
        pnlNoiDung.setBounds(220, 0, screenWidth - 220, screenHeight);
        pnlChuaNoiDung.add(pnlNoiDung);
        pnlNoiDung.setLayout(new BorderLayout());

        pnlChuaNoiDung.setComponentZOrder(pnlMenu, 0);
        pnlChuaNoiDung.setComponentZOrder(pnlNoiDung, 1);

        themSuKienChoNut(btnTrangChu, "TrangChu");
        themSuKienChoNut(btnDatThuePhong, "ThueDatPhong");
        themSuKienChoNut(btnPhong, "Phong");
        themSuKienChoNut(btnLoaiPhong, "LoaiPhong");
        themSuKienChoNut(btnKhuyenMai, "KhuyenMai");
        themSuKienChoNut(btnHoaDon, "HoaDon");
        themSuKienChoNut(btnKhachHang, "KhachHang");
        themSuKienChoNut(btnNhanVien, "NhanVien");

        // THÊM: Sự kiện cho nút thống kê (mở/đóng dropdown)
        btnThongKe.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isSubMenuThongKeVisible) {
                    anSubMenuThongKe();
                } else {
                    hienSubMenuThongKe();
                }
            }
        });

        themSuKienChoNut(btnTroGiup, "TroGiup");
        themSuKienChoNut(btnDangXuat, "DangXuat");

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                mainController.onWindowResized();
            }
        });
    }

    // THÊM: Phương thức hiển thị dropdown thống kê - SỬA MỚI
    private void hienSubMenuThongKe() {
        isSubMenuThongKeVisible = true;

        // Hiển thị panel submenu và mở rộng
        pnlSubMenuThongKe.setVisible(true);
        pnlSubMenuThongKe.setSize(170, 85);

        // Hiển thị các nút con
        btnDoanhThu.setVisible(true);
        btnTyLePhong.setVisible(true);

        // Xoay icon mũi tên xuống
        FontIcon arrowDown = FontIcon.of(FontAwesomeSolid.CHEVRON_DOWN, 12, new Color(100, 116, 139));
        btnThongKe.setIcon(arrowDown);

        // Di chuyển các component phía dưới xuống
        int subMenuHeight = 85;

        duongKe.setLocation(duongKe.getX(), duongKe.getY() + subMenuHeight);
        btnTroGiup.setLocation(btnTroGiup.getX(), btnTroGiup.getY() + subMenuHeight);
        btnDangXuat.setLocation(btnDangXuat.getX(), btnDangXuat.getY() + subMenuHeight);

        // Đặt nút thống kê ở trạng thái được chọn
        datNutDuocChon(btnThongKe);

        pnlMenu.revalidate();
        pnlMenu.repaint();
    }

    // THÊM: Phương thức ẩn dropdown thống kê - SỬA MỚI
    private void anSubMenuThongKe() {
        isSubMenuThongKeVisible = false;

        // Thu nhỏ và ẩn panel submenu
        pnlSubMenuThongKe.setVisible(false);
        pnlSubMenuThongKe.setSize(170, 0);

        // Ẩn các nút con
        btnDoanhThu.setVisible(false);
        btnTyLePhong.setVisible(false);

        // Xoay icon mũi tên về bên phải
        FontIcon arrowRight = FontIcon.of(FontAwesomeSolid.CHEVRON_RIGHT, 12, new Color(100, 116, 139));
        btnThongKe.setIcon(arrowRight);

        // Di chuyển các component phía dưới lên
        int subMenuHeight = 85;

        duongKe.setLocation(duongKe.getX(), duongKe.getY() - subMenuHeight);
        btnTroGiup.setLocation(btnTroGiup.getX(), btnTroGiup.getY() - subMenuHeight);
        btnDangXuat.setLocation(btnDangXuat.getX(), btnDangXuat.getY() - subMenuHeight);

        pnlMenu.revalidate();
        pnlMenu.repaint();
    }

    public void setTaiKhoanDangNhap(TaiKhoan taiKhoan) {
        this.taiKhoanDangNhap = taiKhoan;
        capNhatThongTinNguoiDung();
    }

    private void capNhatThongTinNguoiDung() {
        QuanLyPhien quanLyPhien = QuanLyPhien.getInstance();
        NhanVien nhanVien = quanLyPhien.getNhanVienDangNhap();

        if (nhanVien != null) {
            lblTenNguoiDung.setText(nhanVien.getTenNV());
            String chucVuStr = NhanVienController.getChucVuHienThi(nhanVien.getChucVu());
            lblChucVu.setText(chucVuStr);
        }
    }

    // THÊM: Phương thức hiển thị thông tin chi tiết người dùng
    private void hienThiThongTinNguoiDung() {
        QuanLyPhien quanLyPhien = QuanLyPhien.getInstance();
        NhanVien nhanVien = quanLyPhien.getNhanVienDangNhap();
        TaiKhoan taiKhoan = quanLyPhien.getTaiKhoanDangNhap();

        if (nhanVien != null && taiKhoan != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            StringBuilder message = new StringBuilder();
            message.append("<html><body style='font-family: Segoe UI; font-size: 12pt;'>");
            message.append("<div style='text-align: center; margin-bottom: 15px;'>");
            message.append("<b style='font-size: 14pt; color: #4361EE;'>THÔNG TIN TÀI KHOẢN</b><br>");
            message.append("<hr style='border: 1px solid #4361EE; width: 80%;'>");
            message.append("</div>");

            message.append("<table cellpadding='5' style='margin: 0 auto;'>");
            message.append("<tr><td width='120'><b>Mã nhân viên:</b></td><td>" + nhanVien.getMaNV() + "</td></tr>");
            message.append("<tr><td><b>Họ tên:</b></td><td>" + nhanVien.getTenNV() + "</td></tr>");

            if (nhanVien.getNgaySinh() != null) {
                message.append("<tr><td><b>Ngày sinh:</b></td><td>" +
                        nhanVien.getNgaySinh().format(formatter) + "</td></tr>");
            }

            message.append("<tr><td><b>Giới tính:</b></td><td>" +
                    (nhanVien.isGioiTinh() ? "Nam" : "Nữ") + "</td></tr>");

            if (nhanVien.getSdt() != null && !nhanVien.getSdt().isEmpty()) {
                message.append("<tr><td><b>Số điện thoại:</b></td><td>" + nhanVien.getSdt() + "</td></tr>");
            }

            if (nhanVien.getEmail() != null && !nhanVien.getEmail().isEmpty()) {
                message.append("<tr><td><b>Email:</b></td><td>" + nhanVien.getEmail() + "</td></tr>");
            }

            String chucVuStr = NhanVienController.getChucVuHienThi(nhanVien.getChucVu());
            message.append("<tr><td><b>Chức vụ:</b></td><td>" + chucVuStr + "</td></tr>");
            message.append("<tr><td><b>Tên đăng nhập:</b></td><td>" + taiKhoan.getTenDangNhap() + "</td></tr>");
            message.append("<tr><td><b>Vai trò:</b></td><td>" +
                    (taiKhoan.getVaiTro() != null ? taiKhoan.getVaiTro().toString() : "Nhân viên") + "</td></tr>");

            message.append("</table>");
            message.append("<br><div style='text-align: center; font-style: italic; color: #666;'>");
            message.append("Khách sạn ATTM - Hệ thống quản lý</div>");
            message.append("</body></html>");

            JOptionPane.showMessageDialog(this, message.toString(),
                    "Thông tin tài khoản", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void themSuKienChoNut(NutBoGoc nut, String action) {
        nut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Đóng dropdown thống kê nếu đang mở
                if (isSubMenuThongKeVisible && !nut.equals(btnThongKe)) {
                    anSubMenuThongKe();
                }

                datNutDuocChon(nut);
                xuLyChonMenu(action);
            }
        });
    }

    public void chonNutMacDinh() {
        for (Component comp : pnlMenu.getComponents()) {
            if (comp instanceof NutBoGoc) {
                NutBoGoc nut = (NutBoGoc) comp;
                if (nut.getText().equals("Màn hình chính")) {
                    datNutDuocChon(nut);
                    break;
                }
            }
        }
    }

    private void xuLyChonMenu(String action) {
        switch (action) {
            case "TrangChu":
                mainController.showMangHinhChinh();
                break;
            case "ThueDatPhong":
                mainController.showThue_Dat_Phong_Panel();
                break;
            case "Phong":
                mainController.showPhong_Panel();
                break;
            case "LoaiPhong":
                mainController.showLoai_Phong_Panel();
                break;
            case "KhuyenMai":
                mainController.showKhuyen_Mai_Panel();
                break;
            case "HoaDon":
                mainController.showHoa_Don_Panel();
                break;
            case "KhachHang":
                mainController.showKhach_Hang_Panel();
                break;
            case "NhanVien":
                mainController.showNhan_Vien_Panel();
                break;
            case "TroGiup":
                mainController.showTroGiup();
                break;
            case "DangXuat":
                xuLyDangXuat();
                break;
        }
    }

    // THÊM: Phương thức xử lý đăng xuất
    private void xuLyDangXuat() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn đăng xuất khỏi hệ thống?",
                "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            // Xóa phiên đăng nhập
            QuanLyPhien.getInstance().xoaPhien();

            // Đóng cửa sổ hiện tại
            this.dispose();

            // Mở lại cửa sổ đăng nhập
            DangNhapFrame dangNhapFrame = new DangNhapFrame();
            dangNhapFrame.setVisible(true);
        }
    }

    private void datNutDuocChon(NutBoGoc nutMoi) {
        if (nutDangChon != null) {
            nutDangChon.setTrangThaiBinhThuong();
        }
        nutDangChon = nutMoi;
        if (nutDangChon != null) {
            nutDangChon.setTrangThaiDuocChon();
        }
    }

    public JPanel getPnlNoiDung() {
        return pnlNoiDung;
    }

    class NutBoGoc extends JButton {
        private Color mauNenHover = new Color(239, 246, 255);
        private Color mauNenNhan = new Color(219, 234, 254);
        private Color mauNenDuocChon = new Color(67, 97, 238);
        private Color mauChuDuocChon = Color.WHITE;
        private Color mauChuHover = new Color(67, 97, 238);
        private Color mauChuThuong = new Color(51, 65, 85);

        // Thêm biến cho màu nền thường
        private Color mauNenThuong = Color.WHITE;

        boolean duocChon = false;
        private FontIcon icon;

        private final int ICON_SIZE = 18;
        private final int ICON_MARGIN_LEFT = 20;
        private final int TEXT_MARGIN_LEFT = 55;

        public NutBoGoc(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            setHorizontalAlignment(SwingConstants.LEFT);
            setBorder(BorderFactory.createEmptyBorder(10, ICON_MARGIN_LEFT, 10, 10));
            setForeground(mauChuThuong);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    capNhatMauIcon();
                    if (!duocChon) {
                        setForeground(mauChuHover);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    capNhatMauIcon();
                    if (!duocChon) {
                        setForeground(mauChuThuong);
                    }
                }
            });
        }

        public void setIkonliIcon(FontAwesomeSolid iconType) {
            this.icon = FontIcon.of(iconType, ICON_SIZE, mauChuThuong);
            setIcon(this.icon);
            capNhatMauIcon();
        }

        private void capNhatMauIcon() {
            if (icon != null) {
                Color c = duocChon ? mauChuDuocChon : (getModel().isRollover() ? mauChuHover : mauChuThuong);
                icon.setIconColor(c);
                repaint();
            }
        }

        public void setTrangThaiDuocChon() {
            duocChon = true;
            setForeground(mauChuDuocChon);
            capNhatMauIcon();
            repaint();
        }

        public void setTrangThaiBinhThuong() {
            duocChon = false;
            setForeground(mauChuThuong);
            capNhatMauIcon();
            repaint();
        }

        // Thêm phương thức setter cho màu nền thường
        public void setMauNenThuong(Color mau) {
            this.mauNenThuong = mau;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (duocChon) {
                g2.setColor(mauNenDuocChon);
            } else if (getModel().isPressed()) {
                g2.setColor(mauNenNhan);
            } else if (getModel().isRollover()) {
                g2.setColor(mauNenHover);
            } else {
                g2.setColor(mauNenThuong); // Sử dụng màu nền thường
            }

            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

            g2.dispose();
            super.paintComponent(g);
        }
    }
}