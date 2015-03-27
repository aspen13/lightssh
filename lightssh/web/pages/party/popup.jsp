<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<script type="text/javascript">
	function select( span ){
		var id = $( span ).attr("id");
		var name = $( span ).attr("name");
		var party = {"id":id,"name":name,"clazz":$( span ).attr("clazz")};
		callbackSelectParty( party );
	}
	
	function query( url ){
		popupParty( url ); //父窗体方法
	}
	
	function postQuery( ){
		<s:url var="root_url"/>
		var url = '<s:property value="%{root_url}"/>';
		var clazz=$('#popup_party_type').val();
		popupParty( url ,{'party.name':$('#popup_party_name').val(),"party":clazz,"party_type":clazz} );
		return false;
	}
</script>

<div>
	<s:form name="list" namespace="/party" method="post" onsubmit="return postQuery();">
		<table class="profile">
			<tbody>
				<tr>
					<th><label for="name">名称</label></th>
					<td>
						<s:hidden name="party_type" id="popup_party_type" value="%{#parameters['party_type'][0]}"/>
						<s:textfield id="popup_party_name" name="party.name" size="40" maxlength="100"/>
					</td>
					<td colspan="2"><input type="submit" class="action search" value="查询"/></td>
				</tr>
			</tbody>
		</table>
	</s:form>
	
	<table class="list">
		<colgroup>
			<col class="element" width="50px"/>
			<col class="element" width="200px"/>
			<col class="element" />
		</colgroup>
		<thead>
			<tr>
				<th>&nbsp;</th>
				<th>名称</th>
				<th>描述</th>
			</tr>
		</thead>
		<s:iterator value="#request.page.list" status="loop">
		<s:set name="clazz" value="(#request.page.list[#loop.index] instanceof com.google.code.lightssh.project.party.entity.Person) ?'person':'organization'"/>
		<tr class="<s:property value="#loop.odd?\"odd\":\"even\""/>"
			id="<s:property value="id"/>" name="<s:property value="name"/>" 
			clazz="<s:property value="#clazz"/>"
			style="cursor: pointer;" onclick="select(this)">
			<td><s:property value="#clazz=='person'?'人员':'组织'"/></td>
			<td>
				<s:property value="name"/>
			</td>
			<td><s:property value="description"/></td>
		</tr>
		</s:iterator>
	</table>
		
	<s:set name="pagination" value="%{#request.page}"/>
	<jsp:include page="/pages/common/util/paginationAJAX.jsp"/>
</div>