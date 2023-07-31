package org.productivity.java.syslog4j.impl.net.tcp;

import org.productivity.java.syslog4j.impl.net.AbstractNetSyslogConfigIF;

/**
* TCPNetSyslogConfigIF is a configuration interface supporting TCP/IP-based
* Syslog implementations.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: TCPNetSyslogConfigIF.java,v 1.6 2010/10/29 03:14:12 cvs Exp $
*/
public interface TCPNetSyslogConfigIF extends AbstractNetSyslogConfigIF {
	byte[] getDelimiterSequence();
	void setDelimiterSequence(byte[] delimiterSequence);

	boolean isPersistentConnection();
	void setPersistentConnection(boolean persistentConnection);
	
	boolean isSoLinger();
	void setSoLinger(boolean soLinger);
	
	int getSoLingerSeconds();
	void setSoLingerSeconds(int soLingerSeconds);
	
	boolean isKeepAlive();
	void setKeepAlive(boolean keepAlive);
	
	boolean isReuseAddress();
	void setReuseAddress(boolean reuseAddress);
	
	boolean isSetBufferSize();
	void setSetBufferSize(boolean setBufferSize);

	int getFreshConnectionInterval();
	void setFreshConnectionInterval(int interval);
}
