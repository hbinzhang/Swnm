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

		<script src="${pageContext.request.contextPath}/resources/js/gaojingtongji.js" type="text/javascript"></script>

	</head>

	<body>

		<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>

		<div id="main_content_container">

			<div id="nav_place">

				<div id="title">位置：</div>

				<ul>

					<li><a href="<c:url value="/"></c:url>">首页</a></li>

					<li><a href="<c:url value="/alarmmgt/queryDeviceAlarm"></c:url>">告警管理</a></li>

					<li>告警统计</li>

				</ul>

			</div>

			<div id="content_container">

				<c:import url="../layout/gaojingtab.jsp?gTabLink=gaojingtongji" charEncoding="UTF-8"></c:import>

				<div class="searchloading"></div>

				<form action="" method="get" id="gaojingtongjiform">

					<div class="search_container">

				    	<div>

					    	<div class="single_filter_container left">

					    		<label class="search_name">告警类型:</label>

					    		<div class="left single_filter">

						    		<select id="leixing_select" class="normalselect" name="alarmStatisticCondition.type">

					    				<option value="1" selected="selected">安防告警</option>

					    				<option value="2">设备告警</option>

					    			</select>

				    			</div>

					    		<div class="clear"></div>

					    	</div>

					    	<div class="single_filter_container left">

					    		<label class="search_name">分公司:</label>

					    		<div class="left single_filter">

					    			<select id="fengongsi_select" class="normalselect" name="alarmStatisticCondition.branchId">

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

					    			<select id="guanlichu_select" class="normalselect" name="alarmStatisticCondition.departmentId">

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

					    			<select id="fangqu_select" class="normalselect" name="alarmStatisticCondition.zoneId">

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

					    	<div class="clear"></div>

				    	</div>

				    	<div>

				    		<div class="single_filter_container left">

				    			<label class="search_name">告警级别:</label>

					    		<div class="left single_filter">

					    			<select id="selectgaojingjibie" class="normalselect" name="alarmStatisticCondition.levelId">

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

					    			<select id="selectshebeileixing" class="normalselect" name="alarmStatisticCondition.deviceTypeId">

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

					    		<label class="search_name">开始时间:</label>

					    		<div class="left single_filter">

					    			<input type="text" name="alarmStatisticCondition.beginTime" class="normaltext validate[required] datepicker" id="fromdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'todate\',{s:-1})}'})" style="width:200px;"/>

									<a href="javascript:void(0)" class="underlinenone">

										<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',el:'fromdate',maxDate:'#F{$dp.$D(\'todate\',{s:-1})}' })" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />

									</a>

					    		</div>

					    	</div>

					    	<div class="single_filter_container left">

					    		<label class="search_name">结束时间:</label>

					    		<div class="left single_filter">

					    			<input type="text" name="alarmStatisticCondition.endTime" class="normaltext validate[required] datepicker" id="todate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'fromdate\',{s:1})}'})" style="width:200px;"/>

									<a href="javascript:void(0)" class="underlinenone">

										<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',el:'todate',minDate:'#F{$dp.$D(\'fromdate\',{s:1})}'})" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />

									</a>

					    		</div>

					    	</div>

					    	<div class="clear"></div>

				    	</div>

				    </div>

				    <input type="hidden" value="1" name="alarmStatisticCondition.offset" id="pageoffset"/>

				    <div class="search_container">

				    	<div>

				    		<div class="single_filter_container left" style="width: 600px;">

					    		<label class="search_name">统计粒度:</label>

					    		<div class="left single_filter" style="width: 450px;margin-top:10px;">

					    			<input type="checkbox" class="staticGranularity_checkbox" name="alarmStatisticCondition.staticGranularity" id="check_fengongsi" checked="checked" value="1">分公司

					    			<input style="margin-left:20px;" class="staticGranularity_checkbox" type="checkbox" name="alarmStatisticCondition.staticGranularity" id="check_guanlichu" checked="checked" value="2">管理处

					    			<input style="margin-left:20px;" class="staticGranularity_checkbox" type="checkbox" name="alarmStatisticCondition.staticGranularity" id="check_fangqu" checked="checked" value="3">防区

					    			<input style="margin-left:20px;" class="staticGranularity_checkbox" type="checkbox" name="alarmStatisticCondition.staticGranularity" id="check_shebeileixing" value="4">设备类型

					    			<input style="margin-left:20px;" class="staticGranularity_checkbox" type="checkbox" name="alarmStatisticCondition.staticGranularity" id="check_gaojingjibie" value="5">告警级别

					    		</div>

					    	</div>

					    	<div class="single_filter_container left" style="width: 600px;">

				    			<label class="search_name">时间粒度:</label>

					    		<div class="left single_filter" id="shijianlidu" style="width: 450px;margin-top:10px;">

					    			<input style="" type="radio" name="alarmStatisticCondition.timeGranularity" id="" checked="checked" value="DD">天

					    			<input style="margin-left:20px;" type="radio" name="alarmStatisticCondition.timeGranularity" id="" value="DY">周

					    			<input style="margin-left:20px;" type="radio" name="alarmStatisticCondition.timeGranularity" id="" value="MM">月

					    			<input style="margin-left:20px;" type="radio" name="alarmStatisticCondition.timeGranularity" id="" value="Q">季度

					    			<input style="margin-left:20px;" type="radio" name="alarmStatisticCondition.timeGranularity" id="" value="YY">年

					    		</div>

				    		</div>

					    	<div class="clear"></div>

				    	</div>

				    	<div>

				    		<div class="single_filter_container left" style="margin-left: 20px;">

				    			<input type="button" value="统计" class="btn" id="gaojingtongji_button">
								<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'导出告警统计分析结果') }">
									<a href="javascript:void(0);" class="hrefbtn" id="exportgaojingtongji">导出</a>
								</c:if>
				    		</div>

				    		<div class="clear"></div>

				    	</div>

				    </div>

				</form>

			    <div class="tableui_container">

					<table class="tableui" id="gaojingtongjitablecontainer">

			    		<thead>

					    	<tr>       

						        <th><span>开始时间</span></th>

								<th><span>结束时间</span></th>

						        <th><span>分公司</span></th>

						        <th><span>管理处</span></th>

						        <th><span>防区</span></th>

						        <th><span>告警个数</span></th>

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

	</body>

</html>