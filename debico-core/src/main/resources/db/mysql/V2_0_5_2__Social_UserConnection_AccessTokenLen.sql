-- O token do facebook eh grande demais! O.O
ALTER TABLE userconnection
CHANGE COLUMN `accessToken` `accessToken` VARCHAR(1024) NOT NULL ;