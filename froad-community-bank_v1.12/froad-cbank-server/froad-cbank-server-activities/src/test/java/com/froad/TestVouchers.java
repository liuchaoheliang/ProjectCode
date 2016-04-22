package com.froad;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

import com.froad.thrift.service.VouchersRuleInfoService;
import com.froad.thrift.service.VouchersSearchService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.ActiveBaseRuleVo;
import com.froad.thrift.vo.active.ActiveSustainRelationVo;
import com.froad.thrift.vo.active.ActiveTagRelationVo;
import com.froad.thrift.vo.active.AddResultVo;
import com.froad.thrift.vo.active.FindPageVouchersRuleInfoVoResultVo;
import com.froad.thrift.vo.active.TemporaryVouchersDetailInfoVO;
import com.froad.thrift.vo.active.VouchersCouponCodeVO;
import com.froad.thrift.vo.active.VouchersDetailRuleVo;
import com.froad.thrift.vo.active.VouchersRuleInfoVo;

public class TestVouchers extends TestCase{

	public static void main(String[] args) throws TTransportException{
		long createTime2 = System.currentTimeMillis();
		long currentTime1 = System.currentTimeMillis();
		long currentTime = System.currentTimeMillis() + 10 * 1000 * 60 * 60 * 24;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
		Date date = new Date(currentTime);
		PageVo page = new PageVo();
		VouchersRuleInfoVo vou = new VouchersRuleInfoVo();
		VouchersRuleInfoVo queryvou = new VouchersRuleInfoVo();
		// 红包规则
		ActiveBaseRuleVo baseRule = new ActiveBaseRuleVo();
		OriginVo originVo = new OriginVo();
		//baseRule.setActiveId("chongqing-HB-2015-017");
		baseRule.setClientId("taizhou");
		baseRule.setActiveName("红包");
		baseRule.setActiveLogo("");
		baseRule.setBankRate(60000);		
		baseRule.setCreateTime(createTime2);
		baseRule.setUpdateTime(createTime2);
		baseRule.setExpireStartTime(currentTime1);
	    baseRule.setExpireEndTime(currentTime);
		baseRule.setDescription("红包");		
		baseRule.setFftRate(20000);
		baseRule.setLimitType("1");
		baseRule.setMerchantRate(20000);
		baseRule.setOperator("abc");
		baseRule.setStatus("3");
		baseRule.setType("3");		
		baseRule.setSettleType("1");
		// 红包规则明细
		VouchersDetailRuleVo detailRuleVo = new VouchersDetailRuleVo();
		//detailRuleVo.setActiveId("chongqing-hb-2015-001");
		detailRuleVo.setClientId("taizhou");		
		detailRuleVo.setIsFtof(true);
		detailRuleVo.setIsOtherActive(true);
		detailRuleVo.setIsPerDay(true);
		detailRuleVo.setIsPrePay(true);
		detailRuleVo.setIsRepeat(true);
		detailRuleVo.setIsTotalDay(true);
		detailRuleVo.setMaxMoney(5);
		detailRuleVo.setMinMoney(5000);
		detailRuleVo.setTotalCount(50);
		detailRuleVo.setTotalDay(10);
		detailRuleVo.setTotalMoney(50000);
		// 劵码ID集
		List<VouchersCouponCodeVO> vouchersIds = new ArrayList<VouchersCouponCodeVO>();
/*		for(int i = 0; i < 10; i++) {
			 String base = "QWERTYUIOPLKJHGFDSAZXCVBNM0123456789";   
		     Random random = new Random();   
		     StringBuffer sb = new StringBuffer();   
		     for (int j = 0; j < 12; j++) {   
		        int number = random.nextInt(base.length());   
		        sb.append(base.charAt(number));   
		     }
			
			VouchersCouponCodeVO voooCodeVO = new VouchersCouponCodeVO();
			voooCodeVO.setVouchersIds(sb.toString());
			voooCodeVO.setCreateTime(createTime2);
			voooCodeVO.setVouchersMoney(30);
			voooCodeVO.setVouchersResMoney(30);
			vouchersIds.add(voooCodeVO);	
		}*/
		
		detailRuleVo.setVouchersCouponCodelist(vouchersIds);
		detailRuleVo.setTemporaryActiveId("1115939644462867");
		detailRuleVo.setVouchersTotal(10);
		// 红包支持活动
		List<ActiveSustainRelationVo> activeSustainRelationList = new ArrayList<ActiveSustainRelationVo>();
		ActiveSustainRelationVo activeSustainRelation = new ActiveSustainRelationVo();
		//activeSustainRelation.setActiveId("chongqing-hb-2015-001");
		activeSustainRelation.setClientId("taizhou");
		activeSustainRelation.setCreateTime(0);
		activeSustainRelation.setSustainActiveId("chongqing-MJ-2015-001");
		activeSustainRelation.setSustainActiveName("新增修改页面自测用例");
		activeSustainRelation.setSustainActiveType("2");
		activeSustainRelation.setUpdateTime(0);
		activeSustainRelationList.add(activeSustainRelation);
		
		detailRuleVo.setActiveSustainRelationList(activeSustainRelationList);
		
		// 活动标签关联
		ActiveTagRelationVo activeTagRelationVo = new ActiveTagRelationVo();
		//activeTagRelationVo.setActiveId("chongqing-hb-2015-001");
		activeTagRelationVo.setClientId("taizhou");
		activeTagRelationVo.setCreateTime(createTime2);
		activeTagRelationVo.setItemId("0");
		activeTagRelationVo.setItemType("0");
		activeTagRelationVo.setUpdateTime(createTime2);
		
		vou.setActiveBaseRule(baseRule);
		vou.setVouchersDetailRule(detailRuleVo);
		vou.setActiveTagRelation(activeTagRelationVo);
		/***********************************************/
		TSocket transport = new TSocket("localhost", 16501);
		TBinaryProtocol protocol = new TBinaryProtocol(transport);
		
		TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"VouchersRuleInfoService");		
		VouchersRuleInfoService.Client service = new VouchersRuleInfoService.Client(mp);
		
