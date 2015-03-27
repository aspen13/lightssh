<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<head>
	<meta name="decorator" content="public"/>
	<title>帐户恢复</title>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".radio-option .hideable-box").hide();
			$("input[type=submit]").attr("disabled","true");
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

		/**
		 * 提交数据
		 */
		function dosubmit( ){
			$("input[type=submit]").attr('disabled', 'true');//防重复提交
			
			var url = "<s:url value="/security/recovery/forgotusername.do"/>";
			if( $("input[name='forgot']:checked").val()=='password')
				url = "<s:url value="/security/recovery/forgotpassword.do"/>";

			$("form").attr("action",url);
			
			$("form").submit();
		}
		
	</script>
</head>

<body class="public">
	<h1>登录过程遇到问题了？</h1>
	<%@ include file="/pages/common/util/messages.jsp" %>
	<form method="post">
		<div class="radio-option">
			<input type="radio" name="forgot" value="password" id="radio1" onclick="recoveryOptionSelected()">
			<label class="radio-label" for="radio1">忘记了密码</label>
			<div class="hideable-box">
				<div class="secondary">要重置密码，请输入您登录系统时所用的用户名，该用户名可以是您的邮箱地址。</div>
				<label>
					电子邮件地址/用户名
					<p>
						<input id="subject" type="text" name="account.loginName" value="" size="40">
					</p>
				</label>
			</div>
		</div>
		
		<div class="radio-option">
			<input type="radio" name="forgot" value="username" id="radio2" onclick="recoveryOptionSelected()">
			<label class="radio-label" for="radio2">忘记了登录帐号</label>
			<div class="hideable-box">
				<div class="secondary">请输入您在创建帐户时提供的邮箱地址，然后输入下面图片显示的字符。</div>
				<label>
					电子邮件地址
					<p>
						<input id="email" type="text" name="account.email" value="" size="40"/>
					</p>
				</label>
				
				<label for="captcha">验证码</label>
				<p>
					<input id="captcha" type="text" name="captcha" value="" size="20">
				</p>
				
				<img id="img_captcha" alt="captcha" style="margin-left:0 !important;margin:4px 0 0 2px;" 
					src="<%= request.getContextPath() %>/images/kaptcha.jpg">
				<span class="refresh" onclick="refreshCaptcha()">刷新</span>
			</div>
		</div>
		
		<br/>
		<input type="submit" value="继续" class="action plain" onclick="dosubmit()"/>
	</form>
</body>