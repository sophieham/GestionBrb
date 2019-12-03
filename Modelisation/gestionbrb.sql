-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  mar. 03 déc. 2019 à 19:26
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
  `ReservationID` int(11) NOT NULL,
  `Nom` varchar(50) NOT NULL,
  `Prenom` varchar(50) NOT NULL,
  `Numero_Tel` varchar(13) NOT NULL,
  `Date_Reservation` date NOT NULL,
  `Heure_Reservation` varchar(5) NOT NULL,
  `Nombre_Couverts` tinyint(4) NOT NULL,
  `Demande_Speciale` tinytext NOT NULL,
  `NoTable` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `calendrier`
--

INSERT INTO `calendrier` (`ReservationID`, `Nom`, `Prenom`, `Numero_Tel`, `Date_Reservation`, `Heure_Reservation`, `Nombre_Couverts`, `Demande_Speciale`, `NoTable`) VALUES
(1, 'Nicole', 'Nicolas', '654548758', '2019-11-19', '19:30', 2, 'Biscuits au cacao', 1),
(2, 'Loli', 'Lola', '65487457', '2019-11-12', '20:30', 5, '', 2),
(5, 'Hooki', 'Lola', '00234578557', '2019-11-12', '20:30', 5, '', 2),
(6, 'Miku', 'Jacqueline', '0635210147', '2019-11-13', '11:30', 5, '', 2),
(7, 'Laniki', 'Laniki', '+3354567658', '2019-11-12', '20:11', 2, '', 3);

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `CommandeID` int(11) NOT NULL,
  `noTable` tinyint(4) NOT NULL,
  `prixTotal` decimal(5,2) DEFAULT NULL,
  `Reste_A_Payer` decimal(5,2) DEFAULT NULL,
  `nbCouverts` tinyint(2) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `commande`
--

INSERT INTO `commande` (`CommandeID`, `noTable`, `prixTotal`, `Reste_A_Payer`, `nbCouverts`, `date`) VALUES
(1, 2, '2.00', '2.00', 2, '2019-11-30 16:58:11'),
(2, 3, '3.00', '3.00', 3, '2018-11-05 16:58:11'),
(3, 4, '4.00', '4.00', 4, '2019-11-26 23:00:00'),
(4, 5, '5.00', '5.00', 5, '2019-11-06 23:00:00'),
(5, 6, '6.00', '6.00', 6, '2019-10-15 22:00:00'),
(6, 2, '4.00', '4.00', 2, '2019-12-01 11:58:11'),
(7, 6, '4.00', '4.00', 2, '2019-11-22 11:58:11'),
(8, 4, NULL, NULL, 2, '2019-12-01 17:04:56'),
(9, 3, NULL, NULL, 2, '2019-12-01 21:25:56'),
(10, 3, NULL, NULL, 2, '2019-12-01 21:28:55'),
(11, 3, NULL, NULL, 2, '2019-12-01 21:29:32'),
(12, 1, NULL, NULL, 2, '2019-12-01 21:31:38'),
(13, 1, NULL, NULL, 2, '2019-12-01 21:37:10'),
(14, 1, NULL, NULL, 2, '2019-12-01 21:39:23'),
(15, 1, NULL, NULL, 2, '2019-12-01 21:40:34'),
(16, 1, NULL, NULL, 2, '2019-12-01 21:41:23'),
(17, 1, NULL, NULL, 2, '2019-12-01 21:43:35'),
(18, 3, NULL, NULL, 2, '2019-12-01 21:49:58'),
(19, 1, NULL, NULL, 2, '2019-12-01 21:56:56'),
(20, 1, NULL, NULL, 2, '2019-12-01 21:57:30'),
(21, 4, NULL, NULL, 2, '2019-12-01 22:01:24'),
(22, 1, NULL, NULL, 2, '2019-12-01 22:09:00'),
(23, 3, NULL, NULL, 2, '2019-12-01 22:09:44'),
(24, 1, NULL, NULL, 2, '2019-12-01 22:13:47'),
(25, 3, NULL, NULL, 2, '2019-12-01 22:15:48'),
(26, 1, NULL, NULL, 2, '2019-12-01 22:19:15'),
(27, 3, NULL, NULL, 2, '2019-12-01 22:20:33'),
(28, 1, NULL, NULL, 2, '2019-12-01 22:23:28'),
(29, 3, NULL, NULL, 2, '2019-12-01 22:27:22'),
(30, 3, NULL, NULL, 2, '2019-12-01 22:29:01'),
(31, 3, NULL, NULL, 2, '2019-12-01 22:29:45'),
(32, 1, NULL, NULL, 2, '2019-12-01 22:35:41'),
(33, 3, NULL, NULL, 2, '2019-12-01 22:39:32'),
(34, 1, '10.50', NULL, 2, '2019-12-01 22:49:54'),
(35, 1, NULL, NULL, 2, '2019-12-01 22:51:43'),
(36, 1, '10.99', NULL, 2, '2019-12-01 23:05:08'),
(37, 3, '2.00', NULL, 2, '2019-12-01 23:05:40'),
(38, 3, '13.98', '13.98', 2, '2019-12-02 08:43:47'),
(39, 3, '49.00', '0.00', 2, '2019-12-02 09:37:14'),
(40, 3, '9.98', '0.00', 2, '2019-12-02 09:50:46'),
(41, 3, NULL, NULL, 2, '2019-12-02 09:51:29'),
(42, 4, NULL, NULL, 2, '2019-12-02 09:51:37'),
(43, 1, '4.99', NULL, 2, '2019-12-02 09:54:48'),
(44, 3, NULL, NULL, 2, '2019-12-02 09:55:03'),
(45, 3, '10.00', NULL, 2, '2019-12-02 09:57:14'),
(46, 4, '4.99', NULL, 2, '2019-12-02 09:58:54'),
(47, 1, NULL, NULL, 2, '2019-12-02 09:59:04'),
(48, 3, '6.99', '6.99', 2, '2019-12-02 10:01:03'),
(49, 4, '4.99', '0.00', 2, '2019-12-02 10:05:51'),
(50, 1, '10.80', '10.80', 1, '2019-12-02 10:11:13'),
(51, 1, '4.00', '0.00', 2, '2019-12-02 10:16:08'),
(52, 4, '4.99', NULL, 2, '2019-12-02 10:32:28'),
(53, 3, NULL, NULL, 2, '2019-12-02 10:34:03'),
(54, 3, NULL, NULL, 2, '2019-12-02 10:42:26'),
(55, 4, '4.99', NULL, 2, '2019-12-02 10:46:35'),
(56, 3, '25.96', '0.00', 1, '2019-12-02 14:31:50'),
(57, 6, '33.94', '0.00', 3, '2019-12-02 15:44:00'),
(58, 7, '13.98', '0.00', 2, '2019-12-02 15:51:07');

