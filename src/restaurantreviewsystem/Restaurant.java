/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantreviewsystem;

import java.util.*;

/**
 *
 * @author IMAN ZULHAKIM
 */
public class Restaurant {
    private String name;
    private String location;
    private String cuisine;
    private double averageRating;
    private List<Review> reviews;

    // Constructor
    public Restaurant(String name, String location, String cuisine) {
        this.name = name;
        this.location = location;
        this.cuisine = cuisine;
        this.averageRating = 0.0;
        this.reviews = new ArrayList<>();
    }

    // Getters and setters

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public void addReview(Review review) {
        reviews.add(review);
        updateAverageRating();
    }

    private void updateAverageRating() {
        double sumRatings = 0.0;
        for (Review review : reviews) {
            sumRatings += review.getRating();
        }
        averageRating = sumRatings / reviews.size();
    }

    @Override
    public String toString() {
        return "Name: " + name +
                "\nLocation: " + location +
                "\nCuisine: " + cuisine +
                "\nAverage Rating: " + averageRating;
    }
}