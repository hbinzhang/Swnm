<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../../include.inc.jsp"%>

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

		<script type="text/javascript">

			$(function(){

				TablePage("#session_table",15,"#pagin0",0,"pagin0");

				var selectLev = $("#select_session_jigou").val();

				$("#normal_mingcheng_select").empty();

				$("#normal_mingcheng_select").append($("#optiongroups"+selectLev).html());

				$("#select_session_jigou").change(function(){

					var sLev = $("#select_session_jigou").val();

					$("#normal_mingcheng_select").empty();

					$("#normal_mingcheng_select").append($("#optiongroups"+sLev).html());

				});

				var filterBy = '${filter}';

				if(filterBy==2)

				{

					$("#searchgonghao_container").show();

			    	$("#searchjigou_container").hide();

			    	$("#searchjigou_container select").attr("disabled",true);

			    	$("#search_account").attr("disabled",false);

			    	$("#search_by_gonghao").prop("checked",true);

				}

				else

				{

					$("#searchjigou_container").show();

			    	$("#searchgonghao_container").hide();

			    	$("#search_account").attr("disabled",true);

			    	$("#searchjigou_container select").attr("disabled",false);

			    	$("#search_by_jigou").prop("checked",true);

				}

			});

		</script>

	</head>

	<body>

		<c:import url="../../layout/header.jsp" charEncoding="UTF-8"></c:import>

		<div id="main_content_container">

			<div id="nav_place">

				<div id="title">位置：</div>

				<ul>

					<li><a href="<c:url value="/"></c:url>">首页</a></li>

					<li>会话管理</li>

				</ul>

			</div>

			<div id="content_container">

			    <div class="search_container">

			    	<form action="<c:url value='/authmgt/searchSession'></c:url>" method="get" id="huihua_form">

			    		<div>

				    		<div class="left" style="margin-top:5px;">

				    			<div class="single_filter_container left">

						    		<label class="search_name">查询条件:</label>

						    		<div class="left single_filter" style="margin-top:8px;margin-left:0px;" id="jigou_gonghao_search_control">

						    			<c:choose>

						    				<c:when test="${filter==2 }">

						    					<input type="radio" value="1" name="filter" id="search_by_jigou">按机构查询

						    					<input type="radio" value="2" name="filter" id="search_by_gonghao"  checked="checked" class="left20">按工号查询

						    				</c:when>

						    				<c:otherwise>

						    					<input type="radio" value="1" name="filter" id="search_by_jigou"  checked="checked">按机构查询

						    					<input type="radio" value="2" name="filter" id="search_by_gonghao" class="left20">按工号查询

						    				</c:otherwise>

						    			</c:choose>

						    		</div>

						    	</div>

						    	<div class="left" id="searchjigou_container">

						    		<div class="single_filter_container left">

							    		<label class="search_name">机构级别:</label>

							    		<div class="left single_filter">

							    			<select id="select_session_jigou" name="lev" class="normalselect">

								    			<c:forEach items="${orgLevAndIdNames.levs }" var="lv">

								    				<c:choose>

								    					<c:when test="${lev eq lv.id }">

									    					<option value="${lv.id }" selected="selected">${lv.name }</option>

									    				</c:when>

									    				<c:otherwise>

									    					<option value="${lv.id }">${lv.name }</option>

									    				</c:otherwise>

								    				</c:choose>

								    			</c:forEach>

							    			</select>

							    		</div>

							    	</div>

							    	<div class="single_filter_container left">

							    		<label class="search_name">机构名称:</label>

							    		<div class="left single_filter">

							    			<select id="normal_mingcheng_select" name="organizationId" class="normalselect">

							    			</select>

							    			<div style="display: none;" id="optiongroups0">

							    				<option style="display: none;" value="${orgLevAndIdNames.company.id }" group="0">${orgLevAndIdNames.company.name }</option>

							    			</div>

							    			<div style="display: none;" id="optiongroups1">

							    				<c:forEach items="${orgLevAndIdNames.subCompanys }" var="bean">

							    					<c:choose>

							    						<c:when test="${organizationId eq bean.id }">

							    							<option group="1" selected="selected" value="${bean.id }">${bean.name }</option>

							    						</c:when>

							    						<c:otherwise>

							    							<option group="1" value="${bean.id }">${bean.name }</option>

							    						</c:otherwise>

							    					</c:choose>

							    				</c:forEach>

							    			</div>

							    			<div style="display: none;" id="optiongroups2">

							    				<c:forEach items="${orgLevAndIdNames.managements }" var="bean">

							    					<c:choose>

							    						<c:when test="${organizationId eq bean.id }">

							    							<option group="2" selected="selected" value="${bean.id }">${bean.name }</option>

							    						</c:when>

							    						<c:otherwise>

							    							<option group="2" value="${bean.id }">${bean.name }</option>

							    						</c:otherwise>

							    					</c:choose>

							    				</c:forEach>

							    			</div>

							    		</div>

							    	</div>

						    	</div>

						    	<div class="single_filter_container left" style="display:none;" id="searchgonghao_container">

						    		<label class="search_name">工号:</label>

						    		<div class="left single_filter">

						    			<input type="text" name="id" value="${id }" disabled="disabled" id="search_account" class="normaltext validate[required,custom[gonghao],maxSize[20]]">

						    		</div>

						    	</div>

						    	<div class="clear"></div>

					    	</div>

					    	<div class="single_filter_container left" style="margin-left: 20px;">

				    			<input type="submit" value="查询" class="btn">

				    		</div>

				    	</div>

				    	<div>

				    		<div class="clear"></div>

				    	</div>

			    	</form>

			    </div>

			    <div class="normalajaxloading" id="data_loading"></div>

			    <div class="tableui_container">

					<table class="tableui" id="session_table">

				    	<thead>

					    	<tr>    

					    		<th><span>姓名</span></th>   

						        <th><span>工号</span></th>

						        <th><span>用户名</span></th>

						        <th><span>账号类型</span></th>

						        <th><span>登录时间</span></th>

						        <th><span>主机名称</span></th>

								<th><span>主机地址</span></th>

								<th><span>机构名称</span></th>

								<!-- <th><span>机构级别</span></th> -->

								<th><span>操作</span></th>

					        </tr>

				        </thead>

				        <tbody>

				        	<c:forEach items="${sessions }" var="se">

					        	<tr>        

					        		<td>${se.userNameForUI }</td>

							        <td><c:out value="${se.id }"></c:out></td>

							        <td><c:out value="${se.userIdForUI }"></c:out></td>

							        <td>

							        	<c:choose>

							        		<c:when test="${se.type==0 }">

							        			管理员帐号

							        		</c:when>

							        		<c:otherwise>

							        			普通帐号

							        		</c:otherwise>

							        	</c:choose>

							        </td>

							        <td><fmt:formatDate value="${se.loginTime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>

									<td><c:out value="${se.loginHostName }"></c:out></td>

							        <td><c:out value="${se.loginHostIp }"></c:out></td>

									<td><c:out value="${se.orgNmForUI }"></c:out></td>

									<%--  <td>
										<c:out value="${se.levForUI }"></c:out>
							        </td> --%>

									<td align="center">
										<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'删除会话') }">
										<a href="javascript:void(0);" title="清除" contextid="${se.contextId }" class="delete clearuserlink">

											清除用户

										</a>
										</c:if>

									</td>

						        </tr> 

				        	</c:forEach>

				        </tbody>

				    </table>

				</div>

				<div class="pagin" id="pagin0">

				  	<div class="message left">共<a class="blue" href="javascript:void(0);">${fn:length(sessions) }</a>条记录</div>

				</div>

				<div class="clear">

				</div>

			</div>

		</div>

		<c:import url="../../layout/footer.jsp" charEncoding="UTF-8"></c:import>

	</body>

</html>