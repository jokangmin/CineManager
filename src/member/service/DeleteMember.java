package member.service;

import cineManager.dao.MemberDAO;
import cineManager.bean.MemberDTO;
import java.util.Scanner;

public class DeleteMember implements Member {

	@Override
	public void execute() {
		System.out.println();
		Scanner in = new Scanner(System.in);

		// DB - SingleTon
		// Singleton.getInstance()를 호출하여 동일한 싱글톤 인스턴스를 가져온다
		MemberDAO memberDAO = MemberDAO.getInstance(); 

		String id = null;
		String pwd = null;
		MemberDTO memberDTO = null;

		while (true) {
			System.out.print("아이디 입력 : ");
			id = in.next();

			System.out.print("비밀번호 입력 : ");
			pwd = in.next();
			
			memberDTO = memberDAO.getMemberToDelete(id, pwd);
			
			if (memberDTO == null) {
				break;
			}
			else {
				System.out.println("아이디 또는 비밀번호가 틀렸습니다. 다시 시도하세요\n");
			}

		}
		// 입력
		memberDAO.delete(id);
		System.out.println("회원탈퇴 되었습니다.");
	}
}