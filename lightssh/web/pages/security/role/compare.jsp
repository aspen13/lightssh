<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<s:set name="originalRole" value="#request.originalRole"/>
<s:set name="newRole" value="#request.newRole"/>

<table class="profile">
	<caption>角色信息</caption>
	<tbody>
		<tr>
			<th><label>角色名称</label></th>
			<td>
				<span class="compare 
					<s:property value="@com.google.code.lightssh.project.util.CompareUtil@style(#originalRole,#newRole,'name')"/>" 
					title="<s:property value="%{#originalRole.name}"/>">
				</span>
				<s:property value="%{#newRole.name}"/>
			</td>
		</tr>
		
		<tr>
			<th><label>角色描述</label></th>
			<td>
				<span class="compare 
					<s:property value="@com.google.code.lightssh.project.util.CompareUtil@style(#originalRole,#newRole,'description')"/>" 
					title="<s:property value="%{#originalRole.description}"/>">
				</span>
				<s:property value="%{#newRole.description}"/>
			</td>
		</tr>
		<tr>
			<th><label for="permission">权限集</label></th>
			<td>
				<ul id="navigation_tree">
				<li>
					<s:set name="roles_perm_set" value="role.permissions.{token}"/>
					<s:set name="original_role_perms" value="#originalRole.permissions.{token}"/>
					<s:set name="new_role_perms" value="#newRole.permissions.{token}"/>
					
					<s:set name="inOriginalRole" value="%{#original_role_perms.size > 0}"/>
					<s:set name="inNewRole" value="%{#new_role_perms.size > 0}"/>
					<s:set name="class" value="((#inOriginalRole && #inNewRole)?'has':((!#inOriginalRole && !#inNewRole)?'none':((#inOriginalRole && !#inNewRole)?'miss':'new')))"/>
					
					<span class="<s:property value="%{#class}"/>">系统权限</span>
					<ul>
					<s:set name="sortedList" value="@com.google.code.lightssh.project.security.entity.Navigation@sort(navigation)"/>
					<s:iterator value="#sortedList" status="loop" >
						<li>
							<s:set name="inOriginalRole" value="%{(permission.token in #original_role_perms)?true:false}"/>
							<s:set name="inNewRole" value="%{(permission.token in #new_role_perms)?true:false}"/>
							<s:set name="class" value="((#inOriginalRole && #inNewRole)?'has':((!#inOriginalRole && !#inNewRole)?'none':((#inOriginalRole && !#inNewRole)?'miss':'new')))"/>
							
							<span class="<s:property value="%{#class}"/>"><s:property value="%{name}"/></span>
							<s:iterator value="@com.google.code.lightssh.project.security.entity.Navigation@sort(children)" status="loop2">
								<s:if test="#loop2.first"><ul></s:if>
								<li>
									<s:set name="inOriginalRole" value="%{(permission.token in #original_role_perms)?true:false}"/>
									<s:set name="inNewRole" value="%{(permission.token in #new_role_perms)?true:false}"/>
									<s:set name="class" value="((#inOriginalRole && #inNewRole)?'has':((!#inOriginalRole && !#inNewRole)?'none':((#inOriginalRole && !#inNewRole)?'miss':'new')))"/>
							
									<span class="<s:property value="%{#class}"/>"><s:property value="%{name}"/></span>
									<s:iterator value="@com.google.code.lightssh.project.security.entity.Navigation@sort(children)" status="loop3">
										<s:if test="#loop3.first"><ul></s:if>
										<li>
											<s:set name="inOriginalRole" value="%{(permission.token in #original_role_perms)?true:false}"/>
											<s:set name="inNewRole" value="%{(permission.token in #new_role_perms)?true:false}"/>
											<s:set name="class" value="((#inOriginalRole && #inNewRole)?'has':((!#inOriginalRole && !#inNewRole)?'none':((#inOriginalRole && !#inNewRole)?'miss':'new')))"/>
							
											<span class="<s:property value="%{#class}"/>"><s:property value="%{name}"/></span>
										</li>
										<s:if test="#loop3.last"></ul></s:if>
									</s:iterator>
								</li>
								<s:if test="#loop2.last"></ul></s:if>
							</s:iterator>
						</li>
					</s:iterator>
					</ul>
				
				</ul>
				
			</td>
		</tr>
		
		<tr>
			<th><label for="result">角色创建时间</label></th>
			<td><s:property value="%{role.createdTime}"/></td>
		</tr>
	</tbody>
</table>