package com.uni.member.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.uni.member.model.dto.Member;

/* 1.Connection 객체 연결하기 
 * 2.Statement 객체 생성하기 
 * 3.ResultSet 객체 생성하기 
 * 4.Sql 작성하기 
 * 5.ResultSet  결과담기 
 * 6.list 에 객체 하나씩 담기 
 * 7.close 하기 (자원반납 - 생성의 역순)
 */


public class MemberDao {

	public ArrayList<Member> selectAll() {
		
		//1.
		ArrayList<Member> list = null;
		
		//2.
		Connection conn = null; // DB에 연결할 객체
		Statement stmt = null;  // 실행할 쿼리
		ResultSet rset = null;  // select한 후 결과 값 받아올 객체
		
		//3.
		String sql = "SELECT * FROM MEMBER"; //MEMBER뒤에 자동으로 세미콜론 붙여서 실행됨
		
		//4.
		try {
			//1) jdbc driver 등록 처리 : 해당 database 벤더시가 제공하는 클래스 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println(" 드라이버 등록 성공! ");
			
			//2) 등록된 클래스를 이용해서 db연결
			// 드라이버타입 @ip주소:포트번호:db이름(SID)
			// orcl:사용자정의설치 , thin : 자동으로 설치 //ip주소 -> localhost로 변경해도됨
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "STUDENT", "STUDENT");
			System.out.println(" conn : " + conn); //성공하면 connection값, 실패하면 null (30행Connection conn = null)
		
			//3) 쿼리문을 실행할 statement 객체 생성
			stmt = conn.createStatement();
			
			//4) 쿼리문 전송, 실행 결과를 ResultSet으로 받기
			rset = stmt.executeQuery(sql);
			
			//5) 받은 결과 값을 객체에 옮겨서 담기
			list = new ArrayList<Member>();
		
			while(rset.next()) {
				//한 행에 대한 정보를 담을 변수
				Member m = new Member();
				m.setUserId(rset.getString("USERID"));
				m.setPassword(rset.getString("PASSWORD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE")); //sql받을때 숫자는 getInt로 받아야함
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));
				
				list.add(m);

			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return list;
	}

	//아이디 리턴
	public Member selectOne(String memberId) {
		Member m = null;
		
		Connection conn = null;
		//위치 홀더를 표시하고 쿼리 먼저 올리고 공간 확보되어 있는상태에서 값을 세팅시키는 방법
		//122행, 125행, 128행
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = ?";
		
		try {
			//1) jdbc driver 등록 처리 : 해당 database 벤더사가 제공하는 클래스 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println(" 드라이버 등록 성공! ");
			
			//2) 등록된 클래스를 이용해서 db연결
			// 드라이버타입@ip주소:포트번호:db이름(SID)
			// orcl:사용자정의설치 , thin : 자동으로 설치 //ip주소 -> localhost 로 변경해도됨
			// sql에서 초록색 + 세부정보
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "STUDENT", "STUDENT");
			System.out.println(" conn : " + conn); 
			
		
			//3) 쿼리문을 실행할 statement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//setString 1부터 시작함
			pstmt.setString(1, memberId);
			
			//4) 쿼리문 전송, 실행 결과를 ResultSet으로 받기
			rset = pstmt.executeQuery();
		
			if(rset.next()) {
				//한 행에 대한 정보를 담을 변수
				m = new Member();
				m.setUserId(rset.getString("USERID"));
				m.setPassword(rset.getString("PASSWORD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE")); //sql받을때 숫자는 getInt로 받아야함
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));

			}
			
		} catch (ClassNotFoundException e) {
			//oracle드라이버가 설치되지 않았을때 뜸..build path에서 jar넣기
			e.printStackTrace();
		} catch (SQLException e) {
			//SQL의 EOF(End of File)의 위치에 있다 즉 읽을 것이 없다
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return m;
	}

	public Member selectOne1(String userName) {
		Member m = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?";
		
		try {
			
			//드라이브 로드 oracle.jdbc.driver패키지안에 OracleDriver라는 클래스가 있음
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println(" 드라이버 등록 성공! ");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "STUDENT", "STUDENT");
			System.out.println(" conn : " + conn); //성공하면 conn이 뜸
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%"+userName+"%");

			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				//한 행에 대한 정보를 담을 변수
				m = new Member();
				m.setUserId(rset.getString("USERID"));
				m.setPassword(rset.getString("PASSWORD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE")); //sql받을때 숫자는 getInt로 받아야함
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));

			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return m;
	}

	public int insertMember(Member m) {
		//처리 받을 행의 개수
		int result = 0;
		
		Connection  conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO MEMBER VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate)";

		try {
			
			//드라이브 로드 oracle.jdbc.driver패키지안에 OracleDriver라는 클래스가 있음
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println(" 드라이버 등록 성공! ");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "STUDENT", "STUDENT");
			System.out.println(" conn : " + conn); //성공하면 conn이 뜸
			
			pstmt = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
			
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getPassword());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			result = pstmt.executeUpdate(); //처리한 행의 개수 리턴
			
			if(result > 0) { //0보다 크면 값이 들어왔다는 뜻
				conn.commit();
			}else {
				conn.rollback();
			}
			System.out.println("result -->" + result);
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				//rset.close(); ResultSet은 없으니까 안닫아도됨
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return result;
	}

	public int updateMember(Member m) {
		int result = 0;
		
		Connection  conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE MEMBER SET PASSWORD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ? WHERE USERID= ?";
			
				
		try {
			
			//드라이브 로드 oracle.jdbc.driver패키지안에 OracleDriver라는 클래스가 있음
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println(" 드라이버 등록 성공! ");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "STUDENT", "STUDENT");
			System.out.println(" conn : " + conn); //성공하면 conn이 뜸
			
			pstmt = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
			
			pstmt.setString(1, m.getPassword());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getUserId());
			
			result = pstmt.executeUpdate(); //처리한 행의 개수 리턴
			
			if(result > 0) { //0보다 크면 값이 들어왔다는 뜻
				conn.commit();
			}else {
				conn.rollback();
			}
			System.out.println("result -->" + result);
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				//rset.close(); rset은 없으니까 안닫아도됨
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return result;
	}

	public int deleteMember(String userId) {
		int result = 0;
		
		Connection conn = null;
//		
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE USERID = ?";
		try {
			
			//드라이브 로드 oracle.jdbc.driver패키지안에 OracleDriver라는 클래스가 있음
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println(" 드라이버 등록 성공! ");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "STUDENT", "STUDENT");
			System.out.println(" conn : " + conn); //성공하면 conn이 뜸
			
//			
			pstmt = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
//			
			pstmt.setString(1, userId);

//
			result = pstmt.executeUpdate(); //처리한 행의 개수 리턴
			
			if(result > 0) { //0보다 크면 값이 들어왔다는 뜻
				conn.commit();
			}else {
				conn.rollback();
			}
			System.out.println("result -->" + result);
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				//rset.close(); rset은 없으니까 안닫아도됨
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return result;
	}
		
}
	
	
