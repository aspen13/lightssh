<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<head>
	<meta name="decorator" content="background"/>
	
	<script src="<%= request.getContextPath() %>/scripts/jquery/plugins/cluetip/jquery.cluetip.all.js"></script>		
	<link href="<%=request.getContextPath() %>/scripts/jquery/plugins/cluetip/jquery.cluetip.css"	rel="stylesheet" type="text/css" />
	
	<title>系统参数列表</title>
	
	<script type="text/javascript">
		$(document).ready(function(){
			//$('a.title').cluetip({splitTitle: '|'});
		});
	</script>
	
</head>
	
<body>
	<ul class="path">
		<li>基础数据</li>
		<li>系统参数</li>
		<li>参数列表</li>
	</ul>
	
	<%-- 
	<input type="button" class="action new" value="新增系统参数" onclick="location.href='<s:url value="edit.do"/>'"/>
	--%>
	
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<s:form name="list" namespace="/settings/tree" method="post">
		<table class="profile">
			<colgroup>
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col width="23%">
				<col width="10%">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th><label for="group">组名</label></th>
					<td>
						<s:textfield name="param.group" id="group"/>
					</td>
					
					<th><label for="name">名称</label></th>
					<td>
						<s:textfield name="param.name" id="name"/>
					</td>
					<th><label for="value">参数值</label></th>
					<td colspan="5">
						<s:textfield name="param.value" id="value" size="30"/>
					</td>
				</tr>
			</tbody>
		</table>
		<input type="submit" class="action search right" value="查询"/>
	</s:form>
	
	<mys:table cssClass="list" value="page" status="loop">
		<mys:column title="序号"  width="50px"><s:property value="#loop.index+1"/></mys:column>
		<mys:column title="子系统" sortable="true" value="system" width="80px"/>
		<mys:column title="组名" value="group" sortable="true" width="100px"/>
		<mys:column title="名称" sortKey="name" sortable="true" width="150px">
			<s:property value="@com.google.code.lightssh.common.util.TextFormater@format(name,20,true)"/>
		</mys:column>
		<mys:column title="参数值" width="260px">
			<s:set name="value_length" value="15" /> 
			<s:if test="value.length() <= #value_length ">
				<s:property value="%{value}"/>
			</s:if>
			<s:else>
				<a class="title" href="#" title="参数值|<s:property value="%{value}"/>">
					<s:property value="@com.google.code.lightssh.common.util.TextFormater@format(value,#value_length,true)"/>
				</a>
			</s:else>
		</mys:column>
		<mys:column title="有效" width="50px">
			<s:property value="%{enabled?'是':'否'}"/>
		</mys:column>
		<mys:column title="只读" width="50px">
			<s:property value="%{readonly?'是':'否'}"/>
		</mys:column>
		<%-- 
		<mys:column title="最后更新时间" sortable="true" sortKey="lastUpdateTime" width="160px">
			<s:property value="@com.google.code.lightssh.common.util.TextFormater@format(lastUpdateTime,'yyyy-MM-dd HH:mm:ss')"/>
		</mys:column>
		--%>
		<mys:column title="描述" value="description" />
		<mys:column title="操作" width="40px" cssClass="action">
				<span>&nbsp;</span>
				<div class="popup-menu-layer">
					<ul class="dropdown-menu">
						<li class="view">
							<a href="<s:url value="view.do?param.id=%{id}"/>">查看参数</a></a>
						</li>
						<li class="section"/>
						
						<s:if test="readonly">
							<li class="disabled"><a href="#">编辑参数</a></li>
						</s:if>
						<s:else>
							<li class="edit">
								<a href="<s:url value="edit.do?param.id=%{id}"/>">编辑参数</a></a>
							</li>
						</s:else>
					</ul>
				</div>
			</mys:column>
	</mys:table>

	<mys:pagination value="%{page}"/>	
		
</body>
