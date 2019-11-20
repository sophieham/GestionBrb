-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  mer. 20 nov. 2019 à 16:42
-- Version du serveur :  10.4.8-MariaDB
-- Version de PHP :  7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `gestionbrb`
--

-- --------------------------------------------------------

--
-- Structure de la table `calendrier`
--

CREATE TABLE `calendrier` (
  `idReservation` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `numeroTel` varchar(13) NOT NULL,
  `dateReservation` date NOT NULL,
  `heureReservation` varchar(5) NOT NULL,
  `nbCouverts` tinyint(4) NOT NULL,
  `demandeSpe` tinytext NOT NULL,
  `noTable` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `calendrier`
--

INSERT INTO `calendrier` (`idReservation`, `nom`, `prenom`, `numeroTel`, `dateReservation`, `heureReservation`, `nbCouverts`, `demandeSpe`, `noTable`) VALUES
(1, 'Nicole', 'Nicolas', '654548758', '2019-11-19', '19:30', 2, 'Biscuits au cacao', 1),
(2, 'Loli', 'Lola', '65487457', '2019-11-12', '20:30', 5, '', 2),
(4, 'Odoki', 'Leslie', '+33554025448', '2019-11-26', '21:30', 4, '', 2);

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `idCommande` int(11) NOT NULL,
  `noTable` tinyint(4) NOT NULL,
  `prixTotal` decimal(5,2) DEFAULT NULL,
  `nbCouverts` tinyint(2) DEFAULT NULL,
  `qteTotal` tinyint(3) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `contenirproduit`
--

CREATE TABLE `contenirproduit` (
  `idContient` int(11) NOT NULL,
  `idProduit` int(11) NOT NULL,
  `idCommande` int(11) NOT NULL,
  `qte` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `fourniringredients`
--

CREATE TABLE `fourniringredients` (
  `idOperation` int(11) NOT NULL,
  `idFournisseur` int(11) NOT NULL,
  `idIngredient` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `fourniringredients`
--

INSERT INTO `fourniringredients` (`idOperation`, `idFournisseur`, `idIngredient`) VALUES
(1, 1, 2),
(2, 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `fournisseur`
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

--
-- Déchargement des données de la table `fournisseur`
--

INSERT INTO `fournisseur` (`idFournisseur`, `nom`, `numTel`, `adresseMail`, `adresseDepot`, `cpDepot`, `villeDepot`) VALUES
(1, 'Bonduelle', 23124287, 'contact@bonduelle.fr', '27 rue de billy', 92120, 'Couville');

-- --------------------------------------------------------

--
-- Structure de la table `ingredients`
--

CREATE TABLE `ingredients` (
  `idIngredient` int(11) NOT NULL,
  `nomIngredient` varchar(50) NOT NULL,
  `prixIngredient` decimal(10,0) NOT NULL,
  `qteRestante` smallint(6) NOT NULL,
  `idfournisseur` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `ingredients`
--

INSERT INTO `ingredients` (`idIngredient`, `nomIngredient`, `prixIngredient`, `qteRestante`, `idfournisseur`) VALUES
(1, 'Courgettes', '2', 5, 1),
(2, 'Aubergine', '2', 2, 1);

-- --------------------------------------------------------

--
-- Structure de la table `ingredientsproduits`
--

CREATE TABLE `ingredientsproduits` (
  `idUnion` int(11) NOT NULL,
  `idProduit` int(11) NOT NULL,
  `idIngredient` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `idProduit` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `qte` smallint(5) NOT NULL,
  `description` tinytext NOT NULL,
  `prix` decimal(5,2) NOT NULL,
  `idType` int(11) NOT NULL,
  `details` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`idProduit`, `nom`, `qte`, `description`, `prix`, `idType`, `details`) VALUES
(1, 'Poelée de légumes', 2, '', '10.00', 5, '');

-- --------------------------------------------------------

--
-- Structure de la table `tables`
--

CREATE TABLE `tables` (
  `idTable` int(11) NOT NULL,
  `noTable` tinyint(4) NOT NULL,
  `nbCouverts_min` tinyint(4) NOT NULL,
  `nbCouverts_max` tinyint(4) NOT NULL,
  `idReservation` int(11) DEFAULT NULL,
  `occupation` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `tables`
--

INSERT INTO `tables` (`idTable`, `noTable`, `nbCouverts_min`, `nbCouverts_max`, `idReservation`, `occupation`) VALUES
(1, 1, 1, 4, 1, 1),
(2, 2, 1, 6, 2, 1),
(3, 3, 1, 2, NULL, 1),
(4, 4, 1, 2, NULL, 1),
(5, 5, 1, 4, NULL, 1),
(6, 6, 1, 4, NULL, 0),
(7, 7, 1, 4, NULL, 1);

-- --------------------------------------------------------

--
-- Structure de la table `type_produit`
--

CREATE TABLE `type_produit` (
  `idType` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `type_produit`
--

INSERT INTO `type_produit` (`idType`, `nom`) VALUES
(1, 'Entrée'),
(5, 'Salade');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateurs`
--

CREATE TABLE `utilisateurs` (
  `idCompte` int(11) NOT NULL,
  `identifiant` varchar(50) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `typeCompte` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `utilisateurs`
--

INSERT INTO `utilisateurs` (`idCompte`, `identifiant`, `pass`, `nom`, `prenom`, `typeCompte`) VALUES
(1, 'soso', '123', 'Sophie', 'Ham', 1);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `calendrier`
--
ALTER TABLE `calendrier`
  ADD PRIMARY KEY (`idReservation`),
  ADD KEY `Calendrier_Tables0_FK` (`noTable`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`idCommande`),
  ADD KEY `noTable` (`noTable`);

--
-- Index pour la table `contenirproduit`
--
ALTER TABLE `contenirproduit`
  ADD PRIMARY KEY (`idContient`),
  ADD KEY `idProduit` (`idProduit`),
  ADD KEY `idCommande` (`idCommande`);

--
-- Index pour la table `fourniringredients`
--
ALTER TABLE `fourniringredients`
  ADD PRIMARY KEY (`idOperation`),
  ADD KEY `idFournisseur` (`idFournisseur`),
  ADD KEY `idIngredient` (`idIngredient`);

--
-- Index pour la table `fournisseur`
--
ALTER TABLE `fournisseur`
  ADD PRIMARY KEY (`idFournisseur`);

--
-- Index pour la table `ingredients`
--
ALTER TABLE `ingredients`
  ADD PRIMARY KEY (`idIngredient`),
  ADD KEY `idfournisseur` (`idfournisseur`);

--
-- Index pour la table `ingredientsproduits`
--
ALTER TABLE `ingredientsproduits`
  ADD PRIMARY KEY (`idUnion`),
  ADD KEY `idProduit` (`idProduit`),
  ADD KEY `idIngredient` (`idIngredient`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`idProduit`),
  ADD KEY `Produit_Type_Produit0_FK` (`idType`);

--
-- Index pour la table `tables`
--
ALTER TABLE `tables`
  ADD PRIMARY KEY (`idTable`),
  ADD KEY `idReservation` (`idReservation`),
  ADD KEY `noTable` (`noTable`);

--
-- Index pour la table `type_produit`
--
ALTER TABLE `type_produit`
  ADD PRIMARY KEY (`idType`);

--
-- Index pour la table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  ADD PRIMARY KEY (`idCompte`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `calendrier`
--
ALTER TABLE `calendrier`
  MODIFY `idReservation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `commande`
--
ALTER TABLE `commande`
  MODIFY `idCommande` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `contenirproduit`
--
ALTER TABLE `contenirproduit`
  MODIFY `idContient` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `fourniringredients`
--
ALTER TABLE `fourniringredients`
  MODIFY `idOperation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `fournisseur`
--
ALTER TABLE `fournisseur`
  MODIFY `idFournisseur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `ingredients`
--
ALTER TABLE `ingredients`
  MODIFY `idIngredient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `ingredientsproduits`
--
ALTER TABLE `ingredientsproduits`
  MODIFY `idUnion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `idProduit` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `tables`
--
ALTER TABLE `tables`
  MODIFY `idTable` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `type_produit`
--
ALTER TABLE `type_produit`
  MODIFY `idType` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  MODIFY `idCompte` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `calendrier`
--
ALTER TABLE `calendrier`
  ADD CONSTRAINT `Calendrier_Tables0_FK` FOREIGN KEY (`noTable`) REFERENCES `tables` (`idTable`);

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `commande_ibfk_1` FOREIGN KEY (`noTable`) REFERENCES `tables` (`noTable`);

--
-- Contraintes pour la table `contenirproduit`
--
ALTER TABLE `contenirproduit`
  ADD CONSTRAINT `contenirproduit_ibfk_1` FOREIGN KEY (`idProduit`) REFERENCES `produit` (`idProduit`),
  ADD CONSTRAINT `contenirproduit_ibfk_2` FOREIGN KEY (`idCommande`) REFERENCES `commande` (`idCommande`);

--
-- Contraintes pour la table `fourniringredients`
--
ALTER TABLE `fourniringredients`
  ADD CONSTRAINT `fourniringredients_ibfk_1` FOREIGN KEY (`idFournisseur`) REFERENCES `fournisseur` (`idFournisseur`),
  ADD CONSTRAINT `fourniringredients_ibfk_2` FOREIGN KEY (`idIngredient`) REFERENCES `ingredients` (`idIngredient`);

--
-- Contraintes pour la table `ingredients`
--
ALTER TABLE `ingredients`
  ADD CONSTRAINT `ingredients_ibfk_1` FOREIGN KEY (`idfournisseur`) REFERENCES `fournisseur` (`idFournisseur`);

--
-- Contraintes pour la table `ingredientsproduits`
--
ALTER TABLE `ingredientsproduits`
  ADD CONSTRAINT `ingredientsproduits_ibfk_1` FOREIGN KEY (`idProduit`) REFERENCES `produit` (`idProduit`),
  ADD CONSTRAINT `ingredientsproduits_ibfk_2` FOREIGN KEY (`idIngredient`) REFERENCES `ingredients` (`idIngredient`);

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `Produit_Type_Produit0_FK` FOREIGN KEY (`idType`) REFERENCES `type_produit` (`idType`);

--
-- Contraintes pour la table `tables`
--
ALTER TABLE `tables`
  ADD CONSTRAINT `tables_ibfk_1` FOREIGN KEY (`idReservation`) REFERENCES `calendrier` (`idReservation`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
