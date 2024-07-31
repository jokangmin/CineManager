package cineManager.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import cineManager.bean.MemberDTO;

// DB
public class MemberDAO {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "c##java";
	private String password = "1234";

	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs; // SQL(select) 쿼리 결과를 저장하는 객체; select를 하면 ResultSet이 따라오도록. 여기에만 담아오도록

	// 싱글톤 인스턴스 생성
	private static MemberDAO instance = new MemberDAO();

	public static MemberDAO getInstance() {
		return instance;
	}

	public MemberDAO() { // Driver Loading
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void getConnection() { // connection
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void closeAll() {
		try { // con --> pstmt --> rs 순서로 만들었으니 닫는건 반대로
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private PreparedStatement prepareStatement(String sql, String... params) throws SQLException {
		getConnection(); // SQL; database 연결(접속)
		pstmt = con.prepareStatement(sql); // SQL 쿼리 정의
		for (int i = 0; i < params.length; i++) {
			pstmt.setString(i + 1, params[i]);
		}
		return pstmt;
	}

	public boolean isExistId(String id) { // id 중복체크
		boolean exists = false; 
		String sql = "select * from members where id=?";

		try {
			getConnection(); // 연결 초기화
			pstmt = con.prepareStatement(sql);
			prepareStatement(sql, id); // ?에 Data Mapping
			rs = pstmt.executeQuery();
			exists = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return exists;
	}

	public void insert(MemberDTO memberDTO) { // 회원가입
		int su = 0;
		String sql = "insert into members values (?, ?, ?, ?)";

		try {
			// ?에 Data Mapping
			prepareStatement(sql, 
					memberDTO.getName(), 
					memberDTO.getId(), 
					memberDTO.getPwd(), 
					memberDTO.getPhone()
					);
			su = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
	}

	public String login(String id, String pwd) { // 로그인
		String name = null;
		String sql = "select * from members where id=? and pwd=?";

		try {
			prepareStatement(sql, id, pwd); // ?에 Data Mapping
			rs = pstmt.executeQuery();
			if (rs.next()) {
				name = rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return name;
	}

	// DB에서 MemberDTO 객체 반환
	public MemberDTO getMemberToUpdate(String id) {
		MemberDTO memberDTO = null;
		String sql = "select * from members where id=?";
		try {
			prepareStatement(sql, id); // ?에 Data Mapping
			rs = pstmt.executeQuery();
			System.out.println("이름" + "\t" +
                    "id" + "\t" +
                    "비밀번호" + "\t" + "전화번호");
			if (rs.next()) { // MemberDTO 객체를 생성, DB에서 가져온 값을 설정
				memberDTO = new MemberDTO(
						rs.getString("name"),
						rs.getString("id"),
						rs.getString("pwd"),
						rs.getString("phone")
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return memberDTO;
	}

	public void update(Map<String, String> map) { // 회원정보수정
		int su = 0;
		String sql = "update members set name=?, pwd=?, phone=? where id=?";

		try {
			// ?에 Data Mapping
			prepareStatement(sql, map.get("name"), map.get("pwd"), map.get("phone"), map.get("id"));
			su = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
	}
	
	
	public MemberDTO getMemberToDelete(String id, String pwd) {
		MemberDTO memberDTO = null;
		String sql = "select * from members where id=? and pwd=?";

		try {
			prepareStatement(sql, id, pwd); // ?에 Data Mapping
			rs = pstmt.executeQuery();
			if (rs.next()) {
				memberDTO = new MemberDTO(
						rs.getString("name"),
						rs.getString("id"),
						rs.getString("pwd"),
						rs.getString("phone")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
		return memberDTO;
	}
	
	public int delete(String id) { // 회원탈퇴
		int su = 0;
		getConnection(); // SQL; database 연결(접속)
		String sql = "delete from members where id=?";
		try {
			prepareStatement(sql, id); // ?에 Data Mapping
			su = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return su;
	}

}