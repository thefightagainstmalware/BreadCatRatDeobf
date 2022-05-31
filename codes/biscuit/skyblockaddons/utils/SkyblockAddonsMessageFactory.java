/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.message.Message
 *  org.apache.logging.log4j.message.MessageFactory
 *  org.apache.logging.log4j.message.ParameterizedMessage
 *  org.apache.logging.log4j.message.SimpleMessage
 */
package codes.biscuit.skyblockaddons.utils;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.logging.log4j.message.SimpleMessage;

public class SkyblockAddonsMessageFactory
implements MessageFactory {
    private String loggerName;

    public SkyblockAddonsMessageFactory(String loggerName) {
        this.loggerName = loggerName;
    }

    public Message newMessage(Object message) {
        return new SimpleMessage(this.getMessageWithLoggerName(message.toString()));
    }

    public Message newMessage(String message) {
        return new SimpleMessage(this.getMessageWithLoggerName(message));
    }

    public Message newMessage(String message, Object ... params) {
        return new ParameterizedMessage(this.getMessageWithLoggerName(message), params);
    }

    private String getMessageWithLoggerName(String message) {
        return String.format("[%s/%s] %s", "SkyblockAddons", this.loggerName, message);
    }
}

