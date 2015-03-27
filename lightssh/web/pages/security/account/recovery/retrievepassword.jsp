<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<head>
	<meta name="decorator" content="public"/>
	<title>重置登录密码</title>
	
	<script type="text/javascript">
		$(document).ready(function(){
		});

		function refreshCaptcha(){
			var src = "<%= request.getContextPath() %>/images/kaptcha.jpg?rnd=" + Math.random();
			$("#img_captcha").attr("src",src);
		}

		function dosubmit(){
			$("input[type=submit]").attr("disabled","true");
			
			$("form").submit();
			return true;
		}
	</script>
</head>	

<body class="public">
	<h1>重置<strong><s:property value="account.loginName"/></strong>的登录密码</h1>

	<form method="post" action="<s:url value="/security/recovery/retrieve.do"/>" onsubmit="return dosubmit()">
		<label>
			帐户新密码
			<p><s:password name="passwords[0]" size="40"/></p>
		</label>
		<label>
			再次输入密码
			<p><s:password name="passwords[1]" size="40"/></p>
		</label>
		
		<label for="captcha">验证码</label>
		<p>
			<s:textfield id="captcha" name="captcha"/>
		</p>
		
		<img id="img_captcha" alt="captcha" style="margin-left:0 !important;margin:4px 0 0 2px;" 
			src="<%= request.getContextPath() %>/images/kaptcha.jpg">
		<span class="refresh" onclick="refreshCaptcha()">刷新</span>
		
		
		<s:hidden name="account.loginName" />
		<s:hidden name="safeMessage"/>
		
		<p>
			<br/>
			<input type="button" value="返回" class="action plain" 
				onclick="location.href='<s:url value="/security/recovery/account.do"/>'"/>
				
			<input type="submit" value="继续" class="action plain" />
		</p>
	</form>
</body>