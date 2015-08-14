package org.crossover.server.ws;

import javax.jws.WebService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.crossover.server.service.CompilationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class responsible to implement the soap web service. 
 * 
 * @author Marcos
 *
 */
@WebService(endpointInterface = "org.crossover.server.ws.CompileService", serviceName = "compileService")
public class CompileServiceImpl implements CompileService {

	// Console logger
	static final Logger logger = LogManager.getLogger(CompileServiceImpl.class.getName());
	
	@Autowired
	private CompilationService compilationService;
	
	/**
	 * Compilation implementation method. 
	 * 
	 * @param compilation object.
	 */
	public Compilation compile(Compilation compilation) throws Exception {
		logger.debug("Start compilation process");
		Compilation result = compilationService.compile(compilation);		
		logger.debug("End compilation process");
		return result;
	}
	
	/**
	 * Finds a compilation by id
	 * 
	 * @param id
	 */
	public Compilation findCompilation(Compilation compilation) throws Exception {
		logger.debug("Start find compilation");
		Compilation result = compilationService.findCompilation(compilation);
		logger.debug("End find compilation");
		return result;
	}
}
