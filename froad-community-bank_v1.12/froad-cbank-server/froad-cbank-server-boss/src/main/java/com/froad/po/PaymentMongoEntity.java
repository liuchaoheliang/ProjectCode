package com.froad.po;
import java.io.Serializable;
import java.util.Date;
public class PaymentMongoEntity implements Serializable{

private static final long serialVersionUID = -9046234598170623135L;

private Long id; //主键id

private Date create_time; //创建时间

private String payment_id; //

private String client_id; //客户端编号

private String order_id; //关联订单

private String bill_no;  //bill支付Num 或者积分支付的payPointNo

private Integer payment_type; //支付类型  1-积分 2-现金

private Integer payment_value; //支付额

private Integer payment_type_details;//支付详细   --> 同cashType枚举  0 代表积分支付

private Integer step; //当前步骤  1-等待支付  2-开始支付中 3-支付请求发送完毕 4-明确支付结果

private Boolean is_enable; //是否有效

private String payment_status; //支付状态 1、等待支付 2、支付请求发送成功 3、支付请求发送异常  4、支付成功 5、支付失败 

private String payment_org_no; //支付机构号

private String from_account_name; 

private String from_account_no;

private String to_account_name;

private String to_account_no;

private String from_phone;

private String to_phone;

private String from_user_name;

private String to_user_name;

private String result_code;

private String result_desc;

private String remark;

private String auto_refund; //0 未发生自动退款 1 自动退款失败 2 自动退款成功

private Integer point_rate; //积分比例

private String payment_reason; //0-结算 1-支付退款 2-用户支付 3-赠送积分 4-赠送积分退款(扣除已赠送的积分)

private String is_dispose_exception; //异常支付流水是否处理   0 - 默认    1 - 已处理

private String refund_points_bill_no; //退积分的refundPointsNo

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getBill_no() {
		return bill_no;
	}

	public void setBill_no(String bill_no) {
		this.bill_no = bill_no;
	}

	public Integer getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(Integer payment_type) {
		this.payment_type = payment_type;
	}

	public Integer getPayment_value() {
		return payment_value;
	}

	public void setPayment_value(Integer payment_value) {
		this.payment_value = payment_value;
	}

	public Integer getPayment_type_details() {
		return payment_type_details;
	}

	public void setPayment_type_details(Integer payment_type_details) {
		this.payment_type_details = payment_type_details;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Boolean getIs_enable() {
		return is_enable;
	}

	public void setIs_enable(Boolean is_enable) {
		this.is_enable = is_enable;
	}

	public String getPayment_status() {
		return payment_status;
	}

	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}

	public String getPayment_org_no() {
		return payment_org_no;
	}

	public void setPayment_org_no(String payment_org_no) {
		this.payment_org_no = payment_org_no;
	}

	public String getFrom_account_name() {
		return from_account_name;
	}

	public void setFrom_account_name(String from_account_name) {
		this.from_account_name = from_account_name;
	}

	public String getFrom_account_no() {
		return from_account_no;
	}

	public void setFrom_account_no(String from_account_no) {
		this.from_account_no = from_account_no;
	}

	public String getTo_account_name() {
		return to_account_name;
	}

	public void setTo_account_name(String to_account_name) {
		this.to_account_name = to_account_name;
	}

	public String getTo_account_no() {
		return to_account_no;
	}

	public void setTo_account_no(String to_account_no) {
		this.to_account_no = to_account_no;
	}

	public String getFrom_phone() {
		return from_phone;
	}

	public void setFrom_phone(String from_phone) {
		this.from_phone = from_phone;
	}

	public String getTo_phone() {
		return to_phone;
	}

	public void setTo_phone(String to_phone) {
		this.to_phone = to_phone;
	}

	public String getFrom_user_name() {
		return from_user_name;
	}

	public void setFrom_user_name(String from_user_name) {
		this.from_user_name = from_user_name;
	}

	public String getTo_user_name() {
		return to_user_name;
	}

	public void setTo_user_name(String to_user_name) {
		this.to_user_name = to_user_name;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getResult_desc() {
		return result_desc;
	}

	public void setResult_desc(String result_desc) {
		this.result_desc = result_desc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAuto_refund() {
		return auto_refund;
	}

	public void setAuto_refund(String auto_refund) {
		this.auto_refund = auto_refund;
	}

	public Integer getPoint_rate() {
		return point_rate;
	}

	public void setPoint_rate(Integer point_rate) {
		this.point_rate = point_rate;
	}

	public String getPayment_reason() {
		return payment_reason;
	}

	public void setPayment_reason(String payment_reason) {
		this.payment_reason = payment_reason;
	}

	public String getIs_dispose_exception() {
		return is_dispose_exception;
	}

	public void setIs_dispose_exception(String is_dispose_exception) {
		this.is_dispose_exception = is_dispose_exception;
	}

	public String getRefund_points_bill_no() {
		return refund_points_bill_no;
	}

	public void setRefund_points_bill_no(String refund_points_bill_no) {
		this.refund_points_bill_no = refund_points_bill_no;
	}
	
	
}
