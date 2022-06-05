package com.pomidor.boil.transport;

import com.pomidor.boil.transport.dtos.RecipientDto;
import com.pomidor.boil.transport.dtos.SupplierDto;
import com.pomidor.boil.transport.dtos.TransportCostDto;
import com.pomidor.boil.transport.dtos.TransportInputDto;
import com.pomidor.boil.transport.model.CalculationAlgorithms;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TransportAlgorythmsTest {
    @Test
    public void AlgorithmTest(){
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
}
