package com.google.code.lightssh.project.webservice;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CXF示例
 * @author YangXiaojin
 *
 */
@WebService( targetNamespace="http://lightssh.googlecode.com/webservice",serviceName="helloworld"
	,endpointInterface = "com.google.code.lightssh.project.webservice.CxfExample" )
public class CxfExampleImpl implements CxfExample{
	
	private static Logger log = LoggerFactory.getLogger(CxfExampleImpl.class);
	
	public String sayHello( String username ){
		String reply =" Hello, " + username  + "!";
		System.out.print(reply) ;		
		return reply;
	}
	
	/**
	 * 传输文件
	 * 
	 * 客户端调用示例：
	 * 
	 * WebserviceClient<CxfExample> wsClient = new WebserviceClient<CxfExample>(CxfExample.class);
	 * String wsdl = "http://localhost:8080/lightssh/webservices/hello/world?wsdl";
	 * String namespaceURL = "http://lightssh.googlecode.com/webservice";
	 * String portName = "helloworld";
	 * 
	 * CxfExample client = wsClient.getClient(wsdl, namespaceURL,portName,false,10000000,10000000);
	 * 
	 * //DataSource source = new ByteArrayDataSource(new byte[] {...},"content/type");
	 * DataSource source = new FileDataSource(new File("my/file"));
	 * 
	 * FileUpload fu = new FileUpload();
	 * fu.setDate( source );
	 * fu.setTitle("test.tmp");
	 * 
	 * client.upload( source );
	 * 
	 */
	public boolean upload( BigData fu ){
		if( fu == null || fu.getData() == null )
			return false;
		
		if( "true".equalsIgnoreCase(fu.getIsfile()) ){
			String dir = System.getProperty("java.io.tmpdir");
			File file = new File( dir,fu.getTitle());
			FileOutputStream fos = null;
			
			try{
				fos = new FileOutputStream( file );
				fu.getData().writeTo(fos);
			}catch( IOException e ){
				log.error("WebService上传文件异常：",e);
				return false;
			}finally{
				if( fos != null )
					try{
						fos.close();
					}catch( IOException e ){
						//ignore;
					};
			}
			
			log.info("文件已保存到：{}",file.getPath() );
		}else{
			ByteArrayOutputStream baos = null;
			
			try{
				baos = new ByteArrayOutputStream( );
				fu.getData().writeTo( baos );
				String message = baos.toString( "UTF-8" );
				log.info("接收到的信息是：{}",message );
			}catch( IOException e ){
				log.error("WebService上传文件异常：",e);
				return false;
			}finally{
				if( baos != null )
					try{
						baos.close();
					}catch( IOException e ){
						//ignore;
					};
			}
		}
		
		
		return true;
	}

}
