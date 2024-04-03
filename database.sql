CREATE database klinik_pratama;

USE klinik_pratama;

CREATE TABLE users
(
    name             VARCHAR(100) NOT NULL,
    email            VARCHAR(100) NOT NULL,
    password         VARCHAR(100) NOT NULL,
    role             INT          NOT NULL NOT NULL,
    no_hp            VARCHAR(100),
    gender           ENUM ('laki-laki', 'perempuan'),
    birthplace       VARCHAR(100),
    birth_date       DATE,
    category         ENUM ('M', 'K'),
    instansi         VARCHAR(115),
    no_rm            VARCHAR(255),
    token            VARCHAR(100),
    token_expired_at BIGINT,
    PRIMARY KEY (email),
    UNIQUE (no_rm, token)
);

SELECT *
FROM users;

DESC users;

CREATE TABLE rekam_medis
(
    id_obat    INT AUTO_INCREMENT NOT NULL,
    no_rm      VARCHAR(255)       NOT NULL,
    anamnesa   VARCHAR(100)       NOT NULL,
    diagnosis  VARCHAR(100)       NOT NULL,
    therapy    VARCHAR(100)       NOT NULL,
    total_obat INT                NOT NULL,
    createdAt  DATE,
    PRIMARY KEY (id_obat),
    FOREIGN KEY fk_users_rm (no_rm) REFERENCES users (no_rm)
);

ALTER TABLE rekam_medis
    ADD COLUMN pemeriksaan  VARCHAR(255) NOT NULL AFTER anamnesa,
    ADD COLUMN therapy_2    VARCHAR(100) AFTER therapy,
    ADD COLUMN therapy_3    VARCHAR(100) AFTER therapy_2,
    ADD COLUMN thetapy_4    VARCHAR(100) AFTER therapy_3,
    ADD COLUMN total_obat_2 INT AFTER total_obat,
    ADD COLUMN total_obat_3 INT AFTER total_obat_2,
    ADD COLUMN total_obat_4 INT AFTER total_obat_3,
    ADD COLUMN keterangan   TEXT AFTER total_obat_3;

ALTER TABLE rekam_medis
    RENAME COLUMN thetapy_4 to therapy_4;




DESC rekam_medis;

DROP TABLE rekam_medis;


CREATE TABLE obat
(
    id_obat    INT AUTO_INCREMENT NOT NULL,
    name_obat  VARCHAR(100)       NOT NULL,
    expired    DATE               NOT NULL,
    min_stock  INT                NOT NULL,
    stock      INT                NOT NULL,
    used_total INT                NOT NULL DEFAULT 0,
    PRIMARY KEY (id_obat),
    UNIQUE (name_obat)
);

select *
from obat;

alter table obat
    modify used_total INT DEFAULT 0;

desc obat;

CREATE TABLE list_updated_at
(
    id_updated INT AUTO_INCREMENT NOT NULL,
    name_obat  VARCHAR(100)       NOT NULL,
    updated_at DATE,
    PRIMARY KEY (id_updated),
    FOREIGN KEY fk_obat_update (name_obat) REFERENCES obat (name_obat)
);

desc list_updated_at;

DROP TABLE OBAT;

DELETE
FROM users;

DELETE
FROM rekam_medis;

DELETE
FROM obat;

DELETE
FROM list_updated_at;


SELECT *
FROM users;