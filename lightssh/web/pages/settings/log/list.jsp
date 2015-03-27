<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/ui/i18n/jquery.ui.datepicker_zh_CN.js"></script>
	
		<title>系统日志</title>
		
		<script type="text/javascript">
			$(document).ready(function(){
				$("#access_start_date").datepicker({dateFormat: 'yy-mm-dd',changeYear:true});
				$("#access_end_date").datepicker({dateFormat: 'yy-mm-dd',changeYear:true});
			});
			
		</script>
		
	</head>
	
	<body>
		<ul class="path">
			<li>基础管理</li>
			<li>系统日志</li>
			<li>日志列表</li>
		</ul>
		
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<s:form name="list" namespace="/settings/log" method="post">
			<table class="profile">
				<tbody>
					<tr>
						<th><label for="type">类型</label></th>
						<td>
							<s:select id="type" list="@com.google.code.lightssh.project.log.entity.AccessType@values()"
							 listKey="name()" headerKey="" headerValue="" value="access.type.name()" name="access.type"/>
						</td>
						
						<th><label for="access_start_date">时间</label></th>
						<td>
							<s:textfield name="access._period.start" id="access_start_date" size="10" /> -
							<s:textfield name="access._period.end" id="access_end_date" size="10"/>
						</td>
						
						<th><label for="ip">IP</label></th>
						<td><s:textfield id="ip" name="access.ip"/></td>
						
						<td colspan="2"><input type="submit" class="action search right" value="查询"/></td>
					</tr>
				</tbody>
			</table>
		</s:form>
		
		<mys:table cssClass="list" value="page" status="loop">
			<mys:column title="序号" width="50px">
				<s:property value="#loop.index + 1"/>
			</mys:column>
			<mys:column title="类型" value="type" sortable="true" width="80px"/>
			<mys:column title="时间" value="@com.google.code.lightssh.common.util.TextFormater@format(time,'yyyy-MM-dd HH:mm:ss')" sortable="true" sortKey="time" width="150px"/>
			<mys:column title="IP" value="ip" sortable="true" width="100px"/>
			<mys:column title="操作者" value="operator" sortable="true" width="100px"/>
			<mys:column title="描述" value="description"/>
			<mys:column title="操作" width="40px">
				<s:if test="showHistory">
					<a href="<s:url value="/settings/log/compare.do?access.id=%{id}"/>">比较</a>
				</s:if>
			</mys:column>
		</mys:table>
	
		<mys:pagination value="page" />
		
	</body>
</html>