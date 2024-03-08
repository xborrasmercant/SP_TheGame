package Local;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TGModel {
    private TGLocalController controller;
    private List<VisualObject> visualElements = Collections.synchronizedList(new ArrayList<>()); 

    public TGModel(TGLocalController controller) {
        this.controller = controller;
    }


    // METHODS
    public VectorDTO simplifyVelocity(VectorDTO velocity) {
        VectorDTO normalizedVel = new VectorDTO(0, 0);
        float x = velocity.getX();
        float y = velocity.getY();

        float normalizedX = (float) (x / 20);
        float normalizedY = (float) (y / 20);

        normalizedVel.setX(normalizedX);
        normalizedVel.setY(normalizedY);

        return normalizedVel;
    }

    public void addBall(VectorDTO ballVelocity, CoordinatesDTO ballInitPosition) {
        Ball ball = new Ball(this, simplifyVelocity(ballVelocity), ballInitPosition);
        visualElements.add(ball);

        new Thread(ball).start();
    }

    public void addBall(Ball ball) {
        ball.setModel(this);
        ball.setAlive(true);
        visualElements.add(ball);
        new Thread(ball).start();
    }

    public void removeBall(Ball ball) {
        ball.setAlive(false);
        for (int i = 0; i < visualElements.size(); i++) {
            if (ball == visualElements.get(i)) {
                visualElements.remove(i);
            }
        }
    }

    public synchronized void checkBallMovement(Ball ball) {
        ArrayList<Ball> ballsColiding = new ArrayList<>();

        for (VisualObject visualElement : visualElements) {
            if (visualElement instanceof Ball) {
                if ((isColliding(ball, ((Ball) visualElement))) && ball != ((Ball) visualElement)) {
                    ballsColiding.add((Ball) visualElement);
                }
            }
        }
        controller.checkBallColison(ball, ballsColiding);
    }

    private boolean isColliding(Ball mainBall, Ball possibleColissionBall) {
        if (possibleColissionBall != null) {
            boolean collided;
            int distance = calcColision(mainBall.getNextPosition(), possibleColissionBall.getNextPosition());
            int sumaRadios = mainBall.getRad() + possibleColissionBall.getRad();
            collided = distance <= sumaRadios;
            return collided;
        }

        return false;
    }

    private int calcColision(CoordinatesDTO mainBallPosition, CoordinatesDTO possibleColissionBallPosition) {
        int xDistance = mainBallPosition.getX() - possibleColissionBallPosition.getX();
        int yDistance = mainBallPosition.getY() - possibleColissionBallPosition.getY();

        return (int) (Math.sqrt(xDistance * xDistance + yDistance * yDistance) + 20);
    }


    // GETTERS AND SETTERS
    public List<VisualObject> getVisualElements() {
        return this.visualElements;
    }
}
