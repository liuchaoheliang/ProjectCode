package com.froad.fft.support;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.froad.fft.bean.Result;
import com.froad.fft.common.AppException;
import com.froad.fft.persistent.api.FundsChannelMapper;
import com.froad.fft.persistent.api.MerchantMapper;
import com.froad.fft.persistent.api.PresellDeliveryMapper;
import com.froad.fft.persistent.api.ProductGroupMapper;
import com.froad.fft.persistent.api.ProductMapper;
import com.froad.fft.persistent.api.ProductPresellMapper;
import com.froad.fft.persistent.api.TransMapper;
import com.froad.fft.persistent.common.enums.PayChannel;
import com.froad.fft.persistent.common.enums.TransType;
import com.froad.fft.persistent.entity.FundsChannel;
import com.froad.fft.persistent.entity.Member;
import com.froad.fft.persistent.entity.Merchant;
import com.froad.fft.persistent.entity.PresellDelivery;
import com.froad.fft.persistent.entity.Product;
import com.froad.fft.persistent.entity.ProductGroup;
import com.froad.fft.persistent.entity.ProductPresell;
import com.froad.fft.persistent.entity.SysClient;
import com.froad.fft.persistent.entity.Trans;
import com.froad.fft.persistent.entity.TransDetails;
import com.froad.fft.service.MemberService;
import com.froad.fft.service.SysClientService;
import com.froad.fft.thirdparty.common.OpenApiCommand;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq.CheckType;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiRes;
import com.froad.fft.thirdparty.request.openapi.OpenApiFunc;


	/**
	 * 类描述：交易参数验证
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年3月28日 上午11:30:27 
	 */
@Component
public class TransValidateSupport {
	
	private static final Logger log=Logger.getLogger(TransValidateSupport.class);
	
	private Pattern numberRegex=Pattern.compile("([1-9][0-9]*)|0(\\.[0-9]{1,2})?");
	
	private Pattern mobileRegex=Pattern.compile("^1[0-9]{10}$");
	
	@Resource
	private SysClientService sysClientService;
	
	@Resource
	private MerchantMapper merchantMapper;
	
	@Resource
	private PresellDeliveryMapper presellDeliveryMapper;
	
	@Resource
	private FundsChannelMapper fundsChannelMapper;
	
	@Resource
	private ProductMapper productMapper;
	
	@Resource
	private ProductGroupMapper productGroupMapper;
	
	@Resource
	private ProductPresellMapper productPresellMapper;
	
	@Resource
	private TransMapper transMapper;
	
	@Resource
	private MemberService memberService;

	@Resource
	private OpenApiFunc openApiFunc;
	
	
	
