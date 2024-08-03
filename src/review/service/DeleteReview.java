package review.service;

import java.util.Scanner;

import cineManager.dao.MovieDAO;
import cineManager.dao.ReviewDAO;

public class DeleteReview implements Review{
	Scanner scan = new Scanner(System.in);
	private String userId;

	public DeleteReview(String userId) {
		this.userId = userId;
	}

	@Override
	public void execute() {

		ReviewDAO reviewDAO = ReviewDAO.getInstance();
		MovieDAO movieDAO = MovieDAO.getInstance();

		while (true) {
			System.out.print("작성한 후기 목록을 확인하시겠습니까? (y or n) : ");
			String check = scan.nextLine().trim().toLowerCase();
			if (check.equals("y")) {
				System.out.println();
				reviewDAO.selectList(userId);
				break;
			}
			else if (check.equals("n")) { 
				break;
			}
			else { 
				System.out.println("잘못된 입력입니다. 'y' 또는 'n'만 입력해주세요.\n");
			}
		}

		System.out.print("리뷰 삭제할 영화 제목 : ");
		String title = scan.nextLine();

		if(movieDAO.titleCheck(title, userId)) {
			movieDAO.selectTitleSummary(title, userId);
		}
		else {
			System.out.println("영화 제목 : " + title + " 이(가) 존재하지 않습니다.\n");
			return;
		}
		System.out.print("리뷰 삭제할 영화 등록번호 : ");
		int code = scan.nextInt();
		if(movieDAO.codeCheck(code) && movieDAO.isMovieOwnedByUser(code, userId)) {
			boolean reviewExists = reviewDAO.checkReviewExists(code, userId);
			if(reviewExists) {
				String get_title = movieDAO.getTitle(code, userId);
				reviewDAO.deleteReview(code,userId);
				System.out.println("영화\t" + get_title + " ( code : " + code +" )리뷰를 삭제했습니다.\n");
				return;
			}
			else {
				System.out.println("후기가 작성되지 않은 영화이므로 삭제가 불가합니다.\n");
				return;
			}
		}
		else {
			System.out.println("올바른 code 값이 아닙니다.\n");
			return;
		}
	}
}
