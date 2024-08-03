package member.service;

import java.util.Scanner;
import cineManager.dao.MemberDAO;
import cineManager.bean.MemberDTO;

public class SignInMember implements Member {
	private Scanner scan = new Scanner(System.in);
	private String userId; // 로그인한 사용자의 ID
	
	public String getUserId() {
        return userId;
    }
	
	@Override
	public void execute() {
		// DB - SingleTon
		// Singleton.getInstance()를 호출하여 동일한 싱글톤 인스턴스를 가져온다
		MemberDAO memberDAO = MemberDAO.getInstance(); 

		String id = null;
		String pwd = null;

		while (true) {
			System.out.println();
			System.out.print("아이디 입력 : ");
			id = scan.next();
			System.out.print("비밀번호 입력 : ");
			pwd = scan.next();

			String name = memberDAO.login(id, pwd);

			if (name == null){
				System.out.println("아이디 또는 비밀번호가 맞지 않습니다.");
				System.out.println("-----------------------------------------");
			}
			else {
				userId = id; // 로그인한 사용자의 ID 저장
				System.out.println("\n" + name + "님 로그인\n");
				break;
			}
		}

		// 로그인 성공 후, SignInMain 클래스 실행
        new SignInMain(userId).execute();
	}

}