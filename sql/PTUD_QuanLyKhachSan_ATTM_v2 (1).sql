CREATE DATABASE QuanLyKhachSan_ATTM;
GO
USE QuanLyKhachSan_ATTM;
GO

CREATE TABLE VaiTro (
    maVaiTro      CHAR(10) PRIMARY KEY,
    tenVaiTro     NVARCHAR(30) NOT NULL UNIQUE
);
CREATE TABLE TrangThaiTaiKhoan (
    maTrangThaiTK CHAR(12) PRIMARY KEY,
    tenTrangThaiTK NVARCHAR(30) NOT NULL UNIQUE
);
CREATE TABLE TaiKhoan (
    tenDangNhap   VARCHAR(50) PRIMARY KEY,
    matKhau       NVARCHAR(50) NOT NULL,
    maVaiTro      CHAR(10) NOT NULL,
    maTrangThaiTK CHAR(12) NOT NULL,
    CONSTRAINT FK_TaiKhoan_VaiTro      FOREIGN KEY (maVaiTro)      REFERENCES VaiTro(maVaiTro),
    CONSTRAINT FK_TaiKhoan_TrangThaiTK FOREIGN KEY (maTrangThaiTK) REFERENCES TrangThaiTaiKhoan(maTrangThaiTK)
);
CREATE TABLE ChucVuNhanVien (
    maChucVu CHAR(10) PRIMARY KEY,
    tenChucVu NVARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE NhanVien (
    maNV          CHAR(10) PRIMARY KEY,
    tenNV         NVARCHAR(100) NOT NULL,
    ngaySinh      DATE NOT NULL,
    gioiTinh      BIT NOT NULL,
    sdt           VARCHAR(20) NOT NULL UNIQUE,
    email         NVARCHAR(100) NOT NULL UNIQUE,
    maChucVu      CHAR(10) NOT NULL,
    tenDangNhap   VARCHAR(50) NULL,
    CONSTRAINT FK_NhanVien_ChucVu   FOREIGN KEY (maChucVu)    REFERENCES ChucVuNhanVien(maChucVu),
    CONSTRAINT FK_NhanVien_TaiKhoan FOREIGN KEY (tenDangNhap) REFERENCES TaiKhoan(tenDangNhap)
);
CREATE TABLE HangKhachHang (
    maHang      CHAR(10) PRIMARY KEY,
    tenHang     NVARCHAR(20) NOT NULL UNIQUE,
    diemToiThieu INT NOT NULL
);
CREATE TABLE KhachHang (
    maKH        CHAR(10) PRIMARY KEY,
    tenKH       NVARCHAR(100) NOT NULL,
    gioiTinh    BIT NOT NULL,
    ngaySinh    DATE NOT NULL,
    email       NVARCHAR(100) NOT NULL UNIQUE,
    sdt         VARCHAR(20) NOT NULL UNIQUE,
    soCCCD      VARCHAR(20) NOT NULL UNIQUE,
    diemTichLuy INT DEFAULT 0,
    maHang      CHAR(10) NOT NULL,
    CONSTRAINT FK_KhachHang_Hang FOREIGN KEY (maHang) REFERENCES HangKhachHang(maHang)
);
CREATE TABLE LoaiPhong (
    maLoaiPhong   CHAR(10) PRIMARY KEY,
    tenLoaiPhong  NVARCHAR(50) NOT NULL,
    giaNiemYet    FLOAT NOT NULL,
    tyLeCoc       FLOAT NOT NULL,
    sucChuaToiThieu INT NOT NULL
);
CREATE TABLE TrangThaiPhong (
    maTrangThaiPhong CHAR(10) PRIMARY KEY,
    tenTrangThaiPhong NVARCHAR(30) NOT NULL UNIQUE
);
CREATE TABLE Phong (
    maPhong          CHAR(10) PRIMARY KEY,
    tang             INT NOT NULL,
    soPhong          INT NOT NULL,
    sucChuaToiDa     INT NOT NULL,
    giaPhong         FLOAT NOT NULL,
    tienCoc          FLOAT NOT NULL,
    maLoaiPhong      CHAR(10) NOT NULL,
    maTrangThaiPhong CHAR(10) NOT NULL,
    CONSTRAINT FK_Phong_LoaiPhong      FOREIGN KEY (maLoaiPhong)      REFERENCES LoaiPhong(maLoaiPhong),
    CONSTRAINT FK_Phong_TrangThaiPhong FOREIGN KEY (maTrangThaiPhong) REFERENCES TrangThaiPhong(maTrangThaiPhong)
);
CREATE TABLE TrangThaiKhuyenMai (
    maTrangThaiKM CHAR(12) PRIMARY KEY,
    tenTrangThaiKM NVARCHAR(30) NOT NULL UNIQUE
);
CREATE TABLE KhuyenMai (
    maKM            CHAR(10) PRIMARY KEY,
    tenKM           NVARCHAR(200) NOT NULL,
    dieuKienApDung  NVARCHAR(200) NOT NULL,
    tyLeGiam        FLOAT NOT NULL,
    ngayBatDau      DATETIME NOT NULL,
    ngayKetThuc     DATETIME NOT NULL,
    maTrangThaiKM   CHAR(12) NOT NULL,
    CONSTRAINT FK_KhuyenMai_TrangThai FOREIGN KEY (maTrangThaiKM) REFERENCES TrangThaiKhuyenMai(maTrangThaiKM)
);
CREATE TABLE PhuongThucThanhToan (
    maPTTT  CHAR(12) PRIMARY KEY,
    tenPTTT NVARCHAR(30) NOT NULL UNIQUE
);
CREATE TABLE TrangThaiHoaDon (
    maTrangThaiHD CHAR(12) PRIMARY KEY,
    tenTrangThaiHD NVARCHAR(30) NOT NULL UNIQUE
);
CREATE TABLE HoaDon (
    maHD           CHAR(12) PRIMARY KEY,
    ngayLap        DATETIME NOT NULL,
    ngayNhanPhong  DATETIME NOT NULL,
    ngayTraPhong   DATETIME NOT NULL,
    maPTTT         CHAR(12) NOT NULL,
    maTrangThaiHD  CHAR(12) NOT NULL,
    tienNhan       FLOAT NULL,
    tongTienThanhToan FLOAT NULL,
    maKM           CHAR(10) NULL,
    maKH           CHAR(10) NOT NULL,
    maNV           CHAR(10) NOT NULL,
    CONSTRAINT FK_HoaDon_PTTT      FOREIGN KEY (maPTTT)        REFERENCES PhuongThucThanhToan(maPTTT),
    CONSTRAINT FK_HoaDon_TrangThai FOREIGN KEY (maTrangThaiHD) REFERENCES TrangThaiHoaDon(maTrangThaiHD),
    CONSTRAINT FK_HoaDon_KhuyenMai FOREIGN KEY (maKM)          REFERENCES KhuyenMai(maKM),
    CONSTRAINT FK_HoaDon_KhachHang FOREIGN KEY (maKH)          REFERENCES KhachHang(maKH),
    CONSTRAINT FK_HoaDon_NhanVien  FOREIGN KEY (maNV)          REFERENCES NhanVien(maNV)
);
CREATE TABLE ChiTietHoaDon (
    maHD      CHAR(12) NOT NULL,
    maPhong   CHAR(10) NOT NULL,
    thanhTien FLOAT NOT NULL,
    CONSTRAINT PK_ChiTietHoaDon PRIMARY KEY (maHD, maPhong),
    CONSTRAINT FK_CTHD_HoaDon FOREIGN KEY (maHD)    REFERENCES HoaDon(maHD),
    CONSTRAINT FK_CTHD_Phong  FOREIGN KEY (maPhong) REFERENCES Phong(maPhong)
);
CREATE TABLE HoaDonHuyPhong (
    maHuy   CHAR(12) PRIMARY KEY,
    ngayHuy DATETIME NOT NULL,
    lyDoHuy NVARCHAR(200) NOT NULL,
    maKH    CHAR(10) NOT NULL,
    maHD    CHAR(12) NOT NULL,
    CONSTRAINT FK_Huy_KhachHang FOREIGN KEY (maKH) REFERENCES KhachHang(maKH),
    CONSTRAINT FK_Huy_HoaDon    FOREIGN KEY (maHD) REFERENCES HoaDon(maHD)
);
CREATE TABLE NguoiO (
    tenNguoiO NVARCHAR(100) NOT NULL,
    ngaySinh  DATE NOT NULL,
    gioiTinh  BIT NOT NULL,
    sdt       VARCHAR(20) NULL,
    soCCCD    VARCHAR(20) NULL,
    maHD      CHAR(12) NOT NULL,
    maPhong   CHAR(10) NOT NULL,
    CONSTRAINT PK_NguoiO PRIMARY KEY (maHD, maPhong),
    CONSTRAINT FK_NguoiO_HoaDon FOREIGN KEY (maHD)    REFERENCES HoaDon(maHD),
    CONSTRAINT FK_NguoiO_Phong  FOREIGN KEY (maPhong) REFERENCES Phong(maPhong)
);
GO

-- Inserts for VaiTro
INSERT INTO VaiTro (maVaiTro, tenVaiTro) VALUES ('VT001', N'QuanLy');
INSERT INTO VaiTro (maVaiTro, tenVaiTro) VALUES ('VT002', N'LeTan');

-- Inserts for TrangThaiTaiKhoan
INSERT INTO TrangThaiTaiKhoan (maTrangThaiTK, tenTrangThaiTK) VALUES ('TTTK001', N'DangHoatDong');
INSERT INTO TrangThaiTaiKhoan (maTrangThaiTK, tenTrangThaiTK) VALUES ('TTTK002', N'VoHieuHoa');

-- Inserts for ChucVuNhanVien
INSERT INTO ChucVuNhanVien (maChucVu, tenChucVu) VALUES ('CV001', N'QuanLy');
INSERT INTO ChucVuNhanVien (maChucVu, tenChucVu) VALUES ('CV002', N'KeToan');
INSERT INTO ChucVuNhanVien (maChucVu, tenChucVu) VALUES ('CV003', N'LeTan');
INSERT INTO ChucVuNhanVien (maChucVu, tenChucVu) VALUES ('CV004', N'BuongPhong');
INSERT INTO ChucVuNhanVien (maChucVu, tenChucVu) VALUES ('CV005', N'Bep');
INSERT INTO ChucVuNhanVien (maChucVu, tenChucVu) VALUES ('CV006', N'BaoVe');
INSERT INTO ChucVuNhanVien (maChucVu, tenChucVu) VALUES ('CV007', N'KyThuat');

-- Inserts for HangKhachHang
INSERT INTO HangKhachHang (maHang, tenHang, diemToiThieu) VALUES ('HG001', N'Dong', 0);
INSERT INTO HangKhachHang (maHang, tenHang, diemToiThieu) VALUES ('HG002', N'Bac', 10);
INSERT INTO HangKhachHang (maHang, tenHang, diemToiThieu) VALUES ('HG003', N'Vang', 20);
INSERT INTO HangKhachHang (maHang, tenHang, diemToiThieu) VALUES ('HG004', N'KimCuong', 40);

INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, maTrangThaiKM) VALUES
('KM001', N'KM 1 giảm 6%', N'Standard', 0.06, '2025-10-27 15:48:08', '2025-11-26 15:48:08', 'TTKM001'),
('KM002', N'KM 2 giảm 7%', N'Superior', 0.07, '2025-10-28 15:48:08', '2025-11-27 15:48:08', 'TTKM002'),
('KM003', N'KM 3 giảm 8%', N'Deluxe', 0.08, '2025-10-29 15:48:08', '2025-11-28 15:48:08', 'TTKM003'),
('KM004', N'KM 4 giảm 9%', N'Suite', 0.09, '2025-10-30 15:48:08', '2025-11-29 15:48:08', 'TTKM004'),
('KM005', N'KM 5 giảm 10%', N'Family Room', 0.10, '2025-10-31 15:48:08', '2025-11-30 15:48:08', 'TTKM001'),
('KM006', N'KM 6 giảm 11%', N'Standard', 0.11, '2025-11-01 15:48:08', '2025-12-01 15:48:08', 'TTKM002'),
('KM007', N'KM 7 giảm 12%', N'Superior', 0.12, '2025-11-02 15:48:08', '2025-12-02 15:48:08', 'TTKM003'),
('KM008', N'KM 8 giảm 13%', N'Deluxe', 0.13, '2025-11-03 15:48:08', '2025-12-03 15:48:08', 'TTKM004'),
('KM009', N'KM 9 giảm 14%', N'Suite', 0.14, '2025-11-04 15:48:08', '2025-12-04 15:48:08', 'TTKM001'),
('KM010', N'KM 10 giảm 15%', N'Family Room', 0.15, '2025-11-05 15:48:08', '2025-12-05 15:48:08', 'TTKM002');
-- Inserts for TrangThaiPhong
INSERT INTO TrangThaiPhong (maTrangThaiPhong, tenTrangThaiPhong) VALUES ('TTP001', N'DaDat');
INSERT INTO TrangThaiPhong (maTrangThaiPhong, tenTrangThaiPhong) VALUES ('TTP002', N'DangSuDung');
INSERT INTO TrangThaiPhong (maTrangThaiPhong, tenTrangThaiPhong) VALUES ('TTP003', N'Trong');
INSERT INTO TrangThaiPhong (maTrangThaiPhong, tenTrangThaiPhong) VALUES ('TTP004', N'NgungHoatDong');

