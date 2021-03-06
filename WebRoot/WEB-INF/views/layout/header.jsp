<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.inc.jsp"%>
<link href="${pageContext.request.contextPath}/resources/css/validationEngine.jquery.css" rel="stylesheet">
<script type="text/javascript">
	function onTopData(event)
	{
		if (event.getSubject() == localWarning) {//告警推送发来的消息
			var alarmOperation = event.get("alarmOperation");//消息处理类型 add 或者 del
			var alarmType = event.get("alarmType");//消息类型 securityAlarm或者deviceAlarm
	        if(alarmOperation=="add")
	        {
	        	if(alarmType=="securityAlarm")
	        	{
	        		var currentAnfangCount = $("#socket_anfanggaojing").text();
	        		$("#socket_anfanggaojing").text(parseInt(currentAnfangCount)+1);
	        	}
	        	else if(alarmType=="deviceAlarm")
	        	{
	        		var currentShebeiCount = $("#socket_shebeigaojing").text();
	        		$("#socket_shebeigaojing").text(parseInt(currentShebeiCount)+1);
	        	}
	        	else{}
	        }
	  	}
	}
	$(function(){
		var currentNavLink = '${param.navLink}';
		$("#nav_"+currentNavLink).addClass("selected");
		var lanrenzhijia = 0;
		if(lanrenzhijia ==0){
			$('#down_nav li').hover(function(){
				$('.second',this).css('top','30px').show();
			},function(){
				$('.second',this).hide();
			});
		}else if(lanrenzhijia ==1){
			$('#down_nav li').hover(function(){
				$('.second',this).css('bottom','30px').show();
			},function(){
				$('.second',this).hide();
			});
		}
		linkageInit('${sessionScope.session.id}','${sessionScope.session.organizationId}',"mainctrl");
	});	function showAbout(){		var width = 400;		var height = 300;		var html = document.documentElement;		var x=window.screenLeft ? window.screenLeft : window.screenX ; 		var y=window.screenTop ? window.screenTop : window.screenY; 		var toleft = x + html.clientWidth / 2 - width / 2;		var totop = y + html.clientHeight / 2 - height / 2;		window.open('<%=basePath%>about.jsp','about','height='+height+',width='+width+',top='+totop+',left='+toleft+',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');	}
