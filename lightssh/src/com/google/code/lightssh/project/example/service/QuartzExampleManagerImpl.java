package com.google.code.lightssh.project.example.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class QuartzExampleManagerImpl {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd mm:HH:ss");
	
	public void exampleDoIt( ){
		System.out.println("running ... 定时任务示例==[LAST]！" 
				+ sdf.format(Calendar.getInstance().getTime()));
	}

}
