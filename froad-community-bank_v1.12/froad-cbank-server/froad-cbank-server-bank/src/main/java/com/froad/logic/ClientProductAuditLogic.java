package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.ClientProductAudit;

/**
 * 
 * <p>@Title: ClientProductAuditLogic.java</p>
 * <p>Description: 描述 </p> 商品审核配置Logic接口
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月26日
 */
public interface ClientProductAuditLogic {


    /**
     * 增加 ClientProductAudit
     * @param clientProductAudit
     * @return Long    主键ID
     */
	public Long addClientProductAudit(ClientProductAudit clientProductAudit) throws FroadServerException, Exception;



    /**
     * 删除 ClientProductAudit
     * @param clientProductAudit
     * @return Boolean    是否成功
     */
	public Boolean deleteClientProductAudit(ClientProductAudit clientProductAudit) throws FroadServerException, Exception;



    /**
     * 修改 ClientProductAudit
     * @param clientProductAudit
     * @return Boolean    是否成功
     */
	public Boolean updateClientProductAudit(ClientProductAudit clientProductAudit) throws FroadServerException, Exception;


	/**
	 * 根据clientId+orgCode查询商户审核配置信息
	 * @param clientId
	 * @param orgCode
	 * @param productType商品类型(1团购，2预售，3名优特惠，4在线积分兑换，5网点礼品)
	 * @return ClientMerchantAudit
	 */
	public ClientProductAudit findClientProductAuditByOrgCode(String clientId,String orgCode,String productType);

	

    /**
     * 查询 ClientProductAudit
     * @param clientProductAudit
     * @return List<ClientProductAudit>    结果集合 
     */
	public List<ClientProductAudit> findClientProductAudit(ClientProductAudit clientProductAudit);



    /**
     * 分页查询 ClientProductAudit
     * @param page
     * @param clientProductAudit
     * @return Page<ClientProductAudit>    结果集合 
     */
	public Page<ClientProductAudit> findClientProductAuditByPage(Page<ClientProductAudit> page, ClientProductAudit clientProductAudit);



}