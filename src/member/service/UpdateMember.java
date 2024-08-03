package member.service;

import cineManager.dao.MemberDAO;
import cineManager.bean.MemberDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UpdateMember implements Member {
	private Scanner scan = new Scanner(System.in);
	
	@Override
	public void execute() {
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
		
		String phoneNumber;
		String dateFormat = "\\d{3}-\\d{4}-\\d{4}";
		Pattern pattern = Pattern.compile(dateFormat);
		
		String phone = null;
		while (true) {

			System.out.print("핸드폰번호 입력(010-1234-5678) : ");
			phone = scan.next();
			
			if (pattern.matcher(phone).matches()) {
                String[] parts = phone.split("-");
                int one = Integer.parseInt(parts[0]);
                int two = Integer.parseInt(parts[1]);
                int three = Integer.parseInt(parts[2]);

                if (one >= 0 && one <= 999 && two >= 0 && two <= 9999 && three >= 0 && three <= 9999) {
                	// phone 중복체크
        			boolean exist = memberDAO.isExistPhone(phone);
        			if (exist) {
        				System.out.println("이미 등록된 전화번호입니다. 다시 입력하세요.\n");
        			}
        			else {
        				break;
        			}
                } else {
                    System.out.println("잘못된 핸드폰 번호 형식입니다. 다시 입력해주세요.\n");
                }
            } else {
                System.out.println("잘못된 핸드폰 번호 형식입니다. 다시 입력해주세요.\n");
            }
		}
		
		Map<String, String> map = new HashMap<>();
		map.put("name", name);
		map.put("id", id);
		map.put("pwd", pwd);
		map.put("phone", phone);
		
		memberDAO.update(map);
		System.out.println("\n회원정보가 수정되었습니다.\n");

	}
}
