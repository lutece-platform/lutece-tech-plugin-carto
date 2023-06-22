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
 * This class provides Data Access methods for MapTemplate objects
 */
public final class MapTemplateDAO implements IMapTemplateDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_modele_carte, title, description, map_background, default_zoom, zoom_min, zoom_max, center_map, center_map_x, center_map_y, front_office FROM carto_map_template WHERE id_modele_carte = ?";
    private static final String SQL_QUERY_SELECT_XPAGE_FO = "SELECT id_modele_carte, title, description, map_background, default_zoom, zoom_min, zoom_max, center_map, center_map_x, center_map_y, front_office FROM carto_map_template WHERE front_office = 1";
    private static final String SQL_QUERY_INSERT = "INSERT INTO carto_map_template ( title, description, map_background, default_zoom, zoom_min, zoom_max, center_map, center_map_x, center_map_y, front_office ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM carto_map_template WHERE id_modele_carte = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE carto_map_template SET title = ?, description = ?, map_background = ?, default_zoom = ?, zoom_min = ?, zoom_max = ?, center_map = ?, center_map_x = ?, center_map_y = ?, front_office = ? WHERE id_modele_carte = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_modele_carte, title, description, map_background, default_zoom, zoom_min, zoom_max, center_map, center_map_x, center_map_y, front_office FROM carto_map_template";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_modele_carte FROM carto_map_template";
    private static final String SQL_QUERY_SELECTALL_BY_IDS = "SELECT id_modele_carte, title, description, map_background, default_zoom, zoom_min, zoom_max, center_map, center_map_x, center_map_y, front_office FROM carto_map_template WHERE id_modele_carte IN (  ";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( MapTemplate mapTemplate, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++ , mapTemplate.getTitle( ) );
            daoUtil.setString( nIndex++ , mapTemplate.getDescription( ) );
            daoUtil.setString( nIndex++ , mapTemplate.getMapBackground( ) );
            daoUtil.setInt( nIndex++ , mapTemplate.getDefaultZoom( ) );
            daoUtil.setInt( nIndex++ , mapTemplate.getZoomMin( ) );
            daoUtil.setInt( nIndex++ , mapTemplate.getZoomMax( ) );
            daoUtil.setString( nIndex++ , mapTemplate.getCenterMap( ) );
            daoUtil.setDouble( nIndex++ , mapTemplate.getCenterMapX( ) );
            daoUtil.setDouble( nIndex++ , mapTemplate.getCenterMapY( ) );
            daoUtil.setBoolean( nIndex++, mapTemplate.getFrontOffice( ) );
            
            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) ) 
            {
                mapTemplate.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }
        
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<MapTemplate> load( int nKey, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
	        daoUtil.setInt( 1 , nKey );
	        daoUtil.executeQuery( );
	        MapTemplate mapTemplate = null;
	
	        if ( daoUtil.next( ) )
	        {
	            mapTemplate = new MapTemplate();
	            int nIndex = 1;
	            
	            mapTemplate.setId( daoUtil.getInt( nIndex++ ) );
			    mapTemplate.setTitle( daoUtil.getString( nIndex++ ) );
			    mapTemplate.setDescription( daoUtil.getString( nIndex++ ) );
			    mapTemplate.setMapBackground( daoUtil.getString( nIndex++ ) );
			    mapTemplate.setDefaultZoom( daoUtil.getInt( nIndex++ ) );
			    mapTemplate.setZoomMin( daoUtil.getInt( nIndex++ ) );
			    mapTemplate.setZoomMax( daoUtil.getInt( nIndex++ ) );
			    mapTemplate.setCenterMap( daoUtil.getString( nIndex++ ) );
			    mapTemplate.setCenterMapX( daoUtil.getDouble( nIndex++ ) );
			    mapTemplate.setCenterMapY( daoUtil.getDouble( nIndex++ ) );
			    mapTemplate.setFrontOffice( daoUtil.getBoolean( nIndex ) );
	        }
	
	        return Optional.ofNullable( mapTemplate );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin ) )
        {
	        daoUtil.setInt( 1 , nKey );
	        daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( MapTemplate mapTemplate, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
	        int nIndex = 1;
	        
            	daoUtil.setString( nIndex++ , mapTemplate.getTitle( ) );
            	daoUtil.setString( nIndex++ , mapTemplate.getDescription( ) );
            	daoUtil.setString( nIndex++ , mapTemplate.getMapBackground( ) );
            	daoUtil.setInt( nIndex++ , mapTemplate.getDefaultZoom( ) );
            	daoUtil.setInt( nIndex++ , mapTemplate.getZoomMin( ) );
            	daoUtil.setInt( nIndex++ , mapTemplate.getZoomMax( ) );
            	daoUtil.setString( nIndex++ , mapTemplate.getCenterMap( ) );
            	daoUtil.setDouble( nIndex++ , mapTemplate.getCenterMapX( ) );
                daoUtil.setDouble( nIndex++ , mapTemplate.getCenterMapY( ) );
                daoUtil.setBoolean( nIndex++, mapTemplate.getFrontOffice( ) );
	        daoUtil.setInt( nIndex , mapTemplate.getId( ) );
	
	        daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<MapTemplate> selectMapTemplatesList( Plugin plugin )
    {
        List<MapTemplate> mapTemplateList = new ArrayList<>(  );
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
	        daoUtil.executeQuery(  );
	
	        while ( daoUtil.next(  ) )
	        {
	            MapTemplate mapTemplate = new MapTemplate(  );
	            int nIndex = 1;
	            
	            mapTemplate.setId( daoUtil.getInt( nIndex++ ) );
			    mapTemplate.setTitle( daoUtil.getString( nIndex++ ) );
			    mapTemplate.setDescription( daoUtil.getString( nIndex++ ) );
			    mapTemplate.setMapBackground( daoUtil.getString( nIndex++ ) );
			    mapTemplate.setDefaultZoom( daoUtil.getInt( nIndex++ ) );
			    mapTemplate.setZoomMin( daoUtil.getInt( nIndex++ ) );
			    mapTemplate.setZoomMax( daoUtil.getInt( nIndex++ ) );
			    mapTemplate.setCenterMap( daoUtil.getString( nIndex++ ) );
			    mapTemplate.setCenterMapX( daoUtil.getDouble( nIndex++ ) );
			    mapTemplate.setCenterMapY( daoUtil.getDouble( nIndex++ ) );
			    mapTemplate.setFrontOffice( daoUtil.getBoolean( nIndex ) );
	
	            mapTemplateList.add( mapTemplate );
	        }
	
	        return mapTemplateList;
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdMapTemplatesList( Plugin plugin )
    {
        List<Integer> mapTemplateList = new ArrayList<>( );
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin ) )
        {
	        daoUtil.executeQuery(  );
	
	        while ( daoUtil.next(  ) )
	        {
	            mapTemplateList.add( daoUtil.getInt( 1 ) );
	        }
	
	        return mapTemplateList;
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectMapTemplatesReferenceList( Plugin plugin )
    {
        ReferenceList mapTemplateList = new ReferenceList();
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
	        daoUtil.executeQuery(  );
	
	        while ( daoUtil.next(  ) )
	        {
	            mapTemplateList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
	        }
	
	        return mapTemplateList;
    	}
    }
    
    /**
     * {@inheritDoc }
     */
	@Override
	public List<MapTemplate> selectMapTemplatesListByIds( Plugin plugin, List<Integer> listIds ) {
		List<MapTemplate> mapTemplateList = new ArrayList<>(  );
		
		StringBuilder builder = new StringBuilder( );

		if ( !listIds.isEmpty( ) )
		{
			for( int i = 0 ; i < listIds.size(); i++ ) {
			    builder.append( "?," );
			}
	
			String placeHolders =  builder.deleteCharAt( builder.length( ) -1 ).toString( );
			String stmt = SQL_QUERY_SELECTALL_BY_IDS + placeHolders + ")";
			
			
	        try ( DAOUtil daoUtil = new DAOUtil( stmt, plugin ) )
	        {
	        	int index = 1;
				for( Integer n : listIds ) {
					daoUtil.setInt(  index++, n ); 
				}
	        	
	        	daoUtil.executeQuery(  );
	        	while ( daoUtil.next(  ) )
		        {
		        	MapTemplate mapTemplate = new MapTemplate(  );
		            int nIndex = 1;
		            
		            mapTemplate.setId( daoUtil.getInt( nIndex++ ) );
				    mapTemplate.setTitle( daoUtil.getString( nIndex++ ) );
				    mapTemplate.setDescription( daoUtil.getString( nIndex++ ) );
				    mapTemplate.setMapBackground( daoUtil.getString( nIndex++ ) );
				    mapTemplate.setDefaultZoom( daoUtil.getInt( nIndex++ ) );
				    mapTemplate.setZoomMin( daoUtil.getInt( nIndex++ ) );
				    mapTemplate.setZoomMax( daoUtil.getInt( nIndex++ ) );
				    mapTemplate.setCenterMap( daoUtil.getString( nIndex++ ) );
				    mapTemplate.setCenterMapX( daoUtil.getDouble( nIndex++ ) );
				    mapTemplate.setCenterMapY( daoUtil.getDouble( nIndex++ ) );
				    mapTemplate.setFrontOffice( daoUtil.getBoolean( nIndex ) );
		            
		            mapTemplateList.add( mapTemplate );
		        }
		
		        daoUtil.free( );
		        
	        }
	    }
		return mapTemplateList;
		
	}

	/**
     * {@inheritDoc }
     */
    @Override
	public Optional<MapTemplate> loadXpageFO( Plugin plugin) {
    	try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_XPAGE_FO, plugin ) )
        {
	        daoUtil.executeQuery( );
	        MapTemplate mapTemplate = null;
	
	        if ( daoUtil.next( ) )
	        {
	            mapTemplate = new MapTemplate();
	            int nIndex = 1;
	            
	            mapTemplate.setId( daoUtil.getInt( nIndex++ ) );
			    mapTemplate.setTitle( daoUtil.getString( nIndex++ ) );
			    mapTemplate.setDescription( daoUtil.getString( nIndex++ ) );
			    mapTemplate.setMapBackground( daoUtil.getString( nIndex++ ) );
			    mapTemplate.setDefaultZoom( daoUtil.getInt( nIndex++ ) );
			    mapTemplate.setZoomMin( daoUtil.getInt( nIndex++ ) );
			    mapTemplate.setZoomMax( daoUtil.getInt( nIndex++ ) );
			    mapTemplate.setCenterMap( daoUtil.getString( nIndex++ ) );
			    mapTemplate.setCenterMapX( daoUtil.getDouble( nIndex++ ) );
			    mapTemplate.setCenterMapY( daoUtil.getDouble( nIndex++ ) );
			    mapTemplate.setFrontOffice( daoUtil.getBoolean( nIndex ) );
	        }
	
	        return Optional.ofNullable( mapTemplate );
        }
	}
}
