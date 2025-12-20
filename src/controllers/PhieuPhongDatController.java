package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import database.dao.HoaDonDao;
import database.dao.KhuyenMaiDao;
import entitys.ChiTietHoaDon;
import entitys.HoaDon;
import entitys.NguoiO;
import entitys.Phong;
import services.KhachHangService;
import services.PhongService;
import view.dialogs.PhieuPhongDatDialog;

public class PhieuPhongDatController implements ActionListener, MouseListener {
	private PhieuPhongDatDialog phieuPhongDatDialog;
	private PhongService phongService;
	private KhachHangService khachHangServies;
	private HoaDonDao hoaDonDao;
	private KhuyenMaiDao khuyenMaiDao;
	private Phong phongChinh;
	private Map<String, ArrayList<NguoiO>> danhSachNguoiOTheoPhong;
	private String phongDuocChonDeThem = null; 

	public PhieuPhongDatController(PhieuPhongDatDialog phieuPhongDatDialog, Phong phong) {
		this.phieuPhongDatDialog = phieuPhongDatDialog;
		this.phongChinh = phong;
		this.danhSachNguoiOTheoPhong = new HashMap<>();
		this.phongDuocChonDeThem = null; 

		hoaDonDao = new HoaDonDao();
		phongService = new PhongService();
		khachHangServies = new KhachHangService();
		khuyenMaiDao = new KhuyenMaiDao();
		
		phieuPhongDatDialog.btnNhanPhong.addActionListener(this);
		phieuPhongDatDialog.btnHuyPhong.addActionListener(this);
		phieuPhongDatDialog.btn_Them.addActionListener(this);
		phieuPhongDatDialog.table_PhongDiKem.addMouseListener(this);

		hienThiThongTinKHDat();
		khoiTaoDanhSachNguoiOTheoPhong();
	}

	private void khoiTaoDanhSachNguoiOTheoPhong() {
		// Khởi tạo danh sách người ở cho tất cả phòng
		danhSachNguoiOTheoPhong.put(phongChinh.getMaPhong(), new ArrayList<>());
		
		DefaultTableModel modelPhong = phieuPhongDatDialog.model_PhongDiKem;
		for (int i = 0; i < modelPhong.getRowCount(); i++) {
			String maPhong = modelPhong.getValueAt(i, 0).toString();
			danhSachNguoiOTheoPhong.put(maPhong, new ArrayList<>());
		}
	}

