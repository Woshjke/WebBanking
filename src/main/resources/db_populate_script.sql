-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               PostgreSQL 11.2, compiled by Visual C++ build 1914, 64-bit
-- Операционная система:         
-- HeidiSQL Версия:              10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES  */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Дамп данных таблицы mydatabase.organisations: 0 rows
/*!40000 ALTER TABLE "organisations" DISABLE KEYS */;
INSERT INTO "organisations" ("id", "name", "user_id") VALUES
	(1, E'Apple', 3);
/*!40000 ALTER TABLE "organisations" ENABLE KEYS */;

-- Дамп данных таблицы mydatabase.transaction: 0 rows
/*!40000 ALTER TABLE "transaction" DISABLE KEYS */;
INSERT INTO "transaction" ("id", "from_user", "to_user", "money_count") VALUES
	(1, E'12345678', E'12345678', E'300');
/*!40000 ALTER TABLE "transaction" ENABLE KEYS */;

-- Дамп данных таблицы mydatabase.usr: 2 rows
/*!40000 ALTER TABLE "usr" DISABLE KEYS */;
INSERT INTO "usr" ("id", "username", "pass", "is_admin", "money_count") VALUES
	(1, E'admin', E'admin', E'true', 0),
	(2, E'user', E'user', E'false', 0),
	(3, E'apple ', E'the_best', E'false', 100350);
/*!40000 ALTER TABLE "usr" ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
