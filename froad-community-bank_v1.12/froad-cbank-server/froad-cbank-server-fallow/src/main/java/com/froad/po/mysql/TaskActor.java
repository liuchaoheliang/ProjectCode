package com.froad.po.mysql;

/**
 * CbTaskActor entity.
 */

public class TaskActor implements java.io.Serializable {

	// Fields

	private String taskId;
	private String actorId;

	public TaskActor() {
		super();
	}

	public TaskActor(String taskId, String actorId) {
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