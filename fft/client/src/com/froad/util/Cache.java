package com.froad.util;

import java.util.List;

public class Cache<E> {
	private List<E> list;
	private int time;
	private long lastTime;
	
	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	public Cache(List<E> list,int time,long lastTime) {
		this.list = list;
		this.time = time;
		this.lastTime = lastTime;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}
	
}
