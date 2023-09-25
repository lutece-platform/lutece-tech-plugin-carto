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
 * This is the business class for the object DataLayer
 */ 
public class DataLayer implements Serializable
{
    private static final long serialVersionUID = 1L;

    // Variables declarations 
    private int _nId;
    
    @NotEmpty( message = "#i18n{carto.validation.datalayer.Title.notEmpty}" )
    @Size( max = 255 , message = "#i18n{carto.validation.datalayer.Title.size}" ) 
    private String _strTitle;
    
    @Size( max = 255 , message = "#i18n{carto.validation.datalayer.SolrTag.size}" ) 
    private String _strSolrTag;
    
    private int _nGeometry;
    
    private GeometryType _geometryType;
    
    private String _strPopupContent;

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
     * Returns the SolrTag
     * @return The SolrTag
     */
    public String getSolrTag( )
    {
        return _strSolrTag;
    }

    /**
     * Sets the SolrTag
     * @param strSolrTag The SolrTag
     */ 
    public void setSolrTag( String strSolrTag )
    {
        _strSolrTag = strSolrTag;
    }
    
    
    /**
     * Returns the Geometry
     * @return The Geometry
     */
    public int getGeometry( )
    {
        return _nGeometry;
    }

    /**
     * Sets the Geometry
     * @param nGeometry The Geometry
     */ 
    public void setGeometry( int nGeometry )
    {
        _nGeometry = nGeometry;
    }

    /**
     * Returns the geometryType
     * @return The geometryType
     */
	public GeometryType getGeometryType( ) {
		return _geometryType;
	}

	/**
     * Sets the geometryType
     * @param geometryType The geometryType
     */ 
	public void setGeometryType(GeometryType geometryType) {
		this._geometryType = geometryType;
	}
    
	/**
     * Returns the popup content
     * @return The popup content
     */
	public String getPopupContent() {
		return _strPopupContent;
	}

	/**
     * Sets the popup content
     * @param strPopupContent The the popup content
     */ 
	public void setPopupContent(String strPopupContent) {
		this._strPopupContent = strPopupContent;
	}
}
