USE pipeline;
DELIMITER //
DROP PROCEDURE IF EXISTS `save_pagesp`//
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `save_pagesp`(pstid INT(10) UNSIGNED,purl VARCHAR(512),ptitle VARCHAR(64),pabstract VARCHAR(256),pcontent TEXT,pcontentmd5 VARCHAR(32),pfirstfound DATETIME,plastfound DATETIME,md5_0 VARCHAR(32),found DATETIME)
BEGIN
  DECLARE pid_ BIGINT(20) UNSIGNED;
  DECLARE md5_0_ VARCHAR(32);
  DECLARE pcontentmd5_ VARCHAR(32);
  SELECT p_id,p_md5_0,p_content_md5 INTO pid_,md5_0_,pcontentmd5_ FROM pages_p WHERE p_id=pid;
  IF pid_ IS NULL THEN
    INSERT INTO `pages_p` (`p_st_id`,`p_url`,`p_title`,`p_abstract`,`p_content`,`p_content_md5`,`p_first_found`,`p_last_found`,`p_md5_0`,`p_first_found`,`p_last_found`) VALUES (pstid,purl,ptitle,pabstract,pcontent,pcontentmd5,pfirstfound,plastfound,md5_0,found,found);
  ELSEIF md5_0_<>md5_0 THEN
    IF md5_0_<>md5_0 THEN
      IF pcontentmd5 IS NULL AND pcontentmd5_ IS NOT NULL THEN
        SET pcontentmd5=pcontentmd5_;
      END IF;
      UPDATE pages_p SET p_content_md5=pcontentmd5,p_md5_0=md5_0,p_processed=0,p_last_found=found WHERE p_id=pid_;
    END IF;
  ELSE
    UPDATE pages_p SET p_processed=0,p_last_found=found WHERE p_id=pid_;
  END IF;
END;//

