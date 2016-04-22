package com.froad.CB.po.user;

import java.io.Serializable;


	/**
	 * 类描述：序列号
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:49:43 PM 
	 */
public class Seq implements Serializable {

	private static final long serialVersionUID = 4241767873867577412L;
	private String ID;
	private String seq;
	private String type;
	private String Seq_type;
	private String Seq_no;
	public String getID() {
		return ID;
	}
	public void setID(String id) {
		this.ID = id;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSeq_type() {
		return Seq_type;
	}
	public void setSeq_type(String seq_type) {
		Seq_type = seq_type;
	}
	public String getSeq_no() {
		return Seq_no;
	}
	public void setSeq_no(String seq_no) {
		Seq_no = seq_no;
	}
}
