package database.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import database.connectDB.ConnectDB;
import entitys.*;
import enums.PhuongThucThanhToan;
import enums.TrangThaiHoaDon;

public class HoaDonDao {
	public ArrayList<HoaDon> getTatCaHoaDon(){
        ArrayList<HoaDon> dsHoaDon = new ArrayList<>();

        HoaDon hd = null;
        Connection connection = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "select maHD, ngayLap, kh.tenKH, kh.sdt, tongTien, nv.tenNV, trangThaiHD,phuongThucTT from HoaDon hd join KhachHang kh on hd.maKH = kh.maKH  join NhanVien nv on nv.maNV = hd.maNV";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                String maHD = rs.getString("maHD");
                LocalDateTime ngayLap = rs.getTimestamp("ngayLap").toLocalDateTime();
                String tenKH = rs.getString("tenKH");
                String sdt = rs.getString("sdt");
                KhachHang kh = new KhachHang(tenKH,sdt);
                String tenNV = rs.getString("tenNV");
                TrangThaiHoaDon trangThaiHD = TrangThaiHoaDon.valueOf(rs.getString("trangThaiHD"));
                NhanVien nv = new NhanVien();
                nv.setTenNV(tenNV);
                PhuongThucThanhToan pttt = PhuongThucThanhToan.valueOf(rs.getString("phuongThucTT"));
                
                //Lay du lieu tu chi tiet hoa don
                ArrayList<ChiTietHoaDon> dsCTDH = this.getChiTietHoaDon(maHD);
                hd = new HoaDon(maHD, ngayLap, pttt,trangThaiHD , kh, dsCTDH , nv);
                hd.setTongTien();
                dsHoaDon.add(hd);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(connection);
        }
        return dsHoaDon;

    }
	 public ArrayList<ChiTietHoaDon> getChiTietHoaDon(String maHD){
	        ArrayList<ChiTietHoaDon> dsChiTietHoaDon = new ArrayList<ChiTietHoaDon>();
	        Connection connection = null;
	        Phong p = null;
	        try {
	            connection = ConnectDB.getConnection();
	            String sql = " SELECT p.maPhong, cthd.soNgayO, p.giaPhong,thanhTien FROM ChiTietHoaDon cthd JOIN Phong p ON cthd.maPhong = p.maPhong join HoaDon hd on cthd.maHD = hd.maHD where cthd.maHD = ?";
	            PreparedStatement stmt = connection.prepareStatement(sql);
	            stmt.setString(1, maHD);
	            ResultSet rs = stmt.executeQuery();
	            while(rs.next()) {
	                String maPhong = rs.getString("maPhong");
	                double giaPhong = rs.getDouble("giaPhong");
	                p = new Phong(maPhong, giaPhong);
	                int soNgayO = rs.getInt("soNgayO");
	                ChiTietHoaDon cthd =  new ChiTietHoaDon(p, soNgayO);
	                dsChiTietHoaDon.add(cthd);
	            }

	        } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	        }finally {
	            ConnectDB.closeConnection(connection);
	        }
	        return dsChiTietHoaDon;
	    }
	 public ArrayList<KhachHang> getKhachHangTheoHD(String ma){
	        ArrayList<KhachHang> dsKh = new ArrayList<>();
	        KhachHang kh = null;
	        try {
	            Connection connection = ConnectDB.getConnection();
	            String sql = "select kh.maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD,\n" +
                        "diemTichLuy, hang.maHang, hang.tenHang, cthd.ngayNhanPhong, cthd.ngayTraPhong\n" +
                        "from KhachHang kh\n" +
                        "join HoaDon hd on kh.maKH = hd.maKH\n" +
                        "join HangKhachHang hang on hang.maHang = kh.maHang\n" +
                        "join ChiTietHoaDon cthd on cthd.maHD = hd.maHD\n" +
                        "where hd.maHD = ?";
                PreparedStatement st = connection.prepareStatement(sql);
	            st.setString(1, ma);
	            ResultSet rs = st.executeQuery();
                while (rs.next()){
                    String maKH = rs.getString("maKH");
                    String tenKH = rs.getString("tenKH");
                    LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                    Boolean gioiTinh = rs.getBoolean("gioiTinh");
                    String sdt = rs.getString("sdt");
                    String email = rs.getString("email");
                    String soCCCD = rs.getString("soCCCD");
                    int diemTichLuy = rs.getInt("diemTichLuy");
                    String maHang = rs.getString("maHang");
                    String tenHang = rs.getString("tenHang");
                    entitys.HangKhachHang hangKhachHang = new HangKhachHang(maHang,tenHang);
                    kh = new KhachHang(maKH, tenKH, gioiTinh, ngaySinh, email, sdt,
                            soCCCD, diemTichLuy,hangKhachHang);
                    dsKh.add(kh);
                }
	        } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	        }
	        return dsKh;
	    }
	 public HoaDon timHoaDonTheoMa(String ma) {
	        Connection connection = ConnectDB.getConnection();
	        HoaDon hd = null;
	        String sql = "select maHD, ngayLap, kh.tenKH, kh.sdt,phuongThucTT, nv.tenNV, tienNhan,hd.trangThaiHD from HoaDon hd join KhachHang kh on hd.maKH = kh.maKH join NhanVien nv on hd.maNV = nv.maNV where maHD = ?"; //co The them ttien tra
	        try {
	            PreparedStatement st = connection.prepareStatement(sql);
	            st.setString(1, ma);
	            ResultSet rs= st.executeQuery();
	            while(rs.next()) {
	                String maHD = rs.getString("maHD");
	                LocalDateTime ngayLap = rs.getTimestamp("ngayLap").toLocalDateTime();
	                String tenKH = rs.getString("tenKH");
	                String sdt = rs.getString("sdt");
	                KhachHang kh = new KhachHang(tenKH,sdt);
	                String tenNV = rs.getString("tenNV");
	                TrangThaiHoaDon trangThaiHD = TrangThaiHoaDon.valueOf(rs.getString("trangThaiHD"));
	                double tienNhan =  rs.getDouble("tienNhan");
	                NhanVien nv = new NhanVien();
	                nv.setTenNV(tenNV);
	                PhuongThucThanhToan pttt = PhuongThucThanhToan.valueOf(rs.getString("phuongThucTT"));
	                //Lay du lieu tu chi tiet hoa don
	                ArrayList<ChiTietHoaDon> dsCTDH = this.getChiTietHoaDon(maHD);

	                hd = new HoaDon(maHD, ngayLap, pttt, trangThaiHD, kh, dsCTDH , nv, tienNhan);
	                hd.setTongTien();
	                hd.setTienThue();
	                hd.setPhiDoiPhong();
	                hd.setTongTienThanhToan();
	            }
	        } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	        }finally {
	            ConnectDB.closeConnection(connection);
	        }
	        return hd;
	    }
	 public ArrayList<ChiTietHoaDon> timPhongTheoMaHD(String ma) {
	        Connection connection = ConnectDB.getConnection();
	        ChiTietHoaDon hd = null;
	        Phong p = null;
	        ArrayList<ChiTietHoaDon> dsctHD = new ArrayList<>();
	        
	        String sql = "select p.maPhong, lp.tenLoaiPhong, p.sucChuaToiDa,p.giaPhong,p.tienCoc,cthd.soNgayO from Phong p join ChiTietHoaDon cthd on p.maPhong = cthd.maPhong join LoaiPhong lp on lp.maLoaiPhong = p.maLoaiPhong  where cthd.maHD= ?";
	        try {
	            PreparedStatement st = connection.prepareStatement(sql);
	            st.setString(1, ma);
	            ResultSet rs= st.executeQuery();
	            while(rs.next()) {
	                String maP = rs.getString("maPhong");
	                String tenLP = rs.getString("tenLoaiPhong");
	                int sltd = rs.getInt("sucChuaToiDa");
	                double giaP = rs.getDouble("giaPhong");
	                double tienCoc = rs.getDouble("tienCoc"); 
	                int soNgayO = rs.getInt("soNgayO");
	                LoaiPhong lp = new LoaiPhong();
	                lp.setTenLoaiPhong(tenLP);
	                p = new Phong(maP, lp, sltd, giaP, tienCoc);
	                hd = new ChiTietHoaDon(p,soNgayO);
	                dsctHD.add(hd);
	            }
	        } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	        }finally {
	            ConnectDB.closeConnection(connection);
	        }
	        return dsctHD;
	    };
	    public ArrayList<NguoiO> getNguoiOTheoPhong(String ma) {
	        Connection connection = ConnectDB.getConnection();
	        NguoiO nO = null;
	        ArrayList<NguoiO> dsNguoiO = new ArrayList<>();
	        String sql = "select * from NguoiO where maPhong = ?";
	        try {
	            PreparedStatement st = connection.prepareStatement(sql);
	            st.setString(1, ma);
	            ResultSet rs= st.executeQuery();
	            while(rs.next()) {
	                String tenNO = rs.getString("tenNguoiO");
	                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
	                boolean gioiTinh = rs.getBoolean("gioiTinh");
	                String sdt = rs.getString("sdt");
	                String soCCCD = rs.getString("soCCCD");
	                nO = new NguoiO(tenNO, ngaySinh, sdt, soCCCD, gioiTinh);
	                dsNguoiO.add(nO);
	                
	            }
	        } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	        }finally {
	            ConnectDB.closeConnection(connection);
	        }
	        return dsNguoiO;
	    };
	    public ArrayList<HoaDon> timHoaDonTheoKhoang(LocalDateTime ngayBD, LocalDateTime ngayKT) {
	        Connection connection = ConnectDB.getConnection();
	        ArrayList<HoaDon> dsHoaDon = new ArrayList<>();

	        HoaDon hd = null;
	        String sql = "select maHD, ngayLap, kh.tenKH, kh.sdt,phuongThucTT, nv.tenNV,hd.trangThaiHD from HoaDon hd join KhachHang kh on hd.maKH = kh.maKH join NhanVien nv on hd.maNV = nv.maNV where CAST(ngayLap AS date) between ? and ?";
	        try {
	            PreparedStatement st = connection.prepareStatement(sql);
	            java.sql.Date ngayBDSQL = java.sql.Date.valueOf(ngayBD.toLocalDate());
	            java.sql.Date ngayKTSQL = java.sql.Date.valueOf(ngayKT.toLocalDate());
	            st.setDate(1, ngayBDSQL);
	            st.setDate(2, ngayKTSQL);
	            ResultSet rs= st.executeQuery();
	            while(rs.next()) {
	                String maHD = rs.getString("maHD");
	                LocalDateTime ngayLap = rs.getTimestamp("ngayLap").toLocalDateTime();
	                String tenKH = rs.getString("tenKH");
	                String sdt = rs.getString("sdt");
	                KhachHang kh = new KhachHang(tenKH,sdt);
	     
	                TrangThaiHoaDon trangThaiHD = TrangThaiHoaDon.valueOf(rs.getString("trangThaiHD"));
	           
	                String tenNV = rs.getString("tenNV");
	                NhanVien nv = new NhanVien();
	                nv.setTenNV(tenNV);
	                PhuongThucThanhToan pttt = PhuongThucThanhToan.valueOf(rs.getString("phuongThucTT"));
	                //Lay du lieu tu chi tiet hoa don
	                ArrayList<ChiTietHoaDon> dsCTDH = this.getChiTietHoaDon(maHD);
	                hd = new HoaDon(maHD, ngayLap, pttt, trangThaiHD, kh, dsCTDH , nv);
	                hd.setTongTien();
	                dsHoaDon.add(hd);
	            }
	        } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	        }finally {
	            ConnectDB.closeConnection(connection);
	        }
	        return dsHoaDon;
	    };
	    public HoaDon timHoaDonTheoSDT(String SDT) {
	        Connection connection = ConnectDB.getConnection();
	        HoaDon hd = null;
	        String sql = "select hd.maHD, ngayLap, kh.tenKH, kh.sdt,phuongThucTT, nv.tenNV, tienNhan,hd.trangThaiHD from HoaDon hd join KhachHang kh on hd.maKH = kh.maKH join NhanVien nv on hd.maNV = nv.maNV where kh.sdt = ?";
	        try {
	            PreparedStatement st = connection.prepareStatement(sql);
	            st.setString(1, SDT);
	            ResultSet rs= st.executeQuery();
	            while(rs.next()) {
	                String maHD = rs.getString("maHD");
	                LocalDateTime ngayLap = rs.getTimestamp("ngayLap").toLocalDateTime();
	                String tenKH = rs.getString("tenKH");
	                String sdt = rs.getString("sdt");
	                KhachHang kh = new KhachHang(tenKH,sdt);
	                String tenNV = rs.getString("tenNV");
	                TrangThaiHoaDon trangThaiHD = TrangThaiHoaDon.valueOf(rs.getString("trangThaiHD"));
	                double tienNhan =  rs.getDouble("tienNhan");
	               
	                NhanVien nv = new NhanVien();
	                nv.setTenNV(tenNV);
	                PhuongThucThanhToan pttt = PhuongThucThanhToan.valueOf(rs.getString("phuongThucTT"));
	                //Lay du lieu tu chi tiet hoa don
	                ArrayList<ChiTietHoaDon> dsCTDH = this.getChiTietHoaDon(maHD);
	                hd = new HoaDon(maHD, ngayLap, pttt, trangThaiHD, kh, dsCTDH , nv, tienNhan);
	                hd.setTongTien();
	                hd.setTienThue();
	                hd.setPhiDoiPhong();
	                hd.setTongTienThanhToan();
	            }
	        } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	        }finally {
	            ConnectDB.closeConnection(connection);
	        }
	        return hd;
	    };
	    public ArrayList<HoaDon> timHoaDonTheoNgay(LocalDateTime ngay) {
	        Connection connection = ConnectDB.getConnection();
	        HoaDon hd = null;
	        ArrayList<HoaDon> dsHoaDon = new ArrayList<>();
	        String sql = "select maHD, ngayLap, kh.tenKH, kh.sdt, phuongThucTT, nv.tenNV,hd.trangThaiHD from HoaDon hd join KhachHang kh on hd.maKH = kh.maKH join NhanVien nv on hd.maNV = nv.maNV where CAST(ngayLap AS date) = ?";
	        try {
	            PreparedStatement st = connection.prepareStatement(sql);
	            java.sql.Date ngaySQL = java.sql.Date.valueOf(ngay.toLocalDate());
	            st.setDate(1, ngaySQL);
	            ResultSet rs= st.executeQuery();
	            while(rs.next()) {
	                String maHD = rs.getString("maHD");
	                LocalDateTime ngayLap = rs.getTimestamp("ngayLap").toLocalDateTime();
	                String tenKH = rs.getString("tenKH");
	                String sdt = rs.getString("sdt");
	                KhachHang kh = new KhachHang(tenKH,sdt);
	                TrangThaiHoaDon trangThaiHD = TrangThaiHoaDon.valueOf(rs.getString("trangThaiHD"));
	                String tenNV = rs.getString("tenNV");
	                NhanVien nv = new NhanVien();
	                nv.setTenNV(tenNV);
	                PhuongThucThanhToan pttt = PhuongThucThanhToan.valueOf(rs.getString("phuongThucTT"));
	                //Lay du lieu tu chi tiet hoa don
	                ArrayList<ChiTietHoaDon> dsCTDH = this.getChiTietHoaDon(maHD);
	                hd = new HoaDon(maHD, ngayLap, pttt, trangThaiHD, kh, dsCTDH , nv);
	                hd.setTongTien();
	                dsHoaDon.add(hd);

	            }
	        } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	        }finally {
	            ConnectDB.closeConnection(connection);
	        }
	        return dsHoaDon;
	    };
	    public ArrayList<HoaDon> getHoaDonTheoTrangThai(String trangThaiHD){
	        Connection connection = ConnectDB.getConnection();
	        ArrayList<HoaDon> dsHoaDon = new ArrayList<>();
	        String sql = "{CALL locHoaDonTheoTrangThai(?)}";
	        HoaDon hd = null;
	        try {
	            CallableStatement st = connection.prepareCall(sql);
	            st.setString(1, trangThaiHD);
	            ResultSet rs = st.executeQuery();
	            while(rs.next()) {
	                String maHD = rs.getString("maHD");
	                LocalDateTime ngayLap = rs.getTimestamp("ngayLap").toLocalDateTime();
	                String tenKH = rs.getString("tenKH");
	                String sdt = rs.getString("sdt");
	                KhachHang kh = new KhachHang(tenKH,sdt);
	                String tenNV = rs.getString("tenNV");
	                TrangThaiHoaDon trangThaiHD1 = TrangThaiHoaDon.valueOf(rs.getString("trangThaiHD"));
	                NhanVien nv = new NhanVien();
	                nv.setTenNV(tenNV);
	                PhuongThucThanhToan pttt = PhuongThucThanhToan.valueOf(rs.getString("phuongThucTT"));
	                //Lay du lieu tu chi tiet hoa don
	                ArrayList<ChiTietHoaDon> dsCTDH = this.getChiTietHoaDon(maHD);
	                hd = new HoaDon(maHD, ngayLap, pttt,trangThaiHD1 , kh, dsCTDH , nv);
	                hd.setTongTien();
	                dsHoaDon.add(hd);
	            }

	        } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	        }finally {
	            ConnectDB.closeConnection(connection);
	        }
	        return dsHoaDon;


	    }

}
