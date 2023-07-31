package org.productivity.java.syslog4j.impl.pool;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.ObjectPool;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.AbstractSyslog;
import org.productivity.java.syslog4j.impl.AbstractSyslogConfigIF;
import org.productivity.java.syslog4j.impl.AbstractSyslogWriter;

/**
* AbstractSyslogPoolFactory is an abstract implementation of the Apache Commons Pool
* BasePoolableObjectFactory.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractSyslogPoolFactory.java,v 1.5 2008/12/10 04:15:11 cvs Exp $
* @see org.productivity.java.syslog4j.impl.pool.generic.GenericSyslogPoolFactory
*/
public abstract class AbstractSyslogPoolFactory extends BasePoolableObjectFactory<AbstractSyslogWriter> {
	protected AbstractSyslog syslog = null;
	protected AbstractSyslogConfigIF syslogConfig = null;
	
	protected ObjectPool<AbstractSyslogWriter> pool = null;
	
	public AbstractSyslogPoolFactory() {
		//
	}

	public void initialize(AbstractSyslog abstractSyslog) throws SyslogRuntimeException {
		this.syslog = abstractSyslog;
		
		try {
			this.syslogConfig = (AbstractSyslogConfigIF) this.syslog.getConfig();

		} catch (ClassCastException cce) {
			throw new SyslogRuntimeException("config must implement AbstractSyslogConfigIF");
		}
		
		this.pool = createPool();
	}

	public AbstractSyslogWriter makeObject() {
		AbstractSyslogWriter syslogWriter = this.syslog.createWriter();
		
		if (this.syslogConfig.isThreaded()) {
			this.syslog.createWriterThread(syslogWriter);
		}
		
		return syslogWriter;
	}

	public void destroyObject(AbstractSyslogWriter writer) throws Exception {

		writer.shutdown();
		
		super.destroyObject(writer);
	}
	
	public abstract ObjectPool<AbstractSyslogWriter> createPool() throws SyslogRuntimeException;
	
	public AbstractSyslogWriter borrowSyslogWriter() throws Exception {
        return this.pool.borrowObject();
	}

	public void returnSyslogWriter(AbstractSyslogWriter syslogWriter) throws Exception {
		this.pool.returnObject(syslogWriter);
	}

	public void clear() throws Exception {
		this.pool.clear();
	}
	
	public void close() throws Exception {
		this.pool.close();
	}
}
