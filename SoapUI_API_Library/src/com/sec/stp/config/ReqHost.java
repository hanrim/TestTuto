package com.sec.stp.config;

import java.util.ArrayList;

public class ReqHost {
	//TAPI Servers
		
	public String URL;
	public String proxyIp;
	public int proxyPort;
	public ArrayList <String> host = new ArrayList <String>();
	
	public void setURL(String url){
		this.URL = url;
	}
	public void setHost(String URL , String ip, int port){
		this.URL = URL;
		this.host.add(URL);
		this.proxyIp = ip;
		this.host.add(ip);
		this.proxyPort = port;
		this.host.add(String.valueOf(port));
	}
		
	public String getURL(){
		try{
			return URL;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public String getIp(){
		try{
			return proxyIp;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public int getPort(){
		try{
			return proxyPort;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	public ArrayList <String> getHost(){
		try{
			return host;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
