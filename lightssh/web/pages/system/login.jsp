<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language ="java" pageEncoding = "UTF-8" contentType="text/html;charset=utf-8" %> 
<%@ page import ="com.google.code.lightssh.common.support.shiro.*"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>	
	<title>系统登录</title>
	<link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/styles/<mys:theme />/theme.css" />
	<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/plugins/cryptography/jquery.md5.js"></script>
		
	<script type="text/javascript">
		$(document).ready(function(){
			if( top != self ){
				//top.location.href=location.href;
				//top.frames['navigation_frame'].hidden();
			}
			//$("#js_warning").remove();
			$("#password").attr("value",'');
			paint();
		});

		/** 渲染背景 */
		function paint(){
			var body = $("body[id='login']");
			//$(body).append("<div id='paint_canvas'></div>")
			var canvas = $("#paint_panel");

			var CELL_HEIGHT = 1;
			for( var i=0;i<$(canvas).height()/CELL_HEIGHT;i++){
				var cell = $("<div class='paint_cell'></div>");
				$(canvas).append( cell )
				var color = randomColor();
				if($.browser.msie){
					$(cell).attr("style","filter:progid:DXImageTransform.Microsoft.Gradient(StartColorStr='#FFFFFF', EndColorStr='"+color+"', GradientType=1)");
				}else if( $.browser.mozilla ){
					$(cell).css("background","-moz-linear-gradient(left, #FFFFFF, "+color+")");
				}else if( $.browser.opera ){
					$(cell).css("background","-o-linear-gradient(left, #FFFFFF,"+color+")");
				}else if( $.browser.webkit ){
					$(cell).css("background","-webkit-gradient(linear,left top, right top, from(#FFFFFF), to("+color+"))");
				}

				$(cell).css("height",CELL_HEIGHT+"px");
			}
		}

		/** 随机颜色 */
		function randomColor() {
			var arrHex = ["0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"];	
			var strHex = "#";	
			var index;	
			for(var i = 0; i < 6; i++) {
				index = Math.round(Math.random() * 15);		
				strHex += arrHex[index];
			}	
			return strHex;
		}
		
		function dosubmit(){
			if( $("#username").attr('value') == ''){
				showMessage("'用户帐号'不能为空！");
				$("#username").focus();
				return false;
			}else if( $("#password").attr('value') == ''){
				showMessage("'登录密码'不能为空！");
				$("#password").focus();
				return false;
			}else if( $("#captcha").attr('value') == ''){
				showMessage("'验证码'不能为空！");
				$("#password").focus();
				return false;
			}
			
			$("#password").attr("value",$.md5($("#password").attr("value")));
			return true;				
		}
		
		/**
		 * 显示提示信息
		 */
		function showMessage( msg ){

			if($("div.messages").length == 0 ){
				$('#login_form').before("<div class='messages'></div>")
			}
			
			if( $('.messages > .error').length == 0 ){
				$('.messages').append("<div class='error'>"+msg+"</div>") 
			}else{
				$('.error').text( msg );
			}
		}
		
		function refresh(){
			var src = "<%= request.getContextPath() %>/images/kaptcha.jpg?rnd=" + Math.random();
			$("#jcaptcha").attr("src",src);
		}
	</script>
</head>

<body style="text-align: center;" id="login">
	
	<div id="login_panel">
	
	<div id="login">
		
		<div class="login_frame">
			<div class="login_title">
				<h3>用户登录</h3>
						
				<%-- 
				<div class="messages" id="js_warning">
					<div class="warning">您的浏览器不支持JAVASCRIPT，无法登录系统！</div>
				</div>
				--%>
				
				<%@ include file="/pages/system/login_error.jsp" %>
			</div>
			
			<div class="login_form">
				
				<s:form id="login_form" action="login" namespace="/" method="post" onsubmit="return dosubmit();">
					<ul>
						<li>
							<label for="username">账号&nbsp;&nbsp;&nbsp;</label>
							<input type='text' id="username" class="user" name='j_username' size="30" autocomplete="off"/>
						</li>
						<li>
							<label for="password">密码&nbsp;&nbsp;&nbsp;</label>
							<input type='password' id="password" class="password" name='j_password' size="30"/>
						</li>
						
						<s:if test="showCaptcha">
						<li>
							<label for="captcha">验证码</label>
							<input type='text' class="captcha" id="captcha" name='j_captcha' size="15"/>
							<span class="refresh" onclick="refresh();">看不清楚?</span><br/>
							<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
							<!--<img id="jcaptcha" src="<%= request.getContextPath() %>/images/jcaptcha" />-->
							<img id="jcaptcha" alt="captcha" style="margin-left:0 !important;margin:4px 0 0 2px;" 
								src="<%= request.getContextPath() %>/images/kaptcha.jpg"/>
						</li>
						</s:if>
						
						<%-- 
						<li>
							<label for="rememberme">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
							<input type="checkbox" id="rememberme" name="j_rememberme" value="true"/>记住登录
						</li>
						--%>
						<li>
							<label for="login_submit">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
							<input type="submit" value="登录" id="login_submit" class="action login"/>
							<a href="<s:url value="/security/recovery/account.do"/>">忘记密码？</a>
						</li>
					</ul>
				</s:form>
			</div>
		</div>
	</div>	
	
	</div>
	
	<div id='paint_canvas'>
		<div id="paint_panel"></div>
	</div>
	
	<div id="login_footer">
		Copyright &copy;2011-<s:property value="@com.google.code.lightssh.common.util.TextFormater@format(new java.util.Date(),'yyyy')"/>
		<a href="http://code.google.com/p/lightssh/">lightssh</a>,Licensed under the Apache License, Version 2.0.
	</div>
	
</body>
</html>
