package com.froad.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("service")
public class ProcessServiceConfig {

	@XStreamAlias("service-name")
	private String serviceName;
	
	@XStreamAlias("service-class")
	private String serviceClass;
	
	@XStreamAlias("service-next")
	private String serviceNext;
	
	@XStreamAlias("load-on-startup")
	private int loadOonStartup;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceClass() {
		return serviceClass;
	}

	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}

	public String getServiceNext() {
		return serviceNext;
	}

	public void setServiceNext(String serviceNext) {
		this.serviceNext = serviceNext;
	}

	public int getLoadOonStartup() {
		return loadOonStartup;
	}

	public void setLoadOonStartup(int loadOonStartup) {
		this.loadOonStartup = loadOonStartup;
	}
	
	
	
}

