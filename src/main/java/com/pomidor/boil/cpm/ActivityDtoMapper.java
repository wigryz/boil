package com.pomidor.boil.cpm;

import com.pomidor.boil.cpm.calculation.Activity;
import com.pomidor.boil.cpm.dtos.ActivityDto;
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
