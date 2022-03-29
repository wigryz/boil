package com.pomidor.boil.calculation;

import com.pomidor.boil.calculation.exceptions.ManyEndingPointsException;
import com.pomidor.boil.calculation.exceptions.ManyStartingPointsException;

import java.util.*;

public class CalcuteCPM {
    public static List<Happening> calculate(List<Activity> activityList) {
        //Creates Happenings according to activityList
        Map<Integer, Happening> happeningMap = new HashMap<>();
        for (Activity activity : activityList) {
            if (happeningMap.get(activity.previousHappeningId()) == null)
                happeningMap.put(activity.previousHappeningId(),
                                 new Happening(activity.previousHappeningId()));
            if (happeningMap.get(activity.nextHappeningId()) == null)
                happeningMap.put(activity.nextHappeningId(),
                                 new Happening(activity.nextHappeningId()));

            Happening previousHappening = happeningMap.get(activity.previousHappeningId());
            Happening nextHappening = happeningMap.get(activity.nextHappeningId());

            previousHappening.getNextHappenings().add(activity.nextHappeningId());
            nextHappening.getPreviousHappenings().add(activity.previousHappeningId());

            previousHappening.getNextActivities().add(activity);
            nextHappening.getPreviousActivities().add(activity);
        }
        //Checks if there's only one starting and ending point
        Integer startingPoint = null, endingPoint = null;
        for (Happening happening : happeningMap.values()) {
            if (!happening.hasPreviousHappening()) {
                if (startingPoint == null)
                    startingPoint = happening.getId();
                else
                    throw new ManyStartingPointsException();
            }
            if (!happening.hasNextHappening()) {
                if (endingPoint == null)
                    endingPoint = happening.getId();
                else
                    throw new ManyEndingPointsException();
            }
        }
        happeningMap.get(startingPoint).setMinHappenTime(0.0);
        happeningMap.get(startingPoint).setMaxHappenTime(0.0);

        //Calculates minimal times for all Happenings
        Map<Integer, Happening> tempHappeningMap = new HashMap<>(happeningMap);
        tempHappeningMap.remove(startingPoint);
        while (!tempHappeningMap.isEmpty()) {
            List<Happening> tempHappeningList = new ArrayList<>(tempHappeningMap.values());
            for (Happening happening : tempHappeningList) {
                if (allPreviousHaveMinCalculated(happeningMap, happening)) {
                    Double maxBetweenPrevious = 0.0;
                    for (Activity previousActivity : happening.getPreviousActivities()) {
                        Double tempMinTime = happeningMap.get(
                                                             previousActivity.previousHappeningId())
                                                         .getMinHappenTime() + previousActivity.duration();
                        if (tempMinTime > maxBetweenPrevious) {
                            maxBetweenPrevious = tempMinTime;
                        }
                    }
                    happening.setMinHappenTime(maxBetweenPrevious);
                    tempHappeningMap.remove(happening.getId());
                }
            }
        }
        Double allHappeningsTime = happeningMap.get(endingPoint).getMinHappenTime();
        happeningMap.get(endingPoint).setMaxHappenTime(allHappeningsTime);
        //Calculates maximal times for all Happenings
        tempHappeningMap = new HashMap<>(happeningMap);
        tempHappeningMap.remove(endingPoint);
        while (!tempHappeningMap.isEmpty()) {
            List<Happening> tempHappeningList = new ArrayList<>(tempHappeningMap.values());
            for (Happening happening : tempHappeningList) {
                if (allNextHaveMaxCalculated(happeningMap, happening)) {
                    Double minBetweenPrevious = allHappeningsTime;
                    for (Activity nextActivity : happening.getNextActivities()) {
                        Double tempMaxTime = happeningMap.get(nextActivity.nextHappeningId())
                                                         .getMaxHappenTime() - nextActivity.duration();
                        if (tempMaxTime < minBetweenPrevious) {
                            minBetweenPrevious = tempMaxTime;
                        }
                    }
                    happening.setMaxHappenTime(minBetweenPrevious);
                    tempHappeningMap.remove(happening.getId());
                }
            }
        }
        for (Happening happening : happeningMap.values()) {
            happening.setReserveTime();
        }
        return new ArrayList<>(happeningMap.values());
    }

