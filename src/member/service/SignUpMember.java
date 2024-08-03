package member.service;

import java.util.Scanner;
import java.util.regex.Pattern;

import cineManager.dao.MemberDAO;
import cineManager.bean.MemberDTO;

public class SignUpMember implements Member{
	private Scanner scan = new Scanner(System.in);
	
	@Override
	public void execute() {
		// DB - SingleTon
		// Singleton.getInstance()를 호출하여 동일한 싱글톤 인스턴스를 가져온다
		MemberDAO memberDAO = MemberDAO.getInstance(); 

		System.out.println(); // 0803 13:00
		System.out.print("이름 입력 : ");
		String name = scan.next();

		// id 중복체크
		String id = null;
		while (true) {
			System.out.print("아이디 입력 : ");
			id = scan.next();

			// DB
			boolean exist = memberDAO.isExistId(id);
			if(exist) {
				System.out.println("현재 사용중인 아이디 입니다. 다시 입력하세요.\n");
			}
			else {
				System.out.println("사용 가능한 아이디 입니다.\n");
				break;
			}
		}

		System.out.print("비밀번호 입력 : ");
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
		// MemberDTO 객체 생성 및 필드 설정
		MemberDTO memberDTO = new MemberDTO(name, id, pwd, phone);
		
		memberDAO.insert(memberDTO);
		System.out.println("\n" + name + "님 회원가입 되었습니다.\n");
	}
}