</script>
<div id="header_container">
	<div id="logo">
		<a href="<c:url value="/"></c:url>">
			<img title="安防综合监控系统首页" src="${pageContext.request.contextPath}/resources/images/header/logo.png">
		</a>
	</div>
	<div id="fastnav">
		<ul>
		    <li>
		    	<a id="nav_shishijiankong" href="${pageContext.request.contextPath}/videomonitor/loadVideoPreview.action">
		    		<img title="视频监控" src="${pageContext.request.contextPath}/resources/images/header/shishijiankong.png">
		    		<h2>视频监控</h2>
		    	</a>
		    </li>
		    <li>
		    	<a id="nav_dianziweilan" href="<c:url value="/fence/queryFence"></c:url>">
		    		<img title="围栏主机" src="${pageContext.request.contextPath}/resources/images/header/dianziweilan.png">
		    		<h2>围栏主机</h2>
		    	</a>
		    </li>
			<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'视频复核') }">
				<li>
					<a id="nav_gaojingchuli" href="<c:url value="/linkagectl/findAllUIMcAlarmInfo"></c:url>">
						<img title="告警处理" src="${pageContext.request.contextPath}/resources/images/header/gaojingchuli.png">
						<h2>告警处理</h2>
					</a>
				</li>
			</c:if>
		    <li>
		    	<a id="nav_shipinhuifang" href="${pageContext.request.contextPath}/videomonitor/loadPlayback.action">
		    		<img title="视频回放" src="${pageContext.request.contextPath}/resources/images/header/shipinhuifang.png">
		    		<h2>视频回放</h2>
		    	</a>
		    </li>
		    <li>
		    	<a id="nav_shebeiguanli" href="${pageContext.request.contextPath}/videomonitor/loadIpc.action">
		    		<img title="设备管理" src="${pageContext.request.contextPath}/resources/images/header/shebeiguanli.png">
		    		<h2>设备管理</h2>
		    	</a>
		    </li>
		     <li>
		    	<a id="nav_zhinengshipin" href="${pageContext.request.contextPath}/videomonitor/queryIvaByPage.action?page.offset=1&page.size=5">
		    		<img title="智能视频" src="${pageContext.request.contextPath}/resources/images/header/zhinengshipin.png">
		    		<h2>智能视频</h2>
		    	</a>
		    </li>
		     <li>
		    	<a id="nav_gis" onclick="openwin('${sessionScope.session.id}','${sessionScope.session.organizationId}')" href="javascript:void(0);">
		    		<img title="地理信息" src="${pageContext.request.contextPath}/resources/images/header/gis.png">
		    		<h2>地理信息</h2>
		    	</a>
		    </li>
	    </ul>
	</div>
	<div id="righttop">
		<div style="margin-top: 10px;">
			<div id="logininfo">
				<span style="cursor: pointer;"><a style="color:#ffffff;" id="change_user_password" title="修改密码" href="javascript:void(0);">欢迎您 &nbsp;&nbsp;${sessionScope.session.organizationId}${sessionScope.session.orgNmForUI} &nbsp;&nbsp;${sessionScope.session.userNameForUI }</a></span>
				<div id="edit_password_container" style="display: none;z-index:0;" wid="30">
					<div class='dialogModal_header'>修改密码</div>
					<div class='dialogModal_content'>
					</div>
					<div class="left20 top10" style="padding-bottom:20px;margin-left:30%;">
						<input class="left20 btn" id="" data-dialogModalBut="ok" type="button" value="确认"/>
						<input class="left20 btn" data-dialogModalBut="cancel" type="button" value="关闭"/>
					</div>
				</div>
				<a class="sockethref" href="<c:url value="/linkagectl/findAllUIMcAlarmInfo"></c:url>">
					<img title="安防告警" src="${pageContext.request.contextPath}/resources/images/header/bell.png">
					<span id="socket_anfanggaojing">0</span>
				</a>
				<a class="sockethref" href="<c:url value='/alarmmgt/queryDeviceAlarm'></c:url>">
					<img title="设备告警" src="${pageContext.request.contextPath}/resources/images/header/t05.png">
					<span id="socket_shebeigaojing">0</span>
				</a>
				<a class="sockethref" href="javascript:void(0);">
					<img title="消息" src="${pageContext.request.contextPath}/resources/images/header/i1.png">
					<span id="socket_xiaoxi">0</span>
				</a>
				<div class="clear"></div>
			</div>
			<div id="" class="right">
				<div id="help">
					<a href="javascript:showAbout();">帮助</a>
				</div>
				<div id="logout">
					<a href="<c:url value='/authmgt/logout'></c:url>">退出</a>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		<div class="clear"></div>
		<div id="down_nav">
			<%-- <li>
				<a href="">安防监控</a>
				<div class="second">
		            <a href="">智能配置</a>
					<a href="${pageContext.request.contextPath}/videomonitor/loadVideoPreview.action">实时监控</a>
					<a href="">视频回放</a>
					<a href="<c:url value='/linkagectl/findAllUIMcAlarmInfo'></c:url>">告警处理</a>
				</div>
			</li> --%>
			<li>
				<a href="<c:url value='/alarmmgt/queryDeviceAlarm'></c:url>">告警管理</a>
			</li>
			<%-- <li>
				<a href="<c:url value='/fence/queryFence'></c:url>">设备管理</a>
			</li> --%>
			<li>
				<a href="javascript:void(0);">安防信息</a>
				<div class="second">
					<a href="<c:url value="/securityinfo/queryTaskBook"></c:url>">安防任务</a>
					<a href="<c:url value="/securityinfo/queryAssessment"></c:url>">目标考核</a>
					<a href="<c:url value="/securityinfo/queryEvent"></c:url>">安全事件</a>
					<a href="<c:url value="/securityinfo/queryInspection"></c:url>">安全督查</a>
				</div>				
		<%-- 	<a href="<c:url value=""></c:url>">安防信息</a>--%>			
			</li>
			<li>
				<a href="javascript:void(0);">系统管理</a>
				<div class="second">
					<a href="<c:url value="/authmgt/queryOrganizationsByAccountId"></c:url>">机构管理</a>
					<a href="<c:url value="/authmgt/queryAllRoles"></c:url>">角色管理</a>
					<a href="<c:url value="/authmgt/queryAccountsByAccountId"></c:url>">帐号管理</a>
					<a href="<c:url value="/authmgt/entrySession"></c:url>">会话管理</a>
					<a href="<c:url value="/logmgt/initSecurityLog"></c:url>">日志管理</a>
				</div>
			</li>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>
<div class="clear"></div>