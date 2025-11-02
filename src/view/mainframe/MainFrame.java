package view.mainframe;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        setTitle("Ứng dụng quản lý khách sạn ATTM");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo CardLayout cho phần trung tâm
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true); // bắt buộc để hiển thị frame
    }

    public void addPanel(JPanel panel, String name) {
        mainPanel.add(panel, name);
        mainPanel.revalidate(); // cập nhật layout
        mainPanel.repaint();
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }
}

