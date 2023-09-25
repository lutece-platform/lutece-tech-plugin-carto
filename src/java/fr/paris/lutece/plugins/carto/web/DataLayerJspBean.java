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
import fr.paris.lutece.portal.service.spring.SpringContextService;
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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import fr.paris.lutece.plugins.carto.business.DataLayer;
import fr.paris.lutece.plugins.carto.business.DataLayerHome;
import fr.paris.lutece.plugins.carto.business.DataLayerMapTemplate;
import fr.paris.lutece.plugins.carto.business.DataLayerMapTemplateHome;
import fr.paris.lutece.plugins.carto.business.GeometryType;
import fr.paris.lutece.plugins.carto.business.GeometryTypeHome;
import fr.paris.lutece.plugins.carto.provider.IMarkerProvider;
import fr.paris.lutece.plugins.carto.provider.InfoMarker;

/**
 * This class provides the user interface to manage DataLayer features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageDataLayers.jsp", controllerPath = "jsp/admin/plugins/carto/", right = "CARTO_MANAGEMENT" )
public class DataLayerJspBean extends AbstractManageCartoJspBean <Integer, DataLayer>
{
    // Templates
    private static final String TEMPLATE_MANAGE_DATALAYERS = "/admin/plugins/carto/manage_datalayers.html";
    private static final String TEMPLATE_CREATE_DATALAYER = "/admin/plugins/carto/create_datalayer.html";
    private static final String TEMPLATE_MODIFY_DATALAYER = "/admin/plugins/carto/modify_datalayer.html";

    // Parameters
    private static final String PARAMETER_ID_DATALAYER = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_DATALAYERS = "carto.manage_datalayers.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_DATALAYER = "carto.modify_datalayer.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_DATALAYER = "carto.create_datalayer.pageTitle";

    // Markers
    private static final String MARK_DATALAYER_LIST = "datalayer_list";
    private static final String MARK_DATALAYER = "datalayer";
    private static final String MARK_REF_GEOMETRY_LIST = "list_geo_type";
    private static final String MARK_SOLR_MARKER_LIST = "list_solr_marker";

    private static final String JSP_MANAGE_DATALAYERS = "jsp/admin/plugins/carto/ManageDataLayers.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_DATALAYER = "carto.message.confirmRemoveDataLayer";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "carto.model.entity.datalayer.attribute.";

    // Views
    private static final String VIEW_MANAGE_DATALAYERS = "manageDataLayers";
    private static final String VIEW_CREATE_DATALAYER = "createDataLayer";
    private static final String VIEW_MODIFY_DATALAYER = "modifyDataLayer";

    // Actions
    private static final String ACTION_CREATE_DATALAYER = "createDataLayer";
    private static final String ACTION_MODIFY_DATALAYER = "modifyDataLayer";
    private static final String ACTION_REMOVE_DATALAYER = "removeDataLayer";
    private static final String ACTION_CONFIRM_REMOVE_DATALAYER = "confirmRemoveDataLayer";

    // Infos
    private static final String INFO_DATALAYER_CREATED = "carto.info.datalayer.created";
    private static final String INFO_DATALAYER_UPDATED = "carto.info.datalayer.updated";
    private static final String INFO_DATALAYER_REMOVED = "carto.info.datalayer.removed";
    
    // Errors
    private static final String ERROR_RESOURCE_NOT_FOUND = "Resource not found";
    private static final String ERROR_DATALAYER_REMOVED = "carto.manage_datalayer.dataLayerIsPresent";
    
    // Session variable to store working values
    private DataLayer _datalayer;
    private List<Integer> _listIdDataLayers;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_DATALAYERS, defaultView = true )
    public String getManageDataLayers( HttpServletRequest request )
    {
        _datalayer = null;
        
        if ( request.getParameter( AbstractPaginator.PARAMETER_PAGE_INDEX) == null || _listIdDataLayers.isEmpty( ) )
        {
        	_listIdDataLayers = DataLayerHome.getIdDataLayersList(  );
        }
        
        Map<String, Object> model = getPaginatedListModel( request, MARK_DATALAYER_LIST, _listIdDataLayers, JSP_MANAGE_DATALAYERS );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_DATALAYERS, TEMPLATE_MANAGE_DATALAYERS, model );
    }

	/**
     * Get Items from Ids list
     * @param listIds
     * @return the populated list of items corresponding to the id List
     */
	@Override
	List<DataLayer> getItemsFromIds( List<Integer> listIds ) 
	{
		List<DataLayer> listDataLayer = DataLayerHome.getDataLayersListByIds( listIds );
		
		// keep original order
        return listDataLayer.stream()
                 .sorted(Comparator.comparingInt( notif -> listIds.indexOf( notif.getId())))
                 .collect(Collectors.toList());
	}
    
    /**
    * reset the _listIdDataLayers list
    */
    public void resetListId( )
    {
    	_listIdDataLayers = new ArrayList<>( );
    }

    /**
     * Returns the form to create a datalayer
     *
     * @param request The Http request
     * @return the html code of the datalayer form
     */
    @View( VIEW_CREATE_DATALAYER )
    public String getCreateDataLayer( HttpServletRequest request )
    {
        _datalayer = ( _datalayer != null ) ? _datalayer : new DataLayer(  );

        ReferenceList refGeometry = GeometryTypeHome.getGeometryTypesReferenceList();
        
        Map<String, Object> model = getModel(  );
        model.put( MARK_REF_GEOMETRY_LIST, refGeometry);
        model.put( MARK_DATALAYER, _datalayer );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_CREATE_DATALAYER ) );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_DATALAYER, TEMPLATE_CREATE_DATALAYER, model );
    }

    /**
     * Process the data capture form of a new datalayer
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_CREATE_DATALAYER )
    public String doCreateDataLayer( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _datalayer, request, getLocale( ) );
        Optional<GeometryType> geoType = GeometryTypeHome.findByPrimaryKey( Integer.valueOf( request.getParameter( "geometry" ) ) );
		if ( geoType.isPresent( ) )
		{
			_datalayer.setGeometryType( geoType.get( ) );
		}

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_CREATE_DATALAYER ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _datalayer, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_DATALAYER );
        }

        DataLayerHome.create( _datalayer );
        addInfo( INFO_DATALAYER_CREATED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_DATALAYERS );
    }

    /**
     * Manages the removal form of a datalayer whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_DATALAYER )
    public String getConfirmRemoveDataLayer( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DATALAYER ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_DATALAYER ) );
        url.addParameter( PARAMETER_ID_DATALAYER, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_DATALAYER, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a datalayer
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage datalayers
     */
    @Action( ACTION_REMOVE_DATALAYER )
    public String doRemoveDataLayer( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DATALAYER ) );
        
        Optional<DataLayerMapTemplate> dataLayerMapTemplate = DataLayerMapTemplateHome.findByIdDataLayerKey( nId );
        if ( dataLayerMapTemplate.isPresent( ) )
        {
        	addError( ERROR_DATALAYER_REMOVED, getLocale( ) );
        	return redirect( request, VIEW_MANAGE_DATALAYERS, PARAMETER_ID_DATALAYER, nId);
        	
        }
        
        DataLayerHome.remove( nId );
        addInfo( INFO_DATALAYER_REMOVED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_DATALAYERS );
    }

    /**
     * Returns the form to update info about a datalayer
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_DATALAYER )
    public String getModifyDataLayer( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_DATALAYER ) );

        if ( _datalayer == null || ( _datalayer.getId(  ) != nId ) )
        {
            Optional<DataLayer> optDataLayer = DataLayerHome.findByPrimaryKey( nId );
            _datalayer = optDataLayer.orElseThrow( ( ) -> new AppException(ERROR_RESOURCE_NOT_FOUND ) );
        }

        List<IMarkerProvider> lstMarkerSolrList = SpringContextService.getBeansOfType( IMarkerProvider.class );
        
        ReferenceList refGeometry = GeometryTypeHome.getGeometryTypesReferenceList();
        
        Map<String, Object> model = getModel(  );
        model.put( MARK_REF_GEOMETRY_LIST, refGeometry);
        model.put( MARK_DATALAYER, _datalayer );
        if ( !lstMarkerSolrList.isEmpty( ) )
        	model.put( MARK_SOLR_MARKER_LIST , lstMarkerSolrList.get(0).provideMarkerDescriptions( _datalayer.getSolrTag( ), request ) );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_MODIFY_DATALAYER ) );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_DATALAYER, TEMPLATE_MODIFY_DATALAYER, model );
    }

    /**
     * Process the change form of a datalayer
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_MODIFY_DATALAYER )
    public String doModifyDataLayer( HttpServletRequest request ) throws AccessDeniedException
    {   
        populate( _datalayer, request, getLocale( ) );
        Optional<GeometryType> geoType = GeometryTypeHome.findByPrimaryKey( Integer.valueOf( request.getParameter( "geometry" ) ) );
		if ( geoType.isPresent( ) )
		{
			_datalayer.setGeometryType( geoType.get( ) );
		}
		
        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_MODIFY_DATALAYER ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _datalayer, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_DATALAYER, PARAMETER_ID_DATALAYER, _datalayer.getId( ) );
        }

        DataLayerHome.update( _datalayer );
        addInfo( INFO_DATALAYER_UPDATED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_DATALAYERS );
    }
}
