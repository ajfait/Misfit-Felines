-- MySQL dump 10.13  Distrib 8.0.41, for macos15 (x86_64)
--
-- Host: 127.0.0.1    Database: misfit_felines_test
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cat`
--

DROP TABLE IF EXISTS `cat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cat`
(
    `c_id`        int NOT NULL AUTO_INCREMENT,
    `c_name`      varchar(75)   DEFAULT NULL,
    `c_sex`       varchar(10)   DEFAULT NULL,
    `c_dob`       date          DEFAULT NULL,
    `c_breed`     varchar(75)   DEFAULT NULL,
    `c_bio`       varchar(3000) DEFAULT NULL,
    `c_adoptable` tinyint(1) DEFAULT NULL,
    `p_id`        int           DEFAULT NULL,
    PRIMARY KEY (`c_id`),
    KEY           `fk_cat_person` (`p_id`),
    CONSTRAINT `fk_cat_person` FOREIGN KEY (`p_id`) REFERENCES `person` (`p_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cat`
--

LOCK
    TABLES `cat` WRITE;
/*!40000 ALTER TABLE `cat` DISABLE KEYS */;
TRUNCATE TABLE `cat`;
INSERT INTO `cat`
VALUES (1, 'CeeCee', 'Female', '2024-01-01', 'Domestic Shorthair',
        'CeeCee is Chip\'s mom.',1,1),(2,'Chip','Male','2024-05-21','Domestic Shorthair','Chip is CeeCee\'s son.', 1,
                                       1),
       (3, 'Maxine', 'Female', '2023-01-01', 'Persian', 'Maxine is the sweetest.', 1, 1);
/*!40000 ALTER TABLE `cat` ENABLE KEYS */;
UNLOCK
    TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event`
(
    `e_id`              int NOT NULL AUTO_INCREMENT,
    `e_name`            varchar(75) DEFAULT NULL,
    `e_location_street` varchar(75) DEFAULT NULL,
    `e_location_city`   varchar(50) DEFAULT NULL,
    `e_location_state`  varchar(2)  DEFAULT NULL,
    `e_location_zip`    varchar(5)  DEFAULT NULL,
    `e_date_start`      datetime    DEFAULT NULL,
    `e_date_end`        datetime    DEFAULT NULL,
    PRIMARY KEY (`e_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK
    TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
TRUNCATE TABLE `event`;
INSERT INTO `event`
VALUES (1, 'Adoption Fair at EarthWise Pet Supply & Grooming Madison', '3260 University Ave', 'Madison', 'WI', '53705',
        '2025-08-23 11:00:00', '2025-08-23 14:00:00');
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK
    TABLES;

--
-- Table structure for table `medical`
--

DROP TABLE IF EXISTS `medical`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medical`
(
    `m_id`         int NOT NULL AUTO_INCREMENT,
    `m_name`       varchar(50) DEFAULT NULL,
    `m_date_given` date        DEFAULT NULL,
    `c_id`         int         DEFAULT NULL,
    PRIMARY KEY (`m_id`),
    KEY            `fk_medical_cat` (`c_id`),
    CONSTRAINT `fk_medical_cat` FOREIGN KEY (`c_id`) REFERENCES `cat` (`c_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medical`
--

LOCK
    TABLES `medical` WRITE;
/*!40000 ALTER TABLE `medical` DISABLE KEYS */;
TRUNCATE TABLE `medical`;
INSERT INTO `medical`
VALUES (1, 'FVRCP', '2024-12-01', 2),
       (2, 'Rabies', '2024-12-01', 2);
/*!40000 ALTER TABLE `medical` ENABLE KEYS */;
UNLOCK
    TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person`
(
    `p_id`          int NOT NULL AUTO_INCREMENT,
    `p_first_name`  varchar(75)  DEFAULT NULL,
    `p_last_name`   varchar(75)  DEFAULT NULL,
    `p_phone`       varchar(15)  DEFAULT NULL,
    `p_email`       varchar(75)  DEFAULT NULL,
    `p_role`        varchar(75)  DEFAULT NULL,
    `p_preferences` varchar(255) DEFAULT NULL,
    `p_admin`       tinyint(1) DEFAULT NULL,
    PRIMARY KEY (`p_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK
    TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
TRUNCATE TABLE `person`;
INSERT INTO `person`
VALUES (1, 'Alison', 'Fait', '715-383-8927', 'ajfait@madisoncollege.edu', 'Foster', 'Pregnant Cats, Kittens', 1),
       (2, 'Jason', 'Fait', '715-383-8915', 'jafait1004@gmail.com', 'Foster', 'Weaned', 0),
       (3, 'Paula', 'Waite', '608-111-1234', 'PAWaite@madisoncollege.edu', 'Volunteer', '', 1);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK
    TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-19 20:19:58
