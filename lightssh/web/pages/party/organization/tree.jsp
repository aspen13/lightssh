<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<s:set name="org" value="party"/>
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
					<s:iterator value="%{children}" status="loop3">
					<s:if test="#loop3.first"><ul></s:if>
						<li>
							<span id="<s:property value="%{id}"/>" name="<s:property value="%{name}"/>" 
						 		onclick="select(this);" style="cursor: pointer;margin: 4px;width: 100px;">
								<s:property value="%{ name }"/>
							</span>
							
							<%-- 四层(begin) --%>
							<s:iterator value="%{children}" status="loop4">
							<s:if test="#loop4.first"><ul></s:if>
								<li>
									<span id="<s:property value="%{id}"/>" name="<s:property value="%{name}"/>" 
								 		onclick="select(this);" style="cursor: pointer;margin: 4px;width: 100px;">
										<s:property value="%{ name }"/>
									</span>
									
									<%-- 五层(begin) --%>
									<s:iterator value="%{children}" status="loop5">
									<s:if test="#loop5.first"><ul></s:if>
										<li>
											<span id="<s:property value="%{id}"/>" name="<s:property value="%{name}"/>" 
										 		onclick="select(this);" style="cursor: pointer;margin: 4px;width: 100px;">
												<s:property value="%{ name }"/>
											</span>
										</li>
									<s:if test="#loop5.last"></ul></s:if>
									</s:iterator>
									<%-- 五层(end) --%>
								</li>
							<s:if test="#loop4.last"></ul></s:if>
							</s:iterator>
							<%-- 四层(end) --%>
						</li>
					<s:if test="#loop3.last"></ul></s:if>
					</s:iterator>
					<%-- 三层(end) --%>
				</li>
			<s:if test="#loop2.last"></ul></s:if>
		</s:iterator>
	</li>
</ul>