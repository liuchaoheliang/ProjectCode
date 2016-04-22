package com.froad.util;

import java.util.Formatter;
import java.util.Random;

import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.ModuleID;
import com.froad.exceptions.FroadBusinessException;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;

public class SimpleID {
    private long datacenterId;
    private long sequence = 0L;
 
    private long twepoch = 1428393042L;
    private long datacenterIdBits = 5L;//32
    private long sequenceBits = 15L;//32768
 
    private long datacenterIdShift = sequenceBits;
    private long timestampLeftShift = sequenceBits + datacenterIdBits;
    private long sequenceMask = -1L ^ (-1L << sequenceBits);
 
    private long lastTimestamp = -1L;
    
    private RedisManager r = new RedisManager();
    private Random rand = new Random();
    
	/**
	 * 唯一DateCenterID 缓存redis key
	 */
    private static final String CBBANK_DC_KEY = "cbank:id:datacenter:";
    private static final String CBBANK_TICKET_KEY = "cbank:ticket:simple:sequence:";
    
    public SimpleID(ModuleID moduleName) {
    	long datacenterId;
    	String dckey = CBBANK_DC_KEY + moduleName.getModulename();
    	datacenterId = r.incr(dckey, 9L);
        this.datacenterId = datacenterId;
    }
 
    public synchronized String nextId(){
        long timestamp = timeGen();
 
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
 
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextSeconds(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
 
        lastTimestamp = timestamp;
 
        long res = ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | sequence;
        byte[] bytes = new byte[6];
        bytes[0] = (byte) (res >> 40);
        bytes[1] = (byte) (res >> 32);
        bytes[2] = (byte) (res >> 24);
        bytes[3] = (byte) (res >> 16);
        bytes[4] = (byte) (res >> 8);
        bytes[5] = (byte) (res);
        
        StringBuilder buf = new StringBuilder(12);

        for (final byte b : bytes) {
            buf.append(String.format("%02x", b & 0xff));
        }

        String redStr = buf.toString().toUpperCase();
        return redStr;
    }

    public String ticketID()throws FroadServerException{
		long timestamp = hourGen();
		 
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d hours", lastTimestamp - timestamp));
        }
 
        String redStr = null;
        boolean same = true;
        int i = 1;
        while(same && i <= 10){
	        int sequence = rand.nextInt(999999);
	        Formatter fmt = new Formatter();
			redStr = fmt.format("%05d%d%06d", timestamp - (twepoch / 3600), datacenterId, sequence).toString();
			String key = CBBANK_TICKET_KEY+redStr;
			if(r.setnx(key, "0").longValue() == 1){
				r.expire(key, 4 * 60 * 60);
				same = false;
				break;
			}
			LogCvt.error("第"+i+"生成券号失败,券ID已存在："+redStr);
			redStr = null;
			i++;
        }
		// 检查是否生成失败
        if(redStr == null){
        	throw new FroadServerException("生成券号失败");
        }
        
		return redStr;
		
//      if (lastTimestamp == timestamp) {
//      sequence = (sequence + 1) & sequenceMask;
//      if (sequence == 0) {
//          timestamp = tilNextHours(lastTimestamp);
//      }
//  } else {
//      sequence = 0L;
//  }
    }
    
    protected long tilNextSeconds(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }
 
    protected long tilNextHours(long lastTimestamp) {
        long timestamp = hourGen();
        while (timestamp <= lastTimestamp) {
            timestamp = hourGen();
        }
        return timestamp;
    }
    
    protected long hourGen() {
    	return System.currentTimeMillis() / 1000 / 3600;
    }
    
    protected long timeGen() {
    	return System.currentTimeMillis() / 1000;
    }

   
    public static void main(String[] args) {
    	try{
            SimpleID wk = new SimpleID(ModuleID.ticket);
            System.out.println(wk.ticketID());
        }catch(Exception e){
        	System.out.println("error");
        }
    }
}
