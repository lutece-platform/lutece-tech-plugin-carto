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
import fr.paris.lutece.util.html.AbstractPaginator;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import fr.paris.lutece.plugins.carto.business.GeometryType;
import fr.paris.lutece.plugins.carto.business.GeometryTypeHome;

/**
 * This class provides the user interface to manage GeometryType features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageGeometryTypes.jsp", controllerPath = "jsp/admin/plugins/carto/", right = "CARTO_MANAGEMENT" )
public class GeometryTypeJspBean extends AbstractManageCartoJspBean <Integer, GeometryType>
{
    // Templates
    private static final String TEMPLATE_MANAGE_GEOMETRYTYPES = "/admin/plugins/carto/manage_geometrytypes.html";
    private static final String TEMPLATE_CREATE_GEOMETRYTYPE = "/admin/plugins/carto/create_geometrytype.html";
    private static final String TEMPLATE_MODIFY_GEOMETRYTYPE = "/admin/plugins/carto/modify_geometrytype.html";

    // Parameters
    private static final String PARAMETER_ID_GEOMETRYTYPE = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_GEOMETRYTYPES = "carto.manage_geometrytypes.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_GEOMETRYTYPE = "carto.modify_geometrytype.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_GEOMETRYTYPE = "carto.create_geometrytype.pageTitle";

    // Markers
    private static final String MARK_GEOMETRYTYPE_LIST = "geometrytype_list";
    private static final String MARK_GEOMETRYTYPE = "geometrytype";

    private static final String JSP_MANAGE_GEOMETRYTYPES = "jsp/admin/plugins/carto/ManageGeometryTypes.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_GEOMETRYTYPE = "carto.message.confirmRemoveGeometryType";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "carto.model.entity.geometrytype.attribute.";

    // Views
    private static final String VIEW_MANAGE_GEOMETRYTYPES = "manageGeometryTypes";
    private static final String VIEW_CREATE_GEOMETRYTYPE = "createGeometryType";
    private static final String VIEW_MODIFY_GEOMETRYTYPE = "modifyGeometryType";

    // Actions
    private static final String ACTION_CREATE_GEOMETRYTYPE = "createGeometryType";
    private static final String ACTION_MODIFY_GEOMETRYTYPE = "modifyGeometryType";
    private static final String ACTION_REMOVE_GEOMETRYTYPE = "removeGeometryType";
    private static final String ACTION_CONFIRM_REMOVE_GEOMETRYTYPE = "confirmRemoveGeometryType";

    // Infos
    private static final String INFO_GEOMETRYTYPE_CREATED = "carto.info.geometrytype.created";
    private static final String INFO_GEOMETRYTYPE_UPDATED = "carto.info.geometrytype.updated";
    private static final String INFO_GEOMETRYTYPE_REMOVED = "carto.info.geometrytype.removed";
    
    // Errors
    private static final String ERROR_RESOURCE_NOT_FOUND = "Resource not found";
    
    // Session variable to store working values
    private GeometryType _geometrytype;
    private List<Integer> _listIdGeometryTypes;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_GEOMETRYTYPES, defaultView = true )
    public String getManageGeometryTypes( HttpServletRequest request )
    {
        _geometrytype = null;
        
        if ( request.getParameter( AbstractPaginator.PARAMETER_PAGE_INDEX) == null || _listIdGeometryTypes.isEmpty( ) )
        {
        	_listIdGeometryTypes = GeometryTypeHome.getIdGeometryTypesList(  );
        }
        
        Map<String, Object> model = getPaginatedListModel( request, MARK_GEOMETRYTYPE_LIST, _listIdGeometryTypes, JSP_MANAGE_GEOMETRYTYPES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_GEOMETRYTYPES, TEMPLATE_MANAGE_GEOMETRYTYPES, model );
    }

	/**
     * Get Items from Ids list
     * @param listIds
     * @return the populated list of items corresponding to the id List
     */
	@Override
	List<GeometryType> getItemsFromIds( List<Integer> listIds ) 
	{
		List<GeometryType> listGeometryType = GeometryTypeHome.getGeometryTypesListByIds( listIds );
		
		// keep original order
        return listGeometryType.stream()
                 .sorted(Comparator.comparingInt( notif -> listIds.indexOf( notif.getId())))
                 .collect(Collectors.toList());
	}
    
    /**
    * reset the _listIdGeometryTypes list
    */
    public void resetListId( )
    {
    	_listIdGeometryTypes = new ArrayList<>( );
    }

    /**
     * Returns the form to create a geometrytype
     *
     * @param request The Http request
     * @return the html code of the geometrytype form
     */
    @View( VIEW_CREATE_GEOMETRYTYPE )
    public String getCreateGeometryType( HttpServletRequest request )
    {
        _geometrytype = ( _geometrytype != null ) ? _geometrytype : new GeometryType(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_GEOMETRYTYPE, _geometrytype );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_CREATE_GEOMETRYTYPE ) );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_GEOMETRYTYPE, TEMPLATE_CREATE_GEOMETRYTYPE, model );
    }

    /**
     * Process the data capture form of a new geometrytype
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_CREATE_GEOMETRYTYPE )
    public String doCreateGeometryType( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _geometrytype, request, getLocale( ) );
        

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_CREATE_GEOMETRYTYPE ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _geometrytype, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_GEOMETRYTYPE );
        }

        GeometryTypeHome.create( _geometrytype );
        addInfo( INFO_GEOMETRYTYPE_CREATED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_GEOMETRYTYPES );
    }

    /**
     * Manages the removal form of a geometrytype whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_GEOMETRYTYPE )
    public String getConfirmRemoveGeometryType( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_GEOMETRYTYPE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_GEOMETRYTYPE ) );
        url.addParameter( PARAMETER_ID_GEOMETRYTYPE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_GEOMETRYTYPE, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a geometrytype
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage geometrytypes
     */
    @Action( ACTION_REMOVE_GEOMETRYTYPE )
    public String doRemoveGeometryType( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_GEOMETRYTYPE ) );
        
        
        GeometryTypeHome.remove( nId );
        addInfo( INFO_GEOMETRYTYPE_REMOVED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_GEOMETRYTYPES );
    }

    /**
     * Returns the form to update info about a geometrytype
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_GEOMETRYTYPE )
    public String getModifyGeometryType( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_GEOMETRYTYPE ) );

        if ( _geometrytype == null || ( _geometrytype.getId(  ) != nId ) )
        {
            Optional<GeometryType> optGeometryType = GeometryTypeHome.findByPrimaryKey( nId );
            _geometrytype = optGeometryType.orElseThrow( ( ) -> new AppException(ERROR_RESOURCE_NOT_FOUND ) );
        }


        Map<String, Object> model = getModel(  );
        model.put( MARK_GEOMETRYTYPE, _geometrytype );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_MODIFY_GEOMETRYTYPE ) );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_GEOMETRYTYPE, TEMPLATE_MODIFY_GEOMETRYTYPE, model );
    }

    /**
     * Process the change form of a geometrytype
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_MODIFY_GEOMETRYTYPE )
    public String doModifyGeometryType( HttpServletRequest request ) throws AccessDeniedException
    {   
        populate( _geometrytype, request, getLocale( ) );
		
		
        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_MODIFY_GEOMETRYTYPE ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _geometrytype, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_GEOMETRYTYPE, PARAMETER_ID_GEOMETRYTYPE, _geometrytype.getId( ) );
        }

        GeometryTypeHome.update( _geometrytype );
        addInfo( INFO_GEOMETRYTYPE_UPDATED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_GEOMETRYTYPES );
    }
}
