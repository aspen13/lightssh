<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<head>
	<meta name="decorator" content="background"/>

	<title>我的消息</title>
	
	<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery/ui/i18n/jquery.ui.datepicker_zh_CN.js"></script>
	<script type="text/javascript" src="<s:url value="/pages/security/account/popup.js" />"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$(".calendar").datepicker({dateFormat: 'yy-mm-dd',changeYear:true});
			
			$(".message").bind("click",function(evt){
				if(evt==null) 
					evt = window.event;
			    var target = (typeof evt.target != 'undefined')?evt.target:evt.srcElement;
			    
				$( $(target).parent().find(".content") ).slideToggle();
				
				if( $(target).parent().attr("class").indexOf('unread') )
					ajaxRead( $(target).parent().attr("publishid"),target );
			});
		});
		
		/**
		 * 检查名称是否重复
		 */
		function ajaxRead( id ,target){
			var result = false;
			$.ajax({
				url: "<s:url value="/message/publish/read.do"/>"
				,dataType: "json" 
				,type:"post"
				,async:false
				,data: {
		        	"publish.id": id
		        }
		        ,error: function(){ 
		        	$.lightssh.showActionError("阅读消息异常!"); 
		        }
		        ,success: function(json){
		        	result = json.result.success;
		        	if( result ){
		        		$(target).parent().find(".flag").text('已读');
		        		$(target).parent().removeClass("unread");
		        	}
		        }
			});
			
			return result;
		}
		
		/**
		 * USER-弹出框回调
		 */
		function callbackSelectLoginAccount( param ){
			$("input[name='publish.message.creator']").val( param.text);
			
			$( popup_login_account ).dialog('destroy').html('');
		}
		
		/**
		 * 删除消息发布
		 */
		function removePublish(target,id,msgId,title ){
			if( id == null || id == '' ){
				$.lightssh.showActionError("参数错误，未选择消息发布id!");
				return;
			}
			
			$.ajax({
				url: "<s:url value="/message/publish/remove.do"/>"
				,dataType: "json" 
				,type:"post"
				,async:false
				,data: {
		        	"publish.id": id
		        	,"publish.message.id": msgId
		        }
		        ,error: function(){ 
		        	$.lightssh.showActionError("阅读消息异常!"); 
		        }
		        ,success: function(json){
		        	result = json.result.success;
		        	if( result ){
		        		$.lightssh.showActionMessage("成功删除消息!"); 
		        		$( target ).closest("tr").remove();
		        	}else{
		        		$.lightssh.showActionError( json.result.message ); 
		        	}
		        }
			});
		}
		
		/**
		 * 消息转发
		 */
		function doForward( id ){
			location.href='<s:url value="/message/publish/preforward.do"/>?publish.id=' + id;
		}
	</script>
	
	<style type="text/css">
		tr.unread{
			font-weight: bold;
			color: green;
		}
	</style>
</head>
	
<body>
	<ul class="path">
		<li>消息中心</li>
		<li>我的消息</li>
	</ul>
	
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<s:form name="mytodolist" method="post">
		<input type="submit" class="action search" value="我的消息"/>
		<input type="button" class="action outbox" value="消息发件箱"
			onclick="location.href='<s:url value="/message/message/outbox.do"/>'"/>
		<input type="button" class="action new" value="创建消息" 
			onclick="location.href='<s:url value="/message/message/edit.do"/>'"/>
		
		<table class="profile">
			<colgroup>
				<col />
				<col width="35%"/>
				<col />
				<col />
			</colgroup>
			<tbody>
				<tr>
					<th><label for="title">消息标题</label></th>
					<td><s:textfield id="title" name="publish.message.title" size="30" maxlength="100"/></td>
					
					<th><label for="read">阅读标志</label></th>
					<td>
						<s:select id="read" name="publish.read" list="#{false:'未读',true:'已读' }"
							 headerKey="" headerValue="" />
					</td>
					
				</tr>
				<tr>
					<th><label for="recevieTime">接收时间</label></th>
					<td>
						<s:textfield name="publish.period.start" id="recevieTime" cssClass="calendar" size="10" /> -
						<s:textfield name="publish.period.end" cssClass="calendar" size="10"/>
					</td>
					
					<th><label for="creator">消息创建者</label></th>
					<td>
						<s:textfield name="publish.message.creator" id="creator" size="20" />
						<span class="popup user" onclick="popupLoginAccount('<s:url value="/security/account/popup.do"/>',{});">&nbsp;</span>
					</td>
				</tr>
			</tbody>
		</table>
	</s:form>
	
	<table class="list">
		<colgroup>
			<col width="50px"/>
			<col width="50px"/>
			<col />
			<col width="50px"/>
			<col width="80px"/>
			<col width="150px"/>
		</colgroup>
		<thead>
			<tr>
				<th>序号</th>
				<th>级别</th>
				<th>标题</th>
				<th>阅读标志</th>
				<th>创建者</th>
				<th>接收时间</th>
			<tr/>
		</thead>
		<tbody>
		<s:iterator value="page.list" status="loop">
			<s:set name="unread" value="readTime == null"/>
			<tr class="<s:property value="#loop.odd?\"odd\":\"even\""/> message <s:property value="#unread?'unread':'read'"/>" publishid="<s:property value="id"/>">
				<td><s:property value="#loop.index + 1"/></td>
				<td><s:property value="message.priority"/></td>
				<td>
					<s:property value="message.title"/>
					
					<div class="content summary" style="display: none;float: none;text-align: left;margin: 2px 5px 2px 5px;">
						<div class="line">
							<div class="left"></div>
							<div class="right"></div>
						</div>
						<pre><s:property value="%{message.content}"/></pre>
						
						<div class="line">
							<div class="left"></div>
							<div class="right"></div>
						</div>
						
						<input type="button" class="action forward" <s:property value="message.forward?'':'disabled'"/> 
							value="转发" onclick="doForward('<s:property value="id"/>')"/>
						<input type="button" class="action remove" value="删除" onclick="removePublish(
							this,'<s:property value="id"/>','<s:property value="message.id"/>')"/>
					</div>
				</td>
				<td class="flag"><s:property value="#unread?'未读':'已读'"/></td>
				<td><s:property value="message.creator"/></td>
				<td><s:property value="createdTime"/></td>
			</tr>
		</s:iterator>
		</tbody>
	</table>

	<mys:pagination value="page"/>
</body>