    private static List<Activity> returnExampleData() {
        List<Activity> activityList = new ArrayList<>(
            Arrays.asList(new Activity("A", 3.0, 1, 2),
                          new Activity("B", 4.0, 2, 3),
                          new Activity("C", 6.0, 2, 4),
                          new Activity("D", 7.0, 3, 5),
                          new Activity("E", 1.0, 5, 7),
                          new Activity("F", 2.0, 4, 7),
                          new Activity("G", 3.0, 4, 6),
                          new Activity("H", 4.0, 6, 7),
                          new Activity("I", 1.0, 7, 8),
                          new Activity("J", 2.0, 8, 9)));
        return activityList;
    }

    private static Boolean allPreviousHaveMinCalculated(Map<Integer, Happening> happeningMap,
                                                        Happening happening) {
        for (Integer previousHappeningId : happening.getPreviousHappenings()) {
            if (!happeningMap.get(previousHappeningId).isMinTimeCalculated()) {
                return false;
            }
        }
        return true;
    }

    private static Boolean allNextHaveMaxCalculated(Map<Integer, Happening> happeningMap,
                                                    Happening happening) {
        for (Integer nextHappeningId : happening.getNextHappenings()) {
            if (!happeningMap.get(nextHappeningId).isMaxTimeCalculated()) {
                return false;
            }
        }
        return true;
    }

    private static Happening getHappeningFromId(int happeningId, List<Happening> happeningList) {
        for (Happening happening : happeningList) {
            if (happening.getId() == happeningId)
                return happening;
        }
        throw new RuntimeException();
    }

    private static Happening getFirstHappening(List<Happening> happenings) {
        Happening startingPoint = null;
        for (Happening happening : happenings) {
            if (!happening.hasPreviousHappening()) {
                if (startingPoint == null)
                    startingPoint = happening;
                else
                    throw new ManyStartingPointsException();
            }
        }
        return startingPoint;
    }

    public static Map<Integer, Integer> getCriticalPath(List<Happening> happenings) {
        Map<Integer, Integer> criticalPath = new HashMap<>();
        Happening start = getFirstHappening(happenings);
        getCriticalPath(start, happenings, criticalPath);
        return criticalPath;
    }

    public static double getCriticalPathLength(Map<Integer, Integer> criticalPath,
                                               List<Activity> activities) {
        return activities.stream().filter(activity -> {
            Integer nextHappeningId = criticalPath.get(activity.previousHappeningId());
            return Objects.equals(nextHappeningId, activity.nextHappeningId());
        }).mapToDouble(Activity::duration).sum();
    }

    private static void getCriticalPath(Happening start,
                                        List<Happening> happenings,
                                        Map<Integer, Integer> criticalPath) {
        Map<Integer, Integer> bestSubCritPath = new HashMap<>();
        List<Integer> nextHappeningIds = start.getNextHappenings().stream()
                                              .filter(h -> getHappeningFromId(h, happenings).getReserveTime() == 0)
                                              .toList();
        List<Activity> nextActivities = start.getNextActivities().stream()
                                             .filter(activity -> nextHappeningIds.contains(activity.nextHappeningId()))
                                             .toList();
        int nextActivityId = -1;
        for (Activity nextActivity : nextActivities) {
            Map<Integer, Integer> nextSubCritPath = new HashMap<>();
            Happening nextHappening = getHappeningFromId(nextActivity.nextHappeningId(),
                                                         happenings);
            getCriticalPath(nextHappening, happenings, criticalPath);
            if (nextSubCritPath.size() >= bestSubCritPath.size()) {
                bestSubCritPath = nextSubCritPath;
                nextActivityId = nextHappening.getId();
            }
        }
        if(nextActivityId != -1)
            criticalPath.put(start.getId(), nextActivityId);
    }
}