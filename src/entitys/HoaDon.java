package entitys;

import enums.PhuongThucThanhToan;
import enums.TrangThaiHoaDon;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import entitys.ChiTietHoaDon;
import entitys.KhachHang;
import entitys.KhuyenMai;
import entitys.NhanVien;


public class HoaDon {
    private String maHD;
    private LocalDateTime ngayLap;
    private PhuongThucThanhToan pTTT;
    private TrangThaiHoaDon trangThai;
    private double tongTienThanhToan;
    private double tongTien;
    private double tienNhan;
    private double tienThue;
    private double phiDoiPhong;
    private KhachHang khachHang;
    private ArrayList<ChiTietHoaDon> dsCTHD;
    private NhanVien nhanVien;
    private double tienGiamTheoHangKH;
    private double tongTienSauGiam;

   

	public HoaDon(String maHD, LocalDateTime ngayLap, TrangThaiHoaDon trangThai, KhachHang kh, ArrayList<ChiTietHoaDon> dsCTHD) {
        this.maHD = maHD;
        this.ngayLap = ngayLap;
        this.khachHang = kh;
        this.dsCTHD =  dsCTHD;
    }
	 public double getTienGiamTheoHangKH() {
			return tienGiamTheoHangKH;
		}

		public void setTienGiamTheoHangKH(double tienGiamTheoHangKH) {
			this.tienGiamTheoHangKH = tienGiamTheoHangKH;
		}

