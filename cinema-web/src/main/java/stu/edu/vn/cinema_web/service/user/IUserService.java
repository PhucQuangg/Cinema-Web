package stu.edu.vn.cinema_web.service.user;

import org.springframework.stereotype.Service;
import stu.edu.vn.cinema_web.entity.User;

@Service
public interface IUserService {
    User findByEmail(String email);
    Boolean createUser(User user);
}
