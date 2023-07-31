package org.productivity.java.syslog4j.server;

import java.util.List;

import org.productivity.java.syslog4j.SyslogCharSetIF;
import org.productivity.java.syslog4j.SyslogConstants;
import org.productivity.java.syslog4j.SyslogRuntimeException;

/**
* SyslogServerConfigIF provides a common, extensible configuration interface for all
* implementations of SyslogServerIF.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogServerConfigIF.java,v 1.12 2011/01/11 05:11:13 cvs Exp $
*/
public interface SyslogServerConfigIF extends SyslogConstants, SyslogCharSetIF {
	Class<? extends SyslogServerIF> getSyslogServerClass();

	String getHost();
	void setHost(String host) throws SyslogRuntimeException;

	int getPort();
	void setPort(int port) throws SyslogRuntimeException;
	
	boolean isUseDaemonThread();
	void setUseDaemonThread(boolean useDaemonThread);
	
	int getThreadPriority();
	void setThreadPriority(int threadPriority);
	
	List<SyslogServerEventHandlerIF> getEventHandlers();
	
	long getShutdownWait();
	void setShutdownWait(long shutdownWait);
	
	void addEventHandler(SyslogServerEventHandlerIF eventHandler);
	void insertEventHandler(int pos, SyslogServerEventHandlerIF eventHandler);
	void removeEventHandler(SyslogServerEventHandlerIF eventHandler);
	void removeAllEventHandlers();
	
	boolean isUseStructuredData();
	void setUseStructuredData(boolean useStructuredData);
	
	Object getDateTimeFormatter();
	void setDateTimeFormatter(Object dateTimeFormatter);
}
