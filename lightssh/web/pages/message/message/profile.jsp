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
			jQuery.validator.addMethod("regularName", function(value, element) {    
				return this.optional(element) ||  /^[^'|"]*$/.test(value);   
			},"不能包括引号(\')及双引号(\")！");
			
			jQuery.validator.addMethod("recValueLen", function(value, element) {    
				return this.optional(element) || value.length < 255;   
			},"选择的用户太多，请减少用户数");
			
			
			var recType= $("select[name='message.recType']").val();
			$("#handler_msg_subtype").removeClass("disabled");
			if( recType == null || recType == '' )
				$("#handler_msg_subtype").addClass("disabled");
			
			/**
			 * 数据校验
			 */
			$("#profile_form").validate({
				rules:{
					"message.recValue":{recValueLen:true,required:function(){return $("select[name='message.recType']").val() != 'ALL'}}
					,"message.title":{required:true,regularName:true,maxlength:100}
					,"message.priority":{required:true}
					,"message.content":{required:true,maxlength:2000}
				}
			});
			
		});
		
		/**
		 * 改变类型时清除选择的数据
		 */
		function cleanSelectedValue( ele ){
			var recType= $(ele).val();
			var recValueHandler = $("#handler_msg_subtype");
			
			$("input[name='message.recValue']").val('');
			$("#span_msg_subscription_subvalue").text( '');
			
			$("#handler_msg_subtype").removeClass("disabled");
			$( recValueHandler ).show( );
			if( recType == '<s:property value="@com.google.code.lightssh.project.message.entity.ReceiveType@ALL.name()"/>' ){
				$( recValueHandler ).hide( );
				$("label[for='message.recValue']").remove(); //移除样式
			}else if( recType == null || recType == '' ){
				$( recValueHandler ).addClass("disabled");
			}
		}
		
		/**
		 * 选择消息类型回调
		 */
		function callbackSelectMsgCatalog(param){
			$("input[name='subscription.catalog.id']").val(param.id);
			$("input[name='subscription.catalog.description']").val(param.description);
			
			if( param != null && (param.id != null || param.description != null)  )
				$("#span_msg_catalog_name").text( param.id + '-' +param.description);
			else
				$("#span_msg_catalog_name").text( '');
			$( popup_msg_catalog ).dialog('destroy').html('');
			
			$("label[for='subscription.catalog.id']").remove(); //移除样式
		}
		
		/**
		 * 根据类型选择
		 */
		function popupSubType( ){
			var recType= $("select[name='message.recType']").val();
			if( recType == null || recType == '' )
				return;
			
			var url = '';
			if( recType == '<s:property value="@com.google.code.lightssh.project.message.entity.ReceiveType@ROLE.name()"/>'){
				url = '<s:url value="/security/role/popup.do"/>';
			}else if( recType == '<s:property value="@com.google.code.lightssh.project.message.entity.ReceiveType@DEPARTMENT.name()"/>' ){
				popupParty('<s:url value="/party/popup.do"/>',{party_type:'org'});
			}else if( recType == '<s:property value="@com.google.code.lightssh.project.message.entity.ReceiveType@PERSON.name()"/>' ){
				popupParty('<s:url value="/party/popup.do"/>',{party_type:'person'});
			}else if( recType == '<s:property value="@com.google.code.lightssh.project.message.entity.ReceiveType@USER.name()"/>' ){
				popupLoginAccount('<s:url value="/security/account/popup.do?page.size=10"/>',{multi:'true'});
			}else if( recType == '<s:property value="@com.google.code.lightssh.project.message.entity.ReceiveType@ALL.name()"/>' ){
				//所有用户
			}
		}
		
		/**
		 * DEPT-弹出框回调
		 */
		function callbackSelectParty( party ){
			$("input[name='message.recValue']").val( party.id);
			$("#span_msg_subscription_subvalue").text( party.name);
			
			$("label[for='subscription.recValue']").remove(); //移除样式
			$( popup_party ).dialog('destroy').html('');
			
		}
		
		/**
		 * USER-弹出框回调
		 */
		function callbackSelectLoginAccount( param,multi ){
			if( multi == null || !multi ){
				$("input[name='message.recValue']").val( param.key);
				$("#span_msg_subscription_subvalue").text( param.text );
			}else{
				var msg = "",ids="" ;
				for( var i=0;i<Object.keys(param).length;i++ ){
					msg = msg + (i==0?"":",") + param[i].text;
					ids = ids + (i==0?"":",") + param[i].key;
				}
				$("input[name='message.recValue']").val( ids );
				$("#span_msg_subscription_subvalue").text( msg );
			}
			
			$("label[for='message.recValue']").remove(); //移除样式
			
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
	
	<s:form id="profile_form" action="save" method="post">
	<table class="profile">
		<tbody>
			<%-- 
			<tr>
				<th><label for="name" class="required">消息类型</label></th>
				<td>
					<s:hidden name="message.id"/>
				
					<span class="popup" onclick="popupMsgCatalog('<s:url value="/message/catalog/popup.do"/>');">&nbsp;</span>
					<s:hidden id="message.catalog.id" name="message.catalog.id"/>
					<s:hidden name="message.catalog.description"/>
					<span id="span_msg_catalog_name"><s:property value="message.catalog.id + '-' + message.catalog.description"/></span>
				</td>
			</tr>
			--%>
			
			<tr>
				<th><label for="name" class="required">接收人</label></th>
				<td>
					<s:select name="message.recType" value="%{message.recType.name()}" 
						listKey="name()" headerKey="" headerValue=""
						onchange="cleanSelectedValue(this)"
						list="@com.google.code.lightssh.project.message.entity.ReceiveType@supportedValues()"/>
						
					<span class="popup disabled" id="handler_msg_subtype" onclick="popupSubType();">&nbsp;</span>
					<s:hidden id="message.recValue" name="message.recValue"/>
					<span id="span_msg_subscription_subvalue"><s:property value="message.recValue"/></span>
				</td>
			</tr>
			
			<tr>
				<th><label for="priority" class="required">优先级</label></th>
				<td>
					<s:select name="message.priority" value="%{message.priority.name()}" 
						listKey="name()" headerKey="" headerValue=""
						list="@com.google.code.lightssh.project.message.entity.Message$Priority@values()"/>
				</td>
			</tr>
			
			<tr>
				<th><label for="title" class="required">标题</label></th>
				<td>
					<s:textfield name="message.title" size="60" maxlength="100"/> 
					
				</td>
			</tr>
			
			<tr>
				<th><label for="content" class="required">内容</label></th>
				<td><s:textarea id="desc" name="message.content" cols="80" rows="7"/></td>
			</tr>
		</tbody>
	</table>
	
	<p class="submit">
		<input type="submit" class="action save" name="Submit" 
			value="<s:property value="%{(subscription==null||subscription.insert)?\"新增消息\":\"修改消息\"}"/>"/>
	</p>
	</s:form>
</body>