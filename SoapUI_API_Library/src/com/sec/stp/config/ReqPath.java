package com.sec.stp.config;

public class ReqPath {
	
	public String path ;

	public String getPath(){
		try{
			if(path != null){
				return path;
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public void setPath(String path){
			this.path = path;
	}
}