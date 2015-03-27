package com.google.code.lightssh.project.party.entity;

/**
 * 证件类型
 * @author YangXiaojin
 *
 */
public enum CredentialsType {
	C01("营业执照","工商行政管理机关")
	,C04("临时营业执照","工商行政管理机关")
	,C05("民办非企业登记证书","民政部门")
	,C06("社会团体法人登记证书","民政部门\\工会")
	,C07("主管部门批文","上级部门")
	,C08("事业法人登记证书","编委")
	,C09("组织机构代码证","技术监督部门")
	,C10("部队开户许可证","解放军、武警部队后勤部")
	,C11("国家税务登记证","国家税务局")
	,C12("地方税务登记证","地方税务局")
	,C13("贷款证","人民银行")
	,C14("金融机构许可证","银监局")
	,C99("其他证明文件","公安机关")
	,P01("居民身份证","公安机关")
	,P02("学生证","学校")
	,P03("临时居民身份证","公安机关")
	,P04("军人证","中国人民解放军")
	,P08("武警身份证","中国人民武装警察部队")
	,P16("居民户口簿","公安机关")
	,P18("通行证","公安机关")
	,P19("回乡证","公安机关")
	,P22("监护人证件","公安机关")
	,P23("居住证","公安机关")
	,P24("暂住证","公安机关")
	,P31("护照","公安机关")
	,P41("港澳台居民来往内地通行证", "公安机关")
	,P99("个人其它证件","公安机关");

	CredentialsType( String name,String org ){
		this.name = name;
		this.org = org;
	}
	
	public static final CredentialsType[] frequentlyUsed( ){
		return new CredentialsType[]{
			P01,P03,P04,P08,P16,P31,P99
		};
	}
	
	public static final CredentialsType[] simpleUsed( ){
		return new CredentialsType[]{
				P01, P04, P08,  P31, P41
		};
	}
	
	private String name;
	
	private String org;

	public String getName() {
		return name;
	}

	public String getOrg() {
		return org;
	}
	
	public String toString(){
		return this.name;
	}
}
