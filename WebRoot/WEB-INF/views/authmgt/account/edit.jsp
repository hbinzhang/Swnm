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
				var selectLev = $("#select_session_jigou").val();
				$("#normal_mingcheng_select").empty();
				$("#normal_mingcheng_select").append($("#optiongroups"+selectLev).html());
				$("#select_session_jigou").change(function(){
					var sLev = $("#select_session_jigou").val();
					$("#normal_mingcheng_select").empty();
					$("#normal_mingcheng_select").append($("#optiongroups"+sLev).html());
				});
			});
		</script>
	</head>
	<body>
		<c:import url="../../layout/header.jsp"  charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/authmgt/queryAccountsByAccountId"></c:url>">帐号管理</a></li>
					<li>编辑帐号(工号:${account.id })</li>
				</ul>
			</div>
			<div id="content_container">
				<div class="roleadd_container">
					<form action="<c:url value="/authmgt/updateAccount"></c:url>" method="post" id="accountform">
						<c:choose>
							<c:when test="${commonBean.id eq '1' || commonBean.id eq '-1' }">
								<div style="margin-top:10px;margin-bottom:10px;font-weight:bold;color:red;margin-left:100px;">
									${commonBean.name }
								</div>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
						<s:hidden name="account.id"></s:hidden>
						<div>
							<div class="text_form left">
								<label class="labelname" for="">工号:</label>
								<input type="text" name="" readonly="readonly" disabled="disabled" value="${account.id }" id="accountid" class="normaltext disabletext validate[required]">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">用户名:</label>
								<input type="text" value="${account.userId }" name="account.userId" id="accountname" class="normaltext validate[required,maxSize[30],custom[onlyLetterNumber]]">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">姓名:</label>
								<input type="text" value="${account.userName }" name="account.userName" id="accountusername" class="normaltext validate[required,maxSize[20]]">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">性别:</label>
								<select name="account.sex" id="accountsex" class="normalselect">
									<c:choose>
										<c:when test="${account.sex eq '0' }">
											<option value="0" selected="selected">男</option>
											<option value="1">女</option>
										</c:when>
										<c:otherwise>
											<option value="0">男</option>
											<option value="1" selected="selected">女</option>
										</c:otherwise>
									</c:choose>
								</select>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div>
							<div class="text_form left">
								<label class="labelname" for="">出生日期:</label>
								<input type="text" name="account.birthday" value='<fmt:formatDate value="${account.birthday }" pattern="yyyy-MM-dd"/>' class="normaltext" id="birthday" onClick="WdatePicker({dateFmt:'yyyy-MM-dd' })" style="width:280px;"/>
								<a href="javascript:void(0)" class="underlinenone">
									<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd',el:'birthday' })" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />
								</a>
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">办公电话:</label>
								<input type="text" value="${account.offTel }" id="offtel" name="account.offTel" class="normaltext validate[custom[phone],maxSize[20]]]">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">家庭电话:</label>
								<input type="text" value="${account.homeTel }" id="hometel" name="account.homeTel" class="normaltext validate[custom[phone],maxSize[20]]]">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">手机:</label>
								<input type="text" value="${account.phone }" id="phone" name="account.phone" class="normaltext validate[custom[phone],maxSize[15]]">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div>
							<div class="text_form left">
								<label class="labelname" for="">邮箱:</label>
								<input type="text" name="account.mail" value="${account.mail }" id="accountmail" class="normaltext validate[custom[email],maxSize[25]]">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">职务:</label>
								<input type="text" value="${account.position }" name="account.position" id="aposition" class="normaltext validate[maxSize[20]]">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">职能描述:</label>
								<input type="text" value="${account.positionDesc }" name="account.positionDesc" id="podesc" class="normaltext validate[maxSize[200]]">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">家庭住址:</label>
								<input type="text" value="${account.homeAddress }" name="account.homeAddress" id="hadress" class="normaltext validate[maxSize[50]]">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div>
							<div class="text_form left">
								<label class="labelname" for="">机构级别:</label>
								<select id="select_session_jigou" name="" class="normalselect">
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
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">机构名称:</label>
								<select id="normal_mingcheng_select" name="account.orgId" id="account.orgid" class="normalselect">
				    			</select>
				    			<div style="display: none;" id="optiongroups0">
				    				<option style="display: none;" value="${orgLevAndIdNames.company.id }" group="0">${orgLevAndIdNames.company.name }</option>
				    			</div>
				    			<div style="display: none;" id="optiongroups1">
				    				<c:forEach items="${orgLevAndIdNames.subCompanys }" var="bean">
				    					<c:choose>
				    						<c:when test="${account.orgId eq bean.id }">
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
				    						<c:when test="${account.orgId eq bean.id }">
				    							<option group="2" selected="selected" value="${bean.id }">${bean.name }</option>
				    						</c:when>
				    						<c:otherwise>
				    							<option group="2" value="${bean.id }">${bean.name }</option>
				    						</c:otherwise>
				    					</c:choose>
				    				</c:forEach>
				    			</div>
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">备注:</label>
								<input type="text" name="account.remark" value="${account.remark }" id="remark" class="normaltext validate[maxSize[500]]" style="width:710px;">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div class="text_form">
							<label class="labelname" for="">授予角色&nbsp;</label>
							<div class="clear"></div>
						</div>
						<div class="">
							<fieldset>
								<legend>可被授予的角色</legend>
						       	<c:forEach items="${allowedRoles }" var="r">
								 	<label class="floating">
								 		<c:choose>
								 			<c:when test="${poteviofn:clContains(seledRoles,r) }">
								 				<input type="checkbox" class="validate[required]" checked="checked" value="${r }" name="seledRoles" id=""><span style="margin-left:10px;">${r }</span>
								 			</c:when>
								 			<c:otherwise>
								 				<input type="checkbox" class="validate[required]" value="${r }" name="seledRoles" id=""><span style="margin-left:10px;">${r }</span>
								 			</c:otherwise>
								 		</c:choose>
									</label>
								</c:forEach>
						    </fieldset>
						</div>
						<div class="top5 left40">
							<input type="submit" value="保存" class="btn left20"/>
							<a href="<c:url value='/authmgt/queryAccountsByAccountId'></c:url>" class="hrefbtn">取消</a>
						</div>
					</form>
				</div>
			</div>
		</div>
		<c:import url="../../layout/footer.jsp"  charEncoding="UTF-8"></c:import>
	</body>
</html>