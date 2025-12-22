package view.panels;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.CategoryStyler;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.List;

import controllers.ThongKeDoanhThuController;

public class ThongKeDoanhThuPanel extends JPanel {

    // Components
    private JComboBox<String> cbThang;
    private JComboBox<String> cbNam;
    private JButton btnThongKe;
    private JPanel pnlChart;
    private JPanel pnlCanhBao;
    private JPanel pnlXChart;

    // Labels cho các card
    private JLabel lblTongDoanhThu;
    private JLabel lblDoanhThuTB;
    private JLabel lblTangTruong;
    private JLabel lblChangeIndicator;

    // Controller
    private ThongKeDoanhThuController thongKeController;

    // Colors
    private final Color PRIMARY_COLOR = new Color(67, 97, 238);
    private final Color SECONDARY_COLOR = new Color(100, 116, 139);
    private final Color WARNING_COLOR = new Color(255, 107, 107);
    private final Color SUCCESS_COLOR = new Color(76, 175, 80);
    private final Color NEGATIVE_COLOR = new Color(220, 53, 69); // Màu đỏ cho tăng trưởng âm
    private final Color BG_COLOR = new Color(248, 250, 252);
    private final Color CARD_BG_COLOR = Color.WHITE;
    private final Color BORDER_COLOR = new Color(226, 232, 240);
    private final Color TEXT_PRIMARY = new Color(51, 65, 85);
    private final Color TEXT_SECONDARY = new Color(100, 116, 139);

    public ThongKeDoanhThuPanel() {
        thongKeController = new ThongKeDoanhThuController();
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);

        initComponents();
        addEventListeners();

