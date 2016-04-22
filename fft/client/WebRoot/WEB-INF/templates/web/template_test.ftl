<html>
<head>
</head>

<body>
<form id="inputForm" action="template_index_change_update.action" method="post">
	<input type="hidden" class="formText" name="dynamicConfig.name" value="indexChange" />
		
	<textarea name="templateFileContent" style="width: 100%; height: 500px; padding: 0px;">${(templateFileContent)!""}</textarea> 
	<input type="submit" value="确  定" hidefocus="true" />
				
</form>
</body>
</html>