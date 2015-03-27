<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

<link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/styles/<mys:theme />/theme.css" />
<link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/scripts/jquery/styles/theme.css" />

<script type="text/javascript">
	function select( span ){
		var param = {
				"key":$( span ).attr("id")
				,"text":$( span ).attr("loginName")
				//,"description":$( span ).attr("description")
			};
		
		callbackSelectLoginAccount( param );
	}
	
	function query( url ){
		popupLoginAccount( url ); //父窗体方法
	}
	
	function postQuery( ){
		<s:url var="root_url"/>
		var url = '<s:property value="%{root_url}"/>';
		popupLoginAccount( url ,{
			'account.loginName':$("input[name='account.loginName']").val()
			,'account.status':$("select[name='account.status']").val()
		});
		return false;
	}
</script>

<div>
	<s:form name="list" namespace="/security/account" method="post" onsubmit="return postQuery();">
		<table class="profile">
			<tbody>
				<tr>
					<th><label for="loginName">登录账号</label></th>
					<td>
						<s:textfield id="loginName" name="account.loginName" size="20" maxlength="100"/>
					</td>
					<th><label for="status">状态</label></th>
					<td>
						<s:select  id="status" name="account.status" value="account.status.name()"
							list="@com.google.code.lightssh.project.util.constant.AuditStatus@values()"
							listKey="name()" listValue="getValue()" headerKey="" headerValue=""/>
					</td>
					<td colspan="2"><input type="submit" class="action search" value="查询"/></td>
				</tr>
			</tbody>
		</table>
	</s:form>
	
	<table class="list">
		<colgroup>
			<col class="element" width="30px"/>
			<col class="element" width="100px"/>
			<col class="element" width="120px"/>
			<col class="element" width="50px"/>
			<col class="element" width="90px"/>
			<col class="element" />
		</colgroup>
		<thead>
			<tr>
				<th>&nbsp;</th>
				<th>帐号名称</th>
				<th>人员名称</th>
				<th>状态</th>
				<th>创建日期</th>
				<th>描述</th>
			</tr>
		</thead>
		
		<s:iterator value="page.list" status="loop">
		<tr class="<s:property value="#loop.odd?\"odd\":\"even\""/>"
			id="<s:property value="id"/>" 
			loginName="<s:property value="loginName"/>" 
			description="<s:property value="description"/>" 
			style="cursor: pointer;" onclick="select(this)">
			<td><s:property value="#loop.index +1"/></td>
			<td><s:property value="loginName"/></td>
			<td>
				<s:property value="@com.google.code.lightssh.project.party.util.PartyHelper@getParty(partyId).name"/>
			</td>
			<td>
				<s:property value="status"/>
			</td>
			<td>
				<s:property value="createDate"/>
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