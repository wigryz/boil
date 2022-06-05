package com.pomidor.boil.transport.dtos;

public record UnitIncomeDto(
    Integer supplierId,
    Integer recipientId,
    Double unitIncome
) {
}
