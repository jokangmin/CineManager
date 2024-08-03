package cineManager.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cineManager.bean.ReviewDTO;

public class ReviewDAO {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "c##java";
	private String password = "1234";
	
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static ReviewDAO instance = new ReviewDAO();

	public static ReviewDAO getInstance() {
		return instance;
	}

	private ReviewDAO() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	
	private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
	
	private void closeAll(Connection con, PreparedStatement pstmt, ResultSet rs) {
		try { // con --> pstmt --> rs 순서로 만들었으니 닫는건 반대로
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkReviewExists(int movieCode, String userId) {
		boolean exists = false;
		String sql = "select count(*) from review where movie_code = ? and user_id =?";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, movieCode);
			pstmt.setString(2, userId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
	            exists = (rs.getInt(1) > 0); // 리뷰가 존재하면 true, 아니면 false
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        closeAll(con, pstmt, rs);
	    }
		return exists;
	}
	
	public int addReview(ReviewDTO reviewDTO) {
        int su = 0;
        String sql = "INSERT INTO review (review_id, movie_code, user_id, review, logdate) VALUES (review_id_seq.NEXTVAL, ?, ?, ?, ?)";

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, reviewDTO.getMovieCode());
            pstmt.setString(2, reviewDTO.getUserId());
            pstmt.setString(3, reviewDTO.getReview());
            pstmt.setDate(4, reviewDTO.getLogDate());

            su = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(con, pstmt, rs);
        }
        return su;
    }
	
	public void selectReview() {
        try {
        	con = getConnection();
        	String sql = "SELECT m.title AS 제목, m.director AS 감독, r.review AS 리뷰,r.user_id AS 유저아이디, TO_CHAR(r.logdate, 'YYYY-MM-DD hh:mm:ss') AS 등록날짜 FROM review r JOIN movies m ON r.movie_code = m.code ORDER BY m.title ASC , r.user_id DESC";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (!rs.isBeforeFirst()) {
	            System.out.println("조회된 데이터가 없습니다.");
	        }
			while (rs.next()) {
				System.out.println("-----------------------------------------\n" +
									"< " + rs.getString("제목") + " > : " + rs.getString("감독")+ "\n" +
									"-----------------------------------------\n"+
									"리뷰 : " + rs.getString("리뷰") + "\n" +
									"-----------------------------------------\n"+
									rs.getString("유저아이디") + "/" + rs.getString("등록날짜")+ "\n" +
									"-----------------------------------------\n \n");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(con, pstmt, rs);
		}
	}
	
	public void selectTitleReview(String title) { //8/2 11:19 강민 수정
        try {
        	con = getConnection();
        	String sql = "SELECT m.title AS 제목, m.director AS 감독, r.review AS 리뷰,r.user_id AS 유저아이디, TO_CHAR(r.logdate, 'YYYY-MM-DD hh:mm:ss') AS 등록날짜 FROM review r JOIN movies m ON r.movie_code = m.code WHERE m.title LIKE ? ORDER BY r.user_id DESC";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + title + "%");
			rs = pstmt.executeQuery();
			if (!rs.isBeforeFirst()) {
	            System.out.println("조회된 데이터가 없습니다.");
	        }
			while (rs.next()) {
				System.out.println("-----------------------------------------\n" +
									"< " + rs.getString("제목") + " > : " + rs.getString("감독")+ "\n" +
									"-----------------------------------------\n"+
									"리뷰 : " + rs.getString("리뷰") + "\n" +
									"-----------------------------------------\n"+
									rs.getString("유저아이디") + "/" + rs.getString("등록날짜")+ "\n" +
									"-----------------------------------------\n \n");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(con, pstmt, rs);
		}
	}
	
	public void deleteReview(int code, String userId) { //8/3 강민 , 리뷰 삭제하는 부분
        try {
        	con = getConnection();
        	String sql = "delete review where movie_code = ? and user_Id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, code);
			pstmt.setString(2, userId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(con, pstmt, rs);
		}
	}
	
	//리뷰 수정 240803 혜진 추가 
	public void updateReview(int code, String userId, String review) { 
        try {
        	con = getConnection();
        	String sql = "update review set review = ? where movie_code = ? and user_Id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,review);
			pstmt.setInt(2, code);
			pstmt.setString(3, userId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(con, pstmt, rs);
		}
	}
}
