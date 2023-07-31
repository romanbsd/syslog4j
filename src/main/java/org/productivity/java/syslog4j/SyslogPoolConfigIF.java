package org.productivity.java.syslog4j;

import java.io.Serializable;

/**
* SyslogPoolConfigIF is an interface which provides configuration support
* for the Apache Commons Pool.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: SyslogPoolConfigIF.java,v 1.2 2009/03/29 17:38:58 cvs Exp $
*/
public interface SyslogPoolConfigIF extends Serializable {
	int getMaxActive();
	void setMaxActive(int maxActive);
	
	int getMaxIdle();
	void setMaxIdle(int maxIdle);
	
	long getMaxWait();
	void setMaxWait(long maxWait);
	
	long getMinEvictableIdleTimeMillis();
	void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis);
	
	int getMinIdle();
	void setMinIdle(int minIdle);
	
	int getNumTestsPerEvictionRun();
	void setNumTestsPerEvictionRun(int numTestsPerEvictionRun);
	
	long getSoftMinEvictableIdleTimeMillis();
	void setSoftMinEvictableIdleTimeMillis(long softMinEvictableIdleTimeMillis);
	
	boolean isTestOnBorrow();
	void setTestOnBorrow(boolean testOnBorrow);
	
	boolean isTestOnReturn();
	void setTestOnReturn(boolean testOnReturn);
	
	boolean isTestWhileIdle();
	void setTestWhileIdle(boolean testWhileIdle);
	
	long getTimeBetweenEvictionRunsMillis();
	void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis);
	
	byte getWhenExhaustedAction();
	void setWhenExhaustedAction(byte whenExhaustedAction);
}
