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
		<script src="${pageContext.request.contextPath}/resources/js/weilan.js" type="text/javascript"></script>
	</head>
	<body>
		<c:import url="../layout/header.jsp?navLink=dianziweilan" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value=""></c:url>">首页</a></li>
					<li><a href="<c:url value=""></c:url>">设备管理</a></li>
					<li>电子围栏</li>
				</ul>
			</div>
			<div id="content_container">
				<c:import url="../layout/shebeitab.jsp?sTabLink=weilan" charEncoding="UTF-8"></c:import>
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
				    		<select class="normalselect" id="weilan_search_filter">
								<option value="1">主机ID</option>
								<option value="2">类型</option>
							</select>
							<input type="text" class="normaltext" name="" value="" id="weilaninput" style="width:300px;">
							<select class="normalselect clickseclect" id="weilanleixing">
								<option>高压脉冲主机</option>
								<option>一体化探测</option>
								<option>定位型振动光纤</option>
								<option>防区型振动光纤</option>
							</select>
				    	</div>
				    	<div class="clear"></div>
			    	</div>
			    	<div class="left" style="margin-top:5px;">
			    		<input type="submit" class="btn" value="查询" style="margin-left:10px;">
						<a href="<c:url value="/views/shebei/weilanadd.jsp"></c:url>" class="hrefbtn">添加</a>
				    	<a href="" class="hrefbtn">批量导入</a>
				    	<a href="" class="hrefbtn">删除</a>
			    	</div>
			    	<div class="clear"></div>
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
							<c:forEach begin="1" end="15" var="index">
								<tr>
									<td align="center">
										<input type="checkbox" name="deleteids"/>
									</td>
							        <td>
							        	<a class="detail weilan_detail" id="weilan_detail" href="javascript:void(0);">001${index }</a>
							        </td>
							        <td>高压脉冲主机</td>
							        <td>114.126.5.123</td>
							        <td>110</td>
							        <td align="center">
							        	<c:choose>
							        		<c:when test="${index%3==0 }">
							        			<span class="shebeistatus_shiyong"></span>
							        		</c:when>
							        		<c:when test="${index%3==1 }">
							        			<span class="shebeistatus_gaojing"></span>
							        		</c:when>
							        		<c:otherwise>
							        			<span class="shebeistatus_lixian"></span>
							        		</c:otherwise>
							        	</c:choose>
							        </td>
							        <td>北京分公司</td>
							        <td>大兴管理处</td>
									<td align="center">
										<a href="javascript:void(0);" title="查看告警" id="" class="view_weilangaojing viewgaojingLink">
											查看告警
										</a>
										<a href="<c:url value="/views/shebei/weilanedit.jsp"></c:url>" title="编辑" class="edit_yinpin editlink left10">
											编辑
										</a>
									</td>
						        </tr> 
							</c:forEach>
						</tbody>
				    </table>
				</div>
				<c:import url="../layout/pager.jsp" charEncoding="UTF-8"></c:import>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
		<div id="weilan_info" style="display: none;z-index:0;" wid="60">
			<div class='dialogModal_header'>电子围栏详细信息</div>
			<div class='dialogModal_content'>
				<table class="detail_table">
					<tbody>
						<tr>
							<td class="title_name">主机ID:</td>
							<td class="content content_zhujiid">1110</td>
							<td class="title_name">围栏类型:</td>
							<td class="content content_gaojingma">高压脉冲主机</td>
							<td class="title_name">围栏名称:</td>
							<td class="content content_mingcheng">围栏名称</td>
						</tr>
						<tr>
							<td class="title_name">网络地址:</td>
							<td class="content content_shijian">114.126.5.123</td>
							<td class="title_name">端口号:</td>
							<td class="content content_jibie">110</td>
							<td class="title_name">围栏状态:</td>
							<td class="content content_leixing">使用中</td>
						</tr>
						<tr>
							<td class="title_name">设备ID:</td>
							<td class="content content_shebeileixing">234234</td>
							<td class="title_name">安装时间:</td>
							<td class="content content_shebeiid">2015-01-01</td>
							<td class="title_name">软件版本:</td>
							<td class="content content_zhuangtai">V3.0</td>
						</tr>
						<tr>
							<td class="title_name">分公司:</td>
							<td class="content content_fengongsi">北京分公司</td>
							<td class="title_name">管理处:</td>
							<td class="content content_guanlichu">大兴管理处</td>
							<td class="title_name">产品型号:</td>
							<td class="content content_fangqu">型号1</td>
						</tr>
						<tr>
							<td class="title_name">经度:</td>
							<td class="content content_fengongsi">53度</td>
							<td class="title_name">纬度:</td>
							<td class="content content_guanlichu">23度</td>
							<td class="title_name">生产厂商:</td>
							<td class="content content_fangqu">厂商1</td>
						</tr>
						<tr>
							<td class="title_name">备注:</td>
							<td colspan="6" class="content content_fujia">备注信息</td>
						</tr>
					<tbody>
				</table>
			</div>
			<div class="left20 top10" style="padding-bottom:20px;">
				<input class="left20 btn" data-dialogModalBut="cancel" type="button" value="关闭"/>
			</div>
		</div>
		<div id="view_weilan_gaojing_container" style="display: none;z-index:0;" wid="60">
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