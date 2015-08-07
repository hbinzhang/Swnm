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
	</head>
	<body>
		<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value=""></c:url>">首页</a></li>
					<li><a href="<c:url value=""></c:url>">系统管理</a></li>
					<li><a href="<c:url value=""></c:url>">日志管理</a></li>
					<li>操作日志</li>
				</ul>
			</div>
			<div id="content_container">
				<div class="normal_tab">
				  	<ul>
					    <li><a href="<c:url value='/views/rizhi/anquan.jsp'></c:url>">安全日志</a></li>
						<li><a class="selected" href="<c:url value='/views/rizhi/caozuo.jsp'></c:url>">操作日志</a></li>
				  	</ul>
			    </div>
			   	<div class="search_container">
			   		<form action="" method="get">
			   			<div>
			    			<div class="single_filter_container left">
					    		<label class="search_name">查询条件:</label>
					    		<div class="left single_filter" style="margin-top:8px;margin-left:0px;" id="jigou_gonghao_search_control">
					    			<input type="radio" value=1 name="search_filter" id="search_by_jigou" checked="checked">按机构查询
					    			<input type="radio" value=2 name="search_filter" id="search_by_gonghao" class="left20">按工号查询
					    		</div>
					    	</div>
					    	<div class="left" id="searchjigou_container">
					    		<div class="single_filter_container left">
						    		<label class="search_name">机构级别:</label>
						    		<div class="left single_filter">
						    			<select id="normal_jibie_select" class="normalselect">
						    				<option value="0">总公司</option>
						    				<option value="1">分公司</option>
						    				<option value="2">管理处</option>
						    			</select>
						    		</div>
						    	</div>
						    	<div class="single_filter_container left">
						    		<label class="search_name">机构名称:</label>
						    		<div class="left single_filter">
						    			<select id="normal_mingcheng_select" class="normalselect">
						    				<option value="0">总公司</option>
						    			</select>
						    		</div>
						    	</div>
					    	</div>
					    	<div class="single_filter_container left" style="display:none;" id="searchgonghao_container">
					    		<label class="search_name">工号:</label>
					    		<div class="left single_filter">
					    			<input type="text" name="" id="" class="normaltext">
					    		</div>
					    	</div>
					    	<div class="clear"></div>
				    	</div>
				    	<div>
				    		<div class="single_filter_container left">
					    		<label class="search_name">开始时间:</label>
					    		<div class="left single_filter">
					    			<input type="text" name="fromdate" class="normaltext" id="fromdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm' })"/>
									<a href="javascript:void(0)" class="underlinenone">
										<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm' })" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />
									</a>
					    		</div>
					    	</div>
					    	<div class="single_filter_container left">
					    		<label class="search_name">结束时间:</label>
					    		<div class="left single_filter">
					    			<input type="text" name="todate" class="normaltext" id="todate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm' })"/>
									<a href="javascript:void(0)" class="underlinenone">
										<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm' })" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />
									</a>
					    		</div>
					    	</div>
					    	<div class="single_filter_container left">
					    		<label class="search_name">操作结果:</label>
					    		<div class="left single_filter">
					    			<select id="" class="normalselect">
						    			<option value="">全部</option>
					    				<option value="">成功</option>
					    				<option value="">失败</option>
					    			</select>
					    		</div>
					    	</div>
					    	<div class="single_filter_container left">
					    		<label class="search_name">操作标识:</label>
					    		<div class="left single_filter">
					    			<select id="" class="normalselect">
						    			<option value="">全部</option>
					    				<option value="">登录</option>
					    				<option value="">注销</option>
					    			</select>
					    		</div>
					    	</div>
					    	<div class="single_filter_container left">
					    		<label class="search_name">操作来源:</label>
					    		<div class="left single_filter">
					    			<select id="" class="normalselect">
					    				<option value="">全部</option>
					    				<option value="">来源1</option>
					    				<option value="">来源2</option>
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
			    <div class="tableui_container">
			    	<div class="right">
			    		<input type="button" value="清除" class="btn">
			    	</div>
			    	<div class="clear"></div>
					<table class="tableui">
				    	<thead>
					    	<tr>       
						        <th><span>流水号</span></th>
						        <th><span>工号</span></th>
								<th><span>操作时间</span></th>
						        <th><span>操作来源</span></th>
						        <th><span>操作标识</span></th>
								<th><span>操作结果</span></th>
								<th><span>主机名称</span></th>
								<th><span>主机地址</span></th>
								<th><span>操作结束时间</span></th>
								<th><span>机构名称</span></th>
					        </tr>
				        </thead>
				        <tbody>
				        	<c:forEach begin="1" end="10">
				        		<tr>        
							       	<td>
							        	<a href="javascript:void(0);" class="detail caozuo_detail">001</a>
							        </td>
							        <td>工号1</td>
							        <td>2014-1-1 22:57:59</td>
							        <td>操作源1</td>
							        <td>标识1</td>
									<td>结果1</td>
									<td>主机1</td>
							        <td>192.168.1.1</td>
							        <td>2014-1-1 22:59:59</td>
									<td>机构1</td>
						        </tr> 
				        	</c:forEach>
				        </tbody>
				    </table>
				</div>
				<div class="pagin">
			    	<div class="message left">共<a class="blue" href="javascript:void(0);">1256</a>条记录，当前显示第&nbsp;<a class="blue" href="javascript:void(0);">2&nbsp;</a>页</div>
			        <ul class="paginList">
				        <li class="paginItem"><a href="javascript:;">上页</a></li>
				        <li class="paginItem"><a href="javascript:;">下页</a></li>
						<li class="paginItem2"><span>第<input type="text" value="" name="">页</span></li>
						<li class="paginItem"><a href="javascript:;">跳转</a></li>
			        </ul>
			    </div>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
		<div id="caozuo_detail_container" style="display: none;z-index:0;" wid="60">
			<div class='dialogModal_header'>操作日志详细信息</div>
			<div class='dialogModal_content'>
				<table class="detail_table">
					<tbody>
						<tr>
							<td class="title_name">流水号:</td>
							<td class="content content_zhujiid">00001</td>
							<td class="title_name">操作时间:</td>
							<td class="content content_gaojingma">2015-01-01 23:54:56</td>
							<td class="title_name">账号名称:</td>
							<td class="content content_mingcheng">张三</td>
						</tr>
						<tr>
							<td class="title_name">操作标识:</td>
							<td class="content content_zhujiid">1110</td>
							<td class="title_name">操作对象:</td>
							<td class="content content_gaojingma">告警</td>
							<td class="title_name">操作结果:</td>
							<td class="content content_mingcheng">成功</td>
						</tr>
						<tr>
							<td class="title_name">主机名称:</td>
							<td class="content content_shijian">主机1</td>
							<td class="title_name">主机地址:</td>
							<td class="content content_jibie">192.168.1.1</td>
							<td class="title_name">结束时间:</td>
							<td class="content content_leixing">2015-01-01 23:56:56</td>
						</tr>
						<tr>
							<td class="title_name">机构标识:</td>
							<td class="content content_shebeiid">北京分公司</td>
							<td class="title_name">操作源:</td>
							<td class="content content_shebeiid">操作源1</td>
							<td class="title_name"></td>
							<td class="title_name"></td>
						</tr>
						<tr>
							<td class="title_name">详细信息:</td>
							<td class="content content_shebeileixing" colspan="3">详细信息</td>
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