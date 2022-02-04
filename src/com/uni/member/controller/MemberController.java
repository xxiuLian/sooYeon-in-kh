package com.uni.member.controller;

import java.util.ArrayList;

import com.uni.member.model.dao.MemberDao;
import com.uni.member.model.dto.Member;
import com.uni.member.view.MemberMenu;

public class MemberController {
	// view 와 dao를 연결해주는 객체
	// view <-> controller <-> dao <-> db

	public void selectAll() {
		
		MemberMenu menu = new MemberMenu();
		ArrayList<Member> list = new MemberDao().selectAll();
		
		if(!list.isEmpty()) { //list에 값이 있다면 //이미 만들어진 상태이기 때문에 != null로 비교하는게 아니라 안이 비었는지를 비교해야함
			menu.displayMemberList(list);
		}else { //list에 값이 없다면
			menu.displayError("해당하는 데이터가 없습니다.");
		}
		
	}

	public void selectOne(String memberId) {
		MemberMenu menu = new MemberMenu();
		Member m = new MemberDao().selectOne(memberId);
		
		if(m != null) {
			menu.displayMemberList(m);
		}else {
			menu.displayError("해당하는 데이터가 없습니다.");
		}
		
	}

	public void selectOne1(String userName) {
		MemberMenu menu = new MemberMenu();
		Member m = new MemberDao().selectOne1(userName);
		
		if(m != null) {
			menu.displayMemberList(m);
		}else {
			menu.displayError("해당하는 데이터가 없습니다.");
		}
		
	}

}
