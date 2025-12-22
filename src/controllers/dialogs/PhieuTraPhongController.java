package controllers.dialogs;

import database.dao.HoaDonDao;
import database.dao.KhuyenMaiDao;
import entitys.*;
import enums.PhuongThucThanhToan;
import enums.TrangThaiHoaDon;
import enums.TrangThaiPhong;
import services.KhachHangService;
import services.PhongService;
import view.dialogs.PhieuTraPhongDialog;
import xuatHoaDon.HoaDonPDFUtil;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class PhieuTraPhongController {
    private PhieuTraPhongDialog phieuTraPhongDialog;
    private PhongService phongService;
    private KhachHangService khachHangServies;
    private HoaDonDao hoaDonDao;
    private KhuyenMaiDao khuyenMaiDao;
    private Phong phong;
    private Date ngayKetThucGoc;
    private boolean load=false;
    private double tongTienPhong;
    private double tongTienGiamVoucher;
    ThueDatPhongController thueDatPhongController;


    public PhieuTraPhongController(PhieuTraPhongDialog dialog,
                                   Phong p, ThueDatPhongController thueDatPhongController) {
        this.thueDatPhongController=thueDatPhongController;
        this.phieuTraPhongDialog=dialog;
        this.phong= p;
        hoaDonDao=new HoaDonDao();
        phongService=new PhongService();
        khachHangServies =new KhachHangService();
        khuyenMaiDao=new KhuyenMaiDao();
    }

    public boolean traToanBoCacPhong() {
        try {
            HoaDon hoaDon = hoaDonDao.timHoaDonTheoPhongDangThue(phong.getMaPhong());
            if(hoaDon == null){
                baoLoi("Không tìm thấy hóa đơn");return false;
            }
            KhachHang kh=khachHangServies.TimKhachHang(hoaDon.getKhachHang().getSdt(),"SDT");
            if(kh ==null){
                baoLoi("Không tìm thấy khách hàng");return false;
            }
            // Hiển thị KH
            phieuTraPhongDialog.txt_HoTen.setText(kh.getTenKH());
            phieuTraPhongDialog.txt_SDT.setText(kh.getSdt());
            phieuTraPhongDialog.txt_CCCD.setText(kh.getSoCCCD());

            ArrayList<ChiTietHoaDon> dsCTHD=hoaDon.getcTHD();
            if (dsCTHD==null||dsCTHD.isEmpty()){
                baoLoi("Lỗi lấy chi tiết hóa đơn");return false;
            }
            // Hiển thị danh sách phòng có trong hóa đơn
            phieuTraPhongDialog.model.setRowCount(0);
            for (ChiTietHoaDon ct:dsCTHD) {
                Phong p=phongService.timPhongBangMa(ct.getPhong().getMaPhong());
                if (p==null)continue;
                double tien=p.getGiaPhong()*ct.getSoNgayO()-p.getTienCoc();
                phieuTraPhongDialog.model.addRow(new Object[]{
                        p.getMaPhong(),p.getLoaiPhong().getTenLoaiPhong(),
                        p.getSucChuaToiDa(),ct.getSoNgayO(),
                        p.getGiaPhong(),p.getTienCoc(), tien
                });
            }

            DecimalFormat df=new DecimalFormat("0 VND");
            tongTienPhong=0;
            for (int i=0;i<phieuTraPhongDialog.model.getRowCount();i++){
                tongTienPhong+=Double.parseDouble(phieuTraPhongDialog.table.getValueAt(i,6).toString());
            }
            phieuTraPhongDialog.lbl_TienCuaTongTienTrongPnlTongTien.setText(df.format(tongTienPhong));
            // khuyến mãi
            tongTienGiamVoucher=0;
            for (ChiTietHoaDon ct:dsCTHD) {
                if(ct.getKhuyenMai()!=null){
                    String maKM=ct.getKhuyenMai().getMaKM();
                    if(maKM!=null&&!maKM.isEmpty()){
                        KhuyenMai km =khuyenMaiDao.getKhuyenMaiTheoMa(maKM);
                        if(km!=null){
                            double thanhTienPhong=ct.getPhong().getGiaPhong()*ct.getSoNgayO();
                            tongTienGiamVoucher +=km.getTyLeGiam()*thanhTienPhong;
                        }
                    }
                }
            }
            phieuTraPhongDialog.lbl_TienCuaKhuyenMaiPnlTongTien.setText(df.format(tongTienGiamVoucher));
            // khuyến mãi theo hạng khách hàng
            double kmTheoHang=khachHangServies.timPhanTramGiamCuaHangKH(hoaDon.getKhachHang().getMaKH())*tongTienPhong;
            double phiDoiPhong=hoaDon.getPhiDoiPhong();
            double thue =(tongTienPhong -kmTheoHang-tongTienGiamVoucher)*0.1;
            double tongThanhToan=tongTienPhong-kmTheoHang-tongTienGiamVoucher+ phiDoiPhong+ thue;
            phieuTraPhongDialog.lbl_TienCuaKhuyenMaiTheoHangKH.setText(df.format(kmTheoHang)+"");
            phieuTraPhongDialog.lbl_TienCuaPhiDoiPhongTrongPnlTongTien.setText(df.format(phiDoiPhong));
            phieuTraPhongDialog.lbl_TienCuaThue.setText(df.format(thue));
            phieuTraPhongDialog.lbl_TienCuaTongTienThanhToanTrongPnlTongTienThanhToan.setText(df.format(tongThanhToan));
            phieuTraPhongDialog.txt_TienKhachDua.addActionListener(e->{
                try {
                     double tienNhan=Double.parseDouble(phieuTraPhongDialog.txt_TienKhachDua.getText());
                    if (tienNhan<tongThanhToan) {
                        baoLoi("Tiền khách phải lớn hơn hoặc bằng tổng tiền thanh toán"); return;
                    }
                    phieuTraPhongDialog.lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setText(df.format(tienNhan));
                    phieuTraPhongDialog.lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setText(df.format(tienNhan - tongThanhToan));
                    phieuTraPhongDialog.txt_TienTraLai.setText((tienNhan-tongThanhToan)+"");
                } catch (NumberFormatException ex) {
                    baoLoi("Tiền khách đưa phải là số và lớn hơn hoặc bằng tổng tiền thanh toán");
                }
            });

            // Hiên thị ngày nếu nhiều hơn 1 phòng thì nhấn mới hiện, 1 phòng thì hiện luôn
            if(phieuTraPhongDialog.model.getRowCount()>1){
                phieuTraPhongDialog.table.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        ArrayList<ChiTietHoaDon> dsCTHD=hoaDon.getcTHD();
                        int row=phieuTraPhongDialog.table.getSelectedRow();
                        for (ChiTietHoaDon cthd : dsCTHD) {
                            if(cthd.getPhong().getMaPhong().equalsIgnoreCase(phieuTraPhongDialog.table.getValueAt(row,0)+"")){
                                load=true;
                                phieuTraPhongDialog.ngayBatDau.setDate(Date.from(cthd.getNgayNhanPhong().atZone(java.time.ZoneId.systemDefault()).toInstant()));
                                Date ngayKT=Date.from(cthd.getNgayTraPhong().atZone(java.time.ZoneId.systemDefault()).toInstant());
                                phieuTraPhongDialog.ngayKetThuc.setDate(ngayKT);

                                // lưu ngày kết thúc gốc
                                ngayKetThucGoc=ngayKT;
                                load=false; break;
                            }
                        }
                    }
                });
            }else{
                for (ChiTietHoaDon cthd : dsCTHD) {
                        load=true;
                        phieuTraPhongDialog.ngayBatDau.setDate(Date.from(cthd.getNgayNhanPhong().atZone(java.time.ZoneId.systemDefault()).toInstant()));
                        Date ngayKT=Date.from(cthd.getNgayTraPhong().atZone(java.time.ZoneId.systemDefault()).toInstant());
                        phieuTraPhongDialog.ngayKetThuc.setDate(ngayKT);

                        // lưu ngày kết thúc gốc
                        ngayKetThucGoc=ngayKT;
                        load=false; break;
                }
            }

            // Cập nhật lại số ngày ở và THIẾU CẬP NHẬT VÀO DBS(SAU NHẤN XÁC NHẬN)

            phieuTraPhongDialog.ngayKetThuc.addPropertyChangeListener("date",evt->{
                if (load) return;
                int row=0;
                if(phieuTraPhongDialog.model.getRowCount()>1){
                     row=phieuTraPhongDialog.table.getSelectedRow();
                    if(row==-1){
                        baoLoi("Vui lòng chọn phòng để thay đổi ngày kết thúc");return;
                    }
                }

                Date ngayBatDau=phieuTraPhongDialog.ngayBatDau.getDate();
                Date ngayKetThuc= phieuTraPhongDialog.ngayKetThuc.getDate();
                if(ngayBatDau==null||ngayKetThuc== null) return;

                if (!ngayKetThuc.after(ngayBatDau)) {
                    baoLoi("Ngày kết thúc phải sau ngày bắt đầu");
                    phieuTraPhongDialog.ngayKetThuc.setDate(ngayKetThucGoc);
                    return;
                }
                if (ngayKetThuc.after(ngayKetThucGoc)) {
                    baoLoi("Ngày kết thúc không được lớn hơn ngày kết thúc ban đầu");
                    phieuTraPhongDialog.ngayKetThuc.setDate(ngayKetThucGoc);
                    return;
                }
                long diff=ngayKetThuc.getTime()-ngayBatDau.getTime();
                int soNgayO =(int)Math.ceil(diff/(1000.0*60*60*24));
                if (soNgayO<=0) soNgayO=1;
                phieuTraPhongDialog.table.setValueAt(soNgayO,row,3);
                for (ChiTietHoaDon cthd : dsCTHD) {
                    if(cthd.getPhong().getMaPhong().equalsIgnoreCase(phieuTraPhongDialog.table.getValueAt(row,0)+"")){
                        cthd.setNgayTraPhong(ngayKetThuc.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                        cthd.setSoNgayO(soNgayO);break;
                    };
                }
                // ====== PHẦN THÊM MỚI: tính lại tiền phòng của dòng ======
                double giaPhong = Double.parseDouble(phieuTraPhongDialog.table.getValueAt(row,4)+"");
                double tienCoc = Double.parseDouble(phieuTraPhongDialog.table.getValueAt(row, 5) + "");
                double tienMoi = giaPhong*soNgayO-tienCoc;
                phieuTraPhongDialog.table.setValueAt(tienMoi, row, 6);

                // ====== PHẦN THÊM MỚI: tính lại tổng tiền ======
                tongTienPhong = 0;
                for (int i = 0; i < phieuTraPhongDialog.table.getRowCount(); i++) {
                    tongTienPhong += Double.parseDouble(
                            phieuTraPhongDialog.table.getValueAt(i, 6) + "");
                }
                phieuTraPhongDialog.lbl_TienCuaTongTienTrongPnlTongTien
                        .setText(df.format(tongTienPhong));

            // khuyến mãi
                tongTienGiamVoucher=0;
                for (ChiTietHoaDon ct:dsCTHD) {
                    if(ct.getKhuyenMai()!=null){
                        String maKM=ct.getKhuyenMai().getMaKM();
                        if(maKM!=null&&!maKM.isEmpty()){
                            KhuyenMai km =khuyenMaiDao.getKhuyenMaiTheoMa(maKM);
                            if(km!=null){
                                double thanhTienPhong=ct.getPhong().getGiaPhong()*ct.getSoNgayO();
                                tongTienGiamVoucher +=km.getTyLeGiam()*thanhTienPhong;
                            }
                        }
                    }
                }
                phieuTraPhongDialog.lbl_TienCuaKhuyenMaiPnlTongTien.setText(df.format(tongTienGiamVoucher));
                // khuyến mãi theo hạng khách hàng
                double kmTheoHangMoi=khachHangServies.timPhanTramGiamCuaHangKH(hoaDon.getKhachHang().getMaKH())*tongTienPhong;
                double phiDoiPhongMoi=hoaDon.getPhiDoiPhong();
                double thueMoi =(tongTienPhong -kmTheoHang-tongTienGiamVoucher)*0.1;
                double tongThanhToanMoi=tongTienPhong-kmTheoHang-tongTienGiamVoucher+ phiDoiPhong+ thue;
                phieuTraPhongDialog.lbl_TienCuaKhuyenMaiTheoHangKH.setText(df.format(kmTheoHangMoi)+"");
                phieuTraPhongDialog.lbl_TienCuaPhiDoiPhongTrongPnlTongTien.setText(df.format(phiDoiPhongMoi));
                phieuTraPhongDialog.lbl_TienCuaThue.setText(df.format(thueMoi));
                phieuTraPhongDialog.lbl_TienCuaTongTienThanhToanTrongPnlTongTienThanhToan.setText(df.format(tongThanhToanMoi));
            });

            phieuTraPhongDialog.rdbtn_ChuyenKhoan.addActionListener(e -> {
                // Ẩn tiền mặt
                phieuTraPhongDialog.lbl_TienKhachDua.setVisible(false);
                phieuTraPhongDialog.txt_TienKhachDua.setVisible(false);
                phieuTraPhongDialog.lbl_TienTraLaiKhach.setVisible(false);
                phieuTraPhongDialog.txt_TienTraLai.setVisible(false);


                // Hiện QR
                phieuTraPhongDialog.lbl_QR.setVisible(true);

                // Cập nhật text phương thức
                phieuTraPhongDialog.lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien
                        .setText("Chuyển khoản");
                phieuTraPhongDialog.lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setText(df.format(tongThanhToan));
                phieuTraPhongDialog.lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setText("0 VND");
                phieuTraPhongDialog.revalidate();  // Tính toán lại layout
                phieuTraPhongDialog.repaint();     // Vẽ lại UI
            });


            phieuTraPhongDialog.rdbtn_TienMat.addActionListener(e -> {
                // Hiện tiền mặt
                phieuTraPhongDialog.lbl_TienKhachDua.setVisible(true);
                phieuTraPhongDialog.txt_TienKhachDua.setVisible(true);
                phieuTraPhongDialog.lbl_TienTraLaiKhach.setVisible(true);
                phieuTraPhongDialog.txt_TienTraLai.setVisible(true);

                // Ẩn QR
                phieuTraPhongDialog.lbl_QR.setVisible(false);

                // Cập nhật text phương thức
                phieuTraPhongDialog.lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien
                        .setText("Tiền mặt");
                phieuTraPhongDialog.lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setText("0 VND");
                phieuTraPhongDialog.revalidate();  // Tính toán lại layout
                phieuTraPhongDialog.repaint();     // Vẽ lại UI
            });



            // Nhấn nút xác nhận
            phieuTraPhongDialog.btnXacNhan.addActionListener(e-> {
                if (!phieuTraPhongDialog.rdbtn_ChuyenKhoan.isSelected() && !phieuTraPhongDialog.rdbtn_TienMat.isSelected()) {
                    baoLoi("Vui lòng chọn phương thức thanh toán");
                    return;
                }
                if (phieuTraPhongDialog.rdbtn_TienMat.isSelected() && phieuTraPhongDialog.txt_TienKhachDua.getText().isEmpty()) {
                    baoLoi("Vui lòng nhập tiền khách đưa");
                    return;
                }
                String txt = phieuTraPhongDialog.txt_TienKhachDua.getText();

                double tienNhan = phieuTraPhongDialog.rdbtn_ChuyenKhoan.isSelected()
                        ? tongThanhToan
                        : Double.parseDouble(
                        txt.replace("VND", "")
                                .replace(",", "")
                                .trim()
                );
                // Cập nhật cthd xuống dbs

                for(int r=0;r< phieuTraPhongDialog.table.getRowCount();r++){
                    double tienGiam=0;
                    String maPhong=phieuTraPhongDialog.table.getValueAt(r,0)+"";
                    int soNgayO = Integer.parseInt(phieuTraPhongDialog.table.getValueAt(r, 3) + "");
                    double giaPhong = Double.parseDouble(phieuTraPhongDialog.table.getValueAt(r, 4) + "");
                    double tienCoc = Double.parseDouble(phieuTraPhongDialog.table.getValueAt(r, 5) + "");

                    ChiTietHoaDon ctUpdate = null;
                    for (ChiTietHoaDon ct : dsCTHD) {
                        if (ct.getPhong().getMaPhong().equals(maPhong)) {
                            ctUpdate = ct;
                            if (ct.getKhuyenMai() != null) {
                                KhuyenMai km = khuyenMaiDao.getKhuyenMaiTheoMa(ct.getKhuyenMai().getMaKM());
                                if (km != null) tienGiam = giaPhong * soNgayO * km.getTyLeGiam();
                            }
                            break;
                        }
                    }

                    if (ctUpdate != null) {
                        double thanhTien = giaPhong * soNgayO-tienCoc;
                        Date ngayTraPhong = Date.from(ctUpdate.getNgayTraPhong().atZone(ZoneId.systemDefault()).toInstant());
                        hoaDonDao.capNhatChiTietHoaDon(ngayTraPhong, soNgayO, tienGiam, thanhTien, maPhong, hoaDon.getMaHD());
                    }
                }

                // Cập nhật phòng xuống dbs
                for (int r = 0; r < phieuTraPhongDialog.table.getRowCount(); r++) {
                    String maPhong = phieuTraPhongDialog.table.getValueAt(r, 0) + "";
                    phongService.capNhatTrangThaiPhong(maPhong, TrangThaiPhong.Trong);
                }

                // Cập nhật hóa đơn xuống dbs
                String pttt = phieuTraPhongDialog.rdbtn_ChuyenKhoan.isSelected() ? "Chuyển khoản" : "Tiền mặt";
                double tongTienSauGiam=tongTienPhong-kmTheoHang-tongTienGiamVoucher;


                hoaDonDao.capNhatHoaDon(pttt, tongTienPhong, thue,phiDoiPhong,tongThanhToan,kmTheoHang,tongTienSauGiam,tienNhan, hoaDon.getMaHD(), TrangThaiHoaDon.HoaDonHoanThanh);

                /// //////////////////
                double thanhTienPhong=0;
                double tienGiamVoucher=0;
                for (ChiTietHoaDon ct:dsCTHD) {
                    if(ct.getPhong().getMaPhong().equalsIgnoreCase(phong.getMaPhong())){
                        thanhTienPhong=ct.getPhong().getGiaPhong()*ct.getSoNgayO();
                        if(ct.getKhuyenMai()!=null){
                            String maKM=ct.getKhuyenMai().getMaKM();
                            if(maKM!=null&&!maKM.isEmpty()){
                                KhuyenMai km =khuyenMaiDao.getKhuyenMaiTheoMa(maKM);
                                if(km!=null){
                                    tienGiamVoucher=thanhTienPhong*km.getTyLeGiam(); break;
                                }
                            }
                        }
                    }
                }
                ArrayList<ChiTietHoaDon> dsCTHDMoi= new ArrayList<>();
                int i=0;
                for (ChiTietHoaDon cthd:dsCTHD) {
                    int soNgayO= Integer.parseInt(phieuTraPhongDialog.table.getValueAt(i,3)+"");
                    Phong p=phongService.timPhongBangMa(cthd.getPhong().getMaPhong());
                    ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(p, thanhTienPhong, tienGiamVoucher, soNgayO);
                    dsCTHDMoi.add(chiTietHoaDon);
                    i++;
                }


                String sdtKH=hoaDon.getKhachHang().getSdt();
                KhachHang khachHang=khachHangServies.TimKhachHang(sdtKH,"SDT");


                String tienNhanCoDuoiVND = phieuTraPhongDialog.lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.getText();
                double tienNhanLuu = Double.parseDouble(tienNhanCoDuoiVND.replace("VND","").trim());


                String tienGiamVCCoDuoiVND = phieuTraPhongDialog.lbl_TienCuaKhuyenMaiPnlTongTien.getText();
                double tienGiamVC = Double.parseDouble(tienGiamVCCoDuoiVND.replace("VND","").trim());


                String tienGiamTheoHangKHCoDuoiVND = phieuTraPhongDialog.lbl_TienCuaKhuyenMaiTheoHangKH.getText();
                double tienGiamTheoHangKH = Double.parseDouble(tienGiamTheoHangKHCoDuoiVND.replace("VND","").trim());

                double tienGiamHangKH=tienGiamVC+tienGiamTheoHangKH;

                HoaDon hoaDonPDF= new HoaDon(hoaDon.getMaHD(), hoaDon.getNgayLap(),hoaDon.getpTTT(),TrangThaiHoaDon.HoaDonHoanThanh,tongThanhToan,tongTienPhong,tienNhanLuu,thue,phiDoiPhong,khachHang,dsCTHDMoi,new NhanVien("NV24033","Đỗ Quốc Khoa"),tienGiamHangKH);

                HoaDonPDFUtil.xuatHoaDonPDF(hoaDonPDF);
                // Cập nhật điểm tích lũy
                double diemCongThem=tongThanhToan/1000000;
                double diemTL= hoaDon.getKhachHang().getDiemTichLuy()+diemCongThem;
                String maKH= hoaDon.getKhachHang().getMaKH();

                thueDatPhongController.refreshDanhSachPhong();
                phieuTraPhongDialog.dispose();
            });
            // nhấn nút hủy
            phieuTraPhongDialog.btnHuy.addActionListener(e->{
                if(JOptionPane.showConfirmDialog(phieuTraPhongDialog,
                        "Bạn có chắc chắn muốn hủy?","Xác nhận",
                        JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                    phieuTraPhongDialog.dispose();
            });

            return true;
        } catch (Exception e){
            baoLoi("Lỗi hệ thống khi trả phòng");
            return false;
        }
    }

    public boolean traMotPhong() {
        try {
            HoaDon hoaDon = hoaDonDao.timHoaDonTheoPhongDangThue(phong.getMaPhong());
            if (hoaDon == null) {
                baoLoi("Không tìm thấy hóa đơn");
                return false;
            }
            KhachHang kh = khachHangServies.TimKhachHang(hoaDon.getKhachHang().getSdt(), "SDT");
            if (kh == null) {
                baoLoi("Không tìm thấy khách hàng");
                return false;
            }
            // Hiển thị KH
            phieuTraPhongDialog.txt_HoTen.setText(kh.getTenKH());
            phieuTraPhongDialog.txt_SDT.setText(kh.getSdt());
            phieuTraPhongDialog.txt_CCCD.setText(kh.getSoCCCD());

            ArrayList<ChiTietHoaDon> dsCTHD=hoaDon.getcTHD();
            if (dsCTHD==null||dsCTHD.isEmpty()) {
                baoLoi("Lỗi lấy chi tiết hóa đơn");
                return false;
            }


        // Tìm chi tiết hóa đơn của phòng này
            ChiTietHoaDon cthdPhongHienTai = null;
            for (ChiTietHoaDon ct : dsCTHD) {
                if (ct.getPhong().getMaPhong().equalsIgnoreCase(phong.getMaPhong())) {
                    cthdPhongHienTai = ct;
                    break;
                }
            }
            if (cthdPhongHienTai == null) {
                baoLoi("Không tìm thấy chi tiết hóa đơn của phòng này");
                return false;
            }

            ArrayList<ChiTietHoaDon> dsCTHDTra = new ArrayList<>();
            dsCTHDTra.add(cthdPhongHienTai);

            // Hiển thị danh sách phòng (chỉ phòng này)
            phieuTraPhongDialog.model.setRowCount(0);
            for (ChiTietHoaDon ct : dsCTHDTra) {
                Phong p = phongService.timPhongBangMa(ct.getPhong().getMaPhong());
                if (p == null) continue;
                double tien = p.getGiaPhong() * ct.getSoNgayO() - p.getTienCoc();
                phieuTraPhongDialog.model.addRow(new Object[]{
                        p.getMaPhong(), p.getLoaiPhong().getTenLoaiPhong(),
                        p.getSucChuaToiDa(), ct.getSoNgayO(),
                        p.getGiaPhong(), p.getTienCoc(), tien
                });
            }

            DecimalFormat df = new DecimalFormat("0 VND");
            tongTienPhong = 0;
            for (int i = 0; i < phieuTraPhongDialog.model.getRowCount(); i++) {
                tongTienPhong += Double.parseDouble(phieuTraPhongDialog.table.getValueAt(i, 6).toString());
            }
            phieuTraPhongDialog.lbl_TienCuaTongTienTrongPnlTongTien.setText(df.format(tongTienPhong));
            // khuyến mãi
            tongTienGiamVoucher = 0;
            for (ChiTietHoaDon ct : dsCTHDTra) {
                if (ct.getKhuyenMai() != null) {
                    String maKM = ct.getKhuyenMai().getMaKM();
                    if (maKM != null && !maKM.isEmpty()) {
                        KhuyenMai km = khuyenMaiDao.getKhuyenMaiTheoMa(maKM);
                        if (km != null) {
                            double thanhTienPhong = ct.getPhong().getGiaPhong() * ct.getSoNgayO();
                            tongTienGiamVoucher += km.getTyLeGiam() * thanhTienPhong;
                        }
                    }
                }
            }
            phieuTraPhongDialog.lbl_TienCuaKhuyenMaiPnlTongTien.setText(df.format(tongTienGiamVoucher));
            // khuyến mãi theo hạng khách hàng
            double kmTheoHang = khachHangServies.timPhanTramGiamCuaHangKH(hoaDon.getKhachHang().getMaKH()) * tongTienPhong;
            double phiDoiPhong=hoaDon.getPhiDoiPhong();
            double thue = (tongTienPhong - kmTheoHang - tongTienGiamVoucher) * 0.1;
            double tongThanhToan = tongTienPhong - kmTheoHang - tongTienGiamVoucher + phiDoiPhong + thue;
            phieuTraPhongDialog.lbl_TienCuaKhuyenMaiTheoHangKH.setText(df.format(kmTheoHang) + "");
            phieuTraPhongDialog.lbl_TienCuaPhiDoiPhongTrongPnlTongTien.setText(df.format(phiDoiPhong));
            phieuTraPhongDialog.lbl_TienCuaThue.setText(df.format(thue));
            phieuTraPhongDialog.lbl_TienCuaTongTienThanhToanTrongPnlTongTienThanhToan.setText(df.format(tongThanhToan));
            phieuTraPhongDialog.txt_TienKhachDua.addActionListener(e -> {
                try {
                    double tienNhan = Double.parseDouble(phieuTraPhongDialog.txt_TienKhachDua.getText());
                    if (tienNhan < tongThanhToan) {
                        baoLoi("Tiền khách phải lớn hơn hoặc bằng tổng tiền thanh toán");
                        return;
                    }
                    phieuTraPhongDialog.lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setText(df.format(tienNhan));
                    phieuTraPhongDialog.lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setText(df.format(tienNhan - tongThanhToan));
                    phieuTraPhongDialog.txt_TienTraLai.setText((tienNhan - tongThanhToan) + "");
                } catch (NumberFormatException ex) {
                    baoLoi("Tiền khách đưa phải là số và lớn hơn hoặc bằng tổng tiền thanh toán");
                }
            });

            // Hiển thị ngày (chỉ 1 phòng)
            for (ChiTietHoaDon cthd : dsCTHDTra) {
                load = true;
                phieuTraPhongDialog.ngayBatDau.setDate(Date.from(cthd.getNgayNhanPhong().atZone(java.time.ZoneId.systemDefault()).toInstant()));
                Date ngayKT = Date.from(cthd.getNgayTraPhong().atZone(java.time.ZoneId.systemDefault()).toInstant());
                phieuTraPhongDialog.ngayKetThuc.setDate(ngayKT);

                // lưu ngày kết thúc gốc
                ngayKetThucGoc = ngayKT;
                load = false;
                break;
            }

            // Cập nhật lại số ngày ở
            phieuTraPhongDialog.ngayKetThuc.addPropertyChangeListener("date", evt -> {
                if (load) return;
                int row = 0; // Chỉ có 1 dòng

                Date ngayBatDau = phieuTraPhongDialog.ngayBatDau.getDate();
                Date ngayKetThuc = phieuTraPhongDialog.ngayKetThuc.getDate();
                if (ngayBatDau == null || ngayKetThuc == null) return;

                if (!ngayKetThuc.after(ngayBatDau)) {
                    baoLoi("Ngày kết thúc phải sau ngày bắt đầu");
                    phieuTraPhongDialog.ngayKetThuc.setDate(ngayKetThucGoc);
                    return;
                }
                if (ngayKetThuc.after(ngayKetThucGoc)) {
                    baoLoi("Ngày kết thúc không được lớn hơn ngày kết thúc ban đầu");
                    phieuTraPhongDialog.ngayKetThuc.setDate(ngayKetThucGoc);
                    return;
                }
                long diff = ngayKetThuc.getTime() - ngayBatDau.getTime();
                int soNgayO = (int) Math.ceil(diff / (1000.0 * 60 * 60 * 24));
                if (soNgayO <= 0) soNgayO = 1;
                phieuTraPhongDialog.table.setValueAt(soNgayO, row, 3);
                for (ChiTietHoaDon cthd : dsCTHD) {
                    if (cthd.getPhong().getMaPhong().equalsIgnoreCase(phieuTraPhongDialog.table.getValueAt(row, 0) + "")) {
                        cthd.setNgayTraPhong(ngayKetThuc.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                        cthd.setSoNgayO(soNgayO);
                        break;
                    }
                }
                // Tính lại tiền phòng của dòng
                double giaPhong = Double.parseDouble(phieuTraPhongDialog.table.getValueAt(row, 4) + "");
                double tienCoc = Double.parseDouble(phieuTraPhongDialog.table.getValueAt(row, 5) + "");
                double tienMoi = giaPhong * soNgayO - tienCoc;
                phieuTraPhongDialog.table.setValueAt(tienMoi, row, 6);

                // Tính lại tổng tiền
                tongTienPhong = 0;
                for (int i = 0; i < phieuTraPhongDialog.table.getRowCount(); i++) {
                    tongTienPhong += Double.parseDouble(phieuTraPhongDialog.table.getValueAt(i, 6) + "");
                }
                phieuTraPhongDialog.lbl_TienCuaTongTienTrongPnlTongTien.setText(df.format(tongTienPhong));

                // khuyến mãi
                tongTienGiamVoucher = 0;
                for (ChiTietHoaDon ct : dsCTHDTra) {
                    if (ct.getKhuyenMai() != null) {
                        String maKM = ct.getKhuyenMai().getMaKM();
                        if (maKM != null && !maKM.isEmpty()) {
                            KhuyenMai km = khuyenMaiDao.getKhuyenMaiTheoMa(maKM);
                            if (km != null) {
                                double thanhTienPhong = ct.getPhong().getGiaPhong() * ct.getSoNgayO();
                                tongTienGiamVoucher += km.getTyLeGiam() * thanhTienPhong;
                            }
                        }
                    }
                }
                phieuTraPhongDialog.lbl_TienCuaKhuyenMaiPnlTongTien.setText(df.format(tongTienGiamVoucher));
                // khuyến mãi theo hạng khách hàng
                double kmTheoHangMoi = khachHangServies.timPhanTramGiamCuaHangKH(hoaDon.getKhachHang().getMaKH()) * tongTienPhong;
                double thueMoi = (tongTienPhong - kmTheoHangMoi - tongTienGiamVoucher) * 0.1;
                double tongThanhToanMoi = tongTienPhong - kmTheoHangMoi - tongTienGiamVoucher + phiDoiPhong + thueMoi;
                phieuTraPhongDialog.lbl_TienCuaKhuyenMaiTheoHangKH.setText(df.format(kmTheoHangMoi) + "");
                phieuTraPhongDialog.lbl_TienCuaPhiDoiPhongTrongPnlTongTien.setText(df.format(phiDoiPhong));
                phieuTraPhongDialog.lbl_TienCuaThue.setText(df.format(thueMoi));
                phieuTraPhongDialog.lbl_TienCuaTongTienThanhToanTrongPnlTongTienThanhToan.setText(df.format(tongThanhToanMoi));
            });

            phieuTraPhongDialog.rdbtn_ChuyenKhoan.addActionListener(e -> {
                // Ẩn tiền mặt
                phieuTraPhongDialog.lbl_TienKhachDua.setVisible(false);
                phieuTraPhongDialog.txt_TienKhachDua.setVisible(false);
                phieuTraPhongDialog.lbl_TienTraLaiKhach.setVisible(false);
                phieuTraPhongDialog.txt_TienTraLai.setVisible(false);


                // Hiện QR
                phieuTraPhongDialog.lbl_QR.setVisible(true);

                // Cập nhật text phương thức
                phieuTraPhongDialog.lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien
                        .setText("Chuyển khoản");
                phieuTraPhongDialog.lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setText(df.format(tongThanhToan));
                phieuTraPhongDialog.lbl_TienCuaTienTraLaiKhachTrongPnlTongTien.setText("0 VND");
                phieuTraPhongDialog.revalidate();  // Tính toán lại layout
                phieuTraPhongDialog.repaint();     // Vẽ lại UI
            });


            phieuTraPhongDialog.rdbtn_TienMat.addActionListener(e -> {
                // Hiện tiền mặt
                phieuTraPhongDialog.lbl_TienKhachDua.setVisible(true);
                phieuTraPhongDialog.txt_TienKhachDua.setVisible(true);
                phieuTraPhongDialog.lbl_TienTraLaiKhach.setVisible(true);
                phieuTraPhongDialog.txt_TienTraLai.setVisible(true);

                // Ẩn QR
                phieuTraPhongDialog.lbl_QR.setVisible(false);

                // Cập nhật text phương thức
                phieuTraPhongDialog.lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien
                        .setText("Tiền mặt");
                phieuTraPhongDialog.lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.setText("0 VND");
                phieuTraPhongDialog.revalidate();  // Tính toán lại layout
                phieuTraPhongDialog.repaint();     // Vẽ lại UI
            });

            // Nhấn nút xác nhận
            phieuTraPhongDialog.btnXacNhan.addActionListener(e -> {
                if (!phieuTraPhongDialog.rdbtn_ChuyenKhoan.isSelected() && !phieuTraPhongDialog.rdbtn_TienMat.isSelected()) {
                    baoLoi("Vui lòng chọn phương thức thanh toán");
                    return;
                }
                if (phieuTraPhongDialog.rdbtn_TienMat.isSelected() && phieuTraPhongDialog.txt_TienKhachDua.getText().isEmpty()) {
                    baoLoi("Vui lòng nhập tiền khách đưa");
                    return;
                }
                String txt = phieuTraPhongDialog.txt_TienKhachDua.getText();

                double tienNhan = phieuTraPhongDialog.rdbtn_ChuyenKhoan.isSelected()
                        ? tongThanhToan
                        : Double.parseDouble(
                        txt.replace("VND", "")
                                .replace(",", "")
                                .trim()
                );

                // thêm hóa đơn, cthd
                double thanhTienPhong=0;
                double tienGiamVoucher=0;
                for (ChiTietHoaDon ct:dsCTHD) {
                    if(ct.getPhong().getMaPhong().equalsIgnoreCase(phong.getMaPhong())){
                        thanhTienPhong=ct.getPhong().getGiaPhong()*ct.getSoNgayO();
                        if(ct.getKhuyenMai()!=null){
                            String maKM=ct.getKhuyenMai().getMaKM();
                            if(maKM!=null&&!maKM.isEmpty()){
                                KhuyenMai km =khuyenMaiDao.getKhuyenMaiTheoMa(maKM);
                                if(km!=null){
                                    tienGiamVoucher=thanhTienPhong*km.getTyLeGiam(); break;
                                }
                            }
                        }
                    }
                }
                ArrayList<ChiTietHoaDon> dsCTHDMoi= new ArrayList<>();
                for (int i=0;i<phieuTraPhongDialog.model.getRowCount();i++) {
                    int soNgayO= Integer.parseInt(phieuTraPhongDialog.table.getValueAt(i,3)+"");
                    ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(phong, thanhTienPhong, tienGiamVoucher, soNgayO);
                    dsCTHDMoi.add(chiTietHoaDon);
                }
                int soNgayO1= Integer.parseInt(phieuTraPhongDialog.table.getValueAt(0,3)+"");
                ChiTietHoaDon cthd=new ChiTietHoaDon(phong, thanhTienPhong, tienGiamVoucher, soNgayO1);
                String maHD= hoaDonDao.taoMaHDMoi();
                PhuongThucThanhToan pttt= phieuTraPhongDialog.rdbtn_ChuyenKhoan.isSelected() ? PhuongThucThanhToan.ChuyenKhoan:PhuongThucThanhToan.TienMat;
                String sdtKH=hoaDon.getKhachHang().getSdt();
                KhachHang khachHang=khachHangServies.TimKhachHang(sdtKH,"SDT");

                LocalDateTime ngayHomnay = LocalDateTime.now();

                String tienNhanCoDuoiVND = phieuTraPhongDialog.lbl_TienCuaTienNhanTuKhachTrongPnlTongTien.getText();
                double tienNhanLuu = Double.parseDouble(tienNhanCoDuoiVND.replace("VND","").trim());
                HoaDon hoaDonMoi= new HoaDon(maHD,ngayHomnay,pttt,TrangThaiHoaDon.HoaDonHoanThanh,tongThanhToan,tongTienPhong,tienNhanLuu,thue,phiDoiPhong,khachHang,dsCTHDMoi,new NhanVien("NV24033","Đỗ Quốc Khoa"));

                hoaDonDao.tachHoaDonMotPhong(hoaDonMoi, cthd,hoaDon.getMaHD());

                String tienGiamVCCoDuoiVND = phieuTraPhongDialog.lbl_TienCuaKhuyenMaiPnlTongTien.getText();
                double tienGiamVC = Double.parseDouble(tienGiamVCCoDuoiVND.replace("VND","").trim());


                String tienGiamTheoHangKHCoDuoiVND = phieuTraPhongDialog.lbl_TienCuaKhuyenMaiTheoHangKH.getText();
                double tienGiamTheoHangKH = Double.parseDouble(tienGiamTheoHangKHCoDuoiVND.replace("VND","").trim());

                double tienGiamHangKH=tienGiamVC+tienGiamTheoHangKH;

                HoaDon hoaDonPDF= new HoaDon(maHD,ngayHomnay,pttt,TrangThaiHoaDon.HoaDonHoanThanh,tongThanhToan,tongTienPhong,tienNhanLuu,thue,phiDoiPhong,khachHang,dsCTHDMoi,new NhanVien("NV24033","Đỗ Quốc Khoa"),tienGiamHangKH);

                HoaDonPDFUtil.xuatHoaDonPDF(hoaDonPDF);


                // Cập nhật điểm tích lũy

                double diemCongThem = tongThanhToan / 1000000;
                double diemTL = kh.getDiemTichLuy() + diemCongThem;
                String maKH = kh.getMaKH();

                khachHangServies.capNhatDiemTichLuy(maKH, diemTL);
                thueDatPhongController.refreshDanhSachPhong();

                phieuTraPhongDialog.dispose();
            });
            // nhấn nút hủy
            phieuTraPhongDialog.btnHuy.addActionListener(e -> {
                if (JOptionPane.showConfirmDialog(phieuTraPhongDialog,
                        "Bạn có chắc chắn muốn hủy?", "Xác nhận",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                    phieuTraPhongDialog.dispose();
            });

            return true;
        } catch (Exception e) {
            baoLoi("Lỗi hệ thống khi trả phòng");
            return false;
        }
    }

    public void chinhSuaPhuongThucThanhToan() {
        phieuTraPhongDialog.lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setText(phieuTraPhongDialog.rdbtn_ChuyenKhoan.isSelected() ? "Chuyển khoản" : "Tiền mặt");
    }

    public void baoLoi(String s) {
        JOptionPane.showMessageDialog(null, s);
    }
}
