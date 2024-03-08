package Comunications;

import java.net.Socket;

public class ClientConnection implements Runnable {
    private TGCommunications tgCommunications;
    private Socket SOCKET;
    private int PORT = 10000;

    public ClientConnection(TGCommunications tgCommunications) {
        this.tgCommunications = tgCommunications;
    }

    @Override
    public void run() {
        while (true) {
            this.SOCKET = new Socket();
            createConnection();
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void createConnection() {
        if (this.tgCommunications.getDownChannels() != null) {
            for (int i = 0; i < this.tgCommunications.getDownChannels().size(); i++) {
                try {
                    System.out.println("[CC] Connecting as client to channel " + i + "...");
                    this.SOCKET = new Socket(this.tgCommunications.getDownChannels().get(i).getInterlocutor().getIP(), PORT);
                    this.tgCommunications.addChannel(SOCKET, i);
                    System.out.println("[CC] Connection as client established.");
                } catch (Exception e) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    // Getters And Setters
    public TGCommunications getTgCommunications() {
        return tgCommunications;
    }

    public void setTgCommunications(TGCommunications tgCommunications) {
        this.tgCommunications = tgCommunications;
    }

    public Socket getSOCKET() {
        return SOCKET;
    }

    public void setSOCKET(Socket sOCKET) {
        SOCKET = sOCKET;
    }
}
