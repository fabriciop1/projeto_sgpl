-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema projeto_pesquisa
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema projeto_pesquisa
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `projeto_pesquisa` DEFAULT CHARACTER SET utf8 ;
USE `projeto_pesquisa` ;

-- -----------------------------------------------------
-- Table `projeto_pesquisa`.`rota`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_pesquisa`.`rota` (
  `idRota` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `rota` VARCHAR(50) NOT NULL COMMENT '',
  PRIMARY KEY (`idRota`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projeto_pesquisa`.`perfil`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_pesquisa`.`perfil` (
  `idPerfil` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `nome` VARCHAR(200) NOT NULL COMMENT '',
  `cidade` VARCHAR(45) NOT NULL COMMENT '',
  `tamPropriedade` DOUBLE NOT NULL COMMENT '',
  `areaPecLeite` DOUBLE NOT NULL COMMENT '',
  `prodLeiteDiario` DOUBLE NOT NULL COMMENT '',
  `empPermanentes` INT(11) NOT NULL COMMENT '',
  `numFamiliares` INT(11) NOT NULL COMMENT '',
  `idRotaFK` INT NOT NULL COMMENT '',
  PRIMARY KEY (`idPerfil`)  COMMENT '',
  INDEX `idRotaFK_Perfil_idx` (`idRotaFK` ASC)  COMMENT '',
  CONSTRAINT `idRotaFK_Perfil`
    FOREIGN KEY (`idRotaFK`)
    REFERENCES `projeto_pesquisa`.`rota` (`idRota`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `projeto_pesquisa`.`inventario_animais`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_pesquisa`.`inventario_animais` (
  `idInventarioAnimais` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `categoria` VARCHAR(200) NULL DEFAULT NULL COMMENT '',
  `inicio` INT(11) NULL DEFAULT 0 COMMENT '',
  `nascimento` INT(11) NULL DEFAULT 0 COMMENT '',
  `morte` INT(11) NULL DEFAULT 0 COMMENT '',
  `venda` INT(11) NULL DEFAULT 0 COMMENT '',
  `compra` INT(11) NULL DEFAULT 0 COMMENT '',
  `final` INT(11) NULL DEFAULT 0 COMMENT '',
  `valorCabeca` DOUBLE NULL DEFAULT 0 COMMENT '',
  `tipoAnimal` INT(11) NOT NULL COMMENT '',
  `ano` INT NOT NULL COMMENT '',
  `idPerfilFK` INT NOT NULL COMMENT '',
  PRIMARY KEY (`idInventarioAnimais`)  COMMENT '',
  UNIQUE INDEX `idInventarioAnimais_UNIQUE` (`idInventarioAnimais` ASC)  COMMENT '',
  INDEX `idPerfilFK_Animais` (`idPerfilFK` ASC)  COMMENT '',
  CONSTRAINT `idPerfilFK_Animais`
    FOREIGN KEY (`idPerfilFK`)
    REFERENCES `projeto_pesquisa`.`perfil` (`idPerfil`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `projeto_pesquisa`.`inventario_benfeitorias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_pesquisa`.`inventario_benfeitorias` (
  `idInventarioBenfeitorias` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `especificacao` VARCHAR(90) NULL DEFAULT NULL COMMENT '',
  `unidade` VARCHAR(15) NULL DEFAULT NULL COMMENT '',
  `quantidade` DOUBLE NULL DEFAULT 0 COMMENT '',
  `valorUnitario` DOUBLE NULL DEFAULT 0 COMMENT '',
  `vidaUtil` INT(11) NULL DEFAULT 1 COMMENT '',
  `ano` INT NOT NULL COMMENT '',
  `idPerfilFK` INT NOT NULL COMMENT '',
  PRIMARY KEY (`idInventarioBenfeitorias`)  COMMENT '',
  UNIQUE INDEX `idInventarioBenfeitorias_UNIQUE` (`idInventarioBenfeitorias` ASC)  COMMENT '',
  INDEX `idPerfilFK_Benfeitorias` (`idPerfilFK` ASC)  COMMENT '',
  CONSTRAINT `idPerfilFK_Benfeitorias`
    FOREIGN KEY (`idPerfilFK`)
    REFERENCES `projeto_pesquisa`.`perfil` (`idPerfil`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `projeto_pesquisa`.`inventario_maquinas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_pesquisa`.`inventario_maquinas` (
  `idInventarioMaquinas` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `especificacao` VARCHAR(90) NULL DEFAULT NULL COMMENT '',
  `unidade` VARCHAR(15) NULL DEFAULT NULL COMMENT '',
  `quantidade` DOUBLE NULL DEFAULT 0.0 COMMENT '',
  `valorUnitario` DOUBLE NULL DEFAULT 0.0 COMMENT '',
  `vidaUtil` INT(11) NULL DEFAULT 1 COMMENT '',
  `ano` INT NOT NULL COMMENT '',
  `idPerfilFK` INT NOT NULL COMMENT '',
  PRIMARY KEY (`idInventarioMaquinas`)  COMMENT '',
  UNIQUE INDEX `idInventarioMaquinas_UNIQUE` (`idInventarioMaquinas` ASC)  COMMENT '',
  INDEX `idPerfilFK_Maquinas` (`idPerfilFK` ASC)  COMMENT '',
  CONSTRAINT `idPerfilFK_Maquinas`
    FOREIGN KEY (`idPerfilFK`)
    REFERENCES `projeto_pesquisa`.`perfil` (`idPerfil`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `projeto_pesquisa`.`inventario_resumo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_pesquisa`.`inventario_resumo` (
  `idInventarioResumo` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `custoOportunidade` DOUBLE NULL DEFAULT 0.0 COMMENT '',
  `atividadeLeiteira` DOUBLE NULL DEFAULT 0.0 COMMENT '',
  `salarioMinimo` DOUBLE NULL DEFAULT 0.0 COMMENT '',
  `vidaUtilReprodutores` INT NULL DEFAULT 0 COMMENT '',
  `vidaUtilAnimaisServico` INT NULL DEFAULT 0 COMMENT '',
  `valorGastoCompraAnimais` DOUBLE NULL DEFAULT 0.0 COMMENT '',
  `ano` INT NOT NULL COMMENT '',
  `idPerfilFK` INT(11) NOT NULL COMMENT '',
  PRIMARY KEY (`idInventarioResumo`)  COMMENT '',
  UNIQUE INDEX `idInventarioResumo_UNIQUE` (`idInventarioResumo` ASC)  COMMENT '',
  INDEX `idPerfilFK_idx` (`idPerfilFK` ASC)  COMMENT '',
  CONSTRAINT `idPerfilFK_InventarioResumo`
    FOREIGN KEY (`idPerfilFK`)
    REFERENCES `projeto_pesquisa`.`perfil` (`idPerfil`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `projeto_pesquisa`.`inventario_terras`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_pesquisa`.`inventario_terras` (
  `idInventarioTerras` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `especificacao` VARCHAR(200) NULL COMMENT '',
  `areaArrendadaInicio` DOUBLE NULL DEFAULT 0.0 COMMENT '',
  `areaPropriaInicio` DOUBLE NULL DEFAULT 0.0 COMMENT '',
  `areaArrendadaFinal` DOUBLE NULL DEFAULT 0.0 COMMENT '',
  `areaPropriaFinal` DOUBLE NULL DEFAULT 0.0 COMMENT '',
  `valorTerraNuaPropria` DOUBLE NULL DEFAULT 0.0 COMMENT '',
  `ano` INT NOT NULL COMMENT '',
  `idPerfilFK` INT(11) NOT NULL COMMENT '',
  PRIMARY KEY (`idInventarioTerras`)  COMMENT '',
  UNIQUE INDEX `idInventarioTerras_UNIQUE` (`idInventarioTerras` ASC)  COMMENT '',
  INDEX `idPerfilFK` (`idPerfilFK` ASC)  COMMENT '',
  CONSTRAINT `idPerfilFK`
    FOREIGN KEY (`idPerfilFK`)
    REFERENCES `projeto_pesquisa`.`perfil` (`idPerfil`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `projeto_pesquisa`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_pesquisa`.`usuario` (
  `idUsuario` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `login` VARCHAR(45) NOT NULL COMMENT '',
  `senha` VARCHAR(45) NOT NULL COMMENT '',
  `tipoUsuario` INT NOT NULL COMMENT '',
  PRIMARY KEY (`idUsuario`)  COMMENT '',
  UNIQUE INDEX `idUsuario_UNIQUE` (`idUsuario` ASC)  COMMENT '')
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `projeto_pesquisa`.`dem_especificacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_pesquisa`.`dem_especificacao` (
  `idDEM_especificacao` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `especificacao` VARCHAR(100) NOT NULL COMMENT '',
  PRIMARY KEY (`idDEM_especificacao`)  COMMENT '',
  UNIQUE INDEX `idDEM_especificacao_UNIQUE` (`idDEM_especificacao` ASC)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projeto_pesquisa`.`dados_economicos_mensais`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_pesquisa`.`dados_economicos_mensais` (
  `idDEM` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `mes` INT NOT NULL COMMENT '',
  `ano` INT NOT NULL COMMENT '',
  `quantidade` INT NULL COMMENT '',
  `valorUnitario` DOUBLE NULL COMMENT '',
  `idDEM_especificacaoFK` INT NOT NULL COMMENT '',
  `idPerfilFK` INT NOT NULL COMMENT '',
  PRIMARY KEY (`idDEM`)  COMMENT '',
  INDEX `idPerfilFK_DEM_idx` (`idPerfilFK` ASC)  COMMENT '',
  INDEX `idDEM_especificacaoFK_DEM_idx` (`idDEM_especificacaoFK` ASC)  COMMENT '',
  UNIQUE INDEX `idDEM_UNIQUE` (`idDEM` ASC)  COMMENT '',
  CONSTRAINT `idPerfilFK_DEM`
    FOREIGN KEY (`idPerfilFK`)
    REFERENCES `projeto_pesquisa`.`perfil` (`idPerfil`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `idDEM_especificacaoFK_DEM`
    FOREIGN KEY (`idDEM_especificacaoFK`)
    REFERENCES `projeto_pesquisa`.`dem_especificacao` (`idDEM_especificacao`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projeto_pesquisa`.`inventario_forrageiras`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_pesquisa`.`inventario_forrageiras` (
  `idInventarioForrageiras` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `forrageirasNaoAnuais` VARCHAR(200) NULL COMMENT '',
  `custoFormacaoHectare` DOUBLE NULL COMMENT '',
  `vidaUtil` INT NULL COMMENT '',
  `ano` INT NOT NULL COMMENT '',
  `idPerfilFK` INT NOT NULL COMMENT '',
  `idInventarioTerrasFK` INT NOT NULL COMMENT '',
  PRIMARY KEY (`idInventarioForrageiras`)  COMMENT '',
  INDEX `idPerfilFK_forrageiras_idx` (`idPerfilFK` ASC)  COMMENT '',
  INDEX `idInventarioTerrasFK_forrageiras_idx` (`idInventarioTerrasFK` ASC)  COMMENT '',
  CONSTRAINT `idPerfilFK_forrageiras`
    FOREIGN KEY (`idPerfilFK`)
    REFERENCES `projeto_pesquisa`.`perfil` (`idPerfil`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `idInventarioTerrasFK_forrageiras`
    FOREIGN KEY (`idInventarioTerrasFK`)
    REFERENCES `projeto_pesquisa`.`inventario_terras` (`idInventarioTerras`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projeto_pesquisa`.`dtm_indicador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_pesquisa`.`dtm_indicador` (
  `idDTM_indicador` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `indicador` VARCHAR(50) NOT NULL COMMENT '',
  PRIMARY KEY (`idDTM_indicador`)  COMMENT '',
  UNIQUE INDEX `idDTM_indicadores_UNIQUE` (`idDTM_indicador` ASC)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projeto_pesquisa`.`dados_tecnicos_mensais`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_pesquisa`.`dados_tecnicos_mensais` (
  `idDTM` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `mes` INT NOT NULL COMMENT '',
  `ano` INT NOT NULL COMMENT '',
  `dado` DOUBLE NULL COMMENT '',
  `idDTM_indicadorFK` INT NOT NULL COMMENT '',
  `idPerfilFK` INT NOT NULL COMMENT '',
  PRIMARY KEY (`idDTM`)  COMMENT '',
  UNIQUE INDEX `idDTM_UNIQUE` (`idDTM` ASC)  COMMENT '',
  INDEX `idPerfilFK_DTM_idx` (`idPerfilFK` ASC)  COMMENT '',
  INDEX `idDTM_indicadoresFK_DTM_idx` (`idDTM_indicadorFK` ASC)  COMMENT '',
  CONSTRAINT `idDTM_indicadoresFK_DTM`
    FOREIGN KEY (`idDTM_indicadorFK`)
    REFERENCES `projeto_pesquisa`.`dtm_indicador` (`idDTM_indicador`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `idPerfilFK_DTM`
    FOREIGN KEY (`idPerfilFK`)
    REFERENCES `projeto_pesquisa`.`perfil` (`idPerfil`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projeto_pesquisa`.`usuario_perfil`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto_pesquisa`.`usuario_perfil` (
  `idUsuarioPerfil` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `idUsuarioFK` INT NOT NULL COMMENT '',
  `idPerfilFK` INT NOT NULL COMMENT '',
  PRIMARY KEY (`idUsuarioPerfil`)  COMMENT '',
  INDEX `idUsuarioFK_usuario_perfil_idx` (`idUsuarioFK` ASC)  COMMENT '',
  INDEX `idPerfilFK_usuario_perfil_idx` (`idPerfilFK` ASC)  COMMENT '',
  CONSTRAINT `idUsuarioFK_usuario_perfil`
    FOREIGN KEY (`idUsuarioFK`)
    REFERENCES `projeto_pesquisa`.`usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `idPerfilFK_usuario_perfil`
    FOREIGN KEY (`idPerfilFK`)
    REFERENCES `projeto_pesquisa`.`perfil` (`idPerfil`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
