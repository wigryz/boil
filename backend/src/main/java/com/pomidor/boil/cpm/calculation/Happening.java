package com.pomidor.boil.cpm.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Happening {
    private Integer id;
    private Double minHappenTime = null;
    private Double maxHappenTime = null;
    private Double reserveTime = null;
    private List<Integer> previousHappenings;
    private List<Integer> nextHappenings;
    private List<Activity> previousActivities;
    private List<Activity> nextActivities;

    public Happening(Integer id) {
        this.id = id;
        previousHappenings = new ArrayList<>();
        nextHappenings = new ArrayList<>();
        previousActivities = new ArrayList<>();
        nextActivities = new ArrayList<>();
    }

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

    public List<Integer> getPreviousHappenings() {
        return previousHappenings;
    }

    public List<Integer> getNextHappenings() {
        return nextHappenings;
    }

    public List<Activity> getPreviousActivities() {
        return previousActivities;
    }

    public List<Activity> getNextActivities() {
        return nextActivities;
    }

    public Boolean isMinTimeCalculated(){
        return minHappenTime!=null;
    }
    public Boolean isMaxTimeCalculated(){
        return maxHappenTime!=null;
    }
    public Boolean hasPreviousHappening(){
        return !previousHappenings.isEmpty();
    }
    public Boolean hasManyPreviousHappenings(){
        return previousHappenings.size()>1;
    }
    public Boolean hasNextHappening(){
        return !nextHappenings.isEmpty();
    }
    public void setReserveTime(){
        if(minHappenTime!=null && maxHappenTime!=null){
            reserveTime=maxHappenTime-minHappenTime;
        }
    }

    public List<Integer> getNextHappeningsId() {
        return nextHappenings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Happening happening = (Happening) o;
        return Objects.equals(id, happening.id) && Objects.equals(minHappenTime, happening.minHappenTime) && Objects.equals(maxHappenTime, happening.maxHappenTime) && Objects.equals(reserveTime, happening.reserveTime) && Objects.equals(previousHappenings, happening.previousHappenings) && Objects.equals(nextHappenings, happening.nextHappenings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, minHappenTime, maxHappenTime, reserveTime, previousHappenings, nextHappenings);
    }
}
