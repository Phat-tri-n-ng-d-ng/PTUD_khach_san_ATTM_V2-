CREATE DATABASE QuanLyKhachSan_ATTM3;
GO
USE QuanLyKhachSan_ATTM3;
GO
CREATE TABLE TaiKhoan (
    tenDangNhap VARCHAR(50) PRIMARY KEY,
    matKhau NVARCHAR(50) NOT NULL,
    tenVaiTro NVARCHAR(30) NOT NULL,
    tenTrangThaiTK NVARCHAR(30) NOT NULL
);
CREATE TABLE NhanVien (
    maNV CHAR(10) PRIMARY KEY,
    tenNV NVARCHAR(100) NOT NULL,
    ngaySinh DATE NOT NULL,
    gioiTinh BIT NOT NULL,
    sdt VARCHAR(20) NOT NULL UNIQUE,
    email NVARCHAR(100) NOT NULL UNIQUE,
    tenChucVu NVARCHAR(50) NOT NULL,
    tenDangNhap VARCHAR(50) NULL,
    CONSTRAINT FK_NhanVien_TaiKhoan FOREIGN KEY (tenDangNhap) REFERENCES TaiKhoan(tenDangNhap)
);
CREATE TABLE HangKhachHang (
    maHang CHAR(10) PRIMARY KEY,
    tenHang NVARCHAR(20) NOT NULL UNIQUE,
    diemToiThieu INT NOT NULL
);
CREATE TABLE KhachHang (
    maKH CHAR(10) PRIMARY KEY,
    tenKH NVARCHAR(100) NOT NULL,
    gioiTinh BIT NOT NULL,
    ngaySinh DATE NOT NULL,
    email NVARCHAR(100) NOT NULL UNIQUE,
    sdt VARCHAR(20) NOT NULL UNIQUE,
    soCCCD VARCHAR(20) NOT NULL UNIQUE,
    diemTichLuy INT DEFAULT 0,
    maHang CHAR(10) NOT NULL,
    CONSTRAINT FK_KhachHang_Hang FOREIGN KEY (maHang) REFERENCES HangKhachHang(maHang)
);
CREATE TABLE LoaiPhong (
    maLoaiPhong CHAR(10) PRIMARY KEY,
    tenLoaiPhong NVARCHAR(50) NOT NULL,
    giaNiemYet FLOAT NOT NULL,
    tyLeCoc FLOAT NOT NULL,
    sucChuaToiThieu INT NOT NULL
);
CREATE TABLE Phong (
    maPhong CHAR(6) PRIMARY KEY,
    tang INT NOT NULL,
    soPhong INT NOT NULL,
    sucChuaToiDa INT NOT NULL,
    giaPhong FLOAT NOT NULL,
    tienCoc FLOAT NOT NULL,
    maLoaiPhong CHAR(10) NOT NULL,
    tenTrangThaiPhong NVARCHAR(30) NOT NULL,
    CONSTRAINT FK_Phong_LoaiPhong FOREIGN KEY (maLoaiPhong) REFERENCES LoaiPhong(maLoaiPhong)
);
CREATE TABLE KhuyenMai (
    maKM CHAR(10) PRIMARY KEY,
    tenKM NVARCHAR(200) NOT NULL,
    dieuKienApDung NVARCHAR(200) NOT NULL,
    tyLeGiam FLOAT NOT NULL,
    ngayBatDau DATETIME NOT NULL,
    ngayKetThuc DATETIME NOT NULL,
    tenTrangThaiKM NVARCHAR(30) NOT NULL
);
CREATE TABLE HoaDon (
    maHD CHAR(12) PRIMARY KEY,
    ngayLap DATETIME NOT NULL,
    ngayNhanPhong DATETIME NOT NULL,
    ngayTraPhong DATETIME NOT NULL,
    tenPTTT NVARCHAR(30) NOT NULL,
    tenTrangThaiHD NVARCHAR(30) NOT NULL,
    tienNhan FLOAT NULL,
    tongTienThanhToan FLOAT NULL,
    maKM CHAR(10) NULL,
    maKH CHAR(10) NOT NULL,
    maNV CHAR(10) NOT NULL,
    CONSTRAINT FK_HoaDon_KhuyenMai FOREIGN KEY (maKM) REFERENCES KhuyenMai(maKM),
    CONSTRAINT FK_HoaDon_KhachHang FOREIGN KEY (maKH) REFERENCES KhachHang(maKH),
    CONSTRAINT FK_HoaDon_NhanVien FOREIGN KEY (maNV) REFERENCES NhanVien(maNV)
);
CREATE TABLE ChiTietHoaDon (
    maHD CHAR(12) NOT NULL,
    maPhong CHAR(6) NOT NULL,
    thanhTien FLOAT NOT NULL,
    CONSTRAINT PK_ChiTietHoaDon PRIMARY KEY (maHD, maPhong),
    CONSTRAINT FK_CTHD_HoaDon FOREIGN KEY (maHD) REFERENCES HoaDon(maHD),
    CONSTRAINT FK_CTHD_Phong FOREIGN KEY (maPhong) REFERENCES Phong(maPhong)
);
CREATE TABLE HoaDonHuyPhong (
    maHuy CHAR(12) PRIMARY KEY,
    ngayHuy DATETIME NOT NULL,
    lyDoHuy NVARCHAR(200) NOT NULL,
    maKH CHAR(10) NOT NULL,
    maHD CHAR(12) NOT NULL,
    CONSTRAINT FK_Huy_KhachHang FOREIGN KEY (maKH) REFERENCES KhachHang(maKH),
    CONSTRAINT FK_Huy_HoaDon FOREIGN KEY (maHD) REFERENCES HoaDon(maHD)
);
CREATE TABLE NguoiO (
    tenNguoiO NVARCHAR(100) NOT NULL,
    ngaySinh DATE NOT NULL,
    gioiTinh BIT NOT NULL,
    sdt VARCHAR(20) NULL,
    soCCCD VARCHAR(20) NULL,
    maHD CHAR(12) NOT NULL,
    maPhong CHAR(6) NOT NULL,
    CONSTRAINT PK_NguoiO PRIMARY KEY (maHD, maPhong),
    CONSTRAINT FK_NguoiO_HoaDon FOREIGN KEY (maHD) REFERENCES HoaDon(maHD),
    CONSTRAINT FK_NguoiO_Phong FOREIGN KEY (maPhong) REFERENCES Phong(maPhong)
);
GO
-- Inserts for TaiKhoan
INSERT INTO TaiKhoan (tenDangNhap, matKhau, tenVaiTro, tenTrangThaiTK) VALUES ('0000000001', N'1111', N'QuanLy', N'DangHoatDong');
INSERT INTO TaiKhoan (tenDangNhap, matKhau, tenVaiTro, tenTrangThaiTK) VALUES ('0000000002', N'1111', N'QuanLy', N'DangHoatDong');
INSERT INTO TaiKhoan (tenDangNhap, matKhau, tenVaiTro, tenTrangThaiTK) VALUES ('0000000003', N'1111', N'QuanLy', N'DangHoatDong');
INSERT INTO TaiKhoan (tenDangNhap, matKhau, tenVaiTro, tenTrangThaiTK) VALUES ('0000000004', N'1111', N'LeTan', N'DangHoatDong');
INSERT INTO TaiKhoan (tenDangNhap, matKhau, tenVaiTro, tenTrangThaiTK) VALUES ('0000000005', N'1111', N'LeTan', N'DangHoatDong');
INSERT INTO TaiKhoan (tenDangNhap, matKhau, tenVaiTro, tenTrangThaiTK) VALUES ('0000000006', N'1111', N'LeTan', N'DangHoatDong');
INSERT INTO TaiKhoan (tenDangNhap, matKhau, tenVaiTro, tenTrangThaiTK) VALUES ('0000000007', N'1111', N'LeTan', N'DangHoatDong');
INSERT INTO TaiKhoan (tenDangNhap, matKhau, tenVaiTro, tenTrangThaiTK) VALUES ('0000000008', N'1111', N'LeTan', N'DangHoatDong');
INSERT INTO TaiKhoan (tenDangNhap, matKhau, tenVaiTro, tenTrangThaiTK) VALUES ('0000000009', N'1111', N'LeTan', N'DangHoatDong');
INSERT INTO TaiKhoan (tenDangNhap, matKhau, tenVaiTro, tenTrangThaiTK) VALUES ('0000000010', N'1111', N'LeTan', N'DangHoatDong');
-- Inserts for NhanVien
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV001', N'Nguyễn Văn An', '1991-01-01', 0, '000000001', N'nv1@attm.com', N'KeToan', '0000000001');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV002', N'Trần Thị Bình', '1992-01-01', 1, '000000002', N'nv2@attm.com', N'LeTan', '0000000002');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV003', N'Lê Văn Cường', '1992-12-31', 0, '000000003', N'nv3@attm.com', N'BuongPhong', '0000000003');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV004', N'Phạm Thị Dung', '1993-12-31', 1, '000000004', N'nv4@attm.com', N'Bep', '0000000004');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV005', N'Hoàng Văn Đạt', '1994-12-31', 0, '000000005', N'nv5@attm.com', N'BaoVe', '0000000005');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV006', N'Vũ Thị Em', '1995-12-31', 1, '000000006', N'nv6@attm.com', N'QuanLy', '0000000006');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV007', N'Nguyễn Văn Phúc', '1996-12-30', 0, '000000007', N'nv7@attm.com', N'KeToan', '0000000007');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV008', N'Trần Thị Giang', '1997-12-30', 1, '000000008', N'nv8@attm.com', N'LeTan', '0000000008');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV009', N'Lê Văn Hải', '1998-12-30', 0, '000000009', N'nv9@attm.com', N'BuongPhong', '0000000009');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV010', N'Phạm Thị Hương', '1999-12-30', 1, '000000010', N'nv10@attm.com', N'Bep', '0000000010');
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV011', N'Hoàng Văn Khoa', '2000-12-29', 0, '000000011', N'nv11@attm.com', N'BaoVe', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV012', N'Vũ Thị Lan', '2001-12-29', 1, '000000012', N'nv12@attm.com', N'QuanLy', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV013', N'Nguyễn Văn Minh', '2002-12-29', 0, '000000013', N'nv13@attm.com', N'KeToan', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV014', N'Trần Thị Nga', '2003-12-29', 1, '000000014', N'nv14@attm.com', N'LeTan', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV015', N'Lê Văn Oanh', '2004-12-28', 0, '000000015', N'nv15@attm.com', N'BuongPhong', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV016', N'Phạm Thị Phương', '2005-12-28', 1, '000000016', N'nv16@attm.com', N'Bep', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV017', N'Hoàng Văn Quân', '2006-12-28', 0, '000000017', N'nv17@attm.com', N'BaoVe', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV018', N'Vũ Thị Ruby', '2007-12-28', 1, '000000018', N'nv18@attm.com', N'QuanLy', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV019', N'Nguyễn Văn Sơn', '2008-12-27', 0, '000000019', N'nv19@attm.com', N'KeToan', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV020', N'Trần Thị Tuyết', '2009-12-27', 1, '000000020', N'nv20@attm.com', N'LeTan', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV021', N'Lê Văn Uyên', '2010-12-27', 0, '000000021', N'nv21@attm.com', N'BuongPhong', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV022', N'Phạm Thị Vân', '1990-01-31', 1, '000000022', N'nv22@attm.com', N'Bep', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV023', N'Hoàng Văn Xuyên', '1991-01-31', 0, '000000023', N'nv23@attm.com', N'BaoVe', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV024', N'Vũ Thị Yến', '1992-01-31', 1, '000000024', N'nv24@attm.com', N'QuanLy', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV025', N'Nguyễn Văn Zũ', '1993-01-30', 0, '000000025', N'nv25@attm.com', N'KeToan', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV026', N'Trần Thị Ánh', '1994-01-30', 1, '000000026', N'nv26@attm.com', N'LeTan', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV027', N'Lê Văn Bảo', '1995-01-30', 0, '000000027', N'nv27@attm.com', N'BuongPhong', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV028', N'Phạm Thị Châu', '1996-01-30', 1, '000000028', N'nv28@attm.com', N'Bep', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV029', N'Hoàng Văn Dũng', '1997-01-29', 0, '000000029', N'nv29@attm.com', N'BaoVe', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV030', N'Vũ Thị Eo', '1998-01-29', 1, '000000030', N'nv30@attm.com', N'QuanLy', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV031', N'Nguyễn Văn Phong', '1999-01-29', 0, '000000031', N'nv31@attm.com', N'KeToan', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV032', N'Trần Thị Gấm', '2000-01-29', 1, '000000032', N'nv32@attm.com', N'LeTan', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV033', N'Lê Văn Hào', '2001-01-28', 0, '000000033', N'nv33@attm.com', N'BuongPhong', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV034', N'Phạm Thị Iến', '2002-01-28', 1, '000000034', N'nv34@attm.com', N'Bep', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV035', N'Hoàng Văn Kỳ', '2003-01-28', 0, '000000035', N'nv35@attm.com', N'BaoVe', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV036', N'Vũ Thị Lệ', '2004-01-28', 1, '000000036', N'nv36@attm.com', N'QuanLy', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV037', N'Nguyễn Văn Mạnh', '2005-01-27', 0, '000000037', N'nv37@attm.com', N'KeToan', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV038', N'Trần Thị Ngọc', '2006-01-27', 1, '000000038', N'nv38@attm.com', N'LeTan', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV039', N'Lê Văn Oai', '2007-01-27', 0, '000000039', N'nv39@attm.com', N'BuongPhong', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV040', N'Phạm Thị Phấn', '2008-01-27', 1, '000000040', N'nv40@attm.com', N'Bep', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV041', N'Hoàng Văn Quang', '2009-01-26', 0, '000000041', N'nv41@attm.com', N'BaoVe', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV042', N'Vũ Thị Rơ', '2010-01-26', 1, '000000042', N'nv42@attm.com', N'QuanLy', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV043', N'Nguyễn Văn Sỹ', '2011-01-26', 0, '000000043', N'nv43@attm.com', N'KeToan', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV044', N'Trần Thị Tâm', '1990-03-02', 1, '000000044', N'nv44@attm.com', N'LeTan', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV045', N'Lê Văn Uy', '1991-03-02', 0, '000000045', N'nv45@attm.com', N'BuongPhong', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV046', N'Phạm Thị Vui', '1992-03-01', 1, '000000046', N'nv46@attm.com', N'Bep', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV047', N'Hoàng Văn Xứng', '1993-03-01', 0, '000000047', N'nv47@attm.com', N'BaoVe', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV048', N'Vũ Thị Yêu', '1994-03-01', 1, '000000048', N'nv48@attm.com', N'QuanLy', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV049', N'Nguyễn Văn Zậy', '1995-03-01', 0, '000000049', N'nv49@attm.com', N'KeToan', NULL);
INSERT INTO NhanVien (maNV, tenNV, ngaySinh, gioiTinh, sdt, email, tenChucVu, tenDangNhap) VALUES ('NV050', N'Trần Thị An', '1996-02-29', 1, '000000050', N'nv50@attm.com', N'LeTan', NULL);
-- Inserts for HangKhachHang
INSERT INTO HangKhachHang (maHang, tenHang, diemToiThieu) VALUES ('HG001', N'Dong', 0);
INSERT INTO HangKhachHang (maHang, tenHang, diemToiThieu) VALUES ('HG002', N'Bac', 10);
INSERT INTO HangKhachHang (maHang, tenHang, diemToiThieu) VALUES ('HG003', N'Vang', 20);
INSERT INTO HangKhachHang (maHang, tenHang, diemToiThieu) VALUES ('HG004', N'KimCuong', 40);
-- Inserts for LoaiPhong
INSERT INTO LoaiPhong (maLoaiPhong, tenLoaiPhong, giaNiemYet, tyLeCoc, sucChuaToiThieu) VALUES ('LP001', N'Standard', 1000000, 0.3, 2);
INSERT INTO LoaiPhong (maLoaiPhong, tenLoaiPhong, giaNiemYet, tyLeCoc, sucChuaToiThieu) VALUES ('LP002', N'Superior', 1500000, 0.3, 2);
INSERT INTO LoaiPhong (maLoaiPhong, tenLoaiPhong, giaNiemYet, tyLeCoc, sucChuaToiThieu) VALUES ('LP003', N'Deluxe', 2000000, 0.3, 2);
INSERT INTO LoaiPhong (maLoaiPhong, tenLoaiPhong, giaNiemYet, tyLeCoc, sucChuaToiThieu) VALUES ('LP004', N'Suite', 3000000, 0.3, 2);
INSERT INTO LoaiPhong (maLoaiPhong, tenLoaiPhong, giaNiemYet, tyLeCoc, sucChuaToiThieu) VALUES ('LP005', N'Family Room', 4000000, 0.3, 8);
-- Inserts for Phong
DECLARE @floor INT = 1;
DECLARE @room INT = 1;
DECLARE @types TABLE (id INT, ma CHAR(10));
INSERT INTO @types VALUES (1, 'LP001'), (2, 'LP002'), (3, 'LP003'), (4, 'LP004'), (5, 'LP005');
DECLARE @status TABLE (id INT, ten NVARCHAR(30));
INSERT INTO @status VALUES (1, N'DaDat'), (2, N'DangSuDung'), (3, N'Trong'), (4, N'NgungHoatDong');
WHILE @floor <= 10
BEGIN
    SET @room = 1;
    WHILE @room <= 10
    BEGIN
        DECLARE @maPhong CHAR(6) = 'P' + RIGHT('0' + CAST(@floor AS VARCHAR(2)), 2) + RIGHT('00' + CAST(@room AS VARCHAR(3)), 3);
        DECLARE @maLoai CHAR(10) = (SELECT ma FROM @types WHERE id = ((@floor - 1) % 5 + 1));
        DECLARE @giaNiemYet FLOAT = (SELECT giaNiemYet FROM LoaiPhong WHERE maLoaiPhong = @maLoai);
        DECLARE @minCap INT = (SELECT sucChuaToiThieu FROM LoaiPhong WHERE maLoaiPhong = @maLoai);
        DECLARE @maxCap INT;
        DECLARE @giaPhong FLOAT;
        IF @room <= 5
        BEGIN
            SET @maxCap = @minCap;
            SET @giaPhong = @giaNiemYet;
        END
        ELSE
        BEGIN
            SET @maxCap = @minCap + 2;
            SET @giaPhong = @giaNiemYet + 500000;
        END
        DECLARE @tienCoc FLOAT = @giaPhong * 0.3;
        DECLARE @tenStatus NVARCHAR(30) = (SELECT ten FROM @status WHERE id = ((@floor + @room - 2) % 4 + 1));
        INSERT INTO Phong (maPhong, tang, soPhong, sucChuaToiDa, giaPhong, tienCoc, maLoaiPhong, tenTrangThaiPhong) VALUES (@maPhong, @floor, @room, @maxCap, @giaPhong, @tienCoc, @maLoai, @tenStatus);
        SET @room = @room + 1;
    END
    SET @floor = @floor + 1;
