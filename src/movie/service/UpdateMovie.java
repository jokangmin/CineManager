package movie.service;

import java.util.Scanner;
import cineManager.dao.MovieDAO;


public class UpdateMovie implements Movie {
    private Scanner scan = new Scanner(System.in);
    private String userId; // 로그인한 사용자의 ID

    public UpdateMovie(String userId) {
        this.userId = userId;
    }

    @Override
    public void execute() {
        MovieDAO movieDAO = MovieDAO.getInstance();
        System.out.print("수정할 영화 제목 : ");
        String title = scan.nextLine();

        if (movieDAO.titleCheck(title)) {
            movieDAO.selectTitleSummary(title, userId);
        } else {
            System.out.println("영화 제목이 올바르지 않습니다.");
            return;
        }

        System.out.print("수정할 영화 번호 : ");
        int code = scan.nextInt();
        scan.nextLine();

        if (movieDAO.codeCheck(code) && movieDAO.isMovieOwnedByUser(code, userId)) {
            System.out.println(code + "\t" + title + " 을(를) 수정합니다.\n");

            // 영화의 현재 정보를 출력하기 위해 selectDetail 메소드 사용
            movieDAO.selectDetail(code, userId);

            System.out.print("수정할 항목 (제목/감독/장르/개봉일/줄거리) : ");
            String updateItem = scan.nextLine();

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
                    System.out.print("새로운 개봉일 (YYYY-MM-DD) : ");
                    updateValue = scan.nextLine();
                    break;
                case "줄거리":
                    System.out.print("새로운 줄거리 : ");
                    updateValue = scan.nextLine();
                    break;
                default:
                    System.out.println("잘못된 항목입니다.");
                    return;
            }

            int result = movieDAO.updateMovie(updateItem, updateValue, code, title, userId);
            if (result > 0) {
                System.out.println("영화 '" + title + "' 이(가) 수정되었습니다.\n");
            } else {
                System.out.println("영화 수정에 실패했습니다.\n");
            }
        } else {
            System.out.println("올바른 code 값이 아닙니다.");
        }
    }
}