#!/usr/bin/env python
import redis
import json
import signal
import datetime
import MySQLdb
import time
import pymongo

r = None
conn = None
mongo_conn = None

merchant_city = {}
merchant_county = {}
product_type_db = {}

def sigint_handler(signum, frame):
    sys.exit(0)

def load_info():
    #加载一些基本的配置信息，例如商户机构对应关系、商品类型与ID的对应关系
    cur = conn.cursor()
    sql_mer = "select merchant_id,city_org_code,county_org_code from cb_merchant"
    
    try:
        cur.execute(sql)
        results = cur.fetchall()
        for row in results:
            merchant_id = row[0]
            city_org_code = row[1]
            county_org_code = row[2]
            merchant_city[merchant_id] = city_org_code
            merchant_county[merchant_id] = county_org_code
    except Exception, e:
        print "Error: unable to fecth merchant data"
    cur.close()
    #conn.close()
    
    mongo_good = mongo_conn.cbank.cb_product_details//需要从mongo中查询
    max_count = mongo_good.count()
    page_size = max_count / 10
    page_count = max_count % 10
    if page_count > 0:
        page_size = page_size + 1
    for i in range(0, page_size):
        mongo_res = mongo_good.find({},{'_id':1,"product_category_info":1}).skip(10 * i).limit(10)
        for document in mongo_res:
            product_id = str(document["_id"])
            category_info = document["product_category_info"]
            product_type_db[product_id]= []
            for category_item in category_info:
                product_type_db[product_id].append(str(category_item["product_category_id"]))
    mongo_conn.disconnect()

day_members_key = "member:day:set"
week_members_key = "member:week:set"
month_members_key = "member:month:set"

#根据商品ID获取商品类型
def get_product_type(product_id):
    product_types = None
    try:
        product_types = product_type_db[product_id]
    catch Exception, e:
        #nothing
    return product_types

#返回市级以及区级机构code
def get_merchant_city_code(merchant_id):
    city_org = ""
    try:
        city_org = merchant_city[merchant_id]
    catch Exception, e:
        #nothing
    return city_org
    

def get_merchant_county_code(merchant_id):
    county_org = ""
    try:
        county_org = merchant_county[merchant_id])
    catch Exception, e:
        #nothing
    return county_org

