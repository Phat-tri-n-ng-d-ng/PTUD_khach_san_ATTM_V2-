package view.dialogs;

//import controllers.dialogs.LoaiPhongDialogController;
import controllers.dialogs.LoaiPhongDialogController;
import entitys.LoaiPhong;
import view.panels.LoaiPhongPanel;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LoaiPhongDialog extends JDialog {
    private LoaiPhong loaiPhong;
    public JTextField txt_MaLoaiPhong;
    public JTextField txt_TenLoaiPhong;
    public JTextField txt_GiaNiemYet;
    public JTextField txt_TyLeCoc;
    public JTextField txt_SoNguoiMacDinh;
    public JButton btn_CapNhat;
    public JButton btn_Dong;

    public LoaiPhongDialog(LoaiPhongPanel loaiPhongPanel, LoaiPhong loaiPhong) {
        this.loaiPhong = loaiPhong;
        giaoDienDialog();
        setLocationRelativeTo(loaiPhongPanel);
    }

    private void giaoDienDialog() {
        setTitle("Thông Tin Loại Phòng");
        setBounds(100, 100, 500, 460);
        getContentPane().setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        getContentPane().setLayout(null);

        JLabel lblTieuDe = new JLabel("Thông tin loại phòng");
        lblTieuDe.setForeground(new Color(10, 100, 189));
        lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lblTieuDe.setBounds(20, 10, 300, 30);
        getContentPane().add(lblTieuDe);

        JPanel pnlThongTin = new JPanel();
        pnlThongTin.setBackground(Color.WHITE);
        pnlThongTin.setBorder(new LineBorder(Color.BLACK));
        pnlThongTin.setBounds(10, 50, 466, 320);
        pnlThongTin.setLayout(null);
        getContentPane().add(pnlThongTin);

        JLabel lblMaLoaiPhong = new JLabel("Mã loại phòng:");
        lblMaLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblMaLoaiPhong.setBounds(10, 10, 150, 20);
        pnlThongTin.add(lblMaLoaiPhong);

        txt_MaLoaiPhong = new JTextField();
        txt_MaLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_MaLoaiPhong.setBounds(10, 35, 446, 30);
        pnlThongTin.add(txt_MaLoaiPhong);

        JLabel lblTenLoaiPhong = new JLabel("Tên loại phòng:");
        lblTenLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblTenLoaiPhong.setBounds(10, 75, 150, 20);
        pnlThongTin.add(lblTenLoaiPhong);

        txt_TenLoaiPhong = new JTextField();
        txt_TenLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_TenLoaiPhong.setBounds(10, 100, 446, 30);
        pnlThongTin.add(txt_TenLoaiPhong);

        JLabel lblGiaNiemYet = new JLabel("Giá niêm yết:");
        lblGiaNiemYet.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblGiaNiemYet.setBounds(10, 140, 200, 20);
        pnlThongTin.add(lblGiaNiemYet);

        txt_GiaNiemYet = new JTextField();
        txt_GiaNiemYet.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_GiaNiemYet.setBounds(10, 165, 446, 30);
        pnlThongTin.add(txt_GiaNiemYet);

        JLabel lblTyLeCoc = new JLabel("Tỷ lệ cọc (%):");
        lblTyLeCoc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblTyLeCoc.setBounds(10, 205, 150, 20);
        pnlThongTin.add(lblTyLeCoc);

        txt_TyLeCoc = new JTextField();
        txt_TyLeCoc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_TyLeCoc.setBounds(10, 230, 218, 30);
        pnlThongTin.add(txt_TyLeCoc);

        JLabel lblSoNguoiMacDinh = new JLabel("Số người mặc định:");
        lblSoNguoiMacDinh.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblSoNguoiMacDinh.setBounds(240, 205, 180, 20);
        pnlThongTin.add(lblSoNguoiMacDinh);

        txt_SoNguoiMacDinh = new JTextField();
        txt_SoNguoiMacDinh.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        txt_SoNguoiMacDinh.setBounds(240, 230, 218, 30);
        pnlThongTin.add(txt_SoNguoiMacDinh);

        btn_CapNhat = new JButton("Cập nhật");
        btn_CapNhat.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btn_CapNhat.setBounds(130, 385, 100, 30);
        getContentPane().add(btn_CapNhat);

        btn_Dong = new JButton("Đóng");
        btn_Dong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btn_Dong.setBounds(250, 385, 100, 30);
        getContentPane().add(btn_Dong);

//         Controller
        LoaiPhongDialogController controller = new LoaiPhongDialogController(this, loaiPhong);
        controller.hienThiThongTinLoaiPhong();
    }
}
