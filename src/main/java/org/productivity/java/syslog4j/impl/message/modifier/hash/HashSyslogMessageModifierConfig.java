package org.productivity.java.syslog4j.impl.message.modifier.hash;

import java.io.Serial;
import org.productivity.java.syslog4j.impl.message.modifier.AbstractSyslogMessageModifierConfig;

/**
* HashSyslogMessageModifierConfig is an implementation of AbstractSyslogMessageModifierConfig
* that provides configuration for HashSyslogMessageModifier.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: HashSyslogMessageModifierConfig.java,v 1.1 2008/11/10 04:38:37 cvs Exp $
*/
public class HashSyslogMessageModifierConfig extends AbstractSyslogMessageModifierConfig {
	@Serial private static final long serialVersionUID = -3148300281439874231L;
	
	protected String hashAlgorithm;
	
	public static HashSyslogMessageModifierConfig createMD5() {

        return new HashSyslogMessageModifierConfig("MD5");
	}
	
	public static HashSyslogMessageModifierConfig createSHA1() {

        return new HashSyslogMessageModifierConfig("SHA1");
	}
	
	public static HashSyslogMessageModifierConfig createSHA160() {
		 return createSHA1();
	}
	
	public static HashSyslogMessageModifierConfig createSHA256() {

        return new HashSyslogMessageModifierConfig("SHA-256");
	}
	
	public static HashSyslogMessageModifierConfig createSHA384() {

        return new HashSyslogMessageModifierConfig("SHA-384");
	}
	
	public static HashSyslogMessageModifierConfig createSHA512() {

        return new HashSyslogMessageModifierConfig("SHA-512");
	}
	
	public HashSyslogMessageModifierConfig(String hashAlgorithm) {
		this.hashAlgorithm = hashAlgorithm;
	}

	public String getHashAlgorithm() {
		return this.hashAlgorithm;
	}
	
	public void setHashAlgorithm(String hashAlgorithm) {
		this.hashAlgorithm = hashAlgorithm;
	}
}