-- Inserts for TrangThaiKhuyenMai
INSERT INTO TrangThaiKhuyenMai (maTrangThaiKM, tenTrangThaiKM) VALUES ('TTKM001', N'SapDienRa');
INSERT INTO TrangThaiKhuyenMai (maTrangThaiKM, tenTrangThaiKM) VALUES ('TTKM002', N'DangHoatDong');
INSERT INTO TrangThaiKhuyenMai (maTrangThaiKM, tenTrangThaiKM) VALUES ('TTKM003', N'HetHan');
INSERT INTO TrangThaiKhuyenMai (maTrangThaiKM, tenTrangThaiKM) VALUES ('TTKM004', N'TamNgung');

-- Inserts for PhuongThucThanhToan
INSERT INTO PhuongThucThanhToan (maPTTT, tenPTTT) VALUES ('PTTT001', N'TienMat');
INSERT INTO PhuongThucThanhToan (maPTTT, tenPTTT) VALUES ('PTTT002', N'ChuyenKhoan');

-- Inserts for TrangThaiHoaDon
INSERT INTO TrangThaiHoaDon (maTrangThaiHD, tenTrangThaiHD) VALUES ('TTHD001', N'PhieuDatPhong');
INSERT INTO TrangThaiHoaDon (maTrangThaiHD, tenTrangThaiHD) VALUES ('TTHD002', N'PhieuThuePhong');
INSERT INTO TrangThaiHoaDon (maTrangThaiHD, tenTrangThaiHD) VALUES ('TTHD003', N'HoaDonHoangThanh');

-- Inserts for TaiKhoan
INSERT INTO TaiKhoan (tenDangNhap, matKhau, maVaiTro, maTrangThaiTK) VALUES ('0000000001', N'1111', 'VT001', 'TTTK001');
INSERT INTO TaiKhoan (tenDangNhap, matKhau, maVaiTro, maTrangThaiTK) VALUES ('0000000002', N'1111', 'VT001', 'TTTK001');
INSERT INTO TaiKhoan (tenDangNhap, matKhau, maVaiTro, maTrangThaiTK) VALUES ('0000000003', N'1111', 'VT001', 'TTTK001');
INSERT INTO TaiKhoan (tenDangNhap, matKhau, maVaiTro, maTrangThaiTK) VALUES ('0000000004', N'1111', 'VT002', 'TTTK001');
INSERT INTO TaiKhoan (tenDangNhap, matKhau, maVaiTro, maTrangThaiTK) VALUES ('0000000005', N'1111', 'VT002', 'TTTK001');
INSERT INTO TaiKhoan (tenDangNhap, matKhau, maVaiTro, maTrangThaiTK) VALUES ('0000000006', N'1111', 'VT002', 'TTTK001');
INSERT INTO TaiKhoan (tenDangNhap, matKhau, maVaiTro, maTrangThaiTK) VALUES ('0000000007', N'1111', 'VT002', 'TTTK001');
INSERT INTO TaiKhoan (tenDangNhap, matKhau, maVaiTro, maTrangThaiTK) VALUES ('0000000008', N'1111', 'VT002', 'TTTK001');
INSERT INTO TaiKhoan (tenDangNhap, matKhau, maVaiTro, maTrangThaiTK) VALUES ('0000000009', N'1111', 'VT002', 'TTTK001');
INSERT INTO TaiKhoan (tenDangNhap, matKhau, maVaiTro, maTrangThaiTK) VALUES ('0000000010', N'1111', 'VT002', 'TTTK001');

