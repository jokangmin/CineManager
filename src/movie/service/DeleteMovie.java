package movie.service;

import java.util.Scanner;

import cineManager.dao.MovieDAO;

public class DeleteMovie implements Movie{
	private Scanner scan = new Scanner(System.in);
	
	@Override
	public void execute() {
//		Scanner scan = new Scanner(System.in);
		MovieDAO movieDAO = MovieDAO.getInstance();
		
		System.out.print("삭제할 영화 제목 : ");
		String title = scan.nextLine();
		movieDAO.selectTitleSummary(title);
		
		System.out.print("삭제할 영화 번호 : ");
		int code = scan.nextInt();
		scan.nextLine(); // 개행 문자 제거
		
		if(movieDAO.codeCheck(code)) {
			movieDAO.deleteMovie(title, code);
		}
		else {
			System.out.println("올바른 code 값이 아닙니다.");
		}
	}
}