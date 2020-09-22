import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DriversDAO {

    private Connection connection;

    public DriversDAO(Connection connection) {
        this.connection = connection;
    }

    public Optional<Driver> findById(Long id) throws SQLException {
        ResultSet driverSet = connection.createStatement().executeQuery(String.format("select * from driver where id =%d", id));
        ResultSet carsSet = connection.createStatement().executeQuery(String.format("select * from car where id =%d", id));
        List<Car> cars = new ArrayList<Car>();
        while(carsSet.next()){
            cars.add(new Car(carsSet.getLong("id"), carsSet.getString("model"), carsSet.getString("color"),null));
        }
        if(!driverSet.next()) return Optional.empty();
        Driver driver = new Driver(driverSet.getLong("id"),driverSet.getString("first_name"),driverSet.getString("last_name"),driverSet.getInt("age"),null);
        driver.setCars(cars);
        for(Car car : cars){
            car.setDriver(driver);
        }
        return Optional.of(driver);
    }
}
