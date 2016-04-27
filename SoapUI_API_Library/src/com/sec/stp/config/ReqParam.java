package com.sec.stp.config;

import java.util.ArrayList;

public class ReqParam {
	
	public ArrayList <String> paramKeys = new ArrayList <String>();
	public ArrayList <String> paramValues = new ArrayList <String>();
		
	
	public void setParams(String key , String value){
		this.paramKeys.add(key);
		this.paramValues.add(value);
		
	}
	public ArrayList <String> getParamKeys(){
		try{
			return paramKeys;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public ArrayList <String> getParamValues(){
		try{
			return paramValues;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
}
