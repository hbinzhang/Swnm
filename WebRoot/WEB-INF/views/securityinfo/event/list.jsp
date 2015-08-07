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
		<script src="${pageContext.request.contextPath}/resources/js/event.js" type="text/javascript"></script>

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
					<li><a href="<c:url value="/securityinfo/queryEvent"></c:url>">安全事件</a></li>
				</ul>
			</div>

			<div id="content_container">
	
			    <div class="search_container">

			    	<form action="<c:url value='/securityinfo/queryEvent'></c:url>" method="get" id="event_form">


			    			<div class="single_filter_container left" style="width:516px;">

					    		<label class="search_name" >所属机构:</label>

					    		<div class="left single_filter">

						    			<select id="select_event_cond" name="eventCondition.belongToOrgId" class="normalselect">

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

					    		<label class="search_name"  style="margin-left:10px;">开始时间:</label>

					    		<div class="left single_filter">

					    			<input type="text" value="<fmt:formatDate value="${eventCondition.beginTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" name="eventCondition.beginTime" class="normaltext" id="starttime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endtime\')}' })" style="width:198px;"/>

									<a href="javascript:void(0)" class="underlinenone">

										<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',el:'starttime',maxDate:'#F{$dp.$D(\'endtime\')}' })" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />

									</a>

					    		</div>
					    	</div>
					    	<div class="single_filter_container left" style="width:516px;">

					    		<label class="search_name" style="margin-left:10px;">结束时间:</label>

					    		<div class="left single_filter">

					    			<input type="text" value="<fmt:formatDate value="${eventCondition.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" name="eventCondition.endTime" class="normaltext" id="endtime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'starttime\')}' })" style="width:198px;"/>

									<a href="javascript:void(0)" class="underlinenone">

										<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',el:'endtime',minDate:'#F{$dp.$D(\'starttime\')}' })" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />

									</a>

					    		</div>

					    	</div> 
					    
							
					    	<div class="clear"></div>
					    		
					 		 <div class="single_filter_container left" style="width:516px;">

					    		<label class="search_name" >事件名称:</label>

					    		<div class="left single_filter">

					    			<input type="text" name="eventCondition.name" value="${eventCondition.name }" id="" class="normaltext validate[maxSize[50]]"  style="width:378px;"/>					    						    		

					    		</div>

					    	</div>
					    	<div class="single_filter_container left" style="width:516px;">

					    		<label class="search_name" style="">事件地点:</label>

					    		<div class="left single_filter">
					    			<input type="text" name="eventCondition.position" value="${eventCondition.position}"  id="" class="normaltext validate[maxSize[100]]" style="width:378px;"/>					    							    		
					    		</div>
					    	</div>				 
					    	<div class="single_filter_container left" style="width:516px;">

					    		<label class="search_name" style="">责任部门:</label>

					    		<div class="left single_filter">
					    			<input type="text" name="eventCondition.inChargeOrg" value="${eventCondition.inChargeOrg}"  id="" class="normaltext validate[maxSize[100]]" style="width:378px;"/>					    							    		
					    		</div>
					    	</div>	
					    	<div class="single_filter_container left" style="width:516px;">

					    		<label class="search_name" style="">事件定级:</label>

					    		<div class="left single_filter">
					    			<input type="text" name="eventCondition.eventLevel" value="${eventCondition.eventLevel}"  id="" class="normaltext validate[maxSize[50]]" style="width:378px;"/>					    							    		
					    		</div>
					    	</div>				 
 						<div class="clear"></div>
				    	<div>
				    		<div class="single_filter_container left" style="margin-left: 20px;">
				    			<input type="submit" value="查询" class="btn">
				    			<a href="<c:url value='/securityinfo/entryCreateEvent?${eventCondition.searchUri }'></c:url>" class="hrefbtn">创建</a>
				    		</div>	
				    		<div class="clear"></div>		    	
				    	</div>

			    	</form>

			    </div>

			    <div class="normalajaxloading" id="data_loading"></div>

			    <div class="tableui_container">				

					<table class="tableui" id="eventTableContainer">
					
				    	<thead>

					    	<tr>       
								<th><span>事件名称</span></th>							
						        <th><span>所属机构</span></th>
						        <th><span>发生时间</span></th>
								<th><span>事件地点</span></th>								
								<th><span>所属区域和管理部门</span></th>							
								<th><span>事件定级</span></th>
								<!-- <th><span>事件原因</span></th>	
								<th><span>事件过程</span></th>	
								<th><span>事件结果</span></th>	
								<th><span>影响范围</span></th> -->
								<th><span>责任部门</span></th>
								<th><span>责任部门责任人</span></th>	
								<!-- <th><span>补救措施</span></th>	
								<th><span>补救结果</span></th>	
								<th><span>事后评估</span></th>	 -->				
								<th><span>操作</span></th>

					        </tr>

				        </thead>

				        <tbody>
				        	<c:forEach items="${eventList}" var="event" varStatus="index">			        		
				        	<tr>        						        								
								    <td id="event${index.count }_shijianmingcheng">
							        	<a href="javascript:void(0);"  eventid="${index.count }" class="detail event_detail" ><c:out value="${event.name}"></c:out></a>
							        </td>
							        <td id="event${index.count }_suoshujigou" ><c:out value="${event.belongToOrgName }"></c:out></td>						        
									<td id="event${index.count }_fashengshijian" ><c:out value="${event.time }"></c:out></td>
									<td id="event${index.count }_shijiandidian" ><c:out value="${event.position }"></c:out></td>																																																														
									<td id="event${index.count }_suoshuquyubumen" ><c:out value="${event.eventBelongToOrg }"></c:out></td>		
									<td id="event${index.count }_shijiandingji" ><c:out value="${event.eventLevel }"></c:out></td>																									
									<td style="display: none;" id="event${index.count }_shijianyuanyin" ><c:out value="${event.reason }"></c:out></td>																											
									<td style="display: none;" id="event${index.count }_shijianguocheng" ><c:out value="${event.process }"></c:out></td>
									<td style="display: none;" id="event${index.count }_shijianjieguo" ><c:out value="${event.result }"></c:out></td>																											
									<td style="display: none;" id="event${index.count }_yingxiangfanwei" ><c:out value="${event.influenceRange}"></c:out></td>	
									<td id="event${index.count }_zerenbumen" ><c:out value="${event.inChargeOrg}"></c:out></td>
									<td id="event${index.count }_zerenbumenfuzeren" ><c:out value="${event.inChargePerson}"></c:out></td>
									<td style="display: none;" id="event${index.count }_bujiucuoshi" ><c:out value="${event.remedialMeasures}"></c:out></td>
									<td style="display: none;" id="event${index.count }_bujiujieguo" ><c:out value="${event.remedialResult}"></c:out></td>
									<td style="display: none;" id="event${index.count }_shihoupinggu" ><c:out value="${event.postEvaluation}"></c:out></td>																																																	        
									<td align="center">
											<a href="<c:url value='/securityinfo/entryUpdateEvent?event.name=${event.name }&event.belongToOrgId=${event.belongToOrgId }&${eventCondition.searchUri }'></c:url>" title="编辑" class="edit_yinpin editlink">
												编辑
											</a>										
											<a href="javascript:void(0);" name="${event.name}" belongToOrgId="${event.belongToOrgId }"  title="删除" class="delete deleteevent deletelink" >删除</a>						
 									</td>
								</tr>

				        	</c:forEach>

				        </tbody>

				    </table>

				</div>


			</div>
		</div>
		<input type="hidden" id="searchUri" value="${eventCondition.searchUri }">		
		<c:import url="../../layout/footer.jsp"  charEncoding="UTF-8"></c:import>
			<div id="event_detail_container" style="display: none;z-index:0;" wid="70">

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

		</div>
	</body>

</html>