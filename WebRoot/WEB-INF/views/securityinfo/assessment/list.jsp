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
		<script src="${pageContext.request.contextPath}/resources/js/assessment.js" type="text/javascript"></script>
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
					<li>考核指标</li>					
				</ul>
			</div>

			<div id="content_container">

				<div class="normal_tab">

				  	<ul>
					    <li><a class="selected" href="<c:url value='/securityinfo/queryAssessment'></c:url>">考核指标</a></li>

					    <li><a href="<c:url value='/securityinfo/queryRewardPunishment'></c:url>">奖惩管理</a></li>

						<li><a href="<c:url value='/securityinfo/queryRectification'></c:url>">整改管理</a></li>

				  	</ul>

			    </div>
			    <div class="search_container">

			    	<form action="<c:url value='/securityinfo/queryAssessment'></c:url>" method="get" id="assessment_form">

			    		<div>

			    			<div class="single_filter_container left">

					    		<label class="search_name">考核机构:</label>

					    			<div class="left single_filter">

						    			<select id="select_assessment_cond" name="orgId" class="normalselect">

							    			<c:forEach items="${orgList}" var="lv">

							    				<c:choose>

							    					<c:when test="${lv.id eq orgId }">

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

					    	</div>					
				
				    	<div>
				    		<div class=" left" style="margin-left: 20px;">
				    			<input type="submit" value="查询" class="btn">
				    			<a href="<c:url value='/securityinfo/queryAssessResult4Org?orgId=${orgId }'></c:url>" class="hrefbtn">查看机构考核成绩</a>			    			
				    			<a href="<c:url value='/securityinfo/entryCreateAssessment'></c:url>" class="hrefbtn">创建</a>
				    		</div>	
				    		<div class="clear"></div>		    	
				    	</div>

			    	</form>

			    </div>

			    <div class="normalajaxloading" id="data_loading"></div>

			    <div class="tableui_container">				

					<table class="tableui" id="assessmentTableContainer">
					
				    	<thead>

					    	<tr>       

						        <th><span>考核机构</span></th>						       
						        <th><span>指标名称</span></th>
						        
								<th><span>考核机构责任人</span></th>

								<th><span>指标目标</span></th>

								<th><span>指标权重</span></th>

								<th><span>考评结果</span></th>
								
								<th><span>考核人员</span></th>
																
								<th><span>评价说明</span></th>
															
								<th><span>操作</span></th>

					        </tr>

				        </thead>

				        <tbody>
				        	<c:forEach items="${assessmentList}" var="assessment" varStatus="index">
				        		
				        		<tr>        

							        <td ><c:out value="${assessment.orgName }"></c:out></td>
							        								
									<td ><c:out value="${assessment.name}"></c:out></td>
							
									<td><c:out value="${assessment.orgPersonName }"></c:out></td>																											
									<td><c:out value="${assessment.target }"></c:out></td>																											
									<td><c:out value="${assessment.percent }"></c:out></td>																											
									<td><c:out value="${assessment.result }"></c:out></td>																											
									<td><c:out value="${assessment.reviewerName }"></c:out></td>																											
									<td><c:out value="${assessment.note}"></c:out></td>																																																		        
									<td align="center">
											<a href="<c:url value='/securityinfo/entryUpdateAssessment?assessment.name=${assessment.name }&assessment.orgId=${assessment.orgId }'></c:url>" title="编辑" class="edit_yinpin editlink">
												编辑
											</a>
											<a href="<c:url value='/securityinfo/entryCheckInfo?assessment.name=${assessment.name }&assessment.orgId=${assessment.orgId }'></c:url>" title="检查情况" class="edit_yinpin editlink">
												检查情况
											</a>	 
											<a href="<c:url value='/securityinfo/entryEvaluation?assessment.name=${assessment.name }&assessment.orgId=${assessment.orgId }'></c:url>" title="评价" class="edit_yinpin editlink">
												评价
											</a>	
										
											<a href="javascript:void(0);" name="${assessment.name}" orgId="${assessment.orgId }"  title="通知" class="noticeassessment editlink" >通知</a>						
										
											<a href="javascript:void(0);" name="${assessment.name}" orgId="${assessment.orgId }"  title="删除" class="delete deleteassessment deletelink" >删除</a>						
 									</td>
								</tr>

				        	</c:forEach>

				        </tbody>

				    </table>

				</div>

			</div>
		</div>
		<c:import url="../../layout/footer.jsp"  charEncoding="UTF-8"></c:import>
	</body>

</html>