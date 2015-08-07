<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="js/jquery/jquery-1.4.4.min.js"></script>

<script type="text/javascript">

</script>

</head>

<body>
<div class="innercontenttitle">
	资源管理>>无线及固网站点管理
</div>
<div class="margin_4">
  <div id="impDialog" >
		<form name="fenceForm" id="fenceForm" action="<%=basePath%>fence/batchImportFence" method="post" enctype="multipart/form-data">
			<input type="file" class="input" name="fence" />
			<input type="submit"/>
		</form>
	</div>
	<div id="zoneDialog" >
		<form name="zoneForm" id="zoneForm" action="<%=basePath%>zone/batchImportZone" method="post" enctype="multipart/form-data">
			<input type="file" class="input" name="zone" />
			<input type="submit"/>
		</form>
	</div>
</div>
</body>
</html>
