package com.pomidor.boil.transport.dtos;

public record TransactionDto(
    Integer supplierId,
    Integer recipientId,
    Integer amount
) {
}
