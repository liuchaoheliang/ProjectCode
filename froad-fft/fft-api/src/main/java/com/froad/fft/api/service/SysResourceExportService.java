package com.froad.fft.api.service;

import java.util.List;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.SysResourceDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 资源
 * @author FQ
 *
 */
public interface SysResourceExportService {
	
	/**
	 * 保存
	 * @param sysResourceDto
	 * @return
	 */
	public Long addSysResource(ClientAccessType clientAccessType,ClientVersion clientVersion,SysResourceDto sysResourceDto)throws FroadException;
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public SysResourceDto findSysResourceById(ClientAccessType clientAccessType,ClientVersion clientVersion,Long id)throws FroadException;
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<SysResourceDto> findAllSysResource(ClientAccessType clientAccessType,ClientVersion clientVersion)throws FroadException;
	
	/**
	 * 查询所有顶级资源
	 * @return
	 */
	public List<SysResourceDto> findRootSysResourceList(ClientAccessType clientAccessType,ClientVersion clientVersion)throws FroadException;
	
	/**
	 * 根据客户端ID 查找资源
	 * @param clientId
	 * @return
	 */
	public List<SysResourceDto> findSysResourceByClientId(ClientAccessType clientAccessType,ClientVersion clientVersion,Long clientId)throws FroadException;
	
	/**
	 * 分页查询
	 * @param pageDto
	 * @return
	 */
	public PageDto findSysResourceByPage(ClientAccessType clientAccessType,ClientVersion clientVersion,PageDto pageDto)throws FroadException;

	/**
	 * @author larry
	 * @param management
	 * @param version10
	 * @param sysResourceDto
	 * <p>更新系统资源</p>
	 * @return
	 */
	public Boolean updateSysResource(ClientAccessType management,
			ClientVersion version10, SysResourceDto sysResourceDto)throws FroadException;
}
