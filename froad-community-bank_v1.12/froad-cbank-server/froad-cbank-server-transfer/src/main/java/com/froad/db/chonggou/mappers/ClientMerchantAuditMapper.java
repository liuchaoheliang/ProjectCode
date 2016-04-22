package com.froad.db.chonggou.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.chonggou.entity.ClientMerchantAudit;


/**
 * 
 * <p>@Title: ClientMerchantAuditMapper.java</p>
 * <p>Description: 描述 </p> 商户审核配置Mapper
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月26日
 */
public interface ClientMerchantAuditMapper {


    /**
     * 增加 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return Long    主键ID
     */
	public Long addClientMerchantAudit(ClientMerchantAudit clientMerchantAudit);



    /**
     * 批量增加 ClientMerchantAudit
     * @param List<ClientMerchantAudit>
     * @return Boolean    是否成功
     */
	public Boolean addClientMerchantAuditByBatch(List<ClientMerchantAudit> clientMerchantAuditList);



    /**
     * 删除 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return Boolean    是否成功
     */
	public Boolean deleteClientMerchantAudit(ClientMerchantAudit clientMerchantAudit);



    /**
     * 修改 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return Boolean    是否成功
     */
	public Boolean updateClientMerchantAudit(ClientMerchantAudit clientMerchantAudit);



    /**
     * 查询一个 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return ClientMerchantAudit    返回结果
     */
	public ClientMerchantAudit findClientMerchantAuditById(Long id);



    /**
     * 查询 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return List<ClientMerchantAudit>    返回结果集
     */
	public List<ClientMerchantAudit> findClientMerchantAudit(ClientMerchantAudit clientMerchantAudit);

}