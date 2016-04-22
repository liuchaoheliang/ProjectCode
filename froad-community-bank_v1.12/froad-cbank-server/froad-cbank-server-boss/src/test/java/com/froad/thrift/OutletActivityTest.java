/**
 * Project Name:froad-cbank-server-boss
 * File Name:OutletActivityTest.java
 * Package Name:com.froad.thrift
 * Date:2015年10月27日上午10:57:57
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.thrift;

import java.util.Date;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.service.OutletActivityService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.outletActivity.AdjustOutletWeightReqVo;
import com.froad.thrift.vo.outletActivity.DeleteRelateOutletReqVo;
import com.froad.thrift.vo.outletActivity.EnableOutletActivityReqVo;
import com.froad.thrift.vo.outletActivity.InputRelateOutletActivityReqVo;
import com.froad.thrift.vo.outletActivity.OutletActivityTagDetailReqVo;
import com.froad.thrift.vo.outletActivity.OutletActivityTagDetailResVo;
import com.froad.thrift.vo.outletActivity.OutletNameAndMerchantNameResVo;
import com.froad.thrift.vo.outletActivity.OutletWeightActivityTagPageReqVo;
import com.froad.thrift.vo.outletActivity.OutletWeightActivityTagPageVo;
import com.froad.thrift.vo.outletActivity.RecommendActivityTagPageVo;
import com.froad.thrift.vo.outletActivity.RecommendActivityTagVo;
import com.froad.thrift.vo.outletActivity.RelateOutletActivityVo;
import com.froad.util.Checker;
import com.froad.util.PropertiesUtil;

/**
 * ClassName:OutletActivityTest
 * Reason:	 
 * Date:     2015年10月27日 上午10:57:57
 * @author   asus
 * @version  
 * @see 	 
 */
public class OutletActivityTest {

	public static void main(String[] args) throws TException {
		PropertiesUtil.load();
		//本地测试
		TSocket transport = new TSocket("127.0.0.1", 16001);
		transport.open();
		TBinaryProtocol protocol = new TBinaryProtocol(transport);
		TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol, OutletActivityService.class.getSimpleName());
		OutletActivityService.Iface outletActivityService = new OutletActivityService.Client(multiplexedProtocol);
		
		//findOutletTagByPage(outletActivityService);//查询门店推荐活动列表
		//findOutletTagDetail(outletActivityService);//查询门店推荐活动详情
		//findRelateOutletInfoByPage(outletActivityService);//推荐活动关联门店列表详情查询
		//addOutletActivity(outletActivityService);//添加门店推荐活动
		//relateOutletInfo(outletActivityService);//关联门店
		
		//enableOutletRecommendActivityTag(outletActivityService);//启用/禁用门店推荐活动标签
		//adjustOutletWeight(outletActivityService);//调整关联门店权重
		//deleteRelateOutlet(outletActivityService);//删除关联门店
		updateOutletActivityTag(outletActivityService);//更新门店推荐活动标签信息
	