    public String getMaHD() {
        return maHD;
    }
    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }
    public LocalDateTime getNgayLap() {
        return ngayLap;
    }
    public void setNgayLap(LocalDateTime ngayLap) {
        this.ngayLap = ngayLap;
    }
    
    public PhuongThucThanhToan getpTTT() {
        return pTTT;
    }
    public void setpTTT(PhuongThucThanhToan pTTT) {
        this.pTTT = pTTT;
    }
    public double getTienNhan() {
        return tienNhan;
    }
    public void setTienNhan(double tienNhan) {
        this.tienNhan = tienNhan;
    }
    public TrangThaiHoaDon getTrangThai() {
        return trangThai;
    }
    public void setTrangThai(TrangThaiHoaDon trangThai) {
        this.trangThai = trangThai;
    }
    
    public KhachHang getKhachHang() {
        return khachHang;
    }
    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }
    public ArrayList<ChiTietHoaDon> getcTHD() {
        return dsCTHD;
    }
    public void setcTHD(ArrayList<ChiTietHoaDon> dsCTHD) {
        this.dsCTHD = dsCTHD;
    }
    public NhanVien getNhanVien() {
        return nhanVien;
    }
    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }
    // dẫn xuất
    public double getTongTienThanhToan() {
        return tongTienThanhToan;
    }
    public void setTongTienThanhToan() {
        this.tongTienThanhToan = tongTien + tienThue  + phiDoiPhong;
    }
    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien() {
        double tong = 0;
        // Kiểm tra null trước khi sử dụng
        if (this.dsCTHD != null) {
            for (ChiTietHoaDon cthd : this.dsCTHD) {
                if (cthd != null) {
                    tong += cthd.getThanhTien();
                }
            }
        }

        this.tongTien = tong;

    }

    public HoaDon() {

    }

    
    
    public double getTienThue() {
        return tienThue;
    }
    public void setTienThue() {
        this.tienThue = (this.dsCTHD != null) ? this.tongTien * 0.1 : 0;

    }
    public double getPhiDoiPhong() {
        return phiDoiPhong;
    }
    public void setPhiDoiPhong() {
        this.phiDoiPhong = 0;
    }
    

    public double getTongTienSauGiam() {
		return tongTienSauGiam;
	}
	public void setTongTienSauGiam(double tongTienSauGiam) {
		this.tongTienSauGiam = tongTienSauGiam;
	}
	public HoaDon(String maHD, LocalDateTime ngayLap,  PhuongThucThanhToan pTTT, TrangThaiHoaDon trangThai, double tongTienThanhToan, double tongTien, double tienGiam, double tienThue, double phiDoiPhong, KhachHang khachHang, ArrayList<ChiTietHoaDon> dsCTHD, NhanVien nhanVien) {
        this.maHD = maHD;
        this.ngayLap = ngayLap;

        this.pTTT = pTTT;
        this.trangThai = trangThai;
        this.tongTienThanhToan = tongTienThanhToan;
        this.tongTien = tongTien;
        this.tienThue = tienThue;
        this.phiDoiPhong = phiDoiPhong;
        this.khachHang = khachHang;
        this.dsCTHD = dsCTHD;
        this.nhanVien = nhanVien;
    }

    public HoaDon(String maHD, LocalDateTime ngayLap, PhuongThucThanhToan pTTT, TrangThaiHoaDon trangThai,
                  KhachHang khachHang, ArrayList<ChiTietHoaDon> dsCTHD, NhanVien nhanVien , double tienNhan) {
        this.maHD = maHD;
        this.ngayLap = ngayLap;
        this.pTTT = pTTT;
        this.trangThai = trangThai;
        this.khachHang = khachHang;
        this.dsCTHD = dsCTHD;
        this.nhanVien = nhanVien;
        this.tienNhan = tienNhan;

    }

    public HoaDon(String maHD, LocalDateTime ngayLap, 
                  PhuongThucThanhToan pTTT, TrangThaiHoaDon trangThai, double tienNhan, 
                  KhachHang khachHang, ArrayList<ChiTietHoaDon> dsCTHD, NhanVien nhanVien) {

        this.maHD = maHD;
        this.ngayLap = ngayLap;

        this.pTTT = pTTT;
        this.trangThai = trangThai;
        // QUAN TRỌNG: Khởi tạo dsCTHD nếu null
        this.dsCTHD = (dsCTHD != null) ? dsCTHD : new ArrayList<>();
        this.tienNhan = tienNhan;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;

        // Chỉ gọi setTongTien() nếu dsCTHD không rỗng
        if (this.dsCTHD != null && !this.dsCTHD.isEmpty()) {
            setTongTien();
            setTienThue();
            setPhiDoiPhong();
            setTongTienThanhToan();
        }
    }

    public HoaDon(String maHD, LocalDateTime ngayLap, PhuongThucThanhToan pTTT, TrangThaiHoaDon trangThai,
                  KhachHang khachHang, ArrayList<ChiTietHoaDon> dsCTHD, NhanVien nhanVien) {
        super();
        this.maHD = maHD;
        this.ngayLap = ngayLap;
        this.pTTT = pTTT;
        this.trangThai = trangThai;
        this.khachHang = khachHang;
        this.dsCTHD = dsCTHD;
        this.nhanVien = nhanVien;
     // Chỉ gọi setTongTien() nếu dsCTHD không rỗng
        if (this.dsCTHD != null && !this.dsCTHD.isEmpty()) {
            setTongTien();
            setTienThue();
            setPhiDoiPhong();
            setTongTienThanhToan();
        }
    }
    public HoaDon(String maHD, LocalDateTime ngayLap, PhuongThucThanhToan pTTT, TrangThaiHoaDon trangThai,
			double tongTienThanhToan, double tongTien, double tienNhan, double tienThue,
			double phiDoiPhong, KhachHang khachHang, ArrayList<ChiTietHoaDon> dsCTHD, NhanVien nhanVien,
			double tienGiamTheoHangKH, double tongTienSauGiam) {
		this.maHD = maHD;
		this.ngayLap = ngayLap;
		this.pTTT = pTTT;
		this.trangThai = trangThai;
		this.tongTienThanhToan = tongTienThanhToan;
		this.tongTien = tongTien;
		this.tienNhan = tienNhan;
		this.tienThue = tienThue;
		this.phiDoiPhong = phiDoiPhong;
		this.khachHang = khachHang;
		this.dsCTHD = dsCTHD;
		this.nhanVien = nhanVien;
		this.tienGiamTheoHangKH = tienGiamTheoHangKH;
		this.tongTienSauGiam = tongTienSauGiam;
	}
    
    
	public HoaDon(String maHD, LocalDateTime ngayLap, PhuongThucThanhToan pTTT, TrangThaiHoaDon trangThai,
			double tongTienThanhToan, double tongTien, double tienThue, double phiDoiPhong, KhachHang khachHang,
			ArrayList<ChiTietHoaDon> dsCTHD, NhanVien nhanVien) {
		super();
		this.maHD = maHD;
		this.ngayLap = ngayLap;
		this.pTTT = pTTT;
		this.trangThai = trangThai;
		this.tongTienThanhToan = tongTienThanhToan;
		this.tongTien = tongTien;
		this.tienThue = tienThue;
		this.phiDoiPhong = phiDoiPhong;
		this.khachHang = khachHang;
		this.dsCTHD = dsCTHD;
		this.nhanVien = nhanVien;
	}
	@Override
    public int hashCode() {
        return Objects.hash(dsCTHD);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HoaDon other = (HoaDon) obj;
        return Objects.equals(dsCTHD, other.dsCTHD);
    }

}
