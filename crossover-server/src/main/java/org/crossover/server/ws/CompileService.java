package org.crossover.server.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Coompilation web service interface (contract)
 * 
 * @author Marcos
 *
 */
@WebService
public interface CompileService {

	@WebMethod
    public Compilation compile(Compilation compilation) throws Exception;
	
	@WebMethod
    public Compilation findCompilation(Compilation compilation) throws Exception;	
	
}
