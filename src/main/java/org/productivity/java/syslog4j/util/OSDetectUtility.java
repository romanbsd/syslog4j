package org.productivity.java.syslog4j.util;

/**
* OSDetectUtility provides operating system detection used to determine
* whether Syslog4j is running on a Unix platform.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: OSDetectUtility.java,v 1.4 2008/11/14 04:31:59 cvs Exp $
*/
public final class OSDetectUtility {
	private final static String[] UNIX_PLATFORMS = {
		"Linux",
		"Mac OS",
		"Solaris",
		"SunOS",
		"MPE/iX",
		"HP-UX",
		"AIX",
		"OS/390",
		"FreeBSD",
		"Irix",
		"Digital Unix",
		"FreeBSD",
		"OSF1",
		"OpenVMS"
	};

	private final static String[] WINDOWS_PLATFORMS = {
		"Windows",
		"OS/2"
	};
	
	private static final boolean UNIX;
	private static final boolean WINDOWS;
	
	private OSDetectUtility() {
		//
	}
	
	private static boolean isMatch(String[] platforms) {
		boolean match = false;
		
		String osName = System.getProperty("os.name");
		
		if (osName != null && !osName.trim().isEmpty()) {
			osName = osName.toLowerCase();

            for (String s : platforms) {
                String platform = s.toLowerCase();

                if (osName.contains(platform)) {
                    match = true;
                    break;
                }
            }
		}
		
		return match;
	}

	static {
		UNIX = isMatch(UNIX_PLATFORMS);
		WINDOWS = isMatch(WINDOWS_PLATFORMS);
	}
	
	public static boolean isUnix() {
		return UNIX;
	}

	public static boolean isWindows() {
		return WINDOWS;
	}
}
