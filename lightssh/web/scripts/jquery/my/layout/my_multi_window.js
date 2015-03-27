(function($){
	/**
	 * 多窗口
	 */
	$.fn.mymultiwindows = function( options ) {
	    var opts = $.extend( {}, $.fn.mymultiwindows.defaults, options );
	    
	    this.bind('click',function(){
			var linkUrl = $(this).attr('href');
			var iframeId = linkUrl;
			if( iframeId.indexOf('?') > 0 )
				iframeId = iframeId.substring(0,iframeId.indexOf('?'));
			
			//菜单选项
	     	getMenuHanlder(opts).addTab({"id":iframeId,"title":$(this).text(),"href":linkUrl}) 
				
			var mainBody = $(top.frames[ opts.window_frame ].window.document.body);
			
			var currIframe = $(mainBody).find("iframe."+opts.iframe_class+"[id='"+iframeId+"']");
			
			$(mainBody).find("iframe."+opts.iframe_class).hide();
			if( $(currIframe).length == 0){ //不存在
				currIframe = $("<iframe class='"+opts.iframe_class+"' id='"+iframeId +"'"
						+" src='"+linkUrl+"' "
						+" width='100%' height='100%' "
						+" marginwidth='0' marginheight='0' frameborder='0' border='0' />");
				$(mainBody).append( currIframe );
				
				//iframe加载
				$(currIframe).bind("load",function(){
					var id = $(currIframe).attr("id");
					var uri = $(currIframe).contents().find("meta[name='uri']").attr("content");
					var title = $(currIframe).contents().find("title").text();
					if( id != uri ){
						$(currIframe).attr("id",uri);
						//更改 tab名称
						getMenuHanlder(opts).updateTab({"originalId":id,"newId":uri,"title":title})
					}
				});
			}
			$(currIframe).show(); //显示当前iframe
			
			opts.link();
			
			return false;
		});
	    
	    return new MyMultiWindows( $(this),opts ); //实例化
	}
	
	/**
	 * 构造函数
	 */
	function MyMultiWindows(obj,settings){
	    this._obj = obj;
	    this._settings = settings;
	    //this._color = settings.color;
	}
	
	/**
     * 窗口操作句柄
     */
    function getMenuHanlder( opts ){
    	return top.frames[ opts.menu_frame ][ opts.menu_tab_handler ];
    }
	
	/**
	 * 隐藏所有窗口
	 */
	function hideAllWindow( opts ){
		getBodyOfMainFrame(opts).find("iframe."+opts.iframe_class).hide();
	}
	
	/**
	 * 关闭Tab后，清除内容
	 */
	MyMultiWindows.prototype.afterCloseTab = function ( params ){
		var opts = this._settings;
		try{
			getBodyOfMainFrame(opts).find("iframe[id='"+params.id+"']").remove();
		}catch(exp){
			return false;
		}
		
		return true;
	}
	
	/**
	 * 选中标签，显示内容
	 */
	MyMultiWindows.prototype.afterSelectTab = function ( params ){
		var opts = this._settings;
		
		hideAllWindow(opts);
		getBodyOfMainFrame(opts).find("iframe[id='"+params.id+"']").show();
	}
	
	/**
	 * 主显示区Body
	 */
	function getBodyOfMainFrame( opts ){
		return $(top.frames[ opts.window_frame ].window.document.body);
	}
	
	$.fn.mymultiwindows.defaults = {
		menu_tab_handler:"myMenuTabs"
		,window_frame:"main_frame"
		,menu_frame:"head_frame"
		,iframe_class:"tab"
		,link:function(){}
	};


})(jQuery);