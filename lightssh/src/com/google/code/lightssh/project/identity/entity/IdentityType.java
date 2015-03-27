package com.google.code.lightssh.project.identity.entity;

public enum IdentityType {	
	CLASS("CLASS") 				//class 全路径名 + id		
	,EAN("EAN") 				//国际物品编码协会制定的一种商品用条码，通用于全世界。
	,GOOGLE_ID("GOOGLE ID")		//Google Id
	,ISBN("ISBN")				//ISBN
	,LOC("LOC") 				//Library of Congress
	,SKU("SKU")					//SKU
	,UPCA("UPCA")				//UPCA
	,UPCE("UPCE")				//UPCE
	,OTHER_ID("OTHER_ID");		//Other
	
	private String value;
	
	IdentityType( String value ){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public String toString(){
		return this.value;
	}
}
