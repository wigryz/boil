package com.pomidor.boil.controller;

import com.pomidor.boil.cpm.calculation.Activity;
import com.pomidor.boil.cpm.calculation.CalcuteCPM;
import com.pomidor.boil.cpm.calculation.Happening;
import com.pomidor.boil.cpm.ActivityDtoMapper;
import com.pomidor.boil.cpm.dtos.CPMDto;
import com.pomidor.boil.cpm.dtos.HappeningDto;
import com.pomidor.boil.cpm.dtoFront.CPM;
import com.pomidor.boil.cpm.dtoFront.CPMMapper;
import com.pomidor.boil.transport.dtos.*;
import com.pomidor.boil.transport.model.CalculationAlgorithms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleBiFunction;

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

    @GetMapping("/input")
    public ResponseEntity<TransportInputDto> test() {
        return ResponseEntity.ok(inputTestData());
    }


    private TransportDto outputTestData() {
        List<UnitIncomeDto> unitIncomes = List.of(
                new UnitIncomeDto(1, 1, 12.0),
                new UnitIncomeDto(1, 2, 1.0),
                new UnitIncomeDto(1, 3, 3.0),
                new UnitIncomeDto(2, 1, 6.0),
                new UnitIncomeDto(2, 2, 4.0),
                new UnitIncomeDto(2, 3, -1.0));

        List<TransactionDto> transactions = List.of(
                new TransactionDto(1, 1, 120),
                new TransactionDto(1, 2, 10),
                new TransactionDto(1, 3, 30),
                new TransactionDto(2, 1, 60),
                new TransactionDto(2, 2, 40),
                new TransactionDto(2, 3, 10)
        );

        Double totalCost = 100.0;
        Double totalProfit = 100.0;
        Double totalIncome = 100.0;

        return new TransportDto(unitIncomes, transactions, totalCost, totalIncome, totalProfit);
    }
    private TransportInputDto inputTestData() {
        SupplierDto s1 = new SupplierDto(1, 20, 10.0);
        SupplierDto s2 = new SupplierDto(2, 30, 12.0);

        RecipientDto r1 = new RecipientDto(1, 10, 30.0);
        RecipientDto r2 = new RecipientDto(2, 28, 25.0);
        RecipientDto r3 = new RecipientDto(3, 27, 30.0);

        List<TransportCostDto> transportCosts = List.of(new TransportCostDto(1, 1, 8.0),
                new TransportCostDto(1, 2, 14.0),
                new TransportCostDto(1, 3, 17.0),
                new TransportCostDto(2, 1, 12.0),
                new TransportCostDto(2, 2, 9.0),
                new TransportCostDto(2, 3, 19.0));
        return new TransportInputDto(List.of(s1, s2),
                List.of(r1, r2, r3),
                transportCosts);
    }

    @CrossOrigin(origins = "null")
    @PostMapping("/transport")
    public ResponseEntity<TransportDto> transport(@RequestBody TransportInputDto transportInputDto) {
        TransportDto transportDto = CalculationAlgorithms.calculateTransport(transportInputDto);
        return ResponseEntity.ok(transportDto);
    }
}
