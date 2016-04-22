package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.Goods;

public interface GoodsDao {

	
	/**
	  * 方法描述：添加商品
	  * @param: Goods
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:16:03 PM
	  */
	public Integer addGoods(Goods goods);
	
	
	/**
	  * 方法描述：查询商品
	  * @param: id
	  * @return: Goods
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:16:23 PM
	  */
	public Goods getGoodsById(Integer id);
	
	
	/**
	  * 方法描述：查询商品库存
	  * @param: id 商品编号
	  * @return: 商品库存
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 14, 2013 2:54:45 PM
	  */
	public int getStoreById(Integer id);
	
	
	/**
	  * 方法描述：查询商品
	  * @param: merchantId
	  * @return: List<Goods>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 20, 2013 2:19:22 PM
	  */
	public List<Goods> getGoodsByMerchantId(String merchantId);
	
	
	/**
	  * 方法描述：分页多条件查询商品
	  * @param: Goods
	  * @return: Goods
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 21, 2013 10:04:20 AM
	  */
	public Goods getGoodsByPager(Goods goods);
	
	
	/**
	  * 方法描述：修改商品状态
	  * @param: id,state
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:16:38 PM
	  */
	public void updateStateById(Integer id,String state);
	
	
	/**
	  * 方法描述：修改商品信息
	  * @param: Goods
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:17:58 PM
	  */
	public void updateById(Goods goods);
	
	
	/**
	  * 方法描述：删除商品
	  * @param: id
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:18:31 PM
	  */
	public void deleteById(Integer id);
	
	
	/**
	  * 方法描述：减库存
	  * @param: reduceNumber 减的数量
	  * @param: id 商品编号
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 9, 2013 3:12:39 PM
	  */
	public void reduceStoreById(int reduceNumber,Integer id);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过goodsRackId+table查询商品名</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-11-26 下午03:03:12 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public String useGoodsRackIdAndTableGetGoodsName(Goods goods);
}
