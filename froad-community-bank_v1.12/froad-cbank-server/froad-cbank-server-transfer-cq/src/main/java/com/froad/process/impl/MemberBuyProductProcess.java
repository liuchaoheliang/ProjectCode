package com.froad.process.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.cbank.persistent.entity.MemberBuyProduct;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chongqing.mappers.MemberBuyProductMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.process.AbstractProcess;
import com.froad.util.Constans;
import com.froad.util.RedisKeyUtil;

public class MemberBuyProductProcess extends AbstractProcess{

	public MemberBuyProductProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	private TransferMapper transferMapper = null;
	private MemberBuyProductMapper mProductMapper = null;
	private final Map<String,String> productIdMap = new HashMap<String,String>();

	
	@Override
	public void before() {
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		mProductMapper = cqSqlSession.getMapper(MemberBuyProductMapper.class);
		
		List<Transfer> list = transferMapper.queryGroupList(TransferTypeEnum.product_id);
		for (Transfer t : list) {
			productIdMap.put(t.getOldId(), t.getNewId());
		}

	}

	@Override
	public void process() {
		List<MemberBuyProduct> list = mProductMapper.selectByCondition(new MemberBuyProduct());
		String clientId = Constans.clientId;
		String productId = "";
		for (MemberBuyProduct obj : list) {
			productId = productIdMap.get(obj.getProductId().toString());
			resetCount(clientId,obj.getMemberCode(),productId,obj.getBuyCount() == null ? "0" : obj.getBuyCount().toString());
			resetVipCount(clientId,obj.getMemberCode(),productId,obj.getVipBuyCount() == null ? "0" : obj.getVipBuyCount().toString());
		}
		
	}

	private final String COUNT = "count";
	
	private final String VIP_COUNT = "vip_count";
	
	
	public void resetCount(String clientId, Long memberCode, String productId, String count) {
		String key = RedisKeyUtil.cbbank_product_limit_client_id_member_code_product_id(clientId, memberCode, productId);
		redis.hset(key, COUNT, count);
	}

	public void resetVipCount(String clientId, Long memberCode, String productId, String vipCount) {
		String key = RedisKeyUtil.cbbank_product_limit_client_id_member_code_product_id(clientId, memberCode, productId);
		redis.hset(key, VIP_COUNT, vipCount);
	}
}

