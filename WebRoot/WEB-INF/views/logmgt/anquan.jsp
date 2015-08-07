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

		<link href="${pageContext.request.contextPath}/resources/css/popModal.min.css" rel="stylesheet">

		<link href="${pageContext.request.contextPath}/resources/css/validationEngine.jquery.css" rel="stylesheet">

		<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">

		<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>

		<script type="text/javascript">

			$(function(){

				var selectLev = $("#select_anquan_jigou").val();

				$("#normal_mingcheng_select").empty();

				$("#normal_mingcheng_select").append($("#optiongroups"+selectLev).html());

				$("#select_anquan_jigou").change(function(){

					var sLev = $("#select_anquan_jigou").val();

					$("#normal_mingcheng_select").empty();

					$("#normal_mingcheng_select").append($("#optiongroups"+sLev).html());

				});

				var filterBy = '${securityLogCondition.flag}';

				if(filterBy==2)

				{

					$("#searchgonghao_container").show();

			    	$("#searchjigou_container").hide();

			    	$("#searchjigou_container select").attr("disabled",true);

			    	$("#search_account").attr("disabled",false);

			    	$("#search_by_gonghao").prop("checked",true);

				}

				else

				{

					$("#searchjigou_container").show();

			    	$("#searchgonghao_container").hide();

			    	$("#search_account").attr("disabled",true);

			    	$("#searchjigou_container select").attr("disabled",false);

			    	$("#search_by_jigou").prop("checked",true);

				}

			});

		</script>

	</head>

	<body>

		<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>

		<div id="main_content_container">

			<div id="nav_place">

				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/logmgt/initSecurityLog"></c:url>">日志管理</a></li>
					<li>安全日志</li>
				</ul>
			</div>

			<div id="content_container">

				<div class="normal_tab">

				  	<ul>

					    <li><a class="selected" href="<c:url value='/logmgt/initSecurityLog'></c:url>">安全日志</a></li>

						<li><a href="<c:url value='/logmgt/initOperationLog'></c:url>">操作日志</a></li>

				  	</ul>

			    </div>

			    <div class="search_container">

			    	<form action="<c:url value='/logmgt/querySecurityLog'></c:url>" method="get" id="anquanrizhi_form">

			    		<div>

			    			<div class="single_filter_container left">

					    		<label class="search_name">查询条件:</label>

					    		<div class="left single_filter" style="margin-top:8px;margin-left:0px;" id="jigou_gonghao_search_control">

					    			<c:choose>

					    				<c:when test="${securityLogCondition.flag==2 }">

					    					<input type="radio" value="1" name="securityLogCondition.flag" id="search_by_jigou">按机构查询

					    					<input type="radio" value="2" name="securityLogCondition.flag" id="search_by_gonghao"  checked="checked" class="left20">按工号查询

					    				</c:when>

					    				<c:otherwise>

					    					<input type="radio" value="1" name="securityLogCondition.flag" id="search_by_jigou"  checked="checked">按机构查询

					    					<input type="radio" value="2" name="securityLogCondition.flag" id="search_by_gonghao" class="left20">按工号查询

					    				</c:otherwise>

					    			</c:choose>

					    		</div>

					    	</div>

					    	<div class="left" id="searchjigou_container">

					    		<div class="single_filter_container left">

						    		<label class="search_name">机构级别:</label>

						    		<div class="left single_filter">

						    			<select id="select_anquan_jigou" name="securityLogCondition.orgLev" class="normalselect">

						    				<c:forEach items="${organizationList }" var="lv">

							    				<c:choose>

							    					<c:when test="${lv.id eq securityLogCondition.orgLev }">

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

						    		<label class="search_name">机构名称:</label>

						    		<div class="left single_filter">

						    			<select id="normal_mingcheng_select" name="securityLogCondition.organizationId" class="normalselect">

						    			</select>

						    			<div style="display: none;" id="optiongroups0">

						    				<c:forEach items="${companyList }" var="company">

						    					<c:choose>

							    					<c:when test="${company.id eq securityLogCondition.organizationId }">

								    					<option style="display: none;" value="${company.id }" group="0" selected="selected">${company.name }</option>

								    				</c:when>

								    				<c:otherwise>

								    					<option style="display: none;" value="${company.id }" group="0">${company.name }</option>

								    				</c:otherwise>

							    				</c:choose>

						    				</c:forEach>

						    			</div>

						    			<div style="display: none;" id="optiongroups1">

						    				<c:forEach items="${branchList }" var="bean">

						    					<c:choose>

						    						<c:when test="${securityLogCondition.organizationId eq bean.id }">

						    							<option group="1" selected="selected" value="${bean.id }">${bean.name }</option>

						    						</c:when>

						    						<c:otherwise>

						    							<option group="1" value="${bean.id }">${bean.name }</option>

						    						</c:otherwise>

						    					</c:choose>

						    				</c:forEach>

						    			</div>

						    			<div style="display: none;" id="optiongroups2">

						    				<c:forEach items="${departmentList }" var="bean">

						    					<c:choose>

						    						<c:when test="${securityLogCondition.organizationId eq bean.id }">

						    							<option group="2" selected="selected" value="${bean.id }">${bean.name }</option>

						    						</c:when>

						    						<c:otherwise>

						    							<option group="2" value="${bean.id }">${bean.name }</option>

						    						</c:otherwise>

						    					</c:choose>

						    				</c:forEach>

						    			</div>

						    		</div>

						    	</div>

					    	</div>

					    	<div class="single_filter_container left" style="display:none;" id="searchgonghao_container">

					    		<label class="search_name">工号:</label>

					    		<div class="left single_filter">

					    			<input type="text" name="securityLogCondition.accountId" value="${securityLogCondition.accountId }" disabled="disabled" id="search_account" class="normaltext validate[custom[gonghao],maxSize[20]]">

					    		</div>

					    	</div>

					    	<div class="clear"></div>

				    	</div>

				    	<div>

				    		<div class="single_filter_container left">

					    		<label class="search_name">开始时间:</label>

					    		<div class="left single_filter">

					    			<input type="text" value="<fmt:formatDate value="${securityLogCondition.beginTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" name="securityLogCondition.beginTime" class="normaltext" id="fromdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'todate\')}' })" style="width:200px;"/>

									<a href="javascript:void(0)" class="underlinenone">

										<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',el:'fromdate',maxDate:'#F{$dp.$D(\'todate\')}' })" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />

									</a>

					    		</div>

					    	</div>

					    	<div class="single_filter_container left">

					    		<label class="search_name">结束时间:</label>

					    		<div class="left single_filter">

					    			<input type="text" value="<fmt:formatDate value="${securityLogCondition.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" name="securityLogCondition.endTime" class="normaltext" id="todate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'fromdate\')}' })" style="width:200px;"/>

									<a href="javascript:void(0)" class="underlinenone">

										<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',el:'todate',minDate:'#F{$dp.$D(\'fromdate\')}' })" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />

									</a>

					    		</div>

					    	</div>

					    	<div class="single_filter_container left">

					    		<label class="search_name">操作结果:</label>

					    		<div class="left single_filter">

					    			<select id="" name="securityLogCondition.opResult" class="normalselect">

					    				<c:choose>

					    					<c:when test="${securityLogCondition.opResult==-1 }">

					    						<option selected="selected" value="-1">全部</option>

					    						<option value="1">成功</option>

					    						<option value="0">失败</option>

					    					</c:when>

					    					<c:when test="${securityLogCondition.opResult==1 }">

					    						<option value="-1">全部</option>

					    						<option selected="selected" value="1">成功</option>

					    						<option value="0">失败</option>

					    					</c:when>

					    					<c:otherwise>

					    						<option value="-1">全部</option>

					    						<option value="1">成功</option>

					    						<option selected="selected" value="0">失败</option>

					    					</c:otherwise>

					    				</c:choose>

					    			</select>

					    		</div>

					    	</div>

					    	<div class="single_filter_container left">

					    		<label class="search_name">操作标识:</label>

					    		<div class="left single_filter">

					    			<select id="" name="securityLogCondition.commandId" class="normalselect">

					    				<c:choose>

					    					<c:when test="${securityLogCondition.commandId==-1 }">

					    						<option selected="selected" value="-1">全部</option>

					    						<option value="103050001">登录</option>

					    						<option value="103050002">注销</option>

					    					</c:when>

					    					<c:when test="${securityLogCondition.commandId==103050001 }">

					    						<option value="-1">全部</option>

					    						<option selected="selected" value="103050001">登录</option>

					    						<option value="103050002">注销</option>

					    					</c:when>

					    					<c:otherwise>

					    						<option value="-1">全部</option>

					    						<option value="103050001">登录</option>

					    						<option selected="selected" value="103050002">注销</option>

					    					</c:otherwise>

					    				</c:choose>

					    			</select>

					    		</div>

					    	</div>

					    	<div class="clear"></div>

				    	</div>

				    	<div>

				    		<div class="single_filter_container left" style="margin-left: 20px;">

				    			<input type="submit" value="查询" class="btn">

				    		</div>

				    		<div class="clear"></div>

				    	</div>

			    	</form>

			    </div>

			     <div class="normalajaxloading" id="data_loading"></div>

			    <div class="tableui_container">
					<%--
			    	<div class="right">
			    	 	<c:if test="${fn:length(pager.datas) >0}">
							<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'删除安全日志') }">
				    			<input type="button" id="clearAnquanLog" value="清除" class="btn">
							</c:if>
						</c:if> 
			    	</div>
					--%>
			    	<div class="clear"></div>

					<table class="tableui" id="anquanTableContainer">

				    	<thead>

					    	<tr>       

						        <th><span>流水号</span></th>

						        <th><span>工号</span></th>

								<th><span>操作时间</span></th>

						        <th><span>操作标识</span></th>

								<th><span>操作结果</span></th>

								<th><span>主机名称</span></th>

								<th><span>主机地址</span></th>

								<th><span>机构标识</span></th>

					        </tr>

				        </thead>

				        <tbody>

				        	<c:forEach items="${pager.datas }" var="log" varStatus="index">

								<span style="display:none;" id="anquan${index.count }_xiangxixinxi"><c:out value="${log.opDetail }"></c:out></span>

				        		<tr>        

							       	<td id="anquan${index.count }_liushuihao">

							        	<a href="javascript:void(0);" liushuihao="${index.count }" class="detail anquan_detail"><c:out value="${log.slsId }"></c:out></a>

							        </td>

							        <td id="anquan${index.count }_gonghao"><c:out value="${log.accountId }"></c:out></td>

							        <td id="anquan${index.count }_caozuoshijian">

							        	<fmt:formatDate value="${log.opTime }" pattern="yyyy-MM-dd HH:mm:ss"/>

							        </td>

							        <td id="anquan${index.count }_caozuobiaoshi"><c:out value="${log.commandName }"></c:out></td>

									<td id="anquan${index.count }_caozuojieguo">

										<c:choose>

											<c:when test="${log.opResult==1 }">

												成功

											</c:when>

											<c:otherwise>

												失败

											</c:otherwise>

										</c:choose>

									</td>

									<td id="anquan${index.count }_zhujimingcheng"><c:out value="${log.hostName }"></c:out></td>

							        <td id="anquan${index.count }_zhujidizhi"><c:out value="${log.hostIp }"></c:out></td>

									<td id="anquan${index.count }_jigoubiaoshi"><c:out value="${log.orgName }"></c:out></td>

						        </tr>

				        	</c:forEach>

				        </tbody>

				    </table>

				</div>

				<input type="hidden" id="searchUri" value="${securityLogCondition.searchUri }">

				<div id="anquanrizhi_page_container">

				<c:choose>
					<c:when test="${pager.offset==0 }">
						
					</c:when>
					<c:when test="${fn:length(pager.datas) >0}">

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

									window.location.href=$("#ctx").val()+"/logmgt/querySecurityLog?"+'${securityLogCondition.searchUri}'+"&offset="+jpage;

								}

							}

						</script>

						<div class="pagin">

							<div class="message left">

								共<a class="blue" href="javascript:void(0);">${pager.total }</a>条记录

								，共&nbsp;<a class="blue" href="javascript:void(0);">${pager.pages }&nbsp;页&nbsp;，</a>当前显示第&nbsp;

								<a class="blue" href="javascript:void(0);">${pager.offset }&nbsp;</a>页

							</div>

							<c:if test="${pager.total>0 }">

								<ul class="paginList">

									<li class="paginItem">

										<a href="<c:url value="/logmgt/querySecurityLog?${securityLogCondition.searchUri }&offset=1"/>">首页</a>

									</li>

									<c:if test="${pager.offset > 1}">

										<li class="paginItem">

											<a href="<c:url value='/logmgt/querySecurityLog?${securityLogCondition.searchUri }&offset=${pager.offset-1 }'></c:url>">上页</a>

										</li>

									</c:if>

									<c:if test="${pager.offset < pager.pages }">

										<li class="paginItem">

											<a href="<c:url value='/logmgt/querySecurityLog?${securityLogCondition.searchUri }&offset=${pager.offset+1 }'></c:url>">下页</a>

										</li>

									</c:if>

									<li class="paginItem2"><span>第<input type="text" id="jumpPages" value="${pager.offset }" name="jumpPages">页</span></li>

									<li class="paginItem"><a href="javascript:void(0);" onclick="jumpPages(this);" maxPages="${pager.pages }">跳转</a></li>

									<li class="paginItem">

										<a href="<c:url value="/logmgt/querySecurityLog?${securityLogCondition.searchUri }&offset=${pager.pages }"/>">尾页</a>

									</li>

								</ul>

							</c:if>

						</div>

					</c:when>
					<c:otherwise>
						<div class="pagin">
							<div class="message left">
								共<a class="blue" href="javascript:void(0);">0</a>条记录
							</div>
						</div>
					</c:otherwise>
					
				</c:choose>

				</div>

			</div>

		</div>

		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>

		<div id="anquan_detail_container" style="display: none;z-index:0;" wid="60">

			<div class='dialogModal_header'>安全日志详细信息</div>

			<div class='dialogModal_content'>

				<table class="detail_table">

					<tbody>

						<tr>

							<td class="title_name">流水号:</td>

							<td class="content content_liushuihao"></td>

							<td class="title_name">操作时间:</td>

							<td class="content content_caozuoshijian"></td>

							<td class="title_name">工号:</td>

							<td class="content content_gonghao"></td>

						</tr>

						<tr>

							<td class="title_name">操作标识:</td>

							<td class="content content_caozuobiaoshi"></td>

							<td class="title_name">操作结果:</td>

							<td class="content content_caozuojieguo"></td>

							<td class="title_name">机构标识:</td>

							<td class="content content_jigoubiaoshi"></td>

						</tr>

						<tr>

							<td class="title_name">主机名称:</td>

							<td class="content content_zhujimingcheng"></td>

							<td class="title_name">主机地址:</td>

							<td class="content content_zhujidizhi" colspan="3"></td>

						</tr>

						<tr>

							<td class="title_name">详细信息:</td>

							<td class="content content_xiangxixinxi" colspan="6"></td>

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