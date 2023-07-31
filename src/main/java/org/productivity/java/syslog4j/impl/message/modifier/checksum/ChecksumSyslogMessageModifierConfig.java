package org.productivity.java.syslog4j.impl.message.modifier.checksum;

import java.io.Serial;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.productivity.java.syslog4j.impl.message.modifier.AbstractSyslogMessageModifierConfig;

/**
* ChecksumSyslogMessageModifierConfig is an implementation of AbstractSyslogMessageModifierConfig
* that provides configuration for ChecksumSyslogMessageModifier.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: ChecksumSyslogMessageModifierConfig.java,v 1.2 2010/02/04 03:41:38 cvs Exp $
*/
public class ChecksumSyslogMessageModifierConfig extends AbstractSyslogMessageModifierConfig {
	@Serial private static final long serialVersionUID = -8298600135683882489L;

	protected Checksum checksum;
	protected boolean continuous = false;
	
	public static ChecksumSyslogMessageModifierConfig createCRC32() {

        return new ChecksumSyslogMessageModifierConfig(new CRC32());
	}
	
	public static ChecksumSyslogMessageModifierConfig createADLER32() {

        return new ChecksumSyslogMessageModifierConfig(new Adler32());
	}
	
	public ChecksumSyslogMessageModifierConfig(Checksum checksum) {
		this.checksum = checksum;
	}

	public Checksum getChecksum() {
		return this.checksum;
	}

	public void setChecksum(Checksum checksum) {
		this.checksum = checksum;
	}

	public boolean isContinuous() {
		return continuous;
	}

	public void setContinuous(boolean continuous) {
		this.continuous = continuous;
	}
}
