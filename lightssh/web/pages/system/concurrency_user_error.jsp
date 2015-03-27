<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<head>	
	<title>403</title>
	<link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/styles/<mys:theme />/theme.css" />
	<script language="javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.min.js"></script>
	
</head>

<body style="text-align: center;">
	<h3>Session已失效或账号已在其它地方登录！</h3>
	<a href="<s:url value="/login.do"/>" target="_top">重新登录</a>
</body>

