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
import fr.paris.lutece.portal.service.security.SecurityTokenService;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.AbstractPaginator;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.carto.business.BasemapHome;
import fr.paris.lutece.plugins.carto.business.MapTemplate;
import fr.paris.lutece.plugins.carto.business.MapTemplateHome;

/**
 * This class provides the user interface to manage MapTemplate features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageMapTemplates.jsp", controllerPath = "jsp/admin/plugins/carto/", right = "CARTO_MANAGEMENT" )
public class MapTemplateJspBean extends AbstractManageCartoJspBean <Integer, MapTemplate>
{
    // Templates
    private static final String TEMPLATE_MANAGE_MAPTEMPLATES = "/admin/plugins/carto/manage_maptemplates.html";
    private static final String TEMPLATE_CREATE_MAPTEMPLATE = "/admin/plugins/carto/create_maptemplate.html";
    private static final String TEMPLATE_MODIFY_MAPTEMPLATE = "/admin/plugins/carto/modify_maptemplate.html";

    // Parameters
    private static final String PARAMETER_ID_MAPTEMPLATE = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_MAPTEMPLATES = "carto.manage_maptemplates.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_MAPTEMPLATE = "carto.modify_maptemplate.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_MAPTEMPLATE = "carto.create_maptemplate.pageTitle";

    // Markers
    private static final String MARK_MAPTEMPLATE_LIST = "maptemplate_list";
    private static final String MARK_MAPTEMPLATE = "maptemplate";
    private static final String MARK_MAP_PROVIDER_LIST = "mapprovider_list";

    private static final String JSP_MANAGE_MAPTEMPLATES = "jsp/admin/plugins/carto/ManageMapTemplates.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_MAPTEMPLATE = "carto.message.confirmRemoveMapTemplate";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "carto.model.entity.maptemplate.attribute.";

    // Views
    private static final String VIEW_MANAGE_MAPTEMPLATES = "manageMapTemplates";
    private static final String VIEW_CREATE_MAPTEMPLATE = "createMapTemplate";
    private static final String VIEW_MODIFY_MAPTEMPLATE = "modifyMapTemplate";

    // Actions
    private static final String ACTION_CREATE_MAPTEMPLATE = "createMapTemplate";
    private static final String ACTION_MODIFY_MAPTEMPLATE = "modifyMapTemplate";
    private static final String ACTION_REMOVE_MAPTEMPLATE = "removeMapTemplate";
    private static final String ACTION_CONFIRM_REMOVE_MAPTEMPLATE = "confirmRemoveMapTemplate";

    // Infos
    private static final String INFO_MAPTEMPLATE_CREATED = "carto.info.maptemplate.created";
    private static final String INFO_MAPTEMPLATE_UPDATED = "carto.info.maptemplate.updated";
    private static final String INFO_MAPTEMPLATE_REMOVED = "carto.info.maptemplate.removed";
    
    // Errors
    private static final String ERROR_RESOURCE_NOT_FOUND = "Resource not found";
    
    // Session variable to store working values
    private MapTemplate _maptemplate;
    private List<Integer> _listIdMapTemplates;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_MAPTEMPLATES, defaultView = true )
    public String getManageMapTemplates( HttpServletRequest request )
    {
        _maptemplate = null;
        
        if ( request.getParameter( AbstractPaginator.PARAMETER_PAGE_INDEX) == null || _listIdMapTemplates.isEmpty( ) )
        {
        	_listIdMapTemplates = MapTemplateHome.getIdMapTemplatesList(  );
        }
        
        Map<String, Object> model = getPaginatedListModel( request, MARK_MAPTEMPLATE_LIST, _listIdMapTemplates, JSP_MANAGE_MAPTEMPLATES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_MAPTEMPLATES, TEMPLATE_MANAGE_MAPTEMPLATES, model );
    }

	/**
     * Get Items from Ids list
     * @param listIds
     * @return the populated list of items corresponding to the id List
     */
	@Override
	List<MapTemplate> getItemsFromIds( List<Integer> listIds ) 
	{
		List<MapTemplate> listMapTemplate = MapTemplateHome.getMapTemplatesListByIds( listIds );
		
		// keep original order
        return listMapTemplate.stream()
                 .sorted(Comparator.comparingInt( notif -> listIds.indexOf( notif.getId())))
                 .collect(Collectors.toList());
	}
    
    /**
    * reset the _listIdMapTemplates list
    */
    public void resetListId( )
    {
    	_listIdMapTemplates = new ArrayList<>( );
    }

    /**
     * Returns the form to create a maptemplate
     *
     * @param request The Http request
     * @return the html code of the maptemplate form
     */
    @View( VIEW_CREATE_MAPTEMPLATE )
    public String getCreateMapTemplate( HttpServletRequest request )
    {
        _maptemplate = ( _maptemplate != null ) ? _maptemplate : new MapTemplate(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_MAPTEMPLATE, _maptemplate );
        model.put( MARK_MAP_PROVIDER_LIST, BasemapHome.getBasemapsReferenceList( ) );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_CREATE_MAPTEMPLATE ) );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_MAPTEMPLATE, TEMPLATE_CREATE_MAPTEMPLATE, model );
    }
    
    /**
     * Builds the {@link ReferenceList} of all available map providers
     * 
     * @return the {@link ReferenceList}
     */
    public ReferenceList getMapProvidersRefList( )
    {
        ReferenceList refList = new ReferenceList( );

        refList.addItem( StringUtils.EMPTY, StringUtils.EMPTY );

        /*
        for ( IMapProvider mapProvider : MapProviderManager.getMapProvidersList( ) )
        {
            refList.add( mapProvider.toRefItem( ) );
        }
        */

        return refList;
    }

    /**
     * Process the data capture form of a new maptemplate
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_CREATE_MAPTEMPLATE )
    public String doCreateMapTemplate( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _maptemplate, request, getLocale( ) );
        

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_CREATE_MAPTEMPLATE ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _maptemplate, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_MAPTEMPLATE );
        }

        MapTemplateHome.create( _maptemplate );
        addInfo( INFO_MAPTEMPLATE_CREATED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_MAPTEMPLATES );
    }

    /**
     * Manages the removal form of a maptemplate whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_MAPTEMPLATE )
    public String getConfirmRemoveMapTemplate( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_MAPTEMPLATE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_MAPTEMPLATE ) );
        url.addParameter( PARAMETER_ID_MAPTEMPLATE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_MAPTEMPLATE, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a maptemplate
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage maptemplates
     */
    @Action( ACTION_REMOVE_MAPTEMPLATE )
    public String doRemoveMapTemplate( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_MAPTEMPLATE ) );
        
        
        MapTemplateHome.remove( nId );
        addInfo( INFO_MAPTEMPLATE_REMOVED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_MAPTEMPLATES );
    }

    /**
     * Returns the form to update info about a maptemplate
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_MAPTEMPLATE )
    public String getModifyMapTemplate( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_MAPTEMPLATE ) );

        if ( _maptemplate == null || ( _maptemplate.getId(  ) != nId ) )
        {
            Optional<MapTemplate> optMapTemplate = MapTemplateHome.findByPrimaryKey( nId );
            _maptemplate = optMapTemplate.orElseThrow( ( ) -> new AppException(ERROR_RESOURCE_NOT_FOUND ) );
        }


        Map<String, Object> model = getModel(  );
        model.put( MARK_MAPTEMPLATE, _maptemplate );
        model.put( MARK_MAP_PROVIDER_LIST, BasemapHome.getBasemapsReferenceList( ) );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_MODIFY_MAPTEMPLATE ) );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_MAPTEMPLATE, TEMPLATE_MODIFY_MAPTEMPLATE, model );
    }

    /**
     * Process the change form of a maptemplate
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_MODIFY_MAPTEMPLATE )
    public String doModifyMapTemplate( HttpServletRequest request ) throws AccessDeniedException
    {   
        populate( _maptemplate, request, getLocale( ) );
		
		
        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_MODIFY_MAPTEMPLATE ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _maptemplate, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_MAPTEMPLATE, PARAMETER_ID_MAPTEMPLATE, _maptemplate.getId( ) );
        }

        MapTemplateHome.update( _maptemplate );
        addInfo( INFO_MAPTEMPLATE_UPDATED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_MAPTEMPLATES );
    }
}
