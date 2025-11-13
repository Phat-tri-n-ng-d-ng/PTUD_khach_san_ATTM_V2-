/*
 * @ (#) LoaiPhongDao.java     1.0     10/21/2025
 *
 *Copyright (c) 2025 IUH. All rights reserved.
 */
package database.dao;




import database.connectDB.ConnectDB;
import entitys.LoaiPhong;

import java.sql.*;
import java.util.ArrayList;

/*
 * @description: This class represents a bank with many bank accounts
 * @author: Anh, Le The Anh
 * @date: 10/21/2025
 * @version: 1.0
 */
public class LoaiPhongDao {


    public ArrayList<LoaiPhong> getDanhSachLoaiPhong(){
        ArrayList<LoaiPhong> dslp = new ArrayList<>();
        Connection con = ConnectDB.getConnection();
        try {
//            CallableStatement st = con.prepareCall("{call}");
            PreparedStatement st = con.prepareStatement("select * from LoaiPhong");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LoaiPhong lp = new LoaiPhong(rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getDouble(4),rs.getInt(5));
                dslp.add(lp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(con);
        }
        return dslp;
//        ConnectDB.closeConnection(con);
    }

//    public int getSoLuongLoaiPhong(){
//        Connection connection = null;
//        int soLuong = 0;
//        try {
//            connection = ConnectDB.getConnection();
//            Statement st = connection.createStatement();
//            ResultSet rs = st.executeQuery("select count(*) from LoaiPhong");
//            if (rs.next()) {
//                soLuong = rs.getInt(1);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            ConnectDB.closeConnection(connection);
//        }
//        return soLuong;
//    }

    public boolean themLoaiPhong(LoaiPhong lp){
        int n=0;
        Connection con = ConnectDB.getConnection();
        try {
//            CallableStatement st = con.prepareCall("{call themLoaiPhong(?,?,?,?,?)}");
            PreparedStatement st = con.prepareStatement("insert into LoaiPhong values(?,?,?,?,?)");
            st.setString(1,lp.getMaLoaiPhong());
            st.setString(2,lp.getTenLoaiPhong());
            st.setDouble(3,lp.getGiaNiemYet());
            st.setDouble(4,lp.getTyLeCoc());
            st.setInt(5,lp.getSucChuaToiThieu());
            n=st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(con);
        }
        //        ConnectDB.closeConnection(con);
        return n>0;

    }
    public boolean capNhatLoaiPhong(LoaiPhong lp){
        int n = 0;
        Connection con = ConnectDB.getConnection();
        try {
//            CallableStatement st = con.prepareCall("{call capNhatLoaiPhong(?,?,?,?,?)}");
            PreparedStatement st = con.prepareStatement(
                    "update LoaiPhong set tenLoaiPhong = ?, giaNiemYet = ?, tyLeCoc = ?, sucChuaToiThieu=? where maLoaiPhong = ?"
            );
            st.setString(1, lp.getTenLoaiPhong());
            st.setDouble(2, lp.getGiaNiemYet());
            st.setDouble(3, lp.getTyLeCoc());
            st.setInt(4,lp.getSucChuaToiThieu());
            st.setString(5, lp.getMaLoaiPhong());
            n = st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(con);
        }
        return n > 0;
    }
    public LoaiPhong getThongTinLoaiPhong(String ten){
        LoaiPhong lp= null;
        Connection con = ConnectDB.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("Select * from LoaiPhong where tenLoaiPhong=?");
            st.setString(1,ten);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                lp = new LoaiPhong(rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getDouble(4),rs.getInt(5));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(con);
        }
        return lp;

    }
}