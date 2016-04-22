/**
 * 文章表设计
 */
DROP TABLE IF EXISTS froadfft.fft_article;
CREATE TABLE froadfft.fft_article (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',  
	title varchar(255) DEFAULT NULL COMMENT '标题',
	author varchar(255) DEFAULT NULL COMMENT '作者',
	content text DEFAULT NULL COMMENT '内容',
	seo_title varchar(255) DEFAULT NULL COMMENT '页面标题',
	seo_keywords varchar(255) DEFAULT NULL COMMENT '页面关键词',
	seo_description text DEFAULT NULL COMMENT '页面描述',
	is_publication bit(1) NOT NULL COMMENT '是否发布',
	is_top bit(1) NOT NULL COMMENT '是否置顶',
	is_recommend bit(1) NOT NULL COMMENT '是否为推荐文章',
	page_count int(11) DEFAULT 1 COMMENT '文章页数',
	html_path varchar(255) DEFAULT '' COMMENT '静态HTML路径',
	hits int(11) DEFAULT 0 COMMENT '点击次数',
	article_category_id bigint(20) NOT NULL COMMENT '文章分类ID',
	client_id bigint(20) NOT NULL COMMENT '客户端编号',
	data_state int(11) NOT NULL COMMENT '基础数据状态',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='文章';
