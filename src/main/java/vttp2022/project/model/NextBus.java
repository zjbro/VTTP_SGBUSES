package vttp2022.project.model;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;

import jakarta.json.JsonObject;

public class NextBus {
    private String estimatedArrival;
    // private String originCode;
    // private String destinationCode;
    // private String latitude;
    // private String longitude;
    // private String visitNumber;
    // private String load;
    // private String feature;
    // private String type;

    public static NextBus create(JsonObject o) throws ParseException{
        NextBus nextBus = new NextBus();
        Instant start = Instant.now();
        Instant end = Instant.parse(o.getString("EstimatedArrival"));
        Duration timeElapsed = Duration.between(start, end);
        nextBus.estimatedArrival = Long.toString(timeElapsed.toMillis()/60000); //convert to minutes from miliseconds
        
        // nextBus.originCode = o.getString("OriginCode");
        // nextBus.destinationCode = o.getString("DestinationCode");
        // nextBus.latitude = o.getString("Latitude");
        // nextBus.longitude = o.getString("Longitude");
        // nextBus.visitNumber = o.getString("VisitNumber");
        // nextBus.load = o.getString("Load");
        // nextBus.feature = o .getString("Feature");
        // nextBus.type = o.getString("Type");
        return nextBus;
    }

    @Override
	public String toString() {
		return estimatedArrival;
    }


    public String getEstimatedArrival() {
        return estimatedArrival;
    }
    public void setEstimatedArrival(String estimatedArrival) {
        this.estimatedArrival = estimatedArrival;
    }
    // public String getOriginCode() {
    //     return originCode;
    // }
    // public void setOriginCode(String originCode) {
    //     this.originCode = originCode;
    // }
    // public String getDestinationCode() {
    //     return destinationCode;
    // }
    // public void setDestinationCode(String destinationCode) {
    //     this.destinationCode = destinationCode;
    // }
    // public String getLatitude() {
    //     return latitude;
    // }
    // public void setLatitude(String latitude) {
    //     this.latitude = latitude;
    // }
    // public String getLongitude() {
    //     return longitude;
    // }
    // public void setLongitude(String longitude) {
    //     this.longitude = longitude;
    // }
    // public String getVisitNumber() {
    //     return visitNumber;
    // }
    // public void setVisitNumber(String visitNumber) {
    //     this.visitNumber = visitNumber;
    // }
    // public String getLoad() {
    //     return load;
    // }
    // public void setLoad(String load) {
    //     this.load = load;
    // }
    // public String getFeature() {
    //     return feature;
    // }
    // public void setFeature(String feature) {
    //     this.feature = feature;
    // }
    // public String getType() {
    //     return type;
    // }
    // public void setType(String type) {
    //     this.type = type;
    // }

    

    
}
