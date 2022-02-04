package com.uni.member.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
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
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = '"+ memberId +"'";
		
		try {
			//1) jdbc driver 등록 처리 : 해당 database 벤더시가 제공하는 클래스 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println(" 드라이버 등록 성공! ");
			
			//2) 등록된 클래스를 이용해서 db연결
			// 드라이버타입@ip주소:포트번호:db이름(SID)
			// orcl:사용자정의설치 , thin : 자동으로 설치 //ip주소 -> localhost 로 변경해도됨
			// sql에서 초록색 + 세부정보
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "STUDENT", "STUDENT");
			System.out.println(" conn : " + conn); 
			
		
			//3) 쿼리문을 실행할 statement 객체 생성
			stmt = conn.createStatement();
			
			//4) 쿼리문 전송, 실행 결과를 ResultSet으로 받기
			rset = stmt.executeQuery(sql);
		
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
				stmt.close();
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
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '"+ userName +"%'";
		
		try {
			
			//드라이브 로드 oracle.jdbc.driver패키지안에 OracleDriver라는 클래스가 있음
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println(" 드라이버 등록 성공! ");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "STUDENT", "STUDENT");
			System.out.println(" conn : " + conn); //성공하면 conn이 뜸
			
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(sql);
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
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return m;
	}
	
	

}
