package stu.edu.vn.cinema_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stu.edu.vn.cinema_web.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
