DROP VIEW IF EXISTS `cb_merchant_id`;
CREATE VIEW `cb_merchant_id` AS 

SELECT
	cb_merchant.id
FROM
	cb_merchant
WHERE
	cb_merchant.`name` LIKE '%方付通%';
	
-- =========交易相关=========
-- 券
delete FROM cb_ticket WHERE merchant_id IN (SELECT * FROM cb_merchant_id);
-- 发货
delete FROM cb_shipping WHERE order_id IN (SELECT id FROM cb_order WHERE merchant_id IN (SELECT * FROM cb_merchant_id));
-- 退款
DELETE FROM cb_order_refunds WHERE order_sn IN (SELECT sn FROM cb_order WHERE merchant_id IN (SELECT * FROM cb_merchant_id));
-- 订单
DELETE from cb_order where merchant_id in (SELECT * from cb_merchant_id);

-- =========商品相关=========
-- 商品收藏
DELETE from cb_product_favorite where product_id in (SELECT id from cb_product where merchant_id in (SELECT * from cb_merchant_id));

-- 商品评论
DELETE from cb_product_comment where product_id in (SELECT id from cb_product where merchant_id in (SELECT * from cb_merchant_id));

-- 商品图片
DELETE from cb_product_image where product_id in (SELECT id from cb_product where merchant_id in (SELECT * from cb_merchant_id));
-- 团购商品
delete FROM cb_product_group WHERE product_id IN (SELECT id FROM cb_product WHERE merchant_id IN(SELECT id FROM cb_merchant_id));
-- 预售商品
delete FROM cb_product_presell WHERE product_id IN (SELECT id FROM cb_product WHERE merchant_id IN(SELECT id FROM cb_merchant_id));
-- 商品表
DELETE from cb_product where merchant_id in (SELECT * from cb_merchant_id);

-- =========门店=========
-- 门店相册
delete FROM cb_merchant_outlet_photo WHERE merchant_id IN (SELECT id FROM cb_merchant_id);
-- 门店表
delete FROM cb_merchant_outlet WHERE merchant_id IN (SELECT id FROM cb_merchant_id);

-- =========商户用户=========
-- 商用用户角色
delete FROM cb_merchant_user_role 
	WHERE merchant_user_id IN (SELECT id FROM cb_merchant_user 
	WHERE id IN (SELECT merchant_user_id FROM cb_merchant_group_user WHERE merchant_id IN(SELECT id FROM cb_merchant_id)));
-- 商户用户
delete FROM cb_merchant_user WHERE id IN (SELECT merchant_user_id FROM cb_merchant_group_user WHERE merchant_id IN(SELECT id FROM cb_merchant_id));
-- 商户用户组
delete FROM cb_merchant_group_user WHERE merchant_id IN(SELECT id FROM cb_merchant_id);

-- 门店收藏
delete FROM cb_merchant_outlet_favorite WHERE merchant_id IN (SELECT id FROM cb_merchant_id);

-- 门店收藏
delete FROM cb_merchant_outlet_favorite WHERE merchant_id IN (SELECT id FROM cb_merchant_id);
-- 商户商户回复
DELETE from cb_merchant_product_comment WHERE merchant_id IN (SELECT * FROM cb_merchant_id);
-- 商户账户表
DELETE FROM cb_merchant_accout WHERE merchant_id IN (SELECT * FROM cb_merchant_id);
-- 商户类型关联
DELETE from cb_merchant_type_relation WHERE merchant_id IN (SELECT * FROM cb_merchant_id);
-- 商户表
DELETE from cb_merchant where name LIKE '%方付通%';


UPDATE cb_merchant SET audit_state=1 WHERE id=100000001;

UPDATE cb_merchant_outlet SET latitude=longitude,longitude=latitude WHERE latitude > 90 AND longitude <90;

UPDATE cb_merchant_outlet SET latitude=90 WHERE latitude > 90;

UPDATE cb_merchant_outlet SET longitude=180 WHERE longitude > 180;

-- 删除部门门店
DELETE FROM cb_merchant_outlet WHERE id IN(
	SELECT merchant_outlet_id FROM cb_merchant_outlet_bank WHERE org_name LIKE '%部' AND org_name NOT LIKE '%营业部' 
);

--删除无效的商品
delete FROM cb_product WHERE id NOT IN (SELECT product_id FROM cb_product_group) AND id NOT IN(SELECT product_id FROM cb_product_presell) AND TYPE<>'03';

DELETE FROM cb_payment WHERE order_id IN(SELECT order_id FROM cb_order_details WHERE product_id IN(SELECT  product_id FROM cb_product_presell));

DELETE FROM cb_order_refunds WHERE order_sn IN(SELECT sn FROM cb_order WHERE id IN(SELECT order_id FROM cb_order_details WHERE product_id IN(SELECT  product_id FROM cb_product_presell)));

DELETE FROM cb_ticket WHERE order_id IN(SELECT order_id FROM cb_order_details WHERE product_id IN(SELECT  product_id FROM cb_product_presell));

DELETE FROM cb_order WHERE id IN(SELECT order_id FROM cb_order_details WHERE product_id IN(SELECT  product_id FROM cb_product_presell));

DELETE FROM cb_order_details WHERE product_id IN(SELECT  product_id FROM cb_product_presell);

DELETE FROM cb_product_limit WHERE product_id IN(SELECT  product_id FROM cb_product_presell);

DELETE FROM cb_product_comment WHERE product_id IN(SELECT  product_id FROM cb_product_presell);

DELETE FROM cb_product_image WHERE product_id IN(SELECT  product_id FROM cb_product_presell);


DELETE FROM cb_product WHERE id IN (SELECT  product_id FROM cb_product_presell);

DELETE FROM cb_product_presell;
