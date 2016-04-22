package com.froad.db.ahui.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.chonggou.entity.OutletCG;
import com.froad.fft.persistent.entity.OutleBank;

public interface OutletBankMapper {
	
	
	/**
	 *  根据门店ID查询机构信息
	  * @Title: queryByOutletId
	  * @Description: TODO
	  * @author: share 2015年5月2日
	  * @modify: share 2015年5月2日
	  * @param @param outletId
	  * @param @return    
	  * @return OutleBank    
	  * @throws
	 */
	public OutleBank queryByOutletId(@Param("outletId")Long outletId);
	
	
	public int findByOutletId(@Param("merchatId")Long merchatId);
	
	/**
	 * 根据机构号查询
	 * @Title: queryByBankOrg 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年5月2日
	 * @modify: froad-huangyihao 2015年5月2日
	 * @param bankOrg
	 * @return
	 * @throws
	 */
	public OutleBank queryByBankOrg(String bankOrg);
	
	
	public OutleBank selectByTransId(@Param("id")Long id);
	
	
	public List<OutleBank> queryAllList();
	
	public OutleBank selectByOutletId(Long id);
}
