package restaurantreviewsystem;

/**
 *
 * @author IMAN ZULHAKIM
 */
public class Review {
    private int reviewID;
    private String resName;
    private String review;
    private double rating;

    // Constructor with ID (for retrieving reviews from the database)
    public Review(int reviewID,String resName, String review, double rating) {
        this.reviewID = reviewID;
        this.resName = resName;
        this.review = review;
        this.rating = rating;
    }

    // Constructor without ID (for inserting new reviews)
    public Review(String resName, String review, double rating) {
        this.resName = resName;
        this.review = review;
        this.rating = rating;
    }

    // Getters
    public double getRating() {
        return rating;
    }

    public int getReviewID() {
        return reviewID;
    }

    public String getResName() {
        return resName;
    }

    public String getReview() {
        return review;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

   

 
}

