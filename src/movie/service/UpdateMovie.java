package movie.service;

import java.util.InputMismatchException;
import java.util.Scanner;
import cineManager.dao.MovieDAO;
import java.util.regex.Pattern;

public class UpdateMovie implements Movie {
	private Scanner scan = new Scanner(System.in);
	private String userId; // 로그인한 사용자의 ID	
	int code;
	
	String dateFormat = "\\d{4}-\\d{2}-\\d{2}";
    Pattern pattern = Pattern.compile(dateFormat);
	
	public UpdateMovie(String userId) {
		this.userId = userId;
	}

	@Override
	public void execute() {
		MovieDAO movieDAO = MovieDAO.getInstance();
		System.out.print("수정할 영화 제목 : ");
		String title = scan.nextLine();

		// 영화 제목 존재 여부 확인
	    if (!movieDAO.titleCheck(title, userId)) {
	        System.out.println("영화 제목 : " + title + " 이(가) 존재하지 않습니다.\n");
	        return; // 영화 제목이 존재하지 않으면 수정 작업을 종료
	    }

	    // 영화 제목이 존재하는 경우, 해당 제목의 영화 목록을 출력
	    movieDAO.selectTitleSummary(title, userId);
	    
	    int code;
		try {
			System.out.print("수정할 영화 번호 : ");
			code = scan.nextInt();
			scan.nextLine();
		} catch(InputMismatchException e) {
			System.out.println("6자리 형태의 '숫자'로만 입력해주세요."); //숫자 값만 입력 받게 
			return;
		}

		if (movieDAO.codeCheck(code) && movieDAO.isMovieOwnedByUser(code, userId)) {
			String get_title = movieDAO.getTitle(code, userId); // 8/2 17:11 조강민 추가
			System.out.println(code + "\t" + get_title + " 을(를) 수정합니다.\n");

			// 영화의 현재 정보를 출력하기 위해 selectDetail 메소드 사용
			movieDAO.selectDetail(code, userId);

			String updateItem = "";
			boolean validInput = false;

			while (!validInput) {
				System.out.print("수정할 항목 (제목/감독/장르/개봉일/줄거리) : ");
				updateItem = scan.nextLine();
				switch (updateItem) {
				case "제목":
				case "감독":
				case "장르":
				case "개봉일":
				case "줄거리":
					validInput = true;
					break;
				default:
					System.out.println("잘못된 항목입니다. 다시 입력해 주세요.\n");
					break;
				}
			}

			String updateValue = "";
			switch (updateItem) {
			case "제목":
				System.out.print("새로운 제목 : ");
				updateValue = scan.nextLine();
				break;
			case "감독":
				System.out.print("새로운 감독 : ");
				updateValue = scan.nextLine();
				break;
			case "장르":
				System.out.print("새로운 장르 : ");
				updateValue = scan.nextLine();
				break;
			case "개봉일":
				while (true) {
		            System.out.print("새로운 개봉일 (YYYY-MM-DD) : ");
		            updateValue = scan.nextLine();
		            
		            if (pattern.matcher(updateValue).matches()) {
		            	String[] parts = updateValue.split("-");
		                int year = Integer.parseInt(parts[0]);
		                int month = Integer.parseInt(parts[1]);
		                int day = Integer.parseInt(parts[2]);

		                if (year >= 1000 && year <= 9999 && month >= 1 && month <= 12 && day >= 1 && day <= 31) {
		                    break;
		                } else {
		                    System.out.println("잘못된 형식입니다. 다시 입력해주세요.");
		                }
		            }
		            else {
		                System.out.println("잘못된 형식입니다. 다시 입력해주세요.");
		            }
		        }
				break;
			case "줄거리":
				System.out.print("새로운 줄거리 : ");
				updateValue = scan.nextLine();
				break;
			default:
				System.out.println("잘못된 항목입니다.\n");
				return;
			}

			int result = movieDAO.updateMovie(updateItem, updateValue, code, get_title, userId);
			if (result > 0) {
				System.out.println("영화 '" + get_title + "' 이(가) 수정되었습니다.\n");
			} else {
				System.out.println("영화 수정에 실패했습니다.\n");
			}
		} else {
			System.out.println("올바른 code 값이 아닙니다.\n");
		}
	}
}