	/* 搜索 */
	function search(){
		var searchWord=$.trim($("#searchWord").val());//searchWord
		if(searchWord != null && searchWord != '' && searchWord != '请输入商家所属地区或行业类别关键字'){
			var testttt = escapeHtml($.trim($("#searchWord").val()));
			$("#tagValue").val(testttt);
		}
		$("#merchantPriority").val('');
		$("#preferentialType").val('');
		$("#tagClassifyAId").val('');
		$("#tagDistrictBId").val('');
		$("#searchForm").attr('action',"searchIndex.action");
		$("#searchForm").submit();
	}

	/* 商家搜索  优惠类型、商圈、分类 */
	function searchMerchantIndex(type){
		var preferentialType = type;//优惠类型
		var selectTagDistrictBId = $.trim($("#selectTagDistrictBId").val());//二级商圈ID
		var selectTagClassifyAId = $.trim($("#selectTagClassifyAId").val());//一级类目ID
		if(preferentialType != null && preferentialType != '' && preferentialType != undefined){
			$("#preferentialType").val(preferentialType);
		}
		if(selectTagClassifyAId != null && selectTagClassifyAId != ''  && selectTagClassifyAId != undefined){
			$("#tagClassifyAId").val(selectTagClassifyAId);
		}
		if(selectTagDistrictBId != null && selectTagDistrictBId != '' && selectTagDistrictBId != undefined){
			$("#tagDistrictBId").val(selectTagDistrictBId);
		}
		if(type == 1){
			$("#searchForm").attr('action',"search_preferential_merchants.action");			
		}else if(type == ''){
			$("#searchForm").attr('action',"searchIndex.action");
		}else{
			$("#searchForm").attr('action',"search_rebate_merchants.action");
		}
		$("#searchForm").submit();
	}