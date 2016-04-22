package com.froad.config;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("services")
public class XmlService {
	
	@XStreamImplicit
	private List<ProcessServiceConfig> config;

	public List<ProcessServiceConfig> getConfig() {
		return config;
	}

	public void setConfig(List<ProcessServiceConfig> config) {
		this.config = config;
	}

	
}

