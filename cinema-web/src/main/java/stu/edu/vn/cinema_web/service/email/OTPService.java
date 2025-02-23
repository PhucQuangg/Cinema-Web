package stu.edu.vn.cinema_web.service.email;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;
import stu.edu.vn.cinema_web.entity.User;

@Service
public class OTPService {
    private final Map<String, String> otpStorage = new HashMap<>();
    private final Map<String, User> temporaryUsers = new HashMap<>();

    // Tạo OTP ngẫu nhiên
    public String generateOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(1000000));
        otpStorage.put(email, otp);
        return otp;
    }

    // Kiểm tra OTP
    public boolean verifyOtp(String email, String otp) {
        return otp.equals(otpStorage.get(email));
    }

    // Lưu user tạm trước khi xác nhận OTP
    public void saveTemporaryUser(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setVerified(false);
        temporaryUsers.put(email, user);
    }

    // Lấy user tạm
    public Optional<User> getTemporaryUser(String email) {
        return Optional.ofNullable(temporaryUsers.get(email));
    }

    // Xóa user tạm sau khi xác nhận OTP
    public void removeTemporaryUser(String email) {
        temporaryUsers.remove(email);
        otpStorage.remove(email);
    }
}

