package org.productivity.java.syslog4j.test.split.base;

import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.AbstractSyslogConfig;

public class SplitSyslogConfig extends AbstractSyslogConfig {

	@Override
	public Class<? extends SyslogIF> getSyslogClass() {
		return SplitSyslog.class;
	}

	public String getHost() {
		return null;
	}

	public int getPort() {
		return 0;
	}

	public void setHost(String host) throws SyslogRuntimeException {
		//
	}

	public void setSocketPath(String path) throws SyslogRuntimeException {
		//
	}

	public void setPort(int port) throws SyslogRuntimeException {
		//
	}

	public int getMaxQueueSize() {
		return 0;
	}

	public void setMaxQueueSize(int maxQueueSize) {
		//
	}
	
	
}
