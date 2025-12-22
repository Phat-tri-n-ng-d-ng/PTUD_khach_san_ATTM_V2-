package xuatHoaDon;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import entitys.ChiTietHoaDon;
import entitys.HoaDon;
import entitys.KhachHang;
import entitys.Phong;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HoaDonPDFUtil {

    public static void xuatHoaDonPDF(HoaDon hoaDon) {
        try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50); // margins
            String folderPath = "D:/HoaDon/";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }


            String filePath = folderPath + "HoaDon_" + hoaDon.getMaHD() + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));


            document.open();

            // Font tiếng Việt - sử dụng font chuẩn
            BaseFont bf = BaseFont.createFont(
                    "c:/windows/fonts/arial.ttf",
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED
            );

            // Font định nghĩa
            Font fontTitle = new Font(bf, 22, Font.BOLD);
            Font fontSubTitle = new Font(bf, 14, Font.BOLD);
            Font fontNormal = new Font(bf, 11);
            Font fontBold = new Font(bf, 11, Font.BOLD);
            Font fontSmall = new Font(bf, 10);
            Font fontTableHeader = new Font(bf, 11, Font.BOLD);
            Font fontTableContent = new Font(bf, 10);

            DecimalFormat df = new DecimalFormat("#,### VND");
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            // ====== HEADER ======
            Paragraph company = new Paragraph("KHÁCH SẠN ATTM", fontTitle);
            company.setAlignment(Element.ALIGN_CENTER);
            company.setSpacingAfter(5);
            document.add(company);

            Paragraph address = new Paragraph("123 Đường ABC, Quận XYZ, TP.HCM", fontSmall);
            address.setAlignment(Element.ALIGN_CENTER);
            address.setSpacingAfter(5);
            document.add(address);

            Paragraph phone = new Paragraph("Hotline: 1900 1234 | Email: info@attmhotel.com", fontSmall);
            phone.setAlignment(Element.ALIGN_CENTER);
            phone.setSpacingAfter(20);
            document.add(phone);

            // Đường kẻ ngang
            Paragraph line = new Paragraph("_____________________________________________________________________________");
            line.setSpacingAfter(15);
            document.add(line);

            // ====== TIÊU ĐỀ HÓA ĐƠN ======
            Paragraph title = new Paragraph("HÓA ĐƠN THANH TOÁN", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // ====== THÔNG TIN HÓA ĐƠN ======
            Paragraph invoiceInfo = new Paragraph("THÔNG TIN HÓA ĐƠN", fontSubTitle);
            invoiceInfo.setSpacingAfter(10);
            document.add(invoiceInfo);

            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setWidths(new float[]{1, 3});

            addInfoRow(infoTable, "Mã hóa đơn:", hoaDon.getMaHD(), fontBold, fontNormal);
            addInfoRow(infoTable, "Ngày lập:",
                    hoaDon.getNgayLap().format(formatter),
                    fontBold, fontNormal);
            addInfoRow(infoTable, "Phương thức TT:", hoaDon.getpTTT().toString(), fontBold, fontNormal);
            addInfoRow(infoTable, "Nhân viên:", hoaDon.getNhanVien().getTenNV(), fontBold, fontNormal);

            document.add(infoTable);
            document.add(Chunk.NEWLINE);

            // ====== THÔNG TIN KHÁCH HÀNG ======
            Paragraph customerInfo = new Paragraph("THÔNG TIN KHÁCH HÀNG", fontSubTitle);
            customerInfo.setSpacingAfter(10);
            document.add(customerInfo);

            PdfPTable customerTable = new PdfPTable(2);
            customerTable.setWidthPercentage(100);
            customerTable.setWidths(new float[]{1, 3});

            KhachHang kh = hoaDon.getKhachHang();
            addInfoRow(customerTable, "Họ tên:", kh.getTenKH(), fontBold, fontNormal);
            addInfoRow(customerTable, "Số điện thoại:", kh.getSdt(), fontBold, fontNormal);
            addInfoRow(customerTable, "CCCD:", kh.getSoCCCD(), fontBold, fontNormal);
            addInfoRow(customerTable, "Hạng khách hàng:", kh.getHangKH() != null ? kh.getHangKH().getTenHang() : "Không xác định", fontBold, fontNormal);

            document.add(customerTable);
            document.add(Chunk.NEWLINE);

            // ====== BẢNG CHI TIẾT PHÒNG ======
            Paragraph roomDetail = new Paragraph("CHI TIẾT DỊCH VỤ", fontSubTitle);
            roomDetail.setSpacingAfter(10);
            document.add(roomDetail);

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 2, 1.5f, 1, 1.5f, 1.5f, 2});

            // Header bảng
            String[] headers = {"STT", "Mã phòng", "Loại phòng", "Số ngày", "Giá phòng", "Tiền cọc", "Thành tiền"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, fontTableHeader));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(8);
                cell.setBorderWidth(0.5f);
                cell.setBorderColor(BaseColor.GRAY);
                table.addCell(cell);
            }

            // Nội dung bảng
            int stt = 1;
            double totalRoom = 0;
            for (ChiTietHoaDon ct : hoaDon.getcTHD()) {
                Phong p = ct.getPhong();
                double thanhTien = ct.getThanhTien();
                totalRoom += thanhTien;

                addTableCell(table, String.valueOf(stt++), fontTableContent, Element.ALIGN_CENTER);
                addTableCell(table, p.getMaPhong(), fontTableContent, Element.ALIGN_LEFT);
                addTableCell(table, p.getLoaiPhong().getTenLoaiPhong(), fontTableContent, Element.ALIGN_LEFT);
                addTableCell(table, ct.getSoNgayO() + "", fontTableContent, Element.ALIGN_CENTER);
                addTableCell(table, df.format(p.getGiaPhong()), fontTableContent, Element.ALIGN_RIGHT);
                addTableCell(table, df.format(p.getTienCoc()), fontTableContent, Element.ALIGN_RIGHT);
                addTableCell(table, df.format(thanhTien), fontTableContent, Element.ALIGN_RIGHT);
            }

            // Dòng tổng cho bảng
            PdfPCell totalCell = new PdfPCell(new Phrase("TỔNG CỘNG", fontBold));
            totalCell.setColspan(6);
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalCell.setBorderWidthTop(1);
            totalCell.setBorderColorTop(BaseColor.GRAY);
            totalCell.setPadding(8);
            table.addCell(totalCell);

            PdfPCell totalValueCell = new PdfPCell(new Phrase(df.format(totalRoom), fontBold));
            totalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalValueCell.setBorderWidthTop(1);
            totalValueCell.setBorderColorTop(BaseColor.GRAY);
            totalValueCell.setPadding(8);
            table.addCell(totalValueCell);

            document.add(table);
            document.add(Chunk.NEWLINE);

            // ====== TỔNG KẾT ======
            Paragraph summary = new Paragraph("TỔNG KẾT THANH TOÁN", fontSubTitle);
            summary.setSpacingAfter(10);
            document.add(summary);

            PdfPTable summaryTable = new PdfPTable(2);
            summaryTable.setWidthPercentage(50);
            summaryTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            summaryTable.setWidths(new float[]{2, 2});

            addSummaryRow(summaryTable, "Tổng tiền phòng:", df.format(hoaDon.getTongTien()), fontNormal, fontBold);
            addSummaryRow(summaryTable, "Thuế (10%):", df.format(hoaDon.getTienThue()), fontNormal, fontBold);
            addSummaryRow(summaryTable, "Phí đổi phòng:", df.format(hoaDon.getPhiDoiPhong()), fontNormal, fontBold);
            ArrayList<ChiTietHoaDon> dsCTHD=hoaDon.getcTHD();
            double tongTienGiam=hoaDon.getTienGiamTheoHangKH();
            addSummaryRow(summaryTable, "Khuyến mãi:", df.format(tongTienGiam), fontNormal, fontBold);

            // Dòng tổng thanh toán với viền trên
            PdfPCell totalLabelCell = new PdfPCell(new Phrase("TỔNG THANH TOÁN:", fontBold));
            totalLabelCell.setBorderWidthTop(1);
            totalLabelCell.setBorderColorTop(BaseColor.GRAY);
            totalLabelCell.setPaddingTop(8);
            totalLabelCell.setPaddingBottom(8);
            totalLabelCell.setBorder(0);
            summaryTable.addCell(totalLabelCell);

            PdfPCell totalValueCell2 = new PdfPCell(new Phrase(df.format(hoaDon.getTongTienThanhToan()), fontBold));
            totalValueCell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalValueCell2.setBorderWidthTop(1);
            totalValueCell2.setBorderColorTop(BaseColor.GRAY);
            totalValueCell2.setPaddingTop(8);
            totalValueCell2.setPaddingBottom(8);
            totalValueCell2.setBorder(0);
            summaryTable.addCell(totalValueCell2);

            document.add(summaryTable);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            // ====== LỜI CẢM ƠN ======
            Paragraph thanks = new Paragraph("Cảm ơn quý khách đã sử dụng dịch vụ!", fontNormal);
            thanks.setAlignment(Element.ALIGN_CENTER);
            thanks.setSpacingAfter(5);
            document.add(thanks);

            Paragraph note = new Paragraph("Hóa đơn này là hóa đơn điện tử, có giá trị như hóa đơn GTGT", fontSmall);
            note.setAlignment(Element.ALIGN_CENTER);
            document.add(note);

            document.close();
            Mail guiQuaMail= new Mail();
            guiQuaMail.sendEmailWithAttachment(filePath);
            JOptionPane.showMessageDialog(null,
                    "Xuất hóa đơn PDF thành công!\n" +
                            "Tệp: " + filePath,
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi xuất hóa đơn PDF!\n" + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private static void addInfoRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(0);
        labelCell.setPadding(5);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBorder(0);
        valueCell.setPadding(5);
        table.addCell(valueCell);
    }

    private static void addTableCell(PdfPTable table, String text, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(6);
        cell.setBorderWidth(0.5f);
        cell.setBorderColor(BaseColor.GRAY);
        table.addCell(cell);
    }

    private static void addSummaryRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(0);
        labelCell.setPadding(3);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        valueCell.setBorder(0);
        valueCell.setPadding(3);
        table.addCell(valueCell);
    }
}