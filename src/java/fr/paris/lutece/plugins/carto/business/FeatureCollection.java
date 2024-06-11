package fr.paris.lutece.plugins.carto.business;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureCollection {
    private List<Feature> features;

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    /*
    public List<Point> getPoints() {
        return features.stream()
                .map(Feature::getGeometry)
                .map(geometry -> new Point(geometry.getCoordinates().get(0), geometry.getCoordinates().get(1)))
                .toList();
    }
    */
    
    public List<Coordonnee> getPoints()
	{
		List<Coordonnee> lstCoordonnees = new ArrayList<>( );
		/*
    	for (Feature feature : features) {
			Coordonnee coord = new Coordonnee(feature.getGeometry().getCoordinates().get(0), feature.getGeometry().getCoordinates().get(1));
			lstCoordonnees.add(coord);
		}
    	*/
    	return lstCoordonnees;
	}
}
