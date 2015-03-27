<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<script type="text/javascript">
	function select( span ){
		var param = {
				"id":$( span ).attr("id")
				,"type":$( span ).attr("type")
				,"description":$( span ).attr("description")
			};
		
		callbackSelectMsgCatalog( param );
	}
	
	function query( url ){
		popupMsgCatalog( url ); //父窗体方法
	}
	
	function postQuery( ){
		<s:url var="root_url"/>
		var url = '<s:property value="%{root_url}"/>';
		popupMsgCatalog( url ,{'catalog.id':$('#popup_msgcatalog_id').val()} );
		return false;
	}
</script>

<div>
	<%-- 
	<s:form name="list" namespace="/workflow/process" method="post" onsubmit="return postQuery();">
		<table class="profile">
			<tbody>
				<tr>
					<th><label for="name">编号</label></th>
					<td>
						<s:textfield id="popup_procdef_name" name="process.processDefinitionName" size="40" maxlength="100"/>
					</td>
					<td colspan="2"><input type="submit" class="action search" value="查询"/></td>
				</tr>
			</tbody>
		</table>
	</s:form>
	--%>
	
	<table class="list">
		<colgroup>
			<col class="element" width="30px"/>
			<col class="element" width="80px"/>
			<col class="element" width="50px"/>
			<col class="element" width="50px"/>
			<col class="element" width="50px"/>
			<col class="element" width="50px"/>
			<col class="element" />
		</colgroup>
		<thead>
			<tr>
				<th>&nbsp;</th>
				<th>编号</th>
				<th>类型</th>
				<th>可订阅</th>
				<th>可转发</th>
				<th>只读</th>
				<th>描述</th>
			</tr>
		</thead>
		
		<s:iterator value="page.list" status="loop">
		<tr class="<s:property value="#loop.odd?\"odd\":\"even\""/>"
			id="<s:property value="id"/>" 
			type="<s:property value="type"/>" 
			description="<s:property value="description"/>" 
			style="cursor: pointer;" onclick="select(this)">
			<td><s:property value="#loop.index +1"/></td>
			<td><s:property value="id"/></td>
			<td>
				<s:property value="type"/>
			</td>
			<td>
				<s:property value="subscribe?'是':'否'"/>
			</td>
			<td>
				<s:property value="forward?'是':'否'"/>
			</td>
			<td>
				<s:property value="readOnly?'是':'否'"/>
			</td>
			<td>
				<s:property value="description"/>
			</td>
		</tr>
		</s:iterator>
	</table>
		
	<s:set name="pagination" value="%{page}" />
	<s:set name="PAGE_NUMBER_NAME" value="'page.number'" />
	<jsp:include page="/pages/common/util/paginationAJAX.jsp"/>
</div>