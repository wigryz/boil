package com.pomidor.boil.calculation.cpm;

import java.util.Objects;

public class Activity {
    private String name;
    private Double duration;
    private Integer previousHappeningId;
    private Integer nextHappeningId;

    public Activity(
        String name,
        Double duration,
        Integer previousHappeningId,
        Integer nextHappeningId
                   ) {
        this.name = name;
        this.duration = duration;
        this.previousHappeningId = previousHappeningId;
        this.nextHappeningId = nextHappeningId;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double duration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Integer previousHappeningId() {
        return previousHappeningId;
    }

    public void setPreviousHappeningId(Integer previousHappeningId) {
        this.previousHappeningId = previousHappeningId;
    }

    public Integer nextHappeningId() {
        return nextHappeningId;
    }

    public void setNextHappeningId(Integer nextHappeningId) {
        this.nextHappeningId = nextHappeningId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Activity) obj;
        return Objects.equals(this.name, that.name) &&
               Objects.equals(this.duration, that.duration) &&
               Objects.equals(this.previousHappeningId, that.previousHappeningId) &&
               Objects.equals(this.nextHappeningId, that.nextHappeningId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, duration, previousHappeningId, nextHappeningId);
    }

    @Override
    public String toString() {
        return "Activity[" +
               "name=" + name + ", " +
               "duration=" + duration + ", " +
               "previousHappeningId=" + previousHappeningId + ", " +
               "nextHappeningId=" + nextHappeningId + ']';
    }

}
