package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.Qrcode;

/**
 * 二维码维护接口
  * @ClassName: QrcodeMapper
  * @Description: TODO
  * @author zhangxiaohua 2015-1-8
  * @modify zhangxiaohua 2015-1-8
 */
public interface QrcodeMapper {
	
	/**
	 * 新增
	  * @Title: insert
	  * @Description: TODO
	  * @author: zhangxiaohua 2015-1-8
	  * @modify: zhangxiaohua 2015-1-8
	  * @param @param t
	  * @param @return    
	  * @return Long    
	  * @throws
	 */
	public Long insert(Qrcode t) ;

	/**
	 * 修改
	  * @Title: updateById
	  * @Description: TODO
	  * @author: zhangxiaohua 2015-1-8
	  * @modify: zhangxiaohua 2015-1-8
	  * @param @param t
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public boolean updateById(Qrcode t) ;
	
	/**
	 * 删除二维码
	 * @param clientAccessType
	 * @param clientVersion
	 * @param cbQrcodeDto
	 * @return boolean
	 */
	public boolean deleteQrcodeById(Qrcode qrcode) ;
	
	/**
	 * 根据二维码检索关键字检索二维码
	 * @param clientAccessType
	 * @param clientVersion
	 * @param keyword 检索二维码关键字
	 * @return CbQrcodeDto
	 */
	public Qrcode selectById(Long id) ;

	/**
	 * 根据关键字检索二维码
	  * @Title: selectByKeyword
	  * @Description: TODO
	  * @author: zhangxiaohua 2015-1-9
	  * @modify: zhangxiaohua 2015-1-9
	  * @param @param clientId
	  * @param @param keyword
	  * @param @return    
	  * @return Qrcode    
	  * @throws
	 */
	public Qrcode selectByKeyword(String keyword);

	/**
	 * 根据多个查询条件查询
	  * @Title: selectByCondition
	  * @Description: TODO
	  * @author: zhangxiaohua 2015-1-9
	  * @modify: zhangxiaohua 2015-1-9
	  * @param @param t
	  * @param @return    
	  * @return List<Qrcode>    
	  * @throws
	 */
	public List<Qrcode> selectByCondition(Qrcode t);


}

