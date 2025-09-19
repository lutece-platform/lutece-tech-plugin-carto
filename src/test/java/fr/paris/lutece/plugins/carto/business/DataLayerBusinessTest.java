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

import org.junit.jupiter.api.Test;

/**
 * This is the business class test for the object DataLayer
 */
public class DataLayerBusinessTest extends LuteceTestCase
{
    private static final String TITLE1 = "Title1";
    private static final String TITLE2 = "Title2";
    private static final String SOLRTAG1 = "SolrTag1";
    private static final String SOLRTAG2 = "SolrTag2";
    private static final int GEOMETRY1 = 1;
    private static final int GEOMETRY2 = 2;

    /**
     * test DataLayer
     */
    @Test
    public void testBusiness( )
    {
        // Initialize an object
    	GeometryType geoType = new GeometryType( );
    	geoType.setId( 1 );
    	geoType.setName( "geo1" );
        DataLayer dataLayer = new DataLayer( );
        dataLayer.setTitle( TITLE1 );
        dataLayer.setSolrTag( SOLRTAG1 );
        dataLayer.setGeometry( GEOMETRY1 );
        dataLayer.setPopupContent( "test" );
        dataLayer.setGeometryType( geoType );

        // Create test
        DataLayerHome.create( dataLayer );
        Optional<DataLayer> optDataLayerStored = DataLayerHome.findByPrimaryKey( dataLayer.getId( ) );
        DataLayer dataLayerStored = optDataLayerStored.orElse( new DataLayer( ) );
        assertEquals( dataLayerStored.getTitle( ), dataLayer.getTitle( ) );
        assertEquals( dataLayerStored.getSolrTag( ), dataLayer.getSolrTag( ) );
        //assertEquals( dataLayerStored.getGeometry( ), dataLayer.getGeometry( ) );

        // Update test
        GeometryType geoType2 = new GeometryType( );
        geoType2.setId( 2 );
        geoType2.setName( "geo2" );
        dataLayer.setTitle( TITLE2 );
        dataLayer.setSolrTag( SOLRTAG2 );
        dataLayer.setGeometry( GEOMETRY2 );
        dataLayer.setPopupContent( "test2" );
        dataLayer.setGeometryType( geoType2 );
        DataLayerHome.update( dataLayer );
        optDataLayerStored = DataLayerHome.findByPrimaryKey( dataLayer.getId( ) );
        dataLayerStored = optDataLayerStored.orElse( new DataLayer( ) );

        assertEquals( dataLayerStored.getTitle( ), dataLayer.getTitle( ) );
        assertEquals( dataLayerStored.getSolrTag( ), dataLayer.getSolrTag( ) );
        //assertEquals( dataLayerStored.getGeometry( ), dataLayer.getGeometry( ) );

        // List test
        DataLayerHome.getDataLayersList( );

        // Delete test
        DataLayerHome.remove( dataLayer.getId( ) );
        optDataLayerStored = DataLayerHome.findByPrimaryKey( dataLayer.getId( ) );
        dataLayerStored = optDataLayerStored.orElse( null );
        assertNull( dataLayerStored );

    }

}
