/**
 * 文章分类设计
 */
DROP TABLE IF EXISTS froadfft.fft_article_category;
CREATE TABLE froadfft.fft_article_category (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',  
	name varchar(255) NOT NULL COMMENT '分类名称',
	seo_title varchar(255) NOT NULL COMMENT '页面标题',
	seo_keywords varchar(255) DEFAULT NULL COMMENT '页面关键字',
	seo_description text NOT NULL COMMENT '页面描述',
	order_value int(11) DEFAULT 0 COMMENT '排序',
	tree_path varchar(255) NOT NULL COMMENT '树路劲',
	parent_id bigint(20) COMMENT '上级分类ID',
	data_state int(11) NOT NULL COMMENT '基础数据状态',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='文章分类';
