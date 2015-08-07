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
		<script src="${pageContext.request.contextPath}/resources/js/fangqu.js" type="text/javascript"></script>
	</head>
	<body>
		<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value=""></c:url>">首页</a></li>
					<li><a href="<c:url value=""></c:url>">设备管理</a></li>
					<li>防区管理</li>
				</ul>
			</div>
			<div id="content_container">
				<c:import url="../layout/shebeitab.jsp?sTabLink=fangqu" charEncoding="UTF-8"></c:import>
				<div class="search_container">
					<div class="left" style="margin-top:5px;">
			    		<div class="single_filter_container left">
				    		<label class="search_name">分公司:</label>
				    		<div class="left single_filter">
				    			<select id="normal_gongsi_select" class="normalselect">
				    				<option value="0">全部</option>
				    				<option value="1">北京分公司</option>
				    				<option value="2">河北分公司</option>
				    			</select>
				    		</div>
				    	</div>
				    	<div class="single_filter_container left">
				    		<label class="search_name">管理处:</label>
				    		<div class="left single_filter">
				    			<select id="normal_guanlichu_select" class="normalselect">
				    				<option value="0">全部</option>
				    			</select>
				    		</div>
				    	</div>
				    	<div class="single_filter_container left" style="width:520px;">
				    		<label class="search_name">附加条件:</label>
				    		<select class="normalselect" id="fangqu_search_filter">
								<option value="1">防区ID</option>
								<option value="2">设备ID</option>
								<option value="3">防区名</option>
								<option value="4">类型</option>
							</select>
							<input type="text" class="normaltext" name="" value="" id="yingpininput" style="width:300px;">
				    	</div>
				    	<div class="clear"></div>
			    	</div>
			    	<div class="left" style="margin-top:5px;">
			    		<input type="submit" class="btn" value="查询" style="margin-left:10px;">
						<a href="<c:url value='/views/shebei/fangquadd.jsp'></c:url>" class="hrefbtn">添加</a>
						<a href="" class="hrefbtn">删除</a>
			    	</div>
			    	<div class="clear"></div>
				</div>
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
							<c:forEach begin="1" end="15" var="index">
								<tr>
									<td align="center">
										<input type="checkbox" name="deleteids"/>
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
												<%--<a href="javascript:void(0);" title="删除" class="delete deletelink left10">
													删除
												</a>
												--%><a href="javascript:void(0);" title="撤防" class="left10 chefanglink">
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
												<%--<a href="javascript:void(0);" title="删除" class="delete deletelink left10">
													删除
												</a>
												--%><a href="javascript:void(0);" title="布防" class="left10 bufanglink">
													布防
												</a>
											</td>
							        	</c:otherwise>
							        </c:choose>
						        </tr> 
							</c:forEach>
						</tbody>
				    </table>
				</div>
				<c:import url="../layout/pager.jsp"  charEncoding="UTF-8"></c:import>
			</div>
		</div>
		<c:import url="../layout/footer.jsp"  charEncoding="UTF-8"></c:import>
		<div id="view_fangqu_gaojing_container" style="display: none;z-index:0;" wid="60">
			<div class='dialogModal_header'>电子围栏相关告警</div>
			<div class='dialogModal_content'>
				<div class="tableui_container">
					<table class="tableui">
				    	<thead>
					    	<tr>       
						        <th><span>告警编号</span></th>
								<th><span>告警码</span></th>
						        <th><span>告警名称</span></th>
						        <th><span>告警时间</span></th>
						        <th><span>告警级别</span></th>
								<th><span>告警类型</span></th>
								<th><span>设备类型</span></th>
								<th><span>设备IP</span></th>
								<th><span>分公司</span></th>
								<th><span>管理处</span></th>
								<th><span>防区</span></th>
								<th><span>处理状态</span></th>
					        </tr>
				        </thead>
				        <tbody>
				        	<c:forEach begin="1" end="10" var="index">
				        		<tr>        
							        <td>
							        	<a class="detail" id="gaojing_detail" href="javascript:void(0);">001${index }</a>
							        </td>
							        <td>1</td>
							        <td>入侵</td>
							        <td>2014-1-1 23:59:59</td>
							        <td>严重</td>
									<td>安防</td>
							        <td>震动光纤</td>
									<td>192.168.1.2</td>
									<td>河北</td>
									<td>易县</td>
							        <td>5</td>
									<td>已处理</td>
						        </tr> 
				        	</c:forEach>
				        </tbody>
				    </table>
				</div>
			</div>
			<div class="left20 top10" style="padding-bottom:20px;">
				<input class="left20 btn" data-dialogModalBut="cancel" type="button" value="关闭"/>
			</div>
		</div>
	</body>
</html>