package vttp2022.project.model;

import jakarta.json.JsonObject;


public class ArrivalBus {
    private String serviceNo;
    private String operator;
    private NextBus nextBus;
    private NextBus nextBus2;
    private NextBus nextBus3;

    public ArrivalBus create (JsonObject o) {
        ArrivalBus arrivalBus = new ArrivalBus();
        arrivalBus.serviceNo = o.getString("ServiceNo");
        arrivalBus.operator = o.getString("Operator");
        arrivalBus.nextBus = NextBus.create(o.getJsonObject("NextBus"));
        arrivalBus.nextBus2 = NextBus.create(o.getJsonObject("NextBus2"));
        arrivalBus.nextBus3 = NextBus.create(o.getJsonObject("NextBus3"));
    
    return arrivalBus;
}
    
    public String getServiceNo() {
        return serviceNo;
    }
    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public NextBus getNextBus() {
        return nextBus;
    }
    public void setNextBus(NextBus nextBus) {
        this.nextBus = nextBus;
    }
    public NextBus getNextBus2() {
        return nextBus2;
    }
    public void setNextBus2(NextBus nextBus2) {
        this.nextBus2 = nextBus2;
    }
    public NextBus getNextBus3() {
        return nextBus3;
    }
    public void setNextBus3(NextBus nextBus3) {
        this.nextBus3 = nextBus3;
    }
    
    
}
