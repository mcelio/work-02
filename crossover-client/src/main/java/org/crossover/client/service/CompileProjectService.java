package org.crossover.client.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.crossover.server.ws.Compilation;
import org.crossover.server.ws.CompileService;
import org.crossover.server.ws.CompileService_Service;
import org.crossover.server.ws.Exception_Exception;
import org.springframework.stereotype.Service;

/**
 * 
 * Class responsible to prepared the request to the web service
 * 
 * @author Marcos
 *
 */
@Service
public class CompileProjectService {

	// Date format pattern
	public static DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/**
	 * Calss the web service passing the file as parameter. 
	 * 
	 * @param file to be compiled
	 * @return
	 * @throws exception from web service. 
	 */
	public Compilation compileProject(byte[] file) throws Exception{
		CompileService_Service service = new CompileService_Service();
		CompileService client = service.getCompileServiceImplPort();
		// format date
		String date = sdf.format(new Date());
		Compilation compilation = new Compilation();		
		compilation.setId(Long.valueOf(date));		
		compilation.setLogFile(file);
		// format date
		compilation.setDate(date);
		return client.compile(compilation);
	}
	
	/**
	 * Calss the web service passing the file as parameter. 
	 * 
	 * @param file to be compiled
	 * @return
	 * @throws exception from web service. 
	 */
	public Compilation findCompilation(Compilation compilation) throws Exception{
		CompileService_Service service = new CompileService_Service();
		CompileService client = service.getCompileServiceImplPort();
		if(compilation != null){
			return client.findCompilation(compilation);
		}
		return null;
	}
	
}
