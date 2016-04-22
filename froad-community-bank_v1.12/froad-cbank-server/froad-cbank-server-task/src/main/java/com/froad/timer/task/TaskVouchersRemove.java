package com.froad.timer.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.VouchersInfoMapper;
import com.froad.logback.LogCvt;
import com.froad.po.VouchersInfo;
import com.froad.util.AllkindsTimeConfig;

/**
 * 营销代金券迁移
 * @ClassName: TaskVouchersRemove 
 * @Description: TODO
 * @author: Yaolong Liang
 * @date: 2015年12月30日
 */

public class TaskVouchersRemove implements Runnable{
	/*
	*      
	*          ┌─┐       ┌─┐
	*       ┌──┘ ┴───────┘ ┴──┐
	*       │                 │
	*       │       ───       │
	*       │  ─┬┘       └┬─  │
	*       │                 │
	*       │       ─┴─       │
	*       │                 │
	*       └───┐         ┌───┘
	*           │         │
	*           │         │
	*           │         │
	*           │         └──────────────┐
	*           │                        │
	*           │                        ├─┐
	*           │                        ┌─┘    
	*           │                        │
	*           └─┐  ┐  ┌───────┬──┐  ┌──┘         
	*             │ ─┤ ─┤       │ ─┤ ─┤         
	*             └──┴──┘       └──┴──┘ 
	*                 神兽保佑 
	*                 代码无BUG! 
	*     迁移规则：1.迁移已使用超过半年的红包卷码到已使用表中        
	*            2. 迁移已过期卷码到过期表中
	*            3.每天删除临时表前一天的数据
	*/	
	public static final int insertNum=1000;
	public static final String UsedFlag="0";
	public static final String OutOFDateFlag="1";
	public static final String RemoveDayBeforeFlag="2";
	public static final String RemoveHalfYearBeforeFlag="3";
	@Override
	public void run() {
		//迁移规则1
		TransferUsedVouchers();
		//迁移规则2
		TransferOutOfDateVouchers();
		//迁移规则3
		removeTempEveryDay();
		
		
	}
	
