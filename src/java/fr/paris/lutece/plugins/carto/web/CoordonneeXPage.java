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

import fr.paris.lutece.plugins.carto.business.Coordonnee;
import fr.paris.lutece.plugins.carto.business.CoordonneeHome;
import fr.paris.lutece.plugins.forms.business.Form;
import fr.paris.lutece.plugins.forms.business.FormQuestionResponse;
import fr.paris.lutece.plugins.forms.business.FormResponse;
import fr.paris.lutece.plugins.forms.business.form.search.FormResponseSearchItem;
import fr.paris.lutece.plugins.forms.modules.solr.service.Utilities;
import fr.paris.lutece.plugins.leaflet.business.GeolocItem;
import fr.paris.lutece.plugins.leaflet.business.GeolocItemPolygon;
import fr.paris.lutece.plugins.leaflet.service.IconService;
import fr.paris.lutece.plugins.search.solr.business.SolrSearchAppConf;
import fr.paris.lutece.plugins.search.solr.business.SolrSearchEngine;
import fr.paris.lutece.plugins.search.solr.business.SolrSearchResult;
import fr.paris.lutece.plugins.search.solr.indexer.SolrIndexerService;
import fr.paris.lutece.plugins.search.solr.indexer.SolrItem;
import fr.paris.lutece.plugins.search.solr.service.SolrSearchAppConfService;
import fr.paris.lutece.plugins.search.solr.web.SolrIndexerJspBean;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.util.url.UrlItem;
import fr.paris.lutece.portal.service.message.SiteMessageService;
import fr.paris.lutece.portal.service.message.SiteMessage;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.security.SecurityTokenService;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppLogService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest; 


/**
 * This class provides the user interface to manage Coordonnee xpages ( manage, create, modify, remove )
 */
@Controller( xpageName = "coordonnee" , pageTitleI18nKey = "carto.xpage.coordonnee.pageTitle" , pagePathI18nKey = "carto.xpage.coordonnee.pagePathLabel" )
public class CoordonneeXPage extends MVCApplication
{
    // Templates
    private static final String TEMPLATE_MANAGE_COORDONNEES = "/skin/plugins/carto/manage_coordonnees.html";
    private static final String TEMPLATE_CREATE_COORDONNEE = "/skin/plugins/carto/create_coordonnee.html";
    private static final String TEMPLATE_MODIFY_COORDONNEE = "/skin/plugins/carto/modify_coordonnee.html";
    
    // Parameters
    private static final String PARAMETER_ID_COORDONNEE = "id";
    
    // Markers
    private static final String MARK_COORDONNEE_LIST = "coordonnee_list";
    private static final String MARK_COORDONNEE = "coordonnee";
    
    // Message
    private static final String MESSAGE_CONFIRM_REMOVE_COORDONNEE = "carto.message.confirmRemoveCoordonnee";
    
    // Views
    private static final String VIEW_MANAGE_COORDONNEES = "manageCoordonnees";
    private static final String VIEW_CREATE_COORDONNEE = "createCoordonnee";
    private static final String VIEW_MODIFY_COORDONNEE = "modifyCoordonnee";

    // Actions
    private static final String ACTION_CREATE_COORDONNEE = "createCoordonnee";
    private static final String ACTION_MODIFY_COORDONNEE = "modifyCoordonnee";
    private static final String ACTION_REMOVE_COORDONNEE = "removeCoordonnee";
    private static final String ACTION_CONFIRM_REMOVE_COORDONNEE = "confirmRemoveCoordonnee";
    private static final String ACTION_CREATE_NEW_POINT = "createPoint";

    // Infos
    private static final String INFO_COORDONNEE_CREATED = "carto.info.coordonnee.created";
    private static final String INFO_COORDONNEE_UPDATED = "carto.info.coordonnee.updated";
    private static final String INFO_COORDONNEE_REMOVED = "carto.info.coordonnee.removed";
    
    // Errors
    private static final String ERROR_RESOURCE_NOT_FOUND = "Resource not found";
    
    private static final String MARK_POINTS = "points";
    private static final String MARK_POINTS_GEOJSON = "geojson";
    private static final String MARK_POINTS_ID = "id";
    private static final String MARK_POINTS_FIELDCODE = "code";
    private static final String MARK_POINTS_TYPE = "type";
    