	/**
	  * 方法描述：校验所有交易参数
	  * @param: Trans
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月29日 下午5:37:48
	  */
	public Result validate(Trans trans){
		Result result=this.commonValidate(trans);
		if(Result.FAIL.equals(result.getCode())){
			return result;
		}
		result=this.userTransValidate(trans);
		if(Result.FAIL.equals(result.getCode())){
			return result;
		}
		result=this.merchantTransValidate(trans);
		if(Result.FAIL.equals(result.getCode())){
			return result;
		}
		return new Result(Result.SUCCESS,"成功");
	}
	
	
	/**
	  * 方法描述：校验交易中的公共参数
	  * @param: Trans
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月29日 下午5:38:18
	  */
	private Result commonValidate(Trans trans){
		if(trans==null){
			return new Result(Result.FAIL,"交易记录为空");
		}
		if(trans.getType()==null){
			return new Result(Result.FAIL,"交易类型为空");
		}
		if(trans.getCreateSource()==null){
			return new Result(Result.FAIL,"交易的来源为空");
		}
		if(trans.getPayMethod()==null){
			return new Result(Result.FAIL,"支付方式为空");
		}
		if(trans.getClientId()==null){
			return new Result(Result.FAIL,"客户端不存在");
		}
		return new Result(Result.SUCCESS,"成功");
	}
	
	
	/**
	  * 方法描述：校验用户发起的交易的参数
	  * @param: Trans
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月29日 下午5:39:40
	  */
	private Result userTransValidate(Trans trans){
		if(TransHelper.isMerchantTrans(trans.getType())){
			return new Result(Result.SUCCESS,"成功");
		}
		Result result=this.validateMemberCode(trans.getMemberCode());
		if(Result.FAIL.equals(result.getCode())){
			return result;
		}
		result=this.productTransValidate(trans);
		if(Result.FAIL.equals(result.getCode())){
			return result;
		}
		result=this.withdrawTransValidate(trans);
		if(Result.FAIL.equals(result.getCode())){
			return result;
		}
		return new Result(Result.SUCCESS,"成功");
	}
	
	
	/**
	  * 方法描述：校验由商户发起的交易的参数
	  * @param: Trans
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月29日 下午5:40:28
	  */
	private Result merchantTransValidate(Trans trans){
		if(!TransHelper.isMerchantTrans(trans.getType())){
			return new Result(Result.SUCCESS,"成功");
		}
		if(StringUtils.isEmpty(trans.getReason())){
			return new Result(Result.FAIL,"交易事由为空");
		}
		String gathering=trans.getGatheringValue();
		if(StringUtils.isEmpty(gathering)){
			return new Result(Result.FAIL,"收款金额为空");
		}
		if(!numberRegex.matcher(gathering).matches()){
			return new Result(Result.FAIL,"收款金额必须为整数或小数");
		}
		if(trans.getMerchantId()==null){
			return new Result(Result.FAIL,"商户编号为空");
		}
		Merchant merchant=merchantMapper.selectMerchantById(trans.getMerchantId());
		if(merchant==null){
			return new Result(Result.FAIL,"编号为："+trans.getMerchantId()+"的商户不存在");
		}
		return new Result(Result.SUCCESS,"成功");
	}
	
	
	/**
	  * 方法描述：校验实体商品交易的参数
	  * @param: Trans
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月29日 下午5:40:56
	  */
	private Result productTransValidate(Trans trans){
		if(!TransHelper.isProductTrans(trans.getType())){
			return new Result(Result.SUCCESS,"成功");
		}
		if(!TransHelper.isPointsPay(trans.getPayMethod())){
			if(trans.getPayChannel()==null){
				return new Result(Result.FAIL,"支付渠道为空");
			}
			SysClient client=sysClientService.findSysClientByNumber(trans.getClientNumber());
			List<FundsChannel> channelList=fundsChannelMapper.selectByClientId(client.getId());
			if(channelList==null||channelList.size()==0){
				log.error("资金渠道为空，客户端编号为："+client.getId());
				return new Result(Result.FAIL,"该客户端的资金渠道为空");
			}
			String payOrg=TransHelper.findPayOrg(channelList, trans.getPayChannel());
			if(payOrg==null){
				log.error("资金渠道为空，客户端编号为："+client.getId()+" 支付渠道："+trans.getPayChannel());
				return new Result(Result.FAIL,"支付渠道对应的资金渠道为空");
			}
			if(PayChannel.filmCard.equals(trans.getPayChannel())){
				Result result=this.validateFilmMobile(payOrg,client.getPartnerId(),trans.getFilmMobile());
				if(Result.FAIL.equals(result.getCode())){
					return result;
				}
			}
		}
		Result result=this.validateProduct(trans);
		if(Result.FAIL.equals(result.getCode())){
			return result;
		}
		if(StringUtils.isEmpty(trans.getPhone())){
			return new Result(Result.FAIL,"接收短信的手机号码为空");
		}
		if(!mobileRegex.matcher(trans.getPhone()).matches()){
			return new Result(Result.FAIL,"接收短信的手机号码格式不正确");
		}
		result=this.presellValidate(trans);
		if(Result.FAIL.equals(result.getCode())){
			return result;
		}
		return new Result(Result.SUCCESS,"成功");
	}
	
	
	/**
	  * 方法描述：校验积分提现的专有参数
	  * @param: Trans
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月29日 下午5:36:50
	  */
	private Result withdrawTransValidate(Trans trans){
		if(!TransType.points_withdraw.equals(trans.getType())){
			return new Result(Result.SUCCESS,"成功");
		}
		if(StringUtils.isEmpty(trans.getFftPoints())){
			return new Result(Result.FAIL,"提现积分数为空");
		}
		if(!numberRegex.matcher(trans.getFftPoints()).matches()){
			return new Result(Result.FAIL,"提现积分数必须为整数或小数");
		}
		if(StringUtils.isEmpty(trans.getPhone())){
			return new Result(Result.FAIL,"接收短信的手机号码为空");
		}
		if(!mobileRegex.matcher(trans.getPhone()).matches()){
			return new Result(Result.FAIL,"接收短信的手机号码格式不正确");
		}
		return new Result(Result.SUCCESS,"成功");
	}
	
	
	/**
	  * 方法描述：校验预售交易的专有参数
	  * @param: Trans
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月29日 下午5:35:56
	  */
	private Result presellValidate(Trans trans){
		if(!TransType.presell.equals(trans.getType())){
			return new Result(Result.SUCCESS,"成功");
		}
		if(StringUtils.isEmpty(trans.getDeliveryName())){
			return new Result(Result.FAIL,"提货人姓名为空");
		}
		if(trans.getDeliveryId()==null){
			return new Result(Result.FAIL,"提货点编号为空");
		}
		PresellDelivery delivery=presellDeliveryMapper.selectPresellDeliveryById(trans.getDeliveryId());
		if(delivery==null){
			return new Result(Result.FAIL,"编号为："+trans.getDeliveryId()+"的提货点不存在");
		}
		return new Result(Result.SUCCESS,"成功");
	}
	
	
	/**
	  * 方法描述：校验商品参数
	  * @param: Trans
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月29日 下午5:35:01
	  */
	private Result validateProduct(Trans trans){
		List<TransDetails> detailsList=trans.getDetailsList();
		if(detailsList==null||detailsList.size()==0){
			return new Result(Result.FAIL,"商品信息为空");
		}
		TransDetails details=null;
		Product product=null;
		for (int i = 0; i < detailsList.size(); i++) {
			details=detailsList.get(i);
			product=productMapper.selectProductById(details.getProductId());
			if(product==null){
				return new Result(Result.FAIL,"编号为："+details.getProductId()+"的商品不存在");
			}
			if(!product.getIsMarketable()){
				return new Result(Result.FAIL,"编号为："+details.getProductId()+"的商品已下架");
			}
//			if(details.getQuantity()>product.getStore()){
//				return new Result(Result.FAIL,"编号为："+details.getProductId()+"的商品库存不足");
//			}
			Result result=this.validateGroup(trans.getMemberCode(),product,details.getQuantity());
			if(Result.FAIL.equals(result.getCode())){
				return result;
			}
		}
		return new Result(Result.SUCCESS,"成功");
	}
	
	
	/**
	  * 方法描述：校验团购和精品预售商品参数
	  * @param: Product
	  * @param: quantity
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月29日 下午5:33:27
	  */
	private Result validateGroup(Long memberCode,Product product,Integer quantity){
		GroupCommonBean common=null;
		String name="";
		if(product.getIsEnableGroup()){
			ProductGroup group=productGroupMapper.selectProductGroupById(product.getProductGroupId());
			common=GroupAdapter.adapt(group);
			name="团购";
		}else if(product.getIsEnablePresell()){
			ProductPresell presell=productPresellMapper.selectProductPresellById(product.getProductPresellId());
			common=GroupAdapter.adapt(presell);
			name="精品预售";
		}else{
			return new Result(Result.SUCCESS,"成功");
		}
		if(common==null){
			log.error(name+"商品信息录入有误，商品编号："+product.getId());
			return new Result(Result.FAIL,name+"商品信息录入有误");
		}
		Date now=new Date();
		if(now.before(common.getStartTime())){
			return new Result(Result.FAIL,name+"还没有到达开始时间");
		}
		if(now.after(common.getEndTime())){
			return new Result(Result.FAIL,name+"已经结束");
		}
		Integer min=common.getPerMinNumber();
		Integer max=common.getPerNumber();
		Long productId=product.getId();
		Result result=this.validateQuantity(memberCode, productId, min, max, quantity);
		return result;
	}
	
	
	/**
	  * 方法描述：验证memberCode是否存在
	  * @param: memberCode
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月28日 下午2:38:28
	  */
	private Result validateMemberCode(Long memberCode){
		if(memberCode==null){
			return new Result(Result.FAIL,"会员标识为空");
		}
		try {
			Member member=memberService.queryByMemberCode(memberCode);
			if(member==null){
				log.error("memberCode为："+memberCode+" 的用户不存在");
				return new Result(Result.FAIL,"用户不存在");
			}
		} catch (AppException e) {
			log.error("用户系统查询接口调用异常",e);
			return new Result(Result.FAIL,"用户系统接口调用异常");
		}
		return new Result(Result.SUCCESS,"成功");
	}
	
	
	/**
	  * 方法描述：验证贴膜卡手机号是否有效
	  * @param: payOrg
	  * @param: partnerId
	  * @param: filmMobile
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月29日 下午3:16:07
	  */
	private Result validateFilmMobile(String payOrg,String partnerId,String filmMobile){
		if(StringUtils.isEmpty(filmMobile)){
			return new Result(Result.FAIL,"贴膜卡手机号为空");
		}
		OpenApiReq req=new OpenApiReq(payOrg, CheckType.ACCOUNT_MOBILE, filmMobile, null,partnerId);
		try {
			OpenApiRes res=openApiFunc.accountCheck(req);
			if(OpenApiCommand.SUCCESS.equals(res.getCheckResultCode())){
				return new Result(Result.SUCCESS,"成功");
			}
			return new Result(Result.FAIL,"该手机号不是有效的贴膜卡手机号");
		} catch (AppException e) {
			log.error("调用openapi账户校验接口出现异常",e);
			return new Result(Result.FAIL,"账户校验接口异常");
		}
	}
	
	private Result validateQuantity(Long memberCode,Long productId,
			Integer min,Integer max,Integer quantity){
		if(min==0&&max==0){
			return new Result(Result.SUCCESS,"成功");
		}
		Integer number=0;//已购买数量
		if(max>0){
			List<Trans> list=transMapper.selectGroupAndPresellByMemberCode(memberCode);
			if(list!=null&&list.size()>0){
				TransDetails details=null;
				for (int i = 0; i < list.size(); i++) {
					details=list.get(i).getDetailsList().get(0);
					if(productId.equals(details.getProductId())){
						number=number+details.getQuantity();
					}
				}
				if((number+quantity)>max){
					Integer rest=max-number;
					if(rest<0){
						rest=0;
					}
					return new Result(Result.FAIL,"您还可购买数量为："+rest);
				}
			}
		}
		if(min>0&&(quantity+number)<min){
			return new Result(Result.FAIL,"购买数量小于每人最低购买数量");
		}
		if(max>0&&(quantity+number)>max){
			return new Result(Result.FAIL,"您还可购买数量为："+(max-number));
		}
		return new Result(Result.SUCCESS,"成功");
	}
	
}
