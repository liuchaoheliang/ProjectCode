package com.froad.cbank.coremodule.normal.boss.support.merchant;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.MerchantAccountInfoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.MerchantAccountInfoVo;
import com.froad.thrift.service.MerchantAccountService;
import com.froad.thrift.vo.MerchantAccountPageVoRes;
import com.froad.thrift.vo.MerchantAccountVo;
import com.froad.thrift.vo.PageVo;

/**
 * 类描述：商户账户相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-5-3下午6:53:12 
 */
@Service
public class MerchantAccountSupport {
	@Resource
	private MerchantAccountService.Iface  merchantAccountService;	
	
	/**
	  * 方法描述：商户账户列表查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: f-road.com.cn
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws TException 
	  * @time: 2015年4月27日 下午4:43:27
	  */
	public HashMap<String, Object> queryMerchantAccountList(MerchantAccountInfoReq req) throws IllegalAccessException, InvocationTargetException, TException{
		HashMap<String, Object> resMap=new HashMap<String, Object>();				
		//分页对象转换
		PageVo pageVo=new PageVo();
		pageVo.setPageNumber(req.getPageNumber()==0?1:req.getPageNumber());
		pageVo.setPageSize(req.getPageSize()==0?10:req.getPageSize());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		pageVo.setBegDate(req.getBegDate()==0?req.getStartDate():req.getBegDate());
		pageVo.setEndDate(req.getEndDate());
		
		Page page=new Page();
		List<MerchantAccountInfoVo> merchantAccountInfoVoList= null;
		MerchantAccountInfoVo res= null;	
		MerchantAccountPageVoRes merchantAccountPageVoRes = null;
		
		MerchantAccountVo accountVo=new MerchantAccountVo();

		if(req!=null && StringUtil.isNotBlank(req.getMerchantId())){
			accountVo.setMerchantId(req.getMerchantId());	
		}
		//BeanUtils.copyPropertiesNotNull(merchantVo, req);
		merchantAccountPageVoRes = merchantAccountService.getMerchantAccountByPage(pageVo,accountVo);
		LogCvt.info("商户账户列表查询后端返回数据："+JSON.toJSONString(merchantAccountPageVoRes));
		//分页实体转换
		BeanUtils.copyProperties(page, merchantAccountPageVoRes.getPage());
		
		//查询数据集数据集转换
		if( !ArrayUtil.empty(merchantAccountPageVoRes.getMerchantAccountVoList())){
			merchantAccountInfoVoList = new   ArrayList<MerchantAccountInfoVo>();
			for(MerchantAccountVo temp:merchantAccountPageVoRes.getMerchantAccountVoList()){
				res=new MerchantAccountInfoVo();
				res.setAccountName(temp.getAcctName());
				res.setAccountNum(temp.getAcctNumber());
				res.setAcctType(temp.getAcctType());
				res.setClientId(temp.getClientId());
				if(StringUtil.isNotBlank(temp.getCreateTime()) && temp.getCreateTime() != 0){
					String createTime = String.valueOf(temp.getCreateTime());
					res.setCreateTime(createTime);
				}
				res.setDelete(temp.isIsDelete());
				if(StringUtil.isNotBlank(temp.getId()) && temp.getId() != 0){
					String id = String.valueOf(temp.getId());
					//res.setId(id);
					res.setMerchantAccountId(id);
				}
				res.setMerchantId(temp.getMerchantId());
				res.setOpeningBank(temp.getOpeningBank());
				res.setOutletId(temp.getOutletId());
				merchantAccountInfoVoList.add(res);
			}
		}					
		resMap.put("page", page);
		resMap.put("merchantAccountList", merchantAccountInfoVoList);
		return  resMap;
	}
}
