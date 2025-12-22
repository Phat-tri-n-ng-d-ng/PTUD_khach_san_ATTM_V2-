package database.dao;


import database.connectDB.ConnectDB;
import entitys.LoaiPhong;
import entitys.NguoiO;
import entitys.Phong;
import enums.TrangThaiPhong;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

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
        	con.setAutoCommit(true); //Test loi mau
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
    public ArrayList<Phong> getPhongBangMa(String maHD) {
        Connection con = ConnectDB.getConnection();
        ArrayList<Phong> dsP = new ArrayList<>();

        String sql = 
            "SELECT p.maPhong, p.tang, p.soPhong, lp.tenLoaiPhong, p.giaPhong " +
            "FROM ChiTietHoaDon cthd " +
            "JOIN Phong p ON cthd.maPhong = p.maPhong " +
            "JOIN LoaiPhong lp ON lp.maLoaiPhong = p.maLoaiPhong " +
            "WHERE cthd.maHD = ?";

        try (PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, maHD);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                LoaiPhong loaiPhong = new LoaiPhong(rs.getString("tenLoaiPhong"));
                Phong p = new Phong(
                        rs.getString("maPhong"),
                        loaiPhong,
                        rs.getInt("tang"),
                        rs.getInt("soPhong"),
                        rs.getDouble("giaPhong")
                );
                dsP.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(con);
        }

        return dsP;
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
    public boolean doiPhong(String maHD, String maPhongCu, String maPhongMoi, double phiDoiPhong) {
        Connection con = null;
        try {
            con =  ConnectDB.getConnection();
            con.setAutoCommit(false);

            PreparedStatement ps1 = con.prepareStatement(
                "UPDATE Phong SET trangThaiPhong = 'Trong' WHERE maPhong = ?"
            );
            ps1.setString(1, maPhongCu);
            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(
                "UPDATE Phong SET trangThaiPhong = 'DaDat' WHERE maPhong = ?"
            );
            ps2.setString(1, maPhongMoi);
            ps2.executeUpdate();

            PreparedStatement ps3 = con.prepareStatement(
                "UPDATE ChiTietHoaDon SET maPhong = ? WHERE maHD = ? AND maPhong = ?"
            );
            ps3.setString(1, maPhongMoi);
            ps3.setString(2, maHD);
            ps3.setString(3, maPhongCu);
            ps3.executeUpdate();

            PreparedStatement ps4 = con.prepareStatement(
                "UPDATE HoaDon SET phiDoiPhong = ? WHERE maHD = ?"
            );
            ps4.setDouble(1, phiDoiPhong);
            ps4.setString(2, maHD);
            ps4.executeUpdate();

            con.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
        	try {
        		con.setAutoCommit(true);
			} catch (Exception e2) {
				// TODO: handle exception
			}
			ConnectDB.closeConnection(con);
		}
    }
    public boolean nhanPhong(String maHD, ArrayList<NguoiO> dsNguoiO, String maP) {
        Connection con = ConnectDB.getConnection();
        try {
            con.setAutoCommit(false);

            // 1. Update Phong - cập nhật tất cả phòng trong hóa đơn
            String sqlPhong = "UPDATE p " +
                             "SET p.trangThaiPhong = N'DangSuDung' " +
                             "FROM Phong p " +
                             "INNER JOIN ChiTietHoaDon cthd ON p.maPhong = cthd.maPhong " +
                             "WHERE cthd.maHD = ?";
            PreparedStatement psPhong = con.prepareStatement(sqlPhong);
            psPhong.setString(1, maHD);
            psPhong.executeUpdate();

            // 2. Update CTHD - cập nhật tất cả phòng trong hóa đơn
            String sqlCTHD = "UPDATE ChiTietHoaDon SET ngayNhanPhong = GETDATE() WHERE maHD = ?";
            PreparedStatement psCTHD = con.prepareStatement(sqlCTHD);
            psCTHD.setString(1, maHD);
            psCTHD.executeUpdate();

            // 3. Update HoaDon với JOIN để đảm bảo có phòng trong hóa đơn
            String sqlHD = "UPDATE hd " +
                          "SET hd.trangThaiHD = N'PhieuThuePhong' " +
                          "FROM HoaDon hd " +
                          "INNER JOIN ChiTietHoaDon cthd ON hd.maHD = cthd.maHD " +
                          "WHERE hd.maHD = ? AND cthd.maPhong = ?";
            PreparedStatement psHD = con.prepareStatement(sqlHD);
            psHD.setString(1, maHD);
            psHD.setString(2, maP); // Sử dụng phòng chính để xác nhận
            psHD.executeUpdate();

            // 4. Insert NguoiO
            String sqlNguoiO = "INSERT INTO NguoiO(soCCCD, tenNguoiO, ngaySinh, gioiTinh, sdt, maHD, maPhong) " +
                              "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement psNguoiO = con.prepareStatement(sqlNguoiO);

            for (NguoiO no : dsNguoiO) {
                psNguoiO.setString(1, no.getCCCD());
                psNguoiO.setString(2, no.getHoTen());
                psNguoiO.setDate(3, java.sql.Date.valueOf(no.getNgaySinh()));
                psNguoiO.setBoolean(4, no.isGioiTinh());
                psNguoiO.setString(5, no.getSDT());
                psNguoiO.setString(6, no.getHoaDon().getMaHD());
                psNguoiO.setString(7, no.getPhong().getMaPhong());

                psNguoiO.addBatch();
            }

            psNguoiO.executeBatch();

            con.commit();
            return true;

        } catch (Exception e) {
            try { con.rollback(); } catch (Exception ex) {}
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.setAutoCommit(true);
                ConnectDB.closeConnection(con);
            } catch (Exception e) {}
        }
    }
    private synchronized String taoMaHuy(Connection con) throws SQLException {
        LocalDate today = LocalDate.now();
        String prefix = "PH" + String.format("%02d%02d%02d", 
            today.getYear() % 100, today.getMonthValue(), today.getDayOfMonth());
        
        // Kiểm tra mã đã tồn tại và tìm số tiếp theo
        String sql = "SELECT MAX(maHuy) as maxMa FROM HoaDonHuyPhong WHERE maHuy LIKE ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, prefix + "%");
            ResultSet rs = ps.executeQuery();
            
            int nextNum = 1;
            if (rs.next()) {
                String maxMa = rs.getString("maxMa");
                if (maxMa != null && maxMa.startsWith(prefix)) {
                    // Lấy 3 số cuối của mã hiện tại
                    String lastThree = maxMa.substring(maxMa.length() - 3);
                    try {
                        int currentNum = Integer.parseInt(lastThree);
                        nextNum = currentNum + 1;
                    } catch (NumberFormatException e) {
                        nextNum = 1;
                    }
                }
            }
            
            // Nếu nextNum vượt quá 999, thêm phần timestamp để đảm bảo duy nhất
            if (nextNum > 999) {
                long timestamp = System.currentTimeMillis() % 1000000;
                return prefix + String.format("%06d", timestamp);
            }
            
            return prefix + String.format("%03d", nextNum);
        }
    }
    public boolean huyPhong(String maHD, String maPhong, String lyDoHuy, String maKH) {
        Connection con = null;
        try {
            con = ConnectDB.getConnection();
            con.setAutoCommit(false);
            String maHuy = taoMaHuy(con);
            
            
            
            //Cập nhật trạng thái phòng
            String sqlUpdate = "UPDATE p\n" +
                    "SET p.trangThaiPhong = N'Trong' \n" +
                    "FROM Phong p\n" +
                    "INNER JOIN ChiTietHoaDon cthd ON p.maPhong = cthd.maPhong\n" +
                    "WHERE p.maPhong = ? AND cthd.maHD = ?";
            try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {
                psUpdate.setString(1, maPhong);
                psUpdate.setString(2, maHD);
                psUpdate.executeUpdate();
            }
          //Xóa chi tiết hóa đơn
            String sqlDelete = "DELETE FROM ChiTietHoaDon WHERE maHD = ? AND maPhong = ?";
            try (PreparedStatement psDelete = con.prepareStatement(sqlDelete)) {
                psDelete.setString(1, maHD);
                psDelete.setString(2, maPhong);
                if (psDelete.executeUpdate() == 0) {
                    con.rollback();
                    return false;
                }
            }
            
            //INSERT vào HoaDonHuyPhong (chỉ 5 cột)
            String sqlInsert = "INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) " +
                    "VALUES (?, GETDATE(), ?, ?, ?)";
            try (PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {
                psInsert.setString(1, maHuy);           // maHuy
                psInsert.setString(2, lyDoHuy);         // lyDoHuy
                psInsert.setString(3, maKH);            // maKH
                psInsert.setString(4, maHD);            // maHD
                psInsert.executeUpdate();
            }
            
            con.commit();
            return true;
            
        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
        	try {
        		con.setAutoCommit(true);
			} catch (Exception e2) {
				// TODO: handle exception
			}
            ConnectDB.closeConnection(con);
        }
    }
     
}