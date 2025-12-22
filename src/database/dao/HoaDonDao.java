package database.dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import database.connectDB.ConnectDB;
import entitys.*;
import enums.PhuongThucThanhToan;
import enums.TrangThaiHoaDon;
import enums.TrangThaiPhong;

public class HoaDonDao {
	//Get 1 thang chi titiet h
	public ChiTietHoaDon getChiTietHoaDon_1(String maHD){
        Connection connection = null;
        ChiTietHoaDon cthd = null;
        Phong p = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "select distinct p.maPhong, cthd.soNgayO, p.giaPhong,thanhTien,maKM, ngayNhanPhong,ngayTraPhong, tienGiam " +
                    "from ChiTietHoaDon cthd JOIN Phong p ON cthd.maPhong = p.maPhong join HoaDon hd on cthd.maHD = hd.maHD where cthd.maHD =?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, maHD);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String maPhong = rs.getString("maPhong");
                double giaPhong = rs.getDouble("giaPhong");
                p = new Phong(maPhong, giaPhong);
                int soNgayO = rs.getInt("soNgayO");
                KhuyenMai km=null;
                if(rs.getString("maKM")!=null) km=new KhuyenMai(rs.getString("maKM"));
                LocalDateTime ngayNhanPhong=rs.getTimestamp("ngayNhanPhong").toLocalDateTime();
                LocalDateTime ngayTraPhong=rs.getTimestamp("ngayTraPhong").toLocalDateTime();
                cthd =  new ChiTietHoaDon(p, soNgayO,km,ngayNhanPhong,ngayTraPhong);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally {
            ConnectDB.closeConnection(connection);
        }
        return cthd;
    }
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
            String sql = "select distinct p.maPhong, cthd.soNgayO, p.giaPhong,thanhTien,maKM, ngayNhanPhong,ngayTraPhong, tienGiam " +
                    "from ChiTietHoaDon cthd JOIN Phong p ON cthd.maPhong = p.maPhong join HoaDon hd on cthd.maHD = hd.maHD where cthd.maHD =?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, maHD);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String maPhong = rs.getString("maPhong");
                double giaPhong = rs.getDouble("giaPhong");
                p = new Phong(maPhong, giaPhong);
                int soNgayO = rs.getInt("soNgayO");
                KhuyenMai km=null;
                if(rs.getString("maKM")!=null) km=new KhuyenMai(rs.getString("maKM"));
                LocalDateTime ngayNhanPhong=rs.getTimestamp("ngayNhanPhong").toLocalDateTime();
                LocalDateTime ngayTraPhong=rs.getTimestamp("ngayTraPhong").toLocalDateTime();
                ChiTietHoaDon cthd =  new ChiTietHoaDon(p, soNgayO,km,ngayNhanPhong,ngayTraPhong);

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
	 

