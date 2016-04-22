package com.froad.timer.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("tasks")
public class TaskList {
	
	@XStreamImplicit
	private List<TaskBean> list;

	public List<TaskBean> getList() {
		return list;
	}

	public void setList(List<TaskBean> list) {
		this.list = list;
	}
	
}
