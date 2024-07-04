ALTER TABLE carto_basemap add column attribution varchar(255) default '';
ALTER TABLE carto_data_layer_map_template add column zoom_picto int default '0';
ALTER TABLE carto_data_layer_map_template add column icon_image long varchar default '';

ALTER TABLE carto_data_layer add column source varchar(255) default 'SOLR'; 	
ALTER TABLE carto_data_layer add column url_flux varchar(255) default '';	
ALTER TABLE carto_data_layer add column type_name_flux varchar(255) default ''; 	
ALTER TABLE carto_data_layer add column version_flux varchar(255) default ''; 	

ALTER TABLE carto_data_layer_map_template add column picto_size_zoom_0_7 int(11) default 50;
ALTER TABLE carto_data_layer_map_template add column picto_size_zoom_8_12 int(11) default 50;
ALTER TABLE carto_data_layer_map_template add column picto_size_zoom_13_15 int(11) default 50;
ALTER TABLE carto_data_layer_map_template add column picto_size_zoom_16_19 int(11) default 50;

ALTER TABLE carto_data_layer_map_template add column cluster_marker smallint(6) default 1;