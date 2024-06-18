ALTER TABLE carto_basemap add column attribution varchar(255) default '';
ALTER TABLE carto_data_layer_map_template add column zoom_picto int default '0';
ALTER TABLE carto_data_layer_map_template add column icon_image long varchar default '';

ALTER TABLE carto_data_layer add column source varchar(255) default 'SOLR'; 	
ALTER TABLE carto_data_layer add column url_flux varchar(255) default '';	
ALTER TABLE carto_data_layer add column type_name_flux varchar(255) default ''; 	
ALTER TABLE carto_data_layer add column version_flux varchar(255) default ''; 	