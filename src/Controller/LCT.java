package Controller;
import Model.*;
import View.Viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LCT extends JFrame implements MouseListener {
    private final TGPCT peerController;
    private final Viewer viewer = new Viewer();

    public LCT(TGPCT peerController) {
        this.peerController = peerController;
        add(viewer);
        configureJFrame();
        setVisible(true);
    }

    private void configureJFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        viewer.addMouseListener(this);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int ballSize = 15;

        Ball newBall = new Ball(e.getX() - 25, e.getY() - 25, ballSize, ballSize, Color.black, viewer);
        viewer.addBall(newBall);
        Thread ballThread = new Thread(newBall);
        ballThread.start();

        System.out.println("X: " + e.getX() + " | Y: " + e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
