package com.froad.db.mongo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.froad.Constants;
import com.froad.db.mongo.impl.CursorHandleImpl;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.enums.RefundState;
import com.froad.po.mongo.RefundHistory;
import com.froad.po.mongo.RefundShoppingInfo;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;

/**
 * 查询退款商品信息
 * 
 * @author Arron
 * 
 */
public class RefundShoppingService {

    private MongoManager mongo = new MongoManager();

    /**
     * 退款子订单信息
     * @param subOrderId
     * @return
     *<pre>
     *
     * @Description: TODO(用一句话描述该文件做什么) 
     * @version V1.0 修改人：Arron 日期：2015年5月27日 下午4:18:26 
     *
     *</pre>
     */
    public RefundShoppingInfo queryRefundShoppingInfo(String subOrderId) {
        List<DBObject> pipeLine = new ArrayList<DBObject>();
        
        DBObject query = new BasicDBObject();
        query.put("refund_state", RefundState.REFUND_SUCCESS.getCode());
        
        pipeLine.add(new BasicDBObject("$match", query));
        pipeLine.add(new BasicDBObject("$unwind", "$shopping_info"));
        
        DBObject query2 = new BasicDBObject();
        query2.put("shopping_info.sub_order_id", subOrderId);
        
        pipeLine.add(new BasicDBObject("$match", query2));
        Cursor cursor = mongo.getReadDBCollection(MongoTableName.CB_ORDER_REFUNDS).aggregate(pipeLine, AggregationOptions.builder().build());
        DBObject dbObj = new CursorHandleImpl().handle(cursor);
        if(dbObj != null){
             String json = JSonUtil.toJSonString(((DBObject)dbObj.get("shopping_info")));
             return JSonUtil.toObject(json, RefundShoppingInfo.class);
        }
        return null;
    }
    
    /**
     * find refund information by pipe line
     * 
     * @param pipeline
     * @return
     */
	public Iterator<DBObject> findByPipeline(List<DBObject> pipeline) {
		return mongo.findByPipeline(pipeline, MongoTableName.CB_ORDER_REFUNDS);
	}
	
	@SuppressWarnings("unchecked")
	public List<RefundHistory> findByCondition(DBObject queryObj){
		return (List<RefundHistory>) mongo.findAll(queryObj, MongoTableName.CB_ORDER_REFUNDS, RefundHistory.class);
	}
    
    public static void main(String[] args) {
        System.setProperty(Constants.CONFIG_PATH, "./config/");
        RefundShoppingService s = new RefundShoppingService();
        System.out.println(JSonUtil.toJSonString(s.queryRefundShoppingInfo("0409B4D10017")));
    }

}
