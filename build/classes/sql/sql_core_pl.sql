USE pipeline;

DELIMITER //
DROP PROCEDURE IF EXISTS `get_resumed_run`//
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `get_resumed_run`(
bid BIGINT UNSIGNED, jid BIGINT UNSIGNED, tid BIGINT UNSIGNED)
BEGIN
   SELECT MAX(btrns_id) INTO @brid FROM bot_runs_btrns WHERE btrns_bts_id = bid AND btrns_jbs_id = jid AND btrns_tsk_id = tid;
   SELECT @brid AS brid, btrns_rns_id AS rid, btrns_rns_start AS rstart FROM bot_runs_btrns WHERE btrns_id = @brid;
END;//

DELIMITER //
DROP PROCEDURE IF EXISTS `insert_bot_run`//
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `insert_bot_run`(
bid BIGINT UNSIGNED, jid BIGINT UNSIGNED, tid BIGINT UNSIGNED, rid BIGINT UNSIGNED, 
rstart DATETIME)
BEGIN
  IF NOT EXISTS (SELECT * FROM bot_runs_btrns WHERE btrns_bts_id = bid 
      AND btrns_jbs_id = jid AND btrns_tsk_id = tid AND btrns_rns_id = rid) THEN
    INSERT `bot_runs_btrns` (`btrns_bts_id`, `btrns_jbs_id`, `btrns_tsk_id`, 
        `btrns_rns_id`, `btrns_rns_start`) VALUES (bid, jid, tid, rid, rstart);
  END IF;
  SELECT btrns_id AS id FROM bot_runs_btrns WHERE btrns_bts_id = bid 
      AND btrns_jbs_id = jid AND btrns_tsk_id = tid AND btrns_rns_id = rid;
END;//

DELIMITER //
DROP PROCEDURE IF EXISTS `insert_web_statistic`//
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `insert_web_statistic`(
brid BIGINT UNSIGNED, resume TINYINT(1), wstart DATETIME, wend DATETIME, 
duration DECIMAL(10,2), actual_duration DECIMAL(10,2), 
page_hits_total BIGINT UNSIGNED, kb_downloaded_total BIGINT UNSIGNED, 
kb_uploaded_total BIGINT UNSIGNED, b_request_header_total BIGINT UNSIGNED, 
b_request_content_total BIGINT UNSIGNED, b_response_header_total BIGINT UNSIGNED, 
b_response_content_total BIGINT UNSIGNED, max_page_hits_per_hour INT UNSIGNED, 
max_kb_per_hour INT UNSIGNED, threads INT UNSIGNED)
BEGIN
  IF NOT EXISTS (SELECT * FROM web_statistic_wbs WHERE wbs_btrns_id = brid 
      AND wbs_start = wstart) THEN
    INSERT `web_statistic_wbs` (`wbs_btrns_id`, `wbs_is_resume`, `wbs_start`, 
        `wbs_end`, `wbs_duration`, `wbs_actual_duration`, `wbs_page_hits_total`, 
        `wbs_kb_downloaded_total`, `wbs_kb_uploaded_total`, 
        `wbs_b_request_header_total`, `wbs_b_request_content_total`, 
        `wbs_b_response_header_total`, `wbs_b_response_content_total`, 
        `wbs_max_page_hits_per_hour`, `wbs_max_kb_per_hour`, `wbs_threads`) 
      VALUES (brid, resume, wstart, wend, duration, actual_duration, 
        page_hits_total, kb_downloaded_total, kb_uploaded_total, 
        b_request_header_total, b_request_content_total, 
        b_response_header_total, b_response_content_total, 
        max_page_hits_per_hour, max_kb_per_hour, threads);
  END IF;
END;//
