package com.froad.thrift.monitor.service;

import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;

import com.froad.thrift.monitor.ServiceMonitorInfo;
import com.froad.thrift.monitor.server.BaseServer;
import com.froad.thrift.monitor.vo.BizMethodInfo;
import com.froad.thrift.monitor.vo.BizMethodInvokeInfo;

/**
 * 服务监控基类
 * 
 * @ClassName: BizMonitorBaseService
 * @Description: TODO
 * @author FQ
 * @date 2015年3月28日 上午10:57:14
 *
 */
public abstract class BizMonitorBaseService implements BizMonitorService.Iface {

	private String serviceName;
	private String serviceVersion;
	private long serviceStartTime;
	private ServiceMonitorInfo serviceInfo;
	private BaseServer server;

	protected BizMonitorBaseService() {
		init("", "");
	}

	protected BizMonitorBaseService(String name, String version) {
		init(name, version);
	}

	public String getName() throws TException {
		return this.serviceName;
	}

	public String getVersion() throws TException {
		return this.serviceVersion;
	}

	public List<BizMethodInfo> getServiceBizMethods() throws TException {
		return this.serviceInfo.getBizMethodInfoList();
	}

	public Map<String, BizMethodInvokeInfo> getBizMethodsInvokeInfo()
			throws TException {
		return this.serviceInfo.getBizMethodInvokeInfoMap();
	}

	public BizMethodInvokeInfo getBizMethodInvokeInfo(String methodName)
			throws TException {
		return this.serviceInfo.getBizMethodInvokeInfoMap().get(methodName);
	}

	public long getServiceCount() throws TException {
		return this.serviceInfo.getServiceCount();
	}

	public long aliveSince() throws TException {
		return (System.currentTimeMillis() / 1000) - this.serviceStartTime;
	}

	public void reinitialize() {
		this.server.reinitialize();
	}

	public void shutdown() {
		this.server.stop();
	}

	public void setOption(String key, String value) {
		this.server.setOption(key, value);
	}

	public Map<String, String> getOptions() {
		return this.server.getOptions();
	}

	public BaseServer getServer() {
		return server;
	}

	public void setServer(BaseServer server) {
		this.server = server;
	}

	public ServiceMonitorInfo getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(ServiceMonitorInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	private void init(String name, String version) {
		this.serviceName = name;
		this.serviceVersion = version;
		this.serviceStartTime = System.currentTimeMillis() / 1000;
	}

}
