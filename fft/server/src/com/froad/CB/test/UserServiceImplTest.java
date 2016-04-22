package com.froad.CB.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.AppException.AppException;
import com.froad.CB.dao.user.impl.UserAppendDaoImpl;
import com.froad.CB.dao.user.impl.UserDaoImpl;
import com.froad.CB.po.user.User;
import com.froad.CB.po.user.UserAppend;
import com.froad.CB.service.user.impl.MUserServiceImpl;
import com.pay.user.helper.CreateChannel;
import com.pay.user.helper.MemberType;


	/**
	 * 类描述：用户service测试类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 24, 2013 1:49:15 PM 
	 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class UserServiceImplTest {
	
	@Resource
	private MUserServiceImpl mUserServiceImpl;
	
	@Resource
	private UserAppendDaoImpl appendDaoImpl;

	@Resource
	private UserDaoImpl udi;
	
	@Test
	public void queryUserById(){
		String userId="1001001";
		try {
			System.out.println("============开始测试============");
			User user=mUserServiceImpl.queryUserAllByUserID(userId);
			System.out.println(user);
		} catch (AppException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void addUserAppend(){
		UserAppend userAppend=new UserAppend();
		userAppend.setBlance("");
		userAppend.setUserID("342312325521");
		userAppend.setBlance("343231");
		int n=appendDaoImpl.addUserAppend(userAppend);
		System.out.println("============"+n);
	}

	@Test
	public void getUserAppendByUserId(){
		String userId="342312325521";
		UserAppend append=appendDaoImpl.getUserAppendByUserId(userId);
		System.out.println(append.getUserID()+"------"+append.getBlance());
	}
	
	
	@Test
	public void updateUserAppendByUserId(){
		UserAppend append=new UserAppend();
		append.setUserID("342312325521");
		append.setBlance("1221111");
		append.setState("测试数据");
		
		if(appendDaoImpl.updateUserAppend(append)){
			System.out.println("修改数据成功");
		}else{
			System.out.println("修改数据失败");
		}
		
	}

	
	
	@Test
	public void register(){
		User u = new User();
//		u.setRegisterIP("127.0.0.1");
//		u.setUsername("liuchao");
		u.setUname("分分通");
		u.setPassword("111111");
		u.setCreateChannel(CreateChannel.FFT_MC.getValue());
		u.setMobilephone("13038389991");
		u.setEmail(null);
		u.setMemberType(MemberType.PERSONAL.getValue());
//		udi.register(u);
		mUserServiceImpl.clientRegister(u, false);
	}
	
	@Test
	public void queryUserByLoginID(){
		User u=udi.queryUserByMobilephoneOrUsername("13038389991");
		System.out.println("++++++"+u.getRespCode());
	}
	@Test
	public void updateUserByMemberCode(){
		User u = new User();
		u.setMemberCode(50000000057L);
		//u.setUname("分分通");
		u.setMobilephone("18290258138");
//		u.setAge(10);
//		u.setStatus("0");
//		u.setMemberType(0);
//		u.setSex("1");
		udi.updUserInfo(u);
	}
	
	@Test
	public void selectUserByPhoneNumber(){
		udi.selectUserMobilephone("13527459170");
	}
	
	@Test
	public void login(){
		mUserServiceImpl.loginPassAccountSys("kaka201011", "111111", "127.0.0.1");
	}
	
	@Test
	public void queryUserByUserId(){
		try {
			mUserServiceImpl.queryUserAllByUserID("a59530a3-f661-4dab-b662-0674cceba224");
		} catch (AppException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getPower(){
//		udi.queryAuthoritiesByUsername("fftfft");
//		udi.queryAuthorities();

		//	udi.queryUserAllByUserID("10000014439");
		udi.forgetPwdToGetIt(1L);	

	//	udi.queryUserAllByUserID("10000014439");
		//udi.forgetPwdToGetIt(1L);
		User user = new User();
		user.setUserID("50000000060");
		user.setPointActivateFailureCount(2);
		user.setIsPointActivateAccountLocked("g");
		udi.updateUserLastInfoByUsername(user);

	}
}
