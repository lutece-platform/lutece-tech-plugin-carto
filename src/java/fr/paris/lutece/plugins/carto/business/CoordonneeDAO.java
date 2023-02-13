/*
 * Copyright (c) 2002-2022, City of Paris
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
 * This class provides Data Access methods for Coordonnee objects
 */
public final class CoordonneeDAO implements ICoordonneeDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_coordonnee, adresse, coordonnee_x, coordonnee_y, geojson FROM carto_coordonnee WHERE id_coordonnee = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO carto_coordonnee ( adresse, coordonnee_x, coordonnee_y, geojson ) VALUES ( ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM carto_coordonnee WHERE id_coordonnee = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE carto_coordonnee SET id_coordonnee = ?, adresse = ?, coordonnee_x = ?, coordonnee_y = ?, geojson = ? WHERE id_coordonnee = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_coordonnee, adresse, coordonnee_x, coordonnee_y, geojson FROM carto_coordonnee";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_coordonnee FROM carto_coordonnee";
    private static final String SQL_QUERY_SELECTALL_BY_IDS = "SELECT id_coordonnee, adresse, coordonnee_x, coordonnee_y, geojson FROM carto_coordonnee WHERE id_coordonnee IN (  ";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( Coordonnee coordonnee, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++ , coordonnee.getAdresse( ) );
            daoUtil.setDouble( nIndex++ , coordonnee.getCoordonneeX( ) );
            daoUtil.setDouble( nIndex++ , coordonnee.getCoordonneeY( ) );
            daoUtil.setString( nIndex++ , coordonnee.getGeoJson( ) );
            
            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) ) 
            {
                coordonnee.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }
        
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<Coordonnee> load( int nKey, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
	        daoUtil.setInt( 1 , nKey );
	        daoUtil.executeQuery( );
	        Coordonnee coordonnee = null;
	
	        if ( daoUtil.next( ) )
	        {
	            coordonnee = new Coordonnee();
	            int nIndex = 1;
	            
	            coordonnee.setId( daoUtil.getInt( nIndex++ ) );
			    coordonnee.setAdresse( daoUtil.getString( nIndex++ ) );
			    coordonnee.setCoordonneeX( daoUtil.getDouble( nIndex++ ) );
			    coordonnee.setCoordonneeY( daoUtil.getDouble( nIndex++ ) );
			    coordonnee.setGeoJson( daoUtil.getString( nIndex ) );
	        }
	
	        return Optional.ofNullable( coordonnee );
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
    public void store( Coordonnee coordonnee, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
	        int nIndex = 1;
	        
	        daoUtil.setInt( nIndex++ , coordonnee.getId( ) );
            	daoUtil.setString( nIndex++ , coordonnee.getAdresse( ) );
            	daoUtil.setDouble( nIndex++ , coordonnee.getCoordonneeX( ) );
            	daoUtil.setDouble( nIndex++ , coordonnee.getCoordonneeY( ) );
            	daoUtil.setString( nIndex++ , coordonnee.getGeoJson( ) );
	        daoUtil.setInt( nIndex , coordonnee.getId( ) );
	
	        daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Coordonnee> selectCoordonneesList( Plugin plugin )
    {
        List<Coordonnee> coordonneeList = new ArrayList<>(  );
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
	        daoUtil.executeQuery(  );
	
	        while ( daoUtil.next(  ) )
	        {
	            Coordonnee coordonnee = new Coordonnee(  );
	            int nIndex = 1;
	            
	            coordonnee.setId( daoUtil.getInt( nIndex++ ) );
			    coordonnee.setAdresse( daoUtil.getString( nIndex++ ) );
			    coordonnee.setCoordonneeX( daoUtil.getDouble( nIndex++ ) );
			    coordonnee.setCoordonneeY( daoUtil.getDouble( nIndex++ ) );
			    coordonnee.setGeoJson( daoUtil.getString( nIndex ) );
	
	            coordonneeList.add( coordonnee );
	        }
	
	        return coordonneeList;
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdCoordonneesList( Plugin plugin )
    {
        List<Integer> coordonneeList = new ArrayList<>( );
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin ) )
        {
	        daoUtil.executeQuery(  );
	
	        while ( daoUtil.next(  ) )
	        {
	            coordonneeList.add( daoUtil.getInt( 1 ) );
	        }
	
	        return coordonneeList;
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectCoordonneesReferenceList( Plugin plugin )
    {
        ReferenceList coordonneeList = new ReferenceList();
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
	        daoUtil.executeQuery(  );
	
	        while ( daoUtil.next(  ) )
	        {
	            coordonneeList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
	        }
	
	        return coordonneeList;
    	}
    }
    
    /**
     * {@inheritDoc }
     */
	@Override
	public List<Coordonnee> selectCoordonneesListByIds( Plugin plugin, List<Integer> listIds ) {
		List<Coordonnee> coordonneeList = new ArrayList<>(  );
		
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
		        	Coordonnee coordonnee = new Coordonnee(  );
		            int nIndex = 1;
		            
		            coordonnee.setId( daoUtil.getInt( nIndex++ ) );
				    coordonnee.setAdresse( daoUtil.getString( nIndex++ ) );
				    coordonnee.setCoordonneeX( daoUtil.getDouble( nIndex++ ) );
				    coordonnee.setCoordonneeY( daoUtil.getDouble( nIndex++ ) );
				    coordonnee.setGeoJson( daoUtil.getString( nIndex ) );
		            
		            coordonneeList.add( coordonnee );
		        }
		
		        daoUtil.free( );
		        
	        }
	    }
		return coordonneeList;
		
	}
}