	public void hienThiThongTinKHDat() {
		String maP = phongChinh.getMaPhong();
		HoaDon hd = hoaDonDao.timHoaDonTheoPhongDaDat(maP);
		ChiTietHoaDon cthd = hoaDonDao.getChiTietHoaDon_1(hd.getMaHD());
		ArrayList<Phong> dsPhong = phongService.getPhongBangMa(hd.getMaHD());

		// Hiển thị thông tin khách hàng
		phieuPhongDatDialog.txt_HoTen.setText(hd.getKhachHang().getTenKH());
		phieuPhongDatDialog.txt_SDT.setText(hd.getKhachHang().getSdt());
		phieuPhongDatDialog.txt_CCCD.setText(hd.getKhachHang().getSoCCCD());
		
		// Hiển thị thời gian thuê
		phieuPhongDatDialog.ngayBatDau.setDate(localDateTimeToDate(cthd.getNgayNhanPhong()));
		phieuPhongDatDialog.ngayKetThuc.setDate(localDateTimeToDate(cthd.getNgayTraPhong()));

		// Hiển thị danh sách phòng đi kèm
		for (Phong p : dsPhong) {
			if (!p.getMaPhong().equals(phongChinh.getMaPhong())) {
				phieuPhongDatDialog.model_PhongDiKem.addRow(new Object[] {
						p.getMaPhong(),
						p.getTang(),
						p.getSoPhong(),
						p.getLoaiPhong().getTenLoaiPhong(),
						p.getGiaPhong()
				});
			}
		}
		

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == phieuPhongDatDialog.table_PhongDiKem) {
			int selectedRow = phieuPhongDatDialog.table_PhongDiKem.getSelectedRow();
			if (selectedRow != -1) {
				String maPhong = phieuPhongDatDialog.table_PhongDiKem.getValueAt(selectedRow, 0).toString();
				String soPhong = phieuPhongDatDialog.table_PhongDiKem.getValueAt(selectedRow, 2).toString();

				// Lưu phòng được chọn để thêm người ở
				phongDuocChonDeThem = maPhong;
				
				// Thông báo đã chọn phòng
				JOptionPane.showMessageDialog(phieuPhongDatDialog, 
					"Đã chọn phòng: " + maPhong + "\n" +
					"Người ở tiếp theo sẽ được thêm vào phòng này");
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

	public void lamMoiFormNguoiO() {
		phieuPhongDatDialog.txt_SDTKHO.setText("");
		phieuPhongDatDialog.txt_HoTenKHO.setText("");
		phieuPhongDatDialog.txt_CCCDKHO.setText("");
		phieuPhongDatDialog.rdbtn_Nam.setSelected(false);
		phieuPhongDatDialog.rdbtn_Nu.setSelected(false);
		phieuPhongDatDialog.ngaySinhKHO.setDate(null);
	}

	public Date localDateTimeToDate(LocalDateTime ldt) {
		if (ldt == null) return null;
		return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
	}

	public boolean kiemTraDuLieuNguoiO() {
		if (phieuPhongDatDialog.txt_HoTenKHO.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(phieuPhongDatDialog, "Vui lòng nhập họ tên");
			phieuPhongDatDialog.txt_HoTenKHO.requestFocus();
			return false;
		}

		String cccd = phieuPhongDatDialog.txt_CCCDKHO.getText().trim();
		if (!cccd.matches("\\d{12}")) {
			JOptionPane.showMessageDialog(phieuPhongDatDialog, "CCCD phải đủ 12 chữ số");
			phieuPhongDatDialog.txt_CCCDKHO.requestFocus();
			return false;
		}

		String sdt = phieuPhongDatDialog.txt_SDTKHO.getText().trim();
		if (!sdt.matches("0\\d{9}")) {
			JOptionPane.showMessageDialog(phieuPhongDatDialog, "Số điện thoại phải đủ 10 số và bắt đầu bằng 0");
			phieuPhongDatDialog.txt_SDTKHO.requestFocus();
			return false;
		}

		if (phieuPhongDatDialog.ngaySinhKHO.getDate() == null) {
			JOptionPane.showMessageDialog(phieuPhongDatDialog, "Vui lòng chọn ngày sinh");
			return false;
		}

		if (!phieuPhongDatDialog.rdbtn_Nam.isSelected() && !phieuPhongDatDialog.rdbtn_Nu.isSelected()) {
			JOptionPane.showMessageDialog(phieuPhongDatDialog, "Vui lòng chọn giới tính");
			return false;
		}

		// Kiểm tra CCCD trùng trong TẤT CẢ các phòng
		if (kiemTraCCCDTrung(cccd)) {
			JOptionPane.showMessageDialog(phieuPhongDatDialog, "CCCD đã tồn tại trong danh sách người ở");
			return false;
		}

		return true;
	}

	private boolean kiemTraCCCDTrung(String cccd) {
		for (ArrayList<NguoiO> dsNguoiO : danhSachNguoiOTheoPhong.values()) {
			for (NguoiO no : dsNguoiO) {
				if (no.getCCCD() != null && no.getCCCD().equals(cccd)) {
					return true;
				}
			}
		}
		return false;
	}
	private void huyPhong() {
		 int confirm = JOptionPane.showConfirmDialog(phieuPhongDatDialog,
			        "XÁC NHẬN HỦY PHÒNG\n\n" +
			        "Bạn có chắc chắn muốn hủy phòng:\n" +
			        "• Phòng: " + phongChinh.getMaPhong() + "\n" +
			        "• Loại phòng: " + phongChinh.getLoaiPhong().getTenLoaiPhong() + "\n" +
			        "• Giá phòng: " + phongChinh.getGiaPhong() + " VNĐ\n\n",
			        "HỦY PHÒNG",
			        JOptionPane.YES_NO_OPTION,
			        JOptionPane.WARNING_MESSAGE);
			    
			    if (confirm != JOptionPane.YES_OPTION) {
			        return;
			    }
		String lyDoHuy = JOptionPane.showInputDialog(phieuPhongDatDialog, 
		        "Nhập lý do hủy phòng " + phongChinh.getMaPhong() + ":");
		if (lyDoHuy == null) {
	        return; //Cancel
	    }
		try {
			HoaDon hd = hoaDonDao.timHoaDonTheoPhongDaDat(phongChinh.getMaPhong());
			 if (hd == null) {
		            JOptionPane.showMessageDialog(phieuPhongDatDialog, 
		                "Không tìm thấy hóa đơn đặt phòng!");
		            return;
		        }
			 boolean huyP = hoaDonDao.huyPhong(hd.getMaHD(),phongChinh.getMaPhong(),lyDoHuy,hd.getKhachHang().getMaKH());
			 if (huyP) {
		            JOptionPane.showMessageDialog(phieuPhongDatDialog, "Đã hủy phòng!");
		            phieuPhongDatDialog.dispose();
		        } else {
		            JOptionPane.showMessageDialog(phieuPhongDatDialog, "Hủy thất bại!");
		        }
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if (o == phieuPhongDatDialog.btn_Them) {
			// Kiểm tra dữ liệu
			if (!kiemTraDuLieuNguoiO()) {
				return;
			}

			String gioiTinh = phieuPhongDatDialog.rdbtn_Nam.isSelected() ? "Nam" : "Nữ";
			Date ngaySinh = phieuPhongDatDialog.ngaySinhKHO.getDate();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String ngaySinhStr = sdf.format(ngaySinh);

			// Tạo đối tượng NguoiO
			NguoiO nguoiO = new NguoiO();
			nguoiO.setHoTen(phieuPhongDatDialog.txt_HoTenKHO.getText());
			nguoiO.setNgaySinh(ngaySinh.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			nguoiO.setGioiTinh(gioiTinh.equals("Nam"));
			nguoiO.setSDT(phieuPhongDatDialog.txt_SDTKHO.getText());
			nguoiO.setCCCD(phieuPhongDatDialog.txt_CCCDKHO.getText());

			String phongDangChon = phongDuocChonDeThem;
			String thongBao = "";
			
			// Xác định phòng để thêm
			if (phongDangChon == null) {
				// KHÔNG chọn phòng đi kèm -> thêm vào phòng chính
				phongDangChon = phongChinh.getMaPhong();
				thongBao = "ĐÃ THÊM NGƯỜI Ở VÀO PHÒNG CHÍNH";
			} else {
				// Thêm vào phòng đi kèm đã chọn
				// Tìm tên phòng để hiển thị
				String tenPhong = "";
				DefaultTableModel modelPhong = phieuPhongDatDialog.model_PhongDiKem;
				for (int i = 0; i < modelPhong.getRowCount(); i++) {
					if (modelPhong.getValueAt(i, 0).toString().equals(phongDangChon)) {
						tenPhong = modelPhong.getValueAt(i, 0).toString();
						break;
					}
				}
				
				thongBao = "ĐÃ THÊM NGƯỜI Ở VÀO PHÒNG ĐI KÈM: " + tenPhong;
			}

			// Thêm vào danh sách người ở của phòng
			ArrayList<NguoiO> dsNguoiO = danhSachNguoiOTheoPhong.get(phongDangChon);
			if (dsNguoiO != null) {
				dsNguoiO.add(nguoiO);
			} else {
				// Nếu chưa có danh sách cho phòng này, tạo mới
				dsNguoiO = new ArrayList<>();
				dsNguoiO.add(nguoiO);
				danhSachNguoiOTheoPhong.put(phongDangChon, dsNguoiO);
			}

			// Hiển thị vào bảng người ở
			DefaultTableModel modelNguoiO = phieuPhongDatDialog.model_NguoiO;
			String tenPhongHienThi = phongDangChon.equals(phongChinh.getMaPhong()) ? 
					"Phòng chính" : "Phòng " + phongDangChon;
			
			modelNguoiO.addRow(new Object[] {
					phieuPhongDatDialog.txt_HoTenKHO.getText(),
					ngaySinhStr,
					gioiTinh,
					phieuPhongDatDialog.txt_SDTKHO.getText(),
					phieuPhongDatDialog.txt_CCCDKHO.getText(),
					tenPhongHienThi
			});

			// Hiển thị thông báo
			JOptionPane.showMessageDialog(phieuPhongDatDialog, thongBao);
			
			// Làm mới form để thêm tiếp
			lamMoiFormNguoiO();
			
			// Reset lại phòng được chọn (sau khi thêm xong)
			phongDuocChonDeThem = null;
			phieuPhongDatDialog.table_PhongDiKem.clearSelection();
			
		} else if (o == phieuPhongDatDialog.btnNhanPhong) {
			// Kiểm tra xem có người ở nào không
			int tongNguoiO = 0;
			for (ArrayList<NguoiO> dsNguoiO : danhSachNguoiOTheoPhong.values()) {
				tongNguoiO += dsNguoiO.size();
			}
			
			if (tongNguoiO == 0) {
				int confirm = JOptionPane.showConfirmDialog(
						phieuPhongDatDialog,
						"Không có người ở nào được thêm!\n" +
						"Bạn có muốn nhận phòng mà không có người ở không?",
						"Xác nhận",
						JOptionPane.YES_NO_OPTION);
				
				if (confirm != JOptionPane.YES_OPTION) {
					return;
				}
			}

			// Hiển thị tổng hợp trước khi lưu
			StringBuilder thongBaoXacNhan = new StringBuilder();
			thongBaoXacNhan.append("XÁC NHẬN NHẬN PHÒNG\n\n");
			thongBaoXacNhan.append("Danh sách người ở:\n");
			
			// Người ở phòng chính
			ArrayList<NguoiO> dsPhongChinh = danhSachNguoiOTheoPhong.get(phongChinh.getMaPhong());
			if (dsPhongChinh != null && !dsPhongChinh.isEmpty()) {
				thongBaoXacNhan.append("- Phòng chính: ").append(dsPhongChinh.size()).append(" người\n");
			}
			
			// Người ở các phòng đi kèm
			DefaultTableModel modelPhong = phieuPhongDatDialog.model_PhongDiKem;
			for (int i = 0; i < modelPhong.getRowCount(); i++) {
				String maPhong = modelPhong.getValueAt(i, 0).toString();
				String soPhong = modelPhong.getValueAt(i, 2).toString();
				
				ArrayList<NguoiO> dsNguoiO = danhSachNguoiOTheoPhong.get(maPhong);
				if (dsNguoiO != null && !dsNguoiO.isEmpty()) {
					thongBaoXacNhan.append("- Phòng đi kèm").append(": ").append(dsNguoiO.size()).append(" người\n");
				}
			}
			
			thongBaoXacNhan.append("\nTổng cộng: ").append(tongNguoiO).append(" người ở");
			
			int xacNhan = JOptionPane.showConfirmDialog(
					phieuPhongDatDialog,
					thongBaoXacNhan.toString(),
					"Xác nhận nhận phòng",
					JOptionPane.YES_NO_OPTION);

			if (xacNhan != JOptionPane.YES_OPTION) return;

			try {
				HoaDon hd = hoaDonDao.timHoaDonTheoPhongDaDat(phongChinh.getMaPhong());

				// Lưu tất cả người ở vào database
				ArrayList<NguoiO> tatCaNguoiO = new ArrayList<>();
				
				for (Map.Entry<String, ArrayList<NguoiO>> entry : danhSachNguoiOTheoPhong.entrySet()) {
					String maPhong = entry.getKey();
					ArrayList<NguoiO> dsNguoiO = entry.getValue();

					if (!dsNguoiO.isEmpty()) {
						Phong phong = new Phong();
						phong.setMaPhong(maPhong);

						for (NguoiO no : dsNguoiO) {
							no.setHoaDon(hd);
							no.setPhong(phong);
							tatCaNguoiO.add(no);
						}
					}
				}
				
				if (!tatCaNguoiO.isEmpty()) {
					// Lưu tất cả người ở vào database
					hoaDonDao.nhanPhong(hd.getMaHD(), tatCaNguoiO);
				}

				JOptionPane.showMessageDialog(phieuPhongDatDialog, "NHẬN PHÒNG THÀNH CÔNG!");
				phieuPhongDatDialog.dispose();

			} catch (Exception e2) {
				e2.printStackTrace();
				JOptionPane.showMessageDialog(phieuPhongDatDialog, "Lỗi khi nhận phòng: " + e2.getMessage());
			}
		}else if(o == phieuPhongDatDialog.btnHuyPhong) {
			huyPhong();
			
			
		}
	}
}