def count_order(year, month, day, weekyear, weekmonth, weekday, monthyear, monthmonth, monthday, 
client_id, biz_type, city_code, county_code, payment_method, merchant_id, total_money, product_money, order_cnt, pcnt):
    order_key = "order:" + year + ":" + month + ":" + day + ":" + client_id + ":" + 
    biz_type + ":" + city_code + ":" + county_code + ":" + payment_method + ":" + merchant_id
    order_cnt_key = "ocnt:" + order_key
    order_money_key = "omoney:" + order_key
    order_pcnt_key = "pcnt:" + order_key
    order_pmoney_key = "pmoney:" + order_key

    order_biz_key = "orderb:" + year + ":" + month + ":" + day + ":" + client_id + ":" + biz_type
    order_code_key = "orderc:" + year + ":" + month + ":" + day + ":" + client_id + ":" + city_code + ":" + county_code
    order_meth_key = "ordermt:" + year + ":" + month + ":" + day + ":" + client_id + ":" + payment_method
    order_mer_key = "ordermr:" + year + ":" + month + ":" + day + ":" + client_id + ":" + merchant_id

    week_order_key = "worder:" + weekyear + ":" + weekmonth + ":" + weekday + ":" + client_id + ":" + 
        biz_type + ":" + city_code + ":" + county_code + ":" + payment_method
    week_order_biz_key = "worderb:" + weekyear + ":" + weekmonth + ":" + weekday + ":" + client_id + ":" + biz_type
    week_order_code_key = "worderc:" + weekyear + ":" + weekmonth + ":" + weekday + ":" + client_id + ":" + city_code + ":" + county_code
    week_order_meth_key = "wordermt:" + weekyear + ":" + weekmonth + ":" + weekday + ":" + client_id + ":" + payment_method
    week_order_mer_key = "wordermr:" + weekyear + ":" + weekmonth + ":" + weekday + ":" + client_id + ":" + merchant_id

    month_order_key = "morder:" + monthyear + ":" + monthmonth + ":" + monthday + ":" + client_id + ":" + 
        biz_type + ":" + city_code + ":" + county_code + ":" + payment_method
    month_order_biz_key = "morderb:" + monthyear + ":" + monthmonth + ":" + monthday + ":" + client_id + ":" + biz_type
    month_order_code_key = "morderc:" + monthyear + ":" + monthmonth + ":" + monthday + ":" + client_id + ":" + city_code + ":" + county_code
    month_order_meth_key = "mordermt:" + monthyear + ":" + monthmonth + ":" + monthday + ":" + client_id + ":" + payment_method
    month_order_mer_key = "mordermr:" + monthyear + ":" + monthmonth + ":" + monthday + ":" + client_id + ":" + merchant_id

    #天级别全量维度去重
    order_membercnt_key = "mcnt:" + order_key
    order_biz_membercnt_key = "mcnt:" + order_biz_key
    order_code_membercnt_key = "mcnt:" + order_code_key
    order_meth_membercnt_key = "mcnt:" + order_meth_key
    order_mer_membercnt_key = "mcnt:" + order_mer_key
    #周级别全量去重
    week_order_membercnt_key = "mcnt:" + week_order_key
    week_order_biz_membercnt_key = "mcnt:" + week_order_biz_key
    week_order_code_membercnt_key = "mcnt:" + week_order_code_key
    week_order_meth_membercnt_key = "mcnt:" + week_order_meth_key
    week_order_mer_membercnt_key = "mcnt:" + week_order_mer_key
    #月级别全量去重
    month_order_membercnt_key = "mcnt:" + month_order_key
    month_order_biz_membercnt_key = "mcnt:" + month_order_biz_key
    month_order_code_membercnt_key = "mcnt:" + month_order_code_key
    month_order_meth_membercnt_key = "mcnt:" + month_order_meth_key
    month_order_mer_membercnt_key = "mcnt:" + month_order_mer_key

    #按照所有维度去重
    detail_member_code = client_id + ":" + biz_type + ":" + city_code + ":" + 
    county_code + ":" + payment_method + ":" + merchant_id + ":" + member_code
    #按照业务类型去重
    biz_member_code = client_id + ":" + biz_type + ":" + member_code
    #按照机构号去重
    org_member_code = client_id + ":" + city_code + ":" + county_code + ":" + member_code
    #按照支付方式去重
    method_member_code = client_id + ":" + payment_method + ":" + member_code
    #按照商户ID去重
    mer_member_code = client_id + ":" + merchant_id + ":" + member_code

    pipe = r.pipeline()
    pipe.incrby(order_cnt_key, order_cnt)
    pipe.incrby(order_money_key, total_money)
    pipe.incrby(order_pcnt_key, pcnt)
    pipe.incrby(order_pmoney_key, product_money)

    #按照所有维度去重
    day_member_ret = r.ismember(day_members_key, detail_member_code)
    if day_member_ret == 0:
        pipe.incr(order_membercnt_key)
        pipe.sadd(day_members_key, detail_member_code)
    week_member_ret = r.ismember(week_members_key, detail_member_code)
    if week_member_ret == 0:
        pipe.incr(week_order_membercnt_key)
        pipe.sadd(week_members_key, detail_member_code)
    month_member_ret = r.ismember(month_members_key, detail_member_code)
    if month_member_ret == 0:
        pipe.incr(month_order_membercnt_key)
        pipe.sadd(month_members_key, detail_member_code)

    #按照业务类型去重
    day_member_ret = r.ismember(day_members_key, biz_member_code)
    if day_member_ret == 0:
        pipe.incr(order_biz_membercnt_key)
        pipe.sadd(day_members_key, biz_member_code)
    week_member_ret = r.ismember(week_members_key, biz_member_code)
    if week_member_ret == 0:
        pipe.incr(week_order_biz_membercnt_key)
        pipe.sadd(week_members_key, biz_member_code)
    month_member_ret = r.ismember(month_members_key, biz_member_code)
    if month_member_ret == 0:
        pipe.incr(month_order_biz_membercnt_key)
        pipe.sadd(month_members_key, biz_member_code)

    #按照机构号去重
    day_member_ret = r.ismember(day_members_key, org_member_code)
    if day_member_ret == 0:
        pipe.incr(order_code_membercnt_key)
        pipe.sadd(day_members_key, org_member_code)
    week_member_ret = r.ismember(week_members_key, org_member_code)
    if week_member_ret == 0:
        pipe.incr(week_order_code_membercnt_key)
        pipe.sadd(week_members_key, org_member_code)
    month_member_ret = r.ismember(month_members_key, org_member_code)
    if month_member_ret == 0:
        pipe.incr(month_order_code_membercnt_key)
        pipe.sadd(month_members_key, org_member_code)

    #按照支付方式去重
    day_member_ret = r.ismember(day_members_key, method_member_code)
    if day_member_ret == 0:
        pipe.incr(order_meth_membercnt_key)
        pipe.sadd(day_members_key, method_member_code)
    week_member_ret = r.ismember(week_members_key, method_member_code)
    if week_member_ret == 0:
        pipe.incr(week_order_meth_membercnt_key)
        pipe.sadd(week_members_key, method_member_code)
    month_member_ret = r.ismember(month_members_key, method_member_code)
    if month_member_ret == 0:
        pipe.incr(month_order_meth_membercnt_key)
        pipe.sadd(month_members_key, method_member_code)
        
    #按照商户去重
    day_member_ret = r.ismember(day_members_key, mer_member_code)
    if day_member_ret == 0:
        pipe.incr(order_mer_membercnt_key)
        pipe.sadd(day_members_key, mer_member_code)
    week_member_ret = r.ismember(week_members_key, mer_member_code)
    if week_member_ret == 0:
        pipe.incr(week_order_mer_membercnt_key)
        pipe.sadd(week_members_key, mer_member_code)
    month_member_ret = r.ismember(month_members_key, mer_member_code)
    if month_member_ret == 0:
        pipe.incr(month_order_mer_membercnt_key)
        pipe.sadd(month_members_key, mer_member_code)

    #批量更新到redis中
    pipe.execute()

