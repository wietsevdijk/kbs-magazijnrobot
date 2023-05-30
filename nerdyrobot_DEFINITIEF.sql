-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 30, 2023 at 11:29 AM
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

DROP TABLE IF EXISTS `klanten`;
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
(1, 'Dane', 'Schuijt', 'Soendastraat 5', '8022PN', 'Zwolle'),
(2, 'Wietse', 'van Dijk', 'Campus 2', '8017CA', 'Zwolle'),
(3, 'Carlijn', 'Blom', 'Campus 18', '8017CA', 'Zwolle');

-- --------------------------------------------------------

--
-- Table structure for table `magazijn`
--

DROP TABLE IF EXISTS `magazijn`;
CREATE TABLE `magazijn` (
  `locatie` varchar(3) NOT NULL COMMENT 'Locatie stellage. 1ste letter = X-coord, 2de nummer = Y-coord',
  `productID` int(11) DEFAULT NULL COMMENT 'Product dat op deze plek ligt'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `magazijn`
--

INSERT INTO `magazijn` (`locatie`, `productID`) VALUES
('A4', NULL),
('A5', NULL),
('B1', NULL),
('B2', NULL),
('B3', NULL),
('B4', NULL),
('B5', NULL),
('C1', NULL),
('C2', NULL),
('C3', NULL),
('C4', NULL),
('C5', NULL),
('D1', NULL),
('D2', NULL),
('D3', NULL),
('D4', NULL),
('D5', NULL),
('E1', NULL),
('E2', NULL),
('E3', NULL),
('E4', NULL),
('E5', NULL),
('A1', 1),
('A2', 2),
('A3', 3);

-- --------------------------------------------------------

--
-- Table structure for table `orderregels`
--

DROP TABLE IF EXISTS `orderregels`;
CREATE TABLE `orderregels` (
  `orderRegelID` int(11) NOT NULL,
  `orderID` int(11) NOT NULL,
  `productID` int(11) NOT NULL,
  `productAantal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orderregels`
--

INSERT INTO `orderregels` (`orderRegelID`, `orderID`, `productID`, `productAantal`) VALUES
(1, 1, 1, 1),
(2, 1, 2, 1),
(3, 1, 3, 1),
(4, 2, 10, 1),
(5, 2, 2, 1),
(6, 2, 26, 1),
(7, 3, 12, 1),
(8, 3, 17, 1),
(9, 3, 4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
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

DROP TABLE IF EXISTS `producten`;
CREATE TABLE `producten` (
  `productID` int(11) NOT NULL,
  `productNaam` varchar(255) NOT NULL,
  `productPrijs` float NOT NULL,
  `productGewicht` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `producten`
--

INSERT INTO `producten` (`productID`, `productNaam`, `productPrijs`, `productGewicht`) VALUES
(1, 'Fietsbel', 5, 2),
(2, 'handvat', 3, 2),
(3, 'Fietsketting', 10, 2),
(4, 'Ketting olie', 5, 2),
(5, 'Spatbord voor', 10, 2),
(6, 'Spatbord achter', 10, 2),
(7, 'Velg voor', 25, 4),
(8, 'Velg achter', 25, 4),
(9, 'fietsband voor', 20, 4),
(10, 'Fietsband achter', 20, 4),
(11, 'Baggage drager', 15, 4),
(12, 'Fietsmand', 20, 4),
(13, 'Fiets standaard', 15, 6),
(14, 'Fiets trappers', 15, 6),
(15, 'Fiets voorlicht', 7.5, 6),
(16, 'Fiets achterlicht', 7.5, 6),
(17, 'Fietsframe', 50, 6),
(18, 'Electrische fiets batterij', 250, 6),
(19, 'Electrische fiets batterij oplader', 50, 8),
(20, 'Electrische fiets motor', 250, 8),
(21, 'Spaken 50stuks', 25, 8),
(22, 'Fiets Kettingkast', 10, 8),
(23, 'Zadel', 15, 8),
(24, 'Zadelpen', 10, 8),
(25, 'Handrem', 15, 8),
(26, 'Electrische fiets batterij oplader', 50, 0),
(27, 'Electrische fiets motor', 250, 0);

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
  MODIFY `klantID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

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
