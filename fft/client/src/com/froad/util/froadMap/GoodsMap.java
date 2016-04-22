package com.froad.util.froadMap;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class GoodsMap {
	public static final Map<String, String> GOODS_NAME;
	static {
		GOODS_NAME = new TreeMap<String, String>();
		GOODS_NAME.put("1", "话费充值");
		//GOODS_NAME.put("2", "游戏点卡");
		GOODS_NAME.put("3", "机票");
		GOODS_NAME.put("5", "网络游戏（直充）");
		GOODS_NAME.put("7", "彩票");
		GOODS_NAME.put("15", "电影票");
		GOODS_NAME.put("17", "酒店");
		GOODS_NAME.put("32", "实体商品");
		GOODS_NAME.put("33", "丁丁优惠券");
		GOODS_NAME.put("46", "支付宝");
	}
	
	public static final Map<Integer, String> TRAN_MSG_MAP;
	
	static {
		TRAN_MSG_MAP = new HashMap<Integer, String>();
		TRAN_MSG_MAP.put(1, "<p>尊敬的客户，我们还未收到该订单的款项，请尽快付款。</p><p>该订单会为您保留12小时（从下单之时算起），12小时之内还未付款，系统将自动取消订单。</p>");
		TRAN_MSG_MAP.put(2, "<p>该笔订单已完成，感谢您在方付通商城购买商品。</p>");
		TRAN_MSG_MAP.put(3, "<p>该笔订单已不能支付，如需购买该商品，请重新下单。</p>");
		TRAN_MSG_MAP.put(4, "<p>该笔订单已关闭，如需购买该商品，请重新下单。</p>");
		TRAN_MSG_MAP.put(5, "<p>该笔订单已支付，请等待卖家发货。</p>");
		TRAN_MSG_MAP.put(6, "<p>尊敬的客户，您的订单商品已经发货，请做好收货准备。</p><p>如果您已收到货物，请点击“确认收货”按钮完成订单。</p>");
		TRAN_MSG_MAP.put(7, "<p>该笔订单已完成，感谢您在方付通商城购买商品。</p><p>如需退换货，请在1天之内（算出具体截止时间）提出申请。</p>");
		TRAN_MSG_MAP.put(8, "<p>您已申请退货，请耐心等待我司工作人员审核。</p>");
		TRAN_MSG_MAP.put(9, "<p>退货申请已通过，请点击“退货”，填写详细的退货订单信息。</p>");
		TRAN_MSG_MAP.put(10, "<p>收到退回货物后，我们将会在10个工作日内将款项退回原支付账户中，请耐心等待退款。</p>");
		TRAN_MSG_MAP.put(11, "<p>该笔订单已退款成功。</p>");
		TRAN_MSG_MAP.put(12, "<p>该笔订单处于异常状态，请等待系统处理或联系客服人员审核。</p>");
		TRAN_MSG_MAP.put(13, "<p>订单处理失败，请等待系统处理或联系客服人员审核。</p>");
		TRAN_MSG_MAP.put(14, "<p>该笔订单已支付，商家正在为您发货，请稍作等待。</p>");
		TRAN_MSG_MAP.put(15, "<p>尊敬的客户，我们还未收到该订单的款项，请尽快付款。</p><p>机票订单涉及占位问题，会为您保留8小时（从下单之时算起），8小时内仍未付款，系统将自动取消订单。</p>");
		TRAN_MSG_MAP.put(16, "<p>尊敬的客户，我们还未收到该订单的款项，请尽快付款。</p><p>电影票订单涉及占位问题，会为您保留15分钟（从下单之时算起），15分钟内未完成付款，系统将自动取消订单。</p><p>若您在15分钟内生成了多笔电影票订单，则系统将以最新的订单为准，其余电影票订单将作废。</p>");
		TRAN_MSG_MAP.put(17, "<p>酒店预订成功后，请于最晚到店时间之前到达酒店办理入住。</p><p>酒店一般为客人保留房间至最晚保留时间，如果晚于最晚保留时间到达酒店，酒店有权取消预订。</p><p>如您将在最晚保留时间之后到达酒店，请及时告知客服人员进行处理。</p>");
		TRAN_MSG_MAP.put(18, "<p>丁丁优惠券，请注意使用时段限制及门店限制，请在优惠券有效期之内使用。</p>");
	}
}
