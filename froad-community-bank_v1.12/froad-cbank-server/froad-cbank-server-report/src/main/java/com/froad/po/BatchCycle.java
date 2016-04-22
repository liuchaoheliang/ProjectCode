package com.froad.po;

import java.util.Date;


public class BatchCycle {
	/**
	 * batch id
	 */
	private Integer batchId = null;
	
	/**
	 * current batch date
	 */
	private Integer batchDate = null;
	
	/**
	 * last batch date
	 */
	private Integer lastBatchDate = null;
	
	/**
	 * last last batch date
	 */
	private Integer lastLastBatchDate = null;
	
	/**
	 * batch start time
	 */
	private Date startTime = null;
	
	/**
	 * batch end time
	 */
	private Date endTime = null;
	
	/**
	 * batch cycle status
	 */
	private char status = ' ';

	/**
	 * @return the batchId
	 */
	public Integer getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	/**
	 * @return the batchDate
	 */
	public Integer getBatchDate() {
		return batchDate;
	}

	/**
	 * @param batchDate the batchDate to set
	 */
	public void setBatchDate(Integer batchDate) {
		this.batchDate = batchDate;
	}

	/**
	 * @return the lastBatchDate
	 */
	public Integer getLastBatchDate() {
		return lastBatchDate;
	}

	/**
	 * @param lastBatchDate the lastBatchDate to set
	 */
	public void setLastBatchDate(Integer lastBatchDate) {
		this.lastBatchDate = lastBatchDate;
	}

	/**
	 * @return the lastLastBatchDate
	 */
	public Integer getLastLastBatchDate() {
		return lastLastBatchDate;
	}

	/**
	 * @param lastLastBatchDate the lastLastBatchDate to set
	 */
	public void setLastLastBatchDate(Integer lastLastBatchDate) {
		this.lastLastBatchDate = lastLastBatchDate;
	}
	
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the status
	 */
	public char getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(char status) {
		this.status = status;
	}

	/**
	 * PO to String
	 */
	public String toString(){
		StringBuffer strb = null;
		
		strb = new StringBuffer();
		strb.append(this.getClass().getSimpleName());
		strb.append("{");
		strb.append(" batchId: ");
		strb.append(this.batchId);
		strb.append(" batchDate: ");
		strb.append(this.batchDate);
		strb.append(" lastBatchDate: ");
		strb.append(this.lastBatchDate);
		strb.append(" lastLastBatchDate: ");
		strb.append(this.lastLastBatchDate);
		strb.append(" startTime: ");
		strb.append(this.startTime);
		strb.append(" endTime: ");
		strb.append(this.endTime);
		strb.append(" status: ");
		strb.append(this.status);
		strb.append("}");
		
		return strb.toString();
	}
}
