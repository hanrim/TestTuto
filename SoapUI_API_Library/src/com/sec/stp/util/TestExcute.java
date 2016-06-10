package com.sec.stp.util;

public class TestExcute {
	
	public static void main(String[] args) throws Exception{
		
		RequestFactory factory = new RequestFactory();
		
		factory.reqHost.setHost("http://stga.sc-proxy.samsungosp.com","168.219.61.252",8080);
		factory.reqPath.setPath("/bnr/restoreitem");
		
		factory.reqHead.setHeader("User-Agent","Jakarta Commons-HttpClient/3.1");
		factory.reqParam.setParams("access_token","token00005");
		factory.reqParam.setParams("uid","userid0005");
		factory.reqParam.setParams("client_did","dfu289djryuskfv1");
		factory.reqParam.setParams("cid","KNszpw41I3");
		factory.reqParam.setParams("key","BACKUP_CALLLOGS_dfu289djryuskfv1_key01 ");
		
		byte [] byteData = factory.sendHttpGetAsByte();
		System.out.println(factory.getRequestLog());
		System.out.println(factory.getResponseLog());
		System.out.println(new String(byteData,"UTF-8"));
		String resultMessage = factory.generateFile(byteData,"C://resultMessage.txt");
		System.out.println(resultMessage);
		
	}

}
