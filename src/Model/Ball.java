package Model;
import View.Viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Ball extends Ellipse2D.Double implements Runnable, VOD {
    private double dx, dy;
    private static final double speed = 4.0;
    private Viewer viewer;
    Color color;

    public Ball(double x, double y, double width, double height, Color color, Viewer viewer) {
        super(x, y, width, height);
        this.color = color;
        this.viewer = viewer;

        double angle = Math.random() * 2 * Math.PI; // Random angle in radians
        this.dx = speed * Math.cos(angle);
        this.dy = speed * Math.sin(angle);
    }

    @Override
    public void run() {
        while (true) {
            move();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void bounce() {

    }

    public void move() {
        // Movement
        x += dx;
        y += dy;

        // Horizontal Collision
        if (x <= 0 || x + this.width >= viewer.getWidth()) {
            dx = -dx;
        }

        // Vertical Collision
        if (y <= 0 || y + this.height >= viewer.getHeight()) {
            dy = -dy;
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                viewer.repaint();
            }
        });
    }

    @Override
    public void explode() {

    }

    @Override
    public void paint() {

    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
