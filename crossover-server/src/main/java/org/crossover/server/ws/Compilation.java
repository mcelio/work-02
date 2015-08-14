package org.crossover.server.ws;

import java.io.Serializable;

import javax.activation.DataHandler;

/**
 * 
 * Compilation class
 * 
 * @author Marcos
 *
 */
public class Compilation implements Serializable {
	
	/**
	 * Identifier
	 */
	private Long id;
	
	/**
	 * Date of the compilation
	 */
	private String date;
	
	/**
	 * Description
	 */
	private String description;
	
	/**
	 * Log file holder.
	 */
	private DataHandler logFile;
	
	/**
	 * Log string
	 */
	private String log;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public DataHandler getLogFile() {
		return logFile;
	}

	public void setLogFile(DataHandler logFile) {
		this.logFile = logFile;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}
	
}
