(function($){
	
	$.fn.mymenutabs = function( options ) {
	    var opts = $.extend( {}, $.fn.mymenutabs.defaults, options );
	 
	 	init(this,opts);//初始化
		
		return new MyMenuTabs( $(this),opts ); //实例化
	};
	
	/**
     * 构造函数
     */
    function MyMenuTabs(obj,settings){
        this._obj = obj;
        this._settings = settings;
        //this._color = settings.color;
    }
    
    /**
     * 初始化
     */
	function init( ultabs,opts ){
		$(ultabs).find("li.tab a").bind('click',function(){
			$(ultabs).find("li.tab").removeClass("active");
			$(this).parent().addClass("active");
			
			opts.activeTab();
		});
		
		$(ultabs).find("li.tab").each(function(index,element){
			//选中标签
			$(element ).bind('click',function(){
				selectTab(element,opts);
			});
		});
	 	
		//关闭按钮
		$(ultabs).find("li.tab span").bind('click',function(){
			$(this).parent().remove();
			
			opts.closeTab();
		});
	}
    
    /**
     * 窗口操作句柄
     */
    function getWindowHanlder( opts ){
    	return top.frames[ opts.window_frame ][ opts.multi_window_handler ];
    }
    
    /**
     * 关闭标签
     */
    function closeTab(li,settings ){
    	//清除Iframe元素
		getWindowHanlder( settings ).afterCloseTab({
			"id":$(li).attr("id"),"href":$(li).attr("href")} );
		
		var nearLi = $(li).next();
		if( nearLi.length == 0 )
			nearLi = $(li).prev();
		
		selectTab(nearLi,settings);//打开临近的
		
		$(li).remove();
    }
    
    /**
     * 选中标签
     */
    function selectTab(li,settings ){
    	$( li ).parent().find("li.tab").removeClass("active");
		$( li ).addClass("active");
		
		//显示iframe
		getWindowHanlder( settings ).afterSelectTab({
			"id":$(li).attr("id"),"href":$(li).attr("href")} );
    }
    
	/** 
	 * 添加菜单 
	 */
    MyMenuTabs.prototype.addTab = function ( params ){
    	var settings = this._settings;
    	var ul = this._obj;
    	var li = $(ul).find("li.tab[id='"+params.id+"']");
    	if( $(li).length ==0 ){ //不存在
	    	var span = $("<span class='close'></span>").html("&times;");
			var a = $("<a href='#'>"+params.title+"</a>");
			li =$("<li class='tab' id='"+params.id+"' href='"+params.href+"'></li>").append(a).append(span);
			$(ul).append( li );
			
			//选中标签
			$(a).bind('click',function(){
				selectTab($(this).parent(),settings);
			});
			
			//关闭标签
			$( span ).bind('click',function(){
				closeTab($(this).parent(),settings );
			});
    	}else{ //存在
    		
    	}
    	
    	//当前元素选中
    	$( ul ).find("li.tab").removeClass("active");
		$( li ).addClass("active");
		
		return this;
	}
    
    /** 
	 * 更新Tab属性 
	 */
    MyMenuTabs.prototype.updateTab = function ( params ){
    	if(params == null || params.originalId == null || params.title == null )
    		return;
    	
    	var settings = this._settings;
    	var ul = this._obj;
    	
    	var li = $( ul ).find("li.tab[id='"+params.originalId+"']");
    	
    	if( params.newId != null )
    		$(li).attr("id",params.newId);
    	
    	$(li).find("a").html( params.title );
    }
    
	$.fn.mymenutabs.defaults = {
		multi_window_handler:"myMultiWindows"
		,window_frame:"navigation_frame"
		,closeTab:function(){}
		,activeTab:function(){}
	    ,max_tab_count: 5
	    ,can_refresh:true
	};

})(jQuery);