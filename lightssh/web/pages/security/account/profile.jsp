<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<head>
	<meta name="decorator" content="background"/>
	
	<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/ui/i18n/jquery.ui.datepicker_<s:property value="locale"/>.js"></script>
	<script type="text/javascript" src="<s:url value="/pages/party/popup.js" />"></script>
	<script type="text/javascript" src="<s:url value="/pages/security/role/popup.js" />"></script>
		
	<title>编辑用户账号</title>
	
	<script type="text/javascript">
		
		$(document).ready(function(){
			//登录名称校验
			jQuery.validator.addMethod("loginName", function(value, element) {    
				return this.optional(element) ||  /^[\w\u4e00-\u9fa5]*[0-9]*[a-z]*[A-Z]*$/.test(value);   
			},"只能包含中文,数字[0-9],英文字母[a-z,A-Z]！");    

			/**
			 * 数据校验
			 */
			$("#profile_form").validate({
				rules:{
					"account.loginName":{required:true,loginName:true}
				},
				messages:{
					"account.loginName": {}
				}
			});
			
			$("#account_start_date").datepicker( );
			$("#account_end_date").datepicker( );
		});
		
		
		/**
		 * 角色弹出窗口
		 */
		function popupRole( ){
			popupSecurityRole('<s:url value="/security/role/popup.do"/>',{multi:'true'});
		}
		
		/**
		 * 弹出框回调
		 */
		function callbackSelectSecurityRole( param ){
			//if( param.length == 0 )
			//	$.lightssh.showActionError("角色为空，新重新选择!"); 
			
			$("#selected_roles").html('');
			$("input[name='account.roles']").remove();
				
			for( var i=0;i<param.length;i++ ){
				$("#selected_roles").append( (i==0?'':' , ')+ param[i].text );
				$("#selected_roles").append("<input type='hidden' name='account.roles' value='"+param[i].key+"'/>" );
			}
			
			$("input[type=submit]").removeAttr("disabled");
			$( popup_security_role ).dialog('destroy').html('');
		}
	</script>
</head>
	
<body>
	<s:set name="isInsert" value="%{(account==null||account.id==null)}"/>
	<ul class="path">
		<li>系统管理</li>
		<li>登录账号</li>
		<li><s:property value="%{#isInsert?\"新增账号\":\"修改账号\"}"/></li>
	</ul>
	
	<input type="button" class="action list" value="账号列表" onclick="location.href='<s:url value="list.do"/>'"/>
		
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<s:form id="profile_form" action="save" namespace="/security/account" method="post">
		<table class="profile">
			<tbody>
				<tr>
					<th><label for="name" class="required">登录账号</label></th>
					<td>
						<s:hidden name="account.id"/>
						
						<s:if test="#isInsert">
							<s:textfield id="name" name="account.loginName" size="40" maxlength="300"/>
						</s:if>
						<s:else>
							<s:property value="account.loginName"/>
							<s:hidden name="account.loginName"/>
						</s:else>
					</td>
				</tr>
				<tr>
					<th><label for="account_party">人员</label></th>
					<td>
						<span class="popup" onclick="popupParty('<s:url value="/party/popup.do"/>',{party_type:'person'});">&nbsp;</span>
						<s:hidden name="account.party.id" id="party_id"/>
						<s:hidden name="account.party.name" id="party_name"/>
						<s:hidden name="account.party" value="%{account.party.id.startsWith('ORG')?'organization':'person'}" id="party_clazz"/>
						<span id="span_party_name"><s:property value="%{account.party.name}"/></span>
					</td>
				</tr>
				<tr>
					<th><label for="account_start_date">有效期</label></th>
					<td>
						<s:textfield name="account.period.start" id="account_start_date" size="10" /> -
						<s:textfield name="account.period.end" id="account_end_date" size="10"/>
					</td>
				</tr>
				<tr>
					<th><label for="email">电子邮箱</label></th>
					<td><s:textfield id="email" name="account.email" size="40"/></td>
				</tr>
				<tr>
					<th><label for="role">权限</label></th>
					<td>
						<span class="popup" onclick="popupRole();">&nbsp;</span>
						
						<span id="selected_roles">
							<s:iterator value="account.roles" status="loop">
								<s:hidden name="account.roles" value="%{id}"/>
								<s:property value="#loop.first?'':' , '"/><s:property value="name"/>
							</s:iterator>
						</span>
					</td>
				</tr>
				<tr>
					<th><label for="desc">描述</label></th>
					<td><s:textarea id="desc" name="account.description" cols="60" rows="5"/></td>
				</tr>
			</tbody>
		</table>
	
		<s:token />
		
		<p class="submit">
			<input type="submit" class="action save" name="Submit" 
				value="<s:property value="%{#isInsert?\"新增账号\":\"修改账号\"}"/>"/>
				
			<input type="submit" class="action save" name="saveAndNext" value="保存&新增下一个"/>
		</p>
	</s:form>

	<div id="party_popup" title="会员" style="display: none;">&nbsp;</div>
	<div id="role_popup" title="角色列表" style="display: none;">&nbsp;</div>
</body>