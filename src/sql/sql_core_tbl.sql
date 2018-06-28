USE pipeline;

CREATE TABLE IF NOT EXISTS `bot_runs_btrns` (
  `btrns_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id of the run in the bot database',
  `btrns_bts_id` BIGINT UNSIGNED NOT NULL COMMENT 'Id of the bot',
  `btrns_jbs_id` BIGINT UNSIGNED NOT NULL COMMENT 'Id of the job of the bot',
  `btrns_tsk_id` BIGINT UNSIGNED NOT NULL COMMENT 'Id of the task of the job of the bot. -1 means no task',
  `btrns_rns_id` BIGINT UNSIGNED NOT NULL COMMENT 'Id of the run of the task of the job of the bot.',
  `btrns_rns_start` DATETIME NOT NULL COMMENT 'Run date and time',
  PRIMARY KEY (`btrns_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Bot runs table' AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS `web_statistic_wbs` (
  `wbs_btrns_id` BIGINT UNSIGNED NOT NULL COMMENT 'Id of the run in the bot database',
  `wbs_is_resume` TINYINT(1) NOT NULL DEFAULT '0' COMMENT 'Whether the run is resumed',
  `wbs_start` DATETIME NOT NULL COMMENT 'Start time of web crawling',
  `wbs_end` DATETIME NOT NULL COMMENT 'End time of web crawling',
  `wbs_duration` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT 'Duration of web crawling in hours',
  `wbs_actual_duration` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT 'Actual web traffic time in hours',
  `wbs_page_hits_total` BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT 'Total page hits on the web site',
  `wbs_kb_downloaded_total` BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT 'Total kilobytes downloaded from the web site, including headers and contents',
  `wbs_kb_uploaded_total` BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT 'Total kilobytes uploaded to the web site, including headers and contents',
  `wbs_b_request_header_total` BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT 'Total bytes of request headers',
  `wbs_b_request_content_total` BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT 'Total bytes of request contents',
  `wbs_b_response_header_total` BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT 'Total bytes of response headers',
  `wbs_b_response_content_total` BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT 'Total bytes of response contents',
  `wbs_max_page_hits_per_hour` INT UNSIGNED NOT NULL DEFAULT '1' COMMENT 'Maximum page hits per hour allowed to the web site',
  `wbs_max_kb_per_hour` INT UNSIGNED NOT NULL DEFAULT '1' COMMENT 'Maximum kilobytes downloaded per hour allowed from the web site',
  `wbs_threads` INT UNSIGNED NOT NULL COMMENT 'Number of web crawling threads used',
  PRIMARY KEY (`wbs_btrns_id`, `wbs_start`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Web statistic table';

CREATE TABLE IF NOT EXISTS `qa_constrains_11002_qcns` (
  `qcns_id` INT UNSIGNED NOT NULL COMMENT 'id',
  `qcns_column` VARCHAR(256) NOT NULL COMMENT 'table.column name to identify the constrain and the first column to have constrain',
  `qcns_cnst_id` SMALLINT UNSIGNED NOT NULL COMMENT 'constrain type',
  `qcns_clm_2` VARCHAR(256) NULL COMMENT 'constrain on table.column 2',
  `qcns_clm_3` VARCHAR(256) NULL COMMENT 'constrain on table.column 3',
  `qcns_clm_4` VARCHAR(256) NULL COMMENT 'constrain on table.column 4',
  `qcns_clm_5` VARCHAR(256) NULL COMMENT 'constrain on table.column 5',
  `qcns_cnsmt_id` SMALLINT UNSIGNED NULL COMMENT 'constrain math type',
  `qcns_value` DECIMAL(32,8) NULL COMMENT 'constrain value for the math type (only for single-constrain)',
  `qcns_id_c` INT UNSIGNED NULL COMMENT 'condition-constrain - constrain id of the condition',
  `qcns_id_r` INT UNSIGNED NULL COMMENT 'condition-constrain - constrain id of the result',
  PRIMARY KEY (`qcns_id`),
  INDEX (`qcns_column`, `qcns_cnst_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='QA constrains table';

CREATE TABLE IF NOT EXISTS `qa_comparators_11002_qcmp` (
  `qcmp_qcns_id` INT UNSIGNED NOT NULL COMMENT 'constrain id',
  `qcmp_cmpvt_id` SMALLINT UNSIGNED NOT NULL COMMENT 'comparator value type id',
  `qcmp_cmpmt_id` SMALLINT UNSIGNED NOT NULL COMMENT 'comparator math type id',
  `qcmp_cmpcn_id` INT NULL COMMENT 'constrain id to be compared',
  `qcmp_cmpcnvt_id` SMALLINT UNSIGNED NULL COMMENT 'constrains''s comparator value type id to be compared',
  `qcmp_cmprp_id` TINYINT NOT NULL DEFAULT -1 COMMENT 'comparator report type id',
  `qcmp_value` DECIMAL(32,8) NOT NULL COMMENT 'comparator value',
  PRIMARY KEY (`qcmp_qcns_id`, `qcmp_cmpvt_id`, `qcmp_cmpmt_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='QA comparators table';
