package org.productivity.java.syslog4j.server.impl.event.structured;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatterBuilder;
import java.io.Serial;
import org.productivity.java.syslog4j.SyslogConstants;
import org.productivity.java.syslog4j.impl.message.structured.StructuredSyslogMessage;
import org.productivity.java.syslog4j.server.impl.event.SyslogServerEvent;

import java.net.InetAddress;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * SyslogServerStructuredEvent provides an implementation of the
 * SyslogServerEventIF interface that supports receiving of structured syslog
 * messages, as defined in:
 *
 * <p>
 * http://tools.ietf.org/html/draft-ietf-syslog-protocol-23#section-6
 * </p>
 *
 * <p>
 * Syslog4j is licensed under the Lesser GNU Public License v2.1. A copy of the
 * LGPL license is available in the META-INF folder in all distributions of
 * Syslog4j and in the base directory of the "doc" ZIP.
 * </p>
 *
 * @author Manish Motwani
 * @version $Id: StructuredSyslogServerEvent.java,v 1.6 2011/01/11 05:11:13 cvs Exp $
 */
public class StructuredSyslogServerEvent extends SyslogServerEvent {
	@Serial private static final long serialVersionUID = 1676499796406044315L;

	protected String applicationName = SyslogConstants.STRUCTURED_DATA_APP_NAME_DEFAULT_VALUE;
	protected String processId = null;
	protected OffsetDateTime dateTime = null;
	protected DateTimeFormatter dateTimeFormatter = null;

	public StructuredSyslogServerEvent(final byte[] message, int length, InetAddress inetAddress) {
		super();

		initialize(message,length,inetAddress);
		parse();
	}

	public StructuredSyslogServerEvent(final String message, InetAddress inetAddress) {
		super();

		initialize(message,inetAddress);
		parse();
	}

	public DateTimeFormatter getDateTimeFormatter() {
		if (dateTimeFormatter == null) {
			dateTimeFormatter = new DateTimeFormatterBuilder()
					.appendPattern("yyyy-MM-dd'T'HH:mm:ss")
					.optionalStart()
					.appendPattern(".SSS")
					.optionalEnd()
					.appendPattern("XXX")
					.optionalStart()
					.appendLiteral('Z')
					.optionalEnd()
					.toFormatter();
		}
		return dateTimeFormatter;
	}

	public void setDateTimeFormatter(Object dateTimeFormatter) {
		this.dateTimeFormatter = (DateTimeFormatter) dateTimeFormatter;
	}

	protected void parseApplicationName() {
		int i = this.message.indexOf(' ');

		if (i > -1) {
			this.applicationName = this.message.substring(0, i).trim();
			this.message = this.message.substring(i + 1);
			parseProcessId();
		}

		if (SyslogConstants.STRUCTURED_DATA_NILVALUE.equals(this.applicationName)) {
			this.applicationName = null;
		}
	}

	protected void parseProcessId() {
		int i = this.message.indexOf(' ');

		if (i > -1) {
			this.processId = this.message.substring(0, i).trim();
			this.message = this.message.substring(i + 1);
		}

		if (SyslogConstants.STRUCTURED_DATA_NILVALUE.equals(this.processId)) {
			this.processId = null;
		}
	}

	protected void parseDate() {
		// skip VERSION field
		int i = this.message.indexOf(' ');
		this.message = this.message.substring(i + 1);

		// parse the date
		i = this.message.indexOf(' ');

		if (i > -1) {
			String dateString = this.message.substring(0, i).trim();

			try {
				DateTimeFormatter formatter = getDateTimeFormatter();
				LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
				this.dateTime = localDateTime.atOffset(ZoneOffset.UTC);
				this.date = Date.from(dateTime.toInstant());

				this.message = this.message.substring(dateString.length() + 1);

			} catch (Exception e) {
				// Not structured date format, try super one
				super.parseDate();
			}
		}
	}

	protected void parseHost() {
		int i = this.message.indexOf(' ');

		if (i > -1) {
			this.host = this.message.substring(0,i).trim();
			this.message = this.message.substring(i+1);

			parseApplicationName();
		}
	}

	public String getApplicationName() {
		return this.applicationName;
	}

	public String getProcessId() {
		return this.processId;
	}

	public StructuredSyslogMessage getStructuredMessage() {
		try {
			return StructuredSyslogMessage.fromString(getMessage());

		} catch (IllegalArgumentException e) {
			// throw new SyslogRuntimeException(
			// "Message received is not a valid structured message: "
			// + getMessage(), e);
			return new StructuredSyslogMessage(null,null,getMessage());
		}
	}
}
