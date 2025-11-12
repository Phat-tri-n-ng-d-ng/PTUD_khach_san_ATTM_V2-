package services;

import database.dao.NhanVienDao;
//import database.TaiKhoanDao;

import entitys.NhanVien;
//import entity.TaiKhoan;

import java.util.ArrayList;

public class NhanVienService {
    private NhanVienDao nhanVienDao;

    public  NhanVienService(){
        nhanVienDao = new NhanVienDao();
    }

    public ArrayList<NhanVien>  getTatCaNhanVien(){
        return nhanVienDao.getTatCaNhanVien();
    }

    public boolean themNhanVien(NhanVien nhanVien){
        return nhanVienDao.themNhanVien(nhanVien);
    }

    public int getSoLuongNhanVien() {
        return nhanVienDao.getSoLuongNhanVien();
    }

    public boolean CapNhatNhanVien(NhanVien nhanVien) {
        return nhanVienDao.CapNhatNhanVien(nhanVien);
    }

    public ArrayList<NhanVien>  getNhanVienTheoChucVu(String chucVu){
        return nhanVienDao.getNhanVienTheoChucVu(chucVu);
    }

    public NhanVien TimNhanVien(String keyword, String type) {
        return nhanVienDao.TimNhanVien(keyword,type);
    }

    public boolean TaoTaiKhoanNhanVien(NhanVien nhanVien) {
        return nhanVienDao.TaoTaiKhoanNhanVien(nhanVien);
    }
}