		//inputRelateOutletInfo(outletActivityService);//批量导入关联门店
		//queryOutletNameAndMerchantNameByOutletId(outletActivityService);//根据门店id查询门店名称和商户名称
		
	}
	
	//查询门店推荐活动列表
	public static void findOutletTagByPage(OutletActivityService.Iface outletActivityService) throws TException {
		RecommendActivityTagVo vo = new RecommendActivityTagVo();
		vo.setClientId("anhui");
		PageVo pageVo = new PageVo();
		RecommendActivityTagPageVo recommendActivityTagPageVo = outletActivityService.findOutletTagByPage(vo, pageVo);
		if (recommendActivityTagPageVo.getRecommendvos().size() > 0) {
			for (RecommendActivityTagVo tagVo : recommendActivityTagPageVo.getRecommendvos()) {
				System.out.println(tagVo);
			}
		} else {
			System.out.println("查询门店推荐活动列表失败！！！");
		}
	}

		
	//推荐活动列表详情查询
	public static void findOutletTagDetail(OutletActivityService.Iface outletActivityService) throws TException {
		OutletActivityTagDetailReqVo reqVo = new OutletActivityTagDetailReqVo();
		reqVo.setActivityId(18);
		reqVo.setActivityNo("AH-2015-006");
		reqVo.setClientId("anhui");
		reqVo.setOperator("李萍");
		
		OutletActivityTagDetailResVo vo = outletActivityService.findOutletTagDetail(reqVo);
		if (Checker.isNotEmpty(vo)) {
			System.out.println(vo.getResultVo());
			System.out.println(vo.getRecommendVo());
		} else {
			System.out.println("推荐活动列表详情查询失败");
		}
	}
	
	
	//添加门店推荐活动
	public static void addOutletActivity(OutletActivityService.Iface outletActivityService) throws TException {
		RecommendActivityTagVo recommendVo = new RecommendActivityTagVo();
		recommendVo.setActivityName("赠送保健品");
		recommendVo.setActivityNo("AH-2015-012");
		recommendVo.setActivityType("2");
		recommendVo.setClientId("chongqing");
		recommendVo.setCreateTime(new Date().getTime());
		recommendVo.setUpdateTime(new Date().getTime());
		recommendVo.setActivityDesc("赠送保健品");
		recommendVo.setLogoUrl("c://desk/picture/productRead.jpg");
		recommendVo.setOperator("王宁");
		recommendVo.setStatus("1");
		ResultVo resultVo = outletActivityService.addOutletActivityTag(recommendVo);
		if (resultVo != null) {
			System.out.println(resultVo.getResultDesc());
		} else {
			System.out.println("添加门店活动失败！");
		}
	}
	
	//关联门店
		public static void relateOutletInfo(OutletActivityService.Iface outletActivityService) throws TException {
			RelateOutletActivityVo vo = new RelateOutletActivityVo();
			vo.setActivityId(28);
			vo.setActivityNo("AH-2015-012");
			vo.setClientId("chongqing");
			vo.setOperator("王宁");
			vo.setOutletId("0D8204C1000A");
			vo.setWeight("1");
			ResultVo resultVo = outletActivityService.relateOutletInfo(vo);
			System.out.println(resultVo);
		}
	
	
	
	//推荐活动列表详情查询
	public static void findRelateOutletInfoByPage(OutletActivityService.Iface outletActivityService) throws TException {
		OutletWeightActivityTagPageReqVo pageReqVo = new OutletWeightActivityTagPageReqVo();
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(1);
		pageVo.setPageSize(2);
		
		//pageReqVo.setActivityId(28);
		pageReqVo.setActivityNo("AH-2015-012");
		pageReqVo.setClientId("chongqing");
		pageReqVo.setPageVo(pageVo);
		
		OutletWeightActivityTagPageVo  tegPageVo = outletActivityService.findRelateOutletInfoByPage(pageReqVo);
		
		if (Checker.isNotEmpty(tegPageVo)) {
			System.out.println(tegPageVo);
		} else {
			System.out.println("推荐活动列表详情查询失败");
		}
	}
	
	//启用/禁用门店推荐活动标签
	public static void enableOutletRecommendActivityTag(OutletActivityService.Iface outletActivityService) throws TException {
		EnableOutletActivityReqVo reqVo = new EnableOutletActivityReqVo();
		reqVo.setClientId("chongqing");
		reqVo.setId(28);
		reqVo.setOperator("王宁");
		reqVo.setStatus("2");
		
		ResultVo resultVo = outletActivityService.enableOutletRecommendActivityTag(reqVo);
		System.out.println(resultVo);
		
	}
	
	//调整关联门店权重
	public static void adjustOutletWeight(OutletActivityService.Iface outletActivityService) throws TException {
		AdjustOutletWeightReqVo reqVo = new AdjustOutletWeightReqVo();
		reqVo.setActivityNo("AH-2015-012");
		reqVo.setClientId("chongqing");
		reqVo.setId(18);
		reqVo.setOperator("王宁");
		reqVo.setWeight("5");
		ResultVo resultVo = outletActivityService.adjustOutletWeight(reqVo);
		System.out.println(resultVo);
	}
	
	//删除关联门店
	public static void deleteRelateOutlet(OutletActivityService.Iface outletActivityService) throws TException {
		DeleteRelateOutletReqVo reqVo = new DeleteRelateOutletReqVo();
		reqVo.setActivityNo("AH-2015-012");
		reqVo.setClientId("chongqing");
		reqVo.setId(2716);//权重ID
		reqVo.setOperator("王宁");
		ResultVo resultVo = outletActivityService.deleteRelateOutlet(reqVo);
		System.out.println(resultVo);
	}
	
	//更新门店推荐活动标签信息
	public static void updateOutletActivityTag(OutletActivityService.Iface outletActivityService) throws TException {
		RecommendActivityTagVo tagVo = new RecommendActivityTagVo();
		tagVo.setActivityDesc("赠送护肤品");
		tagVo.setActivityName("赠送保健品");
		tagVo.setActivityNo("AH-2015-012");
		tagVo.setActivityType("3");
		tagVo.setClientId("chongqing");
		tagVo.setId(28);
		tagVo.setLogoUrl("c://desk/image/picture.jgp");
		tagVo.setOperator("王宁");
		tagVo.setStatus("2");
		ResultVo resultVo = outletActivityService.updateOutletActivityTag(tagVo);
		System.out.println(resultVo);
	}
	
	
	
	//批量导入关联门店
	public static void inputRelateOutletInfo(OutletActivityService.Iface outletActivityService) throws TException {
		InputRelateOutletActivityReqVo reqVo = new InputRelateOutletActivityReqVo();
		ResultVo resultVo = outletActivityService.inputRelateOutletInfo(reqVo);
		System.out.println(resultVo);
	}
	
	//根据门店id查询门店名称和商户名称
	public static void queryOutletNameAndMerchantNameByOutletId(OutletActivityService.Iface outletActivityService) throws TException {
		String clientId = "chongqing";
		String outletId = "0D8204C10003";
		OutletNameAndMerchantNameResVo  resVo = outletActivityService.queryOutletNameAndMerchantNameByOutletId(clientId, outletId);
		System.out.println(resVo);
	}
	
	
	
	
}
