package com.sec.stp.config;

import static org.junit.Assert.*;
import com.sec.stp.config.ReqHead;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReqHeadTest {

	private ReqHead head;


	@Before
	public void setUp() throws Exception {
		head = new ReqHead();
		head.setHeader("Encording", "UTF-8");
		head.setHeader("Accept","application/json");
		head.setHeader("uid","Aksdrsd09");
	}

	@After
	public void tearDown() throws Exception {
		head = null;
	}

	@Test
	public void testGetHeaderKey_size() {
		ArrayList <String> list = head.getHeaderKeys();
		assertTrue(list.size() == 3);
	}
	
	@Test
	public void testGetHeaderKey_parameter1(){
		ArrayList <String> list = head.getHeaderKeys();
		assertTrue(list.get(0)=="Encording");
	}
	
	@Test
	public void testGetHeaderKey_parameter2(){
		ArrayList <String> list = head.getHeaderKeys();
		assertTrue(list.get(1)=="Accept");
	}

	@Test
	public void testGetHeaderKey_parameter3(){
		ArrayList <String> list = head.getHeaderKeys();
		assertTrue(list.get(2)=="uid");
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetHeaderKey_IndexOutofBoundsException(){
		assertTrue(head.getHeaderKeys().get(3) == null);
	}
	
	@Test
	public void testGetHeaderValue_size() {
		ArrayList <String> list = head.getHeaderValues();
		assertTrue(list.size() == 3);
	}
	
	@Test
	public void testGetHeaderValue_parameter1(){
		ArrayList <String> list = head.getHeaderValues();
		assertTrue(list.get(0)=="UTF-8");
	}
	
	@Test
	public void testGetHeaderValue_parameter2(){
		ArrayList <String> list = head.getHeaderValues();
		assertTrue(list.get(1)=="application/json");
	}

	@Test
	public void testGetHeaderValue_parameter3(){
		ArrayList <String> list = head.getHeaderValues();
		assertTrue(list.get(2)=="Aksdrsd09");
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetHeaderValue_IndexOutofBoundsException(){
		assertTrue(head.getHeaderValues().get(3) == null);
	}
	
	@Test
	public void testSetHeaders(){
		head.setHeader("access_token", "navy1063");
		assertTrue(head.headerKeys.contains("access_token"));
		assertTrue(head.headerValues.contains("navy1063"));
	}
	@Test
	public void testGetRequestHeader_key1(){
		String value = head.getRequestHeader("Encording");
		assertTrue(value == "UTF-8");
	}
	
	@Test
	public void testGetRequestHeader_key2(){
		String value = head.getRequestHeader("Accept");
		assertTrue(value == "application/json");
	}
	
	@Test
	public void testGetRequestHeader_key3(){
		String value = head.getRequestHeader("uid");
		assertTrue(value == "Aksdrsd09");
	}
	
	@Test
	public void testSetHeaders_NotExsitsKey(){
		assertTrue(head.getRequestHeader("test") == null);
	}

}
