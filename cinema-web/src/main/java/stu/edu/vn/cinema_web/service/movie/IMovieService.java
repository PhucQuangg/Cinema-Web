package stu.edu.vn.cinema_web.service.movie;

import stu.edu.vn.cinema_web.dto.movieDto;

import java.util.List;

public interface IMovieService {
    List<movieDto> getLimitedMovies(int limit);
    List<movieDto> getAllMovies();
    List<movieDto> getMoviesByCate(String category);
}
