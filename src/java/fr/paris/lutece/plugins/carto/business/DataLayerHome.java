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
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;
import java.util.Optional;

/**
 * This class provides instances management methods (create, find, ...) for DataLayer objects
 */
public final class DataLayerHome
{
    // Static variable pointed at the DAO instance
    private static IDataLayerDAO _dao = SpringContextService.getBean( "carto.dataLayerDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "carto" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private DataLayerHome( )
    {
    }

    /**
     * Create an instance of the dataLayer class
     * 
     * @param dataLayer
     *            The instance of the DataLayer which contains the informations to store
     * @return The instance of dataLayer which has been created with its primary key.
     */
    public static DataLayer create( DataLayer dataLayer )
    {
        _dao.insert( dataLayer, _plugin );

        return dataLayer;
    }

    /**
     * Update of the dataLayer which is specified in parameter
     * 
     * @param dataLayer
     *            The instance of the DataLayer which contains the data to store
     * @return The instance of the dataLayer which has been updated
     */
    public static DataLayer update( DataLayer dataLayer )
    {
        _dao.store( dataLayer, _plugin );

        return dataLayer;
    }

    /**
     * Remove the dataLayer whose identifier is specified in parameter
     * 
     * @param nKey
     *            The dataLayer Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a dataLayer whose identifier is specified in parameter
     * 
     * @param nKey
     *            The dataLayer primary key
     * @return an instance of DataLayer
     */
    public static Optional<DataLayer> findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }
    
    /**
     * Returns an instance of a dataLayer whose identifier is specified in parameter
     * 
     * @param nKey
     *            The dataLayer primary key
     * @return an instance of DataLayer
     */
    public static Optional<DataLayer> findBySolrTag( String strSolrTag )
    {
        return _dao.loadBySolrTag( strSolrTag, _plugin );
    }

    /**
     * Load the data of all the dataLayer objects and returns them as a list
     * 
     * @return the list which contains the data of all the dataLayer objects
     */
    public static List<DataLayer> getDataLayersList( )
    {
        return _dao.selectDataLayersList( _plugin );
    }
    
    /**
     * Load the data of all the dataLayer objects with WFS source and returns them as a list
     * 
     * @return the list which contains the data of all the dataLayer objects
     */
    public static List<DataLayer> getDataLayersListWFS( int nMapId )
    {
        return _dao.selectDataLayersListWFS( _plugin, nMapId );
    }

    /**
     * Load the id of all the dataLayer objects and returns them as a list
     * 
     * @return the list which contains the id of all the dataLayer objects
     */
    public static List<Integer> getIdDataLayersList( )
    {
        return _dao.selectIdDataLayersList( _plugin );
    }

    /**
     * Load the data of all the dataLayer objects and returns them as a referenceList
     * 
     * @return the referenceList which contains the data of all the dataLayer objects
     */
    public static ReferenceList getDataLayersReferenceList( )
    {
        return _dao.selectDataLayersReferenceList( _plugin );
    }

    /**
     * Load the data of all the avant objects and returns them as a list
     * 
     * @param listIds
     *            liste of ids
     * @return the list which contains the data of all the avant objects
     */
    public static List<DataLayer> getDataLayersListByIds( List<Integer> listIds )
    {
        return _dao.selectDataLayersListByIds( _plugin, listIds );
    }

    /**
     * Returns an instance of a dataLayer whose is editable for a specific map
     * 
     * @param nKey
     *            The MapTemplate primary key
     * @return an instance of DataLayer
     */
    public static Optional<DataLayer> findDataLayerFromMapId( int nKey, boolean editable, boolean inclusion, boolean exclusion )
    {
        return _dao.loadDataLayerWithOptions( nKey, editable, inclusion, exclusion, _plugin );
    }

}
