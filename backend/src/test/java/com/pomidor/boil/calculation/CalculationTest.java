package com.pomidor.boil.calculation;

import com.pomidor.boil.cpm.calculation.Activity;
import com.pomidor.boil.cpm.calculation.CalcuteCPM;
import com.pomidor.boil.cpm.calculation.Happening;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CalculationTest {
    @Test
    public void calculationTest(){
        List<Activity> activityTestList = new ArrayList<>(
                Arrays.asList(new Activity("A",3.0,1, 2),
                        new Activity("B",4.0,2, 3),
                        new Activity("C",6.0,2, 4),
                        new Activity("D",7.0,3, 5),
                        new Activity("E",1.0,5, 7),
                        new Activity("F",2.0,4, 7),
                        new Activity("G",3.0,4, 6),
                        new Activity("H",4.0,6, 7),
                        new Activity("I",1.0,7, 8),
                        new Activity("J",2.0,8, 9)));
        List<Happening> happeningsExpected = new ArrayList<>(
                Arrays.asList(new Happening(1, 0.0, 0.0, 0.0, new ArrayList<>(), Arrays.asList(2)),
                        new Happening(2, 3.0, 3.0, 0.0, Arrays.asList(1), Arrays.asList(3,4)),
                        new Happening(3, 7.0, 8.0, 1.0, Arrays.asList(2), Arrays.asList(5)),
                        new Happening(4, 9.0, 9.0, 0.0, Arrays.asList(2), Arrays.asList(7,6)),
                        new Happening(5, 14.0, 15.0, 1.0, Arrays.asList(3), Arrays.asList(7)),
                        new Happening(6, 12.0, 12.0, 0.0, Arrays.asList(4), Arrays.asList(7)),
                        new Happening(7, 16.0, 16.0, 0.0, Arrays.asList(5,4,6), Arrays.asList(8)),
                        new Happening(8, 17.0, 17.0, 0.0, Arrays.asList(7), Arrays.asList(9)),
                        new Happening(9, 19.0, 19.0, 0.0, Arrays.asList(8), new ArrayList<>()))
        );
        Assertions.assertEquals(CalcuteCPM.calculate(activityTestList), happeningsExpected);
    }

    @Test
    void criticalPath() {
        List<Activity> activityTestList = new ArrayList<>(
            Arrays.asList(new Activity("A",3.0,1, 2),
                          new Activity("B",4.0,2, 3),
                          new Activity("C",6.0,2, 4),
                          new Activity("D",7.0,3, 5),
                          new Activity("E",1.0,5, 7),
                          new Activity("F",2.0,4, 7),
                          new Activity("G",3.0,4, 6),
                          new Activity("H",4.0,6, 7),
                          new Activity("I",1.0,7, 8),
                          new Activity("J",2.0,8, 9)));

        List<Happening> happenings = CalcuteCPM.calculate(activityTestList);
        Map<Integer, Integer> criticalPath = CalcuteCPM.getCriticalPath(happenings);
        System.out.println(criticalPath);
        double criticalPathLength = CalcuteCPM.getCriticalPathLength(criticalPath,
                                                                     activityTestList);
        System.out.println(criticalPathLength);
    }
}
