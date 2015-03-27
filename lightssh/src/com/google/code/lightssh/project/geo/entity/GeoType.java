package com.google.code.lightssh.project.geo.entity;

/**
 * GEO type
 * @since http://en.wikipedia.org/wiki/ISO_3166-2
 * @author YangXiaojin
 *
 */
public enum GeoType {	
	CONTINENT("大洲")
	,COUNTRY("国家")
	,PROVINCE("省")
	,GOVERNORATE("省")
	,MUNICIPALITY("直辖市")
	,AUTONOMOUS_REGION("自治区")
	,SPECIAL_ADMINISTRATIVE_REGION("特别行政区")
	,CITY("城市")
	,DISTRICT("区")
	,STATE("州")
	,FEDERAL_DISTRICT("联邦区")
	,FEDERAL_DEPENDENCY("联邦依属地")
	,PARISH("教区")
	,REPUBLIC("共和国")
	,REGION("地区")
	,DEPARTMENT("部门")
	,OUTLYING_AREA("边远地区")
	,ISLAND_AND_GROUPS_OF_ISLAND("岛与岛群")
	,GEOGRAPHICAL_REGION("地理区域")
	,SPECIAL_MUNICIPALITIES("直辖市")
	,MUNICIPALITIES("市")
	,ISLAND_COUNCIL("岛议会")
	,TOWN_COUNCIL("镇议会")
	,DIVISION("师")
	,SPECIAL_ADMINISTRATIVE_CITY("特别市")
	,METROPOLITAN_ADMINISTRATION("都市管理")
	,ADMINISTRATIVE_AREA("行政区")
	,COUNTY("县")
	,CAPITAL_TERRITORY("首都直辖区")
	,AUTONOMOUS_DISTRICT("自治区")
	,AUTONOMOUS_CITY("自治城市")
	,ADMINISTRATIVE_REGION("行政区")
	,ADMINISTRATIVE_TERRITORY("行政领土")
	,AUTONOMOUS_PROVINCE("自治省")
	,ADMINISTERED_AREA("管理区")
	,FEDERAL_CAPITAL_TERRITORY("联邦首都区")
	,CONSTITUTIONAL_PROVINCE("宪法省")
	,SPECIAL_TERRITORY("特别领土")
	,ISLAND("岛")
	,ZONE("地带")
	,DEVELOPMENT_REGION("开发区")
	,CAPITAL_DISTRICT("首都区")
	,URBAN_COMMUNITY("城市社区")
	,FEDERAL_TERRITORY("联邦领土")
	,ADMINISTRATIVE_ATOLL("行政环礁")
	,DEPENDENCY("依属地")
	,LOCAL_COUNCIL("地方议会")
	,CHAIN("链")
	,TERRITORIAL_UNIT("领土单元")
	,PREFECTURE("府")
	,SPECIAL_ZONE("特区")
	,METROPOLITAN_CITY("大都会")
	,CAPITAL_METROPOLITAN_CITY("首都国际大都会")
	,SPECIAL_CITY("特别市")
	,CAPITAL_CITY("首府市")
	,GROUP_OF_ISLANDS("岛屿群")
	,UNION_TERRITORY("联盟领土")
	,GEOGRAPHICAL_UNIT("地理单元")
	,CITY_OF_COUNTY_RIGHT("县级市")
	,AUTONOMOUS_SECTOR("自治机构")
	,SELF_GOVERNED_PART("独立区")
	,TWO_TIER_COUNTY("两层县")
	,LONDON_BOROUGH ("伦敦自治市")
	,METROPOLITAN_DISTRICT("大都市区")
	,UNITARY_AUTHORITY("单一的当局")
	,CITY_CORPORATION("市公司")
	,DISTRICT_COUNCIL_AREA("区议会区")
	,COUNCIL_AREA("市政局辖区")
	,OVERSEAS_TERRITORIAL_COLLECTIVITY("海外领土集体")
	,METROPOLITAN_REGION("都市圈")
	,CANTON("州")
	,AUTONOMOUS_REPUBLIC("自治共和国")
	,RAYON("RAYON")
	,ENTITY("ENTITY")
	,FEDERAL_LAND("联邦土地")
	,SALES_TERRITORY("Sales Territory")
	,SERVICE_TERRITORY("Service Territory")
	,TERRITORY("Territory  地域")
	,CHINA_AREA("中国片区"); //如 华东、华南、西南等

	GeoType( String value ){
		this.value = value;
	}
	
	/**
	 * 常用类型
	 */
	public static GeoType[] frequentlyUsed(){
		return new GeoType[]{
			CONTINENT
			,COUNTRY
			,PROVINCE
			,MUNICIPALITY
			,AUTONOMOUS_REGION
			,SPECIAL_ADMINISTRATIVE_REGION
			,CITY
			,DISTRICT
		};
	}
	
	private String value;
	
	public String getValue(){
		return this.value;
	}
	
	public String toString(){
		return this.value;
	}
}
