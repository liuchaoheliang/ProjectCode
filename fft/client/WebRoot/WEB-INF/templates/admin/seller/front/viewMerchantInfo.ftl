<#if merchantInfoMap.merchant?exists && merchantInfoMap.merchant.mstoreFullName.pointRate?exists>#{merchantInfoMap.merchant.mstoreFullName?html}</#if></br>
<#if merchantInfoMap.merchant?exists && merchantInfoMap.merchant.mstoreAddress?exists>#{merchantInfoMap.merchant.mstoreAddress?html}</#if></br>
<#list merchantInfoMap.merchantPreferentialList as merchantPreferential>
${merchantPreferential.photoUrl}
</#list>