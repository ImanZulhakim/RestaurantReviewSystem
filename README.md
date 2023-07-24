# RestaurantReviewSystem
If error encountered

1. GUI>jFrame>set layout> absolute layout
2. install j-connector

# Database - mysql workbench
Schema name = res

CREATE TABLE restaurant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    cuisine VARCHAR(255) NOT NULL,
    CONSTRAINT unique_name UNIQUE (name)
);

CREATE TABLE review (
    id INT AUTO_INCREMENT PRIMARY KEY,
    resName VARCHAR(255) NOT NULL,
    review VARCHAR(1000) NOT NULL,
    rating DOUBLE NOT NULL,
    FOREIGN KEY (resName) REFERENCES Restaurant(name) ON DELETE CASCADE
);
