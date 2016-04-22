package com.froad.po;

public class BatchNode {
	/**
	 * node id
	 */
	private Integer nodeId = null;
	
	/**
	 * node name
	 */
	private String nodeName = null;
	
	/**
	 * node ip
	 */
	private String nodeIp = null;
	
	/**
	 * node port
	 */
	private Integer nodePort = null;
	
	/**
	 * master node
	 */
	private char masterNode = ' ';
	
	/**
	 * remark
	 */
	private String remark = null;

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
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return the nodeIp
	 */
	public String getNodeIp() {
		return nodeIp;
	}

	/**
	 * @param nodeIp the nodeIp to set
	 */
	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}

	/**
	 * @return the nodePort
	 */
	public Integer getNodePort() {
		return nodePort;
	}

	/**
	 * @param nodePort the nodePort to set
	 */
	public void setNodePort(Integer nodePort) {
		this.nodePort = nodePort;
	}

	/**
	 * @return the masterNode
	 */
	public char getMasterNode() {
		return masterNode;
	}

	/**
	 * @param masterNode the masterNode to set
	 */
	public void setMasterNode(char masterNode) {
		this.masterNode = masterNode;
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
		strb.append(" nodeId: ");
		strb.append(this.nodeId);
		strb.append(" nodeName: ");
		strb.append(this.nodeName);
		strb.append(" nodeIp: ");
		strb.append(this.nodeIp);
		strb.append(" nodePort: ");
		strb.append(this.nodePort);
		strb.append(" masterNode: ");
		strb.append(this.masterNode);
		strb.append(" remark: ");
		strb.append(this.remark);
		strb.append("}");
		
		return strb.toString();
	}
}
