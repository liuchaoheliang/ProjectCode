package com.froad.db.mysql.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.MerchantType;

public interface MerchantTypeMapper {
	
	List<MerchantType> findAllTypeInfo(@Param("clientId")String clientId);
}
