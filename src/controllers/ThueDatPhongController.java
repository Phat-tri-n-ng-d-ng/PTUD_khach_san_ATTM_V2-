package controllers;

import database.dao.*;
import entitys.*;
import enums.TrangThaiKhuyenMai;
import enums.TrangThaiPhong;
//import services.HoaDonService;
//import views.FormThongTinDatPhong;
//import views.FormThongTinNhanPhong;
//import views.FormThongTinThuePhong;
//import views.FormThongTinTraPhong;
import view.panels.ThueDatPhongPanel;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ThueDatPhongController {

    private HoaDonDao hoaDonDao;
    private ThueDatPhongPanel thueDatPhongPanel;
    private KhuyenMaiDao khuyenMaiDao;
    private ArrayList<Phong> danhSachPhong;
    private ArrayList<Phong> danhSachPhongHienThi;
    private ArrayList<Phong> danhSachPhongTheoLoai;
    private ArrayList<KhuyenMai> danhSachKhuyenMai;
    private PhongDao phongDao;
    private KhachHangDao khachHangDao;
    private ArrayList<Phong> dsPhongDaChon = new ArrayList<>();
    // THÊM: Biến lưu thông tin khách hàng
    private KhachHang khachHangHienTai;
//    private String trangThai;

    public ThueDatPhongController(ThueDatPhongPanel thueDatPhongPanel){
        this.thueDatPhongPanel = thueDatPhongPanel;
        phongDao = new PhongDao();
        khachHangDao = new KhachHangDao();
        khuyenMaiDao = new KhuyenMaiDao();
        danhSachKhuyenMai = khuyenMaiDao.getTatCaKhuyenMai();
        hoaDonDao = new HoaDonDao();
        thueDatPhongPanel.btn_BoChon.addActionListener(e -> BoChonPhong());
        thueDatPhongPanel.txt_SoDienThoai.addActionListener(e -> getKhachHang());
        thueDatPhongPanel.chckbx_phongDat.addActionListener(e-> LocTrangThaiPhong());
        thueDatPhongPanel.chckbx_phongThue.addActionListener(e-> LocTrangThaiPhong());
        thueDatPhongPanel.chckbx_phongTrong.addActionListener(e-> LocTrangThaiPhong());
        thueDatPhongPanel.btn_Standard.addActionListener(e -> LocPhongTheoLoai("Standard"));
        thueDatPhongPanel.btn_Deluxe.addActionListener(e -> LocPhongTheoLoai("Deluxe"));
        thueDatPhongPanel.btn_Suite.addActionListener(e -> LocPhongTheoLoai("Suite"));
        thueDatPhongPanel.btn_Superior.addActionListener(e -> LocPhongTheoLoai("Superior"));
        thueDatPhongPanel.btn_FamilyRoom.addActionListener(e -> LocPhongTheoLoai("Family Room"));
        thueDatPhongPanel.btn_TatCa.addActionListener(e -> LocPhongTheoLoai(""));
        thueDatPhongPanel.btn_LamMoi.addActionListener(e -> LamMoi());
        thueDatPhongPanel.cbb_KhuyenMai.addActionListener(e -> LocPhongCoKhuyenMai());
//        thueDatPhongPanel.btn_Tim.addActionListener(e -> TimPhongDatTheoSoDienThoai());
        thueDatPhongPanel.btn_ApDung.addActionListener(e -> TuDongCapNhatTrangThaiPhong_TheoKhoangNgay());
//        thueDatPhongPanel.btn_ThuePhong.addActionListener(e -> moFormThongTinThuePhong());
//        thueDatPhongPanel.btn_DatPhong.addActionListener(e -> moFormThongTinDatPhong());
//        thueDatPhongPanel.btn_NhanPhong.addActionListener(e ->{
//        	FormThongTinNhanPhong form = new FormThongTinNhanPhong(thueDatPhongPanel);
//        	form.setVisible(true);
//        } );
//        thueDatPhongPanel.btn_TraPhong.addActionListener(e -> {
//            FormThongTinTraPhong formThongTinTraPhong = new FormThongTinTraPhong();
//            formThongTinTraPhong.setVisible(true);
//        });
    }

//    private void moFormThongTinDatPhong() {
//        // Sử dụng dsPhongDaChon thay vì danhSachPhongDaChon
//        if (dsPhongDaChon.isEmpty()) { // SỬA Ở ĐÂY
//            JOptionPane.showMessageDialog(thueDatPhongPanel, // SỬA: thay view bằng thueDatPhongPanel
//                    "Vui lòng chọn ít nhất một phòng!",
//                    "Thông báo",
//                    JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        // Kiểm tra đã có thông tin khách hàng chưa
//        String sdt = thueDatPhongPanel.txt_SoDienThoai.getText().trim();
//        if (sdt.isEmpty()) {
//            JOptionPane.showMessageDialog(thueDatPhongPanel,
//                    "Vui lòng tìm kiếm khách hàng trước!",
//                    "Thông báo",
//                    JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        // Lấy thông tin khách hàng từ database
//        KhachHang khachHang = khachHangDao.TimKhachHang(sdt, "SDT");
//        if (khachHang == null) {
//            JOptionPane.showMessageDialog(thueDatPhongPanel,
//                    "Không tìm thấy thông tin khách hàng!",
//                    "Lỗi",
//                    JOptionPane.ERROR_MESSAGE);
//            return;
//        }

        // Mở formThongTinDatPhong thông tin đặt phòng
//        FormThongTinDatPhong formThongTinDatPhong = new FormThongTinDatPhong();
//        formThongTinDatPhong.setThongTin(khachHang, dsPhongDaChon, "DAT"); //sử dụng dsPhongDaChon
//
//        formThongTinDatPhong.addWindowListener(new java.awt.event.WindowAdapter() {
//            @Override
//            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
//                // Cập nhật lại danh sách phòng sau khi formThongTinDatPhong đóng
//                refreshDanhSachPhong();
//                // Xóa danh sách phòng đã chọn sau khi đặt phòng thành công
//                dsPhongDaChon.clear();
//                thueDatPhongPanel.txt_SoDienThoai.setText("");
//                capNhatTableDanhSachPhongDaChon();
//            }
//        });
//
//        formThongTinDatPhong.setVisible(true);
//    }

//    public void moFormThongTinThuePhong() {
//        // Sử dụng dsPhongDaChon thay vì danhSachPhongDaChon
//        if (dsPhongDaChon.isEmpty()) { // SỬA Ở ĐÂY
//            JOptionPane.showMessageDialog(thueDatPhongPanel, // SỬA: thay view bằng thueDatPhongPanel
//                    "Vui lòng chọn ít nhất một phòng!",
//                    "Thông báo",
//                    JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        // Kiểm tra đã có thông tin khách hàng chưa
//        String sdt = thueDatPhongPanel.txt_SoDienThoai.getText().trim();
//        if (sdt.isEmpty()) {
//            JOptionPane.showMessageDialog(thueDatPhongPanel,
//                    "Vui lòng tìm kiếm khách hàng trước!",
//                    "Thông báo",
//                    JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        // Lấy thông tin khách hàng từ database
//        KhachHang khachHang = khachHangServies.TimKhachHang(sdt, "SDT");
//        if (khachHang == null) {
//            JOptionPane.showMessageDialog(thueDatPhongPanel,
//                    "Không tìm thấy thông tin khách hàng!",
//                    "Lỗi",
//                    JOptionPane.ERROR_MESSAGE);
//            return;
//        }

        // Mở formThongTinThuePhong thông tin thuê phòng
//        FormThongTinThuePhong formThongTinThuePhong = new FormThongTinThuePhong();
//        formThongTinThuePhong.setThongTin(khachHang, dsPhongDaChon, "THUE"); // SỬA: sử dụng dsPhongDaChon
//
//        formThongTinThuePhong.addWindowListener(new java.awt.event.WindowAdapter() {
//            @Override
//            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
//                // Cập nhật lại danh sách phòng sau khi formThongTinThuePhong đóng
//                refreshDanhSachPhong();
//                // Xóa danh sách phòng đã chọn sau khi thuê phòng thành công
//                dsPhongDaChon.clear();
//                thueDatPhongPanel.txt_SoDienThoai.setText("");
//                capNhatTableDanhSachPhongDaChon();
//            }
//        });
//
//        formThongTinThuePhong.setVisible(true);
//    }

    public void getTatCaPhong(){
        danhSachPhong = phongDao.getDanhSachPhong();
        danhSachPhongHienThi = danhSachPhong;
//        danhSachPhongTheoLoai = danhSachPhongHienThi;
        // Thêm các ô giả lập
        HienThiDanhSachPhong(danhSachPhongHienThi);
    }

    private void HienThiDanhSachPhong(ArrayList<Phong> danhSachPhongHienThi) {
        thueDatPhongPanel.danhSachPhongPanel.removeAll();
        for (Phong phong : danhSachPhongHienThi) {
            JPanel phongPanel = new JPanel();
            phongPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            phongPanel.setLayout(null);

            if(phong.getTrangThai().equals(TrangThaiPhong.Trong)){
                phongPanel.setBackground(Color.white);
            }else if(phong.getTrangThai().equals(TrangThaiPhong.DangSuDung)){
                phongPanel.setBackground(Color.GREEN);
            }else{
                phongPanel.setBackground(Color.red);
            }

            JLabel label = new JLabel("Phòng: P" );
            label.setBounds(6, 12, 0, 0);
            phongPanel.add(label);
            phongPanel.setPreferredSize(new Dimension(230, 120));


            JLabel lbl_Phong = new JLabel("Phòng: "+ phong.getMaPhong());
            lbl_Phong.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            lbl_Phong.setBounds(6, 10, 184, 20);
            phongPanel.add(lbl_Phong);

            JLabel lbl_LoaiPhong = new JLabel("Loại phòng: " + phong.getLoaiPhong().getTenLoaiPhong());
            lbl_LoaiPhong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            lbl_LoaiPhong.setBounds(6, 36, 184, 17);
            phongPanel.add(lbl_LoaiPhong);

            JLabel lbl_SucChuaToiDa = new JLabel("Số lượng tối đa: " + phong.getSucChuaToiDa());
            lbl_SucChuaToiDa.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            lbl_SucChuaToiDa.setBounds(6, 57, 184, 16);
            phongPanel.add(lbl_SucChuaToiDa);

            JLabel lbl_Gia = new JLabel("Giá: " + phong.getGiaPhong());
            lbl_Gia.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            lbl_Gia.setBounds(6, 78, 149, 16);
            phongPanel.add(lbl_Gia);

            JButton btn_ThemPhong = new JButton();
            btn_ThemPhong.setBounds(0, 0, 240, 120);
            btn_ThemPhong.setOpaque(false);
            btn_ThemPhong.setContentAreaFilled(false);
            btn_ThemPhong.setBorderPainted(false);
            btn_ThemPhong.setFocusPainted(false);
            phongPanel.add(btn_ThemPhong);

            btn_ThemPhong.addActionListener(e -> {
                ThemPhongChon(phong);
            });
            thueDatPhongPanel.danhSachPhongPanel.add(phongPanel);
        }
        // Làm mới lại giao diện scrollPane
        thueDatPhongPanel.danhSachPhongPanel.revalidate();
        thueDatPhongPanel.danhSachPhongPanel.repaint();
    }

    private void ThemPhongChon(Phong phong) {
        for (int i = 0; i < thueDatPhongPanel.model.getRowCount(); i++) {
            String maPhongTrongBang = thueDatPhongPanel.model.getValueAt(i, 0).toString();
            if (maPhongTrongBang.equals(phong.getMaPhong())) {
                JOptionPane.showMessageDialog(null,
                        "Phòng này đã được chọn!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // THÊM: Thêm phòng vào danh sách đã chọn
        dsPhongDaChon.add(phong);

        thueDatPhongPanel.model.addRow(new Object[]{
                phong.getMaPhong(),
                phong.getLoaiPhong().getTenLoaiPhong(),
                phong.getSucChuaToiDa(),
                phong.getGiaPhong()
        });
    }

    private void BoChonPhong() {
        int row = thueDatPhongPanel.table.getSelectedRow();
        if(row != -1){
            // THÊM: Lấy mã phòng và xóa khỏi dsPhongDaChon
            String maPhong = thueDatPhongPanel.model.getValueAt(row, 0).toString();
            dsPhongDaChon.removeIf(phong -> phong.getMaPhong().equals(maPhong));
            thueDatPhongPanel.model.removeRow(row);
        }else if(thueDatPhongPanel.table.getRowCount() == 0){
            JOptionPane.showMessageDialog(thueDatPhongPanel,"Chưa có phòng nào trong danh sách");
        }else{
            JOptionPane.showMessageDialog(thueDatPhongPanel,"Vui lòng chọn phòng muốn bỏ khỏi danh sách");
        }
    }

    private void getKhachHang(){
        String soDTKhachHang = thueDatPhongPanel.txt_SoDienThoai.getText();
        KhachHang khachHang = khachHangDao.TimKhachHang(soDTKhachHang,"SDT");
        if(khachHang != null){
            thueDatPhongPanel.txt_TenKhachHang.setText(khachHang.getTenKH());
            if(khachHang.isGioiTinh() == true) {
                thueDatPhongPanel.rdbtn_Nam.setSelected(true);
                thueDatPhongPanel.rdbtn_Nu.setSelected(false);
            }else {
                thueDatPhongPanel.rdbtn_Nam.setSelected(false);
                thueDatPhongPanel.rdbtn_Nu.setSelected(true);
            }
            thueDatPhongPanel.txt_Email.setText(khachHang.getEmail());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String ngaySinhFormatted = khachHang.getNgaySinh().format(formatter);
            thueDatPhongPanel.txt_ngaySinhKhachHang.setText(ngaySinhFormatted);
        }else {
        	thueDatPhongPanel.txt_Email.setText("");
        	thueDatPhongPanel.rdbtn_Nam.setSelected(false);
        	thueDatPhongPanel.rdbtn_Nu.setSelected(false);
        	thueDatPhongPanel.txt_TenKhachHang.setText("");
        	thueDatPhongPanel.txt_ngaySinhKhachHang.setText("");
        }
    }

    private void LocTrangThaiPhong() {
        ArrayList<Phong> danhSachTam = new ArrayList<>();

        // Bắt đầu lọc từ danh sách đã lọc theo loại (hoặc toàn bộ nếu chưa lọc loại)
        ArrayList<Phong> nguonLoc = danhSachPhongTheoLoai != null ? danhSachPhongTheoLoai : danhSachPhong;

        for (Phong phong : nguonLoc) {
            boolean match = false;

            if (thueDatPhongPanel.chckbx_phongTrong.isSelected()
                    && phong.getTrangThai().equals(TrangThaiPhong.Trong)) match = true;
            if (thueDatPhongPanel.chckbx_phongThue.isSelected()
                    && phong.getTrangThai().equals(TrangThaiPhong.DangSuDung)) match = true;
            if (thueDatPhongPanel.chckbx_phongDat.isSelected()
                    && phong.getTrangThai().equals(TrangThaiPhong.DaDat)) match = true;

            // Nếu không chọn trạng thái nào thì hiển thị hết
            if (!thueDatPhongPanel.chckbx_phongTrong.isSelected()
                    && !thueDatPhongPanel.chckbx_phongThue.isSelected()
                    && !thueDatPhongPanel.chckbx_phongDat.isSelected()) match = true;

            if (match) danhSachTam.add(phong);
        }

        danhSachPhongHienThi = danhSachTam;
        HienThiDanhSachPhong(danhSachPhongHienThi);
    }

    private void LocPhongTheoLoai(String loai) {
        if(loai != ""){
            danhSachPhongTheoLoai = phongDao.locPhongTheoLoai2(loai);
            danhSachPhongHienThi = danhSachPhongTheoLoai;
            LocTrangThaiPhong();
        }else{
            danhSachPhongHienThi = danhSachPhong;
        }
        HienThiDanhSachPhong(danhSachPhongHienThi);
    }

    private void LamMoi(){
        int luaChon = JOptionPane.showConfirmDialog(thueDatPhongPanel,"Bán có muốn làm mới lại trang","chú ý",JOptionPane.YES_NO_OPTION);
        if(luaChon == JOptionPane.YES_OPTION){
            thueDatPhongPanel.txt_TimSoDienThoai.setText("");
            thueDatPhongPanel.txt_SoDienThoai.setText("");
            thueDatPhongPanel.txt_Email.setText("");
            thueDatPhongPanel.txt_TenKhachHang.setText("");
            thueDatPhongPanel.txt_ngaySinhKhachHang.setText("");
            thueDatPhongPanel.rdbtn_Nam.setSelected(false);
            thueDatPhongPanel.rdbtn_Nu.setSelected(false);
            thueDatPhongPanel.model.setRowCount(0);
            thueDatPhongPanel.ngayBatDau.setDate(null);
            thueDatPhongPanel.ngayKetThuc.setDate(null);
            thueDatPhongPanel.chckbx_phongDat.setSelected(false);
            thueDatPhongPanel.chckbx_phongThue.setSelected(false);
            thueDatPhongPanel.chckbx_phongTrong.setSelected(false);
            thueDatPhongPanel.cbb_KhuyenMai.setSelectedIndex(0);
            getTatCaPhong();
        }
    }

    public void getKhuyenMai(){
        for(KhuyenMai khuyenMai : danhSachKhuyenMai){
            if(khuyenMai.getTrangThai().equals(TrangThaiKhuyenMai.DangHoatDong)){
                thueDatPhongPanel.cbb_KhuyenMai.addItem(khuyenMai.getTenKM());
            }
        }
    }
    private void LocPhongCoKhuyenMai() {
        if(thueDatPhongPanel.cbb_KhuyenMai.getSelectedIndex() == 0){
            getTatCaPhong();
            return;
        }
        String tenKhuyenMaiChon = thueDatPhongPanel.cbb_KhuyenMai.getSelectedItem().toString();
        KhuyenMai khuyenMaiChon = null;

        for (KhuyenMai khuyenMai : danhSachKhuyenMai) {
            if (khuyenMai.getTenKM().equals(tenKhuyenMaiChon)) {
                khuyenMaiChon = khuyenMai;
                break;
            }
        }

        if (khuyenMaiChon != null) {
            String[] loaiPhongKhuyenMai = khuyenMaiChon.getDieuKienApDung().split(",");
            ArrayList<Phong> danhSachTam = new ArrayList<>();

            for (String loai : loaiPhongKhuyenMai) {
                String tenLoai = loai.trim();
                ArrayList<Phong> dsTheoLoai = phongDao.locPhongTheoLoai(tenLoai);
                danhSachTam.addAll(dsTheoLoai);
            }
            danhSachPhongTheoLoai = danhSachTam;
            danhSachPhongHienThi = danhSachPhongTheoLoai;
            LocTrangThaiPhong();
            HienThiDanhSachPhong(danhSachPhongHienThi);
        }
    }

//    public void TimPhongDatTheoSoDienThoai(){
//        String sdt = thueDatPhongPanel.txt_TimSoDienThoai.getText().strip();
//        ArrayList<HoaDon> danhSachHoaDon = hoaDonDao.timHoaDonTheoSDT(sdt);
//        if (danhSachHoaDon == null) {
//            JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn cho số điện thoại này");
//            return;
//        }
//        ArrayList<Phong> danhSachTam = new ArrayList<>();
//        for(HoaDon hoaDon : danhSachHoaDon){
//            for(ChiTietHoaDon cthd : hoaDon.getcTHD()){
//                for(Phong phong : danhSachPhong){
//                    if(phong.getMaPhong().equals(cthd.getPhong().getMaPhong())){
//                        danhSachTam.add(phong);
//                    }
//                }
//            }
//        }
//
//        danhSachPhongHienThi = danhSachTam;
//        HienThiDanhSachPhong(danhSachPhongHienThi);
//    }

    public void TuDongCapNhatTrangThaiPhong_TheoKhoangNgay() {
        Date ngayBatDau = thueDatPhongPanel.ngayBatDau.getDate();
        Date ngayKetThuc = thueDatPhongPanel.ngayKetThuc.getDate();

        if (ngayBatDau == null || ngayKetThuc == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày bắt đầu và ngày kết thúc!");
            return;
        }

        hoaDonDao.TuDongCapNhatTrangThaiPhong_TheoKhoangNgay(ngayBatDau, ngayKetThuc);
        
        getTatCaPhong();
        LocTrangThaiPhong();
    }

    // THÊM: Phương thức thêm phòng vào danh sách đã chọn
    public void themPhongDaChon(Phong phong) {
        if (!dsPhongDaChon.contains(phong)) {
            dsPhongDaChon.add(phong);
            capNhatTableDanhSachPhongDaChon();
        }
    }

    // THÊM: Phương thức cập nhật bảng danh sách phòng đã chọn
    private void capNhatTableDanhSachPhongDaChon() {
        // Xóa tất cả các dòng hiện tại
        thueDatPhongPanel.model.setRowCount(0);

        // Thêm các phòng đã chọn vào bảng
        for (Phong phong : dsPhongDaChon) {
            thueDatPhongPanel.model.addRow(new Object[]{
                    phong.getMaPhong(),
                    phong.getLoaiPhong().getTenLoaiPhong(),
                    phong.getSucChuaToiDa(),
                    phong.getGiaPhong()
            });
        }
    }

    // THÊM: Phương thức lấy danh sách phòng đã chọn
    public ArrayList<Phong> getDsPhongDaChon() {
        return dsPhongDaChon;
    }


    public void refreshDanhSachPhong() {
        // Cập nhật danh sách phòng từ database
        danhSachPhong = phongDao.getDanhSachPhong();

        // Xóa các component cũ
        thueDatPhongPanel.danhSachPhongPanel.removeAll();

        // Vẽ lại danh sách phòng
        getTatCaPhong();

        // Cập nhật giao diện
        thueDatPhongPanel.danhSachPhongPanel.revalidate();
        thueDatPhongPanel.danhSachPhongPanel.repaint();
    }
}
