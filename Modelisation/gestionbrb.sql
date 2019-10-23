-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 23, 2019 at 10:43 PM
-- Server version: 10.4.8-MariaDB
-- PHP Version: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gestionbrb`
--

-- --------------------------------------------------------

--
-- Table structure for table `calendrier`
--

CREATE TABLE `calendrier` (
  `idReservation` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `numeroTel` int(11) NOT NULL,
  `dateReservation` datetime NOT NULL,
  `nbCouverts` tinyint(4) NOT NULL,
  `demandeSpe` tinytext NOT NULL,
  `idTable` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `commande`
--

CREATE TABLE `commande` (
  `idCommande` int(11) NOT NULL,
  `prixTotal` decimal(10,0) NOT NULL,
  `idUtilisateur` int(11) NOT NULL,
  `idTable` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `contientproduit`
--

CREATE TABLE `contientproduit` (
  `idProduit` int(11) NOT NULL,
  `idCommande` int(11) NOT NULL,
  `qte` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `fourniringredients`
--

CREATE TABLE `fourniringredients` (
  `idIngredient` int(11) NOT NULL,
  `idFournisseur` int(11) NOT NULL,
  `qteCommandee` smallint(6) NOT NULL,
  `dateCommande` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `prixTotal` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `fournisseur`
--

CREATE TABLE `fournisseur` (
  `idFournisseur` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `numTel` int(11) NOT NULL,
  `adresseMail` varchar(75) NOT NULL,
  `adresseDepot` varchar(75) NOT NULL,
  `cpDepot` int(11) NOT NULL,
  `villeDepot` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `ingredientproduit`
--

CREATE TABLE `ingredientproduit` (
  `idProduit` int(11) NOT NULL,
  `idIngredient` int(11) NOT NULL,
  `qte` smallint(6) NOT NULL,
  `unite` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `ingredients`
--

CREATE TABLE `ingredients` (
  `idIngredient` int(11) NOT NULL,
  `nomIngredient` varchar(50) NOT NULL,
  `prixIngredient` decimal(10,0) NOT NULL,
  `qteRestante` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `produit`
--

CREATE TABLE `produit` (
  `idProduit` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `qte` smallint(6) NOT NULL,
  `description` tinytext NOT NULL,
  `prix` decimal(10,0) NOT NULL,
  `idType` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tables`
--

CREATE TABLE `tables` (
  `idTable` int(11) NOT NULL,
  `numCouverts_min` tinyint(4) NOT NULL,
  `numCouverts_max` tinyint(4) NOT NULL,
  `idReservation` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `type_produit`
--

CREATE TABLE `type_produit` (
  `idType` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `utilisateurs`
--

CREATE TABLE `utilisateurs` (
  `idUtilisateur` int(11) NOT NULL,
  `identifiant` varchar(50) NOT NULL,
  `mot2passe` varchar(50) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `typeCompte` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `calendrier`
--
ALTER TABLE `calendrier`
  ADD PRIMARY KEY (`idReservation`),
  ADD KEY `Calendrier_Tables0_FK` (`idTable`);

--
-- Indexes for table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`idCommande`);

--
-- Indexes for table `contientproduit`
--
ALTER TABLE `contientproduit`
  ADD PRIMARY KEY (`idProduit`,`idCommande`);

--
-- Indexes for table `fourniringredients`
--
ALTER TABLE `fourniringredients`
  ADD PRIMARY KEY (`idIngredient`,`idFournisseur`),
  ADD KEY `FournirIngredients_Fournisseur1_FK` (`idFournisseur`);

--
-- Indexes for table `fournisseur`
--
ALTER TABLE `fournisseur`
  ADD PRIMARY KEY (`idFournisseur`);

--
-- Indexes for table `ingredientproduit`
--
ALTER TABLE `ingredientproduit`
  ADD PRIMARY KEY (`idProduit`,`idIngredient`),
  ADD KEY `IngredientProduit_Ingredients1_FK` (`idIngredient`);

--
-- Indexes for table `ingredients`
--
ALTER TABLE `ingredients`
  ADD PRIMARY KEY (`idIngredient`);

--
-- Indexes for table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`idProduit`),
  ADD KEY `Produit_Type_Produit0_FK` (`idType`);

--
-- Indexes for table `tables`
--
ALTER TABLE `tables`
  ADD PRIMARY KEY (`idTable`);

--
-- Indexes for table `type_produit`
--
ALTER TABLE `type_produit`
  ADD PRIMARY KEY (`idType`);

--
-- Indexes for table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  ADD PRIMARY KEY (`idUtilisateur`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `calendrier`
--
ALTER TABLE `calendrier`
  MODIFY `idReservation` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `commande`
--
ALTER TABLE `commande`
  MODIFY `idCommande` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `fournisseur`
--
ALTER TABLE `fournisseur`
  MODIFY `idFournisseur` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ingredients`
--
ALTER TABLE `ingredients`
  MODIFY `idIngredient` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `produit`
--
ALTER TABLE `produit`
  MODIFY `idProduit` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tables`
--
ALTER TABLE `tables`
  MODIFY `idTable` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `type_produit`
--
ALTER TABLE `type_produit`
  MODIFY `idType` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  MODIFY `idUtilisateur` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `calendrier`
--
ALTER TABLE `calendrier`
  ADD CONSTRAINT `Calendrier_Tables0_FK` FOREIGN KEY (`idTable`) REFERENCES `tables` (`idTable`);

--
-- Constraints for table `fourniringredients`
--
ALTER TABLE `fourniringredients`
  ADD CONSTRAINT `FournirIngredients_Fournisseur1_FK` FOREIGN KEY (`idFournisseur`) REFERENCES `fournisseur` (`idFournisseur`),
  ADD CONSTRAINT `FournirIngredients_Ingredients0_FK` FOREIGN KEY (`idIngredient`) REFERENCES `ingredients` (`idIngredient`);

--
-- Constraints for table `ingredientproduit`
--
ALTER TABLE `ingredientproduit`
  ADD CONSTRAINT `IngredientProduit_Ingredients1_FK` FOREIGN KEY (`idIngredient`) REFERENCES `ingredients` (`idIngredient`),
  ADD CONSTRAINT `IngredientProduit_Produit0_FK` FOREIGN KEY (`idProduit`) REFERENCES `produit` (`idProduit`);

--
-- Constraints for table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `Produit_Type_Produit0_FK` FOREIGN KEY (`idType`) REFERENCES `type_produit` (`idType`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
