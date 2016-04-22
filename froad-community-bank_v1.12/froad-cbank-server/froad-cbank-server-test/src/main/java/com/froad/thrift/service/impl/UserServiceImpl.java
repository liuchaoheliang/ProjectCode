package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.thrift.TException;

import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.monitor.vo.BizMethodInfo;
import com.froad.thrift.monitor.vo.BizMethodInvokeInfo;
import com.froad.thrift.service.UserService;
import com.froad.thrift.vo.UserVo;

public class UserServiceImpl extends BizMonitorBaseService implements UserService.Iface{

	public UserServiceImpl(String name, String version) {
		super(name, version);
	}
	
	@Override
	public void addUser(UserVo userVo) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserVo getById(long id) throws TException {
	    
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UserVo u = new UserVo();
		u.setId(1);
		u.setName("fangquan");
		u.setAge(26);
		u.setSex(true);
	     
	    return u;
	}

	@Override
	public List<UserVo> getAll() throws TException {
		
		List<UserVo> list=new ArrayList<UserVo>(); 
		UserVo u=new UserVo();
		u.setId(1);
		u.setName("fangquan");
		u.setAge(26);
		u.setSex(true);
		list.add(u);
		
		return list;
	}

}
