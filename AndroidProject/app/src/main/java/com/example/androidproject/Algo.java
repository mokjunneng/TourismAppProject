package com.example.androidproject;


import java.util.ArrayList;
import java.util.List;

public class Algo {
    //shared variables between two algo
    private List<List<LocationEdge>> adjlist;
    private double totalCost;

    Algo(List<List<LocationEdge>> adjlist, double budget){
        //convert lcoations to adjmatrix
        this.adjlist = adjlist;
        this.budgetLeft = budget;
        this.bfBudgetLeft = budget;
    }

    //variables for fast algo
    private List<Location> route = new ArrayList<>();
    private boolean[] visited = new boolean[6];
    private int totalTime = 0;
    private double budgetLeft;
    private int minTime = Integer.MAX_VALUE;

    //params adjlist: 0: MBS, 1.Botanics 2. BT nature reserve, 3. Gardens, 4. Macritchies, 5. Sungei Buloh
    public void nearestNeighbor(Location location, Location firstLocation){
        this.visited[location.getId()] = true;
        double cost = 0;
        String nextLocation = "";
        String transportMode = "";
        Integer locationID = null;

        if(this.getRoute().size() == 5){
            for(LocationEdge le : this.adjlist.get(location.getId())){
                if(le.getTo().equals(firstLocation.getLocation())){
                    if(le.getPttime() < minTime && budgetLeft > le.getPtmoney()){
                        minTime = le.getPttime();
                        cost = le.getPtmoney();
                        transportMode = "Public Transport";
                        System.out.println(le.getPttime());
                    }
                    if(le.getTaxitime() < minTime && budgetLeft > le.getTaximoney()){
                        minTime = le.getTaxitime();
                        cost = le.getTaximoney();
                        transportMode = "Taxi";
                        System.out.println(le.getTaxitime());
                    }
                    if(le.getWalktime() < minTime){
                        minTime = le.getWalktime();
                        transportMode = "Walk";
                        System.out.println(le.getWalktime());
                    }
                }
            }
            this.totalTime += minTime;
            this.totalCost += cost;
            Location lastLocation = new Location(firstLocation.getLocation(), firstLocation.getId());
            lastLocation.setModeoftransport(transportMode);
            this.route.add(lastLocation);
        }

        for(LocationEdge le : this.adjlist.get(location.getId())){
            int ptTime = le.getPttime();
            int taxiTime = le.getTaxitime();
            int walkTime = le.getWalktime();
            double ptMoney = le.getPtmoney();
            double taxiMoney = le.getTaximoney();
            String nameOfLocation = le.getTo();

            if(!visited[le.getIdto()]) {
                if (ptTime < minTime && budgetLeft > ptMoney) {
                    minTime = ptTime;
                    nextLocation = nameOfLocation;
                    transportMode = "Public Transport";
                    locationID = le.getIdto();
                    cost = ptMoney;
                    //check for budget
                }
                if (taxiTime < minTime && budgetLeft > taxiMoney) {
                    minTime = taxiTime;
                    nextLocation = nameOfLocation;
                    transportMode = "Taxi";
                    locationID = le.getIdto();
                    cost = taxiMoney;

                }
                if (walkTime < minTime) {
                    minTime = walkTime;
                    nextLocation = nameOfLocation;
                    transportMode = "Foot";
                    locationID = le.getIdto();
                }
            }
        }


        if(this.getRoute().size() < 5) {
            this.totalTime += minTime;
            this.totalCost += cost;
            minTime = Integer.MAX_VALUE;
            budgetLeft -= cost;
            Location newLocation = new Location(nextLocation, locationID);
            newLocation.setModeoftransport(transportMode);
            this.route.add(newLocation);
            this.nearestNeighbor(newLocation, firstLocation);
        }

    }

    //initialize all the neighbor locations of the first location
    public void startBF(Location firstLocation){
        List<LocationEdge> firstLocationEdges = this.adjlist.get(firstLocation.getId());
        List<Location> locations = new ArrayList<>();
        for(LocationEdge le : firstLocationEdges){
            locations.add(new Location(le.getTo(), le.getIdto()));
        }
        naive(locations, new ArrayList<Location>(), firstLocation);
    }


