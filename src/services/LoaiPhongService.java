/*
 * @ (#) LoaiPhongService.java     1.0     10/22/2025
 *
 *Copyright (c) 2025 IUH. All rights reserved.
 */
package services;




import database.dao.LoaiPhongDao;
import entitys.LoaiPhong;

import java.util.ArrayList;

/*
 * @description: This class represents a bank with many bank accounts
 * @author: Anh, Le The Anh
 * @date: 10/22/2025
 * @version: 1.0
 */
public class LoaiPhongService {
    LoaiPhongDao loaiPhongDao = new LoaiPhongDao();
    LoaiPhong lp;

    public LoaiPhongService(){

    }

    public LoaiPhongService(LoaiPhong lp){
        this.lp=lp;
    }

    public ArrayList<LoaiPhong> getDanhSachLoaiPhong(){
        return loaiPhongDao.getDanhSachLoaiPhong();
    }
    public boolean themLoaiPhong(LoaiPhong lp){
        return loaiPhongDao.themLoaiPhong(lp);
    }
//    public int getSoLuongLoaiPhong(){
//        return loaiPhongDao.getSoLuongLoaiPhong();
//    }
    public boolean capNhatLoaiPhong(LoaiPhong lp){
        return loaiPhongDao.capNhatLoaiPhong(lp);
    }
    public LoaiPhong getThongTinLoaiPhong(String ten){
        return loaiPhongDao.getThongTinLoaiPhong(ten);
    }


}