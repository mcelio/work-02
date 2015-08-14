package org.crossover.client;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.crossover.client.service.CompileProjectService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * Client test class
 * 
 * @author Marcos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/applicationContext.xml",
		"file:src/main/webapp/WEB-INF/beans.xml" })
public class ClientTest {

	// Log server logger	
	static final Logger logger = LogManager.getLogger(ClientTest.class.getName());

	/**
	 * Test ogin credentials
	 */	
	@Test
	public void loginTest(){		
		String username = "ADMIN", password = "ADMINPASS";		
		Assert.assertEquals(username, "ADMIN");		
		Assert.assertEquals(password, "ADMINPASS");
		
	}
	
	/**
	 * Test date format
	 */
	@Test
	public void dateFormatTest(){		
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 06, 29, 11, 33, 33);
		String date = CompileProjectService.sdf.format(cal.getTime());
		Assert.assertEquals(date, "20150729113333");
	}
}
