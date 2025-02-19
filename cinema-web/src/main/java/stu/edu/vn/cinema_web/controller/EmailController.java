package stu.edu.vn.cinema_web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import stu.edu.vn.cinema_web.entity.User;
import stu.edu.vn.cinema_web.repository.UserRepository;
import stu.edu.vn.cinema_web.service.EmailService;
import stu.edu.vn.cinema_web.service.OTPService;
import stu.edu.vn.cinema_web.utils.JwtUtil;

import jakarta.mail.MessagingException;

import java.util.HashMap;
import java.util.Map;
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

    @PostMapping("/send-otp")
    @ResponseBody
    public String sendOtp(@RequestParam String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            return "Email đã tồn tại!";
        }

        String otp = otpService.generateOtp(email);
        emailService.sendOtpEmail(email, otp);
        return "Mã OTP đã được gửi!";
    }
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Map<String, String>> register(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String otp) {

        Map<String, String> response = new HashMap<>();

        // Kiểm tra OTP
        if (!otpService.verifyOtp(email, otp)) {
            response.put("status", "error");
            response.put("message", "Mã OTP không hợp lệ!");
            return ResponseEntity.badRequest().body(response);
        }

        // Kiểm tra email đã tồn tại
        if (userRepository.findByEmail(email).isPresent()) {
            response.put("status", "error");
            response.put("message", "Email đã tồn tại!");
            return ResponseEntity.badRequest().body(response);
        }

        // Tạo tài khoản
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setVerified(true);
        userRepository.save(user);

        otpService.removeTemporaryUser(email); // Xóa OTP sau khi đăng ký

        response.put("status", "success");
        response.put("message", "Đăng ký thành công! Bạn có thể đăng nhập.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "home/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.isVerified()) {
                return "redirect:/auth/login?error=unverified"; // Chuyển hướng về trang login kèm lỗi
            }
            if (passwordEncoder.matches(password, user.getPassword())) {
                return "redirect:/home"; // Nếu đúng, chuyển về index.html
            }
        }
        return "redirect:/auth/login?error=invalid"; // Lỗi email hoặc mật khẩu
    }


}