	public KhachHang getKhachHangTheoHD(String ma){
	    KhachHang kh = null;
	    try {
	        Connection connection = ConnectDB.getConnection();
	        String sql = "SELECT kh.maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, " +
	                     "diemTichLuy, hang.maHang, hang.tenHang " +
	                     "FROM KhachHang kh " +
	                     "JOIN HoaDon hd ON kh.maKH = hd.maKH " +
	                     "JOIN HangKhachHang hang ON hang.maHang = kh.maHang " +
	                     "WHERE hd.maHD = ?";
	        
	        PreparedStatement st = connection.prepareStatement(sql);
	        st.setString(1, ma);
	        ResultSet rs = st.executeQuery();
	        
	        if (rs.next()){
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
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return kh;
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
	    public ArrayList<NguoiO> getNguoiOTheoPhong(String ma, String maHD) {
	        Connection connection = ConnectDB.getConnection();
	        NguoiO nO = null;
	        ArrayList<NguoiO> dsNguoiO = new ArrayList<>();
	        String sql = "select * from NguoiO where maPhong = ? and maHD = ?";
	        try {
	            PreparedStatement st = connection.prepareStatement(sql);
	            st.setString(1, ma);
	            st.setString(2, maHD);
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

    public HoaDon timHoaDonTheoPhongDangThue(String maPhong){
        Connection con=ConnectDB.getConnection(); HoaDon hd=null;
        String sql="select top 1 hd.maHD,hd.ngayLap,cthd.ngayNhanPhong,cthd.ngayTraPhong,"
                + "hd.phuongThucTT,hd.trangThaiHD,hd.tongTienThanhToan,hd.tongTien,"
                + "hd.tienGiamHangKH,hd.tienThue,hd.phiDoiPhong,cthd.maKM,"
                + "kh.maKH,kh.tenKH,kh.sdt,kh.soCCCD,"
                + "nv.maNV,nv.tenNV from HoaDon hd "
                + "join ChiTietHoaDon cthd on hd.maHD=cthd.maHD "
                + "join KhachHang kh on hd.maKH=kh.maKH "
                + "join NhanVien nv on nv.maNV=hd.maNV "
                + "where cthd.maPhong=? and hd.trangThaiHD='PhieuThuePhong' "
                + "order by cthd.ngayNhanPhong desc";
        try{
            PreparedStatement st=con.prepareStatement(sql); st.setString(1,maPhong);
            ResultSet rs=st.executeQuery();
            if(rs.next()){
                String maHD=rs.getString("maHD");
                LocalDateTime ngayLap=rs.getTimestamp("ngayLap").toLocalDateTime();
                LocalDateTime ngayNhanPhong=rs.getTimestamp("ngayNhanPhong").toLocalDateTime();
                LocalDateTime ngayTraPhong=rs.getTimestamp("ngayTraPhong").toLocalDateTime();
                PhuongThucThanhToan pTTT=PhuongThucThanhToan.valueOf(rs.getString("phuongThucTT"));
                TrangThaiHoaDon trangThai=TrangThaiHoaDon.valueOf(rs.getString("trangThaiHD"));
                double tongTienThanhToan=rs.getDouble("tongTienThanhToan");
                double tongTien=rs.getDouble("tongTien");
                double tienThue=rs.getDouble("tienThue");
                double phiDoiPhong=rs.getDouble("phiDoiPhong");
                KhachHang kh=new KhachHang(rs.getString("maKH"),rs.getString("tenKH"),rs.getString("sdt"));
                NhanVien nv=new NhanVien(rs.getString("maNV"),rs.getString("tenNV"));
                ArrayList<ChiTietHoaDon> dsCTHD=getChiTietHoaDon(maHD);
                hd=new HoaDon(maHD,ngayLap,pTTT,trangThai,
                        tongTienThanhToan,tongTien,tienThue,phiDoiPhong,kh,dsCTHD,nv);
            }
        }catch(Exception e){e.printStackTrace();}
        finally{ConnectDB.closeConnection(con);}
        return hd;
    }
    public boolean capNhatChiTietHoaDon(Date ngayKT,int soNgayO, double tienGiam,double thanhTien , String maPhong, String maHD){
        Connection con=ConnectDB.getConnection();
        int n=0;
        try {
            PreparedStatement st = con.prepareStatement("update ChiTietHoaDon set ngayTraPhong=?, soNgayO=?, tienGiam=?, thanhTien=?" +
                    " where maPhong=? and maHD=?");
            st.setDate(1, new java.sql.Date(ngayKT.getTime()));
            st.setInt(2,soNgayO);
            st.setDouble(3,tienGiam);
            st.setDouble(4,thanhTien);
            st.setString(5, maPhong);
            st.setString(6, maHD);
            n=st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return n>0;
    }

    public boolean capNhatHoaDon(String phuongThucTT,double tongTien,double tienThue,
                                 double phiDoiPhong,double tongTienThanhToan,
                                 double tienGiamHangKH,double tongTienSauGiam,
                                 double tienNhan,String maHD,TrangThaiHoaDon tthd){
        Connection con=ConnectDB.getConnection();
        int n=0;
        try{
            PreparedStatement st=con.prepareStatement(
                    "update HoaDon set phuongThucTT=?, tongTien=?, tienThue=?, phiDoiPhong=?, tongTienThanhToan=?, " +
                            "tienGiamHangKH=?, tongTienSauGiam=?, tienNhan=? ,trangThaiHD=? where maHD=?"
            );
            st.setString(1,phuongThucTT);
            st.setDouble(2,tongTien);
            st.setDouble(3,tienThue);
            st.setDouble(4,phiDoiPhong);
            st.setDouble(5,tongTienThanhToan);
            st.setDouble(6,tienGiamHangKH);
            st.setDouble(7,tongTienSauGiam);
            st.setDouble(8,tienNhan);
            st.setString(9,tthd.name());
            st.setString(10,maHD);
            n=st.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return n>0;
    }

    public ArrayList<HoaDon> timHoaDonTheoSDT(String SDT) {
    Connection connection = ConnectDB.getConnection();
    ArrayList<HoaDon> dsHoaDon = new ArrayList<>();

    String sql = "select hd.maHD, ngayLap, kh.tenKH, kh.sdt,phuongThucTT, nv.tenNV, tienNhan,hd.trangThaiHD from HoaDon hd join KhachHang kh on hd.maKH = kh.maKH join NhanVien nv on hd.maNV = nv.maNV where kh.sdt = ?";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, SDT);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            String maHD = rs.getString("maHD");
            LocalDateTime ngayLap = rs.getTimestamp("ngayLap").toLocalDateTime();
            String tenKH = rs.getString("tenKH");
            String sdt = rs.getString("sdt");
            KhachHang kh = new KhachHang(tenKH, sdt);
            String tenNV = rs.getString("tenNV");
            TrangThaiHoaDon trangThaiHD = TrangThaiHoaDon.valueOf(rs.getString("trangThaiHD"));
            double tienNhan = rs.getDouble("tienNhan");

            NhanVien nv = new NhanVien();
            nv.setTenNV(tenNV);
            PhuongThucThanhToan pttt = PhuongThucThanhToan.valueOf(rs.getString("phuongThucTT"));
            //Lay du lieu tu chi tiet hoa don
            ArrayList<ChiTietHoaDon> dsCTDH = this.getChiTietHoaDon(maHD);

            HoaDon hd = new HoaDon(maHD, ngayLap, pttt, trangThaiHD, kh, dsCTDH, nv, tienNhan);
            hd.setTongTien();
            hd.setTienThue();
            hd.setPhiDoiPhong();
            hd.setTongTienThanhToan();

            dsHoaDon.add(hd);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        ConnectDB.closeConnection(connection);
    }
    return dsHoaDon;
}

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
			e.printStackTrace();
		} finally {
			ConnectDB.closeConnection(connection);
		}
		return dsHoaDon;

	}


//    public KhachHang getKhachHangTheoMaPhong(String maPhong) {
//        KhachHang kh = null;
//        try {
//            Connection connection = ConnectDB.getConnection();
//            String sql =
//                    "select top 1 kh.maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, " +
//                            "diemTichLuy, hang.maHang, hang.tenHang, cthd.ngayNhanPhong, cthd.ngayTraPhong " +
//                            "from HoaDon hd " +
//                            "join ChiTietHoaDon cthd on hd.maHD = cthd.maHD " +
//                            "join KhachHang kh on hd.maKH = kh.maKH " +
//                            "join HangKhachHang hang on hang.maHang = kh.maHang " +
//                            "where cthd.maPhong = ? and hd.trangThaiHD = 'PhieuThuePhong' " +
//                            "order by cthd.ngayNhanPhong desc";
//
//            PreparedStatement st = connection.prepareStatement(sql);
//            st.setString(1, maPhong);
//            ResultSet rs = st.executeQuery();
//
//            if (rs.next()) { // chỉ lấy 1 khách đang thuê phòng
//                String maKH = rs.getString("maKH");
//                String tenKH = rs.getString("tenKH");
//                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
//                boolean gioiTinh = rs.getBoolean("gioiTinh");
//                String sdt = rs.getString("sdt");
//                String email = rs.getString("email");
//                String soCCCD = rs.getString("soCCCD");
//                int diemTichLuy = rs.getInt("diemTichLuy");
//                String maHang = rs.getString("maHang");
//                String tenHang = rs.getString("tenHang");
//
//                HangKhachHang hangKhachHang = new HangKhachHang(maHang, tenHang);
//                kh = new KhachHang(maKH, tenKH, gioiTinh, ngaySinh, email, sdt,
//                        soCCCD, diemTichLuy, hangKhachHang);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return kh;
//    }
    public void TuDongCapNhatTrangThaiPhong_TheoKhoangNgay(Date ngayBatDau, Date ngayKetThuc) {
    }
    public HoaDon timHoaDonTheoPhongDaDat(String maPhong){
        Connection con=ConnectDB.getConnection(); HoaDon hd=null;
        String sql="select top 1 hd.maHD,hd.ngayLap,cthd.ngayNhanPhong,cthd.ngayTraPhong,"
                + "hd.phuongThucTT,hd.trangThaiHD,hd.tongTienThanhToan,hd.tongTien,"
                + "hd.tienGiamHangKH,hd.tienThue,hd.phiDoiPhong,cthd.maKM,"
                + "kh.maKH,kh.tenKH,kh.sdt,kh.soCCCD,"
                + "nv.maNV,nv.tenNV from HoaDon hd "
                + "join ChiTietHoaDon cthd on hd.maHD=cthd.maHD "
                + "join KhachHang kh on hd.maKH=kh.maKH "
                + "join NhanVien nv on nv.maNV=hd.maNV "
                + "where cthd.maPhong=? and hd.trangThaiHD='PhieuDatPhong' "
                + "order by cthd.ngayNhanPhong desc";
        try{
            PreparedStatement st=con.prepareStatement(sql); st.setString(1,maPhong);
            ResultSet rs=st.executeQuery();
            if(rs.next()){
                String maHD=rs.getString("maHD");
                LocalDateTime ngayLap=rs.getTimestamp("ngayLap").toLocalDateTime();
                LocalDateTime ngayNhanPhong=rs.getTimestamp("ngayNhanPhong").toLocalDateTime();
                LocalDateTime ngayTraPhong=rs.getTimestamp("ngayTraPhong").toLocalDateTime();
                PhuongThucThanhToan pTTT=PhuongThucThanhToan.valueOf(rs.getString("phuongThucTT"));
                TrangThaiHoaDon trangThai=TrangThaiHoaDon.valueOf(rs.getString("trangThaiHD"));
                double tongTienThanhToan=rs.getDouble("tongTienThanhToan");
                double tongTien=rs.getDouble("tongTien");
                double tienThue=rs.getDouble("tienThue");
                double phiDoiPhong=rs.getDouble("phiDoiPhong");
                KhachHang kh=new KhachHang(rs.getString("maKH"),rs.getString("tenKH"),rs.getString("sdt"),rs.getString("soCCCD"));
                NhanVien nv=new NhanVien(rs.getString("maNV"),rs.getString("tenNV"));
                ArrayList<ChiTietHoaDon> dsCTHD=getChiTietHoaDon(maHD);
                hd=new HoaDon(maHD,ngayLap,pTTT,trangThai,
                        tongTienThanhToan,tongTien,tienThue,phiDoiPhong,kh,dsCTHD,nv);
            }
        }catch(Exception e){e.printStackTrace();}
        finally{ConnectDB.closeConnection(con);}
        return hd;
    }

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
//    public boolean capNhatHoaDonVaPhongSauKhiTraPhong(String maHD, String pttt,
//                                                      double thanhTien, double tongTien, double tienGiam, double phiDoiPhong,
//                                                      double tongTienTT, double tienThue, double tienNhan, double tienTra) {
//
//        Connection con = ConnectDB.getConnection();
//        PreparedStatement ps = null;
//        try {
//            con.setAutoCommit(false);
//
//            String sqlCTHD = "update ChiTietHoaDon set thanhTien=? where maHD=?";
//            ps = con.prepareStatement(sqlCTHD);
//            ps.setDouble(1, thanhTien);
//            ps.setString(2, maHD);
//            ps.executeUpdate();
//
//            String sqlPhong = "update p set p.trangThai=N'Trong' from Phong p join ChiTietHoaDon cthd on p.maPhong=cthd.maPhong where cthd.maHD=?";
//            ps = con.prepareStatement(sqlPhong);
//            ps.setString(1, maHD);
//            ps.executeUpdate();
//
//            String sqlKH = "update kh set kh.diemTichLuy = kh.diemTichLuy + (? / 1000000) "
//                    + "from KhachHang kh join HoaDon hd on hd.maKH=kh.maKH where hd.maHD=?";
//            ps = con.prepareStatement(sqlKH);
//            ps.setDouble(1, tongTienTT);
//            ps.setString(2, maHD);
//            ps.executeUpdate();
//
//            String sqlHD = "update HoaDon set trangThai=N'HoaDonHoanThanh', ngayLap=getdate(), pTTT=?, "
//                    + "tongTien=?, tienGiam=?, phiDoiPhong=?, tongTienThanhToan=?, tienThue=?, tienNhan=?, tienTra=? "
//                    + "where maHD=?";
//            ps = con.prepareStatement(sqlHD);
//            ps.setString(1, pttt);
//            ps.setDouble(2, tongTien);
//            ps.setDouble(3, tienGiam);
//            ps.setDouble(4, phiDoiPhong);
//            ps.setDouble(5, tongTienTT);
//            ps.setDouble(6, tienThue);
//            ps.setDouble(7, tienNhan);
//            ps.setDouble(8, tienTra);
//            ps.setString(9, maHD);
//            ps.executeUpdate();
//
//            con.commit();
//            return true;
//
//        } catch (Exception e) {
//            try { con.rollback(); } catch (Exception ex) {}
//            e.printStackTrace();
//        } finally {
//            ConnectDB.closeConnection(con);
//        }
//        return false;
//    }

	public ArrayList<HoaDon> getHoaDonTheoTrangThai(String trangThaiHD) {
		return null;
	}

	public ArrayList<Phong> getPhongTheoSDTKhachHang(String sdt) {
		ArrayList<Phong> dsPhong = new ArrayList<>();
		Connection connection = null;
		try {
			connection = ConnectDB.getConnection();

			// THỬ THAY ĐỔI tên cột thành 'trangThaiPhong'
			String sql = "SELECT DISTINCT p.maPhong, p.trangThaiPhong, p.giaPhong, lp.tenLoaiPhong, p.sucChuaToiDa " +
					"FROM KhachHang kh " +
					"INNER JOIN HoaDon hd ON kh.maKH = hd.maKH " +
					"INNER JOIN ChiTietHoaDon cthd ON hd.maHD = cthd.maHD " +
					"INNER JOIN Phong p ON cthd.maPhong = p.maPhong " +
					"INNER JOIN LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong " +
					"WHERE kh.sdt = ? " +
					"AND hd.trangThaiHD IN ('PhieuThuePhong', 'PhieuDatPhong')";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, sdt);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Phong phong = new Phong();
				phong.setMaPhong(rs.getString("maPhong"));
				phong.setGiaPhong(rs.getDouble("giaPhong"));
				phong.setSucChuaToiDa(rs.getInt("sucChuaToiDa"));

				// THAY ĐỔI tên cột ở đây
				String trangThaiStr = rs.getString("trangThaiPhong");
				TrangThaiPhong trangThai = TrangThaiPhong.valueOf(trangThaiStr);
				phong.setTrangThai(trangThai);

				LoaiPhong loaiPhong = new LoaiPhong();
				loaiPhong.setTenLoaiPhong(rs.getString("tenLoaiPhong"));
				phong.setLoaiPhong(loaiPhong);

				dsPhong.add(phong);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDB.closeConnection(connection);
		}
		return dsPhong;
	}

	public boolean kiemTraMaHDTonTai(String maHD) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT COUNT(*) FROM HoaDon WHERE maHD = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maHD);
			rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			// Đóng kết nối
		}
	}
}
