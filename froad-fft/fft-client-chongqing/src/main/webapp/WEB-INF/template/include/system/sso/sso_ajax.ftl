<script type="text/javascript">
$(window).load(function(){
	var O2OBILL = O2OBILL||{};
	O2OBILL.ajax = function(o){
		if(!parent||!parent.document.getElementById('sso_hidden_iframe')){
			$('<iframe src="'+o.url+'" style="display:none;border:0;margin:0;padding:0;" id="sso_hidden_iframe" width="0" height="0"></iframe>').appendTo(document.body);
			$('#sso_hidden_iframe').bind('load',function(){
				$.ajax({
				    url:o.url,
				    global: false,
				    dataType:o.dataType||'html',
				    method:o.method||'POST',
				    success:function(res){
				       if(typeof o.success == 'function'){
				    	   o.success(res);
				       }
				    },
				    error: function(XMLHttpRequest, textStatus, errorThrown) { 
				    	if(typeof o.unloginFn == 'function'){
					    	o.unloginFn(XMLHttpRequest, textStatus, errorThrown);
					    }
				    }
				});
			});
		}
	}
	O2OBILL.ajax({
		url:'${base}/sso/core/support.jhtml?random=' + Math.random(),
		unloginFn:function(a,b,c){},
	    dataType:'html',
	    method:'POST',
	    success:function(res){
	    	$.Ajax({
	    		url : '${base}/sso/core/query_info.jhtml',
	    		useErrorMsg : false,
	    		useRandom : true,
	    		successFn : function (data){
	    			if(data.flag){
	    				window.location.reload();
	    			}
	    		}
	    	});
	    }
	});    
});
</script>