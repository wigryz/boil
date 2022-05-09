package com.pomidor.boil.transport.dtos;

import java.util.List;

public record TransportDto(
    List<UnitIncomeDto> unitIncomes,
    List<TransactionDto> transactions,
    Double totalCost,
    Double totalIncome,
    Double totalProfit
) {
}
