package cineManager.main;

import java.util.InputMismatchException;
import java.util.Scanner;
import member.service.Member;
import member.service.SignInMember;
import member.service.SignUpMember;

public class IndexMain {
	private Scanner scan = new Scanner(System.in);
	int num;
	public void menu() {
		Member member = null;

		
		while(true) {
			System.out.println("*****************************************");
			System.out.println("	1. 회원가입");
			System.out.println("	2. 로그인");
			System.out.println("	3. CineManager 종료");
			System.out.println("*****************************************");
			System.out.print("번호 입력 : ");
			try {
				num = scan.nextInt();
				scan.nextLine();
				if (num == 1) {
					member = new SignUpMember();
					member.execute();
				} else if (num == 2) {
					member = new SignInMember();
					member.execute();
				} else if (num == 3) {
					break;
				} else {
					System.out.println("잘못된 선택입니다. 다시 선택해주세요.\n");
				}
			}catch(InputMismatchException e) {
				System.out.println("'숫자'형태만 입력하세요.\n");
				scan.nextLine();
			}

		}
	}

	
	public static void main(String[] args) {
        // ASCII 아트를 출력하는 메소드 호출
        printCinemaManagerHeader();
        
        // IndexMain 객체를 생성하고 메뉴 메소드 호출
        IndexMain indexMain = new IndexMain();
        indexMain.menu();
        
        // 프로그램 종료 메시지 출력
        System.out.println("프로그램을 종료합니다");
    }

    // ASCII 아트를 출력하는 메소드 정의
    public static void printCinemaManagerHeader() {
        String header = 
                "    ______  __  .__   __.  _______                                 \n" +
                "   /      ||  | |  \\ |  | |   ____|                                \n" +
                "  |  ,----'|  | |   \\|  | |  |__                                  \n" +
                "  |  |     |  | |  . `  | |   __|                                 \n" +
                "  |  `----.|  | |  |\\   | |  |____                                \n" +
                "   \\______||__| |__| \\__| |_______|                                \n" +
                "  .___  ___.      ___      .__   __.      ___       _______  _______ .______ \n" +
                "  |   \\/   |     /   \\     |  \\ |  |     /   \\     /  _____||   ____||   _  \\ \n" +
                "  |  \\  /  |    /  ^  \\    |   \\|  |    /  ^  \\   |  |  __  |  |__   |  |_)  | \n" +
                "  |  |\\/|  |   /  /_\\  \\   |  . `  |   /  /_\\  \\  |  | |_ | |   __|  |      / \n" +
                "  |  |  |  |  /  _____  \\  |  |\\   |  /  _____  \\ |  |__| | |  |____ |  |\\  \\----.\n" +
                "  |__|  |__| /__/     \\__\\ |__| \\__| /__/     \\__\\ \\______| |_______|| _| `._____| \n"; 


        System.out.println(header);
}
	
	



}

