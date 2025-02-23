package stu.edu.vn.cinema_web.service.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stu.edu.vn.cinema_web.dto.movieDto;
import stu.edu.vn.cinema_web.entity.Movie;
import stu.edu.vn.cinema_web.repository.MovieRepository;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService implements IMovieService {
    @Autowired
    private MovieRepository movieRepository;


    public List<movieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(movie -> {
            String base64Image = Base64.getEncoder().encodeToString(movie.getImageUrl());
            String formattedDuration = formatDuration(movie.getDuration());
            return new movieDto(movie.getId(), movie.getTitle(), movie.getDescription(), base64Image, movie.getTrailerUrl(), movie.getDuration(), formattedDuration,movie.getReleaseDate(), movie.getStarCast(), movie.getVenue(),movie.getCategory());
        }).collect(Collectors.toList());
    }

    @Override
    public List<movieDto> getMoviesByCate(String category) {
        List<Movie> movies = movieRepository.findByCategory(category);
        return movies.stream().map(movie -> {
            String base64Image = Base64.getEncoder().encodeToString(movie.getImageUrl());
            String formattedDuration = formatDuration(movie.getDuration());
            return new movieDto(movie.getId(), movie.getTitle(), movie.getDescription(), base64Image, movie.getTrailerUrl(), movie.getDuration(), formattedDuration,movie.getReleaseDate(), movie.getStarCast(), movie.getVenue(),movie.getCategory());
        }).collect(Collectors.toList());
    }

    public List<movieDto> getLimitedMovies(int limit) {
        List<Movie> movies = movieRepository.findTopMovies(limit);
        return movies.stream().map(movie -> {
            String base64Image = Base64.getEncoder().encodeToString(movie.getImageUrl());
            String formattedDuration = formatDuration(movie.getDuration());
            return new movieDto(movie.getId(), movie.getTitle(), movie.getDescription(), base64Image, movie.getTrailerUrl(), movie.getDuration(), formattedDuration,movie.getReleaseDate(), movie.getStarCast(), movie.getVenue(),movie.getCategory());
        }).collect(Collectors.toList());
    }

    private String formatDuration(String duration) {
        int totalMinutes = Integer.parseInt(duration);
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        return hours + " Hr " + minutes + " min";
    }
}