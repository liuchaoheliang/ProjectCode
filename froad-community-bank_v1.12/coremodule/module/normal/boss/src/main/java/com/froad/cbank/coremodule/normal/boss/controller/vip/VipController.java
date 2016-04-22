package com.froad.cbank.coremodule.normal.boss.controller.vip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.BankOrgRes;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.ClientChannelRes;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.CreateChannelRes;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipListReq;
import com.froad.cbank.coremodule.normal.boss.support.vip.VipSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;
import com.pay.user.helper.BankOrg;
import com.pay.user.helper.ClientChannel;
import com.pay.user.helper.CreateChannel;

/**
 * VIP会员
 * @ClassName VipController
 * @author zxl
 * @date 2015年6月9日 下午3:18:41
 */
@Controller
@RequestMapping(value = "/vip")
public class VipController {
	
	@Resource
	private VipSupport vipSupport;
	
	/**
	 * @desc 获取会员明细列表
	 * @path /vip/list
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月17日 上午11:50:56
	 * @param 
	 * @return 
	 */
	@Auth(keys={"boss_vip_detail_menu"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void getVipList(ModelMap model, VipListReq pojo) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("VIP会员明细列表请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			if(pojo.getBeginTime() != null && pojo.getEndTime() != null && pojo.getBeginTime() > pojo.getEndTime()) {
				throw new BossException("开始时间不可大于结束时间");
			}
			model.putAll(vipSupport.getVipList(pojo));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 创建渠道列表查询
	 * @path /vip/createchannel
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年7月9日 上午11:35:49
	 * @param model
	 */
	@RequestMapping(value = "/createchannel", method = RequestMethod.GET)
	public void createChannelList(ModelMap model) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<CreateChannelRes> list = new ArrayList<CreateChannelRes>();
			CreateChannel[] cc = CreateChannel.values();
			CreateChannelRes temp = null;
			for(CreateChannel tmp : cc) {
				temp = new CreateChannelRes();
				temp.setCreateChannelId(tmp.getValue());
				temp.setCreateChannelName(tmp.getDesc());
				list.add(temp);
			}
			map.put("createChannelList", list);
			model.putAll(map);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 客户端渠道列表查询（创建方式）
	 * @path /vip/clientchannel
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年7月9日 上午11:33:47
	 * @param model
	 */
	@RequestMapping(value = "/clientchannel", method = RequestMethod.GET)
	public void clientChannelList(ModelMap model) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<ClientChannelRes> list = new ArrayList<ClientChannelRes>();
			ClientChannel[] cc = ClientChannel.values();
			ClientChannelRes temp = null;
			for(ClientChannel tmp : cc) {
				temp = new ClientChannelRes();
				temp.setClientChannelId(tmp.getValue());
				temp.setClientChannelName(tmp.getDesc());
				list.add(temp);
			}
			map.put("clientChannelList", list);
			model.putAll(map);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 获取银行渠道列表
	 * @path /vip/bankorg
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年12月9日 上午11:11:28
	 * @param model
	 */
	@RequestMapping(value = "/bankorg", method = RequestMethod.GET)
	public void getBankOrgList(ModelMap model) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<BankOrgRes> list = new ArrayList<BankOrgRes>();
			BankOrg[] bo = BankOrg.values();
			BankOrgRes temp = null;
			for(BankOrg tmp : bo) {
				temp = new BankOrgRes();
				temp.setBankOrgId(tmp.getBankOrg());
				temp.setBankOrgName(tmp.getBankName());
				list.add(temp);
			}
			map.put("bankOrgList", list);
			model.putAll(map);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
}