        // Load default data
        loadThongKeMacDinh();
    }

    private void initComponents() {
        // Main container with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 30));

        // Header
        JPanel pnlHeader = new JPanel(null);
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBounds(0, 0, 1340, 60);
        pnlHeader.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        JLabel lblTitle = new JLabel("THỐNG KÊ DOANH THU", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(PRIMARY_COLOR);
        lblTitle.setBounds(0, 10, 1340, 40);
        pnlHeader.add(lblTitle);

        mainPanel.add(pnlHeader);

        // Filter panel
        JPanel pnlFilter = new JPanel(null);
        pnlFilter.setBackground(new Color(240, 242, 245));
        pnlFilter.setBounds(490, 70, 450, 40);
        pnlFilter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JLabel lblThang = new JLabel("Tháng:");
        lblThang.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblThang.setForeground(TEXT_PRIMARY);
        lblThang.setBounds(10, 10, 50, 20);
        pnlFilter.add(lblThang);

        String[] months = {"Tất cả", "01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12"};
        cbThang = new JComboBox<>(months);
        cbThang.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbThang.setForeground(TEXT_PRIMARY);
        cbThang.setBackground(Color.WHITE);
        cbThang.setBounds(65, 8, 70, 25);
        pnlFilter.add(cbThang);

        JLabel lblNam = new JLabel("Năm:");
        lblNam.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNam.setForeground(TEXT_PRIMARY);
        lblNam.setBounds(150, 10, 40, 20);
        pnlFilter.add(lblNam);

        String[] years = new String[11];
        for (int i = 0; i <= 10; i++) {
            years[i] = String.valueOf(2020 + i);
        }
        cbNam = new JComboBox<>(years);
        cbNam.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbNam.setForeground(TEXT_PRIMARY);
        cbNam.setBackground(Color.WHITE);
        cbNam.setBounds(195, 8, 80, 25);
        pnlFilter.add(cbNam);

        btnThongKe = new JButton("Thống kê");
        FontIcon filterIcon = FontIcon.of(FontAwesomeSolid.FILTER, 14, Color.WHITE);
        btnThongKe.setIcon(filterIcon);
        btnThongKe.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnThongKe.setBackground(PRIMARY_COLOR);
        btnThongKe.setForeground(Color.WHITE);
        btnThongKe.setBounds(290, 8, 120, 25);
        btnThongKe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnThongKe.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 80, 220), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        btnThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnThongKe.setBackground(new Color(50, 80, 220));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThongKe.setBackground(PRIMARY_COLOR);
            }
        });
        pnlFilter.add(btnThongKe);

        mainPanel.add(pnlFilter);

        // Summary cards
        int startX = 980;
        int cardWidth = 350;
        int cardHeight = 100;
        int spacing = 15;
        int cardY = 120;

        // Card 1: Tổng doanh thu
        JPanel card1 = createCard("Tổng doanh thu", "0 VNĐ", PRIMARY_COLOR, FontAwesomeSolid.DOLLAR_SIGN);
        card1.setBounds(startX, cardY, cardWidth, cardHeight);
        mainPanel.add(card1);

        // Card 2: Doanh thu trung bình 3 tháng gần nhất
        JPanel card2 = createCard("Doanh thu trung bình 3 tháng gần nhất", "0 VNĐ", SUCCESS_COLOR, FontAwesomeSolid.CHART_LINE);
        card2.setBounds(startX, cardY + cardHeight + spacing, cardWidth, cardHeight);
        mainPanel.add(card2);

        // Card 3: Tăng trưởng
        JPanel card3 = createCard("Tăng trưởng", "0%", PRIMARY_COLOR, FontAwesomeSolid.CHART_BAR);
        card3.setBounds(startX, cardY + (cardHeight + spacing) * 2, cardWidth, cardHeight);
        mainPanel.add(card3);

        // Chart panel
        pnlChart = new JPanel(new BorderLayout());
        pnlChart.setBackground(CARD_BG_COLOR);
        pnlChart.setBounds(10, 120, 940, 400);
        pnlChart.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                "Biểu đồ doanh thu",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14),
                PRIMARY_COLOR
        ));

        pnlXChart = new JPanel(new BorderLayout());
        pnlXChart.setBackground(CARD_BG_COLOR);

        pnlChart.add(pnlXChart, BorderLayout.CENTER);
        mainPanel.add(pnlChart);

        // Warning panel
        createWarningPanel(mainPanel);

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setBackground(BG_COLOR);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.setPreferredSize(new Dimension(1380, 700));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createWarningPanel(JPanel mainPanel) {
        pnlCanhBao = new JPanel(new BorderLayout(0, 10));
        pnlCanhBao.setBackground(new Color(255, 248, 225));
        pnlCanhBao.setBounds(10, 540, 940, 135);
        pnlCanhBao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 193, 7), 2),
                BorderFactory.createEmptyBorder(12, 15, 5, 5)
        ));

        JPanel contentPanel = new JPanel(new BorderLayout(15, 0));
        contentPanel.setBackground(new Color(255, 248, 225));

        JLabel lblWarningIcon = new JLabel();
        FontIcon warningIcon = FontIcon.of(FontAwesomeSolid.EXCLAMATION_CIRCLE, 32, new Color(255, 152, 0));
        lblWarningIcon.setIcon(warningIcon);
        lblWarningIcon.setVerticalAlignment(SwingConstants.TOP);
        contentPanel.add(lblWarningIcon, BorderLayout.WEST);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(255, 248, 225));

        JLabel lblTitle = new JLabel("⚠️ CẢNH BÁO DOANH THU");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitle.setForeground(new Color(0xD35400));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblTitle.setAlignmentY(Component.TOP_ALIGNMENT);
        textPanel.add(lblTitle);

        textPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel lblLine1 = new JLabel("Doanh thu tháng này thấp hơn 30% so với trung bình 3 tháng gần nhất.");
        lblLine1.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblLine1.setForeground(new Color(0x2C3E50));
        lblLine1.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblLine1.setAlignmentY(Component.TOP_ALIGNMENT);
        textPanel.add(lblLine1);

        textPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JLabel lblLine2 = new JLabel("Khuyến nghị: Tăng cường chiến dịch marketing và kiểm tra lại giá phòng.");
        lblLine2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblLine2.setForeground(new Color(0x2C3E50));
        lblLine2.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblLine2.setAlignmentY(Component.TOP_ALIGNMENT);
        textPanel.add(lblLine2);

        textPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JLabel lblLine3 = new JLabel("Mức giảm: 0%");
        lblLine3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblLine3.setForeground(new Color(0x2C3E50));
        lblLine3.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblLine3.setAlignmentY(Component.TOP_ALIGNMENT);
        textPanel.add(lblLine3);

        JPanel textContainer = new JPanel(new BorderLayout());
        textContainer.setBackground(new Color(255, 248, 225));
        textContainer.add(textPanel, BorderLayout.NORTH);
        contentPanel.add(textContainer, BorderLayout.CENTER);
        pnlCanhBao.add(contentPanel, BorderLayout.CENTER);

        JButton btnAction = new JButton("Xem chi tiết");
        FontIcon detailIcon = FontIcon.of(FontAwesomeSolid.SEARCH, 12, Color.WHITE);
        btnAction.setIcon(detailIcon);
        btnAction.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnAction.setBackground(new Color(255, 193, 7));
        btnAction.setForeground(Color.WHITE);
        btnAction.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 162, 60), 1),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        btnAction.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnAction.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAction.setBackground(new Color(230, 162, 60));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAction.setBackground(new Color(255, 193, 7));
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(255, 248, 225));
        buttonPanel.add(btnAction);
        pnlCanhBao.add(buttonPanel, BorderLayout.SOUTH);

        pnlCanhBao.setVisible(false);
        mainPanel.add(pnlCanhBao);
    }

    private JPanel createCard(String title, String value, Color color, FontAwesomeSolid iconCode) {
        JPanel card = new JPanel(null);
        card.setBackground(CARD_BG_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel lblIcon = new JLabel();
        FontIcon icon = FontIcon.of(iconCode, 28, color);
        lblIcon.setIcon(icon);
        lblIcon.setBounds(15, 20, 35, 35);
        card.add(lblIcon);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitle.setForeground(TEXT_SECONDARY);
        lblTitle.setBounds(60, 15, 250, 20);
        card.add(lblTitle);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblValue.setForeground(color);
        lblValue.setBounds(60, 35, 250, 30);
        card.add(lblValue);

        // Lưu tham chiếu đến các label dựa trên title
        if (title.equals("Tổng doanh thu")) {
            lblTongDoanhThu = lblValue;
        } else if (title.equals("Doanh thu trung bình 3 tháng gần nhất")) {
            lblDoanhThuTB = lblValue;
        } else if (title.equals("Tăng trưởng")) {
            lblTangTruong = lblValue;
            // Tạo thêm label cho indicator
            lblChangeIndicator = new JLabel();
            lblChangeIndicator.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            lblChangeIndicator.setBounds(60, 70, 100, 15);
            card.add(lblChangeIndicator);
        }

        return card;
    }

    private void addEventListeners() {
        btnThongKe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thucHienThongKe();
            }
        });
    }

    private void loadThongKeMacDinh() {
        LocalDate now = LocalDate.now();
        cbThang.setSelectedIndex(now.getMonthValue());
        cbNam.setSelectedItem(String.valueOf(now.getYear()));
        thucHienThongKe();
    }

    private void thucHienThongKe() {
        String thangStr = (String) cbThang.getSelectedItem();
        String namStr = (String) cbNam.getSelectedItem();

        int thang = thangStr.equals("Tất cả") ? LocalDate.now().getMonthValue() : Integer.parseInt(thangStr);
        int nam = Integer.parseInt(namStr);

        // Lấy dữ liệu từ controller
        Map<String, Double> data = thongKeController.layDoanhThuTheoNgay(thang, nam);
        double tongDoanhThu = thongKeController.layTongDoanhThuThang(thang, nam);
        double doanhThuTB = thongKeController.layDoanhThuTrungBinh3ThangGanNhat(thang, nam);

        // Cập nhật biểu đồ
        capNhatBieuDo(data, thangStr, namStr);

        // Cập nhật card
        capNhatThongTinTongQuan(tongDoanhThu, doanhThuTB, thang, nam);

        // Kiểm tra cảnh báo
        boolean coCanhBao = thongKeController.kiemTraCanhBao(thang, nam);
        pnlCanhBao.setVisible(coCanhBao);

        if (coCanhBao) {
            Map<String, Object> chiTiet = thongKeController.layChiTietCanhBao(thang, nam);
            double tyLeGiam = (double) chiTiet.get("tyLeGiam");

            // Cập nhật thông tin cảnh báo
            Component[] components = pnlCanhBao.getComponents();
            for (Component comp : components) {
                if (comp instanceof JPanel) {
                    Component[] subComps = ((JPanel) comp).getComponents();
                    for (Component subComp : subComps) {
                        if (subComp instanceof JPanel) {
                            Component[] textComps = ((JPanel) subComp).getComponents();
                            for (Component textComp : textComps) {
                                if (textComp instanceof JLabel) {
                                    JLabel label = (JLabel) textComp;
                                    if (label.getText().contains("Mức giảm:")) {
                                        label.setText("Mức giảm: " + String.format("%.1f", tyLeGiam) + "%");
                                    } else if (label.getText().contains("Thời điểm:")) {
                                        label.setText("Thời điểm: Tháng " + thang + "/" + nam);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void capNhatBieuDo(Map<String, Double> data, String thang, String nam) {
        pnlXChart.removeAll();

        if (data.isEmpty()) {
            // Hiển thị thông báo không có dữ liệu
            JLabel lblNoData = new JLabel("Không có dữ liệu doanh thu cho tháng này", SwingConstants.CENTER);
            lblNoData.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            lblNoData.setForeground(TEXT_SECONDARY);
            pnlXChart.add(lblNoData, BorderLayout.CENTER);
        } else {
            CategoryChart chart = taoXChart(data, thang, nam);
            XChartPanel<CategoryChart> xChartPanel = new XChartPanel<>(chart);
            pnlXChart.add(xChartPanel, BorderLayout.CENTER);
        }

        pnlXChart.revalidate();
        pnlXChart.repaint();
    }

    private CategoryChart taoXChart(Map<String, Double> data, String thang, String nam) {
        CategoryChart chart = new CategoryChartBuilder()
                .width(880)
                .height(300)
                .title("Doanh thu tháng " + thang + "/" + nam)
                .xAxisTitle("Ngày")
                .yAxisTitle("Doanh thu (triệu VNĐ)")
                .build();

        CategoryStyler styler = chart.getStyler();
        styler.setLegendPosition(Styler.LegendPosition.InsideNE);
        styler.setLegendVisible(false);
        styler.setPlotGridLinesVisible(true);
        styler.setPlotGridLinesColor(BORDER_COLOR);
        styler.setXAxisLabelRotation(45);
        styler.setPlotBackgroundColor(CARD_BG_COLOR);
        styler.setChartBackgroundColor(CARD_BG_COLOR);
        styler.setChartTitleFont(new Font("Segoe UI", Font.BOLD, 16));
        styler.setAxisTitleFont(new Font("Segoe UI", Font.BOLD, 12));
        styler.setAxisTickLabelsFont(new Font("Segoe UI", Font.PLAIN, 10));
        styler.setAxisTitlePadding(10);

        styler.setChartTitleBoxVisible(false);
        styler.setChartTitlePadding(10);
        styler.setPlotBorderColor(BORDER_COLOR);
        styler.setPlotBorderVisible(true);

        styler.setSeriesColors(new Color[]{PRIMARY_COLOR});

        List<String> days = new ArrayList<>();
        List<Double> revenues = new ArrayList<>();

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            days.add(entry.getKey());
            revenues.add(entry.getValue() / 1000000); // Chuyển sang triệu VNĐ
        }

        chart.addSeries("Doanh thu", days, revenues);

        return chart;
    }

    private void capNhatThongTinTongQuan(double tongDoanhThu, double doanhThuTB, int thang, int nam) {
        // Format tiền
        String strTong = thongKeController.formatTien(tongDoanhThu);
        String strTB = thongKeController.formatTien(doanhThuTB);

        // Cập nhật card
        if (lblTongDoanhThu != null) {
            lblTongDoanhThu.setText(strTong);
        }

        if (lblDoanhThuTB != null) {
            lblDoanhThuTB.setText(strTB);
        }

        // Tính tăng trưởng
        double tangTruong = thongKeController.tinhTangTruong(tongDoanhThu, doanhThuTB);
        String strTangTruong = String.format("%.1f%%", tangTruong);

        if (lblTangTruong != null) {
            lblTangTruong.setText(strTangTruong);

            // Kiểm tra nếu có cảnh báo (doanh thu thấp hơn TB 3 tháng)
            boolean coCanhBao = thongKeController.kiemTraCanhBao(thang, nam);

            if (coCanhBao) {
                // Nếu có cảnh báo, đổi màu thành màu cảnh báo
                lblTangTruong.setForeground(WARNING_COLOR);
                if (lblChangeIndicator != null) {
                    lblChangeIndicator.setText("↓ " + String.format("%.1f%%", Math.abs(tangTruong)));
                    lblChangeIndicator.setForeground(WARNING_COLOR);
                }
            } else {
                // Nếu không có cảnh báo, đổi màu theo tăng/giảm bình thường
                if (tangTruong < 0) {
                    lblTangTruong.setForeground(NEGATIVE_COLOR); // Màu đỏ cho tăng trưởng âm
                    if (lblChangeIndicator != null) {
                        lblChangeIndicator.setText("↓ " + String.format("%.1f%%", Math.abs(tangTruong)));
                        lblChangeIndicator.setForeground(NEGATIVE_COLOR);
                    }
                } else {
                    lblTangTruong.setForeground(SUCCESS_COLOR); // Màu xanh cho tăng trưởng dương
                    if (lblChangeIndicator != null) {
                        lblChangeIndicator.setText("↑ " + String.format("%.1f%%", tangTruong));
                        lblChangeIndicator.setForeground(SUCCESS_COLOR);
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(67, 97, 238, 5));
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 120));
        g2d.drawString("ATTM", 400, 400);
    }
}