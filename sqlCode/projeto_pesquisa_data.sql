-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: projeto_pesquisa
-- ------------------------------------------------------
-- Server version	5.6.26-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `dados_economicos_mensais`
--

DROP TABLE IF EXISTS `dados_economicos_mensais`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dados_economicos_mensais` (
  `idDEM` int(11) NOT NULL AUTO_INCREMENT,
  `mes` int(11) NOT NULL,
  `ano` int(11) NOT NULL,
  `quantidade` int(11) DEFAULT NULL,
  `valorUnitario` double DEFAULT NULL,
  `idDEM_especificacaoFK` int(11) NOT NULL,
  `idPerfilFK` int(11) NOT NULL,
  PRIMARY KEY (`idDEM`),
  UNIQUE KEY `idDEM_UNIQUE` (`idDEM`),
  KEY `idPerfilFK_DEM_idx` (`idPerfilFK`),
  KEY `idDEM_especificacaoFK_DEM_idx` (`idDEM_especificacaoFK`),
  CONSTRAINT `idDEM_especificacaoFK_DEM` FOREIGN KEY (`idDEM_especificacaoFK`) REFERENCES `dem_especificacao` (`idDEM_especificacao`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `idPerfilFK_DEM` FOREIGN KEY (`idPerfilFK`) REFERENCES `perfil` (`idPerfil`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=181 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dados_economicos_mensais`
--

