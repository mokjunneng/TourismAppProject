package com.example.androidproject;


import java.util.ArrayList;
import java.util.List;

public class SelectLocations {
    LocationEdge atob = new LocationEdge("Marina Bay", "Singapore Botanic Gardens", 1.23, 22, 10.5, 12, 143, 1);
    LocationEdge atoc = new LocationEdge("Marina Bay", "Bukit Timah Nature Reserve", 1.45, 31, 15.66, 21, 157, 2);
    LocationEdge atod = new LocationEdge("Marina Bay", "Gardens By the Bay, Singapore", Double.MAX_VALUE, Integer.MAX_VALUE, 5.40, 4, 3, 3);
    LocationEdge atoe = new LocationEdge("Marina Bay", "Macritchie Reservoir Park", 1.29, 40, 13.33, 19, 125, 4);
    LocationEdge atof = new LocationEdge("Marina Bay", "Sungei Buloh Wetland Reserve", 2.0, 102, 39.79, 52, 347, 5);

    LocationEdge btoa = new LocationEdge("Singapore Botanic Gardens", "Marina Bay", 1.23, 22, 13.7, 16, 143, 0);
    LocationEdge btoc = new LocationEdge("Singapore Botanic Gardens", "Bukit Timah Nature Reserve", 0.97, 17, 8.6, 10, 64, 2);
    LocationEdge btod = new LocationEdge("Singapore Botanic Gardens", "Gardens By the Bay, Singapore", 1.23, 35, 17.1, 18, 94, 3);
    LocationEdge btoe = new LocationEdge("Singapore Botanic Gardens", "Macritchie Reservoir Park", 0.97, 26, 7.57, 8, 40, 4);
    LocationEdge btof = new LocationEdge("Singapore Botanic Gardens", "Sungei Buloh Wetland Reserve", 1.8, 95, 29.0, 42, 254, 5);

    LocationEdge ctoa = new LocationEdge("Bukit Timah Nature Reserve", "Marina Bay", 1.53, 53, 25.38, 26, 157, 0);
    LocationEdge ctob = new LocationEdge("Bukit Timah Nature Reserve", "Singapore Botanic Gardens", 1.16, 39, 17.21, 15, 64, 1);
    LocationEdge ctod = new LocationEdge("Bukit Timah Nature Reserve", "Gardens By the Bay, Singapore", 1.49, 66, 30.62, 29, 153, 3);
    LocationEdge ctoe = new LocationEdge("Bukit Timah Nature Reserve", "Macritchie Reservoir Park", 1.23, 53, 15.33, 15, 92, 4);
    LocationEdge ctof = new LocationEdge("Bukit Timah Nature Reserve", "Sungei Buloh Wetland Reserve", 1.61, 90, 21.08, 37, 189, 5);

    LocationEdge dtoa = new LocationEdge("Gardens By the Bay, Singapore", "Marina Bay", Double.MAX_VALUE, Integer.MAX_VALUE, 7.8, 6, 3, 0);
    LocationEdge dtob = new LocationEdge("Gardens By the Bay, Singapore", "Singapore Botanic Gardens", 1.23, 35, 18.01, 17, 94, 1);
    LocationEdge dtoc = new LocationEdge("Gardens By the Bay, Singapore", "Bukit Timah Nature Reserve", 1.45, 44, 20.72, 23, 153, 2);
    LocationEdge dtoe = new LocationEdge("Gardens By the Bay, Singapore", "Macritchie Reservoir Park", 1.23, 53, 20.92, 26, 123, 4);
    LocationEdge dtof = new LocationEdge("Gardens By the Bay, Singapore", "Sungei Buloh Wetland Reserve", 1.96, 107, 44.45, 55, 345, 5);

    LocationEdge etoa = new LocationEdge("Macritchie Reservoir Park", "Marina Bay", 1.23, 37, 14.7, 19, 125, 0);
    LocationEdge etob = new LocationEdge("Macritchie Reservoir Park", "Singapore Botanic Gardens", 0.97, 25, 9.19, 12, 40, 1);
    LocationEdge etoc = new LocationEdge("Macritchie Reservoir Park", "Bukit Timah Nature Reserve", 1.23, 37, 12.8, 16, 92, 2);
    LocationEdge etod = new LocationEdge("Macritchie Reservoir Park", "Gardens By the Bay, Singapore", 1.23, 50, 17.69, 21, 123, 3);
    LocationEdge etof = new LocationEdge("Macritchie Reservoir Park", "Sungei Buloh Wetland Reserve", 1.87, 93, 33.9, 48, 282, 5);

