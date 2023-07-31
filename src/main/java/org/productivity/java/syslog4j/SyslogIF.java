package org.productivity.java.syslog4j;

/**
* SyslogIF provides a common interface for all Syslog4j client implementations.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogIF.java,v 1.9 2010/02/11 04:59:22 cvs Exp $
*/
public interface SyslogIF extends SyslogConstants {
	void initialize(String protocol, SyslogConfigIF config) throws SyslogRuntimeException;
	
	String getProtocol();
	SyslogConfigIF getConfig();
	
	void backLog(int level, String message, Throwable reasonThrowable);
	void backLog(int level, String message, String reason);
	
	void log(int level, String message);
	
	void debug(String message);
	void info(String message);
	void notice(String message);
	void warn(String message);
	void error(String message);
	void critical(String message);
	void alert(String message);
	void emergency(String message);

	void log(int level, SyslogMessageIF message);
	
	void debug(SyslogMessageIF message);
	void info(SyslogMessageIF message);
	void notice(SyslogMessageIF message);
	void warn(SyslogMessageIF message);
	void error(SyslogMessageIF message);
	void critical(SyslogMessageIF message);
	void alert(SyslogMessageIF message);
	void emergency(SyslogMessageIF message);

	void flush() throws SyslogRuntimeException;
	void shutdown() throws SyslogRuntimeException;
	
	void setMessageProcessor(SyslogMessageProcessorIF messageProcessor);
	SyslogMessageProcessorIF getMessageProcessor();

	void setStructuredMessageProcessor(SyslogMessageProcessorIF messageProcessor);
	SyslogMessageProcessorIF getStructuredMessageProcessor();
}
