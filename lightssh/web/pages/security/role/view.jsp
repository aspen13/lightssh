<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
		<title>编辑角色</title>
		
		<script language="javascript" src="<%= request.getContextPath() %>/scripts/jquery/jquery.cookie.js"></script>
		<script language="javascript" src="<%= request.getContextPath() %>/scripts/jquery/plugins/treeview/jquery.treeview.js"></script>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/scripts/jquery/plugins/treeview/jquery.treeview.css" type="text/css">
		
		<script type="text/javascript">
			$(document).ready(function(){
				//$("#mytree").treeview();
				$("#navigation_tree").treeview({
					persist: "cookie",
					collapsed: false,
					cookieId: "treeview-navigation-view"
				});
			});
		</script>
	</head>
	
	<body>
		<ul class="path">
			<li>系统管理</li>
			<li>角色管理</li>
			<li>查看角色</li>
		</ul>
		
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<%@ include file="/pages/security/role/view_detail.jsp" %>
		
		<p class="submit">
			<input type="button" class="action back" value="返回" onclick="location.href='<s:url value="list.do"/>'"/>
		</p>
		
	</body>
</html>