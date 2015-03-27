<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<head>
	<meta name="decorator" content="background"/>
	
	<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/ui/i18n/jquery.ui.datepicker_zh_CN.js"></script>
	<script type="text/javascript" src="<s:url value="/pages/message/catalog/popup.js" />"></script>
	<script type="text/javascript" src="<s:url value="/pages/party/popup.js" />"></script>
	<script type="text/javascript" src="<s:url value="/pages/security/account/popup.js" />"></script>
	
	<title>创建消息</title>
	
	<script type="text/javascript">
		$(document).ready(function(){
			var recType= $("select[name='message.recType']").val();
			$("#handler_msg_subtype").removeClass("disabled");
			if( recType == null || recType == '' )
				$("#handler_msg_subtype").addClass("disabled");
			
			/**
			 * 数据校验
			 */
			$("#profile_form").validate({
				rules:{
					"publish.message.recValue":{required:function(){return $("select[name='publish.message.recType']").val() != 'ALL'}}
				}
			});
			
		});
		
		/**
		 * 改变类型时清除选择的数据
		 */
		function cleanSelectedValue( ele ){
			var recType= $(ele).val();
			var recValueHandler = $("#handler_msg_subtype");
			
			$("input[name='publish.message.recValue']").val('');
			$("#span_msg_subscription_subvalue").text( '');
			
			$("#handler_msg_subtype").removeClass("disabled");
			$( recValueHandler ).show( );
			if( recType == '<s:property value="@com.google.code.lightssh.project.message.entity.ReceiveType@ALL.name()"/>' ){
				$( recValueHandler ).hide( );
				$("label[for='publish.message.recValue']").remove(); //移除样式
			}else if( recType == null || recType == '' ){
				$( recValueHandler ).addClass("disabled");
			}
		}
		
		/**
		 * 根据类型选择
		 */
		function popupSubType( ){
			var recType= $("select[name='publish.message.recType']").val();
			if( recType == null || recType == '' )
				return;
			
			var url = '';
			if( recType == '<s:property value="@com.google.code.lightssh.project.message.entity.ReceiveType@ROLE.name()"/>'){
				url = '<s:url value="/security/role/popup.do"/>';
			}else if( recType == '<s:property value="@com.google.code.lightssh.project.message.entity.ReceiveType@DEPARTMENT.name()"/>' ){
				popupParty('<s:url value="/party/popup.do"/>',{party_type:'org'})
			}else if( recType == '<s:property value="@com.google.code.lightssh.project.message.entity.ReceiveType@PERSON.name()"/>' ){
				popupParty('<s:url value="/party/popup.do"/>',{party_type:'person'})
			}else if( recType == '<s:property value="@com.google.code.lightssh.project.message.entity.ReceiveType@USER.name()"/>' ){
				popupLoginAccount('<s:url value="/security/account/popup.do"/>',{})
			}else if( recType == '<s:property value="@com.google.code.lightssh.project.message.entity.ReceiveType@ALL.name()"/>' ){
				//所有用户
			}
		}
		
		/**
		 * DEPT-弹出框回调
		 */
		function callbackSelectParty( party ){
			$("input[name='publish.message.recValue']").val( party.id);
			$("#span_msg_subscription_subvalue").text( party.name);
			
			$("label[for='publish.message.recValue']").remove(); //移除样式
			$( popup_party ).dialog('destroy').html('');
			
		}
		
		/**
		 * USER-弹出框回调
		 */
		function callbackSelectLoginAccount( param ){
			$("input[name='publish.message.recValue']").val( param.key);
			$("#span_msg_subscription_subvalue").text( param.text );
			
			$("label[for='publish.message.recValue']").remove(); //移除样式
			
			$( popup_login_account ).dialog('destroy').html('');
		}
	</script>
</head>
	
<body>
	<ul class="path">
		<li>基础管理</li>
		<li>消息管理</li>
		<li>创建消息</li>
	</ul>
		
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<input type="button" class="action list" value="我的消息" onclick="location.href='<s:url value="/message/publish/mylist.do"/>'"/>
	
	<s:form id="profile_form" action="forward" method="post">
	<table class="profile">
		<tbody>
			<tr>
				<th><label for="name" class="required">接收人</label></th>
				<td>
					<s:hidden name="publish.id"/>
					<s:hidden name="publish.message.id"/>
					
					<s:select name="publish.message.recType" value="%{publish.message.recType.name()}" 
						listKey="name()" headerKey="" headerValue=""
						onchange="cleanSelectedValue(this)"
						list="@com.google.code.lightssh.project.message.entity.ReceiveType@supportedValues()"/>
						
					<span class="popup disabled" id="handler_msg_subtype" onclick="popupSubType();">&nbsp;</span>
					<s:hidden id="publish.message.recValue" name="publish.message.recValue"/>
					<span id="span_msg_subscription_subvalue"><s:property value="publish.message.recValue"/></span>
				</td>
			</tr>
			
			<tr>
				<th><label for="priority">优先级</label></th>
				<td>
					<s:hidden name="publish.message.priority" value="%{publish.message.priority.name()}"/>
					<s:property value="publish.message.priority"/>
				</td>
			</tr>
			
			<tr>
				<th><label for="title">标题</label></th>
				<td>
					<s:hidden name="publish.message.title"/>
					<s:property value="publish.message.title"/>
				</td>
			</tr>
			
			<tr>
				<th><label for="content">内容</label></th>
				<td>
					<s:hidden name="publish.message.content"/>
					<pre><s:property value="publish.message.content"/></pre>
				</td>
			</tr>
		</tbody>
	</table>
	
	<p class="submit">
		<input type="submit" class="action forward" name="submit" value="转发消息"/>
	</p>
	</s:form>
</body>