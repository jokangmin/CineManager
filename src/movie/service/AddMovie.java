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

		System.out.print("영화번호 입력 : ");
		int code = 0;
        try {
        	code = scan.nextInt();
        	scan.nextLine(); // 개행 문자 제거
        } catch (InputMismatchException e) {
            System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
            scan.nextLine(); // 잘못된 입력 처리 후 개행 문자 제거
            return; // 입력 오류로 인해 추가 작업 중단
        }
		System.out.print("영화제목 입력 : ");
		String title  = scan.nextLine();
		System.out.print("영화감독 입력 : ");
		String director = scan.nextLine();
		System.out.print("영화장르 입력 : ");
		String genre = scan.nextLine();
        System.out.print("영화개봉일 입력 (형식: yyyy-MM-dd) : ");
        String releaseDate = scan.nextLine();
//        String releaseDateStr = scan.nextLine();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date releaseDate = null;
//        try {
//            releaseDate = (Date) sdf.parse(releaseDateStr);
//        } catch (ParseException e) {
//            System.out.println("날짜 형식이 올바르지 않습니다. (형식: yyyy-MM-dd)");
//            e.printStackTrace();
//            scan.close();
//            return; 
//        }
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
