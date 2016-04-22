
	 /**
  * 文件名：RefundsMapperImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年3月27日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;
import com.froad.fft.persistent.api.RefundsMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Refunds;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月27日 下午3:02:51 
 */
public class RefundsMapperImpl implements RefundsMapper{

		@Resource 
		private RefundsMapper refundsMapper;
	
		@Override
		public Long saveRefunds(Refunds refunds) {
			refundsMapper.saveRefunds(refunds);
			return refunds.getId();
		}

		

		@Override
		public Boolean updateRefundsById(Refunds refunds) {
			return refundsMapper.updateRefundsById(refunds);
		}

		

		@Override
		public Refunds selectRefundsById(Long id) {
			return refundsMapper.selectRefundsById(id);
		}

		
		@Override
		public List<Refunds> selectRefundsByPage(Page page) {			
			return refundsMapper.selectRefundsByPage(page);
		}


		@Override
		public Integer selectRefundsByPageCount(Page page) {
			return refundsMapper.selectRefundsByPageCount(page);
		}
	
}
