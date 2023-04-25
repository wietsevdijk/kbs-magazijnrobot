-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 25, 2023 at 11:24 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 7.3.33

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
  `klantAdres` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `klanten`
--

INSERT INTO `klanten` (`klantID`, `klantVoornaam`, `klantAchternaam`, `klantAdres`) VALUES
(1, 'Dane', 'Schuijt', 'Soendastraat 5');

-- --------------------------------------------------------

--
-- Table structure for table `orderregels`
--

CREATE TABLE `orderregels` (
  `orderRegelID` int(11) NOT NULL,
  `orderID` int(11) NOT NULL,
  `productID` int(11) NOT NULL,
  `productNaam` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orderregels`
--

INSERT INTO `orderregels` (`orderRegelID`, `orderID`, `productID`, `productNaam`) VALUES
(1, 1, 1, 'Fietsbel'),
(2, 1, 2, 'handvat'),
(3, 1, 3, 'Fietsketting'),
(4, 2, 10, 'Fietsband achter'),
(5, 2, 2, 'handvat'),
(6, 2, 26, 'Electrische fiets batterij oplader'),
(7, 3, 12, 'Fietsmand'),
(8, 3, 17, 'Fietsframe'),
(9, 3, 4, 'Ketting olie');

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
  `productPrijs` float NOT NULL,
  `productLocatie` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `producten`
--

INSERT INTO `producten` (`productID`, `productNaam`, `productPrijs`, `productLocatie`) VALUES
(1, 'Fietsbel', 5, '11'),
(2, 'handvat', 3, '21'),
(3, 'Fietsketting', 10, '31'),
(4, 'Ketting olie', 5, '41'),
(5, 'Spatbord voor', 10, '51'),
(6, 'Spatbord achter', 10, '12'),
(7, 'Velg voor', 25, '22'),
(8, 'Velg achter', 25, '32'),
(9, 'fietsband voor', 20, '42'),
(10, 'Fietsband achter', 20, '52'),
(11, 'Baggage drager', 15, '13'),
(12, 'Fietsmand', 20, '23'),
(13, 'Fiets standaard', 15, '33'),
(14, 'Fiets trappers', 15, '43'),
(15, 'Fiets voorlicht', 7.5, '53'),
(16, 'Fiets achterlicht', 7.5, '14'),
(17, 'Fietsframe', 50, '24'),
(18, 'Electrische fiets batterij', 250, '34'),
(19, 'Electrische fiets batterij oplader', 50, '44'),
(20, 'Electrische fiets motor', 250, '54'),
(21, 'Spaken 50stuks', 25, '15'),
(22, 'Fiets Kettingkast', 10, '25'),
(23, 'Zadel', 15, '35'),
(24, 'Zadelpen', 10, '45'),
(25, 'Handrem', 15, '55'),
(26, 'Electrische fiets batterij oplader', 50, '44'),
(27, 'Electrische fiets motor', 250, '54');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `klanten`
--
ALTER TABLE `klanten`
  ADD PRIMARY KEY (`klantID`);

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
  ADD PRIMARY KEY (`orderID`);

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
