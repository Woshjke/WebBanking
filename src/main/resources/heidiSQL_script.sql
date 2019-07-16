-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               PostgreSQL 11.2, compiled by Visual C++ build 1914, 64-bit
-- Операционная система:
-- HeidiSQL Версия:              10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES  */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Дамп структуры для таблица mydatabase.bank_account
CREATE TABLE IF NOT EXISTS "bank_account"
(
    "id"              BIGINT           NOT NULL DEFAULT nextval('mydatabase.bank_account_id_seq'::regclass) COMMENT E'',
    "money"           DOUBLE PRECISION NOT NULL COMMENT E'',
    "user_id"         BIGINT           NULL     DEFAULT NULL COMMENT E'',
    "organisation_id" BIGINT           NULL     DEFAULT NULL COMMENT E'',
    PRIMARY KEY ("id"),
    UNIQUE KEY ("id")
);

-- Дамп данных таблицы mydatabase.bank_account: 4 rows
/*!40000 ALTER TABLE "bank_account" DISABLE KEYS */;
INSERT INTO "bank_account" ("id", "money", "user_id", "organisation_id")
VALUES (4, 77121, 2, NULL),
       (3, 200, 1, NULL),
       (5, 222679, 3, 1),
       (21, 100000, 42, NULL);
/*!40000 ALTER TABLE "bank_account" ENABLE KEYS */;

-- Дамп структуры для таблица mydatabase.organisations
CREATE TABLE IF NOT EXISTS "organisations"
(
    "id"   BIGINT                NOT NULL DEFAULT nextval('mydatabase.organisations_id_seq'::regclass) COMMENT E'',
    "name" CHARACTER VARYING(40) NOT NULL COMMENT E'',
    PRIMARY KEY ("id"),
    UNIQUE KEY ("name")
);

-- Дамп данных таблицы mydatabase.organisations: 0 rows
/*!40000 ALTER TABLE "organisations" DISABLE KEYS */;
INSERT INTO "organisations" ("id", "name")
VALUES (1, E'Apple');
/*!40000 ALTER TABLE "organisations" ENABLE KEYS */;

-- Дамп структуры для таблица mydatabase.roles
CREATE TABLE IF NOT EXISTS "roles"
(
    "id"   BIGINT                NOT NULL DEFAULT nextval('mydatabase.roles_id_seq'::regclass) COMMENT E'',
    "name" CHARACTER VARYING(15) NOT NULL COMMENT E'',
    UNIQUE KEY ("id"),
    UNIQUE KEY ("name"),
    PRIMARY KEY ("id")
);

-- Дамп данных таблицы mydatabase.roles: 0 rows
/*!40000 ALTER TABLE "roles" DISABLE KEYS */;
INSERT INTO "roles" ("id", "name")
VALUES (1, E'ROLE_USER'),
       (2, E'ROLE_ADMIN');
/*!40000 ALTER TABLE "roles" ENABLE KEYS */;

-- Дамп структуры для таблица mydatabase.transaction
CREATE TABLE IF NOT EXISTS "transaction"
(
    "id"          BIGINT  NOT NULL DEFAULT nextval('mydatabase.transaction_id_seq'::regclass) COMMENT E'',
    "source"      BIGINT  NOT NULL COMMENT E'',
    "destination" BIGINT  NOT NULL COMMENT E'',
    "val"         INTEGER NOT NULL COMMENT E'',
    UNIQUE KEY ("id"),
    PRIMARY KEY ("id")
);

-- Дамп данных таблицы mydatabase.transaction: 0 rows
/*!40000 ALTER TABLE "transaction" DISABLE KEYS */;
INSERT INTO "transaction" ("id", "source", "destination", "val")
VALUES (2, 4, 3, 400),
       (3, 4, 5, 13),
       (4, 4, 5, 222),
       (5, 4, 5, 22222),
       (6, 4, 5, 22),
       (7, 3, 5, 100000),
       (8, 3, 4, 400),
       (9, 3, 5, 100),
       (10, 3, 5, 100);
/*!40000 ALTER TABLE "transaction" ENABLE KEYS */;

-- Дамп структуры для таблица mydatabase.usr
CREATE TABLE IF NOT EXISTS "usr"
(
    "id"       BIGINT                 NOT NULL DEFAULT nextval('mydatabase.user_id_seq'::regclass) COMMENT E'',
    "username" CHARACTER VARYING(15)  NOT NULL COMMENT E'',
    "pass"     CHARACTER VARYING(100) NOT NULL COMMENT E'',
    UNIQUE KEY ("id"),
    UNIQUE KEY ("username"),
    PRIMARY KEY ("id")
);

-- Дамп данных таблицы mydatabase.usr: 4 rows
/*!40000 ALTER TABLE "usr" DISABLE KEYS */;
INSERT INTO "usr" ("id", "username", "pass")
VALUES (2, E'user', E'$2a$11$ow2sj0B3c506jyUXMaaoku2Ie2PBSmEIKnMxGg14qsY5ZByI8Fs2S'),
       (42, E'user1', E'$2a$11$.rKH6L6OTtX/Orv/CyZA1.u7oSgbqCLUCFBCOqfu3vLGtfNbjswb6'),
       (1, E'admin', E'$2a$11$78oD6dK9CfB.5OX7Nzajpexpbz04K/N2TbK2vm.IwELlpd1.fATk2'),
       (3, E'apple', E'$2a$11$tJRoIbBcMHuqs/tSOXSzSuJjlXXlsIevsV7oJGMzrdqOX9/lPgRne');
/*!40000 ALTER TABLE "usr" ENABLE KEYS */;

-- Дамп структуры для таблица mydatabase.usr_role
CREATE TABLE IF NOT EXISTS "usr_role"
(
    "id"      INTEGER NOT NULL DEFAULT nextval('mydatabase.usr_role_id_seq'::regclass) COMMENT E'',
    "user_id" BIGINT  NULL     DEFAULT NULL COMMENT E'',
    "role_id" BIGINT  NULL     DEFAULT NULL COMMENT E'',
    UNIQUE KEY ("id"),
    PRIMARY KEY ("id")
);

-- Дамп данных таблицы mydatabase.usr_role: 3 rows
/*!40000 ALTER TABLE "usr_role" DISABLE KEYS */;
INSERT INTO "usr_role" ("id", "user_id", "role_id")
VALUES (1, 1, 2),
       (3, 3, 1),
       (2, 2, 1),
       (28, 42, 1);
/*!40000 ALTER TABLE "usr_role" ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
