package org.productivity.java.syslog4j.impl.message.structured;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.productivity.java.syslog4j.SyslogConstants;
import org.productivity.java.syslog4j.impl.message.AbstractSyslogMessage;

/**
 * SyslogStructuredMessage extends AbstractSyslogMessage's ability to provide
 * support for turning POJO (Plain Ol' Java Objects) into Syslog messages. It
 * adds support for structured syslog messages as specified by
 * draft-ietf-syslog-protocol-23. More information here:
 *
 * <p>
 * <a href="http://tools.ietf.org/html/draft-ietf-syslog-protocol-23#section-6">...</a>
 * </p>
 *
 * <p>
 * Syslog4j is licensed under the Lesser GNU Public License v2.1. A copy of the
 * LGPL license is available in the META-INF folder in all distributions of
 * Syslog4j and in the base directory of the "doc" ZIP.
 * </p>
 *
 * @author Manish Motwani
 * @version $Id: StructuredSyslogMessage.java,v 1.5 2010/09/11 16:49:24 cvs Exp $
 */
public class StructuredSyslogMessage extends AbstractSyslogMessage implements StructuredSyslogMessageIF {
    @Serial private static final long serialVersionUID = 3669887659567965965L;

    private String messageId;
    private Map<String, Map<String, String>> structuredData;
    private String message;

    private StructuredSyslogMessage() {
        this.messageId = null;
        this.message = null;
        this.structuredData = null;
    }

    /**
     * Constructs the {@link StructuredSyslogMessage} using MSGID,
     * STRUCTURED-DATA and MSG fields, as described in:
     *
     * <p>
     * <a href="http://tools.ietf.org/html/draft-ietf-syslog-protocol-23#section-6">...</a>
     * </p>
     * <p>
     * The Map must be a String -> (Map of String -> String), which encompasses
     * the STRUCTURED-DATA field described in above document.
     *
     * @param messageId
     * @param structuredData
     * @param message
     */
    public StructuredSyslogMessage(
            final String messageId,
            final Map<String, Map<String, String>> structuredData, final String message) {
        super();
        this.messageId = messageId;
        this.structuredData = structuredData;
        this.message = message;

        ensureCorrectMapType();
    }

    private void ensureCorrectMapType() {
        if (!(getStructuredData() == null)) {
            Set<Entry<String, Map<String, String>>> sdEntrySet = getStructuredData().entrySet();
            for (Map.Entry<String, Map<String, String>> sdEntry : sdEntrySet) {
                if (sdEntry.getKey() == null) {
                    throw new IllegalArgumentException(
                            "Structured data map must be a map of String -> (Map of String,String)");
                }
                if (sdEntry.getValue() == null) {
                    throw new IllegalArgumentException(
                            "Structured data map must be a map of String -> (Map of String,String)");
                }

                Set<Entry<String, String>> entrySet = sdEntry.getValue().entrySet();
                for (Map.Entry<String,String> entry: entrySet) {
                    if (entry.getKey() == null) {
                        throw new IllegalArgumentException(
                                "Structured data map must be a map of String -> (Map of String,String)");
                    }
                    if (entry.getValue() == null) {
                        throw new IllegalArgumentException(
                                "Structured data map must be a map of String -> (Map of String,String)");
                    }
                }
            }
        }
    }

    /**
     * Parses and loads a {@link StructuredSyslogMessage} from string.
     *
     * @param syslogMessageStr
     * @return Returns an instance of StructuredSyslogMessage.
     */
    public static StructuredSyslogMessage fromString(
            final String syslogMessageStr) {
        final StructuredSyslogMessage syslogMessage = new StructuredSyslogMessage();
        syslogMessage.deserialize(syslogMessageStr);
        return syslogMessage;
    }

