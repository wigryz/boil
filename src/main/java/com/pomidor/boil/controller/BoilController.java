package com.pomidor.boil.controller;

import com.pomidor.boil.cpm.calculation.Activity;
import com.pomidor.boil.cpm.calculation.CalcuteCPM;
import com.pomidor.boil.cpm.calculation.Happening;
import com.pomidor.boil.cpm.ActivityDtoMapper;
import com.pomidor.boil.cpm.dtos.CPMDto;
import com.pomidor.boil.cpm.dtos.HappeningDto;
import com.pomidor.boil.cpm.dtoFront.CPM;
import com.pomidor.boil.cpm.dtoFront.CPMMapper;
import com.pomidor.boil.transport.dtos.RecipientDto;
import com.pomidor.boil.transport.dtos.SupplierDto;
import com.pomidor.boil.transport.dtos.TransactionDto;
import com.pomidor.boil.transport.dtos.TransportCostDto;
import com.pomidor.boil.transport.dtos.TransportDto;
import com.pomidor.boil.transport.dtos.TransportInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
                                                                                h.getNextHappenings()))
                                                     .toList();

        Double criticalPathLength = CalcuteCPM.getCriticalPathLength(criticalPath, activities);
        CPMDto dto = new CPMDto(happeningDtos,
                                activityDtoMapper.map(activities),
                                criticalPath,
                                criticalPathLength);
        return ResponseEntity.ok(cpmMapper.mapToCPM(dto));
    }

    @GetMapping("/test")
    public ResponseEntity<TransportDto> test() {
        return ResponseEntity.ok(outputTestData());
    }


    private TransportDto outputTestData() {
        List<TransactionDto> transactions = List.of(
            new TransactionDto(null, null, null, null, null));

        return new TransportDto(transactions);
    }
    private TransportInputDto inputTestData() {
        SupplierDto s1 = new SupplierDto(1L, 20.0, 10.0);
        SupplierDto s2 = new SupplierDto(2L, 30.0, 12.0);

        RecipientDto r1 = new RecipientDto(1L, 10.0, 30.0);
        RecipientDto r2 = new RecipientDto(2L, 28.0, 25.0);
        RecipientDto r3 = new RecipientDto(3L, 27.0, 30.0);

        List<TransportCostDto> transportCosts = List.of(new TransportCostDto(1L, 1L, 8.0),
                                                        new TransportCostDto(1L, 2L, 14.0),
                                                        new TransportCostDto(1L, 3L, 17.0),
                                                        new TransportCostDto(2L, 1L, 12.0),
                                                        new TransportCostDto(2L, 2L, 9.0),
                                                        new TransportCostDto(2L, 3L, 19.0));
        return new TransportInputDto(List.of(s1, s2),
                                     List.of(r1, r2, r3),
                                     transportCosts);
    }

    @PostMapping("/transport")
    public ResponseEntity<TransportDto> transport(
        @RequestBody TransportInputDto transportInputDto) {


        TransportDto transportDto = new TransportDto(List.of());
        return ResponseEntity.ok(transportDto);
    }
}
