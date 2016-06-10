package com.sec.stp.config;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReqParamTest extends ReqParam {
	ReqParam param;
	
	@Before
	public void setUp() throws Exception {
		param = new ReqParam();
		param.setParams("access_token", "phou0215");
		param.setParams("uid","navy1063");
	}

	@After
	public void tearDown() throws Exception {
		param = null;
	}

	@Test
	public void testGetParamKeys_size() {
		assertTrue(param.paramKeys.size() == 2);
		
	}	
	
	@Test
	public void testGetParamKeys_param1(){
		ArrayList <String> keys = param.getParamKeys();
		assertTrue(keys.get(0) == "access_token");
	}
	
	@Test
	public void testGetParamKeys_param2(){
		ArrayList <String> keys = param.getParamKeys();
		assertTrue(keys.get(1) == "uid");
	}
		
	@Test (expected = IndexOutOfBoundsException.class)
	public void testGetParamKeys_IndexOutofBoundsException() {
		assertTrue(param.getParamKeys().get(3) == null);
	}
	
	@Test
	public void testGetParamValues_size(){
		assertTrue(param.paramValues.size() == 2);
	}
	
	@Test
	public void testGetParamValues_param1(){
		ArrayList <String> values = param.getParamValues();
		assertTrue(values.get(0) == "phou0215");
	}
	
	@Test
	public void tetGetParamValues_param2(){
		ArrayList <String> values = param.getParamValues();
		assertTrue(values.get(1) == "navy1063");
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetParamValues_IndexOutOfBoundsException(){
		assertTrue(param.getParamValues().get(3) == null);
	}
	
	@Test
	public void testSetParams_Basic(){
		param.setParams("tid","skt000987");
		assertTrue(param.paramKeys.get(2) == "tid");
		assertTrue(param.paramValues.get(2) == "skt000987");
	}
	
	
	
	
	
}
