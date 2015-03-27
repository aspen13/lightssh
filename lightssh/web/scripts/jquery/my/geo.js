
/**
	用法：
	initGeo({'geo_parent_url':'/settings/geo/listcountry.do',
	'geo_children_url':'/settings/geo/listchildren.do',
	'geo_active':'true',
	'geo_selectors':[
		{'name':'xxx.country.code','value':'xxx'},
		{'name':'xxx.xxx.code','value':'xxx'},
		{'name':'xxx.xxx.code','value':'xxx'},
		{'name':'xxx.xxx.code','value':'xxx'}]
	});
 */


	/**
	 * 初始化地理区域
	 */
	function initGeo( param ){
		var selectors = param['geo_selectors'];
		for( var i=0;i<selectors.length;i++){
			var select = $("select[name='"+selectors[i].name+"']");
			showGeo(null,param,i+1,i==0?null:selectors[i-1].value );
			$( select ).val( selectors[i].value );
		}
	}

	/**
	 * 显示地理区域
	 */
	function showGeo(evt,param,level,geo_code ){
		if( param == null )
			return;
		
		//GEO数据获取URL地址
		var geo_url = param['geo_parent_url'];
		if( level != null && level > 1 )
			geo_url = param['geo_children_url'];

		//显示级别
		level = (level==null||level=='')?1:level;

		var selectors = param['geo_selectors'];
		removeChildren(selectors,level);
		
		//当前下拉元素名
		var curr_sel_name = selectors[level-1].name;
		if( curr_sel_name == null || curr_sel_name == '' ){
			return ;
		}
				
		//当前下拉元素
		var geo_select = $("select[name='"+curr_sel_name+"']");
		if( geo_select.length == 0 && ( level-2 >= 0) ){ //
			var parent_geo = $("select[name='"+selectors[level-2].name+"']");
			geo_select = $("<select style='margin-left:0.5em;' name='"+curr_sel_name+"'></select>");
			$(parent_geo).after( geo_select );
		}

		if( (geo_code == null || geo_code == '')&& evt != null ){
			//var eTarget = event.currentTarget?event.currentTarget:event.srcElement;
			var eTarget = (typeof evt.target != 'undefined')?evt.target:evt.srcElement;
			geo_code = $(eTarget).val();
		}
		
		if( (geo_code == null || geo_code == '') && geo_url.indexOf('geo.code') != -1 ){
			//geo_code = geo_url.substring(geo_url.indexOf('geo.code')+'geo.code'.length,geo_url.length);
			geo_code =  decodeURI((RegExp('geo.code' + '=' + '(.+?)(&|$)').exec(geo_url)||[,null])[1]);
			geo_url = geo_url.substring(0,geo_url.indexOf('?'));
		}
		
		var types = param['geo_selectors'][level-1].types ;
		var types_val = '';
		if( types != null )
			for(var i=0;i<types.length;i++ )
				types_val += (i>0?',':'')+types[i] ;
		var data = {'geo.active':param['geo_active'],'geo.code':geo_code,'types':types_val};
		
		$.ajax({
			url: geo_url
			,dataType:"json",type:"post",async:false
			,data:data
	        ,error:function(){  }
	        ,success: function(json){
	        	buildGeoSelect( json.list,geo_select,param,level,param['geo_selectors'][level-1].value );
	        }
		});
	}

	/**
	 * 构造显示元素
	 */
	function buildGeoSelect( list,geo_select,param,level,value ){
		if( list == null ){
			$(geo_select).remove();
			return;
		}
		
		$(geo_select).empty();
		$(geo_select).append( "<option value=''></option>" );
		$.each(list,function(index,item){
				var opt = $("<option value='"+item.code+"'>"+item.name+"</option>");
				$(geo_select).append( opt );
			});
		
		$(geo_select).hide(); //Fixed ie issue
		$(geo_select).show(); //Fixed ie issue
		
		$(geo_select).val(value); //选中
		
		$(geo_select).bind('change',function(event){
			showGeo(event,param,level+1);
		});
	}

	/**
	 * 删除孩子节点
	 */
	function removeChildren( selectors,level ){
		for( var i=level;i<selectors.length;i++ ){
			$("select[name='"+selectors[i].name+"']").remove();
		}
	}