-- Inserts for NhanVien
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV001', N'Nguyễn Văn An', '1991-01-01', 0, '000000001', N'nv1@attm.com', 'CV002', '0000000001');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV002', N'Trần Thị Bình', '1992-01-01', 1, '000000002', N'nv2@attm.com', 'CV003', '0000000002');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV003', N'Lê Văn Cường', '1992-12-31', 0, '000000003', N'nv3@attm.com', 'CV004', '0000000003');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV004', N'Phạm Thị Dung', '1993-12-31', 1, '000000004', N'nv4@attm.com', 'CV005', '0000000004');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV005', N'Hoàng Văn Đạt', '1994-12-31', 0, '000000005', N'nv5@attm.com', 'CV006', '0000000005');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV006', N'Vũ Thị Em', '1995-12-31', 1, '000000006', N'nv6@attm.com', 'CV001', '0000000006');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV007', N'Nguyễn Văn Phúc', '1996-12-30', 0, '000000007', N'nv7@attm.com', 'CV002', '0000000007');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV008', N'Trần Thị Giang', '1997-12-30', 1, '000000008', N'nv8@attm.com', 'CV003', '0000000008');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV009', N'Lê Văn Hải', '1998-12-30', 0, '000000009', N'nv9@attm.com', 'CV004', '0000000009');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV010', N'Phạm Thị Hương', '1999-12-30', 1, '000000010', N'nv10@attm.com', 'CV005', '0000000010');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV011', N'Hoàng Văn Khoa', '2000-12-29', 0, '000000011', N'nv11@attm.com', 'CV006', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV012', N'Vũ Thị Lan', '2001-12-29', 1, '000000012', N'nv12@attm.com', 'CV001', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV013', N'Nguyễn Văn Minh', '2002-12-29', 0, '000000013', N'nv13@attm.com', 'CV002', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV014', N'Trần Thị Nga', '2003-12-29', 1, '000000014', N'nv14@attm.com', 'CV003', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV015', N'Lê Văn Oanh', '2004-12-28', 0, '000000015', N'nv15@attm.com', 'CV004', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV016', N'Phạm Thị Phương', '2005-12-28', 1, '000000016', N'nv16@attm.com', 'CV005', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV017', N'Hoàng Văn Quân', '2006-12-28', 0, '000000017', N'nv17@attm.com', 'CV006', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV018', N'Vũ Thị Ruby', '2007-12-28', 1, '000000018', N'nv18@attm.com', 'CV001', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV019', N'Nguyễn Văn Sơn', '2008-12-27', 0, '000000019', N'nv19@attm.com', 'CV002', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV020', N'Trần Thị Tuyết', '2009-12-27', 1, '000000020', N'nv20@attm.com', 'CV003', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV021', N'Lê Văn Uyên', '2010-12-27', 0, '000000021', N'nv21@attm.com', 'CV004', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV022', N'Phạm Thị Vân', '1990-01-31', 1, '000000022', N'nv22@attm.com', 'CV005', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV023', N'Hoàng Văn Xuyên', '1991-01-31', 0, '000000023', N'nv23@attm.com', 'CV006', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV024', N'Vũ Thị Yến', '1992-01-31', 1, '000000024', N'nv24@attm.com', 'CV001', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV025', N'Nguyễn Văn Zũ', '1993-01-30', 0, '000000025', N'nv25@attm.com', 'CV002', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV026', N'Trần Thị Ánh', '1994-01-30', 1, '000000026', N'nv26@attm.com', 'CV003', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV027', N'Lê Văn Bảo', '1995-01-30', 0, '000000027', N'nv27@attm.com', 'CV004', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV028', N'Phạm Thị Châu', '1996-01-30', 1, '000000028', N'nv28@attm.com', 'CV005', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV029', N'Hoàng Văn Dũng', '1997-01-29', 0, '000000029', N'nv29@attm.com', 'CV006', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV030', N'Vũ Thị Eo', '1998-01-29', 1, '000000030', N'nv30@attm.com', 'CV001', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV031', N'Nguyễn Văn Phong', '1999-01-29', 0, '000000031', N'nv31@attm.com', 'CV002', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV032', N'Trần Thị Gấm', '2000-01-29', 1, '000000032', N'nv32@attm.com', 'CV003', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV033', N'Lê Văn Hào', '2001-01-28', 0, '000000033', N'nv33@attm.com', 'CV004', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV034', N'Phạm Thị Iến', '2002-01-28', 1, '000000034', N'nv34@attm.com', 'CV005', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV035', N'Hoàng Văn Kỳ', '2003-01-28', 0, '000000035', N'nv35@attm.com', 'CV006', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV036', N'Vũ Thị Lệ', '2004-01-28', 1, '000000036', N'nv36@attm.com', 'CV001', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV037', N'Nguyễn Văn Mạnh', '2005-01-27', 0, '000000037', N'nv37@attm.com', 'CV002', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV038', N'Trần Thị Ngọc', '2006-01-27', 1, '000000038', N'nv38@attm.com', 'CV003', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV039', N'Lê Văn Oai', '2007-01-27', 0, '000000039', N'nv39@attm.com', 'CV004', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV040', N'Phạm Thị Phấn', '2008-01-27', 1, '000000040', N'nv40@attm.com', 'CV005', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV041', N'Hoàng Văn Quang', '2009-01-26', 0, '000000041', N'nv41@attm.com', 'CV006', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV042', N'Vũ Thị Rơ', '2010-01-26', 1, '000000042', N'nv42@attm.com', 'CV001', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV043', N'Nguyễn Văn Sỹ', '2011-01-26', 0, '000000043', N'nv43@attm.com', 'CV002', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV044', N'Trần Thị Tâm', '1990-03-02', 1, '000000044', N'nv44@attm.com', 'CV003', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV045', N'Lê Văn Uy', '1991-03-02', 0, '000000045', N'nv45@attm.com', 'CV004', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV046', N'Phạm Thị Vui', '1992-03-01', 1, '000000046', N'nv46@attm.com', 'CV005', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV047', N'Hoàng Văn Xứng', '1993-03-01', 0, '000000047', N'nv47@attm.com', 'CV006', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV048', N'Vũ Thị Yêu', '1994-03-01', 1, '000000048', N'nv48@attm.com', 'CV001', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV049', N'Nguyễn Văn Zậy', '1995-03-01', 0, '000000049', N'nv49@attm.com', 'CV002', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, maChucVu, tenDangNhap) VALUES ('NV050', N'Trần Thị An', '1996-02-29', 1, '000000050', N'nv50@attm.com', 'CV003', NULL);

-- Inserts for KhachHang
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH001', N'Nguyễn Văn An', 1, '1996-02-05', N'kh1@mail.com', '000000001', '00000000001', 10, 'HG002');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH002', N'Trần Thị Bình', 0, '1997-03-11', N'kh2@mail.com', '000000002', '00000000002', 20, 'HG003');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH003', N'Lê Văn Cường', 1, '1998-04-15', N'kh3@mail.com', '000000003', '00000000003', 30, 'HG003');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH004', N'Phạm Thị Dung', 0, '1999-05-20', N'kh4@mail.com', '000000004', '00000000004', 40, 'HG004');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH005', N'Hoàng Văn Đạt', 1, '2000-06-23', N'kh5@mail.com', '000000005', '00000000005', 50, 'HG004');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH006', N'Vũ Thị Em', 0, '2001-07-28', N'kh6@mail.com', '000000006', '00000000006', 0, 'HG001');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH007', N'Nguyễn Văn Phúc', 1, '2002-09-01', N'kh7@mail.com', '000000007', '00000000007', 10, 'HG002');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH008', N'Trần Thị Giang', 0, '2003-10-06', N'kh8@mail.com', '000000008', '00000000008', 20, 'HG003');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH009', N'Lê Văn Hải', 1, '2004-11-09', N'kh9@mail.com', '000000009', '00000000009', 30, 'HG003');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH010', N'Phạm Thị Hương', 0, '2005-12-14', N'kh10@mail.com', '000000010', '00000000010', 40, 'HG004');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH011', N'Hoàng Văn Khoa', 1, '2007-01-18', N'kh11@mail.com', '000000011', '00000000011', 50, 'HG004');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH012', N'Vũ Thị Lan', 0, '2008-02-22', N'kh12@mail.com', '000000012', '00000000012', 0, 'HG001');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH013', N'Nguyễn Văn Minh', 1, '2009-03-28', N'kh13@mail.com', '000000013', '00000000013', 10, 'HG002');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH014', N'Trần Thị Nga', 0, '2010-05-02', N'kh14@mail.com', '000000014', '00000000014', 20, 'HG003');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH015', N'Lê Văn Oanh', 1, '2011-06-06', N'kh15@mail.com', '000000015', '00000000015', 30, 'HG003');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH016', N'Phạm Thị Phương', 0, '2012-07-10', N'kh16@mail.com', '000000016', '00000000016', 40, 'HG004');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH017', N'Hoàng Văn Quân', 1, '2013-08-14', N'kh17@mail.com', '000000017', '00000000017', 50, 'HG004');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH018', N'Vũ Thị Ruby', 0, '2014-09-18', N'kh18@mail.com', '000000018', '00000000018', 0, 'HG001');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH019', N'Nguyễn Văn Sơn', 1, '2015-10-23', N'kh19@mail.com', '000000019', '00000000019', 10, 'HG002');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH020', N'Trần Thị Tuyết', 0, '1995-01-01', N'kh20@mail.com', '000000020', '00000000020', 20, 'HG003');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH021', N'Lê Văn Uyên', 1, '1996-02-05', N'kh21@mail.com', '000000021', '00000000021', 30, 'HG003');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH022', N'Phạm Thị Vân', 0, '1997-03-11', N'kh22@mail.com', '000000022', '00000000022', 40, 'HG004');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH023', N'Hoàng Văn Xuyên', 1, '1998-04-15', N'kh23@mail.com', '000000023', '00000000023', 50, 'HG004');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH024', N'Vũ Thị Yến', 0, '1999-05-20', N'kh24@mail.com', '000000024', '00000000024', 0, 'HG001');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH025', N'Nguyễn Văn Zũ', 1, '2000-06-23', N'kh25@mail.com', '000000025', '00000000025', 10, 'HG002');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH026', N'Trần Thị Ánh', 0, '2001-07-28', N'kh26@mail.com', '000000026', '00000000026', 20, 'HG003');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH027', N'Lê Văn Bảo', 1, '2002-09-01', N'kh27@mail.com', '000000027', '00000000027', 30, 'HG003');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH028', N'Phạm Thị Châu', 0, '2003-10-06', N'kh28@mail.com', '000000028', '00000000028', 40, 'HG004');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH029', N'Hoàng Văn Dũng', 1, '2004-11-09', N'kh29@mail.com', '000000029', '00000000029', 50, 'HG004');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH030', N'Vũ Thị Eo', 0, '2005-12-14', N'kh30@mail.com', '000000030', '00000000030', 0, 'HG001');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH031', N'Nguyễn Văn Phong', 1, '2007-01-18', N'kh31@mail.com', '000000031', '00000000031', 10, 'HG002');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH032', N'Trần Thị Gấm', 0, '2008-02-22', N'kh32@mail.com', '000000032', '00000000032', 20, 'HG003');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH033', N'Lê Văn Hào', 1, '2009-03-28', N'kh33@mail.com', '000000033', '00000000033', 30, 'HG003');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH034', N'Phạm Thị Iến', 0, '2010-05-02', N'kh34@mail.com', '000000034', '00000000034', 40, 'HG004');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH035', N'Hoàng Văn Kỳ', 1, '2011-06-06', N'kh35@mail.com', '000000035', '00000000035', 50, 'HG004');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH036', N'Vũ Thị Lệ', 0, '2012-07-10', N'kh36@mail.com', '000000036', '00000000036', 0, 'HG001');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH037', N'Nguyễn Văn Mạnh', 1, '2013-08-14', N'kh37@mail.com', '000000037', '00000000037', 10, 'HG002');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH038', N'Trần Thị Ngọc', 0, '2014-09-18', N'kh38@mail.com', '000000038', '00000000038', 20, 'HG003');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH039', N'Lê Văn Oai', 1, '2015-10-23', N'kh39@mail.com', '000000039', '00000000039', 30, 'HG003');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH040', N'Phạm Thị Phấn', 0, '1995-01-01', N'kh40@mail.com', '000000040', '00000000040', 40, 'HG004');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH041', N'Hoàng Văn Quang', 1, '1996-02-05', N'kh41@mail.com', '000000041', '00000000041', 50, 'HG004');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH042', N'Vũ Thị Rơ', 0, '1997-03-11', N'kh42@mail.com', '000000042', '00000000042', 0, 'HG001');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH043', N'Nguyễn Văn Sỹ', 1, '1998-04-15', N'kh43@mail.com', '000000043', '00000000043', 10, 'HG002');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH044', N'Trần Thị Tâm', 0, '1999-05-20', N'kh44@mail.com', '000000044', '00000000044', 20, 'HG003');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH045', N'Lê Văn Uy', 1, '2000-06-23', N'kh45@mail.com', '000000045', '00000000045', 30, 'HG003');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH046', N'Phạm Thị Vui', 0, '2001-07-28', N'kh46@mail.com', '000000046', '00000000046', 40, 'HG004');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH047', N'Hoàng Văn Xứng', 1, '2002-09-01', N'kh47@mail.com', '000000047', '00000000047', 50, 'HG004');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH048', N'Vũ Thị Yêu', 0, '2003-10-06', N'kh48@mail.com', '000000048', '00000000048', 0, 'HG001');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH049', N'Nguyễn Văn Zậy', 1, '2004-11-09', N'kh49@mail.com', '000000049', '00000000049', 10, 'HG002');
INSERT INTO KhachHang (maKH, tenKH, gioiTinh, ngaySinh, email, sdt, soCCCD, diemTichLuy, maHang) VALUES ('KH050', N'Trần Thị An', 0, '2005-12-14', N'kh50@mail.com', '000000050', '00000000050', 0, 'HG001');

