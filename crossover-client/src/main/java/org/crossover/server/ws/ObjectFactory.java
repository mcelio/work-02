
package org.crossover.server.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.crossover.server.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Compile_QNAME = new QName("http://ws.server.crossover.org/", "compile");
    private final static QName _CompileResponse_QNAME = new QName("http://ws.server.crossover.org/", "compileResponse");
    private final static QName _FindCompilation_QNAME = new QName("http://ws.server.crossover.org/", "findCompilation");
    private final static QName _FindCompilationResponse_QNAME = new QName("http://ws.server.crossover.org/", "findCompilationResponse");
    private final static QName _Exception_QNAME = new QName("http://ws.server.crossover.org/", "Exception");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.crossover.server.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Compile }
     * 
     */
    public Compile createCompile() {
        return new Compile();
    }

    /**
     * Create an instance of {@link CompileResponse }
     * 
     */
    public CompileResponse createCompileResponse() {
        return new CompileResponse();
    }

    /**
     * Create an instance of {@link FindCompilation }
     * 
     */
    public FindCompilation createFindCompilation() {
        return new FindCompilation();
    }

    /**
     * Create an instance of {@link FindCompilationResponse }
     * 
     */
    public FindCompilationResponse createFindCompilationResponse() {
        return new FindCompilationResponse();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link Compilation }
     * 
     */
    public Compilation createCompilation() {
        return new Compilation();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Compile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.server.crossover.org/", name = "compile")
    public JAXBElement<Compile> createCompile(Compile value) {
        return new JAXBElement<Compile>(_Compile_QNAME, Compile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CompileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.server.crossover.org/", name = "compileResponse")
    public JAXBElement<CompileResponse> createCompileResponse(CompileResponse value) {
        return new JAXBElement<CompileResponse>(_CompileResponse_QNAME, CompileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindCompilation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.server.crossover.org/", name = "findCompilation")
    public JAXBElement<FindCompilation> createFindCompilation(FindCompilation value) {
        return new JAXBElement<FindCompilation>(_FindCompilation_QNAME, FindCompilation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindCompilationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.server.crossover.org/", name = "findCompilationResponse")
    public JAXBElement<FindCompilationResponse> createFindCompilationResponse(FindCompilationResponse value) {
        return new JAXBElement<FindCompilationResponse>(_FindCompilationResponse_QNAME, FindCompilationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.server.crossover.org/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

}
