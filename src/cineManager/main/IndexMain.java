package cineManager.main;

import java.util.Scanner;

import member.service.Member;
import member.service.SignInMember;
import member.service.SignUpMember;
public class IndexMain {


		public void menu() {
			Scanner in = new Scanner(System.in);
			int num;
			Member member = null;

			while(true) {
				System.out.println();
				System.out.println("************************");
				System.out.println("	1. 회원가입");
				System.out.println("	2. 로그인");
				System.out.println("	3. CineManager 종료");
				System.out.println("************************");
				System.out.print("번호 입력 : ");
				num = in.nextInt();

				if (num == 1) {
				member = new SignUpMember();
				member.execute();
			} else if (num == 2) {
				member = new SignInMember();
				member.execute();
			} else if (num == 3) {
				break;
			} else {
				System.out.println("\n잘못된 선택입니다. 다시 선택해주세요.\n");
			}	
				
			}
		}

	public static void main(String[] args) {
		IndexMain indexMain = new IndexMain();
		indexMain.menu();
		System.out.println("\n프로그램을 종료합니다");
	}

}

