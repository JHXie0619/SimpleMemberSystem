package web.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import web.member.entity.Member;

public interface MemberMapper {
	int insert(Member member);
	int deleteById(Integer id);
	int update(Member member);
	Member selectById(Integer id);
	List<Member> selectAll();
	Member selectByUsername(String username);
	Member selectForLogin(@Param("username")String username,@Param("password")String password);
}
