<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<head>
	<meta name="decorator" content="public"/>
	<title>帐户帮助</title>
	
	<script type="text/javascript">
	</script>
</head>	

<body class="public">
	<h1>帐户帮助</h1>
	
	<s:if test="account != null ">
		<p>
			<div class="secondary">
				已向 <strong><s:property value="account.email"/></strong>
				发送了一封电子邮件，其中含有与该地址关联的用户名。
				<br/>
				收到此邮件后，你可用使用该用户名进行登录。如果您忘记了登录密码，可以通过链接
				<a href="<s:url value="/security/recovery/account.do"/>">忘记密码</a>找回。
			</div>
			
			<div class="secondary">
				<br/>
				如果<strong> <s:property value="account.email"/></strong>
				不是您的邮箱地址，您就不会收到电子邮件。
			</div>
		</p>
	</s:if>
	<s:else>
		<div class="secondary">
			<br/>
			请确保您所提供的邮箱地址是正确的，否则您就不会收到电子邮件。
		</div>
		
	</s:else>
		
	<p>
		<br/>
		<s:if test="account == null ">
			<input type="button" value="返回" class="action plain" 
				onclick="location.href='<s:url value="/security/recovery/forgotusername.do"/>'"/>
		</s:if>
		<input type="button" value="返回登录" class="action plain" 
			onclick="location.href='<s:url value="/login.do"/>'"/>
	</p>
</body>