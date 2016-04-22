/**
 * 彩票表设计
 */
DROP TABLE IF EXISTS froadfft.fft_lottery;
CREATE TABLE froadfft.fft_lottery (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间', 
	trans_id bigint(20) NOT NULL COMMENT '交易ID',
	period varchar(255) NOT NULL COMMENT '投注期号',  
	buy_count int(11) NOT NULL COMMENT '投注注数', 
	content varchar(255) NOT NULL COMMENT '投注内容',
	lottery_no varchar(255) NOT NULL COMMENT '彩票编码',
	play_type varchar(255) COMMENT '玩法',
	buyamount int(11) NOT NULL COMMENT '投注倍数',
	amount varchar(255) NOT NULL COMMENT '投注金额',
	prize_grade varchar(255) COMMENT '中奖等级',
	awd_amount varchar(255) COMMENT '中奖金额',
	prize_count int(11) COMMENT '中奖注数',
	num_type varchar(255) COMMENT '单复和合胆',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='彩票';
