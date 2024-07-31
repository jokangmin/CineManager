package movie.service;
import java.util.InputMismatchException;
import java.util.Scanner;

import cineManager.bean.MovieDTO;
import cineManager.dao.MovieDAO;


public class AddMovie implements Movie{
	private Scanner scan = new Scanner(System.in);
	
	@Override
	public void execute() {
		MovieDAO movieDAO = MovieDAO.getInstance();

		int code = 0;
		boolean validCode = false;
		
		while (!validCode) {
			System.out.print("영화번호 입력(6자리) : ");
	        try {
	        	code = scan.nextInt();
	        	scan.nextLine(); // 개행 문자 제거
	        	
	        	// DB에 존재하는 code 인지 확인
	        	if (movieDAO.codeCheck(code)) {
	        		System.out.println("이미 등록된 번호입니다. 다른 번호를 입력하세요.");
	        	}
	        	else {
                    validCode = true; // 존재하지 않는 유효한 영화 번호 입력받으면 while문 종료
                }
	        } catch (InputMismatchException e) {
	            System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
	            scan.nextLine(); // 잘못된 입력 처리 후 개행 문자 제거
	        }
		}
		System.out.print("영화제목 입력 : ");
		String title  = scan.nextLine();
		System.out.print("영화감독 입력 : ");
		String director = scan.nextLine();
		System.out.print("영화장르 입력 : ");
		String genre = scan.nextLine();
        System.out.print("영화개봉일 입력 (형식: yyyy-MM-dd) : ");
        String releaseDate = scan.nextLine();
		System.out.print("영화줄거리 입력 : ");
		String synopsis = scan.nextLine();


		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setCode(code);
		movieDTO.setTitle(title);		
		movieDTO.setDirector(director);
		movieDTO.setGenre(genre);
		movieDTO.setReleaseDate(releaseDate);
		movieDTO.setSynopsis(synopsis);


		int su = movieDAO.add(movieDTO);
		System.out.println(su + " 개의 영화가 추가되었습니다..\n");

		//	System.out.prinstln("영화가 추가되었습니다.");
	}

}
