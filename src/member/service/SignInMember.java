package member.service;

import java.util.Scanner;

import cineManager.dao.MemberDAO;
import cineManager.bean.MemberDTO;

public class SignInMember implements Member {

	@Override
	public void execute() {
		System.out.println();
		Scanner in = new Scanner(System.in);

		// DB - SingleTon
		// Singleton.getInstance()를 호출하여 동일한 싱글톤 인스턴스를 가져온다
		MemberDAO memberDAO = MemberDAO.getInstance(); 

		String id = null;
		String pwd = null;

		while (true) {
			System.out.print("아이디 입력 : ");
			id = in.next();
			System.out.print("비밀번호 입력 : ");
			pwd = in.next();

			String name = memberDAO.login(id, pwd);

			if (name == null){
				System.out.println("아이디 또는 비밀번호가 맞지 않습니다.");
				System.out.println("-------------------------------------------------");
			}
			else {
				System.out.println(name + "님 로그인");
				break;
			}
		}
		
	}

}