-- Inserts for KhuyenMai
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, maTrangThaiKM) VALUES ('KM001', N'KM 1 giảm 6%', N'Áp dụng cho booking từ 2 đêm trở lên', 6, '2025-10-27 15:48:08', '2025-11-26 15:48:08', 'TTKM001');
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, maTrangThaiKM) VALUES ('KM002', N'KM 2 giảm 7%', N'Áp dụng cho booking từ 2 đêm trở lên', 7, '2025-10-28 15:48:08', '2025-11-27 15:48:08', 'TTKM002');
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, maTrangThaiKM) VALUES ('KM003', N'KM 3 giảm 8%', N'Áp dụng cho booking từ 2 đêm trở lên', 8, '2025-10-29 15:48:08', '2025-11-28 15:48:08', 'TTKM003');
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, maTrangThaiKM) VALUES ('KM004', N'KM 4 giảm 9%', N'Áp dụng cho booking từ 2 đêm trở lên', 9, '2025-10-30 15:48:08', '2025-11-29 15:48:08', 'TTKM004');
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, maTrangThaiKM) VALUES ('KM005', N'KM 5 giảm 10%', N'Áp dụng cho booking từ 2 đêm trở lên', 10, '2025-10-31 15:48:08', '2025-11-30 15:48:08', 'TTKM001');
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, maTrangThaiKM) VALUES ('KM006', N'KM 6 giảm 11%', N'Áp dụng cho booking từ 2 đêm trở lên', 11, '2025-11-01 15:48:08', '2025-12-01 15:48:08', 'TTKM002');
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, maTrangThaiKM) VALUES ('KM007', N'KM 7 giảm 12%', N'Áp dụng cho booking từ 2 đêm trở lên', 12, '2025-11-02 15:48:08', '2025-12-02 15:48:08', 'TTKM003');
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, maTrangThaiKM) VALUES ('KM008', N'KM 8 giảm 13%', N'Áp dụng cho booking từ 2 đêm trở lên', 13, '2025-11-03 15:48:08', '2025-12-03 15:48:08', 'TTKM004');
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, maTrangThaiKM) VALUES ('KM009', N'KM 9 giảm 14%', N'Áp dụng cho booking từ 2 đêm trở lên', 14, '2025-11-04 15:48:08', '2025-12-04 15:48:08', 'TTKM001');
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, maTrangThaiKM) VALUES ('KM010', N'KM 10 giảm 15%', N'Áp dụng cho booking từ 2 đêm trở lên', 15, '2025-11-05 15:48:08', '2025-12-05 15:48:08', 'TTKM002');

-- Inserts for Phong
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P001', 1, 101, 2, 1500000, 300000.0, 'LP001', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P002', 1, 102, 4, 2050000, 512500.0, 'LP002', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P003', 1, 103, 4, 3050000, 915000.0, 'LP003', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P004', 1, 104, 4, 5050000, 2020000.0, 'LP004', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P005', 1, 105, 4, 3550000, 532500.0, 'LP005', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P006', 1, 106, 2, 1500000, 300000.0, 'LP001', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P007', 1, 107, 4, 2050000, 512500.0, 'LP002', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P008', 1, 108, 4, 3050000, 915000.0, 'LP003', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P009', 1, 109, 4, 5050000, 2020000.0, 'LP004', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P010', 1, 110, 4, 3550000, 532500.0, 'LP005', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P011', 1, 111, 2, 1500000, 300000.0, 'LP001', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P012', 1, 112, 4, 2050000, 512500.0, 'LP002', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P013', 1, 113, 4, 3050000, 915000.0, 'LP003', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P014', 1, 114, 4, 5050000, 2020000.0, 'LP004', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P015', 1, 115, 4, 3550000, 532500.0, 'LP005', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P016', 1, 116, 2, 1500000, 300000.0, 'LP001', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P017', 1, 117, 4, 2050000, 512500.0, 'LP002', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P018', 1, 118, 4, 3050000, 915000.0, 'LP003', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P019', 1, 119, 4, 5050000, 2020000.0, 'LP004', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P020', 1, 120, 4, 3550000, 532500.0, 'LP005', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P021', 2, 201, 2, 1500000, 300000.0, 'LP001', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P022', 2, 202, 4, 2050000, 512500.0, 'LP002', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P023', 2, 203, 4, 3050000, 915000.0, 'LP003', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P024', 2, 204, 4, 5050000, 2020000.0, 'LP004', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P025', 2, 205, 4, 3550000, 532500.0, 'LP005', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P026', 2, 206, 2, 1500000, 300000.0, 'LP001', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P027', 2, 207, 4, 2050000, 512500.0, 'LP002', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P028', 2, 208, 4, 3050000, 915000.0, 'LP003', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P029', 2, 209, 4, 5050000, 2020000.0, 'LP004', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P030', 2, 210, 4, 3550000, 532500.0, 'LP005', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P031', 2, 211, 2, 1500000, 300000.0, 'LP001', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P032', 2, 212, 4, 2050000, 512500.0, 'LP002', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P033', 2, 213, 4, 3050000, 915000.0, 'LP003', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P034', 2, 214, 4, 5050000, 2020000.0, 'LP004', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P035', 2, 215, 4, 3550000, 532500.0, 'LP005', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P036', 2, 216, 2, 1500000, 300000.0, 'LP001', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P037', 2, 217, 4, 2050000, 512500.0, 'LP002', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P038', 2, 218, 4, 3050000, 915000.0, 'LP003', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P039', 2, 219, 4, 5050000, 2020000.0, 'LP004', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P040', 2, 220, 4, 3550000, 532500.0, 'LP005', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P041', 3, 301, 2, 1500000, 300000.0, 'LP001', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P042', 3, 302, 4, 2050000, 512500.0, 'LP002', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P043', 3, 303, 4, 3050000, 915000.0, 'LP003', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P044', 3, 304, 4, 5050000, 2020000.0, 'LP004', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P045', 3, 305, 4, 3550000, 532500.0, 'LP005', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P046', 3, 306, 2, 1500000, 300000.0, 'LP001', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P047', 3, 307, 4, 2050000, 512500.0, 'LP002', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P048', 3, 308, 4, 3050000, 915000.0, 'LP003', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P049', 3, 309, 4, 5050000, 2020000.0, 'LP004', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P050', 3, 310, 4, 3550000, 532500.0, 'LP005', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P051', 3, 311, 2, 1500000, 300000.0, 'LP001', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P052', 3, 312, 4, 2050000, 512500.0, 'LP002', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P053', 3, 313, 4, 3050000, 915000.0, 'LP003', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P054', 3, 314, 4, 5050000, 2020000.0, 'LP004', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P055', 3, 315, 4, 3550000, 532500.0, 'LP005', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P056', 3, 316, 2, 1500000, 300000.0, 'LP001', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P057', 3, 317, 4, 2050000, 512500.0, 'LP002', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P058', 3, 318, 4, 3050000, 915000.0, 'LP003', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P059', 3, 319, 4, 5050000, 2020000.0, 'LP004', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P060', 3, 320, 4, 3550000, 532500.0, 'LP005', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P061', 4, 401, 2, 1500000, 300000.0, 'LP001', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P062', 4, 402, 4, 2050000, 512500.0, 'LP002', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P063', 4, 403, 4, 3050000, 915000.0, 'LP003', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P064', 4, 404, 4, 5050000, 2020000.0, 'LP004', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P065', 4, 405, 4, 3550000, 532500.0, 'LP005', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P066', 4, 406, 2, 1500000, 300000.0, 'LP001', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P067', 4, 407, 4, 2050000, 512500.0, 'LP002', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P068', 4, 408, 4, 3050000, 915000.0, 'LP003', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P069', 4, 409, 4, 5050000, 2020000.0, 'LP004', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P070', 4, 410, 4, 3550000, 532500.0, 'LP005', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P071', 4, 411, 2, 1500000, 300000.0, 'LP001', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P072', 4, 412, 4, 2050000, 512500.0, 'LP002', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P073', 4, 413, 4, 3050000, 915000.0, 'LP003', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P074', 4, 414, 4, 5050000, 2020000.0, 'LP004', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P075', 4, 415, 4, 3550000, 532500.0, 'LP005', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P076', 4, 416, 2, 1500000, 300000.0, 'LP001', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P077', 4, 417, 4, 2050000, 512500.0, 'LP002', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P078', 4, 418, 4, 3050000, 915000.0, 'LP003', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P079', 4, 419, 4, 5050000, 2020000.0, 'LP004', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P080', 4, 420, 4, 3550000, 532500.0, 'LP005', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P081', 5, 501, 2, 1500000, 300000.0, 'LP001', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P082', 5, 502, 4, 2050000, 512500.0, 'LP002', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P083', 5, 503, 4, 3050000, 915000.0, 'LP003', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P084', 5, 504, 4, 5050000, 2020000.0, 'LP004', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P085', 5, 505, 4, 3550000, 532500.0, 'LP005', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P086', 5, 506, 2, 1500000, 300000.0, 'LP001', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P087', 5, 507, 4, 2050000, 512500.0, 'LP002', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P088', 5, 508, 4, 3050000, 915000.0, 'LP003', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P089', 5, 509, 4, 5050000, 2020000.0, 'LP004', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P090', 5, 510, 4, 3550000, 532500.0, 'LP005', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P091', 5, 511, 2, 1500000, 300000.0, 'LP001', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P092', 5, 512, 4, 2050000, 512500.0, 'LP002', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P093', 5, 513, 4, 3050000, 915000.0, 'LP003', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P094', 5, 514, 4, 5050000, 2020000.0, 'LP004', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P095', 5, 515, 4, 3550000, 532500.0, 'LP005', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P096', 5, 516, 2, 1500000, 300000.0, 'LP001', 'TTP004');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P097', 5, 517, 4, 2050000, 512500.0, 'LP002', 'TTP001');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P098', 5, 518, 4, 3050000, 915000.0, 'LP003', 'TTP002');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P099', 5, 519, 4, 5050000, 2020000.0, 'LP004', 'TTP003');
INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, maTrangThaiPhong) VALUES ('P100', 5, 520, 4, 3550000, 532500.0, 'LP005', 'TTP004');