END
-- Inserts for KhuyenMai
-- 3. Insert lại dữ liệu với tyLeGiam dạng thập phân
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, tenTrangThaiKM) VALUES ('KM001', N'KM 1 giảm 6%', N'Áp dụng cho booking từ 2 đêm trở lên', 0.06, '2025-10-27 15:48:08', '2025-11-26 15:48:08', N'SapDienRa');
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, tenTrangThaiKM) VALUES ('KM002', N'KM 2 giảm 7%', N'Áp dụng cho booking từ 2 đêm trở lên', 0.07, '2025-10-28 15:48:08', '2025-11-27 15:48:08', N'DangHoatDong');
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, tenTrangThaiKM) VALUES ('KM003', N'KM 3 giảm 8%', N'Áp dụng cho booking từ 2 đêm trở lên', 0.08, '2025-10-29 15:48:08', '2025-11-28 15:48:08', N'HetHan');
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, tenTrangThaiKM) VALUES ('KM004', N'KM 4 giảm 9%', N'Áp dụng cho booking từ 2 đêm trở lên', 0.09, '2025-10-30 15:48:08', '2025-11-29 15:48:08', N'TamNgung');
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, tenTrangThaiKM) VALUES ('KM005', N'KM 5 giảm 10%', N'Áp dụng cho booking từ 2 đêm trở lên', 0.10, '2025-10-31 15:48:08', '2025-11-30 15:48:08', N'SapDienRa');
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, tenTrangThaiKM) VALUES ('KM006', N'KM 6 giảm 11%', N'Áp dụng cho booking từ 2 đêm trở lên', 0.11, '2025-11-01 15:48:08', '2025-12-01 15:48:08', N'DangHoatDong');
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, tenTrangThaiKM) VALUES ('KM007', N'KM 7 giảm 12%', N'Áp dụng cho booking từ 2 đêm trở lên', 0.12, '2025-11-02 15:48:08', '2025-12-02 15:48:08', N'HetHan');
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, tenTrangThaiKM) VALUES ('KM008', N'KM 8 giảm 13%', N'Áp dụng cho booking từ 2 đêm trở lên', 0.13, '2025-11-03 15:48:08', '2025-12-03 15:48:08', N'TamNgung');
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, tenTrangThaiKM) VALUES ('KM009', N'KM 9 giảm 14%', N'Áp dụng cho booking từ 2 đêm trở lên', 0.14, '2025-11-04 15:48:08', '2025-12-04 15:48:08', N'SapDienRa');
INSERT INTO KhuyenMai (maKM, tenKM, dieuKienApDung, tyLeGiam, ngayBatDau, ngayKetThuc, tenTrangThaiKM) VALUES ('KM010', N'KM 10 giảm 15%', N'Áp dụng cho booking từ 2 đêm trở lên', 0.15, '2025-11-05 15:48:08', '2025-12-05 15:48:08', N'DangHoatDong');
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
-- Inserts for HoaDon
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000001', '2025-06-05 15:48:08', '2025-06-05 15:48:08', '2025-06-10 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH001', 'NV001');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000002', '2025-06-06 15:48:08', '2025-06-06 15:48:08', '2025-06-09 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH002', 'NV002');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000003', '2025-06-07 15:48:08', '2025-06-07 15:48:08', '2025-06-11 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH003', 'NV003');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000004', '2025-06-08 15:48:08', '2025-06-09 15:48:08', '2025-06-13 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH004', 'NV004');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000005', '2025-06-09 15:48:08', '2025-06-11 15:48:08', '2025-06-15 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, 'KM006', 'KH005', 'NV005');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000006', '2025-06-10 15:48:08', '2025-06-10 15:48:08', '2025-06-11 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH006', 'NV006');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000007', '2025-06-11 15:48:08', '2025-06-12 15:48:08', '2025-06-13 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH007', 'NV007');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000008', '2025-06-12 15:48:08', '2025-06-13 15:48:08', '2025-06-17 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH008', 'NV008');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000009', '2025-06-13 15:48:08', '2025-06-15 15:48:08', '2025-06-16 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH009', 'NV009');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000010', '2025-06-14 15:48:08', '2025-06-16 15:48:08', '2025-06-20 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, 'KM001', 'KH010', 'NV010');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000011', '2025-06-15 15:48:08', '2025-06-16 15:48:08', '2025-06-18 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH011', 'NV011');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000012', '2025-06-16 15:48:08', '2025-06-18 15:48:08', '2025-06-19 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH012', 'NV012');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000013', '2025-06-17 15:48:08', '2025-06-18 15:48:08', '2025-06-19 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH013', 'NV013');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000014', '2025-06-18 15:48:08', '2025-06-18 15:48:08', '2025-06-19 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH014', 'NV014');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000015', '2025-06-19 15:48:08', '2025-06-21 15:48:08', '2025-06-26 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, 'KM006', 'KH015', 'NV015');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000016', '2025-06-20 15:48:08', '2025-06-20 15:48:08', '2025-06-24 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH016', 'NV016');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000017', '2025-06-21 15:48:08', '2025-06-23 15:48:08', '2025-06-25 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH017', 'NV017');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000018', '2025-06-22 15:48:08', '2025-06-23 15:48:08', '2025-06-24 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH018', 'NV018');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000019', '2025-06-23 15:48:08', '2025-06-25 15:48:08', '2025-06-27 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH019', 'NV019');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000020', '2025-06-24 15:48:08', '2025-06-25 15:48:08', '2025-06-29 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, 'KM001', 'KH020', 'NV020');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000021', '2025-06-25 15:48:08', '2025-06-27 15:48:08', '2025-06-29 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH021', 'NV021');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000022', '2025-06-26 15:48:08', '2025-06-27 15:48:08', '2025-06-29 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH022', 'NV022');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000023', '2025-06-27 15:48:08', '2025-06-29 15:48:08', '2025-07-01 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH023', 'NV023');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000024', '2025-06-28 15:48:08', '2025-06-29 15:48:08', '2025-07-02 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH024', 'NV024');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000025', '2025-06-29 15:48:08', '2025-06-29 15:48:08', '2025-07-03 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, 'KM006', 'KH025', 'NV025');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000026', '2025-06-30 15:48:08', '2025-07-02 15:48:08', '2025-07-03 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH026', 'NV026');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000027', '2025-07-01 15:48:08', '2025-07-01 15:48:08', '2025-07-04 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH027', 'NV027');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000028', '2025-07-02 15:48:08', '2025-07-02 15:48:08', '2025-07-05 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH028', 'NV028');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000029', '2025-07-03 15:48:08', '2025-07-05 15:48:08', '2025-07-10 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH029', 'NV029');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000030', '2025-07-04 15:48:08', '2025-07-05 15:48:08', '2025-07-10 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, 'KM001', 'KH030', 'NV030');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000031', '2025-07-05 15:48:08', '2025-07-07 15:48:08', '2025-07-09 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH031', 'NV031');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000032', '2025-07-06 15:48:08', '2025-07-07 15:48:08', '2025-07-10 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH032', 'NV032');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000033', '2025-07-07 15:48:08', '2025-07-09 15:48:08', '2025-07-13 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH033', 'NV033');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000034', '2025-07-08 15:48:08', '2025-07-10 15:48:08', '2025-07-14 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH034', 'NV034');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000035', '2025-07-09 15:48:08', '2025-07-11 15:48:08', '2025-07-12 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, 'KM006', 'KH035', 'NV035');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000036', '2025-07-10 15:48:08', '2025-07-11 15:48:08', '2025-07-13 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH036', 'NV036');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000037', '2025-07-11 15:48:08', '2025-07-13 15:48:08', '2025-07-17 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH037', 'NV037');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000038', '2025-07-12 15:48:08', '2025-07-13 15:48:08', '2025-07-15 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH038', 'NV038');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000039', '2025-07-13 15:48:08', '2025-07-14 15:48:08', '2025-07-19 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH039', 'NV039');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000040', '2025-07-14 15:48:08', '2025-07-16 15:48:08', '2025-07-19 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, 'KM001', 'KH040', 'NV040');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000041', '2025-07-15 15:48:08', '2025-07-15 15:48:08', '2025-07-19 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH041', 'NV041');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000042', '2025-07-16 15:48:08', '2025-07-18 15:48:08', '2025-07-23 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH042', 'NV042');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000043', '2025-07-17 15:48:08', '2025-07-17 15:48:08', '2025-07-19 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH043', 'NV043');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000044', '2025-07-18 15:48:08', '2025-07-20 15:48:08', '2025-07-24 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH044', 'NV044');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000045', '2025-07-19 15:48:08', '2025-07-20 15:48:08', '2025-07-24 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, 'KM006', 'KH045', 'NV045');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000046', '2025-07-20 15:48:08', '2025-07-22 15:48:08', '2025-07-23 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH046', 'NV046');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000047', '2025-07-21 15:48:08', '2025-07-22 15:48:08', '2025-07-23 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH047', 'NV047');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000048', '2025-07-22 15:48:08', '2025-07-23 15:48:08', '2025-07-28 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH048', 'NV048');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000049', '2025-07-23 15:48:08', '2025-07-25 15:48:08', '2025-07-30 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH049', 'NV049');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000050', '2025-07-24 15:48:08', '2025-07-25 15:48:08', '2025-07-27 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, 'KM001', 'KH050', 'NV050');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000051', '2025-07-25 15:48:08', '2025-07-25 15:48:08', '2025-07-30 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH001', 'NV001');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000052', '2025-07-26 15:48:08', '2025-07-26 15:48:08', '2025-07-27 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH002', 'NV002');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000053', '2025-07-27 15:48:08', '2025-07-27 15:48:08', '2025-08-01 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH003', 'NV003');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000054', '2025-07-28 15:48:08', '2025-07-30 15:48:08', '2025-08-01 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH004', 'NV004');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000055', '2025-07-29 15:48:08', '2025-07-30 15:48:08', '2025-08-04 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, 'KM006', 'KH005', 'NV005');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000056', '2025-07-30 15:48:08', '2025-07-31 15:48:08', '2025-08-05 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH006', 'NV006');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000057', '2025-07-31 15:48:08', '2025-08-01 15:48:08', '2025-08-05 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH007', 'NV007');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000058', '2025-08-01 15:48:08', '2025-08-02 15:48:08', '2025-08-07 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH008', 'NV008');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000059', '2025-08-02 15:48:08', '2025-08-04 15:48:08', '2025-08-05 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH009', 'NV009');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000060', '2025-08-03 15:48:08', '2025-08-04 15:48:08', '2025-08-09 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, 'KM001', 'KH010', 'NV010');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000061', '2025-08-04 15:48:08', '2025-08-04 15:48:08', '2025-08-09 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH011', 'NV011');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000062', '2025-08-05 15:48:08', '2025-08-07 15:48:08', '2025-08-09 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH012', 'NV012');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000063', '2025-08-06 15:48:08', '2025-08-07 15:48:08', '2025-08-08 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH013', 'NV013');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000064', '2025-08-07 15:48:08', '2025-08-08 15:48:08', '2025-08-11 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH014', 'NV014');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000065', '2025-08-08 15:48:08', '2025-08-10 15:48:08', '2025-08-15 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, 'KM006', 'KH015', 'NV015');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000066', '2025-08-09 15:48:08', '2025-08-09 15:48:08', '2025-08-14 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH016', 'NV016');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000067', '2025-08-10 15:48:08', '2025-08-11 15:48:08', '2025-08-15 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH017', 'NV017');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000068', '2025-08-11 15:48:08', '2025-08-12 15:48:08', '2025-08-16 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH018', 'NV018');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000069', '2025-08-12 15:48:08', '2025-08-13 15:48:08', '2025-08-14 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH019', 'NV019');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000070', '2025-08-13 15:48:08', '2025-08-15 15:48:08', '2025-08-20 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, 'KM001', 'KH020', 'NV020');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000071', '2025-08-14 15:48:08', '2025-08-16 15:48:08', '2025-08-21 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH021', 'NV021');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000072', '2025-08-15 15:48:08', '2025-08-16 15:48:08', '2025-08-20 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH022', 'NV022');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000073', '2025-08-16 15:48:08', '2025-08-18 15:48:08', '2025-08-19 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH023', 'NV023');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000074', '2025-08-17 15:48:08', '2025-08-17 15:48:08', '2025-08-19 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH024', 'NV024');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000075', '2025-08-18 15:48:08', '2025-08-20 15:48:08', '2025-08-25 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, 'KM006', 'KH025', 'NV025');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000076', '2025-08-19 15:48:08', '2025-08-19 15:48:08', '2025-08-20 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH026', 'NV026');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000077', '2025-08-20 15:48:08', '2025-08-22 15:48:08', '2025-08-25 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH027', 'NV027');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000078', '2025-08-21 15:48:08', '2025-08-21 15:48:08', '2025-08-22 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH028', 'NV028');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000079', '2025-08-22 15:48:08', '2025-08-22 15:48:08', '2025-08-23 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH029', 'NV029');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000080', '2025-08-23 15:48:08', '2025-08-24 15:48:08', '2025-08-25 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, 'KM001', 'KH030', 'NV030');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000081', '2025-08-24 15:48:08', '2025-08-25 15:48:08', '2025-08-27 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH031', 'NV031');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000082', '2025-08-25 15:48:08', '2025-08-26 15:48:08', '2025-08-27 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH032', 'NV032');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000083', '2025-08-26 15:48:08', '2025-08-28 15:48:08', '2025-08-30 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH033', 'NV033');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000084', '2025-08-27 15:48:08', '2025-08-28 15:48:08', '2025-08-31 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH034', 'NV034');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000085', '2025-08-28 15:48:08', '2025-08-28 15:48:08', '2025-08-30 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, 'KM006', 'KH035', 'NV035');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000086', '2025-08-29 15:48:08', '2025-08-29 15:48:08', '2025-09-01 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH036', 'NV036');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000087', '2025-08-30 15:48:08', '2025-09-01 15:48:08', '2025-09-03 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH037', 'NV037');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000088', '2025-08-31 15:48:08', '2025-09-02 15:48:08', '2025-09-05 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH038', 'NV038');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000089', '2025-09-01 15:48:08', '2025-09-03 15:48:08', '2025-09-06 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH039', 'NV039');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000090', '2025-09-02 15:48:08', '2025-09-03 15:48:08', '2025-09-06 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, 'KM001', 'KH040', 'NV040');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000091', '2025-09-03 15:48:08', '2025-09-04 15:48:08', '2025-09-08 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH041', 'NV041');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000092', '2025-09-04 15:48:08', '2025-09-04 15:48:08', '2025-09-05 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH042', 'NV042');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000093', '2025-09-05 15:48:08', '2025-09-06 15:48:08', '2025-09-10 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH043', 'NV043');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000094', '2025-09-06 15:48:08', '2025-09-07 15:48:08', '2025-09-11 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH044', 'NV044');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000095', '2025-09-07 15:48:08', '2025-09-07 15:48:08', '2025-09-10 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, 'KM006', 'KH045', 'NV045');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000096', '2025-09-08 15:48:08', '2025-09-08 15:48:08', '2025-09-11 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH046', 'NV046');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000097', '2025-09-09 15:48:08', '2025-09-11 15:48:08', '2025-09-16 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH047', 'NV047');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000098', '2025-09-10 15:48:08', '2025-09-10 15:48:08', '2025-09-15 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH048', 'NV048');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000099', '2025-09-11 15:48:08', '2025-09-12 15:48:08', '2025-09-13 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH049', 'NV049');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000100', '2025-09-12 15:48:08', '2025-09-12 15:48:08', '2025-09-13 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, 'KM001', 'KH050', 'NV050');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000101', '2025-09-13 15:48:08', '2025-09-14 15:48:08', '2025-09-16 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH001', 'NV001');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000102', '2025-09-14 15:48:08', '2025-09-14 15:48:08', '2025-09-16 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH002', 'NV002');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000103', '2025-09-15 15:48:08', '2025-09-16 15:48:08', '2025-09-21 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH003', 'NV003');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000104', '2025-09-16 15:48:08', '2025-09-18 15:48:08', '2025-09-22 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH004', 'NV004');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000105', '2025-09-17 15:48:08', '2025-09-19 15:48:08', '2025-09-21 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, 'KM006', 'KH005', 'NV005');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000106', '2025-09-18 15:48:08', '2025-09-20 15:48:08', '2025-09-25 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH006', 'NV006');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000107', '2025-09-19 15:48:08', '2025-09-20 15:48:08', '2025-09-22 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH007', 'NV007');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000108', '2025-09-20 15:48:08', '2025-09-22 15:48:08', '2025-09-23 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH008', 'NV008');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000109', '2025-09-21 15:48:08', '2025-09-22 15:48:08', '2025-09-27 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH009', 'NV009');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000110', '2025-09-22 15:48:08', '2025-09-23 15:48:08', '2025-09-27 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, 'KM001', 'KH010', 'NV010');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000111', '2025-09-23 15:48:08', '2025-09-23 15:48:08', '2025-09-26 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH011', 'NV011');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000112', '2025-09-24 15:48:08', '2025-09-24 15:48:08', '2025-09-26 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH012', 'NV012');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000113', '2025-09-25 15:48:08', '2025-09-25 15:48:08', '2025-09-28 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH013', 'NV013');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000114', '2025-09-26 15:48:08', '2025-09-26 15:48:08', '2025-09-27 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH014', 'NV014');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000115', '2025-09-27 15:48:08', '2025-09-28 15:48:08', '2025-10-01 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, 'KM006', 'KH015', 'NV015');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000116', '2025-09-28 15:48:08', '2025-09-30 15:48:08', '2025-10-02 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH016', 'NV016');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000117', '2025-09-29 15:48:08', '2025-09-30 15:48:08', '2025-10-05 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH017', 'NV017');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000118', '2025-09-30 15:48:08', '2025-10-01 15:48:08', '2025-10-03 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH018', 'NV018');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000119', '2025-10-01 15:48:08', '2025-10-01 15:48:08', '2025-10-06 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH019', 'NV019');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000120', '2025-10-02 15:48:08', '2025-10-02 15:48:08', '2025-10-07 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, 'KM001', 'KH020', 'NV020');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000121', '2025-10-03 15:48:08', '2025-10-03 15:48:08', '2025-10-08 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH021', 'NV021');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000122', '2025-10-04 15:48:08', '2025-10-05 15:48:08', '2025-10-07 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH022', 'NV022');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000123', '2025-10-05 15:48:08', '2025-10-07 15:48:08', '2025-10-12 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH023', 'NV023');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000124', '2025-10-06 15:48:08', '2025-10-08 15:48:08', '2025-10-09 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH024', 'NV024');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000125', '2025-10-07 15:48:08', '2025-10-08 15:48:08', '2025-10-10 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, 'KM006', 'KH025', 'NV025');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000126', '2025-10-08 15:48:08', '2025-10-09 15:48:08', '2025-10-10 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH026', 'NV026');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000127', '2025-10-09 15:48:08', '2025-10-09 15:48:08', '2025-10-14 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH027', 'NV027');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000128', '2025-10-10 15:48:08', '2025-10-12 15:48:08', '2025-10-16 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH028', 'NV028');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000129', '2025-10-11 15:48:08', '2025-10-13 15:48:08', '2025-10-15 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH029', 'NV029');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000130', '2025-10-12 15:48:08', '2025-10-13 15:48:08', '2025-10-14 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, 'KM001', 'KH030', 'NV030');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000131', '2025-10-13 15:48:08', '2025-10-15 15:48:08', '2025-10-19 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH031', 'NV031');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000132', '2025-10-14 15:48:08', '2025-10-15 15:48:08', '2025-10-20 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH032', 'NV032');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000133', '2025-10-15 15:48:08', '2025-10-16 15:48:08', '2025-10-17 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH033', 'NV033');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000134', '2025-10-16 15:48:08', '2025-10-17 15:48:08', '2025-10-22 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH034', 'NV034');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000135', '2025-10-17 15:48:08', '2025-10-18 15:48:08', '2025-10-21 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, 'KM006', 'KH035', 'NV035');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000136', '2025-10-18 15:48:08', '2025-10-18 15:48:08', '2025-10-20 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH036', 'NV036');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000137', '2025-10-19 15:48:08', '2025-10-19 15:48:08', '2025-10-22 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH037', 'NV037');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000138', '2025-10-20 15:48:08', '2025-10-22 15:48:08', '2025-10-24 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH038', 'NV038');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000139', '2025-10-21 15:48:08', '2025-10-22 15:48:08', '2025-10-26 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, NULL, 'KH039', 'NV039');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000140', '2025-10-22 15:48:08', '2025-10-22 15:48:08', '2025-10-25 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, 'KM001', 'KH040', 'NV040');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000141', '2025-10-23 15:48:08', '2025-10-25 15:48:08', '2025-10-26 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH041', 'NV041');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000142', '2025-10-24 15:48:08', '2025-10-25 15:48:08', '2025-10-30 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH042', 'NV042');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000143', '2025-10-25 15:48:08', '2025-10-26 15:48:08', '2025-11-01 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH043', 'NV043');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000144', '2025-10-26 15:48:08', '2025-10-27 15:48:08', '2025-11-01 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH044', 'NV044');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000145', '2025-10-27 15:48:08', '2025-10-27 15:48:08', '2025-10-28 15:48:08', N'ChuyenKhoan', N'PhieuDatPhong', NULL, NULL, 'KM006', 'KH045', 'NV045');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000146', '2025-10-28 15:48:08', '2025-10-30 15:48:08', '2025-10-31 15:48:08', N'TienMat', N'PhieuThuePhong', NULL, NULL, NULL, 'KH046', 'NV046');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000147', '2025-10-29 15:48:08', '2025-10-29 15:48:08', '2025-10-31 15:48:08', N'ChuyenKhoan', N'HoaDonHoangThanh', NULL, NULL, NULL, 'KH047', 'NV047');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000148', '2025-10-30 15:48:08', '2025-10-30 15:48:08', '2025-11-01 15:48:08', N'TienMat', N'PhieuDatPhong', NULL, NULL, NULL, 'KH048', 'NV048');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000149', '2025-10-31 15:48:08', '2025-11-02 15:48:08', '2025-11-04 15:48:08', N'ChuyenKhoan', N'PhieuThuePhong', NULL, NULL, NULL, 'KH049', 'NV049');
INSERT INTO HoaDon (maHD, ngayLap, ngayNhanPhong, ngayTraPhong, tenPTTT, tenTrangThaiHD, tienNhan, tongTienThanhToan, maKM, maKH, maNV) VALUES ('HD000150', '2025-11-01 15:48:08', '2025-11-02 15:48:08', '2025-11-05 15:48:08', N'TienMat', N'HoaDonHoangThanh', NULL, NULL, 'KM001', 'KH050', 'NV050');
-- Inserts for ChiTietHoaDon (with updated maPhong and adjusted thanhTien based on new prices, but for simplicity, keeping original thanhTien as placeholder; in real, recalculate based on days * giaPhong * (1 - discount if any))
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000001', 'P04007', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000001', 'P04005', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000002', 'P03003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000002', 'P03008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000003', 'P03004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000004', 'P03004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000004', 'P01005', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000005', 'P03008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000005', 'P03001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000006', 'P04008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000007', 'P05010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000007', 'P05002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000008', 'P04003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000008', 'P01008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000009', 'P04005', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000010', 'P04001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000010', 'P05009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000011', 'P01004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000011', 'P03002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000012', 'P01006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000013', 'P03003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000013', 'P01010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000014', 'P03009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000014', 'P01009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000015', 'P01007', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000016', 'P03004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000016', 'P01005', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000017', 'P04009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000017', 'P04006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000018', 'P03009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000019', 'P01010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000019', 'P04004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000020', 'P04001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000020', 'P02009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000021', 'P04003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000022', 'P01001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000022', 'P02005', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000023', 'P03007', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000023', 'P03008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000024', 'P04003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000025', 'P03009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000025', 'P01005', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000026', 'P03009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000026', 'P02006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000027', 'P01004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000028', 'P01006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000028', 'P03008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000029', 'P01002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000029', 'P04009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000030', 'P05006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000031', 'P01002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000031', 'P01002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000032', 'P03003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000032', 'P01005', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000033', 'P01006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000034', 'P02005', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000034', 'P03001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000035', 'P04006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000035', 'P03004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000036', 'P02001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000037', 'P01005', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000037', 'P03008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000038', 'P02002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000038', 'P05008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000039', 'P03001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000040', 'P02001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000040', 'P05006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000041', 'P01004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000041', 'P03006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000042', 'P03009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000043', 'P04010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000043', 'P03008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000044', 'P04001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000044', 'P03003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000045', 'P05002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000046', 'P04002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000046', 'P03001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000047', 'P01003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000047', 'P02007', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000048', 'P05004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000049', 'P03001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000049', 'P01006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000050', 'P01004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000050', 'P01002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000051', 'P03008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000052', 'P05003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000052', 'P04007', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000053', 'P03001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000053', 'P03008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000054', 'P03001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000055', 'P03001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000055', 'P03002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000056', 'P01009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000056', 'P03001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000057', 'P04007', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000058', 'P03009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000058', 'P01005', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000059', 'P03003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000059', 'P02008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000060', 'P05010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000061', 'P05010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000061', 'P04010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000062', 'P05009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000062', 'P04001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000063', 'P05005', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000064', 'P03006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000064', 'P02004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000065', 'P02004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000065', 'P04010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000066', 'P02007', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000067', 'P03010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000067', 'P02006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000068', 'P02002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000068', 'P03007', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000069', 'P01001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000070', 'P02006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000070', 'P01002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000071', 'P05007', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000071', 'P03008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000072', 'P01002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000073', 'P05004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000073', 'P04004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000074', 'P05003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000074', 'P03004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000075', 'P02010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000076', 'P03010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000077', 'P01006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000077', 'P03002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000078', 'P02004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000079', 'P03001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000079', 'P04005', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000080', 'P03009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000080', 'P02002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000081', 'P03003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000082', 'P01003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000082', 'P04010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000083', 'P04009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000083', 'P04005', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000084', 'P04007', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000085', 'P01002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000085', 'P02002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000086', 'P02009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000086', 'P01003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000087', 'P02002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000088', 'P03002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000088', 'P01010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000089', 'P02005', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000089', 'P04001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000090', 'P01010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000091', 'P05004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000091', 'P01010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000092', 'P01003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000092', 'P05002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000093', 'P01002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000094', 'P03008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000094', 'P05007', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000095', 'P03006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000095', 'P04004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000096', 'P04001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000097', 'P01010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000097', 'P01003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000098', 'P04005', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000098', 'P05010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000099', 'P03002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000100', 'P01010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000100', 'P04006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000101', 'P05006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000101', 'P02003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000102', 'P02003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000103', 'P05010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000103', 'P01010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000104', 'P01009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000104', 'P03001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000105', 'P03010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000106', 'P01004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000106', 'P05001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000107', 'P04006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000107', 'P04008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000108', 'P03008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000109', 'P01007', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000109', 'P02007', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000110', 'P01009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000110', 'P04010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000111', 'P05003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000112', 'P01005', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000112', 'P05010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000113', 'P03001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000113', 'P05010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000114', 'P05007', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000115', 'P04001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000115', 'P05006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000116', 'P05009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000116', 'P02007', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000117', 'P02003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000118', 'P03009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000118', 'P03006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000119', 'P03009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000119', 'P02001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000120', 'P01007', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000121', 'P05002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000121', 'P05006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000122', 'P02002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000122', 'P03003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000123', 'P05010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000124', 'P01009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000124', 'P05008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000125', 'P03008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000125', 'P03006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000126', 'P04001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000127', 'P03003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000127', 'P04010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000128', 'P05007', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000128', 'P03009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000129', 'P03009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000130', 'P01002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000130', 'P03001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000131', 'P03004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000131', 'P02002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000132', 'P02004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000133', 'P04003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000133', 'P01004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000134', 'P05003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000134', 'P03004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000135', 'P04004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000136', 'P01003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000136', 'P01008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000137', 'P05009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000137', 'P03006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000138', 'P04005', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000139', 'P01008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000139', 'P04006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000140', 'P01007', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000140', 'P01008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000141', 'P02004', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000142', 'P02006', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000142', 'P03001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000143', 'P04003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000143', 'P03002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000144', 'P02003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000145', 'P04009', 2020000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000145', 'P01002', 1035000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000146', 'P02010', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000146', 'P04003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000147', 'P01001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000148', 'P02003', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000148', 'P04008', 1430000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000149', 'P03001', 800000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000149', 'P04005', 2015000.0);
INSERT INTO ChiTietHoaDon (maHD, maPhong, thanhTien) VALUES ('HD000150', 'P05004', 2020000.0);
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
-- Inserts for NguoiO (with updated maPhong)
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 1', '2000-10-27', 0, '000000001', '00000000001', 'HD000001', 'P01001');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 2', '2001-08-23', 1, '000000002', '00000000002', 'HD000002', 'P01002');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 3', '2002-06-19', 0, '000000003', '00000000003', 'HD000003', 'P01003');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 4', '2003-04-15', 1, '000000004', '00000000004', 'HD000004', 'P01004');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 5', '2004-02-09', 0, '000000005', '00000000005', 'HD000005', 'P01005');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 6', '2004-12-05', 1, '000000006', '00000000006', 'HD000006', 'P01006');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 7', '2005-10-01', 0, '000000007', '00000000007', 'HD000007', 'P01007');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 8', '2006-07-28', 1, '000000008', '00000000008', 'HD000008', 'P01008');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 9', '2007-05-24', 0, '000000009', '00000000009', 'HD000009', 'P01009');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 10', '2008-03-19', 1, '000000010', '00000000010', 'HD000010', 'P01010');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 11', '2009-01-13', 0, '000000011', '00000000011', 'HD000011', 'P02001');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 12', '2009-11-09', 1, '000000012', '00000000012', 'HD000012', 'P02002');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 13', '2010-09-05', 0, '000000013', '00000000013', 'HD000013', 'P02003');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 14', '2011-07-02', 1, '000000014', '00000000014', 'HD000014', 'P02004');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 15', '2012-04-27', 0, '000000015', '00000000015', 'HD000015', 'P02005');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 16', '2013-02-21', 1, '000000016', '00000000016', 'HD000016', 'P02006');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 17', '2013-12-18', 0, '000000017', '00000000017', 'HD000017', 'P02007');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 18', '2014-10-14', 1, '000000018', '00000000018', 'HD000018', 'P02008');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 19', '2015-08-10', 0, '000000019', '00000000019', 'HD000019', 'P02009');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 20', '2016-06-05', 1, '000000020', '00000000020', 'HD000020', 'P02010');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 21', '2017-04-01', 0, '000000021', '00000000021', 'HD000021', 'P03001');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 22', '2018-01-26', 1, '000000022', '00000000022', 'HD000022', 'P03002');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 23', '2018-11-22', 0, '000000023', '00000000023', 'HD000023', 'P03003');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 24', '2019-09-18', 1, '000000024', '00000000024', 'HD000024', 'P03004');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 25', '2020-07-14', 0, '000000025', '00000000025', 'HD000025', 'P03005');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 26', '2021-05-10', 1, '000000026', '00000000026', 'HD000026', 'P03006');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 27', '2022-03-06', 0, '000000027', '00000000027', 'HD000027', 'P03007');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 28', '2022-12-31', 1, '000000028', '00000000028', 'HD000028', 'P03008');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 29', '2023-10-27', 0, '000000029', '00000000029', 'HD000029', 'P03009');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 30', '2000-01-01', 1, '000000030', '00000000030', 'HD000030', 'P03010');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 31', '2000-10-27', 0, '000000031', '00000000031', 'HD000031', 'P04001');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 32', '2001-08-23', 1, '000000032', '00000000032', 'HD000032', 'P04002');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 33', '2002-06-19', 0, '000000033', '00000000033', 'HD000033', 'P04003');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 34', '2003-04-15', 1, '000000034', '00000000034', 'HD000034', 'P04004');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 35', '2004-02-09', 0, '000000035', '00000000035', 'HD000035', 'P04005');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 36', '2004-12-05', 1, '000000036', '00000000036', 'HD000036', 'P04006');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 37', '2005-10-01', 0, '000000037', '00000000037', 'HD000037', 'P04007');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 38', '2006-07-28', 1, '000000038', '00000000038', 'HD000038', 'P04008');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 39', '2007-05-24', 0, '000000039', '00000000039', 'HD000039', 'P04009');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 40', '2008-03-19', 1, '000000040', '00000000040', 'HD000040', 'P04010');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 41', '2009-01-13', 0, '000000041', '00000000041', 'HD000041', 'P03001');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 42', '2009-11-09', 1, '000000042', '00000000042', 'HD000042', 'P03002');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 43', '2010-09-05', 0, '000000043', '00000000043', 'HD000043', 'P03003');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 44', '2011-07-02', 1, '000000044', '00000000044', 'HD000044', 'P03004');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 45', '2012-04-27', 0, '000000045', '00000000045', 'HD000045', 'P03005');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 46', '2013-02-21', 1, '000000046', '00000000046', 'HD000046', 'P03006');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 47', '2013-12-18', 0, '000000047', '00000000047', 'HD000047', 'P03007');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 48', '2014-10-14', 1, '000000048', '00000000048', 'HD000048', 'P03008');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 49', '2015-08-10', 0, '000000049', '00000000049', 'HD000049', 'P03009');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 50', '2016-06-05', 1, '000000050', '00000000050', 'HD000050', 'P03010');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 51', '2017-04-01', 0, '000000051', '00000000051', 'HD000051', 'P03001');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 52', '2018-01-26', 1, '000000052', '00000000052', 'HD000052', 'P03002');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 53', '2018-11-22', 0, '000000053', '00000000053', 'HD000053', 'P03003');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 54', '2019-09-18', 1, '000000054', '00000000054', 'HD000054', 'P03004');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 55', '2020-07-14', 0, '000000055', '00000000055', 'HD000055', 'P03005');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 56', '2021-05-10', 1, '000000056', '00000000056', 'HD000056', 'P03006');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 57', '2022-03-06', 0, '000000057', '00000000057', 'HD000057', 'P03007');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 58', '2022-12-31', 1, '000000058', '00000000058', 'HD000058', 'P03008');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 59', '2023-10-27', 0, '000000059', '00000000059', 'HD000059', 'P03009');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 60', '2000-01-01', 1, '000000060', '00000000060', 'HD000060', 'P03010');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 61', '2000-10-27', 0, '000000061', '00000000061', 'HD000061', 'P03001');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 62', '2001-08-23', 1, '000000062', '00000000062', 'HD000062', 'P03002');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 63', '2002-06-19', 0, '000000063', '00000000063', 'HD000063', 'P03003');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 64', '2003-04-15', 1, '000000064', '00000000064', 'HD000064', 'P03004');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 65', '2004-02-09', 0, '000000065', '00000000065', 'HD000065', 'P03005');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 66', '2004-12-05', 1, '000000066', '00000000066', 'HD000066', 'P04006');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 67', '2005-10-01', 0, '000000067', '00000000067', 'HD000067', 'P04007');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 68', '2006-07-28', 1, '000000068', '00000000068', 'HD000068', 'P04008');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 69', '2007-05-24', 0, '000000069', '00000000069', 'HD000069', 'P04009');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 70', '2008-03-19', 1, '000000070', '00000000070', 'HD000070', 'P04010');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 71', '2009-01-13', 0, '000000071', '00000000071', 'HD000071', 'P04001');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 72', '2009-11-09', 1, '000000072', '00000000072', 'HD000072', 'P04002');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 73', '2010-09-05', 0, '000000073', '00000000073', 'HD000073', 'P04003');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 74', '2011-07-02', 1, '000000074', '00000000074', 'HD000074', 'P04004');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 75', '2012-04-27', 0, '000000075', '00000000075', 'HD000075', 'P04005');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 76', '2013-02-21', 1, '000000076', '00000000076', 'HD000076', 'P04006');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 77', '2013-12-18', 0, '000000077', '00000000077', 'HD000077', 'P04007');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 78', '2014-10-14', 1, '000000078', '00000000078', 'HD000078', 'P04008');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 79', '2015-08-10', 0, '000000079', '00000000079', 'HD000079', 'P04009');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 80', '2016-06-05', 1, '000000080', '00000000080', 'HD000080', 'P04010');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 81', '2017-04-01', 0, '000000081', '00000000081', 'HD000081', 'P05001');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 82', '2018-01-26', 1, '000000082', '00000000082', 'HD000082', 'P05002');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 83', '2018-11-22', 0, '000000083', '00000000083', 'HD000083', 'P05003');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 84', '2019-09-18', 1, '000000084', '00000000084', 'HD000084', 'P05004');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 85', '2020-07-14', 0, '000000085', '00000000085', 'HD000085', 'P05005');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 86', '2021-05-10', 1, '000000086', '00000000086', 'HD000086', 'P05006');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 87', '2022-03-06', 0, '000000087', '00000000087', 'HD000087', 'P05007');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 88', '2022-12-31', 1, '000000088', '00000000088', 'HD000088', 'P05008');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 89', '2023-10-27', 0, '000000089', '00000000089', 'HD000089', 'P05009');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 90', '2000-01-01', 1, '000000090', '00000000090', 'HD000090', 'P05010');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 91', '2000-10-27', 0, '000000091', '00000000091', 'HD000091', 'P05001');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 92', '2001-08-23', 1, '000000092', '00000000092', 'HD000092', 'P05002');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 93', '2002-06-19', 0, '000000093', '00000000093', 'HD000093', 'P05003');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 94', '2003-04-15', 1, '000000094', '00000000094', 'HD000094', 'P05004');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 95', '2004-02-09', 0, '000000095', '00000000095', 'HD000095', 'P05005');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 96', '2004-12-05', 1, '000000096', '00000000096', 'HD000096', 'P05006');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 97', '2005-10-01', 0, '000000097', '00000000097', 'HD000097', 'P05007');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 98', '2006-07-28', 1, '000000098', '00000000098', 'HD000098', 'P05008');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 99', '2007-05-24', 0, '000000099', '00000000099', 'HD000099', 'P05009');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 100', '2008-03-19', 1, '000000100', '00000000100', 'HD000100', 'P05010');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 101', '2009-01-13', 0, '000000101', '00000000101', 'HD000101', 'P01001');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 102', '2009-11-09', 1, '000000102', '00000000102', 'HD000102', 'P01002');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 103', '2010-09-05', 0, '000000103', '00000000103', 'HD000103', 'P01003');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 104', '2011-07-02', 1, '000000104', '00000000104', 'HD000104', 'P01004');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 105', '2012-04-27', 0, '000000105', '00000000105', 'HD000105', 'P01005');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 106', '2013-02-21', 1, '000000106', '00000000106', 'HD000106', 'P01006');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 107', '2013-12-18', 0, '000000107', '00000000107', 'HD000107', 'P01007');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 108', '2014-10-14', 1, '000000108', '00000000108', 'HD000108', 'P01008');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 109', '2015-08-10', 0, '000000109', '00000000109', 'HD000109', 'P01009');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 110', '2016-06-05', 1, '000000110', '00000000110', 'HD000110', 'P01010');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 111', '2017-04-01', 0, '000000111', '00000000111', 'HD000111', 'P02001');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 112', '2018-01-26', 1, '000000112', '00000000112', 'HD000112', 'P02002');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 113', '2018-11-22', 0, '000000113', '00000000113', 'HD000113', 'P02003');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 114', '2019-09-18', 1, '000000114', '00000000114', 'HD000114', 'P02004');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 115', '2020-07-14', 0, '000000115', '00000000115', 'HD000115', 'P02005');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 116', '2021-05-10', 1, '000000116', '00000000116', 'HD000116', 'P02006');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 117', '2022-03-06', 0, '000000117', '00000000117', 'HD000117', 'P02007');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 118', '2022-12-31', 1, '000000118', '00000000118', 'HD000118', 'P02008');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 119', '2023-10-27', 0, '000000119', '00000000119', 'HD000119', 'P02009');
INSERT INTO NguoiO (tenNguoiO, ngaySinh, gioiTinh, sdt, soCCCD, maHD, maPhong) VALUES (N'Khach O 120', '2000-01-01', 1, '000000120', '00000000120', 'HD000120', 'P02010');
GO



/* CHAY LAI CODE DUOI DAY THEM LAN NUA */



ALTER TABLE HoaDon
ADD 
	tongTien FLOAT NULL,
    tienGiam FLOAT NULL,
    tienTra FLOAT NULL,
    tienThue FLOAT NULL,
    phiDoiPhong FLOAT NULL;
GO

ALTER TABLE [dbo].[ChiTietHoaDon]
ADD 
	soNgayO int null;
GO

ALTER TABLE [dbo].[HoaDonHuyPhong]
ADD 
	tienHoanTra float null;
GO