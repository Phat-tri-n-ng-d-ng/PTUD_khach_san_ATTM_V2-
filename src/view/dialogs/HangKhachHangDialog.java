package view.dialogs;

import controllers.dialogs.HangKhachHangDialogController;
import entitys.HangKhachHang;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class HangKhachHangDialog extends JDialog {
    public JButton btn_Dong;
    public JButton btn_CapNhat;

    // TextField cho điểm mới của từng hạng
    public JTextField txt_DiemMoi_Dong;
    public JTextField txt_DiemMoi_Bac;
    public JTextField txt_DiemMoi_Vang;
    public JTextField txt_DiemMoi_KC;

    // Label hiển thị điểm cũ (tối thiểu để lên hạng)
    public JLabel lbl_DiemCu_Dong;
    public JLabel lbl_DiemCu_Bac;
    public JLabel lbl_DiemCu_Vang;
    public JLabel lbl_DiemCu_KC;

    private ArrayList<HangKhachHang> dsHang;
    public HangKhachHangDialogController hangKhachHangDialogController;

    public HangKhachHangDialog(JFrame mainframe, ArrayList<HangKhachHang> dsHang) {
        super(mainframe, "Cập nhật ngưỡng điểm hạng khách hàng", true);
        this.dsHang = dsHang;
        giaoDienDialog();
        hienDiem(); // Gọi ngay sau khi tạo giao diện để hiển thị điểm cũ
        setLocationRelativeTo(mainframe);
        setResizable(false);
    }

    private void giaoDienDialog() {
        setSize(500, 428);
        setBackground(Color.WHITE);
        getContentPane().setLayout(null);

        JPanel pnl_CapNhatDiemTL = new JPanel();
        pnl_CapNhatDiemTL.setLayout(null);
        pnl_CapNhatDiemTL.setBorder(new LineBorder(Color.BLACK));
        pnl_CapNhatDiemTL.setBackground(Color.WHITE);
        pnl_CapNhatDiemTL.setBounds(10, 53, 466, 272);
        getContentPane().add(pnl_CapNhatDiemTL);

        JLabel lbl_TieuDe = new JLabel("Cập nhật điểm tích lũy hạng khách hàng");
        lbl_TieuDe.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_TieuDe.setForeground(new Color(10, 100, 189));
        lbl_TieuDe.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lbl_TieuDe.setBounds(24, 10, 425, 29);
        getContentPane().add(lbl_TieuDe);

        // Tiêu đề cột
        JLabel lbl_HangKhachHang = new JLabel("Hạng khách hàng:");
        lbl_HangKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_HangKhachHang.setBounds(10, 10, 140, 20);
        pnl_CapNhatDiemTL.add(lbl_HangKhachHang);

        JLabel lbl_DiemCu = new JLabel("Điểm cũ (tối thiểu):");
        lbl_DiemCu.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_DiemCu.setBounds(160, 10, 150, 20);
        pnl_CapNhatDiemTL.add(lbl_DiemCu);

        JLabel lbl_DiemMoi = new JLabel("Điểm mới:");
        lbl_DiemMoi.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_DiemMoi.setBounds(310, 10, 100, 20);
        pnl_CapNhatDiemTL.add(lbl_DiemMoi);

        // Dòng Đồng
        JLabel lbl_Dong = new JLabel("Đồng");
        lbl_Dong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_Dong.setBounds(10, 48, 100, 20);
        pnl_CapNhatDiemTL.add(lbl_Dong);

        lbl_DiemCu_Dong = new JLabel("0");
        lbl_DiemCu_Dong.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_DiemCu_Dong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_DiemCu_Dong.setBounds(180, 48, 80, 20);
        pnl_CapNhatDiemTL.add(lbl_DiemCu_Dong);

        txt_DiemMoi_Dong = new JTextField();
        txt_DiemMoi_Dong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_DiemMoi_Dong.setBounds(300, 42, 100, 30);
        pnl_CapNhatDiemTL.add(txt_DiemMoi_Dong);

        // Dòng Bạc
        JLabel lbl_Bac = new JLabel("Bạc");
        lbl_Bac.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_Bac.setBounds(10, 98, 100, 20);
        pnl_CapNhatDiemTL.add(lbl_Bac);

        lbl_DiemCu_Bac = new JLabel("0");
        lbl_DiemCu_Bac.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_DiemCu_Bac.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_DiemCu_Bac.setBounds(180, 98, 80, 20);
        pnl_CapNhatDiemTL.add(lbl_DiemCu_Bac);

        txt_DiemMoi_Bac = new JTextField();
        txt_DiemMoi_Bac.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_DiemMoi_Bac.setBounds(300, 92, 100, 30);
        pnl_CapNhatDiemTL.add(txt_DiemMoi_Bac);

        // Dòng Vàng
        JLabel lbl_Vang = new JLabel("Vàng");
        lbl_Vang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_Vang.setBounds(10, 148, 100, 20);
        pnl_CapNhatDiemTL.add(lbl_Vang);

        lbl_DiemCu_Vang = new JLabel("0");
        lbl_DiemCu_Vang.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_DiemCu_Vang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_DiemCu_Vang.setBounds(180, 148, 80, 20);
        pnl_CapNhatDiemTL.add(lbl_DiemCu_Vang);

        txt_DiemMoi_Vang = new JTextField();
        txt_DiemMoi_Vang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_DiemMoi_Vang.setBounds(300, 142, 100, 30);
        pnl_CapNhatDiemTL.add(txt_DiemMoi_Vang);

        // Dòng Kim cương
        JLabel lbl_KC = new JLabel("Kim cương");
        lbl_KC.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_KC.setBounds(10, 198, 100, 20);
        pnl_CapNhatDiemTL.add(lbl_KC);

        lbl_DiemCu_KC = new JLabel("0");
        lbl_DiemCu_KC.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_DiemCu_KC.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_DiemCu_KC.setBounds(180, 198, 80, 20);
        pnl_CapNhatDiemTL.add(lbl_DiemCu_KC);

        txt_DiemMoi_KC = new JTextField();
        txt_DiemMoi_KC.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_DiemMoi_KC.setBounds(300, 192, 100, 30);
        pnl_CapNhatDiemTL.add(txt_DiemMoi_KC);

        // Nút Đóng và Cập nhật
        btn_Dong = new JButton("Đóng");
        btn_Dong.setBounds(255, 335, 120, 35);
        getContentPane().add(btn_Dong);

        btn_CapNhat = new JButton("Cập nhật");
        btn_CapNhat.setBounds(100, 335, 120, 35);
        getContentPane().add(btn_CapNhat);

        // Khởi tạo controller nếu có danh sách hạng
        if (dsHang != null && !dsHang.isEmpty()) {
            hangKhachHangDialogController = new HangKhachHangDialogController(this, dsHang);
        }
    }

    /**
     * Hiển thị điểm tối thiểu hiện tại của từng hạng lên các label "Điểm cũ"
     */
    public void hienDiem() {
        if (dsHang == null || dsHang.isEmpty()) {
            return;
        }

        for (HangKhachHang h : dsHang) {
            String tenHang = h.getTenHang().trim().toLowerCase();
            String diemStr = String.valueOf(h.getDiemToiThieu());

            switch (tenHang) {
                case "đồng":
                    lbl_DiemCu_Dong.setText(diemStr);
                    break;
                case "bạc":
                    lbl_DiemCu_Bac.setText(diemStr);
                    break;
                case "vàng":
                    lbl_DiemCu_Vang.setText(diemStr);
                    break;
                case "kim cương":
                    lbl_DiemCu_KC.setText(diemStr);
                    break;
            }
        }
    }
}