<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<head>
	<meta name="decorator" content="background"/>
	<title>修改密码</title>
	
	<script type="text/javascript">
		/**
		 * 数据校验
		 */
		$(document).ready(function(){
			$("#password_form").validate({
				rules:{
					"passwords[0]":"required"
					,"passwords[1]":{required:true,minlength:5}
					,"passwords[2]":{required:true,equalTo:"#new_password"}
				},
				messages:{
					"passwords[2]": {equalTo:"'重复密码'必须与'新密码'相同！"}
				}
			});
		});
	</script>
</head>
	
<body>
	<ul class="path">
		<li>系统管理</li>
		<li>登录账号</li>
		<li>修改密码</li>
	</ul>
		
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<s:form id="password_form" action="password" namespace="/security/account" method="post">
		<table class="profile">
			<tbody>
				<tr>
					<th><label for="name">用户账号</label></th>
					<td>
						<s:set name="principal" value="#session[@com.google.code.lightssh.common.web.SessionKey@LOGIN_ACCOUNT].loginName"/>
						<s:property value="#principal"/>
					</td>
				</tr>
				<tr>
					<th><label for="old_password" class="required">原密码</label></th>
					<td>
						<s:password id="old_password" name="passwords[0]" size="40"/>
					</td>
				</tr>
				<tr>
					<th><label for="new_password" class="required">新密码</label></th>
					<td>
						<s:password id="new_password" name="passwords[1]" size="40"/>
					</td>
				</tr>
				<tr>
					<th><label for="new_password2" class="required">重复新密码</label></th>
					<td>
						<s:password id="new_password2" name="passwords[2]" size="40"/>
					</td>
				</tr>
			</tbody>
		</table>
	
		<p class="submit">
			<input type="submit" class="action save" name="Submit" value="修改密码"/>
		</p>
	</s:form>

</body>