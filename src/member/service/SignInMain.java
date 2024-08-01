package member.service;

import java.util.InputMismatchException;
import java.util.Scanner;

import movie.service.AddMovie;
import movie.service.DeleteMovie;
import movie.service.SelectMovie;
import movie.service.UpdateMovie;

public class SignInMain implements Member {
	private Scanner scan = new Scanner(System.in);
	private String userId; // 로그인한 사용자의 ID
	
	public SignInMain(String userId) {
        this.userId = userId;
    }
	
    @Override
    public void execute() {
        while (true) {
        	System.out.println("************************");
            System.out.println("1. 영화 추가");
            System.out.println("2. 영화 조회");
            System.out.println("3. 영화 수정");
            System.out.println("4. 영화 삭제");
            System.out.println("5. 회원정보 수정");
            System.out.println("6. 회원 탈퇴");
            System.out.println("7. 로그아웃");
            System.out.println("************************");
            System.out.print("번호 입력 : ");
            int num;
            try {
            	num = scan.nextInt();
            	scan.nextLine(); // 입력 후 개행 문자 제거
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                scan.nextLine(); // 잘못된 입력 처리 후 개행 문자 제거
                continue; // 다시 입력 받기
            }
            System.out.println();

            switch (num) {
                case 1:
                    new AddMovie(userId).execute(); // 로그인한 사용자 ID 전달
                    break;
                case 2:
                    new SelectMovie(userId).execute(); // 로그인한 사용자 ID 전달
                    break;
                case 3:
                    new UpdateMovie(userId).execute(); // 로그인한 사용자 ID 전달
                    break;
                case 4:
                    new DeleteMovie(userId).execute(); // 로그인한 사용자 ID 전달
                    break;
                case 5:
                    new UpdateMember().execute();
                    break;
                case 6:
                    new DeleteMember().execute();
                    return; // 조강민 08/01 수정 
                    //break;
                case 7:
                    System.out.println("로그아웃되었습니다.\n");
                    return; // 로그아웃 시 메뉴를 종료하고 IndexMain으로 돌아간다
                default:
                    System.out.println("\n잘못된 선택입니다. 다시 선택해주세요.\n");
            }
        }
    }
}