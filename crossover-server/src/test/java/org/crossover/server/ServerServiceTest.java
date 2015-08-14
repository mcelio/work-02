package org.crossover.server;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.crossover.server.service.CompilationService;
import org.crossover.server.ws.Compilation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.lingala.zip4j.exception.ZipException;

/**
 * 
 * Test class for the compilation web service producer.
 * 
 * @author Marcos
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/applicationContext.xml",
		"file:src/main/webapp/WEB-INF/beans.xml" })
public class ServerServiceTest {

	// Log server logger
	static final Logger logger = LogManager.getLogger(ServerServiceTest.class.getName());

	@Autowired
	private CompilationService compilationService;

	// Date format pattern
	public static DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * Test save file method.
	 */
	@Test
	public void saveFileTest() {
		Compilation compilation = new Compilation();
		File logFile = new File("sample/crossover-client.zip");
		Assert.assertTrue(logFile != null);
		DataSource dataSource = new FileDataSource(logFile);
		DataHandler data = new DataHandler(dataSource);
		compilation.setDescription("Maven project");
		compilation.setLogFile(data);
		// format date
		String date = sdf.format(new Date());
		compilation.setDate(date);
		File result = compilationService.saveFile(compilation);
		Assert.assertTrue(result.isFile());
	}
	
	/**
	 * Test extract method.
	 * @throws IOException 
	 * @throws ZipException 
	 */
	@Test
	public void extractAllTest() throws ZipException, IOException {
		Compilation compilation = new Compilation();
		File logFile = new File("sample/crossover-client.zip");
		Assert.assertTrue(logFile != null);
		DataSource dataSource = new FileDataSource(logFile);
		DataHandler data = new DataHandler(dataSource);
		compilation.setDescription("Maven project");
		compilation.setLogFile(data);
		// format date
		String date = sdf.format(new Date());
		compilation.setDate(date);
		File result = compilationService.saveFile(compilation);
		Assert.assertTrue(result.isFile());
		
		File extracted = compilationService.extractAll(result);
		Assert.assertTrue(extracted.isDirectory());		
	}
	
	/**
	 * Test find pom method.
	 * @throws IOException 
	 * @throws ZipException 
	 */
	@Test
	public void findPomTest() throws ZipException, IOException {
		Compilation compilation = new Compilation();
		File logFile = new File("sample/crossover-client.zip");
		Assert.assertTrue(logFile != null);
		DataSource dataSource = new FileDataSource(logFile);
		DataHandler data = new DataHandler(dataSource);
		compilation.setDescription("Maven project");
		compilation.setLogFile(data);
		// format date
		String date = sdf.format(new Date());
		compilation.setDate(date);
		File result = compilationService.saveFile(compilation);
		Assert.assertTrue(result.isFile());
		
		File extracted = compilationService.extractAll(result);
		Assert.assertTrue(extracted.isDirectory());
		
		File pom = compilationService.findPom(extracted);
		Assert.assertTrue(pom.isFile());
	}
	
	/**
	 * Test package goal Maven task method.
	 * @throws Exception 
	 */
	@Test
	public void packageGoalTest() throws Exception {
		Compilation compilation = new Compilation();
		File logFile = new File("sample/crossover-client.zip");
		Assert.assertTrue(logFile != null);
		DataSource dataSource = new FileDataSource(logFile);
		DataHandler data = new DataHandler(dataSource);
		compilation.setDescription("Maven project");
		compilation.setLogFile(data);
		// format date
		String date = sdf.format(new Date());		
		compilation.setDate(date);
		// id
		Long id = Long.valueOf(date); 
		File result = compilationService.saveFile(compilation);
		Assert.assertTrue(result.isFile());
		
		File extracted = compilationService.extractAll(result);
		Assert.assertTrue(extracted.isDirectory());
		
		File pom = compilationService.findPom(extracted);
		Assert.assertTrue(pom.isFile());
		
		Compilation packageGoal = compilationService.packageGoal(pom, id, date);
		Assert.assertTrue(packageGoal != null);
	}
}