def count_product(year, month, day, client_id, biz_type, city_code,
county_code, product_types, product_id, product_money, product_count):
    for product_type in product_types:
        good_key = "good:" + year + ":" + month + ":" + day + ":" + client_id + ":" + biz_type + ":" + city_code + 
    ":" + county_code + ":" + product_type + ":" + product_id
        good_pcnt_key = "pcnt:" + good_key
        good_pmoney_key = "pmoney:" + good_key
        pipe = r.pipeline()
        pipe.incrby(good_pcnt_key, product_count)
        pipe.incrby(good_pmoney_key, product_money)
        #批量更新到redis中
        pipe.execute()

def order_count(year, month, day, weekyear, weekmonth, weekday, monthyear, monthmonth, monthday):
    while 1:
        lines = file.readlines(10000)
        if not lines:
            break
        for line in lines:
            order_map = json.loads(line)
            client_id = str(order_map["client_id"])
            member_code = str(order_map["member_code"])
            payment_method = str(order_map["payment_method"])
            total_money = int(order_map["total_price"])
            if order_map["step"] == pay_succ:
                #暂时只处理支付成功的订单
                if order_map["order_type"] == "1":
                    #面对面
                    merchant_id = order_map["merchant_id"]
                    product_id = order_map["product_id"]
                    product_money = int(order_map["money"])
                    biz_type = "1"
                    #TODO
                    city_code = get_merchant_city_code(merchant_id)
                    county_code = get_merchant_county_code(merchant_id)
                    #统计订单
                    count_order(year, month, day, weekyear, weekmonth, weekday, monthyear, monthmonth, monthday, 
                            client_id, biz_type, city_code, county_code, payment_method, merchant_id, total_money, product_money, 1, 1)
                    #面对面暂时不统计商品
                elif order_map["order_type"] == "2":
                    #普通订单
                    sub_orders = order_map["sub_order"]
                    for subo in sub_orders:
                        biz_type = subo["type"]
                        merchant_id = subo["merchant_id"]
                        products = subo["products"]
                        product_money_sum = 0
                        product_count_sum = 0
                        #TODO
                        city_code = get_merchant_city_code(merchant_id)
                        county_code = get_merchant_county_code(merchant_id)
                        for subp in products:
                            product_id = subp["product_id"]
                            product_money = int(subp["money"])
                            product_count = int(subp["quantity"])
                            product_money_sum = product_money_sum + product_money
                            product_count_sum = product_count_sum + product_count
                            #TODO
                            product_types = get_product_type(product_id)
                            #统计商品
                            count_product(year, month, day, client_id, biz_type, city_code, 
                            county_code, product_types, product_id, product_money, product_count)
                        #统计订单
                        count_order(year, month, day, weekyear, weekmonth, weekday, monthyear, monthmonth, monthday, 
                                client_id, biz_type, city_code, county_code, payment_method, merchant_id, total_money, 
                                product_money_sum, 1, product_count_sum)
                        

