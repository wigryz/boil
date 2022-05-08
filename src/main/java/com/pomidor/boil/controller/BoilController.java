package com.pomidor.boil.controller;

import com.pomidor.boil.calculation.cpm.Activity;
import com.pomidor.boil.calculation.cpm.CalcuteCPM;
import com.pomidor.boil.calculation.cpm.Happening;
import com.pomidor.boil.controller.cpm.dtos.dtos.CPMDto;
import com.pomidor.boil.controller.cpm.dtos.dtos.HappeningDto;
import com.pomidor.boil.controller.transport.TransportDto;
import com.pomidor.boil.controller.transport.TransportInputDto;
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

    @PostMapping("/transport")
    public ResponseEntity<TransportDto> transport(@RequestBody TransportInputDto transportInputDto) {


        TransportDto transportDto = new TransportDto(List.of());
        return ResponseEntity.ok(transportDto);
    }
}
