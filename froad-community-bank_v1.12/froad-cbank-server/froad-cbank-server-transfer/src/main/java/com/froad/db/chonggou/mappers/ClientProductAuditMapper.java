package com.froad.db.chonggou.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.chonggou.entity.ClientProductAudit;
import com.froad.db.mysql.bean.Page;


/**
 * 
 * <p>@Title: ClientProductAuditMapper.java</p>
 * <p>Description: 描述 </p> 商品审核配置Mapper
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月26日
 */
public interface ClientProductAuditMapper {


    /**
     * 增加 ClientProductAudit
     * @param clientProductAudit
     * @return Long    主键ID
     */
	public Long addClientProductAudit(ClientProductAudit clientProductAudit);



    /**
     * 批量增加 ClientProductAudit
     * @param List<ClientProductAudit>
     * @return Boolean    是否成功
     */
	public Boolean addClientProductAuditByBatch(List<ClientProductAudit> clientProductAuditList);



    /**
     * 删除 ClientProductAudit
     * @param clientProductAudit
     * @return Boolean    是否成功
     */
	public Boolean deleteClientProductAudit(ClientProductAudit clientProductAudit);



    /**
     * 修改 ClientProductAudit
     * @param clientProductAudit
     * @return Boolean    是否成功
     */
	public Boolean updateClientProductAudit(ClientProductAudit clientProductAudit);



    /**
     * 查询一个 ClientProductAudit
     * @param clientProductAudit
     * @return ClientProductAudit    返回结果
     */
	public ClientProductAudit findClientProductAuditById(Long id);



    /**
     * 查询 ClientProductAudit
     * @param clientProductAudit
     * @return List<ClientProductAudit>    返回结果集
     */
	public List<ClientProductAudit> findClientProductAudit(ClientProductAudit clientProductAudit);



    /**
     * 分页查询 ClientProductAudit
     * @param page 
     * @param clientProductAudit
     * @return List<ClientProductAudit>    返回分页查询结果集
     */
	public List<ClientProductAudit> findByPage(Page page, @Param("clientProductAudit")ClientProductAudit clientProductAudit);



}