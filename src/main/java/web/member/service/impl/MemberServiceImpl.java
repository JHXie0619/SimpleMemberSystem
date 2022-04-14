package web.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.member.dao.MemberDao;
import web.member.entity.Member;
import web.member.mapper.MemberMapper;
import web.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberMapper memberMapper;
	
	@Transactional
	@Override
	public Member register(Member member) {
		if (member.getUsername() == null) {
			member.setMessage("帳號未輸入");
			member.setSuccessful(false);
			return member;
		}
		
		if (member.getPassword() == null) {
			member.setMessage("密碼未輸入");
			member.setSuccessful(false);
			return member;
		}
		
		if (member.getNickname() == null) {
			member.setMessage("暱稱未輸入");
			member.setSuccessful(false);
			return member;
		}
		
		try {
//			beginTransaction();
			if (memberMapper.selectByUsername(member.getUsername()) != null) {
				member.setMessage("帳號重複");
				member.setSuccessful(false);
//				rollback();
				return member;
			}
			
			member.setRoleId(2);
			final int resultCount = memberMapper.insert(member);
			if (resultCount < 1) {
				member.setMessage("註冊錯誤，請聯絡管理員!");
				member.setSuccessful(false);
//				rollback();
				return member;
			}
//			commit();
		} catch (Exception e) {
//			rollback();
			e.printStackTrace();
		}
		
		member.setMessage("註冊成功");
		member.setSuccessful(true);
		return member;
	}

	@Override
	public Member login(Member member) {
		final String username = member.getUsername();
		final String password = member.getPassword();
		
		if (username == null) {
			member.setMessage("帳號未輸入");
			member.setSuccessful(false);
			return member;
		}
		
		if (password == null) {
			member.setMessage("密碼未輸入");
			member.setSuccessful(false);
			return member;
		}
		
		member = memberMapper.selectForLogin(username, password);
		if (member == null) {
			member = new Member();
			member.setMessage("帳號或密碼錯誤");
			member.setSuccessful(false);
			return member;
		}
		
		member.setMessage("登入成功");
		member.setSuccessful(true);
		return member;
	}

	@Transactional
	@Override
	public Member edit(Member member) {
		final Member oMember = memberMapper.selectByUsername(member.getUsername());
		member.setPass(oMember.getPass());
		member.setRoleId(oMember.getRoleId());
		member.setUpdater(member.getUsername());
		final int resultCount = memberMapper.update(member);
		member.setSuccessful(resultCount > 0);
		member.setMessage(resultCount > 0 ? "修改成功" : "修改失敗");
		return member;
	}

	@Override
	public List<Member> findAll() {
		return memberMapper.selectAll();
	}

	@Transactional
	@Override
	public boolean remove(Integer id) {
		try {
//			beginTransaction();
			final int resultCount = memberMapper.deleteById(id); 
//			commit();
			return resultCount > 0;
		} catch (Exception e) {
//			rollback();
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	@Override
	public boolean save(Member member) {
		return memberMapper.update(member) > 0;
	}
}
