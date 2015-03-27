<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<script type="text/javascript">
	function select( span ){
		var id = $( span ).attr("id");
		var name = $( span ).attr("name");
		var org = {"id":id,"name":name};
		callbackSelectOrganization( org );
	}
	
	$(document).ready(function(){
		$("#organization_tree").treeview({
			persist: "cookie",
			collapsed: false,
			cookieId: "treeview-organization"
		});
	});
</script>
  
    
<div>
	<s:set name="org" value="#request.popup_org_rollup"/>
	
	<s:if test="#org == null ">
		<span id="" name="" 
			 onclick="select(this);" style="cursor: pointer;margin: 4px;width: 100px;">
			<b>空节点</b>（选择该结点将作为最上级组织）
		</span>
	</s:if>
	
	<ul id="organization_tree">
		<li>
			<span id="<s:property value="%{#org.id}"/>" name="<s:property value="%{#org.name}"/>" 
				 onclick="select(this);" style="cursor: pointer;margin: 4px;width: 100px;">
				<s:property value="#org.name"/>
			</span>
			<s:iterator value="#org.children" status="loop2">
				<s:if test="#loop2.first"><ul></s:if>
					<li>
						<span id="<s:property value="%{id}"/>" name="<s:property value="%{name}"/>" 
					 		onclick="select(this);" style="cursor: pointer;margin: 4px;width: 100px;">
							<s:property value="%{ name }"/>
						</span>
						
						<%-- 三层(begin) --%>
						<s:if test="#parameters['level'][0]>=3"> 
						<s:iterator value="%{children}" status="loop3">
						<s:if test="#loop3.first"><ul></s:if>
							<li>
								<span id="<s:property value="%{id}"/>" name="<s:property value="%{name}"/>" 
							 		onclick="select(this);" style="cursor: pointer;margin: 4px;width: 100px;">
									<s:property value="%{ name }"/>
								</span>
								
								<%-- 四层(begin) --%>
								<s:if test="#parameters['level'][0]>=4"> 
								<s:iterator value="%{children}" status="loop4">
								<s:if test="#loop4.first"><ul></s:if>
									<li>
										<span id="<s:property value="%{id}"/>" name="<s:property value="%{name}"/>" 
									 		onclick="select(this);" style="cursor: pointer;margin: 4px;width: 100px;">
											<s:property value="%{ name }"/>
										</span>
									</li>
								<s:if test="#loop4.last"></ul></s:if>
								</s:iterator>
								</s:if>
								<%-- 四层(end) --%>
							</li>
						<s:if test="#loop3.last"></ul></s:if>
						</s:iterator>
						</s:if>
						<%-- 三层(end) --%>
					</li>
				<s:if test="#loop2.last"></ul></s:if>
			</s:iterator>
		</li>
	</ul>
	
</div>

