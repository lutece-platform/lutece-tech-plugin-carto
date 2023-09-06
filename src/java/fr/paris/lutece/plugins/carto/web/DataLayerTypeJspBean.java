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
import fr.paris.lutece.plugins.carto.business.DataLayerType;
import fr.paris.lutece.plugins.carto.business.DataLayerTypeHome;

/**
 * This class provides the user interface to manage DataLayerType features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageDataLayerTypes.jsp", controllerPath = "jsp/admin/plugins/carto/", right = "CARTO_MANAGEMENT_REFERENTIEL" )
public class DataLayerTypeJspBean extends AbstractManageCartoJspBean <Integer, DataLayerType>
{
    // Templates
    private static final String TEMPLATE_MANAGE_DATALAYERTYPES = "/admin/plugins/carto/manage_datalayertypes.html";
    private static final String TEMPLATE_CREATE_DATALAYERTYPE = "/admin/plugins/carto/create_datalayertype.html";
    private static final String TEMPLATE_MODIFY_DATALAYERTYPE = "/admin/plugins/carto/modify_datalayertype.html";

    // Parameters
    private static final String PARAMETER_ID_DATALAYERTYPE = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_DATALAYERTYPES = "carto.manage_datalayertypes.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_DATALAYERTYPE = "carto.modify_datalayertype.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_DATALAYERTYPE = "carto.create_datalayertype.pageTitle";

    // Markers
    private static final String MARK_DATALAYERTYPE_LIST = "datalayertype_list";
    private static final String MARK_DATALAYERTYPE = "datalayertype";

    private static final String JSP_MANAGE_DATALAYERTYPES = "jsp/admin/plugins/carto/ManageDataLayerTypes.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_DATALAYERTYPE = "carto.message.confirmRemoveDataLayerType";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "carto.model.entity.datalayertype.attribute.";

    // Views
    private static final String VIEW_MANAGE_DATALAYERTYPES = "manageDataLayerTypes";
    private static final String VIEW_CREATE_DATALAYERTYPE = "createDataLayerType";
    private static final String VIEW_MODIFY_DATALAYERTYPE = "modifyDataLayerType";

    // Actions
    private static final String ACTION_CREATE_DATALAYERTYPE = "createDataLayerType";
    private static final String ACTION_MODIFY_DATALAYERTYPE = "modifyDataLayerType";
    private static final String ACTION_REMOVE_DATALAYERTYPE = "removeDataLayerType";
    private static final String ACTION_CONFIRM_REMOVE_DATALAYERTYPE = "confirmRemoveDataLayerType";

    // Infos
    private static final String INFO_DATALAYERTYPE_CREATED = "carto.info.datalayertype.created";
    private static final String INFO_DATALAYERTYPE_UPDATED = "carto.info.datalayertype.updated";
    private static final String INFO_DATALAYERTYPE_REMOVED = "carto.info.datalayertype.removed";
    
    // Errors
    private static final String ERROR_RESOURCE_NOT_FOUND = "Resource not found";
    
    // Session variable to store working values
    private DataLayerType _datalayertype;
    private List<Integer> _listIdDataLayerTypes;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_DATALAYERTYPES, defaultView = true )
    public String getManageDataLayerTypes( HttpServletRequest request )
    {
        _datalayertype = null;
        
        if ( request.getParameter( AbstractPaginator.PARAMETER_PAGE_INDEX) == null || _listIdDataLayerTypes.isEmpty( ) )
        {
        	_listIdDataLayerTypes = DataLayerTypeHome.getIdDataLayerTypesList(  );
        }
        
        Map<String, Object> model = getPaginatedListModel( request, MARK_DATALAYERTYPE_LIST, _listIdDataLayerTypes, JSP_MANAGE_DATALAYERTYPES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_DATALAYERTYPES, TEMPLATE_MANAGE_DATALAYERTYPES, model );
    }

	/**
     * Get Items from Ids list
     * @param listIds
     * @return the populated list of items corresponding to the id List
     */
	@Override
	List<DataLayerType> getItemsFromIds( List<Integer> listIds ) 
	{
		List<DataLayerType> listDataLayerType = DataLayerTypeHome.getDataLayerTypesListByIds( listIds );
		
		// keep original order
        return listDataLayerType.stream()
                 .sorted(Comparator.comparingInt( notif -> listIds.indexOf( notif.getId())))
                 .collect(Collectors.toList());
	}
    
    /**
    * reset the _listIdDataLayerTypes list
    */
    public void resetListId( )
    {
    	_listIdDataLayerTypes = new ArrayList<>( );
    }

    /**
     * Returns the form to create a datalayertype
     *
     * @param request The Http request
     * @return the html code of the datalayertype form
     */
    @View( VIEW_CREATE_DATALAYERTYPE )
    public String getCreateDataLayerType( HttpServletRequest request )
    {
        _datalayertype = ( _datalayertype != null ) ? _datalayertype : new DataLayerType(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_DATALAYERTYPE, _datalayertype );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_CREATE_DATALAYERTYPE ) );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_DATALAYERTYPE, TEMPLATE_CREATE_DATALAYERTYPE, model );
    }

    /**
     * Process the data capture form of a new datalayertype
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_CREATE_DATALAYERTYPE )
    public String doCreateDataLayerType( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _datalayertype, request, getLocale( ) );
        

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_CREATE_DATALAYERTYPE ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _datalayertype, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_DATALAYERTYPE );
        }

        DataLayerTypeHome.create( _datalayertype );
        addInfo( INFO_DATALAYERTYPE_CREATED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_DATALAYERTYPES );
    }

    /**
     * Manages the removal form of a datalayertype whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_DATALAYERTYPE )
    public String getConfirmRemoveDataLayerType( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DATALAYERTYPE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_DATALAYERTYPE ) );
        url.addParameter( PARAMETER_ID_DATALAYERTYPE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_DATALAYERTYPE, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a datalayertype
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage datalayertypes
     */
    @Action( ACTION_REMOVE_DATALAYERTYPE )
    public String doRemoveDataLayerType( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DATALAYERTYPE ) );
        
        
        DataLayerTypeHome.remove( nId );
        addInfo( INFO_DATALAYERTYPE_REMOVED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_DATALAYERTYPES );
    }

    /**
     * Returns the form to update info about a datalayertype
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_DATALAYERTYPE )
    public String getModifyDataLayerType( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DATALAYERTYPE ) );

        if ( _datalayertype == null || ( _datalayertype.getId(  ) != nId ) )
        {
            Optional<DataLayerType> optDataLayerType = DataLayerTypeHome.findByPrimaryKey( nId );
            _datalayertype = optDataLayerType.orElseThrow( ( ) -> new AppException(ERROR_RESOURCE_NOT_FOUND ) );
        }


        Map<String, Object> model = getModel(  );
        model.put( MARK_DATALAYERTYPE, _datalayertype );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_MODIFY_DATALAYERTYPE ) );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_DATALAYERTYPE, TEMPLATE_MODIFY_DATALAYERTYPE, model );
    }

    /**
     * Process the change form of a datalayertype
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_MODIFY_DATALAYERTYPE )
    public String doModifyDataLayerType( HttpServletRequest request ) throws AccessDeniedException
    {   
        populate( _datalayertype, request, getLocale( ) );
		
		
        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_MODIFY_DATALAYERTYPE ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _datalayertype, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_DATALAYERTYPE, PARAMETER_ID_DATALAYERTYPE, _datalayertype.getId( ) );
        }

        DataLayerTypeHome.update( _datalayertype );
        addInfo( INFO_DATALAYERTYPE_UPDATED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_DATALAYERTYPES );
    }
}
