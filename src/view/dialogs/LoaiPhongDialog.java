package view.dialogs;

//import controllers.dialogs.LoaiPhongDialogController;
import controllers.dialogs.LoaiPhongDialogController;
import entitys.LoaiPhong;
import view.panels.LoaiPhongPanel;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LoaiPhongDialog extends JDialog {
    private final LoaiPhong loaiPhong;
    public JTextField txt_MaLoaiPhong;
    public JTextField txt_TenLoaiPhong;
    public JTextField txt_GiaNiemYet;
    public JTextField txt_TyLeCoc;
    public JTextField txt_SucChuaToiThieu;
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
        setBackground(Color.WHITE);
        getContentPane().setLayout(null);
        getContentPane().setLayout(null);

        JLabel lblTieuDe = new JLabel("Thông tin loại phòng");
        lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
        lblTieuDe.setForeground(new Color(10, 100, 189));
        lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lblTieuDe.setBounds(20, 10, 456, 30);
        getContentPane().add(lblTieuDe);

        JPanel pnlThongTin = new JPanel();
        pnlThongTin.setBackground(Color.WHITE);
        pnlThongTin.setBorder(new LineBorder(Color.BLACK));
        pnlThongTin.setBounds(10, 50, 466, 320);
        getContentPane().add(pnlThongTin);
        pnlThongTin.setLayout(null);

        JLabel lblMaLoaiPhong = new JLabel("Mã loại phòng:");
        lblMaLoaiPhong.setBounds(10, 10, 150, 20);
        lblMaLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnlThongTin.add(lblMaLoaiPhong);

        txt_MaLoaiPhong = new JTextField();
        txt_MaLoaiPhong.setBounds(10, 35, 446, 30);
        txt_MaLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnlThongTin.add(txt_MaLoaiPhong);
        txt_MaLoaiPhong.setEnabled(false);
        txt_MaLoaiPhong.setEditable(false);

        JLabel lblTenLoaiPhong = new JLabel("Tên loại phòng:");
        lblTenLoaiPhong.setBounds(10, 75, 150, 20);
        lblTenLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnlThongTin.add(lblTenLoaiPhong);

        txt_TenLoaiPhong = new JTextField();
        txt_TenLoaiPhong.setBounds(10, 100, 446, 30);
        txt_TenLoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnlThongTin.add(txt_TenLoaiPhong);

        JLabel lblGiaNiemYet = new JLabel("Giá niêm yết:");
        lblGiaNiemYet.setBounds(10, 140, 200, 20);
        lblGiaNiemYet.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnlThongTin.add(lblGiaNiemYet);

        txt_GiaNiemYet = new JTextField();
        txt_GiaNiemYet.setBounds(10, 165, 446, 30);
        txt_GiaNiemYet.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnlThongTin.add(txt_GiaNiemYet);

        JLabel lblTyLeCoc = new JLabel("Tỷ lệ cọc (%):");
        lblTyLeCoc.setBounds(10, 205, 150, 20);
        lblTyLeCoc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnlThongTin.add(lblTyLeCoc);

        txt_TyLeCoc = new JTextField();
        txt_TyLeCoc.setBounds(10, 230, 218, 30);
        txt_TyLeCoc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnlThongTin.add(txt_TyLeCoc);

        JLabel lblSucChuaToiThieu = new JLabel("Sức chứa tối thiểu:");
        lblSucChuaToiThieu.setBounds(240, 205, 180, 20);
        lblSucChuaToiThieu.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnlThongTin.add(lblSucChuaToiThieu);

        txt_SucChuaToiThieu = new JTextField();
        txt_SucChuaToiThieu.setBounds(240, 230, 218, 30);
        txt_SucChuaToiThieu.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnlThongTin.add(txt_SucChuaToiThieu);

        btn_CapNhat = new JButton("Cập nhật");
        btn_CapNhat.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btn_CapNhat.setBounds(226, 381, 120, 30);
        getContentPane().add(btn_CapNhat);

        btn_Dong = new JButton("Đóng");
        btn_Dong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btn_Dong.setBounds(356, 381, 120, 30);
        getContentPane().add(btn_Dong);

//         Controller
        LoaiPhongDialogController controller = new LoaiPhongDialogController(this, loaiPhong);
        controller.hienThiThongTinLoaiPhong();
    }
}
