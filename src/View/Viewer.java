package View;

import Model.Ball;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Viewer extends JPanel {
    public BufferStrategy bs;
    private ArrayList<Ball> balls = new ArrayList<>();

    public Viewer(){
        setBackground(Color.yellow);
    }


    public void addBall(Ball ball) {
        balls.add(ball);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Ball ball : balls) {
            g2d.setColor(ball.getColor());
            g2d.fill(ball);
        }
    }

}
