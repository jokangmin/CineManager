package member.service;

import java.util.Scanner;

import cineManager.dao.MemberDAO;
import cineManager.bean.MemberDTO;

public class SignUpMember implements Member{
	private Scanner scan = new Scanner(System.in);
	
	@Override
	public void execute() {
		// DB - SingleTon
		// Singleton.getInstance()를 호출하여 동일한 싱글톤 인스턴스를 가져온다
		MemberDAO memberDAO = MemberDAO.getInstance(); 

		System.out.print("이름 입력 : ");
		String name = scan.next();

		// id 중복체크
		String id = null;
		while (true) {
			System.out.println();
			System.out.print("아이디 입력 : ");
			id = scan.next();

			// DB
			boolean exist = memberDAO.isExistId(id);
			if(exist) {
				System.out.println("현재 사용중인 아이디 입니다. 다시 입력하세요.");
			}
			else {
				System.out.println("사용 가능한 아이디 입니다.");
				break;
			}
		}

		System.out.print("비밀번호 입력 : ");
		String pwd = scan.next();
		System.out.print("핸드폰번호 입력(010-1234-5678) : ");
		String phone = scan.next();

		// MemberDTO 객체 생성 및 필드 설정
		MemberDTO memberDTO = new MemberDTO(name, id, pwd, phone);
		
		memberDAO.insert(memberDTO);
		System.out.println("\n" + name + "님 회원가입 되었습니다.\n");
	}
}