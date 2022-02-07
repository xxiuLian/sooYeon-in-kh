package com.uni.member.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.uni.member.controller.MemberController;
import com.uni.member.model.dto.Member;

public class MemberMenu {
	
	private static Scanner sc = new Scanner(System.in);
	private MemberController mController = new MemberController();
	

	public void mainMenu() {
		
		//번호 선택하면 변수에 담기
		
		int choice; //번호 선택용 변수
		do {
			
			System.out.println("\n***** 회원 관리 프로그램 *****");
			System.out.println("1. 회원 전체 조회");
			System.out.println("2. 회원 아이디 조회");
			System.out.println("3. 회원 이름 조회");
			System.out.println("4. 회원 가입");
			System.out.println("5. 회원 정보 변경");
			System.out.println("6. 회원 탈퇴");
			System.out.println("9. 프로그램 끝내기");
			System.out.println("번호 선택 : ");
			
			choice = sc.nextInt();
			switch(choice) {
			case 1:
				mController.selectAll();
				break;
			case 2:
				mController.selectOne(inputMemberId());
				break;
			case 3:
				mController.selectOne1(inputUserName());
				break;
			case 4:
				mController.insertMember(inputMember());
				break;
			case 5:
				mController.updateMember(updateMember());
				break;
			case 6:
				mController.deleteMember(inputMemberId());
				break;
			case 9: 
				System.out.println("정말로 끝내시겠습니까? (Y/N)");
				if('Y' == sc.next().toUpperCase().charAt(0)) {
					return;
				}
				break;
			default:
				System.out.println("번호를 잘못 입력하였습니다.");
				break;
			}
			
		}while(true);
		
	}

	private Member updateMember() {
		Member m = new Member();
		
		m.setUserId(inputMemberId());
		
		System.out.println("암호 : ");
		m.setPassword(sc.next());
		
		System.out.println("이메일 : ");
		m.setEmail(sc.next());
		
		System.out.println("전화번호(-빼고) : ");
		m.setPhone(sc.next());
		
		System.out.println("주소 : ");
		sc.nextLine(); //버퍼 비우기
		m.setAddress(sc.nextLine());
		
		//총 4가지 값 변경 가능한걸로 정했음
		return m;
	}

	private Member inputMember() {
		
		Member m = new Member();
		System.out.println("새로운 회원정보를 입력하세요 >>");
		System.out.println("아이디 : ");
		m.setUserId(sc.next());
		System.out.println("암호 : ");
		m.setPassword(sc.next());
		System.out.println("이름 : ");
		m.setUserName(sc.next());
		System.out.println("나이 : ");
		m.setAge(sc.nextInt());
		System.out.println("성별(M/F) : ");
		m.setGender(sc.next().toUpperCase());
		System.out.println("이메일 : ");
		m.setEmail(sc.next());
		System.out.println("전화번호(-빼고) : ");
		m.setPhone(sc.next());
		System.out.println("주소 : ");
		sc.nextLine(); //버퍼 비우기
		m.setAddress(sc.nextLine());
		System.out.println("취미(,로 공백없이 입력 : ");
		m.setHobby(sc.next());

		return m;
	}

	private String inputUserName() {
		System.out.println("이름 입력 : ");
		
		return sc.next();
	}

	private String inputMemberId() {
		System.out.println("아이디 입력 : ");
		
		return sc.next(); //입력받은거 바루 넘겨줌
	}

	//ArrayList -> List로 변환(util)
	//컨트롤러로 돌렸을때 Member의 list안에 값이 있!!을때 출력
	public void displayMemberList(List<Member> list) {
		System.out.println("\n조회된 전체 회원정보는 다음과 같습니다.");
		System.out.println("\n아이디\t이름\t성별\t나이\t이메일\t전화번호\t주소\t취미\t가입일");
		System.out.println("----------------------------------------------------------");
		
		for(Member m : list) {
			System.out.println(m);
		}

	}
	//컨트롤러로 돌렸을때 Member의 list안에 값이 없!!을때 출력
	public void displayError(String message) {
		System.out.println("서비스 요청 처리 실패 : " + message);
		
	}

	public void displayMemberList(Member m) {
		System.out.println("\n조회된 회원정보는 다음과 같습니다.");
		System.out.println("\n아이디\t이름\t성별\t나이\t이메일\t전화번호\t주소\t취미\t가입일");
		System.out.println("----------------------------------------------------------");
		
		System.out.println(m);
	}

	//컨트롤러 2.
	public void displaySuccess(String message) {
		System.out.println("서비스 요청 결과 : " + message);
		
	}

}
