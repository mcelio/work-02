package org.crossover.server.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.apache.camel.ProducerTemplate;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationOutputHandler;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.crossover.server.ws.Compilation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * 
 * Class resposible to produce the webb service for compilation.
 * 
 * @author Marcos
 *
 */
@Service
public class CompilationService {

	// Log server logger
	static final Logger logger = LogManager.getLogger(CompilationService.class.getName());

	// Date format pattern for id
	public static DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	// Date format pattern for display
	public static DateFormat dateDispplayFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	/**
	 * Method starts the compilation process.
	 * 
	 * @param compilation
	 * @return
	 * @throws Exception
	 */
	public Compilation compile(Compilation compilation) throws Exception {
		Long id = compilation.getId();
		String date = compilation.getDate();
		try {
			File file = saveFile(compilation);
			// validate and set messages after saving zip file
			if (file == null) {
				Compilation result = new Compilation();
				result.setId(id);
				result.setDescription(
						"Could not save the file, please verify the permissions on the file system or if the transfered file is corrupted.");
				return result;
			}
			// Verifies if the extraction is ok
			File rootFolder = extractAll(file);
			if (rootFolder == null) {
				Compilation result = new Compilation();
				result.setId(id);
				result.setDescription("Could not extract the transfered file. Verify if the file is corrupted.");
				return result;
			}
			// Verifies if the pom.xml was found
			File pom = findPom(rootFolder);
			if (pom == null) {
				Compilation result = new Compilation();
				result.setId(id);
				result.setDescription("Not a Maven project, could not find the pom.xml file.");
				return result;
			}
			// Verifies if the Maven task was executed properly.
			Compilation result = packageGoal(pom, id, date);
			if (result == null) {
				result = new Compilation();
				result.setId(id);
				result.setDescription(
						"Error when compiling the project, please verify the integrity of the project files.");
				return result;
			}
			result.setId(id);
			return result;
		} catch (IOException e) {
			logger.error("Error: " + e);
		}
		Compilation result = new Compilation();
		result.setId(id);
		result.setDescription("Unexpected error on the service provider.");
		return result;
	}

	/**
	 * Method saves the transfered file.
	 * 
	 * @param compilation
	 * @return
	 * @throws IOException
	 */
	public File saveFile(Compilation compilation) {
		File file = null;
		try {
			file = new File(compilation.getDate() + ".zip");
			FileOutputStream out = new FileOutputStream(file);
			InputStream in = compilation.getLogFile().getInputStream();
			IOUtils.copy(in, out);
			in.close();
			out.close();
			return file;
		} catch (IOException io) {
			logger.error("Error: " + io);
			// excludes the file if it was created
			if (file != null) {
				Path path = Paths.get(file.getAbsolutePath());
				try {
					Files.delete(path);
				} catch (IOException e) {
					logger.error("Error: " + io);
				}
			}
		}
		return null;
	}

	/**
	 * Method extracts the compressed file sent.
	 * 
	 * @param source
	 *            file to be extracted
	 * @return root folder
	 * @throws ZipException
	 * @throws IOException
	 */
	public File extractAll(File source) throws ZipException, IOException {
		String outputFolder = source.getAbsolutePath().replace(".zip", "");
		ZipFile zipFile = new ZipFile(source);
		zipFile.extractAll(outputFolder);
		Path path = Paths.get(source.getAbsolutePath());
		Files.delete(path);
		return new File(outputFolder);
	}

	/**
	 * Method find the pom.xml file of the extracted project files
	 * 
	 * @param root
	 * @return pom.xml file
	 */
	public File findPom(File root) {
		if (root.getName().indexOf("pom.xml") != -1) {
			return root;
		}
		if (root.isDirectory()) {
			for (File file : root.listFiles()) {
				return findPom(file);
			}
		}
		return findPom(root);
	}
	
	
	@Autowired
	private ProducerTemplate camelTemplate;

	/**
	 * Execute the Maven tasks.
	 * 
	 * @param pom
	 * @return Compilation object.
	 * @throws Exception 
	 */
	public Compilation packageGoal(final File pom, final Long id, final String date) throws Exception {
		final InvocationRequest request = new DefaultInvocationRequest();
		Compilation compilation = new Compilation();
		request.setPomFile(pom);
		List<String> goals = new ArrayList<String>();
		goals.add("clean");
		goals.add("package");
		request.setGoals(goals);

		// Configure Maven home
		final Invoker invoker = new DefaultInvoker();
		invoker.setMavenHome(new File(System.getenv("MAVEN_HOME")));
		// Configure the log output, in this case it writes the log messages to
		// a file.
		invoker.setOutputHandler(new InvocationOutputHandler() {
			public void consumeLine(String arg0) {
				File file = new File(id + File.separator + "compilation.log");
				Writer writer = null;
				try {
					
					//send line to a queue
					camelTemplate.sendBody("jms:queue:maven-logs", id + "-" + arg0);
					
					// configure the log writer
					writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
					writer.append(arg0 + "\n");
					// Catch exceptions
				} catch (UnsupportedEncodingException e) {
					logger.error("Error: " + e);
				} catch (FileNotFoundException e) {
					logger.error("Error: " + e);
				} catch (IOException e) {
					logger.error("Error: " + e);
				} finally {
					try {
						writer.close();
					} catch (IOException e) {
						logger.error("Error: " + e);
					}
				}
			}
		});

		// Configure the invocation of the Maven tasks.
		Thread compilationThread = new Thread(new Runnable() {

			public void run() {
				try {
					InvocationResult invocationResult = invoker.execute(request);
				} catch (MavenInvocationException e) {
					logger.error("Error: " + e);
				}
			}
		});
		compilationThread.start();
		compilation.setDescription("Maven project");
		compilation.setId(id);
		try{
			Date dateObject = sdf.parse(date);
			compilation.setDate(dateDispplayFormat.format(dateObject));
		}catch(Exception e){
			throw new Exception("Date format exception");
		}
		return compilation;
	}

	public Compilation findCompilation(Compilation compilation) {
		File logFile = new File(compilation.getId() + File.separator + "compilation.log");
		DataSource dataSource = new FileDataSource(logFile);
		DataHandler data = new DataHandler(dataSource);
		compilation.setLogFile(data);
		return compilation;
	}
}
