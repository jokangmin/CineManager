package movie.service;

import java.util.Scanner;

import cineManager.dao.MovieDAO;

public class DeleteMovie implements Movie{
	private Scanner scan = new Scanner(System.in);
	private String userId; // 로그인한 사용자의 ID

    public DeleteMovie(String userId) {
        this.userId = userId;
    }
	
	@Override
	public void execute() {
		MovieDAO movieDAO = MovieDAO.getInstance();
		
		System.out.print("삭제할 영화 제목 : ");
		String title = scan.nextLine();
		if(movieDAO.titleCheck(title)) {
			movieDAO.selectTitleSummary(title, userId);
		}
		else {
			System.out.println("영화 제목이 올바르지 않습니다.\n");
			return;
		}
		
		System.out.print("삭제할 영화 번호 : ");
		int code = scan.nextInt();
		scan.nextLine(); // 개행 문자 제거
		
		if(movieDAO.codeCheck(code) && movieDAO.isMovieOwnedByUser(code, userId)) {
			movieDAO.deleteMovie(title, code, userId);// 사용자 ID를 확인하며 영화 삭제
		}
		else {
			System.out.println("올바른 code 값이 아닙니다.\n");
			return;
		}
	}
}