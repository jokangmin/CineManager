package movie.service;

import java.util.Scanner;
import cineManager.dao.MovieDAO;

public class UpdateMovie implements Movie{
	private Scanner scan = new Scanner(System.in);
	
	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);
		MovieDAO movieDAO = MovieDAO.getInstance();
		System.out.println();
		System.out.print("수정할 영화 제목 : ");
		String title = scan.nextLine();
		movieDAO.selectTitleMovie(title);
		System.out.print("수정할 영화 번호 : ");
		int code = scan.nextInt();
		scan.nextLine();
		if(movieDAO.codeCheck(code)) {
			movieDAO.updateMovie(title, code);
		}
		else {
			System.out.println("올바른 code 값이 아닙니다.");
		}
	}
}