		TMultiplexedProtocol checkmp = new TMultiplexedProtocol(protocol,"VouchersSearchService");
		VouchersSearchService.Client serClient = new VouchersSearchService.Client(checkmp);
		TemporaryVouchersDetailInfoVO tem = new TemporaryVouchersDetailInfoVO();
		
		transport.open();
		try {	
			System.out.println("---开始---");	
			long beginTime = System.currentTimeMillis();
			// 新增
			//AddResultVo resultVo = service.addVouchersRuleInfo(originVo, vou);
			//		
			// 上传券码
			//tem.vouchersCouponCodelist = vouchersIds;
			//AddResultVo resultVo2 = service.addTemporaryVouchersRuleInfo(originVo, tem);		
			// 修改
			//ResultVo resultVo = service.updateVouchersRuleInfo(originVo, vou);
			// 查找
			//FindAllVouchersRuleInfoVoResultVo allVouchersRulevo = service.getActiveRuleInfo(vou);
			// 红包券码详情
			//serClient.findVouchersDetailInfo(page, "chongqing", "chongqing-HB-2015-005");
			//service.getActiveRuleInfoById("chongqing", "chongqing-HB-2015-056");
			// 分页
			//queryvou.setActiveBaseRule(baseRule);
			//FindPageVouchersRuleInfoVoResultVo  res = service.getActiveRuleInfoByPage(page, queryvou);
			// 查找列表
			//service.getActiveRuleInfo(queryvou);
			//查单个
			//service.getActiveRuleInfoById("chongqing", "chongqing-HB-2015-016");
			// 禁用
			//service.disableVouchersRuleInfo(originVo, "chongqing", "chongqing-HB-2015-002", "");
			// 下载
			//service.exportVouchersDetailInfo("chongqing", "chongqing-HB-2015-001");
			// 查找临时数据
			//serClient.findVouchersDetailTemporaryInfo(page, null, "7223672305548878");
			// 查询红包个数
			serClient.FindVouchersCountInfo("chongqing", "chongqing-HB-2016-006");
			System.out.println("总共耗时 ：" + (System.currentTimeMillis() - beginTime));
		} catch (TException e) {
			e.printStackTrace();
		}
		
		transport.close();
	}
}
