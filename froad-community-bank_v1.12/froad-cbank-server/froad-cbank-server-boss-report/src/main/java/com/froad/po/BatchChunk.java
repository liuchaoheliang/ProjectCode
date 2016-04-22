package com.froad.po;

import java.util.Date;

public class BatchChunk {
	/**
	 * chunk id
	 */
	private Integer chunkId = null;
	
	/**
	 * batch date
	 */
	private Integer batchDate = null;
	
	/**
	 * last batch date
	 */
	private Integer lastBatchDate = null;
	
	/**
	 * batch id
	 */
	private Integer batchId = null;
	
	/**
	 * node id
	 */
	private Integer nodeId = null;
	
	/**
	 * job id
	 */
	private Integer jobId = null;
	
	/**
	 * batch job name
	 */
	private String jobName = null;
	
	/**
	 * input file
	 */
	private String inputFile = null;
	
	/**
	 * chunk page number
	 */
	private Integer chunkPage = null;
	
	/**
	 * chunk size
	 */
	private Integer chunkSize = null;
	
	/**
	 * total chunk
	 */
	private Integer totalChunk = null;
	
	/**
	 * total size
	 */
	private Integer totalSize = null;
	
	/**
	 * batch chunk start time
	 */
	private Date startTime = null;
	
	/**
	 * batch chunk end time
	 */
	private Date endTime = null;
	
	/**
	 * batch chunk status
	 */
	private char status = ' ';

	/**
	 * @return the chunkId
	 */
	public Integer getChunkId() {
		return chunkId;
	}

	/**
	 * @param chunkId the chunkId to set
	 */
	public void setChunkId(Integer chunkId) {
		this.chunkId = chunkId;
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
	 * @return the nodeId
	 */
	public Integer getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the jobId
	 */
	public Integer getJobId() {
		return jobId;
	}

	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @return the inputFile
	 */
	public String getInputFile() {
		return inputFile;
	}

	/**
	 * @param inputFile the inputFile to set
	 */
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	/**
	 * @return the chunkPage
	 */
	public Integer getChunkPage() {
		return chunkPage;
	}

	/**
	 * @param chunkPage the chunkPage to set
	 */
	public void setChunkPage(Integer chunkPage) {
		this.chunkPage = chunkPage;
	}

	/**
	 * @return the chunkSize
	 */
	public Integer getChunkSize() {
		return chunkSize;
	}

	/**
	 * @param chunkSize the chunkSize to set
	 */
	public void setChunkSize(Integer chunkSize) {
		this.chunkSize = chunkSize;
	}

	/**
	 * @return the totalChunk
	 */
	public Integer getTotalChunk() {
		return totalChunk;
	}

	/**
	 * @param totalChunk the totalChunk to set
	 */
	public void setTotalChunk(Integer totalChunk) {
		this.totalChunk = totalChunk;
	}

	/**
	 * @return the totalSize
	 */
	public Integer getTotalSize() {
		return totalSize;
	}

	/**
	 * @param totalSize the totalSize to set
	 */
	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
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
		strb.append(" chunkId: ");
		strb.append(this.chunkId);
		strb.append(" batchDate: ");
		strb.append(this.batchDate);
		strb.append(" lastBatchDate: ");
		strb.append(this.lastBatchDate);
		strb.append(" batchId: ");
		strb.append(this.batchId);
		strb.append(" nodeId: ");
		strb.append(this.nodeId);
		strb.append(" jobId: ");
		strb.append(this.jobId);
		strb.append(" jobName: ");
		strb.append(this.jobName);
		strb.append(" inputFile: ");
		strb.append(this.inputFile);
		strb.append(" chunkPage: ");
		strb.append(this.chunkPage);
		strb.append(" chunkSize: ");
		strb.append(this.chunkSize);
		strb.append(" totalChunk: ");
		strb.append(this.totalChunk);
		strb.append(" totalSize: ");
		strb.append(this.totalSize);			
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
