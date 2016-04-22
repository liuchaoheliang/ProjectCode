package com.froad.cbank.coremodule.normal.boss.support.label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ExportRelateActivityVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.OutletLableListVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.OutletLableVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.OutletRecommendVo;
import com.froad.cbank.coremodule.normal.boss.pojo.label.OutletRelatedVoReq;
import com.froad.cbank.coremodule.normal.boss.support.common.ClientSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.NumberUtil;
import com.froad.thrift.service.BusinessZoneTagService;
import com.froad.thrift.service.OutletActivityService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.businesszonetag.GenerateActivityNoReq;
import com.froad.thrift.vo.businesszonetag.GenerateActivityNoRes;
import com.froad.thrift.vo.outletActivity.AdjustOutletWeightReqVo;
import com.froad.thrift.vo.outletActivity.DeleteRelateOutletReqVo;
import com.froad.thrift.vo.outletActivity.EnableOutletActivityReqVo;
import com.froad.thrift.vo.outletActivity.ExportRelateActivityTagReqVo;
import com.froad.thrift.vo.outletActivity.InputRelateOutletActivityReqVo;
import com.froad.thrift.vo.outletActivity.InputRelateOutletActivityVo;
import com.froad.thrift.vo.outletActivity.OutletActivityTagDetailReqVo;
import com.froad.thrift.vo.outletActivity.OutletActivityTagDetailResVo;
import com.froad.thrift.vo.outletActivity.OutletNameAndMerchantNameResVo;
import com.froad.thrift.vo.outletActivity.OutletWeightActivityTagPageReqVo;
import com.froad.thrift.vo.outletActivity.OutletWeightActivityTagPageVo;
import com.froad.thrift.vo.outletActivity.OutletWeightActivityTagVo;
import com.froad.thrift.vo.outletActivity.RecommendActivityTagPageVo;
import com.froad.thrift.vo.outletActivity.RecommendActivityTagVo;
import com.froad.thrift.vo.outletActivity.RelateOutletActivityVo;

/**
 * 门店推荐活动标签
 * @author yfy
 * @date: 2015年10月22日 下午16:30:10
 */
@Service
public class OutletLableSupport {
	
	@Resource
	OutletActivityService.Iface outletActivityService;
	@Resource
	ClientSupport clientSupport;
	@Resource
	BusinessZoneTagService.Iface businessZoneTagService;
	
	/**
	 * 门店推荐活动标签列表查询
	 * @author yfy
	 * @date: 2015年10月23日 上午09:42:01
	 * @param listReq
	 * @return
	 * @throws TException
	 */
	public Map<String, Object> list(OutletLableListVoReq listReq) throws TException {
		Map<String, Object> map = new HashMap<String, Object>();
		// 封装分页查询条件
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(listReq.getPageNumber());
		pageVo.setPageSize(listReq.getPageSize());
		// 封装查询条件
		RecommendActivityTagVo voReq = new RecommendActivityTagVo();
		if(StringUtil.isNotEmpty(listReq.getClientId())){//客户端
			voReq.setClientId(listReq.getClientId());
		}
		if(StringUtil.isNotEmpty(listReq.getStatus())){//状态
			voReq.setStatus(listReq.getStatus());
		}
		if(StringUtil.isNotEmpty(listReq.getActivityName())){//名称
			voReq.setActivityName(listReq.getActivityName());
		}
		OutletRecommendVo recommendVo = null;
		ArrayList<OutletRecommendVo> listVoRes = null;
		//调用server端列表接口
		RecommendActivityTagPageVo queryRes = outletActivityService.findOutletTagByPage(voReq, pageVo);
		List<RecommendActivityTagVo> listRes = queryRes.getRecommendvos();
		if(listRes != null && listRes.size() > 0){
			listVoRes = new ArrayList<OutletRecommendVo>();
			List<ClientRes> clientList = clientSupport.getClient();
			for(RecommendActivityTagVo tagVo : listRes){
				recommendVo = new OutletRecommendVo();
				BeanUtils.copyProperties(recommendVo, tagVo);// 封装列表数据基本信息
				recommendVo.setClientName(this.getClientNameByClientId(clientList, tagVo.getClientId()));
				listVoRes.add(recommendVo);
			}
		}
		// 封装分页数据
		Page page = new Page();
		if(queryRes.getPageVo() != null){
			BeanUtils.copyProperties(page, queryRes.getPageVo());
		}
		map.put("page", page);
		map.put("list", listVoRes);
		return map;
	}
	
