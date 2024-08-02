package cineManager.bean;

import lombok.Data;

@Data
public class MovieDTO {
    private int code; 
    private String title;
    private String director;
    private String genre;
    private String releaseDate;
    private String synopsis;
    private String watched; // 시청 여부
    private String userId; // 로그인한 id
}
