package com.froad.logic;

import java.util.List;

import com.froad.exceptions.FroadServerException;
import com.froad.po.Org;

/**
 * 
 * <p>@Title: ArtificialPersonLogic.java</p>
 * <p>Description: 描述 </p> 法人行社管理员Logic接口类
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月17日
 */
public interface ArtificialPersonLogic {


    /**
     * 批量生成法人行社管理员接口(一个法人行社orgCode只能生成一个管理员用户)
     * @param clientId 客户端id
     * @param orgCodes 法人行社orgCode集合
     * @param defaultPassword 初始密码
     * @param prefix 登录名前缀
     * @return 批量生成失败的orgCode及名称(orgCode:orgName;)  生成成功返回OK  审核异常返回ERROR 
     */
	public String addArtificialPerson(String clientId,List<String> orgCodes,String defaultPassword,String prefix) throws FroadServerException, Exception;



    /**
     * 批量删除法人行社管理员账号
     * @param clientId 客户端id
     * @param orgCodes 法人行社orgCode集合
     * @return Boolean    是否成功
     */
	public Boolean deleteArtificialPerson(String clientId,List<String> orgCodes) throws FroadServerException, Exception;



    /**
     * 批量重置法人行社管理员密码
     * @param clientId 客户端id
     * @param orgCodes 法人行社orgCode集合
     * @param defaultPassword 初始密码
     * @return Boolean    是否成功
     */
	public Boolean updateArtificialPerson(String clientId,List<String> orgCodes,String defaultPassword) throws FroadServerException, Exception;


	
	/**
     * 法人行社列表接口
     * @param clientId 客户端id
     * @param orgCode 当前登录人所属的orgCode
     * @return List<Org> 法人行社org对象
     */
	public List<Org> findArtificialPerson(String clientId,String orgCode);
	
	
    /**
     * 已生成管理员的法人行社列表接口
     * @param clientId 客户端id
     * @param orgCode 当前登录人所属的orgCode
     * @return List<Org> 已生成的法人行社org对象
     */
	public List<Org> findArtificialPersonByAdd(String clientId,String orgCode);

	/**
     * 未生成管理员的法人行社列表接口
     * @param clientId 客户端id
     * @param orgCode 当前登录人所属的orgCode
     * @return List<Org> 未生成的法人行社org对象
     */
	public List<Org> findArtificialPersonByNotAdd(String clientId,String orgCode);
	

}