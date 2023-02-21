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
 * This class provides instances management methods (create, find, ...) for GeometryType objects
 */
public final class GeometryTypeHome
{
    // Static variable pointed at the DAO instance
    private static IGeometryTypeDAO _dao = SpringContextService.getBean( "carto.geometryTypeDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "carto" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private GeometryTypeHome(  )
    {
    }

    /**
     * Create an instance of the geometryType class
     * @param geometryType The instance of the GeometryType which contains the informations to store
     * @return The  instance of geometryType which has been created with its primary key.
     */
    public static GeometryType create( GeometryType geometryType )
    {
        _dao.insert( geometryType, _plugin );

        return geometryType;
    }

    /**
     * Update of the geometryType which is specified in parameter
     * @param geometryType The instance of the GeometryType which contains the data to store
     * @return The instance of the  geometryType which has been updated
     */
    public static GeometryType update( GeometryType geometryType )
    {
        _dao.store( geometryType, _plugin );

        return geometryType;
    }

    /**
     * Remove the geometryType whose identifier is specified in parameter
     * @param nKey The geometryType Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a geometryType whose identifier is specified in parameter
     * @param nKey The geometryType primary key
     * @return an instance of GeometryType
     */
    public static Optional<GeometryType> findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin );
    }

    /**
     * Load the data of all the geometryType objects and returns them as a list
     * @return the list which contains the data of all the geometryType objects
     */
    public static List<GeometryType> getGeometryTypesList( )
    {
        return _dao.selectGeometryTypesList( _plugin );
    }
    
    /**
     * Load the id of all the geometryType objects and returns them as a list
     * @return the list which contains the id of all the geometryType objects
     */
    public static List<Integer> getIdGeometryTypesList( )
    {
        return _dao.selectIdGeometryTypesList( _plugin );
    }
    
    /**
     * Load the data of all the geometryType objects and returns them as a referenceList
     * @return the referenceList which contains the data of all the geometryType objects
     */
    public static ReferenceList getGeometryTypesReferenceList( )
    {
        return _dao.selectGeometryTypesReferenceList( _plugin );
    }
    
	
    /**
     * Load the data of all the avant objects and returns them as a list
     * @param listIds liste of ids
     * @return the list which contains the data of all the avant objects
     */
    public static List<GeometryType> getGeometryTypesListByIds( List<Integer> listIds )
    {
        return _dao.selectGeometryTypesListByIds( _plugin, listIds );
    }

}

