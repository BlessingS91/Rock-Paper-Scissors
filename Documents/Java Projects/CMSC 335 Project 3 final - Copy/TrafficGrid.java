import javax.swing.*;
import java.awt.*;

// Main Traffic Grid GUI
public class TrafficGrid {

    static class RoadPanel extends JPanel {

        Cars[] cars;
        TrafficLightGUI[] lights;

        public RoadPanel(Cars[] cars, TrafficLightGUI[] lights) {
            this.cars = cars;
            this.lights = lights;
            setPreferredSize(new Dimension(1200, 400));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GRAY);
            g.fillRect(0, 300, 1800, 30);// horizontal road
            g.fillRect(330, 0, 30, 1200);// vertical road
            g.fillRect(660, 0, 30, 1200);// vertical road
            g.fillRect(990, 0, 30, 1200);// vertical road

            drawCars(g);
            drawLights(g);
        }

        private void drawCars(Graphics g) {
            g.setColor(Color.BLUE);
            for (Cars car : cars) {
                g.fillRect((int) car.getX(), (int) car.getY(), 10, 10);
            }
        }

        private void drawLights(Graphics g) {
            for (TrafficLightGUI light : lights) {
                g.setColor(light.color);
                g.fillOval(light.getX(), light.getY(), 20, 20);
            }
        }
    }

    public static void main(String[] args) {
        // Traffic lights
        TrafficLightSimulator light1 = new TrafficLightSimulator(TrafficLightColor.RED);
        TrafficLightSimulator light2 = new TrafficLightSimulator(TrafficLightColor.GREEN);
        TrafficLightSimulator light3 = new TrafficLightSimulator(TrafficLightColor.RED);

        Thread t1 = new Thread(light1);
        Thread t2 = new Thread(light2);
        Thread t3 = new Thread(light3);

        t1.start();
        t2.start();
        t3.start();

        TrafficLightGUI[] lightsGUI = new TrafficLightGUI[] {
                new TrafficLightGUI("Light1", 335, 300, Color.RED),
                new TrafficLightGUI("Light2", 665, 300, Color.RED),
                new TrafficLightGUI("Light3", 995, 300, Color.RED)
        };

        // Cars
        Cars[] cars = new Cars[] {
                new Cars(0, 305, 5, 0, "Car1X",
                        new TrafficLightSimulator[]{light1, light2, light3},
                        new TrafficLightGUI[]{lightsGUI[0], lightsGUI[1], lightsGUI[2]}),

                new Cars(930, 305, -5, 0, "Car2X",
                        new TrafficLightSimulator[]{light1, light2, light3},
                        new TrafficLightGUI[]{lightsGUI[0], lightsGUI[1], lightsGUI[2]}),


                new Cars(670, 505, 0, -5, "Car1Y",
                        new TrafficLightSimulator[]{light1, light2, light3},
                        new TrafficLightGUI[]{lightsGUI[0], lightsGUI[1], lightsGUI[2]}),

                new Cars(340, 105, 0, 5, "Car2Y",
                        new TrafficLightSimulator[]{light1, light2, light3},
                        new TrafficLightGUI[]{lightsGUI[0], lightsGUI[1], lightsGUI[2]}),


        };
        // Start car threads
        for (Cars car : cars) {
            new Thread(car).start();
        }

        // GUI setup
        JFrame frame = new JFrame("Traffic Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        RoadPanel panel = new RoadPanel(cars, lightsGUI);
        frame.add(panel);

        // Timer to update traffic light GUI colors and repaint
        Timer timer = new Timer(50, e -> {
            // Update GUI light colors based on simulator state
            lightsGUI[0].color = getColorFromSimulator(light1);
            lightsGUI[1].color = getColorFromSimulator(light2);
            lightsGUI[2].color = getColorFromSimulator(light3);

            panel.repaint();
        });
        timer.start();

        frame.pack();
        frame.setVisible(true);
    }

    private static Color getColorFromSimulator(TrafficLightSimulator tl) {
        switch (tl.getColor()) {
            case RED: return Color.RED;
            case GREEN: return Color.GREEN;
            case YELLOW: return Color.YELLOW;
        }
        return Color.BLACK;
    }
}
