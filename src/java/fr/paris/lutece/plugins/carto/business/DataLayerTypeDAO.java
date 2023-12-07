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
 * This class provides Data Access methods for DataLayerType objects
 */
public final class DataLayerTypeDAO implements IDataLayerTypeDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_data_layer_type, title, editable, searchable_by_others, inclusion, exclusion FROM carto_data_layer_type WHERE id_data_layer_type = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO carto_data_layer_type ( title, editable, searchable_by_others, inclusion, exclusion ) VALUES ( ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM carto_data_layer_type WHERE id_data_layer_type = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE carto_data_layer_type SET title = ?, editable = ?, searchable_by_others = ?, inclusion = ?, exclusion = ? WHERE id_data_layer_type = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_data_layer_type, title, editable, searchable_by_others, inclusion, exclusion FROM carto_data_layer_type";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_data_layer_type FROM carto_data_layer_type";
    private static final String SQL_QUERY_SELECTALL_BY_IDS = "SELECT id_data_layer_type, title, editable, searchable_by_others, inclusion, exclusion FROM carto_data_layer_type WHERE id_data_layer_type IN (  ";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( DataLayerType dataLayerType, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++, dataLayerType.getTitle( ) );
            daoUtil.setBoolean( nIndex++, dataLayerType.getEditable( ) );
            daoUtil.setBoolean( nIndex++, dataLayerType.getSearchableByOthers( ) );
            daoUtil.setBoolean( nIndex++, dataLayerType.getInclusion( ) );
            daoUtil.setBoolean( nIndex++, dataLayerType.getExclusion( ) );

            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                dataLayerType.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<DataLayerType> load( int nKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
            daoUtil.setInt( 1, nKey );
            daoUtil.executeQuery( );
            DataLayerType dataLayerType = null;

            if ( daoUtil.next( ) )
            {
                dataLayerType = new DataLayerType( );
                int nIndex = 1;

                dataLayerType.setId( daoUtil.getInt( nIndex++ ) );
                dataLayerType.setTitle( daoUtil.getString( nIndex++ ) );
                dataLayerType.setEditable( daoUtil.getBoolean( nIndex++ ) );
                dataLayerType.setSearchableByOthers( daoUtil.getBoolean( nIndex++ ) );
                dataLayerType.setInclusion( daoUtil.getBoolean( nIndex++ ) );
                dataLayerType.setExclusion( daoUtil.getBoolean( nIndex ) );
            }

            return Optional.ofNullable( dataLayerType );
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
    public void store( DataLayerType dataLayerType, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
            int nIndex = 1;

            daoUtil.setString( nIndex++, dataLayerType.getTitle( ) );
            daoUtil.setBoolean( nIndex++, dataLayerType.getEditable( ) );
            daoUtil.setBoolean( nIndex++, dataLayerType.getSearchableByOthers( ) );
            daoUtil.setBoolean( nIndex++, dataLayerType.getInclusion( ) );
            daoUtil.setBoolean( nIndex++, dataLayerType.getExclusion( ) );
            daoUtil.setInt( nIndex, dataLayerType.getId( ) );

            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<DataLayerType> selectDataLayerTypesList( Plugin plugin )
    {
        List<DataLayerType> dataLayerTypeList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                DataLayerType dataLayerType = new DataLayerType( );
                int nIndex = 1;

                dataLayerType.setId( daoUtil.getInt( nIndex++ ) );
                dataLayerType.setTitle( daoUtil.getString( nIndex++ ) );
                dataLayerType.setEditable( daoUtil.getBoolean( nIndex++ ) );
                dataLayerType.setSearchableByOthers( daoUtil.getBoolean( nIndex++ ) );
                dataLayerType.setInclusion( daoUtil.getBoolean( nIndex++ ) );
                dataLayerType.setExclusion( daoUtil.getBoolean( nIndex ) );

                dataLayerTypeList.add( dataLayerType );
            }

            return dataLayerTypeList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdDataLayerTypesList( Plugin plugin )
    {
        List<Integer> dataLayerTypeList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                dataLayerTypeList.add( daoUtil.getInt( 1 ) );
            }

            return dataLayerTypeList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectDataLayerTypesReferenceList( Plugin plugin )
    {
        ReferenceList dataLayerTypeList = new ReferenceList( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                dataLayerTypeList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
            }

            return dataLayerTypeList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<DataLayerType> selectDataLayerTypesListByIds( Plugin plugin, List<Integer> listIds )
    {
        List<DataLayerType> dataLayerTypeList = new ArrayList<>( );

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
                    DataLayerType dataLayerType = new DataLayerType( );
                    int nIndex = 1;

                    dataLayerType.setId( daoUtil.getInt( nIndex++ ) );
                    dataLayerType.setTitle( daoUtil.getString( nIndex++ ) );
                    dataLayerType.setEditable( daoUtil.getBoolean( nIndex++ ) );
                    dataLayerType.setSearchableByOthers( daoUtil.getBoolean( nIndex++ ) );
                    dataLayerType.setInclusion( daoUtil.getBoolean( nIndex++ ) );
                    dataLayerType.setExclusion( daoUtil.getBoolean( nIndex ) );

                    dataLayerTypeList.add( dataLayerType );
                }

                daoUtil.free( );

            }
        }
        return dataLayerTypeList;

    }
}
