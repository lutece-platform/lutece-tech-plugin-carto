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
 * This is the business class test for the object DataLayerType
 */
public class DataLayerTypeBusinessTest extends LuteceTestCase
{
    private static final String TITLE1 = "Title1";
    private static final String TITLE2 = "Title2";
	private static final boolean EDITABLE1 = true;
    private static final boolean EDITABLE2 = false;
	private static final boolean SEARCHABLEBYOTHERS1 = true;
    private static final boolean SEARCHABLEBYOTHERS2 = false;
	private static final boolean INCLUSION1 = true;
    private static final boolean INCLUSION2 = false;
	private static final boolean EXCLUSION1 = true;
    private static final boolean EXCLUSION2 = false;

	/**
	* test DataLayerType
	*/
    public void testBusiness(  )
    {
        // Initialize an object
        DataLayerType dataLayerType = new DataLayerType();
        dataLayerType.setTitle( TITLE1 );
        dataLayerType.setEditable( EDITABLE1 );
        dataLayerType.setSearchableByOthers( SEARCHABLEBYOTHERS1 );
        dataLayerType.setInclusion( INCLUSION1 );
        dataLayerType.setExclusion( EXCLUSION1 );

        // Create test
        DataLayerTypeHome.create( dataLayerType );
        Optional<DataLayerType> optDataLayerTypeStored = DataLayerTypeHome.findByPrimaryKey( dataLayerType.getId( ) );
        DataLayerType dataLayerTypeStored = optDataLayerTypeStored.orElse( new DataLayerType ( ) );
        assertEquals( dataLayerTypeStored.getTitle( ) , dataLayerType.getTitle( ) );
        assertEquals( dataLayerTypeStored.getEditable( ) , dataLayerType.getEditable( ) );
        assertEquals( dataLayerTypeStored.getSearchableByOthers( ) , dataLayerType.getSearchableByOthers( ) );
        assertEquals( dataLayerTypeStored.getInclusion( ) , dataLayerType.getInclusion( ) );
        assertEquals( dataLayerTypeStored.getExclusion( ) , dataLayerType.getExclusion( ) );

        // Update test
        dataLayerType.setTitle( TITLE2 );
        dataLayerType.setEditable( EDITABLE2 );
        dataLayerType.setSearchableByOthers( SEARCHABLEBYOTHERS2 );
        dataLayerType.setInclusion( INCLUSION2 );
        dataLayerType.setExclusion( EXCLUSION2 );
        DataLayerTypeHome.update( dataLayerType );
        optDataLayerTypeStored = DataLayerTypeHome.findByPrimaryKey( dataLayerType.getId( ) );
        dataLayerTypeStored = optDataLayerTypeStored.orElse( new DataLayerType ( ) );
        
        assertEquals( dataLayerTypeStored.getTitle( ) , dataLayerType.getTitle( ) );
        assertEquals( dataLayerTypeStored.getEditable( ) , dataLayerType.getEditable( ) );
        assertEquals( dataLayerTypeStored.getSearchableByOthers( ) , dataLayerType.getSearchableByOthers( ) );
        assertEquals( dataLayerTypeStored.getInclusion( ) , dataLayerType.getInclusion( ) );
        assertEquals( dataLayerTypeStored.getExclusion( ) , dataLayerType.getExclusion( ) );

        // List test
        DataLayerTypeHome.getDataLayerTypesList( );

        // Delete test
        DataLayerTypeHome.remove( dataLayerType.getId( ) );
        optDataLayerTypeStored = DataLayerTypeHome.findByPrimaryKey( dataLayerType.getId( ) );
        dataLayerTypeStored = optDataLayerTypeStored.orElse( null );
        assertNull( dataLayerTypeStored );
        
    }
    
    
     

}