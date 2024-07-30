package movie.service;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import cineManager.bean.MovieDTO;
import cineManager.dao.MovieDAO;


public class AddMovie implements Movie{
	@Override
	public void execute() {
		System.out.println();
		Scanner scan = new Scanner(System.in);

		MovieDAO movieDAO = MovieDAO.getInstance();

		System.out.print("영화번호 입력 : ");
		int code = scan.nextInt();
		scan.nextLine(); // 버퍼 비우기
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
