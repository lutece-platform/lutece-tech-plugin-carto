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
 * This class provides instances management methods (create, find, ...) for DataLayerMapTemplate objects
 */
public final class DataLayerMapTemplateHome
{
    // Static variable pointed at the DAO instance
    private static IDataLayerMapTemplateDAO _dao = SpringContextService.getBean( "carto.dataLayerMapTemplateDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "carto" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private DataLayerMapTemplateHome(  )
    {
    }

    /**
     * Create an instance of the dataLayerMapTemplate class
     * @param dataLayerMapTemplate The instance of the DataLayerMapTemplate which contains the informations to store
     * @return The  instance of dataLayerMapTemplate which has been created with its primary key.
     */
    public static DataLayerMapTemplate create( DataLayerMapTemplate dataLayerMapTemplate )
    {
        _dao.insert( dataLayerMapTemplate, _plugin );

        return dataLayerMapTemplate;
    }

    /**
     * Update of the dataLayerMapTemplate which is specified in parameter
     * @param dataLayerMapTemplate The instance of the DataLayerMapTemplate which contains the data to store
     * @return The instance of the  dataLayerMapTemplate which has been updated
     */
    public static DataLayerMapTemplate update( DataLayerMapTemplate dataLayerMapTemplate )
    {
        _dao.store( dataLayerMapTemplate, _plugin );

        return dataLayerMapTemplate;
    }

    /**
     * Remove the dataLayerMapTemplate whose identifier is specified in parameter
     * @param nKey The dataLayerMapTemplate Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a dataLayerMapTemplate whose identifier is specified in parameter
     * @param nKey The dataLayerMapTemplate primary key
     * @return an instance of DataLayerMapTemplate
     */
    public static Optional<DataLayerMapTemplate> findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Load the data of all the dataLayerMapTemplate objects and returns them as a list
     * @return the list which contains the data of all the dataLayerMapTemplate objects
     */
    public static List<DataLayerMapTemplate> getDataLayerMapTemplatesList( )
    {
        return _dao.selectDataLayerMapTemplatesList( _plugin );
    }
    
    /**
     * Load the id of all the dataLayerMapTemplate objects and returns them as a list
     * @return the list which contains the id of all the dataLayerMapTemplate objects
     */
    public static List<Integer> getIdDataLayerMapTemplatesList( )
    {
        return _dao.selectIdDataLayerMapTemplatesList( _plugin );
    }
    
    /**
     * Load the data of all the dataLayerMapTemplate objects and returns them as a referenceList
     * @return the referenceList which contains the data of all the dataLayerMapTemplate objects
     */
    public static ReferenceList getDataLayerMapTemplatesReferenceList( )
    {
        return _dao.selectDataLayerMapTemplatesReferenceList( _plugin );
    }
    
	
    /**
     * Load the data of all the avant objects and returns them as a list
     * @param listIds liste of ids
     * @return the list which contains the data of all the avant objects
     */
    public static List<DataLayerMapTemplate> getDataLayerMapTemplatesListByIds( List<Integer> listIds )
    {
        return _dao.selectDataLayerMapTemplatesListByIds( _plugin, listIds );
    }
    
    /**
     * Load all the DataLayer attached to the MapTemplate id
     * @param nkey : id of the mapTemplate
     * @return The list of DataLayer
     */
    public static List<DataLayer> getDataLayerListByMapTemplateId( int nKey )
    {
    	return _dao.selectDataLayerListByMapTemplateId( _plugin, nKey );
    }
    
    /**
     * Returns an instance of a dataLayerMapTemplate whose identifier is specified in parameter
     * @param nMapKey The MapTemplate primary key
     * @param nDataLayerKey The dataLayer primary key
     * @return an instance of DataLayerMapTemplate
     */
    public static Optional<DataLayerMapTemplate> findByIdMapKeyIdDataLayerKey( int nMapKey, int nDataLayerKey )
    {
        return _dao.loadByIdMapIdLayer( nMapKey, nDataLayerKey, _plugin );
    }

}