    LocationEdge ftoa = new LocationEdge("Sungei Buloh Wetland Reserve", "Marina Bay", 1.96, 100, 40.4, 53, 347, 0);
    LocationEdge ftob = new LocationEdge("Sungei Buloh Wetland Reserve", "Singapore Botanic Gardens", 1.78, 84, 33.84, 44, 254, 1);
    LocationEdge ftoc = new LocationEdge("Sungei Buloh Wetland Reserve", "Bukit Timah Nature Reserve", 1.61, 77, 25.11, 37, 189, 2);
    LocationEdge ftod = new LocationEdge("Sungei Buloh Wetland Reserve", "Gardens By the Bay, Singapore", 1.97, 111, 38.95, 53, 345, 3);
    LocationEdge ftoe = new LocationEdge("Sungei Buloh Wetland Reserve", "Macritchie Reservoir Park", 1.88, 95, 32.46, 44, 282, 4);

    private List<LocationEdge> locationEdgesA = new ArrayList<>();
    private List<LocationEdge> locationEdgesB;
    private List<LocationEdge> locationEdgesC;
    private List<LocationEdge> locationEdgesD;
    private List<LocationEdge> locationEdgesE;
    private List<LocationEdge> locationEdgesF;

    private List<List<LocationEdge>> adjlist;

    SelectLocations(boolean botanic, boolean btnr, boolean gbtb, boolean macritchie, boolean sbwr){
        if(botanic){
            locationEdgesB = new ArrayList<>();
            locationEdgesA.add(atob);
            locationEdgesB.add(btoa);
            if(btnr){
                locationEdgesB.add(btoc);
            }
            if(gbtb){
                locationEdgesB.add(btod);
            }
            if(macritchie){
                locationEdgesB.add(btoe);
            }
            if(sbwr){
                locationEdgesB.add(btof);
            }
        }
        if(btnr){
            locationEdgesC = new ArrayList<>();
            locationEdgesA.add(atoc);
            locationEdgesC.add(ctoa);
            if(botanic){
                locationEdgesC.add(ctob);
            }
            if(gbtb){
                locationEdgesC.add(ctod);
            }
            if(macritchie){
                locationEdgesC.add(ctoe);
            }
            if(sbwr){
                locationEdgesC.add(ctof);
            }
        }
        if(gbtb){
            locationEdgesD = new ArrayList<>();
            locationEdgesA.add(atod);
            locationEdgesD.add(dtoa);
            if(botanic){
                locationEdgesD.add(dtob);
            }
            if(btnr){
                locationEdgesD.add(dtoc);
            }
            if(macritchie){
                locationEdgesD.add(dtoe);
            }
            if(sbwr){
                locationEdgesD.add(dtof);
            }
        }
        if(macritchie){
            locationEdgesE = new ArrayList<>();
            locationEdgesA.add(atoe);
            locationEdgesE.add(etoa);
            if(botanic){
                locationEdgesE.add(etob);
            }
            if(btnr){
                locationEdgesE.add(etoc);
            }
            if(gbtb){
                locationEdgesE.add(etod);
            }
            if(sbwr){
                locationEdgesE.add(etof);
            }
        }
        if(sbwr){
            locationEdgesF = new ArrayList<>();
            locationEdgesA.add(atof);
            locationEdgesF.add(etoa);
            if(botanic){
                locationEdgesF.add(ftob);
            }
            if(btnr){
                locationEdgesF.add(ftoc);
            }
            if(gbtb){
                locationEdgesF.add(ftod);
            }
            if(macritchie){
                locationEdgesF.add(ftoe);
            }
        }
        adjlist = new ArrayList<List<LocationEdge>>(6);
        for(int i = 0 ; i < 6; i++){
            adjlist.add(new ArrayList<LocationEdge>());
        }
        if(locationEdgesA != null){
            adjlist.set(0, locationEdgesA);
        }
        if(locationEdgesB != null){
            adjlist.set(1, locationEdgesB);
        }
        if(locationEdgesC != null){
            adjlist.set(2, locationEdgesC);
        }
        if(locationEdgesD != null){
            adjlist.set(3, locationEdgesD);
        }
        if(locationEdgesE != null){
            adjlist.set(4, locationEdgesE);
        }
        if(locationEdgesF != null){
            adjlist.set(5, locationEdgesF);
        }
    }

    public List<List<LocationEdge>> getAdjlist() {
        return adjlist;
    }
}
