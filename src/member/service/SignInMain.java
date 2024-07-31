package member.service;
import movie.service.AddMovie;
import movie.service.DeleteMovie;
import movie.service.SelectMovie;
import movie.service.UpdateMovie;
import java.util.Scanner;

public class SignInMain implements Member {

    @Override
    public void execute() {
        Scanner in = new Scanner(System.in);

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
            int num = in.nextInt();

            switch (num) {
                case 1:
                    new AddMovie().execute();
                    break;
                case 2:
                    new SelectMovie().execute();
                    break;
                case 3:
                    new UpdateMovie().execute();
                    break;
                case 4:
                    new DeleteMovie().execute();
                    break;
                case 5:
                    new UpdateMember().execute();
                    break;
                case 6:
                    new DeleteMember().execute();
                    break;
                case 7:
                    System.out.println("\n로그아웃합니다.");
                    return; // 로그아웃 시 메뉴를 종료하고 IndexMain으로 돌아간다
                default:
                    System.out.println("\n잘못된 선택입니다. 다시 선택해주세요.\n");
            }
        }
    }
}