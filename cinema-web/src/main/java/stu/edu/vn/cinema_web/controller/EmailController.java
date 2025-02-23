package stu.edu.vn.cinema_web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import stu.edu.vn.cinema_web.entity.User;
import stu.edu.vn.cinema_web.service.email.EmailService;
import stu.edu.vn.cinema_web.service.email.OTPService;
import stu.edu.vn.cinema_web.service.user.UserService;
import stu.edu.vn.cinema_web.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class EmailController {

    @Autowired
    private UserService userService;

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
        if (userService.findByEmail(email) != null) {
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
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String otp) {

        Map<String, String> response = new HashMap<>();

        if (!otpService.verifyOtp(email, otp)) {
            response.put("status", "error");
            response.put("message", "Mã OTP không hợp lệ!");
            return ResponseEntity.badRequest().body(response);
        }

        if (userService.findByEmail(email) != null) {
            response.put("status", "error");
            response.put("message", "Email đã tồn tại!");
            return ResponseEntity.badRequest().body(response);
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setVerified(true);
        userService.createUser(user);

        otpService.removeTemporaryUser(email);

        response.put("status", "success");
        response.put("message", "Đăng ký thành công! Bạn có thể đăng nhập.");
        return ResponseEntity.ok(response);
    }




}
