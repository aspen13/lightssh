<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<table class="profile">
	<tbody>
		<input type="hidden" name="party" value="person"/>
		<s:hidden name="party.id"/>
	
		<tr>
			<th><label for="relationship" class="required">成员关系</label></th>
			<td>
				<s:select list="@com.google.code.lightssh.project.party.entity.PartyRole$RoleType@familyMember()" 
						name="party.roles[0]" listKey="name()" headerKey="" headerValue=""
						value="party.roles[0].name()" id="relationship"/>
			</td>
		</tr>
		
		<tr>
			<th><label for="name" class="required">姓名</label></th>
			<td>
				<s:textfield id="name" name="party.name" size="20"/>
			</td>
		</tr>
		
		<tr>
			<th><label for="gendar">性别</label></th>
			<td>
				<s:select name="party.gender" value="%{party.gender.name()}" listKey="name()" 
					list="@com.google.code.lightssh.project.party.entity.Person$Gender@values()"
					headerKey="" headerValue="" 
					/>
			</td>
		</tr>
		
		<tr>
			<th><label for="birthday">生日</label></th>
			<td>
				<s:textfield name="party.birthday" id="person_birthday" size="10"/>
			</td>
		</tr>
		
		<tr>
			<th><label for="desc">描述</label></th>
			<td><s:textarea id="desc" name="party.description" cols="60" rows="5"/></td>
		</tr>
	</tbody>
</table>