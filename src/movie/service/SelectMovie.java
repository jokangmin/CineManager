package movie.service;

import java.util.Scanner;
import cineManager.dao.MovieDAO;


public class SelectMovie implements Movie{
	private Scanner scan = new Scanner(System.in);
	
	@Override
	public void execute() {
		int menu_num = 0;
		MovieDAO movieDAO = MovieDAO.getInstance();
		while(true) {
			System.out.println("-----------------------");
			System.out.println("1. 전체 영화 목록 조회 ");
			System.out.println("2. 검색 영화 상세 정보 조회 ");
			System.out.println("3. 뒤로가기 ");
			System.out.println("-----------------------");
			System.out.print("번호 선택 : ");
			try {
                menu_num = scan.nextInt();
                scan.nextLine(); // 개행 문자 제거
                System.out.println();

                if (menu_num == 3) break;
                else if (menu_num == 1) {
                    movieDAO.selectAll();
                } else if (menu_num == 2) {
                    System.out.print("영화 제목 검색 : ");
                    String title = scan.nextLine();
                    System.out.println();
                    if(movieDAO.titleCheck(title)) {
            			movieDAO.selectTitleSummary(title);
            		}
            		else {
            			System.out.println("영화 제목 : " + title + "이(가) 존재하지 않습니다.");
            			return;
            		}
                    System.out.print("영화 번호 입력 : ");
                    int code = scan.nextInt();
                    scan.nextLine(); // 개행 문자 제거
                    System.out.println();
                    movieDAO.selectDetail(code);
                } else {
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
                }
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                scan.nextLine(); // 잘못된 입력 처리 후 개행 문자 제거
            }
		}
	}
}