package org.productivity.java.syslog4j.server.impl.event.printstream;

import java.io.Serial;
import org.productivity.java.syslog4j.server.SyslogServerSessionEventHandlerIF;

public class SystemErrSyslogServerEventHandler extends PrintStreamSyslogServerEventHandler {
	@Serial private static final long serialVersionUID = -3496862887351690575L;

	public static SyslogServerSessionEventHandlerIF create() {
		return new SystemErrSyslogServerEventHandler();
	}
	
	public SystemErrSyslogServerEventHandler() {
		super(System.err);
	}
}
