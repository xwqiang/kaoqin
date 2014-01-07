//package com.lichuan.attendance.controller;
//
//import static org.junit.Assert.assertEquals;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration("src/main/resources/applicationContext_plate.xml")
//public class SalaryControllerTest {
//	
//	 @Autowired
//	 private WebApplicationContext wac;
//	 
//	private MockMvc mockMvc;
//	 @Before
//	  public void setup() {
////	    this.mockMvc = webAppContextSetup(this.wac).build();
//	 }
//	
//	@Test
//	public void testGetPersonCheckingin(){
//		
//		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/getPersonChecking-in.do");
//		request.addParameter("every_month", "2013-12");
//		request.addParameter("oa", "1007026");
//		request.addParameter("user_name", "李川");
//		
//		
//		SalaryController controller = new SalaryController();
//
//		request.setSession(new MockHttpSession(null));
//
//		HttpServletResponse response = new MockHttpServletResponse();
//		String result = controller.getPersonSalary(request, null);
//
//		// review the results.
//		assertEquals( "/salary/personChecking-in.jsp", result);
////		assertTrue("Invoked loggedIn method", controller.wasInvoked("loggedIn"));
////
////		assertTrue("view name is ",);
////
////		assertTrue("Only one method invoked", cont.getInvokedMethods() == 1);
//
//	}
//	
//
//
//////	@Ignore
////	@Test
////	public void testSubstract() {
////		calculator.substract(1);
////		assertEquals(-1, calculator.getResult());
////	}
//}
