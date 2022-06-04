package com.pomidor.boil.transport.dtos;

public record UnitIncomeDto(
    Long supplierId,
    Long recipientId,
    Double unitIncome
) {
}
