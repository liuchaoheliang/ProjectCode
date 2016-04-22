package app.po;

import java.io.Serializable;
import java.util.List;

public class Clazz implements Serializable{
	private Integer id;
	private String teacher;
	private String clazz;
	private List<Object> list;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<Object> list) {
		this.list = list;
	}
	
}
