package com.froad.cbank.coremodule.normal.boss.pojo.system;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 
 * @ClassName: UpdatePwdVo
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2015年11月26日 下午2:03:20 
 * @desc <p>用户修改密码请求vo</p>
 */
public class UpdatePwdVo {
	
	@NotEmpty("密码不可为空")
	private String pwd;
	@NotEmpty("旧密码不可为空")
	private String oldPwd;
	
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	
	
}
