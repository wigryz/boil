package com.pomidor.boil.calculation;

import java.util.List;

public class Happening {
    private Integer id;
    private Double minHappenTime;
    private Double maxHappenTime;
    private Double reserveTime;
    List<Integer> previousHappenings;
    List<Integer> nextHappenings;

    public Happening(Integer id, Double minHappenTime, Double maxHappenTime,
                     Double reserveTime, List<Integer> previousHappenings,
                     List<Integer> nextHappenings) {
        this.id = id;
        this.minHappenTime = minHappenTime;
        this.maxHappenTime = maxHappenTime;
        this.reserveTime = reserveTime;
        this.previousHappenings = previousHappenings;
        this.nextHappenings = nextHappenings;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMinHappenTime() {
        return minHappenTime;
    }

    public void setMinHappenTime(Double minHappenTime) {
        this.minHappenTime = minHappenTime;
    }

    public Double getMaxHappenTime() {
        return maxHappenTime;
    }

    public void setMaxHappenTime(Double maxHappenTime) {
        this.maxHappenTime = maxHappenTime;
    }

    public Double getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(Double reserveTime) {
        this.reserveTime = reserveTime;
    }

    public List<Integer> getPreviousHappenings() {
        return previousHappenings;
    }

    public void setPreviousHappenings(List<Integer> previousHappenings) {
        this.previousHappenings = previousHappenings;
    }

    public List<Integer> getNextHappenings() {
        return nextHappenings;
    }

    public void setNextHappenings(List<Integer> nextHappenings) {
        this.nextHappenings = nextHappenings;
    }
}
