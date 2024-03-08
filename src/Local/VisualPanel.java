package Local;

import static java.lang.Thread.sleep;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.List;

public class VisualPanel extends Canvas implements Runnable, ActionListener {

    private BufferStrategy bufferStrategy;
    private TGViewer viewer;

    public VisualPanel(TGViewer viewer) {
        this.viewer = viewer;
        addMouseListener(viewer);
        setSize(new Dimension(920, 720));
    }

    private void paintElement(List<VisualObject> elements) {
        if (bufferStrategy == null) {
            createBufferStrategy(2);
            bufferStrategy = getBufferStrategy();
        }

        Graphics g = bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < elements.size(); i++) {
            g = elements.get(i).paint(g);
        }

        bufferStrategy.show();
        g.dispose();
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (isValid() && viewer.getVisualElements() != null) {
                    sleep(20);
                    paintElement(viewer.getVisualElements());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
