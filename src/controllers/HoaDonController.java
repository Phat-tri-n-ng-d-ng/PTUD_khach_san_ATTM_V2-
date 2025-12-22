package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import database.dao.HoaDonDao;
import entitys.ChiTietHoaDon;
import entitys.HoaDon;
import entitys.KhachHang;
import entitys.NguoiO;
import enums.PhuongThucThanhToan;
import enums.TrangThaiHoaDon;
import view.dialogs.HoaDonDialog;
import view.dialogs.PhieuDatPhongDialog;
import view.dialogs.PhieuThuePhongDialog;
import view.panels.HoaDonPanel;

public class HoaDonController implements ActionListener,MouseListener {
	private HoaDonPanel hoaDonPanel;
	private HoaDonDao hoaDonDao;

	public HoaDonController(HoaDonPanel hoaDonPanel) {
		this.hoaDonDao =  new HoaDonDao();
		this.hoaDonPanel = hoaDonPanel;
		hoaDonPanel.table_DanhSachHoaDon.addMouseListener(this);
		hoaDonPanel.btn_TimHoaDon.addActionListener(this);

	}

	public void hienThiThongTin() {
		ArrayList<HoaDon> dsHD = hoaDonDao.getTatCaHoaDon();
		hoaDonPanel.model_DSHD.setRowCount(0);
		for(HoaDon hd : dsHD) {
			String tongTien =  String.format("%,.0f VND", hd.getTongTien());
			hoaDonPanel.model_DSHD.addRow(new Object[] {
					hd.getMaHD(),
					hd.getNgayLap(),
					hd.getKhachHang().getTenKH(),
					hd.getKhachHang().getSdt(),
					tongTien,
					hd.getTrangThai().getTenTrangThai()
			});

		}
	}
	public String doiDonVi(double tien) {
		return String.format("%,.0f VND",tien);
	}
	private LocalDateTime layNgayTuDateChooser(JDateChooser ngayChon) {
		if (ngayChon!= null && ngayChon.getDate() != null) {
			java.util.Date ngay = ngayChon.getDate();
			return ngay.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		}
		return null;
	}
	//Hoa DOn theo ma
	public void timHoaDonTheoMa(String maHD) {
		try {
			HoaDon hd = hoaDonDao.timHoaDonTheoMa(maHD);
			hoaDonPanel.model_DSHD.setRowCount(0);

			if (hd == null) {
				JOptionPane.showMessageDialog(hoaDonPanel, "Khong tim thay hoa don co ma: " + maHD);
				return;
			}

			hoaDonPanel.model_DSHD.addRow(new Object[]{
					hd.getMaHD(),
					hd.getNgayLap(),
					hd.getKhachHang().getTenKH(),
					hd.getKhachHang().getSdt(),
					doiDonVi(hd.getTongTien())
			});

		} catch (Exception e) {
			JOptionPane.showMessageDialog(hoaDonPanel, "Loi khi tim hoa don theo ma: " + e.getMessage());
			e.printStackTrace();
		}
	}
	//hoa don trong khoang
	public void timHoaDonTheoKhoang(LocalDateTime ngayBD, LocalDateTime ngayKT) {
		try {
			ArrayList<HoaDon> dsHoaDon = hoaDonDao.timHoaDonTheoKhoang(ngayBD, ngayKT);
			hoaDonPanel.model_DSHD.setRowCount(0);

			if (dsHoaDon.isEmpty()) {
				JOptionPane.showMessageDialog(hoaDonPanel, "Khong tim thay hoa don trong khoang ngay nay!");
				return;
			}

			for (HoaDon hd : dsHoaDon) {
				hoaDonPanel.model_DSHD.addRow(new Object[]{
						hd.getMaHD(),
						hd.getNgayLap(),
						hd.getKhachHang().getTenKH(),
						hd.getKhachHang().getSdt(),
						doiDonVi(hd.getTongTien())
				});
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(hoaDonPanel, "Loi khi tim hoa don: " + e.getMessage());
			e.printStackTrace();
		}
	}
	//Hd theo sdt
	public void timHoaDonTheoSDT(String SDT) {
		try {
			ArrayList<HoaDon> dsHoaDon = hoaDonDao.timHoaDonTheoSDT(SDT);
			hoaDonPanel.model_DSHD.setRowCount(0);
			if (dsHoaDon == null || dsHoaDon.isEmpty()) {
				JOptionPane.showMessageDialog(hoaDonPanel, "Khong tim thay hoa don co so dien thoai: " + SDT);
				return;
			}
			for (HoaDon hd : dsHoaDon) {
				hoaDonPanel.model_DSHD.addRow(new Object[]{
						hd.getMaHD(),
						hd.getNgayLap(),
						hd.getKhachHang().getTenKH(),
						hd.getKhachHang().getSdt(),
						doiDonVi(hd.getTongTien())
				});
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(hoaDonPanel, "Loi khi tim hoa don theo so dien thoai: " + e.getMessage());
			e.printStackTrace();
		}
	}

	//HD theo ngay
	public void timHoaDonTheoNgay(LocalDateTime ngay) {
		try {
			ArrayList<HoaDon> dsHoaDon = hoaDonDao.timHoaDonTheoNgay(ngay);
			DefaultTableModel model = hoaDonPanel.model_DSHD;
			model.setRowCount(0);

			if (dsHoaDon.isEmpty()) {
				JOptionPane.showMessageDialog(hoaDonPanel, "Khong tim thay hoa don trong khoang ngay nay!");
				return;
			}

			for (HoaDon hd : dsHoaDon) {
				model.addRow(new Object[]{
						hd.getMaHD(),
						hd.getNgayLap(),
						hd.getKhachHang().getTenKH(),
						hd.getKhachHang().getSdt(),
						doiDonVi(hd.getTongTien())
				});
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(hoaDonPanel, "Loi khi tim hoa don: " + e.getMessage());
			e.printStackTrace();
		}
	}
	private TrangThaiHoaDon getTrangThai(String trangThai) {
		switch (trangThai) {
			case "Hóa đơn đặt phòng" -> { return TrangThaiHoaDon.PhieuDatPhong ; }
			case "Hóa đơn hoàn thành" -> { return TrangThaiHoaDon.HoaDonHoanThanh; }
			case "Hóa đơn thuê phòng" -> { return TrangThaiHoaDon.PhieuThuePhong; }
			case "Tất cả" -> { return TrangThaiHoaDon.TatCa;}
			default -> {return TrangThaiHoaDon.PhieuDatPhong;}
		}
	}
	//HD theo trang thai
	public void locHoaDonTheoTrangThai() {
		String trangThai = hoaDonPanel.cbb_TrangThaiHoaDon.getSelectedItem().toString();
		ArrayList<HoaDon> dsHoaDon;
		if (trangThai.equals("Tất cả")) {
			dsHoaDon = hoaDonDao.getTatCaHoaDon();
		}else {
			TrangThaiHoaDon trangThaiHD = getTrangThai(trangThai);
			dsHoaDon = hoaDonDao.getHoaDonTheoTrangThai(trangThaiHD.toString());
		}
		hoaDonPanel.model_DSHD.setRowCount(0);
		for(HoaDon hd : dsHoaDon) {
			hoaDonPanel.model_DSHD.addRow(new Object[] {
					hd.getMaHD(),
					hd.getNgayLap(),
					hd.getKhachHang().getTenKH(),
					hd.getKhachHang().getSdt(),
					doiDonVi(hd.getTongTien()),
					hd.getTrangThai().getTenTrangThai()
			});

		}

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o ==  hoaDonPanel.btn_TimHoaDon) {
			String maHD = hoaDonPanel.txt_MaHoaDon.getText().trim();
			String soDT = hoaDonPanel.txt_SoDienThoaiKhachHang.getText().trim();
			LocalDateTime ngayChon = layNgayTuDateChooser(hoaDonPanel.ChonNgay);
			LocalDateTime ngayBD = layNgayTuDateChooser(hoaDonPanel.NgayBD);
			LocalDateTime ngayKT = layNgayTuDateChooser(hoaDonPanel.ngayKT);
			String trangThai = hoaDonPanel.cbb_TrangThaiHoaDon.getSelectedItem().toString();
			if(hoaDonPanel.rdbtn_TimMaHoaDon.isSelected()) {
				if (maHD.isEmpty()) {
					JOptionPane.showMessageDialog(hoaDonPanel, "Vui long nhap ma hoa don!");
					return;
				}
				timHoaDonTheoMa(maHD);
			}else if(hoaDonPanel.rdbtn_SoDTKH.isSelected()) {
				if (soDT.isEmpty()) {
					JOptionPane.showMessageDialog(hoaDonPanel, "Vui long nhap so dien thoai!");
					return;
				}
				timHoaDonTheoSDT(soDT);
			}else if (hoaDonPanel.rdbtn_ChonNgay.isSelected()) {
				timHoaDonTheoNgay(ngayChon);

			}else if (hoaDonPanel.rdbtn_ChonKhoangTG.isSelected()) {
				timHoaDonTheoKhoang(ngayBD, ngayKT);
			}else if (hoaDonPanel.rdbtn_TrangThai.isSelected()) {
				locHoaDonTheoTrangThai();
			}else {
				JOptionPane.showMessageDialog(hoaDonPanel, "Vui long chon dieu kien tim kiem!");
				return;
			}
		}


	}
	@Override
    public void mouseClicked(MouseEvent e) {
        int row = hoaDonPanel.table_DanhSachHoaDon.getSelectedRow();
        if(row != -1) {
            String maHD = hoaDonPanel.table_DanhSachHoaDon.getValueAt(row, 0).toString();
            KhachHang kh = hoaDonDao.getKhachHangTheoHD(maHD);

            // THÊM KIỂM TRA NULL
            if (kh == null) {
                JOptionPane.showMessageDialog(hoaDonPanel,
                        "Không tìm thấy thông tin khách hàng cho hóa đơn: " + maHD);
                return;
            }

            ArrayList<ChiTietHoaDon> dsCTHD = hoaDonDao.timPhongTheoMaHD(maHD);
            String trangThai = hoaDonPanel.table_DanhSachHoaDon.getValueAt(row, 5).toString();
            HoaDon hd = hoaDonDao.timHoaDonTheoMa(maHD);

            // Kiểm tra thêm các đối tượng khác có thể null
            if (dsCTHD == null || dsCTHD.isEmpty()) {
                JOptionPane.showMessageDialog(hoaDonPanel,
                        "Không tìm thấy chi tiết hóa đơn cho: " + maHD);
                return;
            }

            if (hd == null) {
                JOptionPane.showMessageDialog(hoaDonPanel,
                        "Không tìm thấy thông tin hóa đơn: " + maHD);
                return;
            }

            switch (trangThai) {
                case "Phiếu đặt phòng": {
                    PhieuDatPhongDialog phieuDatPhong = new PhieuDatPhongDialog();
                    phieuDatPhong.txt_SDT.setText(kh.getSdt());
                    phieuDatPhong.txt_HoTen.setText(kh.getTenKH());
                    phieuDatPhong.txt_Email.setText(kh.getEmail());
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    phieuDatPhong.txt_NgaySinh.setText(kh.getNgaySinh().format(fmt));
                    phieuDatPhong.txt_CCCD.setText(kh.getSoCCCD());
                    phieuDatPhong.txt_DiemTichLuy.setText(kh.getDiemTichLuy()+"");
                    phieuDatPhong.txt_HangKhachHang.setText(kh.getHangKH().getTenHang());
                    if(kh.isGioiTinh()){
                        phieuDatPhong.rdbtn_NamNguoiO.setSelected(true);
                        phieuDatPhong.rdbtn_NuNguoiO.setSelected(false);
                    }else{
                        phieuDatPhong.rdbtn_NuNguoiO.setSelected(true);
                        phieuDatPhong.rdbtn_NamNguoiO.setSelected(false);
                    };
                    if(hd!= null) {
                        if(PhuongThucThanhToan.TienMat.equals(hd.getpTTT())) {
                            phieuDatPhong.lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setText("Tiền mặt");
                        }else {
                            phieuDatPhong.lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setText("Chuyển khoản");
                        }
                    }
                    phieuDatPhong.txt_TienKhachDua.setText(hd.getTienNhan()+"");
                    phieuDatPhong.model.setRowCount(0);
                    for(ChiTietHoaDon p : dsCTHD) {
                        String giaPhong =  String.format("%,.0f", p.getPhong().getGiaPhong());
                        phieuDatPhong.model.addRow(new Object[] {
                                p.getPhong().getMaPhong(),
                                p.getPhong().getLoaiPhong().getTenLoaiPhong(),
                                p.getPhong().getSucChuaToiDa(),
                                giaPhong,
                                p.getPhong().getTienCoc()
                        });
                    }
                    phieuDatPhong.lbl_TienCuaTongTienTrongPnlTongTien.setText(hd.getTongTien()+"");
//				phieuDatPhong.lbl_TienCuaTienCocTrongPnlTongTien.setText(h)
                    phieuDatPhong.setModal(true);
                    phieuDatPhong.setVisible(true);
                    break;
                }case "Phiếu thuê phòng":{
                    PhieuThuePhongDialog phieuThuePhong = new PhieuThuePhongDialog();

//				for(KhachHang kh : dsKhachHang) {
                    phieuThuePhong.txt_SDT.setText(kh.getSdt());
                    phieuThuePhong.txt_HoTen.setText(kh.getTenKH());
                    phieuThuePhong.txt_Email.setText(kh.getEmail());
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    phieuThuePhong.txt_NgaySinh.setText(kh.getNgaySinh().format(fmt));
                    phieuThuePhong.txt_CCCD.setText(kh.getSoCCCD());
                    phieuThuePhong.txt_DiemTichLuy.setText(kh.getDiemTichLuy()+"");
                    phieuThuePhong.txt_HangKhachHang.setText(kh.getHangKH().getTenHang());
                    if(kh.isGioiTinh()){
                        phieuThuePhong.rdbtn_NamNguoiO.setSelected(true);
                        phieuThuePhong.rdbtn_NuNguoiO.setSelected(false);
                    }else{
                        phieuThuePhong.rdbtn_NuNguoiO.setSelected(true);
                        phieuThuePhong.rdbtn_NamNguoiO.setSelected(false);
                    };

//				};
                    phieuThuePhong.model_DanhSachPhong.setRowCount(0);
                    for(ChiTietHoaDon p : dsCTHD) {
                        String giaPhong =  String.format("%,.0f", p.getPhong().getGiaPhong());
                        phieuThuePhong.model_DanhSachPhong.addRow(new Object[] {
                                p.getPhong().getMaPhong(),
                                p.getPhong().getLoaiPhong().getTenLoaiPhong(),
                                p.getPhong().getSucChuaToiDa(),
                                giaPhong,
                                p.getPhong().getTienCoc()
                        });
                    }
                    if (!dsCTHD.isEmpty()) {
                        String maP = dsCTHD.get(0).getPhong().getMaPhong();

                        ArrayList<NguoiO> dsNguoiO = hoaDonDao.getNguoiOTheoPhong(maP);
                        phieuThuePhong.model_DanhSachNguoiO.setRowCount(0);
                        for (NguoiO ngO : dsNguoiO) {
                            phieuThuePhong.model_DanhSachNguoiO.addRow(new Object[]{
                                    ngO.getHoTen(),
                                    ngO.getNgaySinh(),
                                    ngO.isGioiTinh() ? "Nam" : "Nữ",
                                    ngO.getSDT(),
                                    ngO.getCCCD()
                            });
                        }
                    }
                    if(hd!= null) {
                        if(PhuongThucThanhToan.TienMat.equals(hd.getpTTT())) {
                            phieuThuePhong.lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setText("Tiền mặt");
                        }else {
                            phieuThuePhong.lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setText("Chuyển khoản");
                        }
                    }


                    phieuThuePhong.setModal(true);
                    phieuThuePhong.setVisible(true);
                    //add mouse lishtenner cho tablePhong




                    break;

                }case "Hóa đơn hoàn thành" :{
                    HoaDonDialog hoaDonHT = new HoaDonDialog();
//				for(KhachHang kh : dsKhachHang) {
                    hoaDonHT.txt_SDT.setText(kh.getSdt());
                    hoaDonHT.txt_HoTen.setText(kh.getTenKH());
                    hoaDonHT.txt_Email.setText(kh.getEmail());
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    hoaDonHT.txt_NgaySinh.setText(kh.getNgaySinh().format(fmt));
                    hoaDonHT.txt_CCCD.setText(kh.getSoCCCD());
                    hoaDonHT.txt_DiemTichLuy.setText(kh.getDiemTichLuy()+"");
                    hoaDonHT.txt_HangKhachHang.setText(kh.getHangKH().getTenHang());
                    if(kh.isGioiTinh()){
                        hoaDonHT.rdbtn_NamNguoiO.setSelected(true);
                        hoaDonHT.rdbtn_NuNguoiO.setSelected(false);
                    }else{
                        hoaDonHT.rdbtn_NuNguoiO.setSelected(true);
                        hoaDonHT.rdbtn_NamNguoiO.setSelected(false);
                    };

//				};
                    hoaDonHT.model.setRowCount(0);
                    for(ChiTietHoaDon ctDon : dsCTHD) {
                        String giaPhong =  String.format("%,.0f", ctDon.getPhong().getGiaPhong());
                        hoaDonHT.model.addRow(new Object[] {
                                ctDon.getPhong().getMaPhong(),
                                ctDon.getPhong().getLoaiPhong().getTenLoaiPhong(),
                                ctDon.getPhong().getSucChuaToiDa(),
                                ctDon.getSoNgayO(),
                                giaPhong,
                                ctDon.getPhong().getTienCoc(),
                                ctDon.getThanhTien()
                        });
                    }
                    if(hd!= null) {
                        if(PhuongThucThanhToan.TienMat.equals(hd.getpTTT())) {
                            hoaDonHT.lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setText("Tiền mặt");
                        }else {
                            hoaDonHT.lbl_PhuongThucThanhToanDuocChonTrongPnlTongTien.setText("Chuyển khoản");
                        }
                    }
                    hoaDonHT.setModal(true);
                    hoaDonHT.setVisible(true);
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unexpected value: " + trangThai);
            }
        }
    }


    @Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}


}
