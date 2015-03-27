<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<head>
	<meta name="decorator" content="background"/>
	<title>对象比较</title>
</head>

<body>
	<ul class="path">
		<li>基础管理</li>
		<li>系统日志</li>
		<li>变更记录</li>
	</ul>
	
		<table class="list">
			<colgroup>
				<col class="element" width="50px"/>
				<col class="element" width="50px"/>
				<col class="element" width="50px"/>
				<col class="element" width="300px"/>
				<col class="element" width="300px"/>
				<col class="element"/>
			</colgroup>
			<thead>
				<tr>
					<th>序号</th>
					<th>属性</th>
					<th>变动</th>
					<th>原值</th>
					<th>新值</th>
					<th>描述</th>
				</tr>
			</thead>
			<s:iterator value="#request.history.toFileds()" status="loop">
			<tr class="<s:property value="#loop.odd?\"odd\":\"even\""/>">
				<td><s:property value="#loop.index+1"/></td>
				<td><s:property value="%{name}"/></td>
				<td><s:property value="%{(#request.history.created||#request.history.deleted||change)?'Y':''}"/></td>
				<td><s:property value="%{originalValue}"/></td>
				<td><s:property value="%{newValue}"/></td>
				<td><s:property value="%{description}"/></td>
			</tr>
			</s:iterator>
		</table>
		
</body>