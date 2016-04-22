<requestFroadApi>
	<orderDetails>
	<#list orderDetails as detail>
	<orderDetail>
			<orderID>${detail.orderID!''}</orderID>
			<orderAmount>${detail.orderAmount!''}</orderAmount>
			<orderSupplier>${detail.orderSupplier!''}</orderSupplier>
			<orderDisplay><![CDATA[${detail.orderDisplay!''}]]></orderDisplay>
		</orderDetail>
		</#list>
	</orderDetails>
	<order>
		<orderType>${orderType}</orderType>
		<totalAmount>${totalAmount}</totalAmount>
		<orderCurrency>${orderCurrency}</orderCurrency>
		<orderSubmitTime>${orderSubmitTime}</orderSubmitTime>
		<orderFailureTime/>
		<orderRemark><![CDATA[${orderRemark!""}]]></orderRemark>
		<orderDisplay><![CDATA[${orderDisplay}]]></orderDisplay>
	</order>
	<pay>
		<payType>${payType}</payType>
		<mobileToken>${mobileToken!""}</mobileToken>
		<fftSignNo/>
		<payOrg>${payOrg}</payOrg>
		<payerMember>${payerMember}</payerMember>
		<payerIdentity>${payerIdentity!}</payerIdentity>
		<payeeMember>${payeeMember!}</payeeMember>
		<payeeIdentity/>
		<payDirect>${payDirect}</payDirect>
	</pay>
	<notification>
		<noticeUrl>${noticeUrl!}</noticeUrl>
		<returnUrl>${returnUrl!}</returnUrl>
	</notification>
	<system>
		<reqID>${reqID}</reqID>
		<version>${versions}</version>
		<partnerID>${partnerID}</partnerID>
		<charset>${charset}</charset>
		<signType>${signType}</signType>
		<client>${client}</client>
		<signMsg>${signMsg}</signMsg>
	</system>
</requestFroadApi>