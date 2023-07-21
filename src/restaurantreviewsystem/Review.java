package restaurantreviewsystem;

/**
 *
 * @author IMAN ZULHAKIM
 */
public class Review {
    private String resName;
    private String review;
    private double rating;

    // Constructor
    public Review(String resName, String review, double rating) {
        this.resName = resName;
        this.review = review;
        this.rating = rating;
    }

    // Getters
    public double getRating() {
        return rating;
    }

    public String getResName() {
        return resName;
    }

    public String getReview() {
        return review;
    }

 
}

