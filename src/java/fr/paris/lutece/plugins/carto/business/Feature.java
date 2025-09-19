package fr.paris.lutece.plugins.carto.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Feature {
	/*
    private Geometry geometry;

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
    */
    //private List<Double> coordinates;
    private List<Double> _pointCoord;
    private List<List<List<Double>>> _polygonCoord;
    private List<List<Double>> _polylineCoord;
    public static final String PATH_GEOMETRY_COORDINATES = "coordinates";
    public static final String PATH_GEOMETRY = "geometry";
    public static final String PATH_GEOMETRY_TYPE = "type";
    public static final String PATH_TYPE = "type";
    private String _typegeometry;
	
    @JsonProperty( PATH_GEOMETRY )
    public Map<String, Object> getGeometry(  )
    {
        HashMap<String, Object> geometry = new HashMap<String, Object>(  );
        geometry.put( PATH_GEOMETRY_TYPE, _typegeometry );
        if ("Point".equals(_typegeometry))
        {
        	geometry.put( PATH_GEOMETRY_COORDINATES, _pointCoord );
        }
        else if ("Polygon".equals(_typegeometry))
        {
        	geometry.put( PATH_GEOMETRY_COORDINATES, _polygonCoord );
        }
        else if ("Polyline".equals(_typegeometry))
        {
        	geometry.put( PATH_GEOMETRY_COORDINATES, _polylineCoord );
        }

        return geometry;
    }

    //public void setCoordinates(List<Double> coordinates) {
    @JsonProperty( PATH_GEOMETRY )
    public void setGeometry( Map<String, Object> coordinates )
    {
        //this.coordinates = coordinates;
    	_typegeometry = (String) coordinates.get( PATH_GEOMETRY_TYPE );
	    if ("Point".equals(_typegeometry))
	    {
	    	_pointCoord = (List<Double>) coordinates.get( PATH_GEOMETRY_COORDINATES );
	    } else if ("Polygon".equals(_typegeometry))
	    {
	    	_polygonCoord = ( List<List<List<Double>>> ) coordinates.get( PATH_GEOMETRY_COORDINATES );
	    } else if ("Polyline".equals(_typegeometry))
	    {
	    	_polylineCoord = ( List<List<Double>> ) coordinates.get( PATH_GEOMETRY_COORDINATES );
	    }
    }
    
    /**
     * Returns the Type
     *
     * @return The type
     */
    @JsonProperty( PATH_GEOMETRY_TYPE )
    public String getType(  )
    {
        return _typegeometry;
    }
    
    /**
     * Returns the Type
     *
     * @return The type
     */
    @JsonProperty( PATH_GEOMETRY_TYPE )
	public String setType(String strType) {
		return _typegeometry = strType;
	}
}
