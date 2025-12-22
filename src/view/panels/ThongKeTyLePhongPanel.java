package view.panels;

import org.knowm.xchart.*;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

import controllers.ThongKeTyLePhongController;

public class ThongKeTyLePhongPanel extends JPanel {

    // Components
    private JComboBox<String> cbThang;
    private JComboBox<String> cbNam;
    private JButton btnThongKe;
    private JPanel pnlChart;
    private JPanel pnlCanhBao;
    private JPanel pnlXChart;

    // Labels cho các card
    private JLabel lblTyLeLapDay;
    private JLabel lblPhongTrong;
    private JLabel lblPhongDangSuDung;
    private JProgressBar progressBar;

    // Controller
    private ThongKeTyLePhongController tyLePhongController;

    // Colors
    private final Color PRIMARY_COLOR = new Color(67, 97, 238);
    private final Color SECONDARY_COLOR = new Color(100, 116, 139);
    private final Color WARNING_COLOR = new Color(255, 107, 107);
    private final Color SUCCESS_COLOR = new Color(76, 175, 80);
    private final Color OCCUPIED_COLOR = new Color(144, 238, 144); // Xanh lá nhạt
    private final Color VACANT_COLOR = Color.WHITE; // Trắng
    private final Color BOOKED_COLOR = new Color(255, 182, 193); // Hồng nhạt
    private final Color BG_COLOR = new Color(248, 250, 252);
    private final Color CARD_BG_COLOR = Color.WHITE;
    private final Color BORDER_COLOR = new Color(226, 232, 240);
    private final Color TEXT_PRIMARY = new Color(51, 65, 85);
    private final Color TEXT_SECONDARY = new Color(100, 116, 139);
    private final Color ALERT_COLOR = new Color(255, 87, 87);
    private final Color PREPARE_COLOR = new Color(255, 193, 7);

