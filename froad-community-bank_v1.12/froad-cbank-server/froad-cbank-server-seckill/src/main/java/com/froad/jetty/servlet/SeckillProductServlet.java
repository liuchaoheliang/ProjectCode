package com.froad.jetty.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.froad.enums.H5ResultCode;
import com.froad.logic.SeckillProductLogic;
import com.froad.logic.impl.SeckillProductLogicImpl;
import com.froad.util.ServletResultUtil;

/**
 * 秒杀商品相关接口
 * 
 * @author wangzhangxu
 * @date 2015年4月27日 下午1:01:58
 * @version v1.0
 */
public class SeckillProductServlet extends AbstractHttpServlet {
	
	private static final long serialVersionUID = 2738417259338423856L;
	
	private SeckillProductLogic seckillProductLogic;
	
	/**
	 * 初始化
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		seckillProductLogic = new SeckillProductLogicImpl();
	}
	
	/**
	 * 获取秒杀商品库存
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String data = null;
		String productId = request.getParameter("productId"), clientId = (String)request.getAttribute("clientId");
		if (StringUtils.isEmpty(productId) || StringUtils.isEmpty(clientId)){
			data = errorCodeMsg(H5ResultCode.missingParam.getCode(), H5ResultCode.missingParam.getMsg());
			ServletResultUtil.render(data, request, response);
			return;
		}
		
		long stock = seckillProductLogic.getStock(clientId, productId);
		if (stock < 0) {
			data = errorCodeMsg(H5ResultCode.missingParam.getCode(), "未获取到秒杀商品库存数量");
		} else {
			data = "{\"secStore\":" + stock + "}";
		}
		
		ServletResultUtil.render(data, request, response);
	}
	
}
