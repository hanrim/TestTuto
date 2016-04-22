package com.sec.stp.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.OptionsMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.TraceMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;

import com.sec.stp.config.ReqHead;
import com.sec.stp.config.ReqHost;
import com.sec.stp.config.ReqParam;
import com.sec.stp.config.ReqPath;
import com.sec.stp.config.ReqPayload;
import com.sec.stp.config.ResHead;
import com.sec.stp.config.ResPayload;

public class RequestFactory {

	public String requestHeader = "";
	public String requestParameter = "";
	public String requestBody = "";
	public String responseHeader = "";
	public int    statusCode = 0; 
	
	public ResPayload  resPayload  = new ResPayload();
	public ResHead     resHead     = new ResHead();
	
	public ReqHead     reqHead     = new ReqHead();
	public ReqHost     reqHost     = new ReqHost();
	public ReqPath     reqPath     = new ReqPath();
	public ReqParam    reqParam    = new ReqParam();
	public ReqPayload  reqPayload  = new ReqPayload();

	public ArrayList <String> reqHeadKeys = reqHead.headerKeys;
	public ArrayList <String> reqHeadValues = reqHead.headerValues;
	public ArrayList <String> reqParamKeys = reqParam.paramKeys;
	public ArrayList <String> reqParamValues = reqParam.paramValues;
	public ArrayList <String> proxy         = reqHost.proxy;
	
	public ArrayList <String> resHeadKeys = resHead.headerKeys;
	public ArrayList <String> resHeadValues = resHead.headerValues;
	/**************** This line is added for GitHub Test****************/

	
	public String getRequestLog() throws Exception{
		String log = "";
		log = log+requestParameter+"\r\n";
		log = log+requestHeader+"\r\n";
		log =log+ "\r\n"+requestBody+"\r\n";
		return log;
	}
	public String getResponseLog() throws Exception{
		String log = "";
		log = log+responseHeader+"\r\n";
		return log;
	}
	public int getStatusCode() throws Exception{
		return statusCode;
	}
	public String getResponseHeader(String keyName) throws Exception{
		if(resHeadKeys.contains(keyName) == true){
			int index = resHeadKeys.indexOf(keyName);
			return resHeadValues.get(index);
		}else{
			return null;
		}
	}
	public String getRequestHeader(String keyName) throws Exception{
		if(reqHeadKeys.contains(keyName) == true){
			int index = reqHeadKeys.indexOf(keyName);
			return reqHeadValues.get(index);
		}else{
			return null;
		}
	}
	
