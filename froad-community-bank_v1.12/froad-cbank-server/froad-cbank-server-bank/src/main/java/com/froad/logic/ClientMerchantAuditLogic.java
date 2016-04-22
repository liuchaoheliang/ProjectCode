package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.ClientMerchantAudit;

/**
 * 
 * <p>@Title: ClientMerchantAuditLogic.java</p>
 * <p>Description: 描述 </p> 商户审核配置管理Logic接口
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月26日
 */
public interface ClientMerchantAuditLogic {


    /**
     * 增加 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return Long    主键ID
     */
	public Long addClientMerchantAudit(ClientMerchantAudit clientMerchantAudit) throws FroadServerException, Exception;



    /**
     * 删除 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return Boolean    是否成功
     */
	public Boolean deleteClientMerchantAudit(ClientMerchantAudit clientMerchantAudit) throws FroadServerException, Exception;



    /**
     * 修改 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return Boolean    是否成功
     */
	public Boolean updateClientMerchantAudit(ClientMerchantAudit clientMerchantAudit) throws FroadServerException, Exception;


	/**
	 * 根据clientId+orgCode查询商户审核配置信息
	 * @param clientId
	 * @param orgCode
	 * @param type 1-审核 2-商户重置密码
	 * @return
	 */
	public ClientMerchantAudit findClientMerchantAuditByOrgCode(String clientId,String orgCode,String type);
	
	

    /**
     * 查询 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return List<ClientMerchantAudit>    结果集合 
     */
	public List<ClientMerchantAudit> findClientMerchantAudit(ClientMerchantAudit clientMerchantAudit);



    /**
     * 分页查询 ClientMerchantAudit
     * @param page
     * @param clientMerchantAudit
     * @return Page<ClientMerchantAudit>    结果集合 
     */
	public Page<ClientMerchantAudit> findClientMerchantAuditByPage(Page<ClientMerchantAudit> page, ClientMerchantAudit clientMerchantAudit);



}