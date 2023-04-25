-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 25, 2023 at 12:10 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `nerdyrobot`
--

-- --------------------------------------------------------

--
-- Table structure for table `magazijn`
--

DROP TABLE IF EXISTS `magazijn`;
CREATE TABLE `magazijn` (
  `locatie` int(2) NOT NULL,
  `productID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `magazijn`
--

INSERT INTO `magazijn` (`locatie`, `productID`) VALUES
(11, NULL),
(12, NULL),
(13, NULL),
(14, NULL),
(15, NULL),
(21, NULL),
(22, NULL),
(23, NULL),
(24, NULL),
(25, NULL),
(31, NULL),
(32, NULL),
(33, NULL),
(34, NULL),
(35, NULL),
(41, NULL),
(42, NULL),
(43, NULL),
(44, NULL),
(45, NULL),
(51, NULL),
(52, NULL),
(53, NULL),
(54, NULL),
(55, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `magazijn`
--
ALTER TABLE `magazijn`
  ADD PRIMARY KEY (`locatie`),
  ADD KEY `FK_magazijn_productID` (`productID`) USING BTREE;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `magazijn`
--
ALTER TABLE `magazijn`
  ADD CONSTRAINT `magazijn_ibfk_1` FOREIGN KEY (`productID`) REFERENCES `producten` (`productID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