    // Session variable to store working values
    private Coordonnee _coordonnee;
    
    /**
     * return the form to manage coordonnees
     * @param request The Http request
     * @return the html code of the list of coordonnees
     */
    @View( value = VIEW_MANAGE_COORDONNEES, defaultView = true )
    public XPage getManageCoordonnees( HttpServletRequest request )
    {
        _coordonnee = null;
        List<Coordonnee> listCoordonnees = CoordonneeHome.getCoordonneesList(  );
        
        SolrSearchEngine engine = SolrSearchEngine.getInstance( );
        
     // Use default conf if the requested one doesn't exist
        SolrSearchAppConf conf = SolrSearchAppConfService.loadConfiguration( "conf" );
        
        if ( conf == null )
        {
            // Use default conf if the requested one doesn't exist
            conf = SolrSearchAppConfService.loadConfiguration( null );
        }
        
        List<HashMap<String, Object>> points = null;
        if ( !conf.getExtraMappingQuery( ) )
        {
            List<SolrSearchResult> listResultsGeoloc = engine.getGeolocSearchResults( "*:*", null, 100 );
            points = getGeolocModel( listResultsGeoloc );
        }

        
        Map<String, Object> model = getModel(  );
        model.put( MARK_COORDONNEE_LIST, listCoordonnees );
        model.put( MARK_POINTS, points );
        
        return getXPage( TEMPLATE_MANAGE_COORDONNEES, getLocale( request ), model );
    }
    
    /**
     * Returns a model with points data from a geoloc search
     * 
     * @param listResultsGeoloc
     *            the result of a search
     * @return the model
     */
    private static List<HashMap<String, Object>> getGeolocModel( List<SolrSearchResult> listResultsGeoloc )
    {
        List<HashMap<String, Object>> points = new ArrayList<>( listResultsGeoloc.size( ) );
        Map<String, String> iconKeysCache = new HashMap<>( );

        for ( SolrSearchResult result : listResultsGeoloc )
        {
            Map<String, Object> dynamicFields = result.getDynamicFields( );

            for ( Entry<String, Object> entry : dynamicFields.entrySet( ) )
            {
                if ( !entry.getKey( ).endsWith( SolrItem.DYNAMIC_GEOJSON_FIELD_SUFFIX ) )
                {
                    continue;
                }
                HashMap<String, Object> h = new HashMap<>( );
                String strJson = (String) entry.getValue( );
                GeolocItem geolocItem = null;

                try
                {
                    geolocItem = GeolocItem.fromJSON( strJson );
                }
                catch( IOException e )
                {
                    AppLogService.error( "SolrSearchApp: error parsing geoloc JSON: " + strJson + ", exception " + e );
                }

                //if ( geolocItem != null && geolocItem.getTypegeometry( ).equals( GeolocItem.VALUE_GEOMETRY_TYPE ) )
                if ( geolocItem != null )
                {
                    String strType = result.getId( ).substring( result.getId( ).lastIndexOf( '_' ) + 1 );
                    String strIcon;

                    if ( iconKeysCache.containsKey( geolocItem.getIcon( ) ) )
                    {
                        strIcon = iconKeysCache.get( geolocItem.getIcon( ) );
                    }
                    else
                    {
                        strIcon = IconService.getIcon( strType, geolocItem.getIcon( ) );
                        iconKeysCache.put( geolocItem.getIcon( ), strIcon );
                    }

                    geolocItem.setIcon( strIcon );
                    h.put( MARK_POINTS_GEOJSON, geolocItem.toJSON( ) );
                    h.put( MARK_POINTS_ID, result.getId( ).substring( result.getId( ).indexOf( '_' ) + 1, result.getId( ).lastIndexOf( '_' ) ) );
                    h.put( MARK_POINTS_FIELDCODE, entry.getKey( ).substring( 0, entry.getKey( ).lastIndexOf( '_' ) ) );
                    h.put( MARK_POINTS_TYPE, strType );
                    points.add( h );
                }
            }
        }
        return points;
    }
    
