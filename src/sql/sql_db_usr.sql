-- command line: 
-- mysqladmin -u root -p create people

-- mysql -u root -p
CREATE DATABASE IF NOT EXISTS people CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE USER 'botmaster_local'@'localhost' IDENTIFIED BY 'p@ssw0rd';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE, CREATE, DROP ON people.* TO 'botmaster_local'@'localhost' IDENTIFIED BY 'p@ssw0rd';
GRANT SELECT, INSERT, UPDATE ON `mysql`.`proc` TO 'botmaster_local'@'localhost';