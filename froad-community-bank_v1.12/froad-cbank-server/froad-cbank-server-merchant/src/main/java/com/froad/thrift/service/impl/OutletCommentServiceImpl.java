package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.OutletCommentLogic;
import com.froad.logic.impl.OutletCommentLogicImpl;
import com.froad.po.OutletComment;
import com.froad.po.OutletCommentAddRes;
import com.froad.po.OutletCommentLevelAmount;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.OutletCommentService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletCommentAddVoRes;
import com.froad.thrift.vo.OutletCommentLevelAmountVo;
import com.froad.thrift.vo.OutletCommentPageVoRes;
import com.froad.thrift.vo.OutletCommentVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;

/**
 * 
 * <p>@Title: OutletCommentServiceImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月25日
 */
public class OutletCommentServiceImpl extends BizMonitorBaseService implements OutletCommentService.Iface{

	private OutletCommentLogic outletCommentLogic = new OutletCommentLogicImpl();
	
	public OutletCommentServiceImpl() {}
	public OutletCommentServiceImpl(String name, String version) {
		super(name, version);
	}
	
	/**
     * 增加 OutletComment
     * @param outletCommentVo
     * @return OutletCommentAddVoRes    
     */
	@Override
	public OutletCommentAddVoRes addOutletComment(OriginVo originVo, OutletCommentVo outletCommentVo)
			throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加OutletComment param = "+JSON.toJSONString(outletCommentVo));
		OutletComment outletComment = (OutletComment)BeanUtil.copyProperties(OutletComment.class, outletCommentVo);
		OutletCommentAddRes outletCommentAddRes = outletCommentLogic.addOutletComment(outletComment);
		OutletCommentAddVoRes outletCommentAddVoRes = (OutletCommentAddVoRes)BeanUtil.copyProperties(OutletCommentAddVoRes.class, outletCommentAddRes);
		return outletCommentAddVoRes;
	}	

	/**
     * 删除 OutletCommentVo
     * @param outletCommentVo
     * @return ResultVo    
     */
	@Override
	public ResultVo deleteOutletComment(OriginVo originVo, String id)throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除OutletComment param = "+id);
		Result result = outletCommentLogic.deleteOutletComment(id);
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
		return resultVo;
	}

	/**
     * 修改 OutletCommentVo
     * @param outletCommentVo
     * @return ResultVo    
     */
	@Override
	public ResultVo updateOutletComment(OriginVo originVo, OutletCommentVo outletCommentVo)
			throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改OutletComment param = "+JSON.toJSONString(outletCommentVo));
		OutletComment outletComment = (OutletComment)BeanUtil.copyProperties(OutletComment.class, outletCommentVo);
		Result result = outletCommentLogic.updateOutletComment(outletComment);
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
		return resultVo;
	}

	/**
     * 查询 OutletCommentVo
     * @param String id
     * @return OutletCommentVo
     */
	@Override
	public OutletCommentVo getOutletCommentById(String id) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询单个OutletComment param = "+id);
		OutletComment outletComment = outletCommentLogic.findOutletCommentById(id);
		OutletCommentVo outletCommentVo = (OutletCommentVo)BeanUtil.copyProperties(OutletCommentVo.class, outletComment);
		return outletCommentVo;
	}

	/**
     * 查询 OutletCommentVo
     * @param outletCommentVo
     * @return List<OutletCommentVo>
     */
	@Override
	public List<OutletCommentVo> getOutletComment(
			OutletCommentVo outletCommentVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询OutletCommentList param = "+JSON.toJSONString(outletCommentVo));
		OutletComment outletComment = (OutletComment)BeanUtil.copyProperties(OutletComment.class, outletCommentVo);
		List<OutletComment> outletCommentList = outletCommentLogic.findOutletComment(outletComment);
		List<OutletCommentVo> outletCommentVoList = new ArrayList<OutletCommentVo>();
		if( outletCommentList != null && outletCommentList.size() > 0 ){
			for( OutletComment po : outletCommentList ){
				OutletCommentVo vo = (OutletCommentVo)BeanUtil.copyProperties(OutletCommentVo.class, po);
				outletCommentVoList.add(vo);
			}
		}else{
			LogCvt.info("未查询到OutletComment");
		}
		return outletCommentVoList;
	}

	/**
     * 分页查询 OutletCommentVo
     * @param page, outletCommentVo
     * @return OutletCommentPageVoRes
     */
	@Override
	public OutletCommentPageVoRes getOutletCommentByPage(PageVo pageVo,
			OutletCommentVo outletCommentVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询OutletComment param = "+JSON.toJSONString(outletCommentVo)+" - "+JSON.toJSONString(pageVo));
		Page<OutletComment> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		OutletComment outletComment = (OutletComment)BeanUtil.copyProperties(OutletComment.class, outletCommentVo);

		page = outletCommentLogic.findOutletCommentByPage(page, outletComment);
		OutletCommentPageVoRes res = new OutletCommentPageVoRes();
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		if(pageVo.getPageCount()>pageVo.getPageNumber()){
            pageVo.setHasNext(true);
        } else {
            pageVo.setHasNext(false);
        }
		res.setPage(pageVo);
		List<OutletComment> outletCommentList = page.getResultsContent();
		List<OutletCommentVo> outletCommentVoList = new ArrayList<OutletCommentVo>();
		if( outletCommentList != null && outletCommentList.size() > 0 ){
			for( OutletComment po : outletCommentList ){
				OutletCommentVo vo = (OutletCommentVo)BeanUtil.copyProperties(OutletCommentVo.class, po);
				outletCommentVoList.add(vo);
			}
		}else{
			LogCvt.info("未查询到OutletComment");
		}
		res.setOutletCommentVoList(outletCommentVoList);
		return res;
	}
	
	/**
     * 分页查询 OutletComment
     * @param OutletComment(+orgCode)
     * @return Page
     */
	@Override
	public OutletCommentPageVoRes getOutletCommentPageByOrgCode(PageVo pageVo,
			OutletCommentVo outletCommentVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询OutletComment param = "+JSON.toJSONString(outletCommentVo)+" - "+JSON.toJSONString(pageVo));
		Page<OutletComment> page = (Page<OutletComment>)BeanUtil.copyProperties(Page.class, pageVo);
		OutletComment outletComment = (OutletComment)BeanUtil.copyProperties(OutletComment.class, outletCommentVo);
		
		page = outletCommentLogic.findOutletCommentPageByOrgCode(page, outletComment);
		OutletCommentPageVoRes res = new OutletCommentPageVoRes();
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		if(pageVo.getPageCount()>pageVo.getPageNumber()){
            pageVo.setHasNext(true);
        } else {
            pageVo.setHasNext(false);
        }
		res.setPage(pageVo);
		List<OutletComment> outletCommentList = page.getResultsContent();
		List<OutletCommentVo> outletCommentVoList = new ArrayList<OutletCommentVo>();
		if( outletCommentList != null && outletCommentList.size() > 0 ){
			for( OutletComment po : outletCommentList ){
				OutletCommentVo vo = (OutletCommentVo)BeanUtil.copyProperties(OutletCommentVo.class, po);
				outletCommentVoList.add(vo);
			}
		}else{
			LogCvt.info("未查询到OutletComment");
		}
		res.setOutletCommentVoList(outletCommentVoList);
		return res;
	}

	/**
     * 增加 评论回复
     * @param OutletComment
     * @return ResultVo
     * 
     * @param outletCommentVo
     */
	@Override
    public ResultVo addOutletCommentOfRecomment(OriginVo originVo, OutletCommentVo outletCommentVo)
			throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
    	LogCvt.info("增加 Outlet评论回复 param = "+JSON.toJSONString(outletCommentVo));
		OutletComment outletComment = (OutletComment)BeanUtil.copyProperties(OutletComment.class, outletCommentVo);
		Result result = outletCommentLogic.addOutletCommentOfRecomment(outletComment);
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
		return resultVo;
    	
    }

	 /**
     * 门店评论数量查询
     * @param outletCommentVo - clientId merchantId outletId
     * @return i32
     * 
     * @param outletCommentVo
     */
	@Override
	public int getOutletCommentSum(OutletCommentVo outletCommentVo)
			throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("门店评论数量查询 param = "+JSON.toJSONString(outletCommentVo));
		OutletComment outletComment = (OutletComment)BeanUtil.copyProperties(OutletComment.class, outletCommentVo);
		int result = outletCommentLogic.getOutletCommentSum(outletComment);
		return result;
	}

	/**
     * 门店评论级别数量查询
     * @param outletCommentVo - merchantId outletId
     * @return OutletCommentLevelAmount
     * 
     * @param outletCommentVo
     */
	@Override
	public OutletCommentLevelAmountVo getOutletCommentLevelAmount(
			OutletCommentVo outletCommentVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("门店评论级别数量查询 param = "+JSON.toJSONString(outletCommentVo));
		OutletComment outletComment = (OutletComment)BeanUtil.copyProperties(OutletComment.class, outletCommentVo);
		OutletCommentLevelAmount outletCommentLevelAmount = outletCommentLogic.getOutletCommentLevelAmount(outletComment);
		OutletCommentLevelAmountVo result = (OutletCommentLevelAmountVo)BeanUtil.copyProperties(OutletCommentLevelAmountVo.class, outletCommentLevelAmount);
		return result;
	}

	/**
     * 商户评论级别数量查询
     * @param merchantId
     * @return list<OutletCommentLevelAmount>
     * 
     * @param merchantId
     */
	@Override
	public List<OutletCommentLevelAmountVo> getMerchantCommentLevelAmount(
			String merchantId) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("商户评论级别数量查询 param = "+merchantId);
		List<OutletCommentLevelAmount> outletCommentLevelAmountList = outletCommentLogic.getMerchantCommentLevelAmount(merchantId);
		List<OutletCommentLevelAmountVo> result = new ArrayList<OutletCommentLevelAmountVo>();
		if( outletCommentLevelAmountList != null && outletCommentLevelAmountList.size() > 0 ){
			for( OutletCommentLevelAmount outletCommentLevelAmount : outletCommentLevelAmountList ){
				result.add((OutletCommentLevelAmountVo)BeanUtil.copyProperties(OutletCommentLevelAmountVo.class, outletCommentLevelAmount));
			}
		}
		return result;
	}
	
	/**
     * 是否存某会员在某天针对某门店的评论
     * @return bool
     * 
     * @param memberCode
     * @param outletId
     * @param time
     */
	@Override
	public boolean isExistComment(String memberCode, String outletId, long time)
			throws TException {
		return outletCommentLogic.isExistComment(memberCode, outletId, time);
	}
	
	/**
	 * 是否存在某会员已经对门店进行了面对面的评论.
	 * @param memberCode
	 * @param outletId
	 * @return
	 * @throws TException
	 */
	@Override
	public boolean isExitsFaceToFaceComment(String memberCode,String outletId,String orderId)throws TException {		
		if (Checker.isEmpty(orderId)) {
			LogCvt.info("面对面评论订单号不能为空,返回不能进行评论");
			return true;
		}
		return outletCommentLogic.isExitsFaceToFaceComment(memberCode, outletId, orderId);
	}
}