package org.productivity.java.syslog4j.impl.message.modifier.escape;

import java.io.Serial;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogMessageModifierIF;

/**
* HTMLEntityEscapeSyslogMessageModifier is an implementation of SyslogMessageModifierIF
* that safely escapes HTML entity characters.
* 
* <p>This modifier is useful for applications that display log content in browsers without
* properly escaping HTML characters.</p>
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: HTMLEntityEscapeSyslogMessageModifier.java,v 1.4 2010/10/28 05:10:57 cvs Exp $
*/
public class HTMLEntityEscapeSyslogMessageModifier implements SyslogMessageModifierIF {
	@Serial private static final long serialVersionUID = -8481773209240762293L;

	public static SyslogMessageModifierIF createDefault() {
		return new HTMLEntityEscapeSyslogMessageModifier();
	}

	public String modify(SyslogIF syslog, int facility, int level, String message) {
		if (message != null && !message.trim().isEmpty()) {

            return escapeHtml(message);
		}
		
		return message;
	}
	
	public boolean verify(String message) {
		// NO-OP
		
		return true;
	}
	
	/**
	 * escapeHtml(String) is based partly on the article posted here: http://www.owasp.org/index.php/How_to_perform_HTML_entity_encoding_in_Java
	 * with the addition of common characters and modifications for Java 1.4 support.
	 * 
	 * @param message
	 * @return Returns a message where any HTML entity characters are escaped.
	 */
	public static String escapeHtml(String message) {
		StringBuilder b = new StringBuilder(message.length());
	 	
		for (int i = 0; i < message.length(); i++) {
			char ch = message.charAt(i);

			if (ch == '<') {
				b.append("&lt;");
			} else if (ch == '>') {
	    	   b.append("&gt;");
			} else if (ch == '"') {
	    	   b.append("&quot;");
			} else if (ch == '\'') {
				b.append("&#39;");
			} else if (ch == '&') {
				b.append("&amp;");
			} else if (ch >= ' ' && ch <= '~') {
				b.append(ch);
			} else if (Character.isWhitespace(ch)) {
				b.append("&#").append((int) ch).append(";");
			} else if (Character.isISOControl(ch)) {
				// Ignore character
			} else if (Character.isDefined(ch)) {
				b.append("&#").append((int) ch).append(";");
			}	       
	    }
		
		return b.toString();
	 }

}
