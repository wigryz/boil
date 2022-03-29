package com.pomidor.boil.controller;

import com.pomidor.boil.calculation.Activity;
import com.pomidor.boil.calculation.CalcuteCPM;
import com.pomidor.boil.calculation.Happening;
import com.pomidor.boil.controller.dtos.CPMDto;
import com.pomidor.boil.controller.dtos.HappeningDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Controller("boil")
public class BoilController {

    @Autowired
    private Validator validator;

    @PostMapping("/cpm")
    public ResponseEntity<CPMDto> calculate(@RequestBody List<Activity> activities) {

        validator.validate(activities);
        List<Happening> happenings = CalcuteCPM.calculate(activities);
        Map<Integer, Integer> criticalPath = CalcuteCPM.getCriticalPath(happenings);

        List<HappeningDto> happeningDtos = happenings.stream()
                                                     .map(h -> new HappeningDto(h.getId(),
                                                                                h.getMinHappenTime(),
                                                                                h.getMaxHappenTime(),
                                                                                h.getReserveTime())).toList();

        Double criticalPathLength = CalcuteCPM.getCriticalPathLength(criticalPath, activities);
        CPMDto dto = new CPMDto(happeningDtos,
                                activities,
                                criticalPath,
                                criticalPathLength);
        return ResponseEntity.ok(dto);
    }
}
