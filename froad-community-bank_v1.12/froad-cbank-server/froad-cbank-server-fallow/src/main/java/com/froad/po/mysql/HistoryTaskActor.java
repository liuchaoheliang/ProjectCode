package com.froad.po.mysql;

/**
 * CbHistoryTaskActor entity.
 */

public class HistoryTaskActor implements java.io.Serializable {

	// Fields

	private String taskId;
	private String actorId;

	public HistoryTaskActor() {
		super();
	}

	public HistoryTaskActor(String taskId, String actorId) {
		super();
		this.taskId = taskId;
		this.actorId = actorId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

}