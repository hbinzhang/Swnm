<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title>安防综合监控系统</title>
		<link href="<c:url value='/resources/images/favicon.ico'></c:url>" rel="icon" type="image/x-icon" />
	    <link href="<c:url value='/resources/images/favicon.ico'></c:url>" rel="shortcut icon" type="image/x-icon" />
		<link href="${pageContext.request.contextPath}/resources/css/common.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/resources/css/popModal.min.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/resources/css/validationEngine.jquery.css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/gaojing_search.js" type="text/javascript"></script>
	</head>
	<body>
		<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/alarmmgt/queryDeviceAlarm"></c:url>">告警管理</a></li>
					<li>告警查询</li>
				</ul>
			</div>
			<div id="content_container">
				<c:import url="../layout/gaojingtab.jsp?gTabLink=gaojingchaxun" charEncoding="UTF-8"></c:import>
				<form action="" method="get" id="gaojingchaxun_from">
					<div class="search_container">
				    	<div>
					    	<div class="single_filter_container left">
					    		<label class="search_name">告警类型:</label>
					    		<div class="left" style="margin-top:8px;margin-left:20px;">
					    			<input type="radio" radioselect="gaojingleixing" name="type" id="anfanggaojing" value="1" checked="checked"/><label for="anfanggaojing">安防告警</label>
					    		</div>
					    		<div class="left" style="margin-top:8px;margin-left:40px;">
					    			<input type="radio" radioselect="gaojingleixing" name="type" id="shebeigaojing" value="2"/><label for="shebeigaojing">设备告警</label>
					    		</div>
					    		<div class="clear"></div>
					    	</div>
					    	<div class="single_filter_container left" id="youshijianshipin">
					    		<label class="search_name">有事件视频:</label>
					    		<div class="left single_filter" style="margin-top:10px;">
					    			<input type="checkbox" name="hasEventVideo" id="check_youshijianshipin" value="1">
					    		</div>
					    	</div>
					    	<div class="clear"></div>
				    	</div>
				    	<div>
				    		<div class="single_filter_container left">
					    		<label class="search_name">分公司:</label>
					    		<div class="left single_filter">
					    			<select id="fengongsi_select" class="normalselect" name="branchId">
					    				<c:choose>
					    					<c:when test="${userLevel eq '0' }">
					    						<option value="" selected="selected">全部</option>
							    				<c:forEach items="${branchList }" var="st">
							    					<option value="${st.id }">${st.name }</option>
							    				</c:forEach>
					    					</c:when>
					    					<c:otherwise>
					    						<c:forEach items="${branchList }" var="st">
							    					<option value="${st.id }">${st.name }</option>
							    				</c:forEach>
					    					</c:otherwise>
					    				</c:choose>
					    			</select>
					    		</div>
					    	</div>
					    	<div class="single_filter_container left">
					    		<label class="search_name">管理处:</label>
					    		<div class="left single_filter">
					    			<select id="guanlichu_select" class="normalselect" name="departmentId">
					    				<c:choose>
					    					<c:when test="${userLevel eq '0' }">
					    						<option value="" selected="selected">全部</option>
					    					</c:when>
					    					<c:when test="${userLevel eq '1' }">
					    						<option value="" selected="selected">全部</option>
					    						<c:forEach items="${departmentList }" var="st">
							    					<option value="${st.id }">${st.name }</option>
							    				</c:forEach>
					    					</c:when>
					    					<c:otherwise>
					    						<c:forEach items="${departmentList }" var="st">
							    					<option value="${st.id }">${st.name }</option>
							    				</c:forEach>
					    					</c:otherwise>
					    				</c:choose>
					    			</select>
					    		</div>
					    	</div>
					    	<div class="single_filter_container left">
					    		<label class="search_name">防区:</label>
					    		<div class="left single_filter">
					    			<select id="fangqu_select" class="normalselect" name="zoneId">
					    				<c:choose>
					    					<c:when test="${userLevel eq '0' }">
					    						<option value="-1" selected="selected">全部</option>
					    					</c:when>
					    					<c:when test="${userLevel eq '1' }">
					    						<option value="-1" selected="selected">全部</option>
					    						<c:forEach items="${zoneList }" var="st">
							    					<option value="${st.id }">${st.name }</option>
							    				</c:forEach>
					    					</c:when>
					    					<c:otherwise>
					    						<option value="-1" selected="selected">全部</option>
					    						<c:forEach items="${zoneList }" var="st">
							    					<option value="${st.id }">${st.name }</option>
							    				</c:forEach>
					    					</c:otherwise>
					    				</c:choose>
					    			</select>
					    		</div>
					    	</div>
					    	<div class="single_filter_container left">
					    		<label class="search_name">开始时间:</label>
					    		<div class="left single_filter">
					    			<input type="text" name="beginTime" class="normaltext" id="fromdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'todate\')}' })" style="width:200px;"/>
									<a href="javascript:void(0)" class="underlinenone">
										<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',el:'fromdate',maxDate:'#F{$dp.$D(\'todate\')}' })" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />
									</a>
					    		</div>
					    	</div>
					    	<div class="single_filter_container left">
					    		<label class="search_name">结束时间:</label>
					    		<div class="left single_filter">
					    			<input type="text" name="endTime" class="normaltext" id="todate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'fromdate\')}' })" style="width:200px;"/>
									<a href="javascript:void(0)" class="underlinenone">
										<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',el:'todate',minDate:'#F{$dp.$D(\'fromdate\')}' })" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />
									</a>
					    		</div>
					    	</div>
					    	<div class="clear"></div>
				    	</div>
				    	<div>
				    		<div class="single_filter_container left">
					    		<label class="search_name">设备ID:</label>
					    		<div class="left single_filter">
					    			<input type="text" name="deviceId" id="" class="normaltext validate[maxSize[11]]"/>
					    		</div>
					    	</div>
				    		<div class="single_filter_container left">
				    			<label class="search_name">告警级别:</label>
					    		<div class="left single_filter">
					    			<select id="" class="normalselect" name="levelId">
						    			<option value="-1">全部</option>
					    				<option value="1">警告</option>
					    				<option value="2">次要</option>
					    				<option value="3">主要</option>
					    				<option value="4">严重</option>
					    			</select>
					    		</div>
				    		</div>
				    		<div class="single_filter_container left">
					    		<label class="search_name">设备类型:</label>
					    		<div class="left single_filter">
					    			<select id="shebeileixing_select" class="normalselect" name="deviceTypeId">
						    			<option value="-1" selected="selected">全部</option>
					    				<option value="1">高压脉冲主机</option>
					    				<option value="2">一体化探测主机</option>
					    				<option value="3">防区型振动光纤</option>
					    				<option value="4">定位型振动光纤</option>
					    				<option value="5">NVR</option>
					    				<option value="6">IPC</option>
					    				<option value="7">智能视频服务器</option>
					    			</select>
					    		</div>
					    	</div>
				    		<div class="single_filter_container left">
				    			<label class="search_name">告警名称:</label>
					    		<div class="left single_filter">
					    			<select id="gaojingmingcheng_select" class="normalselect" name="alarmName">
					    				<option value="">全部</option>
					    			</select>
					    		</div>
				    		</div>
				    		<div class="single_filter_container left">
				    			<label class="search_name">处理状态:</label>
					    		<div class="left single_filter">
					    			<select id="" class="normalselect" name="alarmStatus">
					    				<option value="-1">全部</option>
					    				<option value="0">未处理</option>
					    				<option value="1">已处理</option>
					    			</select>
					    		</div>
				    		</div>
				    		<input type="hidden" value="1" name="offset" id="pageoffset"/>
				    		<div class="clear"></div>
				    	</div>
				    	<div>
				    		<div class="single_filter_container left" style="margin-left: 20px;">
				    			<input type="button" value="查询" class="btn" id="gaojingchaxun_btn">
				    			<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'导出告警') }">
				    				<a href="javascript:void(0);" id="export_gaojing_query" class="hrefbtn">导出</a>
				    			</c:if>
				    		</div>
				    		<div class="clear"></div>
				    	</div>
				    </div>
				</form>
			    <div class="tableui_container">
			    	<div class="searchloading"></div>
					<table class="tableui" id="gaojingtablecontainer">
				    	<thead>
					    	<tr>       
						        <th><span>告警编号</span></th>
								<th><span>告警码</span></th>
						        <th><span>告警名称</span></th>
						        <th><span>告警时间</span></th>
						        <th><span>告警级别</span></th>
								<th><span>设备类型</span></th>
								<th><span>设备ID</span></th>
								<th><span>分公司</span></th>
								<th><span>管理处</span></th>
								<th><span>防区</span></th>
								<th><span>处理状态</span></th>
								<th><span>附加信息</span></th>
					        </tr>
				        </thead>
				        <tbody>
				        </tbody>
				    </table>
				</div>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
		<div id="detail_hidden">
		</div>
		<div id="anfang_gaojing_info" style="display: none;z-index:0;" wid="70">
			<div class='dialogModal_header'>告警详细信息</div>
			<div class='dialogModal_content'>
				<table class="detail_table anfanggaojing_detail" id="anfanggaojing_detail">
					<tbody>
						<tr>
							<td class="title_name">告警编号:</td>
							<td class="content content_gaojingbianhao"></td>
							<td class="title_name">告警码:</td>
							<td class="content content_gaojingma"></td>
							<td class="title_name">告警名称:</td>
							<td class="content content_gaojingmingcheng"></td>
						</tr>
						<tr>
							<td class="title_name">告警时间:</td>
							<td class="content content_gaojingshijian"></td>
							<td class="title_name">告警级别:</td>
							<td class="content content_gaojingjibie"></td>
							<td class="title_name">告警类型:</td>
							<td class="content content_gaojingleixing">安防告警</td>
						</tr>
						<tr>
							<td class="title_name">设备类型:</td>
							<td class="content content_shebeileixing"></td>
							<td class="title_name">设备ID:</td>
							<td class="content content_shebeiid"></td>
							<td class="title_name">处理状态:</td>
							<td class="content content_chulizhuangtai">已处理</td>
						</tr>
						<tr>
							<td class="title_name">分公司:</td>
							<td class="content content_fengongsi"></td>
							<td class="title_name">管理处:</td>
							<td class="content content_guanlichu"></td>
							<td class="title_name">防区:</td>
							<td class="content content_fangqu"></td>
						</tr>
						<tr>
							<td class="title_name">复核人:</td>
							<td class="content content_chuliren"></td>
							<td class="title_name">处理时间:</td>
							<td class="content content_chulishijian"></td>
							<td class="title_name">责任人:</td>
							<td class="content content_zerenren"></td>
						</tr>
						<tr>
							<td class="title_name">事件原因:</td>
							<td class="content content_shijianyuanyin"></td>
							<td class="title_name">事件过程:</td>
							<td class="content content_shijianguocheng"></td>
							<td class="title_name">上报情况:</td>
							<td class="content content_shangbaoqingkuang"></td>
						</tr>
						<tr>
							<td class="title_name">虚实警:</td>
							<td class="content content_xushijing"></td>
							<td class="title_name">持续时间:</td>
							<td class="content content_chixushijian"></td>
							<td class="title_name">次数:</td>
							<td class="content content_cishu"></td>
						</tr>
						<tr>
							<td class="title_name">事件视频:</td>
							<td class="content content_fangqushijian"></td>
							<td class="title_name">事件图片:</td>
							<td class="content content_shijiantupian"></td>
							<td class="title_name">复核依据:</td>
							<td class="content content_fuheyiju"></td>
						</tr>
						<tr>
							<td class="title_name">附加信息:</td>
							<td colspan="5" class="content content_fujiaxinxi"></td>
						</tr>
					<tbody>
				</table>
			</div>
			<div class="left20 top10" style="padding-bottom:20px;">
				<input class="left20 btn" data-dialogModalBut="cancel" type="button" value="关闭"/>
			</div>
		</div>
		<div id="shebei_gaojing_info" style="display: none;z-index:0;" wid="70">
			<div class='dialogModal_header'>告警详细信息</div>
			<div class='dialogModal_content'>
				<table class="detail_table shebeigaojing_detail" id="shebeigaojing_detail">
					<tbody>
						<tr>
							<td class="title_name">告警编号:</td>
							<td class="content content_gaojingbianhao"></td>
							<td class="title_name">告警码:</td>
							<td class="content content_gaojingma"></td>
							<td class="title_name">告警名称:</td>
							<td class="content content_gaojingmingcheng"></td>
						</tr>
						<tr>
							<td class="title_name">告警时间:</td>
							<td class="content content_gaojingshijian"></td>
							<td class="title_name">告警级别:</td>
							<td class="content content_gaojingjibie"></td>
							<td class="title_name">告警类型:</td>
							<td class="content content_gaojingleixing">设备告警</td>
						</tr>
						<tr>
							<td class="title_name">设备类型:</td>
							<td class="content content_shebeileixing"></td>
							<td class="title_name">设备ID:</td>
							<td class="content content_shebeiid"></td>
							<td class="title_name">处理状态:</td>
							<td class="content content_chulizhuangtai"></td>
						</tr>
						<tr>
							<td class="title_name">分公司:</td>
							<td class="content content_fengongsi"></td>
							<td class="title_name">管理处:</td>
							<td class="content content_guanlichu"></td>
							<td class="title_name">防区:</td>
							<td class="content content_fangqu"></td>
						</tr>
						<tr>
							<td class="title_name">可能原因:</td>
							<td class="content content_kenengyuanyin"></td>
							<td class="title_name">可能后果:</td>
							<td class="content content_kenenghouguo"></td>
							<td class="title_name">建议操作:</td>
							<td class="content content_jianyicaozuo"></td>
						</tr>
						<tr>
							<td class="title_name">处理人:</td>
							<td class="content content_chuliren"></td>
							<td class="title_name">处理时间:</td>
							<td class="content content_chulishijian"></td>
							<td class="title_name">附加信息:</td>
							<td class="content content_fujiaxinxi"></td>
						</tr>
					<tbody>
				</table>
			</div>
			<div class="left20 top10" style="padding-bottom:20px;">
				<input class="left20 btn" data-dialogModalBut="cancel" type="button" value="关闭"/>
			</div>
		</div>
	</body>
</html>