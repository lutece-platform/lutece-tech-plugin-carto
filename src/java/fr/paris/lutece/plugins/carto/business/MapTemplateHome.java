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
import fr.paris.lutece.util.ReferenceList;
import jakarta.enterprise.inject.spi.CDI;

import java.util.List;
import java.util.Optional;

/**
 * This class provides instances management methods (create, find, ...) for MapTemplate objects
 */
public final class MapTemplateHome
{
    // Static variable pointed at the DAO instance
    private static IMapTemplateDAO _dao = CDI.current( ).select( IMapTemplateDAO.class ).get( );
    private static Plugin _plugin = PluginService.getPlugin( "carto" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private MapTemplateHome( )
    {
    }

    /**
     * Create an instance of the mapTemplate class
     * 
     * @param mapTemplate
     *            The instance of the MapTemplate which contains the informations to store
     * @return The instance of mapTemplate which has been created with its primary key.
     */
    public static MapTemplate create( MapTemplate mapTemplate )
    {
        _dao.insert( mapTemplate, _plugin );

        return mapTemplate;
    }

    /**
     * Update of the mapTemplate which is specified in parameter
     * 
     * @param mapTemplate
     *            The instance of the MapTemplate which contains the data to store
     * @return The instance of the mapTemplate which has been updated
     */
    public static MapTemplate update( MapTemplate mapTemplate )
    {
        _dao.store( mapTemplate, _plugin );

        return mapTemplate;
    }

    /**
     * Remove the mapTemplate whose identifier is specified in parameter
     * 
     * @param nKey
     *            The mapTemplate Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a mapTemplate whose identifier is specified in parameter
     * 
     * @param nKey
     *            The mapTemplate primary key
     * @return an instance of MapTemplate
     */
    public static Optional<MapTemplate> findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Returns an instance of a mapTemplate whose identifier is specified in parameter
     * 
     * @return an instance of MapTemplate
     */
    public static Optional<MapTemplate> findXpageFrontOffice( )
    {
        return _dao.loadXpageFO( _plugin );
    }

    /**
     * Load the data of all the mapTemplate objects and returns them as a list
     * 
     * @return the list which contains the data of all the mapTemplate objects
     */
    public static List<MapTemplate> getMapTemplatesList( )
    {
        return _dao.selectMapTemplatesList( _plugin );
    }

    /**
     * Load the id of all the mapTemplate objects and returns them as a list
     * 
     * @return the list which contains the id of all the mapTemplate objects
     */
    public static List<Integer> getIdMapTemplatesList( )
    {
        return _dao.selectIdMapTemplatesList( _plugin );
    }

    /**
     * Load the data of all the mapTemplate objects and returns them as a referenceList
     * 
     * @return the referenceList which contains the data of all the mapTemplate objects
     */
    public static ReferenceList getMapTemplatesReferenceList( )
    {
        return _dao.selectMapTemplatesReferenceList( _plugin );
    }

    /**
     * Load the data of all the avant objects and returns them as a list
     * 
     * @param listIds
     *            liste of ids
     * @return the list which contains the data of all the avant objects
     */
    public static List<MapTemplate> getMapTemplatesListByIds( List<Integer> listIds )
    {
        return _dao.selectMapTemplatesListByIds( _plugin, listIds );
    }

}
