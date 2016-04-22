package com.froad.db.mongo.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.db.mongo.CursorHandle;
import com.froad.logback.LogCvt;
import com.mongodb.Cursor;
import com.mongodb.DBObject;


public class CursorHandleImpl implements CursorHandle{

	@Override
	public DBObject handle(Cursor cursor) {
		// TODO Auto-generated method stub
		DBObject dbobj = null;
		try {
			if(cursor.hasNext()){
				dbobj = cursor.next();
			}
		} catch (Exception e) {
			LogCvt.error(e.getMessage(),e);
		} finally{
			cursor.close();
		}
		
		return dbobj;
	}

	@Override
	public List<DBObject> handleList(Cursor cursor) {
		// TODO Auto-generated method stub
		List<DBObject> array = new ArrayList<DBObject>();
		try {
			while(cursor.hasNext()){
				array.add(cursor.next());
			}
		} catch (Exception e) {
			LogCvt.error(e.getMessage(),e);
		} finally{
			cursor.close();
		}
		return array;
	}

}

