package com.froad.fft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.common.AppException;
import com.froad.fft.persistent.api.MemberMapper;
import com.froad.fft.persistent.entity.Member;
import com.froad.fft.service.MemberService;
import com.froad.fft.thirdparty.request.userengine.UserEngineFunc;
import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;

@Service("memberServiceImpl")
public class MemberServiceImpl implements MemberService {

	private static Logger log = Logger.getLogger(MemberServiceImpl.class);
	
	@Resource
	private MemberMapper memberMapper;
	
	@Resource
	private UserEngineFunc userEngineFunc;
	
	@Override
	public Long saveMember(Member member) {
		return memberMapper.saveMember(member);
	}

	@Override
	public Boolean updateMemberById(Member member) {
		if(member.getId() == null){
			log.error("更新对象缺少必要Id字段值");
			return false;
		}
		return memberMapper.updateMemberById(member);
	}

	@Override
	public Member selectMemberById(Long id) {
		if(id == null){
			log.error("查询数据id为空");
			return null;
		}
		return memberMapper.selectMemberById(id);
	}
		

	@Override
	public Member queryByMemberCode(Long memberCode) throws AppException{
		UserResult userResult=userEngineFunc.queryByMemberCode(memberCode);
		if(userResult==null){
			log.error("用户系统接口调用异常");
			throw new AppException("用户系统接口调用异常");
		}
		if(!userResult.getResult()){
			log.error("用户信息查询失败");
			throw new AppException("用户信息查询失败，原因："+userResult.getErrorMsg());
		}
		List<UserSpecDto> userList=userResult.getUserList();
		if(userList==null||userList.size()==0){
			log.warn("memberCode为："+memberCode+"的用户不存在");
			return null;
		}
		return this.toMember(userList.get(0));
	}

	private Member toMember(UserSpecDto userDto){
		Member member=new Member();
		member.setMemberCode(userDto.getMemberCode());
		member.setUsername(userDto.getLoginID());
		member.setMobile(userDto.getMobile());
		return member;
	}
}
