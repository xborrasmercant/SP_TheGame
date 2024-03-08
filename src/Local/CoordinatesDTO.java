package Local;

import java.io.Serializable;

public class CoordinatesDTO implements Serializable{
    private int x;
    private int y;

    public CoordinatesDTO (int x, int y){
        this.x = x;
        this.y = y;
    }

    public CoordinatesDTO (){}

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
