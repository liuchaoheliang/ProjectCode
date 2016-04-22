package com.froad.thirdparty.dto.response.pe;

import java.util.Date;


public class MemberVipInfo {

    /**
     * 用户VIP状态
    * <p>Function: VIPStatus</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015年1月6日 上午10:23:03
    * @version 1.0
     */
    public enum VIPStatus {
        
        NOT_VIP("0000", "非VIP"),
        NORMAL("0001", "正常"), 
        PENDING_AUDIT("0002","待审核"), 
        EXPIRED("0003", "已过期");

        private String desc;

        private String value;

        private VIPStatus(final String value, final String desc) {
            this.value = value;
            this.desc = desc;
        }

        public String getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        public String toString() {
            return desc;
        }

        public static boolean checkValue(String value) {

            VIPStatus[] vds = VIPStatus.values();
            for (int i = 0; i < vds.length; i++) {
                if (vds[i].getValue().equals(value)) {
                    return true;
                }
            }
            return false;

        }

        public static VIPStatus getInstanceByValue(String value) {
            VIPStatus[] vds = VIPStatus.values();
            for (int i = 0; i < vds.length; i++) {
                if (vds[i].getValue().equals(value)) {
                    return vds[i];
                }
            }

            throw new IllegalArgumentException("不是有效的值:" + value);
        }
    }

    
    /**
     * 用户VIP等级
    * <p>Function: VIPLevel</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015年1月6日 上午10:23:13
    * @version 1.0
     */
    public enum VIPLevel {

        VIP_LEVEL_1("0001", "VIP一级"),
        VIP_LEVEL_2("0002", "VIP二级"), 
        VIP_LEVEL_3("0003", "三级VIP"), 
        VIP_LEVEL_4("0004", "VIP四级"), 
        VIP_LEVEL_5("0005", "VIP五级"), 
        VIP_LEVEL_6("0006", "VIP六级");

        private String desc;
        private String value;

        private VIPLevel(final String value, final String desc) {
            this.value = value;
            this.desc = desc;
        }

        public String getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        public String toString() {
            return desc;
        }

        public static boolean checkValue(String value) {

            VIPStatus[] vds = VIPStatus.values();
            for (int i = 0; i < vds.length; i++) {
                if (vds[i].getValue().equals(value)) {
                    return true;
                }
            }
            return false;

        }

        public static VIPLevel getInstanceByValue(String value) {
            VIPLevel[] vds = VIPLevel.values();
            for (int i = 0; i < vds.length; i++) {
                if (vds[i].getValue().equals(value)) {
                    return vds[i];
                }
            }

            throw new IllegalArgumentException("不是有效的值:" + value);
        }
    }

    
    //-------------------------------------------------------------VIP Information
    private Long memberCode;

    private String bankOrgNo;

    private String bankLabelName;
    
    private Date vipActivationTime;

    private Date vipExpiratioinTime;

    private String vipLevel;

    private String vipStatus;
    
    private Integer availableDays;

    public Long getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(Long memberCode) {
        this.memberCode = memberCode;
    }

    public Date getVipActivationTime() {
        return vipActivationTime;
    }

    public void setVipActivationTime(Date vipActivationTime) {
        this.vipActivationTime = vipActivationTime;
    }

    public Date getVipExpiratioinTime() {
        return vipExpiratioinTime;
    }

    public void setVipExpiratioinTime(Date vipExpiratioinTime) {
        this.vipExpiratioinTime = vipExpiratioinTime;
    }

	public String getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(String vipLevel) {
		this.vipLevel = vipLevel;
	}

	public String getVipStatus() {
		return vipStatus;
	}

	public void setVipStatus(String vipStatus) {
		this.vipStatus = vipStatus;
	}

	public String getBankLabelName() {
		return bankLabelName;
	}

	public void setBankLabelName(String bankLabelName) {
		this.bankLabelName = bankLabelName;
	}

	public String getBankOrgNo() {
		return bankOrgNo;
	}

	public void setBankOrgNo(String bankOrgNo) {
		this.bankOrgNo = bankOrgNo;
	}

	public Integer getAvailableDays() {
		return availableDays;
	}

	public void setAvailableDays(Integer availableDays) {
		this.availableDays = availableDays;
	}
    
}
