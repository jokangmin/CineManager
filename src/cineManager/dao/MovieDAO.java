package cineManager.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import cineManager.bean.MovieDTO;

public class MovieDAO {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "c##java";
	private String password = "1234";
	private PreparedStatement pstmt;
	private Connection con;
	private ResultSet rs = null;
	private static MovieDAO instance = new MovieDAO();
	Scanner scan = new Scanner(System.in);

	public MovieDAO() {
		try {
			Class.forName(driver); 	
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); 
		}
	} 

	public static MovieDAO getInstance(){
		return instance;
	}

	public void getConnection() { 
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void finally_ck() {
		try {
			if(rs != null) rs.close(); 
			if(pstmt != null) pstmt.close();
			if(con != null) con.close(); 

		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //검사 부분.

	// 오혜진 
	//int su = 0;

	public int add(MovieDTO movieDTO){
		int su =0;
		getConnection();
		String sql = "insert into movies values(?,?,?,?,?,?)";
//		System.out.println(code+ "\t" + title + " 을 입력합니다.");


		try {
			pstmt = con.prepareStatement(sql); //생성

			//?에 데이터 매핑
			pstmt.setInt(1, movieDTO.getCode());
			pstmt.setString(2, movieDTO.getTitle());
			pstmt.setString(3, movieDTO.getDirector());
			pstmt.setString(4, movieDTO.getGenre());
			pstmt.setString(5, movieDTO.getReleaseDate());
			pstmt.setString(6, movieDTO.getSynopsis());

			su = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}

		return su;
	}

	public void selectDetail(int code) {
		getConnection();
		String sql = "select * from movies where code = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,code);
			rs = pstmt.executeQuery();
			System.out.println("*** 조회결과 ***");
			if(rs.next()) {
				System.out.println("번호 : " + rs.getInt("code") +"\n" + 
						"제목 : " + rs.getString("title") +"\n" + 
						"감독 : " + rs.getString("director") + "\n" + 
						"장르 : " + rs.getString("genre") + "\n" +
						"개봉일 : " + rs.getDate("release_date") + "\n" +
						"줄거리 : " + rs.getString("synopsis"));
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			finally_ck();
		}
	}
	
	public void selectAll() {
		getConnection();
		String sql = "select * from movies";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			System.out.println("영화 번호" + "\t" +
					"영화 제목" + "\t" +
					"영화 감독" + "\t" +
					"영화 개봉일");
			while(rs.next()) {
				System.out.println(rs.getString("code") +"\t" +
								   rs.getString("title") +"\t" + 
							       rs.getString("director") + "\t" +
							       rs.getDate("release_date"));
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			finally_ck();
		}
	}
	
	public void updateMovie(String title, int code) { //업데이트 메소드
		getConnection();

		System.out.println(code+ "\t" + title + " 을 수정합니다.");
		String sql = "";
		try {
			System.out.print("수정할 항목 : ");
			String update_item = scan.nextLine();

			if(update_item.contains("제목")) {
				sql = "update movies set title = ? where code = ? and title = ?";
				System.out.print("새로운 제목 : ");
				String update_title = scan.nextLine();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,update_title);
				pstmt.setInt(2,code);				
				pstmt.setString(3,title);
			}

			else if(update_item.contains("감독")) {
				sql = "update movies set director = ? where code = ? and title = ?";
				System.out.print("새로운 감독 : ");
				String update_director = scan.nextLine();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,update_director);
				pstmt.setInt(2,code);				
				pstmt.setString(3,title);
			}

			else if(update_item.contains("장르")) {
				sql = "update movies set genre = ? where code = ? and title = ?";
				System.out.print("새로운 장르 : ");
				String update_genre = scan.nextLine();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,update_genre);
				pstmt.setInt(2,code);				
				pstmt.setString(3,title);
			}

			else if(update_item.contains("개봉일")) {
				sql = "update movies set release_date = TO_DATE(?, 'YYYY-MM-DD') where code = ? and title = ?";
				System.out.print("새로운 개봉일(yyyy-MM-dd) : ");
				String update_date = scan.nextLine();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,update_date);
				pstmt.setInt(2,code);				
				pstmt.setString(3,title);
			}

			else if(update_item.contains("줄거리")) {
				sql = "update movies set synopsis = ? where code = ? and title = ?";
				System.out.print("새로운 줄거리 : ");
				String update_synopsis = scan.nextLine();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,update_synopsis);
				pstmt.setInt(2,code);				
				pstmt.setString(3,title);
			}
			pstmt.executeUpdate();	
			System.out.println("영화 '" + title + "' 이(가) 수정되었습니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			finally_ck();
		}
	}

	public void deleteMovie(String title, int code) {
		getConnection();
		String sql = "delete movies where code = ? and title = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,code);				
			pstmt.setString(2,title);
			pstmt.executeUpdate();	
			System.out.println("영화 '" + title + "' 이(가) 삭제되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			finally_ck();
		}
	}


	public void selectTitleMovie(String title) { //제목으로 검색해서 출력해주는 메소드
		getConnection();
		String sql = "select * from movies where title = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,title);
			rs = pstmt.executeQuery();
			System.out.println("영화 번호" + "\t" +
					"영화 제목" + "\t" +
					"영화 감독" + "\t" +
					"영화 장르" + "\t" +
					"영화 개봉일" + "\t" +
					"영화 줄거리");
			while(rs.next()) {
				System.out.println(rs.getInt("code") +"\t" + 
						rs.getString("title") +"\t" + 
						rs.getString("director") + "\t" + 
						rs.getString("genre") + "\t" +
						rs.getDate("release_date") + "\t" +
						rs.getString("synopsis"));
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			finally_ck();
		}
	}

	public boolean codeCheck(int code) { //code 가 있는 code 인지 확인해주는 부분 
		boolean state = false;
		getConnection();
		String sql = "select code from movies";
		try {
			pstmt = con.prepareStatement(sql);
			//			pstmt.setString(1,code);
			rs = pstmt.executeQuery();
			if (rs.next()) { // 추가된 부분
				int get_code = rs.getInt("code");
				if (get_code == code) state = true;
			}
			//			int get_code = rs.getInt("code");
			//			if(get_code == code) state = true;
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			finally_ck();
		}
		return state;
	}

}
