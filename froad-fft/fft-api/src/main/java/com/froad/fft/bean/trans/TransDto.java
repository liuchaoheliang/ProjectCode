package com.froad.fft.bean.trans;

import java.util.Map;

import com.froad.fft.dto.BaseDto;
import com.froad.fft.enums.trans.TransCreateSource;
import com.froad.fft.enums.trans.TransPayChannel;
import com.froad.fft.enums.trans.TransPayMethod;
import com.froad.fft.enums.trans.TransType;

/**
 * 交易请求信息
 * @author FQ
 *
 */
public class TransDto extends BaseDto{
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/**交易公共字段**/
	private TransCreateSource createSource;//交易创建来源
	private TransType transType;//交易类型
	private TransPayMethod payMethod;//支付方式
	
	/**用户发起交易时必填(积分返利和纯收款交易不需要此字段)**/
	private Long memberCode;//用户唯一标识
	
	/**精品预售专有字段**/
	private Long deliveryId;//提货点编号
	private String deliveryName;//提货人名
	
	/**积分兑换、团购、精品预售字段**/
	private TransPayChannel payChannel;//金额的支付渠道
	private String filmMobile;//贴膜卡手机号
	private Map<Long, Integer> productMap;//key:商品编号 value:商品数量
	private String bankPoints;//银行积分
	
	/**积分兑换、团购、精品预售字段、积分提现**/
	private String fftPoints;//消费积分数
	private String mobile;//用于接收短信的手机号
	
	/**积分提现、积分返利**/
	private String reason;//交易事由
	
	/**积分返利专有字段**/
	private String gatheringValue;//收款金额
	private Long merchantId;//商户编号
	
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public TransCreateSource getCreateSource() {
		return createSource;
	}
	public void setCreateSource(TransCreateSource createSource) {
		this.createSource = createSource;
	}
	public TransType getTransType() {
		return transType;
	}
	public void setTransType(TransType transType) {
		this.transType = transType;
	}
	public TransPayMethod getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(TransPayMethod payMethod) {
		this.payMethod = payMethod;
	}

	public TransPayChannel getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(TransPayChannel payChannel) {
		this.payChannel = payChannel;
	}
	public String getFilmMobile() {
		return filmMobile;
	}
	public void setFilmMobile(String filmMobile) {
		this.filmMobile = filmMobile;
	}
	public Map<Long, Integer> getProductMap() {
		return productMap;
	}
	public void setProductMap(Map<Long, Integer> productMap) {
		this.productMap = productMap;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getGatheringValue() {
		return gatheringValue;
	}
	public void setGatheringValue(String gatheringValue) {
		this.gatheringValue = gatheringValue;
	}
	public Long getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}
	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	public String getBankPoints() {
		return bankPoints;
	}
	public void setBankPoints(String bankPoints) {
		this.bankPoints = bankPoints;
	}
	public String getFftPoints() {
		return fftPoints;
	}
	public void setFftPoints(String fftPoints) {
		this.fftPoints = fftPoints;
	}
	
}
