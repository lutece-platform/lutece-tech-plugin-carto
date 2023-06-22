
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
fk_id_data_layer int default 0,
PRIMARY KEY (id_coordonnee)
);

--
-- Structure for table carto_map_template
--

DROP TABLE IF EXISTS carto_map_template;
CREATE TABLE carto_map_template (
id_modele_carte int AUTO_INCREMENT,
title varchar(255) default '' NOT NULL,
description long varchar,
map_background varchar(255) default '',
default_zoom int default '0',
zoom_min int default '0',
zoom_max int default '0',
center_map varchar(255) default '',
PRIMARY KEY (id_modele_carte)
);

--
-- Structure for table carto_data_layer
--

DROP TABLE IF EXISTS carto_data_layer;
CREATE TABLE carto_data_layer (
id_data_layer int AUTO_INCREMENT,
title varchar(255) default '' NOT NULL,
solr_tag varchar(255) default '',
geometry int default '0',
PRIMARY KEY (id_data_layer)
);

--
-- Structure for table carto_data_layer_map_template
--

DROP TABLE IF EXISTS carto_data_layer_map_template;
CREATE TABLE carto_data_layer_map_template (
id_data_layer_map_template int AUTO_INCREMENT,
id_map_template int default '0' NOT NULL,
id_data_layer int default '0' NOT NULL,
pictogram varchar(50) default '',
zoom_min int default '0',
zoom_max int default '0',
layer_type int default '0' NOT NULL,
color varchar(50) default '',
thickness int default '0',
PRIMARY KEY (id_data_layer_map_template)
);

--
-- Structure for table carto_geometry_type
--

DROP TABLE IF EXISTS carto_geometry_type;
CREATE TABLE carto_geometry_type (
id_geometry_type int AUTO_INCREMENT,
name varchar(255) default '' NOT NULL,
technical_name varchar(255) default '' NOT NULL,
PRIMARY KEY (id_geometry_type)
);

--
-- Structure for table carto_data_layer_type
--

DROP TABLE IF EXISTS carto_data_layer_type;
CREATE TABLE carto_data_layer_type (
id_data_layer_type int AUTO_INCREMENT,
title varchar(255) default '' NOT NULL,
editable SMALLINT,
searchable_by_others SMALLINT,
PRIMARY KEY (id_data_layer_type)
);

--
-- Structure for table carto_basemap
--

DROP TABLE IF EXISTS carto_basemap;
CREATE TABLE carto_basemap (
id_basemap int AUTO_INCREMENT,
title varchar(255) default '' NOT NULL,
url long varchar NOT NULL,
PRIMARY KEY (id_basemap)
);


