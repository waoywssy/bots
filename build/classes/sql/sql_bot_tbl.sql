USE pipeline;
CREATE TABLE IF NOT EXISTS `pages_p` (
  `p_id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '',
  `p_st_id` INT(10) UNSIGNED NOT NULL COMMENT '',
  `p_url` VARCHAR(512) NOT NULL COMMENT '',
  `p_title` VARCHAR(64) NOT NULL COMMENT '',
  `p_abstract` VARCHAR(256) COMMENT '',
  `p_content` TEXT COMMENT '',
  `p_content_md5` VARCHAR(32) COMMENT '',
  `p_first_found` DATETIME NOT NULL COMMENT '',
  `p_last_found` DATETIME NOT NULL COMMENT '',
  `p_md5_0` VARCHAR(32) NULL COMMENT 'MD5 hash code for information fields',
  `p_changed` TINYINT(1) DEFAULT 1 COMMENT 'Whether there is an information change, i.e. md5 changed',
  `p_processed` TINYINT(1) DEFAULT 0 COMMENT 'Whether the data has been processed since last changes/found/run',
  `p_first_found` DATETIME NOT NULL COMMENT 'First found date',
  `p_last_found` DATETIME NOT NULL COMMENT 'Last found date',
  PRIMARY KEY (`p_id`),
  UNIQUE INDEX (`p_url`),
  INDEX (`p_st_id`),
  INDEX (`p_processed`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci COMMENT='pagesp table' AUTO_INCREMENT=1;

