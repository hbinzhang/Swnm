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
		<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/fangqu.js" type="text/javascript"></script>
		<link href="${pageContext.request.contextPath}/resources/css/validationEngine.jquery.css" rel="stylesheet">				<script>			$(function(){				getCurrentOrgByCurrentBranch();				check();			});			function check(){				var error=$("#errorMsg").val();				if(error!=""){					alert(error);				}			}			function getCurrentOrgByCurrentBranch(){				var orglev=$("#orglevel").val();				if(orglev==2){					return;				}				jQuery.ajax({					type : 'GET',					cache:false,					url : $("#ctx").val()+"/alarmmgt/queryDepartByBranch?branchId="+$("#normal_gongsi_select").val(),					contentType: "application/json; charset=utf-8",					success : function(data) {						if(data.result==1)						{							var content = "";							for(var i=0;i<data.object.length;i++)							{									content+="<option value="+data.object[i].id+">"+data.object[i].name+"</option>";							}							$("#normal_guanlichu_select").empty();				        	$("#normal_guanlichu_select").append(content);				        							}					},					error : function(data) {					}				});			}		</script>
	</head>
	<body>
		<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/fence/queryFence"></c:url>">设备管理</a></li>
					<li><a href="<c:url value="/zone/queryZoneByPage"></c:url>">防区管理</a></li>
					<li>添加</li>
				</ul>
			</div>
			<div id="content_container">
				<c:import url="../layout/shebeitab.jsp?sTabLink=fangqu" charEncoding="UTF-8"></c:import>
				<div class="roleadd_container">
					<div style="margin-left:30%;margin-top:20px;">
						<div>
							<div class="left step stepcurrent">
								添加基本信息
							</div>
							<div class="next left">
							</div>
							<div class="left step stepafter">
								添加设备
							</div>
							<div class="clear"></div>
						</div>
					</div>	
					<form action="<c:url value='/zone/addZone'></c:url>" method="post" id="fangqujibenxinxitianjia">						<input type="hidden" value="${sessionScope.session.lev}" id="orglevel" />						<input type="hidden" value="${errorMeg}" id="errorMsg" />
						<div>
							<div style="margin-left:100px;">
								<span style="color:red;" id="exitfanqguId">防区ID已存在</span>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">防区ID:</label>
								<input type="text" value="${zoneBean.zoneID}" name="zoneBean.zoneID" id="fangquid" class="normaltext validate[required,custom[integer],maxSize[9],funcCall[checkZoneIdUnique]]">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">防区名称:</label>
								<input type="text" value="${zoneBean.zoneName}" name="zoneBean.zoneName" id="" class="normaltext validate[required,maxSize[32]]">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">分公司:</label>
								<select id="normal_gongsi_select" class="normalselect" name="zoneBean.branchID">
					    				<c:choose>
					    					<c:when test="${sessionScope.session.lev eq '0' }">
					    						<c:forEach items="${sessionScope.session.orgIdAndNames.subCompanys }" var="companyBean">
					    							<option value="${companyBean.id }">${companyBean.name }</option>
					    						</c:forEach>
					    					</c:when>
					    					<c:otherwise>
					    						<c:forEach items="${sessionScope.session.orgIdAndNames.subCompanys }" var="companyBean">
					    							<option value="${companyBean.id }">${companyBean.name }</option>
					    						</c:forEach>
					    					</c:otherwise>
					    				</c:choose>
				    			</select>
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">管理处:</label>
								<select id="normal_guanlichu_select" class="normalselect" name="zoneBean.mgtID" >
					    				<c:choose>
					    					<c:when test="${sessionScope.session.lev eq '0' }">
					    					</c:when>
					    					<c:when test="${sessionScope.session.lev eq '1' }">
					    						<c:forEach items="${sessionScope.session.orgIdAndNames.managements }" var="departBean">
					    							<option value="${departBean.id }">${departBean.name }</option>
					    						</c:forEach>
					    					</c:when>
					    					<c:otherwise>
					    						<c:forEach items="${sessionScope.session.orgIdAndNames.managements }" var="departBean">
					    							<option value="${departBean.id }">${departBean.name }</option>
					    						</c:forEach>
					    					</c:otherwise>
					    				</c:choose>
				    			</select>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div>
							<div class="text_form left">
								<label class="labelname" for="">起始经度:</label>
								<input type="text" value="${zoneBean.startLon}" name="zoneBean.startLon" id="startjingdu" class="normaltext validate[required,custom[number]]">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">起始纬度:</label>
								<input type="text" value="${zoneBean.startLat}" name="zoneBean.startLat" id="startweidu" class="normaltext validate[required,custom[number]]">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">终止经度:</label>
								<input type="text" value="${zoneBean.endLon}" name="zoneBean.endLon" id="endjingdu" class="normaltext validate[required,custom[number]]">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">终止纬度:</label>
								<input type="text" value="${zoneBean.endLat}" name="zoneBean.endLat" id="endweidu" class="normaltext validate[required,custom[number]]">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div>
							<div class="text_form left">
								<label class="labelname" for="">左/右岸:</label>
								<select class="normalselect" name="zoneBean.orientation">									<c:choose>										<c:when test="${zoneBean.orientation=='左岸'}">											<option value="左岸" selected="selected" >左岸</option>											<option value="右岸">右岸</option>										</c:when>										<c:otherwise>											<option value="左岸">左岸</option>											<option value="右岸" selected="selected" >右岸</option>										</c:otherwise>									</c:choose>

								</select>
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">附加信息:</label>
								<input type="text" value="${zoneBean.info}" name="zoneBean.info" id="" class="normaltext" style="width:710px;">
							</div>
							<div class="clear"></div>
						</div>
				    	<div class="top10 bottom10 left10" style="">
							<%--<input type="submit" value="保存" class="btn" style="margin-left:50px;">
							--%><%-- <a href="<c:url value='/views/shebei/fangqushebeiadd.jsp'></c:url>" class="hrefbtn">下一步</a> --%>
							<input type="button" value="下一步" id="zoneaddbtn" class="hrefbtn">
							<a href="<c:url value='/zone/queryZoneByPage'></c:url>" class="hrefbtn">取消</a>
						</div>
					</form>
				</div>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
	</body>
</html>