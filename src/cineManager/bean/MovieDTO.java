package cineManager.bean;

import java.util.Date;
import lombok.Data;

@Data
public class MovieDTO {
    private int code; 
    private String title;
    private String director;
    private String genre;
    private String releaseDate;
    private String synopsis;
	
}
