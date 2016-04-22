package com.froad.cbank.coremodule.normal.boss.support.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.data.thrift.service.MemberRegisterService;
import com.data.thrift.vo.MemberRegisterSummaryRequestVo;
import com.data.thrift.vo.MemberRegisterSummaryResponseVo;
import com.data.thrift.vo.PageVo;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.MemberEntity;
import com.froad.cbank.coremodule.normal.boss.logic.MemberReportLogic;
import com.froad.cbank.coremodule.normal.boss.pojo.report.BaseReportReq;
import com.froad.cbank.coremodule.normal.boss.pojo.report.MemberReportRes;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;

/**
 * 会员注册分析报表
 * @author liaopeixin
 *	@date 2015年11月5日 上午9:19:40
 */
@Service
public class MemberReportSupport {

	@Resource
	private MemberReportLogic memberReportLogic;
	@Resource
	private MemberRegisterService.Iface memberRegisterService;
	public Map<String,Object> list(BaseReportReq req) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		List<MemberReportRes> list =new ArrayList<MemberReportRes>();
		MemberReportRes m=null;
		
		//封装logic请求参数
		BaseReportEntity entity=new BaseReportEntity();
		entity.setClientId(req.getClientId());
		entity.setStartTime(req.getStartTime());
		entity.setEndTime(req.getEndTime());
		
		//创建boss分页对象
		com.froad.cbank.coremodule.normal.boss.pojo.Page page =new com.froad.cbank.coremodule.normal.boss.pojo.Page();
		
		if(req.getDaily()==1){		
			//创建数据库分页对象
			Page<MemberEntity> sqlpage = new Page<MemberEntity>();
			sqlpage.setPageNumber(req.getPageNumber());
			sqlpage.setPageSize(req.getPageSize());
		
			List<MemberEntity> mlist=memberReportLogic.getListByPage(sqlpage, entity);
			for (MemberEntity m1 : mlist) {
				m=new MemberReportRes();		
				m.setCreateTime(DateUtil.formatDate(m1.getCreateTime(), false));
				m.setUserCount(m1.getUserCount());
				m.setUserResourceCount(m1.getUserResourceCount());
				
				list.add(m);
			}
			BeanUtils.copyProperties(page, sqlpage);
			
		}else{
			MemberRegisterSummaryRequestVo reqVo=new MemberRegisterSummaryRequestVo();
			m=new MemberReportRes();	
			reqVo.setChannel(req.getClientId());
			//封装给后台的分页对象
			PageVo pageVo=new PageVo();
			pageVo.setBegDate(PramasUtil.DateFormat(req.getStartTime()));
			pageVo.setEndDate(PramasUtil.DateFormatEnd(req.getEndTime()));
			pageVo.setPageNumber(1);
			pageVo.setPageSize(1);
			reqVo.setPageVo(pageVo);
			
			MemberRegisterSummaryResponseVo res=memberRegisterService.queryMemberRegisterSummary(reqVo);
			//时间
			m.setCreateTime(res.getDateTime());
			//注册会员数
			m.setUserCount(res.getRegisterMemberCount());
			//渠道数量
			m.setUserResourceCount(res.getChannelCount());
		
			list.add(m);
			//boss分页对象
			page.setPageCount(1);
			page.setPageNumber(1);
			page.setTotalCount(1);
		}

		map.put("page", page);
		map.put("list", list);
		return map;
	}
	 /**
	  * 数据导出
	  * @param req
	  * @return
	  * @throws Exception
	  * @author liaopeixin
	  *	@date 2015年11月6日 上午9:54:03
	  */
	@SuppressWarnings("unchecked")
	public List<MemberReportRes> export(BaseReportReq req) throws Exception{
		List<MemberReportRes> list = new ArrayList<MemberReportRes>();
		
		Map<String, Object> map = list(req);
		com.froad.cbank.coremodule.normal.boss.pojo.Page page = (com.froad.cbank.coremodule.normal.boss.pojo.Page)map.get("page");
		list = (List<MemberReportRes>)map.get("list");
		LogCvt.info("totalCount:"+page.getTotalCount());
		if(page.getTotalCount()>req.getPageSize()){
			int s=(page.getTotalCount())/req.getPageSize(); //需要再查询次数
			if(page.getTotalCount()%req.getPageSize()==0){ //倍数减1
				s--;
			}
			int pagenum=1;
			for(int i=1;i<=s;i++){
				req.setPageNumber(++pagenum);
				Map<String, Object> map1 = list(req);
				list.addAll((List<MemberReportRes>)map1.get("list"));
			}
		}
		
		return list;		
	}
}
