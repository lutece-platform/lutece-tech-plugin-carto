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

import fr.paris.lutece.test.LuteceTestCase;

import java.util.Optional;

/**
 * This is the business class test for the object MapTemplate
 */
public class MapTemplateBusinessTest extends LuteceTestCase
{
    private static final String TITLE1 = "Title1";
    private static final String TITLE2 = "Title2";
    private static final String DESCRIPTION1 = "Description1";
    private static final String DESCRIPTION2 = "Description2";
    private static final String MAPBACKGROUND1 = "MapBackground1";
    private static final String MAPBACKGROUND2 = "MapBackground2";
    private static final int DEFAULTZOOM1 = 1;
    private static final int DEFAULTZOOM2 = 2;
    private static final int ZOOMMIN1 = 1;
    private static final int ZOOMMIN2 = 2;
    private static final int ZOOMMAX1 = 1;
    private static final int ZOOMMAX2 = 2;
    private static final String CENTERMAP1 = "CenterMap1";
    private static final String CENTERMAP2 = "CenterMap2";
    private static final Double CENTERMAPX1 = 1.0;
    private static final Double CENTERMAPX2 = 2.0;
    private static final Double CENTERMAPY1 = 1.0;
    private static final Double CENTERMAPY2 = 2.0;

    /**
     * test MapTemplate
     */
    public void testBusiness( )
    {
        // Initialize an object
        MapTemplate mapTemplate = new MapTemplate( );
        mapTemplate.setTitle( TITLE1 );
        mapTemplate.setDescription( DESCRIPTION1 );
        mapTemplate.setMapBackground( MAPBACKGROUND1 );
        mapTemplate.setDefaultZoom( DEFAULTZOOM1 );
        mapTemplate.setZoomMin( ZOOMMIN1 );
        mapTemplate.setZoomMax( ZOOMMAX1 );
        mapTemplate.setCenterMap( CENTERMAP1 );
        mapTemplate.setCenterMapX( CENTERMAPX1 );
        mapTemplate.setCenterMapY( CENTERMAPY1 );

        // Create test
        MapTemplateHome.create( mapTemplate );
        Optional<MapTemplate> optMapTemplateStored = MapTemplateHome.findByPrimaryKey( mapTemplate.getId( ) );
        MapTemplate mapTemplateStored = optMapTemplateStored.orElse( new MapTemplate( ) );
        assertEquals( mapTemplateStored.getTitle( ), mapTemplate.getTitle( ) );
        assertEquals( mapTemplateStored.getDescription( ), mapTemplate.getDescription( ) );
        assertEquals( mapTemplateStored.getMapBackground( ), mapTemplate.getMapBackground( ) );
        assertEquals( mapTemplateStored.getDefaultZoom( ), mapTemplate.getDefaultZoom( ) );
        assertEquals( mapTemplateStored.getZoomMin( ), mapTemplate.getZoomMin( ) );
        assertEquals( mapTemplateStored.getZoomMax( ), mapTemplate.getZoomMax( ) );
        assertEquals( mapTemplateStored.getCenterMap( ), mapTemplate.getCenterMap( ) );
        assertEquals( mapTemplateStored.getCenterMapX( ), mapTemplate.getCenterMapX( ) );
        assertEquals( mapTemplateStored.getCenterMapY( ), mapTemplate.getCenterMapY( ) );

        // Update test
        mapTemplate.setTitle( TITLE2 );
        mapTemplate.setDescription( DESCRIPTION2 );
        mapTemplate.setMapBackground( MAPBACKGROUND2 );
        mapTemplate.setDefaultZoom( DEFAULTZOOM2 );
        mapTemplate.setZoomMin( ZOOMMIN2 );
        mapTemplate.setZoomMax( ZOOMMAX2 );
        mapTemplate.setCenterMap( CENTERMAP2 );
        mapTemplate.setCenterMapX( CENTERMAPX2 );
        mapTemplate.setCenterMapY( CENTERMAPY2 );
        MapTemplateHome.update( mapTemplate );
        optMapTemplateStored = MapTemplateHome.findByPrimaryKey( mapTemplate.getId( ) );
        mapTemplateStored = optMapTemplateStored.orElse( new MapTemplate( ) );

        assertEquals( mapTemplateStored.getTitle( ), mapTemplate.getTitle( ) );
        assertEquals( mapTemplateStored.getDescription( ), mapTemplate.getDescription( ) );
        assertEquals( mapTemplateStored.getMapBackground( ), mapTemplate.getMapBackground( ) );
        assertEquals( mapTemplateStored.getDefaultZoom( ), mapTemplate.getDefaultZoom( ) );
        assertEquals( mapTemplateStored.getZoomMin( ), mapTemplate.getZoomMin( ) );
        assertEquals( mapTemplateStored.getZoomMax( ), mapTemplate.getZoomMax( ) );
        assertEquals( mapTemplateStored.getCenterMap( ), mapTemplate.getCenterMap( ) );
        assertEquals( mapTemplateStored.getCenterMapX( ), mapTemplate.getCenterMapX( ) );
        assertEquals( mapTemplateStored.getCenterMapY( ), mapTemplate.getCenterMapY( ) );

        // List test
        MapTemplateHome.getMapTemplatesList( );

        // Delete test
        MapTemplateHome.remove( mapTemplate.getId( ) );
        optMapTemplateStored = MapTemplateHome.findByPrimaryKey( mapTemplate.getId( ) );
        mapTemplateStored = optMapTemplateStored.orElse( null );
        assertNull( mapTemplateStored );

    }

}
