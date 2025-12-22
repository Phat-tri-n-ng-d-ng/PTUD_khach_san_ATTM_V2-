package view.panels;

import controllers.ManHinhTrangChuController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

public class ManHinhTrangChuPanel extends JPanel {
    public JTextField txtTimKiem;
    public JTable tablePhong;
    public DefaultTableModel tableModel;

    // Bảng màu hiện đại thống nhất
    private final Color BG_COLOR = new Color(248, 250, 252);           // #F8FAFC
    private final Color PRIMARY = new Color(67, 97, 238);              // #4361EE
    private final Color SUCCESS = new Color(46, 221, 149);             // #2EDD95
    private final Color WARNING = new Color(255, 190, 11);             // #FFBE0B
    private final Color BORDER_COLOR = new Color(226, 232, 240);       // #E2E8F0
    private final Color TEXT_DARK = new Color(51, 65, 85);             // #334155
    private final Color TEXT_LIGHT = new Color(100, 116, 139);         // #64748B

    // Thêm màu cho nền card phòng
    private final Color PHONG_THUE_COLOR = new Color(67, 97, 238);       // Xanh dương đậm (#4361EE)
    private final Color PHONG_THUE_BG = new Color(220, 235, 255);       // Xanh dương nhạt
    private final Color PHONG_DAT_COLOR = new Color(255, 190, 11);      // Vàng đậm (#FFBE0B)
    private final Color PHONG_DAT_BG = new Color(255, 248, 225);        // Vàng nhạt

    private JPanel searchPanel;
    private JScrollPane scrollPane;
    private JPanel card1, card2, card3;
    private JPanel statsPanel;
    private JPanel tablePanel;

    private ManHinhTrangChuController controller;
    private JLabel card1MainLabel, card1UnitLabel, card1PercentLabel;
    private JLabel card2MainLabel, card2UnitLabel, card2PercentLabel;
    private JLabel card3MainLabel, card3UnitLabel, card3PercentLabel;
    private JPanel card1ChartPanel, card2ChartPanel, card3ChartPanel;

    public ManHinhTrangChuPanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(BG_COLOR);
        initComponents();