#store count res into mysql
def store_count(year, month, day, weekyear, weekmonth, weekday, monthyear, monthmonth, monthday):
    '''
    order_cnt_key = ocnt:order:year:month:day:client_id:biz_type:city_code:county_code:payment_method:merchant_id
    order_money_key = omoney:order:year:month:day:client_id:biz_type:city_code:county_code:payment_method:merchant_id
    order_pcnt_key = pcnt:order:year:month:day:client_id:biz_type:city_code:county_code:payment_method:merchant_id
    order_pmoney_key = pmoney:order:year:month:day:client_id:biz_type:city_code:county_code:payment_method:merchant_id
    '''
    order_cnt_keys = r.keys("ocnt:order:year:month:day:*")
    order_mny_keys = r.keys("omoney:order:year:month:day:*")
    order_pcnt_keys = r.keys("pcnt:order:year:month:day:*")
    order_pmny_keys = r.keys("pmoney:order:year:month:day:*")
    
    cur = conn.cursor()
    sql_day_order_cnt = "insert into order_res(year, month,day, client_id, city_org, county_org, payment_method, biz_type, merchant_id, day_ocnt) values(%d, %d, %d, %l, %s, %s, %s, %s, %s, %d)"
    values = []
    for order_cnt_key in order_cnt_keys:
        value = r.get(order_cnt_key)
        order_cnt_key_arr = order_cnt_key.split(":")
        year = order_cnt_key_arr[2]
        month = order_cnt_key_arr[3]
        day = order_cnt_key_arr[4]
        client_id = order_cnt_key_arr[5]
        biz_type = order_cnt_key_arr[6]
        city_code = order_cnt_key_arr[7]
        county_code = order_cnt_key_arr[8]
        payment_method = order_cnt_key_arr[9]
        merchant_id = order_cnt_key_arr[10]
        values.append((year, month, day, client_id, biz_type, city_code, county_code, payment_method, merchant_id, value))
    try:
        cur.executemany(sql_day_order, values)
    except Exception,e:
        print "Error: unable to insert day order count"
    finally:
        cur.close()
        conn.commit()

    sql_day_order_mny = "update order_res o set o.day_omoney=%d where o.year=%d, o.month=%d, o.day=%d, o.client_id=%l, o.city_org=%s, o.county_org=%s, o.payment_method=%s, o.biz_type=%s, o.merchant_id=%s) values(%d, %d, %d, %d, %l, %s, %s, %s, %s, %s)"
    values = []
    for order_mny_key in order_mny_keys:
        value = r.get(order_mny_key)
        order_mny_key_arr = order_mny_key.split(":")
        year = order_mny_key_arr[2]
        month = order_mny_key_arr[3]
        day = order_mny_key_arr[4]
        client_id = order_mny_key_arr[5]
        biz_type = order_mny_key_arr[6]
        city_code = order_mny_key_arr[7]
        county_code = order_mny_key_arr[8]
        payment_method = order_mny_key_arr[9]
        merchant_id = order_mny_key_arr[10]
        values.append((value, year, month, day, client_id, biz_type, city_code, county_code, payment_method, merchant_id))
    try:
        cur.executemany(sql_day_order_mny, values)
    except Exception,e:
        print "Error: unable to insert day order money"
    finally:
        cur.close()
        conn.commit()

    sql_day_order_pcnt = "update order_res o set o.day_pcnt=%d where o.year=%d, o.month=%d, o.day=%d, o.client_id=%l, o.city_org=%s, o.county_org=%s, o.payment_method=%s, o.biz_type=%s, o.merchant_id=%s) values(%d, %d, %d, %d, %l, %s, %s, %s, %s, %s)"
    values = []
    for order_pcnt_key in order_pcnt_keys:
        value = r.get(order_pcnt_key)
        order_pcnt_key_arr = order_pcnt_key.split(":")
        year = order_pcnt_key_arr[2]
        month = order_pcnt_key_arr[3]
        day = order_pcnt_key_arr[4]
        client_id = order_pcnt_key_arr[5]
        biz_type = order_pcnt_key_arr[6]
        city_code = order_pcnt_key_arr[7]
        county_code = order_pcnt_key_arr[8]
        payment_method = order_pcnt_key_arr[9]
        merchant_id = order_pcnt_key_arr[10]
        values.append((value, year, month, day, client_id, biz_type, city_code, county_code, payment_method, merchant_id))
    try:
        cur.executemany(sql_day_order_pcnt, values)
    except Exception,e:
        print "Error: unable to insert day order good count"
    finally:
        cur.close()
        conn.commit()

    sql_day_order_pmny = "update order_res o set o.day_pmoney=%d where o.year=%d, o.month=%d, o.day=%d, o.client_id=%l, o.city_org=%s, o.county_org=%s, o.payment_method=%s, o.biz_type=%s, o.merchant_id=%s) values(%d, %d, %d, %d, %l, %s, %s, %s, %s, %s)"
    values = []
    for order_pmny_key in order_pmny_keys:
        value = r.get(order_pcnt_key)
        order_pmny_key_arr = order_pmny_key.split(":")
        year = order_pmny_key_arr[2]
        month = order_pmny_key_arr[3]
        day = order_pmny_key_arr[4]
        client_id = order_pmny_key_arr[5]
        biz_type = order_pmny_key_arr[6]
        city_code = order_pmny_key_arr[7]
        county_code = order_pmny_key_arr[8]
        payment_method = order_pmny_key_arr[9]
        merchant_id = order_pmny_key_arr[10]
        values.append((value, year, month, day, client_id, biz_type, city_code, county_code, payment_method, merchant_id))
    try:
        cur.executemany(sql_day_order_pmny, values)
    except Exception,e:
        print "Error: unable to insert day order good money"
    finally:
        cur.close()
        conn.commit()

    '''
    order_membercnt_key = mcnt:order:year:month:day:client_id:biz_type:city_code:county_code:payment_method:merchant_id
    order_biz_membercnt_key = mcnt:order:year:month:day:client_id:biz_type
    order_code_membercnt_key = mcnt:order:year:month:day:client_id:city_code:county_code
    order_meth_membercnt_key = mcnt:order:year:month:day:client_id:payment_method
    order_mer_membercnt_key = mcnt:order:year:month:day:client_id:merchant_id
    '''
    day_all_mcnt_keys = r.keys("mcnt:order:year:month:day:*")
    day_biz_mcnt_keys = r.keys("mcnt:orderb:year:month:day:*")
    day_code_mcnt_keys = r.keys("mcnt:orderc:year:month:day:*")
    day_meth_mcnt_keys = r.keys("mcnt:ordermt:year:month:day:*")
    day_mer_mcnt_keys = r.keys("mcnt:ordermr:year:month:day:*")
    
    sql_day_order_mcnt = "update order_res o set o.day_mcnt=%d where o.year=%d, o.month=%d, o.day=%d, o.client_id=%l, o.city_org=%s, o.county_org=%s, o.payment_method=%s, o.biz_type=%s, o.merchant_id=%s) values(%d, %d, %d, %d, %l, %s, %s, %s, %s, %s)"
    values = []
    for order_mcnt_key in day_all_mcnt_keys:
        value = r.get(order_mcnt_key)
        order_mcnt_key_arr = order_mcnt_key.split(":")
        year = order_mcnt_key_arr[2]
        month = order_mcnt_key_arr[3]
        day = order_mcnt_key_arr[4]
        client_id = order_mcnt_key_arr[5]
        biz_type = order_mcnt_key_arr[6]
        city_code = order_mcnt_key_arr[7]
        county_code = order_mcnt_key_arr[8]
        payment_method = order_mcnt_key_arr[9]
        merchant_id = order_mcnt_key_arr[10]
        values.append((value, year, month, day, client_id, biz_type, city_code, county_code, payment_method, merchant_id))
    try:
        cur.executemany(sql_day_order_mcnt, values)
    except Exception,e:
        print "Error: unable to insert day order member count"
    finally:
        cur.close()
        conn.commit()

    sql_day_order_bizmcnt = "insert order_biz_res(year, month, day, client_id, biz_type, day_biz_mcnt) values(%d, %d, %d, %l, %s, %d)"
    values = []
    for biz_mcnt_key in day_biz_mcnt_keys:
        value = r.get(biz_mcnt_key)
        biz_mcnt_key_arr = biz_mcnt_key.split(":")
        year = biz_mcnt_key_arr[2]
        month = biz_mcnt_key_arr[3]
        day = biz_mcnt_key_arr[4]
        client_id = biz_mcnt_key_arr[5]
        biz_type = biz_mcnt_key_arr[6]
        values.append((year, month, day, client_id, biz_type, value))
    try:
        cur.executemany(sql_day_order_bizmcnt, values)
    except Exception,e:
        print "Error: unable to insert day biz member count"
    finally:
        cur.close()
        conn.commit()
        
    sql_day_order_codemcnt = "insert order_biz_res(year, month, day, client_id, city_org, county_org, day_code_mcnt) values(%d, %d, %d, %l, %s, %s, %d)"
    values = []
    for code_mcnt_key in day_code_mcnt_keys:
        value = r.get(code_mcnt_key)
        code_mcnt_key_arr = code_mcnt_key.split(":")
        year = code_mcnt_key_arr[2]
        month = code_mcnt_key_arr[3]
        day = code_mcnt_key_arr[4]
        client_id = code_mcnt_key_arr[5]
        city_code = code_mcnt_key_arr[6]
        county_code = code_mcnt_key_arr[7]
        values.append((year, month, day, client_id, biz_type, value))
    try:
        cur.executemany(sql_day_order_codemcnt, values)
    except Exception,e:
        print "Error: unable to insert day code member count"
    finally:
        cur.close()
        conn.commit()

    #周和月的去重会员数为更新
    '''
    week_order_membercnt_key = mcnt:worder:year:month:day:client_id:biz_type:city_code:county_code:payment_method:merchant_id
    week_order_biz_membercnt_key = "mcnt:" + week_order_biz_key
    week_order_code_membercnt_key = "mcnt:" + week_order_code_key
    week_order_meth_membercnt_key = "mcnt:" + week_order_meth_key
    week_order_mer_membercnt_key = "mcnt:" + week_order_mer_key
    '''
    week_all_mcnt_keys = r.keys("mcnt:worder:year:month:day:*")
    week_biz_mcnt_keys = r.keys("mcnt:worderb:year:month:day:*")
    week_code_mcnt_keys = r.keys("mcnt:worderc:year:month:day:*")
    week_meth_mcnt_keys = r.keys("mcnt:wordermt:year:month:day:*")
    week_mer_mcnt_keys = r.keys("mcnt:wordermr:year:month:day:*")
    
    '''
    month_order_membercnt_key = mcnt:morder:year:month:day:client_id:biz_type:city_code:county_code:payment_method:merchant_id
    month_order_biz_membercnt_key = "mcnt:" + month_order_biz_key
    month_order_code_membercnt_key = "mcnt:" + month_order_code_key
    month_order_meth_membercnt_key = "mcnt:" + month_order_meth_key
    month_order_mer_membercnt_key = "mcnt:" + month_order_mer_key
    '''
    mon_all_mcnt_keys = r.keys("mcnt:morder:year:month:day:*")
    mon_biz_mcnt_keys = r.keys("mcnt:morderb:year:month:day:*")
    mon_code_mcnt_keys = r.keys("mcnt:morderc:year:month:day:*")
    mon_meth_mcnt_keys = r.keys("mcnt:mordermt:year:month:day:*")
    mon_mer_mcnt_keys = r.keys("mcnt:mordermr:year:month:day:*")

    '''
    good_key = good:year:month:day:client_id:biz_type:city_code:county_code:product_type:product_id
    good_pcnt_key = pcnt:good_key
    good_pmoney_key = pmoney:good_key
    '''
    good_pcnt_keys = r.keys("pcnt:good:year:month:day:*")
    good_mny_keys = r.keys("pmoney:good:year:month:day:*")
    
    sql_order = "insert into order_res values(%s,%s,%s,%s)"
    sql_good = "insert into good_res values(%s,%s,%s,%s)"

    sql_week = "insert into week_order_res values(%s,%s,%s,%s)"
    sql_month = "insert into month_order_res values(%s,%s,%s,%s)"
    
    order_cnt_keys_count = len(order_cnt_keys)
    

    cur.close()
    conn.commit()
    conn.close()

