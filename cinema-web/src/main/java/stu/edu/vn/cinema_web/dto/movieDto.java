package stu.edu.vn.cinema_web.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class movieDto {
    private Integer id;
    private String title;
    private String description;
    private String base64Image;
    private String trailerUrl;
    private String duration;
    private String formattedDuration;
    private String releaseDate;
    private String starCast;
    private String venue;
    private String category;

}

