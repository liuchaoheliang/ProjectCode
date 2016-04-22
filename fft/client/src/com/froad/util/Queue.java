package com.froad.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Queue {
	private int size;
	private List container;
	public Queue(int size){
		this.size=size;
		container=new ArrayList();
	}
	public synchronized  void add(Object ele){
		if(container.size()==size){
			container.remove(size-1);
		}
		container.add(0, ele);
	}
	
	public synchronized  void addAll(List eleList){
		if(Assert.empty(eleList))
			return ;
		Iterator it=eleList.iterator();
		int i=0;
		while(it.hasNext()){
			if(i<size)
				add(it.next());
			else
				break;
			i++;
		}
	}
	
	public synchronized List getAll(){
		return container;
	}
	
	public synchronized int getQueueSize(){
		return container.size();
	}
	
	
	public static void main(String[] args){
		final Queue q=new Queue(2);
//		q.add("1");
//		System.out.println(q.container);
//		q.add("-1");
//		System.out.println(q.container);
//		q.add("-2");
//		System.out.println(q.container);
//		q.add("-3");
//		System.out.println(q.container);
//		q.add("-4");
//		System.out.println(q.container);
//		q.add("-5");
//		System.out.println(q.container);
//		q.add("-6");
//		System.out.println(q.container);
		
		for(int i=0;i<10;i++){
			final int t=i;
			new Thread(new Runnable(){
				public void run(){
					q.add(t);
					System.out.println(q.getAll());
				}
			}).start();
		}
	}
	
}
