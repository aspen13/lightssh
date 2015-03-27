<%@ include file="/pages/common/util/taglibs.jsp" %>
<%@ page pageEncoding="utf-8" isErrorPage="true"%>

<html>
	<head>
		<title>系统异常</title>
		<link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/styles/<mys:theme />/theme.css" />
	</head>

	<body style="margin: 0;">
		<h3> 应用程序出现异常!</h3>
		
		<s:property value="exception.message"/>
		<div>
			<s:property value="exception"/>
			<s:property value="exception.cause"/>
		</div>
		
		<div id="background" class="error"></div>
	</body>
</html>