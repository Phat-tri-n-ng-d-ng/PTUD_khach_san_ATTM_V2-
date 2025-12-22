package database.dao;

import database.connectDB.ConnectDB;
import entitys.HangKhachHang;
import entitys.KhachHang;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class KhachHangDao {
    public ArrayList<KhachHang> getTatCaKhachHang(){
        ArrayList<KhachHang> dsKhachHang = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "select k.maKH, k.tenKH, k.ngaySinh, k.gioiTinh, k.sdt, k.email, k.soCCCD, k.diemTichLuy, hkh.tenHang, hkh.maHang\n" +
                    "from KhachHang k\n" +
                    "inner join HangKhachHang hkh on hkh.maHang = k.maHang";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
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
                HangKhachHang hangKhachHang = new HangKhachHang(maHang,tenHang);
                KhachHang kh = new KhachHang(maKH, tenKH, gioiTinh, ngaySinh, email, sdt,
                        soCCCD, diemTichLuy,hangKhachHang);
                dsKhachHang.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(connection);
        }
        return dsKhachHang;
    }

    public int getSoLuongKhachHang() {
        Connection connection = null;
        int soLuong = 0;
        try {
            connection = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) FROM KhachHang";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                soLuong = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(connection);
        }
        return soLuong;
    }

    public boolean themKhachHang(KhachHang khachHang) {
        Connection connection = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "insert into KhachHang (maKH, tenKH, ngaySinh, gioiTinh, sdt, email, soCCCD)\n" +
                    "values (?,?,?,?,?,?,?)";
            CallableStatement stmt = connection.prepareCall(sql);
            stmt.setString(1,khachHang.getMaKH());
            stmt.setString(2,khachHang.getTenKH());
            stmt.setDate(3, Date.valueOf(khachHang.getNgaySinh()));
            stmt.setBoolean(4,khachHang.isGioiTinh());
            stmt.setString(5,khachHang.getSdt());
            stmt.setString(6,khachHang.getEmail());
            stmt.setString(7,khachHang.getSoCCCD());
            int n = stmt.executeUpdate();
            return n>0;
        }catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("Lỗi khác: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        finally {
            ConnectDB.closeConnection(connection);
        }
    }

    public String getMaHang(String tenHang) {
        Connection connection = null;
        String tenHang1 = "";
        try {
            connection = ConnectDB.getConnection();
            String sql = "SELECT maHang FROM HangKhachHang WHERE tenHang = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,tenHang);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tenHang1 = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(connection);
        }
        return tenHang1;
    }

    public ArrayList<KhachHang> getKhachHangTheoHang(String hangKhachHangTim) {
        ArrayList<KhachHang> dsKhachHang = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = ("select k.maKH, k.tenKH, k.ngaySinh, k.gioiTinh, k.sdt, k.email, k.soCCCD, k.diemTichLuy, hkh.tenHang, hkh.maHang\n" +
                    "from KhachHang k\n" +
                    "inner join HangKhachHang hkh on hkh.maHang = k.maHang \n" +
                    "WHERE hkh.tenHang = ?");
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, hangKhachHangTim);
            ResultSet rs = stmt.executeQuery();
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
                HangKhachHang hangKhachHang = new HangKhachHang(maHang,tenHang);
                KhachHang kh = new KhachHang(maKH, tenKH, gioiTinh, ngaySinh, email, sdt,
                        soCCCD, diemTichLuy,hangKhachHang);
                dsKhachHang.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }

    public boolean CapNhatKhachHang(KhachHang khachHang) {
        Connection connection = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "UPDATE KhachHang \n" +
                    "    SET tenKh = ?, ngaySinh = ?, gioiTinh = ?, sdt = ?, email = ?, soCCCD = ? \n" +
                    "    WHERE maKH = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,khachHang.getTenKH());
            stmt.setDate(2, Date.valueOf(khachHang.getNgaySinh()));
            stmt.setBoolean(3,khachHang.isGioiTinh());
            stmt.setString(4,khachHang.getSdt());
            stmt.setString(5,khachHang.getEmail());
            stmt.setString(6,khachHang.getSoCCCD());
            stmt.setString(7,khachHang.getMaKH());
            int n = stmt.executeUpdate();
            return n>0;
        }catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("Lỗi khác: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        finally {
            ConnectDB.closeConnection(connection);
        }
    }

    public KhachHang TimKhachHang(String keyword, String type){
        KhachHang khachHang = null;
        Connection connection = null;
        try {
            connection = ConnectDB.getConnection();
            String sql;
            if (type.equalsIgnoreCase("CCCD")) {
                sql = "select k.maKH, k.tenKH, k.ngaySinh, k.gioiTinh, k.sdt, k.email, k.soCCCD, k.diemTichLuy, hkh.tenHang, hkh.maHang\n" +
                        "    from KhachHang k\n" +
                        "    inner join HangKhachHang hkh on hkh.maHang = k.maHang\n" +
                        "    WHERE k.soCCCD = ?";
            } else if (type.equalsIgnoreCase("SDT")) {
                sql = "select k.maKH, k.tenKH, k.ngaySinh, k.gioiTinh, k.sdt, k.email, k.soCCCD, k.diemTichLuy, hkh.tenHang, hkh.maHang\n" +
                        "    from KhachHang k\n" +
                        "    inner join HangKhachHang hkh on hkh.maHang = k.maHang\n" +
                        "    WHERE k.sdt = ?";
            }
            else {
                throw new IllegalArgumentException("Loại tìm kiếm không hợp lệ: " + type);
            }
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,keyword);
            ResultSet rs = stmt.executeQuery();
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
                HangKhachHang hangKhachHang = new HangKhachHang(maHang,tenHang);
                khachHang = new KhachHang(maKH, tenKH, gioiTinh, ngaySinh, email, sdt,
                        soCCCD, diemTichLuy,hangKhachHang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(connection);
        }
        return khachHang;
    }

    public ArrayList<HangKhachHang> getTatCaHangKhachHang() {
        ArrayList<HangKhachHang> dsHang = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectDB.getConnection();

            String sql = """
            SELECT maHang, tenHang, diemToiThieu
            FROM HangKhachHang
        """;

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String maHang = rs.getString("maHang");
                String tenHang = rs.getString("tenHang");
                int diemToiThieu = rs.getInt("diemToiThieu");

                HangKhachHang hang = new HangKhachHang(maHang, tenHang, diemToiThieu);
                dsHang.add(hang);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection(connection);
        }

        return dsHang;
    }

    public boolean CapNhatHangKhachHang(ArrayList<HangKhachHang> dsHang) {
        if (dsHang == null || dsHang.isEmpty()) {
            return false;
        }

        Connection connection = null;
        PreparedStatement stmt = null;
        boolean success = true;

        try {
            connection = ConnectDB.getConnection();
            connection.setAutoCommit(false); // Bắt đầu transaction để đảm bảo toàn vẹn dữ liệu

            String sql = "UPDATE HangKhachHang SET diemToiThieu = ? WHERE maHang = ?";

            stmt = connection.prepareStatement(sql);

            for (HangKhachHang hang : dsHang) {
                stmt.setDouble(1, hang.getDiemToiThieu());    // Điểm mới
                stmt.setString(2, hang.getMaHang());       // Mã hạng để xác định bản ghi

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected == 0) {
                    System.out.println("Cảnh báo: Không tìm thấy hạng có mã " + hang.getMaHang());
                    success = false; // Vẫn tiếp tục nhưng đánh dấu có lỗi
                }
            }

            if (success) {
                connection.commit(); // Chỉ commit nếu tất cả thành công
                System.out.println("Cập nhật ngưỡng điểm hạng khách hàng thành công!");
            } else {
                connection.rollback();
                System.out.println("Có lỗi khi cập nhật một số hạng, đã rollback.");
            }

        } catch (Exception e) {
            success = false;
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                    System.out.println("Đã rollback do ngoại lệ.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi cập nhật hạng khách hàng: " + e.getMessage(),
                    "Lỗi cơ sở dữ liệu", JOptionPane.ERROR_MESSAGE);

        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) {
                    connection.setAutoCommit(true); // Khôi phục chế độ mặc định
                    ConnectDB.closeConnection(connection);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return success;
    }
}
