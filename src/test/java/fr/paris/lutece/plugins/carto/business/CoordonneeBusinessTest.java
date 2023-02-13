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
 *"
 * License 1.0
 */

package fr.paris.lutece.plugins.carto.business;

import fr.paris.lutece.test.LuteceTestCase;

import java.util.Optional;


/**
 * This is the business class test for the object Coordonnee
 */
public class CoordonneeBusinessTest extends LuteceTestCase
{
    private static final String ADRESSE1 = "Adresse1";
    private static final String ADRESSE2 = "Adresse2";
    private static final int COORDONNEEX1 = 1;
    private static final int COORDONNEEX2 = 2;
    private static final int COORDONNEEY1 = 1;
    private static final int COORDONNEEY2 = 2;

	/**
	* test Coordonnee
	*/
    public void testBusiness(  )
    {
        // Initialize an object
        Coordonnee coordonnee = new Coordonnee();
        coordonnee.setAdresse( ADRESSE1 );
        coordonnee.setCoordonneeX( COORDONNEEX1 );
        coordonnee.setCoordonneeY( COORDONNEEY1 );

        // Create test
        CoordonneeHome.create( coordonnee );
        Optional<Coordonnee> optCoordonneeStored = CoordonneeHome.findByPrimaryKey( coordonnee.getId( ) );
        Coordonnee coordonneeStored = optCoordonneeStored.orElse( new Coordonnee ( ) );
        assertEquals( coordonneeStored.getAdresse( ) , coordonnee.getAdresse( ) );
        assertEquals( coordonneeStored.getCoordonneeX( ) , coordonnee.getCoordonneeX( ) );
        assertEquals( coordonneeStored.getCoordonneeY( ) , coordonnee.getCoordonneeY( ) );

        // Update test
        coordonnee.setAdresse( ADRESSE2 );
        coordonnee.setCoordonneeX( COORDONNEEX2 );
        coordonnee.setCoordonneeY( COORDONNEEY2 );
        CoordonneeHome.update( coordonnee );
        optCoordonneeStored = CoordonneeHome.findByPrimaryKey( coordonnee.getId( ) );
        coordonneeStored = optCoordonneeStored.orElse( new Coordonnee ( ) );
        
        assertEquals( coordonneeStored.getAdresse( ) , coordonnee.getAdresse( ) );
        assertEquals( coordonneeStored.getCoordonneeX( ) , coordonnee.getCoordonneeX( ) );
        assertEquals( coordonneeStored.getCoordonneeY( ) , coordonnee.getCoordonneeY( ) );

        // List test
        CoordonneeHome.getCoordonneesList( );

        // Delete test
        CoordonneeHome.remove( coordonnee.getId( ) );
        optCoordonneeStored = CoordonneeHome.findByPrimaryKey( coordonnee.getId( ) );
        coordonneeStored = optCoordonneeStored.orElse( null );
        assertNull( coordonneeStored );
        
    }
    
    
     

}