-- Inserts for HoaDon
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000001', '2025-06-05 15:48:08', '2025-06-05 15:48:08', '2025-06-10 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH001', 'NV001');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000002', '2025-06-06 15:48:08', '2025-06-06 15:48:08', '2025-06-09 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH002', 'NV002');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000003', '2025-06-07 15:48:08', '2025-06-07 15:48:08', '2025-06-11 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH003', 'NV003');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000004', '2025-06-08 15:48:08', '2025-06-09 15:48:08', '2025-06-13 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH004', 'NV004');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000005', '2025-06-09 15:48:08', '2025-06-11 15:48:08', '2025-06-15 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, 'KM006', 'KH005', 'NV005');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000006', '2025-06-10 15:48:08', '2025-06-10 15:48:08', '2025-06-11 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH006', 'NV006');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000007', '2025-06-11 15:48:08', '2025-06-12 15:48:08', '2025-06-13 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH007', 'NV007');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000008', '2025-06-12 15:48:08', '2025-06-13 15:48:08', '2025-06-17 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH008', 'NV008');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000009', '2025-06-13 15:48:08', '2025-06-15 15:48:08', '2025-06-16 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH009', 'NV009');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000010', '2025-06-14 15:48:08', '2025-06-16 15:48:08', '2025-06-20 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, 'KM001', 'KH010', 'NV010');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000011', '2025-06-15 15:48:08', '2025-06-16 15:48:08', '2025-06-18 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH011', 'NV011');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000012', '2025-06-16 15:48:08', '2025-06-18 15:48:08', '2025-06-19 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH012', 'NV012');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000013', '2025-06-17 15:48:08', '2025-06-18 15:48:08', '2025-06-19 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH013', 'NV013');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000014', '2025-06-18 15:48:08', '2025-06-18 15:48:08', '2025-06-19 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH014', 'NV014');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000015', '2025-06-19 15:48:08', '2025-06-21 15:48:08', '2025-06-26 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, 'KM006', 'KH015', 'NV015');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000016', '2025-06-20 15:48:08', '2025-06-20 15:48:08', '2025-06-24 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH016', 'NV016');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000017', '2025-06-21 15:48:08', '2025-06-23 15:48:08', '2025-06-25 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH017', 'NV017');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000018', '2025-06-22 15:48:08', '2025-06-23 15:48:08', '2025-06-24 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH018', 'NV018');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000019', '2025-06-23 15:48:08', '2025-06-25 15:48:08', '2025-06-27 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH019', 'NV019');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000020', '2025-06-24 15:48:08', '2025-06-25 15:48:08', '2025-06-29 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, 'KM001', 'KH020', 'NV020');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000021', '2025-06-25 15:48:08', '2025-06-27 15:48:08', '2025-06-29 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH021', 'NV021');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000022', '2025-06-26 15:48:08', '2025-06-27 15:48:08', '2025-06-29 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH022', 'NV022');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000023', '2025-06-27 15:48:08', '2025-06-29 15:48:08', '2025-07-01 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH023', 'NV023');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000024', '2025-06-28 15:48:08', '2025-06-29 15:48:08', '2025-07-02 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH024', 'NV024');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000025', '2025-06-29 15:48:08', '2025-06-29 15:48:08', '2025-07-03 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, 'KM006', 'KH025', 'NV025');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000026', '2025-06-30 15:48:08', '2025-07-02 15:48:08', '2025-07-03 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH026', 'NV026');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000027', '2025-07-01 15:48:08', '2025-07-01 15:48:08', '2025-07-04 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH027', 'NV027');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000028', '2025-07-02 15:48:08', '2025-07-02 15:48:08', '2025-07-05 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH028', 'NV028');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000029', '2025-07-03 15:48:08', '2025-07-05 15:48:08', '2025-07-10 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH029', 'NV029');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000030', '2025-07-04 15:48:08', '2025-07-05 15:48:08', '2025-07-10 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, 'KM001', 'KH030', 'NV030');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000031', '2025-07-05 15:48:08', '2025-07-07 15:48:08', '2025-07-09 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH031', 'NV031');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000032', '2025-07-06 15:48:08', '2025-07-07 15:48:08', '2025-07-10 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH032', 'NV032');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000033', '2025-07-07 15:48:08', '2025-07-09 15:48:08', '2025-07-13 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH033', 'NV033');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000034', '2025-07-08 15:48:08', '2025-07-10 15:48:08', '2025-07-14 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH034', 'NV034');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000035', '2025-07-09 15:48:08', '2025-07-11 15:48:08', '2025-07-12 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, 'KM006', 'KH035', 'NV035');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000036', '2025-07-10 15:48:08', '2025-07-11 15:48:08', '2025-07-13 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH036', 'NV036');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000037', '2025-07-11 15:48:08', '2025-07-13 15:48:08', '2025-07-17 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH037', 'NV037');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000038', '2025-07-12 15:48:08', '2025-07-13 15:48:08', '2025-07-15 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH038', 'NV038');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000039', '2025-07-13 15:48:08', '2025-07-14 15:48:08', '2025-07-19 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH039', 'NV039');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000040', '2025-07-14 15:48:08', '2025-07-16 15:48:08', '2025-07-19 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, 'KM001', 'KH040', 'NV040');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000041', '2025-07-15 15:48:08', '2025-07-15 15:48:08', '2025-07-19 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH041', 'NV041');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000042', '2025-07-16 15:48:08', '2025-07-18 15:48:08', '2025-07-23 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH042', 'NV042');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000043', '2025-07-17 15:48:08', '2025-07-17 15:48:08', '2025-07-19 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH043', 'NV043');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000044', '2025-07-18 15:48:08', '2025-07-20 15:48:08', '2025-07-24 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH044', 'NV044');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000045', '2025-07-19 15:48:08', '2025-07-20 15:48:08', '2025-07-24 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, 'KM006', 'KH045', 'NV045');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000046', '2025-07-20 15:48:08', '2025-07-22 15:48:08', '2025-07-23 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH046', 'NV046');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000047', '2025-07-21 15:48:08', '2025-07-22 15:48:08', '2025-07-23 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH047', 'NV047');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000048', '2025-07-22 15:48:08', '2025-07-23 15:48:08', '2025-07-28 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH048', 'NV048');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000049', '2025-07-23 15:48:08', '2025-07-25 15:48:08', '2025-07-30 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH049', 'NV049');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000050', '2025-07-24 15:48:08', '2025-07-25 15:48:08', '2025-07-27 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, 'KM001', 'KH050', 'NV050');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000051', '2025-07-25 15:48:08', '2025-07-25 15:48:08', '2025-07-30 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH001', 'NV001');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000052', '2025-07-26 15:48:08', '2025-07-26 15:48:08', '2025-07-27 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH002', 'NV002');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000053', '2025-07-27 15:48:08', '2025-07-27 15:48:08', '2025-08-01 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH003', 'NV003');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000054', '2025-07-28 15:48:08', '2025-07-30 15:48:08', '2025-08-01 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH004', 'NV004');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000055', '2025-07-29 15:48:08', '2025-07-30 15:48:08', '2025-08-04 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, 'KM006', 'KH005', 'NV005');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000056', '2025-07-30 15:48:08', '2025-07-31 15:48:08', '2025-08-05 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH006', 'NV006');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000057', '2025-07-31 15:48:08', '2025-08-01 15:48:08', '2025-08-05 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH007', 'NV007');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000058', '2025-08-01 15:48:08', '2025-08-02 15:48:08', '2025-08-07 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH008', 'NV008');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000059', '2025-08-02 15:48:08', '2025-08-04 15:48:08', '2025-08-05 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH009', 'NV009');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000060', '2025-08-03 15:48:08', '2025-08-04 15:48:08', '2025-08-09 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, 'KM001', 'KH010', 'NV010');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000061', '2025-08-04 15:48:08', '2025-08-04 15:48:08', '2025-08-09 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH011', 'NV011');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000062', '2025-08-05 15:48:08', '2025-08-07 15:48:08', '2025-08-09 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH012', 'NV012');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000063', '2025-08-06 15:48:08', '2025-08-07 15:48:08', '2025-08-08 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH013', 'NV013');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000064', '2025-08-07 15:48:08', '2025-08-08 15:48:08', '2025-08-11 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH014', 'NV014');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000065', '2025-08-08 15:48:08', '2025-08-10 15:48:08', '2025-08-15 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, 'KM006', 'KH015', 'NV015');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000066', '2025-08-09 15:48:08', '2025-08-09 15:48:08', '2025-08-14 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH016', 'NV016');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000067', '2025-08-10 15:48:08', '2025-08-11 15:48:08', '2025-08-15 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH017', 'NV017');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000068', '2025-08-11 15:48:08', '2025-08-12 15:48:08', '2025-08-16 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH018', 'NV018');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000069', '2025-08-12 15:48:08', '2025-08-13 15:48:08', '2025-08-14 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH019', 'NV019');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000070', '2025-08-13 15:48:08', '2025-08-15 15:48:08', '2025-08-20 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, 'KM001', 'KH020', 'NV020');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000071', '2025-08-14 15:48:08', '2025-08-16 15:48:08', '2025-08-21 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH021', 'NV021');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000072', '2025-08-15 15:48:08', '2025-08-16 15:48:08', '2025-08-20 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH022', 'NV022');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000073', '2025-08-16 15:48:08', '2025-08-18 15:48:08', '2025-08-19 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH023', 'NV023');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000074', '2025-08-17 15:48:08', '2025-08-17 15:48:08', '2025-08-19 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH024', 'NV024');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000075', '2025-08-18 15:48:08', '2025-08-20 15:48:08', '2025-08-25 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, 'KM006', 'KH025', 'NV025');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000076', '2025-08-19 15:48:08', '2025-08-19 15:48:08', '2025-08-20 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH026', 'NV026');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000077', '2025-08-20 15:48:08', '2025-08-22 15:48:08', '2025-08-25 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH027', 'NV027');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000078', '2025-08-21 15:48:08', '2025-08-21 15:48:08', '2025-08-22 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH028', 'NV028');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000079', '2025-08-22 15:48:08', '2025-08-22 15:48:08', '2025-08-23 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH029', 'NV029');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000080', '2025-08-23 15:48:08', '2025-08-24 15:48:08', '2025-08-25 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, 'KM001', 'KH030', 'NV030');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000081', '2025-08-24 15:48:08', '2025-08-25 15:48:08', '2025-08-27 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH031', 'NV031');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000082', '2025-08-25 15:48:08', '2025-08-26 15:48:08', '2025-08-27 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH032', 'NV032');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000083', '2025-08-26 15:48:08', '2025-08-28 15:48:08', '2025-08-30 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH033', 'NV033');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000084', '2025-08-27 15:48:08', '2025-08-28 15:48:08', '2025-08-31 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH034', 'NV034');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000085', '2025-08-28 15:48:08', '2025-08-28 15:48:08', '2025-08-30 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, 'KM006', 'KH035', 'NV035');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000086', '2025-08-29 15:48:08', '2025-08-29 15:48:08', '2025-09-01 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH036', 'NV036');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000087', '2025-08-30 15:48:08', '2025-09-01 15:48:08', '2025-09-03 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH037', 'NV037');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000088', '2025-08-31 15:48:08', '2025-09-02 15:48:08', '2025-09-05 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH038', 'NV038');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000089', '2025-09-01 15:48:08', '2025-09-03 15:48:08', '2025-09-06 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH039', 'NV039');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000090', '2025-09-02 15:48:08', '2025-09-03 15:48:08', '2025-09-06 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, 'KM001', 'KH040', 'NV040');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000091', '2025-09-03 15:48:08', '2025-09-04 15:48:08', '2025-09-08 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH041', 'NV041');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000092', '2025-09-04 15:48:08', '2025-09-04 15:48:08', '2025-09-05 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH042', 'NV042');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000093', '2025-09-05 15:48:08', '2025-09-06 15:48:08', '2025-09-10 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH043', 'NV043');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000094', '2025-09-06 15:48:08', '2025-09-07 15:48:08', '2025-09-11 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH044', 'NV044');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000095', '2025-09-07 15:48:08', '2025-09-07 15:48:08', '2025-09-10 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, 'KM006', 'KH045', 'NV045');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000096', '2025-09-08 15:48:08', '2025-09-08 15:48:08', '2025-09-11 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH046', 'NV046');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000097', '2025-09-09 15:48:08', '2025-09-11 15:48:08', '2025-09-16 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH047', 'NV047');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000098', '2025-09-10 15:48:08', '2025-09-10 15:48:08', '2025-09-15 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH048', 'NV048');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000099', '2025-09-11 15:48:08', '2025-09-12 15:48:08', '2025-09-13 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH049', 'NV049');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000100', '2025-09-12 15:48:08', '2025-09-12 15:48:08', '2025-09-13 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, 'KM001', 'KH050', 'NV050');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000101', '2025-09-13 15:48:08', '2025-09-14 15:48:08', '2025-09-16 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH001', 'NV001');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000102', '2025-09-14 15:48:08', '2025-09-14 15:48:08', '2025-09-16 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH002', 'NV002');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000103', '2025-09-15 15:48:08', '2025-09-16 15:48:08', '2025-09-21 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH003', 'NV003');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000104', '2025-09-16 15:48:08', '2025-09-18 15:48:08', '2025-09-22 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH004', 'NV004');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000105', '2025-09-17 15:48:08', '2025-09-19 15:48:08', '2025-09-21 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, 'KM006', 'KH005', 'NV005');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000106', '2025-09-18 15:48:08', '2025-09-20 15:48:08', '2025-09-25 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH006', 'NV006');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000107', '2025-09-19 15:48:08', '2025-09-20 15:48:08', '2025-09-22 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH007', 'NV007');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000108', '2025-09-20 15:48:08', '2025-09-22 15:48:08', '2025-09-23 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH008', 'NV008');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000109', '2025-09-21 15:48:08', '2025-09-22 15:48:08', '2025-09-27 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH009', 'NV009');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000110', '2025-09-22 15:48:08', '2025-09-23 15:48:08', '2025-09-27 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, 'KM001', 'KH010', 'NV010');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000111', '2025-09-23 15:48:08', '2025-09-23 15:48:08', '2025-09-26 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH011', 'NV011');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000112', '2025-09-24 15:48:08', '2025-09-24 15:48:08', '2025-09-26 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH012', 'NV012');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000113', '2025-09-25 15:48:08', '2025-09-25 15:48:08', '2025-09-28 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH013', 'NV013');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000114', '2025-09-26 15:48:08', '2025-09-26 15:48:08', '2025-09-27 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH014', 'NV014');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000115', '2025-09-27 15:48:08', '2025-09-28 15:48:08', '2025-10-01 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, 'KM006', 'KH015', 'NV015');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000116', '2025-09-28 15:48:08', '2025-09-30 15:48:08', '2025-10-02 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH016', 'NV016');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000117', '2025-09-29 15:48:08', '2025-09-30 15:48:08', '2025-10-05 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH017', 'NV017');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000118', '2025-09-30 15:48:08', '2025-10-01 15:48:08', '2025-10-03 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH018', 'NV018');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000119', '2025-10-01 15:48:08', '2025-10-01 15:48:08', '2025-10-06 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH019', 'NV019');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000120', '2025-10-02 15:48:08', '2025-10-02 15:48:08', '2025-10-07 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, 'KM001', 'KH020', 'NV020');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000121', '2025-10-03 15:48:08', '2025-10-03 15:48:08', '2025-10-08 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH021', 'NV021');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000122', '2025-10-04 15:48:08', '2025-10-05 15:48:08', '2025-10-07 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH022', 'NV022');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000123', '2025-10-05 15:48:08', '2025-10-07 15:48:08', '2025-10-12 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH023', 'NV023');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000124', '2025-10-06 15:48:08', '2025-10-08 15:48:08', '2025-10-09 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH024', 'NV024');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000125', '2025-10-07 15:48:08', '2025-10-08 15:48:08', '2025-10-10 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, 'KM006', 'KH025', 'NV025');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000126', '2025-10-08 15:48:08', '2025-10-09 15:48:08', '2025-10-10 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH026', 'NV026');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000127', '2025-10-09 15:48:08', '2025-10-09 15:48:08', '2025-10-14 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH027', 'NV027');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000128', '2025-10-10 15:48:08', '2025-10-12 15:48:08', '2025-10-16 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH028', 'NV028');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000129', '2025-10-11 15:48:08', '2025-10-13 15:48:08', '2025-10-15 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH029', 'NV029');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000130', '2025-10-12 15:48:08', '2025-10-13 15:48:08', '2025-10-14 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, 'KM001', 'KH030', 'NV030');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000131', '2025-10-13 15:48:08', '2025-10-15 15:48:08', '2025-10-19 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH031', 'NV031');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000132', '2025-10-14 15:48:08', '2025-10-15 15:48:08', '2025-10-20 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH032', 'NV032');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000133', '2025-10-15 15:48:08', '2025-10-16 15:48:08', '2025-10-17 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH033', 'NV033');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000134', '2025-10-16 15:48:08', '2025-10-17 15:48:08', '2025-10-22 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH034', 'NV034');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000135', '2025-10-17 15:48:08', '2025-10-18 15:48:08', '2025-10-21 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, 'KM006', 'KH035', 'NV035');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000136', '2025-10-18 15:48:08', '2025-10-18 15:48:08', '2025-10-20 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH036', 'NV036');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000137', '2025-10-19 15:48:08', '2025-10-19 15:48:08', '2025-10-22 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH037', 'NV037');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000138', '2025-10-20 15:48:08', '2025-10-22 15:48:08', '2025-10-24 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH038', 'NV038');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000139', '2025-10-21 15:48:08', '2025-10-22 15:48:08', '2025-10-26 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, NULL, 'KH039', 'NV039');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000140', '2025-10-22 15:48:08', '2025-10-22 15:48:08', '2025-10-25 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, 'KM001', 'KH040', 'NV040');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000141', '2025-10-23 15:48:08', '2025-10-25 15:48:08', '2025-10-26 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH041', 'NV041');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000142', '2025-10-24 15:48:08', '2025-10-25 15:48:08', '2025-10-30 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH042', 'NV042');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000143', '2025-10-25 15:48:08', '2025-10-26 15:48:08', '2025-10-31 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH043', 'NV043');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000144', '2025-10-26 15:48:08', '2025-10-27 15:48:08', '2025-11-01 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, NULL, 'KH044', 'NV044');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000145', '2025-10-27 15:48:08', '2025-10-27 15:48:08', '2025-10-28 15:48:08', 'PTTT002', 'TTHD001', NULL, NULL, 'KM006', 'KH045', 'NV045');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000146', '2025-10-28 15:48:08', '2025-10-30 15:48:08', '2025-10-31 15:48:08', 'PTTT001', 'TTHD002', NULL, NULL, NULL, 'KH046', 'NV046');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000147', '2025-10-29 15:48:08', '2025-10-29 15:48:08', '2025-10-31 15:48:08', 'PTTT002', 'TTHD003', NULL, NULL, NULL, 'KH047', 'NV047');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000148', '2025-10-30 15:48:08', '2025-10-30 15:48:08', '2025-11-01 15:48:08', 'PTTT001', 'TTHD001', NULL, NULL, NULL, 'KH048', 'NV048');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000149', '2025-10-31 15:48:08', '2025-11-02 15:48:08', '2025-11-04 15:48:08', 'PTTT002', 'TTHD002', NULL, NULL, NULL, 'KH049', 'NV049');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, maPTTT, maTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000150', '2025-11-01 15:48:08', '2025-11-02 15:48:08', '2025-11-05 15:48:08', 'PTTT001', 'TTHD003', NULL, NULL, 'KM001', 'KH050', 'NV050');

