DROP VIEW IF EXISTS `cb_merchant_id`;
CREATE VIEW `cb_merchant_id` AS 

SELECT
	cb_merchant.id
FROM
	cb_merchant
WHERE
	cb_merchant.`name` LIKE '%����ͨ%';
	
-- =========�������=========
-- ȯ
delete FROM cb_ticket WHERE merchant_id IN (SELECT * FROM cb_merchant_id);
-- ����
delete FROM cb_shipping WHERE order_id IN (SELECT id FROM cb_order WHERE merchant_id IN (SELECT * FROM cb_merchant_id));
-- �˿�
DELETE FROM cb_order_refunds WHERE order_sn IN (SELECT sn FROM cb_order WHERE merchant_id IN (SELECT * FROM cb_merchant_id));
-- ����
DELETE from cb_order where merchant_id in (SELECT * from cb_merchant_id);

-- =========��Ʒ���=========
-- ��Ʒ�ղ�
DELETE from cb_product_favorite where product_id in (SELECT id from cb_product where merchant_id in (SELECT * from cb_merchant_id));

-- ��Ʒ����
DELETE from cb_product_comment where product_id in (SELECT id from cb_product where merchant_id in (SELECT * from cb_merchant_id));

-- ��ƷͼƬ
DELETE from cb_product_image where product_id in (SELECT id from cb_product where merchant_id in (SELECT * from cb_merchant_id));
-- �Ź���Ʒ
delete FROM cb_product_group WHERE product_id IN (SELECT id FROM cb_product WHERE merchant_id IN(SELECT id FROM cb_merchant_id));
-- Ԥ����Ʒ
delete FROM cb_product_presell WHERE product_id IN (SELECT id FROM cb_product WHERE merchant_id IN(SELECT id FROM cb_merchant_id));
-- ��Ʒ��
DELETE from cb_product where merchant_id in (SELECT * from cb_merchant_id);

-- =========�ŵ�=========
-- �ŵ����
delete FROM cb_merchant_outlet_photo WHERE merchant_id IN (SELECT id FROM cb_merchant_id);
-- �ŵ��
delete FROM cb_merchant_outlet WHERE merchant_id IN (SELECT id FROM cb_merchant_id);

-- =========�̻��û�=========
-- �����û���ɫ
delete FROM cb_merchant_user_role 
	WHERE merchant_user_id IN (SELECT id FROM cb_merchant_user 
	WHERE id IN (SELECT merchant_user_id FROM cb_merchant_group_user WHERE merchant_id IN(SELECT id FROM cb_merchant_id)));
-- �̻��û�
delete FROM cb_merchant_user WHERE id IN (SELECT merchant_user_id FROM cb_merchant_group_user WHERE merchant_id IN(SELECT id FROM cb_merchant_id));
-- �̻��û���
delete FROM cb_merchant_group_user WHERE merchant_id IN(SELECT id FROM cb_merchant_id);

-- �ŵ��ղ�
delete FROM cb_merchant_outlet_favorite WHERE merchant_id IN (SELECT id FROM cb_merchant_id);

-- �ŵ��ղ�
delete FROM cb_merchant_outlet_favorite WHERE merchant_id IN (SELECT id FROM cb_merchant_id);
-- �̻��̻��ظ�
DELETE from cb_merchant_product_comment WHERE merchant_id IN (SELECT * FROM cb_merchant_id);
-- �̻��˻���
DELETE FROM cb_merchant_accout WHERE merchant_id IN (SELECT * FROM cb_merchant_id);
-- �̻����͹���
DELETE from cb_merchant_type_relation WHERE merchant_id IN (SELECT * FROM cb_merchant_id);
-- �̻���
DELETE from cb_merchant where name LIKE '%����ͨ%';


UPDATE cb_merchant SET audit_state=1 WHERE id=100000001;

UPDATE cb_merchant_outlet SET latitude=longitude,longitude=latitude WHERE latitude > 90 AND longitude <90;

UPDATE cb_merchant_outlet SET latitude=90 WHERE latitude > 90;

UPDATE cb_merchant_outlet SET longitude=180 WHERE longitude > 180;

-- ɾ�������ŵ�
DELETE FROM cb_merchant_outlet WHERE id IN(
	SELECT merchant_outlet_id FROM cb_merchant_outlet_bank WHERE org_name LIKE '%��' AND org_name NOT LIKE '%Ӫҵ��' 
);

--ɾ����Ч����Ʒ
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
