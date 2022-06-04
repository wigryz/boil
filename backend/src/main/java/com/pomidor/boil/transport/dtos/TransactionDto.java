package com.pomidor.boil.transport.dtos;

public record TransactionDto(
    Long supplierId,
    Long recipientId,
    Double amount
) {
}
