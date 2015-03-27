<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<s:form id="profile_form" action="save" namespace="/party/person" method="post">
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
			<%-- 
			<tr>
				<th><label for="lastName" class="required">姓</label></th>
				<td>
					<s:set name="isInsert" value="%{(person==null||person.id==null)}"/>
					
					<s:textfield id="lastName" name="person.lastName" size="20"/>
				</td>
			</tr>
			
			<tr>
				<th><label for="name" class="required">名字</label></th>
				<td>
					<s:textfield id="firstName" name="person.firstName" size="20"/>
				</td>
			</tr>
			--%>
			
			<tr>
				<th><label for="name" class="required">姓名</label></th>
				<td>
					<s:textfield id="name" name="party.name" size="20"/>
				</td>
				
				<th><label for="gendar">性别</label></th>
				<td>
					<s:select name="party.gender" value="%{party.gender.name()}" listKey="name()" 
						list="@com.google.code.lightssh.project.party.entity.Person$Gender@values()"></s:select>
				</td>
			</tr>
			
			<tr>
				<th><label for="person_birthday">生日</label></th>
				<td>
					<s:textfield name="party.birthday" id="person_birthday" size="12"/>
				</td>
				
				<th><label for="geo">出生地(籍贯)</label></th>
				<td>
					<select name="party.country.code">
						<option value="<s:property value="%{party.country.code}"/>">
							<s:property value="%{party.countryGeo.name}"/>
						</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<th><label for="ethnicGroup">民族</label></th>
				<td>
					<s:textfield id="ethnicGroup" name="party.ethnicGroup" />
				</td>
				
				<th><label for="partyAffiliation">政治面貌</label></th>
				<td>
					<s:textfield id="partyAffiliation" name="party.partyAffiliation"/>
				</td>
			</tr>
			
			<%-- 
			<tr>
				<th><label for="enabled">是否有效</label></th>
				<td>
					<s:select name="party.enabled" list="#{true:'是',false:'否'}"></s:select>
				</td>
			</tr>
			--%>
			
			<tr>
				<th><label for="credentialsType">证件类型</label></th>
				<td>
					<s:select list="@com.google.code.lightssh.project.party.entity.CredentialsType@frequentlyUsed()" 
						name="party.credentialsType" listKey="name()" headerKey="" headerValue=""
						value="party.credentialsType.name()" onchange="autofill(this)" id="credentialsType"/>
				</td>
			
				<th><label for="identityCardNumber">证件号码</label></th>
				<td>
					<s:textfield name="party.identityCardNumber" size="30" onchange="autofill(this)" id="identityCardNumber"/>
				</td>
			</tr>
			
			<tr>
				
			</tr>
			
			<tr>
				<th><label for="maritalstatus">婚姻状况</label></th>
				<td>
					<s:select list="@com.google.code.lightssh.project.party.entity.Person$MaritalStatus@values()" 
						name="party.maritalStatus" listKey="name()" headerKey="" headerValue=""
						value="party.maritalStatus.name()" id="maritalstatus"/>
				</td>
			 
				<th><label for="degree">最高学历</label></th>
				<td>
					<s:select list="@com.google.code.lightssh.project.party.entity.Person$EducationLevel@values()" 
						name="party.degree" listKey="name()" headerKey="" headerValue=""
						value="party.degree.name()" id="degree"/>
				</td>
			</tr>
			
			<tr>
				<th><label for="person_height">身高(CM)</label></th>
				<td>
					<s:textfield name="party.height" id="person_height" size="10"/>
				</td>
		
				<th><label for="person_weight">体重(KG)</label></th>
				<td>
					<s:textfield name="party.weight" id="person_weight" size="10"/>
				</td>
			</tr>
			
			<tr>
				<th><label for="bloodType">血型</label></th>
				<td>
					<s:select list="@com.google.code.lightssh.project.party.entity.Person$BloodType@values()" 
						name="party.bloodType" listKey="name()" headerKey="" headerValue=""
						value="party.bloodType.name()" id="bloodType"/>
				</td>
				
				<th><label for=""></label></th>
				<td>
				</td>
			</tr>
			
			<tr>
				<th><label for="desc">描述</label></th>
				<td colspan="3"><s:textarea id="desc" name="party.description" cols="80" rows="5"/></td>
			</tr>
		</tbody>
	</table>

	<s:token/>

	<p class="submit">
		<s:set name="isInsert" value="%{(party==null||party.id==null)}"/>
		<input type="submit" class="action save" name="Submit" 
			value="<s:property value="%{#isInsert?\"新增人员\":\"修改人员\"}"/>"/>
	</p>
</s:form>