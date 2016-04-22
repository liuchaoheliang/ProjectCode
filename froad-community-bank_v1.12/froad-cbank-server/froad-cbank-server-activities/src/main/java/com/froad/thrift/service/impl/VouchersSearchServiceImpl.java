package com.froad.thrift.service.impl;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.logic.VouchersInfoLogic;
import com.froad.logic.impl.VouchersInfoLogicImpl;
import com.froad.po.VouchersInfo;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.VouchersSearchService.Iface;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.FindVouchersCountVO;
import com.froad.thrift.vo.active.FindVouchersDetailInfoVO;
import com.froad.util.BeanUtil;

public class VouchersSearchServiceImpl extends BizMonitorBaseService implements Iface {
	 VouchersInfoLogic vouchersInfoLogic = new VouchersInfoLogicImpl();
	 
		public VouchersSearchServiceImpl() {}

		public VouchersSearchServiceImpl(String name, String version) {
			super(name, version);
		}
		
	 /**
	  * @Title: findVouchersDetailInfo
	  * @Description: 详情页查询红包券码明细列表分页
	  * @author: shenshaocheng 2015年12月1日
	  * @modify: shenshaocheng 2015年12月1日
	  * @param paramPageVo 分页信息
	  * @param clientId 客户单ID
	  * @param activeId 活动ID
	  * @return 返回红包券码明细列表分页
	  * @throws TException
	  * @see com.froad.thrift.service.VouchersSearchService.Iface#findVouchersDetailInfo(
	  * com.froad.thrift.vo.PageVo, java.lang.String, java.lang.String)
	  */	
	@Override
	public FindVouchersDetailInfoVO findVouchersDetailInfo(PageVo paramPageVo,
			String clientId, String activeId) throws TException {
		FindVouchersDetailInfoVO vo = new FindVouchersDetailInfoVO();
		VouchersInfo vouchersInfo = new VouchersInfo();
		vouchersInfo.setClientId(clientId);
		vouchersInfo.setActiveId(activeId);
		Page page = (Page) BeanUtil.copyProperties(Page.class, paramPageVo);
		vo = vouchersInfoLogic.findVouchersInfoByPage(page, vouchersInfo);
		return vo;
	}
	
	 /**
	  * @Title: findVouchersDetailTemporaryInfo
	  * @Description: 新增页查询红包券码明细列表分页（红包临时信息） 
	  * @author: shenshaocheng 2015年12月1日
	  * @modify: shenshaocheng 2015年12月1日
	  * @param paramPageVo 分页信息
	  * @param clientId 客户单ID
	  * @param activeId 活动ID
	  * @return 返回红包券码明细列表分页
	  * @throws TException
	 */	
	@Override
	public FindVouchersDetailInfoVO findVouchersDetailTemporaryInfo(PageVo paramPageVo,
			String clientId, String activeId) throws TException {
		FindVouchersDetailInfoVO vo = new FindVouchersDetailInfoVO();
		VouchersInfo vouchersInfo = new VouchersInfo();
		vouchersInfo.setActiveId(activeId);
		Page page = (Page) BeanUtil.copyProperties(Page.class, paramPageVo);
		vo = vouchersInfoLogic.findTemporaryVouchersInfoByPage(page, vouchersInfo);
		return vo;
	}

	@Override
	public FindVouchersCountVO FindVouchersCountInfo(String clientId, String activeId)
			throws TException {
		VouchersInfo vouchersInfo = new VouchersInfo();
		vouchersInfo.setActiveId(activeId);
		vouchersInfo.setClientId(clientId);
		Integer vouchersCount = 
				this.vouchersInfoLogic.countVouchersInfo(vouchersInfo);
		FindVouchersCountVO resCountVO = new FindVouchersCountVO();
		resCountVO.setVouchersCount(vouchersCount);
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		resCountVO.setResultVo(resultVo);
		return resCountVO;
	}
	
}