    private void deserialize(final String stringMessage) {
        // Check correct format
        if (stringMessage.indexOf('[') <= 0) {
            throw new IllegalArgumentException("Invalid Syslog string format: "
                    + stringMessage);
        }

        // Divide the string in 2 sections
        final String syslogHeader = stringMessage.substring(0, stringMessage
                .indexOf('['));
        String structuredDataString = stringMessage.substring(stringMessage
                .indexOf('['), stringMessage.lastIndexOf(']') + 1);

        if ((stringMessage.lastIndexOf(']') + 2) <= stringMessage.length()) {
            this.message = stringMessage.substring(stringMessage.lastIndexOf(']') + 2);
        } else {
            this.message = "";
        }

        // Split into tokens
        final String[] tokens = syslogHeader.split(" ");

        // Check number of tokens must be 1 -- rest of the header should already
        // be stripped
        if (tokens.length != 1) {
            throw new IllegalArgumentException("Invalid Syslog string format: "
                    + stringMessage);
        }

        this.messageId = SyslogConstants.STRUCTURED_DATA_NILVALUE.equals(tokens[0]) ? null
                : tokens[0];

        this.structuredData = new HashMap<>();
        if (!SyslogConstants.STRUCTURED_DATA_EMPTY_VALUE.equals(structuredDataString)) {
            while (!structuredDataString.isEmpty()) {
                if (!structuredDataString.startsWith("[")
                        || structuredDataString.indexOf(']') == -1) {
                    throw new IllegalArgumentException(
                            "Invalid structured data format in Syslog message: "
                                    + stringMessage);
                }

                final String structuredDataIteration = structuredDataString
                        .substring(1, structuredDataString.indexOf(']'));

                final Map<String, String> iterMap = new HashMap<>();

                final String[] params = structuredDataIteration.split(" ");

                for (int i = 1; i < params.length; i++) {
                    final String[] paramIter = getStrings(stringMessage, params[i]);

                    iterMap.put(paramIter[0], paramIter[1].substring(
                            1,
                            paramIter[1].length() - 1));
                }

                this.structuredData.put(params[0], iterMap);

                if (structuredDataString.indexOf(']') != structuredDataString
                        .lastIndexOf(']')) {
                    structuredDataString = structuredDataString
                            .substring(structuredDataString.indexOf(']') + 1);
                } else {
                    structuredDataString = "";
                }
            }
        }
    }

    private static String[] getStrings(String stringMessage, String params) {
        final String[] paramIter = params.split("=");
        if (paramIter.length != 2) {
            throw new IllegalArgumentException(
                    "Invalid structured data format in Syslog message: "
                            + stringMessage);
        }

        if (!paramIter[1].startsWith("\"")
                || !paramIter[1].endsWith("\"")) {
            throw new IllegalArgumentException(
                    "Invalid structured data format in Syslog message: "
                            + stringMessage);
        }
        return paramIter;
    }

    /**
     * Returns the MSGID field of the structured message format, as described
     * in:
     *
     * <p>
     * <a href="http://tools.ietf.org/html/draft-ietf-syslog-protocol-23#section-6">...</a>
     * </p>
     *
     * @return Returns the MSG ID field.
     */
    public String getMessageId() {
        return this.messageId;
    }

    /**
     * Returns the structured data map. The Map is a String -> (Map of String ->
     * String), which encompasses the STRUCTURED-DATA field, as described in:
     *
     * <p>
     * <a href="http://tools.ietf.org/html/draft-ietf-syslog-protocol-23#section-6">...</a>
     * </p>
     *
     * @return Returns a Map object containing structured data.
     */
    public Map<String, Map<String, String>> getStructuredData() {
        return this.structuredData;
    }

    /**
     * Returns the MSG field of the structured message format, as described in:
     *
     * <p>
     * <a href="http://tools.ietf.org/html/draft-ietf-syslog-protocol-23#section-6">...</a>
     * </p>
     *
     * @return Returns the MSG field.
     */
    public String getMessage() {
        return this.message;
    }

