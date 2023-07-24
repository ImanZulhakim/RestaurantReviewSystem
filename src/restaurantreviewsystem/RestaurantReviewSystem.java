package restaurantreviewsystem;

import java.sql.*;
import java.util.*;

public class RestaurantReviewSystem {

    Restaurant res;
    Review r;

   // Method to insert a new restaurant into the database
    public void addRestaurant(Restaurant r) throws RestaurantAlreadyExistsException {
        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
            // Check if a restaurant with the same name already exists in the database
            String checkQuery = "SELECT COUNT(*) FROM Restaurant WHERE name = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, r.getName());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                throw new RestaurantAlreadyExistsException("A restaurant with the name '" + r.getName() + "' already exists.");
            }

            // If the restaurant doesn't already exist, insert it into the database
            String sql = "INSERT INTO Restaurant (name, location, cuisine) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, r.getName());
            pstmt.setString(2, r.getLocation());
            pstmt.setString(3, r.getCuisine());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
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
        }
    }

    // Method to take all the restaurant info from the database
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();

        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
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
        }

        return restaurants;
    }

    // Method to take all the review info from the database
    public List<Review> getAllReview() {
        List<Review> review = new ArrayList<>();

        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
            String sql = "SELECT id, resName, review, rating FROM Review";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();

            // Process the result set and add data to the list
            while (resultSet.next()) {
                int reviewID = resultSet.getInt("id");
                String resName = resultSet.getString("resName");
                String rev = resultSet.getString("review");
                Double rating = Double.parseDouble(resultSet.getString("rating"));

                // Create a new Review object and add it to the list
                r = new Review(reviewID, resName, rev, rating);
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

    // Method to calculate the average rating
    public double getAverageRatingForRestaurant(String restaurantName) {
        double averageRating = 0.0;
        int totalRatings = 0;
        double sumRatings = 0.0;

        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
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
    public void deleteRestaurantByName(String restaurantName) {
        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
            // Delete the row from the Restaurant table based on the name
            String deleteQuery = "DELETE FROM Restaurant WHERE name = ?";
            PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
            pstmt.setString(1, restaurantName);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            // Handle any exceptions here
        }
    }

    //Delete review info from the DB
    public boolean deleteReviewById(int reviewId) {
        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
            // First, check if the review exists in the Review table
            String reviewCheckQuery = "SELECT id FROM Review WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(reviewCheckQuery);
            pstmt.setInt(1, reviewId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // If the review is found, delete the row from the Review table
                String deleteQuery = "DELETE FROM Review WHERE id = ?";
                pstmt = conn.prepareStatement(deleteQuery);
                pstmt.setInt(1, reviewId);
                pstmt.executeUpdate();
                // Optionally, you can also delete any references to this review from other tables here if needed.
                return true; // Return true to indicate successful deletion
            } else {
                // Handle case when the review is not found
                return false; // Return false to indicate review ID not found
            }
        } catch (SQLException ex) {
            // Handle any exceptions here
            ex.printStackTrace();
            return false; // Return false on error
        }
    }

    // Method to get the restaurant info by its name
    public Restaurant getRestaurantByName(String restaurantName) {
        Restaurant restaurant = null;
        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
            String sql = "SELECT * FROM Restaurant WHERE name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, restaurantName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Retrieve data from the result set and create a Restaurant object
                String name = rs.getString("name");
                String location = rs.getString("location");
                String cuisine = rs.getString("cuisine");
                restaurant = new Restaurant(name, location, cuisine);
            }
        } catch (SQLException ex) {
            // Handle any exceptions here
            ex.printStackTrace();
        }

        return restaurant;
    }

    // Method to get review info by name
    public Review getReviewByName(String restaurantName) {
        Review r = null;
        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
            String sql = "SELECT * FROM Review WHERE resName = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, restaurantName);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                // Retrieve data from the result set and create a Restaurant object
                int reviewID = resultSet.getInt("id");
                String resName = resultSet.getString("resName");
                String rev = resultSet.getString("review");
                Double rating = Double.parseDouble(resultSet.getString("rating"));

                // Create a new Review object and add it to the list
                r = new Review(reviewID, resName, rev, rating);
                // r = new Review(resName, rev, rating);
            }
        } catch (SQLException ex) {
            // Handle any exceptions here
            ex.printStackTrace();
        }

        return r;
    }

    // Method to get review info by id
    public Review getReviewById(int reviewId) {
        Review r = null;
        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
            String sql = "SELECT * FROM Review WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                // Retrieve data from the result set and create a Review object
                int id = resultSet.getInt("id");
                String resName = resultSet.getString("resName");
                String rev = resultSet.getString("review");
                double rating = resultSet.getDouble("rating");

                // Create a new Review object
                r = new Review(id, resName, rev, rating);
            }
        } catch (SQLException ex) {
            // Handle any exceptions here
            ex.printStackTrace();
        }

        return r;
    }

    // Method to update restaurant location and cuisine by name
    public void updateRestaurant(Restaurant restaurantToUpdate) {
        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
            String sql = "UPDATE Restaurant SET location = ?, cuisine = ? WHERE name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, restaurantToUpdate.getLocation());
            pstmt.setString(2, restaurantToUpdate.getCuisine());
            pstmt.setString(3, restaurantToUpdate.getName());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            // Handle any exceptions here
            ex.printStackTrace();
        }
    }

    // Method to update review name, review and rating by id
    public void updateReview(Review reviewToUpdate) {
        try ( Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/res", "root", "root")) {
            String sql = "UPDATE Review SET review = ?, rating = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, reviewToUpdate.getReview());
            pstmt.setDouble(2, reviewToUpdate.getRating());
            pstmt.setInt(3, reviewToUpdate.getReviewID());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            // Handle any exceptions here
            ex.printStackTrace();
        }
    }

}
