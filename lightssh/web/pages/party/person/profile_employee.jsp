<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<s:form id="profile_form" action="saveemployee" namespace="/party/person" method="post">
	<table class="profile">
		<colgroup>
			<col width="15%"/>
			<col width="30%"/>
			<col width="15%"/>
			<col />
		</colgroup>
		<tbody>
			<input type="hidden" name="party" value="person"/>
			<s:hidden name="party.id"/>
			
			<tr>
				<th><label for="code">员工号码</label></th>
				<td>
					<s:hidden name="employee.id"/>
					<s:hidden name="employee.person.id" value="%{(employee==null||employee.code==null)?party.id:employee.person.id}"/>
					<s:set name="code" value="%{(employee==null||employee.code==null)?party.id:employee.code}" />
					<s:hidden id="code" name="employee.code" value="#code"/>
					<s:property value="#code"/>
				</td>
				
				<th><label for="organization">所属部门</label></th>
				<td>
					<span class="popup party" onclick="popupParty('<s:url value="/party/popup.do"/>',{party_type:'org'});">&nbsp;</span>
					<s:hidden name="employee.organization.id" id="party_id"/>
					<s:hidden name="employee.organization.name" id="party_name"/>
					<%--<s:hidden name="account.party" value="%{account.party.id.startsWith('ORG')?'organization':'person'}" id="party_clazz"/>--%>
					<span id="span_party_name"><s:property value="%{employee.organization.name}"/></span>
				</td>
			</tr>
			
			<tr>
				<th><label for="type">人事性质</label></th>
				<td>
					<s:select id="type" name="employee.type" value="%{employee.type.name()}" listKey="name()" 
						list="@com.google.code.lightssh.project.party.entity.Employee$Type@values()"></s:select>
				</td>
				
				<th><label for="status">人事状态</label></th>
				<td>
					<s:select id="status" name="employee.status" value="%{employee.status.name()}" listKey="name()" 
						list="@com.google.code.lightssh.project.party.entity.Employee$Status@values()"></s:select>
				</td>
			</tr>
			
			<tr>
				<th><label for="position">职位</label></th>
				<td>
					<s:textfield id="position" name="employee.position" size="30"/>
				</td>
				<th><label for="workplace">工作地</label></th>
				<td>
					<s:textfield id="workplace" name="employee.workplace" size="40"/>
				</td>
			</tr>
		</tbody>
	</table>
	
	<p class="submit">
		<input type="submit" class="action save" name="Submit" 
			value="<s:property value="%{(employee==null||employee.insert)?\"新增人事信息\":\"修改人事信息\"}"/>"/>
	</p>
</s:form>