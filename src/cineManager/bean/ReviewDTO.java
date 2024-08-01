package cineManager.bean;

import java.sql.Date;
import lombok.Data;

@Data
public class ReviewDTO {
    private int reviewId;
    private int movieCode;
    private String userId;
    private String review;
    private Date logDate;
}