-- Inserts for ChiTietHoaDon
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000001', 'P077', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000001', 'P065', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000002', 'P033', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000002', 'P048', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000003', 'P044', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000004', 'P044', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000004', 'P015', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000005', 'P038', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000005', 'P031', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000006', 'P078', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000007', 'P100', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000007', 'P092', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000008', 'P063', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000008', 'P018', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000009', 'P075', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000010', 'P071', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000010', 'P099', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000011', 'P014', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000011', 'P042', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000012', 'P006', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000013', 'P053', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000013', 'P010', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000014', 'P049', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000014', 'P019', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000015', 'P017', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000016', 'P044', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000016', 'P015', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000017', 'P079', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000017', 'P076', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000018', 'P049', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000019', 'P010', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000019', 'P074', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000020', 'P071', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000020', 'P029', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000021', 'P073', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000022', 'P011', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000022', 'P035', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000023', 'P047', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000023', 'P038', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000024', 'P073', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000025', 'P069', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000025', 'P015', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000026', 'P059', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000026', 'P036', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000027', 'P014', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000028', 'P006', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000028', 'P038', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000029', 'P002', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000029', 'P079', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000030', 'P086', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000031', 'P002', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000031', 'P012', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000032', 'P053', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000032', 'P015', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000033', 'P006', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000034', 'P025', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000034', 'P031', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000035', 'P076', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000035', 'P054', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000036', 'P021', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000037', 'P015', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000037', 'P058', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000038', 'P022', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000038', 'P088', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000039', 'P031', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000040', 'P021', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000040', 'P096', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000041', 'P014', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000041', 'P056', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000042', 'P049', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000043', 'P070', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000043', 'P038', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000044', 'P071', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000044', 'P033', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000045', 'P092', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000046', 'P062', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000046', 'P041', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000047', 'P013', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000047', 'P027', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000048', 'P084', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000049', 'P041', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000049', 'P006', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000050', 'P004', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000050', 'P002', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000051', 'P038', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000052', 'P093', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000052', 'P077', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000053', 'P041', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000053', 'P058', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000054', 'P051', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000055', 'P041', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000055', 'P052', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000056', 'P009', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000056', 'P041', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000057', 'P077', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000058', 'P059', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000058', 'P015', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000059', 'P033', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000059', 'P028', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000060', 'P080', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000061', 'P100', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000061', 'P070', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000062', 'P089', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000062', 'P061', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000063', 'P085', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000064', 'P046', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000064', 'P034', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000065', 'P024', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000065', 'P070', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000066', 'P027', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000067', 'P040', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000067', 'P026', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000068', 'P032', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000068', 'P047', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000069', 'P011', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000070', 'P036', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000070', 'P012', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000071', 'P097', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000071', 'P058', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000072', 'P012', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000073', 'P084', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000073', 'P074', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000074', 'P083', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000074', 'P044', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000075', 'P030', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000076', 'P050', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000076', 'P040', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000077', 'P006', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000077', 'P042', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000078', 'P024', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000079', 'P041', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000079', 'P075', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000080', 'P039', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000080', 'P032', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000081', 'P043', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000082', 'P013', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000082', 'P070', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000083', 'P079', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000083', 'P075', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000084', 'P077', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000085', 'P012', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000085', 'P032', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000086', 'P029', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000086', 'P003', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000087', 'P032', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000088', 'P052', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000088', 'P010', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000089', 'P035', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000089', 'P071', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000090', 'P010', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000091', 'P094', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000091', 'P010', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000092', 'P003', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000092', 'P082', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000093', 'P002', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000094', 'P038', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000094', 'P097', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000095', 'P046', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000095', 'P064', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000096', 'P061', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000097', 'P020', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000097', 'P013', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000098', 'P065', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000098', 'P100', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000099', 'P042', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000100', 'P010', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000100', 'P066', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000101', 'P086', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000101', 'P023', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000102', 'P023', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000103', 'P100', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000103', 'P020', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000104', 'P019', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000104', 'P041', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000105', 'P040', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000106', 'P014', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000106', 'P091', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000107', 'P066', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000107', 'P078', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000108', 'P038', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000109', 'P017', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000109', 'P027', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000110', 'P019', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000110', 'P070', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000111', 'P093', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000112', 'P005', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000112', 'P100', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000113', 'P041', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000113', 'P080', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000114', 'P087', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000115', 'P071', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000115', 'P096', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000116', 'P089', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000116', 'P027', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000117', 'P023', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000118', 'P039', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000118', 'P056', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000119', 'P069', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000119', 'P021', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000120', 'P007', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000121', 'P092', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000121', 'P086', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000122', 'P032', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000122', 'P033', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000123', 'P100', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000124', 'P009', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000124', 'P088', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000125', 'P058', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000125', 'P056', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000126', 'P071', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000127', 'P033', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000127', 'P070', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000128', 'P057', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000128', 'P069', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000129', 'P059', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000130', 'P002', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000130', 'P051', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000131', 'P044', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000131', 'P022', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000132', 'P034', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000133', 'P063', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000133', 'P004', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000134', 'P083', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000134', 'P054', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000135', 'P074', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000136', 'P003', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000136', 'P008', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000137', 'P089', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000137', 'P046', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000138', 'P075', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000139', 'P018', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000139', 'P076', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000140', 'P017', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000140', 'P018', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000141', 'P034', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000142', 'P036', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000142', 'P051', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000143', 'P073', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000143', 'P052', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000144', 'P023', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000145', 'P079', 3030000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000145', 'P012', 1537500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000146', 'P030', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000146', 'P063', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000147', 'P001', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000148', 'P023', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000148', 'P068', 2135000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000149', 'P041', 1200000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000149', 'P065', 3017500.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000150', 'P084', 3030000.0);

