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
 * This is the business class for the object DataLayerMapTemplate
 */ 
public class DataLayerMapTemplate implements Serializable
{
    private static final long serialVersionUID = 1L;

    // Variables declarations 
    private int _nId;
    
    private int _nIdMapTemplate;
    
    private int _nIdDataLayer;
    
    @Size( max = 50 , message = "#i18n{carto.validation.datalayermaptemplate.Pictogram.size}" ) 
    private String _strPictogram;
    
    private int _nZoomMin;
    
    private int _nZoomMax;
    
    private int _nLayerType;
    
    @Size( max = 50 , message = "#i18n{carto.validation.datalayermaptemplate.Color.size}" ) 
    private String _strColor;
    
    private int _nThickness;

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
     * Returns the IdMapTemplate
     * @return The IdMapTemplate
     */
    public int getIdMapTemplate( )
    {
        return _nIdMapTemplate;
    }

    /**
     * Sets the IdMapTemplate
     * @param nIdMapTemplate The IdMapTemplate
     */ 
    public void setIdMapTemplate( int nIdMapTemplate )
    {
        _nIdMapTemplate = nIdMapTemplate;
    }
    
    
    /**
     * Returns the IdDataLayer
     * @return The IdDataLayer
     */
    public int getIdDataLayer( )
    {
        return _nIdDataLayer;
    }

    /**
     * Sets the IdDataLayer
     * @param nIdDataLayer The IdDataLayer
     */ 
    public void setIdDataLayer( int nIdDataLayer )
    {
        _nIdDataLayer = nIdDataLayer;
    }
    
    
    /**
     * Returns the Pictogram
     * @return The Pictogram
     */
    public String getPictogram( )
    {
        return _strPictogram;
    }

    /**
     * Sets the Pictogram
     * @param strPictogram The Pictogram
     */ 
    public void setPictogram( String strPictogram )
    {
        _strPictogram = strPictogram;
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
     * Returns the LayerType
     * @return The LayerType
     */
    public int getLayerType( )
    {
        return _nLayerType;
    }

    /**
     * Sets the LayerType
     * @param nLayerType The LayerType
     */ 
    public void setLayerType( int nLayerType )
    {
        _nLayerType = nLayerType;
    }
    
    
    /**
     * Returns the Color
     * @return The Color
     */
    public String getColor( )
    {
        return _strColor;
    }

    /**
     * Sets the Color
     * @param strColor The Color
     */ 
    public void setColor( String strColor )
    {
        _strColor = strColor;
    }
    
    
    /**
     * Returns the Thickness
     * @return The Thickness
     */
    public int getThickness( )
    {
        return _nThickness;
    }

    /**
     * Sets the Thickness
     * @param nThickness The Thickness
     */ 
    public void setThickness( int nThickness )
    {
        _nThickness = nThickness;
    }
    
}
