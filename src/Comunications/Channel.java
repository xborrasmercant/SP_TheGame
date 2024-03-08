package Comunications;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import Local.Ball;

public class Channel implements Runnable {

    private TGCommunications tgCommunications;
    private HealthCheck HCChannel;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket SOCKET;
    private int sendTime;
    private long receivedTime;
    private Interlocutor interlocutor;
    private boolean isClient;

    public Channel(TGCommunications tgCommunications, Interlocutor interlocutor) {
        this.tgCommunications = tgCommunications;
        this.interlocutor = interlocutor;
    }

    @Override
    public void run() {
        while (SOCKET != null) {
            System.out.println("\n" + "[CHN] Waiting for message...");
            this.dataIn();
        }
    }

    public synchronized void setSocket(Socket SOCKET) {
        try {
            this.SOCKET = SOCKET;
            OutputStream os = SOCKET.getOutputStream();
            InputStream is = SOCKET.getInputStream();

            out = new ObjectOutputStream(os);
            in = new ObjectInputStream(is);

            HCChannel = new HealthCheck(this, 10000);
            new Thread(HCChannel).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void downChannel() {
        // If connection is not valid, delete the socket
        try {
            if (HCChannel != null) {
                HCChannel.stopExecution();
                HCChannel = null;
            }

            in.close();
            out.close();
            SOCKET.close();
            SOCKET = null;
            System.err.println("[CHN] Deleting socket...");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.tgCommunications.moveToDownChannel(this);
        }
    }

    public void sendData(Object object) {
        if (object instanceof Ball) {
            Ball b = (Ball) object;

            AppFrame appFrame = new AppFrame(AppFrameType.BALL, b);
            DataFrame data = new DataFrame(DataFrameType.APLICATION_FRAME, appFrame);

            try {
                out.writeObject(data);
                out.flush();
                System.out.println("[CHN-OUT] Object sent");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendPing() {
        try {
            DataFrame data = new DataFrame(DataFrameType.KEEP_ALIVE, "Ping");
            out.writeObject(data);
            out.flush();
            System.out.println("[CHN-OUT] Ping sent");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPingBack() {
        try {
            DataFrame data = new DataFrame(DataFrameType.KEEP_ALIVE_BACK, "PingBack");
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dataIn() {
        try {
            DataFrame data = (DataFrame) in.readObject();
            this.receivedTime = System.currentTimeMillis();
            if (data != null) {
                switch (data.getDataFrameType()) {
                    case APLICATION_FRAME -> {
                        this.tgCommunications.addObject((AppFrame) data.getSendObject());
                        System.out.println("[CHN-IN] Object received");
                    }
                    case INTERNAL_INFO, FRAME_REFUSED -> {
                        // No action needed, but block is here for future use or clarity.
                    }
                    case KEEP_ALIVE -> {
                        System.out.println("[CHN-IN] Ping received");
                        this.sendPingBack();
                    }
                    case KEEP_ALIVE_BACK -> {
                        System.out.println("[CHN-IN] Ping received again");
                        this.HCChannel.setKillSocket(false);
                    }
                    default -> System.out.println("[CHN-IN] Not handled message");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            downChannel();
        }
    }

    // Getters And Setters
    public TGCommunications getTgComunications() {
        return tgCommunications;
    }

    public void setTgComunications(TGCommunications tgCommunications) {
        this.tgCommunications = tgCommunications;
    }

    public HealthCheck getHealthCheck() {
        return HCChannel;
    }

    public void setHealthCheck(HealthCheck HCChannel) {
        this.HCChannel = HCChannel;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public Socket getSOCKET() {
        return SOCKET;
    }

    public int getSendTime() {
        return sendTime;
    }

    public void setSendTime(int sendTime) {
        this.sendTime = sendTime;
    }

    public long getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(long receivedTime) {
        this.receivedTime = receivedTime;
    }

    public Interlocutor getInterlocutor() {
        return interlocutor;
    }

    public void setInterlocutor(Interlocutor interlocutor) {
        this.interlocutor = interlocutor;
    }

    public boolean isClient() {
        return isClient;
    }

    public void setIsClient(boolean wasClient) {
        this.isClient = wasClient;
    }
}