	/**
	 * 门店推荐活动标签新增
	 * @author yfy
	 * @date: 2015年10月22日 上午10:26:10
	 * @param voReq
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> add(OutletLableVoReq voReq) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		RecommendActivityTagVo addReq = new RecommendActivityTagVo();
		BeanUtils.copyProperties(addReq, voReq);//封装数据基本信息
		//调用server端新增接口
		ResultVo resultVo = outletActivityService.addOutletActivityTag(addReq);
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "新增门店推荐活动标签信息成功");
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 门店推荐活动标签修改
	 * @author yfy
	 * @date: 2015年10月22日 上午10:58:25
	 * @param voReq
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> update(OutletLableVoReq voReq) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		RecommendActivityTagVo updateReq = new RecommendActivityTagVo();
		updateReq.setId(Long.valueOf(voReq.getActivityId()));
		BeanUtils.copyProperties(updateReq, voReq);//封装数据基本信息
		//调用server端修改接口
		ResultVo resultVo = outletActivityService.updateOutletActivityTag(updateReq);
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "修改门店推荐活动标签信息成功");
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 门店推荐活动标签详情
	 * @author yfy
	 * @date: 2015年10月23日 下午17:52:11
	 * @param activityId
	 * @param clientId
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> detail(OutletRelatedVoReq voReq) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		OutletActivityTagDetailReqVo reqVo = new OutletActivityTagDetailReqVo();
		BeanUtils.copyProperties(reqVo, voReq);//封装关联门店数据（活动ID，活动编号，客户端，操作人）
		//调用server端详情接口
		OutletActivityTagDetailResVo detailRes = outletActivityService.findOutletTagDetail(reqVo);
		if(detailRes.getResultVo().getResultCode().equals(Constants.RESULT_SUCCESS)){
			OutletRecommendVo recommendVo = null;
			//获取门店推荐活动基本信息
			if(detailRes.getRecommendVo() != null && detailRes.getRecommendVo().getActivityNo() != null){
			    recommendVo = new OutletRecommendVo();
			    //封装数据基本信息
				BeanUtils.copyProperties(recommendVo, detailRes.getRecommendVo());
				List<ClientRes> clientList = clientSupport.getClient();
				recommendVo.setClientName(this.getClientNameByClientId(clientList,detailRes.getRecommendVo().getClientId()));
				map.put("outletLable", recommendVo);
			}
		}else{
			throw new BossException(detailRes.getResultVo().getResultCode(), detailRes.getResultVo().getResultDesc());
		}
		return map;
	}

	/**
	 * 门店推荐活动标签禁用
	 * @author yfy
	 * @date: 2015年10月23日 下午13:13:13
	 * @param voReq
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> isEnable(OutletRelatedVoReq voReq) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		EnableOutletActivityReqVo reqVo = new EnableOutletActivityReqVo();
		reqVo.setId(voReq.getActivityId());//标签ID
		reqVo.setClientId(voReq.getClientId());//客户端
		reqVo.setStatus(voReq.getStatus());//状态: 启1; 禁用2
		reqVo.setOperator(voReq.getOperator());//操作人
		ResultVo resultVo = outletActivityService.enableOutletRecommendActivityTag(reqVo);
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "已禁用该门店推荐活动标签信息");
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 关联门店
	 * @author yfy
	 * @date: 2015年10月23日 下午14:23:14
	 * @param voReq
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> relateOutlet(OutletRelatedVoReq voReq) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		RelateOutletActivityVo relateVoReq = new RelateOutletActivityVo();
		BeanUtils.copyProperties(relateVoReq, voReq);//封装关联门店数据（活动ID（可选），活动编号，客户端，门店ID，权重，操作人）
		ResultVo resultVo = outletActivityService.relateOutletInfo(relateVoReq);
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "关联门店成功");
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 关联门店删除
	 * @author yfy
	 * @date: 2015年10月23日 下午14:23:14
	 * @param voReq
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> delete(OutletRelatedVoReq voReq) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		DeleteRelateOutletReqVo reqVo = new DeleteRelateOutletReqVo();
		BeanUtils.copyProperties(reqVo, voReq);//封装删除关联门店数据（权重ID，客户端，活动编号，操作人）
		ResultVo resultVo = outletActivityService.deleteRelateOutlet(reqVo);
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "已删除该关联门店信息");
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 关联门店权重调整
	 * @author yfy
	 * @date: 2015年10月23日 下午14:33:11
	 * @param voReq
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> adjustWeight(OutletRelatedVoReq voReq) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		AdjustOutletWeightReqVo reqVo = new AdjustOutletWeightReqVo();
		BeanUtils.copyProperties(reqVo, voReq);//封装权重调整数据（权重ID，活动编号，客户端，权重，操作人）
		ResultVo resultVo = outletActivityService.adjustOutletWeight(reqVo);
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "已调整该关联门店权重");
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 根据客户端生成活动编号
	 * @author yfy
	 * @date: 2015年10月23日 下午14:03:31
	 * @param activityType
	 * @param clientId
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> createActivityNo(String activityType, String clientId) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		GenerateActivityNoReq activityNoReq = new GenerateActivityNoReq();
		activityNoReq.setActivityType(activityType);//活动类型1-商户活动2-门店活动3-商品活动
		activityNoReq.setClientId(clientId);
		GenerateActivityNoRes activityNoRes = businessZoneTagService.generateActivityNo(activityNoReq);
		if(Constants.RESULT_SUCCESS.equals(activityNoRes.getResultVo().getResultCode())){
			map.put("activityNo", activityNoRes.getActiviyNo());
		}else{
			throw new BossException(activityNoRes.getResultVo().getResultCode(), activityNoRes.getResultVo().getResultDesc());
		}
		return map;
	}
		
	/**
	 * 查询关联门店信息列表
	 * @author yfy
	 * @date: 2015年10月23日 下午16:23:15
	 * @param listReq
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> outletList(OutletLableListVoReq listReq) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		OutletWeightActivityTagPageReqVo pageReqVo = new OutletWeightActivityTagPageReqVo();
		if(StringUtil.isNotBlank(listReq.getActivityId())){//活动ID
			pageReqVo.setActivityId(listReq.getActivityId());
		}
		pageReqVo.setClientId(listReq.getClientId());//客户端
		pageReqVo.setActivityNo(listReq.getActivityNo());//活动编号
		// 封装分页查询条件
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(listReq.getPageNumber());
		pageVo.setPageSize(listReq.getPageSize());
		pageReqVo.setPageVo(pageVo);
		
		OutletRelatedVoReq relatedVo = null;
		ArrayList<OutletRelatedVoReq> outletList = null;
		//调用server端列表接口
		OutletWeightActivityTagPageVo queryRes = outletActivityService.findRelateOutletInfoByPage(pageReqVo);//2-门店活动
		List<OutletWeightActivityTagVo> listRes = queryRes.getRelateOutlets();
		if(listRes != null && listRes.size() > 0){
			outletList = new ArrayList<OutletRelatedVoReq>();
			for(OutletWeightActivityTagVo tagVo : listRes){
				relatedVo = new OutletRelatedVoReq();
				relatedVo.setOutletId(tagVo.getElementId());
				BeanUtils.copyProperties(relatedVo, tagVo);// 封装列表数据基本信息
				//如果最后更改时间为0，就返回创建时间
				if(tagVo.getUpdateTime()==0){
					relatedVo.setUpdateTime(tagVo.getCreateTime());
				}else{
					relatedVo.setUpdateTime(tagVo.getUpdateTime());
				}
				outletList.add(relatedVo);
			}
		}
		// 封装分页数据
		Page page = new Page();
		if(queryRes.getPageVo() != null){
			BeanUtils.copyProperties(page, queryRes.getPageVo());
		}
		map.put("page", page);
		map.put("outletList", outletList);
		return map;
	}
	
	/**
	 * 上传关联门店
	 * @author yfy
	 * @date: 2015年10月23日 上午10:23:07
	 * @param voReq
	 * @return
	 * @throws TException
	 * @throws BossException
	 * @throws IOException
	 */
	public Map<String, Object> uploadOutletActivity(OutletRelatedVoReq voReq,List<List<String>> data)
					throws TException, BossException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if(data != null && data.size() > 0){
			InputRelateOutletActivityReqVo reqVo = new InputRelateOutletActivityReqVo(); 
			BeanUtils.copyProperties(reqVo, voReq);//封装列表数据基本信息（活动ID（可选），活动编号，客户端，操作人）
			
			List<InputRelateOutletActivityVo> vos = new ArrayList<InputRelateOutletActivityVo>();
			InputRelateOutletActivityVo vo = null;
			for(int i = 1; i < data.size();i++){
				List<String> temp = data.get(i);
				vo = new InputRelateOutletActivityVo();
				vo.setId(i+1);
				vo.setOutletName(temp.get(1));
				vo.setOutletId(temp.get(2));
				vo.setWeight(NumberUtil.subZeroAndDot(temp.get(3)));
				vo.setActivityNo(temp.get(4));
				vos.add(vo);
			}
			reqVo.setVos(vos);
			//调用server端后台批量上传接口
			ResultVo resultVo = outletActivityService.inputRelateOutletInfo(reqVo);
			if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
				map.put("code", Constants.RESULT_SUCCESS);
				map.put("message", "上传关联文件成功");
			}else{
				throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
			}
		}
		return map;
	}
	
	/**
     * 根据门店ID获取门店名称
     * @param clientId
     * @param outletId
     * @return
     * @throws TException
	 * @throws BossException 
     */
	public Map<String, Object> getOutletNameByOutletId(String clientId, String outletId) throws TException, BossException{
		Map<String, Object> map = new HashMap<String, Object>();
		//调用server后台接口获取数据
		OutletNameAndMerchantNameResVo outletVo = outletActivityService.queryOutletNameAndMerchantNameByOutletId(clientId,outletId);
		if(Constants.RESULT_SUCCESS.equals(outletVo.getResultVo().getResultCode())){
			map.put("outletName", outletVo.getOutletName());
		}else{
			throw new BossException(outletVo.getResultVo().getResultCode(), outletVo.getResultVo().getResultDesc());
		}
		return map;
    }
	
	/**
	 * 根据客户端ID获取客户端名称
	 * @param clientId
	 * @return
	 * @throws TException
	 */
	public String getClientNameByClientId(List<ClientRes> clientList,String clientId) throws TException{
		String clientName = "";
		if(clientList != null && clientList.size() > 0 
				&& StringUtil.isNotBlank(clientId)){
			//获取客户端名称
			for(ClientRes clientRes : clientList){
				if(clientRes.getClientId().equals(clientId)){
					clientName = clientRes.getClientName();
					break;
				}
			}
		}
		return clientName;
	}
	
	/**
	 * 
	 * <p>Title:关联门店信息导出 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月12日 下午4:26:11 
	 * @param req
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String,Object> exportRelateActivity(ExportRelateActivityVoReq req) throws TException, BossException{
		HashMap<String,Object> map = new HashMap<String,Object>();
		ExportRelateActivityTagReqVo temp = new ExportRelateActivityTagReqVo();
		BeanUtils.copyProperties(temp, req);
		ExportResultRes res = outletActivityService.exportOutletRelateActivityTag(temp);
		if(!Constants.RESULT_SUCCESS.equals(res.getResultVo().getResultCode())){
			throw new BossException(res.getResultVo().getResultCode(), res.getResultVo().getResultDesc());
		}else{
			map.put("code", res.getResultVo().getResultCode());
			map.put("message","操作成功！");
			map.put("url", res.getUrl());
		}
		return map;
	}

}
