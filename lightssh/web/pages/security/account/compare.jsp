<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<s:set name="originalObject" value="#request.originalObject"/>
<s:set name="newObject" value="#request.newObject"/>

<table class="profile">
	<caption>登录账号信息</caption>
	<tbody>
		<tr>
			<th><label>登录帐号</label></th>
			<td>
				<span class="compare 
					<s:property value="@com.google.code.lightssh.project.util.CompareUtil@style(#originalObject,#newObject,'loginName')"/>" 
					title="<s:property value="%{#originalObject.loginName}"/>">
				</span>
				<s:property value="%{#newObject.loginName}"/>
			</td>
		</tr>
		<tr>
			<th><label>部门</label></th>
			<td>
				<span class="compare 
					<s:property value="@com.google.code.lightssh.project.util.CompareUtil@style(#originalObject,#newObject,'partyId')"/>" 
					title="<s:property value="%{#originalObject.partyId}"/>">
				</span>
				<s:property value="%{#newObject.partyId}"/>
			</td>
		</tr>
		<tr>
			<th><label>有效期</label></th>
			<td>
				<span class="compare 
					<s:property value="@com.google.code.lightssh.project.util.CompareUtil@style(#originalObject,#newObject,'period')"/>" 
					title="<s:property value="%{#originalObject.period.start}"/> - <s:property value="%{#originalObject.period.end}"/>">
				</span>
				<s:property value="%{#newObject.period.start}"/>
				<s:property value="(#newObject!=null && #newObject.period.start != null && #newObject.period.end != null)?'-':''"/>
				<s:property value="%{#newObject.period.end}"/>
			</td>
		</tr>
		<tr>
			<th><label>电子邮箱</label></th>
			<td>
				<span class="compare 
					<s:property value="@com.google.code.lightssh.project.util.CompareUtil@style(#originalObject,#newObject,'email')"/>" 
					title="<s:property value="%{#originalObject.email}"/>">
				</span>
				<s:property value="%{#newObject.email}"/>
			</td>
		</tr>
		<tr>
			<th><label>角色集</label></th>
			<td>
				<span class="compare 
					<s:property value="@com.google.code.lightssh.project.util.CompareUtil@style(#originalObject,#newObject,'roles')"/>" 
					title="<s:iterator value="%{#originalObject.roles}" status="loop"><s:property value="#loop.first?'':' , '"/><s:property value="name"/></s:iterator>">
				</span>
				<s:iterator value="%{#newObject.roles}" status="loop">
					<s:property value="#loop.first?'':' , '"/>
					<s:property value="name"/>
				</s:iterator>
			</td>
		</tr>
		<tr>
			<th><label>描述</label></th>
			<td>
				<span class="compare 
					<s:property value="@com.google.code.lightssh.project.util.CompareUtil@style(#originalObject,#newObject,'description')"/>" 
					title="<s:property value="%{#originalObject.description}"/>">
				</span>
				<s:property value="%{#newObject.description}"/>
			</td>
		</tr>
		
		<%-- 
		<tr>
			<th><label for="result">创建时间</label></th>
			<td><s:property value="%{account.createdTime}"/></td>
		</tr>
		--%>
	</tbody>
</table>