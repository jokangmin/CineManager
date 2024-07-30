package cineManager.main;

import java.util.Scanner;
import member.service.Member;
import member.service.SignUpMember;
import member.service.SignInMember;
import member.service.UpdateMember;
import member.service.DeleteMember;
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
				System.out.println("	3. 회원정보수정");
				System.out.println("	4. 회원탈퇴");
				System.out.println("	5. 끝");
				System.out.println("************************");
				System.out.print("번호 입력 : ");
				num = in.nextInt();

				if (num == 5) {
					break;
				}

				switch (num) {
				case 1: // 회원가입
					member = new SignUpMember();
					member.execute();
					break;

				case 2: // 로그인
					member = new SignInMember();
					member.execute();
					break;

				case 3: // 회원정보수정
					member = new UpdateMember();
					member.execute();
					break;

				case 4: // 회원탈퇴
					member = new DeleteMember();
					member.execute();
					break;

				default:
					System.out.println("잘못된 입력입니다. 다시 시도하세요.\n");
				}
			}

		}


	

	public static void main(String[] args) {
		IndexMain indexMain = new IndexMain();
		indexMain.menu();
		System.out.println("프로그램을 종료합니다");
	}

}
