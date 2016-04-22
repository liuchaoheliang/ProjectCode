/*
 * 
 * JavaScript - lSelect
 * Version: 3.0
 */

(function($) {
	$.fn.extend({
		lSelect: function(options) {
			var settings = {
				choose: "请选择...",
				emptyValue: "",
				cssStyle: {"margin-right": "4px"},
				url: null,
				sort:false,
				sortFunc:undefined,
				sortSeparator:"-",
				change:null,
				type: "GET"
			};
			$.extend(settings, options);
			
			if(settings.url.indexOf("/area") != -1) {
				settings.sort = true;
			}
			
			var cache = {};
			return this.each(function() {
				var $input = $(this);
				var id = $input.val();
				var treePath = $input.attr("treePath");
				var selectName = $input.attr("name") + "_select";
				
				if (treePath != null && treePath != "") {
					var ids = (treePath + id + ",").split(",");
					var $position = $input;
					for (var i = 1; i < ids.length; i ++) {
						$position = addSelect($position, ids[i - 1], ids[i]);
					}
				} else {
					addSelect($input, null, null);
				}
				
				function addSelect($position, parentId, currentId) {
					$position.nextAll("select[name=" + selectName + "]").remove();
					if ($position.is("select") && (parentId == null || parentId == "")) {
						return false;
					}
					if (cache[parentId] == null) {
						var url = settings.url;
						if(parentId != null){
							url+="?parentId="+parentId;
						}
						$.ajax({
							url: 'open_o2oarea_client.action',
							type: "POST",
							data: {'url':url},
							dataType: "json",
							cache: false,
							async: false,
							success: function(data) {
								cache[parentId] = data;
							}
						});
					}
					var data = cache[parentId];
					if ($.isEmptyObject(data)) {
						return false;
					}
					var select = '<select name="' + selectName + '">';
					if (settings.emptyValue != null && settings.choose != null) {
						select += '<option value="' + settings.emptyValue + '">' + settings.choose + '</option>';
					}
					
					var array = new Array();
					
					$.each(data, function(value, name) {
						
						var key = settings.sort ? sortKey(name) : "1";
						var name = settings.sort ? sortName(name) : name;
						
						if(value == currentId) {
							array.push(new Array(key,'<option value="' + value + '" selected="selected">' + name + '</option>'));
						} else {
							array.push(new Array(key,'<option value="' + value + '">' + name + '</option>'));
						}
					});
					
					if(settings.sort){ 
						array.sort(settings.sortFunc ? settings.sortFunc : sortFunc);
					}
					
					for(var i in array){
						select += array[i][1];
					}
					
					select += '</select>';
					return $(select).css(settings.cssStyle).insertAfter($position).bind("change", function() {
						var $this = $(this);
						if ($this.val() == "") {
							var $prev = $this.prev("select[name=" + selectName + "]");
							if ($prev.size() > 0) {
								$input.val($prev.val());
							} else {
								$input.val(settings.emptyValue);
							}
						} else {
							$input.val($this.val());
						}
						if(settings.change) {
							settings.change.call(this,$this.val());
						}
						addSelect($this, $this.val(), null);
					});
				}
				function sortKey(name){
					return name.split(settings.sortSeparator)[0];
				}
				function sortName(name){
					return name.split(settings.sortSeparator)[1];
				}
				function sortFunc(x,y) {
					 return x[0] - y[0];
				}
			});
			
		}
	});
})(jQuery);