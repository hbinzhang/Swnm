<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="../include.inc.jsp"%>
<%@ page import="net.sf.json.*"%>
<%@ page import="java.util.*"%>
<%
	Map<String,List<Map<String, String>>> mtsb = (Map<String,List<Map<String, String>>>)request.getAttribute("managementagencies");
	Map<String,String> mts = new HashMap<String,String>();
	for(List<Map<String, String>> va : mtsb.values()){
		for(Map<String, String> v : va){
			String key = v.get("id");
			String value = v.get("name");
			mts.put(key,value);
		}
	}
	
	Map<String,String> branchkv = new HashMap<String,String>();
	request.setAttribute("mts", mts);
	List<Map<String, String>> branchlist = (List<Map<String, String>>) request.getAttribute("branchs");
	for(Map<String, String> map:branchlist){
		branchkv.put(map.get("id"), map.get("name"));
	}
	request.setAttribute("branchkv", branchkv);
	
	String jsonmt = JSONObject.fromObject(mtsb).toString();
	String searchManagement = (String)request.getAttribute("searchManagement");
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>安防综合监控系统</title>
<link href="<c:url value='/resources/images/favicon.ico'></c:url>"
	rel="icon" type="image/x-icon" />
<link href="<c:url value='/resources/images/favicon.ico'></c:url>"
	rel="shortcut icon" type="image/x-icon" />
<link href="${pageContext.request.contextPath}/resources/css/common.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/style.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/popModal.min.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.form.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/yinpin_search.js"
	type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/shebei.js"></script>
<script type="text/javascript">
	$(function(){
		$('#normal_gongsi_select').change(function(){
			reloadMT($(this));
		});
		
		reloadMT($('#normal_gongsi_select'));
		
		/* $('#filediv').mouseleave(function(d){
			$(this).hide();
		}); */
		
		$('#showimport').click(function(d){
			$('#filediv').show();
		});
		
		$('#showcancel').click(function(d){
			$('#filediv').hide();
			$('#ivafile').val('');
		});
		
		$('#ivaimport').click(function(d){
			var file = $('#ivafile').val();
			if(!file){
				alert('请选择文件再上传');
				return;
			}
			$('#data_loading').show();
			$('#ivafileform').ajaxSubmit({
				success:function(d,e){
					$('#data_loading').hide();
					alert('导入成功！');
					var location = (window.location+'').split('/'); 
					var basePath = location[0]+'//'+location[2]+'/'+location[3];
					window.location = basePath + '/videomonitor/loadIva.action';
				},
				error:function(d,e){
					$('#data_loading').hide();
					alert('导入失败！');
				}
			});
			$('#filediv').hide();
			$('#ivafile').val('');
		});
	});
	function reloadMT(branch){
			var branch = branch.children('option:selected').val();
			if(branch == -1){
				$('#normal_guanlichu_select').children('option:eq(0)')[0].selected = 'selected';
				return;
			}
			$('#normal_guanlichu_select').children('option:gt(0)').remove();
			var jmt = eval("("+'<%=jsonmt%>'+")");
			var mts = jmt[branch];
			if(mts){
			var length = mts.length;
			for(var i = 0 ; i < length;i++){
				var selected = "";
				if('<%=searchManagement%>' == mts[i].id) {
					selected = "selected='selected'";
				}
				$('#normal_guanlichu_select').append(
						"<option value='"+mts[i].id+"' "+selected+">"
								+ mts[i].name + "</option>");
			}
		}
	}
	
	function getpageno(isup){
		var pageNo = '${ pageNo }';
		var pageSize = '${pageSize}';
		var rowCount = parseInt('${rowCount}');
		var pageCount = Math.ceil(rowCount / pageSize);
		pageNo = parseInt(pageNo) + (isup ? -1 : 1);
		pageNo = pageNo > pageCount ? pageCount : pageNo;
		pageNo = pageNo < 1 ? 1 : pageNo;
		return pageNo;
	}
	function dopageup(){
		window.location = '<%=basePath%>videomonitor/loadIva.action?pageNo=' + getpageno(true);
	}
	function dopagedown(){
		window.location = '<%=basePath%>videomonitor/loadIva.action?pageNo=' + getpageno(false);
	}
	function doskip(){
		var topageno = $('#itopage').val();
		if(!topageno || isNaN(topageno)){
			alert('请输入正确的页码');
			return;
		}
		var pn = 0;
		pn = parseInt(topageno);
		var pageNo = '${ pageNo }';
		var pageSize = '${pageSize}';
		var rowCount = parseInt('${rowCount}');
		var pageCount = Math.ceil(rowCount / pageSize);
		window.location = '<%=basePath%>videomonitor/loadIva.action?pageNo=' + (pn < 1 ? 1 : (pn > pageCount ? pageCount : pn));
	}