    /*
     * (non-Javadoc)
     *
     * @seeorg.productivity.java.syslog4j.impl.message.AbstractSyslogMessage#
     * createMessage()
     */
    public String createMessage() {
        return serialize();
    }

    private String serialize() {
        if (StructuredSyslogMessage.checkIsPrintable(getMessageId())) {
            throw new IllegalArgumentException("Invalid message id: "
                    + getMessageId());
        }

        final StringBuffer sb = new StringBuffer();

        sb.append(StructuredSyslogMessage.nilProtect(getMessageId()));
        sb.append(' ');

        if (getStructuredData() == null || getStructuredData().isEmpty()) {
            // This is not desired, but rsyslogd does not store version 1 syslog
            // message correctly if
            // there is no
            // structured data present
            sb.append(SyslogConstants.STRUCTURED_DATA_EMPTY_VALUE);
        } else {
            Set<Entry<String, Map<String, String>>> sdEntrySet = getStructuredData().entrySet();
            for (Map.Entry<String, Map<String, String>> sdElement : sdEntrySet) {
                final String sdId = sdElement.getKey();

                if (sdId == null || sdId.isEmpty()
                        || StructuredSyslogMessage.checkIsPrintable(sdId)) {
                    throw new IllegalArgumentException(
                            "Illegal structured data id: " + sdId);
                }

                sb.append('[').append(sdId);

                Map<String, String> sdParams = sdElement.getValue();

                if (sdParams != null) {
                    Set<Entry<String, String>> entrySet = sdParams.entrySet();
                    for (Map.Entry<String,String> entry : entrySet) {
                        final String paramName = entry.getKey();
                        final String paramValue = entry.getValue();

                        if (paramName == null
                                || paramName.isEmpty()
                                || StructuredSyslogMessage
                                .checkIsPrintable(paramName)) {
                            throw new IllegalArgumentException(
                                    "Illegal structured data parameter name: "
                                            + paramName);
                        }

                        if (paramValue == null) {
                            throw new IllegalArgumentException(
                                    "Null structured data parameter value for parameter name: "
                                            + paramName);
                        }

                        sb.append(' ');
                        sb.append(paramName);
                        sb.append('=').append('"');
                        StructuredSyslogMessage.sdEscape(sb, paramValue);
                        sb.append('"');
                    }
                }

                sb.append(']');
            }
        }

        if (getMessage() != null && !getMessage().isEmpty()) {
            sb.append(' ');
            sb.append(StructuredSyslogMessage.nilProtect(getMessage()));
        }

        return sb.toString();
    }

    public static void sdEscape(final StringBuffer sb, final String value) {
        for (int i = 0; i < value.length(); i++) {
            final char c = value.charAt(i);

            if (c == '"' || c == '\\' || c == ']') {
                sb.append('\\');
            }

            sb.append(c);
        }
    }

    public static boolean checkIsPrintable(final String value) {
        if (value == null) {
            return false;
        }

        for (int i = 0; i < value.length(); i++) {
            final char c = value.charAt(i);

            if (c < 33 || c > 126) {
                return true;
            }
        }

        return false;
    }

    public static String nilProtect(final String value) {
        if (value == null || value.trim().isEmpty()) {
            return SyslogConstants.STRUCTURED_DATA_NILVALUE;
        }

        return value;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result
                + ((messageId == null) ? 0 : messageId.hashCode());
        result = prime * result
                + ((structuredData == null) ? 0 : structuredData.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        StructuredSyslogMessage other = (StructuredSyslogMessage) obj;

        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }

        if (messageId == null) {
            if (other.messageId != null) {
                return false;
            }
        } else if (!messageId.equals(other.messageId)) {
            return false;
        }

        if (structuredData == null) {
            return other.structuredData == null;
        } else {
            return structuredData.equals(other.structuredData);
        }
    }

    public String toString() {
        return serialize();
    }
}
