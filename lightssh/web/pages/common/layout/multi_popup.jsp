<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

	<script type="text/javascript">
	</script>

	<s:if test="#parameters['popup_multi_url'][0] != null">
		<iframe id="popup_multi_data_iframe" name="popup_multi_data_iframe"
			style="float: left;width:70%;height: 100%"
			src="<s:url value="%{#parameters['popup_multi_url'][0]}"/>" />
			
		<iframe id="popup_multi_select_iframe" name="popup_multi_select_iframe"
			style="float: left;width:30%;height: 100%"
			src="<s:url value="/multipopupselect.do"/>" >
		</iframe>
	</s:if>
	<s:else>
		<div>多选弹出窗口参数异常。参数值[popup_multi_url]为空！</div>
	</s:else>
