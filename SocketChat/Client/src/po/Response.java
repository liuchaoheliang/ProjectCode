package po;

import java.util.List;

public class Response {
	private String code;
	private List<List<String>> data=null;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<List<String>> getData() {
		return data;
	}
	public void setData(List<List<String>> data) {
		this.data = data;
	}
	
}
