<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
	<script type="text/javascript" src="<s:url value="/pages/security/account/popup.js" />"></script>
	<script type="text/javascript">
		
		$(document).ready(function(){
			/**
			 * 数据校验
			 */
			$("#profile_form").validate({
				rules:{
					"task.message":{required:true,maxlength:800}
				},
				messages:{
					"account.loginName": {}
				}
			});
			
		});
		
		/**
		 * 完成任务
		 */
		function doTask(type){
			//$("#task_id").val(taskId);
			$("#task_type").val(type);
			//$("#task_message").val(msg);
			
			$("#profile_form").submit();
		}
		
		/**
		 * 提交
		 */
		function doTaskSubmit(){
			doTask('SUBMIT');
		}
		
		/**
		 * 退回
		 */
		function doTaskRevoke(){
			doTask('REVOKE');
		}
		
		/**
		 * 用户弹出窗口
		 */
		function popupUser( ){
			popupLoginAccount('<s:url value="/security/account/popup.do?page.size=10"/>',{multi:'true'});
		}
		
		/**
		 * USER-弹出框回调
		 */
		function callbackSelectLoginAccount( param,multi ){
			var data = {};
			if( multi == null || !multi ){
				//单选弹出窗口
			}else{
				var msg = "",ids="" ;
				if( param.length == 0 )
					$.lightssh.showActionError("会签人为空，新重新选择!"); 
					
				data["taskId"] = $("#task_id").val( );
				for( var i=0;i<param.length;i++ ){
					msg = msg + (i==0?"":",") + param[i].text;
					ids = ids + (i==0?"":",") + param[i].key;
					data["users["+i+"]"] = param[i].text ;
				}
			}
			
			$( popup_login_account ).dialog('destroy').html('');
			
			countersign(data,msg); //会签
		}
		
		/**
		 * 会签
		 */
		function countersign( param ,msg){
			$.ajax({
				url: "<s:url value="/workflow/task/countersign.do"/>"
				,dataType: "json" 
				,type:"post"
				,async:false
				,data:param
		        ,error: function(){ alert("会签操作异常!"); }
		        ,success: function(json){
		        	$.lightssh.removeMessages();
		        	if( json.result.success )
		        		$.lightssh.showActionMessage("成功会签["+msg+"]");
		        	else
		        		$.lightssh.showActionError( json.result.message );
		        }
			});
			
			return result;
		}
	</script>
	
	<s:action name="viewproc" namespace="/workflow/process" executeResult="true">
		<s:param name="process.processInstanceId" value="#request.procInstId"/>
	</s:action>
	
	<s:form id="profile_form" action="complete" namespace="/workflow/task" method="post">
		<table class="profile">
			<caption>流程操作</caption>
			<tbody>
				<tr>
					<th><label for="desc">流转意见</label></th>
					<td>
						<s:hidden id="task_id" name="task.id" value="%{taskId==null?task.id:taskId}"/>
						<s:hidden id="task_type" name="task.type" value="SUBMIT"/>
						<s:textarea id="task_message" theme="simple" name="task.message" cols="80" rows="5"/>
					</td>
				</tr>
			</tbody>
		</table>
	
		<s:token />
		
		<p class="submit">
			<input type="button" class="action ok" value="提交流程" onclick="doTaskSubmit()" />
			<input type="button" class="action remove" value="拒绝流程" onclick="doTaskRevoke()" />
			
			<input type="button" class="action forward disabled" value="添加会签" onclick="popupUser()"/>
		</p>
	</s:form>
	
