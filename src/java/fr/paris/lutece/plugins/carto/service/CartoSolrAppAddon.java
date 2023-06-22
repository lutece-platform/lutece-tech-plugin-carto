package fr.paris.lutece.plugins.carto.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.carto.business.BasemapHome;
import fr.paris.lutece.plugins.carto.business.DataLayer;
import fr.paris.lutece.plugins.carto.business.DataLayerHome;
import fr.paris.lutece.plugins.carto.business.DataLayerMapTemplate;
import fr.paris.lutece.plugins.carto.business.DataLayerMapTemplateHome;
import fr.paris.lutece.plugins.carto.business.MapTemplate;
import fr.paris.lutece.plugins.carto.business.MapTemplateHome;
import fr.paris.lutece.plugins.search.solr.business.SolrSearchAppConf;
import fr.paris.lutece.plugins.search.solr.business.SolrSearchEngine;
import fr.paris.lutece.plugins.search.solr.business.SolrSearchResult;
import fr.paris.lutece.plugins.search.solr.service.ISolrSearchAppAddOn;
import fr.paris.lutece.plugins.search.solr.service.SolrSearchAppConfService;

public class CartoSolrAppAddon implements ISolrSearchAppAddOn {

	private static final String MARK_ENTRY_TYPE_SERVICE = "entryTypeService";
    private static final String MARK_BASEMAP = "basemap";
    private String _strEntryServiceName = StringUtils.EMPTY;
    
 // Parameters
    private static final String PARAMETER_ID_COORDONNEE = "id";
    private static final String PARAMETER_SOLR_GEOJSON = "DataLayer_text";
	
	@Override
	public void buildPageAddOn(Map<String, Object> model, HttpServletRequest request) {
		
		String[] fq = request.getParameterValues("fq");
		String fquery = request.getParameter("query");
		
        //Field mapProvider = entry.getFieldByCode( "provider" );
        
        //Charger map par defaut.
        //MapTemplate map = MapTemplateHome.findByPrimaryKey( Integer.valueOf( mapProvider.getValue( ) )  ).get( ) ;
		MapTemplate map = MapTemplateHome.findXpageFrontOffice( ).get( ) ;
        //List<Coordonnee> listCoordonnees = CoordonneeHome.getCoordonneesList(  );
        
        SolrSearchEngine engine = SolrSearchEngine.getInstance( );
        
        // Use default conf if the requested one doesn't exist
        SolrSearchAppConf conf = SolrSearchAppConfService.loadConfiguration( "carto" );
        
        if ( conf == null )
        {
            // Use default conf if the requested one doesn't exist
            conf = SolrSearchAppConfService.loadConfiguration( null );
        }

        List<HashMap<String, Object>> points = new ArrayList<HashMap<String, Object>> ( );
        Optional<DataLayer> dataLayerEditable  = DataLayerHome.findDataLayerFromMapId( map.getId( ), true, false, false );
        //StringBuilder query = new StringBuilder("DataLayer_text:");
        StringBuilder query = new StringBuilder();
        if ( fq != null && fq.length > 0)
        {
        	query.append( fq[0] );
        	for ( int i=1; i<fq.length; i++)
        	for (String fqvalue : fq)
	        {
        		query.append( " OR " );
        		query.append( fqvalue );
	        	//query.append( " OR " );
	        }
        }

        	//List<SolrSearchResult> listResultsGeoloc = engine.getGeolocSearchResults( query.toString() + PARAMETER_SOLR_GEOJSON + ":" + datalayer.getSolrTag( ), null, 100 );
        	List<SolrSearchResult> listResultsGeoloc = engine.getGeolocSearchResults( query.toString(), null, 100 );
        	//Optional<DataLayerMapTemplate> dataLayerMapTemplate = DataLayerMapTemplateHome.findByIdMapKeyIdDataLayerKey( map.getId( ), datalayer.getId( ) );
        	points.addAll( CartographyService.getGeolocModel( listResultsGeoloc, null, null ) );

        //model.put( MARK_COORDONNEE_LIST, listCoordonnees );
        model.put( CartographyService.MARK_POINTS, points );
        model.put( CartographyService.MARK_MAP, map );
        model.put( MARK_BASEMAP, BasemapHome.findByPrimaryKey( Integer.valueOf( map.getMapBackground( ) ) ).get( ).getUrl( ) );
        if ( dataLayerEditable.isPresent( ) )
        {
        	model.put( CartographyService.MARK_LAYER_EDITABLE, dataLayerEditable.get( ) );
        }
		
	}

}
