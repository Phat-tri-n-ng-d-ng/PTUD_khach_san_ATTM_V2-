package database.dao;


import database.connectDB.ConnectDB;
import entitys.LoaiPhong;
import entitys.Phong;
import enums.TrangThaiPhong;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class PhongDao {
    public ArrayList<Phong> getDanhSachPhong(){
        Connection con = ConnectDB.getConnection();
        ArrayList<Phong> dsp= new ArrayList<>();

        try {
//            CallableStatement st = con.prepareCall("{call getDanhSachPhong}");
//            ResultSet rs = st.executeQuery();
            Statement st = con.createStatement();
            ResultSet rs =st.executeQuery("select maPhong,lp.maLoaiPhong,lp.tenLoaiPhong,tang,soPhong,sucChuaToiDa,giaPhong,tienCoc,trangThaiPhong " +
                    "from Phong p join LoaiPhong lp on p.maLoaiPhong=lp.maLoaiPhong ");

            while (rs.next()){
                LoaiPhong loaiPhong= new LoaiPhong(rs.getString(2),rs.getString(3));
                Phong p = new Phong(rs.getString("maPhong"),loaiPhong,
                        rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getDouble(7),
                        rs.getDouble(8),TrangThaiPhong.valueOf(rs.getString(9)));
                dsp.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(con);
        }
        return dsp;
    }

    public boolean themPhong(Phong phong){
        Connection con = ConnectDB.getConnection();
        int n=0;

        try {
//            CallableStatement st = con.prepareCall("{call themPhong(?,?,?,?,?,?)}");
            PreparedStatement st = con.prepareStatement("insert into Phong values (?,?,?,?,?,?,?,?)");
            st.setString(1,phong.getMaPhong());
            st.setInt(2,phong.getTang());
            st.setInt(3,phong.getSoPhong());
            st.setInt(4,phong.getSucChuaToiDa());
            st.setDouble(5,phong.getGiaPhong());
            st.setDouble(6,phong.getTienCoc());
            st.setString(7,phong.getLoaiPhong().getMaLoaiPhong());
            st.setString(8,phong.getTrangThai().name());
            n= st.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi thêm phòng: " + e.getMessage());
            return false;
//            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(con);
        }
        return n>0;

    }
    public boolean capNhatPhong(Phong p){
        Connection con = ConnectDB.getConnection();
        int n=0;
        try {
//            CallableStatement st = con.prepareCall("{call capNhatThongTinPhong (?,?,?,?,?,?)}");
            PreparedStatement st =con.prepareStatement("update Phong " +
                    "set tang=?,soPhong=?,sucChuaToiDa=?,giaPhong=?,tienCoc=?,maLoaiPhong=?,trangThaiPhong=? " +
                    "where maPhong=?");

            st.setInt(1,p.getTang());
            st.setInt(2,p.getSoPhong());
            st.setInt(3,p.getSucChuaToiDa());
            st.setDouble(4,p.getGiaPhong());
            st.setDouble(5,p.getTienCoc());
            st.setString(6,p.getLoaiPhong().getMaLoaiPhong());
            st.setString(7,p.getTrangThai().name());
            st.setString(8,p.getMaPhong());
            n=st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(con);
        }
        return n>0;
    }

    public boolean capNhatTrangThaiPhong(String ma, TrangThaiPhong tt){
        Connection con = ConnectDB.getConnection();
        int n=0;
        try {
//            CallableStatement st = con.prepareCall("{call capNhatThongTinPhong (?,?,?,?,?,?)}");
            PreparedStatement st =con.prepareStatement("update Phong " +
                    "set trangThaiPhong=? " +
                    "where maPhong=?");
            st.setString(1,tt.name());
            st.setString(2,ma);
            n=st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectDB.closeConnection(con);
        }
        return n>0;
    }

    public Phong timPhongBangMa(String ma) {
        Connection con = ConnectDB.getConnection();
        Phong p = null;
        try {
//            CallableStatement st = con.prepareCall("{call timPhongBangMa(?)}");
            PreparedStatement st = con.prepareStatement("select maPhong,lp.maLoaiPhong,lp.tenLoaiPhong,tang,soPhong,sucChuaToiDa,giaPhong,tienCoc,trangThaiPhong " +
                    "from Phong p join LoaiPhong lp on p.maLoaiPhong=lp.maLoaiPhong " +
                    "where maPhong=?");
            st.setString(1,ma);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                LoaiPhong loaiPhong= new LoaiPhong(rs.getString(2),rs.getString(3));
                p = new Phong(rs.getString("maPhong"),loaiPhong,
                        rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getDouble(7),
                        rs.getDouble(8),TrangThaiPhong.valueOf(rs.getString(9)));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            ConnectDB.closeConnection(con);
        }
        return p;

    }

    public ArrayList<Phong> locPhongTheoLoai(String chuoiLoaiPhongDaChon) {
        Connection con = ConnectDB.getConnection();
        ArrayList<Phong> dsp = new ArrayList<>();
        try {
//            CallableStatement st = con.prepareCall("{call locTheoLoaiPhong (?)}");
            PreparedStatement st = con.prepareStatement("select maPhong,lp.maLoaiPhong,lp.tenLoaiPhong,tang,soPhong,sucChuaToiDa,giaPhong,tienCoc,trangThaiPhong " +
                    "from Phong p join LoaiPhong lp on p.maLoaiPhong=lp.maLoaiPhong " +
                    "where tenLoaiPhong in (" + chuoiLoaiPhongDaChon + ") order by maPhong asc");
            ResultSet rs =st.executeQuery();
            while (rs.next()){
                LoaiPhong loaiPhong= new LoaiPhong(rs.getString(2),rs.getString(3));
                Phong p = new Phong(rs.getString("maPhong"),loaiPhong,
                        rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getDouble(7),
                        rs.getDouble(8),TrangThaiPhong.valueOf(rs.getString(9)));
                dsp.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(con);
        }
        return dsp;
    }

    public ArrayList<Phong> locPhongTheoLoai2(String s) {
        Connection con = ConnectDB.getConnection();
        ArrayList<Phong> dsp = new ArrayList<>();

        String sql = "SELECT maPhong, lp.maLoaiPhong, lp.tenLoaiPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, trangThaiPhong " +
                "FROM Phong p JOIN LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong " +
                "WHERE lp.tenLoaiPhong = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, s);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                LoaiPhong loaiPhong= new LoaiPhong(rs.getString(2),rs.getString(3));
                Phong p = new Phong(rs.getString("maPhong"),loaiPhong,
                        rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getDouble(7),
                        rs.getDouble(8),TrangThaiPhong.valueOf(rs.getString(9)));
                dsp.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(con);
        }

        return dsp;
    }
}