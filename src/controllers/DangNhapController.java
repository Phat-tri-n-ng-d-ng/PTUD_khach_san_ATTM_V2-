
package controllers;

import database.dao.DangNhapDao;
import entitys.NhanVien;
import entitys.TaiKhoan;
import view.mainframe.DangNhapFrame;
import view.mainframe.MainFrame;

import javax.swing.*;

public class DangNhapController {
    private DangNhapFrame dangNhapFrame;
    private DangNhapDao dangNhapDao;

    public DangNhapController(DangNhapFrame dangNhapFrame) {
        this.dangNhapFrame = dangNhapFrame;
        this.dangNhapDao = new DangNhapDao();

        this.dangNhapFrame.btn_DangNhap.addActionListener(e -> xuLyDangNhap());
        this.dangNhapFrame.btn_QuenMatKhau.addActionListener(e -> xuLyQuenMatKhau());

        // Thêm sự kiện Enter cho các trường nhập liệu
        this.dangNhapFrame.txt_TaiKhoan.addActionListener(e -> xuLyDangNhap());
        this.dangNhapFrame.passwordField_MatKhau.addActionListener(e -> xuLyDangNhap());
    }

    private void xuLyQuenMatKhau() {
        JOptionPane.showMessageDialog(dangNhapFrame,
                "Vui lòng liên hệ quản trị viên để đặt lại mật khẩu!",
                "Quên mật khẩu",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void xuLyDangNhap() {
        String tenDangNhap = dangNhapFrame.txt_TaiKhoan.getText().trim();
        String matKhau = new String(dangNhapFrame.passwordField_MatKhau.getPassword()).trim();

        // Kiểm tra dữ liệu nhập
        if (tenDangNhap.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(dangNhapFrame,
                    "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!",
                    "Lỗi đăng nhập",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Kiểm tra đăng nhập
            TaiKhoan taiKhoan = dangNhapDao.kiemTraDangNhap(tenDangNhap, matKhau);

            if (taiKhoan != null) {
                // Lấy thông tin nhân viên
                NhanVien nhanVien = dangNhapDao.getNhanVienByMaNV(taiKhoan.getMaNV());

                if (nhanVien != null) {
                    // Đăng nhập thành công - LƯU VÀO QUẢN LÝ PHIÊN
                    QuanLyPhien quanLyPhien = QuanLyPhien.getInstance();
                    quanLyPhien.setTaiKhoanDangNhap(taiKhoan);
                    quanLyPhien.setNhanVienDangNhap(nhanVien);

                    // Mở MainFrame
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.setVisible(true);

                    // Đóng cửa sổ đăng nhập
                    dangNhapFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(dangNhapFrame,
                            "Không tìm thấy thông tin nhân viên!",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }

            }else {
                // Đăng nhập thất bại
                JOptionPane.showMessageDialog(dangNhapFrame,
                        "Tên đăng nhập hoặc mật khẩu không đúng!\n" +
                                "Hoặc tài khoản đã bị vô hiệu hóa.",
                        "Đăng nhập thất bại",
                        JOptionPane.ERROR_MESSAGE);

                // Xóa mật khẩu và focus lại ô tài khoản
                dangNhapFrame.passwordField_MatKhau.setText("");
                dangNhapFrame.txt_TaiKhoan.requestFocus();
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(dangNhapFrame,
                    "Lỗi kết nối hệ thống. Vui lòng thử lại sau!",
                    "Lỗi hệ thống",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
