-- liquibase formatted sql
-- changeset carto:init_core_carto.sql
-- preconditions onFail:MARK_RAN onError:WARN

--
-- Data for table core_admin_right
--
DELETE FROM core_admin_right WHERE id_right = 'CARTO_MANAGEMENT';
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url, id_order ) VALUES 
('CARTO_MANAGEMENT','carto.adminFeature.ManageCarto.name',1,'jsp/admin/plugins/carto/ManageMapTemplates.jsp','carto.adminFeature.ManageCarto.description',0,'carto',NULL,NULL,NULL,4);


--
-- Data for table core_user_right
--
DELETE FROM core_user_right WHERE id_right = 'CARTO_MANAGEMENT';
INSERT INTO core_user_right (id_right,id_user) VALUES ('CARTO_MANAGEMENT',1);

--
-- Data for table core_admin_right
--
DELETE FROM core_admin_right WHERE id_right = 'CARTO_MANAGEMENT_REFERENTIEL';
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url, id_order ) VALUES 
('CARTO_MANAGEMENT_REFERENTIEL','carto.adminFeature.ManageReferentielCartographie.name',1,'jsp/admin/plugins/carto/ManageDataLayerTypes.jsp','carto.adminFeature.ManageReferentielCartographie.description',0,'carto',NULL,NULL,NULL,4);


--
-- Data for table core_user_right
--
DELETE FROM core_user_right WHERE id_right = 'CARTO_MANAGEMENT_REFERENTIEL';
INSERT INTO core_user_right (id_right,id_user) VALUES ('CARTO_MANAGEMENT_REFERENTIEL',1);
