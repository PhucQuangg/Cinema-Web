package stu.edu.vn.cinema_web.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}") // Email người gửi từ cấu hình
    private String senderEmail;

    // Lưu OTP tạm thời
    private final Map<String, String> otpStorage = new HashMap<>();

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Hàm tạo mã OTP 6 số
    private String generateOtp() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    // Gửi email chứa mã OTP
    public void sendOtpEmail(String recipientEmail) throws MessagingException {
        String otp = generateOtp();
        otpStorage.put(recipientEmail, otp); // Lưu OTP để xác minh sau này

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        String emailContent = "<h3>Chào bạn,</h3>"
                + "<p>Mã OTP của bạn là: <b>" + otp + "</b></p>"
                + "<p>Vui lòng nhập mã này để hoàn tất đăng ký.</p>";

        helper.setTo(recipientEmail);
        helper.setSubject("Mã OTP xác nhận đăng ký");
        helper.setFrom(senderEmail);
        helper.setText(emailContent, true);

        mailSender.send(message);
    }

    // Kiểm tra OTP có hợp lệ không
    public boolean verifyOtp(String email, String otp) {
        return otpStorage.containsKey(email) && otpStorage.get(email).equals(otp);
    }

    // Xóa OTP sau khi xác thực thành công
    public void removeOtp(String email) {
        otpStorage.remove(email);
    }
}


