Log Server
-----------------------------------------------------------------------------------------

The Log Server program centralize log traces of all application configured according to 
its log host environment. 

-----------------------------------------------------------------------------------------

Requirements

- Java 1.7.x
- Maven 3.x
- Read and write permission on the program files

Compilation

To compile the program, execute the command below from the program root directory

<root> mvn clean install

Execution

To start the Log Server program, execute the command below from the target subdirectory:

<root>/target/java -jar log-server-0.0.1-SNAPSHOT-jar-with-dependencies.jar 4712 log4j-server.properties

Log Visualization

To visualize the log file with traces from the applications, open the file "application-error.log".
The may take a time to be update because of the several open channels for log traces.

