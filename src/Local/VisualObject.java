package Local;
import java.awt.Graphics;
import java.io.Serializable;

public abstract class VisualObject implements Serializable {
    public abstract Graphics paint(Graphics g);
}
