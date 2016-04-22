package com.froad.fft.support;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.froad.fft.bean.Result;
import com.froad.fft.common.AppException;
import com.froad.fft.persistent.api.MerchantMapper;
import com.froad.fft.persistent.api.ProductMapper;
import com.froad.fft.persistent.common.enums.DataState;
import com.froad.fft.persistent.common.enums.MerchantType;
import com.froad.fft.persistent.common.enums.TransPayState;
import com.froad.fft.persistent.common.enums.TransState;
import com.froad.fft.persistent.common.enums.TransType;
import com.froad.fft.persistent.entity.CashPointsRatio;
import com.froad.fft.persistent.entity.Member;
import com.froad.fft.persistent.entity.Merchant;
import com.froad.fft.persistent.entity.Product;
import com.froad.fft.persistent.entity.Trans;
import com.froad.fft.persistent.entity.TransDetails;
import com.froad.fft.service.CashPointsRatioService;
import com.froad.fft.service.MemberService;
import com.froad.fft.util.SerialNumberUtil;


	/**
	 * 类描述：组装交易和交易商品明细参数
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年3月31日 下午4:23:05 
	 */
@Component
public class TransSupport {
	
	private static final Logger log=Logger.getLogger(TransSupport.class);
	
	private BigDecimal zero=new BigDecimal(0);
	
	@Resource
	private PaySupport paySupport;
	
	@Resource
	private ProductMapper productMapper;
	
	@Resource
	private MerchantMapper merchantMapper; 
	
	@Resource
	private CashPointsRatioService cashPointsRatioService;
	
	@Resource
	private MemberService memberService;

	
	/**
	  * 方法描述：组装交易和交易商品明细
	  * @param: Trans
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月31日 下午4:51:00
	  */
	public Result makeTrans(Trans trans){
		this.makeCommonTrans(trans);
		Result result=this.makeUserTrans(trans);
		if(Result.FAIL.equals(result.getCode())){
			return result;
		}
		result=this.makeMerchantTrans(trans);
		if(Result.FAIL.equals(result.getCode())){
			return result;
		}
		return new Result(Result.SUCCESS,"成功");
	}
	
	private void makeCommonTrans(Trans trans){
		trans.setSn(SerialNumberUtil.buildTransSn());
		trans.setState(TransState.processing);
		trans.setPayState(TransPayState.unpaid);
	}
	
	private Result makeUserTrans(Trans trans){
		if(TransHelper.isMerchantTrans(trans.getType())){
			return new Result(Result.SUCCESS,"成功");
		}
		try {
			Member member=memberService.queryByMemberCode(trans.getMemberCode());
			trans.setMember(member);
		} catch (AppException e) {
			log.error("用户系统接口调用异常",e);
			return new Result(Result.FAIL,"用户系统接口调用异常");
		}
		Result result=this.makeProductTrans(trans);
		if(Result.FAIL.equals(result.getCode())){
			return result;
		}
		this.makeExchangeTrans(trans);
		this.makeWithdrawTrans(trans);
		return new Result(Result.SUCCESS,"成功");
	}
	
	private Result makeMerchantTrans(Trans trans){
		if(!TransHelper.isMerchantTrans(trans.getType())){
			return new Result(Result.SUCCESS,"成功");
		}
		//TODO 组装参数--
		
		return paySupport.makePayList(trans);
	}
	
	private Result makeProductTrans(Trans trans){
		if(!TransHelper.isProductTrans(trans.getType())){
			return new Result(Result.SUCCESS,"成功");
		}
		Result result=this.makeDetails(trans);
		if(Result.FAIL.equals(result.getCode())){
			return result;
		}
		return paySupport.makePayList(trans);
	}
	
	private void makeExchangeTrans(Trans trans){
		if(!TransType.points_exchange.equals(trans.getType())){
			return;
		}
		//TODO 组装参数--
		
	}
	
	private void makeWithdrawTrans(Trans trans){
		if(!TransType.points_withdraw.equals(trans.getType())){
			return;
		}
		//TODO 组装参数--
		
	}
	
