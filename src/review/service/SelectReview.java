package review.service;

import java.util.Scanner;

import cineManager.dao.MovieDAO;
import cineManager.dao.ReviewDAO;

public class SelectReview implements Review {//8/2 11:19 강민 수정
	Scanner scan = new Scanner(System.in);
	private String userId;
	
	public SelectReview(String userId) {
        this.userId = userId;
    }
	@Override
	public void execute() {
		ReviewDAO reviewDAO = ReviewDAO.getInstance();
		MovieDAO movieDAO = MovieDAO.getInstance();
		reviewDAO.selectReview();
		System.out.println("********");
		System.out.println("1.검색");
		System.out.println("2.뒤로 가기");
		System.out.println("********");
		System.out.print("번호 선택 : ");
		int num = scan.nextInt();
		if(num == 1) {
			System.out.print("후기 볼 영화의 제목 : ");
			scan.nextLine();
			String title  = scan.nextLine();
			if(movieDAO.titleCheck(title)) {
				reviewDAO.selectTitleReview(title);
			}
			else {
				System.out.println("없는 제목입니다.");
			}
		}
		else return;
	}


}
	



