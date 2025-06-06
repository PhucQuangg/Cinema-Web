package com.example.cinema_back_end.apis;

import com.example.cinema_back_end.dtos.MovieDTO;
import com.example.cinema_back_end.entities.Movie;
import com.example.cinema_back_end.repositories.IMovieRepository;
import com.example.cinema_back_end.services.IMovieService;
import com.example.cinema_back_end.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/movies", produces = "application/json")
@CrossOrigin(value = "*")

public class MovieApi {
    @Autowired
    private MovieService movieService;

    @Autowired
    private IMovieRepository movieRepository;

    @GetMapping("/showing")
    public ResponseEntity<List<MovieDTO>> findAllShowingMovies(){
        return new ResponseEntity<>(movieService.findAllShowingMovies(), HttpStatus.OK);
    }
    @GetMapping("/coming")
    public ResponseEntity<List<MovieDTO>> findAllComingMovies(){
        return new ResponseEntity<>(movieService.findAllComingMovies(), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies(){
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }
    @GetMapping("/details")
    public MovieDTO getMovieById(@RequestParam Integer movieId){
        return movieService.getById(movieId);
    }

    @GetMapping("/search")
    public List<MovieDTO> findAllShowingMoviesByName(@RequestParam String name,@RequestParam(required=false) Integer status){
        return movieService.findAllMoviesByNameAndStatus(name,status);
    }

    @PostMapping
    public void updateMovie(@RequestBody Movie movie){
        movieRepository.save(movie);
    }
}
