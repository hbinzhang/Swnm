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
		<script src="${pageContext.request.contextPath}/resources/js/taskbook.js" type="text/javascript"></script>

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
					<li><a href="<c:url value="/securityinfo/queryTaskBook"></c:url>">安防任务</a></li>
				</ul>
			</div>

			<div id="content_container">

			    <div class="search_container">

			    	<form action="<c:url value='/securityinfo/queryTaskBook'></c:url>" method="get" id="taskbook_form">

			    		<div>

			    			<div class="single_filter_container left">

					    		<label class="search_name">查询条件:</label>

					    			<div class="left single_filter">

						    			<select id="select_taskbook_cond" name="taskBookCondition.queryTaskBookFlag" class="normalselect">

							    				<c:choose>
							    					<c:when test="${taskBookCondition.queryTaskBookFlag == 1}">

					    								<option selected="selected" value="1">任务发布时间</option>

					    								<option value="2">任务启动时间</option>
					    								
					    								<option value="3">任务结束时间</option>
								    				</c:when>
													<c:when test="${taskBookCondition.queryTaskBookFlag == 2}">

					    								<option value="1">任务发布时间</option>

					    								<option  selected="selected" value="2">任务启动时间</option>
					    								
					    								<option value="3">任务结束时间</option>
								    				</c:when>
								    	
								    				<c:otherwise>
								    					
					    								<option value="1">任务发布时间</option>

					    								<option value="2">任务启动时间</option>
					    								
					    								<option selected="selected" value="3">任务结束时间</option>

								    				</c:otherwise>

							    				</c:choose>


						    			</select>

						    		</div>

					    	</div>

					    		<div class="single_filter_container left">

					    		<label class="search_name">开始时间:</label>

					    		<div class="left single_filter">

					    			<input type="text" value="<fmt:formatDate value="${taskBookCondition.beginTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" name="taskBookCondition.beginTime" class="normaltext" id="starttime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endtime\')}' })" style="width:200px;"/>

									<a href="javascript:void(0)" class="underlinenone">

										<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',el:'starttime',maxDate:'#F{$dp.$D(\'endtime\')}' })" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />

									</a>

					    		</div>

					    	</div>

					    	<div class="single_filter_container left">

					    		<label class="search_name">结束时间:</label>

					    		<div class="left single_filter">

					    			<input type="text" value="<fmt:formatDate value="${taskBookCondition.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" name="taskBookCondition.endTime" class="normaltext" id="endtime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'starttime\')}' })" style="width:200px;"/>

									<a href="javascript:void(0)" class="underlinenone">

										<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',el:'endtime',minDate:'#F{$dp.$D(\'starttime\')}' })" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />

									</a>

					    		</div>

					    	</div>

					    </div>
						<div>

				    		<div class="clear"></div>

				    	</div>
				    	
					    <div>
					    
					    	<div class="single_filter_container left">

					    		<label class="search_name">任务类型:</label>

					    		<div class="left single_filter">

					    			<select id="" name="taskBookCondition.type" class="normalselect">

					    				<c:choose>

					    					<c:when test="${taskBookCondition.type==-1 }">
					    						<option selected="selected" value="-1">全部</option>
					    						<option value="0">月度任务</option>
					    						<option value="1">季度任务</option>
					    						<option value="2">年度任务</option>
					    					</c:when>

					    					<c:when test="${taskBookCondition.type==0 }">
												<option value="-1">全部</option>
					    						<option selected="selected" value="0">月度任务</option>
					    						<option value="1">季度任务</option>
					    						<option value="2">年度任务</option>
					    					</c:when>

											<c:when test="${taskBookCondition.type==1 }">
												<option value="-1">全部</option>
					    						<option value="0">月度任务</option>
					    						<option selected="selected" value="1">季度任务</option>
					    						<option value="2">年度任务</option>
					    					</c:when>
				    					
					    					<c:otherwise>
												<option value="-1">全部</option>
					    						<option value="0">月度任务</option>
					    						<option value="1">季度任务</option>
					    						<option selected="selected" value="2">年度任务</option>
					    					</c:otherwise>

					    				</c:choose>

					    			</select>

					    		</div>

					    	</div>

					    	<div class="single_filter_container left">

					    		<label class="search_name">任务状态:</label>

					    		<div class="left single_filter">

					    			<select id="" name="taskBookCondition.state" class="normalselect">

					    				<c:choose>

					    					<c:when test="${taskBookCondition.state==-1}">
					    						<option selected="selected" value="-1">全部</option>
					    						<option value="0">未发布</option>
					    						<option value="1">已发布</option>
												<option value="2">已关闭</option>
					    					</c:when>

					    					<c:when test="${taskBookCondition.state==0}">
												<option value="-1">全部</option>
					    						<option selected="selected" value="0">未发布</option>
					    						<option value="1">已发布</option>
												<option value="2">已关闭</option>
					    					</c:when>
											<c:when test="${taskBookCondition.state==1}">
												<option value="-1">全部</option>
					    						<option value="0">未发布</option>
					    						<option selected="selected" value="1">已发布</option>
												<option value="2">已关闭</option>
					    					</c:when>
					    					<c:otherwise>
												<option value="-1">全部</option>
					    						<option value="0">未发布</option>
					    						<option value="1">已关闭</option>
												<option selected="selected" value="2">已关闭</option>
					    					</c:otherwise>

					    				</c:choose>

					    			</select>

					    		</div>

					    	</div>
					    		<div class="single_filter_container left">

						    		<label class="search_name">制定机构:</label>

						    		<div class="left single_filter">

						    			<select id="select_taskbook_dingzhijigou" name="taskBookCondition.planOrgId" class="normalselect">

						    				<c:forEach items="${planOrgList}" var="lv">

							    				<c:choose>

							    					<c:when test="${lv.id eq taskBookCondition.planOrgId }">

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

						    		<label class="search_name">责任机构:</label>

						    		<div class="left single_filter">

						    			<select id="select_taskbook_zerenjigou" name="taskBookCondition.inChargeOrgId" class="normalselect">
											<c:forEach items="${inChargeOrgList}" var="lv">

							    				<c:choose>

							    					<c:when test="${lv.id eq taskBookCondition.inChargeOrgId }">

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
						    <div class="clear"></div>
						    </div>
						<div>
				    		

				    	</div>
				    	<div>
				    		<div class="single_filter_container left" style="margin-left: 20px;">
				    			<input type="submit" value="查询" class="btn">
				    			<a href="<c:url value='/securityinfo/entryCreateTaskBook?${taskBookCondition.searchUri }'></c:url>" class="hrefbtn">创建</a>
				    		</div>	
				    		<div class="clear"></div>		    	
				    	</div>

			    	</form>

			    </div>

			    <div class="normalajaxloading" id="data_loading"></div>

			    <div class="tableui_container">				

					<table class="tableui" id="taskBookTableContainer">
					
				    	<thead>

					    	<tr>       

						        <th><span>任务名称</span></th>
								<th><span>制定机构</span></th>
						        <th><span>任务目标</span></th>

								<th><span>任务类型</span></th>

								<th><span>责任机构</span></th>

								<th><span>责任机构负责人</span></th>
								
								<th><span>任务状态</span></th>
																
								<th><span>发布时间</span></th>
								
								<th><span>启动时间</span></th>
								
								<th><span>结束时间</span></th>
																
								<th><span>操作</span></th>

					        </tr>

				        </thead>

				        <tbody>
				        	<c:forEach items="${taskBookList}" var="taskbook" varStatus="index">
				        		
				        		<tr>        

							        <td id="taskbook${index.count }_renwuming">

							        	<a href="javascript:void(0);"  taskbookid="${index.count }" class="detail taskbook_detail" ><c:out value="${taskbook.name}"></c:out></a>

							        </td>
							        <td id="taskbook${index.count }_zhidingjigou"><c:out value="${taskbook.planOrgName }"></c:out></td>
							        
									<td id="taskbook${index.count }_mubiao"><c:out value="${taskbook.target }"></c:out></td>
									<c:choose>
											<c:when test="${taskbook.type == 0 }">
												<td id="taskbook${index.count }_renwuleixing"><c:out value="月度任务"></c:out></td>
											</c:when>
											<c:when test="${taskbook.type == 1 }">
												<td id="taskbook${index.count }_renwuleixing"><c:out value="季度任务"></c:out></td>
											</c:when>
											<c:otherwise>
												<td id="taskbook${index.count }_renwuleixing"><c:out value="年度任务"></c:out></td>
											</c:otherwise>
									</c:choose>		
									<td id="taskbook${index.count }_zerenjigou"><c:out value="${taskbook.inChargeOrgName }"></c:out></td>
									<td id="taskbook${index.count }_zerenjigoufuzeren"><c:out value="${taskbook.inChargeOrgPersonName }"></c:out></td>	
									<c:choose>
											<c:when test="${taskbook.state == 0 }">
												<td id="taskbook${index.count }_renwuzhuangtai"><c:out value="未发布"></c:out></td>
											</c:when>
											<c:when test="${taskbook.state == 1 }">
												<td id="taskbook${index.count }_renwuzhuangtai"><c:out value="已发布"></c:out></td>
											</c:when>
											<c:otherwise>
												<td id="taskbook${index.count }_renwuzhuangtai"><c:out value="已关闭"></c:out></td>
											</c:otherwise>
									</c:choose>	
									<td id="taskbook${index.count }_fabushijian"><fmt:formatDate value="${taskbook.issueTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>									
									<td id="taskbook${index.count }_qidongshijian"><fmt:formatDate value="${taskbook.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>									
									<td id="taskbook${index.count }_jieshushijian"><fmt:formatDate value="${taskbook.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>														
									<td style="display: none;" id="taskbook${index.count }_renwuneirong"><c:out value="${taskbook.content }"></c:out></td>
									<td id="taskbook${index.count }_zhidingjigoufuzeren" style="display: none;"><c:out value="${taskbook.planOrgPersonName }"></c:out></td>																											
									<td id="taskbook${index.count }_zhidingshijian" style="display: none;"><fmt:formatDate value="${taskbook.planTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>									
									
									<td id="taskbook${index.count }_beizhu" style="display: none;"><c:out value="${taskbook.note }"></c:out></td>									
															        
									<td align="center">
										<c:if test="${taskbook.state==0 }">									
											<a href="<c:url value='/securityinfo/entryUpdateTaskBook?taskBook.name=${taskbook.name }&taskBook.planOrgId=${taskbook.planOrgId}&${taskBookCondition.searchUri }'></c:url>" title="编辑" class="edit_yinpin editlink">
												编辑
											</a>
										</c:if>								
										<c:if test="${taskbook.state==0 }">
											<a href="javascript:void(0);"  name="${taskbook.name}" planOrgId="${taskbook.planOrgId}"  title="发布"  class="edit publishtaskbook editlink" >发布</a>																
										</c:if>
										<c:if test="${taskbook.state==1 }">
											<a href="javascript:void(0);" name="${taskbook.name}" planOrgId="${taskbook.planOrgId}"  title="关闭" class="edit closetaskbook editlink" >关闭</a>																
										</c:if>
										<c:if test="${taskbook.state==0 || taskbook.state==2  }">
											<a href="javascript:void(0);" name="${taskbook.name}" planOrgId="${taskbook.planOrgId}"  title="删除" class="delete deletetaskbook deletelink" >删除</a>						
										</c:if>
 									</td>
								</tr>

				        	</c:forEach>

				        </tbody>

				    </table>

				</div>

				<input type="hidden" id="searchUri" value="${taskBookCondition.searchUri }">

			</div>
		</div>
		<c:import url="../../layout/footer.jsp"  charEncoding="UTF-8"></c:import>
		<div id="taskbook_detail_container" style="display: none;z-index:0;" wid="70">

			<div class='dialogModal_header'>任务书详细信息</div>

			<div class='dialogModal_content'>

				<table class="detail_table">

					<tbody>

						<tr>

							<td class="title_name">任务名称:</td>

							<td class="content content_renwuming"  colspan="4"></td>
						</tr>
						<tr>

							<td class="title_name">任务目标:</td>

							<td class="content content_mubiao"  colspan="4"></td>
						</tr>
						<tr>
							<td class="title_name">任务内容:</td>

							<td class="content content_renwuneirong"  colspan="4"></td>

						</tr>

						<tr>

							<td class="title_name">任务类型:</td>

							<td class="content content_renwuleixing"></td>

							<td class="title_name">任务状态:</td>

							<td class="content content_renwuzhuangtai"></td>
						</tr>
						<tr>

							<td class="title_name">制定时间:</td>

							<td class="content content_zhidingshijian"></td>
							<td class="title_name">发布时间:</td>

							<td class="content content_fabushijian"></td>
						</tr>

						<tr>

							<td class="title_name">启动时间:</td>

							<td class="content content_qidongshijian" ></td>

							<td class="title_name">结束时间:</td>

							<td class="content content_jieshushijian"></td>
						</tr>

						<tr>

							<td class="title_name">制定机构:</td>

							<td class="content content_zhidingjigou"></td>

							<td class="title_name">制定机构负责人:</td>

							<td class="content content_zhidingjigoufuzeren"></td>

						</tr>
						<tr>

							<td class="title_name">责任机构:</td>

							<td class="content content_zerenjigou"></td>

							<td class="title_name">责任机构负责人:</td>

							<td class="content content_zerenjigoufuzeren" ></td>

						</tr>
						<tr>

							<td class="title_name">备注:</td>

							<td class="content content_beizhu" colspan="4"></td>

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