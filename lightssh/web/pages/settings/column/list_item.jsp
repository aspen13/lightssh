<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
		
		<title>定制化表</title>
		
		<script type="text/javascript">
		</script>
	</head>
	
	<body>
		<ul class="path">
			<li>基础管理</li>
			<li>定制化列</li>
			<li>属性列表</li>
		</ul>
		
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<input type="button" class="action back" value="返回" onclick="location.href='<s:url value="/settings/column/listtable.do"/>'"/>
		
		<mys:table cssClass="list" value="#request.column_item_list" dynamic="false" status="loop">
			<mys:column title="序号" width="40px" value="#loop.index + 1"/>
			<mys:column title="属性" value="name" width="200px"/>
			<mys:column title="名称" value="displayTitle" width="180px"/>
			<%-- 
			<mys:column title="是否可见" value="visible" width="80px"/>
			<mys:column title="不可变" value="immutable" width="80px"/>
			--%>
			<mys:column title="描述" value="description" />
		</mys:table>
		
		<mys:pagination value="page"/>
	</body>
</html>