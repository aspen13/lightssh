<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<head>
	<meta name="decorator" content="public"/>
	<title>找回登录密码</title>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$(".radio-option .hideable-box:last").hide();
			$("input[name=forgot]:first").attr("checked",true);
		});

		function recoveryOptionSelected(){
			$("input[type=submit]").removeAttr("disabled");
			var eTarget = event.currentTarget?event.currentTarget:event.srcElement;
			$(".radio-option .hideable-box").hide('fast');
			$(eTarget).parent().children(".hideable-box").show('fast');
		}
		
		function refreshCaptcha(){
			var src = "<%= request.getContextPath() %>/images/kaptcha.jpg?rnd=" + Math.random();
			$("#img_captcha").attr("src",src);
		}

		function dosubmit(){
			$("input[type=submit]").attr("disabled","true");
			
			var url = "<s:url value="/security/recovery/emailrecovery.do"/>";
			if( $("input[name='forgot']:checked").val()!='email')
				url = "<s:url value="/security/recovery/bysafequestion.do"/>";

			$("form").attr("action",url);
			
			$("form").submit();
			
		}
	</script>
</head>	

<body class="public">
	<h1>关于<strong><s:property value="account.loginName"/></strong>的密码帮助</h1>
	
	<form method="post">
		<div class="radio-option">
			<input type="radio" name="forgot" value="email" id="radio1" onclick="recoveryOptionSelected()">
			<label class="radio-label" for="radio1">将重设密码链接发送到我的邮箱：<s:property value="account.email"/></label>
			<div class="hideable-box">
				<label for="captcha">验证码</label>
				<p>
					<s:hidden name="account.loginName"/>
					<s:hidden name="account.email"/>
					<s:textfield name="captcha" size="20"/>
				</p>
				
				<img id="img_captcha" alt="captcha" style="margin-left:0 !important;margin:4px 0 0 2px;" 
					src="<%= request.getContextPath() %>/images/kaptcha.jpg">
				<span class="refresh" onclick="refreshCaptcha()">刷新</span>
			</div>
		</div>
		
		<div class="radio-option">
			<input type="radio" name="forgot" value="safequestion" id="radio2" onclick="recoveryOptionSelected()">
			<label class="radio-label" for="radio2">回答我的安全问题</label>
			<div class="hideable-box">
				<div class="secondary">安全问题...</div>
				<label>
					安全问题答案
					<p>
						<s:textfield size="40"/>
					</p>
				</label>
				
			</div>
		</div>
		
		<br/>
		<input type="button" value="返回" class="action plain" 
			onclick="location.href='<s:url value="/security/recovery/account.do"/>'"/>
		<input type="submit" value="继续" class="action plain" onclick="dosubmit()"/>
	</form>
</body>