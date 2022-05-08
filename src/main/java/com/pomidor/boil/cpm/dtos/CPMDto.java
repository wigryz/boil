package com.pomidor.boil.cpm.dtos;

import java.util.List;
import java.util.Map;

public record CPMDto (
    List<HappeningDto> happenings,
    List<ActivityDto> activities,
    Map<Integer, Integer> criticalPath,
    Double criticalPathLength
) {
}
