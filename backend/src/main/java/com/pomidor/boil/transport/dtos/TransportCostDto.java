package com.pomidor.boil.transport.dtos;

public record TransportCostDto(
    Integer supplierId,
    Integer recipientId,
    Double cost
) {
}
