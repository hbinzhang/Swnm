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
		<script>
			$(function(){
				getCurrentOrgByCurrentBranch();
			});
			function getCurrentOrgByCurrentBranch(){
				var orglev=$("#orglevel").val();
				if(orglev==2){
					return;
				}
				jQuery.ajax({
					type : 'GET',
					cache:false,
					url : $("#ctx").val()+"/alarmmgt/queryDepartByBranch?branchId="+$("#normal_gongsi_select").val(),
					contentType: "application/json; charset=utf-8",
					success : function(data) {
						if(data.result==1)
						{
							var content = "";
							for(var i=0;i<data.object.length;i++)
							{	
								content+="<option value="+data.object[i].id+">"+data.object[i].name+"</option>";
							}
							$("#normal_guanlichu_select").empty();
				        	$("#normal_guanlichu_select").append(content);
				        	
						}
					},
					error : function(data) {
					}
				});
			}
		</script>
	</head>
	<body>
		<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/fence/queryFence"></c:url>">设备管理</a></li>
					<li><a href="<c:url value="/fence/queryFence"></c:url>">围栏主机</a></li>
					<li>添加</li>
				</ul>
			</div>
			<div id="content_container">
				<c:import url="../layout/shebeitab.jsp?sTabLink=weilan" charEncoding="UTF-8"></c:import>
				<div class="roleadd_container">
					<input type="hidden" id="redirecturl" value="/fence/queryFence?${fenceCondition.searchUri}&page.size=10&page.offset=${page.offset }">
					<form action="" method="post" class="left50 top30" id="weilanadd_form">
						<input type="hidden" value="${sessionScope.session.lev}" id="orglevel" />
						<div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">主机ID:</label>
								<input type="text" name="fenceBean.hostID" value="${fenceBean.hostID }" id="hostId" class="normaltext validate[required]" style="width: 500px;">
								<span class="exithostId" style="color:red;">主机Id已存在</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">围栏类型:</label>
								<select id="" class="normalselect"  style="width: 513px;" name="fenceBean.fenceType">
									<option value="1">高压脉冲主机</option>
									<option value="2">一体化探测</option>
									<option value="3">防区型振动光纤</option>
									<option value="4">定位型振动光纤</option>
								</select>
								<span class="input_tip">*围栏类型为必选项</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">围栏名称:</label>
								<input type="text"  id="" class="normaltext validate[required,maxSize[32]]" style="width: 500px;" name="fenceBean.fenceName">
								<span class="input_tip">*围栏名称为必填项</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">网络地址:</label>
								<input type="text" name="fenceBean.ip" id="" class="normaltext validate[required,custom[ipv4]]" style="width: 500px;">
								<span class="input_tip">*网络地址为必填项,格式例如192.168.1.1</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">端口号:</label>
								<input type="text" name="fenceBean.port" id="" class="normaltext validate[required,maxSize[7],custom[integer]]" style="width: 500px;">
								<span class="input_tip">*端口号为必填项，必须为数字</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">分公司:</label>
								<select id="normal_gongsi_select" class="normalselect"  style="width: 513px;" name="fenceBean.subComID">
									<c:choose>
				    					<c:when test="${sessionScope.session.lev eq '0' }">
				    						<c:forEach items="${sessionScope.session.orgIdAndNames.subCompanys }" var="companyBean">
				    						<c:choose>
				    							<c:when test="${companyBean.id ==fenceBean.subComID}">
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
								<span class="input_tip">*分公司为必填项，选择分公司之后，对应的管理处会发生变化</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">管理处:</label>
								<select id="normal_guanlichu_select" class="normalselect validate[required]"  style="width: 513px;" name="fenceBean.mntMentID">
				    				<c:choose>
				    					<c:when test="${sessionScope.session.lev eq '0' }">
				    						<c:forEach items="${sessionScope.session.orgIdAndNames.managements }" var="departBean">
					    						<c:choose>
					    							<c:when test="${departBean.id ==fenceBean.mntMentID}">
					    								<option value="${departBean.id }" selected="selected" >${departBean.name }</option>
					    							</c:when>
					    							<c:otherwise>
					    								<option value="${departBean.id }">${departBean.name }</option>
					    							</c:otherwise>
					    						</c:choose>
				    						</c:forEach>
				    					</c:when>
				    					<c:when test="${sessionScope.session.lev eq '1' }">
				    						<c:forEach items="${sessionScope.session.orgIdAndNames.managements }" var="departBean">
				    						<c:choose>
				    							<c:when test="${departBean.id ==fenceBean.mntMentID}">
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
								<span class="input_tip">*管理处为必填项，此处的管理处是上面分公司所属的管理处</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">经度:</label>
								<input type="text" id="" class="normaltext validate[required,custom[number]]" style="width: 500px;" name="fenceBean.efLongitude">
								<span class="input_tip">*经度为必填项，必须为数字</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">纬度:</label>
								<input type="text" id="" class="normaltext validate[required,custom[number]]" style="width: 500px;" name="fenceBean.efLatitude">
								<span class="input_tip">*纬度为必填项，必须为数字</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">产品型号:</label>
								<input type="text" id="" class="normaltext" style="width: 500px;" name="fenceBean.hardwareVer">
								<span class="input_tip">*产品型号必填项</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">软件版本:</label>
								<input type="text"  id="" class="normaltext" style="width: 500px;" name="fenceBean.sorfwareVer">
								<span class="input_tip">*软件版本为必填项</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">生产厂商:</label>
								<!-- <input type="text" name="" id="" class="normaltext" style="width: 500px;" name="fenceBean.vendorID"> -->
								<select class="normalselect"  style="width: 513px;"  name="fenceBean.vendorID" id="shengchanchangshang">
									<c:forEach items="${manufacturers }" var="mf">
										<option value="${mf.vendorID }"><c:out value="${mf.vendorName }"></c:out></option>
									</c:forEach>
								</select>
								<span class="input_tip">*生产厂商为必填项</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">备注:</label>
								<input type="text"  id="" class="normaltext" style="width: 500px;" name="fenceBean.remarks">
								<span class="input_tip">*备注信息为可选项，记录对此围栏的特别说明</span>
								<div class="clear"></div>
							</div> 
							<div class="clear"></div>
						</div>
						<div class="normalajaxloading" id="data_loading"></div>
						<div class="left40 top20">
							<input type="button" value="添加" class="btn left20 top5" id="weilanaddbtn"/>
							<a href="<c:url value='/fence/queryFence?${fenceCondition.searchUri}&page.size=10&page.offset=${page.offset }'></c:url>" class="hrefbtn">取消</a>
						</div>
					</form>
				</div>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
	</body>
</html>