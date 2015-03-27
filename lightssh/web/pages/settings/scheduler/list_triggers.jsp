<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
	
		<title>定时任务</title>
		
		<script type="text/javascript">
			function toggle(name,group,pause){
				var url = '<s:url value="/settings/scheduler/toggle.do"/>';
				url = url + '?name='+name+'&group='+group;
				if( confirm('确认'+(pause?'暂停':'启动')+'定时任务?') )
					location.href=url;
			}
		</script>
	</head>
	
	<body>
		<ul class="path">
			<li>基础管理</li>
			<li>计划任务</li>
			<li>时钟列表</li>
		</ul>
		
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<mys:table cssClass="list" value="page" status="loop">
			<mys:column title="序号"  width="50px">
				<s:property value="#loop.index+1"/>
			</mys:column>
			
			<%-- 
			<mys:column title="名称" value="name" width="300px"/>
			--%>
			<mys:column title="组名" value="group" width="100px"/>
			<mys:column title="CRON表达式" value="cronExpression" width="180px"/>
			<mys:column title="状态" width="20px">
				<s:property value="pause?'暂停':'运行'"/>
			</mys:column>
			<mys:column title="上/下次运行时间" width="190px">
				<font color="#CCCCCD">
				[上次] <s:property value="@com.google.code.lightssh.common.util.TextFormater@format(previousFireTime,'yyyy-MM-dd HH:mm:ss')"/>
				</font>
				<br/>
				[下次] <s:property value="@com.google.code.lightssh.common.util.TextFormater@format(nextFireTime,'yyyy-MM-dd HH:mm:ss')"/>
			</mys:column>
			<mys:column title="描述">
				<font color="#CCCCCD"><s:property value="name"/></font><br/>
				<s:property value="description"/>
			</mys:column>
			<mys:column title="操作" width="40px" cssClass="action">
				<span>&nbsp;</span>
				<div class="popup-menu-layer">
					<ul class="dropdown-menu">
						<li class="<s:property value="pause?'unfreeze':'freeze'"/>">
							<a href="#" onclick="toggle('<s:property value="name"/>','<s:property value="group"/>',<s:property value="pause?'false':'true'"/>)">
								<s:property value="pause?'启用时钟':'停用时钟'"/>
							</a>
						</li>
						<s:if test="pause">
							<li class="disabled"><a href="#">刷新时钟</a></li>
						</s:if>
						<s:else>
							<li class="refresh"><a href="<s:url value="refresh.do?name=%{name}&group=%{group}"/>">刷新时钟</a></li>
						</s:else>
						
						<li class="section"/>
						<li class="edit"><a href="<s:url value="edit.do?name=%{name}&group=%{group}"/>">更改时钟</a></li>
					</ul>
				</div>
			</mys:column>
		</mys:table>
			
	</body>
</html>