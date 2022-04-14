package web.member.controller;

import static core.util.CommonUtil.json2Pojo;
import static core.util.CommonUtil.writePojo2Json;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.pojo.Core;
import web.member.entity.Member;
import web.member.service.MemberService;

@RestController
@RequestMapping("member")
public class MemberRestController {
	@Autowired
	private MemberService service;
	
	@PostMapping("checkPassword")
	public Core checkPassword(@RequestBody Member reqmember,HttpSession session) {
		
		final Member member = (Member) session.getAttribute("member");
		final Core core = new Core();
		if (member == null) {
			core.setMessage("無會員資訊");
			core.setSuccessful(false);
		} else {
			final String currentPassword = member.getPassword();
			if (Objects.equals(reqmember.getPassword(), currentPassword)) {
				core.setSuccessful(true);
			} else {
				core.setMessage("舊密碼錯誤");
				core.setSuccessful(false);
			}
		}
		return core;
	}
	
	@PostMapping("edit")
	public Member edit(@RequestBody Member member,HttpSession session) {
		final String username = ((Member) session.getAttribute("member")).getUsername();
		member.setUsername(username);
		return service.edit(member);
	}
	
	@PostMapping("getInfoAll")
	public List<Member> getInfoAll(){
		return service.findAll();
	}
	
	@PostMapping("getInfo")
	public Member getInfo(HttpSession session) {
		Member member = (Member) session.getAttribute("member");
		if (member == null) {
			member = new Member();
			member.setMessage("無會員資訊");
			member.setSuccessful(false);
		} else {
			member.setSuccessful(true);
		}
		return member;
	}
	
	@PostMapping("login")
	public Member login(@RequestBody Member member,HttpServletRequest request) {
		if (member == null) {
			member = new Member();
			member.setMessage("無會員資訊");
			member.setSuccessful(false);
			return member;
		}
		
		member = service.login(member);
		if (member.isSuccessful()) {
			if (request.getSession(false) != null) {
				request.changeSessionId();
			}
			final HttpSession session = request.getSession();
			session.setAttribute("loggedin", true);
			session.setAttribute("member", member);
		}
		return member;
	}
	
	@GetMapping("logout")
	public void logout(HttpSession session) {
		session.invalidate();
	}
	
	@PostMapping("register")
	public Member register(@RequestBody Member member) {
		if (member == null) {
			member = new Member();
			member.setMessage("無會員資訊");
			member.setSuccessful(false);
			return member;
		}

		member = service.register(member);
		return member;
	}
	
	@PostMapping("remove")
	public Core remove(@RequestBody Member member) {
		final Integer id = member.getId();
		final Core core = new Core();
		if (id == null) {
			core.setMessage("無id");
			core.setSuccessful(false);
		} else {
			core.setSuccessful(service.remove(id));
		}
		return core;
	}
	
	@PostMapping("save")
	public Core save(@RequestBody Member member) {
		final Core core = new Core();
		if (member == null) {
			core.setMessage("無會員資訊");
			core.setSuccessful(false);
		} else {
			core.setSuccessful(service.save(member));
		}
		return core;
	}
}
