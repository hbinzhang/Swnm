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
		<script src="${pageContext.request.contextPath}/resources/js/inspection.js" type="text/javascript"></script>

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
					<li><a href="<c:url value="/securityinfo/queryInspection"></c:url>">安全督查</a></li>
				</ul>
			</div>

			<div id="content_container">
	
			    <div class="search_container">

			    	<form action="<c:url value='/securityinfo/queryInspection'></c:url>" method="get" id="inspection_form">


			    			<div class="single_filter_container left" style="width:516px;">

					    		<label class="search_name" >所属机构:</label>

					    		<div class="left single_filter">

						    			<select id="select_inspection_cond" name="inspectionCondition.belongToOrgId" class="normalselect">

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

					    		<label class="search_name" >督查名称:</label>

					    		<div class="left single_filter">

					    			<input type="text" name="rectificationCondition.name" value="${rectificationCondition.name }" id="" class="normaltext validate[maxSize[50]]"  style="width:378px;"/>					    						    		

					    		</div>

					    	</div>
					    	<div class="single_filter_container left" style="width:516px;">

					    		<label class="search_name" style="">督查时间:</label>

					    		<div class="left single_filter">

					    			<input type="text" name="rectificationCondition.time" value="${rectificationCondition.time }" id="" class="normaltext validate[maxSize[50]]"  style="width:378px;"/>					    						    		

					    		</div>

					    	</div>	
				    	  	
					    	<div class="clear"></div>
					  		
					    	<div class="single_filter_container left" style="width:516px;">

					    		<label class="search_name" style="">执行部门:</label>

					    		<div class="left single_filter">
					    			<input type="text" name="rectificationCondition.initiatingOrg" value="${rectificationCondition.initiatingOrg}"  id="" class="normaltext validate[maxSize[100]]" style="width:378px;"/>					    							    		
					    		</div>
					    	</div>				 
 						
					    	<div class="single_filter_container left" style="width:516px;">

					    		<label class="search_name" style="">目标部门:</label>

					    		<div class="left single_filter">
					    			<input type="text" name="rectificationCondition.implementOrg" value="${rectificationCondition.implementOrg}"  id="" class="normaltext validate[maxSize[100]]" style="width:378px;"/>					    							    		
					    		</div>
					    	</div>		
					   	 
 						<div class="clear"></div>
				    	<div>
				    		<div class="single_filter_container left" style="margin-left: 20px;">
				    			<input type="submit" value="查询" class="btn">
				    			<a href="<c:url value='/securityinfo/entryCreateInspection?${inspectionCondition.searchUri }'></c:url>" class="hrefbtn">创建</a>
				    		</div>	
				    		<div class="clear"></div>		    	
				    	</div>

			    	</form>

			    </div>

			    <div class="normalajaxloading" id="data_loading"></div>

			    <div class="tableui_container">				

					<table class="tableui" id="inspectionTableContainer">
					
				    	<thead>

					    	<tr>       
						        <th><span>所属机构</span></th>
						        <th><span>督查名称</span></th>	
						        <th><span>督查时间</span></th>							
						        <th><span>督查内容</span></th>
						        <th><span>执行部门</span></th>
								<th><span>督查执行人员</span></th>								
								<th><span>目标部门</span></th>							
								<th><span>督查结果</span></th>
								<!-- <th><span>事件原因</span></th>	
								<th><span>事件过程</span></th>	
								<th><span>事件结果</span></th>	
								<th><span>影响范围</span></th> -->
								<th><span>督查处理情况</span></th>
								
								<!-- <th><span>补救措施</span></th>	
								<th><span>补救结果</span></th>	
								<th><span>事后评估</span></th>	 -->				
								<th><span>操作</span></th>

					        </tr>

				        </thead>

				        <tbody>
				        	<c:forEach items="${inspectionList}" var="inspection" varStatus="index">			        		
				        	<tr>        						        								
								    <td id="inspection${index.count }_shijianmingcheng">
							        	<a href="javascript:void(0);"  inspectionid="${index.count }" class="detail inspection_detail" ><c:out value="${inspection.name}"></c:out></a>
							        </td>
							        <td id="inspection${index.count }_suoshujigou" ><c:out value="${inspection.belongToOrgName }"></c:out></td>						        
									<td id="inspection${index.count }_fashengshijian" ><c:out value="${inspection.time }"></c:out></td>
									<td id="inspection${index.count }_shijiandidian" ><c:out value="${inspection.position }"></c:out></td>																																																														
									<td id="inspection${index.count }_suoshuquyubumen" ><c:out value="${inspection.inspectionBelongToOrg }"></c:out></td>		
									<td id="inspection${index.count }_shijiandingji" ><c:out value="${inspection.inspectionLevel }"></c:out></td>																									
									<td style="display: none;" id="inspection${index.count }_shijianyuanyin" ><c:out value="${inspection.reason }"></c:out></td>																											
									<td style="display: none;" id="inspection${index.count }_shijianguocheng" ><c:out value="${inspection.process }"></c:out></td>
									<td style="display: none;" id="inspection${index.count }_shijianjieguo" ><c:out value="${inspection.result }"></c:out></td>																											
									<td style="display: none;" id="inspection${index.count }_yingxiangfanwei" ><c:out value="${inspection.influenceRange}"></c:out></td>	
									<td id="inspection${index.count }_zerenbumen" ><c:out value="${inspection.inChargeOrg}"></c:out></td>
									<td id="inspection${index.count }_zerenbumenfuzeren" ><c:out value="${inspection.inChargePerson}"></c:out></td>
									<td style="display: none;" id="inspection${index.count }_bujiucuoshi" ><c:out value="${inspection.remedialMeasures}"></c:out></td>
									<td style="display: none;" id="inspection${index.count }_bujiujieguo" ><c:out value="${inspection.remedialResult}"></c:out></td>
									<td style="display: none;" id="inspection${index.count }_shihoupinggu" ><c:out value="${inspection.postEvaluation}"></c:out></td>																																																	        
									<td align="center">
											<a href="<c:url value='/securityinfo/entryUpdateInspection?inspection.name=${inspection.name }&inspection.belongToOrgId=${inspection.belongToOrgId }&${inspectionCondition.searchUri }'></c:url>" title="编辑" class="edit_yinpin editlink">
												编辑
											</a>										
											<a href="javascript:void(0);" name="${inspection.name}" belongToOrgId="${inspection.belongToOrgId }"  title="删除" class="delete deleteinspection deletelink" >删除</a>						
 									</td>
								</tr>

				        	</c:forEach>

				        </tbody>

				    </table>

				</div>


			</div>
		</div>
		<input type="hidden" id="searchUri" value="${inspectionCondition.searchUri }">		
		<c:import url="../../layout/footer.jsp"  charEncoding="UTF-8"></c:import>
		<!-- 	<div id="inspection_detail_container" style="display: none;z-index:0;" wid="70">

			<div class='dialogModal_header'>安全事件详细信息</div>

			<div class='dialogModal_content'>

				<table class="detail_table">

					<tbody>
					
						<tr>

							<td class="title_name">事件名称:</td>

							<td class="content content_shijianmingcheng"  colspan="4"></td>
						</tr>
						<tr>

							<td class="title_name">所属机构:</td>

							<td class="content content_suoshujigou" ></td>
						</tr>
						<tr>
							<td class="title_name">发生时间:</td>

							<td class="content content_fashengshijian"></td>

						</tr>

						<tr>

							<td class="title_name">事件地点:</td>

							<td class="content content_shijiandidian" colspan="4"></td>
						</tr>
						<tr>

							<td class="title_name">所属区域和管理部门:</td>

							<td class="content content_suoshuquyubumen" colspan="4"></td>
						</tr>
						<tr>

							<td class="title_name">事件定级:</td>

							<td class="content content_shijiandingji" colspan="4"></td>
						</tr>
						<tr>
							<td class="title_name">事件原因:</td>

							<td class="content content_shijianyuanyin" colspan="4"></td>
						</tr>

						<tr>

							<td class="title_name">事件过程:</td>

							<td class="content content_shijianguocheng" colspan="4"></td>
						</tr>
						<tr>

							<td class="title_name">事件结果:</td>

							<td class="content content_shijianjieguo" colspan="4"></td>
						</tr>

						<tr>

							<td class="title_name">影响范围:</td>

							<td class="content content_yingxiangfanwei" colspan="4"></td>
						</tr>
						<tr>

							<td class="title_name">责任部门:</td>

							<td class="content content_zerenbumen" colspan="4"></td>

						</tr>
						<tr>

							<td class="title_name">责任部门责任人:</td>

							<td class="content content_zerenbumenfuzeren" colspan="4"></td>
						</tr>
						<tr>

							<td class="title_name">补救措施:</td>

							<td class="content content_bujiucuoshi"  colspan="4"></td>

						</tr>
						<tr>

							<td class="title_name">补救结果:</td>

							<td class="content content_bujiujieguo" colspan="4"></td>

						</tr>
						<tr>

							<td class="title_name">事后评估:</td>

							<td class="content content_shihoupinggu" colspan="4"></td>

						</tr>
					<tbody>

				</table>

			</div>

			<div class="left20 top10" style="padding-bottom:20px;">

				<input class="left20 btn" data-dialogModalBut="cancel" type="button" value="关闭"/>

			</div>

		</div> -->
	</body>

</html>