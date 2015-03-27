<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>
	
<head>
	<meta name="decorator" content="background"/>
	<title>查看人员</title>
</head>

<body>
	<ul class="path">
		<li>系统管理</li>
		<li>人员管理</li>
		<li>查看人员</li>
	</ul>
		
	<%@ include file="/pages/common/util/messages.jsp" %>
	<table class="profile">
		<caption>人员基本信息</caption>
		<colgroup>
			<col width="15%"/>
			<col width="15%"/>
			<col width="25%"/>
			<col width="15%"/>
			<col />
		</colgroup>
		<tbody>
			<tr>
				<th rowspan="7"><img src="<s:url value="/images/default_avatar.png"/>" height="150px"/></th>
				<th>姓名</th>
				<td><s:property value="%{party.name}"/></td>
				<th>出生日期</th>
				<td><s:property value="%{party.birthday}"/></td>
			</tr>
			
			<tr>
				<th>性别</th>
				<td><s:property value="%{party.gender}"/></td>
				<th>婚况</th>
				<td><s:property value="%{party.maritalStatus}"/></td>
			</tr>
			
			<tr>
				<th>民族</th>
				<td><s:property value="%{party.ethnicGroup}"/></td>
				<th>政治面貌</th>
				<td><s:property value="%{party.partyAffiliation}"/></td>
			</tr>
			
			<tr>
				<th>籍贯</th>
				<td>
					<s:property value="%{party.country.name}"/>
					<s:property value="%{party.secondaryGeo.name}"/>
					<s:property value="%{party.thirdGeo.name}"/>
					<s:property value="%{party.fourthGeo.name}"/>
				</td>
				<th>最高学历</th>
				<td><s:property value="%{party.degree}"/></td>
			</tr>
			
			<tr>
				<th>证件(<s:property value="%{party.credentialsType}"/>)</th>
				<td><s:property value="%{party.identityCardNumber}"/></td>
				<th>血型</th>
				<td><s:property value="%{party.bloodType}"/></td>
			</tr>
			
			<tr>
				<th>身高</th>
				<td>
					<s:if test="party.height != null && party.height != 0">
						<s:property value="%{party.height}"/>&nbsp;CM
					</s:if>
				</td>
				<th>体重</th>
				<td>
					<s:if test="party.weight != null && party.weight != 0">
						<s:property value="%{party.weight}"/>&nbsp;KG
					</s:if>
				</td>
			</tr>
			
			<tr>
				<th>描述</th>
				<td colspan="3"><s:property value="%{party.description}"/></td>
			</tr>
		</tbody>
	</table>
	
	<table class="profile">
		<caption>人事信息</caption>
		<colgroup>
			<col width="15%"/>
			<col width="30%"/>
			<col width="15%"/>
			<col />
		</colgroup>
		<tbody>
			<tr>
				<th>员工编号</th>
				<td><s:property value="%{employee.code}"/></td>
				<th>部门</th>
				<td><s:property value="%{employee.organization.name}"/></td>
			</tr>
			<tr>
				<th>人事性质</th>
				<td><s:property value="%{employee.type}"/></td>
				<th>人事状态</th>
				<td><s:property value="%{employee.status}"/></td>
			</tr>
			<tr>
				<th>职位</th>
				<td><s:property value="%{employee.position}"/></td>
				<th>工作地</th>
				<td><s:property value="%{employee.workplace}"/></td>
			</tr>
		</tbody>
	</table>
</body>