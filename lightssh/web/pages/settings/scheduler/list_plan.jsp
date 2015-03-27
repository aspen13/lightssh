<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
		
		<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/ui/i18n/jquery.ui.datepicker_<s:property value="locale"/>.js"></script>
	
		<title>执行计划</title>
		
		<script type="text/javascript">
			$(document).ready(function(){
				$("#pft_start").datepicker( );
				$("#pft_end").datepicker( );
			});
		</script>
	</head>
	
	<body>
		<ul class="path">
			<li>基础管理</li>
			<li>计划任务</li>
			<li>执行计划</li>
		</ul>
		
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<s:form action="list" method="post">
			<table class="profile">
				<tr>
					<th><label for="id">任务编号</label></th>
					<td><s:textfield id="id" name="plan.id" /></td>
					<th><label for="pft_start">计划执行时间</label></th>
					<td>
						<s:textfield id="pft_start" name="plan._pftPeriod.start" size="10" />
						<s:textfield id="pft_end" name="plan._pftPeriod.end" size="10" />
					</td>
					<td>
						<s:submit cssClass="action search right" value="查询"/>
						
						<input type="button" class="action refresh right" value="刷新页面" onclick="window.location.reload()"/>
					</td>
				</tr>
			</table>
		</s:form>
				
		<mys:table cssClass="list" value="page" status="loop">
			<mys:column title="序号"  width="50px">
				<s:property value="#loop.index+1"/>
			</mys:column>
			
			<mys:column title="任务编号" value="identity" sortable="true" sortKey="id" width="120px"/>
			<mys:column title="工作日" value="workdate" sortable="false" width="50px"/>
			<mys:column title="计划执行时间" value="planFireTime" sortable="true" width="150px"/>
			<mys:column title="实际执行时间" value="fireTime" sortable="false" width="150px"/>
			<mys:column title="执行完成时间" value="finishTime" sortable="false" width="150px"/>
			<mys:column title="状态" width="50px">
				<font color="<s:property value="%{finished?'green':'red'}"/>">
					<s:property value="finished?'完成':'未完成'"/>
				</font>
			</mys:column>
			<%-- 
			--%>
			<mys:column title="创建时间" value="createdTime" width="150px"/>
			<mys:column title="描述" value="description"/>
			<mys:column title="操作" width="40px" cssClass="action">
				<span>&nbsp;</span>
				<div class="popup-menu-layer">
					<ul class="dropdown-menu">
						<li class="list">
							<a href="<s:url value="listdetail.do?plan.id=%{id}"/>">执行计划明细</a>
						</li>
						<li class="section"/>
					</ul>
				</div>
			</mys:column>
		</mys:table>
			
		<mys:pagination value="page"/>	
	</body>
</html>