-- Inserts for NguoiO
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 1', '2000-10-27', 0, '000000001', '00000000001', 'HD000001', 'P001');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 2', '2001-08-23', 1, '000000002', '00000000002', 'HD000002', 'P002');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 3', '2002-06-19', 0, '000000003', '00000000003', 'HD000003', 'P003');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 4', '2003-04-15', 1, '000000004', '00000000004', 'HD000004', 'P004');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 5', '2004-02-09', 0, '000000005', '00000000005', 'HD000005', 'P005');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 6', '2004-12-05', 1, '000000006', '00000000006', 'HD000006', 'P006');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 7', '2005-10-01', 0, '000000007', '00000000007', 'HD000007', 'P007');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 8', '2006-07-28', 1, '000000008', '00000000008', 'HD000008', 'P008');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 9', '2007-05-24', 0, '000000009', '00000000009', 'HD000009', 'P009');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 10', '2008-03-19', 1, '000000010', '00000000010', 'HD000010', 'P010');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 11', '2009-01-13', 0, '000000011', '00000000011', 'HD000011', 'P011');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 12', '2009-11-09', 1, '000000012', '00000000012', 'HD000012', 'P012');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 13', '2010-09-05', 0, '000000013', '00000000013', 'HD000013', 'P013');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 14', '2011-07-02', 1, '000000014', '00000000014', 'HD000014', 'P014');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 15', '2012-04-27', 0, '000000015', '00000000015', 'HD000015', 'P015');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 16', '2013-02-21', 1, '000000016', '00000000016', 'HD000016', 'P016');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 17', '2013-12-18', 0, '000000017', '00000000017', 'HD000017', 'P017');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 18', '2014-10-14', 1, '000000018', '00000000018', 'HD000018', 'P018');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 19', '2015-08-10', 0, '000000019', '00000000019', 'HD000019', 'P019');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 20', '2016-06-05', 1, '000000020', '00000000020', 'HD000020', 'P020');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 21', '2017-04-01', 0, '000000021', '00000000021', 'HD000021', 'P021');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 22', '2018-01-26', 1, '000000022', '00000000022', 'HD000022', 'P022');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 23', '2018-11-22', 0, '000000023', '00000000023', 'HD000023', 'P023');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 24', '2019-09-18', 1, '000000024', '00000000024', 'HD000024', 'P024');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 25', '2020-07-14', 0, '000000025', '00000000025', 'HD000025', 'P025');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 26', '2021-05-10', 1, '000000026', '00000000026', 'HD000026', 'P026');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 27', '2022-03-06', 0, '000000027', '00000000027', 'HD000027', 'P027');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 28', '2022-12-31', 1, '000000028', '00000000028', 'HD000028', 'P028');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 29', '2023-10-27', 0, '000000029', '00000000029', 'HD000029', 'P029');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 30', '2000-01-01', 1, '000000030', '00000000030', 'HD000030', 'P030');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 31', '2000-10-27', 0, '000000031', '00000000031', 'HD000031', 'P031');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 32', '2001-08-23', 1, '000000032', '00000000032', 'HD000032', 'P032');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 33', '2002-06-19', 0, '000000033', '00000000033', 'HD000033', 'P033');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 34', '2003-04-15', 1, '000000034', '00000000034', 'HD000034', 'P034');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 35', '2004-02-09', 0, '000000035', '00000000035', 'HD000035', 'P035');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 36', '2004-12-05', 1, '000000036', '00000000036', 'HD000036', 'P036');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 37', '2005-10-01', 0, '000000037', '00000000037', 'HD000037', 'P037');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 38', '2006-07-28', 1, '000000038', '00000000038', 'HD000038', 'P038');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 39', '2007-05-24', 0, '000000039', '00000000039', 'HD000039', 'P039');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 40', '2008-03-19', 1, '000000040', '00000000040', 'HD000040', 'P040');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 41', '2009-01-13', 0, '000000041', '00000000041', 'HD000041', 'P041');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 42', '2009-11-09', 1, '000000042', '00000000042', 'HD000042', 'P042');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 43', '2010-09-05', 0, '000000043', '00000000043', 'HD000043', 'P043');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 44', '2011-07-02', 1, '000000044', '00000000044', 'HD000044', 'P044');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 45', '2012-04-27', 0, '000000045', '00000000045', 'HD000045', 'P045');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 46', '2013-02-21', 1, '000000046', '00000000046', 'HD000046', 'P046');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 47', '2013-12-18', 0, '000000047', '00000000047', 'HD000047', 'P047');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 48', '2014-10-14', 1, '000000048', '00000000048', 'HD000048', 'P048');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 49', '2015-08-10', 0, '000000049', '00000000049', 'HD000049', 'P049');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 50', '2016-06-05', 1, '000000050', '00000000050', 'HD000050', 'P050');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 51', '2017-04-01', 0, '000000051', '00000000051', 'HD000051', 'P051');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 52', '2018-01-26', 1, '000000052', '00000000052', 'HD000052', 'P052');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 53', '2018-11-22', 0, '000000053', '00000000053', 'HD000053', 'P053');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 54', '2019-09-18', 1, '000000054', '00000000054', 'HD000054', 'P054');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 55', '2020-07-14', 0, '000000055', '00000000055', 'HD000055', 'P055');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 56', '2021-05-10', 1, '000000056', '00000000056', 'HD000056', 'P056');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 57', '2022-03-06', 0, '000000057', '00000000057', 'HD000057', 'P057');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 58', '2022-12-31', 1, '000000058', '00000000058', 'HD000058', 'P058');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 59', '2023-10-27', 0, '000000059', '00000000059', 'HD000059', 'P059');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 60', '2000-01-01', 1, '000000060', '00000000060', 'HD000060', 'P060');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 61', '2000-10-27', 0, '000000061', '00000000061', 'HD000061', 'P061');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 62', '2001-08-23', 1, '000000062', '00000000062', 'HD000062', 'P062');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 63', '2002-06-19', 0, '000000063', '00000000063', 'HD000063', 'P063');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 64', '2003-04-15', 1, '000000064', '00000000064', 'HD000064', 'P064');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 65', '2004-02-09', 0, '000000065', '00000000065', 'HD000065', 'P065');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 66', '2004-12-05', 1, '000000066', '00000000066', 'HD000066', 'P066');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 67', '2005-10-01', 0, '000000067', '00000000067', 'HD000067', 'P067');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 68', '2006-07-28', 1, '000000068', '00000000068', 'HD000068', 'P068');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 69', '2007-05-24', 0, '000000069', '00000000069', 'HD000069', 'P069');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 70', '2008-03-19', 1, '000000070', '00000000070', 'HD000070', 'P070');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 71', '2009-01-13', 0, '000000071', '00000000071', 'HD000071', 'P071');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 72', '2009-11-09', 1, '000000072', '00000000072', 'HD000072', 'P072');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 73', '2010-09-05', 0, '000000073', '00000000073', 'HD000073', 'P073');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 74', '2011-07-02', 1, '000000074', '00000000074', 'HD000074', 'P074');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 75', '2012-04-27', 0, '000000075', '00000000075', 'HD000075', 'P075');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 76', '2013-02-21', 1, '000000076', '00000000076', 'HD000076', 'P076');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 77', '2013-12-18', 0, '000000077', '00000000077', 'HD000077', 'P077');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 78', '2014-10-14', 1, '000000078', '00000000078', 'HD000078', 'P078');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 79', '2015-08-10', 0, '000000079', '00000000079', 'HD000079', 'P079');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 80', '2016-06-05', 1, '000000080', '00000000080', 'HD000080', 'P080');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 81', '2017-04-01', 0, '000000081', '00000000081', 'HD000081', 'P081');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 82', '2018-01-26', 1, '000000082', '00000000082', 'HD000082', 'P082');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 83', '2018-11-22', 0, '000000083', '00000000083', 'HD000083', 'P083');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 84', '2019-09-18', 1, '000000084', '00000000084', 'HD000084', 'P084');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 85', '2020-07-14', 0, '000000085', '00000000085', 'HD000085', 'P085');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 86', '2021-05-10', 1, '000000086', '00000000086', 'HD000086', 'P086');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 87', '2022-03-06', 0, '000000087', '00000000087', 'HD000087', 'P087');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 88', '2022-12-31', 1, '000000088', '00000000088', 'HD000088', 'P088');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 89', '2023-10-27', 0, '000000089', '00000000089', 'HD000089', 'P089');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 90', '2000-01-01', 1, '000000090', '00000000090', 'HD000090', 'P090');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 91', '2000-10-27', 0, '000000091', '00000000091', 'HD000091', 'P091');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 92', '2001-08-23', 1, '000000092', '00000000092', 'HD000092', 'P092');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 93', '2002-06-19', 0, '000000093', '00000000093', 'HD000093', 'P093');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 94', '2003-04-15', 1, '000000094', '00000000094', 'HD000094', 'P094');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 95', '2004-02-09', 0, '000000095', '00000000095', 'HD000095', 'P095');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 96', '2004-12-05', 1, '000000096', '00000000096', 'HD000096', 'P096');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 97', '2005-10-01', 0, '000000097', '00000000097', 'HD000097', 'P097');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 98', '2006-07-28', 1, '000000098', '00000000098', 'HD000098', 'P098');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 99', '2007-05-24', 0, '000000099', '00000000099', 'HD000099', 'P099');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 100', '2008-03-19', 1, '000000100', '00000000100', 'HD000100', 'P100');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 101', '2009-01-13', 0, '000000101', '00000000101', 'HD000101', 'P001');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 102', '2009-11-09', 1, '000000102', '00000000102', 'HD000102', 'P002');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 103', '2010-09-05', 0, '000000103', '00000000103', 'HD000103', 'P003');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 104', '2011-07-02', 1, '000000104', '00000000104', 'HD000104', 'P004');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 105', '2012-04-27', 0, '000000105', '00000000105', 'HD000105', 'P005');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 106', '2013-02-21', 1, '000000106', '00000000106', 'HD000106', 'P006');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 107', '2013-12-18', 0, '000000107', '00000000107', 'HD000107', 'P007');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 108', '2014-10-14', 1, '000000108', '00000000108', 'HD000108', 'P008');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 109', '2015-08-10', 0, '000000109', '00000000109', 'HD000109', 'P009');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 110', '2016-06-05', 1, '000000110', '00000000110', 'HD000110', 'P010');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 111', '2017-04-01', 0, '000000111', '00000000111', 'HD000111', 'P011');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 112', '2018-01-26', 1, '000000112', '00000000112', 'HD000112', 'P012');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 113', '2018-11-22', 0, '000000113', '00000000113', 'HD000113', 'P013');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 114', '2019-09-18', 1, '000000114', '00000000114', 'HD000114', 'P014');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 115', '2020-07-14', 0, '000000115', '00000000115', 'HD000115', 'P015');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 116', '2021-05-10', 1, '000000116', '00000000116', 'HD000116', 'P016');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 117', '2022-03-06', 0, '000000117', '00000000117', 'HD000117', 'P017');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 118', '2022-12-31', 1, '000000118', '00000000118', 'HD000118', 'P018');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 119', '2023-10-27', 0, '000000119', '00000000119', 'HD000119', 'P019');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 120', '2000-01-01', 1, '000000120', '00000000120', 'HD000120', 'P020');

