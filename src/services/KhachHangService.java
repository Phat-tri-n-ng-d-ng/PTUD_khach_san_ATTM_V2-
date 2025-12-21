package services;

import database.dao.KhachHangDao;
import entitys.KhachHang;

import java.util.ArrayList;

public class KhachHangService {
    private KhachHangDao khachHangDao;

    public  KhachHangService(){
        khachHangDao = new KhachHangDao();
    }

    public ArrayList<KhachHang> getTatCaKhachHang(){
        return khachHangDao.getTatCaKhachHang();
    }

    public String getMaHang(String tenHang) {
        return khachHangDao.getMaHang(tenHang);
    }

    public int getSoLuongKhachHang() {
        return khachHangDao.getSoLuongKhachHang();
    }

    public boolean themKhachHang(KhachHang khachHang) {
        return khachHangDao.themKhachHang(khachHang);
    }

    public ArrayList<KhachHang> getKhachHangTheoHang(String hangKhachHang){
        return khachHangDao.getKhachHangTheoHang(hangKhachHang);
    }

    public boolean CapNhatKhachHang(KhachHang khachHang) {
        return khachHangDao.CapNhatKhachHang(khachHang);
    }
    public KhachHang TimKhachHang(String keyword, String type){
        return khachHangDao.TimKhachHang(keyword,type);

    }
    public double timPhanTramGiamCuaHangKH(String maKh){
        return khachHangDao.timPhanTramGiamCuaHangKH(maKh);
    }
    public boolean capNhatDiemTichLuy(String maKH, double diemTL){
        return khachHangDao.capNhatDiemTichLuy(maKH,diemTL);
    }
//    public KhachHang TimKhachHangTheoMaPhongvaTrangThaiHoaDon(String maPhong,String trangThaiHD){
//        return khachHangDao.timKhachHangTheoMaPhongvaTrangThaiHoaDon(maPhong,trangThaiHD);
//    }
}
