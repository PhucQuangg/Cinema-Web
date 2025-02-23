package stu.edu.vn.cinema_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stu.edu.vn.cinema_web.entity.Movie;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query(value = "SELECT * FROM movies ORDER BY id DESC LIMIT ?1", nativeQuery = true)
    List<Movie> findTopMovies(int limit);
    List<Movie> findByCategory(String category);
}
