import java.util.ArrayList;
import java.util.List;


    public class CarsRegistry {
        private static final List<Cars> allCars = new ArrayList<>();

        public static void register(Cars car) {
            allCars.add(car);
        }

        public static List<Cars> getAllCars() {
            return allCars;
        }
    }
