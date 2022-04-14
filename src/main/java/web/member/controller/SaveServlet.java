package web.member.controller;

import static core.util.CommonUtil.json2Pojo;
import static core.util.CommonUtil.writePojo2Json;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.pojo.Core;
import core.util.CommonUtil;
import web.member.entity.Member;
import web.member.service.MemberService;

@WebServlet("/member/save")
public class SaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService service;

	@Override
	public void init() throws ServletException {
		service = CommonUtil.getBean(getServletContext(), MemberService.class);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		final Member member = json2Pojo(request, Member.class);
		final Core core = new Core();
		if (member == null) {
			core.setMessage("無會員資訊");
			core.setSuccessful(false);
		} else {
			core.setSuccessful(service.save(member));
		}
		writePojo2Json(response, core);
	}
}
