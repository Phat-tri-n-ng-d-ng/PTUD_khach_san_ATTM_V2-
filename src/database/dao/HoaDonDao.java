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
    public String taoMaHDMoi() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();

            // Lấy ngày hiện tại dạng ddMMyy (2 số ngày, 2 số tháng, 2 số năm)
            LocalDateTime now = LocalDateTime.now();
            String ngayStr = String.format("%02d%02d%02d",
                    now.getDayOfMonth(),
                    now.getMonthValue(),
                    now.getYear() % 100);

            // Tìm số thứ tự lớn nhất trong ngày (sửa lại logic để tránh trùng)
            String sql = "SELECT MAX(maHD) FROM HoaDon WHERE maHD LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "HD" + ngayStr + "%");
            rs = stmt.executeQuery();

            int soThuTu = 0;
            if (rs.next()) {
                String maHDMax = rs.getString(1);
                if (maHDMax != null && maHDMax.startsWith("HD" + ngayStr)) {
                    // Lấy phần số sau "HD" + ngayStr
                    String soStr = maHDMax.substring(8); // "HD" (2) + 6 số ngày = 8 ký tự
                    try {
                        // Xóa các số 0 ở đầu và chuyển thành số
                        soThuTu = Integer.parseInt(soStr.replaceFirst("^0+", ""));
                    } catch (NumberFormatException e) {
                        soThuTu = 0;
                    }
                }
            }

            // Tăng số thứ tự lên 1
            soThuTu++;

            // Tạo mã mới và kiểm tra xem đã tồn tại chưa
            String maHDMoi;
            int maxRetry = 100; // Giới hạn số lần thử
            int retryCount = 0;

            do {
                maHDMoi = "HD" + ngayStr + String.format("%03d", soThuTu);

                // Kiểm tra xem mã này đã tồn tại chưa
                String checkSql = "SELECT COUNT(*) FROM HoaDon WHERE maHD = ?";
                try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
                    checkStmt.setString(1, maHDMoi);
                    try (ResultSet checkRs = checkStmt.executeQuery()) {
                        if (checkRs.next()) {
                            int count = checkRs.getInt(1);
                            if (count == 0) {
                                // Mã chưa tồn tại
                                System.out.println("Đã tạo mã HD mới (chưa tồn tại): " + maHDMoi);
                                break;
                            } else {
                                // Mã đã tồn tại, tăng số lên
                                System.err.println("Mã HD đã tồn tại: " + maHDMoi + ", tăng số thứ tự...");
                                soThuTu++;
                                retryCount++;
                            }
                        }
                    }
                }
            } while (retryCount < maxRetry);

            if (retryCount >= maxRetry) {
                // Nếu vẫn không tạo được mã sau nhiều lần thử, dùng timestamp
                long timestamp = System.currentTimeMillis() % 100000;
                maHDMoi = "HD" + ngayStr + String.format("%05d", timestamp);
                System.err.println("Dùng timestamp để tạo mã HD: " + maHDMoi);
            }

            return maHDMoi;

        } catch (Exception e) {
            System.err.println("Lỗi tạo mã hóa đơn: " + e.getMessage());
            e.printStackTrace();

            // Fallback: tạo mã bằng timestamp
            try {
                LocalDateTime now = LocalDateTime.now();
                String ngayStr = String.format("%02d%02d%02d",
                        now.getDayOfMonth(),
                        now.getMonthValue(),
                        now.getYear() % 100);
                long timestamp = System.currentTimeMillis() % 1000000;
                String fallbackMaHD = "HD" + ngayStr + String.format("%06d", timestamp);
                System.err.println("Fallback: tạo mã HD: " + fallbackMaHD);
                return fallbackMaHD;
            } catch (Exception ex) {
                return null;
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                ConnectDB.closeConnection(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
	            String sql = "select kh.maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD," +
                        "diemTichLuy, hang.maHang, hang.tenHang, cthd.ngayNhanPhong, cthd.ngayTraPhong" +
                        "from KhachHang kh" +
                        "join HoaDon hd on kh.maKH = hd.maKH" +
                        "join HangKhachHang hang on hang.maHang = kh.maHang" +
                        "join ChiTietHoaDon cthd on cthd.maHD = hd.maHD" +
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
                }
	        } catch (Exception e) {
	            // TODO: handle exception
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
    public ArrayList<NguoiO> getNguoiOTheoPhongVaHoaDon(String maPhong, String maHD) {
        Connection connection = ConnectDB.getConnection();
        ArrayList<NguoiO> dsNguoiO = new ArrayList<>();
        NguoiO nO = null;

        String sql = "select * from NguoiO where maPhong = ? and maHD = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, maPhong);
            st.setString(2, maHD);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String tenNO = rs.getString("tenNguoiO");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                boolean gioiTinh = rs.getBoolean("gioiTinh");
                String sdt = rs.getString("sdt");
                String soCCCD = rs.getString("soCCCD");

                nO = new NguoiO(tenNO, ngaySinh, sdt, soCCCD, gioiTinh);
                dsNguoiO.add(nO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(connection);
        }

        return dsNguoiO;
    }

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
    public boolean themNguoiO(NguoiO nguoiO, String maHD, String maPhong) {
        int n=0;
        String sql = "INSERT INTO NguoiO "
                + "(tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nguoiO.getHoTen());
            ps.setDate(2, java.sql.Date.valueOf(nguoiO.getNgaySinh()));
            ps.setBoolean(3, nguoiO.isGioiTinh());
            ps.setString(4, nguoiO.getSDT());
            ps.setString(5, nguoiO.getCCCD());
            ps.setString(6, maHD);
            ps.setString(7, maPhong);

            n= ps.executeUpdate() ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n>0;
    }

    public HoaDon timHoaDonTheoPhongDangThue(String maPhong){
        Connection con=ConnectDB.getConnection(); HoaDon hd=null;
        String sql="select top 1 hd.maHD,hd.ngayLap,cthd.ngayNhanPhong,cthd.ngayTraPhong,"
                + "hd.phuongThucTT,hd.trangThaiHD,hd.tongTienThanhToan,hd.tongTien,"
                + "hd.tienGiamHangKH,hd.tienThue,hd.phiDoiPhong,cthd.maKM,"
                + "kh.maKH,kh.tenKH,kh.sdt,kh.soCCCD, diemTichLuy, "
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
                kh.setDiemTichLuy(rs.getInt("diemTichLuy"));
                NhanVien nv=new NhanVien(rs.getString("maNV"),rs.getString("tenNV"));
                ArrayList<ChiTietHoaDon> dsCTHD=getChiTietHoaDon(maHD);
                hd=new HoaDon(maHD,ngayLap,pTTT,trangThai,
                        tongTienThanhToan,tongTien,tienThue,phiDoiPhong,kh,dsCTHD,nv);
            }
        }catch(Exception e){e.printStackTrace();}
        finally{ConnectDB.closeConnection(con);}
        return hd;
    }
    public boolean tachHoaDonMotPhong(HoaDon hoaDonMoi, ChiTietHoaDon cthdCanTach, String maHDcu) {
        Connection con = null;
        PreparedStatement psHoaDon = null;
        PreparedStatement psCTHD = null;
        PreparedStatement psNguoiO = null;
        PreparedStatement psPhong = null;

        try {
            con = ConnectDB.getConnection();
            con.setAutoCommit(false);

            String maHDMoi = hoaDonMoi.getMaHD(); // mã hóa đơn mới đã được set trước

            // 1. Thêm hóa đơn mới
            String sqlHoaDon = "INSERT INTO HoaDon (maHD, ngayLap, phuongThucTT, trangThaiHD, tongTien," +
                    "tienThue, phiDoiPhong, tongTienThanhToan, tienGiamHangKH, tongTienSauGiam," +
                    "tienNhan, maKH, maNV) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            psHoaDon = con.prepareStatement(sqlHoaDon);
            psHoaDon.setString(1, maHDMoi);
            psHoaDon.setTimestamp(2, Timestamp.valueOf(hoaDonMoi.getNgayLap()));
            psHoaDon.setString(3, hoaDonMoi.getpTTT().name());
            psHoaDon.setString(4, TrangThaiHoaDon.HoaDonHoanThanh.name());
            psHoaDon.setDouble(5, cthdCanTach.getThanhTien());
            psHoaDon.setDouble(6, hoaDonMoi.getTienThue());
            psHoaDon.setDouble(7, hoaDonMoi.getPhiDoiPhong());
            psHoaDon.setDouble(8, cthdCanTach.getThanhTien() + hoaDonMoi.getTienThue() + hoaDonMoi.getPhiDoiPhong());
            psHoaDon.setDouble(9, cthdCanTach.getTienGiam());
            psHoaDon.setDouble(10, cthdCanTach.getThanhTien() - cthdCanTach.getTienGiam());
            psHoaDon.setDouble(11, hoaDonMoi.getTienNhan());
            psHoaDon.setString(12, hoaDonMoi.getKhachHang().getMaKH());
            psHoaDon.setString(13, hoaDonMoi.getNhanVien().getMaNV());
            psHoaDon.executeUpdate();

            // 2. Cập nhật ChiTietHoaDon sang hóa đơn mới
            String sqlCTHD = "UPDATE ChiTietHoaDon SET maHD = ?, thanhTien = ?, tienGiam = ? WHERE maHD = ? AND maPhong = ?";
            psCTHD = con.prepareStatement(sqlCTHD);
            psCTHD.setString(1, maHDMoi);
            psCTHD.setDouble(2, cthdCanTach.getThanhTien());
            psCTHD.setDouble(3, cthdCanTach.getTienGiam());
            psCTHD.setString(4, maHDcu); // dùng mã cũ
            psCTHD.setString(5, cthdCanTach.getPhong().getMaPhong());
            psCTHD.executeUpdate();

            // 3. Cập nhật NguoiO sang hóa đơn mới
            String sqlNguoiO = "UPDATE NguoiO SET maHD = ? WHERE maHD = ? AND maPhong = ?";
            psNguoiO = con.prepareStatement(sqlNguoiO);
            psNguoiO.setString(1, maHDMoi);
            psNguoiO.setString(2, maHDcu); // dùng mã cũ
            psNguoiO.setString(3, cthdCanTach.getPhong().getMaPhong());
            psNguoiO.executeUpdate();

            // 4. Cập nhật trạng thái phòng thành "Trong"
            String sqlPhong = "UPDATE Phong SET trangThaiPhong = 'Trong' WHERE maPhong = ?";
            psPhong = con.prepareStatement(sqlPhong);
            psPhong.setString(1, cthdCanTach.getPhong().getMaPhong());
            psPhong.executeUpdate();

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try { if(con != null) con.rollback(); } catch(SQLException ex) { ex.printStackTrace(); }
            return false;
        } finally {
            try {
                if(psPhong != null) psPhong.close();
                if(psNguoiO != null) psNguoiO.close();
                if(psCTHD != null) psCTHD.close();
                if(psHoaDon != null) psHoaDon.close();
                if(con != null) {
                    con.setAutoCommit(true);
                    ConnectDB.closeConnection(con);
                }
            } catch (SQLException e) { e.printStackTrace(); }
        }
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
	            // TODO: handle exception
	            e.printStackTrace();
	        }finally {
	            ConnectDB.closeConnection(connection);
	        }
	        return dsHoaDon;
	    };

    public ArrayList<HoaDon> getHoaDonTheoTrangThai(String trangThaiHD) {
        return null;
    }

    public void TuDongCapNhatTrangThaiPhong_TheoKhoangNgay(Date ngayBatDau, Date ngayKetThuc) {
    }
}