    //find all possible routes
    private void naive(List<Location> locations, List<Location> route, Location firstLocation){
        if(locations.size() <= 0){
           this.findOptimalRoute(route, firstLocation);
        }

        for(Location location : locations){
            route.add(location);
            List<Location> newLocation = new ArrayList<>(locations);
            newLocation.remove(location);
            naive(newLocation, route, firstLocation);
            route.remove(location);
        }
    }

    //variables needed to find optimal route from brute force algo
    private List<Location> optimalRoute;
    //total time for optimal route
    private int bfminTime = Integer.MAX_VALUE;
    private double bfBudgetLeft;

    //find optimal route (lowest travelling time) that is within budget
    private void findOptimalRoute(List<Location> route, Location firstLocation){
//        System.out.println("--------------");
//        System.out.println(firstLocation.getLocation());
//        for(Location l : route){
//            System.out.println(l.getLocation());
//        }
        int routeTime = 0;
        double totalcost = 0;
        for(int i = 0; i <= route.size(); i++){
            if(i == 0){
                List<LocationEdge> firstLocationEdges = this.adjlist.get(firstLocation.getId());
                int minTransportTime = Integer.MAX_VALUE;
                double cost = 0;
                for(LocationEdge le : firstLocationEdges){
                    //check if location matches
                    if(le.getTo().equals(route.get(i).getLocation())){
                        if(le.getPttime() < minTransportTime && bfBudgetLeft > le.getPtmoney()){
                            minTransportTime = le.getPttime();
                            cost = le.getPtmoney();
                            route.get(i).setModeoftransport("Public Transport");
                        }
                        if(le.getTaxitime() < minTransportTime && bfBudgetLeft > le.getTaximoney()){
                            minTransportTime = le.getTaxitime();
                            cost = le.getTaximoney();
                            route.get(i).setModeoftransport("Taxi");
                        }
                        if(le.getWalktime() < minTransportTime){
                            minTransportTime = le.getWalktime();
                            cost = 0;
                            route.get(i).setModeoftransport("Walk");
                        }
                        routeTime += minTransportTime;
                    }
                }
                totalcost += cost;
                bfBudgetLeft -= cost;
            }else{
                List<LocationEdge> locationEdges = this.adjlist.get(route.get(i-1).getId());
                if(i == route.size()){
                    int minTransportTime = Integer.MAX_VALUE;
                    double cost = 0;
                    for(LocationEdge le: locationEdges){
                        if(le.getTo().equals(firstLocation.getLocation())){

                            if(le.getPttime() < minTransportTime && bfBudgetLeft > le.getPtmoney()){
                                minTransportTime = le.getPttime();
                                cost = le.getPtmoney();
                                firstLocation.setModeoftransport("Public Transport");
                            }
                            if(le.getTaxitime() < minTransportTime && bfBudgetLeft > le.getTaximoney()){
                                minTransportTime = le.getTaxitime();
                                cost = le.getTaximoney();
                                firstLocation.setModeoftransport("Taxi");
                            }
                            if(le.getWalktime() < minTransportTime){
                                minTransportTime = le.getWalktime();
                                cost = 0;
                                firstLocation.setModeoftransport("Walk");
                            }
                            routeTime += minTransportTime;
                        }
                    }
                    totalcost += cost;
                    bfBudgetLeft -= cost;
                }else{
                    int minTransportTime = Integer.MAX_VALUE;
                    double cost = 0;
                    for(LocationEdge le : locationEdges){
                        if(le.getTo().equals(route.get(i).getLocation())){
                            if(le.getPttime() < minTransportTime && bfBudgetLeft > le.getPtmoney()){
                                minTransportTime = le.getPttime();
                                cost = le.getPtmoney();
                                route.get(i).setModeoftransport("Public Transport");
                            }
                            if(le.getTaxitime() < minTransportTime && bfBudgetLeft > le.getTaximoney()){
                                minTransportTime = le.getTaxitime();
                                cost = le.getTaximoney();
                                route.get(i).setModeoftransport("Taxi");
                            }
                            if(le.getWalktime() < minTransportTime){
                                minTransportTime = le.getWalktime();
                                cost = 0;
                                route.get(i).setModeoftransport("Walk");
                            }
                            routeTime += minTransportTime;
                        }
                    }
                    totalcost += cost;
                    bfBudgetLeft -= cost;
                }

            }
        }
        //update optimal route
        if(routeTime < bfminTime){
            this.setBfminTime(routeTime);
            this.setTotalCost(totalcost);
            List<Location> latestOptimalRoute = new ArrayList<>(route);
            latestOptimalRoute.add(firstLocation);
            this.setOptimalRoute(latestOptimalRoute);
        }
        //reset budget for new route
        bfBudgetLeft = budgetLeft;

    }


