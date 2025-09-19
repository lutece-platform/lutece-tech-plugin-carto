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

package fr.paris.lutece.plugins.carto.web;

import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.web.cdi.mvc.Models;
import fr.paris.lutece.util.url.UrlItem;
import fr.paris.lutece.util.html.AbstractPaginator;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import fr.paris.lutece.plugins.carto.business.Basemap;
import fr.paris.lutece.plugins.carto.business.BasemapHome;

/**
 * This class provides the user interface to manage Basemap features ( manage, create, modify, remove )
 */
@SessionScoped
@Named
@Controller( controllerJsp = "ManageBasemaps.jsp", controllerPath = "jsp/admin/plugins/carto/", right = "CARTO_MANAGEMENT", securityTokenEnabled = true )
public class BasemapJspBean extends AbstractManageCartoJspBean<Integer, Basemap>
{
    // Templates
    private static final String TEMPLATE_MANAGE_BASEMAPS = "/admin/plugins/carto/manage_basemaps.html";
    private static final String TEMPLATE_CREATE_BASEMAP = "/admin/plugins/carto/create_basemap.html";
    private static final String TEMPLATE_MODIFY_BASEMAP = "/admin/plugins/carto/modify_basemap.html";

    // Parameters
    private static final String PARAMETER_ID_BASEMAP = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_BASEMAPS = "carto.manage_basemaps.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_BASEMAP = "carto.modify_basemap.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_BASEMAP = "carto.create_basemap.pageTitle";

    // Markers
    private static final String MARK_BASEMAP_LIST = "basemap_list";
    private static final String MARK_BASEMAP = "basemap";

    private static final String JSP_MANAGE_BASEMAPS = "jsp/admin/plugins/carto/ManageBasemaps.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_BASEMAP = "carto.message.confirmRemoveBasemap";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "carto.model.entity.basemap.attribute.";

    // Views
    private static final String VIEW_MANAGE_BASEMAPS = "manageBasemaps";
    private static final String VIEW_CREATE_BASEMAP = "createBasemap";
    private static final String VIEW_MODIFY_BASEMAP = "modifyBasemap";

    // Actions
    private static final String ACTION_CREATE_BASEMAP = "createBasemap";
    private static final String ACTION_MODIFY_BASEMAP = "modifyBasemap";
    private static final String ACTION_REMOVE_BASEMAP = "removeBasemap";
    private static final String ACTION_CONFIRM_REMOVE_BASEMAP = "confirmRemoveBasemap";

    // Infos
    private static final String INFO_BASEMAP_CREATED = "carto.info.basemap.created";
    private static final String INFO_BASEMAP_UPDATED = "carto.info.basemap.updated";
    private static final String INFO_BASEMAP_REMOVED = "carto.info.basemap.removed";

    // Errors
    private static final String ERROR_RESOURCE_NOT_FOUND = "Resource not found";

    // Session variable to store working values
    private Basemap _basemap;
    private List<Integer> _listIdBasemaps;

    @Inject
    private Models model;
    
    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_BASEMAPS, defaultView = true )
    public String getManageBasemaps( HttpServletRequest request )
    {
        _basemap = null;

        if ( request.getParameter( AbstractPaginator.PARAMETER_PAGE_INDEX ) == null || _listIdBasemaps.isEmpty( ) )
        {
            _listIdBasemaps = BasemapHome.getIdBasemapsList( );
        }

        Map<String, Object> model = getPaginatedListModel( request, MARK_BASEMAP_LIST, _listIdBasemaps, JSP_MANAGE_BASEMAPS );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_BASEMAPS, TEMPLATE_MANAGE_BASEMAPS, model );
    }

    /**
     * Get Items from Ids list
     * 
     * @param listIds
     * @return the populated list of items corresponding to the id List
     */
    @Override
    List<Basemap> getItemsFromIds( List<Integer> listIds )
    {
        List<Basemap> listBasemap = BasemapHome.getBasemapsListByIds( listIds );

        // keep original order
        return listBasemap.stream( ).sorted( Comparator.comparingInt( notif -> listIds.indexOf( notif.getId( ) ) ) ).collect( Collectors.toList( ) );
    }

    /**
     * reset the _listIdBasemaps list
     */
    public void resetListId( )
    {
        _listIdBasemaps = new ArrayList<>( );
    }

    /**
     * Returns the form to create a basemap
     *
     * @param request
     *            The Http request
     * @return the html code of the basemap form
     */
    @View( VIEW_CREATE_BASEMAP )
    public String getCreateBasemap( HttpServletRequest request )
    {
        _basemap = ( _basemap != null ) ? _basemap : new Basemap( );

        model.put( MARK_BASEMAP, _basemap );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_BASEMAP, TEMPLATE_CREATE_BASEMAP, model );
    }

    /**
     * Process the data capture form of a new basemap
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_CREATE_BASEMAP )
    public String doCreateBasemap( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _basemap, request, getLocale( ) );

        // Check constraints
        if ( !validateBean( _basemap, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_BASEMAP );
        }

        BasemapHome.create( _basemap );
        addInfo( INFO_BASEMAP_CREATED, getLocale( ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_BASEMAPS );
    }

    /**
     * Manages the removal form of a basemap whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( value = ACTION_CONFIRM_REMOVE_BASEMAP, securityTokenAction = ACTION_REMOVE_BASEMAP )
    public String getConfirmRemoveBasemap( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_BASEMAP ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_BASEMAP ) );
        url.addParameter( PARAMETER_ID_BASEMAP, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_BASEMAP, null, null, url.getUrl( ), null, AdminMessage.TYPE_CONFIRMATION, null, JSP_MANAGE_BASEMAPS );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a basemap
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage basemaps
     */
    @Action( ACTION_REMOVE_BASEMAP )
    public String doRemoveBasemap( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_BASEMAP ) );

        BasemapHome.remove( nId );
        addInfo( INFO_BASEMAP_REMOVED, getLocale( ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_BASEMAPS );
    }

    /**
     * Returns the form to update info about a basemap
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_BASEMAP )
    public String getModifyBasemap( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_BASEMAP ) );

        if ( _basemap == null || ( _basemap.getId( ) != nId ) )
        {
            Optional<Basemap> optBasemap = BasemapHome.findByPrimaryKey( nId );
            _basemap = optBasemap.orElseThrow( ( ) -> new AppException( ERROR_RESOURCE_NOT_FOUND ) );
        }

        model.put( MARK_BASEMAP, _basemap );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_BASEMAP, TEMPLATE_MODIFY_BASEMAP, model );
    }

    /**
     * Process the change form of a basemap
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_MODIFY_BASEMAP )
    public String doModifyBasemap( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _basemap, request, getLocale( ) );

        // Check constraints
        if ( !validateBean( _basemap, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_BASEMAP, PARAMETER_ID_BASEMAP, _basemap.getId( ) );
        }

        BasemapHome.update( _basemap );
        addInfo( INFO_BASEMAP_UPDATED, getLocale( ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_BASEMAPS );
    }
}
