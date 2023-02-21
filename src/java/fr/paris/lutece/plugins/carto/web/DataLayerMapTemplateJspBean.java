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

import fr.paris.lutece.plugins.carto.business.DataLayerHome;
import fr.paris.lutece.plugins.carto.business.DataLayerMapTemplate;
import fr.paris.lutece.plugins.carto.business.DataLayerMapTemplateHome;
import fr.paris.lutece.plugins.carto.business.DataLayerTypeHome;
import fr.paris.lutece.plugins.carto.business.MapTemplateHome;

/**
 * This class provides the user interface to manage DataLayerMapTemplate features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageDataLayerMapTemplates.jsp", controllerPath = "jsp/admin/plugins/carto/", right = "CARTO_MANAGEMENT" )
public class DataLayerMapTemplateJspBean extends AbstractManageCartoJspBean <Integer, DataLayerMapTemplate>
{
    // Templates
    private static final String TEMPLATE_MANAGE_DATALAYERMAPTEMPLATES = "/admin/plugins/carto/manage_datalayermaptemplates.html";
    private static final String TEMPLATE_CREATE_DATALAYERMAPTEMPLATE = "/admin/plugins/carto/create_datalayermaptemplate.html";
    private static final String TEMPLATE_MODIFY_DATALAYERMAPTEMPLATE = "/admin/plugins/carto/modify_datalayermaptemplate.html";

    // Parameters
    private static final String PARAMETER_ID_DATALAYERMAPTEMPLATE = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_DATALAYERMAPTEMPLATES = "carto.manage_datalayermaptemplates.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_DATALAYERMAPTEMPLATE = "carto.modify_datalayermaptemplate.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_DATALAYERMAPTEMPLATE = "carto.create_datalayermaptemplate.pageTitle";

    // Markers
    private static final String MARK_DATALAYERMAPTEMPLATE_LIST = "datalayermaptemplate_list";
    private static final String MARK_DATALAYERMAPTEMPLATE = "datalayermaptemplate";
    private static final String MARK_REF_MAP_TEMPLATE = "reflist_map_template";
    private static final String MARK_REF_DATA_LAYER = "reflist_data_layer";
    private static final String MARK_REF_DATA_LAYER_TYPE = "reflist_data_layer_type";

    private static final String JSP_MANAGE_DATALAYERMAPTEMPLATES = "jsp/admin/plugins/carto/ManageDataLayerMapTemplates.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_DATALAYERMAPTEMPLATE = "carto.message.confirmRemoveDataLayerMapTemplate";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "carto.model.entity.datalayermaptemplate.attribute.";

    // Views
    private static final String VIEW_MANAGE_DATALAYERMAPTEMPLATES = "manageDataLayerMapTemplates";
    private static final String VIEW_CREATE_DATALAYERMAPTEMPLATE = "createDataLayerMapTemplate";
    private static final String VIEW_MODIFY_DATALAYERMAPTEMPLATE = "modifyDataLayerMapTemplate";

    // Actions
    private static final String ACTION_CREATE_DATALAYERMAPTEMPLATE = "createDataLayerMapTemplate";
    private static final String ACTION_MODIFY_DATALAYERMAPTEMPLATE = "modifyDataLayerMapTemplate";
    private static final String ACTION_REMOVE_DATALAYERMAPTEMPLATE = "removeDataLayerMapTemplate";
    private static final String ACTION_CONFIRM_REMOVE_DATALAYERMAPTEMPLATE = "confirmRemoveDataLayerMapTemplate";

    // Infos
    private static final String INFO_DATALAYERMAPTEMPLATE_CREATED = "carto.info.datalayermaptemplate.created";
    private static final String INFO_DATALAYERMAPTEMPLATE_UPDATED = "carto.info.datalayermaptemplate.updated";
    private static final String INFO_DATALAYERMAPTEMPLATE_REMOVED = "carto.info.datalayermaptemplate.removed";
    
    // Errors
    private static final String ERROR_RESOURCE_NOT_FOUND = "Resource not found";
    
    // Session variable to store working values
    private DataLayerMapTemplate _datalayermaptemplate;
    private List<Integer> _listIdDataLayerMapTemplates;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_DATALAYERMAPTEMPLATES, defaultView = true )
    public String getManageDataLayerMapTemplates( HttpServletRequest request )
    {
        _datalayermaptemplate = null;
        
        if ( request.getParameter( AbstractPaginator.PARAMETER_PAGE_INDEX) == null || _listIdDataLayerMapTemplates.isEmpty( ) )
        {
        	_listIdDataLayerMapTemplates = DataLayerMapTemplateHome.getIdDataLayerMapTemplatesList(  );
        }
        
        Map<String, Object> model = getPaginatedListModel( request, MARK_DATALAYERMAPTEMPLATE_LIST, _listIdDataLayerMapTemplates, JSP_MANAGE_DATALAYERMAPTEMPLATES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_DATALAYERMAPTEMPLATES, TEMPLATE_MANAGE_DATALAYERMAPTEMPLATES, model );
    }

	/**
     * Get Items from Ids list
     * @param listIds
     * @return the populated list of items corresponding to the id List
     */
	@Override
	List<DataLayerMapTemplate> getItemsFromIds( List<Integer> listIds ) 
	{
		List<DataLayerMapTemplate> listDataLayerMapTemplate = DataLayerMapTemplateHome.getDataLayerMapTemplatesListByIds( listIds );
		
		// keep original order
        return listDataLayerMapTemplate.stream()
                 .sorted(Comparator.comparingInt( notif -> listIds.indexOf( notif.getId())))
                 .collect(Collectors.toList());
	}
    
    /**
    * reset the _listIdDataLayerMapTemplates list
    */
    public void resetListId( )
    {
    	_listIdDataLayerMapTemplates = new ArrayList<>( );
    }

    /**
     * Returns the form to create a datalayermaptemplate
     *
     * @param request The Http request
     * @return the html code of the datalayermaptemplate form
     */
    @View( VIEW_CREATE_DATALAYERMAPTEMPLATE )
    public String getCreateDataLayerMapTemplate( HttpServletRequest request )
    {
        _datalayermaptemplate = ( _datalayermaptemplate != null ) ? _datalayermaptemplate : new DataLayerMapTemplate(  );

        ReferenceList refDataLayer = DataLayerHome.getDataLayersReferenceList( );
        ReferenceList refMapTemplate = MapTemplateHome.getMapTemplatesReferenceList( );
        ReferenceList refDataLayerType = DataLayerTypeHome.getDataLayerTypesReferenceList( );
        
        Map<String, Object> model = getModel(  );
        model.put( MARK_REF_DATA_LAYER, refDataLayer );
        model.put( MARK_REF_MAP_TEMPLATE, refMapTemplate );
        model.put( MARK_DATALAYERMAPTEMPLATE, _datalayermaptemplate );
        model.put( MARK_REF_DATA_LAYER_TYPE, refDataLayerType); 
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_CREATE_DATALAYERMAPTEMPLATE ) );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_DATALAYERMAPTEMPLATE, TEMPLATE_CREATE_DATALAYERMAPTEMPLATE, model );
    }

    /**
     * Process the data capture form of a new datalayermaptemplate
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_CREATE_DATALAYERMAPTEMPLATE )
    public String doCreateDataLayerMapTemplate( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _datalayermaptemplate, request, getLocale( ) );
        

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_CREATE_DATALAYERMAPTEMPLATE ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _datalayermaptemplate, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_DATALAYERMAPTEMPLATE );
        }

        DataLayerMapTemplateHome.create( _datalayermaptemplate );
        addInfo( INFO_DATALAYERMAPTEMPLATE_CREATED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_DATALAYERMAPTEMPLATES );
    }

    /**
     * Manages the removal form of a datalayermaptemplate whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_DATALAYERMAPTEMPLATE )
    public String getConfirmRemoveDataLayerMapTemplate( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DATALAYERMAPTEMPLATE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_DATALAYERMAPTEMPLATE ) );
        url.addParameter( PARAMETER_ID_DATALAYERMAPTEMPLATE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_DATALAYERMAPTEMPLATE, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a datalayermaptemplate
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage datalayermaptemplates
     */
    @Action( ACTION_REMOVE_DATALAYERMAPTEMPLATE )
    public String doRemoveDataLayerMapTemplate( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DATALAYERMAPTEMPLATE ) );
        
        
        DataLayerMapTemplateHome.remove( nId );
        addInfo( INFO_DATALAYERMAPTEMPLATE_REMOVED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_DATALAYERMAPTEMPLATES );
    }

    /**
     * Returns the form to update info about a datalayermaptemplate
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_DATALAYERMAPTEMPLATE )
    public String getModifyDataLayerMapTemplate( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DATALAYERMAPTEMPLATE ) );

        if ( _datalayermaptemplate == null || ( _datalayermaptemplate.getId(  ) != nId ) )
        {
            Optional<DataLayerMapTemplate> optDataLayerMapTemplate = DataLayerMapTemplateHome.findByPrimaryKey( nId );
            _datalayermaptemplate = optDataLayerMapTemplate.orElseThrow( ( ) -> new AppException(ERROR_RESOURCE_NOT_FOUND ) );
        }


        ReferenceList refDataLayer = DataLayerHome.getDataLayersReferenceList( );
        ReferenceList refMapTemplate = MapTemplateHome.getMapTemplatesReferenceList( );
        ReferenceList refDataLayerType = DataLayerTypeHome.getDataLayerTypesReferenceList( );
        
        Map<String, Object> model = getModel(  );
        model.put( MARK_REF_DATA_LAYER, refDataLayer );
        model.put( MARK_REF_MAP_TEMPLATE, refMapTemplate );
        model.put( MARK_DATALAYERMAPTEMPLATE, _datalayermaptemplate );
        model.put( MARK_REF_DATA_LAYER_TYPE, refDataLayerType); 
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_MODIFY_DATALAYERMAPTEMPLATE ) );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_DATALAYERMAPTEMPLATE, TEMPLATE_MODIFY_DATALAYERMAPTEMPLATE, model );
    }

    /**
     * Process the change form of a datalayermaptemplate
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_MODIFY_DATALAYERMAPTEMPLATE )
    public String doModifyDataLayerMapTemplate( HttpServletRequest request ) throws AccessDeniedException
    {   
        populate( _datalayermaptemplate, request, getLocale( ) );
		
		
        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_MODIFY_DATALAYERMAPTEMPLATE ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _datalayermaptemplate, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_DATALAYERMAPTEMPLATE, PARAMETER_ID_DATALAYERMAPTEMPLATE, _datalayermaptemplate.getId( ) );
        }

        DataLayerMapTemplateHome.update( _datalayermaptemplate );
        addInfo( INFO_DATALAYERMAPTEMPLATE_UPDATED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_DATALAYERMAPTEMPLATES );
    }
}
