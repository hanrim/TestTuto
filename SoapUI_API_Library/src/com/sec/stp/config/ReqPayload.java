package com.sec.stp.config;
import java.io.File;

import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;


public class ReqPayload {

	public String payloadString;
	public File   payloadFile;
	public String payloadContentType;
	public int    multiSize;
	public Part[] multiFiles;
	
	
	public String getPayloadAsString() throws Exception{
		return payloadString;
	}
	
	public File getPayloadAsFile() throws Exception{
		return payloadFile;
	}
	
	public String getPayloadContentType() throws Exception{
		return payloadContentType;
	}
	
	public void setPayloadAsString(String payload) throws Exception {
		this.payloadString = payload;
	}
	
	public void setPayloadAsFile(String filePath) throws Exception {
		this.payloadFile = new File(filePath);
	}
	
	public void setPayloadMultiSize(int multiSize) throws Exception{
		this.multiSize = multiSize;
		multiFiles = new Part[multiSize];
	}
	
	public void setPayloadMultiFile(int order, String FilePath , String name , String fileName , String contentType , String charSet) throws Exception{
		File file = new File(FilePath);
		this.multiFiles[order] = new FilePart(name,fileName,file,contentType,charSet);
	}
	
	public void setPayloadContentType(String contentType) throws Exception{
		this.payloadContentType = contentType;
	}
	
}