        // Khởi tạo controller sau khi các thành phần đã được khởi tạo
        controller = new ManHinhTrangChuController(this);
    }

    private void initComponents() {
        // ===== PHẦN TRÊN CÙNG =====
        JPanel topPanel = new JPanel(new BorderLayout(0, 30));
        topPanel.setBackground(BG_COLOR);
        topPanel.setBorder(new EmptyBorder(30, 30, 0, 30));

        // Thanh tìm kiếm - bo góc 16px
        searchPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
            }

            @Override
            public Insets getInsets() {
                return new Insets(12, 20, 12, 20);
            }
        };
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        searchPanel.setPreferredSize(new Dimension(400, 50));

        txtTimKiem = new JTextField("Tìm kiếm phòng, khách hàng...");
        txtTimKiem.setBorder(null);
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtTimKiem.setForeground(Color.GRAY);

        txtTimKiem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtTimKiem.getText().equals("Tìm kiếm phòng, khách hàng...")) {
                    txtTimKiem.setText("");
                    txtTimKiem.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtTimKiem.getText().isEmpty()) {
                    txtTimKiem.setForeground(Color.GRAY);
                    txtTimKiem.setText("Tìm kiếm phòng, khách hàng...");
                }
            }
        });

        FontIcon searchIcon = FontIcon.of(FontAwesomeSolid.SEARCH, 20, TEXT_LIGHT);
        JLabel lblSearchIcon = new JLabel(searchIcon);
        searchPanel.add(txtTimKiem, BorderLayout.CENTER);
        searchPanel.add(lblSearchIcon, BorderLayout.EAST);

        topPanel.add(searchPanel, BorderLayout.NORTH);

        // ===== 3 CARD THỐNG KÊ =====
        statsPanel = new JPanel(new GridLayout(1, 3, 25, 0));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(new EmptyBorder(20, 0, 30, 0));

        // Khởi tạo các card với giá trị mặc định (sẽ được cập nhật bởi controller)
        card1 = createStatCard("DOANH THU THÁNG", "0", "VND", "Tăng 0%",
                PRIMARY, Color.WHITE, FontAwesomeSolid.DOLLAR_SIGN, 1);
        // Thành:
        card2 = createStatCard("PHÒNG ĐANG THUÊ", "0/0", "", "Chiếm 0%",
                PHONG_THUE_COLOR, PHONG_THUE_BG, FontAwesomeSolid.HOME, 2);
        card3 = createStatCard("PHÒNG ĐÃ ĐẶT", "0/0", "", "Chiếm 0%",
                PHONG_DAT_COLOR, PHONG_DAT_BG, FontAwesomeSolid.CALENDAR_CHECK, 3);

        statsPanel.add(card1);
        statsPanel.add(card2);
        statsPanel.add(card3);

        topPanel.add(statsPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // ===== PHẦN BẢNG =====
        tablePanel = new JPanel(new BorderLayout(0, 15));
        tablePanel.setBackground(BG_COLOR);
        tablePanel.setBorder(new EmptyBorder(0, 30, 30, 30));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);

        JLabel lblTitle = new JLabel("DANH SÁCH PHÒNG ĐANG SỬ DỤNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(TEXT_DARK);

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        JLabel lblDate = new JLabel("Hôm nay: " + today);
        lblDate.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDate.setForeground(TEXT_LIGHT);

        titlePanel.add(lblTitle, BorderLayout.WEST);
        titlePanel.add(lblDate, BorderLayout.EAST);
        tablePanel.add(titlePanel, BorderLayout.NORTH);

        String[] columns = {"Mã phòng", "Tên khách hàng", "Số điện thoại", "Ngày nhận", "Ngày trả", "Trạng thái", "Thao tác"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };

        tablePhong = new JTable(tableModel);
        tablePhong.setRowHeight(50);
        tablePhong.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablePhong.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablePhong.getTableHeader().setBackground(PRIMARY);
        tablePhong.getTableHeader().setForeground(Color.WHITE);
        tablePhong.getTableHeader().setPreferredSize(new Dimension(0, 50));
        tablePhong.setSelectionBackground(new Color(239, 246, 255));
        tablePhong.setGridColor(new Color(241, 245, 250));

        tablePhong.getColumnModel().getColumn(5).setCellRenderer(new StatusCellRenderer());
        tablePhong.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        tablePhong.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));

        scrollPane = new JScrollPane(tablePhong) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BORDER_COLOR);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
            }
        };
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);
    }

    private JPanel createStatCard(String title, String mainValue, String unit, String percent,
                                  Color accentColor, Color bgColor, org.kordamp.ikonli.Ikon ikon, int cardNumber) {
        JPanel card = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Vẽ nền với bo góc 16px
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);

                // Vẽ viền với bo góc 16px
                g2.setColor(accentColor);
                g2.setStroke(new BasicStroke(2.0f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 16, 16);

                g2.dispose();
            }
        };

        card.setOpaque(false);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Top - Icon và Title
        JPanel topPanel = new JPanel(new BorderLayout(10, 0));
        topPanel.setOpaque(false);

        FontIcon icon = FontIcon.of(ikon, 24, accentColor);
        JLabel iconLabel = new JLabel(icon);
        topPanel.add(iconLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Điều chỉnh màu chữ title
        if (bgColor.equals(PHONG_THUE_BG) || bgColor.equals(PHONG_DAT_BG)) {
            // Đối với card thuê và đặt, dùng màu chữ tối để tương phản với nền pastel
            titleLabel.setForeground(TEXT_DARK);
        } else if (bgColor.equals(Color.WHITE)) {
            titleLabel.setForeground(TEXT_LIGHT);
        } else {
            titleLabel.setForeground(TEXT_LIGHT);
        }

        topPanel.add(titleLabel, BorderLayout.CENTER);
        card.add(topPanel, BorderLayout.NORTH);

        // Center - Main Value và Unit
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        centerPanel.setOpaque(false);

        JLabel mainLabel = new JLabel(mainValue);
        mainLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        mainLabel.setForeground(accentColor);

        JLabel unitLabel = new JLabel(unit);
        unitLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        unitLabel.setForeground(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 180));

        centerPanel.add(mainLabel);
        centerPanel.add(unitLabel);
        card.add(centerPanel, BorderLayout.CENTER);

        // Lưu reference đến các label dựa trên cardNumber
        switch (cardNumber) {
            case 1:
                card1MainLabel = mainLabel;
                card1UnitLabel = unitLabel;
                break;
            case 2:
                card2MainLabel = mainLabel;
                card2UnitLabel = unitLabel;
                break;
            case 3:
                card3MainLabel = mainLabel;
                card3UnitLabel = unitLabel;
                break;
        }

        // Bottom - Percent
        // Bottom - Percent
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bottomPanel.setOpaque(false);

        JLabel percentLabel = new JLabel(percent);
        percentLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        percentLabel.setForeground(accentColor);

