<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<html>
	<head>
		<meta name="decorator" content="background"/>
		
		<script type="text/javascript" src="<s:url value="/pages/party/popup.js" />"></script>
		
		<title>人员列表</title>
		
		<script type="text/javascript">
			function doRemove( id,name ){
				var url = '<s:url value="/party/person/remove.do"/>?party=person&party.id=' + id ;
				if( confirm('确认删除人员[' + name + ']'))
					location.href=url;
			}
		</script>
	</head>
	
	<body>
		<ul class="path">
			<li>系统管理</li>
			<li>人员管理</li>
			<li>人员列表</li>
		</ul>
		
		<%@ include file="/pages/common/util/messages.jsp" %>
		
		<s:form name="list" method="post">
			<table class="profile">
				<colgroup>
					<col width="10%"/>
					<col width="23%"/>
					<col width="10%"/>
					<col width="23%"/>
					<col width="10%"/>
					<col />
				</colgroup>
				<tbody>
					<tr>
						<th><label for="id">编号</label></th>
						<td>
							<s:hidden name="party" class="person"/>
							<s:textfield id="id" name="party.id" maxlength="100"/>
						</td>
						<th><label for="name">名称</label></th>
						<td>
							<s:textfield id="name" name="party.name" size="30" maxlength="100"/>
						</td>
						<th><label for="org">部门</label></th>
						<td>
							<span class="popup party" onclick="popupParty('<s:url value="/party/popup.do"/>',{party_type:'org'});">&nbsp;</span>
							<s:hidden name="party.employee.organization.id" id="party_id"/>
							<s:hidden name="party.employee.organization.name" id="party_name"/>
							<span id="span_party_name"><s:property value="%{party.employee.organization.name}"/></span>
						</td>
					</tr>
					<tr>
						<th><label for="credentialsType">证件类型</label></th>
						<td>
							<s:select list="@com.google.code.lightssh.project.party.entity.CredentialsType@frequentlyUsed()" 
								name="party.credentialsType" listKey="name()" headerKey="" headerValue=""
								value="party.credentialsType.name()" onchange="autofill(this)" id="credentialsType"/>
						</td>
						<th><label for="identityCardNumber">证件号码</label></th>
						<td>
							<s:textfield id="identityCardNumber" name="party.identityCardNumber" size="30" maxlength="100"/>
						</td>
						<td colspan="2"><input type="submit" class="action search" value="查询"/></td>
					</tr>
				</tbody>
			</table>
		</s:form>
		
		<%-- dynamicCols="new java.lang.String[]{'enabled-','partyAffiliation','maritalStatus'}" --%>
		<%-- dynamicCols="#{'enabled':'1','partyAffiliation':'2','maritalStatus':'3','credentialsType-':'5'}" --%>
		<mys:table id="party_person_list" cssClass="list" value="page" dynamic="true" status="loop"
			dynamicCols="@com.google.code.lightssh.project.column.util.CustomizeColumnHelper@getDynamicCols('','')">
			<mys:column title="序号" width="40px" value="#loop.index + 1" sequence="first" >
				<s:set name="employee" value="@com.google.code.lightssh.project.party.util.PartyHelper@getEmployee(id)"/>
			</mys:column>
			<mys:column title="编号" value="identity" sortKey="id" sortable="true" width="60px"/>
			<mys:column title="姓名" value="name" sortable="true" width="100px"/>
			<mys:column title="性别" value="gender" sortable="true" width="20px"/>
			<mys:column title="婚况" value="maritalStatus" dynamic="true" sortable="true" width="20px"/>
			<mys:column title="国籍" value="country.name" sortable="false" width="40px"/>
			<mys:column title="民族" value="ethnicGroup" sortable="false" width="80px"/>
			<mys:column title="政治面貌" value="partyAffiliation" dynamic="true" sortable="false" width="50px"/>
			<mys:column title="证件类型" value="credentialsType" dynamic="true" sortable="true" width="100px"/>
			<mys:column title="证件号码" value="identityCardNumber" sortable="true" width="130px"/>
			<mys:column title="部门" value="#employee.organization.name"/>
			<mys:column title="人事性质" width="50px" value="#employee.type"/>
			<mys:column title="人事状态" width="50px" id="status" value="#employee.status"/>
			<mys:column title="有效" id="enabled" value="enabled?'是':'否'" 
				dynamic="true" sortable="true" sortKey="enabled" width="50px"/>
			<mys:column title="描述" value="description" />
			<mys:column title="操作" width="40px" cssClass="action" sequence="last">
				<span>&nbsp;</span>
				<div class="popup-menu-layer">
					<ul class="dropdown-menu">
						<li class="view">
							<a href="<s:url value="/party/person/view.do?party=person&party.id=%{id}"/>">查看人员</a>
						</li>
						
						<li class="section"/>
						
						<li class="edit">
							<a href="<s:url value="/party/person/edit.do?party=person&party.id=%{id}"/>">编辑信息</a>
						</li>
						
						<li class="remove">
							<a href="#" onclick="javascript:doRemove('<s:property value="%{id}"/>','<s:property value="%{name}"/>')">删除信息</a>
						</li>
					</ul>
				</div>
			</mys:column>
		</mys:table>
		
		<mys:pagination value="page"/>
	</body>
</html>