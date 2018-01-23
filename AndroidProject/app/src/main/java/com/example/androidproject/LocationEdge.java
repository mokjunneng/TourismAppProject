package com.example.androidproject;


public class LocationEdge {
    private Integer idto;
    private String from;
    private String to;
    private Double ptmoney;
    private Integer pttime;
    private Double taximoney;
    private Integer taxitime;
    private Integer walktime;

    LocationEdge(String from, String to, Double ptmoney, Integer pttime, Double taximoney, Integer taxitime, Integer walktime, Integer idto){
        this.from = from;
        this.to = to;
        this.ptmoney = ptmoney;
        this.pttime = pttime;
        this.taximoney = taximoney;
        this.taxitime = taxitime;
        this.walktime = walktime;
        this.idto = idto;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Double getPtmoney() {
        return ptmoney;
    }

    public Integer getPttime() {
        return pttime;
    }

    public Double getTaximoney() {
        return taximoney;
    }

    public Integer getTaxitime() {
        return taxitime;
    }

    public Integer getWalktime() {
        return walktime;
    }

    public Integer getIdto() {
        return idto;
    }
}
