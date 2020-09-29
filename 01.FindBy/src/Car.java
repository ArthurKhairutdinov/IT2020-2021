public class Car {
    private Long id;
    private String model;
    private String color;
    private Driver driver;

    public Car(Long id, String model, String color, Driver driver){
        this.id = id;
        this.model = model;
        this.color = color;
        this.driver = driver;
    }

    public void setDriver(Driver driver){
        this.driver = driver;
    }

    // TODO: на интерес

}
