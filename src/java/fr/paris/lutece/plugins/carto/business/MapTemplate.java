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

import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
/**
 * This is the business class for the object MapTemplate
 */ 
public class MapTemplate implements Serializable
{
    private static final long serialVersionUID = 1L;

    // Variables declarations 
    private int _nId;
    
    @NotEmpty( message = "#i18n{carto.validation.maptemplate.Title.notEmpty}" )
    @Size( max = 255 , message = "#i18n{carto.validation.maptemplate.Title.size}" ) 
    private String _strTitle;
    
    private String _strDescription;
    
    @Size( max = 255 , message = "#i18n{carto.validation.maptemplate.MapBackground.size}" ) 
    private String _strMapBackground;
    
    private int _nDefaultZoom;
    
    private int _nZoomMin;
    
    private int _nZoomMax;
    
    @Size( max = 255 , message = "#i18n{carto.validation.maptemplate.CenterMap.size}" ) 
    private String _strCenterMap;
    
    private Double _nCenterMapX;
    
    private Double _nCenterMapY;
    
    private boolean _bFrontOffice;

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
     * Returns the Title
     * @return The Title
     */
    public String getTitle( )
    {
        return _strTitle;
    }

    /**
     * Sets the Title
     * @param strTitle The Title
     */ 
    public void setTitle( String strTitle )
    {
        _strTitle = strTitle;
    }
    
    
    /**
     * Returns the Description
     * @return The Description
     */
    public String getDescription( )
    {
        return _strDescription;
    }

    /**
     * Sets the Description
     * @param strDescription The Description
     */ 
    public void setDescription( String strDescription )
    {
        _strDescription = strDescription;
    }
    
    
    /**
     * Returns the MapBackground
     * @return The MapBackground
     */
    public String getMapBackground( )
    {
        return _strMapBackground;
    }

    /**
     * Sets the MapBackground
     * @param strMapBackground The MapBackground
     */ 
    public void setMapBackground( String strMapBackground )
    {
        _strMapBackground = strMapBackground;
    }
    
    
    /**
     * Returns the DefaultZoom
     * @return The DefaultZoom
     */
    public int getDefaultZoom( )
    {
        return _nDefaultZoom;
    }

    /**
     * Sets the DefaultZoom
     * @param nDefaultZoom The DefaultZoom
     */ 
    public void setDefaultZoom( int nDefaultZoom )
    {
        _nDefaultZoom = nDefaultZoom;
    }
    
    
    /**
     * Returns the ZoomMin
     * @return The ZoomMin
     */
    public int getZoomMin( )
    {
        return _nZoomMin;
    }

    /**
     * Sets the ZoomMin
     * @param nZoomMin The ZoomMin
     */ 
    public void setZoomMin( int nZoomMin )
    {
        _nZoomMin = nZoomMin;
    }
    
    
    /**
     * Returns the ZoomMax
     * @return The ZoomMax
     */
    public int getZoomMax( )
    {
        return _nZoomMax;
    }

    /**
     * Sets the ZoomMax
     * @param nZoomMax The ZoomMax
     */ 
    public void setZoomMax( int nZoomMax )
    {
        _nZoomMax = nZoomMax;
    }
    
    
    /**
     * Returns the CenterMap
     * @return The CenterMap
     */
    public String getCenterMap( )
    {
        return _strCenterMap;
    }

    /**
     * Sets the CenterMap
     * @param strCenterMap The CenterMap
     */ 
    public void setCenterMap( String strCenterMap )
    {
        _strCenterMap = strCenterMap;
    }

    /**
     * Returns the CenterMapX
     * @return The CenterMapX
     */
	public Double getCenterMapX() {
		return _nCenterMapX;
	}

	/**
     * Sets the CenterMapX
     * @param nCenterMapX The CenterMapX
     */ 
	public void setCenterMapX(Double nCenterMapX) {
		this._nCenterMapX = nCenterMapX;
	}

	/**
     * Returns the CenterMapY
     * @return The CenterMapY
     */
    public Double getCenterMapY( )
    {
        return _nCenterMapY;
    }

    /**
     * Sets the CenterMapY
     * @param nCenterMapY The CenterMapY
     */ 
    public void setCenterMapY( Double nCenterMapY )
    {
        _nCenterMapY = nCenterMapY;
    }
    
    /**
     * Returns the FrontOffice
     * @return The FrontOffice
     */
    public boolean getFrontOffice( )
    {
        return _bFrontOffice;
    }

    /**
     * Sets the FrontOffice
     * @param bFrontOffice The FrontOffice
     */ 
    public void setFrontOffice( boolean bFrontOffice )
    {
        _bFrontOffice = bFrontOffice;
    }
    
}
