package MainController;

import Local.*;
import java.util.ArrayList;
import Comunications.*;

public class TGController {
    private TGLocalController tgLocalController;
    private TGCommunications tgCommunications;
    private ArrayList<Interlocutor> peerInterlocutors = new ArrayList<>();

    public static void main(String[] args) {
        TGController gameController = new TGController();
        gameController.init();
    }

    public void init() {
        peerInterlocutors.add(new Interlocutor("localhost", PeerLocation.EAST));
        peerInterlocutors.add(new Interlocutor("localhost", PeerLocation.WEST));
        tgLocalController = new TGLocalController(this);
        tgCommunications = new TGCommunications(this);
    }

    public void addObject(AppFrame appFrame) {
        try {
            switch (appFrame.getAppFrameType()) {
                case BALL:
                    System.out.println("[ADD] Adding ball...");
                    tgLocalController.addBall((Ball) appFrame.getObject());
                    break;
                case ASTEROID:

                    break;
                case BULLET:

                    break;
                case CONTROL_ACTION:

                    break;
                case MISSILE:

                    break;
                case PLAYER_SHIP:

                    break;
                case PLAYER_SHIP_ACTION:

                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Interlocutor> getPeerInterlocutors() {
        return peerInterlocutors;
    }

    public void setPeerInterlocutors(ArrayList<Interlocutor> peerInterlocutors) {
        this.peerInterlocutors = peerInterlocutors;
    }

    public TGLocalController getTgLocalController() {
        return tgLocalController;
    }

    public TGCommunications getTgComunications() {
        return tgCommunications;
    }
}