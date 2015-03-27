<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<head>
	<meta name="decorator" content="background"/>

	<title>地理区域列表</title>
	
</head>
	
<body>
	<ul class="path">
		<li>基础管理</li>
		<li>地理区域</li>
		<li>搜索列表</li>
	</ul>
	
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<s:form name="list" namespace="/settings/uom" method="post">
		<table class="profile">
			<tbody>
				<tr>
					<th><label for="type">类型</label></th>
					<td>
						<s:select id="type" list="@com.google.code.lightssh.project.geo.entity.GeoType@frequentlyUsed()"
						 listKey="name()" headerKey="" headerValue="" value="geo.type.name()" name="geo.type"/>
					</td>
					
					<th><label for="name">名称</label></th>
					<td>
						<s:textfield id="name" name="geo.name" />
					</td>
					
					<th><label for="active">状态</label></th>
					<td>
						<s:select headerKey="" headerValue="" list="#{true:'活动的',false:'已冻结'}" 
							name="geo.active" value="geo.getActive()" id="active"/>
					</td>
					
					<td colspan="2"><input type="submit" class="action search right" value="查询"/></td>
				</tr>
			</tbody>
		</table>
	</s:form>

	<%-- 
	<mys:pagination value="page"/>
	--%>
	
	<mys:table cssClass="list" value="page" status="loop">
		<mys:column title="序号" width="50px">
			<s:property value="#loop.index + 1"/>
		</mys:column>
		<mys:column title="类型" value="type" sortable="true" sortKey="type" width="80px"/>
		<mys:column title="系统编号" value="code" sortable="true" width="120px"/>
		<mys:column title="缩写" value="abbreviation" sortable="false" width="80px"/>
		<mys:column title="名称" value="name" sortable="true" width="180px"/>
		<mys:column title="数字编码" value="numericCode" sortable="true" width="100px"/>
		<mys:column title="状态" width="50px" sortable="true" sortKey="active">
			<s:property value="%{active?'活动的':'已冻结'}"/>
		</mys:column>
		<mys:column title="描述" value="description"/>
		<mys:column title="操作" width="40px" cssClass="action">
			<span>&nbsp;</span>
			<div class="popup-menu-layer">
				<ul class="dropdown-menu">
					<li class="<s:property value="!active?'unfreeze':'freeze'"/>">
						<a href="<s:url value="/settings/geo/toggle.do?geo.code=%{code}"/>">
							<s:property value="%{active?'冻结':'激活'}"/>
						</a>
					</li>
					<li class="section"/>
					<li class="disabled"><a href="#">编辑数据</a></li>
					<li class="disabled"><a href="#">删除数据</a></li>
				</ul>
			</div>
		</mys:column>
	</mys:table>
	
	<mys:pagination value="page" pageSizeArray="15,50,100,500"/>
	
</body>
