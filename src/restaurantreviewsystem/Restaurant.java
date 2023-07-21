package restaurantreviewsystem;

/**
 *
 * @author IMAN ZULHAKIM
 */
public class Restaurant {

    private String name;
    private String location;
    private String cuisine;

    // Constructor
    public Restaurant(String name, String location, String cuisine) {
        this.name = name;
        this.location = location;
        this.cuisine = cuisine;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getCuisine() {
        return cuisine;
    }

 

    @Override
    public String toString() {
        return "Name: " + name
                + "\nLocation: " + location
                + "\nCuisine: " + cuisine;
    }
}
