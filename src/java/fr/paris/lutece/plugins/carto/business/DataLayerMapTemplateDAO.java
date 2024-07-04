/*
 * Copyright (c) 2002-2023, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */

package fr.paris.lutece.plugins.carto.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class provides Data Access methods for DataLayerMapTemplate objects
 */
public final class DataLayerMapTemplateDAO implements IDataLayerMapTemplateDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_data_layer_map_template, id_map_template, id_data_layer, pictogram, zoom_min, zoom_max, layer_type, color, thickness, id_coordinate, zoom_picto, icon_image, picto_size_zoom_0_7, picto_size_zoom_8_12, picto_size_zoom_13_15, picto_size_zoom_16_19, cluster_marker FROM carto_data_layer_map_template WHERE id_data_layer_map_template = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO carto_data_layer_map_template ( id_map_template, id_data_layer, pictogram, zoom_min, zoom_max, layer_type, color, thickness, id_coordinate, zoom_picto, icon_image, picto_size_zoom_0_7, picto_size_zoom_8_12, picto_size_zoom_13_15, picto_size_zoom_16_19, cluster_marker ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM carto_data_layer_map_template WHERE id_data_layer_map_template = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE carto_data_layer_map_template SET id_map_template = ?, id_data_layer = ?, pictogram = ?, zoom_min = ?, zoom_max = ?, layer_type = ?, color = ?, thickness = ?, id_coordinate = ?, zoom_picto = ?, icon_image = ?, picto_size_zoom_0_7 = ?, picto_size_zoom_8_12 = ?, picto_size_zoom_13_15 = ?, picto_size_zoom_16_19 = ?, cluster_marker = ? WHERE id_data_layer_map_template = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_data_layer_map_template, id_map_template, id_data_layer, pictogram, zoom_min, zoom_max, layer_type, color, thickness, id_coordinate, zoom_picto, icon_image, picto_size_zoom_0_7, picto_size_zoom_8_12, picto_size_zoom_13_15, picto_size_zoom_16_19, cluster_marker FROM carto_data_layer_map_template";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_data_layer_map_template FROM carto_data_layer_map_template";
    private static final String SQL_QUERY_SELECTALL_BY_IDS = "SELECT id_data_layer_map_template, id_map_template, id_data_layer, pictogram, zoom_min, zoom_max, layer_type, color, thickness, id_coordinate, zoom_picto, icon_image, picto_size_zoom_0_7, picto_size_zoom_8_12, picto_size_zoom_13_15, picto_size_zoom_16_19, cluster_marker FROM carto_data_layer_map_template WHERE id_data_layer_map_template IN (  ";
    private static final String SQL_QUERY_SELECT_DATA_LAYER_BY_MAP_TEMPLATE_ID = "SELECT id_data_layer FROM carto_data_layer_map_template WHERE id_map_template = ?";
    private static final String SQL_QUERY_SELECT_BY_ID_MAP_ID_LAYER = "SELECT id_data_layer_map_template, id_map_template, id_data_layer, pictogram, zoom_min, zoom_max, layer_type, color, thickness, id_coordinate, zoom_picto, icon_image, picto_size_zoom_0_7, picto_size_zoom_8_12, picto_size_zoom_13_15, picto_size_zoom_16_19, cluster_marker FROM carto_data_layer_map_template WHERE id_map_template = ? AND id_data_layer = ?";
    private static final String SQL_QUERY_SELECT_BY_DATA_LAYER_ID = "SELECT id_data_layer_map_template, id_map_template, id_data_layer, pictogram, zoom_min, zoom_max, layer_type, color, thickness, id_coordinate, zoom_picto, icon_image, picto_size_zoom_0_7, picto_size_zoom_8_12, picto_size_zoom_13_15, picto_size_zoom_16_19, cluster_marker FROM carto_data_layer_map_template WHERE id_data_layer = ?";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( DataLayerMapTemplate dataLayerMapTemplate, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getIdMapTemplate( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getIdDataLayer( ) );
            daoUtil.setString( nIndex++, dataLayerMapTemplate.getPictogram( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getZoomMin( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getZoomMax( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getLayerType( ) );
            daoUtil.setString( nIndex++, dataLayerMapTemplate.getColor( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getThickness( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getIdCoordinate( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getZoomPicto() );
            daoUtil.setString( nIndex++, dataLayerMapTemplate.getIconImage( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getPictoSizeZoom07( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getPictoSizeZoom812( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getPictoSizeZoom1315( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getPictoSizeZoom1619( ) );
            daoUtil.setBoolean( nIndex++, dataLayerMapTemplate.isMarkerCluster( ) );
            

            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                dataLayerMapTemplate.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<DataLayerMapTemplate> load( int nKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
            daoUtil.setInt( 1, nKey );
            daoUtil.executeQuery( );
            DataLayerMapTemplate dataLayerMapTemplate = null;

            if ( daoUtil.next( ) )
            {
                dataLayerMapTemplate = new DataLayerMapTemplate( );
                int nIndex = 1;

                dataLayerMapTemplate.setId( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setIdMapTemplate( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setIdDataLayer( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setPictogram( daoUtil.getString( nIndex++ ) );
                dataLayerMapTemplate.setZoomMin( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setZoomMax( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setLayerType( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setColor( daoUtil.getString( nIndex++ ) );
                dataLayerMapTemplate.setThickness( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setIdCoordinate( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setZoomPicto( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setIconImage( daoUtil.getString( nIndex++ ) );
                dataLayerMapTemplate.setPictoSizeZoom07( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setPictoSizeZoom812( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setPictoSizeZoom1315( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setPictoSizeZoom1619( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setMarkerCluster( daoUtil.getBoolean( nIndex++ ) );

            }

            return Optional.ofNullable( dataLayerMapTemplate );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin ) )
        {
            daoUtil.setInt( 1, nKey );
            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( DataLayerMapTemplate dataLayerMapTemplate, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
            int nIndex = 1;

            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getIdMapTemplate( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getIdDataLayer( ) );
            daoUtil.setString( nIndex++, dataLayerMapTemplate.getPictogram( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getZoomMin( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getZoomMax( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getLayerType( ) );
            daoUtil.setString( nIndex++, dataLayerMapTemplate.getColor( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getThickness( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getIdCoordinate( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getZoomPicto() );
            daoUtil.setString( nIndex++, dataLayerMapTemplate.getIconImage( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getPictoSizeZoom07( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getPictoSizeZoom812( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getPictoSizeZoom1315( ) );
            daoUtil.setInt( nIndex++, dataLayerMapTemplate.getPictoSizeZoom1619( ) );
            daoUtil.setBoolean( nIndex++, dataLayerMapTemplate.isMarkerCluster( ) );
            daoUtil.setInt( nIndex, dataLayerMapTemplate.getId( ) );

            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<DataLayerMapTemplate> selectDataLayerMapTemplatesList( Plugin plugin )
    {
        List<DataLayerMapTemplate> dataLayerMapTemplateList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                DataLayerMapTemplate dataLayerMapTemplate = new DataLayerMapTemplate( );
                int nIndex = 1;

                dataLayerMapTemplate.setId( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setIdMapTemplate( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setIdDataLayer( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setPictogram( daoUtil.getString( nIndex++ ) );
                dataLayerMapTemplate.setZoomMin( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setZoomMax( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setLayerType( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setColor( daoUtil.getString( nIndex++ ) );
                dataLayerMapTemplate.setThickness( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setIdCoordinate( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setZoomPicto( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setIconImage( daoUtil.getString( nIndex++ ) );
                dataLayerMapTemplate.setPictoSizeZoom07( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setPictoSizeZoom812( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setPictoSizeZoom1315( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setPictoSizeZoom1619( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setMarkerCluster( daoUtil.getBoolean( nIndex++ ) );

                dataLayerMapTemplateList.add( dataLayerMapTemplate );
            }

            return dataLayerMapTemplateList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdDataLayerMapTemplatesList( Plugin plugin )
    {
        List<Integer> dataLayerMapTemplateList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                dataLayerMapTemplateList.add( daoUtil.getInt( 1 ) );
            }

            return dataLayerMapTemplateList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectDataLayerMapTemplatesReferenceList( Plugin plugin )
    {
        ReferenceList dataLayerMapTemplateList = new ReferenceList( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                dataLayerMapTemplateList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
            }

            return dataLayerMapTemplateList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<DataLayerMapTemplate> selectDataLayerMapTemplatesListByIds( Plugin plugin, List<Integer> listIds )
    {
        List<DataLayerMapTemplate> dataLayerMapTemplateList = new ArrayList<>( );

        StringBuilder builder = new StringBuilder( );

        if ( !listIds.isEmpty( ) )
        {
            for ( int i = 0; i < listIds.size( ); i++ )
            {
                builder.append( "?," );
            }

            String placeHolders = builder.deleteCharAt( builder.length( ) - 1 ).toString( );
            String stmt = SQL_QUERY_SELECTALL_BY_IDS + placeHolders + ")";

            try ( DAOUtil daoUtil = new DAOUtil( stmt, plugin ) )
            {
                int index = 1;
                for ( Integer n : listIds )
                {
                    daoUtil.setInt( index++, n );
                }

                daoUtil.executeQuery( );
                while ( daoUtil.next( ) )
                {
                    DataLayerMapTemplate dataLayerMapTemplate = new DataLayerMapTemplate( );
                    int nIndex = 1;

                    dataLayerMapTemplate.setId( daoUtil.getInt( nIndex++ ) );
                    dataLayerMapTemplate.setIdMapTemplate( daoUtil.getInt( nIndex++ ) );
                    dataLayerMapTemplate.setIdDataLayer( daoUtil.getInt( nIndex++ ) );
                    dataLayerMapTemplate.setPictogram( daoUtil.getString( nIndex++ ) );
                    dataLayerMapTemplate.setZoomMin( daoUtil.getInt( nIndex++ ) );
                    dataLayerMapTemplate.setZoomMax( daoUtil.getInt( nIndex++ ) );
                    dataLayerMapTemplate.setLayerType( daoUtil.getInt( nIndex++ ) );
                    dataLayerMapTemplate.setColor( daoUtil.getString( nIndex++ ) );
                    dataLayerMapTemplate.setThickness( daoUtil.getInt( nIndex++ ) );
                    dataLayerMapTemplate.setIdCoordinate( daoUtil.getInt( nIndex++ ) );
                    dataLayerMapTemplate.setZoomPicto( daoUtil.getInt( nIndex++ ) );
                    dataLayerMapTemplate.setIconImage( daoUtil.getString( nIndex++ ) );
                    dataLayerMapTemplate.setPictoSizeZoom07( daoUtil.getInt( nIndex++ ) );
                    dataLayerMapTemplate.setPictoSizeZoom812( daoUtil.getInt( nIndex++ ) );
                    dataLayerMapTemplate.setPictoSizeZoom1315( daoUtil.getInt( nIndex++ ) );
                    dataLayerMapTemplate.setPictoSizeZoom1619( daoUtil.getInt( nIndex++ ) );
                    dataLayerMapTemplate.setMarkerCluster( daoUtil.getBoolean( nIndex++ ) );

                    dataLayerMapTemplateList.add( dataLayerMapTemplate );
                }

                daoUtil.free( );

            }
        }
        return dataLayerMapTemplateList;

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<DataLayer> selectDataLayerListByMapTemplateId( Plugin _plugin, int nKey )
    {
        List<DataLayer> dataLayerList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_DATA_LAYER_BY_MAP_TEMPLATE_ID, _plugin ) )
        {
            daoUtil.setInt( 1, nKey );
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                int nIndex = 1;
                Optional<DataLayer> dataLayer = DataLayerHome.findByPrimaryKey( daoUtil.getInt( nIndex++ ) );
                if ( dataLayer.isPresent( ) )
                {
                    dataLayerList.add( dataLayer.get( ) );
                }
            }

            return dataLayerList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<DataLayerMapTemplate> loadByIdMapIdLayer( int nMapKey, int nDataLayerKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID_MAP_ID_LAYER, plugin ) )
        {
            daoUtil.setInt( 1, nMapKey );
            daoUtil.setInt( 2, nDataLayerKey );
            daoUtil.executeQuery( );
            DataLayerMapTemplate dataLayerMapTemplate = null;

            if ( daoUtil.next( ) )
            {
                dataLayerMapTemplate = new DataLayerMapTemplate( );
                int nIndex = 1;

                dataLayerMapTemplate.setId( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setIdMapTemplate( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setIdDataLayer( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setPictogram( daoUtil.getString( nIndex++ ) );
                dataLayerMapTemplate.setZoomMin( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setZoomMax( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setLayerType( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setColor( daoUtil.getString( nIndex++ ) );
                dataLayerMapTemplate.setThickness( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setIdCoordinate( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setZoomPicto( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setIconImage( daoUtil.getString( nIndex++ ) );
                dataLayerMapTemplate.setPictoSizeZoom07( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setPictoSizeZoom812( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setPictoSizeZoom1315( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setPictoSizeZoom1619( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setMarkerCluster( daoUtil.getBoolean( nIndex++ ) );
            }

            return Optional.ofNullable( dataLayerMapTemplate );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<DataLayerMapTemplate> loadByDataLayerId( Plugin _plugin, int nKey )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_DATA_LAYER_ID, _plugin ) )
        {
            daoUtil.setInt( 1, nKey );
            daoUtil.executeQuery( );
            DataLayerMapTemplate dataLayerMapTemplate = null;

            if ( daoUtil.next( ) )
            {
                dataLayerMapTemplate = new DataLayerMapTemplate( );
                int nIndex = 1;

                dataLayerMapTemplate.setId( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setIdMapTemplate( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setIdDataLayer( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setPictogram( daoUtil.getString( nIndex++ ) );
                dataLayerMapTemplate.setZoomMin( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setZoomMax( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setLayerType( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setColor( daoUtil.getString( nIndex++ ) );
                dataLayerMapTemplate.setThickness( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setIdCoordinate( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setZoomPicto( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setIconImage( daoUtil.getString( nIndex++ ) );
                dataLayerMapTemplate.setPictoSizeZoom07( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setPictoSizeZoom812( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setPictoSizeZoom1315( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setPictoSizeZoom1619( daoUtil.getInt( nIndex++ ) );
                dataLayerMapTemplate.setMarkerCluster( daoUtil.getBoolean( nIndex++ ) );
            }

            return Optional.ofNullable( dataLayerMapTemplate );
        }
    }
}