    public ThongKeTyLePhongPanel() {
        tyLePhongController = new ThongKeTyLePhongController();
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

        JLabel lblTitle = new JLabel("THỐNG KÊ TỶ LỆ LẤP ĐẦY PHÒNG", SwingConstants.CENTER);
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

        // Card 1: Tỷ lệ lấp đầy
        JPanel card1 = createCard("Tỷ lệ lấp đầy", "0%", PRIMARY_COLOR, FontAwesomeSolid.HOTEL);
        card1.setBounds(startX, cardY, cardWidth, cardHeight);
        mainPanel.add(card1);

        // Card 2: Phòng trống
        JPanel card2 = createCard("Phòng trống", "0/0 phòng", SUCCESS_COLOR, FontAwesomeSolid.BED);
        card2.setBounds(startX, cardY + cardHeight + spacing, cardWidth, cardHeight);
        mainPanel.add(card2);

        // Card 3: Phòng đang sử dụng
        JPanel card3 = createCard("Phòng đang sử dụng", "0/0 phòng", OCCUPIED_COLOR, FontAwesomeSolid.CHECK_CIRCLE);
        card3.setBounds(startX, cardY + (cardHeight + spacing) * 2, cardWidth, cardHeight);
        mainPanel.add(card3);

        // Chart panel
        pnlChart = new JPanel(new BorderLayout());
        pnlChart.setBackground(CARD_BG_COLOR);
        pnlChart.setBounds(10, 120, 940, 400);
        pnlChart.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                "Biểu đồ tỷ lệ lấp đầy",
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

        JLabel lblTitle = new JLabel("⚠️ CẢNH BÁO TỶ LỆ LẤP ĐẦY");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitle.setForeground(new Color(0xD35400));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblTitle.setAlignmentY(Component.TOP_ALIGNMENT);
        textPanel.add(lblTitle);

        textPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel lblLine1 = new JLabel("Tỷ lệ lấp đầy thấp hơn 60% trong 3 ngày liên tiếp.");
        lblLine1.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblLine1.setForeground(new Color(0x2C3E50));
        lblLine1.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblLine1.setAlignmentY(Component.TOP_ALIGNMENT);
        textPanel.add(lblLine1);

        textPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JLabel lblLine2 = new JLabel("Khuyến nghị: Xem xét giảm giá phòng hoặc tăng cường quảng cáo.");
        lblLine2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblLine2.setForeground(new Color(0x2C3E50));
        lblLine2.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblLine2.setAlignmentY(Component.TOP_ALIGNMENT);
        textPanel.add(lblLine2);

        textPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JLabel lblLine3 = new JLabel("Các phòng trống: ");
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

        JButton btnAction = new JButton("Xem đề xuất giá");
        FontIcon priceIcon = FontIcon.of(FontAwesomeSolid.TAGS, 12, Color.WHITE);
        btnAction.setIcon(priceIcon);
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

        // Lưu tham chiếu đến các label
        if (title.equals("Tỷ lệ lấp đầy")) {
            lblTyLeLapDay = lblValue;

            // Thêm progress bar
            progressBar = new JProgressBar();
            progressBar.setValue(0);
            progressBar.setStringPainted(true);
            progressBar.setForeground(color);
            progressBar.setBackground(new Color(240, 242, 245));
            progressBar.setBounds(60, 70, 200, 20);
            card.add(progressBar);
        } else if (title.equals("Phòng trống")) {
            lblPhongTrong = lblValue;
        } else if (title.equals("Phòng đang sử dụng")) {
            lblPhongDangSuDung = lblValue;
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
        Map<String, Integer> thongKePhong = tyLePhongController.layThongKePhongTheoTrangThai();
        int tongSoPhong = tyLePhongController.layTongSoPhong();
        double tyLeLapDay = tyLePhongController.layTyLeLapDay();
        String dsPhongTrong = tyLePhongController.layDanhSachPhongTrong();

        // Cập nhật biểu đồ
        capNhatBieuDo(thongKePhong, tongSoPhong);

        // Cập nhật card
        capNhatThongTinTongQuan(thongKePhong, tongSoPhong, tyLeLapDay);

        // Kiểm tra cảnh báo
        boolean coCanhBao = tyLePhongController.kiemTraCanhBao();
        pnlCanhBao.setVisible(coCanhBao);

        if (coCanhBao && dsPhongTrong != null && !dsPhongTrong.isEmpty()) {
            // Cập nhật danh sách phòng trống trong cảnh báo
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
                                    if (label.getText().contains("Các phòng trống:")) {
                                        label.setText("Các phòng trống: " + dsPhongTrong);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void capNhatBieuDo(Map<String, Integer> thongKePhong, int tongSoPhong) {
        pnlXChart.removeAll();

        if (thongKePhong.isEmpty() || tongSoPhong == 0) {
            JLabel lblNoData = new JLabel("Không có dữ liệu phòng", SwingConstants.CENTER);
            lblNoData.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            lblNoData.setForeground(TEXT_SECONDARY);
            pnlXChart.add(lblNoData, BorderLayout.CENTER);
        } else {
            PieChart chart = taoXPieChart(thongKePhong, tongSoPhong);
            XChartPanel<PieChart> xChartPanel = new XChartPanel<>(chart);
            pnlXChart.add(xChartPanel, BorderLayout.CENTER);
        }

        pnlXChart.revalidate();
        pnlXChart.repaint();
    }

    private PieChart taoXPieChart(Map<String, Integer> thongKePhong, int tongSoPhong) {
        PieChart chart = new PieChartBuilder()
                .width(880)
                .height(300)
                .title("Tỷ lệ lấp đầy phòng - Tổng số phòng: " + tongSoPhong)
                .build();

        PieStyler styler = chart.getStyler();
        styler.setLegendVisible(true);
        styler.setLegendPosition(Styler.LegendPosition.InsideNW);
        styler.setLegendPadding(10);
        styler.setLegendBorderColor(BORDER_COLOR);
        styler.setLegendBackgroundColor(CARD_BG_COLOR);
        styler.setPlotContentSize(0.7);
        styler.setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Pie);
        styler.setChartBackgroundColor(CARD_BG_COLOR);
        styler.setChartTitleFont(new Font("Segoe UI", Font.BOLD, 16));
        styler.setLegendFont(new Font("Segoe UI", Font.PLAIN, 12));
        styler.setDecimalPattern("#0.0");

        // Lấy màu sắc từ controller
        java.util.List<Color> colors = new ArrayList<>();
        for (String key : thongKePhong.keySet()) {
            colors.add(tyLePhongController.layMauChoPhong(key));
        }
        styler.setSeriesColors(colors.toArray(new Color[0]));

        // Thêm dữ liệu vào biểu đồ
        for (Map.Entry<String, Integer> entry : thongKePhong.entrySet()) {
            chart.addSeries(entry.getKey(), entry.getValue());
        }

        return chart;
    }

    private void capNhatThongTinTongQuan(Map<String, Integer> thongKePhong, int tongSoPhong, double tyLeLapDay) {
        int phongTrong = thongKePhong.getOrDefault("Trống", 0);
        int phongDangThue = thongKePhong.getOrDefault("Đang thuê", 0);

        // Cập nhật card
        if (lblTyLeLapDay != null) {
            String tyLeStr = tyLePhongController.formatTyLe(tyLeLapDay);
            lblTyLeLapDay.setText(tyLeStr);

            if (progressBar != null) {
                progressBar.setValue((int) tyLeLapDay);
                progressBar.setString(tyLeStr);

                // Đổi màu progress bar dựa trên tỷ lệ
                if (tyLeLapDay < 60) {
                    progressBar.setForeground(WARNING_COLOR);
                } else if (tyLeLapDay < 80) {
                    progressBar.setForeground(new Color(255, 193, 7)); // Màu cam
                } else {
                    progressBar.setForeground(SUCCESS_COLOR);
                }
            }
        }

        if (lblPhongTrong != null) {
            lblPhongTrong.setText(phongTrong + "/" + tongSoPhong + " phòng");
        }

        if (lblPhongDangSuDung != null) {
            lblPhongDangSuDung.setText(phongDangThue + "/" + tongSoPhong + " phòng");
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