package restaurantreviewsystem;

import java.sql.*;
import java.util.*;

public class RestaurantReviewSystem {

    Restaurant res;
    Review rev;

    // Method to insert a new restaurant into the database
    public void addRestaurant(Restaurant r) {
        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
            String sql = "INSERT INTO Restaurant (name, location, cuisine) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, r.getName());
            pstmt.setString(2, r.getLocation());
            pstmt.setString(3, r.getCuisine());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            // Handle any exceptions here
        }
    }

    // Method to add a review for a restaurant
    public void addReview(String restaurantName, Review review) {
        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
            // First, retrieve the restaurant ID from the Restaurant table based on the name
            String restaurantIdQuery = "SELECT id FROM Restaurant WHERE name = ?";
            PreparedStatement pstmt = conn.prepareStatement(restaurantIdQuery);
            pstmt.setString(1, restaurantName);
            ResultSet rs = pstmt.executeQuery();

            int restaurantId = 0;
            if (rs.next()) {
                restaurantId = rs.getInt("id");
            }

            if (restaurantId != 0) {
                // If the restaurant is found, insert the review data into the Review table
                String reviewInsertQuery = "INSERT INTO Review (resName, review, rating) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(reviewInsertQuery);
                pstmt.setString(1, review.getResName());
                pstmt.setString(2, review.getReview());
                pstmt.setDouble(3, review.getRating());
                pstmt.executeUpdate();
            } else {
                // Handle case when the restaurant is not found
            }
        } catch (SQLException ex) {
            // Handle any exceptions here
        }
    }
    
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
            // Create a SQL query to fetch data from the Restaurant table
            String sql = "SELECT name, location, cuisine FROM Restaurant";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery(sql);

            // Process the result set and add data to the list
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String location = resultSet.getString("location");
                String cuisine = resultSet.getString("cuisine");

                // Create a new Restaurant object and add it to the list
                Restaurant restaurant = new Restaurant(name, location, cuisine);
                restaurants.add(restaurant);
            }

            // Close the statement and result set
            resultSet.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any potential exceptions here
        }

        return restaurants;
    }
    
    public List<Review> getAllReview() {
        List<Review> review = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
            // Create a SQL query to fetch data from the Review table
            String sql = "SELECT resName, review, rating FROM Review";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery(sql);

            // Process the result set and add data to the list
            while (resultSet.next()) {
                String resName = resultSet.getString("resName");
                String rev = resultSet.getString("review");
                Double rating = Double.parseDouble(resultSet.getString("rating"));

                // Create a new Review object and add it to the list
                Review r = new Review(resName, rev, rating);
                review.add(r);
            }

            // Close the statement and result set
            resultSet.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any potential exceptions here
        }

        return review;
    }
    

    public double getAverageRatingForRestaurant(String restaurantName) {
    double averageRating = 0.0;
    int totalRatings = 0;
    double sumRatings = 0.0;

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
        String query = "SELECT rating FROM Review WHERE resName = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, restaurantName);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            double rating = rs.getDouble("rating");
            sumRatings += rating;
            totalRatings++;
        }

        // Calculate the average rating if there are ratings available
        if (totalRatings > 0) {
            averageRating = sumRatings / totalRatings;
        }

    } catch (SQLException ex) {
        // Handle any exceptions here
        ex.printStackTrace();
    }

    return averageRating;
}
    
    
    //Delete restaurant info from the DB
//    public void deleteRestaurantByName(String restaurantName) {
//    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
//        // First, retrieve the restaurant ID from the Restaurant table based on the name
//        String restaurantIdQuery = "SELECT id FROM Restaurant WHERE name = ?";
//        PreparedStatement pstmt = conn.prepareStatement(restaurantIdQuery);
//        pstmt.setString(1, restaurantName);
//        ResultSet rs = pstmt.executeQuery();
//
//        int restaurantId = 0;
//        if (rs.next()) {
//            restaurantId = rs.getInt("id");
//        }
//
//        if (restaurantId != 0) {
//            // If the restaurant is found, delete the row from the Restaurant table
//            String deleteQuery = "DELETE FROM Restaurant WHERE id = ?";
//            pstmt = conn.prepareStatement(deleteQuery);
//            pstmt.setInt(1, restaurantId);
//            pstmt.executeUpdate();
//        } else {
//            // Handle case when the restaurant is not found
//        }
//    } catch (SQLException ex) {
//        // Handle any exceptions here
//    }
//}
    public void deleteRestaurantByName(String restaurantName) {
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
        // Delete the row from the Restaurant table based on the name
        String deleteQuery = "DELETE FROM Restaurant WHERE name = ?";
        PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
        pstmt.setString(1, restaurantName);
        pstmt.executeUpdate();
    } catch (SQLException ex) {
        // Handle any exceptions here
    }
}

    
       //Delete restaurant info from the DB
    public void deleteReviewByName(String reviewName) {
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
        // First, retrieve the restaurant ID from the Restaurant table based on the name
        String reviewIdQuery = "SELECT id FROM Review WHERE resName = ?";
        PreparedStatement pstmt = conn.prepareStatement(reviewIdQuery);
        pstmt.setString(1, reviewName);
        ResultSet rs = pstmt.executeQuery();

        int reviewId = 0;
        if (rs.next()) {
            reviewId = rs.getInt("id");
        }

        if (reviewId != 0) {
            // If the restaurant is found, delete the row from the Restaurant table
            String deleteQuery = "DELETE FROM Review WHERE id = ?";
            pstmt = conn.prepareStatement(deleteQuery);
            pstmt.setInt(1, reviewId);
            pstmt.executeUpdate();
        } else {
            // Handle case when the restaurant is not found
        }
    } catch (SQLException ex) {
        // Handle any exceptions here
    }
}


}
