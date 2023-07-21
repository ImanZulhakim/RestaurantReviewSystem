/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantreviewsystem;

/**
 *
 * @author IMAN ZULHAKIM
 */
public class Review {
    private String username;
    private String comment;
    private double rating;

    // Constructor
    public Review(String username, String comment, double rating) {
        this.username = username;
        this.comment = comment;
        this.rating = rating;
    }

    // Getters
    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Username: " + username +
                "\nRating: " + rating +
                "\nComment: " + comment;
    }
}

