package org.productivity.java.syslog4j.impl;

import java.util.List;

import org.productivity.java.syslog4j.SyslogBackLogHandlerIF;
import org.productivity.java.syslog4j.SyslogConfigIF;
import org.productivity.java.syslog4j.SyslogMessageModifierIF;

/**
* AbstractSyslogConfigIF provides an interface for all Abstract Syslog
* configuration implementations.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractSyslogConfigIF.java,v 1.7 2010/10/29 03:14:20 cvs Exp $
*/
public interface AbstractSyslogConfigIF extends SyslogConfigIF {
	Class<? extends AbstractSyslogWriter> getSyslogWriterClass();
	
	List<SyslogBackLogHandlerIF> getBackLogHandlers();
	
	List<SyslogMessageModifierIF> getMessageModifiers();

	byte[] getSplitMessageBeginText();
	void setSplitMessageBeginText(byte[] beginText);
	
	byte[] getSplitMessageEndText();
	void setSplitMessageEndText(byte[] endText);

	boolean isThreaded();
	void setThreaded(boolean threaded);
	
	boolean isUseDaemonThread();
	void setUseDaemonThread(boolean useDaemonThread);
	
	int getThreadPriority();
	void setThreadPriority(int threadPriority);
	
	long getThreadLoopInterval();
	void setThreadLoopInterval(long threadLoopInterval);
	
	long getMaxShutdownWait();
	void setMaxShutdownWait(long maxShutdownWait);
	
	int getWriteRetries();
	void setWriteRetries(int writeRetries);
	
	int getMaxQueueSize();
	/**
	 * Use the (default) value of -1 to allow for a queue of indefinite depth (size).
	 * 
	 * @param maxQueueSize
	 */
    void setMaxQueueSize(int maxQueueSize);
}
