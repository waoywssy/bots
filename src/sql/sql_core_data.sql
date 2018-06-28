-- mysql -u root -p boryi_bot_central
USE pipeline;

-- INSERT INTO `servers_srv` (`srv_name`, `srv_ip`, `srv_enabled`) VALUES ('localhost', 'localhost', 1);

INSERT INTO `bots_bts` (`bts_id`, `bts_cmp_id`, `bts_sct_sector_1`, 
                    `bts_sct_sector_2`, `bts_sct_sector_3`, `bts_name`, 
                    `bts_version`, `bts_description`, `bts_enabled`, 
                    `bts_created`, `bts_last_updated`, `bts_path`, `bts_file`) 
            VALUES 
	(11002, 1, 'news', 'business', null, 'sohu', '1.1.0', 'boryi news.business sohu', 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '/com/deploy/boryi/bots/news/business/11002-sohu/', 'com.boryi.bots.news.business.sohu.jar');

INSERT INTO `jobs_jbs` (`jbs_bts_id`, `jbs_id`, `jbs_name`, `jbs_description`, `jbs_enabled`, `jbs_created`, `jbs_last_updated`, `jbs_path`, `jbs_file`) VALUES 
	(11002, 1, 'Job 1', 'boryi.news.business.sohu', 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '/com/deploy/boryi/bots/news/business/11002-sohu/config/', 'boryi.bot.news.business.sohu.conf');

INSERT INTO `tasks_tsk` (`tsk_bts_id`, `tsk_jbs_id`, `tsk_id`, `tsk_name`, `tsk_description`, `tsk_enabled`, `tsk_created`, `tsk_last_updated`, `tsk_minute`, `tsk_hour`, `tsk_dom`, `tsk_month`, `tsk_dow`, `tsk_srv_id`) VALUES 
	(11002, 1, 0, 'Manual run', 'Manually start boryi news.business sohu bot', 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), null, null, null, null, null, 1);
