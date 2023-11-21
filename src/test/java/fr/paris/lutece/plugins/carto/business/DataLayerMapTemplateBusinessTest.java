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
 *"
 * License 1.0
 */

package fr.paris.lutece.plugins.carto.business;

import fr.paris.lutece.test.LuteceTestCase;

import java.util.Optional;


/**
 * This is the business class test for the object DataLayerMapTemplate
 */
public class DataLayerMapTemplateBusinessTest extends LuteceTestCase
{
    private static final int IDMAPTEMPLATE1 = 1;
    private static final int IDMAPTEMPLATE2 = 2;
    private static final int IDDATALAYER1 = 1;
    private static final int IDDATALAYER2 = 2;
    private static final String PICTOGRAM1 = "Pictogram1";
    private static final String PICTOGRAM2 = "Pictogram2";
    private static final int ZOOMMIN1 = 1;
    private static final int ZOOMMIN2 = 2;
    private static final int ZOOMMAX1 = 1;
    private static final int ZOOMMAX2 = 2;
    private static final int LAYERTYPE1 = 1;
    private static final int LAYERTYPE2 = 2;
    private static final String COLOR1 = "Color1";
    private static final String COLOR2 = "Color2";
    private static final int THICKNESS1 = 1;
    private static final int THICKNESS2 = 2;

	/**
	* test DataLayerMapTemplate
	*/
    public void testBusiness(  )
    {
        // Initialize an object
        DataLayerMapTemplate dataLayerMapTemplate = new DataLayerMapTemplate();
        dataLayerMapTemplate.setIdMapTemplate( IDMAPTEMPLATE1 );
        dataLayerMapTemplate.setIdDataLayer( IDDATALAYER1 );
        dataLayerMapTemplate.setPictogram( PICTOGRAM1 );
        dataLayerMapTemplate.setZoomMin( ZOOMMIN1 );
        dataLayerMapTemplate.setZoomMax( ZOOMMAX1 );
        dataLayerMapTemplate.setLayerType( LAYERTYPE1 );
        dataLayerMapTemplate.setColor( COLOR1 );
        dataLayerMapTemplate.setThickness( THICKNESS1 );

        // Create test
        DataLayerMapTemplateHome.create( dataLayerMapTemplate );
        Optional<DataLayerMapTemplate> optDataLayerMapTemplateStored = DataLayerMapTemplateHome.findByPrimaryKey( dataLayerMapTemplate.getId( ) );
        DataLayerMapTemplate dataLayerMapTemplateStored = optDataLayerMapTemplateStored.orElse( new DataLayerMapTemplate ( ) );
        assertEquals( dataLayerMapTemplateStored.getIdMapTemplate( ) , dataLayerMapTemplate.getIdMapTemplate( ) );
        assertEquals( dataLayerMapTemplateStored.getIdDataLayer( ) , dataLayerMapTemplate.getIdDataLayer( ) );
        assertEquals( dataLayerMapTemplateStored.getPictogram( ) , dataLayerMapTemplate.getPictogram( ) );
        assertEquals( dataLayerMapTemplateStored.getZoomMin( ) , dataLayerMapTemplate.getZoomMin( ) );
        assertEquals( dataLayerMapTemplateStored.getZoomMax( ) , dataLayerMapTemplate.getZoomMax( ) );
        assertEquals( dataLayerMapTemplateStored.getLayerType( ) , dataLayerMapTemplate.getLayerType( ) );
        assertEquals( dataLayerMapTemplateStored.getColor( ) , dataLayerMapTemplate.getColor( ) );
        assertEquals( dataLayerMapTemplateStored.getThickness( ) , dataLayerMapTemplate.getThickness( ) );

        // Update test
        dataLayerMapTemplate.setIdMapTemplate( IDMAPTEMPLATE2 );
        dataLayerMapTemplate.setIdDataLayer( IDDATALAYER2 );
        dataLayerMapTemplate.setPictogram( PICTOGRAM2 );
        dataLayerMapTemplate.setZoomMin( ZOOMMIN2 );
        dataLayerMapTemplate.setZoomMax( ZOOMMAX2 );
        dataLayerMapTemplate.setLayerType( LAYERTYPE2 );
        dataLayerMapTemplate.setColor( COLOR2 );
        dataLayerMapTemplate.setThickness( THICKNESS2 );
        DataLayerMapTemplateHome.update( dataLayerMapTemplate );
        optDataLayerMapTemplateStored = DataLayerMapTemplateHome.findByPrimaryKey( dataLayerMapTemplate.getId( ) );
        dataLayerMapTemplateStored = optDataLayerMapTemplateStored.orElse( new DataLayerMapTemplate ( ) );
        
        assertEquals( dataLayerMapTemplateStored.getIdMapTemplate( ) , dataLayerMapTemplate.getIdMapTemplate( ) );
        assertEquals( dataLayerMapTemplateStored.getIdDataLayer( ) , dataLayerMapTemplate.getIdDataLayer( ) );
        assertEquals( dataLayerMapTemplateStored.getPictogram( ) , dataLayerMapTemplate.getPictogram( ) );
        assertEquals( dataLayerMapTemplateStored.getZoomMin( ) , dataLayerMapTemplate.getZoomMin( ) );
        assertEquals( dataLayerMapTemplateStored.getZoomMax( ) , dataLayerMapTemplate.getZoomMax( ) );
        assertEquals( dataLayerMapTemplateStored.getLayerType( ) , dataLayerMapTemplate.getLayerType( ) );
        assertEquals( dataLayerMapTemplateStored.getColor( ) , dataLayerMapTemplate.getColor( ) );
        assertEquals( dataLayerMapTemplateStored.getThickness( ) , dataLayerMapTemplate.getThickness( ) );

        // List test
        DataLayerMapTemplateHome.getDataLayerMapTemplatesList( );

        // Delete test
        DataLayerMapTemplateHome.remove( dataLayerMapTemplate.getId( ) );
        optDataLayerMapTemplateStored = DataLayerMapTemplateHome.findByPrimaryKey( dataLayerMapTemplate.getId( ) );
        dataLayerMapTemplateStored = optDataLayerMapTemplateStored.orElse( null );
        assertNull( dataLayerMapTemplateStored );
        
    }
    
    
     

}