	public String generateFile(byte[] byteData , String path){
		String returnMessage = "";
		try{
			File file = new File(path);
			FileOutputStream out = new FileOutputStream(file);
			ByteArrayInputStream in = new ByteArrayInputStream(byteData);
			int nRead = 0;
			byte[] buffer = new byte[1024];
			while((nRead=in.read(buffer,0,buffer.length)) !=-1){
				out.write(buffer,0,nRead);
			}
			returnMessage = "Writing File at "+path+" is success!";
			in.close();
			out.close();
			return returnMessage;
		}catch(Exception e){
			returnMessage = e.toString();
			return returnMessage;
		}
	}
	/**************************************************************************************************
	 * 			
	 *										sendHttpGetAsByte
	 * 
	 *************************************************************************************************/
	/*
	 *	<sendHttpGetAsByte>
	 *	sendHttpGet Response Byte type Get Method 입니다.
	 *	이한림 2015. 07. 17 Fri
	 */
	public byte [] sendHttpGetAsByte(){
		
		byte [] byteData = null;
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod();

		try{
			//Set Proxy
			HostConfiguration config = client.getHostConfiguration();
			if(proxy != null){
			config.setProxy(proxy.get(0), Integer.parseInt(proxy.get(1)));
			}
			proxy = reqHost.proxy;
			//Set Parameter Query
			String ParamQuery = "";
			if(reqParamKeys.isEmpty() == false && reqParamValues.isEmpty() == false){
				for(int i=0; i <reqParamKeys.size(); i++){
					if(i == 0){
						ParamQuery = ParamQuery+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}else{
						ParamQuery = ParamQuery+"&"+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}
				}
			}else{
				ParamQuery = "";
			}
			//Assembly Host address , resourcePath , Parameter Query
			String url = reqHost.URL+reqPath.path+"?"+ParamQuery;
			method.setURI(new URI(url,true));
			requestParameter = "GET "+method.getURI();
				
			//Set Header
			if(reqHeadKeys.isEmpty() == false && reqHeadValues.isEmpty()== false){
				int i = 0;
				while(i == reqHeadKeys.size()){
					method.setRequestHeader(reqHeadKeys.get(i),reqHeadValues.get(i));
				}
			}
			Header[] reqHeaders = method.getRequestHeaders();
			for(int i=0;i<reqHeaders.length;i++){
				requestHeader = requestHeader+reqHeaders[i].getName()+" : "+reqHeaders[i].getValue()+"\r\n";
			}
			reqHeadKeys = reqHead.headerKeys;
			reqHeadValues = reqHead.headerValues;
			
			client.executeMethod(method);
				
			Header[] resHeaders = method.getResponseHeaders();
			responseHeader = responseHeader+method.getStatusLine()+"\r\n";
			for(int i=0;i<resHeaders.length;i++){
					responseHeader = responseHeader+resHeaders[i].getName()+" : "+resHeaders[i].getValue()+"\r\n";
					resHeadKeys.add(resHeaders[i].getName());
					resHeadValues.add(resHeaders[i].getValue());
			}
			statusCode = method.getStatusCode();
			byteData = method.getResponseBody();
			
		}catch(Exception e){
				e.printStackTrace();
		}finally{
				method.releaseConnection();
				reqHead = null;
				reqParam = null;
				reqHost = null;
				reqPath = null;
				resPayload = null;
				resHead = null;
		}
		return byteData;
	}
	/**************************************************************************************************
	 * 			
	 *										sendHttpGetAsString
	 * 
	 *************************************************************************************************/
	/*
	 *	<sendHttpGetAsString>
	 *	sendHttpGet Response String type Get Method 입니다.
	 *	이한림 2015. 07. 17 Fri
	 */
	public String sendHttpGetAsString(){
		
		String returnData = null;
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod();

		try{
			//Set Proxy
			HostConfiguration config = client.getHostConfiguration();
			if(proxy != null){
			config.setProxy(proxy.get(0), Integer.parseInt(proxy.get(1)));
			}
			proxy = reqHost.proxy;
			//Set Parameter Query
			String ParamQuery = "";
			if(reqParamKeys.isEmpty() == false && reqParamValues.isEmpty() == false){
				for(int i=0; i <reqParamKeys.size(); i++){
					if(i == 0){
						ParamQuery = ParamQuery+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}else{
						ParamQuery = ParamQuery+"&"+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}
				}
			}else{
				ParamQuery = "";
			}
			//Assembly Host address , resourcePath , Parameter Query
			String url = reqHost.URL+reqPath.path+"?"+ParamQuery;
			method.setURI(new URI(url,true));
			requestParameter = "GET "+method.getURI();
				
			//Set Header
			if(reqHeadKeys.isEmpty() == false && reqHeadValues.isEmpty()== false){
				int i = 0;
				while(i == reqHeadKeys.size()){
					method.setRequestHeader(reqHeadKeys.get(i),reqHeadValues.get(i));
				}
			}
			Header[] reqHeaders = method.getRequestHeaders();
			for(int i=0;i<reqHeaders.length;i++){
				requestHeader = requestHeader+reqHeaders[i].getName()+" : "+reqHeaders[i].getValue()+"\r\n";
			}
			reqHeadKeys = reqHead.headerKeys;
			resHeadValues = reqHead.headerValues;
			
			client.executeMethod(method);
				
			Header[] resHeaders = method.getResponseHeaders();
			responseHeader = responseHeader+method.getStatusLine()+"\r\n";
			for(int i=0;i<resHeaders.length;i++){
					responseHeader = responseHeader+resHeaders[i].getName()+" : "+resHeaders[i].getValue()+"\r\n";
					resHeadKeys.add(resHeaders[i].getName());
					resHeadValues.add(resHeaders[i].getValue());
			}
			statusCode = method.getStatusCode();
			returnData = method.getResponseBodyAsString();
			
		}catch(Exception e){
				e.printStackTrace();
		}finally{
				method.releaseConnection();
				reqHead = null;
				reqParam = null;
				reqHost = null;
				reqPath = null;
				resPayload = null;
				resHead = null;
		}
		return returnData;
	}
	/**************************************************************************************************
	 * 			
	 *										sendHttpPostAsByte
	 * 
	 *************************************************************************************************/
	/*
	 *	<sendHttpPostAsByte>
	 *	sendHttpPost Response Byte type Post Method 입니다.
	 *	이한림 2015. 07. 17 Fri
	 */
	@SuppressWarnings("deprecation")
	public byte[] sendHttpPostAsByte(){
		
		byte[] byteData = null;
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod();

		try{
			//Set Proxy
			HostConfiguration config = client.getHostConfiguration();
			if(proxy != null){
			config.setProxy(proxy.get(0), Integer.parseInt(proxy.get(1)));
			}
			proxy = reqHost.proxy;
			//Set Parameter Query
			String ParamQuery = "";
			if(reqParamKeys.isEmpty() == false && reqParamValues.isEmpty() == false){
				for(int i=0; i <reqParamKeys.size(); i++){
					if(i == 0){
						ParamQuery = ParamQuery+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}else{
						ParamQuery = ParamQuery+"&"+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}
				}
			}else{
				ParamQuery = "";
			}
			//Assembly Host address , resourcePath , Parameter Query
			String url = reqHost.URL+reqPath.path+"?"+ParamQuery;
			method.setURI(new URI(url,true));
			requestParameter = "POST "+method.getURI();
				
			//Set Header
			if(reqHeadKeys.isEmpty() == false && reqHeadValues.isEmpty()== false){
				for(int i=0;i<reqHeadKeys.size();i++){
					method.setRequestHeader(reqHeadKeys.get(i),reqHeadValues.get(i));
				}
			}
			Header[] reqHeaders = method.getRequestHeaders();
			for(int i=0;i<reqHeaders.length;i++){
				requestHeader = reqHeaders[i].getName()+" : "+reqHeaders[i].getValue()+"\r\n";
			}
			reqHeadKeys = reqHead.headerKeys;
			reqHeadValues = reqHead.headerValues;
			
			//Set Payload
			if(reqPayload.payloadString != null && reqPayload.payloadString instanceof String){
				StringRequestEntity entity = new StringRequestEntity(reqPayload.payloadString);
				method.setRequestEntity(entity);
				requestBody = requestBody+reqPayload.payloadString;
				
			}else if(reqPayload.payloadFile != null && reqPayload.payloadFile.isFile() == true){
				FileRequestEntity entity = new FileRequestEntity(reqPayload.payloadFile,reqPayload.payloadContentType);
				method.setRequestEntity(entity);
				
				FileInputStream in = new FileInputStream(reqPayload.payloadFile);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				
				//Request payload Data print to console
				int nRead = 0;
				byte [] buffer = new byte[1024];
				while((nRead = in.read(buffer,0,buffer.length)) == 1024){
					bos.write(buffer,0,nRead);
				}
				byte[] outputData = bos.toByteArray();
				requestBody = requestBody+new String(outputData,"UTF-8")+"....<Binary Data>....";
				
				in.close();
				bos.close();
				
			}
			
			client.executeMethod(method);
				
			Header[] resHeaders = method.getResponseHeaders();
			responseHeader = responseHeader+method.getStatusLine()+"\r\n";
			for(int i=0;i<resHeaders.length;i++){
					responseHeader = responseHeader+resHeaders[i].getName()+" : "+resHeaders[i].getValue()+"\r\n";
					resHeadKeys.add(resHeaders[i].getName());
					resHeadValues.add(resHeaders[i].getValue());
			}
			
			InputStream in = method.getResponseBodyAsStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			int nRead = 0;
			byte [] buffer = new byte[1024];
			while((nRead = in.read(buffer,0,buffer.length))!= -1){
				bos.write(buffer,0,nRead);
			}
			statusCode = method.getStatusCode();
			byteData = bos.toByteArray();
			in.close();
			bos.close();
			
		}catch(Exception e){
				e.printStackTrace();
		}finally{
				method.releaseConnection();
				reqHead = null;
				reqParam = null;
				reqHost = null;
				reqPath = null;
				resPayload = null;
				resHead = null;
		}
		return byteData;
	}
	/**************************************************************************************************
	 * 			
	 *										sendHttpPostAsString
	 * 
	 *************************************************************************************************/
	/*
	 *	<sendHttpPostAsString>
	 *	sendHttpPost Response String type Post Method 입니다.
	 *	이한림 2015. 07. 17 Fri
	 */
	@SuppressWarnings("deprecation")
	public String sendHttpPostAsString(){
		
		String returnData = null;
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod();

		try{
			//Set Proxy
			HostConfiguration config = client.getHostConfiguration();
			if(proxy != null){
			config.setProxy(proxy.get(0), Integer.parseInt(proxy.get(1)));
			}
			proxy = reqHost.proxy;
			//Set Parameter Query
			String ParamQuery = "";
			if(reqParamKeys.isEmpty() == false && reqParamValues.isEmpty() == false){
				for(int i=0; i <reqParamKeys.size(); i++){
					if(i == 0){
						ParamQuery = ParamQuery+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}else{
						ParamQuery = ParamQuery+"&"+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}
				}
			}else{
				ParamQuery = "";
			}
			//Assembly Host address , resourcePath , Parameter Query
			String url = reqHost.URL+reqPath.path+"?"+ParamQuery;
			method.setURI(new URI(url,true));
			requestParameter = "POST "+method.getURI();
				
			//Set Header
			if(reqHeadKeys.isEmpty() == false && reqHeadValues.isEmpty()== false){
				int i = 0;
				while(i == reqHeadKeys.size()){
					method.setRequestHeader(reqHeadKeys.get(i),reqHeadValues.get(i));
				}
			}
			Header[] reqHeaders = method.getRequestHeaders();
			for(int i=0;i<reqHeaders.length;i++){
				requestHeader = requestHeader+reqHeaders[i].getName()+" : "+reqHeaders[i].getValue()+"\r\n";
			}
			reqHeadKeys = reqHead.headerKeys;
			reqHeadValues = reqHead.headerValues;
			
			//Set Payload
			if(reqPayload.payloadString != null && reqPayload.payloadString instanceof String){
				StringRequestEntity entity = new StringRequestEntity(reqPayload.payloadString);
				method.setRequestEntity(entity);
				requestBody = requestBody+reqPayload.payloadString;
				
			}else if(reqPayload.payloadFile != null && reqPayload.payloadFile.isFile() == true){
				FileRequestEntity entity = new FileRequestEntity(reqPayload.payloadFile,reqPayload.payloadContentType);
				method.setRequestEntity(entity);
				
				FileInputStream in = new FileInputStream(reqPayload.payloadFile);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				
				//Request payload Data print to console
				int nRead = 0;
				byte [] buffer = new byte[1024];
				while((nRead = in.read(buffer,0,buffer.length)) == 1024){
					bos.write(buffer,0,nRead);
				}
				byte[] outputData = bos.toByteArray();
				requestBody = requestBody+new String(outputData,"UTF-8")+"....<Binary Data>....";
				
				in.close();
				bos.close();
				
			}
			client.executeMethod(method);
				
			Header[] resHeaders = method.getResponseHeaders();
			responseHeader = responseHeader+method.getStatusLine()+"\r\n";
			for(int i=0;i<resHeaders.length;i++){
					responseHeader = responseHeader+resHeaders[i].getName()+" : "+resHeaders[i].getValue()+"\r\n";
					resHeadKeys.add(resHeaders[i].getName());
					resHeadValues.add(resHeaders[i].getValue());
			}
			statusCode = method.getStatusCode();
			returnData = method.getResponseBodyAsString();
			
		}catch(Exception e){
				e.printStackTrace();
		}finally{
				method.releaseConnection();
				reqHead = null;
				reqParam = null;
				reqHost = null;
				reqPath = null;
				resPayload = null;
				resHead = null;
		}
		return returnData;
	}
	/**************************************************************************************************
	 * 			
	 *										sendHttpMultiAsByte
	 * 
	 *************************************************************************************************/
	/*
	 *	<sendHttpMultiPart>
	 *	sendHttpMultiPart Response byte type Post Method 입니다.
	 *	이한림 2015. 07. 17 Fri
	 */
	public byte[] sendHttpMultiAsByte(){
		
		byte[] byteData = null;
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod();
	
		try{
			//Set Proxy
			HostConfiguration config = client.getHostConfiguration();
			if(proxy != null){
			config.setProxy(proxy.get(0), Integer.parseInt(proxy.get(1)));
			}
			proxy = reqHost.proxy;
			//Set Parameter Query
			String ParamQuery = "";
			if(reqParamKeys.isEmpty() == false && reqParamValues.isEmpty() == false){
				for(int i=0; i <reqParamKeys.size(); i++){
					if(i == 0){
						ParamQuery = ParamQuery+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}else{
						ParamQuery = ParamQuery+"&"+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}
				}
			}else{
				ParamQuery = "";
			}
			//Assembly Host address , resourcePath , Parameter Query
			String url = reqHost.URL+reqPath.path+"?"+ParamQuery;
			method.setURI(new URI(url,true));
			requestParameter = "POST "+method.getURI();
				
			//Set Header
			method.setRequestHeader("enctype","multipart/form-data");
			if(reqHeadKeys.isEmpty() == false && reqHeadValues.isEmpty()== false){
				for(int i=0;i<reqHeadKeys.size();i++){
					method.setRequestHeader(reqHeadKeys.get(i),reqHeadValues.get(i));
				}
			}
			Header[] reqHeaders = method.getRequestHeaders();
			for(int i=0;i<reqHeaders.length;i++){
				requestHeader = reqHeaders[i].getName()+" : "+reqHeaders[i].getValue()+"\r\n";
			}
			reqHeadKeys = reqHead.headerKeys;
			reqHeadValues = reqHead.headerValues;
			
			MultipartRequestEntity multi = new MultipartRequestEntity(reqPayload.multiFiles,method.getParams());
			method.setRequestEntity(multi);
			
			//Request payload Data print to console
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			multi.writeRequest(out);
			byte[] rawData = out.toByteArray();
			requestBody = requestBody+new String(rawData,"UTF-8")+"....<Binary Data>....";
			
			//Executes httpClient
			client.executeMethod(method);
				
			Header[] resHeaders = method.getResponseHeaders();
			responseHeader = responseHeader+method.getStatusLine()+"\r\n";
			for(int i=0;i<resHeaders.length;i++){
					responseHeader = responseHeader+resHeaders[i].getName()+" : "+resHeaders[i].getValue()+"\r\n";
					resHeadKeys.add(resHeaders[i].getName());
					resHeadValues.add(resHeaders[i].getValue());
			}
			InputStream in = method.getResponseBodyAsStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			int nRead = 0;
			byte [] buffer = new byte[1024];
			while((nRead = in.read(buffer,0,buffer.length))!= -1){
				bos.write(buffer,0,nRead);
			}
			byteData = bos.toByteArray();
			statusCode = method.getStatusCode();
			in.close();
			bos.close();
			
		}catch(Exception e){
				e.printStackTrace();
		}finally{
				method.releaseConnection();
				reqHead = null;
				reqParam = null;
				reqHost = null;
				reqPath = null;
				resPayload = null;
				resHead = null;
		}
		return byteData;
	}
	/**************************************************************************************************
	 * 			
	 *										sendHttpMultiAsString
	 * 
	 *************************************************************************************************/
	/*
	 *	<sendHttpMultiPart>
	 *	sendHttpMultiPart Response String type Post Method 입니다.
	 *	이한림 2015. 07. 17 Fri
	 */
	public String sendHttpMultiAsString(){
		
		String returnData = null;
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod();
	
		try{
			//Set Proxy
			HostConfiguration config = client.getHostConfiguration();
			if(proxy != null){
			config.setProxy(proxy.get(0), Integer.parseInt(proxy.get(1)));
			}
			proxy = reqHost.proxy;
			//Set Parameter Query
			String ParamQuery = "";
			if(reqParamKeys.isEmpty() == false && reqParamValues.isEmpty() == false){
				for(int i=0; i <reqParamKeys.size(); i++){
					if(i == 0){
						ParamQuery = ParamQuery+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}else{
						ParamQuery = ParamQuery+"&"+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}
				}
			}else{
				ParamQuery = "";
			}
			//Assembly Host address , resourcePath , Parameter Query
			String url = reqHost.URL+reqPath.path+"?"+ParamQuery;
			method.setURI(new URI(url,true));
			requestParameter = "POST "+method.getURI();
				
			//Set Header
			method.setRequestHeader("enctype","multipart/form-data");
			if(reqHeadKeys.isEmpty() == false && reqHeadValues.isEmpty()== false){
				for(int i=0;i<reqHeadKeys.size();i++){
					method.setRequestHeader(reqHeadKeys.get(i),reqHeadValues.get(i));
				}
			}
			Header[] reqHeaders = method.getRequestHeaders();
			for(int i=0;i<reqHeaders.length;i++){
				requestHeader = reqHeaders[i].getName()+" : "+reqHeaders[i].getValue()+"\r\n";
			}
			
			reqHeadKeys = reqHead.headerKeys;
			reqHeadValues = reqHead.headerValues;
			
			MultipartRequestEntity multi = new MultipartRequestEntity(reqPayload.multiFiles,method.getParams());
			method.setRequestEntity(multi);
			
			//Request payload Data print to console
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			multi.writeRequest(out);
			byte[] rawData = out.toByteArray();
			requestBody = requestBody+new String(rawData,"UTF-8")+"....<Binary Data>....";
			
			//Executes httpClient
			client.executeMethod(method);
				
			Header[] resHeaders = method.getResponseHeaders();
			responseHeader = responseHeader+method.getStatusLine()+"\r\n";
			for(int i=0;i<resHeaders.length;i++){
					responseHeader = responseHeader+resHeaders[i].getName()+" : "+resHeaders[i].getValue()+"\r\n";
					resHeadKeys.add(resHeaders[i].getName());
					resHeadValues.add(resHeaders[i].getValue());
			}
			
			InputStream in = method.getResponseBodyAsStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			int nRead = 0;
			byte [] buffer = new byte[1024];
			while((nRead = in.read(buffer,0,buffer.length))!= -1){
				bos.write(buffer,0,nRead);
			}
			byte [] byteData = bos.toByteArray();
			returnData = new String(byteData,"UTF-8");
			statusCode = method.getStatusCode();
			in.close();
			bos.close();
			
		}catch(Exception e){
				e.printStackTrace();
		}finally{
				method.releaseConnection();
				reqHead = null;
				reqParam = null;
				reqHost = null;
				reqPath = null;
				resPayload = null;
				resHead = null;
		}
		return returnData;
	}
	/**************************************************************************************************
	 * 			
	 *										sendHttpDeleteAsByte
	 * 
	 *************************************************************************************************/
	/*
	 *	<sendHttpDeleteAsByte>
	 *	sendHttpDelete Response Byte type Delete Method 입니다.
	 *	이한림 2015. 07. 17 Fri
	 */
	public byte [] sendHttpDeleteAsByte(){
		
		byte [] byteData = null;
		HttpClient client = new HttpClient();
		DeleteMethod method = new DeleteMethod();

		try{
			//Set Proxy
			HostConfiguration config = client.getHostConfiguration();
			if(proxy != null){
			config.setProxy(proxy.get(0), Integer.parseInt(proxy.get(1)));
			}
			proxy = reqHost.proxy;	
			//Set Parameter Query
			String ParamQuery = "";
			if(reqParamKeys.isEmpty() == false && reqParamValues.isEmpty() == false){
				for(int i=0; i <reqParamKeys.size(); i++){
					if(i == 0){
						ParamQuery = ParamQuery+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}else{
						ParamQuery = ParamQuery+"&"+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}
				}
			}else{
				ParamQuery = "";
			}
			//Assembly Host address , resourcePath , Parameter Query
			String url = reqHost.URL+reqPath.path+"?"+ParamQuery;
			method.setURI(new URI(url,true));
			requestParameter = "DELETE "+method.getURI();
				
			//Set Header
			if(reqHeadKeys.isEmpty() == false && reqHeadValues.isEmpty()== false){
				int i = 0;
				while(i == reqHeadKeys.size()){
					method.setRequestHeader(reqHeadKeys.get(i),reqHeadValues.get(i));
				}
			}
			Header[] reqHeaders = method.getRequestHeaders();
			for(int i=0;i<reqHeaders.length;i++){
				requestHeader = requestHeader+reqHeaders[i].getName()+" : "+reqHeaders[i].getValue()+"\r\n";
			}
			
			reqHeadKeys = reqHead.headerKeys;
			reqHeadValues = reqHead.headerValues;
			
			client.executeMethod(method);
				
			Header[] resHeaders = method.getResponseHeaders();
			responseHeader = responseHeader+method.getStatusLine()+"\r\n";
			for(int i=0;i<resHeaders.length;i++){
					responseHeader = responseHeader+resHeaders[i].getName()+" : "+resHeaders[i].getValue()+"\r\n";
					resHeadKeys.add(resHeaders[i].getName());
					resHeadValues.add(resHeaders[i].getValue());
			}
			
			statusCode = method.getStatusCode();
			byteData = method.getResponseBody();
			
		}catch(Exception e){
				e.printStackTrace();
		}finally{
				method.releaseConnection();
				reqHead = null;
				reqParam = null;
				reqHost = null;
				reqPath = null;
				resPayload = null;
				resHead = null;
		}
		return byteData;
	}
	/**************************************************************************************************
	 * 			
	 *										sendHttpDeleteAsString
	 * 
	 *************************************************************************************************/
	/*
	 *	<sendHttpDeleteAsString>
	 *	sendHttpDelete Response String type Delete Method 입니다.
	 *	이한림 2015. 07. 17 Fri
	 */
	public String sendHttpDeleteAsString(){
		
		String returnData = null;
		HttpClient client = new HttpClient();
		DeleteMethod method = new DeleteMethod();

		try{
			//Set Proxy
			HostConfiguration config = client.getHostConfiguration();
			if(proxy != null){
			config.setProxy(proxy.get(0), Integer.parseInt(proxy.get(1)));
			}
			proxy = reqHost.proxy;
			
			//Set Parameter Query
			String ParamQuery = "";
			if(reqParamKeys.isEmpty() == false && reqParamValues.isEmpty() == false){
				for(int i=0; i <reqParamKeys.size(); i++){
					if(i == 0){
						ParamQuery = ParamQuery+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}else{
						ParamQuery = ParamQuery+"&"+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}
				}
			}else{
				ParamQuery = "";
			}
			//Assembly Host address , resourcePath , Parameter Query
			String url = reqHost.URL+reqPath.path+"?"+ParamQuery;
			method.setURI(new URI(url,true));
			requestParameter = "DELETE "+method.getURI();
				
			//Set Header
			if(reqHeadKeys.isEmpty() == false && reqHeadValues.isEmpty()== false){
				int i = 0;
				while(i == reqHeadKeys.size()){
					method.setRequestHeader(reqHeadKeys.get(i),reqHeadValues.get(i));
				}
			}
			Header[] reqHeaders = method.getRequestHeaders();
			for(int i=0;i<reqHeaders.length;i++){
				requestHeader = requestHeader+reqHeaders[i].getName()+" : "+reqHeaders[i].getValue()+"\r\n";
			}
			
			reqHeadKeys = reqHead.headerKeys;
			reqHeadValues = reqHead.headerValues;	
			
			client.executeMethod(method);
				
			Header[] resHeaders = method.getResponseHeaders();
			responseHeader = responseHeader+method.getStatusLine()+"\r\n";
			for(int i=0;i<resHeaders.length;i++){
					responseHeader = responseHeader+resHeaders[i].getName()+" : "+resHeaders[i].getValue()+"\r\n";
			}
			statusCode = method.getStatusCode();
			returnData = method.getResponseBodyAsString();
			
		}catch(Exception e){
				e.printStackTrace();
		}finally{
				method.releaseConnection();
				reqHead = null;
				reqParam = null;
				reqHost = null;
				reqPath = null;
				resPayload = null;
				resHead = null;
		}
		return returnData;
	}
	/**************************************************************************************************
	 * 			
	 *										sendHttpPutAsByte
	 * 
	 *************************************************************************************************/
	/*
	 *	<sendHttpPutAsByte>
	 *	sendHttpPut Response Byte type Put Method 입니다.
	 *	이한림 2015. 07. 17 Fri
	 */
	@SuppressWarnings("deprecation")
	public byte[] sendHttpPutAsByte(){
		
		byte[] byteData = null;
		HttpClient client = new HttpClient();
		PutMethod method = new PutMethod();

		try{
			//Set Proxy
			HostConfiguration config = client.getHostConfiguration();
			if(proxy != null){
			config.setProxy(proxy.get(0), Integer.parseInt(proxy.get(1)));
			}
			proxy = reqHost.proxy;
			//Set Parameter Query
			String ParamQuery = "";
			if(reqParamKeys.isEmpty() == false && reqParamValues.isEmpty() == false){
				for(int i=0; i <reqParamKeys.size(); i++){
					if(i == 0){
						ParamQuery = ParamQuery+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}else{
						ParamQuery = ParamQuery+"&"+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}
				}
			}else{
				ParamQuery = "";
			}
			//Assembly Host address , resourcePath , Parameter Query
			String url = reqHost.URL+reqPath.path+"?"+ParamQuery;
			method.setURI(new URI(url,true));
			requestParameter = "PUT "+method.getURI();
				
			//Set Header
			if(reqHeadKeys.isEmpty() == false && reqHeadValues.isEmpty()== false){
				for(int i=0;i<reqHeadKeys.size();i++){
					method.setRequestHeader(reqHeadKeys.get(i),reqHeadValues.get(i));
				}
			}
			Header[] reqHeaders = method.getRequestHeaders();
			for(int i=0;i<reqHeaders.length;i++){
				requestHeader = reqHeaders[i].getName()+" : "+reqHeaders[i].getValue()+"\r\n";
			}
			
			reqHeadKeys = reqHead.headerKeys;
			reqHeadValues = reqHead.headerValues;
			
			//Set Payload
			if(reqPayload.payloadString != null && reqPayload.payloadString instanceof String){
				StringRequestEntity entity = new StringRequestEntity(reqPayload.payloadString);
				method.setRequestEntity(entity);
				requestBody = requestBody+reqPayload.payloadString;
				
			}else if(reqPayload.payloadFile != null && reqPayload.payloadFile.isFile() == true){
				FileRequestEntity entity = new FileRequestEntity(reqPayload.payloadFile,reqPayload.payloadContentType);
				method.setRequestEntity(entity);
				
				FileInputStream in = new FileInputStream(reqPayload.payloadFile);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				
				//Request payload Data print to console
				int nRead = 0;
				byte [] buffer = new byte[1024];
				while((nRead = in.read(buffer,0,buffer.length)) == 1024){
					bos.write(buffer,0,nRead);
				}
				byte[] outputData = bos.toByteArray();
				requestBody = requestBody+new String(outputData,"UTF-8")+"....<Binary Data>....";
				
				in.close();
				bos.close();
				
			}
			
			client.executeMethod(method);
				
			Header[] resHeaders = method.getResponseHeaders();
			responseHeader = responseHeader+method.getStatusLine()+"\r\n";
			for(int i=0;i<resHeaders.length;i++){
					responseHeader = responseHeader+resHeaders[i].getName()+" : "+resHeaders[i].getValue()+"\r\n";
					resHeadKeys.add(resHeaders[i].getName());
					resHeadValues.add(resHeaders[i].getValue());
			}
			
			InputStream in = method.getResponseBodyAsStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			int nRead = 0;
			byte [] buffer = new byte[1024];
			while((nRead = in.read(buffer,0,buffer.length))!= -1){
				bos.write(buffer,0,nRead);
			}
			statusCode = method.getStatusCode();
			byteData = bos.toByteArray();
			in.close();
			bos.close();
			
		}catch(Exception e){
				e.printStackTrace();
		}finally{
				method.releaseConnection();
				reqHead = null;
				reqParam = null;
				reqHost = null;
				reqPath = null;
				resPayload = null;
				resHead = null;
		}
		return byteData;
	}
	
