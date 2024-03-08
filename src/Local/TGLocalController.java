package Local;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import Comunications.PeerLocation;
import MainController.*;

public class TGLocalController {
    private TGModel tgModel;
    private TGViewer tgViewer;
    private GameRules rules;
    private TGController tgController;

    public TGLocalController(TGController controller) {
        this.tgModel = new TGModel(this);
        this.tgViewer = new TGViewer(this);
        this.rules = new GameRules(this);
        this.tgController = controller;
    }

    // METGODS
    public void checkBallColison(Ball mainBall, ArrayList<Ball> ballsColiding) {
        // Colisión contra otras bolas
        if (ballsColiding.size() > 0) {
            for (int i = 0; i < ballsColiding.size(); i++) {
                rules.ballToBallBounce(mainBall, ballsColiding.get(i));
            }
        }

        // Colision contra bordes
        if (rules.borderBounce(mainBall)) {
            mainBall.move();
        }
    }

    public Vector<Integer> getCanvasSize() {
        Vector<Integer> canvasSize = new Vector<>();
        canvasSize.add(tgViewer.getxSize());
        canvasSize.add(tgViewer.getySize());
        return canvasSize;
    }

    public void addBall(VectorDTO ballVelocity, CoordinatesDTO ballInitPosition) {
        tgModel.addBall(ballVelocity, ballInitPosition);
    }

    public void addBall(Ball ball) {
        CoordinatesDTO newPosition = new CoordinatesDTO(0, ball.getPosition().getY());
        
        if (ball.getPosition().getX() >= getCanvasSize().get(0)/2) {
            
            newPosition.setX(0);
            ball.setPosition(newPosition);
        } else if (ball.getPosition().getX() < getCanvasSize().get(0)/2) {
            
            newPosition.setX(getCanvasSize().get(0));
            ball.setPosition(newPosition);
        }

        tgModel.addBall(ball);
    }

    public void sendObject(Ball ball, Enum<PeerLocation> direc) {
        tgModel.removeBall(ball);
        
        if(!ball.isAlive()){
            tgController.getTgComunications().sendObject(ball, direc);
        }
    }

    // GETTERS AND SETTERS
    public List<VisualObject> getVisualElements() {
        return this.tgModel.getVisualElements();
    }

    public TGModel getTgModel() {
        return tgModel;
    }

    public void setTgModel(TGModel tgModel) {
        this.tgModel = tgModel;
    }

    public TGViewer getTgViewer() {
        return tgViewer;
    }

    public void setTgViewer(TGViewer tgViewer) {
        this.tgViewer = tgViewer;
    }

    public GameRules getRules() {
        return rules;
    }

    public void setRules(GameRules rules) {
        this.rules = rules;
    }

    public TGController getTgController() {
        return tgController;
    }

    public void setTgController(TGController tgController) {
        this.tgController = tgController;
    }
}
