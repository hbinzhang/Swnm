<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title>欢迎登录安防综合监控系统</title>
		<link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
		<script language="javascript">
			$(function(){
			    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
				$(window).resize(function(){  
			    		$('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2
			   		});
		    	});
				$(".text_login").focus(function () {
	                if ($(this).val() == '请输入用户名' || $(this).val() == '请输入密码') {
	                    $(this).val('');
	                }
	                if ($(this).attr('id') == 'pwd_text') {
	                    $(this).hide();
	                    $('#password').show();
	                    $('#password').focus();
	                }
	            });
	            $(".text_login").blur(function () {
	                if ($(this).attr('id') == 'password' && $(this).val() == '') {
	                    $(this).hide();
	                    $('#pwd_text').show();
	                    $('#pwd_text').val('请输入密码');
	                }
	                else if ($(this).attr('id') == 'userName' && $(this).val() == '') {
	                    $(this).val('请输入用户名');
	                }
	            });
			});  
		</script> 
	</head>
	<body style="">
		<div id="mainBody">
			<div id="cloud1" class="cloud"></div>
			<div id="cloud2" class="cloud"></div>
		</div>
		<div class="logintop">
			<span>欢迎登录安防综合监控系统</span>
			<ul>
				<li><a href="">回首页</a></li>
				<li><a href="">帮助</a></li>
				<li><a href="">关于</a></li>
			</ul>
		</div>
		<div class="loginbody">
			<span class="systemlogo"></span>
			<div class="loginbox">
				<form action="${pageContext.request.contextPath}/views/gaojingjianshi.jsp" method="post">
					<ul>
						<li>
							<input name="userName" id="userName" type="text" class="loginuser text_login" value="请输入用户名"/>
						</li>
						<li>
							<input name="pwd_text" id="pwd_text" type="text" class="loginuser text_login" value="请输入密码"/>
						</li>
						<li>
							<input name="password" id="password" type="password" class="loginpwd text_login" style="display:none;"/></li>
						<li>
							<input name="" type="submit" class="loginbtn" value="登录"/>
							<label>
								<a href="">忘记密码？</a>
							</label>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="loginbm">版权所有&nbsp;：南水北调中线干线工程建设管理局</div>
	</body>
</html>