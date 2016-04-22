package com.sec.stp.config;

import java.util.ArrayList;

public class ReqHost {
	//TAPI Servers
		
	public String URL;
	public String proxyHost;
	public int proxyPort;
	public ArrayList <String> proxy = new ArrayList <String>();
	
	public void setURL(String url){
		this.URL = url;
	}
	public void setProxy(String host, int port){
		this.proxy.add(host);
		this.proxy.add(String.valueOf(port));
	}
		
	public String getURL(){
		try{
			return URL;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public ArrayList <String> getProxy(){
		try{
			return proxy;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