// CHỈ THÊM ICON MŨI TÊN NẾU LÀ "Tăng" hoặc "Giảm"
        if (percent.contains("Tăng") || percent.contains("Giảm")) {
            FontIcon arrowIcon;
            if (percent.contains("Tăng")) {
                arrowIcon = FontIcon.of(FontAwesomeSolid.ARROW_UP, 12, accentColor);
            } else {
                arrowIcon = FontIcon.of(FontAwesomeSolid.ARROW_DOWN, 12, accentColor);
            }
            percentLabel.setIcon(arrowIcon);
            percentLabel.setIconTextGap(5);
        }

        bottomPanel.add(percentLabel);
        card.add(bottomPanel, BorderLayout.SOUTH);

        // Lưu reference đến percent label
        switch (cardNumber) {
            case 1:
                card1PercentLabel = percentLabel;
                break;
            case 2:
                card2PercentLabel = percentLabel;
                break;
            case 3:
                card3PercentLabel = percentLabel;
                break;
        }

        // Mini chart - Right side
        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Lấy dữ liệu biểu đồ từ client property
                List<Double> chartData = (List<Double>) getClientProperty("chartData");
                Color chartColor = (Color) getClientProperty("chartColor");

                if (chartColor == null) {
                    chartColor = new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 180);
                }
                g2.setColor(chartColor);
                g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                int width = getWidth();
                int height = getHeight();

                if (chartData != null && !chartData.isEmpty()) {
                    // Vẽ biểu đồ với dữ liệu thực
                    double maxValue = chartData.stream().max(Double::compare).orElse(100.0);
                    if (maxValue == 0) maxValue = 100.0;

                    int dataCount = chartData.size();
                    int step = (dataCount > 1) ? width / (dataCount - 1) : 0;

                    for (int i = 0; i < dataCount - 1; i++) {
                        int y1 = height - 20 - (int)(chartData.get(i) * (height - 40) / maxValue);
                        int y2 = height - 20 - (int)(chartData.get(i + 1) * (height - 40) / maxValue);
                        if (step > 0) {
                            g2.drawLine(i * step, y1, (i + 1) * step, y2);
                        }
                    }
                } else {
                    // Vẽ biểu đồ mẫu nếu không có dữ liệu
                    Random random = new Random(title.hashCode());
                    int[] dataPoints = new int[6];
                    for (int i = 0; i < dataPoints.length; i++) {
                        dataPoints[i] = 30 + random.nextInt(40);
                    }

                    int step = width / 5;

                    for (int i = 0; i < 5; i++) {
                        int y1 = height - 20 - (dataPoints[i] * (height - 40) / 100);
                        int y2 = height - 20 - (dataPoints[i + 1] * (height - 40) / 100);
                        g2.drawLine(i * step, y1, (i + 1) * step, y2);
                    }
                }

                g2.dispose();
            }
        };
        chartPanel.setOpaque(false);
        chartPanel.setPreferredSize(new Dimension(90, 60));
        card.add(chartPanel, BorderLayout.EAST);

        // Lưu reference đến chart panel
        switch (cardNumber) {
            case 1:
                card1ChartPanel = chartPanel;
                break;
            case 2:
                card2ChartPanel = chartPanel;
                break;
            case 3:
                card3ChartPanel = chartPanel;
                break;
        }

        return card;
    }

    // Phương thức để cập nhật dữ liệu trên các card
    public void updateCardData(String doanhThu, String doanhThuPercent,
                               String phongThueText, String phongThuePercent,
                               String phongDatText, String phongDatPercent,
                               List<Double> doanhThu5Thang) {

        // Cập nhật card 1: DOANH THU THÁNG
        if (card1MainLabel != null && card1UnitLabel != null && card1PercentLabel != null) {
            String[] parts = doanhThu.split(" ");
            if (parts.length >= 1) {
                card1MainLabel.setText(parts[0]);
            }
            if (parts.length >= 2) {
                card1UnitLabel.setText(" " + parts[1]);
            }
            card1PercentLabel.setText(doanhThuPercent);

            // Cập nhật icon cho card 1 - xử lý cả "Giảm"
            if (doanhThuPercent.contains("Tăng") || doanhThuPercent.contains("Giảm")) {
                FontIcon arrowIcon;
                if (doanhThuPercent.contains("Tăng")) {
                    arrowIcon = FontIcon.of(FontAwesomeSolid.ARROW_UP, 12, PRIMARY);
                } else {
                    arrowIcon = FontIcon.of(FontAwesomeSolid.ARROW_DOWN, 12, PRIMARY);
                }
                card1PercentLabel.setIcon(arrowIcon);
                card1PercentLabel.setIconTextGap(5);
            } else {
                card1PercentLabel.setIcon(null);
            }

            if (card1ChartPanel != null && doanhThu5Thang != null) {
                card1ChartPanel.putClientProperty("chartData", doanhThu5Thang);
                card1ChartPanel.putClientProperty("chartColor",
                        new Color(PRIMARY.getRed(), PRIMARY.getGreen(), PRIMARY.getBlue(), 180));
                card1ChartPanel.repaint();
            }
        }

        // Cập nhật card 2: PHÒNG ĐANG THUÊ (giữ nguyên)
        if (card2MainLabel != null && card2UnitLabel != null && card2PercentLabel != null) {
            card2MainLabel.setText(phongThueText);
            card2UnitLabel.setText("");
            card2PercentLabel.setText(phongThuePercent);

            if (phongThuePercent.contains("Tăng") || phongThuePercent.contains("Giảm")) {
                FontIcon arrowIcon;
                if (phongThuePercent.contains("Tăng")) {
                    arrowIcon = FontIcon.of(FontAwesomeSolid.ARROW_UP, 12, PHONG_THUE_COLOR);
                } else {
                    arrowIcon = FontIcon.of(FontAwesomeSolid.ARROW_DOWN, 12, PHONG_THUE_COLOR);
                }
                card2PercentLabel.setIcon(arrowIcon);
                card2PercentLabel.setIconTextGap(5);
            } else {
                card2PercentLabel.setIcon(null);
            }
        }

        // Cập nhật card 3: PHÒNG ĐÃ ĐẶT (giữ nguyên)
        if (card3MainLabel != null && card3UnitLabel != null && card3PercentLabel != null) {
            card3MainLabel.setText(phongDatText);
            card3UnitLabel.setText("");
            card3PercentLabel.setText(phongDatPercent);

            if (phongDatPercent.contains("Tăng") || phongDatPercent.contains("Giảm")) {
                FontIcon arrowIcon;
                if (phongDatPercent.contains("Tăng")) {
                    arrowIcon = FontIcon.of(FontAwesomeSolid.ARROW_UP, 12, PHONG_DAT_COLOR);
                } else {
                    arrowIcon = FontIcon.of(FontAwesomeSolid.ARROW_DOWN, 12, PHONG_DAT_COLOR);
                }
                card3PercentLabel.setIcon(arrowIcon);
                card3PercentLabel.setIconTextGap(5);
            } else {
                card3PercentLabel.setIcon(null);
            }
        }

        // Repaint để cập nhật giao diện
        statsPanel.repaint();
    }

    // Getter methods
    public JTextField getTxtTimKiem() {
        return txtTimKiem;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getTablePhong() {
        return tablePhong;
    }

    // Inner classes cho renderer và editor
    class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (value != null) {
                String status = value.toString();
                setHorizontalAlignment(SwingConstants.CENTER);
                setFont(new Font("Segoe UI", Font.BOLD, 11));
                if (status.equals("Đang thuê")) {
                    setBackground(new Color(220, 247, 200));
                    setForeground(new Color(0, 128, 0));
                } else if (status.equals("Đã đặt")) {
                    setBackground(new Color(255, 242, 204));
                    setForeground(new Color(153, 102, 0));
                } else {
                    setBackground(Color.LIGHT_GRAY);
                    setForeground(Color.DARK_GRAY);
                }
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(getForeground().darker(), 1),
                        BorderFactory.createEmptyBorder(3, 8, 3, 8)
                ));
            }
            return c;
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.BOLD, 11));
            setFocusPainted(false);
            setBorderPainted(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            String text = (value == null) ? "" : value.toString();
            setText(text);

            if (text.contains("Nhận phòng")) {
                setBackground(SUCCESS);
                setForeground(Color.WHITE);
                setIcon(FontIcon.of(FontAwesomeSolid.DOOR_OPEN, 14, Color.WHITE));
            } else if (text.contains("Trả phòng")) {
                setBackground(new Color(231, 76, 60));
                setForeground(Color.WHITE);
                setIcon(FontIcon.of(FontAwesomeSolid.DOOR_CLOSED, 14, Color.WHITE));
            } else {
                setBackground(Color.GRAY);
                setForeground(Color.WHITE);
            }
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setFont(new Font("Segoe UI", Font.BOLD, 11));
            button.setFocusPainted(false);
            button.addActionListener(e -> {
                fireEditingStopped();
                String action = button.getText();
                int row = tablePhong.getSelectedRow();
                if (row >= 0 && row < tablePhong.getRowCount()) {
                    String maPhong = (String) tablePhong.getValueAt(row, 0);

                    // Gọi controller để xử lý
                    if (controller != null) {
                        controller.xuLyThaoTacPhong(row, action, maPhong);
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);

            if (label.contains("Nhận phòng")) {
                button.setIcon(FontIcon.of(FontAwesomeSolid.DOOR_OPEN, 14, Color.WHITE));
                button.setBackground(SUCCESS);
                button.setForeground(Color.WHITE);
            } else if (label.contains("Trả phòng")) {
                button.setIcon(FontIcon.of(FontAwesomeSolid.DOOR_CLOSED, 14, Color.WHITE));
                button.setBackground(new Color(231, 76, 60));
                button.setForeground(Color.WHITE);
            }

            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}