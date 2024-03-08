package Comunications;

import java.net.Socket;
import java.util.ArrayList;

import Local.Ball;
import MainController.TGController;

public class TGCommunications {

    private ArrayList<Channel> downChannels = new ArrayList<>();
    private ArrayList<Channel> channels = new ArrayList<>();
    private ClientConnection clientConnection;
    private ServerConnection serverConnection;
    private TGController tgController;

    public TGCommunications(TGController tgController) {
        this.tgController = tgController;
        this.clientConnection = new ClientConnection(this);
        this.serverConnection = new ServerConnection(this);
        
        new Thread(clientConnection).start();
        new Thread(serverConnection).start();
        
        createChannels();
    }

    private synchronized void createChannels() {
        for (int i = 0; i < tgController.getPeerInterlocutors().size(); i++) {
            downChannels.add(new Channel(this, tgController.getPeerInterlocutors().get(i)));
        }
    }

    public void addChannel(Socket SOCKET, int index) {

        getDownChannels().get(index).setSocket(SOCKET);
        new Thread(getDownChannels().get(index)).start();
        getChannels().add(getDownChannels().get(index));
        getDownChannels().remove(index);
    }

    public synchronized void moveToDownChannel(Channel channel) {

        for (int i = 0; i < getChannels().size(); i++){
            if (getChannels().get(i) == channel){
                getDownChannels().add(channel);
                getChannels().remove(i);
            }
        }
    }

    public void sendObject(Ball ball, Enum<PeerLocation> peerLocation) {
    
        for(int i = 0; i < channels.size(); i++){
            if(channels.get(i).getInterlocutor().getPeerLocation() == peerLocation){
                channels.get(i).sendData(ball);
            }
        }
    }

    public void addObject(AppFrame appFrame) {
        this.tgController.addObject(appFrame);
    }

    // Getters And Setters
    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }

    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
    }

    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public TGController getTgController() {
        return tgController;
    }

    public void setTgController(TGController tgController) {
        this.tgController = tgController;
    }

    public ArrayList<Channel> getDownChannels() {
        return downChannels;
    }

    public void setDownChannels(ArrayList<Channel> downChannels) {
        this.downChannels = downChannels;
    }
}