package movie.service;

import java.util.Scanner;

import cineManager.dao.MovieDAO;


public class SelectMovie implements Movie{
	@Override
	public void execute() {
		int menu_num = 0;
		Scanner scan = new Scanner(System.in);
		MovieDAO movieDAO = MovieDAO.getInstance();
		while(true) {
			System.out.println("-----------------------");
			System.out.println("1. 전체 영화 목록 조회 ");
			System.out.println("2. 검색 영화 상세 정보 조회 ");
			System.out.println("3. 뒤로가기 ");
			System.out.println("-----------------------");
			System.out.print("번호 선택 : ");
			menu_num = scan.nextInt();
			if(menu_num == 3) break;
			else if(menu_num == 1) movieDAO.selectAll();
			else if(menu_num == 2) {
				System.out.print("영화 제목 검색 : ");
				String title = scan.nextLine();
				scan.next();
				movieDAO.selectTitleMovie(title);
				System.out.print("영화 번호 입력 : ");
				int code = scan.nextInt();
				movieDAO.selectDetail(code);
			}
		}
	}
}