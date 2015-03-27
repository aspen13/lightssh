<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
	<head>
		<meta name="decorator" content="background"/>
		
		<title>系统参数</title>
		
		<script type="text/javascript">
			$(document).ready(function(){
				//$("#mytree").treeview();
				
				/**
				 * 数据校验
				 */
				$("#profile_form").validate({
					rules:{
						"param.name":{required:true,maxlength:100}
						,"param.group":{maxlength:100}
						,"param.value":{required:true,maxlength:500}
						,"param.description":{maxlength:500}
					}
					,submitHandler: function(form) {
						if( !ajaxCheck( ) ){ 
							this.showErrors({"param.name": "参数组+名称已存在,请改用其它名称！"});
						}else{
							//检查是否可授权
							$.lightssh.checkAuth(
								{'contextPath':'<%= request.getContextPath() %>'
								,'checkPassword': true
								,'targetUrl':'<s:url value="/settings/param/save.do"/>'}
								,function(succes,ticket,msg){ 
									if( !succes )
										return;

									if( ticket != null || ticket != '' ){
										$('<input>').attr({
										    type:'hidden',name:'auth.ticket',value: ticket,
										}).appendTo( form );
									}
									
									form.submit(); 
								}
							);//end $.lightssh.checkAuth
						}
					}
				});
				
			});
			
			/**
			 * 检查名称是否重复
			 */
			function ajaxCheck( ){
				var result = false;
				$.ajax({
					url: "<s:url value="/settings/param/unique.do"/>"
					,dataType: "json" 
					,type:"post"
					,async:false
					,data: {
			        	"param.id": function(){
							return $("input[name='param.id']").val()
				        }
				        ,"param.name": function(){
							return $.trim( $("input[name='param.name']").val() );
				        }
				        ,"param.group": function(){
							return $.trim( $("input[name='param.group']").val() );
				        }
			        }
			        ,error: function(){ alert("检查重名出现异常!") }
			        ,success: function(json){
			        	result = json.unique;
			        }
				});
				
				return result;
			}
		</script>
	</head>
	
<body>
	<ul class="path">
		<li>基础数据</li>
		<li>系数参数</li>
		<li>编辑参数</li>
	</ul>
	
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<s:form id="profile_form" action="save" namespace="/settings/param" method="post">
		<table class="profile">
			<tbody>
				<tr>
					<th><label for="group">组名</label></th>
					<td>
						<s:set name="isInsert" value="%{(param==null||param.id==null)}"/>
						<s:hidden name="param.id"/>
						<s:hidden name="param.system" value="%{param.system.name()}"/>
						<s:hidden name="param.createdTime"/>
						
						<s:textfield id="group" name="param.group" size="40" maxlength="100"/>
					</td>
				</tr>
				<tr>
					<th><label for="name" class="required">名称</label></th>
					<td>
						<s:textfield id="name" name="param.name" size="40" maxlength="100"/>
					</td>
				</tr>
				
				<tr>
					<th><label for="enabled">是否有效</label></th>
					<td><s:select list="#{true:'是',false:'否'}" name="param.enabled" value="%{param.enabled}"/></td>
				</tr>
				
				<tr>
					<th><label for="value"  class="required">参数值</label></th>
					<td>
						<%-- 
						<s:textfield id="value" name="param.value" size="40" maxlength="500"/>
						--%>
						<s:textarea id="value" name="param.value" cols="60" rows="3"/>
					</td>
				</tr>
				
				<tr>
					<th><label for="desc">描述</label></th>
					<td><s:textarea id="desc" name="param.description" cols="60" rows="5"/></td>
				</tr>
			</tbody>
		</table>
	
		<s:token/>
	
		<p class="submit">
			<input type="submit" class="action save" name="Submit" 
				value="<s:property value="%{#isInsert?\"新增系统参数\":\"修改系统参数\"}"/>"/>
		</p>
	</s:form>

</body>