-- Inserts for HoaDonHuyPhong
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00001', '2025-10-30 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH001', 'HD000001');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00002', '2025-10-28 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH002', 'HD000002');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00003', '2025-10-26 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH003', 'HD000003');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00004', '2025-10-24 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH004', 'HD000004');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00005', '2025-10-22 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH005', 'HD000005');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00006', '2025-10-20 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH006', 'HD000006');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00007', '2025-10-18 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH007', 'HD000007');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00008', '2025-10-16 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH008', 'HD000008');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00009', '2025-10-14 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH009', 'HD000009');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00010', '2025-10-12 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH010', 'HD000010');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00011', '2025-10-10 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH011', 'HD000011');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00012', '2025-10-08 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH012', 'HD000012');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00013', '2025-10-06 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH013', 'HD000013');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00014', '2025-10-04 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH014', 'HD000014');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00015', '2025-10-02 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH015', 'HD000015');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00016', '2025-09-30 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH016', 'HD000016');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00017', '2025-09-28 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH017', 'HD000017');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00018', '2025-09-26 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH018', 'HD000018');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00019', '2025-09-24 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH019', 'HD000019');
INSERT INTO HoaDonHuyPhong (maHuy, ngayHuy, lyDoHuy, maKH, maHD) VALUES ('HHP00020', '2025-09-22 15:48:08', N'Khách hủy do thay đổi kế hoạch', 'KH020', 'HD000020');
GO