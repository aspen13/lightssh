/**
 * 用法：
 * $(document).ready(function(){
 * 		initVerticalMenu( "my_nav" );
 * })
 */

/**
 * 初始化菜单
 */
function initVerticalMenu( menu_id ){
	addLevelClass( menu_id );
	
	//initExpandOrCollapseStatus();//初始化状态
	
	var ul_elements = $( '#'+menu_id + ' ul');
	$.each( ul_elements , function(index,ul_menu ){
		$(ul_menu).parent().children("a").bind("click",menuExpandOrCollapse );
	});

	menuCookieStatus( ul_elements );
	
	//添加事件，选中菜单的样式
	$.each( $( '#'+menu_id+' li a') , function(index,li_a ){
		$(li_a).bind("click",function(){
				$("#"+menu_id+" a.current").removeClass("current");
				$(this).addClass("current");
			});
		});
}

/**
 * 样式添加,最多支持三级样式
 * @return
 */
function addLevelClass( menu_id ){
	addOneLevelClass( $('#'+menu_id + ' > li > a'),1);//一级菜单
	addOneLevelClass( $('#'+menu_id + ' > li > ul > li > a'),2);//二级菜单
	addOneLevelClass( $('#'+menu_id + ' > li > ul > li > ul > li > a'),3);//三级菜单
}

/**
 * 添加一级样式
 */
function addOneLevelClass( elements,level,last_level ){
	$.each( elements , function(index,ul_menu ){
		var no_child = ($(ul_menu).parent().has("ul").length ==0);
		$(ul_menu).html("<span>&nbsp;</span>" + $(ul_menu).html() );
		
		if( last_level ==3 && no_child){
			$(ul_menu).addClass("level"+last_level);
		}else{
			$(ul_menu).addClass("level"+level);
		}
		
		if( level == 1 || (level==2 && !no_child))
			$(ul_menu).addClass("expand");
	});
}

/**
 * cookie 状态
 */
function menuCookieStatus( ul_elements ){
	var cookie_name = "menu_status"; //TODO
	//根据Cookie初始化菜单展开状态
	var status = $.cookie( cookie_name );
	var ul_id_arr,ul_style_arr;
	if( status != null){
		var ss = status.split(",");
		ul_id_arr = new Array( ss.length );
		ul_style_arr = new Array( ss.length );
		if( ss != null && ss != "" ){
			$.each( ss, function(index,ul_status){
				ul_id_arr[index] = ul_status.split(":")[0];
				ul_style_arr[index] = ul_status.split(":")[1];
			});
		}else if( status.split(":")[0] != ul_id ){
			ul_id_arr = new Array( 1 );
			ul_style_arr = new Array( 1 );
			ul_id_arr[0] = status.split(":")[0];
			ul_style_arr[0] = status.split(":")[1];
		}
	}

	if( ul_id_arr == null )
		return;
	
	$.each( ul_elements , function(index,ul_menu ){
		//根据Cookie初始化菜单展开状态
		var ul_id = $(ul_menu).attr("id");
		for( var i=0;i<ul_style_arr.length;i++){
			if( ul_id == ul_id_arr[i] ){
				var style = ul_style_arr[i]=="expand"?"block":"none";
				$(ul_menu).css("display",style);
				
				var a_ele = $(ul_menu).parent().children("a");
				a_ele.removeClass("expand").removeClass("collapse");
				$(a_ele).addClass( ul_style_arr[i] );
				break;
			}
		}
	});
	
}

/**
 * 菜单的展开与折叠
 */
function menuExpandOrCollapse( ){
	var cookie_name = "menu_status"; //TODO
	
	//隐藏或显示子菜单
	var ul_menu = $(this).parent().children("ul");
	var isExpanded = $(ul_menu).css("display")=="block";
	$(ul_menu).css("display",isExpanded?"none":"block");
	
	//关闭其它打开菜单
	var openedL1 = $("ul").siblings(".level1.expand");
	var openedIsCurrent = $(ul_menu).attr("id")
		==$(openedL1).parent().children("ul").attr("id");//当前为展开
	if( !openedIsCurrent )
		openedIsCurrent = $(ul_menu).parent().parent().attr("id")
		==$(openedL1).parent().children("ul").attr("id");
	var closedUl = null; //关闭的UL
	if( !openedIsCurrent ){
		$(openedL1).removeClass("expand").addClass("collapse");
		$(openedL1).parent().children("ul").css("display","none");
		closedUl = $(openedL1).parent().children("ul").attr("id");
	}
	
	//当前菜单样式
	$(this).removeClass("expand").removeClass("collapse");
	var style = isExpanded?"collapse":"expand";
	$(this).addClass( style );
	
	//记录菜单状态到Cookie
	var id = $(ul_menu).attr("id");
	if( id == null || id == '' ){
		return;
	}
	
	var status = $.cookie( cookie_name );
	var new_status = "";
	if( status != null){
		var ss = status.split(",");
		if( ss != null && ss != "" ){
			$.each( ss, function(index,ul_status){
				var pair = ul_status.split(":");
				if( (pair[0] == id) ){
					//new_status += (new_status!=""?",":"") + ul_status;
					//Cookie其它展开菜单都关闭
					new_status += (new_status!=""?",":"") + pair[0] + (isExpanded?":collapse":":expand");
				}else if( pair[0] == closedUl ){
					new_status += (new_status!=""?",":"") + pair[0] + ":collapse";
				}else{
					new_status += (new_status!=""?",":"") + ul_status;
				}
			});
		}else if( status.split(":")[0] != id ){
			new_status += status;
		}
	}
	
	new_status += (new_status!=""?",":"")+ id + ":" + style;
	var date = new Date();
	date.setTime(date.getTime() + (7 * 24 * 60 * 60 * 1000)); //cookie 超期时间
	$.cookie( cookie_name, new_status ,{expires: date});
}