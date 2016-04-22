<script type="text/javascript">
$( function() {


	$("#pager").pager({
		pagenumber: ${pager.pageNumber?c},
		pagecount: ${pager.pageCount?c},	
		totalcount: ${pager.totalCount?c},
		buttonClickCallback: $.gotoPage
	});

})
</script>
<span id="pager"></span>
<input type="hidden" name="pager.pageNumber" id="pageNumber" value="${pager.pageNumber?c}" />
<input type="hidden" name="pager.orderBy" id="orderBy" value="${pager.orderBy}" />
<input type="hidden" name="pager.orderType" id="order" value="${pager.orderType}" />
<input type="hidden" name="pager.totalCount" id="totalCount" value="${pager.totalCount?c}" />