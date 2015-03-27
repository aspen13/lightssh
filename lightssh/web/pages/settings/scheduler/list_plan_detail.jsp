<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
	
		<title>执行计划明细</title>
		
		<script type="text/javascript">
		</script>
	</head>
	
	<body>
		<ul class="path">
			<li>基础管理</li>
			<li>计划任务</li>
			<li>执行计划明细</li>
		</ul>
		
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<input type="button" class="action refresh" value="刷新页面" onclick="window.location.reload()"/>
		
		<mys:table cssClass="list" value="#request.list" status="loop">
			<mys:column title="序号"  width="30px">
				<s:property value="#loop.index+1"/>
			</mys:column>
			
			<mys:column title="类型" value="type.name"/>
			<mys:column title="任务编号" value="identity" width="150px"/>
			<mys:column title="组名" value="group" width="50px"/>
			<mys:column title="前置任务" value="precondition.id" width="150px"/>
			<mys:column title="实际执行时间" value="fireTime" width="150px"/>
			<mys:column title="执行完成时间" value="finishTime" width="150px"/>
			<mys:column title="执行方式" width="50px">
				<s:property value="synTask?'异步':'同步'"/>
			</mys:column>
			<mys:column title="状态" width="60px">
				<font color="<s:property value="%{warning?'red':(finished?'green':'')}"/>">
				<s:property value="status"/>
				</font>
			</mys:column>
			<%-- 
			<mys:column title="异常信息">
				<s:property value="@com.google.code.lightssh.common.util.TextFormater@format(errMsg,100,true)"/>
			</mys:column>
			<mys:column title="失败次数" value="failureCount" width="60px"/>
			<mys:column title="创建时间" value="createdTime" width="150px"/>
			<mys:column title="描述" value="description"/>
			--%>
			<mys:column title="操作" width="40px" cssClass="action">
				<span>&nbsp;</span>
				<div class="popup-menu-layer">
					<ul class="dropdown-menu">
						<li class="view">
							<a href="<s:url value="viewdetail.do?plan.id=%{plan.id}&planDetail.id=%{id}"/>">信息详细</a>
						</li>
						<s:if test="!finished && preconditionFinished">
							<li class="section"/>
							<li class="redo">
								<a href="<s:url value="detailinqueue.do?plan.id=%{plan.id}&planDetail.id=%{id}"/>">重新执行</a>
							</li>
						</s:if>
						<s:elseif test="!finished">
							<li class="section"/>
							<li class="disabled">
								<a href="#">重新执行</a>
							</li>
						</s:elseif>
						
						<%-- 
						<li class="disabled">
							<a href="#">跳过执行下一步</a>
						</li>
						--%>
					</ul>
				</div>
			</mys:column>
		</mys:table>
			
	</body>
</html>