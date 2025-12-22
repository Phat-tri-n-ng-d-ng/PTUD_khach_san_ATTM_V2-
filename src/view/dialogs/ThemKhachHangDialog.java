package view.dialogs;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ThemKhachHangDialog extends JDialog {
    // Các thành phần giao diện
    public JTextField txt_TenKhachHang;
    public JTextField txt_SoDienThoai;
    public JTextField txt_Email;
    public JTextField txt_SoCCCD;
    public JDateChooser ngaySinh;
    public JRadioButton rdbtn_Nam;
    public JRadioButton rdbtn_Nu;
    public JButton btn_Them;
    public JButton btn_Huy;

    // Biến kiểm tra xác nhận
    private boolean daXacNhan = false;

    public ThemKhachHangDialog(JFrame parent, String soDienThoai) {
        super(parent, "Thêm Khách Hàng Mới", true);
        initComponents();
        setLocationRelativeTo(parent);

        // Điền sẵn số điện thoại nếu có
        if (soDienThoai != null && !soDienThoai.trim().isEmpty()) {
            txt_SoDienThoai.setText(soDienThoai.trim());
        }
    }

    private void initComponents() {
        setBounds(100, 100, 500, 450);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(245, 250, 255));

        // Tiêu đề
        JLabel lblTieuDe = new JLabel("Thêm Khách Hàng Mới");
        lblTieuDe.setForeground(new Color(10, 100, 189));
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
        lblTieuDe.setBounds(0, 15, 484, 30);
        getContentPane().add(lblTieuDe);

        // Panel thông tin
        JPanel pnlThongTin = new JPanel();
        pnlThongTin.setLayout(null);
        pnlThongTin.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 220, 240), 2, true),
                "Thông tin khách hàng",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.PLAIN, 13),
                new Color(80, 140, 200)
        ));
        pnlThongTin.setBackground(Color.WHITE);
        pnlThongTin.setBounds(25, 50, 440, 270);
        getContentPane().add(pnlThongTin);

        // ==== CÁC TRƯỜNG NHẬP LIỆU ====

        // 1. Tên khách hàng
        JLabel lblTen = new JLabel("Tên khách hàng:");
        lblTen.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTen.setBounds(20, 25, 120, 20);
        pnlThongTin.add(lblTen);

        txt_TenKhachHang = new JTextField();
        txt_TenKhachHang.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txt_TenKhachHang.setBounds(20, 45, 395, 32);
        txt_TenKhachHang.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 220), 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        pnlThongTin.add(txt_TenKhachHang);

        // 2. Số điện thoại
        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSDT.setBounds(20, 87, 120, 20);
        pnlThongTin.add(lblSDT);

        txt_SoDienThoai = new JTextField();
        txt_SoDienThoai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txt_SoDienThoai.setBounds(20, 107, 190, 32);
        txt_SoDienThoai.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 220), 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        pnlThongTin.add(txt_SoDienThoai);

        // 3. Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblEmail.setBounds(225, 87, 120, 20);
        pnlThongTin.add(lblEmail);

        txt_Email = new JTextField();
        txt_Email.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txt_Email.setBounds(225, 107, 190, 32);
        txt_Email.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 220), 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        pnlThongTin.add(txt_Email);

        // 4. Số CCCD
        JLabel lblCCCD = new JLabel("Số CCCD:");
        lblCCCD.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblCCCD.setBounds(20, 149, 120, 20);
        pnlThongTin.add(lblCCCD);

        txt_SoCCCD = new JTextField();
        txt_SoCCCD.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txt_SoCCCD.setBounds(20, 169, 190, 32);
        txt_SoCCCD.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 220), 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        pnlThongTin.add(txt_SoCCCD);

        // 5. Giới tính
        JLabel lblGioiTinh = new JLabel("Giới tính:");
        lblGioiTinh.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblGioiTinh.setBounds(225, 149, 120, 20);
        pnlThongTin.add(lblGioiTinh);

        JPanel pnlGioiTinh = new JPanel();
        pnlGioiTinh.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlGioiTinh.setBounds(225, 169, 190, 32);
        pnlGioiTinh.setOpaque(false);
        pnlThongTin.add(pnlGioiTinh);

        rdbtn_Nam = new JRadioButton("Nam");
        rdbtn_Nam.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rdbtn_Nam.setBackground(Color.WHITE);
        rdbtn_Nam.setSelected(true);
        pnlGioiTinh.add(rdbtn_Nam);

        rdbtn_Nu = new JRadioButton("Nữ");
        rdbtn_Nu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rdbtn_Nu.setBackground(Color.WHITE);
        pnlGioiTinh.add(rdbtn_Nu);

        ButtonGroup groupGioiTinh = new ButtonGroup();
        groupGioiTinh.add(rdbtn_Nam);
        groupGioiTinh.add(rdbtn_Nu);

        // 6. Ngày sinh
        JLabel lblNgaySinh = new JLabel("Ngày sinh:");
        lblNgaySinh.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblNgaySinh.setBounds(20, 211, 120, 20);
        pnlThongTin.add(lblNgaySinh);

        ngaySinh = new JDateChooser();
        ngaySinh.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        ngaySinh.setDateFormatString("dd/MM/yyyy");
        ngaySinh.setBounds(20, 231, 190, 32);

        // KHÔNG set màu nút lịch quá nổi
        ngaySinh.getCalendarButton().setBackground(new Color(100, 160, 220));
        ngaySinh.getCalendarButton().setForeground(Color.WHITE);
        ngaySinh.getCalendarButton().setFocusPainted(false);
        ngaySinh.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 220), 1, true),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)
        ));

        // Tính ngày hợp lệ (ít nhất 18 tuổi)
        LocalDate today = LocalDate.now();
        LocalDate maxDate = today.minusYears(18);
        LocalDate minDate = today.minusYears(100);

        ngaySinh.setMaxSelectableDate(java.sql.Date.valueOf(maxDate));
        ngaySinh.setMinSelectableDate(java.sql.Date.valueOf(minDate));
        // KHÔNG SET NGÀY MẶC ĐỊNH - để trống
        // ngaySinh.setDate(null);

        // Khi click vào ô ngày sinh thì mở popup
        Component editor = ngaySinh.getDateEditor().getUiComponent();
        editor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ngaySinh.getCalendarButton().doClick();
            }
        });

        // Set placeholder cho JDateChooser
        ((JTextField) ngaySinh.getDateEditor().getUiComponent()).setText("dd/MM/yyyy");
        ((JTextField) ngaySinh.getDateEditor().getUiComponent()).setForeground(Color.GRAY);

        // Khi focus thì xóa placeholder
        ((JTextField) ngaySinh.getDateEditor().getUiComponent()).addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                JTextField tf = (JTextField) ngaySinh.getDateEditor().getUiComponent();
                if (tf.getText().equals("dd/MM/yyyy")) {
                    tf.setText("");
                    tf.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                JTextField tf = (JTextField) ngaySinh.getDateEditor().getUiComponent();
                if (tf.getText().isEmpty()) {
                    tf.setText("dd/MM/yyyy");
                    tf.setForeground(Color.GRAY);
                }
            }
        });

        pnlThongTin.add(ngaySinh);

        // Nút Thêm - thiết kế mềm mại hơn với font nhỏ và bo góc
        btn_Them = new JButton("Thêm khách hàng") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Vẽ nền bo góc
                Color fillColor = getModel().isRollover() ?
                        new Color(80, 170, 100) : new Color(70, 160, 90);
                g2.setColor(fillColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Vẽ viền
                g2.setColor(new Color(60, 150, 80));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);

                g2.dispose();

                super.paintComponent(g);
            }
        };
        btn_Them.setBounds(140, 340, 140, 38);
        btn_Them.setForeground(Color.WHITE);
        btn_Them.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Font nhỏ hơn (12px)
        btn_Them.setBorderPainted(false);
        btn_Them.setFocusPainted(false);
        btn_Them.setContentAreaFilled(false);
        btn_Them.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Căn giữa chữ trong nút
        btn_Them.setHorizontalTextPosition(SwingConstants.CENTER);

        getContentPane().add(btn_Them);

        // Nút Hủy
        btn_Huy = new JButton("Hủy") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Vẽ nền bo góc
                Color fillColor = getModel().isRollover() ?
                        new Color(210, 100, 100) : new Color(200, 90, 90);
                g2.setColor(fillColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Vẽ viền
                g2.setColor(new Color(190, 80, 80));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);

                g2.dispose();

                super.paintComponent(g);
            }
        };
        btn_Huy.setBounds(300, 340, 110, 38);
        btn_Huy.setForeground(Color.WHITE);
        btn_Huy.setFont(new Font("Segoe UI", Font.BOLD, 12)); // Font nhỏ hơn (12px)
        btn_Huy.setBorderPainted(false);
        btn_Huy.setFocusPainted(false);
        btn_Huy.setContentAreaFilled(false);
        btn_Huy.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Căn giữa chữ trong nút
        btn_Huy.setHorizontalTextPosition(SwingConstants.CENTER);

        getContentPane().add(btn_Huy);

        // Xử lý sự kiện
        btn_Them.addActionListener(e -> {
            if (kiemTraDuLieu()) {
                daXacNhan = true;
                dispose();
            }
        });

        btn_Huy.addActionListener(e -> {
            daXacNhan = false;
            dispose();
        });

        // Đóng dialog khi nhấn ESC
        getRootPane().registerKeyboardAction(
                e -> {
                    daXacNhan = false;
                    dispose();
                },
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        // Enter để thêm
        getRootPane().setDefaultButton(btn_Them);
    }

    private boolean kiemTraDuLieu() {
        // Kiểm tra các trường bắt buộc
        if (txt_TenKhachHang.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên khách hàng!");
            txt_TenKhachHang.requestFocus();
            return false;
        }

        if (txt_SoDienThoai.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!");
            txt_SoDienThoai.requestFocus();
            return false;
        }

        // Kiểm tra định dạng số điện thoại (10-11 chữ số)
        String sdt = txt_SoDienThoai.getText().trim();
        if (!sdt.matches("\\d{10,11}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ! Vui lòng nhập 10-11 chữ số.");
            txt_SoDienThoai.requestFocus();
            txt_SoDienThoai.selectAll();
            return false;
        }

        if (txt_SoCCCD.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số CCCD!");
            txt_SoCCCD.requestFocus();
            return false;
        }

        // Kiểm tra định dạng CCCD (9-12 chữ số)
        String cccd = txt_SoCCCD.getText().trim();
        if (!cccd.matches("\\d{9,12}")) {
            JOptionPane.showMessageDialog(this, "Số CCCD không hợp lệ! Vui lòng nhập 9-12 chữ số.");
            txt_SoCCCD.requestFocus();
            txt_SoCCCD.selectAll();
            return false;
        }

        return true;
    }

    public boolean isDaXacNhan() {
        return daXacNhan;
    }

    // Thêm các getter để lấy dữ liệu
    public String getTenKhachHang() {
        return txt_TenKhachHang.getText().trim();
    }

    public String getSoDienThoai() {
        return txt_SoDienThoai.getText().trim();
    }

    public String getEmail() {
        return txt_Email.getText().trim();
    }

    public String getSoCCCD() {
        return txt_SoCCCD.getText().trim();
    }

    public boolean getGioiTinh() {
        return rdbtn_Nam.isSelected();
    }

    public LocalDate getNgaySinh() {
        Date date = ngaySinh.getDate();
        if (date != null) {
            return date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }

        // Kiểm tra nếu đang là placeholder thì trả về null
        JTextField dateField = (JTextField) ngaySinh.getDateEditor().getUiComponent();
        if (dateField.getText().equals("dd/MM/yyyy") || dateField.getText().trim().isEmpty()) {
            return null;
        }

        return null;
    }
}