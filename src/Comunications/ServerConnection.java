package Comunications;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection implements Runnable{
    private TGCommunications tgCommunications;
    private ServerSocket serverSocket;
    private int PORT = 8000;
    private Socket clientSocket;

    public ServerConnection(TGCommunications tgCommunications) {
        this.tgCommunications = tgCommunications;
        
        try {
            this.serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("[SC] Listening on port: " + PORT);
                createConnection();
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void createConnection() {
        try {
            System.out.println("[SC] Connecting as server...");
            clientSocket = serverSocket.accept();
            System.out.println("[SC] Connection as server established.");
            new Thread(new PeerIdentificator(this, clientSocket)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Getters And Setters
    public boolean isSocketClosed() {
        return serverSocket.isClosed();
    }

    public TGCommunications getTgCommunications() {
        return tgCommunications;
    }

    public void setTgCommunications(TGCommunications tgCommunications) {
        this.tgCommunications = tgCommunications;
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int port) {
        this.PORT = port;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket socket) {
        this.serverSocket = socket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
