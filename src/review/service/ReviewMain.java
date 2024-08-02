package review.service;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ReviewMain {

//	public void menu() {

	private Scanner scan = new Scanner(System.in);
	private String userId; // 로그인한 사용자의 ID
//		public void execute() {
//			while

	
	public ReviewMain(String userId) {
		this.userId = userId;
	}
	
	public void exeute() {
		while (true) {
			System.out.println("-----------------------");
			System.out.println("1. 후기 작성  ");
			System.out.println("2. 후기 조회 ");
			System.out.println("3. 후기 수정 ");
			System.out.println("4. 후기 삭제 ");
			System.out.println("5. 뒤로 가기  ");
			System.out.println("-----------------------");
			System.out.print("번호 선택 : ");
			int num;
			try {
				num = scan.nextInt();
				// scan.nextLine(); // 입력 후 개행 문자 제거
			} catch (InputMismatchException e) {
				System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
				scan.nextLine(); // 잘못된 입력 처리 후 개행 문자 제거
				continue; // 다시 입력 받기
			}
			System.out.println();

			switch (num) {
			case 1:
				new AddReview(userId).execute(); // 로그인한 사용자 ID 전달
				break;
			case 2:
				new SelectReview(userId).execute(); // 로그인한 사용자 ID 전달
				break;
			case 3:
				new UpdateReview(userId).execute(); // 로그인한 사용자 ID 전달
				break;
			case 4:
				new DeleteReview(userId).execute(); // 로그인한 사용자 ID 전달
				break;
			case 5:
//				System.out.println("메인으로 돌아갑니다.");
				return;
			default:
				System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
			}
		}

	}
	}

/*
 * 
 * if (menu_num == 5) break; else if (menu_num == 1) {
 * movieDAO.selectAll(userId); // 사용자 ID를 통해 본인 영화만 조회 } else if (menu_num == 2)
 * { System.out.print("영화 제목 검색 : "); String title = scan.nextLine();
 * System.out.println(); // else if (menu_num == 3) {
 * 
 * } else if (menu_num == 4) {
 * 
 * }
 * 
 * else { System.out.println("잘못된 선택입니다. 다시 선택해주세요."); } } catch (Exception e) {
 * System.out.println("잘못된 입력입니다. 숫자를 입력해주세요."); scan.nextLine(); // 잘못된 입력 처리 후
 * 개행 문자 제거 } }
 * 
 * } } }
 * 
 */