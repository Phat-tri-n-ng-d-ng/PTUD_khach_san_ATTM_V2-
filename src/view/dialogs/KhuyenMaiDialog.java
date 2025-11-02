package view.dialogs;

import com.toedter.calendar.JDateChooser;
import controllers.dialogs.KhuyenMaiDialogController;
import entitys.KhuyenMai;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class KhuyenMaiDialog extends JDialog {
    private KhuyenMaiDialogController khuyenMaiDialogController;
    private KhuyenMai khuyenMai;

    public JTextField txt_MaKM;
    public JTextField txt_TenKM;
    public JTextField txt_TiLeGiamGia;
    public JDateChooser ngayBatDau;
    public JDateChooser ngayKetThuc;
    public JButton btn_CapNhat;
    public JButton btn_Dong;
    public JComboBox cbb_TrangThaiHoaDon; // ĐÃ ĐỔI TÊN từ cbb_TrangThaiHoaDon
    public JCheckBox chckbx_Standard;
    public JCheckBox chckbx_Superior;
    public JCheckBox chckbx_FamilyRoom; // ĐÃ ĐỔI TÊN từ chckbx_Family
    public JCheckBox chckbx_Deluxe;
    public JCheckBox chckbx_Suite;
    public JCheckBox chckbx_TatCa;

    public KhuyenMaiDialog(JFrame mainframe, KhuyenMai khuyenMai) {
        super(mainframe, "Thông tin khuyến mãi", true);
        getContentPane().setBackground(new Color(255, 255, 255));
        this.khuyenMai = khuyenMai;

        giaoDienDialog();
        setLocationRelativeTo(mainframe);
    }

    private void giaoDienDialog() {
        setBounds(100, 100, 655, 600);
        setBackground(new Color(255, 255, 255));
        getContentPane().setLayout(null);

        JLabel lbl_ThongTinKhuyenMai = new JLabel("Thông tin khuyến mãi");
        lbl_ThongTinKhuyenMai.setForeground(new Color(10, 100, 189));
        lbl_ThongTinKhuyenMai.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lbl_ThongTinKhuyenMai.setBounds(214, 10, 222, 29);
        getContentPane().add(lbl_ThongTinKhuyenMai);

        JPanel pnl_ThongTinKhuyenMai = new JPanel();
        pnl_ThongTinKhuyenMai.setBackground(new Color(255, 255, 255));
        pnl_ThongTinKhuyenMai.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnl_ThongTinKhuyenMai.setBounds(10, 50, 618, 450);
        getContentPane().add(pnl_ThongTinKhuyenMai);
        pnl_ThongTinKhuyenMai.setLayout(null);

        JLabel lbl_MaKM = new JLabel("Mã khuyến mãi:");
        lbl_MaKM.setBounds(10, 10, 116, 16);
        lbl_MaKM.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnl_ThongTinKhuyenMai.add(lbl_MaKM);

        txt_MaKM = new JTextField();
        txt_MaKM.setBounds(10, 33, 596, 30);
        txt_MaKM.setBackground(new Color(255, 255, 255));
        txt_MaKM.setEnabled(false);
        txt_MaKM.setEditable(false);
        txt_MaKM.setForeground(new Color(0, 0, 0));
        txt_MaKM.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnl_ThongTinKhuyenMai.add(txt_MaKM);

        JLabel lbl_TenKM = new JLabel("Tên khuyến mãi:");
        lbl_TenKM.setBounds(10, 73, 116, 16);
        lbl_TenKM.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnl_ThongTinKhuyenMai.add(lbl_TenKM);

        txt_TenKM = new JTextField();
        txt_TenKM.setBounds(10, 96, 596, 30);
        txt_TenKM.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnl_ThongTinKhuyenMai.add(txt_TenKM);

        JLabel lbl_TiLeGiamGia = new JLabel("Tỉ lệ giảm giá:");
        lbl_TiLeGiamGia.setBounds(10, 136, 120, 16);
        lbl_TiLeGiamGia.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnl_ThongTinKhuyenMai.add(lbl_TiLeGiamGia);

        txt_TiLeGiamGia = new JTextField();
        txt_TiLeGiamGia.setBounds(10, 159, 150, 30);
        txt_TiLeGiamGia.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnl_ThongTinKhuyenMai.add(txt_TiLeGiamGia);

        JLabel lbl_NgayBatDau = new JLabel("Ngày bắt đầu:");
        lbl_NgayBatDau.setBounds(230, 136, 104, 16);
        lbl_NgayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnl_ThongTinKhuyenMai.add(lbl_NgayBatDau);

        ngayBatDau = new JDateChooser();
        ngayBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayBatDau.setDateFormatString("dd/MM/yyyy");
        ngayBatDau.setBounds(230, 159, 150, 30);
        pnl_ThongTinKhuyenMai.add(ngayBatDau);

        JLabel lbl_NgayKetThuc = new JLabel("Ngày kết thúc:");
        lbl_NgayKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_NgayKetThuc.setBounds(450, 136, 104, 16);
        pnl_ThongTinKhuyenMai.add(lbl_NgayKetThuc);

        ngayKetThuc = new JDateChooser();
        ngayKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ngayKetThuc.setDateFormatString("dd/MM/yyyy");
        ngayKetThuc.setBounds(450, 159, 150, 30);
        pnl_ThongTinKhuyenMai.add(ngayKetThuc);

        // Sự kiện click để mở calendar
        Component editorNgayBatDau = ngayBatDau.getDateEditor().getUiComponent();
        editorNgayBatDau.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ngayBatDau.getCalendarButton().doClick();
            }
        });

        Component editorNgayKetThuc = ngayKetThuc.getDateEditor().getUiComponent();
        editorNgayKetThuc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ngayKetThuc.getCalendarButton().doClick();
            }
        });

        JLabel lbl_DieuKienApDung = new JLabel("Điều kiện áp dụng:");
        lbl_DieuKienApDung.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbl_DieuKienApDung.setBounds(10, 199, 132, 16);
        pnl_ThongTinKhuyenMai.add(lbl_DieuKienApDung);

        chckbx_Standard = new JCheckBox("Standard");
        chckbx_Standard.setBackground(new Color(255, 255, 255));
        chckbx_Standard.setBounds(10, 225, 87, 20);
        pnl_ThongTinKhuyenMai.add(chckbx_Standard);

        chckbx_Superior = new JCheckBox("Superior");
        chckbx_Superior.setBackground(Color.WHITE);
        chckbx_Superior.setBounds(110, 225, 87, 20);
        pnl_ThongTinKhuyenMai.add(chckbx_Superior);

        chckbx_Deluxe = new JCheckBox("Deluxe");
        chckbx_Deluxe.setBackground(Color.WHITE);
        chckbx_Deluxe.setBounds(210, 225, 87, 20);
        pnl_ThongTinKhuyenMai.add(chckbx_Deluxe);

        chckbx_Suite = new JCheckBox("Suite");
        chckbx_Suite.setBackground(Color.WHITE);
        chckbx_Suite.setBounds(310, 225, 87, 20);
        pnl_ThongTinKhuyenMai.add(chckbx_Suite);

        chckbx_FamilyRoom = new JCheckBox("Family Room");
        chckbx_FamilyRoom.setBackground(Color.WHITE);
        chckbx_FamilyRoom.setBounds(410, 225, 120, 20);
        pnl_ThongTinKhuyenMai.add(chckbx_FamilyRoom);

        chckbx_TatCa = new JCheckBox("Tất cả");
        chckbx_TatCa.setBackground(Color.WHITE);
        chckbx_TatCa.setBounds(530, 225, 87, 20);
        pnl_ThongTinKhuyenMai.add(chckbx_TatCa);

        JLabel lbl_TrangThai = new JLabel("Trạng thái:");
        lbl_TrangThai.setBounds(10, 265, 150, 16);
        lbl_TrangThai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pnl_ThongTinKhuyenMai.add(lbl_TrangThai);

        // QUAN TRỌNG: Khởi tạo cbb_TrangThai
        cbb_TrangThaiHoaDon = new JComboBox();
        cbb_TrangThaiHoaDon.setModel(new DefaultComboBoxModel(new String[] {"Đang hoạt động", "Sắp diễn ra", "Hết hạn", "Tạm ngừng"}));
        cbb_TrangThaiHoaDon.setBounds(10, 290, 596, 30);
        pnl_ThongTinKhuyenMai.add(cbb_TrangThaiHoaDon);

        // Thêm sự kiện cho checkbox
        themSuKienCheckBox();

        btn_CapNhat = new JButton("Cập nhật");
        btn_CapNhat.setActionCommand("OK");
        btn_CapNhat.setBounds(376, 509, 120, 30);
        getContentPane().add(btn_CapNhat);

        btn_Dong = new JButton("Đóng");
        btn_Dong.setActionCommand("Cancel");
        btn_Dong.setBounds(506, 509, 120, 30);
        getContentPane().add(btn_Dong);

        khuyenMaiDialogController = new KhuyenMaiDialogController(this, khuyenMai);
        khuyenMaiDialogController.hienThiThongTin(); // Gọi phương thức hiển thị thông tin
    }

    private void themSuKienCheckBox() {
        // Khi chọn "Tất cả", bỏ chọn các checkbox khác
        chckbx_TatCa.addActionListener(e -> {
            if(chckbx_TatCa.isSelected()){
                chckbx_Standard.setSelected(false);
                chckbx_Superior.setSelected(false);
                chckbx_FamilyRoom.setSelected(false);
                chckbx_Deluxe.setSelected(false);
                chckbx_Suite.setSelected(false);
            }
        });

        // Khi chọn các checkbox khác, bỏ chọn "Tất cả"
        ActionListener suKienCacCheckBoxKhac = e -> {
            if (chckbx_Standard.isSelected() || chckbx_Superior.isSelected() ||
                    chckbx_FamilyRoom.isSelected() || chckbx_Deluxe.isSelected() ||
                    chckbx_Suite.isSelected()) {
                chckbx_TatCa.setSelected(false);
            }
        };

        chckbx_Standard.addActionListener(suKienCacCheckBoxKhac);
        chckbx_Superior.addActionListener(suKienCacCheckBoxKhac);
        chckbx_FamilyRoom.addActionListener(suKienCacCheckBoxKhac);
        chckbx_Deluxe.addActionListener(suKienCacCheckBoxKhac);
        chckbx_Suite.addActionListener(suKienCacCheckBoxKhac);
    }
}