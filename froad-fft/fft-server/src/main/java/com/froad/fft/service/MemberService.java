package com.froad.fft.service;

import com.froad.fft.common.AppException;
import com.froad.fft.persistent.entity.Member;

public interface MemberService {

	public Long saveMember(Member member);
	public Boolean updateMemberById(Member member);
	public Member selectMemberById(Long id);
	
	public Member queryByMemberCode(Long memberCode)throws AppException;
}
