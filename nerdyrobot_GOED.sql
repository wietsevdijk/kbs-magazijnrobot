-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 25, 2023 at 12:29 PM
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
CREATE DATABASE IF NOT EXISTS `nerdyrobot` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `nerdyrobot`;

-- --------------------------------------------------------

--
-- Table structure for table `klanten`
--

CREATE TABLE `klanten` (
  `klantID` int(11) NOT NULL,
  `klantVoornaam` varchar(50) NOT NULL,
  `klantAchternaam` varchar(50) NOT NULL,
  `klantAdres` varchar(255) NOT NULL,
  `klantPostcode` varchar(255) NOT NULL,
  `klantWoonplaats` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `klanten`
--

INSERT INTO `klanten` (`klantID`, `klantVoornaam`, `klantAchternaam`, `klantAdres`, `klantPostcode`, `klantWoonplaats`) VALUES
(1, 'Dane', 'Schuijt', 'Soendastraat 5', '', '0');

-- --------------------------------------------------------

--
-- Table structure for table `magazijn`
--

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

-- --------------------------------------------------------

--
-- Table structure for table `orderregels`
--

CREATE TABLE `orderregels` (
  `orderRegelID` int(11) NOT NULL,
  `orderID` int(11) NOT NULL,
  `productID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orderregels`
--

INSERT INTO `orderregels` (`orderRegelID`, `orderID`, `productID`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 2, 10),
(5, 2, 2),
(6, 2, 26),
(7, 3, 12),
(8, 3, 17),
(9, 3, 4);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `orderID` int(11) NOT NULL,
  `klantID` int(11) NOT NULL,
  `pickingCompleet` tinyint(1) NOT NULL DEFAULT 0,
  `pickedWanneer` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`orderID`, `klantID`, `pickingCompleet`, `pickedWanneer`) VALUES
(1, 1, 0, '0000-00-00'),
(2, 1, 0, '0000-00-00'),
(3, 1, 0, '0000-00-00');

-- --------------------------------------------------------

--
-- Table structure for table `producten`
--

CREATE TABLE `producten` (
  `productID` int(11) NOT NULL,
  `productNaam` varchar(255) NOT NULL,
  `productPrijs` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `producten`
--

INSERT INTO `producten` (`productID`, `productNaam`, `productPrijs`) VALUES
(1, 'Fietsbel', 5),
(2, 'handvat', 3),
(3, 'Fietsketting', 10),
(4, 'Ketting olie', 5),
(5, 'Spatbord voor', 10),
(6, 'Spatbord achter', 10),
(7, 'Velg voor', 25),
(8, 'Velg achter', 25),
(9, 'fietsband voor', 20),
(10, 'Fietsband achter', 20),
(11, 'Baggage drager', 15),
(12, 'Fietsmand', 20),
(13, 'Fiets standaard', 15),
(14, 'Fiets trappers', 15),
(15, 'Fiets voorlicht', 7.5),
(16, 'Fiets achterlicht', 7.5),
(17, 'Fietsframe', 50),
(18, 'Electrische fiets batterij', 250),
(19, 'Electrische fiets batterij oplader', 50),
(20, 'Electrische fiets motor', 250),
(21, 'Spaken 50stuks', 25),
(22, 'Fiets Kettingkast', 10),
(23, 'Zadel', 15),
(24, 'Zadelpen', 10),
(25, 'Handrem', 15),
(26, 'Electrische fiets batterij oplader', 50),
(27, 'Electrische fiets motor', 250);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `klanten`
--
ALTER TABLE `klanten`
  ADD PRIMARY KEY (`klantID`);

--
-- Indexes for table `magazijn`
--
ALTER TABLE `magazijn`
  ADD PRIMARY KEY (`locatie`),
  ADD KEY `FK_magazijn_productID` (`productID`) USING BTREE;

--
-- Indexes for table `orderregels`
--
ALTER TABLE `orderregels`
  ADD PRIMARY KEY (`orderRegelID`),
  ADD KEY `FK_orderregels_productID` (`productID`),
  ADD KEY `FK_orderregels_orderID` (`orderID`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`orderID`),
  ADD KEY `FK_orders_klantID` (`klantID`);

--
-- Indexes for table `producten`
--
ALTER TABLE `producten`
  ADD PRIMARY KEY (`productID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `klanten`
--
ALTER TABLE `klanten`
  MODIFY `klantID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `orderregels`
--
ALTER TABLE `orderregels`
  MODIFY `orderRegelID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `orderID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `producten`
--
ALTER TABLE `producten`
  MODIFY `productID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `magazijn`
--
ALTER TABLE `magazijn`
  ADD CONSTRAINT `magazijn_ibfk_1` FOREIGN KEY (`productID`) REFERENCES `producten` (`productID`);

--
-- Constraints for table `orderregels`
--
ALTER TABLE `orderregels`
  ADD CONSTRAINT `FK_orderregels_orderID` FOREIGN KEY (`orderID`) REFERENCES `orders` (`orderID`),
  ADD CONSTRAINT `FK_orderregels_productID` FOREIGN KEY (`productID`) REFERENCES `producten` (`productID`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FK_orders_klantID` FOREIGN KEY (`klantID`) REFERENCES `klanten` (`klantID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
