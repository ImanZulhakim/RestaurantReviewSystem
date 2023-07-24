package restaurantreviewsystem;

/**
 *
 * @author IMAN ZULHAKIM
 */
// Custom exception class to indicate that the restaurant name already exists in the database
public class RestaurantAlreadyExistsException extends Exception {

    public RestaurantAlreadyExistsException(String message) {
        super(message);
    }
}
