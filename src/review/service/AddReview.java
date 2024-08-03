package review.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

import cineManager.bean.ReviewDTO;
import cineManager.dao.MovieDAO;
import cineManager.dao.ReviewDAO;
import movie.service.AddMovie;

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
        int movieCode = 0;
        
        boolean exists = reviewDAO.checkMovieExists(userId); //작성한 영화가 있는지 확인 
        if(exists) movieDAO.selectAll(userId); // 영화가 있으면 전체 영화 출력
        else {									// 영화가 없으면 등록 이동 / 뒤로가기 구현 //강민 8/3
        	System.out.println("후기를 작성할 영화가 없습니다.");
        	while(true) {
	        	System.out.print("영화를 등록하시겠습니까? (y or n)");
	        	String state = scan.nextLine().trim().toLowerCase(); //대문자 소문자 상관없이 입력
	        	if(state.equals("y")) {
	        		System.out.println("영화 등록으로 이동합니다.");
	        		new AddMovie(userId).execute();
	        		return;
	        	}
	        	else if(state.equals("n")) {
	        		System.out.println("메뉴로 돌아갑니다.");
	        		return;
	        	}
	        	else {
	        		System.out.println("알맞은 형식으로 입력해주세요.");
	        	}
        	}
        }
        
        while(true) { //등록번호가 잘못 입력했을 때 나가지 않고 while 문 돌리기  // 강민 8/3
	        System.out.print("후기 작성할 영화 등록번호 : ");
	        
	        try {
	            movieCode = scan.nextInt();
	            scan.nextLine(); // 개행 문자 제거
	            break;
	        } catch (InputMismatchException e) {
	            System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.\n");
	            scan.nextLine(); // 잘못된 입력 처리 후 개행 문자 제거
	        }
        }
        
        // 영화 개봉 날짜 확인
        String releaseDateStr = movieDAO.getReleaseDate(movieCode);
        if (releaseDateStr == null) {
            System.out.println("영화가 존재하지 않습니다.\n");
            return;
        }
        
        // 개봉일 문자열을 Date 객체로 변환
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date releaseDate = null;
        try {
            releaseDate = sdf.parse(releaseDateStr);
        } catch (ParseException e) {
            System.out.println("개봉일을 처리하는 데 문제가 발생했습니다.\n");
            return;
        }
        
        // 현재 날짜
        Date currentDate = new Date(System.currentTimeMillis());
        
        // 개봉 날짜가 현재 날짜보다 늦으면 예외 처리
        if (releaseDate.after(currentDate)) {
            System.out.println("후기를 등록할 수 없습니다. 영화 개봉 날짜가 현재 날짜보다 늦습니다.\n");
            return;
        }
        
        // 이미 후기가 등록된 영화인지 확인
        boolean reviewExists = reviewDAO.checkReviewExists(movieCode, userId);
        
		if (reviewExists) { // 이미 후기가 등록된 영화일 경우
			while (true) {
				System.out.print("이미 후기가 등록된 영화입니다. 후기를 수정하시겠습니까? (y or n) : ");
				String input = scan.nextLine().trim().toLowerCase();
				
				
				if(input.equals("y")) {
					new UpdateReview(userId).execute();
					return; // 후기 수정 후 AddReview 종료
				}
				else if (input.equals("n")) {
					System.out.println();
					return; // ReviewMain 으로 돌아가기
				}
				else {
					System.out.println("잘못된 입력입니다. 'y' 또는 'n'만 입력해주세요.\n");
				}
			}
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
            System.out.println("후기가 성공적으로 작성되었습니다.\n");
        } else {
            System.out.println("후기 작성에 실패했습니다.\n");
        }
		
	}

}
