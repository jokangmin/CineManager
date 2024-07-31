package member.service;

import cineManager.dao.MemberDAO;
import cineManager.bean.MemberDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UpdateMember implements Member {
	private Scanner scan = new Scanner(System.in);
	
	@Override
	public void execute() {
		System.out.println();
//		Scanner in = new Scanner(System.in);

		// DB - SingleTon
		// Singleton.getInstance()를 호출하여 동일한 싱글톤 인스턴스를 가져온다
		MemberDAO memberDAO = MemberDAO.getInstance(); 

		String id = null;
		MemberDTO memberDTO = null;
		while (true) {
			System.out.print("아이디 검색 : ");
			id = scan.next();
			
			// DB에서 해당 아이디로 회원 정보 조회
			memberDTO = memberDAO.getMemberToUpdate(id);
            
            if (memberDTO == null) {
                System.out.println("검색한 아이디가 없습니다.");
            }
			else {
				// 회원 정보 출력
				System.out.println(memberDTO);
				break;
			}
		}
		
		
		System.out.print("\n수정할 이름 입력 : ");
		String name = scan.next();
		
		System.out.print("수정할 비밀번호 입력 : ");
		String pwd = scan.next();
		
		System.out.print("수정할 핸드폰 입력(010-1234-1234) : ");
		String phone = scan.next();
		
		Map<String, String> map = new HashMap<>();
		map.put("name", name);
		map.put("id", id);
		map.put("pwd", pwd);
		map.put("phone", phone);
		
		memberDAO.update(map);
		System.out.println("\n회원정보가 수정되었습니다.\n");


	}
}
