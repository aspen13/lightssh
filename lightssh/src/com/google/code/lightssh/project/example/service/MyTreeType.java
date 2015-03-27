package com.google.code.lightssh.project.example.service;

public enum MyTreeType {
	
	CONTENT_CATEGORY("内容分类")
	,PRODUCT_CATEGORY_PRICE("商品分类[价格]")
	,PRODUCT_CATEGORY_GEO("商品分类[产地]")
	;
	
	private String value;
	
	MyTreeType( String value ){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public String toString(){
		return this.value;
	}	

}
