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
 * This is the business class for the object DataLayerType
 */ 
public class DataLayerType implements Serializable
{
    private static final long serialVersionUID = 1L;

    // Variables declarations 
    private int _nId;
    
    @NotEmpty( message = "#i18n{carto.validation.datalayertype.Title.notEmpty}" )
    @Size( max = 255 , message = "#i18n{carto.validation.datalayertype.Title.size}" ) 
    private String _strTitle;
    
    private boolean _bEditable;
    
    private boolean _bSearchableByOthers;
    
    private boolean _bInclusion;
    
    private boolean _bExclusion;

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
     * Returns the Editable
     * @return The Editable
     */
    public boolean getEditable( )
    {
        return _bEditable;
    }

    /**
     * Sets the Editable
     * @param bEditable The Editable
     */ 
    public void setEditable( boolean bEditable )
    {
        _bEditable = bEditable;
    }
    
    
    /**
     * Returns the SearchableByOthers
     * @return The SearchableByOthers
     */
    public boolean getSearchableByOthers( )
    {
        return _bSearchableByOthers;
    }

    /**
     * Sets the SearchableByOthers
     * @param bSearchableByOthers The SearchableByOthers
     */ 
    public void setSearchableByOthers( boolean bSearchableByOthers )
    {
        _bSearchableByOthers = bSearchableByOthers;
    }
    
    /**
     * Returns the Inclusion
     * @return The Inclusion
     */
    public boolean getInclusion( )
    {
        return _bInclusion;
    }

    /**
     * Sets the Inclusion
     * @param bInclusion The Inclusion
     */ 
    public void setInclusion( boolean bInclusion )
    {
        _bInclusion = bInclusion;
    }
    
    /**
     * Returns the Exclusion
     * @return The Exclusion
     */
    public boolean getExclusion( )
    {
        return _bExclusion;
    }

    /**
     * Sets the Exclusion
     * @param bExclusion The Exclusion
     */ 
    public void setExclusion( boolean bExclusion )
    {
        _bExclusion = bExclusion;
    }
    
}