	/**************************************************************************************************
	 * 			
	 *										sendHttpPutAsString
	 * 
	 *************************************************************************************************/
	/*
	 *	<sendHttpPutAsString>
	 *	sendHttput Response String type Put Method 입니다.
	 *	이한림 2015. 07. 17 Fri
	 */
	@SuppressWarnings("deprecation")
	public String sendHttpPutAsString(){
		
		String returnData = null;
		HttpClient client = new HttpClient();
		PutMethod method = new PutMethod();

		try{
			//Set Proxy
			HostConfiguration config = client.getHostConfiguration();
			if(proxy != null){
			config.setProxy(proxy.get(0), Integer.parseInt(proxy.get(1)));
			}
			proxy = reqHost.proxy;
			
			//Set Parameter Query
			String ParamQuery = "";
			if(reqParamKeys.isEmpty() == false && reqParamValues.isEmpty() == false){
				for(int i=0; i <reqParamKeys.size(); i++){
					if(i == 0){
						ParamQuery = ParamQuery+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}else{
						ParamQuery = ParamQuery+"&"+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}
				}
			}else{
				ParamQuery = "";
			}
			//Assembly Host address , resourcePath , Parameter Query
			String url = reqHost.URL+reqPath.path+"?"+ParamQuery;
			method.setURI(new URI(url,true));
			requestParameter = "PUT "+method.getURI();
				
			//Set Header
			if(reqHeadKeys.isEmpty() == false && reqHeadValues.isEmpty()== false){
				int i = 0;
				while(i == reqHeadKeys.size()){
					method.setRequestHeader(reqHeadKeys.get(i),reqHeadValues.get(i));
				}
			}
			Header[] reqHeaders = method.getRequestHeaders();
			for(int i=0;i<reqHeaders.length;i++){
				requestHeader = requestHeader+reqHeaders[i].getName()+" : "+reqHeaders[i].getValue()+"\r\n";
			}
			
			reqHeadKeys = reqHead.headerKeys;
			reqHeadValues = reqHead.headerValues;
			
			//Set Payload
			if(reqPayload.payloadString != null && reqPayload.payloadString instanceof String){
				StringRequestEntity entity = new StringRequestEntity(reqPayload.payloadString);
				method.setRequestEntity(entity);
				requestBody = requestBody+reqPayload.payloadString;
				
			}else if(reqPayload.payloadFile != null && reqPayload.payloadFile.isFile() == true){
				FileRequestEntity entity = new FileRequestEntity(reqPayload.payloadFile,reqPayload.payloadContentType);
				method.setRequestEntity(entity);
				
				FileInputStream in = new FileInputStream(reqPayload.payloadFile);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				
				//Request payload Data print to console
				int nRead = 0;
				byte [] buffer = new byte[1024];
				while((nRead = in.read(buffer,0,buffer.length)) == 1024){
					bos.write(buffer,0,nRead);
				}
				byte[] outputData = bos.toByteArray();
				requestBody = requestBody+new String(outputData,"UTF-8")+"....<Binary Data>....";
				
				in.close();
				bos.close();
				
			}
			client.executeMethod(method);
				
			Header[] resHeaders = method.getResponseHeaders();
			responseHeader = responseHeader+method.getStatusLine()+"\r\n";
			for(int i=0;i<resHeaders.length;i++){
					responseHeader = responseHeader+resHeaders[i].getName()+" : "+resHeaders[i].getValue()+"\r\n";
					resHeadKeys.add(resHeaders[i].getName());
					resHeadValues.add(resHeaders[i].getValue());
			}
			
			statusCode = method.getStatusCode();
			returnData = method.getResponseBodyAsString();
			
		}catch(Exception e){
				e.printStackTrace();
		}finally{
				method.releaseConnection();
				reqHead = null;
				reqParam = null;
				reqHost = null;
				reqPath = null;
				resPayload = null;
				resHead = null;
		}
		return returnData;
	}
	/**************************************************************************************************
	 * 			
	 *										sendHttpGetAsByte
	 * 
	 *************************************************************************************************/
	/*
	 *	<sendHttpGetAsByte>
	 *	sendHttpGet Response Byte type Get Method 입니다.
	 *	이한림 2015. 07. 17 Fri
	 */
	public byte [] sendHttpOptionsAsByte(){
		
		byte [] byteData = null;
		HttpClient client = new HttpClient();
		OptionsMethod method = new OptionsMethod();
	
		try{
			//Set Proxy
			HostConfiguration config = client.getHostConfiguration();
			if(proxy != null){
			config.setProxy(proxy.get(0), Integer.parseInt(proxy.get(1)));
			}
			proxy = reqHost.proxy;
			
			//Set Parameter Query
			String ParamQuery = "";
			if(reqParamKeys.isEmpty() == false && reqParamValues.isEmpty() == false){
				for(int i=0; i <reqParamKeys.size(); i++){
					if(i == 0){
						ParamQuery = ParamQuery+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}else{
						ParamQuery = ParamQuery+"&"+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}
				}
			}else{
				ParamQuery = "";
			}
			//Assembly Host address , resourcePath , Parameter Query
			String url = reqHost.URL+reqPath.path+"?"+ParamQuery;
			method.setURI(new URI(url,true));
			requestParameter = "OPTIONS "+method.getURI();
				
			//Set Header
			if(reqHeadKeys.isEmpty() == false && reqHeadValues.isEmpty()== false){
				int i = 0;
				while(i == reqHeadKeys.size()){
					method.setRequestHeader(reqHeadKeys.get(i),reqHeadValues.get(i));
				}
			}
			Header[] reqHeaders = method.getRequestHeaders();
			for(int i=0;i<reqHeaders.length;i++){
				requestHeader = requestHeader+reqHeaders[i].getName()+" : "+reqHeaders[i].getValue()+"\r\n";
			}
			reqHeadKeys = reqHead.headerKeys;
			reqHeadValues = reqHead.headerValues;
			
			client.executeMethod(method);
				
			Header[] resHeaders = method.getResponseHeaders();
			responseHeader = responseHeader+method.getStatusLine()+"\r\n";
			for(int i=0;i<resHeaders.length;i++){
					responseHeader = responseHeader+resHeaders[i].getName()+" : "+resHeaders[i].getValue()+"\r\n";
					resHeadKeys.add(resHeaders[i].getName());
					resHeadValues.add(resHeaders[i].getValue());
			}
			statusCode = method.getStatusCode();
			byteData = method.getResponseBody();
			
		}catch(Exception e){
				e.printStackTrace();
		}finally{
				method.releaseConnection();
				reqHead = null;
				reqParam = null;
				reqHost = null;
				reqPath = null;
				resPayload = null;
				resHead = null;
		}
		return byteData;
	}
	/**************************************************************************************************
	 * 			
	 *										sendHttpGetAsString
	 * 
	 *************************************************************************************************/
	/*
	 *	<sendHttpGetAsString>
	 *	sendHttpGet Response String type Get Method 입니다.
	 *	이한림 2015. 07. 17 Fri
	 */
	public String sendHttpOptionsAsString(){
		
		String returnData = null;
		HttpClient client = new HttpClient();
		OptionsMethod method = new OptionsMethod();
	
		try{
			//Set Proxy
			HostConfiguration config = client.getHostConfiguration();
			if(proxy != null){
			config.setProxy(proxy.get(0), Integer.parseInt(proxy.get(1)));
			}
			proxy = reqHost.proxy;
			//Set Parameter Query
			String ParamQuery = "";
			if(reqParamKeys.isEmpty() == false && reqParamValues.isEmpty() == false){
				for(int i=0; i <reqParamKeys.size(); i++){
					if(i == 0){
						ParamQuery = ParamQuery+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}else{
						ParamQuery = ParamQuery+"&"+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}
				}
			}else{
				ParamQuery = "";
			}
			//Assembly Host address , resourcePath , Parameter Query
			String url = reqHost.URL+reqPath.path+"?"+ParamQuery;
			method.setURI(new URI(url,true));
			requestParameter = "OPTIONS "+method.getURI();
				
			//Set Header
			if(reqHeadKeys.isEmpty() == false && reqHeadValues.isEmpty()== false){
				int i = 0;
				while(i == reqHeadKeys.size()){
					method.setRequestHeader(reqHeadKeys.get(i),reqHeadValues.get(i));
				}
			}
			Header[] reqHeaders = method.getRequestHeaders();
			for(int i=0;i<reqHeaders.length;i++){
				requestHeader = requestHeader+reqHeaders[i].getName()+" : "+reqHeaders[i].getValue()+"\r\n";
			}
			reqHeadKeys = reqHead.headerKeys;
			resHeadValues = reqHead.headerValues;
			
			client.executeMethod(method);
				
			Header[] resHeaders = method.getResponseHeaders();
			responseHeader = responseHeader+method.getStatusLine()+"\r\n";
			for(int i=0;i<resHeaders.length;i++){
					responseHeader = responseHeader+resHeaders[i].getName()+" : "+resHeaders[i].getValue()+"\r\n";
					resHeadKeys.add(resHeaders[i].getName());
					resHeadValues.add(resHeaders[i].getValue());
			}
			statusCode = method.getStatusCode();
			returnData = method.getResponseBodyAsString();
			
		}catch(Exception e){
				e.printStackTrace();
		}finally{
				method.releaseConnection();
				reqHead = null;
				reqParam = null;
				reqHost = null;
				reqPath = null;
				resPayload = null;
				resHead = null;
		}
		return returnData;
	}
	/**************************************************************************************************
	 * 			
	 *										sendHttpGetAsByte
	 * 
	 *************************************************************************************************/
	/*
	 *	<sendHttpGetAsByte>
	 *	sendHttpGet Response Byte type Get Method 입니다.
	 *	이한림 2015. 07. 17 Fri
	 */
	public byte [] sendHttpHeadAsByte(){
		
		byte [] byteData = null;
		HttpClient client = new HttpClient();
		HeadMethod method = new HeadMethod();
	
		try{
			//Set Proxy
			HostConfiguration config = client.getHostConfiguration();
			if(proxy != null){
			config.setProxy(proxy.get(0), Integer.parseInt(proxy.get(1)));
			}
			proxy = reqHost.proxy;
			
			//Set Parameter Query
			String ParamQuery = "";
			if(reqParamKeys.isEmpty() == false && reqParamValues.isEmpty() == false){
				for(int i=0; i <reqParamKeys.size(); i++){
					if(i == 0){
						ParamQuery = ParamQuery+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}else{
						ParamQuery = ParamQuery+"&"+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}
				}
			}else{
				ParamQuery = "";
			}
			//Assembly Host address , resourcePath , Parameter Query
			String url = reqHost.URL+reqPath.path+"?"+ParamQuery;
			method.setURI(new URI(url,true));
			requestParameter = "HEAD "+method.getURI();
				
			//Set Header
			if(reqHeadKeys.isEmpty() == false && reqHeadValues.isEmpty()== false){
				int i = 0;
				while(i == reqHeadKeys.size()){
					method.setRequestHeader(reqHeadKeys.get(i),reqHeadValues.get(i));
				}
			}
			Header[] reqHeaders = method.getRequestHeaders();
			for(int i=0;i<reqHeaders.length;i++){
				requestHeader = requestHeader+reqHeaders[i].getName()+" : "+reqHeaders[i].getValue()+"\r\n";
			}
			reqHeadKeys = reqHead.headerKeys;
			reqHeadValues = reqHead.headerValues;
			
			client.executeMethod(method);
				
			Header[] resHeaders = method.getResponseHeaders();
			responseHeader = responseHeader+method.getStatusLine()+"\r\n";
			for(int i=0;i<resHeaders.length;i++){
					responseHeader = responseHeader+resHeaders[i].getName()+" : "+resHeaders[i].getValue()+"\r\n";
					resHeadKeys.add(resHeaders[i].getName());
					resHeadValues.add(resHeaders[i].getValue());
			}
			statusCode = method.getStatusCode();
			byteData = method.getResponseBody();
			
		}catch(Exception e){
				e.printStackTrace();
		}finally{
				method.releaseConnection();
				reqHead = null;
				reqParam = null;
				reqHost = null;
				reqPath = null;
				resPayload = null;
				resHead = null;
		}
		return byteData;
	}
	/**************************************************************************************************
	 * 			
	 *										sendHttpGetAsString
	 * 
	 *************************************************************************************************/
	/*
	 *	<sendHttpGetAsString>
	 *	sendHttpGet Response String type Get Method 입니다.
	 *	이한림 2015. 07. 17 Fri
	 */
	public String sendHttpHeadAsString(){
		
		String returnData = null;
		HttpClient client = new HttpClient();
		HeadMethod method = new HeadMethod();
	
		try{
			//Set Proxy
			HostConfiguration config = client.getHostConfiguration();
			if(proxy != null){
			config.setProxy(proxy.get(0), Integer.parseInt(proxy.get(1)));
			}
			proxy = reqHost.proxy;
			//Set Parameter Query
			String ParamQuery = "";
			if(reqParamKeys.isEmpty() == false && reqParamValues.isEmpty() == false){
				for(int i=0; i <reqParamKeys.size(); i++){
					if(i == 0){
						ParamQuery = ParamQuery+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}else{
						ParamQuery = ParamQuery+"&"+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}
				}
			}else{
				ParamQuery = "";
			}
			//Assembly Host address , resourcePath , Parameter Query
			String url = reqHost.URL+reqPath.path+"?"+ParamQuery;
			method.setURI(new URI(url,true));
			requestParameter = "HEAD "+method.getURI();
				
			//Set Header
			if(reqHeadKeys.isEmpty() == false && reqHeadValues.isEmpty()== false){
				int i = 0;
				while(i == reqHeadKeys.size()){
					method.setRequestHeader(reqHeadKeys.get(i),reqHeadValues.get(i));
				}
			}
			Header[] reqHeaders = method.getRequestHeaders();
			for(int i=0;i<reqHeaders.length;i++){
				requestHeader = requestHeader+reqHeaders[i].getName()+" : "+reqHeaders[i].getValue()+"\r\n";
			}
			reqHeadKeys = reqHead.headerKeys;
			resHeadValues = reqHead.headerValues;
			
			client.executeMethod(method);
				
			Header[] resHeaders = method.getResponseHeaders();
			responseHeader = responseHeader+method.getStatusLine()+"\r\n";
			for(int i=0;i<resHeaders.length;i++){
					responseHeader = responseHeader+resHeaders[i].getName()+" : "+resHeaders[i].getValue()+"\r\n";
					resHeadKeys.add(resHeaders[i].getName());
					resHeadValues.add(resHeaders[i].getValue());
			}
			statusCode = method.getStatusCode();
			returnData = method.getResponseBodyAsString();
			
		}catch(Exception e){
				e.printStackTrace();
		}finally{
				method.releaseConnection();
				reqHead = null;
				reqParam = null;
				reqHost = null;
				reqPath = null;
				resPayload = null;
				resHead = null;
		}
		return returnData;
	}
	/**************************************************************************************************
	 * 			
	 *										sendHttpGetAsByte
	 * 
	 *************************************************************************************************/
	/*
	 *	<sendHttpGetAsByte>
	 *	sendHttpGet Response Byte type Get Method 입니다.
	 *	이한림 2015. 07. 17 Fri
	 */
	public byte [] sendHttpTraceAsByte(){
		
		byte [] byteData = null;
		HttpClient client = new HttpClient();
	
		try{
			//Set Proxy
			HostConfiguration config = client.getHostConfiguration();
			if(proxy != null){
			config.setProxy(proxy.get(0), Integer.parseInt(proxy.get(1)));
			}
			proxy = reqHost.proxy;
			
			//Set Parameter Query
			String ParamQuery = "";
			if(reqParamKeys.isEmpty() == false && reqParamValues.isEmpty() == false){
				for(int i=0; i <reqParamKeys.size(); i++){
					if(i == 0){
						ParamQuery = ParamQuery+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}else{
						ParamQuery = ParamQuery+"&"+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}
				}
			}else{
				ParamQuery = "";
			}
			//Assembly Host address , resourcePath , Parameter Query
			String url = reqHost.URL+reqPath.path+"?"+ParamQuery;
			TraceMethod method = new TraceMethod(url);
			method.setURI(new URI(url,true));
			requestParameter = "TRACE "+method.getURI();
				
			//Set Header
			if(reqHeadKeys.isEmpty() == false && reqHeadValues.isEmpty()== false){
				int i = 0;
				while(i == reqHeadKeys.size()){
					method.setRequestHeader(reqHeadKeys.get(i),reqHeadValues.get(i));
				}
			}
			Header[] reqHeaders = method.getRequestHeaders();
			for(int i=0;i<reqHeaders.length;i++){
				requestHeader = requestHeader+reqHeaders[i].getName()+" : "+reqHeaders[i].getValue()+"\r\n";
			}
			reqHeadKeys = reqHead.headerKeys;
			reqHeadValues = reqHead.headerValues;
			
			client.executeMethod(method);
				
			Header[] resHeaders = method.getResponseHeaders();
			responseHeader = responseHeader+method.getStatusLine()+"\r\n";
			for(int i=0;i<resHeaders.length;i++){
					responseHeader = responseHeader+resHeaders[i].getName()+" : "+resHeaders[i].getValue()+"\r\n";
					resHeadKeys.add(resHeaders[i].getName());
					resHeadValues.add(resHeaders[i].getValue());
			}
			statusCode = method.getStatusCode();
			byteData = method.getResponseBody();
			method.releaseConnection();
			
		}catch(Exception e){
				e.printStackTrace();
		}finally{
				reqHead = null;
				reqParam = null;
				reqHost = null;
				reqPath = null;
				resPayload = null;
				resHead = null;
		}
		return byteData;
	}
	/**************************************************************************************************
	 * 			
	 *										sendHttpGetAsString
	 * 
	 *************************************************************************************************/
	/*
	 *	<sendHttpGetAsString>
	 *	sendHttpGet Response String type Get Method 입니다.
	 *	이한림 2015. 07. 17 Fri
	 */
	public String sendHttpTraceAsString(){
		
		String returnData = null;
		HttpClient client = new HttpClient();
	
		try{
			//Set Proxy
			HostConfiguration config = client.getHostConfiguration();
			if(proxy != null){
			config.setProxy(proxy.get(0), Integer.parseInt(proxy.get(1)));
			}
			proxy = reqHost.proxy;
			//Set Parameter Query
			String ParamQuery = "";
			if(reqParamKeys.isEmpty() == false && reqParamValues.isEmpty() == false){
				for(int i=0; i <reqParamKeys.size(); i++){
					if(i == 0){
						ParamQuery = ParamQuery+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}else{
						ParamQuery = ParamQuery+"&"+reqParamKeys.get(i)+"="+reqParamValues.get(i);
					}
				}
			}else{
				ParamQuery = "";
			}
			//Assembly Host address , resourcePath , Parameter Query
			String url = reqHost.URL+reqPath.path+"?"+ParamQuery;
			TraceMethod method = new TraceMethod(url);
			method.setURI(new URI(url,true));
			requestParameter = "TRACE "+method.getURI();
				
			//Set Header
			if(reqHeadKeys.isEmpty() == false && reqHeadValues.isEmpty()== false){
				int i = 0;
				while(i == reqHeadKeys.size()){
					method.setRequestHeader(reqHeadKeys.get(i),reqHeadValues.get(i));
				}
			}
			Header[] reqHeaders = method.getRequestHeaders();
			for(int i=0;i<reqHeaders.length;i++){
				requestHeader = requestHeader+reqHeaders[i].getName()+" : "+reqHeaders[i].getValue()+"\r\n";
			}
			reqHeadKeys = reqHead.headerKeys;
			resHeadValues = reqHead.headerValues;
			
			client.executeMethod(method);
				
			Header[] resHeaders = method.getResponseHeaders();
			responseHeader = responseHeader+method.getStatusLine()+"\r\n";
			for(int i=0;i<resHeaders.length;i++){
					responseHeader = responseHeader+resHeaders[i].getName()+" : "+resHeaders[i].getValue()+"\r\n";
					resHeadKeys.add(resHeaders[i].getName());
					resHeadValues.add(resHeaders[i].getValue());
			}
			statusCode = method.getStatusCode();
			returnData = method.getResponseBodyAsString();
			method.releaseConnection();
			
		}catch(Exception e){
				e.printStackTrace();
		}finally{
				reqHead = null;
				reqParam = null;
				reqHost = null;
				reqPath = null;
				resPayload = null;
				resHead = null;
		}
		return returnData;
	}
}
			



