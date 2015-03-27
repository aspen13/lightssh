<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<head>
	<meta name="decorator" content="public"/>
	<title>找回用户帐号</title>
	
	<script type="text/javascript">
		function refreshCaptcha(){
			var src = "<%= request.getContextPath() %>/images/kaptcha.jpg?rnd=" + Math.random();
			$("#img_captcha").attr("src",src);
		}

		function dosubmit(){
			$("input[type=submit]").attr("disabled","true");
			return true;
		}
	</script>
</head>	

<body class="public">
	<h1>忘记了您的用户名？</h1>
	
	<p>
		<div class="secondary">请输入您在创建帐户时提供的邮箱地址，然后输入下面图片显示的字符。</div>
	</p>
	
	<form method="post" action="<s:url value="/security/recovery/forgotusername.do"/>" onsubmit="return dosubmit()">
		<label>
			电子邮件地址
			<p>
				<s:textfield name="account.email" size="40"/>
			</p>
		</label>
		
		<label for="captcha">验证码</label>
		<p>
			<s:textfield id="captcha" name="captcha" size="30"/>
		</p>
		
		<p style="height: 60px;">
			<img id="img_captcha" alt="captcha" style="margin-left:0 !important;margin:4px 0 0 2px;" 
				src="<%= request.getContextPath() %>/images/kaptcha.jpg">
			<span class="refresh" onclick="refreshCaptcha()">刷新</span>
		</p>
		
		<input type="button" value="返回" class="action plain" 
			onclick="location.href='<s:url value="/security/recovery/account.do"/>'"/>
		<input type="submit" value="继续" class="action plain"/>
	</form>
</body>