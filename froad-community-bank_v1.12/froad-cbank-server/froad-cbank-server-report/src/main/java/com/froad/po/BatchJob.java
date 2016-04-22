package com.froad.po;

public class BatchJob {
	/**
	 * job id
	 */
	private Integer jobId = null;
	
	/**
	 * job name
	 */
	private String jobName = null;
	
	/**
	 * input file
	 */
	private String inputFile = null;
	
	/**
	 * input file type
	 */
	private char inputFileType = ' ';
	
	/**
	 * chunk size
	 */
	private Integer chunkSize = null;
	
	/**
	 * remark
	 */
	private String remark = null;

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
	 * @return the inputFileType
	 */
	public char getInputFileType() {
		return inputFileType;
	}

	/**
	 * @param inputFileType the inputFileType to set
	 */
	public void setInputFileType(char inputFileType) {
		this.inputFileType = inputFileType;
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
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * PO to String
	 */
	public String toString(){
		StringBuffer strb = null;
		
		strb = new StringBuffer();
		strb.append(this.getClass().getSimpleName());
		strb.append("{");
		strb.append(" jobId: ");
		strb.append(this.jobId);
		strb.append(" jobName: ");
		strb.append(this.jobName);
		strb.append(" inputFile: ");
		strb.append(this.inputFile);
		strb.append(" inputFileType: ");
		strb.append(this.inputFileType);
		strb.append(" chunkSize: ");
		strb.append(this.chunkSize);
		strb.append(" remark: ");
		strb.append(this.remark);
		strb.append("}");
		
		return strb.toString();
	}
}
