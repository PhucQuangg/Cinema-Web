package stu.edu.vn.cinema_web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import stu.edu.vn.cinema_web.entity.User;
import stu.edu.vn.cinema_web.repository.UserRepository;
import stu.edu.vn.cinema_web.service.EmailService;
import stu.edu.vn.cinema_web.service.OTPService;
import stu.edu.vn.cinema_web.utils.JwtUtil;

import jakarta.mail.MessagingException;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class EmailController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Đăng ký tài khoản & gửi OTP
    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestParam String email, @RequestParam String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            return "Email đã tồn tại!";
        }

        otpService.saveTemporaryUser(email, password);

        try {
            emailService.sendOtpEmail(email);
            return "Mã OTP đã được gửi đến email của bạn!";
        } catch (MessagingException e) {
            return "Lỗi khi gửi OTP!";
        }
    }

    // Xác nhận OTP
    @PostMapping("/verify-otp")
    @ResponseBody
    public String verifyOtp(@RequestParam String email, @RequestParam String otp) {
        Optional<User> userOptional = otpService.getTemporaryUser(email);
        if (userOptional.isPresent() && emailService.verifyOtp(email, otp)) {
            User user = userOptional.get();
            user.setVerified(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            emailService.removeOtp(email); // Xóa OTP sau khi xác thực thành công
            return "Xác thực thành công! Bạn có thể đăng nhập.";
        }
        return "OTP không hợp lệ!";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.isVerified()) return "Tài khoản chưa xác thực OTP!";
            if (passwordEncoder.matches(password, user.getPassword())) {
                return "Token: " + jwtUtil.generateToken(email);
            }
        }
        return "Sai email hoặc mật khẩu!";
    }
}
