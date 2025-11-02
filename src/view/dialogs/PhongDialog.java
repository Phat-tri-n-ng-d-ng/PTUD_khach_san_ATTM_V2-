package view.dialogs;

import controllers.PhongController;
import controllers.dialogs.PhongDialogController;
import enums.TrangThaiPhong;
import entitys.Phong;
import view.panels.PhongPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PhongDialog extends JDialog {
    private Phong phong;
    public JTextField txt_MaPhong;
    public JComboBox<String> cbb_LoaiPhong;
    public JTextField txt_Tang;
    public JTextField txt_SoPhong;
    public JTextField txt_SoLuongToiDa;
    public JTextField txt_GiaPhong;
    public JTextField txt_TienCoc;
    public JComboBox<String> cbb_TrangThai;
    public JButton btn_CapNhat;
    public JButton btn_Dong;


    public PhongDialog(PhongPanel phongPanel, Phong phong) {
        this.phong = phong;
        giaoDienDialog();
        setLocationRelativeTo(phongPanel);
    }

    private void giaoDienDialog() {
        setBounds(100, 100, 500, 520);
        getContentPane().setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        getContentPane().setLayout(null);

        JLabel lblTieuDe = new JLabel("Thông tin phòng");
        lblTieuDe.setForeground(new Color(10, 100, 189));
        lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lblTieuDe.setBounds(20, 10, 250, 30);
        getContentPane().add(lblTieuDe);

        JPanel pnlThongTin = new JPanel();
        pnlThongTin.setBackground(Color.WHITE);
        pnlThongTin.setBorder(new LineBorder(Color.BLACK));
        pnlThongTin.setBounds(10, 50, 466, 380);
        pnlThongTin.setLayout(null);
        getContentPane().add(pnlThongTin);

        // Mã phòng
        JLabel lblMaPhong = new JLabel("Mã phòng:");
        lblMaPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblMaPhong.setBounds(10, 10, 120, 20);
        pnlThongTin.add(lblMaPhong);

        txt_MaPhong = new JTextField();
        txt_MaPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_MaPhong.setBounds(10, 35, 446, 30);
        pnlThongTin.add(txt_MaPhong);

        // Loại phòng
        JLabel lblLoaiPhong = new JLabel("Loại phòng:");
        lblLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblLoaiPhong.setBounds(10, 75, 120, 20);
        pnlThongTin.add(lblLoaiPhong);

        cbb_LoaiPhong = new JComboBox<>(new String[]{
                "Standard", "Superior", "Deluxe", "Suite", "Family Room"
        });
        cbb_LoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        cbb_LoaiPhong.setBounds(10, 100, 218, 30);
        pnlThongTin.add(cbb_LoaiPhong);

        // Tầng
        JLabel lblTang = new JLabel("Tầng:");
        lblTang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblTang.setBounds(240, 75, 80, 20);
        pnlThongTin.add(lblTang);

        txt_Tang = new JTextField();
        txt_Tang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_Tang.setBounds(240, 100, 218, 30);
        pnlThongTin.add(txt_Tang);

        // Số phòng
        JLabel lblSoPhong = new JLabel("Số phòng:");
        lblSoPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblSoPhong.setBounds(10, 140, 100, 20);
        pnlThongTin.add(lblSoPhong);

        txt_SoPhong = new JTextField();
        txt_SoPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_SoPhong.setBounds(10, 165, 218, 30);
        pnlThongTin.add(txt_SoPhong);

        // Số lượng tối đa
        JLabel lblSoLuong = new JLabel("Số lượng tối đa:");
        lblSoLuong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblSoLuong.setBounds(240, 140, 150, 20);
        pnlThongTin.add(lblSoLuong);

        txt_SoLuongToiDa = new JTextField();
        txt_SoLuongToiDa.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_SoLuongToiDa.setBounds(240, 165, 218, 30);
        pnlThongTin.add(txt_SoLuongToiDa);

        // Giá phòng
        JLabel lblGiaPhong = new JLabel("Giá phòng:");
        lblGiaPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblGiaPhong.setBounds(10, 205, 150, 20);
        pnlThongTin.add(lblGiaPhong);

        txt_GiaPhong = new JTextField();
        txt_GiaPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_GiaPhong.setBounds(10, 230, 446, 30);
        pnlThongTin.add(txt_GiaPhong);

        // Tiền cọc
        JLabel lblTienCoc = new JLabel("Tiền cọc:");
        lblTienCoc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblTienCoc.setBounds(10, 270, 150, 20);
        pnlThongTin.add(lblTienCoc);

        txt_TienCoc = new JTextField();
        txt_TienCoc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_TienCoc.setBounds(10, 295, 218, 30);
        pnlThongTin.add(txt_TienCoc);

        // Trạng thái
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        lblTrangThai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblTrangThai.setBounds(240, 270, 100, 20);
        pnlThongTin.add(lblTrangThai);

        cbb_TrangThai = new JComboBox<>();
        for (TrangThaiPhong ttp : TrangThaiPhong.values()) {
            cbb_TrangThai.addItem(ttp.getMoTa());
        }
        cbb_TrangThai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        cbb_TrangThai.setBounds(240, 295, 218, 30);
        pnlThongTin.add(cbb_TrangThai);

        // Nút
        btn_CapNhat = new JButton("Cập nhật");
        btn_CapNhat.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btn_CapNhat.setBounds(140, 445, 100, 30);
        getContentPane().add(btn_CapNhat);

        btn_Dong = new JButton("Đóng");
        btn_Dong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btn_Dong.setBounds(260, 445, 100, 30);
        getContentPane().add(btn_Dong);
        PhongDialogController phongDialogController= new PhongDialogController(this,phong);
        phongDialogController.hienThiThongTinPhong();
    }
}
