package com.froad.cbank.coremodule.module.normal.user.controller.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.pojo.MyPacketsReqPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductOfFindUseReqPojo;
import com.froad.cbank.coremodule.module.normal.user.support.RedPacketSupport;
import com.froad.cbank.coremodule.module.normal.user.vo.FindCanUseRedPacketReqVo;
import com.froad.cbank.coremodule.module.normal.user.vo.FindRedPacketReqVo;
import com.froad.cbank.coremodule.module.normal.user.vo.ProductsReqVo;

/**
 * 红包
 * @author Administrator
 *
 */
@Controller
@RequestMapping
public class RedPacketController extends BasicSpringController {

	@Resource
	private RedPacketSupport redPacketSupport;
	
	/**
	 * 说明   商品购物可用红包查询
	 * 创建日期  2015年11月26日  上午10:12:13
	 * 作者  artPing
	 * 参数  @param model
	 * 参数  @param req
	 * 参数  @param productIds
	 * 参数  @param redPacketStatus
	 * 参数  @param pageNumber
	 * 参数  @param pageSize
	 */
	@Token
	@RequestMapping(value = "/redPacket/canUsePackets" , method = RequestMethod.POST)
	public void canUsePackets(ModelMap model,HttpServletRequest request,@RequestHeader Long memberCode ,@RequestBody FindCanUseRedPacketReqVo reqVo ){
		String clientId = getClient(request);
		try{
			PagePojo pagePojo = new PagePojo();
			pagePojo.setPageSize(reqVo.getPageSize());
			pagePojo.setPageNumber(reqVo.getPageNumber());
			List<String> ilist = new ArrayList<String>();
			if(reqVo.getSustainActiveIds() != null && reqVo.getSustainActiveIds().length > 0 ){
				ilist = Arrays.asList(reqVo.getSustainActiveIds());
			}
			//可用红包
			model.put("redPackets",redPacketSupport.canUsePackets(clientId, memberCode, reqVo.getIsAvailable(), reqVo.getIsEnableQrcode(),ilist,reqVo.getList(), pagePojo).get("redPackets"));
			//不可用红包
			model.put("unableRedPackets",redPacketSupport.canUsePackets(clientId, memberCode, false, reqVo.getIsEnableQrcode(),ilist,reqVo.getList(), pagePojo).get("redPackets"));
		}catch (Exception e) {
			model.put("code", "9999");
			model.put("message","红包查询异常");
			LogCvt.error(e.getMessage(), e);
		}
	}

	/**
	 * 说明   我的红包查询
	 * 创建日期  2015年11月26日  上午10:12:53
	 * 作者  artPing
	 * 参数  @param model
	 * 参数  @param req
	 * 参数  @param productIds
	 * 参数  @param redPacketStatus
	 * 参数  @param pageNumber
	 * 参数  @param pageSize
	 */
	@Token
	@RequestMapping(value = "/redPacket/myPackets" , method = RequestMethod.POST)
	public void myPackets(ModelMap model,HttpServletRequest request,@RequestBody MyPacketsReqPojo req){
		req.setClientId(getClient(request));
		req.setMemberCode(Long.valueOf(request.getAttribute("memberCode")+""));
		try{
			model.putAll(redPacketSupport.myPackets(req));
		}catch (Exception e) {
			model.put("code", "9999");
			model.put("message","可用红包查询异常");
			LogCvt.error(e.getMessage(), e);
		}
		
	}
	/**
	 * 优惠码校验
	 * @author zhouyuhan
	 * @param model
	 * @param request
	 * @param memberCode 用户编号
	 * @param vouchersId 代金券id
	 * @param list 商品列表
	 * @date 2015年11月26日 下午4:29:41
	 */
	@Token
	@RequestMapping(value = "/redPacket/checkCode" ,method = RequestMethod.POST)
	public void getVouchersMonry(ModelMap model,HttpServletRequest request,@RequestHeader Long memberCode,@RequestBody FindRedPacketReqVo reqVo ){
		model.clear();
		String clientId = getClient(request);
		if(!StringUtil.empty(reqVo.getVouchersId()) && !StringUtil.empty(clientId)){
			
			List<String> ilist = new ArrayList<String>();
			if(reqVo.getSustainActiveIds() != null && reqVo.getSustainActiveIds().length > 0 ){
				ilist = Arrays.asList(reqVo.getSustainActiveIds());
			}
			model.putAll(redPacketSupport.getVouchersMonrys(clientId,memberCode,reqVo.getVouchersId(),reqVo.getIsEnableQrcode(),reqVo.getList(),ilist));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}
}
