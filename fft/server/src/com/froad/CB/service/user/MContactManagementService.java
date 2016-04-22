package com.froad.CB.service.user;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.AppException.AppException;
import com.froad.CB.po.user.Contacter;

@WebService
public interface MContactManagementService {

	/**
	 * 方法描述：增加联系人
	 * 
	 * @param: Contacter con
	 * @return:
	 * @version: 1.0
	 * @author: 孙庆亚 sunqingya@f-road.com.cn
	 * @time: Apr 17, 2012 10:02:47 AM
	 */
	public Contacter addContacter(Contacter con) throws AppException;

	/**
	 * 方法描述：删除联系人
	 * 
	 * @param: Contacter con
	 * @return:
	 * @version: 1.0
	 * @author: 孙庆亚 sunqingya@f-road.com.cn
	 * @time: Apr 17, 2012 10:02:50 AM
	 */
	public Contacter delContacter(Contacter con) throws AppException;

	/**
	 * 方法描述：更改联系人
	 * 
	 * @param: Contacter con
	 * @return:
	 * @version: 1.0
	 * @author: 孙庆亚 sunqingya@f-road.com.cn
	 * @time: Apr 17, 2012 10:02:53 AM
	 */
	public Contacter updContacter(Contacter con) throws AppException;

	/**
	 * 方法描述：查找联系人
	 * 
	 * @param: Contacter con
	 * @return:
	 * @version: 1.0
	 * @author: 孙庆亚 sunqingya@f-road.com.cn
	 * @time: Apr 17, 2012 10:02:55 AM
	 */
	public List<Contacter> getContacterList(Contacter con) throws AppException;
}
