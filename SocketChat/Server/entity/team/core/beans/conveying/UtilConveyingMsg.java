package team.core.beans.conveying;

import java.io.Serializable;

public class UtilConveyingMsg implements Serializable{

	private static final long serialVersionUID = 1L;

	private String code;//指令代码|状态代码（服务端=====>客户端:状态代码 | 客户端=====>服务端:指令代码）
	
	private Object object;//传输数据
	
	public UtilConveyingMsg(String code,Object object){
		this.code = code;
		this.object = object;
	}
	public String getCode() {
		return code;
	}
	public Object getObject() {
		return object;
	}
	
}
