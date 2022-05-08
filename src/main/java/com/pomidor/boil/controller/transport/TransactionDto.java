package com.pomidor.boil.controller.transport;

public record TransactionDto(
    Long supplierId,
    Long recipientId,
    Double amount,
    Double totalIncome,
    Double totalCost
) {
}
