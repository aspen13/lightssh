<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<script type="text/javascript">
	function select( span ){
		var procDef = {
				"key":$( span ).attr("key")
				,"name":$( span ).attr("name")
				,"version":$( span ).attr("version")
			};
		callbackSelectProcDef( procDef );
	}
	
	function query( url ){
		popupProcDef( url ); //父窗体方法
	}
	
	function postQuery( ){
		<s:url var="root_url"/>
		var url = '<s:property value="%{root_url}"/>';
		popupProcDef( url ,{'process.processDefinitionName':$('#popup_procdef_name').val()} );
		return false;
	}
</script>

<div>
	<%-- 
	<s:form name="list" namespace="/workflow/process" method="post" onsubmit="return postQuery();">
		<table class="profile">
			<tbody>
				<tr>
					<th><label for="name">名称</label></th>
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
			<col class="element" width="180px"/>
			<col class="element" width="50px"/>
			<col class="element" />
		</colgroup>
		<thead>
			<tr>
				<th>&nbsp;</th>
				<th>编号</th>
				<th>版本</th>
				<th>名称</th>
			</tr>
		</thead>
		
		<s:iterator value="pd_page.list" status="loop">
		<tr class="<s:property value="#loop.odd?\"odd\":\"even\""/>"
			key="<s:property value="key"/>" 
			name="<s:property value="name"/>" 
			style="cursor: pointer;" onclick="select(this)">
			<td><s:property value="#loop.index +1"/></td>
			<td><s:property value="key"/></td>
			<td>
				<s:property value="version"/>
			</td>
			<td>
				<s:property value="name"/>
			</td>
		</tr>
		</s:iterator>
	</table>
		
	<s:set name="pagination" value="%{pd_page}" />
	<s:set name="PAGE_NUMBER_NAME" value="'pd_page.number'" />
	<jsp:include page="/pages/common/util/paginationAJAX.jsp"/>
</div>