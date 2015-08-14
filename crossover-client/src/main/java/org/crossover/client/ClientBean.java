package org.crossover.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.camel.CamelContext;
import org.apache.camel.Consume;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.PollingConsumer;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.crossover.client.service.CompileProjectService;
import org.crossover.server.ws.Compilation;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class resposible to centralize the client requests
 * 
 * @author Marcos
 *
 */
@ManagedBean
@SessionScoped
public class ClientBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// Log server logger
	static final Logger logger = LogManager.getLogger(ClientBean.class.getName());

	// compilation service instance injecttion
	@Inject
	private CompileProjectService compileProjectService;

	// compilation detail object
	private Compilation compilationDetail;

	// Client upload file
	private UploadedFile file;

	// compilatio list
	private List<Compilation> list;

	// courrent log
	private String currentLog;
	
	public void pollCurrentLog() {
		try {
			CamelContext camelContext=new DefaultCamelContext();
			camelContext.addComponent("jms",org.apache.activemq.camel.component.ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));
			ConsumerTemplate consumerTemplate = camelContext.createConsumerTemplate();
			Message message = consumerTemplate.receive("jms:queue:maven-logs").getIn();
			
			if (message != null  && message.getBody() != null) {
				if (currentLog == null) {
					currentLog = message.getBody().toString();
				} else {
					currentLog += "\n" + message.getBody();
				}
			}			
		} catch (Exception e) {
			logger.error("Error:", e);
		}
	}
	
	public String fetchCurrentLog() {
		try {
			CamelContext camelContext=new DefaultCamelContext();
			camelContext.addComponent("jms",org.apache.activemq.camel.component.ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));
			ConsumerTemplate consumerTemplate = camelContext.createConsumerTemplate();
			Message message = consumerTemplate.receive("jms:queue:maven-logs").getIn();
			
			if (message != null  && message.getBody() != null) {
				if (currentLog == null) {
					currentLog = message.getBody().toString();
				} else {
					currentLog += "\n" + message.getBody();
				}
			}			
		} catch (Exception e) {
			logger.error("Error:", e);
		}
		return "compile-panel";
	}

	// getter and setter
	public UploadedFile getFile() {
		return file;
	}

	// getter and setter
	public void setFile(UploadedFile file) {
		this.file = file;
	}

	// getter and setter
	public String getCurrentLog() {
		return currentLog;
	}

	// getter and setter
	public void setCurrentLog(String currentLog) {
		this.currentLog = currentLog;
	}

	/**
	 * File upload façade
	 */
	public void handleFileUpload() {
		doCompilation(getFile());
	}

	/**
	 * Methos does the upload and compilation process, inclusing the web service
	 * call
	 * 
	 * @param uploadedFile
	 */
	private void doCompilation(UploadedFile uploadedFile) {
		logger.error("Start the compilation process");
		// check if the file is null or not
		if (uploadedFile != null) {
			try {
				// web service call
				Compilation result = compileProjectService.compileProject(uploadedFile.getContents());
				// the result to the compilation list
				if (result != null) {
					if (getList() == null) {
						list = new ArrayList<Compilation>();
					}
					list.add(result);
				}

			} catch (Exception e) {
				logger.error("Error: " + e);
				FacesMessage message = new FacesMessage("Error",
						"Unexpected error occurred qhen calling the web service provider.");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}
	}

	/**
	 * Fetch the compilation calling the web service provider.
	 */
	public String fetchLogFile() {
		Compilation result = null;
		try {
			// web service call
			result = compileProjectService.findCompilation(getCompilationDetail());
		} catch (Exception e) {
			logger.error("Error: " + e);
			FacesMessage message = new FacesMessage("Error",
					"Unexpected error occurred qhen calling the web service provider.");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return "compile-panel";
		}

		// verify if the return result is null
		if (result != null && result.getLogFile() != null) {
			// convert the binary log file to text
			ByteArrayInputStream bis = new ByteArrayInputStream(result.getLogFile());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bis), 1);
			String line;
			StringBuffer logBuffer = new StringBuffer();
			try {
				while ((line = bufferedReader.readLine()) != null) {
					logBuffer.append(line + "\n");
				}
			} catch (IOException e) {
				logger.error("Error: " + e);
				FacesMessage message = new FacesMessage("Error", "Unexpected error occurred, please verify the file.");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
			// set the compilation detail
			result.setLog(logBuffer.toString());
			setCompilationDetail(result);
			return "compile-detail";
		}
		return "compile-panel";
	}

	// getter and setter
	public List<Compilation> getList() {
		return list;
	}

	// getter and setter
	public void setList(List<Compilation> list) {
		this.list = list;
	}

	// getter and setter
	public Compilation getCompilationDetail() {
		return compilationDetail;
	}

	// getter and setter
	public void setCompilationDetail(Compilation compilationDetail) {
		this.compilationDetail = compilationDetail;
	}
}
