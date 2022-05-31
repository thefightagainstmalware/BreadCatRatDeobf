/*
 * Decompiled with CFR 0.152.
 */
package codes.biscuit.skyblockaddons.shadow.jagrosh.discordipc.entities.pipe;

import codes.biscuit.skyblockaddons.shadow.jagrosh.discordipc.IPCClient;
import codes.biscuit.skyblockaddons.shadow.jagrosh.discordipc.entities.Callback;
import codes.biscuit.skyblockaddons.shadow.jagrosh.discordipc.entities.Packet;
import codes.biscuit.skyblockaddons.shadow.jagrosh.discordipc.entities.pipe.Pipe;
import codes.biscuit.skyblockaddons.shadow.jagrosh.discordipc.entities.pipe.PipeStatus;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WindowsPipe
extends Pipe {
    private static final Logger LOGGER = LoggerFactory.getLogger(WindowsPipe.class);
    private final RandomAccessFile file;

    WindowsPipe(IPCClient ipcClient, HashMap<String, Callback> callbacks, String location) {
        super(ipcClient, callbacks);
        try {
            this.file = new RandomAccessFile(location, "rw");
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.file.write(b);
    }

    @Override
    public Packet read() throws IOException, JSONException {
        while ((this.status == PipeStatus.CONNECTED || this.status == PipeStatus.CLOSING) && this.file.length() == 0L) {
            try {
                Thread.sleep(50L);
            }
            catch (InterruptedException interruptedException) {}
        }
        if (this.status == PipeStatus.DISCONNECTED) {
            throw new IOException("Disconnected!");
        }
        if (this.status == PipeStatus.CLOSED) {
            return new Packet(Packet.OpCode.CLOSE, null);
        }
        Packet.OpCode op = Packet.OpCode.values()[Integer.reverseBytes(this.file.readInt())];
        int len = Integer.reverseBytes(this.file.readInt());
        byte[] d = new byte[len];
        this.file.readFully(d);
        Packet p = new Packet(op, new JSONObject(new String(d)));
        LOGGER.debug(String.format("Received packet: %s", p.toString()));
        if (this.listener != null) {
            this.listener.onPacketReceived(this.ipcClient, p);
        }
        return p;
    }

    @Override
    public void close() throws IOException {
        LOGGER.debug("Closing IPC pipe...");
        this.status = PipeStatus.CLOSING;
        this.send(Packet.OpCode.CLOSE, new JSONObject(), null);
        this.status = PipeStatus.CLOSED;
        this.file.close();
    }
}

