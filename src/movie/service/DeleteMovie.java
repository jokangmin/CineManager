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

		while (true) {
			System.out.print("등록한 영화 목록을 확인하시겠습니까? (y or n) : ");
			String check = scan.nextLine().trim().toLowerCase();
			if (check.equals("y")) {
				System.out.println();
				movieDAO.selectAll(userId);
				break;
			}
			else if (check.equals("n")) { 
				break;
			}
			else { 
				System.out.println("잘못된 입력입니다. 'y' 또는 'n'만 입력해주세요.\n");
			}
		}

		System.out.print("삭제할 영화 제목 : ");
		String title = scan.nextLine();
		if(movieDAO.titleCheck(title, userId)) {
			movieDAO.selectTitleSummary(title, userId);
		}
		else {
			System.out.println("영화 제목 : " + title + " 이(가) 존재하지 않습니다.\n");
			return;
		}

		System.out.print("삭제할 영화 번호 : ");
		int code = scan.nextInt();
		scan.nextLine(); // 개행 문자 제거

		if(movieDAO.codeCheck(code) && movieDAO.isMovieOwnedByUser(code, userId)) {
			String get_title = movieDAO.getTitle(code, userId);
			movieDAO.deleteMovie(title, code, userId);// 사용자 ID를 확인하며 영화 삭제
			System.out.println(code + "\t" + get_title + " 을(를) 삭제했습니다.\n");
		}
		else {
			System.out.println("올바른 code 값이 아닙니다.\n");
			return;
		}
	}
}