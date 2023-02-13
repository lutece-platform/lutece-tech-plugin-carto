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
import fr.paris.lutece.plugins.carto.business.Coordonnee;
import fr.paris.lutece.plugins.carto.business.CoordonneeHome;

/**
 * This class provides the user interface to manage Coordonnee features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageCoordonnees.jsp", controllerPath = "jsp/admin/plugins/carto/", right = "CARTO_MANAGEMENT" )
public class CoordonneeJspBean extends AbstractManageCartoJspBean <Integer, Coordonnee>
{
    // Templates
    private static final String TEMPLATE_MANAGE_COORDONNEES = "/admin/plugins/carto/manage_coordonnees.html";
    private static final String TEMPLATE_CREATE_COORDONNEE = "/admin/plugins/carto/create_coordonnee.html";
    private static final String TEMPLATE_MODIFY_COORDONNEE = "/admin/plugins/carto/modify_coordonnee.html";

    // Parameters
    private static final String PARAMETER_ID_COORDONNEE = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_COORDONNEES = "carto.manage_coordonnees.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_COORDONNEE = "carto.modify_coordonnee.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_COORDONNEE = "carto.create_coordonnee.pageTitle";

    // Markers
    private static final String MARK_COORDONNEE_LIST = "coordonnee_list";
    private static final String MARK_COORDONNEE = "coordonnee";

    private static final String JSP_MANAGE_COORDONNEES = "jsp/admin/plugins/carto/ManageCoordonnees.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_COORDONNEE = "carto.message.confirmRemoveCoordonnee";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "carto.model.entity.coordonnee.attribute.";

    // Views
    private static final String VIEW_MANAGE_COORDONNEES = "manageCoordonnees";
    private static final String VIEW_CREATE_COORDONNEE = "createCoordonnee";
    private static final String VIEW_MODIFY_COORDONNEE = "modifyCoordonnee";

    // Actions
    private static final String ACTION_CREATE_COORDONNEE = "createCoordonnee";
    private static final String ACTION_MODIFY_COORDONNEE = "modifyCoordonnee";
    private static final String ACTION_REMOVE_COORDONNEE = "removeCoordonnee";
    private static final String ACTION_CONFIRM_REMOVE_COORDONNEE = "confirmRemoveCoordonnee";

    // Infos
    private static final String INFO_COORDONNEE_CREATED = "carto.info.coordonnee.created";
    private static final String INFO_COORDONNEE_UPDATED = "carto.info.coordonnee.updated";
    private static final String INFO_COORDONNEE_REMOVED = "carto.info.coordonnee.removed";
    
    // Errors
    private static final String ERROR_RESOURCE_NOT_FOUND = "Resource not found";
    
    // Session variable to store working values
    private Coordonnee _coordonnee;
    private List<Integer> _listIdCoordonnees;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_COORDONNEES, defaultView = true )
    public String getManageCoordonnees( HttpServletRequest request )
    {
        _coordonnee = null;
        
        if ( request.getParameter( AbstractPaginator.PARAMETER_PAGE_INDEX) == null || _listIdCoordonnees.isEmpty( ) )
        {
        	_listIdCoordonnees = CoordonneeHome.getIdCoordonneesList(  );
        }
        
        Map<String, Object> model = getPaginatedListModel( request, MARK_COORDONNEE_LIST, _listIdCoordonnees, JSP_MANAGE_COORDONNEES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_COORDONNEES, TEMPLATE_MANAGE_COORDONNEES, model );
    }

	/**
     * Get Items from Ids list
     * @param listIds
     * @return the populated list of items corresponding to the id List
     */
	@Override
	List<Coordonnee> getItemsFromIds( List<Integer> listIds ) 
	{
		List<Coordonnee> listCoordonnee = CoordonneeHome.getCoordonneesListByIds( listIds );
		
		// keep original order
        return listCoordonnee.stream()
                 .sorted(Comparator.comparingInt( notif -> listIds.indexOf( notif.getId())))
                 .collect(Collectors.toList());
	}
    
    /**
    * reset the _listIdCoordonnees list
    */
    public void resetListId( )
    {
    	_listIdCoordonnees = new ArrayList<>( );
    }

    /**
     * Returns the form to create a coordonnee
     *
     * @param request The Http request
     * @return the html code of the coordonnee form
     */
    @View( VIEW_CREATE_COORDONNEE )
    public String getCreateCoordonnee( HttpServletRequest request )
    {
        _coordonnee = ( _coordonnee != null ) ? _coordonnee : new Coordonnee(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_COORDONNEE, _coordonnee );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_CREATE_COORDONNEE ) );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_COORDONNEE, TEMPLATE_CREATE_COORDONNEE, model );
    }

    /**
     * Process the data capture form of a new coordonnee
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_CREATE_COORDONNEE )
    public String doCreateCoordonnee( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _coordonnee, request, getLocale( ) );
        

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_CREATE_COORDONNEE ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _coordonnee, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_COORDONNEE );
        }

        CoordonneeHome.create( _coordonnee );
        addInfo( INFO_COORDONNEE_CREATED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_COORDONNEES );
    }

    /**
     * Manages the removal form of a coordonnee whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_COORDONNEE )
    public String getConfirmRemoveCoordonnee( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_COORDONNEE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_COORDONNEE ) );
        url.addParameter( PARAMETER_ID_COORDONNEE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_COORDONNEE, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a coordonnee
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage coordonnees
     */
    @Action( ACTION_REMOVE_COORDONNEE )
    public String doRemoveCoordonnee( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_COORDONNEE ) );
        
        
        CoordonneeHome.remove( nId );
        addInfo( INFO_COORDONNEE_REMOVED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_COORDONNEES );
    }

    /**
     * Returns the form to update info about a coordonnee
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_COORDONNEE )
    public String getModifyCoordonnee( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_COORDONNEE ) );

        if ( _coordonnee == null || ( _coordonnee.getId(  ) != nId ) )
        {
            Optional<Coordonnee> optCoordonnee = CoordonneeHome.findByPrimaryKey( nId );
            _coordonnee = optCoordonnee.orElseThrow( ( ) -> new AppException(ERROR_RESOURCE_NOT_FOUND ) );
        }


        Map<String, Object> model = getModel(  );
        model.put( MARK_COORDONNEE, _coordonnee );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_MODIFY_COORDONNEE ) );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_COORDONNEE, TEMPLATE_MODIFY_COORDONNEE, model );
    }

    /**
     * Process the change form of a coordonnee
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_MODIFY_COORDONNEE )
    public String doModifyCoordonnee( HttpServletRequest request ) throws AccessDeniedException
    {   
        populate( _coordonnee, request, getLocale( ) );
		
		
        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_MODIFY_COORDONNEE ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _coordonnee, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_COORDONNEE, PARAMETER_ID_COORDONNEE, _coordonnee.getId( ) );
        }

        CoordonneeHome.update( _coordonnee );
        addInfo( INFO_COORDONNEE_UPDATED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_COORDONNEES );
    }
}
