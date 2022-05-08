package com.pomidor.boil.controller.cpm.dtos.dtos;

import com.pomidor.boil.calculation.cpm.Activity;

import java.util.List;
import java.util.Map;

public record CPMDto (
    List<HappeningDto> happenings,
    List<Activity> activities,
    Map<Integer, Integer> criticalPath,
    Double criticalPathLength
) {
}
