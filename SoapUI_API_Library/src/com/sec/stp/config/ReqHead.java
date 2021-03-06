package com.sec.stp.config;

import java.util.ArrayList;

public class ReqHead {
	
	public ArrayList <String> headerKeys = new ArrayList <String>();
	public ArrayList <String> headerValues = new ArrayList <String>();
	
	
	public void setHeader(String key , String value){
		this.headerKeys.add(key);
		this.headerValues.add(value);
	}
	public ArrayList <String> getHeaderKeys(){
		try{
			return headerKeys;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public ArrayList <String> getHeaderValues(){
		try{
			return headerValues;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	
	}
	public String getRequestHeader(String keyName){
		try{
			if(headerKeys.contains(keyName) == true){
				int index = headerKeys.indexOf(keyName);
				return headerValues.get(index);
			}else{
				return null;
			}
		}catch(Exception e){
				e.printStackTrace();
				return null;
		}
	}
}
