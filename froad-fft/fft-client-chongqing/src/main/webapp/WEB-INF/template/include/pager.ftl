<script src="${base}/resources/common/js/pager.js"></script>
<script src="${base}/resources/common/js/list.js"></script>
<link href="${base}/resources/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$( function() {


	$('#pager').pager({
		pagenumber: ${page.pageNumber?c},
		pagecount: ${page.pageCount?c},	
		totalcount: ${page.totalCount?c},
		buttonClickCallback: $.gotoPage
	});

})
</script>
<span id="pager"></span>
<input type="hidden" name="pageNumber" id="pageNumber" value="${page.pageNumber?c}" />
<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}" />
<input type="hidden" name="orderType" id="order" value="${page.orderType}" />
<input type="hidden" name="totalCount" id="totalCount" value="${page.totalCount?c}" />