package com.froad.cbank.coremodule.normal.boss.support.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.message.SmsTemplateVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.message.SmsTemplateVoRes;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.SmsContentService;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.SmsContentPageVoRes;
import com.froad.thrift.vo.SmsContentVo;

/**
 * 
 * <p>标题: 短信模板</p>
 * <p>说明: 处理短信模板业务类</p>
 * <p>创建时间：2015年5月12日上午9:53:58</p>
 * <p>作者: 陈明灿</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
@Service
public class MessageTemplateSupport {

	@Resource
	SmsContentService.Iface smsContentService;
	/**
	 * 
	 * <p>功能简述：短信模板列表</p> 
	 * <p>使用说明：查询短信模板列表</p> 
	 * <p>创建时间：2015年5月12日上午10:19:12</p>
	 * <p>作者: 陈明灿</p>
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws TException 
	 */
	public Map<String, Object> list(SmsTemplateVoReq templateVoReq) throws TException {
		Map<String, Object> map = new HashMap<String, Object>();
		// 封装页码等信息
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(templateVoReq.getPageNumber());
		pageVo.setPageSize(templateVoReq.getPageSize());
		// TODO 注入查询条件(迭代延后)
		SmsContentVo smsContentVo = new SmsContentVo();
		smsContentVo.setClientId(templateVoReq.getClientId());
		smsContentVo.setChannel(templateVoReq.getChannel());
		smsContentVo.setSmsType(templateVoReq.getSmsType());
		//BeanUtils.copyProperties(smsContentVo, templateVoReq);
		SmsContentPageVoRes pageVoRes = smsContentService
				.getSmsContentByPage(pageVo, smsContentVo);
		LogCvt.info("短信模板列表server返回数据:", JSON.toJSONString(pageVoRes));
		PageVo pageRes = pageVoRes.getPage();
		Page page = new Page();
		if(null!=pageRes){
			BeanUtils.copyProperties(page, pageRes);
		}
		map.put("page", page);
		// 封装返回的list
		List<SmsContentVo> listRes = pageVoRes.getSmsContentVoList();
		SmsTemplateVoRes smsTemVoRes = null;
		List<SmsTemplateVoRes> list = null;
		if (null != listRes && listRes.size() > 0) {
			list = new ArrayList<SmsTemplateVoRes>();
			for (SmsContentVo smsContentVo2 : listRes) {
				smsTemVoRes = new SmsTemplateVoRes();
				BeanUtils.copyProperties(smsTemVoRes, smsContentVo2);
				list.add(smsTemVoRes);
			}
			map.put("messageTemplate", list);
		}
		return map;
	}
	
	/**
	 * 
	 * <p>功能简述：保存短息模板</p> 
	 * <p>使用说明：新增或者修改短信模板</p> 
	 * <p>创建时间：2015年5月12日下午5:30:39</p>
	 * <p>作者: 陈明灿</p>
	 * @param templateVoReq
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public Map<String, Object> saveOrUpdate(SmsTemplateVoReq templateVoReq) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		OriginVo originVo = new OriginVo();
		// TODO 添加限制条件注入(迭代延后)
		originVo.setPlatType(PlatType.boss);
		SmsContentVo smsContentVo = new SmsContentVo();
		// 短信模板字段注入
		// TODO 模板名称(迭代延后)
		// 模板类型
		smsContentVo.setSmsType(templateVoReq.getSmsType());
		// 发送渠道
		smsContentVo.setChannel(templateVoReq.getChannel());
		// 客户端id
		smsContentVo.setClientId(templateVoReq.getClientId());
		// TODO 频率校验(迭代延后)
		// TODO 元素(迭代延后)
		// 内容
		smsContentVo.setContent(templateVoReq.getContent());
		// smsContentVo.setIsEnable(isEnable);
		// smsContentVo.setMsgSuffix(channel);
		// smsContentVo.setValidTime(channel);
		if (null == templateVoReq.getId()) {
			// 新增短信模板
			AddResultVo resultVo = smsContentService.addSmsContent(
					originVo, smsContentVo);
			if (Constants.RESULT_SUCCESS.equals(resultVo.getResultVo()
					.getResultCode())) {
				map.put("code", Constants.RESULT_SUCCESS);
				map.put("message", resultVo.getResultVo().getResultDesc());
			} else {
				throw new BossException(resultVo.getResultVo().getResultCode(),  resultVo.getResultVo().getResultDesc());
			}
		} else {
			// 修改短信模板
			smsContentVo.setId(Long.parseLong(templateVoReq.getId()));
			ResultVo resultVo = smsContentService.updateSmsContent(
					originVo, smsContentVo);
			if (Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())) {
				map.put("code", Constants.RESULT_SUCCESS);
				map.put("message", resultVo.getResultDesc());
			} else {
				throw new BossException(resultVo.getResultCode(),  resultVo.getResultDesc());
			}
		}

		return map;
	}

	/**
	 * 
	 * <p>功能简述：删除短信模板</p> 
	 * <p>使用说明：根据id删除短信模板</p> 
	 * <p>创建时间：2015年5月12日下午3:48:00</p>
	 * <p>作者: 陈明灿</p>
	 * @param id
	 * @return
	 * @throws BossException 
	 * @throws TException 
	 */
	public Map<String, Object> deleteById(String id) throws BossException, TException {
		Map<String, Object> map = new HashMap<String, Object>();
		OriginVo originVo = new OriginVo();
		// TODO 删除条件
		originVo.setPlatType(PlatType.boss);
		SmsContentVo smsContentVo = new SmsContentVo();
		smsContentVo.setId(Long.parseLong(id));
		ResultVo resultVo = smsContentService.deleteSmsContent(originVo,
				smsContentVo);
		String resultCode = resultVo.getResultCode();
		if(Constants.RESULT_SUCCESS.equals(resultCode)){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", resultVo.getResultDesc());
		} else {
			throw new BossException(resultVo.getResultCode(),  resultVo.getResultDesc());
		}
		return map;
	}


}
