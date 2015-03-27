<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<head>
	<meta name="decorator" content="public"/>
	<title>帐户帮助</title>
	
	<script type="text/javascript">
	</script>
</head>	

<body class="public">
	<h1>电子邮件已发送到<strong><s:property value="account.email"/></strong></h1>
	
	<s:if test="account != null ">
		<p>
			<div class="secondary">
				已向 <strong><s:property value="account.email"/></strong>
				发送了一封电子邮件，要恢复您的帐户，请按照邮件内容进行操作。
				<br/>
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