#除预售之外的中间状态订单
select 
  case
    when trans.type = '08' 
    then '预售' 
    when trans.type = '12' 
    then '名优特惠' 
    when trans.type = '05' 
    then '收款' 
    when trans.type = '02' 
    then '团购' 
    else trans.type 
  end 交易类型,
  trans.create_time 创建时间,
  case
    when trans.state = '20' 
    then '交易处理中' 
    when trans.state = '40' 
    then '支付失败' 
    when trans.state = '60' 
    then '交易异常' 
    else trans.state 
  end 订单状态,
  case
    when pay.pay_state = 1004 
    then '支付成功' 
    when pay.pay_state = 1005 
    then '支付失败' 
    when pay.pay_state = 1012 
    then '退款或退分成功' 
    when pay.pay_state = 1002 
    then '支付请求发送成功' 
    when pay.pay_state = 1011 
    then '退款请求发送失败' 
    when pay.pay_state = 1013 
    then '退款或退分失败' 
    when pay.pay_state = 1010 
    then '发送退款请求成功' 
    when pay.pay_state = 1000 
    then '等待支付' 
    else pay.pay_state 
  end 支付状态,
  case
    when trans.pay_state = '10' 
    then '待支付' 
    when trans.pay_state = '20' 
    then '支付中' 
    when trans.pay_state = '30' 
    then '已支付' 
    when trans.pay_state = '40' 
    then '退款中' 
    when trans.pay_state = '50' 
    then '全额退款' 
    when trans.pay_state = '60' 
    then '部分退款' 
    when trans.pay_state = '70' 
    then '等待商家处理退款' 
    else trans.pay_state 
  end 订单支付状态 #,count(distinct trans.id),count(1)
  ,
  trans.* 
from
  fft_trans trans 
  inner join fft_pay pay 
    on trans.id = pay.trans_id 
where trans.state in (20, 40, 60) 
  and not (
    trans.type = '08' 
    and trans.pay_state = '30'
  ) #group by 交易类型,订单状态,支付状态  