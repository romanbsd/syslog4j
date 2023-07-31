package org.productivity.java.syslog4j.impl.net.tcp.ssl;

import java.io.Serial;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.net.tcp.TCPNetSyslog;

/**
* SSLTCPNetSyslog is an extension of AbstractSyslog that provides support for
* TCP/IP-based (over SSL/TLS) syslog clients.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SSLTCPNetSyslog.java,v 1.1 2009/03/29 17:38:58 cvs Exp $
*/
public class SSLTCPNetSyslog extends TCPNetSyslog {
	@Serial private static final long serialVersionUID = 2766654802524487317L;

	public void initialize() throws SyslogRuntimeException {
		super.initialize();
		
		SSLTCPNetSyslogConfigIF sslTcpNetSyslogConfig = (SSLTCPNetSyslogConfigIF) this.tcpNetSyslogConfig;
		
		String keyStore = sslTcpNetSyslogConfig.getKeyStore();
		
		if (keyStore != null && !keyStore.trim().isEmpty()) {
			System.setProperty("javax.net.ssl.keyStore",keyStore);
		}

		String keyStorePassword = sslTcpNetSyslogConfig.getKeyStorePassword();
		
		if (keyStorePassword != null && !keyStorePassword.trim().isEmpty()) {
			System.setProperty("javax.net.ssl.keyStorePassword",keyStorePassword);
		}

		String trustStore = sslTcpNetSyslogConfig.getTrustStore();
		
		if (trustStore != null && !trustStore.trim().isEmpty()) {
			System.setProperty("javax.net.ssl.trustStore",trustStore);
		}

		String trustStorePassword = sslTcpNetSyslogConfig.getTrustStorePassword();
		
		if (trustStorePassword != null && !trustStorePassword.trim().isEmpty()) {
			System.setProperty("javax.net.ssl.trustStorePassword",trustStorePassword);
		}
	}
}
