package org.productivity.java.syslog4j.server;

import java.io.Serializable;

public interface SyslogServerEventHandlerIF extends Serializable {
	void initialize(SyslogServerIF syslogServer);
	void destroy(SyslogServerIF syslogServer);
}
