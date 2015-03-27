<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<head>
	<meta name="decorator" content="background"/>
	<script type="text/javascript">
		function doRemove( id,name ){
			var url = '<s:url value="/settings/organization/removecontact.do?partyContact.id="/>' + id ;
			if( confirm('确认删除该联系方式'))
				location.href=url;
		}
	</script>
</head>

<body>
	<ul class="path">
		<li>基础管理</li>
		<li>企业资料</li>
		<li>查看信息</li>
	</ul>
	
	<input type="button" class="action enterprise" value="设置企业信息" 
		onclick="location.href='<s:url value="viewparent.do?action=edit"/>'"/>
		
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<s:set name="org" value="party"/>
	
	<table class="profile">
		<caption>企业信息</caption>
		<tbody>
			<tr>
				<th>编号</th>
				<td><s:property value="#org.id"/></td>
			</tr>
			<tr>
				<th>名称</th>
				<td><s:property value="#org.name"/></td>
			</tr>
			<tr>
				<th>类型</th>
				<td>
					<s:property value="party_role_type"/>
				</td>
			</tr>
			<tr>
				<th>描述</th>
				<td><s:property value="#org.description"/></td>
			</tr>
		</tbody>
	</table>
	
	<mys:table value="#request.party_contacts" cssClass="list" status="loop">
		<mys:column title="序号" width="50px">
			<s:property value="#loop.index+1"/>
		</mys:column>
		<mys:column title="类型" value="contact.type" width="80px" sortable="false"/>
		<mys:column title="联系方式" width="400px">
			<s:property value="contact.format()"/>
		</mys:column>
		<mys:column title="描述" value="contact.description"/>
		<mys:column title="操作" width="100px">
			<a href="#" onclick="doRemove('<s:property value="%{id}"/>');">删除</a>
		</mys:column>
	</mys:table>
	
</body>