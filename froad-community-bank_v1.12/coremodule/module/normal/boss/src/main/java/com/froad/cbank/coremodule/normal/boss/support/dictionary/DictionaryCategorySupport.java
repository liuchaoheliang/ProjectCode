package com.froad.cbank.coremodule.normal.boss.support.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.dictionary.DictionaryCategoryAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.dictionary.DictionaryCategoryRes;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.BossDictionaryCategoryService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.dictionary.DictionaryCategoryListVoRes;
import com.froad.thrift.vo.dictionary.DictionaryCategoryVo;

/**
 * 字典分类
 * @ClassName DictionaryCategorySupport
 * @author zxl
 * @date 2015年11月26日 下午2:38:07
 */
@Service
public class DictionaryCategorySupport {
	
	@Resource
	BossDictionaryCategoryService.Iface bossDictionaryCategoryService;
	
	/**
	 * 树列表查询
	 * @tilte list
	 * @author zxl
	 * @date 2015年11月26日 下午2:51:00
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> list() throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		DictionaryCategoryVo vo = new DictionaryCategoryVo();
		vo.setIsEnable(true);
		DictionaryCategoryListVoRes resp = bossDictionaryCategoryService.getDictionaryCategory(vo);
		
		if(Constants.RESULT_SUCCESS.equals(resp.getResultVo().getResultCode())){
			List<DictionaryCategoryRes> list = new ArrayList<DictionaryCategoryRes>();
			
			for(DictionaryCategoryVo v :resp.getDictionaryCategoryVoList()){
				DictionaryCategoryRes r = new DictionaryCategoryRes();
				BeanUtils.copyProperties(r, v);
				list.add(r);
			}
			
			map.put("code", Constants.RESULT_SUCCESS);
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
	public HashMap<String,Object> add(HttpServletRequest request,DictionaryCategoryAddReq req) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		DictionaryCategoryVo vo = new DictionaryCategoryVo();
		BeanUtils.copyProperties(vo, req);
		vo.setIsEnable(true);
		ResultVo resp = bossDictionaryCategoryService.addDictionaryCategory((OriginVo)request.getAttribute(Constants.ORIGIN), vo);
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())){
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 修改
	 * @tilte mdy
	 * @author zxl
	 * @date 2015年12月2日 下午3:47:29
	 * @param request
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> mdy(HttpServletRequest request,DictionaryCategoryAddReq req) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		DictionaryCategoryVo vo = new DictionaryCategoryVo();
		BeanUtils.copyProperties(vo, req);
		ResultVo resp = bossDictionaryCategoryService.updateDictionaryCategory((OriginVo)request.getAttribute(Constants.ORIGIN), vo);
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
	public HashMap<String,Object> del(HttpServletRequest request,Long id) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		ResultVo resp=bossDictionaryCategoryService.deleteDictionaryCategory((OriginVo)request.getAttribute(Constants.ORIGIN), id);
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())){
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 导出
	 * @tilte export
	 * @author zxl
	 * @date 2015年12月1日 下午4:54:37
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> export(String id,Integer type) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		List<Long> ids = new ArrayList<Long>();
		for(String s : id.split(",")){
			ids.add(Long.parseLong(s));
		}
		ExportResultRes resp=bossDictionaryCategoryService.getDictionaryCategoryExportUrl(type,ids);
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultVo().getResultCode())){
			throw new BossException(resp.getResultVo().getResultCode(), resp.getResultVo().getResultDesc());
		}
		map.put("code", Constants.RESULT_SUCCESS);
		map.put("url", resp.getUrl());
		return map;
	}
}
