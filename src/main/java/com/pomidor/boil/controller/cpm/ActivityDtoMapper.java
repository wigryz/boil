package com.pomidor.boil.controller.cpm;

import com.pomidor.boil.calculation.cpm.Activity;
import com.pomidor.boil.controller.cpm.dtos.ActivityDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityDtoMapper {
    public List<ActivityDto> map(List<Activity> activity) {
        return activity.stream()
                .map(a -> new ActivityDto(a.name(), a.duration(), a.nextHappeningId(), a.previousHappeningId()))
                .toList();
    }
}
