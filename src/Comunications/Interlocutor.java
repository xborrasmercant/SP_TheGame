package Comunications;

public class Interlocutor {
    private String IP = "localhost";
    private PeerLocation peerLocation;
    
    public Interlocutor(String IP, PeerLocation peerLocation) {
        this.IP = IP;
        this.peerLocation = peerLocation;
    }
    
    public String getIP() {
        return IP;
    }

    public Enum<PeerLocation> getPeerLocation() {
        return peerLocation;
    }
}
