package org.productivity.java.syslog4j.server;

import java.util.Date;

import org.productivity.java.syslog4j.SyslogCharSetIF;

/**
* SyslogServerEventIF provides an extensible interface for Syslog4j
* server events.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogServerEventIF.java,v 1.4 2010/11/28 01:38:08 cvs Exp $
*/
public interface SyslogServerEventIF extends SyslogCharSetIF {
	/**
	 * Note: getRaw() may use System.arraycopy(..) each time it is called; best to call it once and store the result.
	 * 
	 * @return Returns the raw data received from the client.
	 */
    byte[] getRaw();
	
	int getFacility();
	void setFacility(int facility);

	Date getDate();
	void setDate(Date date);
	
	int getLevel();
	void setLevel(int level);
	
	String getHost();
	void setHost(String host);
	boolean isHostStrippedFromMessage();
		
	String getMessage();
	void setMessage(String message);
}
