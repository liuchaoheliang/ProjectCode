package com.froad.fft.persistent.entity;


	/**
	 * 类描述：现金和积分的比例
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年3月31日 上午11:51:37 
	 */
public class CashPointsRatio extends BaseEntity{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private String fftPoints;//现金和分分通积分的比例(如果 1元=0.5积分,该值为0.5;1元=3积分,该值为3)
	
	private String bankPoints;//现金和银行积分的比例
	
	private Long sysClientId;//客户端编号
	
	public CashPointsRatio(){}
	
	public CashPointsRatio(String fftPoints,String bankPoints){
		this.fftPoints=fftPoints;
		this.bankPoints=bankPoints;
	}
	
	public String getFftPoints() {
		return fftPoints;
	}

	public void setFftPoints(String fftPoints) {
		this.fftPoints = fftPoints;
	}

	public String getBankPoints() {
		return bankPoints;
	}

	public void setBankPoints(String bankPoints) {
		this.bankPoints = bankPoints;
	}

	public Long getSysClientId() {
		return sysClientId;
	}

	public void setSysClientId(Long sysClientId) {
		this.sysClientId = sysClientId;
	}
	
}
