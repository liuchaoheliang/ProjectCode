package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.MemberMapper;
import com.froad.fft.persistent.entity.Member;

public class MemberMapperImpl implements MemberMapper {

	@Resource
	private MemberMapper memberMapper;
	
	@Override
	public Long saveMember(Member member) {
		memberMapper.saveMember(member);
		return member.getId();
	}

	@Override
	public Boolean updateMemberById(Member member) {
		return memberMapper.updateMemberById(member);
	}

	@Override
	public Member selectMemberById(Long id) {
		return memberMapper.selectMemberById(id);
	}

}
