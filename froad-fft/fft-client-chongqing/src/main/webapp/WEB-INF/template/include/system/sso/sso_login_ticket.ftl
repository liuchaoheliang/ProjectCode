<script type="text/javascript">
	$(function() {
		flushLoginTicket();
	});
	var flushLoginTicket = function() {
		var _services = 'service=' + encodeURIComponent('${LOCATION_URL}${base}');
		var urlOfGetLt = '${SSO_SERVER_URL}login?' + _services + '&get-lt=true&n=' + new Date().getTime();
		$.ajax({
			type : "get",
			async : false,
			url : urlOfGetLt,
			dataType : "jsonp",
			jsonp : "callback",
			jsonpCallback : "flightHandler",
			success : function(json) {
				$("#lt").val(json.lt);
				$("#execution").val(json.execution);
			},
			error : function() {
				layer.msg('系统繁忙，请稍后再试',2,-1);
			}
		});
	};
	var flightHandler = function(data){
		$("#lt").val(data.lt);
		$("#execution").val(data.execution);
	};
</script>