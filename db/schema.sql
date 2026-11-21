
CREATE DATABASE IF NOT EXISTS life_rpg
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_unicode_ci;

USE life_rpg;

CREATE TABLE User (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE,
    exp INT DEFAULT 0,
    level INT DEFAULT 1
);

CREATE TABLE Awards (
    id INT PRIMARY KEY AUTO_INCREMENT,
    repeatNum INT,
    completedNum INT DEFAULT 0,
    userId INT,
    startDate DATE DEFAULT (CURRENT_DATE),
    consistent BOOLEAN DEFAULT 1,

    FOREIGN KEY (userId) REFERENCES User(user_id)
);

CREATE TABLE Quest (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    title VARCHAR(50),
    category VARCHAR(50),
    difficulty INT,
    completed BOOLEAN DEFAULT 0,
    due_date DATE DEFAULT (CURRENT_DATE),
    award_id INT,

    FOREIGN KEY (user_id) REFERENCES User(user_id),
    FOREIGN KEY (award_id) REFERENCES Awards(id)
);

INSERT INTO User (user_id, name, exp, level)
VALUES (100, 'Lucy', 0, 1);