LOCK TABLES `dados_economicos_mensais` WRITE;
/*!40000 ALTER TABLE `dados_economicos_mensais` DISABLE KEYS */;
INSERT INTO `dados_economicos_mensais` (`idDEM`, `mes`, `ano`, `quantidade`, `valorUnitario`, `idDEM_especificacaoFK`, `idPerfilFK`) VALUES (1,1,2015,1,1600,1,1),(2,1,2015,0,0,2,1),(3,1,2015,0,0,3,1),(4,1,2015,0,0,4,1),(5,1,2015,0,0,5,1),(6,1,2015,0,0,6,1),(7,2,2015,0,0,1,1),(8,2,2015,1476,0.85,2,1),(9,2,2015,4,30,7,1),(10,2,2015,1,15.56,9,1),(11,2,2015,1,23.8,10,1),(12,2,2015,4,28,31,1),(13,2,2015,8,22,32,1),(14,2,2015,4,45,33,1),(15,2,2015,4,70,34,1),(16,2,2015,10,9,37,1),(17,2,2015,1,35,40,1),(18,2,2015,1,15.5,60,1),(20,1,2015,1910,0,1,3),(21,1,2015,1790,1.081005,2,3),(22,1,2015,4,30,8,3),(23,1,2015,1,280,9,3),(24,1,2015,1,59,10,3),(25,1,2015,120,1.081005,11,3),(26,1,2015,1,250,16,3),(27,1,2015,1,200,31,3),(28,1,2015,1,360,34,3),(29,1,2015,2,75,55,3),(30,1,2015,2,0,70,3),(31,2,2015,2300,0,1,3),(32,2,2015,2200,1.454545,2,3),(33,2,2015,4,30,8,3),(34,2,2015,1,187,9,3),(35,2,2015,1,50,10,3),(36,2,2015,100,1.454545,11,3),(37,2,2015,1,300,16,3),(38,2,2015,3,200,38,3),(39,2,2015,3,150,40,3),(40,2,2015,1,60,55,3),(41,2,2015,1,0,70,3),(42,3,2015,2356,0,1,3),(43,3,2015,2266,1.055163,2,3),(44,3,2015,1,120,8,3),(45,3,2015,1,225,9,3),(46,3,2015,1,100,10,3),(47,3,2015,90,1.06,11,3),(48,3,2015,4,30,31,3),(49,3,2015,2,70,34,3),(50,3,2015,2,200,38,3),(51,3,2015,1,37,54,3),(52,3,2015,4,45,65,3),(53,4,2015,2040,0,1,3),(54,4,2015,1950,1.0641,2,3),(55,4,2015,4,30,8,3),(56,4,2015,2,118.5,9,3),(57,4,2015,1,100,10,3),(58,4,2015,90,1.06,11,3),(59,4,2015,8,29,32,3),(60,4,2015,4,68,34,3),(61,4,2015,2,39,36,3),(62,4,2015,2,200,38,3),(63,4,2015,10,12.2,57,3),(64,4,2015,1,33,60,3),(65,4,2015,1,0,70,3),(66,5,2015,1767,0,1,3),(67,5,2015,1767,1.038856,2,3),(68,5,2015,1,237,9,3),(69,5,2015,1,150,10,3),(70,5,2015,0,0,16,3),(71,5,2015,8,29,31,3),(72,5,2015,1,33,32,3),(73,5,2015,4,68,33,3),(74,5,2015,2,220,38,3),(75,5,2015,2,6.5,39,3),(76,5,2015,30,1.5,48,3),(77,5,2015,2,13,53,3),(78,5,2015,1,0,70,3),(79,6,2015,2224,0,1,3),(80,6,2015,2134,1.058575,2,3),(81,6,2015,1,493,4,3),(82,6,2015,6,30,8,3),(83,6,2015,1,187,9,3),(84,6,2015,1,150,10,3),(85,6,2015,90,1.058575,11,3),(86,6,2015,2,39,21,3),(87,6,2015,2,100,23,3),(88,6,2015,4,30,29,3),(89,6,2015,8,30,31,3),(90,6,2015,4,22,32,3),(91,6,2015,4,33,33,3),(92,6,2015,4,66,34,3),(93,6,2015,2,250,38,3),(94,6,2015,2,6.5,39,3),(95,6,2015,4,6,60,3),(96,6,2015,2,0,70,3),(97,7,2015,1905,0,1,3),(98,7,2015,1815,1.110743,2,3),(99,7,2015,8,30,7,3),(100,7,2015,1,88,8,3),(101,7,2015,1,100,9,3),(102,7,2015,1,300,16,3),(103,7,2015,5,31.54,21,3),(104,7,2015,8,28,31,3),(105,7,2015,4,33,33,3),(106,7,2015,6,66,34,3),(107,7,2015,2,35.925,52,3),(108,7,2015,2,0,70,3),(109,8,2015,1697,0,1,3),(110,8,2015,1607,1.106004,2,3),(111,8,2015,8,35,8,3),(112,8,2015,1,60,9,3),(113,8,2015,1,100,10,3),(114,8,2015,1,300,17,3),(115,8,2015,10,28,31,3),(116,8,2015,5,67,34,3),(117,8,2015,3,97.5,40,3),(118,8,2015,0,76,52,3),(119,8,2015,2,975.3333333333334,70,3),(120,9,2015,1544,0,1,3),(121,9,2015,1454,1.1273727,2,3),(122,9,2015,8,35,8,3),(123,9,2015,1,72,9,3),(124,9,2015,1,100,10,3),(125,9,2015,90,1.1273727,11,3),(126,9,2015,1,180,18,3),(127,9,2015,6,30,28,3),(128,9,2015,8,30,30,3),(129,9,2015,1,80,52,3),(130,9,2015,2,0,70,3),(131,10,2015,1398,0,1,3),(132,10,2015,1308,1.136009,2,3),(133,10,2015,8,35,8,3),(134,10,2015,1,206,9,3),(135,10,2015,1,100,10,3),(136,10,2015,90,1.136009,11,3),(137,10,2015,2,132,14,3),(138,10,2015,1,250,15,3),(139,10,2015,1,250,27,3),(140,10,2015,120,180,28,3),(141,10,2015,8,30,31,3),(142,10,2015,4,85,34,3),(143,10,2015,1,52,40,3),(144,10,2015,2,20,53,3),(145,10,2015,2,0,70,3),(146,11,2015,2823,0,1,3),(147,11,2015,2333,1.1078654,2,3),(148,11,2015,6,35,8,3),(149,11,2015,1,225,9,3),(150,11,2015,1,100,10,3),(151,11,2015,90,1.1078654,11,3),(152,11,2015,400,1.1078654,12,3),(153,11,2015,1,350,18,3),(154,11,2015,1,250,27,3),(155,11,2015,10,50,28,3),(156,11,2015,4,30,31,3),(157,11,2015,3,85,34,3),(158,11,2015,5,30,38,3),(159,11,2015,1,52,40,3),(160,11,2015,30,1.5,48,3),(161,11,2015,1,0,70,3),(162,12,2015,3518,0,1,3),(163,12,2015,2588,1.110857,2,3),(164,12,2015,1,350,3,3),(165,12,2015,3,35,8,3),(166,12,2015,1,220,9,3),(167,12,2015,1,150,10,3),(168,12,2015,90,1.110857,11,3),(169,12,2015,840,1.110857,12,3),(170,12,2015,1,350,18,3),(171,12,2015,2,300,19,3),(172,12,2015,10,50,28,3),(173,12,2015,8,35,31,3),(174,12,2015,3,85,34,3),(175,12,2015,6,25,38,3),(176,12,2015,2,0,70,3),(178,9,2015,1,52,40,3),(179,9,2015,4,85,34,3);
/*!40000 ALTER TABLE `dados_economicos_mensais` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dados_tecnicos_mensais`
--

DROP TABLE IF EXISTS `dados_tecnicos_mensais`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dados_tecnicos_mensais` (
  `idDTM` int(11) NOT NULL AUTO_INCREMENT,
  `mes` int(11) NOT NULL,
  `ano` int(11) NOT NULL,
  `dado` double DEFAULT NULL,
  `idDTM_indicadorFK` int(11) NOT NULL,
  `idPerfilFK` int(11) NOT NULL,
  PRIMARY KEY (`idDTM`),
  UNIQUE KEY `idDTM_UNIQUE` (`idDTM`),
  KEY `idPerfilFK_DTM_idx` (`idPerfilFK`),
  KEY `idDTM_indicadoresFK_DTM_idx` (`idDTM_indicadorFK`),
  CONSTRAINT `idDTM_indicadoresFK_DTM` FOREIGN KEY (`idDTM_indicadorFK`) REFERENCES `dtm_indicador` (`idDTM_indicador`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `idPerfilFK_DTM` FOREIGN KEY (`idPerfilFK`) REFERENCES `perfil` (`idPerfil`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=244 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dados_tecnicos_mensais`
--

LOCK TABLES `dados_tecnicos_mensais` WRITE;
/*!40000 ALTER TABLE `dados_tecnicos_mensais` DISABLE KEYS */;
INSERT INTO `dados_tecnicos_mensais` (`idDTM`, `mes`, `ano`, `dado`, `idDTM_indicadorFK`, `idPerfilFK`) VALUES (1,1,2015,7,2,1),(2,1,2015,1,3,1),(3,1,2015,7,4,1),(4,1,2015,4,6,1),(5,1,2015,1,7,1),(6,1,2015,1,10,1),(7,1,2015,1662,1,1),(8,7,2015,7,2,1),(9,7,2015,1476,1,1),(11,1,2015,1910,1,3),(12,1,2015,7,2,3),(13,1,2015,5,3,3),(14,1,2015,3,4,3),(15,1,2015,5,5,3),(16,1,2015,0,6,3),(17,1,2015,0,7,3),(18,1,2015,0,8,3),(19,1,2015,0,9,3),(20,1,2015,0,10,3),(21,1,2015,0,11,3),(22,1,2015,0,12,3),(23,1,2015,1,13,3),(24,1,2015,2,14,3),(25,1,2015,1,15,3),(26,1,2015,0,16,3),(27,1,2015,0,17,3),(28,1,2015,0,18,3),(29,1,2015,0,19,3),(30,2,2015,2300,1,3),(31,2,2015,7,2,3),(32,2,2015,5,3,3),(33,2,2015,3,4,3),(34,2,2015,5,5,3),(35,2,2015,0,6,3),(36,2,2015,0,7,3),(37,2,2015,0,8,3),(38,2,2015,0,9,3),(39,2,2015,0,10,3),(40,2,2015,0,11,3),(41,2,2015,0,12,3),(42,2,2015,0,13,3),(43,2,2015,0,14,3),(44,2,2015,0,15,3),(45,2,2015,0,16,3),(46,2,2015,0,17,3),(47,2,2015,0,18,3),(48,2,2015,0,19,3),(49,3,2015,2356,1,3),(50,3,2015,7,2,3),(51,3,2015,5,3,3),(52,3,2015,3,4,3),(53,3,2015,5,5,3),(54,3,2015,0,6,3),(55,3,2015,0,7,3),(56,3,2015,0,8,3),(57,3,2015,4,9,3),(58,3,2015,0,10,3),(59,3,2015,0,11,3),(60,3,2015,0,12,3),(61,3,2015,0,13,3),(62,3,2015,0,14,3),(63,3,2015,0,15,3),(64,3,2015,0,16,3),(65,3,2015,0,17,3),(66,3,2015,0,18,3),(67,3,2015,0,19,3),(68,4,2015,2040,1,3),(69,4,2015,7,2,3),(70,4,2015,5,3,3),(71,4,2015,3,4,3),(72,4,2015,5,5,3),(73,4,2015,0,6,3),(74,4,2015,0,7,3),(75,4,2015,0,8,3),(76,4,2015,0,9,3),(77,4,2015,0,10,3),(78,4,2015,0,11,3),(79,4,2015,0,12,3),(80,4,2015,0,13,3),(81,4,2015,0,14,3),(82,4,2015,0,15,3),(83,4,2015,0,16,3),(84,4,2015,0,17,3),(85,4,2015,0,18,3),(86,4,2015,0,19,3),(87,5,2015,1767,1,3),(88,5,2015,6,2,3),(89,5,2015,8,3,3),(90,5,2015,10,4,3),(91,5,2015,1,5,3),(92,5,2015,2,6,3),(93,5,2015,0,7,3),(94,5,2015,0,8,3),(95,5,2015,0,9,3),(96,5,2015,1,10,3),(97,5,2015,0,11,3),(98,5,2015,0,12,3),(99,5,2015,0,13,3),(100,5,2015,1,14,3),(101,5,2015,0,15,3),(102,5,2015,0,16,3),(103,5,2015,0,17,3),(104,5,2015,0,18,3),(105,5,2015,0,19,3),(106,6,2015,2224,1,3),(107,6,2015,6,2,3),(108,6,2015,3,3,3),(109,6,2015,10,4,3),(110,6,2015,1,5,3),(111,6,2015,1,6,3),(112,6,2015,0,7,3),(113,6,2015,0,8,3),(114,6,2015,0,9,3),(115,6,2015,0,10,3),(116,6,2015,0,11,3),(117,6,2015,0,12,3),(118,6,2015,0,13,3),(119,6,2015,0,14,3),(120,6,2015,0,15,3),(121,6,2015,0,16,3),(122,6,2015,0,17,3),(123,6,2015,0,18,3),(124,6,2015,0,19,3),(125,7,2015,1905,1,3),(126,7,2015,6,2,3),(127,7,2015,3,3,3),(128,7,2015,10,4,3),(129,7,2015,1,5,3),(130,7,2015,1,6,3),(131,7,2015,0,7,3),(132,7,2015,0,8,3),(133,7,2015,0,9,3),(134,7,2015,0,10,3),(135,7,2015,0,11,3),(136,7,2015,0,12,3),(137,7,2015,0,13,3),(138,7,2015,0,14,3),(139,7,2015,0,15,3),(140,7,2015,0,16,3),(141,7,2015,0,17,3),(142,7,2015,0,18,3),(143,7,2015,0,19,3),(144,8,2015,1697,1,3),(145,8,2015,6,2,3),(146,8,2015,3,3,3),(147,8,2015,10,4,3),(148,8,2015,1,5,3),(149,8,2015,1,6,3),(150,8,2015,0,7,3),(151,8,2015,0,8,3),(152,8,2015,0,9,3),(153,8,2015,0,10,3),(154,8,2015,0,11,3),(155,8,2015,0,12,3),(156,8,2015,0,13,3),(157,8,2015,0,14,3),(158,8,2015,0,15,3),(159,8,2015,0,16,3),(160,8,2015,0,17,3),(161,8,2015,0,18,3),(162,8,2015,0,19,3),(163,9,2015,1544,1,3),(164,9,2015,5,2,3),(165,9,2015,4,3,3),(166,9,2015,10,4,3),(167,9,2015,1,5,3),(168,9,2015,1,6,3),(169,9,2015,0,7,3),(170,9,2015,0,8,3),(171,9,2015,0,9,3),(172,9,2015,0,10,3),(173,9,2015,0,11,3),(174,9,2015,0,12,3),(175,9,2015,0,13,3),(176,9,2015,0,14,3),(177,9,2015,0,15,3),(178,9,2015,0,16,3),(179,9,2015,0,17,3),(180,9,2015,0,18,3),(181,9,2015,0,19,3),(182,10,2015,1398,1,3),(183,10,2015,5,2,3),(184,10,2015,4,3,3),(185,10,2015,11,4,3),(186,10,2015,3,5,3),(187,10,2015,1,6,3),(188,10,2015,0,7,3),(189,10,2015,0,8,3),(190,10,2015,0,9,3),(191,10,2015,3,10,3),(192,10,2015,0,11,3),(193,10,2015,0,12,3),(194,10,2015,0,13,3),(195,10,2015,1,14,3),(196,10,2015,0,15,3),(197,10,2015,0,16,3),(198,10,2015,0,17,3),(199,10,2015,0,18,3),(200,10,2015,0,19,3),(202,11,2015,5,2,3),(203,11,2015,4,3,3),(204,11,2015,10,4,3),(205,11,2015,3,5,3),(206,11,2015,0,6,3),(207,11,2015,0,7,3),(208,11,2015,0,8,3),(209,11,2015,0,9,3),(210,11,2015,0,10,3),(211,11,2015,0,11,3),(212,11,2015,0,12,3),(213,11,2015,0,13,3),(214,11,2015,0,14,3),(215,11,2015,0,15,3),(216,11,2015,0,16,3),(217,11,2015,0,17,3),(218,11,2015,0,18,3),(219,11,2015,0,19,3),(221,12,2015,9,2,3),(222,12,2015,0,3,3),(223,12,2015,11,4,3),(224,12,2015,3,5,3),(225,12,2015,4,6,3),(226,12,2015,0,7,3),(227,12,2015,0,8,3),(228,12,2015,2,9,3),(229,12,2015,4,10,3),(230,12,2015,0,11,3),(231,12,2015,0,12,3),(232,12,2015,0,13,3),(233,12,2015,1,14,3),(234,12,2015,0,15,3),(235,12,2015,1,16,3),(236,12,2015,0,17,3),(237,12,2015,0,18,3),(238,12,2015,0,19,3),(240,11,2015,2823,1,3);
/*!40000 ALTER TABLE `dados_tecnicos_mensais` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dem_especificacao`
--

DROP TABLE IF EXISTS `dem_especificacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dem_especificacao` (
  `idDEM_especificacao` int(11) NOT NULL AUTO_INCREMENT,
  `especificacao` varchar(100) NOT NULL,
  PRIMARY KEY (`idDEM_especificacao`),
  UNIQUE KEY `idDEM_especificacao_UNIQUE` (`idDEM_especificacao`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dem_especificacao`
--

LOCK TABLES `dem_especificacao` WRITE;
/*!40000 ALTER TABLE `dem_especificacao` DISABLE KEYS */;
INSERT INTO `dem_especificacao` (`idDEM_especificacao`, `especificacao`) VALUES (1,'Produção Total de Leite'),(2,'Venda de Leite (L)'),(3,'Venda de Animais(und)'),(4,'Empréstimos'),(5,'Queijo'),(6,'Outros(Entrada)'),(7,'Mão de obra permanente'),(8,'Mão de obra temporária'),(9,'Energia'),(10,'Combustível'),(11,'Consumo Interno(produtor e func.)'),(12,'Leite para Bezerro'),(13,'Impostos e Taxas'),(14,'Reparos de Benfeitorias'),(15,'Reparos de Máquinas'),(16,'Outros(Saída)'),(17,'Gastos Extras'),(18,'Compra de volumoso (R$)'),(19,'Compra de volumoso (Kg)'),(20,'Calcário (R$)'),(21,'Adubo químico (R$)'),(22,'Adubo orgânico comprado (R$)'),(23,'Serviços mecânicos (R$)'),(24,'Serviços de tração animal (R$)'),(25,'Defensivo/veneno (R$)'),(26,'Frete (R$)'),(27,'Sementes (R$)'),(28,'Aluguel de pastagens (R$)'),(29,'Concentrado rebanho (cria)'),(30,'Concentrado rebanho (produção)'),(31,'Fubá de milho'),(32,'Farelo de trigo'),(33,'Farelo de algodão'),(34,'Farelo de soja'),(35,'Melaço'),(36,'Uréia'),(37,'Cama de galinha'),(38,'Outros(Concentrado)'),(39,'Sal comum'),(40,'Sal mineral'),(41,'Concentrado mineral'),(42,'Farinha de ossos'),(43,'Fosfato bicálcico'),(44,'Mistura preparada na fazenda'),(45,'Vermífugos'),(46,'Carrapaticida'),(47,'Mata-bicheira'),(48,'Vacina (aftosa)'),(49,'Vacina (brucelose)'),(50,'Vacina (manqueira)'),(51,'Vacina (raiva)'),(52,'Vacina (outras)'),(53,'Antibióticos'),(54,'Antitóxicos'),(55,'Material de limpeza e desinfecção'),(56,'Vitaminas e anti-inflamatório'),(57,'Detergentes'),(58,'Desinfetantes para tetos'),(59,'Materiais'),(60,'Outros(Ordenha)'),(61,'Sêmen'),(62,'Nitrogênio líquido'),(63,'Luvas'),(64,'Pipetas/bainha'),(65,'Inseminador contratado'),(66,'Formação de pastagens/Forragem de corte'),(67,'Benfeitorias/Instalações/Máquinas'),(68,'Aquisição de animais'),(69,'Pagamento de empréstimos no mês'),(70,'Mão-de-obra familiar (não paga)');
/*!40000 ALTER TABLE `dem_especificacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dtm_indicador`
--

DROP TABLE IF EXISTS `dtm_indicador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dtm_indicador` (
  `idDTM_indicador` int(11) NOT NULL AUTO_INCREMENT,
  `indicador` varchar(50) NOT NULL,
  PRIMARY KEY (`idDTM_indicador`),
  UNIQUE KEY `idDTM_indicadores_UNIQUE` (`idDTM_indicador`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dtm_indicador`
--

LOCK TABLES `dtm_indicador` WRITE;
/*!40000 ALTER TABLE `dtm_indicador` DISABLE KEYS */;
INSERT INTO `dtm_indicador` (`idDTM_indicador`, `indicador`) VALUES (1,'Total Litros/Mês (L)'),(2,'Nº V. Lactação'),(3,'Nº V. Secas'),(4,'Nº de Novilhas'),(5,'Nº de Bezerras'),(6,'Nº de Bezerros'),(7,'Touros'),(8,'Outros'),(9,'Nº de Coberturas'),(10,'Nº de Partos'),(11,'Nº de Animais Comprados'),(12,'Nº de Abortos'),(13,'Nº de Natimortos'),(14,'Nº de Retenção de Placenta'),(15,'Nº de Mortes de Bezerros'),(16,'Nº de Bezerros Doentes'),(17,'Nº de Mortes de Novilhas'),(18,'Nº de Mortes de Vacas'),(19,'Nº de Vacas com Mastite Clínica');
/*!40000 ALTER TABLE `dtm_indicador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventario_animais`
--

DROP TABLE IF EXISTS `inventario_animais`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventario_animais` (
  `idInventarioAnimais` int(11) NOT NULL AUTO_INCREMENT,
  `categoria` varchar(200) DEFAULT NULL,
  `inicio` int(11) DEFAULT '0',
  `nascimento` int(11) DEFAULT '0',
  `morte` int(11) DEFAULT '0',
  `venda` int(11) DEFAULT '0',
  `compra` int(11) DEFAULT '0',
  `final` int(11) DEFAULT '0',
  `valorCabeca` double DEFAULT '0',
  `tipoAnimal` int(11) NOT NULL,
  `ano` int(11) NOT NULL,
  `idPerfilFK` int(11) NOT NULL,
  PRIMARY KEY (`idInventarioAnimais`),
  UNIQUE KEY `idInventarioAnimais_UNIQUE` (`idInventarioAnimais`),
  KEY `idPerfilFK_Animais` (`idPerfilFK`),
  CONSTRAINT `idPerfilFK_Animais` FOREIGN KEY (`idPerfilFK`) REFERENCES `perfil` (`idPerfil`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventario_animais`
--

LOCK TABLES `inventario_animais` WRITE;
/*!40000 ALTER TABLE `inventario_animais` DISABLE KEYS */;
INSERT INTO `inventario_animais` (`idInventarioAnimais`, `categoria`, `inicio`, `nascimento`, `morte`, `venda`, `compra`, `final`, `valorCabeca`, `tipoAnimal`, `ano`, `idPerfilFK`) VALUES (1,'Touro',0,0,0,0,0,0,0,1,2015,1),(2,'Vaca Parida',7,0,0,0,0,0,1500,1,2015,1),(3,'Vaca Falhada',2,0,0,0,0,0,1500,1,2015,1),(4,'Machos 0-1 ano',0,0,0,0,0,0,0,1,2015,1),(5,'Fêmeas 0-1 ano',4,0,0,0,0,0,400,1,2015,1),(6,'Fêmeas 1-2 anos',3,0,0,0,0,0,600,1,2015,1),(7,'Fêmeas 2-3 anos',4,0,0,0,0,0,800,1,2015,1),(8,'Equinos',0,0,0,0,0,0,0,2,2015,1),(9,'Muares',2,0,0,0,0,0,200,2,2015,1),(10,'Boi de carro',0,0,0,0,0,0,0,2,2015,1),(11,'Touro',0,0,0,0,0,0,0,1,2016,3),(12,'Vaca Parida',7,0,0,0,0,9,2000,1,2016,3),(13,'Vaca Falhada',2,0,0,0,0,0,2000,1,2016,3),(14,'Macho 0-1 ano',0,0,0,0,0,4,300,1,2016,3),(15,'Femeas 0-1 ano',5,0,0,0,0,3,1500,1,2016,3),(16,'Femeas 1-2 anos',3,0,0,0,0,2,2500,1,2016,3),(17,'Femes 2-3 anos',3,0,0,0,0,3,3000,1,2016,3),(18,'Femea 3-4 anos',0,0,0,0,0,6,3000,1,2016,3),(19,'Equinos',0,0,0,0,0,0,0,2,2016,3),(20,'Muares',0,0,0,0,0,0,0,2,2016,3),(21,'Boi de Carro',0,0,0,0,0,0,0,2,2016,3);
/*!40000 ALTER TABLE `inventario_animais` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventario_benfeitorias`
--

DROP TABLE IF EXISTS `inventario_benfeitorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventario_benfeitorias` (
  `idInventarioBenfeitorias` int(11) NOT NULL AUTO_INCREMENT,
  `especificacao` varchar(90) DEFAULT NULL,
  `unidade` varchar(15) DEFAULT NULL,
  `quantidade` double DEFAULT '0',
  `valorUnitario` double DEFAULT '0',
  `vidaUtil` int(11) DEFAULT '1',
  `ano` int(11) NOT NULL,
  `idPerfilFK` int(11) NOT NULL,
  PRIMARY KEY (`idInventarioBenfeitorias`),
  UNIQUE KEY `idInventarioBenfeitorias_UNIQUE` (`idInventarioBenfeitorias`),
  KEY `idPerfilFK_Benfeitorias` (`idPerfilFK`),
  CONSTRAINT `idPerfilFK_Benfeitorias` FOREIGN KEY (`idPerfilFK`) REFERENCES `perfil` (`idPerfil`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventario_benfeitorias`
--

LOCK TABLES `inventario_benfeitorias` WRITE;
/*!40000 ALTER TABLE `inventario_benfeitorias` DISABLE KEYS */;
INSERT INTO `inventario_benfeitorias` (`idInventarioBenfeitorias`, `especificacao`, `unidade`, `quantidade`, `valorUnitario`, `vidaUtil`, `ano`, `idPerfilFK`) VALUES (1,'Sala de Ordenha','Ud',1,1600,10,2015,1),(2,'Currais de Manejo','Ud',1,500,10,2015,1),(3,'Casa Sede','Ud',1,18000,10,2015,1),(4,'Cercas Convencional','Km',25.4,3000,10,2015,1),(5,'Depósito de Ração','Ud',1,1000,10,2015,1),(6,'Cochos','Ud',1,300,10,2015,1),(7,'Açude','Ud',1,2500,20,2015,1),(8,'Armazém','R$',0,0,1,2015,1),(9,'Sala de Ordenha','Ud',0,0,1,2016,3),(10,'Currais de manejo','Ud',1,6000,25,2016,3),(11,'Casa sede','Ud',1,30000,25,2016,3),(12,'Cercas convencional','km',2.5,2600,25,2016,3),(13,'Depósito de ração','Ud',3,60,25,2016,3),(14,'Cochos','Ud',24,15000,25,2016,3),(15,'Açude','Ud',0,0,1,2016,3),(16,'Casa','Ud',0,0,1,2016,3),(17,'Bebedouro','Ud',1,180,25,2016,3),(18,'Silo','Ud',0,0,1,2016,3),(19,'Armazém','R$',0,0,1,2016,3),(20,'Bezerreiro Coletivo','R$',1,3000,25,2016,3),(21,'Galpão de máquinas','R$',1,2000,25,2016,3),(22,'Energia elétrica','R$',0,0,1,2016,3);
/*!40000 ALTER TABLE `inventario_benfeitorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventario_forrageiras`
--

DROP TABLE IF EXISTS `inventario_forrageiras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventario_forrageiras` (
  `idInventarioForrageiras` int(11) NOT NULL AUTO_INCREMENT,
  `forrageirasNaoAnuais` varchar(200) DEFAULT NULL,
  `custoFormacaoHectare` double DEFAULT NULL,
  `vidaUtil` int(11) DEFAULT NULL,
  `ano` int(11) NOT NULL,
  `idPerfilFK` int(11) NOT NULL,
  `idInventarioTerrasFK` int(11) NOT NULL,
  PRIMARY KEY (`idInventarioForrageiras`),
  KEY `idPerfilFK_forrageiras_idx` (`idPerfilFK`),
  KEY `idInventarioTerrasFK_forrageiras_idx` (`idInventarioTerrasFK`),
  CONSTRAINT `idInventarioTerrasFK_forrageiras` FOREIGN KEY (`idInventarioTerrasFK`) REFERENCES `inventario_terras` (`idInventarioTerras`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `idPerfilFK_forrageiras` FOREIGN KEY (`idPerfilFK`) REFERENCES `perfil` (`idPerfil`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventario_forrageiras`
--

LOCK TABLES `inventario_forrageiras` WRITE;
/*!40000 ALTER TABLE `inventario_forrageiras` DISABLE KEYS */;
INSERT INTO `inventario_forrageiras` (`idInventarioForrageiras`, `forrageirasNaoAnuais`, `custoFormacaoHectare`, `vidaUtil`, `ano`, `idPerfilFK`, `idInventarioTerrasFK`) VALUES (1,'Pastagem Nativa',0,1,2015,1,1),(2,'Pastagem Pangola',1000,6,2015,1,2),(3,'Palma',3000,10,2015,1,3),(4,'Reserva legal',2000,5,2016,3,4),(5,'Pastagem - Decumbens',8000,5,2016,3,5),(6,'Pastagem - Nativa',2051,5,2016,3,6),(7,'Palma',8000,5,2016,3,7),(8,'Capineira de C. Elefante',8000,5,2016,3,8),(9,'Tifton',8000,5,2016,3,9),(10,'Mombaça',8000,5,2016,3,10);
/*!40000 ALTER TABLE `inventario_forrageiras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventario_maquinas`
--

DROP TABLE IF EXISTS `inventario_maquinas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventario_maquinas` (
  `idInventarioMaquinas` int(11) NOT NULL AUTO_INCREMENT,
  `especificacao` varchar(90) DEFAULT NULL,
  `unidade` varchar(15) DEFAULT NULL,
  `quantidade` double DEFAULT '0',
  `valorUnitario` double DEFAULT '0',
  `vidaUtil` int(11) DEFAULT '1',
  `ano` int(11) NOT NULL,
  `idPerfilFK` int(11) NOT NULL,
  PRIMARY KEY (`idInventarioMaquinas`),
  UNIQUE KEY `idInventarioMaquinas_UNIQUE` (`idInventarioMaquinas`),
  KEY `idPerfilFK_Maquinas` (`idPerfilFK`),
  CONSTRAINT `idPerfilFK_Maquinas` FOREIGN KEY (`idPerfilFK`) REFERENCES `perfil` (`idPerfil`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventario_maquinas`
--

LOCK TABLES `inventario_maquinas` WRITE;
/*!40000 ALTER TABLE `inventario_maquinas` DISABLE KEYS */;
INSERT INTO `inventario_maquinas` (`idInventarioMaquinas`, `especificacao`, `unidade`, `quantidade`, `valorUnitario`, `vidaUtil`, `ano`, `idPerfilFK`) VALUES (1,'Picadeira de Forragens','Ud',1,3000,10,2015,1),(2,'Ordenhadeira Mecânica','Ud',0,5000,10,2015,1),(3,'Pulverizador','Ud',0,100,8,2015,1),(4,'Arado','Ud',0,3500,10,2015,1),(5,'Carreta','Ud',0,2300,12,2015,1),(6,'Moto','Ud',0,7000,7,2015,1),(7,'Adubadeira Tração Animal','Ud',0,2500,5,2015,1),(8,'Plaina Traseira','Ud',0,3000,10,2015,1),(9,'Batedor de Cereais','Ud',0,3000,10,2015,1),(10,'Carroça','Ud',2,700,8,2015,1),(11,'Misturador','Ud',0,1600,20,2015,1),(12,'Desintegrador','Ud',0,2000,15,2015,1),(13,'Arado Tração Animal','Ud',0,500,15,2015,1),(14,'Bomba de Água','Ud',0,500,6,2015,1),(15,'Eletrificador','Ud',0,60,5,2015,1),(16,'Caixa de Água','Ud',0,1500,20,2015,1),(17,'Roçadeira','Ud',0,700,5,2015,1),(18,'Computador','Ud',0,3500,5,2015,1),(19,'Botijão de Sêmen','Ud',1,200,8,2015,1),(20,'Outros','Ud',0,2000,20,2015,1),(21,'Picadeira de forragens','ud',2,2000,10,2016,3),(22,'Ordenhadeira mecaninca','ud',1,10000,10,2016,3),(23,'Pulverizador','ud',1,200,10,2016,3),(24,'Motor a óleo','ud',1,5000,10,2016,3),(25,'Arado','ud',0,0,1,2016,3),(26,'Ensiladeira','ud',1,1200,10,2016,3),(27,'Carreta','ud',0,0,1,2016,3),(28,'Moto','ud',1,6000,5,2016,3),(29,'Balança','ud',0,0,1,2016,3),(30,'Adubadeira tração animal','ud',0,0,1,2016,3),(31,'Plaina traseira','ud',0,0,1,2016,3),(32,'Batedor de cereais','ud',0,0,1,2016,3),(33,'Carroça','ud',0,0,1,2016,3),(34,'Misturador','ud',0,0,1,2016,3),(35,'Veículo','ud',1,35000,5,2016,3),(36,'Desintegrador','ud',0,0,1,2016,3),(37,'Arado tração animal','ud',0,0,1,2016,3),(38,'Bomba de água','ud',1,300,10,2016,3),(39,'Eletrificador','ud',2,600,10,2016,3),(40,'Caixa de água','ud',2,300,10,2016,3),(41,'Roçadeira','ud',0,0,1,2016,3),(42,'Computador','ud',2,1100,10,2016,3),(43,'Botijão de sêmen','ud',0,0,1,2016,3),(44,'Outros','ud',3,8233.33,10,2016,3);
/*!40000 ALTER TABLE `inventario_maquinas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventario_resumo`
--

DROP TABLE IF EXISTS `inventario_resumo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventario_resumo` (
  `idInventarioResumo` int(11) NOT NULL AUTO_INCREMENT,
  `custoOportunidade` double DEFAULT '0',
  `atividadeLeiteira` double DEFAULT '0',
  `salarioMinimo` double DEFAULT '0',
  `vidaUtilReprodutores` int(11) DEFAULT '0',
  `vidaUtilAnimaisServico` int(11) DEFAULT '0',
  `valorGastoCompraAnimais` double DEFAULT '0',
  `ano` int(11) NOT NULL,
  `idPerfilFK` int(11) NOT NULL,
  PRIMARY KEY (`idInventarioResumo`),
  UNIQUE KEY `idInventarioResumo_UNIQUE` (`idInventarioResumo`),
  KEY `idPerfilFK_idx` (`idPerfilFK`),
  CONSTRAINT `idPerfilFK_InventarioResumo` FOREIGN KEY (`idPerfilFK`) REFERENCES `perfil` (`idPerfil`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventario_resumo`
--

LOCK TABLES `inventario_resumo` WRITE;
/*!40000 ALTER TABLE `inventario_resumo` DISABLE KEYS */;
INSERT INTO `inventario_resumo` (`idInventarioResumo`, `custoOportunidade`, `atividadeLeiteira`, `salarioMinimo`, `vidaUtilReprodutores`, `vidaUtilAnimaisServico`, `valorGastoCompraAnimais`, `ano`, `idPerfilFK`) VALUES (1,0,0,0,0,0,0,2016,1),(2,6,80,880,5,10,0,2016,3),(3,0,0,0,0,0,0,2016,2);
/*!40000 ALTER TABLE `inventario_resumo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventario_terras`
--

DROP TABLE IF EXISTS `inventario_terras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventario_terras` (
  `idInventarioTerras` int(11) NOT NULL AUTO_INCREMENT,
  `especificacao` varchar(200) DEFAULT NULL,
  `areaArrendadaInicio` double DEFAULT '0',
  `areaPropriaInicio` double DEFAULT '0',
  `areaArrendadaFinal` double DEFAULT '0',
  `areaPropriaFinal` double DEFAULT '0',
  `valorTerraNuaPropria` double DEFAULT '0',
  `ano` int(11) NOT NULL,
  `idPerfilFK` int(11) NOT NULL,
  PRIMARY KEY (`idInventarioTerras`),
  UNIQUE KEY `idInventarioTerras_UNIQUE` (`idInventarioTerras`),
  KEY `idPerfilFK` (`idPerfilFK`),
  CONSTRAINT `idPerfilFK` FOREIGN KEY (`idPerfilFK`) REFERENCES `perfil` (`idPerfil`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventario_terras`
--

LOCK TABLES `inventario_terras` WRITE;
/*!40000 ALTER TABLE `inventario_terras` DISABLE KEYS */;
INSERT INTO `inventario_terras` (`idInventarioTerras`, `especificacao`, `areaArrendadaInicio`, `areaPropriaInicio`, `areaArrendadaFinal`, `areaPropriaFinal`, `valorTerraNuaPropria`, `ano`, `idPerfilFK`) VALUES (1,'Pastagem Nativa',0,2.8,0,0,10000,2015,1),(2,'Pastagem Pangola',0,3,0,0,10000,2015,1),(3,'Palma',0,3,0,0,10000,2015,1),(4,'Reserva legal',0,6.2,0,4,2000,2016,3),(5,'Pastagem - Decumbens',0,0,0,2,8000,2016,3),(6,'Pastagem - Nativa',0,0,0,3.9,2051,2016,3),(7,'Palma',0,0,0,0.3,8000,2016,3),(8,'Capineira de C. Elefante',0,0,0,0.3,8000,2016,3),(9,'Tifton',0,0,0,0.5,8000,2016,3),(10,'Mombaça',0,0,0,1,8000,2016,3);
/*!40000 ALTER TABLE `inventario_terras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil`
--

DROP TABLE IF EXISTS `perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perfil` (
  `idPerfil` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(200) NOT NULL,
  `cidade` varchar(45) NOT NULL,
  `tamPropriedade` double NOT NULL,
  `areaPecLeite` double NOT NULL,
  `prodLeiteDiario` double NOT NULL,
  `empPermanentes` int(11) NOT NULL,
  `numFamiliares` int(11) NOT NULL,
  `idRotaFK` int(11) NOT NULL,
  PRIMARY KEY (`idPerfil`),
  KEY `idRotaFK_Perfil_idx` (`idRotaFK`),
  CONSTRAINT `idRotaFK_Perfil` FOREIGN KEY (`idRotaFK`) REFERENCES `rota` (`idRota`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil`
--

LOCK TABLES `perfil` WRITE;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` (`idPerfil`, `nome`, `cidade`, `tamPropriedade`, `areaPecLeite`, `prodLeiteDiario`, `empPermanentes`, `numFamiliares`, `idRotaFK`) VALUES (1,'Adriana Veloso','São Bento do Una',8.8,8.8,60,0,2,1),(2,'Diniz Souza dos Santos','São Bento do Una',30.8,19.5,465,0,2,2),(3,'Sione Maria Ferreira Queiros Marques','Garanhuns(Miracica)',14,2.5,80,0,2,4);
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rota`
--

DROP TABLE IF EXISTS `rota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rota` (
  `idRota` int(11) NOT NULL AUTO_INCREMENT,
  `rota` varchar(50) NOT NULL,
  PRIMARY KEY (`idRota`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rota`
--

LOCK TABLES `rota` WRITE;
/*!40000 ALTER TABLE `rota` DISABLE KEYS */;
INSERT INTO `rota` (`idRota`, `rota`) VALUES (1,'São Bento Curta'),(2,'São Bento Longa'),(3,'Lajedo'),(4,'Miracica');
/*!40000 ALTER TABLE `rota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `senha` varchar(45) NOT NULL,
  `tipoUsuario` int(11) NOT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `idUsuario_UNIQUE` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`idUsuario`, `login`, `senha`, `tipoUsuario`) VALUES (1,'adriana','adriana',2),(2,'diniz','diniz',3),(3,'adm','adm',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_perfil`
--

DROP TABLE IF EXISTS `usuario_perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario_perfil` (
  `idUsuarioPerfil` int(11) NOT NULL AUTO_INCREMENT,
  `idUsuarioFK` int(11) NOT NULL,
  `idPerfilFK` int(11) NOT NULL,
  PRIMARY KEY (`idUsuarioPerfil`),
  KEY `idUsuarioFK_usuario_perfil_idx` (`idUsuarioFK`),
  KEY `idPerfilFK_usuario_perfil_idx` (`idPerfilFK`),
  CONSTRAINT `idPerfilFK_usuario_perfil` FOREIGN KEY (`idPerfilFK`) REFERENCES `perfil` (`idPerfil`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idUsuarioFK_usuario_perfil` FOREIGN KEY (`idUsuarioFK`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_perfil`
--

LOCK TABLES `usuario_perfil` WRITE;
/*!40000 ALTER TABLE `usuario_perfil` DISABLE KEYS */;
INSERT INTO `usuario_perfil` (`idUsuarioPerfil`, `idUsuarioFK`, `idPerfilFK`) VALUES (1,1,1),(2,2,2),(3,3,1),(4,3,2);
/*!40000 ALTER TABLE `usuario_perfil` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-21 15:55:24