/*Device Account API 연동*/			
//	public String[]  getDeviceAccount(String urlStr, String User_Agent, String contentType, String Authorization, String device_id) throws Exception {
//			
//		String query = "/account/v1/devices/"+device_id+"/register";
//			
//			URL url = new URL(urlStr + query);
//			System.out.println("[DeviceAccount Request ]"+"\n"+"POST "+ url.toString());
//	
//			//	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("168.219.61.252", 8080));
//			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("70.10.15.10", 8080));
//			
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
//			//	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestMethod("POST");
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//			conn.setUseCaches(false);
//			conn.setAllowUserInteraction(false);
//	
//			conn.setRequestProperty("Authorization", Authorization);
//			conn.setRequestProperty("Content-Type",	contentType);
//			conn.setRequestProperty("User-Agent", User_Agent);
//			
//			System.out.println("Authorization : "+ Authorization);
//			System.out.println("Content-Type: "+ contentType);
//			System.out.println("User-Agent: "+User_Agent);
//			
//			
//			// Buffer the result into a string
//			System.out.println("\n"+"[DeviceAccount Response]");
//			//System.out.println("---Start of Header---");
//			System.out.println(conn.getHeaderField(null));
//			responseHeader.responseCode = conn.getResponseCode();
//			System.out.println("Content-Type: "+conn.getContentType());
//			responseHeader.ContentType = conn.getContentType();		
//			System.out.println("Date: "+ conn.getHeaderField("Date"));
//			System.out.println("Server: "+ conn.getHeaderField("Server"));
//			System.out.println("Content-Length: "+conn.getHeaderField("Content-Length"));
//			responseHeader.contentLength = Integer.parseInt(conn.getHeaderField("Content-Length"));
//			System.out.println("Proxy-Connection: "+ conn.getHeaderField("Proxy-Connection"));
//			System.out.println("Connection: "+ conn.getHeaderField("Connection"));
////			System.out.println("Content-Encoding: "+conn.getContentEncoding());
//			responseHeader.charSet = conn.getContentEncoding();
//			
//			
//			
//			//System.out.println(conn.getHeaderFields().toString());
//			JSONParser parser = new JSONParser();
//			//System.out.println("\n"+"---End of Header---");
//			
//			
//			if (conn.getResponseCode() != 200) {
//				int nRead;
//				byte[] data = new byte[16384];
//				InputStream is = conn.getErrorStream();
//				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//				while ((nRead = is.read(data, 0, data.length)) != -1) {
//					buffer.write(data, 0, nRead);
//				}
//	
//				buffer.flush();
//				
//				byte[] byteData = buffer.toByteArray();
//				
//				
//				String jsonStringBody = new String(byteData, charset);
//				System.out.println("PlantextData: "+jsonStringBody);
//				Object obj = parser.parse(jsonStringBody);
//				JSONObject jsonObject = (JSONObject) obj;
//				thisResponse.rcode = (long) jsonObject.get("rcode");
//				thisResponse.rmsg = (String) jsonObject.get("rmsg");
//				
//				//throw new IOException(conn.getResponseMessage());
//				
//				
//			} else {
//				int nRead;
//				byte[] data = new byte[16384];
//	
//				InputStream is = conn.getInputStream();
//	
//				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//				while ((nRead = is.read(data, 0, data.length)) != -1) {
//					buffer.write(data, 0, nRead);
//				}
//	
//				buffer.flush();
//				
//				byte[] byteData = buffer.toByteArray();
//				
//				String jsonStringBody = new String(byteData, charset);
//				System.out.println("PlantextData: "+jsonStringBody);
//			
//				Object obj =parser.parse(jsonStringBody);
//				JSONObject jsonObject = (JSONObject) obj;
//				
//				String access_token = (String) jsonObject.get("access_token");
//				String uid = (String) jsonObject.get("uid");
//				
//				String[] Account = { access_token, uid };
//				return Account;
//		
//			}		
//	
//			conn.disconnect();
//			return null;
//		
//		}
//	
//	
//	public String[]  getDeviceAccess(String urlStr, String User_Agent, String contentType, String Authorization, String Access_token, String uid, String Client_id) throws Exception {
//		
//		String query = "/account/v1/tokens/"+Access_token;
//			
//		if(uid != null){
//			query = query+"?uid="+URLEncoder.encode(uid, charset)+"&";
//		}
//		if(Client_id != null){
//			query = query+"client_id="+URLEncoder.encode(Client_id, charset)+"&";
//		}
//			if(query.charAt(query.length()-1) == '&'){
//			query = query.substring(0, query.length()-1);
//		}
//				
//			URL url = new URL(urlStr + query);
//			System.out.println("[Request]"+ "\n"+"GET "+ url.toString());
//	
//			//	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("168.219.61.252", 8080));
//			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("70.10.15.10", 8080));
//			
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
//			//	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestMethod("GET");
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//			conn.setUseCaches(false);
//			conn.setAllowUserInteraction(false);
//	
//			conn.setRequestProperty("Authorization", Authorization);
//			conn.setRequestProperty("Content-Type",	contentType);
//			conn.setRequestProperty("User-Agent", User_Agent);
//			
//			
//			System.out.println("Authorization : "+ Authorization);
//			System.out.println("Content-Type: "+ contentType);
//			System.out.println("User-Agent: "+User_Agent);
//					
//			//Accept 수정은 여기서...
//			if(contentType.equals("application/json") == true){
//				conn.setRequestProperty("Accept", contentType);	
//				System.out.println("Accept: +"+ contentType);
//			}else{
//				conn.setRequestProperty("Accept", contentType + ", application/json");
//				System.out.println("Accept: +"+ contentType + ", application/json");
//			}		
//		
//			// Buffer the result into a string
//			System.out.println("\n"+"[Response]");
//			//System.out.println("---Start of Header---");
//			System.out.println(conn.getHeaderField(null));
//			responseHeader.responseCode = conn.getResponseCode();
//			System.out.println("Content-Type: "+conn.getContentType());
//			responseHeader.ContentType = conn.getContentType();		
//			System.out.println("Date: "+ conn.getHeaderField("Date"));
//			System.out.println("Server: "+ conn.getHeaderField("Server"));
//			System.out.println("Content-Length: "+conn.getHeaderField("Content-Length"));
//			responseHeader.contentLength = Integer.parseInt(conn.getHeaderField("Content-Length"));
//			System.out.println("Proxy-Connection: "+ conn.getHeaderField("Proxy-Connection"));
//			System.out.println("Connection: "+ conn.getHeaderField("Connection"));
//			System.out.println("Content-Encoding: "+conn.getContentEncoding());
//			responseHeader.charSet = conn.getContentEncoding();
//			
//			
//			
//			//System.out.println(conn.getHeaderFields().toString());
//			JSONParser parser = new JSONParser();
//			
//			//System.out.println("\n"+"---End of Header---");
//					
//			if (conn.getResponseCode() != 200) {
//				int nRead;
//				byte[] data = new byte[16384];
//				InputStream is = conn.getErrorStream();
//				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//				while ((nRead = is.read(data, 0, data.length)) != -1) {
//					buffer.write(data, 0, nRead);
//				}
//	
//				buffer.flush();
//				
//				byte[] byteData = buffer.toByteArray();
//				
//				String jsonStringBody = new String(byteData, charset);
//				System.out.println("PlantextData: "+jsonStringBody);
//				Object obj = parser.parse(jsonStringBody);
//				JSONObject jsonObject = (JSONObject) obj;
//				thisResponse.rcode = (long) jsonObject.get("rcode");
//				thisResponse.rmsg = (String) jsonObject.get("rmsg");
//				
//				//throw new IOException(conn.getResponseMessage());
//				
//				
//			} else {
//				int nRead;
//				byte[] data = new byte[16384];
//	
//				InputStream is = conn.getInputStream();
//	
//				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//				while ((nRead = is.read(data, 0, data.length)) != -1) {
//					buffer.write(data, 0, nRead);
//				}
//	
//				buffer.flush();
//			}		
//			conn.disconnect();
//			return null;
//		
//		}
//}