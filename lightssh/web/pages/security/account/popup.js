
var popup_login_account = $("<div id='popup_party' title='登录帐户' style='display: none;'></div>");

//$(popup).html('') fixed IE bug
function popupLoginAccount( url ,param){
	var popup = $( popup_login_account );
	
	var buttons = {};
	buttons["关闭"] = function() {$(this).dialog('destroy');};
	if( param !=null && param.multi == 'true' )
		buttons["确认"] = multiPopupConfirm;
	
	$( popup ).dialog({
		resizable: true,modal: true,height:500,width: 800,
		close: function(event, ui) {$(this).dialog('destroy'); },				
		buttons: buttons
	});
	
	$.post(url,param,function(data){$( popup ).html( data );});
}

/**
 * 弹出框回调
 */
function callbackSelectLoginAccount( param ){
	$( popup_login_account ).dialog('destroy').html('');
}

/**
 * 确认选择
 */
function multiPopupConfirm(){
	var json = [];
	$.lightssh.getMultiPopupSelect().find("option").each(function() {
		var item = {
			"key":this.value,"text": this.text
			};
		json.push( item );
	});
	
	//alert( JSON.stringify(json) )
	callbackSelectLoginAccount( json,true );
}
