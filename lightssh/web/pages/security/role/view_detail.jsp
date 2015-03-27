<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<table class="profile">
	<caption>角色信息</caption>
	<tbody>
		<tr>
			<th><label for="id">编号</label></th>
			<td>
				<s:property value="%{role.id}"/>
			</td>
		</tr>
		
		<tr>
			<th><label for="name">名称</label></th>
			<td>
				<s:property value="%{role.name}"/>
			</td>
		</tr>
		
		<tr>
			<th><label for="status">状态</label></th>
			<td>
				<s:property value="%{role.status}"/>
			</td>
		</tr>
		
		<tr>
			<th><label for="desc">描述</label></th>
			<td><s:property value="%{role.description}"/></td>
		</tr>
		
		<tr>
			<th><label for="permission">权限集</label></th>
			<td>
			
				<ul id="navigation_tree">
				<li>
					<s:set name="roles_perm_set" value="role.permissions.{token}"/>
					<s:set name="inRole" value="%{(#roles_perm_set.size>0)?true:false}"/>
					<span class="<s:property value="%{#inRole?\"has\":\"none\"}"/>">系统权限</span>
					<ul>
					<s:set name="sortedList" value="@com.google.code.lightssh.project.security.entity.Navigation@sort(navigation)"/>
					<s:iterator value="#sortedList" status="loop" >
						<li>
							<s:set name="inRole" value="%{(permission.token in #roles_perm_set)?true:false}"/>
							<span class="<s:property value="%{#inRole?\"has\":\"none\"}"/>"><s:property value="%{name}"/></span>
							<s:iterator value="@com.google.code.lightssh.project.security.entity.Navigation@sort(children)" status="loop2">
								<s:if test="#loop2.first"><ul></s:if>
								<li>
									<s:set name="inRole" value="%{(permission.token in #roles_perm_set)?true:false}"/>
									<span class="<s:property value="%{#inRole?\"has\":\"none\"}"/>"><s:property value="%{name}"/></span>
									<s:iterator value="@com.google.code.lightssh.project.security.entity.Navigation@sort(children)" status="loop3">
										<s:if test="#loop3.first"><ul></s:if>
										<li>
											<s:set name="inRole" value="%{(permission.token in #roles_perm_set)?true:false}"/>
											<span class="<s:property value="%{#inRole?\"has\":\"none\"}"/>"><s:property value="%{name}"/></span>
										
											<s:iterator value="@com.google.code.lightssh.project.security.entity.Navigation@sort(children)" status="loop4">
												<s:if test="#loop4.first"><ul></s:if>
												<li>
													<s:set name="inRole" value="%{(permission.token in #roles_perm_set)?true:false}"/>
													<span class="<s:property value="%{#inRole?\"has\":\"none\"}"/>"><s:property value="%{name}"/></span>
												</li>
												<s:if test="#loop4.last"></ul></s:if>
											</s:iterator>
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
			<th><label for="result">创建时间</label></th>
			<td><s:property value="%{role.createdTime}"/></td>
		</tr>
	</tbody>
</table>