USE pipeline;
TRUNCATE TABLE qa_comparators_11002_qcmp;
TRUNCATE TABLE qa_constrains_11002_qcns;
INSERT INTO `qa_constrains_11002_qcns` (`qcns_id`,`qcns_column`,`qcns_cnst_id`,`qcns_clm_2`,`qcns_clm_3`,`qcns_clm_4`,`qcns_clm_5`,`qcns_cnsmt_id`,`qcns_value`,`qcns_id_c`,`qcns_id_r`) VALUES (1,'pagesp.p_id',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO qa_comparators_11002_qcmp (`qcmp_qcns_id`,`qcmp_cmpvt_id`,`qcmp_cmpmt_id`,`qcmp_cmpcn_id`,`qcmp_cmpcnvt_id`,`qcmp_cmprp_id`,`qcmp_value`) VALUES (1,0,7,-1,0,-1,0.1);
DROP TABLE IF EXISTS `qa_statistic_11002_1_qst`;
CREATE TABLE IF NOT EXISTS `qa_statistic_11002_1_qst` (
  `qst_btrns_id` BIGINT UNSIGNED NOT NULL COMMENT 'Id of the bot run',
  `qst_is_valid` TINYINT(1) DEFAULT NULL COMMENT 'Whether the data from the run is valid',
  `qst_001_00_07` DECIMAL(32,8) DEFAULT NULL COMMENT 'constrainId_comparatorValueTypeId_comparatorMathTypeId',
  `qst_001_00_07_report` TINYINT(1) DEFAULT NULL COMMENT '1: OK, 0: warning, -1: error, NULL: unknown',
  PRIMARY KEY (`qst_btrns_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Bot qa statistic table';
