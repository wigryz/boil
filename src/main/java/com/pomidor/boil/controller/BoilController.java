package com.pomidor.boil.controller;

import com.pomidor.boil.calculation.cpm.Activity;
import com.pomidor.boil.calculation.cpm.CalcuteCPM;
import com.pomidor.boil.calculation.cpm.Happening;
import com.pomidor.boil.controller.cpm.ActivityDtoMapper;
import com.pomidor.boil.controller.cpm.dtos.CPMDto;
import com.pomidor.boil.controller.cpm.dtos.HappeningDto;
import com.pomidor.boil.controller.cpm.dtoFront.CPM;
import com.pomidor.boil.controller.cpm.dtoFront.CPMMapper;
import com.pomidor.boil.controller.transport.dtos.TransportDto;
import com.pomidor.boil.controller.transport.dtos.TransportInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller("boil")
public class BoilController {

    @Autowired
    private ActivityDtoMapper activityDtoMapper;
    @Autowired
    private CPMMapper cpmMapper;
    @Autowired
    private Validator validator;

    @PostMapping("/old-cpm")
    public ResponseEntity<CPMDto> calculate(@RequestBody List<Activity> activities) {

        validator.validate(activities);
        List<Happening> happenings = CalcuteCPM.calculate(activities);
        Map<Integer, Integer> criticalPath = CalcuteCPM.getCriticalPath(happenings);

        List<HappeningDto> happeningDtos = happenings.stream()
                                                     .map(h -> new HappeningDto(h.getId(),
                                                                                h.getMinHappenTime(),
                                                                                h.getMaxHappenTime(),
                                                                                h.getReserveTime(),
                                                             h.getNextHappenings())).toList();

        Double criticalPathLength = CalcuteCPM.getCriticalPathLength(criticalPath, activities);
        CPMDto dto = new CPMDto(happeningDtos,
                                activityDtoMapper.map(activities),
                                criticalPath,
                                criticalPathLength);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/cpm")
    public ResponseEntity<CPM> cpm(@RequestBody List<Activity> activities) {

        validator.validate(activities);
        List<Happening> happenings = CalcuteCPM.calculate(activities);
        Map<Integer, Integer> criticalPath = CalcuteCPM.getCriticalPath(happenings);

        List<HappeningDto> happeningDtos = happenings.stream()
                .map(h -> new HappeningDto(h.getId(),
                        h.getMinHappenTime(),
                        h.getMaxHappenTime(),
                        h.getReserveTime(),
                        h.getNextHappenings())).toList();

        Double criticalPathLength = CalcuteCPM.getCriticalPathLength(criticalPath, activities);
        CPMDto dto = new CPMDto(happeningDtos,
                activityDtoMapper.map(activities),
                criticalPath,
                criticalPathLength);
        return ResponseEntity.ok(cpmMapper.mapToCPM(dto));
    }

    @GetMapping("/test")
    public ResponseEntity<CPM> test() {
        CPM cpm = cpmMapper.mapToCPM(getTestData());
        return ResponseEntity.ok(cpm);
    }

    private CPMDto getTestData() {
        List<Activity> activities = new ArrayList<>(
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

        List<Happening> happenings = CalcuteCPM.calculate(activities);
        Map<Integer, Integer> criticalPath = CalcuteCPM.getCriticalPath(happenings);

        List<HappeningDto> happeningDtos = happenings.stream()
                .map(h -> new HappeningDto(h.getId(),
                        h.getMinHappenTime(),
                        h.getMaxHappenTime(),
                        h.getReserveTime(),
                        h.getNextHappenings())).toList();

        Double criticalPathLength = CalcuteCPM.getCriticalPathLength(criticalPath, activities);
        return new CPMDto(happeningDtos,
                activityDtoMapper.map(activities),
                criticalPath,
                criticalPathLength);
    }

    @PostMapping("/transport")
    public ResponseEntity<TransportDto> transport(@RequestBody TransportInputDto transportInputDto) {


        TransportDto transportDto = new TransportDto(List.of());
        return ResponseEntity.ok(transportDto);
    }
}
