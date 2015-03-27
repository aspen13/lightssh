package com.google.code.lightssh.project.webservice;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 * Web Service Client
 * @author YangXiaojin
 *
 * @param <T>
 */
public class WebserviceClient<T> {
	
    protected Class<T> serviceClass;
    
	public WebserviceClient( Class<T> t ) {
		this.serviceClass = t;
	}
	
	@SuppressWarnings("unchecked")
	protected WebserviceClient() {
		Type type = null;
		if( getClass().getGenericSuperclass() instanceof ParameterizedType)
			type =((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		
		if( type != null && type instanceof Class){
			this.serviceClass = (Class<T>) type;
		}
	}
	
	public T getClient( String wsdl,String namespaceURL,String portName ){
		return this.getClient(wsdl, namespaceURL, portName,false);
	}
	
	public T getClient( String wsdl,String namespaceURL,String portName,boolean mtomEnabled ){
		return this.getClient(wsdl, namespaceURL, portName, mtomEnabled, 0, 0);
	}
	
	public T getClient( String wsdl,String namespaceURL,String portName,long connTimout,long recTimout ){
		return this.getClient(wsdl, namespaceURL, portName,false,connTimout, recTimout);
	}
	
	/**
	 * Web Service客户端
	 * @param wsdl WSDL地址
	 * @param namespaceURL 目标命令空间
	 * @param portName 本地名
	 * @param mtomEnabled MTOM支持，文件传输适用
	 * @param connTimout 连接超时时间
	 * @param recTimout 接收超时时间
	 * @return
	 */
	public T getClient( String wsdl,String namespaceURL,String portName
			,boolean mtomEnabled,long connTimout,long recTimout ){
		
		URL wsdlURL;
		try {
			wsdlURL = new URL( wsdl );
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		
		QName SERVICE_NAME = new QName( namespaceURL , portName);
		Service service = Service.create(wsdlURL,SERVICE_NAME);
		
		T port = service.getPort( serviceClass );
		Binding binding = ((BindingProvider)port).getBinding();
		((SOAPBinding)binding).setMTOMEnabled( mtomEnabled );

		Client client = ClientProxy.getClient(port);
		HTTPConduit http = (HTTPConduit)client.getConduit();

		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		if( connTimout > 0 )
			httpClientPolicy.setConnectionTimeout( connTimout );
		if( recTimout > 0 )
			httpClientPolicy.setReceiveTimeout( recTimout );

		if( recTimout > 0 || connTimout > 0 )
			http.setClient(httpClientPolicy);
		
		return port;
	}

}
