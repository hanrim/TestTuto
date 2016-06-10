package com.sec.stp.config;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.sec.stp.config.ReqHost;

public class ReqHostTest extends ReqHost {
	private ReqHost host;
	private String url;
	private String ip;
	private int port;

	@Before
	public void setUp() throws Exception {
		host = new ReqHost();
		url = "http://www.naver.com";
		ip = "10.234.55.34";
		port = 8080;
		host.setHost(url,ip,port);
	}

	@After
	public void tearDown() throws Exception {
		host = null;
		url = null;
		ip = null;
		port = 0;
	}

	@Test
	public void testGetHost_url() {
		assertTrue(host.getHost().get(0) == url);
		
	}
	
	@Test
	public void testGetHost_ip(){
		assertTrue(host.getHost().get(1) == ip);
	}
	
	@Test
	public void testGetHost_port(){
		assertTrue(Integer.parseInt(host.getHost().get(2)) == port);
	}
	
	@Test(expected=NullPointerException.class)
	public void testGetHost_NullPointerException(){
		host = null;
		host.getHost();
	}
	
	@Test
	public void testGethost_returnNullWhenException(){
		host = null;
		assert(host.getHost() == null);
	}
	
	@Test
	public void testGetIp(){
		assertTrue(host.getIp() == ip);
	}
	
	@Test(expected=NullPointerException.class)
	public void testGetIp_NullPointerException(){
		host = null;
		host.getIp();
	}
	
	@Test
	public void testGetIp_returnNullWhenException(){
		host = null;
		assert(host.getIp() == null);
	}
	
	@Test
	public void testGetUrl(){
		assertTrue(host.getURL() == url);
	}
	
	@Test(expected=NullPointerException.class)
	public void testGetUrl_NullPointerException(){
		host = null;
		host.getURL();
	}
	
	@Test
	public void testGetUrl_returnNullWhenException(){
		host = null;
		assert(host.getURL() == null);
	}
	
	@Test
	public void testGetPort(){
		assertTrue(host.getPort() == port);
	}
	
	@Test(expected=NullPointerException.class)
	public void testGetPort_NullPointerException(){
		host = null;
		host.getPort();
	}
	
	@Test
	public void testGetPort_returnNullWhenException(){
		host = null;
		assert(host.getPort() == 0);
	}
}
