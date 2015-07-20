-- facilita o drop de todas as tabelas
SELECT concat('DROP TABLE IF EXISTS ', table_name, ';')
FROM information_schema.tables
WHERE table_schema = 'bolao_campeao' AND TABLE_TYPE = 'TABLE';

SELECT concat('DROP VIEW IF EXISTS ', table_name, ';')
FROM information_schema.tables
WHERE table_schema = 'bolao_campeao' AND TABLE_TYPE = 'VIEW';

SET FOREIGN_KEY_CHECKS = 0;

SET FOREIGN_KEY_CHECKS = 1;