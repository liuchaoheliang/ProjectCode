package com.froad.cbank.coremodule.normal.boss.controller.label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.annotation.ImpExp;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.BossUser;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ExportRelateActivityVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.OutletLableListVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.OutletLableVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.OutletRelatedVoReq;
import com.froad.cbank.coremodule.normal.boss.support.label.OutletLableSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.ErrorEnums;
import com.froad.cbank.coremodule.normal.boss.utils.ExcelReaderUtils;
import com.froad.cbank.coremodule.normal.boss.utils.NumberUtil;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 门店推荐活动标签
 * @author yfy
 * @date: 2015年10月21日 下午15:23:21
 */
@Controller
@RequestMapping(value="outletLable")
public class OutletLableController {

	@Resource
	private OutletLableSupport outletLableSupport;
	
	/**
	 * 门店推荐活动标签列表查询
	 * @author yfy
	 * @date: 2015年10月21日 下午15:21:01
	 * @param model
	 * @param request
	 * @param listReq
	 */
	@Auth(keys={"boss_label_outlet_menu"})
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public void list(ModelMap model, HttpServletRequest request,OutletLableListVoReq listReq) {
		LogCvt.info("门店推荐活动标签列表查询条件:" + JSON.toJSONString(listReq));
		try {
			model.clear();
			model.putAll(outletLableSupport.list(listReq));
		} catch (Exception e) {
			LogCvt.error("门店推荐活动标签列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "门店推荐活动标签列表查询失败!!!");
		}
	}
	
	/**
	 * 门店推荐活动标签新增
	 * @author yfy
	 * @date: 2015年10月21日  下午16:48:15
	 * @param model
	 * @param request
	 * @param voReq
	 */
	@Auth(keys={"boss_label_outlet_add"})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(ModelMap model, HttpServletRequest request,@RequestBody OutletLableVoReq voReq) {
		LogCvt.info("门店推荐活动标签新增参数:" + JSON.toJSONString(voReq));
		try {
			if(voReq.getActivityName().length() >50){
				throw new BossException("活动名称限50字符内!");
			}
			if(voReq.getActivityDesc().length() < 10 || voReq.getActivityDesc().length() > 300){
				throw new BossException("描述限300字符内,最少10个字符!");
			}
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			voReq.setOperator(user.getName());//当前操作人
			model.clear();
			model.putAll(outletLableSupport.add(voReq));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("门店推荐活动标签新增请求异常"+e.getMessage(), e);
			new RespError(model, "门店推荐活动标签新增失败!!!");
		}
	}
	
	/**
	 * 门店推荐活动标签修改
	 * @author yfy
	 * @date: 2015年10月21日 下午17:07:55
	 * @param model
	 * @param request
	 * @param voReq
	 */
	@Auth(keys={"boss_label_outlet_modify"})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public void update(ModelMap model, HttpServletRequest request,@RequestBody OutletLableVoReq voReq) {
		LogCvt.info("门店推荐活动标签修改参数:" + JSON.toJSONString(voReq));
		try {
			if(!StringUtils.isNotBlank(voReq.getActivityId())){
				throw new BossException("活动ID不能为空!");
			}
			if(voReq.getActivityName().length() >50){
				throw new BossException("活动名称限50字符内!");
			}
			if(voReq.getActivityDesc().length() < 10 || voReq.getActivityDesc().length() > 300){
				throw new BossException("描述限300字符内,最少10个字符!");
			}
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			voReq.setOperator(user.getName());//当前操作人
			model.clear();
			model.putAll(outletLableSupport.update(voReq));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("门店推荐活动标签修改请求异常"+e.getMessage(), e);
			new RespError(model, "门店推荐活动标签修改失败!!!");
		}
	}
	
	/**
	 * 门店推荐活动标签详情
	 * @author yfy
	 * @date: 2015年10月21日 下午17:29:34
	 * @param model
	 * @param request
	 * @param activityId
	 * @param clientId
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public void detail(ModelMap model, HttpServletRequest request,OutletRelatedVoReq voReq) {
		LogCvt.info("门店推荐活动标签详情查询条件" + JSON.toJSONString(voReq));
		try {
			if(!StringUtil.isNotBlank(voReq.getActivityId())){
				throw new BossException("活动ID不能为空!");
			}
			if(!StringUtil.isNotBlank(voReq.getClientId())){
				throw new BossException("客户端不能为空!");
			}
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			voReq.setOperator(user.getName());//当前操作人
			model.clear();
			model.putAll(outletLableSupport.detail(voReq));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("门店推荐活动标签详情查询请求异常"+e.getMessage(), e);
			new RespError(model, "门店推荐活动标签详情查询失败!!!");
		}
	}
	
	/**
	 * 门店推荐活动标签禁用
	 * @author yfy
	 * @date: 2015年10月21日 下午17:42:14
	 * @param model
	 * @param request
	 * @param voReq
	 */
	@Auth(keys={"boss_label_outlet_disable"})
	@RequestMapping(value = "isEnable", method = RequestMethod.GET)
	public void isEnable(ModelMap model, HttpServletRequest request,OutletRelatedVoReq voReq) {
		LogCvt.info("门店推荐活动标签禁用参数:" + JSON.toJSONString(voReq));
		try {
			if(!StringUtil.isNotBlank(voReq.getActivityId())){
				throw new BossException("活动ID不能为空!");
			}
			if(!StringUtil.isNotBlank(voReq.getClientId())){
				throw new BossException("客户端不能为空!");
			}
			if(!StringUtil.isNotBlank(voReq.getStatus())){
				throw new BossException("状态不能为空!");
			}
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			voReq.setOperator(user.getName());//当前操作人
			model.clear();
			model.putAll(outletLableSupport.isEnable(voReq));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("门店推荐活动标签禁用请求异常"+e.getMessage(), e);
			new RespError(model, "门店推荐活动标签禁用失败!!!");
		}
	}
	
	/**
	 * 关联门店
	 * @author yfy
	 * @date: 2015年10月23日 下午14:20:01
	 * @param model
	 * @param request
	 * @param voReq
	 */
	@RequestMapping(value = "relateOutlet", method = RequestMethod.POST)
	public void relateOutlet(ModelMap model, HttpServletRequest request,@RequestBody OutletRelatedVoReq voReq) {
		LogCvt.info("关联门店参数:" + JSON.toJSONString(voReq));
		try {
			if(!StringUtil.isNotBlank(voReq.getActivityNo())){
				throw new BossException("活动编号不能为空!");
			}
			if(!StringUtil.isNotBlank(voReq.getClientId())){
				throw new BossException("客户端不能为空!");
			}
			if(!StringUtil.isNotBlank(voReq.getOutletId())){
				throw new BossException("门店ID不能为空!");
			}
			if(!StringUtil.isNotBlank(voReq.getWeight())){
				throw new BossException("权重不能为空!");
			}
			if(!voReq.getWeight().matches("[1-9][0-9]*$")){
				throw new BossException("权重必须为大于零小于100,000的整数!");
			}
			if(Integer.parseInt(voReq.getWeight()) < 0 || Integer.parseInt(voReq.getWeight()) > 100000){
				throw new BossException("权重必须为大于零小于100,000的整数!");
			}
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			voReq.setOperator(user.getName());//当前操作人
			model.clear();
			model.putAll(outletLableSupport.relateOutlet(voReq));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("关联门店请求异常"+e.getMessage(), e);
			new RespError(model, "关联门店失败!!!");
		}
	}
	
	/**
	 * 关联门店删除
	 * @author yfy
	 * @date: 2015年10月23日 下午14:20:01
	 * @param model
	 * @param request
	 * @param voReq
	 */
	@RequestMapping(value = "delete", method = RequestMethod.DELETE)
	public void delete(ModelMap model, HttpServletRequest request,OutletRelatedVoReq voReq) {
		LogCvt.info("关联门店删除参数:" + JSON.toJSONString(voReq));
		try {
			if(!StringUtil.isNotBlank(voReq.getId())){
				throw new BossException("权重关联ID不能为空!");
			}
			if(!StringUtil.isNotBlank(voReq.getActivityNo())){
				throw new BossException("活动编号不能为空!");
			}
			if(!StringUtil.isNotBlank(voReq.getClientId())){
				throw new BossException("客户端不能为空!");
			}
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			voReq.setOperator(user.getName());//当前操作人
			model.clear();
			model.putAll(outletLableSupport.delete(voReq));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("关联门店删除请求异常"+e.getMessage(), e);
			new RespError(model, "关联门店删除失败!!!");
		}
	}
	
	/**
	 * 关联门店权重调整
	 * @author yfy
	 * @date: 2015年10月23日 下午14:30:08
	 * @param model
	 * @param request
	 * @param voReq
	 */
	@RequestMapping(value = "adjustWeight", method = RequestMethod.GET)
	public void adjustWeight(ModelMap model, HttpServletRequest request,OutletRelatedVoReq voReq) {
		LogCvt.info("关联门店权重调整参数:" + JSON.toJSONString(voReq));
		try {
			if(!StringUtil.isNotBlank(voReq.getId())){
				throw new BossException("权重关联ID不能为空!");
			}
			if(!StringUtil.isNotBlank(voReq.getActivityNo())){
				throw new BossException("活动编号不能为空!");
			}
			if(!StringUtil.isNotBlank(voReq.getClientId())){
				throw new BossException("客户端不能为空!");
			}
			if(!StringUtil.isNotBlank(voReq.getWeight())){
				throw new BossException("权重不能为空!");
			}
			if(!voReq.getWeight().matches("[1-9][0-9]*$")){
				throw new BossException("权重必须为大于零小于100,000的整数!");
			}
			if(Integer.parseInt(voReq.getWeight()) < 0 || Integer.parseInt(voReq.getWeight()) > 100000){
				throw new BossException("权重必须为大于零小于100,000的整数!");
			}
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			voReq.setOperator(user.getName());//当前操作人
			model.clear();
			model.putAll(outletLableSupport.adjustWeight(voReq));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("关联门店权重调整请求异常"+e.getMessage(), e);
			new RespError(model, "关联门店权重调整失败!!!");
		}
	}
	
	/**
	 * 根据门店ID获取门店名称
	 * @author yfy
	 * @date: 2015年10月21日 下午17:42:14
	 * @param model
	 * @param request
	 * @param clientId
	 * @param outletId
	 */
	@RequestMapping(value = "outletName", method = RequestMethod.GET)
	public void getOutletNameByOutletId(ModelMap model, HttpServletRequest request,String clientId,String outletId) {
		LogCvt.info("根据门店ID获取门店名称条件:outletId:" +outletId);
		try {
			if(!StringUtils.isNotBlank(clientId)){
				throw new BossException("客户端不能为空!");
			}
			if(!StringUtils.isNotBlank(outletId)){
				throw new BossException("门店ID不能为空!");
			}
			model.clear();
			model.putAll(outletLableSupport.getOutletNameByOutletId(clientId,outletId));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("根据门店ID获取门店名称请求异常"+e.getMessage(), e);
			new RespError(model, "根据门店ID获取门店名称失败!!!");
		}
	}
	
	/**
	 * 根据客户端生成活动编号
	 * @author yfy
	 * @date: 2015年10月23日 下午17:42:14
	 * @param model
	 * @param request
	 * @param activityType
	 * @param clientId
	 */
	@RequestMapping(value = "createActivityNo", method = RequestMethod.GET)
	public void createActivityNo(ModelMap model, HttpServletRequest request,String activityType,String clientId) {
		LogCvt.info("根据客户端生成活动编号条件:activityType:"+activityType+",clientId:"+clientId);
		try {
			if(!StringUtils.isNotBlank(activityType)){
				throw new BossException("活动类型不能为空!");
			}
			if(!StringUtils.isNotBlank(clientId)){
				throw new BossException("客户端不能为空!");
			}
			model.clear();
			model.putAll(outletLableSupport.createActivityNo(activityType,clientId));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("根据客户端生成活动编号请求异常"+e.getMessage(), e);
			new RespError(model, "根据客户端生成活动编号失败!!!");
		}
	}
	
	/**
	 * 查询关联门店信息列表
	 * @author yfy
	 * @date: 2015年10月21日 下午17:42:14
	 * @param model
	 * @param request
	 * @param listReq
	 */
	@RequestMapping(value = "outletList", method = RequestMethod.GET)
	public void outletList(ModelMap model, HttpServletRequest request,OutletLableListVoReq listReq) {
		LogCvt.info("查询关联门店信息列表条件:"+JSON.toJSONString(listReq));
		try {
			if(!StringUtils.isNotBlank(listReq.getClientId())){
				throw new BossException("客户端不能为空!");
			}
			model.clear();
			model.putAll(outletLableSupport.outletList(listReq));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("查询关联门店信息列表请求异常"+e.getMessage(), e);
			new RespError(model, "查询关联门店信息列表失败!!!");
		}
	}
	
	/**
	 * 上传关联门店文件
	 * @author yfy
	 * @date: 2015年10月21日 下午17:42:14
	 * @param req
	 * @param res
	 * @param file
	 * @param voReq
	 * @throws IOException
	 */
	@ImpExp
	@RequestMapping(value="uploadOutlet", method=RequestMethod.POST)
	public void uploadOutletActivity(HttpServletRequest req, HttpServletResponse res, @RequestParam("file") MultipartFile file,
			OutletRelatedVoReq voReq) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		LogCvt.info("上传关联门店文件条件:"+JSON.toJSONString(voReq));
		String json = "";
		try {
			res.setCharacterEncoding("UTF-8");
			res.setContentType("text/html");
			if(file.getOriginalFilename().indexOf(".xls") == -1 
					&& file.getOriginalFilename().indexOf(".xlsx") == -1) {
				throw new BossException("文件格式有误，请上传excel文件"); 
			}
			if(!StringUtil.isNotBlank(voReq.getActivityNo())){
				throw new BossException("活动编号不能为空!");
			}
			if(!StringUtil.isNotBlank(voReq.getClientId())){
				throw new BossException("客户端不能为空!");
			}
			BossUser user = (BossUser) req.getAttribute(Constants.BOSS_USER);
			voReq.setOperator(user.getName());//当前操作人
			List<List<String>> data = new ArrayList<List<String>>();
			//excel读取
			ExcelReaderUtils.readExcel(file.getInputStream(), file.getOriginalFilename(), null, 5, data);
			//上传数据检查
			if(data.size()==0){
				throw new BossException("上传文件内容为空!");
			}
			for(int i = 0; i< data.size();i++){
				List<String> temp = data.get(i);
				//首行
				if(i == 0){
					if(!("序号".equals(temp.get(0))&&"门店名称".equals(temp.get(1))
							&&"门店ID".equals(temp.get(2).toUpperCase())&&"权重".equals(temp.get(3))
							&&"活动编号".equals(temp.get(4)))){
						throw new BossException("文件内容格式错误");
					}
				}else{
					if(StringUtil.isBlank(temp.get(2))){
						throw new BossException((i+1)+"行数据门店ID不能为空。请修改后重新上传");
					}
					if(StringUtil.isBlank(temp.get(3))){
						throw new BossException((i+1)+"行数据权重不能为空。请修改后重新上传");
					}
					if(!NumberUtils.isNumber(temp.get(3))){
						throw new BossException((i+1)+"行权重数据非法，必须为大于零小于100,000整数。请修改后重新上传");
					}
					String weight = NumberUtil.subZeroAndDot(temp.get(3));
					if(!weight.matches("[1-9][0-9]*$")){
						throw new BossException((i+1)+"行权重数据非法，必须为大于零小于100,000整数。请修改后重新上传");
					}
					if(Integer.parseInt(weight) <= 0 || Integer.parseInt(weight) >= 100000){
						throw new BossException((i+1)+"行权重数据非法，必须为大于零小于100,000整数。请修改后重新上传");
					}
					if(StringUtil.isBlank(temp.get(4))){
						throw new BossException((i+1)+"行数据活动编号不能为空。请修改后重新上传");
					}
					if(!voReq.getActivityNo().equals(temp.get(4))){
						throw new BossException((i+1)+"行数据活动编号与本次活动不一致。请修改后重新上传");
					}
				}
			}
			map = outletLableSupport.uploadOutletActivity(voReq,data);
			json = JSONArray.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE：" + json);
		} catch (BossException e) {
			map.put("code", e.getCode());
			map.put("message", e.getMsg());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE：" + json);
			LogCvt.error(e.getMessage(), e);
		} catch (Exception e) {
			map.put("code", ErrorEnums.thrift_err.getCode());
			map.put("message", ErrorEnums.thrift_err.getMsg());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE：" + json);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * <p>Title:关联门店信息导出</p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月12日 下午4:20:34 
	 * @param model
	 * @param req
	 */
	@RequestMapping(value="/export",method=RequestMethod.GET)
	public void exportProductRelateActivityTag(ModelMap model,ExportRelateActivityVoReq req){
		LogCvt.info("关联门店信息导出条件:"+JSON.toJSONString(req));
		try {
			model.clear();
			model.putAll(outletLableSupport.exportRelateActivity(req));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
	
}
