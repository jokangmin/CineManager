package movie.service;

import java.util.Scanner;

import cineManager.dao.MovieDAO;

public class DeleteMovie implements Movie{
	Scanner scan = new Scanner(System.in);
	@Override
	public void execute() {
		MovieDAO movieDAO = MovieDAO.getInstance();
		System.out.print("삭제할 영화 제목 : ");
		String title = scan.nextLine();
		movieDAO.selectTitleMovie(title);
		System.out.print("삭제할 영화 번호 : ");
		int code = scan.nextInt();
		if(movieDAO.codeCheck(code)) {
			movieDAO.deleteMovie(title, code);
		}
		else {
			System.out.println("올바른 code 값이 아닙니다.");
		}
	}
}