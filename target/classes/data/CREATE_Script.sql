
-- -----------------------------------------------------
-- Table `autoDB`.`USTATUS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`USTATUS` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`USTATUS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `status_UNIQUE` (`status` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`COUNTRY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`COUNTRY` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`COUNTRY` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `a3code` VARCHAR(3) NOT NULL,
  `a2code` VARCHAR(2) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  UNIQUE INDEX `codeun_UNIQUE` (`a3code` ASC),
  UNIQUE INDEX `codeiso_UNIQUE` (`a2code` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`REGION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`REGION` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`REGION` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `COUNTRY_ID` INT NOT NULL,
  `abbrev2l` VARCHAR(5) NULL,
  PRIMARY KEY (`ID`, `COUNTRY_ID`),
  INDEX `fk_REGION_COUNTRY1_idx` (`COUNTRY_ID` ASC),
  CONSTRAINT `fk_REGION_COUNTRY1`
    FOREIGN KEY (`COUNTRY_ID`)
    REFERENCES `autoDB`.`COUNTRY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`ADDRESS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`ADDRESS` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`ADDRESS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `street` VARCHAR(75) NOT NULL,
  `other` VARCHAR(45) NULL,
  `city` VARCHAR(45) NOT NULL,
  `mailcode` VARCHAR(10) NOT NULL,
  `REGION_ID` INT NOT NULL,
  PRIMARY KEY (`ID`, `REGION_ID`),
  INDEX `fk_ADDRESS_REGION1_idx` (`REGION_ID` ASC),
  CONSTRAINT `fk_ADDRESS_REGION1`
    FOREIGN KEY (`REGION_ID`)
    REFERENCES `autoDB`.`REGION` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`USERS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`USERS` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`USERS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(15) NOT NULL,
  `password` CHAR(255) NOT NULL,
  `firstname` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `phonemain` VARCHAR(25) NOT NULL,
  `phoneaux` VARCHAR(25) NULL,
  `email` VARCHAR(45) NULL,
  `ADDRESS_ID` INT NOT NULL,
  `STATUS_ID` INT NOT NULL,
  PRIMARY KEY (`ID`, `ADDRESS_ID`, `STATUS_ID`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `names_UNIQUE` (`firstname` ASC, `lastname` ASC),
  INDEX `fk_USERS_ADDRESS1_idx` (`ADDRESS_ID` ASC),
  INDEX `fk_USERS_USTATUS1_idx` (`STATUS_ID` ASC),
  CONSTRAINT `fk_USERS_ADDRESS1`
    FOREIGN KEY (`ADDRESS_ID`)
    REFERENCES `autoDB`.`ADDRESS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USERS_USTATUS1`
    FOREIGN KEY (`STATUS_ID`)
    REFERENCES `autoDB`.`USTATUS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`ROLE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`ROLE` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`ROLE` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `role_desc` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `role_UNIQUE` (`role_desc` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`APP_USER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`APP_USER` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`APP_USER` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `stimedate` DATE NOT NULL,
  `etimedate` TIMESTAMP NULL,
  `APPUSER_ID` INT NOT NULL,
  PRIMARY KEY (`ID`, `APPUSER_ID`),
  INDEX `fk_APP_USER_USERS_idx` (`APPUSER_ID` ASC),
  CONSTRAINT `fk_APP_USER_USERS`
    FOREIGN KEY (`APPUSER_ID`)
    REFERENCES `autoDB`.`USERS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`USER_ROLE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`USER_ROLE` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`USER_ROLE` (
  `USER_ID` INT NOT NULL AUTO_INCREMENT,
  `ROLE_ID` INT NOT NULL,
  PRIMARY KEY (`USER_ID`, `ROLE_ID`),
  INDEX `fk_USERS_has_ROLE_ROLE1_idx` (`ROLE_ID` ASC),
  INDEX `fk_USERS_has_ROLE_USERS1_idx` (`USER_ID` ASC),
  CONSTRAINT `fk_USERS_has_ROLE_USERS1`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `autoDB`.`USERS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USERS_has_ROLE_ROLE1`
    FOREIGN KEY (`ROLE_ID`)
    REFERENCES `autoDB`.`ROLE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`OWNER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`OWNER` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`OWNER` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(25) NOT NULL,
  `phoneother` VARCHAR(25) NULL,
  `ADDRESS_ID` INT NOT NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`, `ADDRESS_ID`),
  UNIQUE INDEX `UNIQUE_names` (`lastname` ASC, `firstname` ASC),
  UNIQUE INDEX `UNIQUE_names_phone` (`phone` ASC, `lastname` ASC, `firstname` ASC),
  INDEX `fk_OWNER_ADDRESS1_idx` (`ADDRESS_ID` ASC),
  CONSTRAINT `fk_OWNER_ADDRESS1`
    FOREIGN KEY (`ADDRESS_ID`)
    REFERENCES `autoDB`.`ADDRESS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`MAKE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`MAKE` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`MAKE` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `longname` VARCHAR(45) NOT NULL,
  `shortname` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `UNIQUE_names` (`longname` ASC, `shortname` ASC),
  UNIQUE INDEX `UNIQUE_longname` (`longname` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`MODEL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`MODEL` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`MODEL` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(15) NOT NULL,
  `year` VARCHAR(5) NOT NULL,
  `MAKE_ID` INT NOT NULL,
  `trim` VARCHAR(45) NOT NULL,
  `engdesc` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`, `MAKE_ID`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC, `trim` ASC, `engdesc` ASC),
  INDEX `fk_MODEL_MAKE1_idx` (`MAKE_ID` ASC),
  CONSTRAINT `fk_MODEL_MAKE1`
    FOREIGN KEY (`MAKE_ID`)
    REFERENCES `autoDB`.`MAKE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`VEHICLE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`VEHICLE` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`VEHICLE` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `VIN` VARCHAR(45) NOT NULL,
  `plate` VARCHAR(15) NOT NULL DEFAULT 'ZZZ999',
  `vcolor` VARCHAR(15) NOT NULL DEFAULT 'COPPER',
  `MODEL_ID` INT NOT NULL,
  `OWNER_ID` INT NOT NULL,
  PRIMARY KEY (`ID`, `MODEL_ID`, `OWNER_ID`),
  UNIQUE INDEX `VIN_UNIQUE` (`VIN` ASC),
  INDEX `fk_VEHICLE_MODEL1_idx` (`MODEL_ID` ASC),
  INDEX `fk_VEHICLE_OWNER1_idx` (`OWNER_ID` ASC),
  UNIQUE INDEX `PLATE_UNIQUE` (`plate` ASC),
  CONSTRAINT `fk_VEHICLE_MODEL1`
    FOREIGN KEY (`MODEL_ID`)
    REFERENCES `autoDB`.`MODEL` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_VEHICLE_OWNER1`
    FOREIGN KEY (`OWNER_ID`)
    REFERENCES `autoDB`.`OWNER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`SSTATUS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`SSTATUS` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`SSTATUS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(25) NOT NULL,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `status_UNIQUE` (`status` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`SERVICEORDER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`SERVICEORDER` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`SERVICEORDER` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `sdate` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `edate` DATETIME NULL,
  `pcost` DECIMAL(7,2) NULL DEFAULT 0.00,
  `lcosts` DECIMAL(7,2) NOT NULL DEFAULT 0.00,
  `scomment` VARCHAR(150) NULL,
  `odometer` BIGINT(7) NOT NULL DEFAULT 0,
  `jobid` VARCHAR(10) NOT NULL,
  `description` VARCHAR(255) NULL,
  `SSTATUS_ID` INT NOT NULL,
  `VEHICLE_ID` INT NOT NULL,
  PRIMARY KEY (`ID`, `SSTATUS_ID`, `VEHICLE_ID`),
  INDEX `fk_SERVICE_VEHICLE1_idx` (`VEHICLE_ID` ASC),
  INDEX `fk_SERVICEORDER_SSTATUS1_idx` (`SSTATUS_ID` ASC),
  UNIQUE INDEX `jobid_UNIQUE` (`jobid` ASC),
  CONSTRAINT `fk_SERVICE_VEHICLE1`
    FOREIGN KEY (`VEHICLE_ID`)
    REFERENCES `autoDB`.`VEHICLE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SERVICEORDER_SSTATUS1`
    FOREIGN KEY (`SSTATUS_ID`)
    REFERENCES `autoDB`.`SSTATUS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`CATEGORY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`CATEGORY` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`CATEGORY` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `description_UNIQUE` (`description` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`INVENTORY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`INVENTORY` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`INVENTORY` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `partnr` VARCHAR(15) NOT NULL,
  `description` VARCHAR(75) NOT NULL,
  `costprice` DECIMAL(7,2) NOT NULL DEFAULT 0.0,
  `redist` BIGINT(5) NOT NULL,
  `quantity` DECIMAL(4,1) NOT NULL,
  `qadj` DECIMAL(4,1) NULL DEFAULT 0.0,
  `CATEGORY_ID` INT NOT NULL,
  `measure` ENUM('U', 'L', 'M', 'CM', 'IN', 'G', 'Set') NOT NULL DEFAULT 'U',
  `reference` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`, `CATEGORY_ID`),
  INDEX `fk_INVENTORY_CATEGORY1_idx` (`CATEGORY_ID` ASC),
  CONSTRAINT `fk_INVENTORY_CATEGORY1`
    FOREIGN KEY (`CATEGORY_ID`)
    REFERENCES `autoDB`.`CATEGORY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`PARTS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`PARTS` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`PARTS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `pdesc` VARCHAR(45) NOT NULL,
  `INVENTORY_ID` INT NOT NULL,
  `qty` DECIMAL(5,1) NOT NULL DEFAULT 0.0,
  `price` DECIMAL(6,2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY (`ID`),
  INDEX `fk_PARTS_INVENTORY1_idx` (`INVENTORY_ID` ASC),
  CONSTRAINT `fk_PARTS_INVENTORY1`
    FOREIGN KEY (`INVENTORY_ID`)
    REFERENCES `autoDB`.`INVENTORY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`WORK`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`WORK` ;
CREATE TABLE IF NOT EXISTS `autoDB`.`WORK` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `wdesc` VARCHAR(75) NOT NULL,
  `duration` FLOAT NOT NULL DEFAULT 0.0,
  `wcosts` DECIMAL(6,2) NOT NULL DEFAULT 0.00,
  `wrate` DECIMAL(6,2) NOT NULL DEFAULT 0.00,
  `SERVICEORDER_ID` INT NOT NULL,
  `PARTS_ID` INT NOT NULL,
  `STATUS_ID` INT NOT NULL,
  PRIMARY KEY (`ID`, `SERVICEORDER_ID`),
  INDEX `fk_PARTS_SERVICEORDER1_idx` (`SERVICEORDER_ID` ASC),
  INDEX `fk_WORK_PARTS1_idx` (`PARTS_ID` ASC),
  INDEX `fk_WORK_SSTATUS1_idx` (`STATUS_ID` ASC),
  CONSTRAINT `fk_PARTS_SERVICEORDER1`
    FOREIGN KEY (`SERVICEORDER_ID`)
    REFERENCES `autoDB`.`SERVICEORDER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_WORK_PARTS1`
    FOREIGN KEY (`PARTS_ID`)
    REFERENCES `autoDB`.`PARTS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_WORK_SSTATUS1`
    FOREIGN KEY (`STATUS_ID`)
    REFERENCES `autoDB`.`SSTATUS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

--CREATE TABLE IF NOT EXISTS `autoDB`.`WORK` (
--  `ID` INT NOT NULL AUTO_INCREMENT,
--  `wdesc` VARCHAR(75) NOT NULL,
--  `duration` FLOAT NOT NULL DEFAULT 0.0,
--  `wcosts` DECIMAL(6,2) NOT NULL DEFAULT 0.00,
--  `wrate` DECIMAL(6,2) NOT NULL DEFAULT 0.00,
--  `SERVICEORDER_ID` INT NOT NULL,
--  `PARTS_ID` INT NOT NULL,
--  PRIMARY KEY (`ID`, `SERVICEORDER_ID`),
--  INDEX `fk_PARTS_SERVICEORDER1_idx` (`SERVICEORDER_ID` ASC),
--  INDEX `fk_WORK_PARTS1_idx` (`PARTS_ID` ASC),
--  CONSTRAINT `fk_PARTS_SERVICEORDER1`
--    FOREIGN KEY (`SERVICEORDER_ID`)
--    REFERENCES `autoDB`.`SERVICEORDER` (`ID`)
--    ON DELETE NO ACTION
--    ON UPDATE NO ACTION,
--  CONSTRAINT `fk_WORK_PARTS1`
--    FOREIGN KEY (`PARTS_ID`)
--    REFERENCES `autoDB`.`PARTS` (`ID`)
--    ON DELETE NO ACTION
--    ON UPDATE NO ACTION)
--ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`SOURCE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`SOURCE` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`SOURCE` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `ADDRESS_ID` INT NOT NULL,
  `phone` VARCHAR(15) NOT NULL,
  `phoneother` VARCHAR(15) NULL,
  `contact` VARCHAR(45) NULL,
  `reference` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`, `ADDRESS_ID`),
  INDEX `fk_SOURCE_ADDRESS1_idx` (`ADDRESS_ID` ASC),
  UNIQUE INDEX `reference_UNIQUE` (`reference` ASC),
  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  CONSTRAINT `fk_SOURCE_ADDRESS1`
    FOREIGN KEY (`ADDRESS_ID`)
    REFERENCES `autoDB`.`ADDRESS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`INVENTORY_SOURCE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`INVENTORY_SOURCE` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`INVENTORY_SOURCE` (
  `INVENTORY_ID` INT NOT NULL AUTO_INCREMENT,
  `SOURCE_ID` INT NOT NULL,
  PRIMARY KEY (`INVENTORY_ID`, `SOURCE_ID`),
  INDEX `fk_INVENTORY_has_SOURCE_SOURCE1_idx` (`SOURCE_ID` ASC),
  INDEX `fk_INVENTORY_has_SOURCE_INVENTORY1_idx` (`INVENTORY_ID` ASC),
  CONSTRAINT `fk_INVENTORY_has_SOURCE_INVENTORY1`
    FOREIGN KEY (`INVENTORY_ID`)
    REFERENCES `autoDB`.`INVENTORY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_INVENTORY_has_SOURCE_SOURCE1`
    FOREIGN KEY (`SOURCE_ID`)
    REFERENCES `autoDB`.`SOURCE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`ENGINE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`ENGINE` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`ENGINE` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `auxdesc` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `auxdesc_UNIQUE` (`auxdesc` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`TRIM`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`TRIM` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`TRIM` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `level` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `displayname_UNIQUE` (`level` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`BODYTYPE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`BODYTYPE` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`BODYTYPE` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `btype` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `description_UNIQUE` (`btype` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`INSIGNIA`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`INSIGNIA` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`INSIGNIA` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `emblem` BLOB(128) NULL,
  `sdesc` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`COLOURS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`COLOURS` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`COLOURS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `colour` VARCHAR(25) NOT NULL,
  `ccode` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `colour_UNIQUE` (`colour` ASC),
  UNIQUE INDEX `ccode_UNIQUE` (`ccode` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`FUELS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`FUELS` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`FUELS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `fuel` VARCHAR(15) NOT NULL,
  `fdesc` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `fuel_UNIQUE` (`fuel` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`MSR`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`MSR` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`MSR` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `msr` VARCHAR(5) NOT NULL,
  `mdesc` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `msr_UNIQUE` (`msr` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`MODELS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`MODELS` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`MODELS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) NOT NULL,
  `REF` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `NAME_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`DRIVETYPE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`DRIVETYPE` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`DRIVETYPE` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `dtype` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `tname_UNIQUE` (`dtype` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `autoDB`.`ECONFIG`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `autoDB`.`ECONFIG` ;

CREATE TABLE IF NOT EXISTS `autoDB`.`ECONFIG` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `config` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `config_UNIQUE` (`config` ASC))
ENGINE = InnoDB;

