<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<head>
	<meta name="decorator" content="background"/>
	
	<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/ui/i18n/jquery.ui.datepicker_zh_CN.js"></script>
	<script type="text/javascript" src="<s:url value="/pages/message/catalog/popup.js" />"></script>
	<script type="text/javascript" src="<s:url value="/pages/party/popup.js" />"></script>
	<script type="text/javascript" src="<s:url value="/pages/security/account/popup.js" />"></script>
	
	<title>消息订阅</title>
	
	<script type="text/javascript">
		$(document).ready(function(){
			var recType= $("select[name='subscription.recType']").val();
			$("#handler_msg_subtype").removeClass("disabled");
			if( recType == null || recType == '' )
				$("#handler_msg_subtype").addClass("disabled");
				
			jQuery.validator.addMethod("recValueLen", function(value, element) {    
				return this.optional(element) || value.length < 255;   
			},"选择的用户太多，请减少用户数");
			
			/**
			 * 数据校验
			 */
			$("#profile_form").validate({
				rules:{
					"subscription.recType":{required:true}
					,"subscription.catalog.id":{required:true}
					,"subscription.recValue":{recValueLen:true,required:function(){return $("select[name='subscription.recType']").val() != 'ALL'}}
				}
				,submitHandler: function(form) {
					var result = ajaxCheck( );
					if( result.type == 'login' ){ 
						//alert( result.message );
						$.lightssh.login({contextPath:'<%=request.getContextPath() %>'},function(result){
							if( result ) 
								$("#profile_form").submit();
						});
					}else if( !result.unique ){ 
						var url = '<s:url value="edit.do?"/>'
							+ '?subscription.catalog.id=' + $("input[name='subscription.catalog.id']").val() 
							+ '&subscription.recType=' + $("select[name='subscription.recType']").val() 
							+ '&subscription.recValue=' + $.trim( $("input[name='subscription.recValue']").val() );
						var msg = '\'消息类型\'+\'订阅类型\'+\'订阅值\'已存在！';
							msg += '<a href=\'' + url + '\'>点击进行修改！</a>';

						$.lightssh.showActionError(msg);
					}else{
						form.submit();
					}
				}
			});
			
			$(".calendar").datepicker( );
		});
		
		/**
		 * 检查名称是否重复
		 */
		function ajaxCheck( ){
			var result = false;
			$.ajax({
				url: "<s:url value="/message/subscription/unique.do"/>"
				,dataType: "json" 
				,type:"post"
				,async:false
				,data: {
		        	"subscription.id": function(){
						return $("input[name='subscription.id']").val()
			        }
			        ,"subscription.catalog.id": function(){
						return $.trim( $("input[name='subscription.catalog.id']").val() );
			        }
			        ,"subscription.recType": function(){
						return $.trim( $("select[name='subscription.recType']").val() );
			        }
			        ,"subscription.recValue": function(){
						return $.trim( $("input[name='subscription.recValue']").val() );
			        }
		        }
		        ,error: function(){ alert("检查重名出现异常!") }
		        ,success: function(json){
		        	result = json;
		        }
			});
			
			return result;
		}
		
		/**
		 * 改变类型时清除选择的数据
		 */
		function cleanSelectedValue( ele ){
			var recType= $(ele).val();
			
			$("input[name='subscription.recValue']").val('');
			$("#span_msg_subscription_subvalue").text( '');
			
			$("#handler_msg_subtype").removeClass("disabled");
			if( recType == null || recType == '' )
				$("#handler_msg_subtype").addClass("disabled");
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
			var recType= $("select[name='subscription.recType']").val();
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
			}
		}
		
		/**
		 * DEPT-弹出框回调
		 */
		function callbackSelectParty( party ){
			$("input[name='subscription.recValue']").val( party.id);
			$("#span_msg_subscription_subvalue").text( party.name);
			
			$("label[for='subscription.recValue']").remove(); //移除样式
			$( popup_party ).dialog('destroy').html('');
			
		}
		
		/**
		 * USER-弹出框回调
		 */
		function callbackSelectLoginAccount( param ){
			$("input[name='subscription.recValue']").val( param.key);
			$("#span_msg_subscription_subvalue").text( param.text );
			
			$("label[for='subscription.recValue']").remove(); //移除样式
			
			$( popup_login_account ).dialog('destroy').html('');
		}
	</script>
</head>
	
<body>
	<ul class="path">
		<li>基础管理</li>
		<li>消息管理</li>
		<li>消息订阅</li>
	</ul>
		
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<input type="button" class="action list" value="消息订阅列表" onclick="location.href='<s:url value="list.do"/>'"/>
	
	<s:form id="profile_form" action="save" method="post">
	<table class="profile">
		<tbody>
			<tr>
				<th><label for="name" class="required">消息类型</label></th>
				<td>
					<s:hidden id="subscription.id" name="subscription.id"/>
					<s:hidden name="subscription.createdTime"/>
					<s:hidden name="subscription.creator"/>
				
					<span class="popup" onclick="popupMsgCatalog('<s:url value="/message/catalog/popup.do"/>');">&nbsp;</span>
					<s:hidden id="subscription.catalog.id" name="subscription.catalog.id"/>
					<s:hidden name="subscription.catalog.description"/>
					<span id="span_msg_catalog_name"><s:property value="subscription.catalog.id + '-' + subscription.catalog.description"/></span>
				</td>
			</tr>
			
			<tr>
				<th><label for="name" class="required">订阅类型</label></th>
				<td>
					<s:select name="subscription.recType" value="%{subscription.recType.name()}" 
						listKey="name()" headerKey="" headerValue=""
						onchange="cleanSelectedValue(this)"
						list="@com.google.code.lightssh.project.message.entity.ReceiveType@supportedValues()"/>
				</td>
			</tr>
			
			<tr>
				<th><label for="handler_msg_subtype" class="required">订阅值</label></th>
				<td>
					<span class="popup disabled" id="handler_msg_subtype" onclick="popupSubType();">&nbsp;</span>
					<s:hidden id="subscription.recValue" name="subscription.recValue"/>
					<span id="span_msg_subscription_subvalue"><s:property value="subscription.recValue"/></span>
				</td>
			</tr>
			
			<tr>
				<th><label for="account_start_date">有效期</label></th>
				<td>
					<s:textfield name="subscription.period.start" id="account_start_date" cssClass="calendar" size="10" /> -
					<s:textfield name="subscription.period.end" cssClass="calendar" size="10"/>
				</td>
			</tr>
			
			<tr>
				<th><label for="desc">描述</label></th>
				<td><s:textarea id="desc" name="subscription.description" cols="60" rows="5"/></td>
			</tr>
		</tbody>
	</table>
	
	<p class="submit">
		<input type="submit" class="action save" name="Submit" 
			value="<s:property value="%{(subscription==null||subscription.insert)?\"新增消息订阅\":\"修改消息订阅\"}"/>"/>
	</p>
	</s:form>
</body>