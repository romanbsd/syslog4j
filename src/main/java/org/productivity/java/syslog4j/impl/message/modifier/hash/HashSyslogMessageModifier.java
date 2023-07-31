package org.productivity.java.syslog4j.impl.message.modifier.hash;

import java.io.Serial;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.message.modifier.AbstractSyslogMessageModifier;
import org.productivity.java.syslog4j.util.Base64;
import org.productivity.java.syslog4j.util.SyslogUtility;

/**
* HashSyslogMessageModifier is an implementation of SyslogMessageModifierIF
* that provides support for Java Cryptographic hashes (MD5, SHA1, SHA256, etc.).
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: HashSyslogMessageModifier.java,v 1.5 2010/10/28 05:10:57 cvs Exp $
*/
public class HashSyslogMessageModifier extends AbstractSyslogMessageModifier {
	@Serial private static final long serialVersionUID = 7335757344826206953L;
	
	protected final HashSyslogMessageModifierConfig config;
	
	public static HashSyslogMessageModifier createMD5() {

        return new HashSyslogMessageModifier(HashSyslogMessageModifierConfig.createMD5());
	}
	
	public static HashSyslogMessageModifier createSHA1() {

        return new HashSyslogMessageModifier(HashSyslogMessageModifierConfig.createSHA1());
	}
	
	public static HashSyslogMessageModifier createSHA160() {
		 return createSHA1();
	}
	
	public static HashSyslogMessageModifier createSHA256() {

        return new HashSyslogMessageModifier(HashSyslogMessageModifierConfig.createSHA256());
	}
	
	public static HashSyslogMessageModifier createSHA384() {

        return new HashSyslogMessageModifier(HashSyslogMessageModifierConfig.createSHA384());
	}
	
	public static HashSyslogMessageModifier createSHA512() {

        return new HashSyslogMessageModifier(HashSyslogMessageModifierConfig.createSHA512());
	}
	
	public HashSyslogMessageModifier(HashSyslogMessageModifierConfig config) throws SyslogRuntimeException {
		super(config);
		
		this.config = config;
		
		if (this.config == null) {
			throw new SyslogRuntimeException("Hash config object cannot be null");			
		}

		if (this.config.getHashAlgorithm() == null) {
			throw new SyslogRuntimeException("Hash algorithm cannot be null");			
		}
		
		try {
			MessageDigest.getInstance(config.getHashAlgorithm());
			
		} catch (NoSuchAlgorithmException nsae){
			throw new SyslogRuntimeException(nsae);			
		}
	}
	
	protected MessageDigest obtainMessageDigest() {
		MessageDigest digest;
		
		try {
			digest = MessageDigest.getInstance(this.config.getHashAlgorithm());
			
		} catch (NoSuchAlgorithmException nsae) {
			throw new SyslogRuntimeException(nsae);
		}
		
		return digest;
	}

	public HashSyslogMessageModifierConfig getConfig() {
		return this.config;
	}
	
	public String modify(SyslogIF syslog, int facility, int level, String message) {
		byte[] messageBytes = SyslogUtility.getBytes(syslog.getConfig(),message);
		
		MessageDigest digest = obtainMessageDigest();
		byte[] digestBytes = digest.digest(messageBytes);

		String digestString = Base64.encodeBytes(digestBytes,Base64.DONT_BREAK_LINES);

        return message + this.config.getPrefix()
				+ digestString
				+ this.config.getSuffix();
	}

	public boolean verify(String message, String base64Hash) {
		byte[] hash = Base64.decode(base64Hash);
		
		return verify(message,hash);
	}
	
	public boolean verify(String message, byte[] hash) {
		byte[] messageBytes = SyslogUtility.getBytes(this.config,message);
		
		MessageDigest digest = obtainMessageDigest();
		byte[] digestBytes = digest.digest(messageBytes);
		
		return Arrays.equals(digestBytes,hash);
	}
}
