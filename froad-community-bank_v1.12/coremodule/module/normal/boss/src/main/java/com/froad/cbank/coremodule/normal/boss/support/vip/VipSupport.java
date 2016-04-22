package com.froad.cbank.coremodule.normal.boss.support.vip;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipRes;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.pay.user.dto.Result;
import com.pay.user.dto.VIPSpecDto;
import com.pay.user.helper.BankOrg;
import com.pay.user.helper.ClientChannel;
import com.pay.user.helper.CreateChannel;
import com.pay.user.service.VIPSpecService;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月10日 上午10:28:59
 * @version 1.0
 * @desc VIP会员支持类
 */
@Service
public class VipSupport {

	@Resource
	private VIPSpecService vIPSpecService;
	
	/**
	 * 获取会员明细列表
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月30日 上午10:24:52
	 * @param pojo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getVipList(VipListReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<VipRes> vipList = new ArrayList<VipRes>();
		VipRes temp = null;
		Page page = new Page();
		//封装请求对象
		Date beginTime = pojo.getBeginTime() == null ? null : DateUtil.longToDate(pojo.getBeginTime());
		Date endTime = pojo.getEndTime() == null ? null : DateUtil.longToDate(pojo.getEndTime());
		BankOrg bankOrg = StringUtil.isNotBlank(pojo.getBankOrg()) ? BankOrg.getInstanceByValue(pojo.getBankOrg()) : null;
		//开通方式
		ClientChannel clientChannel = StringUtil.isNotBlank(pojo.getClientChannel()) ? ClientChannel.getInstanceByValue(pojo.getClientChannel()) : null;
		//调用VIP会员系统
		Result resp = vIPSpecService.queryVIPSpecList(beginTime, endTime, bankOrg, pojo.getLabelId(), clientChannel, pojo.getPageSize(), pojo.getPageNumber());
		if(resp.getResult()) {
			com.pay.user.dto.Page<?> result = (com.pay.user.dto.Page<?>) resp.getData();
			//封装返回的分页对象
			page.setPageNumber(result.getPageNo());
			page.setPageSize(result.getPageSize());
			page.setTotalCount(result.getTotalRecord());
			page.setPageCount(result.getTotalPage());
			
			List<VIPSpecDto> respList = (List<VIPSpecDto>) result.getResult();
			if(!ArrayUtil.empty(respList)) {
				for(VIPSpecDto tmp : respList) {
					temp = new VipRes();
					BeanUtils.copyProperties(temp, tmp);
					temp.setBankOrgNo(StringUtils.isBlank(tmp.getBankOrgNo()) ? null : BankOrg.getInstanceByValue(tmp.getBankOrgNo()).getBankName());
					temp.setMemberCreateChannel(StringUtils.isBlank(tmp.getMemberCreateChannel()) ? null : CreateChannel.valueOf(tmp.getMemberCreateChannel()).getDesc());
					temp.setClientChannel(StringUtils.isBlank(tmp.getClientChannel()) ? null : ClientChannel.getInstanceByValue(tmp.getClientChannel()).getDesc());
					temp.setLastLoginTime(tmp.getLastLoginTime() == null ? null : tmp.getLastLoginTime().getTime());
					temp.setRegisterTime(tmp.getRegisterTime() == null ? null : tmp.getRegisterTime().getTime());
					temp.setVipActivationTime(tmp.getVipActivationTime() == null ? null : tmp.getVipActivationTime().getTime());
					temp.setVipExpirationTime(tmp.getVipExpirationTime() == null ? null : tmp.getVipExpirationTime().getTime());
					temp.setVipLevel(tmp.getVipLevel() == null ? null : tmp.getVipLevel().getDesc());
					temp.setVipStatus(tmp.getVipStatus() == null ? null : tmp.getVipStatus().getDesc());
					vipList.add(temp);
				}
			}
		} else {
			throw new BossException(resp.getCode() + "", resp.getMessage());
		}
		map.put("vipList", vipList);
		map.put("page", page);
		return map;
	}
}
