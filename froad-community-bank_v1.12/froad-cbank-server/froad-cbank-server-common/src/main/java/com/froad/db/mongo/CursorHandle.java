package com.froad.db.mongo;

import java.util.List;

import com.mongodb.Cursor;
import com.mongodb.DBObject;

public interface CursorHandle {
	
	/**
	 *  Mongo游标处理
	  * @Title: handle
	  * @Description: TODO
	  * @author: share 2015年3月23日
	  * @modify: share 2015年3月23日
	  * @param @param cursor
	  * @param @return    
	  * @return DBObject    
	  * @throws
	 */
    public abstract DBObject handle(Cursor cursor);
    
    /**
     *  mongo游标处理返回List
      * @Title: handleList
      * @Description: TODO
      * @author: share 2015年3月23日
      * @modify: share 2015年3月23日
      * @param @param cursor
      * @param @return    
      * @return List<DBObject>    
      * @throws
     */
    public abstract List<DBObject> handleList(Cursor cursor);
    
}