</script>
</head>
<body>
	<c:import url="../layout/header.jsp?navLink=shebeiguanli" charEncoding="UTF-8"></c:import>
	<div class="normalajaxloading" id="data_loading"></div>
	<div id="main_content_container">
		<div id="nav_place">
			<div id="title">位置：</div>
			<ul>
				<li><a href="<c:url value="/"></c:url>">首页</a>
				</li>
				<li><a href="<c:url value="<%=basePath %>videomonitor/loadIpc.action"></c:url>">设备管理</a>
				</li>
				<li><a href="<%=basePath %>videomonitor/loadIva.action">智能视频设备</a>
				</li>
			</ul>
		</div>
		<div id="content_container">
			<c:import url="../layout/shebeitab.jsp?sTabLink=iva"
				charEncoding="UTF-8"></c:import>
			<div class="search_container">
				<div class="left" style="margin-top:5px;">
					<form id="ivasearchform" action="<%=basePath %>videomonitor/loadIva.action" method="post">
					<div class="single_filter_container left">
						<label class="search_name">分公司:</label>
						<div class="left single_filter">
							<select id="normal_gongsi_select" class="normalselect" name="searchBranch">
								<option value="-1">全部</option>
									<c:forEach items="${ branchs }" var="vbranch">
										<option value="${vbranch['id']}" <c:if test="${searchBranch == vbranch['id'] }">selected="selected"</c:if>>${vbranch['name'] }</option>
									</c:forEach>
							</select>
						</div>
					</div>
					<div class="single_filter_container left">
						<label class="search_name">管理处:</label>
						<div class="left single_filter">
							<select id="normal_guanlichu_select" class="normalselect" name="searchManagement">
								<option value="-1">全部</option>
							</select>
						</div>
					</div>
					<div class="single_filter_container left" style="width:500px;">
						<label class="search_name">附加条件:</label> <select
							class="normalselect" id="yinpin_search_filter" name="searchAttachType">
							<option value="1" <c:if test="${ searchAttachType == 1 }">selected="selected"</c:if>>ID</option>
							<option value="2" <c:if test="${ searchAttachType == 2 }">selected="selected"</c:if>>IP</option>
							<option value="3" <c:if test="${ searchAttachType == 3 }">selected="selected"</c:if>>名称</option>
						</select> <input type="text" class="normaltext" name="searchAttachValue" value="${ searchAttachValue }"
							id="yingpininput" style="width:300px;"> 
					</div>
					<div class="clear"></div>
					</form>
				</div>
				<div class="left" style="margin-top:5px;">
					<input id="ivasearch" type="submit" class="btn" value="查询"
						style="margin-left:10px;"> 
						<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'添加智能视频设备') }">
						<input type="button"
						class="btn" value="添加" id="iva_addbtn"
						style="margin-left:10px;">
						</c:if>
						 <input type="button"
						class="btn" value="批量导入" style="margin-left:10px;" id="showimport">
						<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'删除智能视频设备') }">
						<input type="button" class="btn" value="删除" style="margin-left:10px;" id="ivadelete">
						</c:if>
						
						<div style="position:absolute;border:lightblue solid 1px;background:white;width:280px;height:100px;display:block;display:none;background:white;margin-left:280px;" id="filediv">
							<form id="ivafileform" action="<%=basePath%>videomonitor/batchImportIva.action" enctype="multipart/form-data" method="post">
							<input type="file" name="iva" id="ivafile" style="float:left;margin:20px 30px 10px 30px;"/>
							<input id="ivaimport" type="button" value="导入" class="btn" style="float:left;margin:2px 10px 2px 30px;"/>
							<input id="showcancel" type="button" value="取消" class="btn" style="float:left;margin:2px 30px 2px 10px;"/>
							</form>
						</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="tableui_container">
				<table class="tableui" id="tivas">
					<thead>
						<tr>
							<th width="2%">
					    		<input type="checkbox" id="iva_select_all"/>
					    	</th>
							<th><span>设备编号</span></th>
							<th><span>设备名称</span></th>
							<th><span>IP</span></th>
							<th><span>PORT</span></th>
							<th><span>所属分公司</span></th>
							<th><span>所属管理处</span></th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator var="iva" value="ivas" status="st">
							<tr>
								<!--pcy <td style="display:none;"><s:property value="decoderid" /></td> -->
								<td align="center">
									<input type="checkbox" class="ivaselected"/>
								</td>
								<td><s:property value="ivaID" /></td>
								<td><s:property value="name" /></td>
								<td><s:property value="ip" /></td>
								<td><s:property value="port" /></td>
								<td>${ branchkv[branch] }</td>
								<td>${ mts[managementagency] }</td>
								<td align="center">
								<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'修改智能视频设备') }">
								<a href="javascript:void(0);" title="编辑"
									class="edit_iva editlink"> 编辑 </a>
									</c:if>
									 </td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
			<div class="pagin">
				<div class="message left">
					共<a class="blue" href="javascript:void(0);"><s:property value="rowCount"/></a>条记录，当前显示第&nbsp;<a
						class="blue" href="javascript:void(0);"><s:property value="pageNo"/>&nbsp;</a>页
				</div>
				<ul class="paginList">
					<li class="paginItem"><a href="javascript:dopageup();">上页</a>
					</li>
					<li class="paginItem"><a href="javascript:dopagedown();">下页</a>
					</li>
					<li class="paginItem2"><span>第<input type="text"
							value="" name="" id="itopage">页</span>
					</li>
					<li class="paginItem"><a href="javascript:doskip();">跳转</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
</body>
</html>