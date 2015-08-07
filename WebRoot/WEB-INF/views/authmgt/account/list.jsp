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

				TablePage("#userlist_table",15,"#pagin0",0,"pagin0");

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

					<li>帐号管理</li>

				</ul>

			</div>

			<div id="content_container">

			    <div class="search_container">

			    	<div>

			    		<form action="" id="zhanghao_form">

					    	<div class="left" style="margin-top:5px;">

						    	<div class="single_filter_container left">

						    		<label class="search_name">工号:</label>

						    		<div class="left single_filter">

						    			<input type="text" name="" id="search_user_value" class="normaltext validate[custom[gonghao],maxSize[20]]">

						    		</div>

						    	</div>

						    	<div class="clear"></div>

					    	</div>

					    	<div class="single_filter_container left" style="margin-left: 20px;">

				    			<input type="button" value="查询" class="btn" id="search_users">


				    			<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'创建帐号') }">

				    				<a href="<c:url value='/authmgt/entryCreateAccount'></c:url>" class="hrefbtn">创建帐号</a>

				    			</c:if>

				    		</div>

			    		</form>

			    	</div>

			    	<div>

			    		<div class="clear"></div>

			    	</div>

			    </div>

			    <div class="normalajaxloading" id="data_loading"></div>
				
				<div id="tableui_container"  style="display:none;">
					<table class="tableui" id="userlist_table_search">

			    		<thead>

					    	<tr>       

						        <th><span>用户名</span></th>

						        <th><span>机构名称</span></th>

						        <th><span>工号</span></th>

						        <th><span>姓名</span></th>

								<th><span>办公电话</span></th>

								<th><span>手机</span></th>

								<th><span>邮箱</span></th>

								<th><span>角色</span></th>

								<th><span>操作</span></th>

					        </tr>

				        </thead>

				        <tbody>

				        </tbody>

			    	</table>
				</div>
				
			    <div class="tableui_container">
					<table class="tableui" id="userlist_table">

				    	<thead>

					    	<tr>       

						        <th><span>用户名</span></th>

						        <th><span>机构名称</span></th>

						        <th><span>工号</span></th>

						        <th><span>姓名</span></th>

								<th><span>办公电话</span></th>

								<th><span>手机</span></th>

								<th><span>邮箱</span></th>

								<th><span>角色</span></th>

								<th><span>操作</span></th>

					        </tr>

				        </thead>

				        <tbody>

				        	<c:forEach items="${accounts }" var="zhanghao" varStatus="index">

					        	<span style="display:none;" id="zhanghao${index.count }_xingbie">

					        		<c:choose>

					        			<c:when test="${zhanghao.sex eq '0' }">

					        				男

					        			</c:when>

					        			<c:otherwise>

					        				女

					        			</c:otherwise>

					        		</c:choose>

					        	</span>

					        	<span style="display:none;" id="zhanghao${index.count }_chushengriqi">

					        		<fmt:formatDate value="${zhanghao.birthday }" pattern="yyyy-MM-dd"/>

					        	</span>

					        	<span style="display:none;" id="zhanghao${index.count }_jiatingdianhua"><c:out value="${zhanghao.homeTel }"></c:out></span>

					        	<span style="display:none;" id="zhanghao${index.count }_zhiwu"><c:out value="${zhanghao.position }"></c:out></span>

					        	<span style="display:none;" id="zhanghao${index.count }_jiatingzhuzhi"><c:out value="${zhanghao.homeAddress }"></c:out></span>

					        	<span style="display:none;" id="zhanghao${index.count }_zhiwumiaoshu"><c:out value="${zhanghao.positionDesc }"></c:out></span>

					        	<span style="display:none;" id="zhanghao${index.count }_beizhuxinxi"><c:out value="${zhanghao.remark }"></c:out></span>

					        	<span style="display:none;" id="zhanghao${index.count }_zhanghaoleixing">

					        		<c:choose>

					        			<c:when test="${zhanghao.type==0 }">

					        				管理员帐号

					        			</c:when>

					        			<c:otherwise>

					        				普通帐号

					        			</c:otherwise>

					        		</c:choose>

					        	</span>

				        		<tr id="tr${zhanghao.id }">        

							        <td id="zhanghao${index.count }_yonghuming">

							        	<a href="javascript:void(0);" onclick="viewUserDetail(this)" class="detail zhanghao_detail" zhanghaoid=${index.count }><c:out value="${zhanghao.userId }"></c:out></a>

							        </td>

							        <td id="zhanghao${index.count }_jigoumingcheng"><c:out value="${zhanghao.orgNmForUI }"></c:out></td>

							        <td id="zhanghao${index.count }_gonghao"><c:out value="${zhanghao.id }"></c:out></td>

							        <td id="zhanghao${index.count }_xingming"><c:out value="${zhanghao.userName }"></c:out></td>

							        <td id="zhanghao${index.count }_bangongdianhua"><c:out value="${zhanghao.offTel }"></c:out></td>

							        <td id="zhanghao${index.count }_shouji"><c:out value="${zhanghao.phone }"></c:out></td>

							        <td id="zhanghao${index.count }_youxiang"><c:out value="${zhanghao.mail }"></c:out></td>

									<td id="zhanghao${index.count }_juese"><c:out value="${zhanghao.roles }"></c:out></td>

									<td align="center">

										<%-- <a href="<c:url value='/authmgt/editAccount?id=${zhanghao.id }'></c:url>" title="编辑" class="editlink">

											编辑

										</a>

										<a href="javascript:void(0);" title="重置密码" gonghao="${zhanghao.id }" class="changepasswdlink left20">

											重置密码

										</a>

										<a href="javascript:void(0);" gonghao="${zhanghao.id }" title="删除" class="delete deleteaccount deletelink left20">

											删除

										</a> --%>

										<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'修改帐号') }">

											<a href="<c:url value='/authmgt/editAccount?id=${zhanghao.id }'></c:url>" title="编辑" class="editlink">

												编辑

											</a>

										</c:if>

										<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'重置密码') }">

											<a href="javascript:void(0);" title="重置密码" gonghao="${zhanghao.id }" class="changepasswdlink left20">

												重置密码

											</a>

										</c:if>

										<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'删除帐号') }">

											<a href="javascript:void(0);" gonghao="${zhanghao.id }" title="删除" class="delete deleteaccount deletelink left20">

												删除

											</a>

										</c:if>

									</td>

						        </tr> 

				        	</c:forEach>

				        </tbody>

				    </table>

				</div>

				<div class="pagin" id="pagin0">

				  	<div class="message left">共<a class="blue" href="javascript:void(0);">50</a>条记录</div>

				</div>

				<div class="clear">

				</div>

			</div>

		</div>

		<c:import url="../../layout/footer.jsp" charEncoding="UTF-8"></c:import>

		<div id="zhanghao_detail_container" style="display: none;z-index:0;" wid="60">

			<div class='dialogModal_header'>帐号详细信息</div>

			<div class='dialogModal_content'>

				<table class="detail_table">

					<tbody>

						<tr>

							<td class="title_name">用户名:</td>

							<td class="content content_yonghuming"></td>

							<td class="title_name">姓名:</td>

							<td class="content content_xingming"></td>

							<td class="title_name">性别:</td>

							<td class="content content_xingbie"></td>

						</tr>

						<tr>

							<td class="title_name">工号:</td>

							<td class="content content_gonghao"></td>

							<td class="title_name">帐号类型:</td>

							<td class="content content_zhanghaoleixing"></td>

							<td class="title_name">出生日期:</td>

							<td class="content content_chushengriqi"></td>

						</tr>

						<tr>

							<td class="title_name">机构名称:</td>

							<td class="content content_jigoumingcheng"></td>

							<td class="title_name">职务:</td>

							<td class="content content_zhiwu"></td>

							<td class="title_name">职务描述:</td>

							<td class="content content_zhiwumiaoshu"></td>

						</tr>

						<tr>

							<td class="title_name">电子邮箱:</td>

							<td class="content content_youxiang"></td>

							<td class="title_name">手机:</td>

							<td class="content content_shouji"></td>

							<td class="title_name">办公电话:</td>

							<td class="content content_bangongdianhua"></td>

						</tr>

						<tr>

							<td class="title_name">家庭电话:</td>

							<td class="content content_jiatingdianhua"></td>

							<td class="title_name">家庭住址:</td>

							<td class="content content_jiatingzhuzhi" colspan="3"></td>

						</tr>

						<tr>

							<td class="title_name">角色:</td>

							<td class="content content_juese" colspan="3"></td>

							<td class="title_name">备注:</td>

							<td colspan="3" class="content content_beizhuxinxi"></td>

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