<%@ page import="java.util.Map" %>
<%@ page import="java.util.Enumeration" %>
<%@ include file="/pages/common/util/taglibs.jsp" %>
<%@ page pageEncoding="UTF-8" %>

<div class="pagination">
<s:if test="#pagination.allSize > 0">
	<s:if test="#PAGE_NUMBER_NAME == null">
		<s:set name="PAGE_NUMBER_NAME" value="\"page.number\""/>
	</s:if>
	
	<s:if test="#PAGE_SIZE_NAME == null">
		<s:set name="PAGE_SIZE_NAME" value="\"page.size\""/>
	</s:if>
    
    <!-- query string -->    
    <s:url var="root_url"/> 
    <s:set name="headerQueryString" value="%{root_url}" />
    <s:set name="headerQueryString" value="#headerQueryString + '?'" />
    <s:set name="queryString" value="''" />
    <s:set name="jumpQS" value="''" />
    <s:set name="over_write_params" value="#request.over_write"/>
    <s:set name="first" value="true"/>
    <s:set name="first_param_value" value="''"/>
	<s:iterator value="#parameters" status="loop">
		<s:iterator value="value" status="loop">
			<s:set name="paramValue" value="value[#loop.index]"/>	
			<s:if test="key == #FIRST_PARAM_NAME ">
				<s:set name="first_param_value" value="#paramValue"/>
			</s:if>
			<s:elseif test="(key != #PAGE_NUMBER_NAME) && (key != \"_view_theme\")">
				<s:if test="#over_write_params[key] != null">
					<s:set name="paramValue" value="%{#over_write_params[key]}"/>
				</s:if>			
				<s:set name="queryString" value="#queryString + (#first?\"\":\"&amp;\") + key + \"=\" + #paramValue "/>
				
				<s:if test="(key != #PAGE_SIZE_NAME)">
					<s:set name="jumpQS" value="#jumpQS + (#first?\"\":\"&\") + key + \"=\" + #paramValue "/>
				</s:if>
	
				<s:set name="first" value="false"/>	  
			</s:elseif>
		</s:iterator>
	</s:iterator>
	
	<s:set name="firstParamMap" value="(#FIRST_PARAM_NAME==null)?'':(#FIRST_PARAM_NAME+'='+#first_param_value+'&')"/>
	<s:set name="queryString" value="#headerQueryString + #firstParamMap + #queryString" />
	<s:set name="jumpQS" value="#headerQueryString + #firstParamMap +#jumpQS" />
		
	<s:set name="MAX_PAGE_NUM" value="8"/> 
	<s:set name="MAX_PAGE_LEFT" value="%{#MAX_PAGE_NUM/2}"/>
	<s:set name="MAX_PAGE_RIGHT" value="#MAX_PAGE_NUM - #MAX_PAGE_LEFT - 1"/>
	<s:set name="start_page" value="@java.lang.Math@max(#pagination.number - #MAX_PAGE_LEFT,1)"/>
	<s:set name="end_page" value="@java.lang.Math@min(#pagination.number + #MAX_PAGE_RIGHT, #pagination.allPage)"/>
	
	<s:if test="#start_page==1">
		<s:set name="end_page" value="@java.lang.Math@min(#MAX_PAGE_NUM,#pagination.allPage)"/>
	</s:if>
	<s:if test="#end_page==#pagination.allPage">
		<s:set name="start_page" value="@java.lang.Math@max(#end_page-#MAX_PAGE_NUM,1)"/>
	</s:if>
	
	<s:if test="#pagination.number > 1">
		<a class="previous"  href="<s:property value="#queryString+ '&amp;'" escape="false"/><s:property value="#PAGE_NUMBER_NAME"/>=<s:property value="#pagination.number-1"/>">
			&laquo; 上一页
		</a> 
	</s:if>
	
	<s:if test="#start_page > 1">
		<a class="number" href="<s:property value="#queryString + '&amp;'" escape="false"/><s:property value="#PAGE_NUMBER_NAME"/>=1">
			1
		</a>
		<s:if test="#start_page == 3">
			<a class="number"  href="<s:property value="#queryString+ '&amp;'" escape="false"/><s:property value="#PAGE_NUMBER_NAME"/>=2">
				2
			</a>
		</s:if>
		<s:elseif test="#start_page >2">
			<a class="number"  href="<s:property value="#queryString+ '&amp;'" escape="false"/><s:property value="#PAGE_NUMBER_NAME"/>=<s:property value="%{#start_page/2+1}"/>">...</a>
			<%-- <span class="number dots">...</span> --%>
		</s:elseif>
	</s:if>
	
	<s:iterator value="new int[#end_page - #start_page + 1]" status="loop">	
		<s:set name="page_index" value="#start_page + #loop.index"/>
		<s:if test="#pagination.allPage >1">
			<s:if test="#page_index==#pagination.number">
				<span class="number current" title="Page <s:property value="#page_index"/>"><s:property value="#page_index"/></span>
			</s:if>
			<s:else>
				<a class="number" href="<s:property value="#queryString+ '&amp;'" escape="false"/><s:property value="#PAGE_NUMBER_NAME"/>=<s:property value="#page_index"/>"><s:property value="#page_index"/></a>
			</s:else>	
		</s:if>	
	</s:iterator>
	
	<s:if test="#end_page != #pagination.allPage">
		<s:if test="#end_page == #pagination.allPage-2">
			<a class="number" href="<s:property value="#queryString+ '&amp;'" escape="false"/><s:property value="#PAGE_NUMBER_NAME"/>=<s:property value="#pagination.allPage-1"/>">
				<s:property value="#pagination.allPage-1"/> 
			</a>
		</s:if>
		<s:elseif test="#end_page <#pagination.allPage-1">
			<a class="number"  href="<s:property value="#queryString+ '&amp;'" escape="false"/><s:property value="#PAGE_NUMBER_NAME"/>=<s:property value="%{#end_page+(#pagination.allPage-#end_page)/2}"/>">...</a>
			<%-- <span class="number dots">...</span> --%>
		</s:elseif>
		<a class="number" href="<s:property value="#queryString+ '&amp;'" escape="false"/><s:property value="#PAGE_NUMBER_NAME"/>=<s:property value="#pagination.allPage"/>">
			<s:property value="#pagination.allPage"/> 
		</a>
	</s:if>
	
	<s:if test="#pagination.number < #pagination.allPage">
		<a class="next"  href="<s:property value="#queryString+ '&amp;'" escape="false"/><s:property value="#PAGE_NUMBER_NAME"/>=<s:property value="#pagination.number+1"/>">
			下一页 &raquo;
		</a> 
	</s:if>
	
	共<s:property value="#pagination.allSize"/>条记录，<s:property value="#pagination.allPage"/>页，
	
	每页显示
	<s:select list="#{'1':'1','5':'5','10':'10','15':' 15','20':'20','50':' 50','100':'100'}" value="%{#pagination.size}"
		onchange="location.href='%{#jumpQS+ '&' + #PAGE_SIZE_NAME +'='}'+this.value"/>
	&nbsp;
</s:if>
</div>

<div class="clear"></div>