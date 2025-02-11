package org.productivity.java.syslog4j.test.message.structured;

//
// Cleversafe open-source code header - Version 1.2 - February 15, 2008
//
// Cleversafe Dispersed Storage(TM) is software for secure, private and
// reliable storage of the world's data using information dispersal.
//
// Copyright (C) 2005-2008 Cleversafe, Inc.
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,
// USA.
//
// Contact Information: Cleversafe, 224 North Desplaines Street, Suite 500
// Chicago IL 60661
// email licensing@cleversafe.org
//
// END-OF-HEADER
// -----------------------
// @author: mmotwani
//
// Date: Jul 15, 2009
// ---------------------

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.productivity.java.syslog4j.impl.message.structured.StructuredSyslogMessage;

public class StructuredSyslogMessageTest extends TestCase {
    public void testFromString1() {
        final String messageStr = "msgId1 [0@0] my message!!";

        final StructuredSyslogMessage message = StructuredSyslogMessage.fromString(messageStr);

        assertEquals("msgId1 [0@0] my message!!", message.toString());
        assertEquals(-108931075, message.hashCode());

        assertEquals("my message!!", message.getMessage());
        assertEquals("msgId1", message.getMessageId());
        assertEquals(0, message.getStructuredData().size());
    }

    public void testFromString2() {
        final String messageStr = "msgId1 [invalid SD] my message!!";

        try {
            StructuredSyslogMessage.fromString(messageStr);
            fail();
        } catch (IllegalArgumentException iae) {
            //
        }
    }

    public void testFromString3() {
        final String messageStr = "msgId1 [data1 a=b] my message!!";

        try {
            StructuredSyslogMessage.fromString(messageStr);
            fail();
        } catch (IllegalArgumentException iae) {
            //
        }
    }

    public void testFromString4() {
        final String messageStr = "msgId1 [data1 a=\"b] my message!!";

        try {
            StructuredSyslogMessage.fromString(messageStr);
            fail();
        } catch (IllegalArgumentException iae) {
            //
        }
    }

    public void testFromString5() {
        final String messageStr = "msgId1 [data1 a=b\"] my message!!";

        try {
            StructuredSyslogMessage.fromString(messageStr);
            fail();
        } catch (IllegalArgumentException iae) {
            //
        }
    }

    public void testFromString6() {
        final String messageStr = "msgId1 [data1 a=\"b\"] my message!!";

        final StructuredSyslogMessage message = StructuredSyslogMessage.fromString(messageStr);

        assertEquals("my message!!", message.getMessage());
        assertEquals("msgId1", message.getMessageId());
        assertEquals(1, message.getStructuredData().size());
        assertEquals(1, message.getStructuredData().get("data1").size());
        assertEquals("b", message.getStructuredData().get("data1").get("a"));
    }

    public void testFromString7() {
        final String messageStr =
                "msgId1 [data1 a=\"b\"][data2 a=\"b\" x1=\"c1\" n2=\"f5\"] my message!!";

        final StructuredSyslogMessage message = StructuredSyslogMessage.fromString(messageStr);

        assertEquals("my message!!", message.getMessage());
        assertEquals("msgId1", message.getMessageId());
        assertEquals(2, message.getStructuredData().size());
        assertEquals(1, message.getStructuredData().get("data1").size());
        assertEquals(3, message.getStructuredData().get("data2").size());
        assertEquals("b", message.getStructuredData().get("data1").get("a"));
        assertEquals("b", message.getStructuredData().get("data2").get("a"));
        assertEquals("c1", message.getStructuredData().get("data2").get("x1"));
        assertEquals("f5", message.getStructuredData().get("data2").get("n2"));
    }

    public void testCreateMessage1() {
        final StructuredSyslogMessage message = new StructuredSyslogMessage("msgId", null, null);
        assertEquals("msgId [0@0]", message.createMessage());
    }

    public void testCreateMessage2() {
        final StructuredSyslogMessage message =
                new StructuredSyslogMessage("msgId", null, "my message");
        assertEquals("msgId [0@0] my message", message.createMessage());
    }

    public void testCreateMessage3() {
        final StructuredSyslogMessage message =
                new StructuredSyslogMessage("msgId", new HashMap<>(), "my message");
        assertEquals("msgId [0@0] my message", message.createMessage());
    }

    public void testCreateMessage4() {
        final Map<String, Map<String, String>> map = new HashMap<>();
        final StructuredSyslogMessage message =
                new StructuredSyslogMessage("msgId", map, "my message");
        assertEquals("msgId [0@0] my message", message.createMessage());
    }
}
