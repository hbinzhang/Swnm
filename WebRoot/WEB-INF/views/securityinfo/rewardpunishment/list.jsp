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

		<link href="${pageContext.request.contextPath}/resources/css/popModal.min.css" rel="stylesheet">

		<link href="${pageContext.request.contextPath}/resources/css/validationEngine.jquery.css" rel="stylesheet">

		<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">

		<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/rewardpunishment.js" type="text/javascript"></script>

		<script type="text/javascript">


		</script>

	</head>

	<body>

		<c:import url="../../layout/header.jsp" charEncoding="UTF-8"></c:import>

		<div id="main_content_container">

			<div id="nav_place">

				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/securityinfo/queryAssessment"></c:url>">目标考核</a></li>
					<li>奖惩管理</li>	
				</ul>
			</div>

			<div id="content_container">
				<div class="normal_tab">

				  	<ul>
					    <li><a  href="<c:url value='/securityinfo/queryAssessment'></c:url>">考核指标</a></li>

					    <li><a class="selected" href="<c:url value='/securityinfo/queryRewardPunishment'></c:url>">奖惩管理</a></li>

						<li><a href="<c:url value='/securityinfo/queryRectification'></c:url>">整改管理</a></li>

				  	</ul>

			    </div>
			    <div class="search_container">

			    	<form action="<c:url value='/securityinfo/queryRewardPunishment'></c:url>" method="get" id="rewardpunish_form">


			    			<div class="single_filter_container left" style="width:516px;">

					    		<label class="search_name">所属机构:</label>

					    		<div class="left single_filter">

						    			<select id="select_rewardpunish_cond" name="rewardPunishmentCondition.belongToOrgId" class="normalselect">

							    			<c:forEach items="${belongToOrgList}" var="lv">

							    				<c:choose>

							    					<c:when test="${lv.id eq belongToOrgId }">

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

					    	<div class="single_filter_container left" style="width:516px;">

					    		<label class="search_name" style="margin-left:10px;">开始时间:</label>

					    		<div class="left single_filter">

					    			<input type="text" value="<fmt:formatDate value="${rewardPunishmentCondition.beginTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" name="rewardPunishmentCondition.beginTime" class="normaltext" id="starttime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endtime\')}' })" style="width:200px;"/>

									<a href="javascript:void(0)" class="underlinenone">

										<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',el:'starttime',maxDate:'#F{$dp.$D(\'endtime\')}' })" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />

									</a>

					    		</div>
					    	</div>
					    	<div class="single_filter_container left" style="width:516px;">

					    		<label class="search_name" style="margin-left:10px;">结束时间:</label>

					    		<div class="left single_filter">

					    			<input type="text" value="<fmt:formatDate value="${rewardPunishmentCondition.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" name="rewardPunishmentCondition.endTime" class="normaltext" id="endtime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'starttime\')}' })" style="width:200px;"/>

									<a href="javascript:void(0)" class="underlinenone">

										<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',el:'endtime',minDate:'#F{$dp.$D(\'starttime\')}' })" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />

									</a>

					    		</div>

					    	</div>
				    		<div class="clear"></div>
					    	<div class="single_filter_container left" style="width:516px;">

					    		<label class="search_name" style="margin-left:10px;">奖惩名称:</label>

					    		<div class="left single_filter">

					    			<input type="text" name="rewardPunishmentCondition.name" value="${rewardPunishmentCondition.name }" id="" class="normaltext validate[maxSize[50]]"  style="width:378px;"/>					    						    		

					    		</div>

					    	</div>
					    	<div class="single_filter_container left" style="width:516px;">

					    		<label class="search_name" style="margin-left:10px;">实施部门:</label>

					    		<div class="left single_filter">
					    			<input type="text" name="rewardPunishmentCondition.implementOrg" value="${rewardPunishmentCondition.implementOrg}"  id="" class="normaltext validate[maxSize[100]]" style="width:378px;"/>					    							    		
					    		</div>
					    	</div>				 
 						<div class="clear"></div>
				    	<div>
				    		<div class="single_filter_container left" style="margin-left: 20px;">
				    			<input type="submit" value="查询" class="btn">
				    			<a href="<c:url value='/securityinfo/entryCreateRewardPunish?${rewardPunishmentCondition.searchUri }'></c:url>" class="hrefbtn">创建</a>
				    		</div>	
				    		<div class="clear"></div>		    	
				    	</div>

			    	</form>

			    </div>

			    <div class="normalajaxloading" id="data_loading"></div>

			    <div class="tableui_container">				

					<table class="tableui" id="rewardPunishTableContainer">
					
				    	<thead>

					    	<tr>       

						        <th><span>所属机构</span></th>
								<th><span>奖惩名称</span></th>
						        <th><span>奖惩部门</span></th>

								<th><span>奖惩描述</span></th>
								
								<th><span>奖惩时间</span></th>
								
								<th><span>奖惩级别</span></th>
								<th><span>实施部门</span></th>	
								<th><span>奖惩备注</span></th>							
								<th><span>操作</span></th>

					        </tr>

				        </thead>

				        <tbody>
				        	<c:forEach items="${rewardPunishList}" var="rewardPunish" varStatus="index">			        		
				        	<tr>        

							        <td ><c:out value="${rewardPunish.belongToOrgName }"></c:out></td>
							        								
									<td ><c:out value="${rewardPunish.name}"></c:out></td>
							
									<td><c:out value="${rewardPunish.rpOrg }"></c:out></td>																											
									<td><c:out value="${rewardPunish.description }"></c:out></td>																											
									<td><fmt:formatDate value="${rewardPunish.time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>																											
									<td><c:out value="${rewardPunish.rplevel }"></c:out></td>																											
									<td><c:out value="${rewardPunish.implementOrg }"></c:out></td>																											
									<td><c:out value="${rewardPunish.note}"></c:out></td>																																																		        
									<td align="center">
											<a href="<c:url value='/securityinfo/entryUpdateRewardPunish?rewardPunishment.name=${rewardPunish.name }&rewardPunishment.belongToOrgId=${rewardPunish.belongToOrgId }&${rewardPunishmentCondition.searchUri }'></c:url>" title="编辑" class="edit_yinpin editlink">
												编辑
											</a>										
											<a href="javascript:void(0);" name="${rewardPunish.name}" belongToOrgId="${rewardPunish.belongToOrgId }"  title="删除" class="delete deleterewardpunishment deletelink" >删除</a>						
 									</td>
								</tr>

				        	</c:forEach>

				        </tbody>

				    </table>

				</div>


			</div>
		</div>
		<input type="hidden" id="searchUri" value="${rewardPunishmentCondition.searchUri }">		
		<c:import url="../../layout/footer.jsp"  charEncoding="UTF-8"></c:import>
	</body>

</html>