package com.nhn.minidooray.accountapi.service;

/**
 * -- -----------------------------------------------------
 * -- Table `nhn_academy_15`.`ACCOUNT_ACCOUNT_STATE`
 * -- -----------------------------------------------------
 * CREATE TABLE IF NOT EXISTS `nhn_academy_15`.`ACCOUNT_ACCOUNT_STATE`
 * (
 *     `ACCOUNT_ID`         VARCHAR(40) NOT NULL,
 *     `ACCOUNT_STATE_CODE` CHAR(2)     NOT NULL,
 *     `CHANGE_AT`          DATETIME    NOT NULL DEFAULT NOW(),
 *     PRIMARY KEY (`ACCOUNT_ID`, `ACCOUNT_STATE_CODE`, `CHANGE_AT`),
 *     INDEX `fk_ACCOUNT_ACCOUNT_STATE_ACCOUNT_STATE1_idx` (`ACCOUNT_STATE_CODE` ASC) VISIBLE,
 *     CONSTRAINT `fk_ACCOUNT_ACCOUNT_STATE_ACCOUNT1`
 *         FOREIGN KEY (`ACCOUNT_ID`)
 *             REFERENCES `nhn_academy_15`.`ACCOUNT` (`ID`)
 *             ON DELETE NO ACTION
 *             ON UPDATE NO ACTION,
 *     CONSTRAINT `fk_ACCOUNT_ACCOUNT_STATE_ACCOUNT_STATE1`
 *         FOREIGN KEY (`ACCOUNT_STATE_CODE`)
 *             REFERENCES `nhn_academy_15`.`ACCOUNT_STATE` (`CODE`)
 *             ON DELETE NO ACTION
 *             ON UPDATE NO ACTION
 * )
 *     ENGINE =
 */
public interface AccountAccountStateService {
}
