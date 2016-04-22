package com.froad.thrift.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.froad.thrift.monitor.vo.BizMethodInfo;
import com.froad.thrift.monitor.vo.BizMethodInvokeInfo;

/**
 * 服务功能运行监控信息
 * @ClassName: ServiceMonitorInfo 
 * @Description: TODO 
 * @author FQ 
 * @date 2015年4月8日 上午11:52:34 
 *
 */
public class ServiceMonitorInfo {

	private long serviceCount = 0;// 服务调用总次数
	private final List<BizMethodInfo> bizMethodInfoList = new ArrayList<BizMethodInfo>();// 业务方法列表
	private final ConcurrentHashMap<String, BizMethodInvokeInfo> bizMethodInvokeInfoMap = new ConcurrentHashMap<String, BizMethodInvokeInfo>();// 业务方法调用信息

	public long getServiceCount() {
		return serviceCount;
	}

	private synchronized void incrementServiceCount() {
		this.serviceCount++;
	}

	public List<BizMethodInfo> getBizMethodInfoList() {
		return this.bizMethodInfoList;
	}

	public void addServiceBizMethod(BizMethodInfo info) {
		this.bizMethodInfoList.add(info);
		this.initBizMethodInvokeInfo(info.getName());
	}

	public ConcurrentHashMap<String, BizMethodInvokeInfo> getBizMethodInvokeInfoMap() {
		return this.bizMethodInvokeInfoMap;
	}

	private void initBizMethodInvokeInfo(String method) {
		BizMethodInvokeInfo bizMethodInvokeInfo = new BizMethodInvokeInfo();
		bizMethodInvokeInfo.setFailureCount(0);
		bizMethodInvokeInfo.setSuccessAverageTime(0);
		bizMethodInvokeInfo.setSuccessCount(0);
		bizMethodInvokeInfo.setSuccessMaxTime(0);
		bizMethodInvokeInfo.setSuccessMinTime(0);
		bizMethodInvokeInfo.setTotalCount(0);
		this.bizMethodInvokeInfoMap.put(method, bizMethodInvokeInfo);
	}

	/**
	 * 业务方法调用信息更新
	 * 
	 * @param method
	 *            方法名
	 * @param isSuccess
	 *            是否成功
	 * @param invokeTime
	 *            方法调用时间，如果失败，不统计时间
	 */
	public void updateBizMethodInvokeInfo(String method, boolean isSuccess,
			long invokeTime) {
		this.incrementServiceCount();
		BizMethodInvokeInfo bizMethodInvokeInfo = this.bizMethodInvokeInfoMap
				.get(method);
		synchronized (bizMethodInvokeInfo) {
			if (isSuccess) {
				if (invokeTime < bizMethodInvokeInfo.getSuccessMinTime()
						|| bizMethodInvokeInfo.getSuccessMinTime() == 0)
					bizMethodInvokeInfo.setSuccessMinTime(invokeTime);
				if (invokeTime > bizMethodInvokeInfo.getSuccessMaxTime()
						|| bizMethodInvokeInfo.getSuccessMaxTime() == 0)
					bizMethodInvokeInfo.setSuccessMaxTime(invokeTime);
				long totalTime = bizMethodInvokeInfo.getSuccessAverageTime()
						* bizMethodInvokeInfo.getSuccessCount();
				bizMethodInvokeInfo
						.setSuccessAverageTime((invokeTime + totalTime)
								/ (bizMethodInvokeInfo.getSuccessCount() + 1));
				bizMethodInvokeInfo.setSuccessCount(bizMethodInvokeInfo
						.getSuccessCount() + 1);
			} else {
				bizMethodInvokeInfo.setFailureCount(bizMethodInvokeInfo
						.getFailureCount() + 1);
			}
			bizMethodInvokeInfo.setTotalCount(bizMethodInvokeInfo
					.getTotalCount() + 1);
		}
	}

	public boolean isBizMethod(String method) {
		return this.bizMethodInvokeInfoMap.containsKey(method);
	}

}
