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
		<script type="text/javascript">
			$(function()
			{
				$("#contab02").height($("#contab01").height());
				$("#contab03").height($("#contab01").height());
			});
		</script>
	</head>
	<body>
		<c:import url="../layout/header.jsp?navLink=gaojingchuli"  charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/alarmmgt/queryDeviceAlarm"></c:url>">安防监控</a></li>
					<li>告警处理</li>
				</ul>
			</div>
			<div id="content_container">
			    <div id="left_gaojingchuli">
			    	<div class="top10 bottom10" style="width: 98%;margin-left: 1%;">
			    		<img alt="" src="${pageContext.request.contextPath}/resources/images/tupian1.png">
			    	</div>
			    	<div class="top10 bottom20" style="width:98%;margin-left: 1%;height:200px;overflow: scroll;">
			    		<table border="1" width="730" class="imgtable">
							<thead>
							<tr>
								<th><span>时间</span></th>
								<th><span>持续时间</span></th>
								<th><span>分公司</span></th>
								<th><span>管理处</span></th>
								<th><span>防区</span></th>
								<th><span>告警名称</span></th>
								<th><span>设备类型</span></th>
								<th><span>设备ID</span></th>
								<th><span>严重程度</span></th>
								<th><span>次数</span></th>
							</tr>
							</thead>
							<tbody>
								<tr  class="bgcolor">
									<td>2015年1月1日9:00</td>
									<td>5分</td>
									<td>北京分公司</td>
									<td>海淀管理处</td>
									<td>防区1</td>
									<td>告警1</td>
									<td>高压电子围栏</td>
									<td>0101</td>
									<td>严重</td>
									<td>5</td>
							  	</tr>
								<c:forEach begin="1" end="10">
									<tr>
										<td>2015年1月1日9:00</td>
										<td>5分</td>
										<td>北京分公司</td>
										<td>海淀管理处</td>
										<td>防区1</td>
										<td>告警1</td>
										<td>高压电子围栏</td>
										<td>0101</td>
										<td>严重</td>
										<td>5</td>
								  </tr>
								</c:forEach>
							</tbody>
			            </table>
			    	</div>
			    </div>
			    <div id="right_gaojingchuli">
			    	<div class="top10 bottom10" style="width: 96%;margin-left: 2%;">
			    		<img alt="" src="${pageContext.request.contextPath}/resources/images/tupian2.png">
			    	</div>
			    	<div class="top10 bottom20" style="width:96%;margin-left: 2%;">
				    	<div class="tab-hd">
							<li onclick="set('tab0',1,3)" id="tab01" style="margin-right:2px;cursor: pointer;" class="active">基本信息</li>
							<li onclick="set('tab0',2,3)" id="tab02" style="margin-right:2px;cursor: pointer;" class="">录像</li>
							<li onclick="set('tab0',3,3)" id="tab03" style="margin-right:2px;cursor: pointer;" class="">图像</li>
						</div>
						<form action="" method="post">
							<div class="tab-bd">
								<div id="contab01">
									<div class="top10">
										<div class="">
											<label style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">复核依据:</label>
											<input type="radio" id="fuheyiju" name="fuheyiju" style="margin-top:5px;margin-left:10px;"/>视频复核
											<input type="radio" id="fuheyiju" name="fuheyiju" style="margin-top:5px;margin-left:20px;"/>现场复核
											<div class="clear"></div>
										</div>
									</div>
									<div class="top5">
										<div class="">
											<label style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">警报类型:</label>
											<input type="radio" id="baojingleixing" name="baojingleixing" style="margin-top:5px;margin-left:10px;"/>虚警
											<input type="radio" id="baojingleixing" name="baojingleixing" style="margin-top:5px;margin-left:20px;"/>实警
											<div class="clear"></div>
										</div>
									</div>
									<div class="top5">
										<div class="">
											<label style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">复核严重级别:</label>
											<select class="normalselect left" style="width: 55%;">
												<option>轻微</option>
												<option>一般</option>
												<option>主要</option>
												<option>严重</option>
											</select>
											<div class="clear"></div>
										</div>
									</div>
									<div class="top5">
										<div class="">
											<label style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">事件原因:</label>
											<input type="text" id="" class="normaltext left" style="width: 53%;" value=""/>
											<div class="clear"></div>
										</div>
										<div class="clear"></div>
									</div>
									<div class="top5">
										<div class="">
											<label style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">事件过程:</label>
											<textarea rows="10" cols="10"  class="normaltext left" style="width: 53%;height:100px;"></textarea>
											<div class="clear"></div>
										</div>
										<div class="clear"></div>
									</div>
									<div class="top5">
										<div class="">
											<label style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">负责人:</label>
											<select class="normalselect left" style="width: 55%;">
												<option>张三</option>
												<option>李四</option>
												<option>王五</option>
											</select>
											<div class="clear"></div>
										</div>
										<div class="clear"></div>
									</div>
									<div class="top5">
										<div class="">
											<label style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">记录备注:</label>
											<textarea rows="10" cols="10"  class="normaltext left" style="width: 53%;height:100px;"></textarea>
											<div class="clear"></div>
										</div>
										<div class="clear"></div>
									</div>
								</div>
								<div style="display:none;min-height:300px;" id="contab02">
									<img alt="" src="${pageContext.request.contextPath}/resources/images/t2.png">
								</div>
								<div style="min-height:300px; display:none;" id="contab03">
									<img alt="" src="${pageContext.request.contextPath}/resources/images/t3.png">
								</div>
							</div>
							<div class="top10 bottom10" style="margin-left:20%;">
								<input type="button" value="确认" class="btn">
								<input type="button" value="上报" class="btn">
							</div>
						</form>
			    	</div>
			    </div>
			    <div class="clear"></div>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
	</body>
</html>