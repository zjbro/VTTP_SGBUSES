package vttp2022.project.model;


import java.time.Duration;
import java.time.Instant;

import jakarta.json.JsonObject;

public class NextBus {
    private String estimatedArrival;


    public static NextBus create(JsonObject o){
        NextBus nextBus = new NextBus();
        Instant start = Instant.now();
        if (o.getString("EstimatedArrival").equals("")){
            nextBus.estimatedArrival = "No Est. Available";
            return nextBus;
        }
        Instant end = Instant.parse(o.getString("EstimatedArrival"));
        Duration timeElapsed = Duration.between(start, end);
        nextBus.estimatedArrival = Long.toString(timeElapsed.toMillis()/60000); //convert to minutes from miliseconds
        if(Integer.parseInt(nextBus.estimatedArrival) <= 0){
            nextBus.estimatedArrival = "Arr";
            return nextBus;
        }
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
    
}
