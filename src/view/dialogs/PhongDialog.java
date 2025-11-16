package view.dialogs;

import controllers.PhongController;
import controllers.dialogs.PhongDialogController;
import enums.TrangThaiPhong;
import entitys.Phong;
import view.panels.PhongPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PhongDialog extends JDialog {
    private Phong phong;
    public JTextField txt_MaPhong;
    public JComboBox<String> cbb_LoaiPhong;
    public JTextField txt_Tang;
    public JTextField txt_SoPhong;
    public JTextField txt_SucChuaToiDa;
    public JTextField txt_GiaPhong;
    public JTextField txt_TienCoc;
    public JTextField txt_TrangThai;
    public JButton btn_CapNhat;
    public JButton btn_Dong;
    public JButton btn_NgungHoatDong;

    public PhongDialog(PhongPanel phongPanel, Phong phong) {
        this.phong = phong;
        giaoDienDialog();
        setLocationRelativeTo(phongPanel);
    }

    private void giaoDienDialog() {
        setBounds(100, 100, 500, 520);
        setBackground(Color.WHITE);
        getContentPane().setLayout(null);
        getContentPane().setLayout(null);

        JLabel lblTieuDe = new JLabel("Thông tin phòng");
        lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
        lblTieuDe.setForeground(new Color(10, 100, 189));
        lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lblTieuDe.setBounds(10, 10, 466, 30);
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
        txt_MaPhong.setEnabled(false);
        txt_MaPhong.setEditable(false);

        // Loại phòng
        JLabel lblLoaiPhong = new JLabel("Loại phòng:");
        lblLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblLoaiPhong.setBounds(10, 75, 120, 20);
        pnlThongTin.add(lblLoaiPhong);

        cbb_LoaiPhong = new JComboBox<>();
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
        txt_Tang.setEditable(false);

        // Số phòng
        JLabel lblSoPhong = new JLabel("Số phòng:");
        lblSoPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblSoPhong.setBounds(10, 140, 100, 20);
        pnlThongTin.add(lblSoPhong);

        txt_SoPhong = new JTextField();
        txt_SoPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_SoPhong.setBounds(10, 165, 218, 30);
        pnlThongTin.add(txt_SoPhong);
        txt_SoPhong.setEditable(false);

        // Số lượng tối đa
        JLabel lblSucChuaToiDa = new JLabel("Sức chứa tối đa:");
        lblSucChuaToiDa.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblSucChuaToiDa.setBounds(240, 140, 150, 20);
        pnlThongTin.add(lblSucChuaToiDa);

        txt_SucChuaToiDa = new JTextField();
        txt_SucChuaToiDa.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_SucChuaToiDa.setBounds(240, 165, 218, 30);
        pnlThongTin.add(txt_SucChuaToiDa);

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

        txt_TrangThai = new JTextField();
//        for (TrangThaiPhong ttp : TrangThaiPhong.values()) {
//            cbb_TrangThai.addItem(ttp.getMoTa());
//        }
        txt_TrangThai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_TrangThai.setBounds(240, 295, 218, 30);
        pnlThongTin.add(txt_TrangThai);
//        txt_TrangThai.setEnabled(false);
        txt_TrangThai.setEditable(false);

        // Nút
        btn_CapNhat = new JButton("Cập nhật");
        btn_CapNhat.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btn_CapNhat.setBounds(228, 441, 120, 30);
        getContentPane().add(btn_CapNhat);

        btn_Dong = new JButton("Đóng");
        btn_Dong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btn_Dong.setBounds(358, 441, 120, 30);
        getContentPane().add(btn_Dong);
        
         btn_NgungHoatDong = new JButton("Ngưng hoạt động");
        btn_NgungHoatDong.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btn_NgungHoatDong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btn_NgungHoatDong.setBounds(68, 441, 150, 30);
        getContentPane().add(btn_NgungHoatDong);
        PhongDialogController phongDialogController= new PhongDialogController(this,phong);
        phongDialogController.hienThiThongTinPhong();
    }
}
