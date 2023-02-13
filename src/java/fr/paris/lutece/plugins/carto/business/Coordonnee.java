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

import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
/**
 * This is the business class for the object Coordonnee
 */ 
public class Coordonnee implements Serializable
{
    private static final long serialVersionUID = 1L;

    // Variables declarations 
    private int _nId;
    
    @NotEmpty( message = "#i18n{carto.validation.coordonnee.Adresse.notEmpty}" )
    @Size( max = 255 , message = "#i18n{carto.validation.coordonnee.Adresse.size}" ) 
    private String _strAdresse;
    
    private Double _nCoordonneeX;
    
    private Double _nCoordonneeY;
    
    private String _strGeoJson;

    /**
     * Returns the Id
     * @return The Id
     */
    public int getId( )
    {
        return _nId;
    }

    /**
     * Sets the Id
     * @param nId The Id
     */ 
    public void setId( int nId )
    {
        _nId = nId;
    }
    
    /**
     * Returns the Adresse
     * @return The Adresse
     */
    public String getAdresse( )
    {
        return _strAdresse;
    }

    /**
     * Sets the Adresse
     * @param strAdresse The Adresse
     */ 
    public void setAdresse( String strAdresse )
    {
        _strAdresse = strAdresse;
    }
    
    
    /**
     * Returns the CoordonneeX
     * @return The CoordonneeX
     */
    public double getCoordonneeX( )
    {
        return _nCoordonneeX;
    }

    /**
     * Sets the CoordonneeX
     * @param nCoordonneeX The CoordonneeX
     */ 
    public void setCoordonneeX( Double nCoordonneeX )
    {
        _nCoordonneeX = nCoordonneeX;
    }
    
    
    /**
     * Returns the CoordonneeY
     * @return The CoordonneeY
     */
    public double getCoordonneeY( )
    {
        return _nCoordonneeY;
    }

    /**
     * Sets the CoordonneeY
     * @param nCoordonneeY The CoordonneeY
     */ 
    public void setCoordonneeY( Double nCoordonneeY )
    {
        _nCoordonneeY = nCoordonneeY;
    }

	public String getGeoJson() {
		return _strGeoJson;
	}

	public void setGeoJson(String _strGeoJson) {
		this._strGeoJson = _strGeoJson;
	}
    
}
