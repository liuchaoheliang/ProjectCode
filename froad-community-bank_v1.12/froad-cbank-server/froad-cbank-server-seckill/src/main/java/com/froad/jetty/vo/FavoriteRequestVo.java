package com.froad.jetty.vo;

/**
 * 商品收藏
 * @author wangzhangxu
 * @date 2015年5月3日 下午6:20:13
 * @version v1.0
 */
public class FavoriteRequestVo {
	
	private String clientId;
	
	private Long memberCode;
	private String token;
	
	private String productId;
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}
