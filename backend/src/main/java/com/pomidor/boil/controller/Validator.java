package com.pomidor.boil.controller;

import com.pomidor.boil.cpm.calculation.Activity;
import com.pomidor.boil.cpm.exceptions.DurationLessThanZeroException;
import com.pomidor.boil.cpm.exceptions.DurationNullException;
import com.pomidor.boil.cpm.exceptions.IdNullException;
import com.pomidor.boil.cpm.exceptions.NameNotUniqueException;
import com.pomidor.boil.cpm.exceptions.NameNullException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class Validator {
    public boolean validate(List<Activity> activities) {

        activities.forEach(activity -> {
            if(Objects.isNull(activity.name())) throw new NameNullException();
            if(Objects.isNull(activity.duration())) throw new DurationNullException();
            if(Objects.isNull(activity.nextHappeningId())) throw new IdNullException();
            if(Objects.isNull(activity.previousHappeningId())) throw new NameNullException();
        });

        List<String> names = activities.stream().map(Activity::name).map(String::trim).toList();
        if(names.size() > names.stream().distinct().toList().size())
            throw new NameNotUniqueException();

        activities.forEach(activity -> {
            if(activity.duration() <= 0) throw new DurationLessThanZeroException();
        });

        return true;
    }
}
