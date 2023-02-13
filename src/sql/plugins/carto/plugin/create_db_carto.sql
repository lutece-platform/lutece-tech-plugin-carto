
--
-- Structure for table carto_coordonnee
--

DROP TABLE IF EXISTS carto_coordonnee;
CREATE TABLE carto_coordonnee (
id_coordonnee int AUTO_INCREMENT,
adresse varchar(255) default '' NOT NULL,
coordonnee_x float,
coordonnee_y float,
geojson mediumtext default '' NOT NULL,
PRIMARY KEY (id_coordonnee)
);
