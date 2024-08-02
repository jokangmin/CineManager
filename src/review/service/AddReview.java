package review.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

import cineManager.bean.ReviewDTO;
import cineManager.dao.MovieDAO;
import cineManager.dao.ReviewDAO;

public class AddReview implements Review{
	private Scanner scan = new Scanner(System.in);
	private String userId;
	
	public AddReview(String userId) {
        this.userId = userId;
    }
	@Override
	public void execute() {
		MovieDAO movieDAO = MovieDAO.getInstance();
        ReviewDAO reviewDAO = ReviewDAO.getInstance();
		
        movieDAO.selectAll(userId); // 전체 영화 출력
        
        System.out.print("후기 작성할 영화 등록번호 : ");
        int movieCode = 0;
        
        try {
            movieCode = scan.nextInt();
            scan.nextLine(); // 개행 문자 제거
        } catch (InputMismatchException e) {
            System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
            scan.nextLine(); // 잘못된 입력 처리 후 개행 문자 제거
            return;
        }
        
     // 영화 개봉 날짜 확인
        String releaseDateStr = movieDAO.getReleaseDate(movieCode);
        if (releaseDateStr == null) {
            System.out.println("영화가 존재하지 않습니다.");
            return;
        }
        
        // 개봉일 문자열을 Date 객체로 변환
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date releaseDate = null;
        try {
            releaseDate = sdf.parse(releaseDateStr);
        } catch (ParseException e) {
            System.out.println("개봉일을 처리하는 데 문제가 발생했습니다.");
            return;
        }
        
        // 현재 날짜
        Date currentDate = new Date(movieCode);
        
        // 개봉 날짜가 현재 날짜보다 늦으면 예외 처리
        if (releaseDate.after(currentDate)) {
            System.out.println("후기를 등록할 수 없습니다. 영화 개봉 날짜가 현재 날짜보다 늦습니다.\n");
            return;
        }
        
        System.out.print("후기 작성 : ");
        String reviewText = scan.nextLine();
        
     // 영화의 watched 컬럼을 'Y'로 업데이트
        movieDAO.updateWatchedStatus(movieCode, userId, 'Y');
        
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setMovieCode(movieCode);
        reviewDTO.setUserId(userId);
        reviewDTO.setReview(reviewText);
        reviewDTO.setLogDate(new java.sql.Date(System.currentTimeMillis()));

        int result = reviewDAO.addReview(reviewDTO);
        if (result > 0) {
            System.out.println("후기가 성공적으로 작성되었습니다.");
        } else {
            System.out.println("후기 작성에 실패했습니다.");
        }
		
	}

}
