package Local;

import java.util.Vector;

import Comunications.PeerLocation;

public class GameRules {
    private TGLocalController localController;

    public GameRules(TGLocalController controller) {
        this.localController = controller;
    }

    public boolean checkGate(int direcc) {

        for (int i = 0; i < this.localController.getTgController().getTgComunications().getChannels().size(); i++) {
            if (this.localController.getTgController().getTgComunications().getChannels().get(i).getInterlocutor()
                    .getPeerLocation() == PeerLocation.EAST && direcc == 1) {

                return true;
            } else if (this.localController.getTgController().getTgComunications().getChannels().get(i).getInterlocutor()
                    .getPeerLocation() == PeerLocation.WEST && direcc == 2) {

                return true;
            }
        }

        return false;
    }

    public void ballToBallBounce(Ball mainBall, Ball otherBall) {
        // Si tienen direcciones contrarias
        if ((mainBall.getVelocity().getX() > 0 && otherBall.getVelocity().getX() < 0)
                || (mainBall.getVelocity().getX() < 0 && otherBall.getVelocity().getX() > 0)) {

            mainBall.getVelocity().setX(mainBall.getVelocity().getX() * (-1));
            otherBall.getVelocity().setX(otherBall.getVelocity().getX() * (-1));
        }

        if ((mainBall.getVelocity().getX() > 0 && otherBall.getVelocity().getY() < 0)
                || (mainBall.getVelocity().getY() < 0 && otherBall.getVelocity().getY() > 0)) {

            mainBall.getVelocity().setY(mainBall.getVelocity().getY() * (-1));
            otherBall.getVelocity().setY(otherBall.getVelocity().getY() * (-1));
        }

        // Si las bolas tienen la misma direccion
        if ((mainBall.getVelocity().getX() > 0 && otherBall.getVelocity().getX() > 0)
                || (mainBall.getVelocity().getX() < 0 && otherBall.getVelocity().getX() < 0)) {

            mainBall.getVelocity().setX(mainBall.getVelocity().getX() * (-1));
        }

        if ((mainBall.getVelocity().getY() > 0 && otherBall.getVelocity().getY() > 0)
                || (mainBall.getVelocity().getY() < 0 && otherBall.getVelocity().getY() < 0)) {

            mainBall.getVelocity().setY(mainBall.getVelocity().getY() * (-1));
        }
        
        // Si una de las velocidades es 0
        if (otherBall.getVelocity().getX() == 0) {
            
            mainBall.getVelocity().setX(mainBall.getVelocity().getX() * (-1));
        }
        
        if (otherBall.getVelocity().getY() == 0) {
            
            mainBall.getVelocity().setY(mainBall.getVelocity().getY() * (-1));
        }
    }

    public boolean borderBounce(Ball mainBall) {
        // Colision contra bordes
        Vector<Integer> canvaSize = this.localController.getCanvasSize();
        CoordinatesDTO bouncePosition = new CoordinatesDTO();

        // Bordes Laterales
        if (mainBall.getNextPosition().getX() <= 0) {

            if (!this.checkGate(2)) {

                bouncePosition.setX(0);
                bouncePosition.setY(mainBall.getNextPosition().getY());

                mainBall.bounce(bouncePosition, 0);
                return true;
            } else {

                this.localController.sendObject(mainBall, PeerLocation.WEST);
                return false;
            }
        } else if (mainBall.getNextPosition().getX() >= canvaSize.get(0)) {

            if (!this.checkGate(1)) {

                bouncePosition.setX(canvaSize.get(0));
                bouncePosition.setY(mainBall.getNextPosition().getY());

                mainBall.bounce(bouncePosition, 0);
                return true;
            } else {

                this.localController.sendObject(mainBall, PeerLocation.EAST);
                return false;
            }
        }

        // Techo y Suelo
        if (mainBall.getNextPosition().getY() <= 0) {

            bouncePosition.setX(mainBall.getNextPosition().getX());
            bouncePosition.setY(0);

            mainBall.bounce(bouncePosition, 1);

            return true;
        } else if (mainBall.getNextPosition().getY() >= canvaSize.get(1)) {

            bouncePosition.setX(mainBall.getNextPosition().getX());
            bouncePosition.setY(canvaSize.get(1));

            mainBall.bounce(bouncePosition, 1);

            return true;
        }

        return true;
    }
}
