package org.productivity.java.syslog4j.server;

import java.net.SocketAddress;

public interface SyslogServerSessionEventHandlerIF extends SyslogServerEventHandlerIF {
	Object sessionOpened(SyslogServerIF syslogServer, SocketAddress socketAddress);
	void event(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, SyslogServerEventIF event);
	void exception(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, Exception exception);
	void sessionClosed(Object session, SyslogServerIF syslogServer, SocketAddress socketAddress, boolean timeout);
}
