package Comunications;

import java.net.Socket;

public class PeerIdentificator implements Runnable{
    private Socket clientSocket;
    private ServerConnection serverConnection;
    
    public PeerIdentificator(ServerConnection serverConnection, Socket clientSocket) {
        this.serverConnection = serverConnection;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        if(clientSocket != null){
            System.out.println("[PID] Connection address: " + clientSocket.getInetAddress().getHostAddress());
            try {
                for (int i = 0; i < serverConnection.getTgCommunications().getDownChannels().size(); i++) {
                    // Si el programa se ejecuta en varios ordenadores descomentar el IF para que el ServerConector compruebe si la conexion es valida o no.
                    //if(this.serverConection.getTgComunications().getDownChannels().get(i).getInterlocutor().getIP() == clientSocket.getInetAddress().getHostAddress()){
                        serverConnection.getTgCommunications().addChannel(clientSocket, i);
                    //}
                }
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}