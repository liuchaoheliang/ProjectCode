package com.froad.cbank.coremodule.normal.boss.support.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.dictionary.DictionaryAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.dictionary.DictionaryReq;
import com.froad.cbank.coremodule.normal.boss.pojo.dictionary.DictionaryRes;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.BossDictionaryService;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.dictionary.DictionaryPageVoRes;
import com.froad.thrift.vo.dictionary.DictionaryVo;

/**
 * 字典
 * @ClassName DictionarySupport
 * @author zxl
 * @date 2015年11月26日 下午2:38:07
 */
@Service
public class DictionarySupport {
	
	@Resource
	BossDictionaryService.Iface bossDictionaryService;
	
	/**
	 * 列表查询
	 * @tilte list
	 * @author zxl
	 * @date 2015年11月26日 下午2:51:00
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> list(DictionaryReq req) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		DictionaryVo vo = new DictionaryVo();
		if(req.getCategoryId()!=null){
			vo.setCategoryId(req.getCategoryId());
		}
		if(StringUtils.isNotBlank(req.getDicCode())){
			vo.setDicCode(req.getDicCode());
		}
		if(StringUtils.isNotBlank(req.getDicName())){
			vo.setDicName(req.getDicName());
		}
		vo.setIsEnable(true);
		
		int type = 1;
		if(req.getType()!=null){
			type = req.getType();
		}
		
		PageVo page = new PageVo();
		page.setPageSize(req.getPageSize());
		page.setPageNumber(req.getPageNumber());
		page.setFirstRecordTime(req.getFirstRecordTime());
		page.setLastRecordTime(req.getLastRecordTime());
		page.setLastPageNumber(req.getLastPageNumber());
		
		DictionaryPageVoRes resp = bossDictionaryService.getDictionaryByPage(page, vo ,type);
		
		if(Constants.RESULT_SUCCESS.equals(resp.getResultVo().getResultCode())){
			List<DictionaryRes> list = new ArrayList<DictionaryRes>();
			
			for(DictionaryVo v :resp.getDictionaryVoList()){
				DictionaryRes r = new DictionaryRes();
				BeanUtils.copyProperties(r, v);
				list.add(r);
			}
			
			Page p = new Page();
			BeanUtils.copyProperties(p, resp.getPage());
			
			map.put("page", p);
			map.put("list", list);
		}else{
			throw new BossException(resp.getResultVo().getResultCode(), resp.getResultVo().getResultDesc());
		}
		
		return map;
	}
	
	/**
	 * 新增
	 * @tilte add
	 * @author zxl
	 * @date 2015年11月26日 下午2:51:12
	 * @param request
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> add(HttpServletRequest request,DictionaryAddReq req) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		DictionaryVo vo = new DictionaryVo();
		BeanUtils.copyProperties(vo, req);
		vo.setIsEnable(true);
		
		CommonAddVoRes resp = bossDictionaryService.addDictionary((OriginVo)request.getAttribute(Constants.ORIGIN), vo);
		if(!Constants.RESULT_SUCCESS.equals(resp.getResult().getResultCode())){
			throw new BossException(resp.getResult().getResultCode(), resp.getResult().getResultDesc());
		}else{
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("id", resp.getId());
		}
		return map;
	}
	
	
	/**
	 * 修改
	 * @tilte mdy
	 * @author zxl
	 * @date 2015年12月7日 上午11:43:16
	 * @param request
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> mdy(HttpServletRequest request,DictionaryAddReq req) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		DictionaryVo vo = new DictionaryVo();
		BeanUtils.copyProperties(vo, req);
		vo.setIsEnable(true);
		
		ResultVo resp = bossDictionaryService.updateDictionary((OriginVo)request.getAttribute(Constants.ORIGIN), vo);
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())){
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 删除
	 * @tilte del
	 * @author zxl
	 * @date 2015年11月26日 下午2:51:17
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> del(HttpServletRequest request,String id) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		List<Long> ids = new ArrayList<Long>();
		for(String i : id.split(",")){
			ids.add(Long.parseLong(i));
		}
		ResultVo resp = bossDictionaryService.deleteDictionaryBatch((OriginVo)request.getAttribute(Constants.ORIGIN), ids);
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())){
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}
		return map;
	}
}
