/***
* FQ
 **/

$(function() {

		var $listForm = $("#listForm");// 列表表单
		var $searchButton = $("#searchButton");// 查询按钮
		var $pageNumber = $("#pageNumber");// 当前页码
		var $totalCount = $("#totalCount");// 总记录数
		var $pageSize = $("#pageSize");// 每页显示数
		
		// 查找
		$searchButton.click(function() {
			$pageNumber.val("1");
			$listForm.submit();
		});

		// 每页显示数
		$pageSize.change(function() {
			$pageNumber.val("1");
			$listForm.submit();
		});

		// 页码跳转
		$.gotoPage = function(id) {
			$pageNumber.val(id);
			$listForm.submit();
		}

	});