-- --------------------------------------------------------

--
-- Structure de la table `contenirproduit`
--

CREATE TABLE `contenirproduit` (
  `ContenirID` int(11) NOT NULL,
  `ProduitID` int(11) NOT NULL,
  `CommandeID` int(11) NOT NULL,
  `qte` smallint(6) NOT NULL,
  `serveurID` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `contenirproduit`
--

INSERT INTO `contenirproduit` (`ContenirID`, `ProduitID`, `CommandeID`, `qte`, `serveurID`) VALUES
(1, 7, 2, 2, 'marie.dlp'),
(2, 23, 3, 1, 'emma.j'),
(3, 11, 6, 1, 'emma.j'),
(4, 21, 6, 1, 'emma.j'),
(5, 5, 6, 2, 'marie.dlp'),
(6, 12, 5, 1, 'marie.dlp'),
(7, 18, 36, 1, 'soso'),
(8, 15, 36, 3, 'soso'),
(9, 13, 37, 1, 'soso'),
(10, 11, 37, 1, 'soso'),
(11, 8, 37, 1, 'soso'),
(12, 23, 37, 1, 'soso'),
(13, 7, 38, 1, 'soso'),
(14, 13, 38, 1, 'soso'),
(15, 12, 38, 2, 'soso'),
(16, 1, 39, 2, 'soso'),
(17, 16, 39, 1, 'soso'),
(18, 7, 39, 1, 'soso'),
(19, 13, 40, 2, 'soso'),
(20, 13, 43, 2, 'soso'),
(21, 1, 45, 1, 'soso'),
(22, 13, 46, 1, 'soso'),
(23, 6, 48, 1, 'soso'),
(24, 7, 49, 1, 'soso'),
(25, 13, 49, 1, 'soso'),
(26, 9, 49, 1, 'soso'),
(27, 17, 50, 1, 'soso'),
(28, 7, 51, 1, 'soso'),
(29, 8, 52, 1, 'soso'),
(30, 11, 52, 1, 'soso'),
(31, 12, 52, 1, 'soso'),
(32, 17, 55, 1, 'soso'),
(33, 16, 55, 1, 'soso'),
(34, 15, 55, 1, 'soso'),
(35, 1, 55, 1, 'soso'),
(36, 21, 55, 1, 'soso'),
(37, 7, 56, 1, 'soso'),
(38, 13, 56, 1, 'soso'),
(39, 5, 56, 1, 'soso'),
(40, 11, 56, 1, 'soso'),
(41, 10, 56, 1, 'soso'),
(43, 5, 57, 2, 'soso'),
(44, 11, 57, 1, 'soso'),
(45, 8, 57, 1, 'soso'),
(46, 13, 58, 1, 'soso'),
(47, 7, 58, 1, 'soso'),
(48, 11, 58, 1, 'soso');

-- --------------------------------------------------------

--
-- Structure de la table `fourniringredients`
--

CREATE TABLE `fourniringredients` (
  `idOperation` int(11) NOT NULL,
  `idFournisseur` int(11) NOT NULL,
  `idIngredient` int(11) DEFAULT NULL,
  `qte` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `fourniringredients`
--

INSERT INTO `fourniringredients` (`idOperation`, `idFournisseur`, `idIngredient`, `qte`) VALUES
(1, 1, 2, 0),
(2, 1, 1, 0);

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
(1, 'Bonduelle', 23124287, 'contact@bonduelle.fr', '27 rue de billy', 92120, 'Couville'),
(2, 'Topinambourr', 635415789, 'pro@topasso.fr', '20 rue du Faubourg', 92110, 'Clamart'),
(3, 'Frutto', 233212425, 'melfrutto@frutto.fr', '20 boulevard coccy', 75012, 'Paris'),
(4, 'Delisso', 247851163, 'contact@delisso.fr', 'Impasse du front', 92140, 'Charlesville');

-- --------------------------------------------------------

--
-- Structure de la table `ingredients`
--

CREATE TABLE `ingredients` (
  `IngredientID` int(11) NOT NULL,
  `nomIngredient` varchar(50) NOT NULL,
  `prixIngredient` decimal(10,0) NOT NULL,
  `qteRestante` smallint(6) NOT NULL,
  `idfournisseur` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `ingredients`
--

INSERT INTO `ingredients` (`IngredientID`, `nomIngredient`, `prixIngredient`, `qteRestante`, `idfournisseur`) VALUES
(1, 'Courgettes', '2', 5, 1),
(2, 'Aubergine', '2', 2, 1),
(3, 'Asperges', '5', 2, 2),
(4, 'Citron', '5', 10, 3);

-- --------------------------------------------------------

--
-- Structure de la table `ingredientsproduits`
--

CREATE TABLE `ingredientsproduits` (
  `ID` int(11) NOT NULL,
  `ProduitID` int(11) NOT NULL,
  `IngredientID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `logs`
--

CREATE TABLE `logs` (
  `ID` int(11) NOT NULL,
  `compteID` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `preferences`
--

CREATE TABLE `preferences` (
  `idPreference` int(11) NOT NULL,
  `devise` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `preferences`
--

INSERT INTO `preferences` (`idPreference`, `devise`) VALUES
(1, 'EUR');

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `ProduitID` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `qte` smallint(5) NOT NULL,
  `description` tinytext NOT NULL,
  `prix` decimal(5,2) NOT NULL,
  `TypeID` int(11) NOT NULL,
  `ingredients` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`ProduitID`, `nom`, `qte`, `description`, `prix`, `TypeID`, `ingredients`) VALUES
(1, 'Poelée de légumes', 2, 'Un mélange printanier de légumes.', '10.00', 5, 'aubergines, champignons, courgettes'),
(2, 'Champignons a l\'ail', 2, 'Champignons émincés nappés d\'une délicieuse sauce à l\'ail', '15.00', 1, 'champignons, ail, noix de muscade'),
(5, 'bûche au chocolat ', 5, '', '6.99', 6, 'sucre, oeuf, farine, sucre, noisettes, jaune d\'oeuf, farine de maïs, lait, beurre, chocolat'),
(6, 'glace au chocolat', 8, '', '6.99', 6, 'chocolat noir, creme liquide, lait, vanille, sucre'),
(7, 'Brownies', 5, 'Un moelleux incomparable', '4.00', 6, 'chocolat pâtissier, sucre vanillé, beurre, oeuf, sucre, farine'),
(8, 'Mille-feuille', 4, '', '4.99', 6, 'pâte feuilletée, oeuf, lait, sucre, farine, sucre vanillé, sucre glace, chocolat'),
(9, 'Ile flottante', 9, '', '4.99', 6, 'oeuf, lait, vanille, amande, sucre en poudre, sucre en morceaux, citron'),
(10, 'Tarte au fraises', 5, '', '4.99', 6, 'farine, beurre, sucre, jaune d\'oeuf, sel, lait, oeuf, farine, sucre, sucre vanillé, fraises'),
(11, 'Cheesecake au citron', 5, '', '4.99', 6, 'biscuit, beurre doux, citron, fromage blanc, sucre semoule, farine, oeuf, crème fraîche'),
(12, 'Mousse au chocolat', 8, '', '4.99', 6, 'oeuf, chocolat, sucre vanillé'),
(13, 'Fondant au chocolat', 5, '', '4.99', 6, 'chocolat, beurre doux, sucre semoule, oeuf, farine'),
(14, 'Tiramisu', 8, '', '5.99', 6, 'oeuf, sucre, sucre vanillé, mascarpone, biscuit cuillère, café, cacao amer'),
(15, 'Poulet coco curry', 6, '', '10.99', 8, 'escalope de poulet, noix de coco, gingembre, lait de coco, curry, oignon jaune, champignon de Paris'),
(16, 'Paella', 9, '', '10.50', 8, 'riz long, poulet, moules, tomate, poivron, chorizo, petit pois, épices à paella'),
(17, 'Hachis parmentier', 5, '', '10.80', 8, 'viande, purée, tomate, herbes de Provence, parmesan, beurre, fromage râpé'),
(18, 'Lasagnes à la bolognaise', 5, '', '8.50', 8, 'pâtes, céleri, carotte, viande, purée de tomate, vin rouge, basilic, fromage râpé, parmesan'),
(19, 'Taboulé libanais', 5, 'Le gout du Liban!', '6.00', 1, 'tomate, oignon, persil, menthe, boulgour, citron, huile d\'olive'),
(20, 'Caviar d\'aubergines', 8, '', '6.99', 1, 'aubergine, ail, olives noires, paprika, cumin, poivre, marjolaine, huile d\'olive'),
(21, 'Rouleaux de printemps', 8, '', '4.99', 1, 'galette de riz, vermicelles de riz, crevette, porc, salade, menthe'),
(23, 'Clémentines', 15, 'Juteuses et sucrées!', '2.00', 6, '');

-- --------------------------------------------------------

--
-- Structure de la table `tables`
--

CREATE TABLE `tables` (
  `TableID` int(11) NOT NULL,
  `noTable` tinyint(4) NOT NULL,
  `nbCouverts_min` tinyint(4) NOT NULL,
  `nbCouverts_max` tinyint(4) NOT NULL,
  `ReservationID` int(11) DEFAULT NULL,
  `occupation` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `tables`
--

INSERT INTO `tables` (`TableID`, `noTable`, `nbCouverts_min`, `nbCouverts_max`, `ReservationID`, `occupation`) VALUES
(1, 1, 1, 4, 1, 0),
(2, 2, 1, 6, 2, 1),
(3, 3, 1, 2, NULL, 1),
(4, 4, 1, 2, NULL, 1),
(5, 5, 1, 4, NULL, 1),
(6, 6, 1, 4, NULL, 1),
(7, 7, 1, 6, NULL, 1),
(8, 8, 2, 4, NULL, 0);

-- --------------------------------------------------------

--
-- Structure de la table `type_produit`
--

CREATE TABLE `type_produit` (
  `TypeID` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `type_produit`
--

INSERT INTO `type_produit` (`TypeID`, `nom`) VALUES
(1, 'Entrée'),
(5, 'Salade'),
(6, 'Desserts'),
(7, 'Boissons'),
(8, 'Plat principal');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateurs`
--

CREATE TABLE `utilisateurs` (
  `CompteID` int(11) NOT NULL,
  `identifiant` varchar(50) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `typeCompte` tinyint(4) NOT NULL,
  `langue` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `utilisateurs`
--

INSERT INTO `utilisateurs` (`CompteID`, `identifiant`, `pass`, `nom`, `prenom`, `typeCompte`, `langue`) VALUES
(1, 'soso', '123', 'Sophie', 'Ham', 1, 'en'),
(2, 'emma.j', '1579', 'Emma', 'Juvelle', 0, 'fr'),
(3, 'marie.dlp', '123', 'Marie', 'Dulop', 0, 'zh');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `calendrier`
--
ALTER TABLE `calendrier`
  ADD PRIMARY KEY (`ReservationID`),
  ADD KEY `Calendrier_Tables0_FK` (`NoTable`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`CommandeID`),
  ADD KEY `noTable_FK` (`noTable`) USING BTREE;

--
-- Index pour la table `contenirproduit`
--
ALTER TABLE `contenirproduit`
  ADD PRIMARY KEY (`ContenirID`),
  ADD KEY `idProduit` (`ProduitID`),
  ADD KEY `idCommande` (`CommandeID`),
  ADD KEY `identifiant_FK` (`serveurID`);

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
  ADD PRIMARY KEY (`IngredientID`),
  ADD KEY `idfournisseur` (`idfournisseur`);

--
-- Index pour la table `ingredientsproduits`
--
ALTER TABLE `ingredientsproduits`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `idProduit` (`ProduitID`),
  ADD KEY `idIngredient` (`IngredientID`);

--
-- Index pour la table `preferences`
--
ALTER TABLE `preferences`
  ADD PRIMARY KEY (`idPreference`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`ProduitID`),
  ADD KEY `Produit_Type_Produit0_FK` (`TypeID`);

--
-- Index pour la table `tables`
--
ALTER TABLE `tables`
  ADD PRIMARY KEY (`TableID`),
  ADD KEY `idReservation` (`ReservationID`),
  ADD KEY `noTable` (`noTable`);

--
-- Index pour la table `type_produit`
--
ALTER TABLE `type_produit`
  ADD PRIMARY KEY (`TypeID`);

--
-- Index pour la table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  ADD PRIMARY KEY (`CompteID`),
  ADD KEY `identifiant` (`identifiant`) USING BTREE;

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `calendrier`
--
ALTER TABLE `calendrier`
  MODIFY `ReservationID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `commande`
--
ALTER TABLE `commande`
  MODIFY `CommandeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT pour la table `contenirproduit`
--
ALTER TABLE `contenirproduit`
  MODIFY `ContenirID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT pour la table `fourniringredients`
--
ALTER TABLE `fourniringredients`
  MODIFY `idOperation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `fournisseur`
--
ALTER TABLE `fournisseur`
  MODIFY `idFournisseur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT pour la table `ingredients`
--
ALTER TABLE `ingredients`
  MODIFY `IngredientID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `ingredientsproduits`
--
ALTER TABLE `ingredientsproduits`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `preferences`
--
ALTER TABLE `preferences`
  MODIFY `idPreference` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `ProduitID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT pour la table `tables`
--
ALTER TABLE `tables`
  MODIFY `TableID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `type_produit`
--
ALTER TABLE `type_produit`
  MODIFY `TypeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  MODIFY `CompteID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `calendrier`
--
ALTER TABLE `calendrier`
  ADD CONSTRAINT `Calendrier_Tables0_FK` FOREIGN KEY (`noTable`) REFERENCES `tables` (`TableID`);

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `commande_ibfk_1` FOREIGN KEY (`noTable`) REFERENCES `tables` (`noTable`);

--
-- Contraintes pour la table `contenirproduit`
--
ALTER TABLE `contenirproduit`
  ADD CONSTRAINT `contenirproduit_ibfk_1` FOREIGN KEY (`ProduitID`) REFERENCES `produit` (`ProduitID`),
  ADD CONSTRAINT `contenirproduit_ibfk_2` FOREIGN KEY (`CommandeID`) REFERENCES `commande` (`CommandeID`),
  ADD CONSTRAINT `identifiant_FK` FOREIGN KEY (`serveurID`) REFERENCES `utilisateurs` (`identifiant`);

--
-- Contraintes pour la table `fourniringredients`
--
ALTER TABLE `fourniringredients`
  ADD CONSTRAINT `fourniringredients_ibfk_1` FOREIGN KEY (`idFournisseur`) REFERENCES `fournisseur` (`idFournisseur`),
  ADD CONSTRAINT `fourniringredients_ibfk_2` FOREIGN KEY (`idIngredient`) REFERENCES `ingredients` (`IngredientID`);

--
-- Contraintes pour la table `ingredients`
--
ALTER TABLE `ingredients`
  ADD CONSTRAINT `ingredients_ibfk_1` FOREIGN KEY (`idfournisseur`) REFERENCES `fournisseur` (`idFournisseur`);

--
-- Contraintes pour la table `ingredientsproduits`
--
ALTER TABLE `ingredientsproduits`
  ADD CONSTRAINT `ingredientsproduits_ibfk_1` FOREIGN KEY (`ProduitID`) REFERENCES `produit` (`ProduitID`),
  ADD CONSTRAINT `ingredientsproduits_ibfk_2` FOREIGN KEY (`IngredientID`) REFERENCES `ingredients` (`IngredientID`);

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `Produit_Type_Produit0_FK` FOREIGN KEY (`TypeID`) REFERENCES `type_produit` (`TypeID`);

--
-- Contraintes pour la table `tables`
--
ALTER TABLE `tables`
  ADD CONSTRAINT `tables_ibfk_1` FOREIGN KEY (`ReservationID`) REFERENCES `calendrier` (`ReservationID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