	private Result makeDetails(Trans trans){
		List<TransDetails> detailsList=trans.getDetailsList();
		TransDetails details=null;
		Product product=null;
		for (int i = 0; i < detailsList.size(); i++) {
			details=detailsList.get(i);
			product=productMapper.selectProductById(details.getProductId());
			details.setPrice(this.figureDetailsAmount(product.getPrice(), details.getQuantity()));
			details.setProductName(product.getName());
			details.setSingle(product.getPrice());
			details.setSupplyMerchantId(product.getMerchantId());
			Long gatherMerchantId=this.queryGatherMerchantId(product);
			if(gatherMerchantId==null){
				return new Result(Result.FAIL,"方付通的收款账户不存在，clientId="+product.getClientId());
			}
			details.setGatherMerchantId(gatherMerchantId);
		}
		this.makeProductTransAmount(trans);
		return new Result(Result.SUCCESS,"成功");
	}
	
	private void makeProductTransAmount(Trans trans){
		List<TransDetails> detailsList=trans.getDetailsList();
		TransDetails details=null;
		BigDecimal totalAmount=new BigDecimal(0);
		totalAmount=totalAmount.setScale(2, RoundingMode.DOWN);
		for (int i = 0; i < detailsList.size(); i++) {
			details=detailsList.get(i);
			totalAmount=totalAmount.add(new BigDecimal(details.getPrice()));
		}
		trans.setTotalPrice(totalAmount.toString());
		String fftPoints=trans.getFftPoints();
		String bankPoints=trans.getBankPoints();
		CashPointsRatio ratio=null;
		if(!StringUtils.isEmpty(fftPoints)||
				!StringUtils.isEmpty(bankPoints)){
			ratio=cashPointsRatioService.selectBySysClientId(trans.getClientId());
			if(ratio==null){
				log.warn("没有配置现金和积分比例，默认按1:1方式进行计算");
				ratio=new CashPointsRatio("1","1");
			}
		}
		BigDecimal realPrice=totalAmount;
		if(!StringUtils.isEmpty(fftPoints)){
			BigDecimal fftAmount=this.toCash(fftPoints, ratio.getFftPoints());
			realPrice=totalAmount.subtract(fftAmount);
			if(realPrice.compareTo(zero)==-1){
				fftPoints=this.toPoints(totalAmount, ratio.getFftPoints());
				trans.setFftPoints(fftPoints);
				realPrice=zero;
			}
		}
		if(!StringUtils.isEmpty(bankPoints)){
			BigDecimal bankAmount=this.toCash(bankPoints, ratio.getBankPoints());
			realPrice=totalAmount.subtract(bankAmount);
			if(realPrice.compareTo(zero)==-1){
				bankPoints=this.toPoints(totalAmount, ratio.getBankPoints());
				trans.setBankPoints(bankPoints);
				realPrice=zero;
			}
		}
		trans.setRealPrice(realPrice.toString());
	}
	
	private BigDecimal toCash(String points,String ratioValue){
		BigDecimal pointsDec=new BigDecimal(points);
		BigDecimal ratioDec=new BigDecimal(ratioValue);
		return pointsDec.divide(ratioDec, 2, RoundingMode.DOWN);
	}
	
	private String toPoints(BigDecimal amount,String ratioValue){
		BigDecimal ratioDec=new BigDecimal(ratioValue);
		return amount.multiply(ratioDec).toString();
	}
	
	private String figureDetailsAmount(String single,Integer quantity){
		BigDecimal singleDec=new BigDecimal(single);
		BigDecimal quantityDec=new BigDecimal(quantity);
		return singleDec.multiply(quantityDec).setScale(2, RoundingMode.DOWN).toString();
	}
	
	private Long queryGatherMerchantId(Product product){
		if(product.getCollectToFroad()){
			List<Merchant> list=merchantMapper.selectMerchantByClientId(product.getClientId());
			Merchant merchant=null;
			for (int i = 0; i < list.size(); i++) {
				merchant=list.get(i);
				if(MerchantType.froad.equals(merchant.getType())&&
						DataState.enable.equals(merchant.getDataState())){
					return merchant.getId();
				}
			}
			log.error("方付通的收款账户为空，clientId="+product.getClientId());
			return null;
		}
		return product.getMerchantId();
	}

}
