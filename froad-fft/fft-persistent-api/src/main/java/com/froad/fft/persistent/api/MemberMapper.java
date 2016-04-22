package com.froad.fft.persistent.api;

import com.froad.fft.persistent.entity.Member;

public interface MemberMapper {

	//-----基本操作| 开始
	public Long saveMember(Member member);
	public Boolean updateMemberById(Member member);
	public Member selectMemberById(Long id);
	//-----基本操作| 结束
}
