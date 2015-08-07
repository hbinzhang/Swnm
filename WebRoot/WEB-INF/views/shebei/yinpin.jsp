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
		<script src="${pageContext.request.contextPath}/resources/js/yinpin.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/plugins/ajaxfileupload/ajaxfileupload.js"></script>
	</head>
	<body>
		<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/fence/queryFence"></c:url>">设备管理</a></li>
					<li><a href="<c:url value="/sounddevEnter"></c:url>">音频设备</a></li>
				</ul>
			</div>
			<div id="content_container">
				<c:import url="../layout/shebeitab.jsp?sTabLink=yinpin" charEncoding="UTF-8"></c:import>
					<div class="search_container">
					<form action="<c:url value='/sounddevQuery'></c:url>" method="get">
							<div class="left" style="margin-top:5px;">
					    		<div class="single_filter_container left">
						    		<label class="search_name">分公司:</label>
						    		<div class="left single_filter">
							    			<select id="normal_gongsi_select" class="normalselect" name="queryCondition.brench">
							    				<c:choose>
							    					<c:when test="${sessionScope.session.lev eq '0' }">
							    						<option value="">全部</option>
							    						<c:forEach items="${sessionScope.session.orgIdAndNames.subCompanys }" var="companyBean">
							    							<c:choose>
							    								<c:when test="${queryCondition.brench eq companyBean.id }">
							    									<option value="${companyBean.id }" selected="selected">${companyBean.name }</option>
							    								</c:when>
							    								<c:otherwise>
							    									<option value="${companyBean.id }">${companyBean.name }</option>
							    								</c:otherwise>
							    							</c:choose>
							    						</c:forEach>
							    					</c:when>
							    					<c:otherwise>
							    						<c:forEach items="${sessionScope.session.orgIdAndNames.subCompanys }" var="companyBean">
							    							<option value="${companyBean.id }">${companyBean.name }</option>
							    						</c:forEach>
							    					</c:otherwise>
							    				</c:choose>
							    			</select>
							    		</div>
						    	</div>
						    	<div class="single_filter_container left">
						    		<label class="search_name">管理处:</label>
						    		<div class="left single_filter">
							    			<select id="normal_guanlichu_select" class="normalselect" name="queryCondition.mgt">
							    				<c:choose>
							    					<c:when test="${sessionScope.session.lev eq '0' }">
							    						<option value="">全部</option>
							    						<c:if test="${queryCondition.brench!='' }">
							    							<c:forEach items="${mgts }" var="departBean">
							    								<c:choose>
							    									<c:when test="${queryCondition.mgt eq departBean.id }">
							    										<option value="${departBean.id }" selected="selected">${departBean.name }</option>
							    									</c:when>
							    									<c:otherwise>
							    										<option value="${departBean.id }">${departBean.name }</option>
							    									</c:otherwise>
							    								</c:choose>
							    							</c:forEach>
							    						</c:if>
							    					</c:when>
							    					<c:when test="${sessionScope.session.lev eq '1' }">
							    						<option value="">全部</option>
							    						<c:forEach items="${sessionScope.session.orgIdAndNames.managements }" var="departBean">
							    							<c:choose>
						    									<c:when test="${queryCondition.mgt eq departBean.id }">
						    										<option value="${departBean.id }" selected="selected">${departBean.name }</option>
						    									</c:when>
						    									<c:otherwise>
						    										<option value="${departBean.id }">${departBean.name }</option>
						    									</c:otherwise>
						    								</c:choose>
							    						</c:forEach>
							    					</c:when>
							    					<c:otherwise>
							    						<c:forEach items="${sessionScope.session.orgIdAndNames.managements }" var="departBean">
							    							<c:choose>
						    									<c:when test="${queryCondition.mgt eq departBean.id }">
						    										<option value="${departBean.id }" selected="selected">${departBean.name }</option>
						    									</c:when>
						    									<c:otherwise>
						    										<option value="${departBean.id }">${departBean.name }</option>
						    									</c:otherwise>
						    								</c:choose>
							    						</c:forEach>
							    					</c:otherwise>
							    				</c:choose>
							    			</select>
							    		</div>
						    	</div>
						    	<div class="single_filter_container left" style="width:520px;">
						    		<label class="search_name">附加条件:</label>
						    		<select class="normalselect" id="yinpin_search_filter" name="queryCondition.filter">
						    			<c:choose>
						    				<c:when test="${queryCondition.filter eq '2' }">
												<option value="1" >设备ID</option>
												<option value="2" selected="selected">IP地址</option>
												<option value="3">设备类型</option>
						    				</c:when>
						    				<c:when test="${queryCondition.filter eq '3' }">
												<option value="1" >设备ID</option>
												<option value="2" >IP地址</option>
												<option value="3" selected="selected">设备类型</option>
						    				</c:when>
						    				<c:otherwise>
						    					<option value="1" selected="selected">设备ID</option>
												<option value="2">IP地址</option>
												<option value="3">设备类型</option>
						    				</c:otherwise>
						    			</c:choose>
									</select>
									<c:choose>
										<c:when test="${queryCondition.filter eq '3'}">
											<select style="width:300px;" class="normalselect" id="shebeileixing" name="queryCondition.devType">
												<c:choose>
								    				<c:when test="${queryCondition.devType eq 'IO控制器' }">
								    					<option value="">全部</option>
														<option value="IO控制器" selected="selected">IO控制器</option>
														<option value="音频终端">音频终端</option>
														<option value="音频服务器">音频服务器</option>
								    				</c:when>
								    				<c:when test="${queryCondition.devType eq '音频终端' }">
								    					<option value="">全部</option>
														<option value="IO控制器" >IO控制器</option>
														<option value="音频终端" selected="selected">音频终端</option>
														<option value="音频服务器">音频服务器</option>
								    				</c:when>
								    				<c:when test="${queryCondition.devType eq '音频服务器' }">
								    					<option value="">全部</option>
														<option value="IO控制器" >IO控制器</option>
														<option value="音频终端">音频终端</option>
														<option value="音频服务器" selected="selected">音频服务器</option>
								    				</c:when>
								    				<c:otherwise>
								    					<option value="" selected="selected">全部</option>
														<option value="IO控制器" >IO控制器</option>
														<option value="音频终端">音频终端</option>
														<option value="音频服务器" >音频服务器</option>
								    				</c:otherwise>
								    			</c:choose>
											</select>
											<input type="text" class="normaltext" name="queryCondition.id" value="" id="shebeiid" style="width:300px;display: none;">
											<input type="text" class="normaltext" name="queryCondition.ipAddress" value="" id="ipdizhi" style="width:300px;display: none;">
										</c:when>
										<c:when test="${queryCondition.filter eq '2' }">
											<select style="width:300px;" class="normalselect clickseclect" id="shebeileixing" style="display: none;" name="queryCondition.devType">
												<option value="" selected="selected">全部</option>
														<option value="1" >IO控制器</option>
														<option value="2">音频终端</option>
														<option value="3" >音频服务器</option>
											</select>
											<input type="text" class="normaltext" name="queryCondition.id" value="${zoneBean.zoneName }" id="shebeiid" style="width:300px;display: none;">
											<input type="text" class="normaltext" name="queryCondition.ipAddress" value="" id="ipdizhi" style="width:300px;">
										</c:when>
										<c:otherwise>
											<select style="width:300px;" class="normalselect clickseclect" id="shebeileixing" style="display: none;" name="queryCondition.devType">
												<option value="" selected="selected">全部</option>
												<option value="IO控制器" >IO控制器</option>
												<option value="音频终端">音频终端</option>
												<option value="音频服务器" >音频服务器</option>
											</select>
											<input type="text" class="normaltext" name="queryCondition.id" value="" id="shebeiid" style="width:300px;">
											<input type="text" class="normaltext" name="queryCondition.ipAddress" value="${zoneBean.zoneID }" id="ipdizhi" style="width:300px;display: none;">
										</c:otherwise>
									</c:choose>
						    	</div>
						    	<div class="clear"></div>
					    	</div>
					    	<div class="left" style="margin-top:5px;">
					    		<input type="submit" class="btn" value="查询" style="margin-left:10px;">
					    		<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'添加音频设备') }">
								<a href="<c:url value='/sounddevAddEnter?${queryCondition.searchUri}&queryCondition.size=10&queryCondition.offset=${page.offset }'></c:url>" class="hrefbtn">添加</a>
								</c:if>
								<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'批量导入音频设备') }">
								<a href="javascript:void(0);" class="hrefbtn" id="importfangqu">批量导入</a>
								</c:if>
								<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'删除音频设备') }">
								<a href="javascript:void(0);" class="hrefbtn" id="deletefangqu">删除</a>
								</c:if>
					    	</div>
					    	<div class="clear"></div>
					    	<div class="top10" style="display: none;margin-left:100px;" id="uploadcontainer">
					    		<input type="file" name="sounddev" id="yinpin">
					    		<input type="button" class="btn" value="开始导入" id="beginimport">
					    		<input type="button" class="btn" value="取消" id="calcelimport">
					    	</div>
						</form>
					</div>
				<div class="normalajaxloading" id="data_loading"></div>
				<div id="tableui_container">
					<table class="tableui" id="yinpin_table">
				    	<thead>
					    	<tr>        
					    		<th width="2%">
					    			<input type="checkbox" id="yinpin_select_all"/>
					    		</th>                          
						        <th><span>设备ID</span></th>
						        <th><span>设备名称</span></th>
						        <th><span>厂商名称</span></th>
						        <th><span>IP地址</span></th>
						        <th><span>设备类型</span></th>
						        <th><span>所属设备</span></th>
						        <th><span>所属设备IP</span></th>
						        <th><span>关联摄像头ID</span></th>
						        <th><span>管理处</span></th>
						       <!--  <th><span>状态</span></th> -->
								<th><span>操作</span></th>
					        </tr>
					   	</thead>
						<tbody>
							<c:forEach items="${page.datas }" var="soundDevResult">
								<tr>
									<td align="center">
										<input type="checkbox" value="${soundDevResult.id}" name="deleteids" id="deleteids"/>
									</td>
							        <td>${soundDevResult.id }</td>
							        <td>${soundDevResult.name }</td>
							        <td>${soundDevResult.vendorName }</td>
							        <td>${soundDevResult.ipAddress }</td>
							        <td>${soundDevResult.devType }</td>
							        <td>${soundDevResult.ownerdev }</td>
							        <td>${soundDevResult.ownerIp }</td>
							        <td>${soundDevResult.ipcCode }</td>
							        <td>${soundDevResult.mgtName }</td>
							       <%--  <td>${soundDevResult.status }</td> --%>
							        <td align="center">
							        	<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'编辑音频设备') }">
										<a href="<c:url value='/sounddevUpdateEnter?id=${soundDevResult.id }&${queryCondition.searchUri}&queryCondition.size=10&queryCondition.offset=${page.offset }'></c:url>" title="编辑" class="edit_yinpin editlink">
											编辑
										</a>
										</c:if>
									</td>
						        </tr> 
							</c:forEach>
						</tbody>
				    </table>
				</div>
				<div id="caozuorizhi_page_container">
					<c:if test="${fn:length(page.datas) >0}">
						<script>
							function jumpPages(obj)
							{
								var maxPage = $(obj).attr("maxPages");
								var jpage = $("#jumpPages").val();
								if(parseInt(jpage)>parseInt(maxPage))
								{
									alert("最大页数是 "+maxPage);
									$("#jumpPages").val(maxPage);
									return false;
								}
								else
								{
									window.location.href=$("#ctx").val()+"/sounddevQuery?"+'${queryCondition.searchUri}'+"&queryCondition.size=10&queryCondition.offset="+jpage;
								}
							}
						</script>
						<div class="pagin">
							<div class="message left">
								共<a class="blue" href="javascript:void(0);">${page.total }</a>条记录
								，共&nbsp;<a class="blue" href="javascript:void(0);">${page.pages }&nbsp;页&nbsp;，</a>当前显示第&nbsp;
								<a class="blue" href="javascript:void(0);">${page.offset }&nbsp;</a>页
							</div>
							<c:if test="${page.total>0 }">
								<ul class="paginList">
									<li class="paginItem">
										<a href="<c:url value="/sounddevQuery?${queryCondition.searchUri}&queryCondition.size=10&queryCondition.offset=1"/>">首页</a>
									</li>
									<c:if test="${page.offset > 1}">
										<li class="paginItem">
											<a href="<c:url value='/sounddevQuery?${queryCondition.searchUri}&queryCondition.size=10&queryCondition.offset=${page.offset-1 }'></c:url>">上页</a>
										</li>
									</c:if>
									<c:if test="${page.offset < page.pages }">
										<li class="paginItem">
											<a href="<c:url value='/sounddevQuery?${queryCondition.searchUri}&queryCondition.size=10&queryCondition.offset=${page.offset+1 }'></c:url>">下页</a>
										</li>
									</c:if>
									<li class="paginItem2"><span>第<input type="text" id="jumpPages" value="${page.offset }" name="jumpPages">页</span></li>
									<li class="paginItem"><a href="javascript:void(0);" onclick="jumpPages(this);" maxPages="${page.pages }">跳转</a></li>
									<li class="paginItem">
										<a href="<c:url value="/sounddevQuery?${queryCondition.searchUri}&queryCondition.size=10&queryCondition.offset=${page.pages }"/>">尾页</a>
									</li>
								</ul>
							</c:if>
						</div>
					</c:if>
				</div>
				<!-- <div class="pagin">
			    	<div class="message left">共<a class="blue" href="javascript:void(0);">1256</a>条记录，当前显示第&nbsp;<a class="blue" href="javascript:void(0);">2&nbsp;</a>页</div>
			        <ul class="paginList">
				        <li class="paginItem"><a href="javascript:;">上页</a></li>
				        <li class="paginItem"><a href="javascript:;">下页</a></li>
						<li class="paginItem2"><span>第<input type="text" value="" name="">页</span></li>
						<li class="paginItem"><a href="javascript:;">跳转</a></li>
			        </ul>
			    </div> -->
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
	</body>
</html>