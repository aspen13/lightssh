<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
	<head>
		<meta name="decorator" content="background"/>
		
		<title>编辑分类树</title>
		
		<script type="text/javascript">
			$(document).ready(function(){
				//$("#mytree").treeview();
				
				/**
				 * 数据校验
				 */
				$("#profile_form").validate({
					rules:{
						"tree.name":{required:true,maxlength:100}
						,"tree.description":{maxlength:200}
					}
					,submitHandler: function(form) {
						if( !ajaxCheck( ) ){ 
							this.showErrors({"tree.name": "名称已存在,请改用其它名称！"});
						}else{
							form.submit();
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
					url: "<s:url value="/settings/tree/unique.do"/>"
					,dataType: "json" 
					,type:"post"
					,async:false
					,data: {
			        	"tree.id": function(){
							return $("input[name='tree.id']").val()
				        }
				        ,"tree.name": function(){
							return $.trim( $("input[name='tree.name']").val() );
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
		<li>基础管理</li>
		<li>分类树</li>
		<li>编辑分类树</li>
	</ul>
		
	<%@ include file="/pages/common/util/messages.jsp" %>
	
	<s:form id="profile_form" action="save" namespace="/settings/tree" method="post">
		<table class="profile">
			<tbody>
				<tr>
					<th><label for="name" class="required">名称</label></th>
					<td>
						<s:set name="isInsert" value="%{(tree==null||tree.id==null)}"/>
						<s:hidden name="tree.id"/>
						
						<s:textfield id="name" name="tree.name" size="40" maxlength="100"/>
					</td>
				</tr>
				
				<tr>
					<th><label for="desc">描述</label></th>
					<td><s:textarea id="desc" name="tree.description" cols="60" rows="5"/></td>
				</tr>
			</tbody>
		</table>
	
		<s:token/>
	
		<p class="submit">
			<input type="submit" class="action save" name="Submit" 
				value="<s:property value="%{#isInsert?\"新增分类树\":\"修改分类树\"}"/>"/>
			
		</p>
	</s:form>

</body>