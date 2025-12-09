public class Cars implements Runnable {
    private double x, y;
    private int xSpeed, ySpeed;
    private String carName;

    private TrafficLightSimulator[] lightSim;   // simulators for color
    private TrafficLightGUI[] lightGui;         // GUI for positions

    private boolean running = true;

    public Cars(double x, double y, int xSpeed, int ySpeed, String carName,
                TrafficLightSimulator[] lightSim, TrafficLightGUI[] lightGui) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.carName = carName;
        this.lightSim = lightSim;
        this.lightGui = lightGui;
        CarsRegistry.register(this);
    }

    public double getX() { return x; }
    public double getY() { return y; }

    @Override
    public void run() {
        while (running) {
            boolean shouldStop = false;

            // Check all traffic lights
            for (int i = 0; i < lightSim.length; i++) {
                if (lightSim[i].getColor() == TrafficLightColor.RED &&
                        isNearLight(lightGui[i], 50)) {
                    shouldStop = true;
                    synchronized (lightSim[i]) {
                        try { lightSim[i].waitForChange(); } catch (Exception ignored) {}
                    }
                    break; // stop at first relevant red light
                }
            }

            // Check other cars to prevent collision
//            for (Cars other : CarsRegistry.getAllCars()) {
//                if (other != this && willCollide(other, 20)) {
//                    shouldStop = true;
//                    break;
//                }
//            }

            // Move only if safe
            if (!shouldStop) {
                x += xSpeed;
                y += ySpeed;
            }

            try { Thread.sleep(50); }
            catch (InterruptedException e) { running = false; }
        }
    }

    private boolean willCollide(Cars other, int safeDistance) {
        double dx = other.getX() - this.x;
        double dy = other.getY() - this.y;

        if (xSpeed != 0) { // moving horizontally
            return Math.abs(dy) < 10 && dx > 0 && dx < safeDistance;
        } else if (ySpeed != 0) { // moving vertically
            return Math.abs(dx) < 10 && dy > 0 && dy < safeDistance;
        }
        return false;
    }

    private boolean isNearLight(TrafficLightGUI light, int threshold) {
        if (xSpeed != 0) { // horizontal
            double dx = light.getX() - this.x;
            double dy = Math.abs(light.getY() - this.y);
            return dx >= 0 && dx < threshold && dy < threshold;
        } else if (ySpeed != 0) { // vertical
            double dx = Math.abs(light.getX() - this.x);
            double dy = light.getY() - this.y;
            return dy >= 0 && dy < threshold && dx < threshold;
        }
        return false;
    }
}
