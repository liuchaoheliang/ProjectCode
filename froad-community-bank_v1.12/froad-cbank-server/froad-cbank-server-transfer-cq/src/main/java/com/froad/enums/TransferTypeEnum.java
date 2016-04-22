package com.froad.enums;

/**
 *  类型转换表
  * @ClassName: TransferEnum
  * @Description: TODO
  * @author share 2015年4月30日
  * @modify share 2015年4月30日
 */
public enum TransferTypeEnum {

	merchant_id("1","商户ID"),
	product_id("2","商品ID"),
	order_id("3","订单ID"),
	sub_order_id("4","子订单ID"),
	outlet_id("5","门店ID"),
	pay_id("6","支付ID"),
	ad_position_id("7","广告位ID"),
	merchant_user_id("8", "商户用户ID"),
	receiver_id("9", "收获地址ID"),
	ad_id("10","广告ID"),
	area_id("11","地区ID"),
	productcategory_id("12","分类商品ID"),
	org_code("13", "机构号"),
	delivery_corp_id("14","物流公司ID"),
	bank_resource_id("15", "银行资源ID"),
	bank_role_id("16", "银行角色ID"),
	shipping_id("17","子订单发货ID"),
	sms_log_id("18","短信日志ID"),
	bank_operator_id("19", "银行操作员ID"),
	refund_id("20","退款ID"),
	merchant_type_id("21", "商户类型ID"),
	sn_order_id("22", "订单SN"),
	merchant_category_id("23", "商户分类ID"),
	merchant_role_id("24", "商户角色ID")
	;
	private String code;
    private String describe;

    private TransferTypeEnum(String code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return this.code;
    }
	
}

