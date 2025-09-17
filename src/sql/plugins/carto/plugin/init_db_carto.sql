-- liquibase formatted sql
-- changeset carto:init_db_carto.sql
-- preconditions onFail:MARK_RAN onError:WARN
insert into carto_geometry_type (name, technical_name) values ('Point','Point');
insert into carto_geometry_type (name, technical_name) values ('Polygone','Polygon');
insert into carto_geometry_type (name, technical_name) values ('Polyligne','Polyline');

insert into carto_data_layer_type(title, editable, searchable_by_others, inclusion, exclusion) values ('Consultation',0,1,0,0);
insert into carto_data_layer_type(title, editable, searchable_by_others, inclusion, exclusion) values ('Editable par les usagers et consultable par tous les usagers',1,1,0,0);
insert into carto_data_layer_type(title, editable, searchable_by_others, inclusion, exclusion) values ('Inclusion',0,1,1,0);
insert into carto_data_layer_type(title, editable, searchable_by_others, inclusion, exclusion) values ('Exclusion',0,1,0,1);
insert into carto_data_layer_type(title, editable, searchable_by_others, inclusion, exclusion) values ('Editable par les usagers et consultable uniquement par les utilisateurs du même role',1,0,0,0);

insert into carto_basemap(title, url) values ('OSM classique', 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png');