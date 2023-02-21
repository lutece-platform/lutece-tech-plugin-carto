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
 * This class provides Data Access methods for DataLayer objects
 */
public final class DataLayerDAO implements IDataLayerDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_data_layer, title, solr_tag, geometry FROM carto_data_layer WHERE id_data_layer = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO carto_data_layer ( title, solr_tag, geometry ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM carto_data_layer WHERE id_data_layer = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE carto_data_layer SET title = ?, solr_tag = ?, geometry = ? WHERE id_data_layer = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_data_layer, title, solr_tag, geometry FROM carto_data_layer";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_data_layer FROM carto_data_layer";
    private static final String SQL_QUERY_SELECTALL_BY_IDS = "SELECT id_data_layer, title, solr_tag, geometry FROM carto_data_layer WHERE id_data_layer IN (  ";
    private static final String SQL_QUERY_SELECT_LAYER_EDITABLE = "SELECT a.id_data_layer, a.title, a.solr_tag, a.geometry FROM carto_data_layer a INNER JOIN carto_data_layer_map_template b ON a.id_data_layer = b.id_data_layer "
    															+ "INNER JOIN carto_data_layer_type c ON b.layer_type = c.id_data_layer_type  WHERE c.editable = 1 AND b.id_map_template = ? ";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( DataLayer dataLayer, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++ , dataLayer.getTitle( ) );
            daoUtil.setString( nIndex++ , dataLayer.getSolrTag( ) );
            //daoUtil.setInt( nIndex++ , dataLayer.getGeometry( ) );
            daoUtil.setInt( nIndex++ , dataLayer.getGeometryType( ).getId( ) );
            
            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) ) 
            {
                dataLayer.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }
        
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<DataLayer> load( int nKey, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
	        daoUtil.setInt( 1 , nKey );
	        daoUtil.executeQuery( );
	        DataLayer dataLayer = null;
	
	        if ( daoUtil.next( ) )
	        {
	            dataLayer = new DataLayer();
	            int nIndex = 1;
	            
	            dataLayer.setId( daoUtil.getInt( nIndex++ ) );
			    dataLayer.setTitle( daoUtil.getString( nIndex++ ) );
			    dataLayer.setSolrTag( daoUtil.getString( nIndex++ ) );
			    //dataLayer.setGeometry( daoUtil.getInt( nIndex ) );
			    Optional<GeometryType> geometryType = GeometryTypeHome.findByPrimaryKey( daoUtil.getInt( nIndex ) );
			    if ( geometryType.isPresent( ) )
			    {
			    	dataLayer.setGeometryType( geometryType.get( ) );
	        	}
	        }
	
	        return Optional.ofNullable( dataLayer );
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
    public void store( DataLayer dataLayer, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
	        int nIndex = 1;
	        
            	daoUtil.setString( nIndex++ , dataLayer.getTitle( ) );
            	daoUtil.setString( nIndex++ , dataLayer.getSolrTag( ) );
            	//daoUtil.setInt( nIndex++ , dataLayer.getGeometry( ) );
            	daoUtil.setInt( nIndex++ , dataLayer.getGeometryType( ).getId( ) );
	        daoUtil.setInt( nIndex , dataLayer.getId( ) );
	
	        daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<DataLayer> selectDataLayersList( Plugin plugin )
    {
        List<DataLayer> dataLayerList = new ArrayList<>(  );
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
	        daoUtil.executeQuery(  );
	
	        while ( daoUtil.next(  ) )
	        {
	            DataLayer dataLayer = new DataLayer(  );
	            int nIndex = 1;
	            
	            dataLayer.setId( daoUtil.getInt( nIndex++ ) );
			    dataLayer.setTitle( daoUtil.getString( nIndex++ ) );
			    dataLayer.setSolrTag( daoUtil.getString( nIndex++ ) );
			    //dataLayer.setGeometry( daoUtil.getInt( nIndex ) );
			    Optional<GeometryType> geometryType = GeometryTypeHome.findByPrimaryKey( daoUtil.getInt( nIndex ) );
			    if ( geometryType.isPresent( ) )
			    {
			    	dataLayer.setGeometryType( geometryType.get( ) );
	        	}
	
	            dataLayerList.add( dataLayer );
	        }
	
	        return dataLayerList;
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdDataLayersList( Plugin plugin )
    {
        List<Integer> dataLayerList = new ArrayList<>( );
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin ) )
        {
	        daoUtil.executeQuery(  );
	
	        while ( daoUtil.next(  ) )
	        {
	            dataLayerList.add( daoUtil.getInt( 1 ) );
	        }
	
	        return dataLayerList;
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectDataLayersReferenceList( Plugin plugin )
    {
        ReferenceList dataLayerList = new ReferenceList();
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
	        daoUtil.executeQuery(  );
	
	        while ( daoUtil.next(  ) )
	        {
	            dataLayerList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
	        }
	
	        return dataLayerList;
    	}
    }
    
    /**
     * {@inheritDoc }
     */
	@Override
	public List<DataLayer> selectDataLayersListByIds( Plugin plugin, List<Integer> listIds ) {
		List<DataLayer> dataLayerList = new ArrayList<>(  );
		
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
		        	DataLayer dataLayer = new DataLayer(  );
		            int nIndex = 1;
		            
		            dataLayer.setId( daoUtil.getInt( nIndex++ ) );
				    dataLayer.setTitle( daoUtil.getString( nIndex++ ) );
				    dataLayer.setSolrTag( daoUtil.getString( nIndex++ ) );
				    //dataLayer.setGeometry( daoUtil.getInt( nIndex ) );
				    Optional<GeometryType> geometryType = GeometryTypeHome.findByPrimaryKey( daoUtil.getInt( nIndex ) );
				    if ( geometryType.isPresent( ) )
				    {
				    	dataLayer.setGeometryType( geometryType.get( ) );
		        	}
		            
		            dataLayerList.add( dataLayer );
		        }
		
		        daoUtil.free( );
		        
	        }
	    }
		return dataLayerList;
		
	}
	
	/**
     * {@inheritDoc }
     */
    @Override
    public Optional<DataLayer> loadDataLayerEditable( int nKey, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_LAYER_EDITABLE, plugin ) )
        {
	        daoUtil.setInt( 1 , nKey );
	        daoUtil.executeQuery( );
	        DataLayer dataLayer = null;
	
	        if ( daoUtil.next( ) )
	        {
	            dataLayer = new DataLayer();
	            int nIndex = 1;
	            
	            dataLayer.setId( daoUtil.getInt( nIndex++ ) );
			    dataLayer.setTitle( daoUtil.getString( nIndex++ ) );
			    dataLayer.setSolrTag( daoUtil.getString( nIndex++ ) );
			    //dataLayer.setGeometry( daoUtil.getInt( nIndex ) );
			    Optional<GeometryType> geometryType = GeometryTypeHome.findByPrimaryKey( daoUtil.getInt( nIndex ) );
			    if ( geometryType.isPresent( ) )
			    {
			    	dataLayer.setGeometryType( geometryType.get( ) );
	        	}
	        }
	
	        return Optional.ofNullable( dataLayer );
        }
    }
}
