-- MySQL Script generated by MySQL Workbench
-- Tue Sep 17 16:26:39 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`cfgUserRole`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`cfgUserRole` ;

CREATE TABLE IF NOT EXISTS `mydb`.`cfgUserRole` (
  `userRole` INT NOT NULL,
  `userRoleName` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`userRole`));


-- -----------------------------------------------------
-- Table `mydb`.`tblUser`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`tblUser` ;

CREATE TABLE IF NOT EXISTS `mydb`.`tblUser` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `creationTime` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `modifiedTime` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `lastChangedBy` INT NULL,
  `userName` VARCHAR(16) NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `userRole` INT NULL,
  `phoneNumber` VARCHAR(15) NULL,
  `isActive` INT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  INDEX `emp_idx` (`userRole` ASC) VISIBLE,
  CONSTRAINT `employeeRole`
    FOREIGN KEY (`userRole`)
    REFERENCES `mydb`.`cfgUserRole` (`userRole`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `mydb`.`tblUserEDD`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`tblUserEDD` ;

CREATE TABLE IF NOT EXISTS `mydb`.`tblUserEDD` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `creationTime` DATETIME NULL,
  `modifiedTime` DATETIME NULL,
  `lastChangedBy` INT NULL,
  `userId` INT NULL,
  `gestationalType` VARCHAR(50) NULL,
  `gestationalData` TEXT NULL,
  `edd` DATE NULL,
  `isActive` INT(1) NULL DEFAULT 1,
  `refUserEDDId` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `userId_idx` (`userId` ASC) VISIBLE,
  INDEX `refUserEddId_idx` (`refUserEDDId` ASC) VISIBLE,
  CONSTRAINT `userId`
    FOREIGN KEY (`userId`)
    REFERENCES `mydb`.`tblUser` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `refUserEddId`
    FOREIGN KEY (`refUserEDDId`)
    REFERENCES `mydb`.`tblUserEDD` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `mydb`.`tblUserDetails`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`tblUserDetails` ;

CREATE TABLE IF NOT EXISTS `mydb`.`tblUserDetails` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `creationTime` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `modifiedTime` DATETIME NULL,
  `lastChangedBy` INT NULL,
  `userId` INT NULL,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NULL,
  `gender` VARCHAR(1) NULL,
  `height` INT NULL,
  `bloodGroup` VARCHAR(3) NULL,
  `dateOfBirth` DATE NULL,
  PRIMARY KEY (`id`),
  INDEX `employeeId1_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `employeeId1`
    FOREIGN KEY (`userId`)
    REFERENCES `mydb`.`tblUser` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `mydb`.`tblDoctorPatientRelationship`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`tblDoctorPatientRelationship` ;

CREATE TABLE IF NOT EXISTS `mydb`.`tblDoctorPatientRelationship` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `creationTime` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `modifiedTime` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `lastChangedBy` INT NULL COMMENT 'if lastChangedBy other than -1\nthen lastChangedBy is employeeId',
  `doctorUserId` INT NOT NULL,
  `patientUserId` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `index3` (`doctorUserId` ASC, `patientUserId` ASC) INVISIBLE,
  INDEX `DoctorPatientRelationshipPatientUserId_idx` (`patientUserId` ASC) VISIBLE,
  CONSTRAINT `DoctorPatientRelationshipDoctorUserId`
    FOREIGN KEY (`doctorUserId`)
    REFERENCES `mydb`.`tblUser` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `DoctorPatientRelationshipPatientUserId`
    FOREIGN KEY (`patientUserId`)
    REFERENCES `mydb`.`tblUser` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `mydb`.`tblPatientVisit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`tblPatientVisit` ;

CREATE TABLE IF NOT EXISTS `mydb`.`tblPatientVisit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `creationTime` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `modifiedTime` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `lastChangedBy` INT NULL COMMENT 'if lastChangedBy other than -1\nthen lastChangedBy is employeeId',
  `doctorPatientRelationshipId` INT NOT NULL,
  `visitDate` DATE NULL,
  `reason` TEXT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `index3` (`doctorPatientRelationshipId` ASC) INVISIBLE,
  CONSTRAINT `DoctorPatientRelationshipUserId0`
    FOREIGN KEY (`doctorPatientRelationshipId`)
    REFERENCES `mydb`.`tblDoctorPatientRelationship` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


desc tblUser;
INSERT INTO cfgUserRole(userRole, userRoleName) VALUES
( 1, 'ADMIN'),
(2, 'DOCTOR'),
(3, 'PATIENT')
;
-- QUERY Related to Post request 
-- /register /users 
INSERT INTO tblUser(lastChangedBy, userName, email, password, userRole, phoneNumber)
VALUES(-1, '@userName', '@email', '@password', '@userRole', '@phoneNumber');

INSERT INTO tblUserDetails(userId, firstName, lastName)
VALUES('@userId', '@firstName', '@lastName');

-- response will be stored in java

-- /login to check username and password
SELECT id AS userId,
       userName AS userName,
       userRoleName
FROM tblUser
JOIN cfgUserRole USING(userRole)
WHERE (email = '@email'
       OR username = '@username')
  AND password = '@password'
  AND isActive=1;
        
-- /users create new user with role option
INSERT INTO tblUser(lastChangedBy, userName, email, password, userRole, phoneNumber)
VALUES('@userId', '@userName', '@email', '@password', '@userRole', '@phoneNumber');


-- /users/edd POST 
INSERT INTO tblUserEdd(lastChangedBy, userId, gestationType, gestationalData, edd)
VALUES('@lastChangedBy', '@userId', '@gestationType', '@gestationalData', '@edd');

UPDATE tblUserEdd
SET refUserEDDId='@userEddId'
WHERE id='@userEddId';

-- users PUT
UPDATE tbluserDetails
SET firstName = "@firstName" -- based on column came from user
WHERE userId="@userId";

-- users/edd PUT 

UPDATE tblUserEdd
SET refUserEDDId='@refUserEDDId',
    isActive=0
WHERE refUserEDDId='@refUserEDDId' ;

INSERT INTO tblUserEdd(lastChangedBy, userId, gestationalType, gestationalData, edd, refUserEDDId)
VALUES('@lastChangedBy', '@userId', '@gestationType', '@gestationalData', '@edd', '@refUserEDDId');



-- QUERY Related to GET Request 
-- if name if given 
SELECT tu.id AS userId,
       tu.userName,
       tu.email,
       tu.phoneNumber,
       tud.firstName,
       tud.lastName,
       tud.bloodGroup,
       tud.dateOfBirth,
       tud.height,
       tud.gender
FROM tblUser tu
JOIN tblUserDetails tud ON(tu.id=tud.userId)
WHERE concat(tud.firstName, ' ', tud.lastName) LIKE '%ver%'
  AND tu.id ='@userId'
  AND isActive=1;


-- if isPatient is true all edd data

SELECT id,
       refUserEDDId,
       gestationAgeCalculationType,
       calculationEDDData,
       edd,
       isActive
FROM tblUserEdd
WHERE userId='@userId';

-- list of doctors patient visited and date and cause of visit
SELECT tpv.id,
       tdpr.doctorUserId,
       tpv.reason
JOIN tblDoctorPatientRelationship tdpr ON (tu.id=tdpr.doctorUserId)
JOIN tblPatientVisit tpv ON (tpv.doctorPatientRelationshipId=tdpr.id)
WHERE tdpr.patientUserId = '@userId'
ORDER BY tpv.id;