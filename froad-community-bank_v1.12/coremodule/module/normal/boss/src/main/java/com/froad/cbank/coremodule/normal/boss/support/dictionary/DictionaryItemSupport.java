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
import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.cbank.coremodule.normal.boss.pojo.dictionary.DictionaryItemAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.dictionary.DictionaryItemReq;
import com.froad.cbank.coremodule.normal.boss.pojo.dictionary.DictionaryItemRes;
import com.froad.cbank.coremodule.normal.boss.support.common.ClientSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.BossDictionaryItemService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.dictionary.DictionaryItemPageVoRes;
import com.froad.thrift.vo.dictionary.DictionaryItemVo;

/**
 * 字典
 * @ClassName DictionarySupport
 * @author zxl
 * @date 2015年11月26日 下午2:38:07
 */
@Service
public class DictionaryItemSupport {
	
	@Resource
	BossDictionaryItemService.Iface bossDictionaryItemService;
	
	@Resource
	ClientSupport clientSupport;
	
	/**
	 * 列表查询
	 * @tilte list
	 * @author zxl
	 * @date 2015年11月26日 下午2:51:00
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> list(DictionaryItemReq req) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		DictionaryItemVo vo = new DictionaryItemVo();
		if(req.getId()!=null){
			vo.setId(req.getId());
		}
		if(req.getDicId()!=null){
			vo.setDicId(req.getDicId());
		}
		if(StringUtils.isNotBlank(req.getItemCode())){
			vo.setItemCode(req.getItemCode());
		}
		if(StringUtils.isNotBlank(req.getItemName())){
			vo.setItemName(req.getItemName());
		}
		vo.setIsEnable(true);
		
		PageVo page = new PageVo();
		page.setPageSize(req.getPageSize());
		page.setPageNumber(req.getPageNumber());
		page.setFirstRecordTime(req.getFirstRecordTime());
		page.setLastRecordTime(req.getLastRecordTime());
		page.setLastPageNumber(req.getLastPageNumber());
		
		DictionaryItemPageVoRes resp = bossDictionaryItemService.getDictionaryItemByPage(page, vo);
		
		if(Constants.RESULT_SUCCESS.equals(resp.getResultVo().getResultCode())){
			List<DictionaryItemRes> list = new ArrayList<DictionaryItemRes>();
			if(resp.getDictionaryItemVoList()!=null&&resp.getDictionaryItemVoListSize()>0){
				List<ClientRes> client = clientSupport.getClient();
				for(DictionaryItemVo v :resp.getDictionaryItemVoList()){
					DictionaryItemRes r = new DictionaryItemRes();
					BeanUtils.copyProperties(r, v);
					for(ClientRes cl : client){
						if(cl.getClientId().equals(v.getClientId())){
							r.setClientName(cl.getClientName());
							break;
						}
					}
					list.add(r);
				}
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
	public HashMap<String,Object> add(HttpServletRequest request,DictionaryItemAddReq req) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		DictionaryItemVo vo = new DictionaryItemVo();
		BeanUtils.copyProperties(vo, req);
		vo.setIsEnable(true);
		
		ResultVo resp = bossDictionaryItemService.addDictionaryItem((OriginVo)request.getAttribute(Constants.ORIGIN), vo);
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())){
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 修改
	 * @tilte mdy
	 * @author zxl
	 * @date 2015年11月27日 上午11:01:23
	 * @param request
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> mdy(HttpServletRequest request,DictionaryItemAddReq req) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		DictionaryItemVo vo = new DictionaryItemVo();
		BeanUtils.copyProperties(vo, req);
		vo.setIsEnable(true);
		
		ResultVo resp = bossDictionaryItemService.updateDictionaryItem((OriginVo)request.getAttribute(Constants.ORIGIN), vo);
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
		ResultVo resp = bossDictionaryItemService.deleteDictionaryItemBatch((OriginVo)request.getAttribute(Constants.ORIGIN), ids);
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())){
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}
		return map;
	}
}