    /**
     * Process the data capture form of a new coordonnee
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_CREATE_NEW_POINT )
    public XPage doCreatePoint( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _coordonnee, request, getLocale( request ) );

		
        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_CREATE_COORDONNEE ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _coordonnee ) )
        {
            return redirectView( request, VIEW_CREATE_COORDONNEE );
        }

        CoordonneeHome.create( _coordonnee );
        addInfo( INFO_COORDONNEE_CREATED, getLocale( request ) );

        return redirectView( request, VIEW_MANAGE_COORDONNEES );
    }

    /**
     * Returns the form to create a coordonnee
     *
     * @param request The Http request
     * @return the html code of the coordonnee form
     */
    @View( VIEW_CREATE_COORDONNEE )
    public XPage getCreateCoordonnee( HttpServletRequest request )
    {
        _coordonnee = ( _coordonnee != null ) ? _coordonnee : new Coordonnee(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_COORDONNEE, _coordonnee );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_CREATE_COORDONNEE ) );

        return getXPage( TEMPLATE_CREATE_COORDONNEE, getLocale( request ), model );
    }

    /**
     * Process the data capture form of a new coordonnee
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_CREATE_COORDONNEE )
    public XPage doCreateCoordonnee( HttpServletRequest request ) throws AccessDeniedException
    {
    	//Point
    	if ( request.getParameter( "coordonnex" ) != null && !request.getParameter( "coordonnex" ).isEmpty( ) && request.getParameter( "coordonney" ) != null && !request.getParameter( "coordonney" ).isEmpty( ) )
    	{
	        double x = Double.valueOf( request.getParameter( "coordonnex" ) );
	        double y = Double.valueOf( request.getParameter( "coordonney" ) );
	        
	        String adresse = "";
	        
	        Coordonnee coord = new Coordonnee();
	        coord.setAdresse("");
	        coord.setCoordonneeX(x);
	        coord.setCoordonneeY(y);
	        
	        GeolocItem geolocItem = new GeolocItem( );
	        HashMap<String, Object> properties = new HashMap<>( );
	        properties.put( GeolocItem.PATH_PROPERTIES_ADDRESS, adresse );
	
	        HashMap<String, Object> geometry = new HashMap<>( );
	        geometry.put( GeolocItem.PATH_GEOMETRY_COORDINATES, Arrays.asList( x, y ) );
	        geometry.put( GeolocItem.PATH_GEOMETRY_TYPE, GeolocItem.VALUE_GEOMETRY_TYPE );
	        geolocItem.setGeometry( geometry );
	        geolocItem.setProperties( properties );
	        
	        Map<String, String> _dfGeojson = new HashMap<>( );;
	        
	        _dfGeojson.put( "point_geojson", geolocItem.toJSON( ) );
	        
	        coord.setGeoJson(geolocItem.toJSON( ));
	        CoordonneeHome.create(coord);
    	}
        
        //Polygon
        String coordPolygon = request.getParameter( "coordonnepolygon" );
        if ( coordPolygon != null && !coordPolygon.isEmpty( ) )
        {
	        String[] lstCoordPolygon = coordPolygon.split(";");
	        
	        GeolocItemPolygon geoPolygon = new GeolocItemPolygon();
	        List<List<Double>> polygonLonLoat = new ArrayList<>( );
	        
	        for (String coordPolygonXY : lstCoordPolygon )
	        {
	        	String [] coordPolygonXY2 = coordPolygonXY.split( "," );
	        	double polygonx = Double.valueOf( coordPolygonXY2[0] );
	            double polygony = Double.valueOf( coordPolygonXY2[1] );
	            polygonLonLoat.add( Arrays.asList( polygonx, polygony ) );
	        }
	        
	        
	        HashMap<String, Object> geometryPolygon = new HashMap<>( );
	        geometryPolygon.put( GeolocItem.PATH_GEOMETRY_COORDINATES, polygonLonLoat );
	        geoPolygon.setGeometry( geometryPolygon );
	        geoPolygon.setTypegeometry( GeolocItemPolygon.VALUE_GEOMETRY_TYPE_POLYGON );

	        
	        Map<String, String> _dfGeojsonPolygib = new HashMap<>( );;
	        
	        _dfGeojsonPolygib.put( "polygon_geojson", geoPolygon.toJSON( ) );
	        
	        Coordonnee coord = new Coordonnee();
	        coord.setAdresse("");
	        coord.setCoordonneeX(0.0);
	        coord.setCoordonneeY(0.0);
	        coord.setGeoJson(geoPolygon.toJSON( ));
	        CoordonneeHome.create(coord);
        }
        
        //Polyline
        String coordPolyline = request.getParameter( "coordonnepolyline" );
        if ( coordPolyline != null && !coordPolyline.isEmpty( ) )
        {
	        String[] lstCoordPolyline = coordPolyline.split(";");
	        
	        //GeolocItemPolygon geoPolygon = new GeolocItemPolygon();
	        List<List<Double>> polylineLonLoat = new ArrayList<>( );
	        
	        for (String coordPolylineXY : lstCoordPolyline )
	        {
	        	String [] coordPolylineXY2 = coordPolylineXY.split( "," );
	        	double polylinex = Double.valueOf( coordPolylineXY2[0] );
	            double polyliney = Double.valueOf( coordPolylineXY2[1] );
	            polylineLonLoat.add( Arrays.asList( polylinex, polyliney ) );
	        }
	        
	        
	        GeolocItem geolocItem = new GeolocItem( );
	        HashMap<String, Object> properties = new HashMap<>( );
	        properties.put( GeolocItem.PATH_PROPERTIES_ADDRESS, "" );
	
	        HashMap<String, Object> geometry = new HashMap<>( );
	        geometry.put( GeolocItem.PATH_GEOMETRY_COORDINATES, polylineLonLoat );
	        geometry.put( GeolocItem.PATH_GEOMETRY_TYPE, GeolocItem.VALUE_GEOMETRY_TYPE_POLYLINE );
	        geolocItem.setGeometry( geometry );
	        geolocItem.setProperties( properties );

	        
	        Map<String, String> _dfGeojsonPolygib = new HashMap<>( );;
	        
	        _dfGeojsonPolygib.put( "polyline_geojson", geolocItem.toJSON( ) );
	        
	        Coordonnee coord = new Coordonnee();
	        coord.setAdresse("");
	        coord.setCoordonneeX(0.0);
	        coord.setCoordonneeY(0.0);
	        coord.setGeoJson(geolocItem.toJSON( ));
	        CoordonneeHome.create(coord);
        }
        
        SolrIndexerService.processIndexing( true );

        return redirectView( request, VIEW_MANAGE_COORDONNEES );
    }

    /**
     * Manages the removal form of a coordonnee whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     * @throws fr.paris.lutece.portal.service.message.SiteMessageException {@link fr.paris.lutece.portal.service.message.SiteMessageException}
     */
    @Action( ACTION_CONFIRM_REMOVE_COORDONNEE )
    public XPage getConfirmRemoveCoordonnee( HttpServletRequest request ) throws SiteMessageException
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_COORDONNEE ) );

        UrlItem url = new UrlItem( getActionFullUrl( ACTION_REMOVE_COORDONNEE ) );
        url.addParameter( PARAMETER_ID_COORDONNEE, nId );
        
        SiteMessageService.setMessage( request, MESSAGE_CONFIRM_REMOVE_COORDONNEE, SiteMessage.TYPE_CONFIRMATION, url.getUrl(  ) );
        return null;
    }

    /**
     * Handles the removal form of a coordonnee
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage coordonnees
     */
    @Action( ACTION_REMOVE_COORDONNEE )
    public XPage doRemoveCoordonnee( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_COORDONNEE ) );
        CoordonneeHome.remove( nId );
        addInfo( INFO_COORDONNEE_REMOVED, getLocale( request ) );

        return redirectView( request, VIEW_MANAGE_COORDONNEES );
    }

    /**
     * Returns the form to update info about a coordonnee
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_COORDONNEE )
    public XPage getModifyCoordonnee( HttpServletRequest request )
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

        return getXPage( TEMPLATE_MODIFY_COORDONNEE, getLocale( request ), model );
    }

    /**
     * Process the change form of a coordonnee
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_MODIFY_COORDONNEE )
    public XPage doModifyCoordonnee( HttpServletRequest request ) throws AccessDeniedException
    {     
        populate( _coordonnee, request, getLocale( request ) );
		

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_MODIFY_COORDONNEE ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _coordonnee ) )
        {
            return redirect( request, VIEW_MODIFY_COORDONNEE, PARAMETER_ID_COORDONNEE, _coordonnee.getId( ) );
        }

        CoordonneeHome.update( _coordonnee );
        addInfo( INFO_COORDONNEE_UPDATED, getLocale( request ) );

        return redirectView( request, VIEW_MANAGE_COORDONNEES );
    }
}
