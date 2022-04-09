package com.pomidor.boil.controller.transport.dtos;

public record TransactionDto(
    Long supplierId,
    Long recipientId,
    Double amount,
    Double totalIncome,
    Double totalCost
) {
}
