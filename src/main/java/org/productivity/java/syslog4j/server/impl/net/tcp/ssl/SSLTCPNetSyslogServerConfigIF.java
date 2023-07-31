package org.productivity.java.syslog4j.server.impl.net.tcp.ssl;

import org.productivity.java.syslog4j.server.impl.net.tcp.TCPNetSyslogServerConfigIF;

/**
* SSLTCPNetSyslogServerConfigIF provides configuration for SSLTCPNetSyslogServer.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SSLTCPNetSyslogServerConfigIF.java,v 1.1 2009/03/29 17:38:58 cvs Exp $
*/
public interface SSLTCPNetSyslogServerConfigIF extends TCPNetSyslogServerConfigIF {
	String getKeyStore();
	void setKeyStore(String keyStore);
	
	String getKeyStorePassword();
	void setKeyStorePassword(String keyStorePassword);

	String getTrustStore();
	void setTrustStore(String trustStore);
	
	String getTrustStorePassword();
	void setTrustStorePassword(String trustStorePassword);
}
