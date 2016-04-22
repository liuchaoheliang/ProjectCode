
package com.froad.thirdparty.dto.response.pe;

public class MemberPointsInfo {

    /**
     * 方付通积分
     */
    private String froadPoints;

    /**
     * 银行积分
     */
    private String bankPoints;

    /**
     * 方付通积分比例
     */
    private String froadPointsExchageRate;

    /**
     * 银行积分比例
     */
    private String bankPointsExchageRate;

    /**
     * 方付通积分机构号
     */
    private String froadOrgNo;

    /**
     * 银行积分机构号
     */
    private String bankOrgNO;

    public String getFroadPoints() {
        return froadPoints;
    }

    public void setFroadPoints(String froadPoints) {
        this.froadPoints = froadPoints;
    }

    public String getBankPoints() {
        return bankPoints;
    }

    public void setBankPoints(String bankPoints) {
        this.bankPoints = bankPoints;
    }

    public String getFroadPointsExchageRate() {
        return froadPointsExchageRate;
    }

    public void setFroadPointsExchageRate(String froadPointsExchageRate) {
        this.froadPointsExchageRate = froadPointsExchageRate;
    }

    public String getBankPointsExchageRate() {
        return bankPointsExchageRate;
    }

    public void setBankPointsExchageRate(String bankPointsExchageRate) {
        this.bankPointsExchageRate = bankPointsExchageRate;
    }

    public String getFroadOrgNo() {
        return froadOrgNo;
    }

    public void setFroadOrgNo(String froadOrgNo) {
        this.froadOrgNo = froadOrgNo;
    }

    public String getBankOrgNO() {
        return bankOrgNO;
    }

    public void setBankOrgNO(String bankOrgNO) {
        this.bankOrgNO = bankOrgNO;
    }

}
