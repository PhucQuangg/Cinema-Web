package stu.edu.vn.cinema_web.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movies")
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "image_url")
    private byte[] imageUrl;
    @Column(name = "trailer_url")
    private String trailerUrl;
    @Column(name = "duration")
    private String duration;
    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "venue")
    private String venue;

    @Column(name = "star_cast")
    private String starCast;

    @Column(name = "category")
    private String category;

    public Movie() {
    }

    public Movie(Integer id, String title, String description, byte[] imageUrl, String trailerUrl, String duration, String releaseDate, String venue, String starCast, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.trailerUrl = trailerUrl;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.venue = venue;
        this.starCast = starCast;
        this.category = category;
    }
}
