import java.awt.*;

public class TrafficLightGUI {
    String name;
    int x, y;
    Color color;

    public TrafficLightGUI(String name, int x, int y, Color color) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.color = color;
    }
    int getX() {
        return x;
    }

    int getY() {
        return y;
    }
}
