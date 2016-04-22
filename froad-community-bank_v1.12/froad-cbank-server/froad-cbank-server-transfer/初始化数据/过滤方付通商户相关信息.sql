DROP VIEW if exists `fft_merchant_id`;
CREATE VIEW `fft_merchant_id` AS SELECT
	fft_merchant.id
FROM
	fft_merchant
WHERE
	fft_merchant.`name` LIKE '%����ͨ%'  OR lattice_point = '1000_3401010009';


DELETE from fft_area WHERE NAME ='������';

-- =========�������=========
-- ȯ
DELETE from fft_trans_security_ticket where merchant_id in (SELECT * from fft_merchant_id);

-- ����
DELETE from fft_shipping where transId in (select id from fft_trans where merchant_id in (SELECT * from fft_merchant_id));

-- ֧��
DELETE from fft_pay where trans_id in (select id from fft_trans where merchant_id in (SELECT * from fft_merchant_id));

DELETE from fft_pay where sn in (select pay_sn FROM fft_trans_refunds WHERE trans_sn IN (SELECT sn FROM fft_trans WHERE merchant_id IN (SELECT * FROM fft_merchant_id)));

-- �˿�
DELETE FROM fft_trans_refunds WHERE trans_sn IN (SELECT sn FROM fft_trans WHERE merchant_id IN (SELECT * FROM fft_merchant_id));

-- ����
DELETE from fft_trans where merchant_id in (SELECT * from fft_merchant_id);


-- =========��Ʒ���=========
-- ��Ʒ�ղ�
DELETE from fft_product_favorite where product_id in (SELECT id from fft_product where merchant_id in (SELECT * from fft_merchant_id));

-- ��Ʒ����
DELETE from fft_product_comment where product_id in (SELECT id from fft_product where merchant_id in (SELECT * from fft_merchant_id));

-- ��ƷͼƬ
DELETE from fft_product_image where product_id in (SELECT id from fft_product where merchant_id in (SELECT * from fft_merchant_id));

-- Ԥ�����������Ϣ
DELETE from fft_merchant_outlet where id in (SELECT merchant_outlet_id from fft_outlet_presell_delivery where presell_delivery_id in (SELECT presell_delivery_id from fft_product_presell_delivery where product_presell_id in (SELECT product_presell_id FROM fft_product where merchant_id in (SELECT * from fft_merchant_id))));

-- Ԥ����������м��
DELETE from fft_outlet_presell_delivery where presell_delivery_id in (SELECT presell_delivery_id from fft_product_presell_delivery where product_presell_id in (SELECT product_presell_id FROM fft_product where merchant_id in (SELECT * from fft_merchant_id)));

-- Ԥ����Ʒ�����
DELETE from fft_presell_delivery WHERE id in (SELECT presell_delivery_id from fft_product_presell_delivery where product_presell_id in (SELECT product_presell_id FROM fft_product where merchant_id in (SELECT * from fft_merchant_id)));

-- Ԥ����Ʒ����м��
DELETE from	fft_product_presell_delivery where product_presell_id in (SELECT t2.id FROM fft_product t1 join fft_product_presell t2 on t1.product_presell_id=t2.id where t1.merchant_id in (SELECT * from fft_merchant_id));

-- �ݹ���Ʒ��Ӧ����������Ϣ��
DELETE from fft_product_delivery where product_id in (SELECT id from fft_product WHERE merchant_id in (SELECT * from fft_merchant_id));

-- Ԥ����Ʒ
DELETE FROM fft_product_presell where id in (SELECT product_presell_id from fft_product where merchant_id in (SELECT * from fft_merchant_id));

-- �����ػ�
DELETE from fft_product_famous where id in (SELECT product_famous_id from fft_product where merchant_id in (SELECT * from fft_merchant_id));

-- ���ֶһ�
DELETE from fft_product_exchange where product_id in (SELECT id from fft_product where merchant_id in (SELECT * from fft_merchant_id));

-- �Ź���Ʒ
DELETE from fft_product_group where id in (SELECT product_group_id FROM fft_product where merchant_id in (SELECT * from fft_merchant_id));

-- ��Ʒ��
DELETE from fft_product where merchant_id in (SELECT * from fft_merchant_id);


-- =========�ŵ�=========
-- �̻�/�ֵ�/���л�������
DELETE from fft_outlet_bank where outle_id in (SELECT id from fft_merchant_outlet where merchant_id in (SELECT * from fft_merchant_id));

-- �̻��ŵ�ͼƬ
DELETE from fft_merchant_photo where merchant_outlet_id in (SELECT id from fft_merchant_outlet where merchant_id in (SELECT * from fft_merchant_id));

-- �ŵ��
DELETE from fft_merchant_outlet where merchant_id in (SELECT * from fft_merchant_id);

-- =========�̻��û�=========
-- �̻��û���ɫ����
DELETE from fft_sys_user_role where user_id in (SELECT sys_user_id from fft_merchant_group_user where merchant_id in (SELECT * from fft_merchant_id));

-- �̻��û�
DELETE from fft_sys_user where id in (SELECT sys_user_id from fft_merchant_group_user where merchant_id in (SELECT * from fft_merchant_id));

-- �̻��û���
DELETE from fft_merchant_group_user where merchant_id in (SELECT * from fft_merchant_id);

-- =========�̻�=========
-- �̻��ղ�
DELETE from fft_merchant_favorite where merchant_id in (SELECT * from fft_merchant_id);

-- �̻��̻��ظ�
DELETE from fft_merchant_product_comment where merchant_id in (SELECT * from fft_merchant_id);

-- �̻��˻���
DELETE from fft_merchant_account where merchant_id in (SELECT * from fft_merchant_id);

-- �̻����͹���
DELETE from fft_merchant_type_relation where merchant_id in (SELECT * from fft_merchant_id);

-- �̻���
DELETE from fft_merchant where name LIKE '%����ͨ%' OR lattice_point = '1000_3401010009';




UPDATE fft_merchant_outlet o INNER JOIN fft_outlet_bank b ON o.`id`= b.`outle_id` AND o.`merchant_id`=b.`merchat_id` SET o.`name`=b.`org_name`,o.`full_name`=b.`org_name` WHERE b.`org_name` <> o.`name`;
UPDATE froadfft_bps_pro.`tr_bank_organization` tbo INNER JOIN froadfft_ah_pro.`fft_outlet_bank` fob ON tbo.org_code=fob.bank_org SET tbo.org_name=fob.org_name WHERE tbo.org_name<>fob.org_name;