CREATE DATABASE moviedb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `moviedb`.`movies` (
  `id` VARCHAR(10) NOT NULL DEFAULT '',
  `title` VARCHAR(100) NOT NULL DEFAULT '',
  `year` INT NOT NULL,
  `director` VARCHAR(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  FULLTEXT(`title`));
  
  CREATE TABLE `moviedb`.`stars` (
  `id` VARCHAR(10) NOT NULL DEFAULT '',
  `name` VARCHAR(100) NOT NULL DEFAULT '',
  `birthYear` INT NULL,
  PRIMARY KEY (`id`));
  
    
  CREATE TABLE `moviedb`.`genres` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`));
  
CREATE TABLE `moviedb`.`stars_in_movies` (
  `starId` VARCHAR(10) NOT NULL DEFAULT '',
  `movieId` VARCHAR(10) NOT NULL DEFAULT '',
    FOREIGN KEY (`starId`)
    REFERENCES `moviedb`.`stars` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (`movieId`)
    REFERENCES `moviedb`.`movies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE `moviedb`.`genres_in_movies` (
  `genreId` INT NOT NULL ,
  `movieId` VARCHAR(10) NOT NULL DEFAULT '',
    FOREIGN KEY (`genreId`)
    REFERENCES `moviedb`.`genres` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (`movieId`)
    REFERENCES `moviedb`.`movies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
CREATE TABLE `moviedb`.`creditcards` (
  `id` VARCHAR(20) NOT NULL DEFAULT '',
  `firstName` VARCHAR(50) NOT NULL DEFAULT '',
  `lastName` VARCHAR(50) NOT NULL DEFAULT '',
  `expiration` DATE NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `moviedb`.`ratings` (
  `movieId` VARCHAR(10) NOT NULL DEFAULT '',
  `rating` FLOAT NOT NULL,
  `numVotes` INT NOT NULL,
    FOREIGN KEY (`movieId`)
    REFERENCES `moviedb`.`movies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `moviedb`.`customers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(50) NOT NULL DEFAULT '',
  `lastName` VARCHAR(50) NOT NULL DEFAULT '',
  `ccId` VARCHAR(20) NOT NULL DEFAULT '',
  `address` VARCHAR(200) NOT NULL DEFAULT '',
  `email` VARCHAR(50) NOT NULL DEFAULT '',
  `password` VARCHAR(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
    FOREIGN KEY (`ccId`)
    REFERENCES `moviedb`.`creditcards` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

  CREATE TABLE `moviedb`.`sales` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `customerId` INT NOT NULL,
  `movieId` VARCHAR(10) NOT NULL DEFAULT '',
  `saleDate` DATE NOT NULL,
  PRIMARY KEY (`id`),
    FOREIGN KEY (`customerId`)
    REFERENCES `moviedb`.`customers` (`id`),
    FOREIGN KEY (`movieId`)
    REFERENCES `moviedb`.`movies` (`id`));

CREATE TABLE `moviedb`.`listOfGenres` (
  `mId` varchar(10) NOT NULL DEFAULT '',
  `listGenres` varchar(1000) NOT NULL DEFAULT '',
  KEY `mId` (`mId`),
  CONSTRAINT `mId` FOREIGN KEY (`mId`) REFERENCES `movies` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `moviedb`.`listOfStars` (
  `movieid` varchar(10) NOT NULL DEFAULT '',
  `listStars` varchar(1000) NOT NULL DEFAULT '',
  KEY `movieId_idx` (`movieid`),
  CONSTRAINT `movieId` FOREIGN KEY (`movieid`) REFERENCES `movies` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `moviedb`.`cart` (
  `title` VARCHAR(100) NOT NULL DEFAULT '',
  `id` VARCHAR(10) NOT NULL DEFAULT '',
  `quantity` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`title`, `id`));