   public void TransferUsedVouchers(){
	   LogCvt.info("定时任务: 营销--迁移已使用卷------开始------");
	   SqlSession sqlSession = null;
	   boolean result=false;
	   try{
		   sqlSession=MyBatisManager.getSqlSessionFactory().openSession();
		   VouchersInfoMapper vouchersInfoMapper = sqlSession.getMapper(VouchersInfoMapper.class);
	    	   result=vouchersInfoMapper.addVouchersUseInfo(getDateBefore(UsedFlag));
	       if(result){
			   LogCvt.debug("定时任务: 营销--迁移已使用卷------插入成功,删除原表数据开始"); 
			   Integer resultNum=vouchersInfoMapper.deleteVouchersInfoFromHalfYear(getDateBefore(UsedFlag));
			   result=resultNum>0?true:false;
			 if(result){
				 sqlSession.commit();
				 LogCvt.debug("定时任务: 营销--迁移已使用卷------删除原表数据成功"); 
			 }else{
				 LogCvt.debug("定时任务: 营销--迁移已使用卷------删除原表数据失败"); 
			 }
			 
		   }else{
			   LogCvt.info("定时任务: 营销--迁移已使用卷------插入失败"); 
		   }
	   }catch(Exception e){
		   LogCvt.error("定时任务: 营销--迁移已使用卷异常"); 
		   LogCvt.error(e.getMessage(), e);
	   }finally{
		   if(sqlSession!=null){
			   sqlSession.close();  
		   }
		   LogCvt.info("定时任务: 营销--迁移已使用卷------结束------"); 
	   }
   }
   public void TransferOutOfDateVouchers(){
	   LogCvt.info("定时任务: 营销--迁移过期卷------开始------");
	   SqlSession sqlSession = null;
	   List<VouchersInfo> vouchersInfos=null;
	   Integer resultNum=0;
	   boolean result=false;
	   try{
		   sqlSession=MyBatisManager.getSqlSessionFactory().openSession();
		   VouchersInfoMapper vouchersInfoMapper = sqlSession.getMapper(VouchersInfoMapper.class);
		   vouchersInfos=vouchersInfoMapper.getVouchersInfoOutOfDateFromHalfYear(getDateBefore(OutOFDateFlag));
		   LogCvt.info("定时任务: 营销--迁移已过期卷------数量:"+vouchersInfos.size());
		   //
		   result= vouchersInfoMapper.addVouchersOutOfDateInfo(getDateBefore(OutOFDateFlag));
		   if(result){
			   LogCvt.debug("定时任务: 营销--迁移已过期卷------插入成功,删除原表数据开始"); 
			   if(vouchersInfos!=null&&vouchersInfos.size()>0){
				    resultNum=  vouchersInfoMapper.deleteVouchersInfoOutOfDateFromHalfYear(vouchersInfos);
			   }
				 if(resultNum>0){
					 sqlSession.commit();
					 LogCvt.debug("定时任务: 营销--迁移已过期卷------删除原表数据成功"); 
				 }else{
					 LogCvt.debug("定时任务: 营销--迁移已过期卷------删除原表数据失败"); 
				 }
		   }else{
			   LogCvt.debug("定时任务: 营销--迁移已过期卷------插入失败"); 
		   }
	   }catch(Exception e){
		   LogCvt.error("定时任务: 营销--迁移过期卷异常"); 
		   LogCvt.error(e.getMessage(), e);
	   }finally{
		   if(sqlSession!=null){
			   sqlSession.close();  
		   }
		   LogCvt.info("定时任务: 营销--迁移过期卷------结束------"); 
	   }
   }
   //处理大数据函数
   public boolean dealBatchVouchers(List<VouchersInfo> vouchersInfos,VouchersInfoMapper vouchersInfoMapper,String flag){
	   List<VouchersInfo> vouchersInfoList=new ArrayList<VouchersInfo>();
	   int orginSize=vouchersInfos.size();
	   boolean result=true;
	   LogCvt.debug("定时任务: 营销--迁移卷------开始处理"); 
		   //批量插入
		   if(orginSize>insertNum){
			   for(int i=0;i<orginSize;i++){
				   //每1000插入一次,插入后将新list清0
				   vouchersInfoList.add(vouchersInfos.get(i));
				   if(vouchersInfoList.size()%insertNum==0&&i!=0){
					   result=dealBatchVouchersSql(vouchersInfoList,vouchersInfoMapper,flag);
					   LogCvt.debug("定时任务: 营销--迁移卷------总处理数量："+i); 
					   if(i<orginSize){
						   vouchersInfoList.removeAll(vouchersInfoList);
					   }
				   }else if((vouchersInfoList.size()==(orginSize%insertNum))&&i==orginSize-1){
					   LogCvt.debug("定时任务: 营销--迁移卷------【剩余数量】:"+vouchersInfoList.size());
					   result=dealBatchVouchersSql(vouchersInfoList,vouchersInfoMapper,flag);
				   }
				   if(!result){
					   break;
				   }
				   
			   }
		   }else{
			   //若数量不足1000则直接批量插入
			   result=dealBatchVouchersSql(vouchersInfos,vouchersInfoMapper,flag);
		   }
		  return result;
   }
   public boolean dealBatchVouchersSql(List<VouchersInfo> vouchersInfoList,VouchersInfoMapper vouchersInfoMapper,String flag){
	   boolean result=false;
	   if(flag.equals(UsedFlag)){
		   result=vouchersInfoMapper.addVouchersUseInfoByBatch(vouchersInfoList); 
	   }else if(flag.equals(OutOFDateFlag)){
		   result=vouchersInfoMapper.addVouchersOutOfDateInfoByBatch(vouchersInfoList);
	   }else if(flag.equals(RemoveDayBeforeFlag)){
		  // Integer resultTemp=vouchersInfoMapper.deleteVouchersTemporary(vouchersInfoList);
		   //result=resultTemp>0?true:false;
	   }else if(flag.equals(RemoveHalfYearBeforeFlag)){
		 //  Integer resultTemp=vouchersInfoMapper.deleteVouchersInfoFromHalfYear(vouchersInfoList);
		//   result=resultTemp>0?true:false;
	   }
	  return result; 
   }
   public void removeTempEveryDay(){
	   LogCvt.info("定时任务: 营销--删除临时表前一天数据------开始------");
	   SqlSession sqlSession = null;
	   List<VouchersInfo> vouchersInfoIds=null;
	   try{
		   sqlSession=MyBatisManager.getSqlSessionFactory().openSession();
		   VouchersInfoMapper vouchersInfoMapper = sqlSession.getMapper(VouchersInfoMapper.class);
			   Integer resultNum=vouchersInfoMapper.deleteVouchersTemporary();
			   boolean result=resultNum>0?true:false;
			   if(result){
				   LogCvt.debug("定时任务: 营销--删除临时表前一天数据------成功------");
				   sqlSession.commit();
			   }else{
				   LogCvt.debug("定时任务: 营销--删除临时表前一天数据------失败------");	 	 
			   }
		   
	   }catch(Exception e){
		   sqlSession.rollback();
		   LogCvt.error("定时任务: 营销--删除临时表异常"); 
		   LogCvt.error(e.getMessage(), e);
	   }finally{
		   if(sqlSession!=null){
			   sqlSession.close();  
		   }
		   LogCvt.info("定时任务: 营销--删除临时表前一天数据-----结束------");
	   }
   }
   public Date getDateBefore(String flag){
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(new Date());
		  if(flag.equals(OutOFDateFlag)){
			  calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-AllkindsTimeConfig.getVouchersOutOfDateAnMonthBefore()); 
		  }else if(flag.equals(UsedFlag)){
			  calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-AllkindsTimeConfig.getVouchersUsedAnMonthBefore()); 
		  }
		  return calendar.getTime();
	  }
}