if __name__ == "__main__":
    signal.signal(signal.SIGINT, sigint_handler)
    #根据配置文件初始化redis与mysql连接
    with open("./conf", "r") as f:
        conf = json.load(f)
        r = redis.Redis(str(conf["redisHost"]), int(conf["redisPort"]))
        try:
            conn = MySQLdb.connect(host = str(conf["mysqlHost"]), user = str(conf["mysqlUser"]), passwd = str(conf["mysqlPasswd"]),
                    db = str(conf["mysqlDB"]), port = int(conf["mysqlPort"]))
        except MySQLdb.Error, e:
            #TODO
        mongo_conn = pymongo.Connection(str(conf["mongoHost"]) + ":" + str(conf["mongoPort"]))

    load_info()
    
    now_time = datetime.datetime.now()
    yes_time = now_time + datetime.timedelta(days = -1)
    yes_time_nyr = yes_time.strftime('%Y%m%d')

    #如果前一天是周一，清除周会员ID集合
    d = time.localtime(time.time())
    if d.tm_wday == 1:
        r.del(week_membmonth_key)
    #如果前一天是一个月的第一天，清除月会员ID集合
    if d.tm_mday == 2:
        r.del(month_members_key)

    week_time = 0
    month_time = 0

    monthyear = ""
    monthmonth = ""
    monthday = ""

    if d.tm_wday == 0:
        #如果运行当天是一周的第一天，前推一周
        week_time = now_time + datetime.timedelta(days = -7)
    else:
        week_time = now_time + datetime.timedelta(days = 0 - d.tm_wday)
    week_time_nyr = week_time.strftime('%Y%m%d')
    weekyear = week_time_nyr[0:4]
    weekmonth = week_time_nyr[4:6]
    weekday = week_time_nyr[6:8]
    
    if d.tm_mday == 1:
        #如果运行当天是一个月的第一天，前推一个月
        month_time = now_time + datetime.timedelta(days = -10)
        month_time_nyr = month_time.strftime('%Y%m%d')
        monthyear = month_time_nyr[0:4]
        monthmonth = month_time_nyr[4:6]
        monthday = "01"
    else:
        month_time = now_time + datetime.timedelta(days = 0 - d.tm_mday)
        month_time_nyr = month_time.strftime('%Y%m%d')
        monthyear = month_time_nyr[0:4]
        monthmonth = month_time_nyr[4:6]
        monthday = month_time_nyr[6:8]
    order_count(year, month, day, weekyear, weekmonth, weekday, monthyear, monthmonth, monthday)
    store_count(year, month, day, weekyear, weekmonth, weekday, monthyear, monthmonth, monthday)