    public List<Location> getOptimalRoute() {
        return optimalRoute;
    }

    public void setOptimalRoute(List<Location> optimalRoute) {
        this.optimalRoute = optimalRoute;
    }

    public int getBfminTime() {
        return bfminTime;
    }

    public void setBfminTime(int bfminTime) {
        this.bfminTime = bfminTime;
    }

    //route from nearest neighbor algo
    public List<Location> getRoute() {
        return route;
    }


    //route time for nearest neighbor
    public int getTotalTime() {
        return totalTime;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }



    //a: MBS, b.Botanics c. BT nature reserve, d. Gardens, e. Macritchies, f. Sungei Buloh
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
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

        List<LocationEdge> locationEdgesA = new ArrayList<>();
        locationEdgesA.add(atob);
        locationEdgesA.add(atoc);
        locationEdgesA.add(atod);
        locationEdgesA.add(atoe);
        locationEdgesA.add(atof);

        List<LocationEdge> locationEdgesB = new ArrayList<>();
        locationEdgesB.add(btoa);
        locationEdgesB.add(btoc);
        locationEdgesB.add(btod);
        locationEdgesB.add(btoe);
        locationEdgesB.add(btof);

        List<LocationEdge> locationEdgesC = new ArrayList<>();
        locationEdgesC.add(ctoa);
        locationEdgesC.add(ctob);
        locationEdgesC.add(ctod);
        locationEdgesC.add(ctoe);
        locationEdgesC.add(ctof);

        List<LocationEdge> locationEdgesD = new ArrayList<>();
        locationEdgesD.add(dtoa);
        locationEdgesD.add(dtob);
        locationEdgesD.add(dtoc);
        locationEdgesD.add(dtoe);
        locationEdgesD.add(dtof);

        List<LocationEdge> locationEdgesE = new ArrayList<>();
        locationEdgesE.add(etoa);
        locationEdgesE.add(etob);
        locationEdgesE.add(etoc);
        locationEdgesE.add(etod);
        locationEdgesE.add(etof);

        List<LocationEdge> locationEdgesF = new ArrayList<>();
        locationEdgesF.add(ftoa);
        locationEdgesF.add(ftob);
        locationEdgesF.add(ftoc);
        locationEdgesF.add(ftod);
        locationEdgesF.add(ftoe);

        List<List<LocationEdge>> adjlist = new ArrayList<List<LocationEdge>>();
        adjlist.add(locationEdgesA);
        adjlist.add(locationEdgesB);
        adjlist.add(locationEdgesC);
        adjlist.add(locationEdgesD);
        adjlist.add(locationEdgesE);
        adjlist.add(locationEdgesF);

//        Location mbs = new Location("Marina Bay Sands", 0);
//        Location botanic = new Location("Singapore Botanic Gardens", 1);
//        Location btnr = new Location("Bukit Timah Nature Reserve", 2);
//        Location gbtb = new Location("Gardens By the Bay, Singapore", 3);
//        Location macritchie = new Location("Macritchie Reservoir Park", 4);
//        Location sbwr = new Location("Sungei Buloh Wetland Reserve", 5);


        Algo solve = new Algo(adjlist, 30);

        Location first = new Location("Marina Bay", 0);

//        solve.nearestNeighbor(first, first);
//        long end = System.currentTimeMillis();
//        System.out.println("Total running time: " + (end - start) + " milliseconds");
//
//        System.out.println("Total travelling time: " + solve.getTotalTime());
//        System.out.println("Total travelling cost within budget: " + solve.getTotalCost());
//        List<Location> route = solve.getRoute();
//        for(Location l : route){
//            System.out.println(l.getLocation() + " " + l.getModeoftransport());
//        }
//        System.out.println("\n");

        solve.startBF(first);
        System.out.println("Total travelling time: " + solve.getBfminTime());
        System.out.println("Total travelling cost within budget: " + solve.getTotalCost());
        List<Location> optimalRoute = solve.getOptimalRoute();
        for(Location l : optimalRoute){
            System.out.println(l.getLocation() + " by " + l.getModeoftransport());
        }
    }
}
