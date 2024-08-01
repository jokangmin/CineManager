package review.service;

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
