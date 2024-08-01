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
	
	
	public int addReview(ReviewDTO reviewDTO) {
        int result = 0;
        String sql = "INSERT INTO review (review_id, movie_code, user_id, review, logdate) VALUES (review_id_seq.NEXTVAL, ?, ?, ?, ?)";

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, reviewDTO.getMovieCode());
            pstmt.setString(2, reviewDTO.getUserId());
            pstmt.setString(3, reviewDTO.getReview());
            pstmt.setDate(4, reviewDTO.getLogDate());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(con, pstmt, rs);
        }
        return result;
    }
	
}
