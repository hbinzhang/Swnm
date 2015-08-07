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
		<link href="${pageContext.request.contextPath}/resources/css/validationEngine.jquery.css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/weilan.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/plugins/ajaxfileupload/ajaxfileupload.js"></script>
		
		<script type="text/javascript">
			function onAppData(event)
			{
				  if (event.getSubject() == localOperation && event.get("devType") =="fence") 
				  {
					  var msg = event.get("message");
					  var strs= new Array(); //定义一数组 
					  strs=msg.split(":"); //字符分割 
					  
					  var hostid = strs[0];
					  var status = strs[1];
					  var weilan = $(".weilanzhuangtai_"+hostid);
					  if(weilan != null)
					  {
						 if(status == "1"){
						 weilan.empty();
						 weilan.append("<span class='shebeistatus_shiyong' id='status'>正常</span>")
						}
						 else if(status == "2")
						{
							 weilan.empty();
							 weilan.append("<span class='shebeistatus_gaojing' id='status'>异常</span>")
						}
						 else
						{
							 weilan.empty();
							 weilan.append("<span class='shebeistatus_lixian' id='status'>离线</span>")
						}
						 
					  }
				  }
			}
		</script>
		
	</head>
	<body>
		<c:import url="../layout/header.jsp?navLink=dianziweilan" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/fence/queryFence"></c:url>">设备管理</a></li>
					<li>围栏主机</li>
				</ul>
			</div>
			<div id="content_container">
				<c:import url="../layout/shebeitab.jsp?sTabLink=weilan" charEncoding="UTF-8"></c:import>
				<div class="search_container">
					<form action="<c:url value='/fence/queryFence'></c:url>" method="get">
						<div class="left" style="margin-top:5px;">
				    		<div class="single_filter_container left">
					    		<label class="search_name">分公司:</label>
					    		<div class="left single_filter">
					    			<select id="normal_gongsi_select" class="normalselect" name="fenceCondition.subComID">
					    				<c:choose>
					    					<c:when test="${sessionScope.session.lev eq '0' }">
					    						<option value="">全部</option>
					    						<c:forEach items="${sessionScope.session.orgIdAndNames.subCompanys }" var="companyBean">
					    						<c:choose>
					    							<c:when test="${companyBean.id ==fenceCondition.subComID}">
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
					    			<select id="normal_guanlichu_select" class="normalselect" name="fenceCondition.mntMentID">
					    				<c:choose>
					    					<c:when test="${sessionScope.session.lev eq '0' }">
					    						<option value="">全部</option>
					    						<c:forEach items="${sessionScope.session.orgIdAndNames.managements }" var="departBean">
						    						<c:choose>
						    							<c:when test="${departBean.id ==fenceCondition.mntMentID}">
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
					    							<c:when test="${departBean.id ==fenceCondition.mntMentID}">
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
					    		<select class="normalselect" id="weilan_search_filter" name="fenceCondition.filter">
					    			<c:choose>
					    				<c:when test="${fenceCondition.filter==1 }">
					    					<option value="1" selected="selected">主机ID</option>
											<option value="2">类型</option>
					    				</c:when>
					    				<c:otherwise>
					    					<option value="1">主机ID</option>
											<option value="2" selected="selected">类型</option>
					    				</c:otherwise>
					    			</c:choose>
								</select>
								<input type="text" class="normaltext" name="fenceCondition.hostID" value="${fenceCondition.hostID }" id="weilaninput" style="width:300px;" >
								<select class="normalselect clickseclect" id="weilanleixing" name="fenceCondition.fenceType">
									<c:choose>
					    				<c:when test="${fenceCondition.fenceType==1 }">
					    					<option value="">全部</option>
											<option value="1" selected="selected">高压脉冲主机</option>
											<option value="2">一体化探测</option>
											<option value="4">定位型振动光纤</option>
											<option value="3">防区型振动光纤</option>
					    				</c:when>
					    				<c:when test="${fenceCondition.fenceType==2 }">
					    					<option value="">全部</option>
											<option value="1">高压脉冲主机</option>
											<option value="2" selected="selected">一体化探测</option>
											<option value="4">定位型振动光纤</option>
											<option value="3">防区型振动光纤</option>
					    				</c:when>
					    				<c:when test="${fenceCondition.fenceType==3 }">
					    					<option value="">全部</option>
											<option value="1">高压脉冲主机</option>
											<option value="2">一体化探测</option>
											<option value="4">定位型振动光纤</option>
											<option value="3" selected="selected">防区型振动光纤</option>
					    				</c:when>
					    				<c:when test="${fenceCondition.fenceType==4 }">
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
					    	</div>
					    	<div class="clear"></div>
				    	</div>
				    	<div class="left" style="margin-top:5px;">
				    		<input type="submit" class="btn" value="查询" style="margin-left:10px;" id="weilanchaxun_btn">
				    		<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'增加电子围栏') }">
				    			<a href="<c:url value="/fence/goAddPage?${fenceCondition.searchUri}&page.size=10&page.offset=${page.offset }"></c:url>" class="hrefbtn">添加</a>
				    		</c:if>
				    		<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'批量导入电子围栏') }">
					  			<a href="javascript:void(0);" class="hrefbtn" id="importweilan">批量导入</a>
					  		</c:if>
					  		<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'删除电子围栏') }">
					  			<a href="javascript:void(0);" class="hrefbtn" id="deleteweilan">删除</a>
					  		</c:if>
				    	</div>
			    	</form>
			    	<div class="clear"></div>
					<div class="top10" style="display: none;margin-left:100px;" id="uploadcontainer">
			    		<input type="file" name="fence" id="fence">
			    		<input type="button" class="btn" value="开始导入" id="beginimport">
			    		<input type="button" class="btn" value="取消" id="calcelimport">
			    	</div>
				</div>
				<div id="tableui_container">
					<table class="tableui" id="weilan_table">
				    	<thead>
					    	<tr> 
					    		<th width="2%">
					    			<input type="checkbox" id="weilan_select_all"/>
					    		</th>                                 
						        <th><span>主机ID</span></th>
						        <th><span>围栏类型</span></th>
						        <th><span>网络地址</span></th>
						        <th><span>端口号</span></th>
						        <th><span>围栏状态</span></th>
						        <th><span>分公司</span></th>
						        <th><span>管理处</span></th>
								<th><span>操作</span></th>
					        </tr>
					   	</thead>
						<tbody>
							<c:forEach items="${page.datas }" var="list">
								
								<span style="display:none;" id="weilan${list.hostID }_weilanmingcheng"><c:out value="${list.fenceName }"></c:out></span>
								<span style="display:none;" id="weilan${list.hostID }_shebeiid"><c:out value="${list.hostID }"></c:out></span>
								<span style="display:none;" id="weilan${list.hostID }_anzhuangshijian"><c:out value="${list.installTime }"></c:out></span>
								<span style="display:none;" id="weilan${list.hostID }_ruanjianbanben"><c:out value="${list.sorfwareVer }"></c:out></span>
								<span style="display:none;" id="weilan${list.hostID }_chanpinxinghao"><c:out value="${list.hardwareVer }"></c:out></span>
								<span style="display:none;" id="weilan${list.hostID }_jingdu"><c:out value="${list.efLongitude }"></c:out></span>
								<span style="display:none;" id="weilan${list.hostID }_weidu"><c:out value="${list.efLatitude }"></c:out></span>
								<span style="display:none;" id="weilan${list.hostID }_shengchanchangshang"><c:out value="${list.vendorName }"></c:out></span>
								<span style="display:none;" id="weilan${list.hostID }_beizhu"><c:out value="${list.remarks }"></c:out></span>
								<tr>
									<td align="center">
										<input type="checkbox" value="${list.hostID}" name="deleteids" id="deleteids"/>
									</td>
									<td id="weilan${list.hostID }_zhujiid">
										<a class="detail weilan_detail" weilanid="<c:out value="${list.hostID }"></c:out>" href="javascript:void(0);"><c:out value="${list.hostID }"></c:out></a>
									</td>
									<td id="weilan${list.hostID }_weilanleixing">
										<c:choose>
											<c:when test="${list.fenceType == 1 }">
												<c:out value="高压脉冲主机"></c:out>
											</c:when>
											<c:when test="${list.fenceType == 2 }">
												<c:out value="一体化主机"></c:out>
											</c:when>
											<c:when test="${list.fenceType == 3 }">
												<c:out value="防区型光纤主机"></c:out>
											</c:when>
											<c:when test="${list.fenceType == 4 }">
												<c:out value="定位型光纤主机"></c:out>
											</c:when>
											<c:otherwise>
											
											</c:otherwise>
										</c:choose>
									</td>
									<td id="weilan${list.hostID }_wangluodizhi"><c:out value="${list.ip }"></c:out></td>
									<td id="weilan${list.hostID }_duankouhao"><c:out value="${list.port }"></c:out></td>
									<td align="center" id="weilan${list.hostID }_weilanzhuangtai" class="weilanzhuangtai_${list.hostID }">
										<c:choose>
											<c:when test="${list.fenceStatus == 1 }">
												<span class="shebeistatus_shiyong" id="status">正常</span>
											</c:when>
											<c:when test="${list.fenceStatus == 2 }">
												<span class="shebeistatus_gaojing" id="status">异常</span>
											</c:when>
											<c:when test="${list.fenceStatus == 3 }">
												<span class="shebeistatus_lixian" id="status">离线</span>
											</c:when>											<c:when test="${list.fenceStatus == 4 }">												<span class="shebeistatus_weiyong" id="status">未用</span>											</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose>
									</td>
									<td  id="weilan${list.hostID }_fengongsi"><c:out value="${list.subComName }"></c:out></td>
									<td  id="weilan${list.hostID }_guanlichu"><c:out value="${list.mntMentName }"></c:out></td>
									<td align="center">
										<a href="javascript:void(0);" title="查看告警" id="" weilanid=${list.hostID } class="view_weilangaojing viewgaojingLink">
											查看告警
										</a>
										<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'修改电子围栏') }">
											<a onclick="return checkStatus('${list.fenceStatus}')" href="<c:url value='/fence/goModFence?fenceBean.hostID=${list.hostID}&${fenceCondition.searchUri}&page.size=10&page.offset=${page.offset }'></c:url>" title="编辑" class="edit_yinpin editlink left10">
											编辑
											</a>
										</c:if>
										
										<%--
										<a href="<c:url value='/sounddevUpdateEnter?id=${soundDevResult.id }&${queryCondition.searchUri}&querycondition.size=10&querycondition.offset=${page.offset }'></c:url>" title="编辑" class="edit_yinpin editlink">
											编辑
										</a>
									--%></td>
								</tr>
							</c:forEach>
						</tbody>
				    </table>
				</div>
				<input type="hidden" id="searchUri" value="${page.objCondition.searchUri}">
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
									window.location.href=$("#ctx").val()+"/fence/queryFence?"+'${fenceCondition.searchUri}'+"&page.size=10&page.offset="+jpage;
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
										<a href="<c:url value="/fence/queryFence?${fenceCondition.searchUri }&page.size=10&page.offset=1"/>">首页</a>
									</li>
									<c:if test="${page.offset > 1}">
										<li class="paginItem">
											<a href="<c:url value='/fence/queryFence?${fenceCondition.searchUri }&page.size=10&page.offset=${page.offset-1 }'></c:url>">上页</a>
										</li>
									</c:if>
									<c:if test="${page.offset < page.pages }">
										<li class="paginItem">
											<a href="<c:url value='/fence/queryFence?${fenceCondition.searchUri }&page.size=10&page.offset=${page.offset+1 }'></c:url>">下页</a>
										</li>
									</c:if>
									<li class="paginItem2"><span>第<input type="text" id="jumpPages" value="${page.offset }" name="jumpPages">页</span></li>
									<li class="paginItem"><a href="javascript:void(0);" onclick="jumpPages(this);" maxPages="${page.pages }">跳转</a></li>
									<li class="paginItem">
										<a href="<c:url value="/fence/queryFence?${fenceCondition.searchUri }&page.size=10&page.offset=${page.pages }"/>">尾页</a>
									</li>
								</ul>
							</c:if>
						</div>
					</c:if>
				</div>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
		<div id="weilan_info" style="display: none;z-index:0;" wid="60">
			<div class='dialogModal_header'>围栏主机详细信息</div>
			<div class='dialogModal_content'>
				<table class="detail_table">
					<tbody>
						<tr>
							<td class="title_name">主机ID:</td>
							<td class="content content_zhujiid"></td>
							<td class="title_name">围栏类型:</td>
							<td class="content content_weilanleixing"></td>
							<td class="title_name">围栏名称:</td>
							<td class="content content_weilanmingcheng"></td>
						</tr>
						<tr>
							<td class="title_name">网络地址:</td>
							<td class="content content_wangluodizhi"></td>
							<td class="title_name">端口号:</td>
							<td class="content content_duankouhao"></td>
							<td class="title_name">围栏状态:</td>
							<td class="content content_weilanzhuangtai"></td>
						</tr>
						<tr>
							<td class="title_name">设备ID:</td>
							<td class="content content_shebeiid"></td>
							<td class="title_name">安装时间:</td>
							<td class="content content_anzhuangshijian"></td>
							<td class="title_name">软件版本:</td>
							<td class="content content_ruanjianbanben"></td>
						</tr>
						<tr>
							<td class="title_name">分公司:</td>
							<td class="content content_fengongsi"></td>
							<td class="title_name">管理处:</td>
							<td class="content content_guanlichu"></td>
							<td class="title_name">产品型号:</td>
							<td class="content content_chanpinxinghao"></td>
						</tr>
						<tr>
							<td class="title_name">经度:</td>
							<td class="content content_jingdu"></td>
							<td class="title_name">纬度:</td>
							<td class="content content_weidu"></td>
							<td class="title_name">生产厂商:</td>
							<td class="content content_shengchanchangshang"></td>
						</tr>
						<tr>
							<td class="title_name">备注:</td>
							<td colspan="6" class="content content_beizhu"></td>
						</tr>
					<tbody>
				</table>
			</div>
			<div class="left20 top10" style="padding-bottom:20px;">
				<input class="left20 btn" data-dialogModalBut="cancel" type="button" value="关闭"/>
			</div>
		</div>
		<div id="view_weilan_gaojing_container" style="display: none;z-index:0;" wid="60">
			<div class='dialogModal_header'>围栏主机设备告警</div>
			<div class='dialogModal_content'>
				<div class="tableui_container" id="weilan_gaojing_content_container">
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
		</div>
		<div class="normalajaxloading" id="data_loading"></div>
	</body>
</html>