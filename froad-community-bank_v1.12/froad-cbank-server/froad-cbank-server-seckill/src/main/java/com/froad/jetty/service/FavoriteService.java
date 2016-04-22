package com.froad.jetty.service;

import com.froad.jetty.vo.FavoriteRequestVo;
import com.froad.jetty.vo.ResponseVo;

/**
 * 秒杀商品收藏业务
 * 
 * @author wangzhangxu
 * @date 2015年5月3日 下午6:20:13
 * @version v1.0
 */
public interface FavoriteService {

	/**
	 * 收藏商品
	 * 
	 * @param reqVo
	 * 
	 * @return ResponseVo
	 * 
	 * @author wangzhangxu
	 * @date 2015年5月3日 下午6:20:13
	 */
	public ResponseVo collect(FavoriteRequestVo reqVo);
	
	/**
	 * 取消收藏商品
	 * 
	 * @param reqVo
	 * 
	 * @return Boolean
	 * 
	 * @author wangzhangxu
	 * @date 2015年5月3日 下午6:20:13
	 */
	public Boolean delete(FavoriteRequestVo reqVo);
	
	/**
	 * 是否已收藏商品
	 * 
	 * @param reqVo
	 * 
	 * @return ResponseVo
	 * 
	 * @author wangzhangxu
	 * @date 2015年5月3日 下午6:20:13
	 */
	public Boolean isCollected(FavoriteRequestVo reqVo);
	
	
}
