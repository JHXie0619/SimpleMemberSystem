package web.member.controller;

import static core.util.Constants.PREFIX_WEB_INF;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import web.member.entity.Member;
import web.member.service.MemberService;

@Controller
@RequestMapping("member")
public class MemberController {
	@Autowired
	private MemberService service;
	
	@GetMapping("manage")
	public String manage(Model model) {
		List<Member> memberList = service.findAll();
		model.addAttribute("memberList", memberList);
		return PREFIX_WEB_INF + "/member/manage.jsp";
	}
}
