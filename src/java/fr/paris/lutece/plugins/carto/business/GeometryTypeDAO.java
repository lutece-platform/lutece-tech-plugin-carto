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
 * This class provides Data Access methods for GeometryType objects
 */
public final class GeometryTypeDAO implements IGeometryTypeDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_geometry_type, name, technical_name FROM carto_geometry_type WHERE id_geometry_type = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO carto_geometry_type ( name, technical_name ) VALUES ( ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM carto_geometry_type WHERE id_geometry_type = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE carto_geometry_type SET name = ?, technical_name = ? WHERE id_geometry_type = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_geometry_type, name, technical_name FROM carto_geometry_type";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_geometry_type FROM carto_geometry_type";
    private static final String SQL_QUERY_SELECTALL_BY_IDS = "SELECT id_geometry_type, name, technical_name FROM carto_geometry_type WHERE id_geometry_type IN (  ";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( GeometryType geometryType, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++, geometryType.getName( ) );
            daoUtil.setString( nIndex++, geometryType.getTechnicalName( ) );

            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                geometryType.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<GeometryType> load( int nKey, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
            daoUtil.setInt( 1, nKey );
            daoUtil.executeQuery( );
            GeometryType geometryType = null;

            if ( daoUtil.next( ) )
            {
                geometryType = new GeometryType( );
                int nIndex = 1;

                geometryType.setId( daoUtil.getInt( nIndex++ ) );
                geometryType.setName( daoUtil.getString( nIndex++ ) );
                geometryType.setTechnicalName( daoUtil.getString( nIndex ) );
            }

            return Optional.ofNullable( geometryType );
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
    public void store( GeometryType geometryType, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
            int nIndex = 1;

            daoUtil.setString( nIndex++, geometryType.getName( ) );
            daoUtil.setString( nIndex++, geometryType.getTechnicalName( ) );
            daoUtil.setInt( nIndex, geometryType.getId( ) );

            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<GeometryType> selectGeometryTypesList( Plugin plugin )
    {
        List<GeometryType> geometryTypeList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                GeometryType geometryType = new GeometryType( );
                int nIndex = 1;

                geometryType.setId( daoUtil.getInt( nIndex++ ) );
                geometryType.setName( daoUtil.getString( nIndex++ ) );
                geometryType.setTechnicalName( daoUtil.getString( nIndex ) );

                geometryTypeList.add( geometryType );
            }

            return geometryTypeList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdGeometryTypesList( Plugin plugin )
    {
        List<Integer> geometryTypeList = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                geometryTypeList.add( daoUtil.getInt( 1 ) );
            }

            return geometryTypeList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectGeometryTypesReferenceList( Plugin plugin )
    {
        ReferenceList geometryTypeList = new ReferenceList( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                geometryTypeList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
            }

            return geometryTypeList;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<GeometryType> selectGeometryTypesListByIds( Plugin plugin, List<Integer> listIds )
    {
        List<GeometryType> geometryTypeList = new ArrayList<>( );

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
                    GeometryType geometryType = new GeometryType( );
                    int nIndex = 1;

                    geometryType.setId( daoUtil.getInt( nIndex++ ) );
                    geometryType.setName( daoUtil.getString( nIndex++ ) );
                    geometryType.setTechnicalName( daoUtil.getString( nIndex ) );

                    geometryTypeList.add( geometryType );
                }

                daoUtil.free( );

            }
        }
        return geometryTypeList;

    }
}
