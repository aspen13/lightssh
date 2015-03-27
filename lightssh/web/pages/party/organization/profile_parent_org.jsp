<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<head>
	<meta name="decorator" content="background"/>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$( "#tabs" ).tabs();
		});
	</script>
</head>

<body>
	<ul class="path">
		<li>基础管理</li>
		<li>企业资料</li>
		<li>编辑信息</li>
	</ul>
	
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<div id="tabs"> 
		<ul> 	
			<li><a href="#tabs-1">基本信息</a></li> 	
			<li><a href="#tabs-2">联系信息</a></li> 	
		</ul> 
	
		<div id="tabs-1">
			<s:set name="org" value="party"/>
	
			<s:form id="profile_form" action="initparent" namespace="/settings/organization" method="post">
				<table class="profile">
					<tbody>
						<tr>
							<th><label for="name" class="required">名称</label></th>
							<td>
								<s:set name="isInsert" value="%{(party==null||party.id==null)}"/>
								<input type="hidden" name="party" value="organization"/>
								<s:hidden name="party.id"/>
								<s:hidden name="party.enabled" value="%{#isInsert?true:party.enabled}"/>
								
								<s:textfield id="name" name="party.name" size="40" maxlength="300"/>
							</td>
						</tr>
						<tr>
							<th><label for="role_type">类型</label></th>
							<td>
								<s:select id="role_type" list="@com.google.code.lightssh.project.party.entity.PartyRole$RoleType@internalOrg()"
									listKey="name()" name="party_role_type" value="party_role_type.name()" headerKey="" headerValue=""/>
							</td>
						</tr>
						<tr>
							<th><label for="desc">描述</label></th>
							<td><s:textarea id="desc" name="party.description" cols="60" rows="5"/></td>
						</tr>
					</tbody>
				</table>
				
				<p class="submit">
					<input type="submit" class="action save" name="save" value="保存"/>
				</p>
			</s:form>
		</div>
		
		<div id="tabs-2">
			<%@ include file="../contact/profile.jsp" %>
		</div>
	</div>
</body>