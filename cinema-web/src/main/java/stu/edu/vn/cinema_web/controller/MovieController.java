package stu.edu.vn.cinema_web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import stu.edu.vn.cinema_web.dto.movieDto;
import stu.edu.vn.cinema_web.service.movie.MovieService;

import java.util.List;

@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;


    @GetMapping("/movies")
    public String getMovies(Model model) {
        List<movieDto> movies = movieService.getAllMovies();
        List<movieDto> top4Movies = movieService.getLimitedMovies(4);
        List<movieDto> adultMovies = movieService.getMoviesByCate("Adult");
        List<movieDto> kidsMovies = movieService.getMoviesByCate("Kids");
        model.addAttribute("movies", movies);
        model.addAttribute("top4Movies", top4Movies);
        model.addAttribute("adultMovies", adultMovies);
        model.addAttribute("kidsMovies", kidsMovies);
        return "home/movies";
    }

}
