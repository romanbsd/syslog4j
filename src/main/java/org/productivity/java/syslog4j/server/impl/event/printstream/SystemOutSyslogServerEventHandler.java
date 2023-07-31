package org.productivity.java.syslog4j.server.impl.event.printstream;

import java.io.Serial;
import org.productivity.java.syslog4j.server.SyslogServerSessionEventHandlerIF;

public class SystemOutSyslogServerEventHandler extends PrintStreamSyslogServerEventHandler {
	@Serial private static final long serialVersionUID = 1690551409588182601L;

	public static SyslogServerSessionEventHandlerIF create() {
		return new SystemOutSyslogServerEventHandler();
	}
	
	public SystemOutSyslogServerEventHandler() {
		super(System.out);
	}
}
