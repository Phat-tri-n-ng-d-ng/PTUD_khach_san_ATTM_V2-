package xuatHoaDon;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.io.File;

public class Mail {

    public void sendEmailWithAttachment(String duongDanFile) {
        // Thông tin người gửi
        String fromUser = "manhkl1025@gmail.com";
        String fromUserPassword = "ahdr eocb xecr kbek"; // App Password
        String emailHost = "smtp.gmail.com";

        // Người nhận
        String[] emailRecipients = {"theanh040905@gmail.com"};
        String emailSubject = "Test Mail with PDF";
        String emailBody = "Hello, this email contains a PDF attachment.";


        // File PDF cần gửi
        String pdfFilePath = duongDanFile; // <-- đổi đường dẫn tới file PDF của bạn

        // Cấu hình Properties
        Properties properties = new Properties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // Tạo session
        Session session = Session.getInstance(properties, null);

        try {
            // Tạo message
            MimeMessage message = new MimeMessage(session);
            for (String recipient : emailRecipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }
            message.setSubject(emailSubject);

            // Body chính
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(emailBody, "text/html; charset=utf-8");

            // File đính kèm
            MimeBodyPart attachmentPart = new MimeBodyPart();
            File file = new File(pdfFilePath);
            if (!file.exists()) {
                System.out.println("File PDF không tồn tại!");
                return;
            }
            DataSource source = new FileDataSource(file);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(file.getName());

            // Gom cả body và attachment
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            // Gửi email
            Transport transport = session.getTransport("smtp");
            transport.connect(emailHost, fromUser, fromUserPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            System.out.println("Email with PDF sent successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}