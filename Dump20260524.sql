DROP TABLE IF EXISTS `boardgame`;
CREATE TABLE `boardgame` (
  `boardGameCode` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `mechanics` set('BettingAndBluffing','Draft','Cooperative','Competitive','Deduction','DiceRolling','FlipAndWrite','DeckBuilding','HiddenRoles','WorkerPlacement','TrickTracking','TilePlacement','RolePlaying','PushYourLuck') DEFAULT NULL,
  `minPlayers` tinyint unsigned DEFAULT NULL,
  `maxPlayers` tinyint unsigned DEFAULT NULL,
  `averageDuration` int unsigned DEFAULT NULL,
  `recommendedAge` int unsigned DEFAULT NULL,
  `publicationYear` year DEFAULT NULL,
  `difficulty` enum('high','medium','low') DEFAULT NULL,
  `ranking` int unsigned DEFAULT NULL,
  PRIMARY KEY (`boardGameCode`),
  UNIQUE KEY `ranking` (`ranking`),
  CONSTRAINT `boardgame_chk_1` CHECK ((`publicationYear` > 1950))
)

INSERT INTO `boardgame` VALUES (1,'Quacks of Quedlinburg','PushYourLuck',2,4,45,10,2018,'low',78),
(2,'Jaipur','Draft,Competitive',2,2,30,10,2009,'low',202),
(3,'Love Letter','Competitive,Deduction,HiddenRoles',2,4,20,10,2012,'low',367),
(4,'Sheriff of Nottingham','BettingAndBluffing,Competitive',3,6,60,14,2014,'low',610),
(5,'Brass: Birmingham','Competitive,TilePlacement',2,4,120,14,2018,'medium',1),
(6,'Terraforming Mars','Draft,TilePlacement',1,5,120,12,2016,'medium',9);




DROP TABLE IF EXISTS `designer`;

CREATE TABLE `designer` (
  `designerCode` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `alias` varchar(30) DEFAULT NULL,
  `birthYear` int DEFAULT NULL,
  `nationality` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`designerCode`)
) 


INSERT INTO `designer` VALUES (1,'Wolfgang Warsch','Warsch',1980,'Austrian'),
(2,'Gavan Brown',NULL,NULL,'Canadian'),
(3,'Isaac Fryxelius',NULL,1983,'German'),
(4,'Sérgio Halaban',NULL,NULL,'Mexican'),
(5,'Jacob Fryxelius','Fryxelius',2016,'Swedish'),
(7,'Daniel Fyxelius',NULL,NULL,'German'),
(8,'Uwe Rosenberg','Rosenberg',1970,'German');



DROP TABLE IF EXISTS `illustrator`;

CREATE TABLE `illustrator` (
  `illustratorCode` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `birthYear` int DEFAULT NULL,
  `nationality` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`illustratorCode`)
) 


INSERT INTO `illustrator` VALUES (1,'Dennis Lohausen',1977,'German'),
(2,'Ryogo Toyoda',1999,'Japanese');



DROP TABLE IF EXISTS `publisher`;

CREATE TABLE `publisher` (
  `publisherCode` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `foundationYear` int DEFAULT NULL,
  `headquarters` varchar(35) DEFAULT NULL,
  PRIMARY KEY (`publisherCode`)
) 

INSERT INTO `publisher` VALUES (1,'Devir',1987,'Spain, Barcelona');


DROP TABLE IF EXISTS `depict`;
CREATE TABLE `depict` (
  `illustratorCode` int unsigned NOT NULL,
  `boardGameCode` int unsigned NOT NULL,
  PRIMARY KEY (`illustratorCode`,`boardGameCode`)
) 


INSERT INTO `depict` VALUES (1,1),(2,1),(3,6),(4,4),(5,6);


DROP TABLE IF EXISTS `make`;


CREATE TABLE `make` (
  `designerCode` int unsigned NOT NULL,
  `boardGameCode` int unsigned NOT NULL,
  PRIMARY KEY (`designerCode`,`boardGameCode`)
) 


INSERT INTO `make` VALUES (1,1),(4,1);


DROP TABLE IF EXISTS `matches`;

CREATE TABLE `matches` (
  `matchCode` int unsigned NOT NULL AUTO_INCREMENT,
  `place` varchar(40) DEFAULT 'Estadia, Córdoba',
  `matchDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `boardGameCode` int unsigned DEFAULT NULL,
  PRIMARY KEY (`matchCode`)
) 


INSERT INTO `matches` VALUES (1,'Estadia, Córdoba','2026-05-25 22:16:00',1),
(2,'Parque de los pinos, Puente Genil','2026-05-31 22:18:00',2),
(3,'IES Francisco de los rios','2026-05-24 12:11:05',3);



DROP TABLE IF EXISTS `participate`;

CREATE TABLE `participate` (
  `participateCode` int unsigned NOT NULL AUTO_INCREMENT,
  `matchCode` int unsigned NOT NULL,
  `playerCode` int unsigned NOT NULL,
  `score` int DEFAULT NULL,
  PRIMARY KEY (`participateCode`),
  KEY `matchCode` (`matchCode`),
  KEY `playerCode` (`playerCode`),
  CONSTRAINT `participate_ibfk_1` FOREIGN KEY (`matchCode`) REFERENCES `matches` (`matchCode`),
  CONSTRAINT `participate_ibfk_2` FOREIGN KEY (`playerCode`) REFERENCES `player` (`playerCode`)
) 


INSERT INTO `participate` VALUES (1,1,1,0),(2,1,2,100),(3,2,4,0);


DROP TABLE IF EXISTS `player`;


CREATE TABLE `player` (
  `playerCode` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `birthYear` year DEFAULT NULL,
  PRIMARY KEY (`playerCode`)
) 


INSERT INTO `player` VALUES (1,'Angel',1997),
(2,'Jimmy',2001),
(3,'Peter',1996),
(4,'Johnny',2007);


DROP TABLE IF EXISTS `produce`;


CREATE TABLE `produce` (
  `publisherCode` int unsigned NOT NULL,
  `boardGameCode` int unsigned NOT NULL,
  PRIMARY KEY (`publisherCode`,`boardGameCode`)
) 


INSERT INTO `produce` VALUES (1,1),(3,0);






DROP TABLE IF EXISTS `user`;


CREATE TABLE `user` (
  `userCode` int unsigned NOT NULL AUTO_INCREMENT,
  `userName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `password` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `userType` enum('USER','ADMINISTRATOR') DEFAULT 'USER',
  PRIMARY KEY (`userCode`),
  UNIQUE KEY `userName` (`userName`)
) 

INSERT INTO `user` VALUES (1,'Luque','1fd6227a465211891ddf2da7cd8b8f143097d5ebefa804e65afe3724ee127a20','USER'),
(2,'Angel','9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08','ADMINISTRATOR'),
(3,'a','ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb','USER');