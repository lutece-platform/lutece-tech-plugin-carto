ALTER TABLE carto_basemap add column attribution varchar(255) default '';
ALTER TABLE carto_data_layer_map_template add column zoom_picto int default '0';
ALTER TABLE carto_data_layer_map_template add column icon_image long varchar default '';