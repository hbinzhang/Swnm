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
		<link href="${pageContext.request.contextPath}/resources/css/popModal.min.css" rel="stylesheet">				<link href="${pageContext.request.contextPath}/resources/css/validationEngine.jquery.css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/fangqu.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/plugins/ajaxfileupload/ajaxfileupload.js"></script>				<script type="text/javascript">		function onAppData(event)		{			  if (event.getSubject() == localOperation && event.get("devType") =="zone") 			  {				  var msg = event.get("message");				  var strs= new Array(); //定义一数组 				  strs=msg.split(":"); //字符分割 				  				  var zoneID = strs[0];				  var status = strs[1];				  var fangqu = $("#zone"+zoneID+"_status");				  var zoneOperate=$("#zoneOperate"+zoneID+" a:eq(1)");				  if(fangqu != null&&zoneOperate!=null)				  {					 if(status == "0"){						 fangqu.empty();						 fangqu.append("<c:out value='已撤防'></c:out>");						 zoneOperate.removeClass();						 zoneOperate.addClass("left10 bufanglink");						 /*						 var content ="";						 content=content+"<a href='javascript:void(0);' title='查看告警' id='' fangquid='"+zoneID+"' class='view_fangqugaojing viewgaojingLink'>";						 content=content+"查看告警";						 content=content+"</a>";						 content=content+"<c:if test='${poteviofn:clContains(sessionScope.session.authorizatedOps,\"撤/布防区\") }'>";						 content=content+"<a href='javascript:void(0);' title='布放' fangquid='"+zoneID+"' class='left10 bufanglink' >";						 content=content+"布防";						 content=content+"</a>";						 content=content+"</c:if>";						 zoneOperate.empty();						 zoneOperate.append(content);						 */					}					 else if(status == "1")					{						 fangqu.empty();						 fangqu.append("<c:out value='已布防'></c:out>");						 zoneOperate.removeClass();						 zoneOperate.addClass("left10 chefanglink");						 /*						 var content ="";						 content=content+"<a href='javascript:void(0);' title='查看告警' id='' fangquid='"+zoneID+"' class='view_fangqugaojing viewgaojingLink'>";						 content=content+"查看告警";						 content=content+"</a>";						 content=content+"<c:if test='${poteviofn:clContains(sessionScope.session.authorizatedOps,\"撤/布防区\") }'>";						 content=content+"<a href='javascript:void(0);' title='撤防' fangquid='"+zoneID+"' class='left10 chefanglink' >";						 content=content+"撤防";						 content=content+"</a>";						 content=content+"</c:if>";						 zoneOperate.empty();						 zoneOperate.append(content);						 */						 					}				  }			  }		}		</script>
	</head>
	<body>
		<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/fence/queryFence"></c:url>">设备管理</a></li>
					<li>防区管理</li>
				</ul>
			</div>
			<div id="content_container">
				<c:import url="../layout/shebeitab.jsp?sTabLink=fangqu" charEncoding="UTF-8"></c:import>
				<form action="<c:url value='/zone/queryZoneByPage'></c:url>" method="get">
				<div class="search_container">
					<div class="left" style="margin-top:5px;">
			    		<div class="single_filter_container left">
				    		<label class="search_name">分公司:</label>
				    		<div class="left single_filter">
					    			<select id="normal_gongsi_select" class="normalselect" name="zoneBean.branchID">
					    			<%--
					    				<c:choose>
					    					<c:when test="${sessionScope.session.lev eq '0' }">
					    						<option value="">全部</option>
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
					    			--%>
					    				<c:choose>
					    					<c:when test="${sessionScope.session.lev eq '0' }">
					    						<option value="">全部</option>
					    						<c:forEach items="${sessionScope.session.orgIdAndNames.subCompanys }" var="companyBean">
					    						<c:choose>
					    							<c:when test="${companyBean.id ==zoneBean.branchID}">
					    								<option value="${companyBean.id }" selected="selected" >${companyBean.name }</option>
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
					    			<select id="normal_guanlichu_select" class="normalselect" name="zoneBean.mgtID">
					    				<%--<c:choose>
					    					<c:when test="${sessionScope.session.lev eq '0' }">
					    						<option value="">全部</option>
					    					</c:when>
					    					<c:when test="${sessionScope.session.lev eq '1' }">
					    						<option value="">全部</option>
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
					    			--%>
					    				<c:choose>
					    					<c:when test="${sessionScope.session.lev eq '0' }">
					    						<option value="">全部</option>
					    						<c:forEach items="${sessionScope.session.orgIdAndNames.managements }" var="departBean">
						    						<c:choose>
						    							<c:when test="${departBean.id ==zoneBean.mgtID}">
						    								<option value="${departBean.id }" selected="selected" >${departBean.name }</option>
						    							</c:when>
						    							<c:otherwise>
						    								<option value="${departBean.id }">${departBean.name }</option>
						    							</c:otherwise>
						    						</c:choose>
					    						</c:forEach>
					    					</c:when>
					    					<c:when test="${sessionScope.session.lev eq '1' }">
					    						<option value="">全部</option>
					    						<c:forEach items="${sessionScope.session.orgIdAndNames.managements }" var="departBean">
					    						<c:choose>
					    							<c:when test="${departBean.id ==zoneBean.mgtID}">
					    								<option value="${departBean.id }" selected="selected" >${departBean.name }</option>
					    							</c:when>
					    							<c:otherwise>
					    								<option value="${departBean.id }">${departBean.name }</option>
					    							</c:otherwise>
					    						</c:choose>
					    						</c:forEach>
					    					</c:when>
					    					<c:otherwise>
					    						<c:forEach items="${sessionScope.session.orgIdAndNames.managements }" var="departBean">
					    							<option value="${departBean.id }">${departBean.name }</option>
					    						</c:forEach>
					    					</c:otherwise>
					    				</c:choose>
					    			</select>
					    		</div>
				    	</div>
				    	<div class="single_filter_container left" style="width:520px;">
				    		<label class="search_name">附加条件:</label>
				    		<select class="normalselect" id="fangqu_search_filter" name="zoneBean.filter">
				    			<c:choose>
				    				<c:when test="${zoneBean.filter==1 }">
				    					<option value="1" selected="selected">防区ID</option>
										<option value="2">防区名</option>
										<option value="3">围栏类型</option>
				    				</c:when>
				    				<c:when test="${zoneBean.filter==2 }">
				    					<option value="1" >防区ID</option>
										<option value="2" selected="selected">防区名</option>
										<option value="3">围栏类型</option>
				    				</c:when>
				    				<c:otherwise>
				    					<option value="1" >防区ID</option>
										<option value="2" >防区名</option>
										<option value="3" selected="selected">围栏类型</option>
				    				</c:otherwise>
				    			</c:choose>
							</select>
							<c:choose>
								<c:when test="${zoneBean.filter==3 }">
									<select style="width:300px;" class="normalselect" id="weilanleixing" name="zoneBean.fenceType">
										<c:choose>
						    				<c:when test="${zoneBean.fenceType==1 }">
						    					<option value="">全部</option>
												<option value="1" selected="selected">高压脉冲主机</option>
												<option value="2">一体化探测</option>
												<option value="4">定位型振动光纤</option>
												<option value="3">防区型振动光纤</option>
						    				</c:when>
						    				<c:when test="${zoneBean.fenceType==2 }">
						    					<option value="">全部</option>
												<option value="1">高压脉冲主机</option>
												<option value="2" selected="selected">一体化探测</option>
												<option value="4">定位型振动光纤</option>
												<option value="3">防区型振动光纤</option>
						    				</c:when>
						    				<c:when test="${zoneBean.fenceType==3 }">
						    					<option value="">全部</option>
												<option value="1">高压脉冲主机</option>
												<option value="2">一体化探测</option>
												<option value="4">定位型振动光纤</option>
												<option value="3" selected="selected">防区型振动光纤</option>
						    				</c:when>
						    				<c:when test="${zoneBean.fenceType==4 }">
						    					<option value="">全部</option>
												<option value="1">高压脉冲主机</option>
												<option value="2">一体化探测</option>
												<option value="4" selected="selected">定位型振动光纤</option>
												<option value="3">防区型振动光纤</option>
						    				</c:when>
						    				<c:otherwise>
						    					<option value="" selected="selected">全部</option>
												<option value="1">高压脉冲主机</option>
												<option value="2">一体化探测</option>
												<option value="4">定位型振动光纤</option>
												<option value="3">防区型振动光纤</option>
						    				</c:otherwise>
						    			</c:choose>
									</select>
									<input type="text" class="normaltext" name="zoneBean.zoneName" value="" id="queryfangquname" style="width:300px;display: none;">
									<input type="text" class="normaltext" name="zoneBean.zoneID" value="" id="queryfangquid" style="width:300px;display: none;">
								</c:when>
								<c:when test="${zoneBean.filter==2 }">
									<select style="width:300px;" class="normalselect clickseclect" id="weilanleixing" style="display: none;" name="zoneBean.fenceType">
										<option value="" selected="selected">全部</option>
										<option value="1">高压脉冲主机</option>
										<option value="2">一体化探测</option>
										<option value="4">定位型振动光纤</option>
										<option value="3">防区型振动光纤</option>
									</select>
									<input type="text" class="normaltext" name="zoneBean.zoneName" value="${zoneBean.zoneName }" id="queryfangquname" style="width:300px;">
									<input type="text" class="normaltext" name="zoneBean.zoneID" value="" id="queryfangquid" style="width:300px;display: none;">
								</c:when>
								<c:otherwise>
									<select style="width:300px;" class="normalselect clickseclect" id="weilanleixing" style="display: none;" name="zoneBean.fenceType">
										<option value="" selected="selected">全部</option>
										<option value="1">高压脉冲主机</option>
										<option value="2">一体化探测</option>
										<option value="4">定位型振动光纤</option>
										<option value="3">防区型振动光纤</option>
									</select>
									<input type="text" class="normaltext" name="zoneBean.zoneName" value="" id="queryfangquname" style="width:300px;display: none;">
									<input type="text" class="normaltext" name="zoneBean.zoneID" value="${zoneBean.zoneID }" id="queryfangquid" style="width:300px;">
								</c:otherwise>
							</c:choose>
				    	</div>
				    	<div class="clear"></div>
			    	</div>
			    	<div class="left" style="margin-top:5px;">
			    		<input type="submit" class="btn" value="查询" style="margin-left:10px;">						<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'增加防区') }">							<a href="<c:url value='/zone/goAddZone'></c:url>" class="hrefbtn">添加</a>						</c:if>						<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'批量导入防区') }">							<a href="javascript:void(0);" class="hrefbtn" id="importfangqu">批量导入</a>						</c:if>						<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'删除防区') }">							<a href="javascript:void(0);" class="hrefbtn" id="deletefangqu">删除</a>						</c:if>
			    	</div>
			    	<div class="clear"></div>
			    	<div class="top10" style="display: none;margin-left:100px;" id="uploadcontainer">
			    		<input type="file" name="zone" id="zone">
			    		<input type="button" class="btn" value="开始导入" id="beginimport">
			    		<input type="button" class="btn" value="取消" id="calcelimport">
			    	</div>
				</div>
				</form>				<div class="normalajaxloading" id="data_loading"></div>
				<div id="tableui_container">
					<table class="tableui" id="fangqu_table">
				    	<thead>
					    	<tr>
					    		<th width="2%">
					    			<input type="checkbox" id="fangqu_select_all"/>
					    		</th>                       
						        <th><span>防区ID</span></th>
						        <th><span>防区名称</span></th>
						        <th><span>围栏类型</span></th>
						        <th><span>分公司</span></th>
						        <th><span>管理处</span></th>
						        <th><span>左/右岸</span></th>
						        <th><span>状态</span></th>
						        <th><span>备注</span></th>
								<th><span>操作</span></th>
					        </tr>
					   	</thead>
						<tbody>
							<c:forEach items="${page.datas }" var="zone">
								<tr>
									<td align="center">
										<input type="checkbox" value="${zone.zoneID}" name="deleteids" id="deleteids"/>
									</td>
									<td align="center">
										<a onclick="return checkStatus('${zone.status}')" href="<c:url value='/zone/queryZoneDetailByID?zoneBean.zoneID=${zone.zoneID }'></c:url>" class="detail"><c:out value="${zone.zoneID  }"></c:out></a>
									</td>
									<td>
										<c:out value="${zone.zoneName }"></c:out>
									</td>
									<td>
									<c:choose>
											<c:when test="${zone.fenceType == 1 }">
												<c:out value="高压脉冲主机"></c:out>
											</c:when>
											<c:when test="${zone.fenceType == 2 }">
												<c:out value="一体化主机"></c:out>
											</c:when>
											<c:when test="${zone.fenceType == 3 }">
												<c:out value="防区型光纤主机"></c:out>
											</c:when>
											<c:when test="${zone.fenceType == 4 }">
												<c:out value="定位型光纤主机"></c:out>
											</c:when>
											<c:otherwise>
											
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:out value="${zone.branchName }"></c:out>
									</td>
									<td>
										<c:out value="${zone.mgtName }"></c:out>
									</td>
									<td>
										<c:out value="${zone.orientation }"></c:out>
									</td>
									<td id="zone${zone.zoneID }_status">
										<c:choose>
											<c:when test="${zone.status == 0 }">
												<c:out value="已撤防"></c:out>
											</c:when>
											<c:when test="${zone.status == 1 }">
												<c:out value="已布防"></c:out>
											</c:when>											<c:when test="${zone.status == 2 }">																							<c:out value="未  用"></c:out>											</c:when>
											<c:otherwise></c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:out value="${zone.info }"></c:out>
									</td>
										<c:choose>
											<c:when test="${zone.status == 0}">
												
												<td align="center" id="zoneOperate${zone.zoneID }">
													<a href="javascript:void(0);" title="查看告警" id="" fangquid="${zone.zoneID }" class="view_fangqugaojing viewgaojingLink">
														查看告警
													</a>													<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'撤/布防区') }">
														<a href="javascript:void(0);" title="布放" fangquid="${zone.zoneID }" class="left10 bufanglink" >	
															布防	
														</a>													 </c:if>											
												</td>
											</c:when>
											<c:when test="${zone.status == 1}">
												<td align="center" id="zoneOperate${zone.zoneID }">
													<a href="javascript:void(0);" title="查看告警" id="" fangquid="${zone.zoneID }" class="view_fangqugaojing viewgaojingLink">
														查看告警
													</a>													<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'撤/布防区') }">
														<a href="javascript:void(0);" title="撤防" fangquid="${zone.zoneID }" fangquStatus="" class="left10 chefanglink">	
															撤防	
														</a>																									</c:if>
												</td>
											</c:when>
											<c:otherwise></c:otherwise>
										</c:choose>
								</tr>
								<%-- <tr>
									<td align="center">
										<input type="checkbox" value="${zone.zoneID }" name="deleteids" id="deleteids"/>
									</td>
							        <td>
							        	<a href="<c:url value="/views/shebei/fangquinfo.jsp"></c:url>" title="详细信息" class="detail">
							        		115${index }
							        	</a>
							        </td>
							        <td>防区1</td>
							        <td>围栏类型</td>
							        <td>北京分公司</td>
							        <td>大兴管理处</td>
							        <td>右</td>
							        <c:choose>
							        	<c:when test="${index%2==0 }">
							        		<td>已布防</td>
									        <td>描述信息</td>
											<td align="center">
												<a href="javascript:void(0);" title="查看告警" id="" class="view_fangqugaojing viewgaojingLink">
													查看告警
												</a>
												<a href="<c:url value="/views/shebei/fangquedit.jsp"></c:url>" title="编辑" class="edit_fangqu editlink left10">
													编辑
												</a>
												<a href="javascript:void(0);" title="删除" class="delete deletelink left10">
													删除
												</a>
												<a href="javascript:void(0);" title="撤防" class="left10 chefanglink">
													布防
												</a>
											</td>
							        	</c:when>
							        	<c:otherwise>
							        		<td>未布防</td>
									        <td>描述信息</td>
											<td align="center">
												<a href="javascript:void(0);" title="查看告警" id="" class="view_fangqugaojing viewgaojingLink">
													查看告警
												</a>
												<a href="<c:url value="/views/shebei/fangquedit.jsp"></c:url>" title="编辑" class="edit_fangqu editlink left10">
													编辑
												</a>
												<a href="javascript:void(0);" title="删除" class="delete deletelink left10">
													删除
												</a>
												<a href="javascript:void(0);" title="布防" class="left10 bufanglink">
													布防
												</a>
											</td>
							        	</c:otherwise>
							        </c:choose>
						        </tr>  --%>
							</c:forEach>
						</tbody>
				    </table>
				</div>
				<input type="hidden" id="searchUri" value="${zoneBean.searchUri }">
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
									window.location.href=$("#ctx").val()+"/zone/queryZoneByPage?"+'${zoneBean.searchUri}'+"&page.size=10&page.offset="+jpage;
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
										<a href="<c:url value="/zone/queryZoneByPage?${zoneBean.searchUri }&page.size=10&page.offset=1"/>">首页</a>
									</li>
									<c:if test="${page.offset > 1}">
										<li class="paginItem">
											<a href="<c:url value='/zone/queryZoneByPage?${zoneBean.searchUri }&page.size=10&page.offset=${page.offset-1 }'></c:url>">上页</a>
										</li>
									</c:if>
									<c:if test="${page.offset < page.pages }">
										<li class="paginItem">
											<a href="<c:url value='/zone/queryZoneByPage?${zoneBean.searchUri }&page.size=10&page.offset=${page.offset+1 }'></c:url>">下页</a>
										</li>
									</c:if>
									<li class="paginItem2"><span>第<input type="text" id="jumpPages" value="${page.offset }" name="jumpPages">页</span></li>
									<li class="paginItem"><a href="javascript:void(0);" onclick="jumpPages(this);" maxPages="${page.pages }">跳转</a></li>
									<li class="paginItem">
										<a href="<c:url value="/zone/queryZoneByPage?${zoneBean.searchUri }&page.size=10&page.offset=${page.pages }"/>">尾页</a>
									</li>
								</ul>
							</c:if>
						</div>
					</c:if>
				</div>
				<%--<c:import url="../layout/pager.jsp"  charEncoding="UTF-8"></c:import>
			--%></div>
		</div>
		<c:import url="../layout/footer.jsp"  charEncoding="UTF-8"></c:import>
		<div id="view_fangqu_gaojing_container" style="display: none;z-index:0;" wid="60">
			<div class='dialogModal_header'>电子围栏安防告警</div>
			<div class='dialogModal_content'>
				<div class="tableui_container" id="fangqu_gaojing_content_container">
					<table class="tableui">
						<thead>
							<tr>
								<th>
									<span>告警编号</span>
								</th>
								<th>
									<span>告警码</span>
								</th>
								<th>
									<span>告警名称</span>
								</th>
								<th>
									<span>告警时间</span>
								</th>
								<th>
									<span>告警级别</span>
								</th>
								<th>
									<span>告警类型</span>
								</th>
								<th>
									<span>设备类型</span>
								</th>
								<th>
									<span>设备IP</span>
								</th>
								<th>
									<span>分公司</span>
								</th>
								<th>
									<span>管理处</span>
								</th>
								<th>
									<span>防区</span>
								</th>
								<th>
									<span>处理状态</span>
								</th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table>
				</div>
			</div>
			<div class="left20 top10" style="padding-bottom:20px;">
				<input class="left20 btn" data-dialogModalBut="cancel" type="button" value="关闭"/>
			</div>
		</div>	</body>
</html>
