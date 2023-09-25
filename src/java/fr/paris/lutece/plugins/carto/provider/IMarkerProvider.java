/*
 * Copyright (c) 2002-2021, City of Paris
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
package fr.paris.lutece.plugins.carto.provider;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;

/**
 * This interface represents a provider of NotifyGru markers
 *
 */
public interface IMarkerProvider
{
    /**
     * <p>
     * Gives the id.
     * </p>
     * <p>
     * The id is used to find which marker providers have to be used in the task. The id must be unique among the marker providers.
     * </p>
     * 
     * @return the id
     */
    String getId( );

    /**
     * Gives the i18n key of the title
     * 
     * @return the i18n key of the title
     */
    String getTitleI18nKey( );

    /**
     * <p>
     * Gives the marker descriptions
     * </p>
     * <p>
     * Used in the task configuration page (in order to add markers in rich texts for example)
     * </p>
     * 
     * @return the marker descriptions
     */
    Collection<InfoMarker> provideMarkerDescriptions( String solrTag, HttpServletRequest request );

    /**
     * <p>
     * Provides the values of the markers.
     * </p>
     * <p>
     * This method is used to replace the markers by the actual values of the resource when the notification id send.
     * </p>
     * 
     * @param request
     *            the request
     * @return the value of the markers
     */
    Collection<InfoMarker> provideMarkerValues( String solrTag, String uid );
    
    Map<String, String> valueMarker( String marker, String solrTag, String uid );
}
