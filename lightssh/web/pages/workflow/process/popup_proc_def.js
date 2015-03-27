
var popup_procdef = $("<div id='procdef_popup' title='流程类型' style='display: none;'></div>");

//$(popup).html('') fixed IE bug
function popupProcDef( url ,param){
	//$( popup ).html( '<div><img id=\'loading\' src=\'<%= request.getContextPath() %>/images/loading.gif\'/>' );
	$( popup_procdef ).dialog({
		resizable: true,modal: true,height:400,width: 700,
		close: function(event, ui) {$(this).dialog('destroy'); },				
		buttons: {				
			"关闭": function() {$(this).dialog('destroy');}
		}
	});
	
	$.post(url,param,function(data){$( popup_procdef ).html( data );});
}

/**
 * 弹出框回调
 */
function callbackSelectProcDef( param ){
	$("#span_procdef_name").html( param.name );
	$("#proc_def_name").val( param.name );
	$("#proc_def_key").val( param.key );
	$( popup_procdef ).dialog('destroy');
}