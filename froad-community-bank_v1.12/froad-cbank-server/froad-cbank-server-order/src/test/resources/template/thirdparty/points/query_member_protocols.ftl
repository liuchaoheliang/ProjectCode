<requestQueryProtocolApi>
	<queryParams>
		<orgNo>${orgNo!""}</orgNo>
		<protocolNo>${protocolNo!""}</protocolNo>
		<protocolType>${protocolType!""}</protocolType>
		<fromTime>${fromTime!""}</fromTime>
		<toTime>${toTime!""}</toTime>
		<#if pageSize?exists>
		<pageSize>${pageSize!"10"}</pageSize>
		<pageNum>${pageNum!"1"}</pageNum>
		</#if>
	</queryParams>
	
	<partnerAccount >
		<accountMarked>${accountMarked!""}</accountMarked>
		<accountMarkedType>${accountMarkedType!""}</accountMarkedType>
	</partnerAccount >
	
	<system>
		<partnerNo>${partnerNo!""}</partnerNo>
		<charset>${charset!""}</charset>
		<signType>${signType!""}</signType>
		<signMsg>${signMsg!""}</signMsg>
		<requestTime>${requestTime!""}</requestTime>
	</system>
</requestQueryProtocolApi>