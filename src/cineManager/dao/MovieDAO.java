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
	
	private static MovieDAO instance = new MovieDAO();
	Scanner scan = new Scanner(System.in);
	
	
	
//	private PreparedStatement pstmt;
//	private ResultSet rs = null;

	
	
	public MovieDAO() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static MovieDAO getInstance() {
		return instance;
	}

	public Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public void finally_ck(PreparedStatement pstmt, Connection con, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // 검사 부분.

	public int add(MovieDTO movieDTO, String userId) {
		int su = 0;
		Connection con = null;
        PreparedStatement pstmt = null;
        try {
        	con = getConnection();
        	String sql = "insert into movies values(?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?)";
			pstmt = con.prepareStatement(sql); // 생성

			// ?에 데이터 매핑
			pstmt.setInt(1, movieDTO.getCode());
			pstmt.setString(2, movieDTO.getTitle());
			pstmt.setString(3, movieDTO.getDirector());
			pstmt.setString(4, movieDTO.getGenre());
			pstmt.setString(5, movieDTO.getReleaseDate());
			pstmt.setString(6, movieDTO.getSynopsis());
			pstmt.setString(7, userId);

			su = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			finally_ck(pstmt, con, null);
		}
		return su;
	}

	//영화제목 10자이상시 정렬 맞추기위해 코드 수정 - 240731 -오혜진
	public void selectAll(String userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        	con = getConnection();
        	String sql = "SELECT * FROM movies WHERE user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			System.out.println(String.format("%-10s", "영화번호") + String.format("%-20s", "영화제목")
					+ String.format("%-15s", "영화감독") + "영화개봉일");
			while (rs.next()) {
				System.out.println(
						String.format("%-10s", rs.getString("code")) + String.format("%-20s", rs.getString("title"))
								+ String.format("%-15s", rs.getString("director")) + rs.getDate("release_date"));
			}
//			pstmt.executeUpdate();
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			finally_ck(pstmt, con, rs);
		}
	}
	
	public void selectTitleSummary(String title, String userId) { // 영화 번호, 제목, 감독만 출력해주는 메소드
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        	con = getConnection();
        	String sql = "select code, title, director from movies where title like ? and user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + title + "%");
			pstmt.setString(2, userId);
			rs = pstmt.executeQuery();
			
			System.out.println("영화번호" + "\t" + "영화제목" + "\t" + "영화감독");
			while (rs.next()) {
				System.out.println(rs.getInt("code") + "\t" + rs.getString("title") + "\t" + rs.getString("director"));
			}
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			finally_ck(pstmt, con, rs);
		}
	}

	public void selectDetail(int code, String userId) {
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        	con = getConnection();
        	String sql = "select * from movies where code = ? and user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, code);
			pstmt.setString(2, userId);
			rs = pstmt.executeQuery();
			
			System.out.println("*** 조회결과 ***");
			if (rs.next()) {
				System.out.println("번호 : " + rs.getInt("code") + "\n" + 
							 		"제목 : " + rs.getString("title") + "\n" + 
							 		"감독 : " + rs.getString("director") + "\n" + 
							 		"장르 : " + rs.getString("genre") + "\n" + 
							 		"개봉일 : " + rs.getDate("release_date") + "\n" + 
							 		"줄거리 : " + rs.getString("synopsis"));
			}
			else {
	             System.out.println("해당 영화는 존재하지 않거나 접근 권한이 없습니다.");
	        }
//			pstmt.executeUpdate();
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			finally_ck(pstmt, con, rs);
		}
	}

	public boolean isMovieOwnedByUser(int code, String userId) {
        boolean isOwned = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            String sql = "SELECT COUNT(*) FROM movies WHERE code = ? AND user_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, code);
            pstmt.setString(2, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                isOwned = (count > 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	finally_ck(pstmt, con, rs);
        }
        return isOwned;
    }
	
	
	public void updateMovie(String title, int code, String userId) { // 업데이트 메소드
		Connection con = null;
        PreparedStatement pstmt = null;
        try {
        	con = getConnection();
        	System.out.println(code + "\t" + title + " 을(를) 수정합니다.\n");
			System.out.print("수정할 항목 : ");
			String update_item = scan.nextLine();
			String sql = "";

			if (update_item.contains("제목")) {
				sql = "update movies set title = ? where code = ? and title = ? and user_id = ?";
				System.out.print("새로운 제목 : ");
				String update_title = scan.nextLine();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, update_title);
				pstmt.setInt(2, code);
				pstmt.setString(3, title);
				pstmt.setString(4, userId);
			}

			else if (update_item.contains("감독")) {
				sql = "update movies set director = ? where code = ? and title = ? and user_id = ?";
				System.out.print("새로운 감독 : ");
				String update_director = scan.nextLine();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, update_director);
				pstmt.setInt(2, code);
				pstmt.setString(3, title);
				pstmt.setString(4, userId);
			}

			else if (update_item.contains("장르")) {
				sql = "update movies set genre = ? where code = ? and title = ? and user_id = ?";
				System.out.print("새로운 장르 : ");
				String update_genre = scan.nextLine();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, update_genre);
				pstmt.setInt(2, code);
				pstmt.setString(3, title);
				pstmt.setString(4, userId);
			}

			else if (update_item.contains("개봉일")) {
				sql = "update movies set release_date = TO_DATE(?, 'YYYY-MM-DD') where code = ? and title = ? and user_id = ?";
				System.out.print("새로운 개봉일(yyyy-MM-dd) : ");
				String update_date = scan.nextLine();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, update_date);
				pstmt.setInt(2, code);
				pstmt.setString(3, title);
				pstmt.setString(4, userId);
			}

			else if (update_item.contains("줄거리")) {
				sql = "update movies set synopsis = ? where code = ? and title = ? and user_id = ?";
				System.out.print("새로운 줄거리 : ");
				String update_synopsis = scan.nextLine();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, update_synopsis);
				pstmt.setInt(2, code);
				pstmt.setString(3, title);
				pstmt.setString(4, userId);
			}
			pstmt.executeUpdate();
			System.out.println("영화 '" + title + "' 이(가) 수정되었습니다.\n");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			finally_ck(pstmt, con, null);
		}
	}

	public void deleteMovie(String title, int code, String userId) {
		Connection con = null;
        PreparedStatement pstmt = null;
        try {
        	con = getConnection();
        	String sql = "delete movies where code = ? and title = ? and user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, code);
			pstmt.setString(2, title);
			pstmt.setString(3, userId);
			pstmt.executeUpdate();
			System.out.println("영화 '" + title + "' 이(가) 삭제되었습니다.\n");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			finally_ck(pstmt, con, null);
		}
	}

	public void selectTitleMovie(String title, String userId) { // 제목으로 검색해서 출력해주는 메소드
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        	con = getConnection();
        	String sql = "select * from movies where title like and user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,"%" + title + "%");
			rs = pstmt.executeQuery();
			System.out.println("영화번호\t영화제목\t영화감독\t영화장르\t영화개봉일\t영화줄거리");
			while(rs.next()) {
				System.out.println(rs.getInt("code") +"\t" + 
						rs.getString("title") +"\t" + 
						rs.getString("director") + "\t" + 
						rs.getString("genre") + "\t" +
						rs.getDate("release_date") + "\t" +
						rs.getString("synopsis"));
			}
//			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			finally_ck(pstmt, con, rs);
		}
	}

	public boolean codeCheck(int code) { // code 가 있는 code 인지 확인해주는 부분
		boolean state = false;
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        	con = getConnection();
        	String sql = "select count(*) from movies where code = ?"; // 주어진 code만 확인
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, code);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				state = (count > 0); // 코드가 존재하면 true, 그렇지 않으면 false
			}
//			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			finally_ck(pstmt, con, rs);
		}
		return state;
	}

	public boolean titleCheck(String title) { // 조강민 7/31 제목확인 추가
		boolean state = false;
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        	con = getConnection();
			String sql = "select * from movies where title like ?"; 
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + title + "%");
			rs = pstmt.executeQuery();
			if (rs.next()) {
	            state = true;
	        }
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			finally_ck(pstmt, con, rs);
		}
		return state;
	}
}
