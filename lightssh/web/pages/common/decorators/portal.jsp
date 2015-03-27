<!DOCTYPE PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/styles/<mys:theme />/theme.css" />
		<link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/scripts/jquery/styles/theme.css" />
		
		<script language="javascript" src="<s:url value="/scripts/jquery/jquery.min.js"/>"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/ui/jquery-ui.custom.min.js"></script>
		
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/plugins/validate/jquery.validate.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/plugins/validate/i18n/messages_<s:property value="locale"/>.js"></script>

        <decorator:head/>
        <title><decorator:title/></title>
	</head>
	
	<body <decorator:getProperty property="body.id" writeEntireProperty="true"/> <decorator:getProperty property="body.class" writeEntireProperty="true"/>>
		<div class="wrapper">
		
		<%@ include file="/pages/common/layout/header_portal.jsp" %>
		
		<div id="main">
			<decorator:body/>
		</div>
		
		<%@ include file="/pages/common/layout/footer_portal.jsp" %>
		</div>
	</body>
</html>